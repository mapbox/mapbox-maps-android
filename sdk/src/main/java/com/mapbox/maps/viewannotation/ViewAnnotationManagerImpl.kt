package com.mapbox.maps.viewannotation

import android.graphics.Rect
import android.os.Looper
import android.view.*
import android.view.View.MeasureSpec
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.annotation.*
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.view.isVisible
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.getProjection
import com.mapbox.maps.renderer.RenderThread
import java.util.UUID
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.abs

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ViewAnnotationManagerImpl(
  mapView: MapView,
  private val viewAnnotationsLayout: FrameLayout = FrameLayout(mapView.context),
) : ViewAnnotationManager, DelegatingViewAnnotationPositionsUpdateListener() {
  private val mapboxMap: MapboxMap = mapView.mapboxMap
  private val renderThread = mapView.mapController.renderer.renderThread
  private val pixelRatio = mapView.resources.displayMetrics.density

  private data class ViewAnnotation(
    val view: View,
    var viewLayoutParams: ViewGroup.LayoutParams,
    // needed to control global layout / on draw listeners lifecycle
    var attachStateListener: View.OnAttachStateChangeListener? = null,
    // if user did not specify [ViewAnnotationOptions.visible]  explicitly
    var handleVisibilityAutomatically: Boolean,
    // TODO could be simplified with isPositioned flag
    var visibility: ViewAnnotationVisibility,
    var measuredWidth: Int,
    var measuredHeight: Int,
    var positionDescriptor: DelegatingViewAnnotationPositionDescriptor?,
    var isPositioned: Boolean,
    // id passed to gl-native
    val id: String = UUID.randomUUID().toString()
  ) {
    // Helper function to understand if view is visible from Android visibility perspective.
    val isVisible
      get() = visibility == ViewAnnotationVisibility.VISIBLE_AND_POSITIONED ||
        visibility == ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED
  }

  private val viewAnnotations = mutableMapOf<String, ViewAnnotation>()

  // using copy on write as user could remove listener while callback is invoked
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val viewUpdatedListenerSet = CopyOnWriteArraySet<OnViewAnnotationUpdatedListener>()

  @Volatile
  private var updatedPositionDescriptors = emptyList<DelegatingViewAnnotationPositionDescriptor>()
  private var currentPositionDescriptors = emptyList<DelegatingViewAnnotationPositionDescriptor>()

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

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflater: AsyncLayoutInflater,
    asyncInflateCallback: (View) -> Unit
  ) {
    validateOptions(options)
    asyncInflater.inflate(resId, viewAnnotationsLayout) { view, _, _ ->
      prepareViewAnnotation(view, options)
      asyncInflateCallback.invoke(view)
    }
  }

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): View {
    validateOptions(options)
    val view = LayoutInflater.from(viewAnnotationsLayout.context)
      .inflate(resId, viewAnnotationsLayout, false)
    prepareViewAnnotation(view, options)
    return view
  }

  override fun addViewAnnotation(view: View, options: ViewAnnotationOptions) {
    if (viewAnnotations.any { it.value.view == view }) {
      throw MapboxViewAnnotationException(
        "Trying to add view annotation that was already added before! " +
          "Please consider deleting annotation view ($view) beforehand."
      )
    }
    validateOptions(options)
    prepareViewAnnotation(view, options)
  }

  override fun removeViewAnnotation(view: View): Boolean {
    return viewAnnotations.entries
      .find { (_, viewAnnotation) -> viewAnnotation.view == view }
      ?.let { (id, viewAnnotation) ->
        viewAnnotations.remove(id)
        remove(viewAnnotation)
        true
      } ?: false
  }

  override fun removeAllViewAnnotations() {
    viewAnnotations.iterator().let { iterator ->
      while (iterator.hasNext()) {
        val viewAnnotation = iterator.next().value
        remove(viewAnnotation)
        iterator.remove()
      }
    }
  }

  override fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ): Boolean {
    return viewAnnotations
      .values
      .find { it.view == view }
      ?.let { viewAnnotation ->
        // FIXME does it conform the documented behaviour if `visible` was set but is not present in the update call ?
        // `When updating existing annotations, if `visible` is not explicitly set, the current value will be retained.`
        viewAnnotation.handleVisibilityAutomatically = (options.visible == null)
        if (options.width != null) {
          viewAnnotation.measuredWidth = USER_FIXED_DIMENSION
        }
        if (options.height != null) {
          viewAnnotation.measuredHeight = USER_FIXED_DIMENSION
        }
        getValue(mapboxMap.updateViewAnnotation(viewAnnotation.id, options))
        true
      } ?: false
  }

  override fun getViewAnnotation(annotatedLayerFeature: AnnotatedLayerFeature): View? {
    return findByAnnotatedLayerFeature(annotatedLayerFeature).first
  }

  override fun getViewAnnotationOptions(annotatedLayerFeature: AnnotatedLayerFeature): ViewAnnotationOptions? {
    return findByAnnotatedLayerFeature(annotatedLayerFeature).second
  }

  override fun getViewAnnotationOptions(view: View): ViewAnnotationOptions? {
    return viewAnnotations
      .values
      .find { it.view == view }
      ?.let { viewAnnotation ->
        getValue(mapboxMap.getViewAnnotationOptions(viewAnnotation.id))
      }
  }

  override fun addOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener) {
    viewUpdatedListenerSet.add(listener)
  }

  override fun removeOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener) {
    viewUpdatedListenerSet.remove(listener)
  }

  @RenderThread
  override fun setViewAnnotationUpdateMode(mode: ViewAnnotationUpdateMode) {
    renderThread.viewAnnotationMode = mode
  }

  @RenderThread
  override fun getViewAnnotationUpdateMode(): ViewAnnotationUpdateMode {
    return renderThread.viewAnnotationMode
  }

  override val annotations: Map<View, ViewAnnotationOptions>
    get() = viewAnnotations
      .mapNotNull { (_, viewAnnotation) ->
        val options = getValue(mapboxMap.getViewAnnotationOptions(viewAnnotation.id))

        options?.let {
          viewAnnotation.view to options
        }
      }
      .toMap()

  /**
   * Calculates CameraOptions to fit the given view annotations, including their width and height.
   *
   * @param annotations List of [View] to be shown.
   * @param edgeInsets [EdgeInsets] to be applied to the camera.
   * @param bearing [Double] bearing to be applied to the camera.
   * @param pitch [Double] pitch to be applied to the camera.
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
    val viewAnnotations = annotations
      .filter { it.isVisible }
      .mapNotNull { view ->
        viewAnnotations.values.find { it.view == view }
      }

    if (viewAnnotations.isEmpty()) return null
    val pointCoordinates =
      viewAnnotations.mapNotNull { it.coordinate(it.positionDescriptor) }
    var cameraForViewAnnotationPoints = mapboxMap.cameraForCoordinates(
      pointCoordinates,
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      bearing,
      pitch
    )

    var isCorrectBound = false
    // viewAnnotation, frame and max coordinate
    var north: Triple<ViewAnnotation, Rect?, Double>? = null
    var east: Triple<ViewAnnotation, Rect?, Double>? = null
    var west: Triple<ViewAnnotation, Rect?, Double>? = null
    var south: Triple<ViewAnnotation, Rect?, Double>? = null

    // we run the loop twice to optimize bounds correctly to fit all the annotations. this might not
    // provide correct results when map is pitched or have bearing.
    var boundsCounter = 1

    while (!isCorrectBound && boundsCounter <= MAX_ADJUST_BOUNDS_COUNTER) {
      val zoom = cameraForViewAnnotationPoints.zoom
      boundsCounter++
      isCorrectBound = true

      viewAnnotations.forEach { viewAnnotation ->
        val frame = getViewAnnotationOptionsFrame(
          viewAnnotation,
          viewAnnotation.positionDescriptor
        ) ?: Rect(0, 0, 0, 0)
        val annotationBounds = calculateCoordinateBoundForAnnotation(viewAnnotation, frame, zoom)
        if (annotationBounds == null) {
          return@forEach
        }

        if (north == null || north!!.third < annotationBounds.north()) {
          north = Triple(viewAnnotation, frame, annotationBounds.north())
          isCorrectBound = false
        }
        if (east == null || east!!.third < annotationBounds.east()) {
          east = Triple(viewAnnotation, frame, annotationBounds.east())
          isCorrectBound = false
        }
        if (south == null || south!!.third > annotationBounds.south()) {
          south = Triple(viewAnnotation, frame, annotationBounds.south())
          isCorrectBound = false
        }
        if (west == null || west!!.third > annotationBounds.west()) {
          west = Triple(viewAnnotation, frame, annotationBounds.west())
          isCorrectBound = false
        }
      }

      // adding extra checks for nullability (this shouldn't execute normally as we are checking nullability in loop of viewannotations).
      if (north == null || east == null || south == null || west == null) {
        logW(TAG, "ViewAnnotation options framing is null. Returning null camera option")
        return null
      }

      val coordinateBoundsForCamera = CoordinateBounds(
        Point.fromLngLat(
          west!!.first.coordinate(west!!.first.positionDescriptor)!!.longitude(),
          south!!.first.coordinate(south!!.first.positionDescriptor)!!.latitude()
        ),
        Point.fromLngLat(
          east!!.first.coordinate(east!!.first.positionDescriptor)!!.longitude(),
          north!!.first.coordinate(north!!.first.positionDescriptor)!!.latitude()
        )
      )

      val paddings = EdgeInsets(
        (edgeInsets?.top ?: 0).toDouble() + abs((north!!.second?.top ?: 0.0).toDouble()),
        (edgeInsets?.left ?: 0).toDouble() + abs((west!!.second?.left ?: 0.0).toDouble()),
        (edgeInsets?.bottom ?: 0).toDouble() + abs((south!!.second?.bottom ?: 0.0).toDouble()),
        (edgeInsets?.right ?: 0).toDouble() + abs((east!!.second?.right ?: 0.0).toDouble())
      )

      cameraForViewAnnotationPoints = mapboxMap.cameraForCoordinateBounds(
        coordinateBoundsForCamera,
        paddings,
        bearing,
        pitch
      )
    }
    return cameraForViewAnnotationPoints
  }

  /**
   * Function to calculate coordinatebound associated with annotation option.
   * this uses [MapboxMap.projectedMetersForCoordinate] and [MapboxMap.coordinateForProjectedMeters]
   * function to get correct coordinate for northEast and southWest point of annotations.
   */
  private fun calculateCoordinateBoundForAnnotation(
    viewAnnotation: ViewAnnotation,
    annotationFrame: Rect,
    zoom: Double?
  ): CoordinateBounds? {
    val pointCoordinate =
      viewAnnotation.coordinate(viewAnnotation.positionDescriptor)
        ?: return null

    val metersPerPixelAtLatitude = if (zoom == null) {
      mapboxMap.getMetersPerPixelAtLatitude(pointCoordinate.latitude())
    } else {
      mapboxMap.getMetersPerPixelAtLatitude(pointCoordinate.latitude(), zoom)
    }

    val projectedMeterForCoordinate = mapboxMap.projectedMetersForCoordinate(pointCoordinate)
    val metersPerPixelDensity = metersPerPixelAtLatitude / pixelRatio
    val northing =
      projectedMeterForCoordinate.northing + (abs(annotationFrame.top).toDouble() * metersPerPixelDensity)
    val easting =
      projectedMeterForCoordinate.easting + (abs(annotationFrame.right).toDouble() * metersPerPixelDensity)
    val southing =
      projectedMeterForCoordinate.northing - (abs(annotationFrame.bottom).toDouble() * metersPerPixelDensity)
    val westing =
      projectedMeterForCoordinate.easting - (abs(annotationFrame.left).toDouble() * metersPerPixelDensity)

    val projectedMeterNorthEast = ProjectedMeters(northing, easting)
    val projectedMeterSouthWest = ProjectedMeters(southing, westing)

    val coordinateNorthEast = mapboxMap.coordinateForProjectedMeters(projectedMeterNorthEast)
    val coordinateSouthWest = mapboxMap.coordinateForProjectedMeters(projectedMeterSouthWest)
    return CoordinateBounds(coordinateSouthWest, coordinateNorthEast)
  }

  private fun ViewAnnotation.coordinate(positionDescriptor: DelegatingViewAnnotationPositionDescriptor?): Point? {
    val viewAnnotationOptions = getValue(mapboxMap.getViewAnnotationOptions(id)) ?: return null

    return if (viewAnnotationOptions.annotatedFeature!!.isGeometry) {
      viewAnnotationOptions.annotatedFeature!!.geometry as? Point
    } else {
      positionDescriptor?.anchorCoordinate
    }
  }

  /**
   * Get [Rect] from [ViewAnnotationOptions]'s geometry, width and height.
   * This function takes [ViewAnnotationOptions.geometry] as the center of rectangle and
   * use width, height and offset values to calculate [Rect] associated.
   *
   * @return [Rect] associated with [ViewAnnotationOptions]
   */
  private fun getViewAnnotationOptionsFrame(
    viewAnnotation: ViewAnnotation,
    positionDescriptor: DelegatingViewAnnotationPositionDescriptor?
  ): Rect? {
    val viewAnnotationOptions = getValue(mapboxMap.getViewAnnotationOptions(viewAnnotation.id))

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
        val anchor = positionDescriptor?.anchorConfig ?: ViewAnnotationAnchorConfig.Builder()
          .anchor(ViewAnnotationAnchor.CENTER).build()

        when (anchor.anchor) {
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
        rect.offset(anchor.offsetX.toInt(), anchor.offsetY.toInt())
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
      val immutableUpdatedPositionDescriptorsCopy = updatedPositionDescriptors
      // schedule positioning on next frame using Choreographer from main thread
      Choreographer.getInstance().postFrameCallback {
        positionAnnotationViews(immutableUpdatedPositionDescriptorsCopy)
      }
    } else {
      // Called as soon as possible on main thread after updated positions arrived on render thread.
      // We need another callback from core as scheduling from render thread to main happens too slow
      // when using Java Main Looper.
      // update that flag here if callback was triggered, it will be reset by renderer directly when swapping buffers
      renderThread.needViewAnnotationSync = true
      updatedPositionDescriptors = positions
    }
  }

  fun destroy() {
    mapboxMap.setViewAnnotationPositionsUpdateListener(null)
    viewUpdatedListenerSet.clear()
    removeAllViewAnnotations()
  }

  private fun validateOptions(options: ViewAnnotationOptions) {
    if (options.annotatedFeature == null) {
      throw IllegalArgumentException(EXCEPTION_TEXT_FEATURE_IS_NULL)
    }
  }

  private fun prepareViewAnnotation(inflatedView: View, options: ViewAnnotationOptions) {
    measureView(inflatedView)
    val inflatedViewLayoutParams = inflatedView.layoutParams as ViewGroup.LayoutParams
    // If values in layout params are negative - view is wrap_content (assuming match_parent view annotations
    // make no sense) and size is determined during measure() above.
    // Otherwise, size is determined by layout params.
    val measuredWidth = if (inflatedViewLayoutParams.width < 0) {
      inflatedView.measuredWidth
    } else {
      inflatedViewLayoutParams.width
    }
    val measuredHeight = if (inflatedViewLayoutParams.height < 0) {
      inflatedView.measuredHeight
    } else {
      inflatedViewLayoutParams.height
    }

    val updatedOptions = options.toBuilder()
      .width(options.width ?: measuredWidth.toDouble())
      .height(options.height ?: measuredHeight.toDouble())
      .build()
    val viewAnnotation = ViewAnnotation(
      view = inflatedView,
      handleVisibilityAutomatically = (options.visible == null),
      visibility = ViewAnnotationVisibility.INITIAL,
      viewLayoutParams = inflatedViewLayoutParams,
      measuredWidth = if (options.width != null) USER_FIXED_DIMENSION else measuredWidth,
      measuredHeight = if (options.height != null) USER_FIXED_DIMENSION else measuredHeight,
      positionDescriptor = null,
      isPositioned = false,
    )

    val onGlobalLayoutListener = buildGlobalLayoutListener(viewAnnotation)
    val onDrawListener = buildDrawListener(viewAnnotation)
    viewAnnotation.attachStateListener =
      buildAttachStateListener(viewAnnotation.view, onGlobalLayoutListener, onDrawListener)
    inflatedView.addOnAttachStateChangeListener(viewAnnotation.attachStateListener)

    viewAnnotations[viewAnnotation.id] = viewAnnotation
    getValue(mapboxMap.addViewAnnotation(viewAnnotation.id, updatedOptions))
  }

  private fun measureView(view: View) {
    val measureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
    val isComposeView = view.getTag(R.id.composeView) != null
    // compose expects view to be added to the layout, otherwise it crashes
    // so we attach it temporarily, then detach until it's positioned and added in [positionAnnotationViews]
    // also see https://stackoverflow.com/questions/72802387/compose-view-instead-of-native-view-for-markers-info-window
    // or https://github.com/googlemaps/android-maps-compose/blob/main/maps-compose/src/main/java/com/google/maps/android/compose/ComposeInfoWindowAdapter.kt
    if (isComposeView) {
      viewAnnotationsLayout.addView(view)
    }
    view.measure(
      measureSpec,
      measureSpec,
    )
    if (isComposeView) {
      viewAnnotationsLayout.removeView(view)
    }
    view.layout(
      /* l = */ 0,
      /* t = */ 0,
      /* r = */ view.measuredWidth,
      /* b = */ view.measuredHeight
    )
  }

  private fun buildAttachStateListener(
    view: View,
    onGlobalLayoutListener: OnGlobalLayoutListener,
    onDrawListener: ViewTreeObserver.OnDrawListener
  ) = object : View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(v: View) {
      view.viewTreeObserver.addOnDrawListener(onDrawListener)
      view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    override fun onViewDetachedFromWindow(v: View) {
      view.viewTreeObserver.removeOnDrawListener(onDrawListener)
      view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
  }

  // triggers on every frame and checks if visibility changed
  // as OnGlobalLayoutListener does not cover cases for View.INVISIBLE properly
  private fun buildDrawListener(viewAnnotation: ViewAnnotation) = ViewTreeObserver.OnDrawListener {
    if (viewAnnotation.handleVisibilityAutomatically) {
      val isAndroidViewVisible = viewAnnotation.view.isVisible

      if (
        (isAndroidViewVisible && viewAnnotation.isVisible) ||
        (!isAndroidViewVisible && viewAnnotation.visibility == ViewAnnotationVisibility.INVISIBLE) ||
        (!isAndroidViewVisible && viewAnnotation.visibility == ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED)
      ) {
        return@OnDrawListener
      }

      // hide view until it is positioned in [positionAnnotationViews]
      if (isAndroidViewVisible) {
        viewAnnotations[viewAnnotation.id]?.isPositioned = false
        viewAnnotation.view.visibility = View.INVISIBLE
      }

      updateVisibilityAndNotifyUpdateListeners(
        viewAnnotation,
        if (isAndroidViewVisible) {
          ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED
        } else {
          ViewAnnotationVisibility.INVISIBLE
        }
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

  // controls view annotations's width and height when dimensions of the view change
  private fun buildGlobalLayoutListener(viewAnnotation: ViewAnnotation) =
    OnGlobalLayoutListener {
      if (
        viewAnnotation.measuredWidth != USER_FIXED_DIMENSION &&
        viewAnnotation.view.measuredWidth > 0 &&
        viewAnnotation.view.measuredWidth != viewAnnotation.measuredWidth
      ) {
        viewAnnotation.measuredWidth = viewAnnotation.view.measuredWidth
        getValue(
          mapboxMap.updateViewAnnotation(
            viewAnnotation.id,
            ViewAnnotationOptions.Builder()
              .width(viewAnnotation.view.measuredWidth.toDouble())
              .build()
          )
        )
      }
      if (
        viewAnnotation.measuredHeight != USER_FIXED_DIMENSION &&
        viewAnnotation.view.measuredHeight > 0 &&
        viewAnnotation.view.measuredHeight != viewAnnotation.measuredHeight
      ) {
        viewAnnotation.measuredHeight = viewAnnotation.view.measuredHeight
        getValue(
          mapboxMap.updateViewAnnotation(
            viewAnnotation.id,
            ViewAnnotationOptions.Builder()
              .height(viewAnnotation.view.measuredHeight.toDouble())
              .build()
          )
        )
      }
    }

  private fun findByAnnotatedLayerFeature(annotatedLayerFeature: AnnotatedLayerFeature): Pair<View?, ViewAnnotationOptions?> {
    return viewAnnotations
      .map { (id, viewAnnotation) ->
        viewAnnotation.view to getValue(mapboxMap.getViewAnnotationOptions(id))
      }
      .firstOrNull { (_, viewAnnotationOptions) ->
        viewAnnotationOptions?.annotatedFeature?.isAnnotatedLayerFeature == true &&
          viewAnnotationOptions.annotatedFeature?.annotatedLayerFeature == annotatedLayerFeature
      } ?: Pair(null, null)
  }

  private fun positionAnnotationViews(
    updatedPositionDescriptors: List<DelegatingViewAnnotationPositionDescriptor>
  ) {
    val needToReorderZ = needToReorderZ(
      currentPositionDescriptors,
      updatedPositionDescriptors,
    )
    currentPositionDescriptors = updatedPositionDescriptors

    // add and reposition new and existed views
    updatedPositionDescriptors.forEach { descriptor ->
      viewAnnotations[descriptor.identifier]?.let { viewAnnotation ->
        // update translation first - notify Android render node to schedule updates
        viewAnnotation.view.apply {
          translationX = descriptor.leftTopCoordinate.x.toFloat()
          translationY = descriptor.leftTopCoordinate.y.toFloat()
        }

        // update layout params explicitly if user has specified concrete width or height
        viewAnnotation.viewLayoutParams.apply {
          if (viewAnnotation.measuredWidth == USER_FIXED_DIMENSION) {
            width = descriptor.width.toInt()
          }
          if (viewAnnotation.measuredHeight == USER_FIXED_DIMENSION) {
            height = descriptor.height.toInt()
          }
        }

        if (
          !viewAnnotation.isVisible &&
          viewAnnotationsLayout.indexOfChild(viewAnnotation.view) == -1
        ) {
          viewAnnotation.isPositioned = true
          viewAnnotationsLayout.addView(viewAnnotation.view, viewAnnotation.viewLayoutParams)
          updateVisibilityAndNotifyUpdateListeners(
            viewAnnotation,
            if (viewAnnotation.view.visibility == View.VISIBLE) {
              ViewAnnotationVisibility.VISIBLE_AND_POSITIONED
            } else {
              ViewAnnotationVisibility.INVISIBLE
            }
          )
        }

        if (!viewAnnotation.isPositioned) {
          viewAnnotation.isPositioned = true
          viewAnnotation.view.visibility = View.VISIBLE
          updateVisibilityAndNotifyUpdateListeners(
            viewAnnotation,
            ViewAnnotationVisibility.VISIBLE_AND_POSITIONED
          )
        }

        notifyAnchorListeners(viewAnnotation, descriptor)
        notifyAnchorCoordinateListeners(viewAnnotation, descriptor)
        notifyPositionListeners(viewAnnotation, descriptor)
        viewAnnotation.positionDescriptor = descriptor

        // reorder Z index with the iteration order to keep selected annotations on top of others
        if (needToReorderZ) {
          viewAnnotation.view.bringToFront()
        }
      }
    }

    // remove visible views that are not present in positions list
    // TODO should we make them invisible instead?
    viewAnnotations
      .filter {
        it.value.view.isVisible
      }
      .filter {
        currentPositionDescriptors.none { positionDescriptor ->
          positionDescriptor.identifier == it.key
        }
      }
      .forEach { (_, viewAnnotation) ->
        viewAnnotationsLayout.removeView(viewAnnotation.view)
        updateVisibilityAndNotifyUpdateListeners(
          viewAnnotation,
          ViewAnnotationVisibility.INVISIBLE
        )
      }
  }

  private fun notifyPositionListeners(
    viewAnnotation: ViewAnnotation,
    newPositionDescriptor: DelegatingViewAnnotationPositionDescriptor
  ) {
    if (
      viewAnnotation.positionDescriptor?.leftTopCoordinate != newPositionDescriptor.leftTopCoordinate ||
      viewAnnotation.positionDescriptor?.width != newPositionDescriptor.width ||
      viewAnnotation.positionDescriptor?.height != newPositionDescriptor.height
    ) {
      viewUpdatedListenerSet.forEach {
        it.onViewAnnotationPositionUpdated(
          view = viewAnnotation.view,
          leftTopCoordinate = newPositionDescriptor.leftTopCoordinate,
          width = newPositionDescriptor.width,
          height = newPositionDescriptor.height,
        )
      }
    }
  }

  private fun notifyAnchorCoordinateListeners(
    viewAnnotation: ViewAnnotation,
    newPositionDescriptor: DelegatingViewAnnotationPositionDescriptor
  ) {
    if (viewAnnotation.positionDescriptor?.anchorCoordinate != newPositionDescriptor.anchorCoordinate) {
      viewUpdatedListenerSet.forEach {
        it.onViewAnnotationAnchorCoordinateUpdated(
          viewAnnotation.view,
          newPositionDescriptor.anchorCoordinate,
        )
      }
    }
  }

  private fun notifyAnchorListeners(
    viewAnnotation: ViewAnnotation,
    newPositionDescriptor: DelegatingViewAnnotationPositionDescriptor
  ) {
    if (viewAnnotation.positionDescriptor?.anchorConfig != newPositionDescriptor.anchorConfig) {
      viewUpdatedListenerSet.forEach {
        it.onViewAnnotationAnchorUpdated(
          viewAnnotation.view,
          newPositionDescriptor.anchorConfig,
        )
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

  private fun remove(annotation: ViewAnnotation) {
    viewAnnotationsLayout.removeView(annotation.view)
    updateVisibilityAndNotifyUpdateListeners(annotation, ViewAnnotationVisibility.INVISIBLE)
    // explicit onViewDetachedFromWindow call is needed to handle use-case
    // if the view has an animation attached
    annotation.attachStateListener?.onViewDetachedFromWindow(annotation.view)
    annotation.view.removeOnAttachStateChangeListener(annotation.attachStateListener)
    annotation.attachStateListener = null
    getValue(mapboxMap.removeViewAnnotation(annotation.id))
  }

  companion object {
    private const val EXCEPTION_TEXT_FEATURE_IS_NULL = "Annotated feature can not be null!"
    private const val TAG = "ViewAnnotationImpl"
    private const val MAX_ADJUST_BOUNDS_COUNTER = 2

    private const val USER_FIXED_DIMENSION = -1

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun needToReorderZ(
      currentPositionDescriptors: List<DelegatingViewAnnotationPositionDescriptor>,
      updatedPositionDescriptors: List<DelegatingViewAnnotationPositionDescriptor>
    ): Boolean {
      when {
        // new annotations will be added to layout and trigger on measure anyway
        currentPositionDescriptors.size < updatedPositionDescriptors.size -> {
          return true
        }

        currentPositionDescriptors.isEmpty() || updatedPositionDescriptors.isEmpty() -> {
          return false
        }

        else -> {
          var currentIndex = 0
          var updatedIndex = 0
          // descriptor of items removed from the new list
          val removedDescriptors = mutableSetOf<String>()
          while (currentIndex < currentPositionDescriptors.size &&
            updatedIndex < updatedPositionDescriptors.size
          ) {
            // skip equal items
            if (currentPositionDescriptors[currentIndex].identifier == updatedPositionDescriptors[updatedIndex].identifier) {
              currentIndex++
              updatedIndex++
              continue
            }

            // if removed descriptors list contains the item from the new list - it means
            // the element was not removed but moved, need to invalidate Z order
            if (removedDescriptors.contains(updatedPositionDescriptors[updatedIndex].identifier)) {
              return true
            }

            // find the elements removed from the old list, add them to set
            while (currentIndex < currentPositionDescriptors.size &&
              currentPositionDescriptors[currentIndex].identifier != updatedPositionDescriptors[updatedIndex].identifier
            ) {
              removedDescriptors.add(currentPositionDescriptors[currentIndex].identifier)
              currentIndex++
            }
          }
          // iterate to the end of updated list
          while (updatedIndex < updatedPositionDescriptors.size) {
            if (removedDescriptors.contains(updatedPositionDescriptors[updatedIndex].identifier)) {
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