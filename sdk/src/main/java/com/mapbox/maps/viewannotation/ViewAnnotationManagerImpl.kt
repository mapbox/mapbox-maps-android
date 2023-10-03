package com.mapbox.maps.viewannotation

import android.graphics.Rect
import android.os.Looper
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.annotation.*
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.view.isVisible
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.getProjection
import com.mapbox.maps.viewannotation.ViewAnnotation.Companion.USER_FIXED_DIMENSION
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.collections.Map
import kotlin.math.abs

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ViewAnnotationManagerImpl(
  mapView: MapView,
  private val viewAnnotationsLayout: FrameLayout = FrameLayout(mapView.context),
) : ViewAnnotationManager, DelegatingViewAnnotationPositionsUpdateListener() {
  private val mapboxMap: MapboxMap = mapView.getMapboxMap()
  private val renderThread = mapView.mapController.renderer.renderThread
  private val pixelRatio = mapView.resources.displayMetrics.density

  init {
    viewAnnotationsLayout.layoutParams = FrameLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT,
    )
    // place the view annotations above the map (index 0) but below the compass, ruler and other plugin views
    mapView.addView(viewAnnotationsLayout, 1)
    mapView.requestDisallowInterceptTouchEvent(false)
    mapboxMap.setViewAnnotationPositionsUpdateListener(this)
  }

  private val annotationMap = ConcurrentHashMap<String, ViewAnnotation>()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val idLookupMap = ConcurrentHashMap<View, String>()
  private val currentlyDrawnViewIdSet = mutableSetOf<String>()
  private val unpositionedViews = mutableSetOf<View>()

  // using copy on write as user could remove listener while callback is invoked
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val viewUpdatedListenerSet = CopyOnWriteArraySet<OnViewAnnotationUpdatedListener>()

  @Volatile
  private var updatedPositionsList: List<DelegatingViewAnnotationPositionDescriptor> = listOf()

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflater: AsyncLayoutInflater,
    asyncInflateCallback: (View) -> Unit
  ) {
    validateOptions(options)
    asyncInflater.inflate(resId, viewAnnotationsLayout) { view, _, _ ->
      asyncInflateCallback.invoke(prepareViewAnnotation(view, options))
    }
  }

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): View {
    validateOptions(options)
    val view = LayoutInflater.from(viewAnnotationsLayout.context).inflate(resId, viewAnnotationsLayout, false)
    return prepareViewAnnotation(view, options)
  }

  override fun addViewAnnotation(view: View, options: ViewAnnotationOptions) {
    if (idLookupMap.containsKey(view)) {
      throw MapboxViewAnnotationException(
        "Trying to add view annotation that was already added before! " +
          "Please consider deleting annotation view ($view) beforehand."
      )
    }
    validateOptions(options)
    prepareViewAnnotation(view, options)
  }

  override fun removeViewAnnotation(view: View): Boolean {
    val id = idLookupMap.remove(view) ?: return false
    val annotation = annotationMap.remove(id) ?: return false
    currentlyDrawnViewIdSet.remove(id)
    remove(id, annotation)
    return true
  }

  override fun removeAllViewAnnotations() {
    annotationMap.forEach { (id, annotation) ->
      remove(id, annotation)
    }
    currentlyDrawnViewIdSet.clear()
    annotationMap.clear()
    idLookupMap.clear()
  }

  override fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ): Boolean {
    val id = idLookupMap[view] ?: return false
    checkAssociatedFeatureIdUniqueness(options)
    annotationMap[id]?.let {
      it.handleVisibilityAutomatically = (options.visible == null)
      if (options.width != null) {
        it.measuredWidth = USER_FIXED_DIMENSION
      }
      if (options.height != null) {
        it.measuredHeight = USER_FIXED_DIMENSION
      }
      getValue(mapboxMap.updateViewAnnotation(id, options))
      return true
    } ?: return false
  }

  override fun getViewAnnotationByFeatureId(featureId: String): View? {
    val (view, _) = findByFeatureId(featureId)
    return view
  }

  override fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions? {
    val (_, options) = findByFeatureId(featureId)
    return options
  }

  override fun getViewAnnotationOptionsByView(view: View): ViewAnnotationOptions? {
    val id = idLookupMap[view] ?: return null
    return getValue(mapboxMap.getViewAnnotationOptions(id))
  }

  override fun addOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener) {
    viewUpdatedListenerSet.add(listener)
  }

  override fun removeOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener) {
    viewUpdatedListenerSet.remove(listener)
  }

  @AnyThread
  override fun setViewAnnotationUpdateMode(mode: ViewAnnotationUpdateMode) {
    renderThread.viewAnnotationMode = mode
  }

  @AnyThread
  override fun getViewAnnotationUpdateMode(): ViewAnnotationUpdateMode {
    return renderThread.viewAnnotationMode
  }

  override val annotations: Map<View, ViewAnnotationOptions>
    get() = HashMap<View, ViewAnnotationOptions>().apply {
      idLookupMap.forEach { (view, _) ->
        getViewAnnotationOptionsByView(view)?.let { viewAnnotationOptions ->
          put(view, viewAnnotationOptions)
        }
      }
    }

  /**
   * ViewAnnotations has width and height, so [MapboxMap.cameraForCoordinates] and
   * [MapboxMap.cameraForCoordinateBounds] doesn't return correct frame for given annotations.
   *
   * In order to calculate correct bounds that includes width and height of viewAnnotations, we used following steps
   * Step1: Calculate cameraOptions using cameraForCoordinates without considering the width and height of
   * viewAnnotations.
   * Step2: Calculate [MapboxMap.getMetersPerPixelAtLatitude] with zoom obtained from cameraOptions.
   * Step3: Calculate projected meter at the anchor of viewAnnotation using [MapboxMap.projectedMetersForCoordinate]
   * Step4: Adjust northing and easting vector of viewAnnotation with width, height and [getViewAnnotationOptionsFrame],
   * this will give us north, east, south, west projected meters values.
   * Step5: Get northEast and southWest coordinates using [MapboxMap.coordinateForProjectedMeters] and
   * extend bounds using all viewAnnotations this will adjust the bounds with new [CameraOptions].
   * Step6: Follow step2 to step5 till we get correct bounds and no other adjustment is needed.
   *
   * @return [CameraOptions] object.
   */
  override fun cameraForAnnotations(
    annotations: List<View>,
    edgeInsets: EdgeInsets?,
    bearing: Double?,
    pitch: Double?
  ): CameraOptions? {
    if (mapboxMap.style?.getProjection()?.name == ProjectionName.GLOBE || annotations.isEmpty()) {
      return null
    }
    val viewAnnotationOptions = annotations.mapNotNull {
      if (it.isVisible) return@mapNotNull getViewAnnotationOptionsByView(it) else null
    }.filter { it.visible != false }

    if (viewAnnotationOptions.isEmpty()) return null
    val coordinates = coordinatesFromAnnotations(viewAnnotationOptions)
    var cameraOptionForCoordinates = mapboxMap.cameraForCoordinates(
      coordinates,
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      bearing,
      pitch
    )

    var isCorrectBound = false
    // keep north, east, west and southmost Annotation and its frame to adjust paddings for zoom.
    var north: Pair<ViewAnnotationOptions, Rect?>? = null
    var east: Pair<ViewAnnotationOptions, Rect?>? = null
    var west: Pair<ViewAnnotationOptions, Rect?>? = null
    var south: Pair<ViewAnnotationOptions, Rect?>? = null

    // we run the loop twice to optimize bounds correctly to fit all the annotations. this might not
    // provide correct results when map is pitched or have bearing.
    var boundsCounter = 1
    while (!isCorrectBound && boundsCounter <= MAX_ADJUST_BOUNDS_COUNTER) {
      val zoom = cameraOptionForCoordinates.zoom
      boundsCounter++
      isCorrectBound = true
      viewAnnotationOptions.forEach { options ->
        val frame = getViewAnnotationOptionsFrame(options) ?: Rect(0, 0, 0, 0)
        val annotationBounds = calculateCoordinateBoundForAnnotation(options, frame, zoom)
        if (north == null || calculateCoordinateBoundForAnnotation(north!!.first, frame, zoom).north() < annotationBounds.north()) {
          north = Pair(options, frame)
          isCorrectBound = false
        }
        if (east == null || calculateCoordinateBoundForAnnotation(east!!.first, frame, zoom).east() < annotationBounds.east()) {
          east = Pair(options, frame)
          isCorrectBound = false
        }
        if (south == null || calculateCoordinateBoundForAnnotation(south!!.first, frame, zoom).south() > annotationBounds.south()) {
          south = Pair(options, frame)
          isCorrectBound = false
        }
        if (west == null || calculateCoordinateBoundForAnnotation(west!!.first, frame, zoom).west() > annotationBounds.west()) {
          west = Pair(options, frame)
          isCorrectBound = false
        }
      }

      // adding extra checks for nullability (this shouldn't execute normally as we are checking nullability in loop of viewannotations).
      if (north == null || east == null || south == null || west == null) {
        logW(TAG, "ViewAnnotation options framing is null. Returning null camera option")
        return null
      }

      val coordinateBoundsForCamera = CoordinateBounds(
        Point.fromLngLat(west!!.first.coordinate().longitude(), south!!.first.coordinate().latitude()),
        Point.fromLngLat(east!!.first.coordinate().longitude(), north!!.first.coordinate().latitude())
      )

      val paddings = EdgeInsets(
        (edgeInsets?.top ?: 0).toDouble() + abs((north!!.second?.top ?: 0.0).toDouble()),
        (edgeInsets?.left ?: 0).toDouble() + abs((west!!.second?.left ?: 0.0).toDouble()),
        (edgeInsets?.bottom ?: 0).toDouble() + abs((south!!.second?.bottom ?: 0.0).toDouble()),
        (edgeInsets?.right ?: 0).toDouble() + abs((east!!.second?.right ?: 0.0).toDouble())
      )

      cameraOptionForCoordinates = mapboxMap.cameraForCoordinateBounds(
        coordinateBoundsForCamera,
        paddings,
        bearing,
        pitch
      )
    }
    return cameraOptionForCoordinates
  }

  private fun ViewAnnotationOptions.coordinate() = geometry as Point

  /**
   * Function to calculate coordinatebound associated with annotation option.
   * this uses [MapboxMap.projectedMetersForCoordinate] and [MapboxMap.coordinateForProjectedMeters]
   * function to get correct coordinate for northEast and southWest point of annotations.
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun calculateCoordinateBoundForAnnotation(
    viewAnnotationOptions: ViewAnnotationOptions,
    annotationFrame: Rect,
    zoom: Double?
  ): CoordinateBounds {
    val metersPerPixelAtLatitude =
      if (zoom == null) mapboxMap.getMetersPerPixelAtLatitude(viewAnnotationOptions.coordinate().latitude()) else
        mapboxMap.getMetersPerPixelAtLatitude(viewAnnotationOptions.coordinate().latitude(), zoom)

    val projectedMeterForCoordinate = mapboxMap.projectedMetersForCoordinate(viewAnnotationOptions.coordinate())
    val metersPerPixelDensity = metersPerPixelAtLatitude / pixelRatio
    val northing = projectedMeterForCoordinate.northing + (abs(annotationFrame.top).toDouble() * metersPerPixelDensity)
    val easting = projectedMeterForCoordinate.easting + (abs(annotationFrame.right).toDouble() * metersPerPixelDensity)
    val southing = projectedMeterForCoordinate.northing - (abs(annotationFrame.bottom).toDouble() * metersPerPixelDensity)
    val westing = projectedMeterForCoordinate.easting - (abs(annotationFrame.left).toDouble() * metersPerPixelDensity)

    val projectedMeterNorthEast = ProjectedMeters(northing, easting)
    val projectedMeterSouthWest = ProjectedMeters(southing, westing)

    val coordinateNorthEast = mapboxMap.coordinateForProjectedMeters(projectedMeterNorthEast)
    val coordinateSouthWest = mapboxMap.coordinateForProjectedMeters(projectedMeterSouthWest)
    return CoordinateBounds(coordinateSouthWest, coordinateNorthEast)
  }

  /**
   * Function to get coordinates from list of [ViewAnnotationOptions].
   */
  private fun coordinatesFromAnnotations(annotationOptions: List<ViewAnnotationOptions>): List<Point> {
    val coordinatesList = mutableListOf<Point>()
    annotationOptions.forEach {
      it.geometry?.let { geometry ->
        coordinatesList.add(geometry as Point)
      }
    }
    return coordinatesList
  }

  /**
   * Get [Rect] from [ViewAnnotationOptions]'s geometry, width and height.
   * This function takes [ViewAnnotationOptions.geometry] as the center of rectangle and
   * use width, height and offset values to calculate [Rect] associated.
   *
   * @return [Rect] associated with [ViewAnnotationOptions]
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun getViewAnnotationOptionsFrame(viewAnnotationOptions: ViewAnnotationOptions?): Rect? {
    viewAnnotationOptions?.let { options ->
      if (options.width != null && options.height != null) {
        val offsetWidth = if (options.width!! > 0) (options.width!! * 0.5).toInt() else 0
        val offsetHeight = if (options.height!! > 0) (options.height!! * 0.5).toInt() else 0
        // create a dummy rect with center assume at 0,0 with offsetWidth and offsetHeight.
        val rect = Rect(
          -offsetWidth,
          -offsetHeight,
          offsetWidth,
          offsetHeight
        )

        // offset rect with respect to anchor defined in viewannotation options.
        when (options.anchor ?: ViewAnnotationAnchor.CENTER) {
          ViewAnnotationAnchor.TOP -> rect.offset(0, offsetHeight)
          ViewAnnotationAnchor.TOP_LEFT -> rect.offset(offsetWidth, offsetHeight)
          ViewAnnotationAnchor.TOP_RIGHT -> rect.offset(-offsetWidth, offsetHeight)
          ViewAnnotationAnchor.BOTTOM -> rect.offset(0, -offsetHeight)
          ViewAnnotationAnchor.BOTTOM_LEFT -> rect.offset(offsetWidth, -offsetHeight)
          ViewAnnotationAnchor.BOTTOM_RIGHT -> rect.offset(-offsetWidth, -offsetHeight)
          ViewAnnotationAnchor.LEFT -> rect.offset(offsetWidth, 0)
          ViewAnnotationAnchor.RIGHT -> rect.offset(-offsetWidth, 0)
          else -> rect.offset(0, 0)
        }
        // add view annotation option's offsetX and offsetY field to offset the rect.
        rect.offset(options.offsetX ?: 0, options.offsetY ?: 0)
        return rect
      }
    }
    return null
  }

  /**
   * We will have two calls of this callback:
   * - first from render thread with actual position list
   * - second from main thread with empty list in any case.
   *
   * We need separate call from main thread as scheduling on main thread happens much faster in C++
   * then rescheduling using Java handler.
   */
  @AnyThread
  override fun onDelegatingViewAnnotationPositionsUpdate(positions: List<DelegatingViewAnnotationPositionDescriptor>) {
    // When called from render thread it means we're in the end of [MapInterface.render] call if positions did change.
    // It's crucial to notify render thread in this callback as depending on mode we're using we need
    // either swap buffers the same or the next frame.
    if (Looper.myLooper() == Looper.getMainLooper()) {
      // create copy to avoid concurrent modification exception
      val immutablePositionListCopy = updatedPositionsList
      // schedule positioning on next frame using Choreographer from main thread
      Choreographer.getInstance().postFrameCallback {
        positionAnnotationViews(immutablePositionListCopy)
      }
    } else {
      // Called as soon as possible on main thread after updated positions arrived on render thread.
      // We need another callback from core as scheduling from render thread to main happens too slow
      // when using Java Main Looper.
      // update that flag here if callback was triggered, it will be reset by renderer directly when swapping buffers
      renderThread.needViewAnnotationSync = true
      updatedPositionsList = positions
    }
  }

  fun destroy() {
    mapboxMap.setViewAnnotationPositionsUpdateListener(null)
    viewUpdatedListenerSet.clear()
    removeAllViewAnnotations()
  }

  private fun validateOptions(options: ViewAnnotationOptions) {
    if (options.geometry == null) {
      throw IllegalArgumentException(EXCEPTION_TEXT_GEOMETRY_IS_NULL)
    }
  }

  private fun checkAssociatedFeatureIdUniqueness(options: ViewAnnotationOptions) {
    options.associatedFeatureId?.let { associatedFeatureId ->
      val (view, _) = findByFeatureId(associatedFeatureId)
      if (view != null) {
        throw MapboxViewAnnotationException(
          String.format(
            EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS,
            associatedFeatureId
          )
        )
      }
    }
  }

  private fun prepareViewAnnotation(inflatedView: View, options: ViewAnnotationOptions): View {
    checkAssociatedFeatureIdUniqueness(options)
    val inflatedViewLayout = inflatedView.layoutParams as FrameLayout.LayoutParams
    val updatedOptions = options.toBuilder()
      .width(options.width ?: inflatedViewLayout.width)
      .height(options.height ?: inflatedViewLayout.height)
      .build()
    val viewAnnotation = ViewAnnotation(
      view = inflatedView,
      handleVisibilityAutomatically = (options.visible == null),
      visibility = ViewAnnotationVisibility.INITIAL,
      viewLayoutParams = inflatedViewLayout,
      measuredWidth = if (options.width != null) USER_FIXED_DIMENSION else inflatedViewLayout.width,
      measuredHeight = if (options.height != null) USER_FIXED_DIMENSION else inflatedViewLayout.height,
    )
    // triggered not so often and needed to control view's width and height when WRAP_CONTENT is used
    val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
      if (viewAnnotation.measuredWidth != USER_FIXED_DIMENSION &&
        inflatedView.measuredWidth > 0 &&
        inflatedView.measuredWidth != viewAnnotation.measuredWidth
      ) {
        viewAnnotation.measuredWidth = inflatedView.measuredWidth
        getValue(
          mapboxMap.updateViewAnnotation(
            viewAnnotation.id,
            ViewAnnotationOptions.Builder()
              .width(inflatedView.measuredWidth)
              .build()
          )
        )
      }
      if (viewAnnotation.measuredHeight != USER_FIXED_DIMENSION &&
        inflatedView.measuredHeight > 0 &&
        inflatedView.measuredHeight != viewAnnotation.measuredHeight
      ) {
        viewAnnotation.measuredHeight = inflatedView.measuredHeight
        getValue(
          mapboxMap.updateViewAnnotation(
            viewAnnotation.id,
            ViewAnnotationOptions.Builder()
              .height(inflatedView.measuredHeight)
              .build()
          )
        )
      }
    }
    // triggered on every frame and needed to check if visibility changed
    // as OnGlobalLayoutListener does not cover cases for View.INVISIBLE properly
    val onDrawListener = ViewTreeObserver.OnDrawListener {
      if (viewAnnotation.handleVisibilityAutomatically) {
        val isAndroidViewVisible = inflatedView.visibility == View.VISIBLE
        if (
          (isAndroidViewVisible && viewAnnotation.isVisible) ||
          (!isAndroidViewVisible && viewAnnotation.visibility == ViewAnnotationVisibility.INVISIBLE) ||
          (!isAndroidViewVisible && viewAnnotation.visibility == ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED)
        ) {
          return@OnDrawListener
        }

        // hide view until it will be positioned in [positionAnnotationViews]
        if (isAndroidViewVisible) {
          unpositionedViews.add(inflatedView)
          inflatedView.visibility = View.INVISIBLE
        }

        updateVisibilityAndNotifyUpdateListeners(
          viewAnnotation,
          if (isAndroidViewVisible)
            ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED
          else
            ViewAnnotationVisibility.INVISIBLE
        )
        if (getValue(mapboxMap.getViewAnnotationOptions(viewAnnotation.id))?.visible != isAndroidViewVisible) {
          getValue(
            mapboxMap.updateViewAnnotation(
              viewAnnotation.id,
              ViewAnnotationOptions.Builder()
                .visible(isAndroidViewVisible)
                .build()
            )
          )
        }
      }
    }
    viewAnnotation.attachStateListener = object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View) {
        inflatedView.viewTreeObserver.addOnDrawListener(onDrawListener)
        inflatedView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
      }

      override fun onViewDetachedFromWindow(v: View) {
        inflatedView.viewTreeObserver.removeOnDrawListener(onDrawListener)
        inflatedView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
      }
    }
    inflatedView.addOnAttachStateChangeListener(viewAnnotation.attachStateListener)
    annotationMap[viewAnnotation.id] = viewAnnotation
    idLookupMap[inflatedView] = viewAnnotation.id
    getValue(mapboxMap.addViewAnnotation(viewAnnotation.id, updatedOptions))
    return inflatedView
  }

  private fun findByFeatureId(featureId: String): Pair<View?, ViewAnnotationOptions?> {
    annotationMap.forEach { (id, annotation) ->
      getValue(mapboxMap.getViewAnnotationOptions(id))?.let { options ->
        if (options.associatedFeatureId == featureId) {
          return annotation.view to options
        }
      }
    }
    return null to null
  }

  private var positionDescriptorCurrentList = emptyList<DelegatingViewAnnotationPositionDescriptor>()
  private fun positionAnnotationViews(
    positionDescriptorUpdatedList: List<DelegatingViewAnnotationPositionDescriptor>
  ) {
    val needToReorderZ = needToReorderZ(
      positionDescriptorCurrentList,
      positionDescriptorUpdatedList,
    )
    positionDescriptorCurrentList = positionDescriptorUpdatedList

    // as per current implementation when view annotation was added with WRAP_CONTENT dimension AND
    // allowOverlap = false - core will notify only about this particular view and thus we will remove
    // all the others and then restore later resulting in a quick blink
    // FIXME - other bugs are possible, this does not cover all cases
    val wrapContentWithAllowOverlapFalseViewAdded = positionDescriptorUpdatedList.size == 1 &&
      (positionDescriptorUpdatedList[0].width == WRAP_CONTENT || positionDescriptorUpdatedList[0].height == WRAP_CONTENT)
    // firstly delete views that do not belong to the viewport if it's not specific use-case from above
    if (!wrapContentWithAllowOverlapFalseViewAdded) {
      currentlyDrawnViewIdSet.forEach { id ->
        if (positionDescriptorUpdatedList.indexOfFirst { it.identifier == id } == -1) {
          annotationMap[id]?.let { annotation ->
            // if view is invisible / gone we don't remove it so that visibility logic could
            // still be handled by OnGlobalLayoutListener
            if (annotation.view.visibility == View.VISIBLE) {
              viewAnnotationsLayout.removeView(annotation.view)
              updateVisibilityAndNotifyUpdateListeners(
                annotation,
                ViewAnnotationVisibility.INVISIBLE
              )
            }
          }
        }
      }
    }
    // add and reposition new and existed views
    positionDescriptorUpdatedList.forEach { descriptor ->
      annotationMap[descriptor.identifier]?.let { annotation ->
        // update translation first - notify Android render node to schedule updates
        annotation.view.apply {
          translationX = descriptor.leftTopCoordinate.x.toFloat()
          translationY = descriptor.leftTopCoordinate.y.toFloat()
        }
        // update layout params explicitly if user has specified concrete width or height
        annotation.viewLayoutParams.apply {
          if (annotation.measuredWidth == USER_FIXED_DIMENSION) {
            width = descriptor.width
          }
          if (annotation.measuredHeight == USER_FIXED_DIMENSION) {
            height = descriptor.height
          }
        }
        if (!currentlyDrawnViewIdSet.contains(descriptor.identifier) &&
          viewAnnotationsLayout.indexOfChild(annotation.view) == -1
        ) {
          viewAnnotationsLayout.addView(annotation.view, annotation.viewLayoutParams)
          updateVisibilityAndNotifyUpdateListeners(
            annotation,
            if (annotation.view.visibility == View.VISIBLE)
              ViewAnnotationVisibility.VISIBLE_AND_POSITIONED
            else
              ViewAnnotationVisibility.INVISIBLE
          )
        }
        if (viewUpdatedListenerSet.isNotEmpty()) {
          viewUpdatedListenerSet.forEach {
            // when using wrap_content dimensions width and height could report -2
            // it makes sense to notify user only when width and height are calculated
            if (descriptor.width > 0 && descriptor.height > 0) {
              it.onViewAnnotationPositionUpdated(
                view = annotation.view,
                leftTopCoordinate = descriptor.leftTopCoordinate,
                width = descriptor.width,
                height = descriptor.height,
              )
            }
          }
        }

        if (unpositionedViews.remove(annotation.view)) {
          annotation.view.visibility = View.VISIBLE
          updateVisibilityAndNotifyUpdateListeners(
            annotation,
            ViewAnnotationVisibility.VISIBLE_AND_POSITIONED
          )
        }

        // reorder Z index with the iteration order to keep selected annotations on top of others
        if (needToReorderZ) {
          annotation.view.bringToFront()
        }
      }
    }
    // all the views should stay as is for that use-case, otherwise we update current view map
    if (!wrapContentWithAllowOverlapFalseViewAdded) {
      currentlyDrawnViewIdSet.clear()
      positionDescriptorUpdatedList.forEach {
        currentlyDrawnViewIdSet.add(it.identifier)
      }
    }
  }

  private inline fun <reified V> getValue(expected: Expected<String, V>): V? {
    if (expected.isError) {
      throw MapboxViewAnnotationException(expected.error)
    }
    return expected.value
  }

  private fun updateVisibilityAndNotifyUpdateListeners(
    annotation: ViewAnnotation,
    currentVisibility: ViewAnnotationVisibility
  ) {
    // we do nothing if currentVisibility value did not change and additionally if view is just added but not shown
    if (annotation.visibility == currentVisibility ||
      (annotation.visibility == ViewAnnotationVisibility.INITIAL && currentVisibility == ViewAnnotationVisibility.INVISIBLE)
    ) {
      return
    }
    val wasVisibleBefore = annotation.isVisible
    val isVisibleNow =
      currentVisibility == ViewAnnotationVisibility.VISIBLE_AND_POSITIONED ||
        currentVisibility == ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED
    annotation.visibility = currentVisibility
    if (viewUpdatedListenerSet.isNotEmpty() && isVisibleNow != wasVisibleBefore) {
      viewUpdatedListenerSet.forEach {
        it.onViewAnnotationVisibilityUpdated(
          view = annotation.view,
          visible = isVisibleNow
        )
      }
    }
  }

  private fun remove(internalId: String, annotation: ViewAnnotation) {
    viewAnnotationsLayout.removeView(annotation.view)
    updateVisibilityAndNotifyUpdateListeners(annotation, ViewAnnotationVisibility.INVISIBLE)
    // explicit onViewDetachedFromWindow call is needed to handle use-case
    // if the view has an animation attached
    annotation.attachStateListener?.onViewDetachedFromWindow(annotation.view)
    annotation.view.removeOnAttachStateChangeListener(annotation.attachStateListener)
    annotation.attachStateListener = null
    getValue(mapboxMap.removeViewAnnotation(internalId))
  }

  companion object {
    internal const val EXCEPTION_TEXT_GEOMETRY_IS_NULL = "Geometry can not be null!"
    internal const val EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS =
      "View annotation with associatedFeatureId=%s already exists!"
    private const val TAG = "ViewAnnotationImpl"
    private const val MAX_ADJUST_BOUNDS_COUNTER = 2

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun needToReorderZ(
      positionDescriptorCurrentList: List<DelegatingViewAnnotationPositionDescriptor>,
      positionDescriptorUpdatedList: List<DelegatingViewAnnotationPositionDescriptor>
    ): Boolean {
      when {
        // new annotations will be added to layout and trigger on measure anyway
        positionDescriptorCurrentList.size < positionDescriptorUpdatedList.size -> {
          return true
        }
        positionDescriptorCurrentList.isEmpty() || positionDescriptorUpdatedList.isEmpty() -> {
          return false
        }
        else -> {
          var currentIndex = 0
          var updatedIndex = 0
          // descriptor of items removed from the new list
          val removedDescriptors = mutableSetOf<String>()
          while (currentIndex < positionDescriptorCurrentList.size &&
            updatedIndex < positionDescriptorUpdatedList.size
          ) {
            // skip equal items
            if (positionDescriptorCurrentList[currentIndex].identifier == positionDescriptorUpdatedList[updatedIndex].identifier) {
              currentIndex++
              updatedIndex++
              continue
            }

            // if removed descriptors list contains the item from the new list - it means
            // the element was not removed but moved, need to invalidate Z order
            if (removedDescriptors.contains(positionDescriptorUpdatedList[updatedIndex].identifier)) {
              return true
            }

            // find the elements removed from the old list, add them to set
            while (currentIndex < positionDescriptorCurrentList.size &&
              positionDescriptorCurrentList[currentIndex].identifier != positionDescriptorUpdatedList[updatedIndex].identifier
            ) {
              removedDescriptors.add(positionDescriptorCurrentList[currentIndex].identifier)
              currentIndex++
            }
          }
          // iterate to the end of updated list
          while (updatedIndex < positionDescriptorUpdatedList.size) {
            if (removedDescriptors.contains(positionDescriptorUpdatedList[updatedIndex].identifier)) {
              return true
            }
            updatedIndex++
          }
        }
      }
      return false
    }
  }
}