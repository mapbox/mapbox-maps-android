package com.mapbox.maps.plugin.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
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
  private val lastVisibleViewIds = mutableListOf<Int>()

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
    type: ViewAnnotationType,
    options: ViewAnnotationOptions
  ): View {
    val nativeView = LayoutInflater.from(mapView.context).inflate(id, mapView, false)
    val nativeViewLayout = nativeView.layoutParams as FrameLayout.LayoutParams
    val updatedOptions = options.toBuilder()
      .width(if (options.width == null) nativeViewLayout.width else options.width)
      .height(if (options.height == null) nativeViewLayout.height else options.height)
      .build()
    val annotation = ViewAnnotation(
      view = nativeView,
      viewLayoutParams = nativeViewLayout,
      descriptor = when(type) {
        ViewAnnotationType.NATIVE -> ViewAnnotationDescriptor.Native(delegateProvider.mapCameraManagerDelegate.cameraState)
        ViewAnnotationType.CALLOUT -> ViewAnnotationDescriptor.Callout(delegateProvider.mapCameraManagerDelegate.cameraState)
      },
      options = updatedOptions
    )
    nativeView.tag = ViewPosition(annotation.descriptor.initialCamera.bearing)
    val viewId = nativeView.hashCode()
    annotations[viewId] = annotation
    val positionsToUpdate = delegateMapViewAnnotations.addViewAnnotation(viewId, updatedOptions)
    displayAnnotation(positionsToUpdate, false)
    return nativeView
  }

  // asynchronous method
  override fun addViewAnnotation(
    @LayoutRes id: Int,
    type: ViewAnnotationType,
    options: ViewAnnotationOptions,
    result: (View) -> Unit
  ) {
    asyncInflater.inflate(id, mapView) { view, _, _ ->
      val nativeViewLayout = view.layoutParams as FrameLayout.LayoutParams
      val updatedOptions = options.toBuilder()
        .width(if (options.width == null) nativeViewLayout.width else options.width)
        .height(if (options.height == null) nativeViewLayout.height else options.height)
        .build()
      val annotation = ViewAnnotation(
        view = view,
        viewLayoutParams = nativeViewLayout,
        descriptor = when(type) {
          ViewAnnotationType.NATIVE -> ViewAnnotationDescriptor.Native(delegateProvider.mapCameraManagerDelegate.cameraState)
          ViewAnnotationType.CALLOUT -> ViewAnnotationDescriptor.Callout(delegateProvider.mapCameraManagerDelegate.cameraState)
        },
        options = updatedOptions
      )
      view.tag = ViewPosition(annotation.descriptor.initialCamera.bearing)
      val viewId = view.hashCode()
      annotations[viewId] = annotation
      val positionsToUpdate = delegateMapViewAnnotations.addViewAnnotation(viewId, updatedOptions)
      displayAnnotation(positionsToUpdate, true)
      result.invoke(view)
    }
  }

  private fun displayAnnotation(
    positionsToUpdate: ViewAnnotationsPosition,
    // TODO updateAll seems useless here, revisit
    updateAll: Boolean
  ) {
    for (viewPosition in positionsToUpdate.positions) {
      annotations[viewPosition.id]?.let { annotation ->
        if (updateAll || lastVisibleViewIds.contains(viewPosition.id)) {
          mapView.removeView(annotation.view)
          // TODO revisit
          val density = mapView.resources.displayMetrics.density
          annotation.viewLayoutParams.setMargins(
            (viewPosition.leftTopCoordinate.x * density).toInt(),
            (viewPosition.leftTopCoordinate.y * density).toInt(),
            0,
            0
          )
          mapView.addView(annotation.view, annotation.viewLayoutParams)
        }
      }
    }
  }

  override fun removeViewAnnotation(view: View) {
    val id = view.hashCode()
    annotations.remove(id)
    val positionsToUpdate = delegateMapViewAnnotations.removeViewAnnotation(id)
    displayAnnotation(positionsToUpdate, true)
  }

  override fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ) {
    val id = view.hashCode()
    val positionsToUpdate = delegateMapViewAnnotations.updateViewAnnotation(id, options)
    displayAnnotation(positionsToUpdate, true)
  }

  override fun cleanup() {
    super.cleanup()
    delegateProvider.mapListenerDelegate.removeOnCameraChangeListener(this)
    annotations.clear()
  }

  override fun onCameraChanged() {
    val positionsToUpdate = delegateMapViewAnnotations.calculateViewAnnotationsPosition()
    displayAnnotation(positionsToUpdate, true)
    lastVisibleViewIds.clear()
    // TODO use nicer Kotlin here
    positionsToUpdate.positions.forEach {
      lastVisibleViewIds.add(it.id)
    }
  }
}