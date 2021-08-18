package com.mapbox.maps.plugin.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.delegates.MapDelegateProvider

class ViewAnnotationPluginImpl(
  private val mapView: FrameLayout
): ViewAnnotationPlugin {

  // using async inflater if needed to free up main thread a bit
  private val asyncInflater by lazy { AsyncLayoutInflater(mapView.context) }
  // we need id to pass it to gl-native and not pass view itself as it's useless
  private val annotations = LinkedHashMap<Int, ViewAnnotation>()
  private lateinit var delegateProvider: MapDelegateProvider

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
  }

  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
    delegateProvider.mapListenerDelegate.addOnCameraChangeListener(this)
  }

  // synchronous method
  override fun addViewAnnotation(
    @LayoutRes id: Int,
    type: ViewAnnotationType,
    geometry: Geometry,
    options: ViewAnnotationOptions
  ): View {
    val nativeView = LayoutInflater.from(mapView.context).inflate(id, mapView, false)
    val nativeViewLayout = nativeView.layoutParams as FrameLayout.LayoutParams
    val annotation = ViewAnnotation(
      view = nativeView,
      viewLayoutParams = nativeViewLayout,
      descriptor = when(type) {
        ViewAnnotationType.NATIVE -> ViewAnnotationDescriptor.Native(delegateProvider.mapCameraManagerDelegate.cameraState)
        ViewAnnotationType.CALLOUT -> ViewAnnotationDescriptor.Callout(delegateProvider.mapCameraManagerDelegate.cameraState)
      },
      initialWidth = nativeView.layoutParams.width,
      initialHeight = nativeView.layoutParams.height,
      geometry = geometry,
      options = options
    )
    nativeView.tag = ViewPosition(annotation.descriptor.initialCamera.bearing)
    annotations[nativeView.hashCode()] = annotation
    displayAnnotation(annotation)
    return nativeView
  }

  // asynchronous method
  override fun addViewAnnotation(
    @LayoutRes id: Int,
    type: ViewAnnotationType,
    geometry: Geometry,
    options: ViewAnnotationOptions,
    result: (View) -> Unit
  ) {
    asyncInflater.inflate(id, mapView) { view, _, _ ->
      val nativeViewLayout = view.layoutParams as FrameLayout.LayoutParams
      val annotation = ViewAnnotation(
        view = view,
        viewLayoutParams = nativeViewLayout,
        descriptor = when(type) {
          ViewAnnotationType.NATIVE -> ViewAnnotationDescriptor.Native(delegateProvider.mapCameraManagerDelegate.cameraState)
          ViewAnnotationType.CALLOUT -> ViewAnnotationDescriptor.Callout(delegateProvider.mapCameraManagerDelegate.cameraState)
        },
        initialWidth = view.layoutParams.width,
        initialHeight = view.layoutParams.height,
        geometry = geometry,
        options = options
      )
      view.tag = ViewPosition(annotation.descriptor.initialCamera.bearing)
      annotations[view.hashCode()] = annotation
      displayAnnotation(annotation)
      result.invoke(view)
    }
  }

  // that's a temp solution to initially display view annotation on map (if map camera is not changing)
  // will be removed with new gl-native API
  private fun displayAnnotation(annotation: ViewAnnotation) {
    val point = annotation.geometry as Point
    val leftTop = delegateProvider.mapCameraManagerDelegate.pixelForCoordinate(point)
    // correctly position the view
    annotation.viewLayoutParams.setMargins(leftTop.x.toInt(), leftTop.y.toInt(), 0, 0)
    when (annotation.descriptor) {
      is ViewAnnotationDescriptor.Callout -> {
        // TBD but nothing comes to mind right now
      }
      is ViewAnnotationDescriptor.Native -> {
        annotation.view.rotationX = delegateProvider.mapCameraManagerDelegate.cameraState.pitch.toFloat()
      }
    }
    mapView.addView(annotation.view, annotation.viewLayoutParams)
  }

  override fun removeViewAnnotation(view: View) {
    annotations.remove(view.hashCode())
  }

  override fun cleanup() {
    super.cleanup()
    delegateProvider.mapListenerDelegate.removeOnCameraChangeListener(this)
    annotations.clear()
  }

  // this should be some new API from gl-native returning array of [id, ScreenCoordinate, width, height]
  // however it terms of prototyping we use camera change callback and calculate ScreenCoordinate by ourselves
  override fun onCameraChanged() {
    annotations.forEach {
      with(it.value) {
        mapView.removeView(view)
        val zoom = delegateProvider.mapCameraManagerDelegate.cameraState.zoom
        if (options.resizeFactor != 0f && zoom != descriptor.initialCamera.zoom) {
          // updated width and height must come from gl native but we calculate them manually here
          val k = (
            1.0 + options.resizeFactor * (zoom - descriptor.initialCamera.zoom)
              / zoom.coerceAtLeast(descriptor.initialCamera.zoom)
            ).coerceAtLeast(0.0)
          viewLayoutParams = FrameLayout.LayoutParams(
            (k * initialWidth).toInt(),
            (k * initialHeight).toInt()
          )
        }
        val point = geometry as Point
        // we must have new callback from gl-native returning ORDERED array of [viewId, ScreenCoordinate, width, height]
        // in this case we calculate it ourselves (bind to hardcoded point)
        val leftTopInner = delegateProvider.mapCameraManagerDelegate.pixelForCoordinate(point)
        // TODO ideally need to depend on transformed dimensions (take pitch / bearing into account)
        val visibleOnX = leftTopInner.x >= -viewLayoutParams.width
          && leftTopInner.x <= delegateProvider.mapTransformDelegate.getSize().width
        val visibleOnY = leftTopInner.y >= -viewLayoutParams.height
          && leftTopInner.y <= delegateProvider.mapTransformDelegate.getSize().height
        if (visibleOnX && visibleOnY) {
          viewLayoutParams.setMargins(leftTopInner.x.toInt(), leftTopInner.y.toInt(), 0, 0)
          when (descriptor) {
            is ViewAnnotationDescriptor.Callout -> {
              // TBD but nothing comes to mind right now
            }
            is ViewAnnotationDescriptor.Native -> {
              // TODO too lazy to calculate it ourselves so using dirty hack here, revisit later
              view.pivotX = viewLayoutParams.width / 2f
              view.pivotY = viewLayoutParams.height / 2f
              view.rotationX = delegateProvider.mapCameraManagerDelegate.cameraState.pitch.toFloat()
              view.tag = ViewPosition(
                descriptor.initialCamera.bearing,
                view.matrix
              )
              view.rotationX = 0f
            }
          }
          mapView.addView(view, viewLayoutParams)
        }
      }
    }
  }
}