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
import com.mapbox.maps.ViewAnnotationsPosition
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
    @LayoutRes id: Int,
    options: ViewAnnotationOptions
  ): View {
    if (options.geometry == null) {
      throw RuntimeException("Geometry can not be null!")
    }
    val nativeView = LayoutInflater.from(mapView.context).inflate(id, mapView, false)
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
    val viewId = nativeView.hashCode().toString()
    annotations[viewId] = annotation
    delegateMapViewAnnotations.addViewAnnotation(viewId, updatedOptions)?.let {
      redrawAnnotations(it, !options.allowViewAnnotationsCollision)
    }
    return nativeView
  }

  // asynchronous method
  override fun addViewAnnotation(
    @LayoutRes id: Int,
    options: ViewAnnotationOptions,
    result: (View) -> Unit
  ) {
    if (options.geometry == null) {
      throw RuntimeException("Geometry can not be null!")
    }
    asyncInflater.inflate(id, mapView) { view, _, _ ->
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
      val viewId = view.hashCode().toString()
      annotations[viewId] = annotation
      delegateMapViewAnnotations.addViewAnnotation(viewId, updatedOptions)?.let {
        redrawAnnotations(it, !options.allowViewAnnotationsCollision)
      }
      result.invoke(view)
    }
  }

  private fun redrawAnnotations(
    positionsToUpdate: ViewAnnotationsPosition,
    fullRedraw: Boolean
  ) {
    if (fullRedraw) {
      annotations.forEach {
        mapView.removeView(it.value.view)
      }
    }
    for (viewPosition in positionsToUpdate.positions) {
      annotations[viewPosition.identifier]?.let { annotation ->
        mapView.removeView(annotation.view)
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

  override fun removeViewAnnotation(view: View) {
    val id = view.hashCode().toString()
    annotations.remove(id)
    mapView.removeView(view)
    delegateMapViewAnnotations.removeViewAnnotation(id)?.let {
      // TODO may be optimized to do full redraw only when top level visible annotation was not allowing collisions
      redrawAnnotations(it, true)
    }
  }

  override fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ) {
    val id = view.hashCode().toString()
    annotations[id]?.let {
      val updatedOptions = ViewAnnotationOptions.Builder()
        .geometry(if (options.geometry != null) options.geometry else it.options.geometry)
        .allowViewAnnotationsCollision(
          if (!options.allowViewAnnotationsCollision)
            options.allowViewAnnotationsCollision
          else
            it.options.allowViewAnnotationsCollision
        )
        .anchor(if (options.anchor != null) options.anchor else it.options.anchor)
        .width(if (options.width != 0) options.width else it.options.width)
        .height(if (options.height != 0) options.height else it.options.height)
        .offsetX(if (options.offsetX != 0) options.offsetX else it.options.offsetX)
        .offsetY(if (options.offsetY != 0) options.offsetY else it.options.offsetY)
        .build()
      delegateMapViewAnnotations.updateViewAnnotation(id, updatedOptions)?.let { position ->
        redrawAnnotations(position, !updatedOptions.allowViewAnnotationsCollision)
      }
    }
  }

  override fun cleanup() {
    super.cleanup()
    delegateProvider.mapListenerDelegate.removeOnCameraChangeListener(this)
    annotations.clear()
  }

  override fun onCameraChanged() {
    val positionsToUpdate = delegateMapViewAnnotations.calculateViewAnnotationsPosition()
    redrawAnnotations(positionsToUpdate, true)
  }
}