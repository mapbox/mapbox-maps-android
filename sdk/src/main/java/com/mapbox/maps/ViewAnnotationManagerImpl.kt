package com.mapbox.maps

import android.os.Looper
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.annotation.*
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.mapbox.bindgen.Expected
import com.mapbox.maps.viewannotation.*
import com.mapbox.maps.viewannotation.ViewAnnotation
import com.mapbox.maps.viewannotation.ViewAnnotation.Companion.USER_FIXED_DIMENSION
import com.mapbox.maps.viewannotation.ViewAnnotationVisibility
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

internal class ViewAnnotationManagerImpl(
  mapView: MapView
) : ViewAnnotationManager, ViewAnnotationPositionsUpdateListener {

  private val annotationsRootView: FrameLayout = FrameLayout(mapView.context).also {
    it.layoutParams = FrameLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT,
    )
  }
  private val mapboxMap: MapboxMap = mapView.getMapboxMap()
  private val renderThread = mapView.mapController.renderer.renderThread

  init {
    // place the view annotations above the map (index 0) but below the compass, ruler and other plugin views
    mapView.addView(annotationsRootView, 1)
    mapView.requestDisallowInterceptTouchEvent(false)
    mapboxMap.setViewAnnotationPositionsUpdateListener(this)
  }

  private val annotationMap = ConcurrentHashMap<String, ViewAnnotation>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val idLookupMap = ConcurrentHashMap<View, String>()
  private val currentlyDrawnViewIdSet = mutableSetOf<String>()

  // using copy on write as user could remove listener while callback is invoked
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val viewUpdatedListenerSet = CopyOnWriteArraySet<OnViewAnnotationUpdatedListener>()

  @Volatile
  private var updatedPositionsList: MutableList<ViewAnnotationPositionDescriptor> = mutableListOf()

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflater: AsyncLayoutInflater,
    asyncInflateCallback: (View) -> Unit
  ) {
    validateOptions(options)
    asyncInflater.inflate(resId, annotationsRootView) { view, _, _ ->
      asyncInflateCallback.invoke(prepareViewAnnotation(view, options))
    }
  }

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): View {
    validateOptions(options)
    val view = LayoutInflater.from(annotationsRootView.context).inflate(resId, annotationsRootView, false)
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

  /**
   * We will have two calls of this callback:
   * - first from render thread with actual position list
   * - second from main thread with empty list in any case.
   *
   * We need separate call from main thread as scheduling on main thread happens much faster in C++
   * then rescheduling using Java handler.
   */
  @AnyThread
  override fun onViewAnnotationPositionsUpdate(positions: MutableList<ViewAnnotationPositionDescriptor>) {
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
        val isAndroidViewVisible = (inflatedView.visibility == View.VISIBLE)
        if ((isAndroidViewVisible && viewAnnotation.isVisible) ||
          (!isAndroidViewVisible && viewAnnotation.visibility == ViewAnnotationVisibility.INVISIBLE)
        ) {
          return@OnDrawListener
        }
        // hide view below map surface and pull it back when new position from core will arrive
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

  private class PositionDiffCallback(
    val oldList: List<ViewAnnotationPositionDescriptor>,
    val newList: List<ViewAnnotationPositionDescriptor>,
  ) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].identifier == newList[newItemPosition].identifier
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].identifier == newList[newItemPosition].identifier
  }

  private var positionDiffCallback = PositionDiffCallback(emptyList(), emptyList())

  private fun positionAnnotationViews(
    positionDescriptorCoreList: List<ViewAnnotationPositionDescriptor>
  ) {
    val positionDiffCallback = PositionDiffCallback(
      oldList = positionDiffCallback.newList,
      newList = positionDescriptorCoreList
    )

    var needToReorderZ = false
    DiffUtil.calculateDiff(positionDiffCallback).dispatchUpdatesTo(
      object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
          // reorder only inserted in the middle of the list
          if (position != positionDescriptorCoreList.lastIndex) {
            needToReorderZ = true
          }
        }

        override fun onRemoved(position: Int, count: Int) {
          // item removal does not affect order of annotations
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
          needToReorderZ = true
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
          // don't care about the contents
        }
      }
    )

    // as per current implementation when view annotation was added with WRAP_CONTENT dimension AND
    // allowOverlap = false - core will notify only about this particular view and thus we will remove
    // all the others and then restore later resulting in a quick blink

    // FIXME - other bugs are possible, this does not cover all cases, e.g. 3 annotations and the last one blinks :
    // Positions : 3
    //   pos : [identifier: 44, width: 282, height: 196, leftTopCoordinate: [x: 148.00000000000566, y: 616.9999999999659]]
    //   pos : [identifier: 45, width: 282, height: 196, leftTopCoordinate: [x: 483.0000000000149, y: 566.9999999999759]]
    //   pos : [identifier: 48, width: -2, height: -2, leftTopCoordinate: [x: -2.147483218E9, y: -4.2949667920000005E9]]
    // Positions : 3
    //   pos : [identifier: 42, width: 282, height: 196, leftTopCoordinate: [x: 99.99999999998583, y: 368.9999999999887]]
    //   pos : [identifier: 44, width: 282, height: 196, leftTopCoordinate: [x: 148.00000000000566, y: 616.9999999999659]]
    //   pos : [identifier: 45, width: 282, height: 196, leftTopCoordinate: [x: 483.0000000000149, y: 566.9999999999759]]
    // Positions : 1
    //   pos : [identifier: 49, width: -2, height: -2, leftTopCoordinate: [x: -2.147483362E9, y: -4.2949661230000005E9]]
    // Positions : 3
    //   pos : [identifier: 42, width: 282, height: 196, leftTopCoordinate: [x: 99.99999999998583, y: 368.9999999999887]]
    //   pos : [identifier: 44, width: 282, height: 196, leftTopCoordinate: [x: 148.00000000000566, y: 616.9999999999659]]
    //   pos : [identifier: 45, width: 282, height: 196, leftTopCoordinate: [x: 483.0000000000149, y: 566.9999999999759]]
    val wrapContentWithAllowOverlapFalseViewAdded = positionDescriptorCoreList.size == 1 &&
      (positionDescriptorCoreList[0].width == WRAP_CONTENT || positionDescriptorCoreList[0].height == WRAP_CONTENT)
    // firstly delete views that do not belong to the viewport if it's not specific use-case from above
    if (!wrapContentWithAllowOverlapFalseViewAdded) {
      currentlyDrawnViewIdSet.forEach { id ->
        if (positionDescriptorCoreList.indexOfFirst { it.identifier == id } == -1) {
          annotationMap[id]?.let { annotation ->
            // if view is invisible / gone we don't remove it so that visibility logic could
            // still be handled by OnGlobalLayoutListener
            if (annotation.view.visibility == View.VISIBLE) {
              annotationsRootView.removeView(annotation.view)
              updateVisibilityAndNotifyUpdateListeners(annotation, ViewAnnotationVisibility.INVISIBLE)
            }
          }
        }
      }
    }
    // add and reposition new and existed views
    positionDescriptorCoreList.forEach { descriptor ->
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
        if (!currentlyDrawnViewIdSet.contains(descriptor.identifier)
          && annotationsRootView.indexOfChild(annotation.view) == -1) {
          annotationsRootView.addView(annotation.view, annotation.viewLayoutParams)
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
        // reorder Z index with the iteration order to keep selected annotations on top of others
        if (needToReorderZ) {
          annotation.view.bringToFront()
        }
      }
    }
    // all the views should stay as is for that use-case, otherwise we update current view map
    if (!wrapContentWithAllowOverlapFalseViewAdded) {
      currentlyDrawnViewIdSet.clear()
      positionDescriptorCoreList.forEach {
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
    val isVisibleNow = (
      currentVisibility == ViewAnnotationVisibility.VISIBLE_AND_POSITIONED ||
        currentVisibility == ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED
      )
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
    annotationsRootView.removeView(annotation.view)
    updateVisibilityAndNotifyUpdateListeners(annotation, ViewAnnotationVisibility.INVISIBLE)
    annotation.view.removeOnAttachStateChangeListener(annotation.attachStateListener)
    annotation.attachStateListener = null
    getValue(mapboxMap.removeViewAnnotation(internalId))
  }

  companion object {
    internal const val EXCEPTION_TEXT_GEOMETRY_IS_NULL = "Geometry can not be null!"
    internal const val EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS =
      "View annotation with associatedFeatureId=%s already exists!"
  }
}