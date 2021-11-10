package com.mapbox.maps.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.maps.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap

internal class ViewAnnotationManagerImpl(
  private val mapView: MapView
) : ViewAnnotationManager, ViewAnnotationPositionsListener {

  private val mapboxMap: MapboxMap = mapView.getMapboxMap()
  private val viewPlugins = mapView.mapController.pluginRegistry.viewPlugins

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
    mapboxMap.setViewAnnotationPositionsUpdateListener(this)
  }

  private val annotationMap = ConcurrentHashMap<String, ViewAnnotation>()
  private val idLookupMap = ConcurrentHashMap<View, String>()

  // struct needed for drawing, declare it only once
  private val currentViewsDrawnMap = HashMap<String, ScreenCoordinate>()

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflater: AsyncLayoutInflater,
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
    if (idLookupMap.contains(view)) {
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
      it.handleVisibilityAutomatically = options.visible == null
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
    annotationMap.clear()
    idLookupMap.clear()
  }

  private fun validateOptions(options: ViewAnnotationOptions) {
    checkNotNull(options.geometry) { "Geometry can not be null!" }
  }

  private fun prepareViewAnnotation(inflatedView: View, options: ViewAnnotationOptions): View {
    val inflatedViewLayout = inflatedView.layoutParams as FrameLayout.LayoutParams
    val updatedOptions = options.toBuilder()
      .width(options.width ?: inflatedViewLayout.width)
      .height(options.height ?: inflatedViewLayout.height)
      .build()
    val viewAnnotation = ViewAnnotation(
      view = inflatedView,
      handleVisibilityAutomatically = options.visible == null,
      viewLayoutParams = inflatedViewLayout,
    )
    val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
      if (viewAnnotation.handleVisibilityAutomatically) {
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
    positionDescriptorCoreList: List<ViewAnnotationPositionDescriptor>
  ) {
    // firstly delete views that do not belong to the viewport
    currentViewsDrawnMap.keys.forEach { id ->
      if (positionDescriptorCoreList.indexOfFirst { it.identifier == id } == -1) {
        annotationMap[id]?.let { annotation ->
          // if view is invisible / gone we don't remove it so that visibility logic could
          // still be handled by OnGlobalLayoutListener
          if (annotation.view.visibility == View.VISIBLE) {
            mapView.removeView(annotation.view)
          }
        }
      }
    }
    // add and reposition new and existed views
    positionDescriptorCoreList.forEach { descriptor ->
      annotationMap[descriptor.identifier]?.let { annotation ->
        annotation.viewLayoutParams.width = descriptor.width
        annotation.viewLayoutParams.height = descriptor.height
        annotation.viewLayoutParams.setMargins(
          descriptor.leftTopCoordinate.x.toInt(),
          descriptor.leftTopCoordinate.y.toInt(),
          0,
          0
        )
        if (!currentViewsDrawnMap.keys.contains(descriptor.identifier) && mapView.indexOfChild(annotation.view) == -1) {
          mapView.addView(annotation.view, annotation.viewLayoutParams)
        }
        // as we preserve correct order we bring each view to the front and correct order will be preserved
        annotation.view.bringToFront()
      }
    }
    // bring to front map view plugins so that they are drawn on top of view annotations
    viewPlugins.forEach {
      it.value.bringToFront()
    }
    currentViewsDrawnMap.clear()
    positionDescriptorCoreList.forEach {
      currentViewsDrawnMap[it.identifier] = it.leftTopCoordinate
    }
  }
}