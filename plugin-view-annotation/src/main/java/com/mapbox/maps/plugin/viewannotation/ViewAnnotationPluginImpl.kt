package com.mapbox.maps.plugin.viewannotation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationPositionDescriptor
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

  // synchronous method
  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): String {
    if (options.geometry == null) {
      throw RuntimeException("Geometry can not be null!")
    }
    val nativeView = LayoutInflater.from(mapView.context).inflate(resId, mapView, false)
    val nativeViewLayout = nativeView.layoutParams as FrameLayout.LayoutParams
    val updatedOptions = options.toBuilder()
      .anchor(if (options.anchor == null) ViewAnnotationAnchor.CENTER else options.anchor)
      .width(if (options.width == 0) nativeViewLayout.width else options.width)
      .height(if (options.height == 0) nativeViewLayout.height else options.height)
      .build()
    val annotation = ViewAnnotation(
      view = nativeView,
      viewLayoutParams = nativeViewLayout,
      options = updatedOptions
    )
    val id = annotation.hashCode().toString()
    annotations[id] = annotation
    delegateMapViewAnnotations.apply {
      addViewAnnotation(id, updatedOptions)
      calculateViewAnnotationsPosition {
        redrawAnnotations(it, !options.allowViewAnnotationsCollision)
      }
    }
    return id
  }

  // asynchronous method
  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    result: (String) -> Unit
  ) {
    if (options.geometry == null) {
      throw RuntimeException("Geometry can not be null!")
    }
    asyncInflater.inflate(resId, mapView) { view, _, _ ->
      val nativeViewLayout = view.layoutParams as FrameLayout.LayoutParams
      val updatedOptions = options.toBuilder()
        .anchor(if (options.anchor == null) ViewAnnotationAnchor.CENTER else options.anchor)
        .width(if (options.width == 0) nativeViewLayout.width else options.width)
        .height(if (options.height == 0) nativeViewLayout.height else options.height)
        .build()
      val annotation = ViewAnnotation(
        view = view,
        viewLayoutParams = nativeViewLayout,
        options = updatedOptions
      )
      val id = annotation.hashCode().toString()
      annotations[id] = annotation
      delegateMapViewAnnotations.apply {
        addViewAnnotation(id, updatedOptions)
        calculateViewAnnotationsPosition {
          redrawAnnotations(it, !options.allowViewAnnotationsCollision)
        }
      }
      result.invoke(id)
    }
  }

  private fun redrawAnnotations(
    positionsToUpdate: List<ViewAnnotationPositionDescriptor>,
    fullRedraw: Boolean
  ) {
    if (fullRedraw) {
      annotations.forEach {
        mapView.removeView(it.value.view)
      }
    }
    for (viewPosition in positionsToUpdate) {
      annotations[viewPosition.identifier]?.let { annotation ->
        mapView.removeView(annotation.view)
        annotation.viewLayoutParams.width = annotation.options.width
        annotation.viewLayoutParams.height = annotation.options.height
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

  override fun removeViewAnnotation(id: String) {
    mapView.removeView(getViewAnnotationById(id))
    annotations.remove(id)
    delegateMapViewAnnotations.apply {
      removeViewAnnotation(id)
      calculateViewAnnotationsPosition {
        redrawAnnotations(it, true)
      }
    }
  }

  override fun updateViewAnnotation(
    id: String,
    options: ViewAnnotationOptions,
  ) {
    annotations[id]?.let {
      val updatedOptions = ViewAnnotationOptions.Builder()
        .geometry(if (options.geometry != null) options.geometry else it.options.geometry)
        .allowViewAnnotationsCollision(options.allowViewAnnotationsCollision)
        .anchor(if (options.anchor != null) options.anchor else it.options.anchor)
        .width(if (options.width != 0) options.width else it.options.width)
        .height(if (options.height != 0) options.height else it.options.height)
        .offsetX(if (options.offsetX != 0) options.offsetX else it.options.offsetX)
        .offsetY(if (options.offsetY != 0) options.offsetY else it.options.offsetY)
        .featureIdentifier(if (options.featureIdentifier != null) options.featureIdentifier else it.options.featureIdentifier)
        .selected(options.selected)
        .build()
      it.options = updatedOptions
      delegateMapViewAnnotations.apply {
        updateViewAnnotation(id, updatedOptions)
        calculateViewAnnotationsPosition { positions ->
          redrawAnnotations(positions, !updatedOptions.allowViewAnnotationsCollision)
        }
      }
    }
  }

  override fun getViewAnnotationByFeatureId(featureId: String): View? {
    annotations.forEach {
      if (it.value.options.featureIdentifier == featureId) {
        return it.value.view
      }
    }
    return null
  }

  override fun getViewAnnotationById(id: String) = annotations[id]?.view

  override fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions? {
    annotations.forEach {
      if (it.value.options.featureIdentifier == featureId) {
        return it.value.options
      }
    }
    return null
  }

  override fun getViewAnnotationOptionsById(id: String) = annotations[id]?.options

  override fun cleanup() {
    super.cleanup()
    delegateProvider.mapListenerDelegate.removeOnCameraChangeListener(this)
    annotations.forEach {
      mapView.removeView(it.value.view)
    }
    annotations.clear()
  }

  override fun onCameraChanged() {
    delegateMapViewAnnotations.calculateViewAnnotationsPosition {
      redrawAnnotations(it, true)
    }
  }
}