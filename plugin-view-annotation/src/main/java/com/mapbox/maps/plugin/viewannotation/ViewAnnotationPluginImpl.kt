package com.mapbox.maps.plugin.viewannotation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationPositionDescriptor
import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapViewAnnotationDelegate

@MapboxExperimental
class ViewAnnotationPluginImpl: ViewAnnotationPlugin {

  private lateinit var mapView: FrameLayout
  // using async inflater if needed to free up main thread a bit
  private val asyncInflater by lazy { AsyncLayoutInflater(mapView.context) }

  private val annotations = HashMap<String, ViewAnnotation>()

  private lateinit var delegateProvider: MapDelegateProvider
  private lateinit var delegateMapViewAnnotations: MapViewAnnotationDelegate

  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
    delegateProvider.mapListenerDelegate.addOnCameraChangeListener(this)
    delegateMapViewAnnotations = delegateProvider.mapViewAnnotationDelegate
  }

  override fun bind(context: Context, attrs: AttributeSet?, pixelRatio: Float) {
    // no need as we need overloaded method
  }

  override fun bind(view: FrameLayout) {
    mapView = view
    mapView.requestDisallowInterceptTouchEvent(false)
  }

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
    delegateMapViewAnnotations.apply {
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
        val options = delegateMapViewAnnotations.getViewAnnotationOptions(viewPosition.identifier)
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
    delegateMapViewAnnotations.apply {
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
      delegateMapViewAnnotations.apply {
        updateViewAnnotation(id, options)
        calculateViewAnnotationsPosition { positions ->
          redrawAnnotations(positions)
        }
      }
    }
  }

  override fun getViewAnnotationByFeatureId(featureId: String): ViewAnnotation? {
    annotations.forEach {
      val options = delegateMapViewAnnotations.getViewAnnotationOptions(it.key)
      if (options.isValue && options.value!!.associatedFeatureId == featureId) {
        return annotations[it.key]
      }
    }
    return null
  }

  override fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions? {
    annotations.forEach {
      val options = delegateMapViewAnnotations.getViewAnnotationOptions(it.key)
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
    val options = delegateMapViewAnnotations.getViewAnnotationOptions(id)
    if (options.isValue) {
      return options.value
    }
    return null
  }

  override fun cleanup() {
    super.cleanup()
    delegateProvider.mapListenerDelegate.removeOnCameraChangeListener(this)
    annotations.forEach {
      mapView.removeView(it.value.view)
    }
    annotations.clear()
  }

  override fun onCameraChanged(eventData: CameraChangedEventData) {
    delegateMapViewAnnotations.calculateViewAnnotationsPosition {
      redrawAnnotations(it)
    }
  }
}