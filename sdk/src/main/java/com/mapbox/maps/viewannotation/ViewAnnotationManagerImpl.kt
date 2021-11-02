package com.mapbox.maps.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.maps.*
import com.mapbox.maps.plugin.ViewPlugin
import java.util.concurrent.ConcurrentHashMap

internal class ViewAnnotationManagerImpl(
  private val mapView: MapView,
  private val mapboxMap: MapboxMap = mapView.getMapboxMap()
) : ViewAnnotationManager, ViewAnnotationPositionsListener {

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
    mapboxMap.setViewAnnotationPositionsUpdateListener(this)
  }

  private val asyncInflater by lazy {
    try {
      AsyncLayoutInflater(mapView.context)
    } catch (e: NoClassDefFoundError) {
      throw RuntimeException(
        "Please add https://mvnrepository.com/artifact/androidx.asynclayoutinflater/asynclayoutinflater/1.0.0 dependency " +
          "to your project to make use of asynchronous view inflation when adding view annotation!"
      )
    }
  }

  private val annotationMap = ConcurrentHashMap<String, ViewAnnotation>()
  private val idLookupMap = HashMap<View, String>()

  // structs needed for drawing, declare them only once
  private val currentViewsDrawnMap = HashMap<String, ScreenCoordinate>()
  private val positionDescriptorMap = HashMap<String, ViewPosition>()
  private val idsToRepositionSet = HashSet<String>()
  private val idsToAddSet = HashSet<String>()
  private val idsToDeleteSet = HashSet<String>()

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflateCallback: (View) -> Unit
  ) {
    validateOptions(options)
    asyncInflater.inflate(resId, mapView) { view, _, _ ->
      asyncInflateCallback.invoke(prepareViewAnnotation(view, options))
    }
  }

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): View {
    validateOptions(options)
    val view = LayoutInflater.from(mapView.context).inflate(resId, mapView, false)
    return prepareViewAnnotation(view, options)
  }

  override fun addViewAnnotation(view: View, options: ViewAnnotationOptions) {
    idLookupMap[view]?.let {
      throw RuntimeException(
        "Trying to add view annotation that was already added before! " +
          "Please consider deleting annotation view ($view) beforehand."
      )
    }
    validateOptions(options)
    prepareViewAnnotation(view, options)
  }

  override fun removeViewAnnotation(view: View): Boolean {
    val id = idLookupMap[view] ?: return false
    val annotation = annotationMap.remove(id) ?: return false
    idLookupMap.remove(view)
    view.viewTreeObserver.removeOnGlobalLayoutListener(annotation.globalLayoutListener)
    mapView.removeView(view)
    mapboxMap.removeViewAnnotation(id)
    return true
  }

  override fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ): Boolean {
    val id = idLookupMap[view] ?: return false
    annotationMap[id]?.let {
      it.handleVisibility = options.visible == null
      mapboxMap.updateViewAnnotation(id, options)
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
    val options = mapboxMap.getViewAnnotationOptions(id)
    if (options.isValue) {
      return options.value
    }
    return null
  }

  override fun onViewAnnotationPositionsUpdate(positions: MutableList<ViewAnnotationPositionDescriptor>) {
    drawAnnotationViews(positions)
  }

  fun destroy() {
    mapboxMap.setViewAnnotationPositionsUpdateListener(null)
    annotationMap.forEach { (id, annotation) ->
      mapboxMap.removeViewAnnotation(id)
      annotation.view.viewTreeObserver.removeOnGlobalLayoutListener(annotation.globalLayoutListener)
      mapView.removeView(annotation.view)
    }
    currentViewsDrawnMap.clear()
    positionDescriptorMap.clear()
    idsToRepositionSet.clear()
    idsToAddSet.clear()
    idsToDeleteSet.clear()
    annotationMap.clear()
    idLookupMap.clear()
  }

  private fun validateOptions(options: ViewAnnotationOptions) {
    checkNotNull(options.geometry) { "Geometry can not be null!" }
  }

  private fun prepareViewAnnotation(inflatedView: View, options: ViewAnnotationOptions): View {
    val inflatedViewLayout = inflatedView.layoutParams as FrameLayout.LayoutParams
    val updatedOptions = options.toBuilder()
      .width(if (options.width == null) inflatedViewLayout.width else options.width)
      .height(if (options.height == null) inflatedViewLayout.height else options.height)
      .build()
    val viewAnnotation = ViewAnnotation(
      view = inflatedView,
      handleVisibility = options.visible == null,
      viewLayoutParams = inflatedViewLayout,
    )
    val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
      if (viewAnnotation.handleVisibility) {
        val isVisibleNow = inflatedView.visibility == View.VISIBLE
        if (mapboxMap.getViewAnnotationOptions(viewAnnotation.id).value?.visible != isVisibleNow) {
          mapboxMap.updateViewAnnotation(
            viewAnnotation.id,
            ViewAnnotationOptions.Builder()
              .visible(isVisibleNow)
              .build()
          )
        }
      }
    }
    viewAnnotation.globalLayoutListener = globalLayoutListener
    inflatedView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    annotationMap[viewAnnotation.id] = viewAnnotation
    idLookupMap[inflatedView] = viewAnnotation.id
    mapboxMap.addViewAnnotation(viewAnnotation.id, updatedOptions)
    return inflatedView
  }

  private fun findByFeatureId(featureId: String): Pair<View?, ViewAnnotationOptions?> {
    annotationMap.forEach { (id, annotation) ->
      mapboxMap.getViewAnnotationOptions(id).value?.let { options ->
        if (options.associatedFeatureId == featureId) {
          return annotation.view to options
        }
      }
    }
    return null to null
  }

  private fun drawAnnotationViews(
    positionDescriptorList: List<ViewAnnotationPositionDescriptor>
  ) {
    positionDescriptorMap.clear()
    idsToRepositionSet.clear()
    idsToAddSet.clear()
    idsToDeleteSet.clear()

    positionDescriptorList.forEachIndexed { i, descriptor ->
      positionDescriptorMap[descriptor.identifier] =
        ViewPosition(
          leftTopCoordinate = descriptor.leftTopCoordinate,
          width = descriptor.width,
          height = descriptor.height,
          zIndex = ViewPlugin.VIEW_PLUGIN_Z_TRANSLATION - 1f + i.toFloat() / positionDescriptorList.size
        )
    }
    idsToRepositionSet.addAll(positionDescriptorMap.keys.intersect(currentViewsDrawnMap.keys))
    idsToAddSet.addAll(positionDescriptorMap.keys.minus(currentViewsDrawnMap.keys))
    idsToDeleteSet.addAll(currentViewsDrawnMap.keys.minus(positionDescriptorMap.keys))

    // firstly delete views that do not belong to the viewport
    idsToDeleteSet.forEach {
      annotationMap[it]?.let { annotation ->
        // if view is invisible / gone we don't remove it so that visibility logic could
        // still be handled by OnGlobalLayoutListener
        if (annotation.view.visibility == View.VISIBLE) {
          mapView.removeView(annotation.view)
        }
      }
    }
    // reposition existing views modifying layout parameters
    addViews(idsToRepositionSet, needsRepositionOnly = true)
    // add views on screen that were not present before
    addViews(idsToAddSet, needsRepositionOnly = false)
    currentViewsDrawnMap.clear()
    positionDescriptorList.forEach {
      currentViewsDrawnMap[it.identifier] = it.leftTopCoordinate
    }
  }

  private fun addViews(idsSet: HashSet<String>, needsRepositionOnly: Boolean) {
    idsSet.forEach { id ->
      annotationMap[id]?.let { annotation ->
        positionDescriptorMap[id]?.let { viewPosition ->
          annotation.viewLayoutParams.width = viewPosition.width
          annotation.viewLayoutParams.height = viewPosition.height
          annotation.viewLayoutParams.setMargins(
            viewPosition.leftTopCoordinate.x.toInt(),
            viewPosition.leftTopCoordinate.y.toInt(),
            0,
            0
          )
          if (needsRepositionOnly) {
            annotation.view.requestLayout()
          } else {
            // remove view to as it may have been not removed before due to visibility
            mapView.removeView(annotation.view)
            // removing shadowing effect brought in by setting z-index / elevation
            annotation.view.outlineProvider = null
            mapView.addView(annotation.view, annotation.viewLayoutParams)
          }
          annotation.view.translationZ = viewPosition.zIndex
        }
      }
    }
  }
}