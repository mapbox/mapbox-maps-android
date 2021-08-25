package com.mapbox.maps.plugin.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationsPosition
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapViewAnnotationDelegate

class ViewAnnotationPluginImpl(
  private val mapView: FrameLayout
): ViewAnnotationPlugin {

  // using async inflater if needed to free up main thread a bit
  private val asyncInflater by lazy { AsyncLayoutInflater(mapView.context) }

  private val annotations = HashMap<Int, ViewAnnotation>()

  private lateinit var delegateProvider: MapDelegateProvider
  private lateinit var delegateMapViewAnnotations: MapViewAnnotationDelegate

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
  }

  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
    delegateProvider.mapListenerDelegate.addOnCameraChangeListener(this)
    delegateMapViewAnnotations = delegateProvider.mapViewAnnotationDelegate
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
      .anchor(if (options.anchor == null) ViewAnnotationAnchor.TOP_LEFT else options.anchor)
      .width(if (options.width == null) nativeViewLayout.width else options.width)
      .height(if (options.height == null) nativeViewLayout.height else options.height)
      .build()
    val annotation = ViewAnnotation(
      view = nativeView,
      viewLayoutParams = nativeViewLayout,
      options = updatedOptions
    )
    val viewId = nativeView.hashCode()
    annotations[viewId] = annotation
    delegateMapViewAnnotations.addViewAnnotation(viewId, updatedOptions)?.let {
      displayAnnotations(it, false)
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
        .anchor(if (options.anchor == null) ViewAnnotationAnchor.TOP_LEFT else options.anchor)
        .width(if (options.width == null) nativeViewLayout.width else options.width)
        .height(if (options.height == null) nativeViewLayout.height else options.height)
        .build()
      val annotation = ViewAnnotation(
        view = view,
        viewLayoutParams = nativeViewLayout,
        options = updatedOptions
      )
      val viewId = view.hashCode()
      annotations[viewId] = annotation
      delegateMapViewAnnotations.addViewAnnotation(viewId, updatedOptions)?.let {
        displayAnnotations(it, false)
      }
      result.invoke(view)
    }
  }

  private fun displayAnnotations(
    positionsToUpdate: ViewAnnotationsPosition,
    fullRedraw: Boolean
  ) {
    if (fullRedraw) {
      annotations.forEach {
        mapView.removeView(it.value.view)
      }
    }
    for (viewPosition in positionsToUpdate.positions) {
      annotations[viewPosition.id]?.let { annotation ->
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
    val id = view.hashCode()
    annotations.remove(id)
    mapView.removeView(view)
    delegateMapViewAnnotations.removeViewAnnotation(id)?.let {
      displayAnnotations(it, false)
    }
  }

  override fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ) {
    val id = view.hashCode()
    annotations[id]?.let {
      val updatedOptions = ViewAnnotationOptions.Builder()
        .geometry(if (options.geometry != null) options.geometry else it.options.geometry)
        .allowViewAnnotationsCollision(
          if (!options.allowViewAnnotationsCollision)
            options.allowViewAnnotationsCollision
          else it.options.allowViewAnnotationsCollision
        )
        .anchor(if (options.anchor != null) options.anchor else it.options.anchor)
        .width(if (options.width != null) options.width else it.options.width)
        .height(if (options.height != null) options.height else it.options.height)
        .marginLeft(if (options.marginLeft != 0) options.marginLeft else it.options.marginLeft)
        .marginTop(if (options.marginTop != 0) options.marginTop else it.options.marginTop)
        .marginRight(if (options.marginRight != 0) options.marginRight else it.options.marginRight)
        .marginBottom(if (options.marginBottom != 0) options.marginBottom else it.options.marginBottom)
        .build()
      delegateMapViewAnnotations.updateViewAnnotation(id, updatedOptions)?.let { position ->
        displayAnnotations(position, false)
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
    displayAnnotations(positionsToUpdate, true)
  }
}