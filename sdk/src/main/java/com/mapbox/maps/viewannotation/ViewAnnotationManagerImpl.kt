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

  // structs needed to for drawing, declare them only once
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
    checkNotNull(options.geometry) { "Geometry can not be null!" }
    asyncInflater.inflate(resId, mapView) { view, _, _ ->
      asyncInflateCallback.invoke(prepareViewAnnotation(view, options))
    }
  }

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): View {
    checkNotNull(options.geometry) { "Geometry can not be null!" }
    val view = LayoutInflater.from(mapView.context).inflate(resId, mapView, false)
    return prepareViewAnnotation(view, options)
  }

  override fun addViewAnnotation(view: View, options: ViewAnnotationOptions) {
    checkNotNull(options.geometry) { "Geometry can not be null!" }
    prepareViewAnnotation(view, options)
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

  private fun addViews(idsSet: HashSet<String>, needsRepositionOnly: Boolean) {
    idsSet.forEach { id ->
      annotationMap[id]?.let { annotation ->
        val options = mapboxMap.getViewAnnotationOptions(id)
        if (options.isValue) {
          annotation.viewLayoutParams.width = options.value?.width!!
          annotation.viewLayoutParams.height = options.value?.height!!
          annotation.viewLayoutParams.setMargins(
            positionDescriptorMap[id]!!.leftTopCoordinate.x.toInt(),
            positionDescriptorMap[id]!!.leftTopCoordinate.y.toInt(),
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
          annotation.view.translationZ = positionDescriptorMap[id]!!.zIndex
        }
      }
    }
  }

  private fun redrawAnnotations(
    positionDescriptorList: List<ViewAnnotationPositionDescriptor>
  ) {
    positionDescriptorMap.clear()
    idsToRepositionSet.clear()
    idsToAddSet.clear()
    idsToDeleteSet.clear()

    positionDescriptorList.forEachIndexed { i, descriptor ->
      positionDescriptorMap[descriptor.identifier] =
        ViewPosition(
          descriptor.leftTopCoordinate,
          ViewPlugin.VIEW_PLUGIN_Z_TRANSLATION - 1f + i.toFloat() / positionDescriptorList.size
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

  private fun findByFeatureId(featureId: String): Pair<View?, ViewAnnotationOptions?> {
    annotationMap.forEach {
      val options = mapboxMap.getViewAnnotationOptions(it.key)
      if (options.isValue && options.value!!.associatedFeatureId == featureId) {
        return Pair(it.value.view, options.value)
      }
    }
    return Pair(null, null)
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

  fun destroy() {
    mapboxMap.setViewAnnotationPositionsUpdateListener(null)
    annotationMap.forEach {
      mapboxMap.removeViewAnnotation(it.key)
      it.value.view.viewTreeObserver.removeOnGlobalLayoutListener(it.value.globalLayoutListener)
      mapView.removeView(it.value.view)
    }
    currentViewsDrawnMap.clear()
    positionDescriptorMap.clear()
    idsToRepositionSet.clear()
    idsToAddSet.clear()
    idsToDeleteSet.clear()
    annotationMap.clear()
    idLookupMap.clear()
  }

  override fun onViewAnnotationPositionsUpdate(positions: MutableList<ViewAnnotationPositionDescriptor>) {
    redrawAnnotations(positions)
  }
}