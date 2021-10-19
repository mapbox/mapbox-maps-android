package com.mapbox.maps.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.maps.*
import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData
import java.util.concurrent.ConcurrentHashMap

internal class ViewAnnotationManagerImpl(
  private val mapView: MapView,
  private val mapboxMap: MapboxMap = mapView.getMapboxMap()
) : ViewAnnotationManager {

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
    mapboxMap.addOnCameraChangeListener(this)
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

  private val annotations = ConcurrentHashMap<String, ViewAnnotation>()

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflateCallback: ((ViewAnnotation) -> Unit)
  ) {
    if (options.geometry == null) {
      throw RuntimeException("Geometry can not be null!")
    }
    asyncInflater.inflate(resId, mapView) { view, _, _ ->
      asyncInflateCallback.invoke(prepareViewAnnotation(view, options))
    }
  }

  override fun addViewAnnotation(resId: Int, options: ViewAnnotationOptions): ViewAnnotation {
    val view = LayoutInflater.from(mapView.context).inflate(resId, mapView, false)
    return prepareViewAnnotation(view, options)
  }

  override fun addViewAnnotation(view: View, options: ViewAnnotationOptions): ViewAnnotation {
    if (options.geometry == null) {
      throw RuntimeException("Geometry can not be null!")
    }
    return prepareViewAnnotation(view, options)
  }

  private fun prepareViewAnnotation(inflatedView: View, options: ViewAnnotationOptions): ViewAnnotation {
    val inflatedViewLayout = inflatedView.layoutParams as FrameLayout.LayoutParams
    val updatedOptions = options.toBuilder()
      .width(if (options.width == null) inflatedViewLayout.width else options.width)
      .height(if (options.height == null) inflatedViewLayout.height else options.height)
      .build()
    val id = inflatedView.hashCode().toString()
    val viewAnnotation = ViewAnnotation(
      id = id,
      view = inflatedView,
      viewLayoutParams = inflatedViewLayout
    )
    annotations[id] = viewAnnotation
    mapboxMap.apply {
      addViewAnnotation(id, updatedOptions)
      calculateViewAnnotationsPosition {
        redrawAnnotations(it)
      }
    }
    return viewAnnotation
  }

  private fun redrawAnnotations(
    positionsToUpdate: List<ViewAnnotationPositionDescriptor>
  ) {
    annotations.forEach {
      mapView.removeView(it.value.view)
    }
    for (viewPosition in positionsToUpdate) {
      annotations[viewPosition.identifier]?.let { annotation ->
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

  override fun removeViewAnnotation(id: String) {
    getViewAnnotationById(id)?.let {
      mapView.removeView(it.view)
    }
    annotations.remove(id)
    mapboxMap.apply {
      removeViewAnnotation(id)
      calculateViewAnnotationsPosition {
        redrawAnnotations(it)
      }
    }
  }

  override fun updateViewAnnotation(
    id: String,
    options: ViewAnnotationOptions,
  ) {
    annotations[id]?.let {
      mapboxMap.apply {
        updateViewAnnotation(id, options)
        calculateViewAnnotationsPosition { positions ->
          redrawAnnotations(positions)
        }
      }
    }
  }

  override fun getViewAnnotationByFeatureId(featureId: String): ViewAnnotation? {
    annotations.forEach {
      val options = mapboxMap.getViewAnnotationOptions(it.key)
      if (options.isValue && options.value!!.associatedFeatureId == featureId) {
        return annotations[it.key]
      }
    }
    return null
  }

  override fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions? {
    annotations.forEach {
      val options = mapboxMap.getViewAnnotationOptions(it.key)
      if (options.isValue && options.value!!.associatedFeatureId == featureId) {
        return options.value
      }
    }
    return null
  }

  override fun getViewAnnotationById(id: String): ViewAnnotation? {
    return annotations[id]
  }

  override fun getViewAnnotationOptionsById(id: String): ViewAnnotationOptions? {
    val options = mapboxMap.getViewAnnotationOptions(id)
    if (options.isValue) {
      return options.value
    }
    return null
  }

  fun destroy() {
    mapboxMap.removeOnCameraChangeListener(this)
    annotations.forEach {
      mapboxMap.removeViewAnnotation(it.key)
      mapView.removeView(it.value.view)
    }
    annotations.clear()
  }

  override fun onCameraChanged(eventData: CameraChangedEventData) {
    mapboxMap.calculateViewAnnotationsPosition {
      redrawAnnotations(it)
    }
  }
}