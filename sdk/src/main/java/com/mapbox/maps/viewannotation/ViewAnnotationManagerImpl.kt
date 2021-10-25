package com.mapbox.maps.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.maps.*
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

  override fun addViewAnnotation(resId: Int, options: ViewAnnotationOptions): View {
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
      viewLayoutParams = inflatedViewLayout
    )
    inflatedView.viewTreeObserver.addOnGlobalLayoutListener {
      if (!viewAnnotation.handleVisibility) {
        return@addOnGlobalLayoutListener
      }
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
    annotationMap[viewAnnotation.id] = viewAnnotation
    idLookupMap[inflatedView] = viewAnnotation.id
    mapboxMap.addViewAnnotation(viewAnnotation.id, updatedOptions)
    return inflatedView
  }

  private fun redrawAnnotations(
    positionsToUpdate: List<ViewAnnotationPositionDescriptor>
  ) {
    annotationMap
      // filter out and remove explicitly only visible views
      // we can't remove invisible / gone ones because global layout listener will stop getting notified
      .filter { it.value.view.visibility == View.VISIBLE }
      .forEach { mapView.removeView(it.value.view) }
    for (viewPosition in positionsToUpdate) {
      annotationMap[viewPosition.identifier]?.let { annotation ->
        // remove invisible or gone view if needed
        mapView.removeView(annotation.view)
        val options = mapboxMap.getViewAnnotationOptions(viewPosition.identifier)
        if (options.isValue) {
          annotation.viewLayoutParams.width = options.value?.width!!
          annotation.viewLayoutParams.height = options.value?.height!!
          annotation.viewLayoutParams.setMargins(
            viewPosition.leftTopCoordinate.x.toInt(),
            viewPosition.leftTopCoordinate.y.toInt(),
            0,
            0
          )
          mapView.addView(annotation.view, annotation.viewLayoutParams)
        }
      }
    }
  }

  override fun removeViewAnnotation(view: View): Boolean {
    val id = idLookupMap[view] ?: return false
    annotationMap.remove(id) ?: return false
    idLookupMap.remove(view)
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
    annotationMap.forEach {
      val options = mapboxMap.getViewAnnotationOptions(it.key)
      if (options.isValue && options.value!!.associatedFeatureId == featureId) {
        return annotationMap[it.key]?.view
      }
    }
    return null
  }

  override fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions? {
    annotationMap.forEach {
      val options = mapboxMap.getViewAnnotationOptions(it.key)
      if (options.isValue && options.value!!.associatedFeatureId == featureId) {
        return options.value
      }
    }
    return null
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
    annotationMap.forEach {
      mapboxMap.removeViewAnnotation(it.key)
      mapView.removeView(it.value.view)
    }
    annotationMap.clear()
    idLookupMap.clear()
  }

  override fun onViewAnnotationPositionsUpdate(positions: MutableList<ViewAnnotationPositionDescriptor>) {
    redrawAnnotations(positions)
  }
}