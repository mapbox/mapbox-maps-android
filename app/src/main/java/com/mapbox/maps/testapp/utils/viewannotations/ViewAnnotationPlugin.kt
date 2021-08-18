package com.mapbox.maps.testapp.utils.viewannotations

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.view.updateMargins
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener

class ViewAnnotationPlugin(
  val mapView: MapView
): OnCameraChangeListener {

  // using async inflater if needed to free up main thread a bit
  private val asyncInflater by lazy { AsyncLayoutInflater(mapView.context) }
  // we need id to pass it to gl-native and not pass view itself as it's useless
  private val annotations = LinkedHashMap<Int, ViewAnnotation>()

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
    mapView.getMapboxMap().addOnCameraChangeListener(this)
  }

  // synchronous method
  fun addViewAnnotation(
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
        ViewAnnotationType.NATIVE -> ViewAnnotationDescriptor.Native(mapView.getMapboxMap().cameraState)
        ViewAnnotationType.CALLOUT -> ViewAnnotationDescriptor.Callout(mapView.getMapboxMap().cameraState)
      },
      initialWidth = nativeView.layoutParams.width,
      initialHeight = nativeView.layoutParams.height,
      geometry = geometry,
      options = options
    )
    annotations[nativeView.hashCode()] = annotation
    displayAnnotation(annotation)
    return nativeView
  }

  // asynchronous method
  fun addViewAnnotation(
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
          ViewAnnotationType.NATIVE -> ViewAnnotationDescriptor.Native(
            mapView.getMapboxMap().cameraState
          )
          ViewAnnotationType.CALLOUT -> ViewAnnotationDescriptor.Callout(
            mapView.getMapboxMap().cameraState
          )
        },
        initialWidth = view.layoutParams.width,
        initialHeight = view.layoutParams.height,
        geometry = geometry,
        options = options
      )
      annotations[view.hashCode()] = annotation
      displayAnnotation(annotation)
      result.invoke(view)
    }
  }

  // that's a temp solution to initially display view annotation on map (if map camera is not changing)
  // will be removed with new gl-native API
  private fun displayAnnotation(annotation: ViewAnnotation) {
    val point = annotation.geometry as Point
    val leftTop = mapView.getMapboxMap().pixelForCoordinate(point)
    // correctly position the view
    annotation.viewLayoutParams.updateMargins(leftTop.x.toInt(), leftTop.y.toInt(), 0, 0)
    when (annotation.descriptor) {
      is ViewAnnotationDescriptor.Callout -> {
        // TBD but nothing comes to mind right now
      }
      is ViewAnnotationDescriptor.Native -> {
        // TODO pivot needs to be f(anchor), not anchor is left-top hardcoded so hardcoding zeros
        annotation.view.pivotX = 0f
        annotation.view.pivotY = 0f
        // do not depend on current map bearing on initial view adding
        annotation.view.rotation = 0f
        // TODO not working as expected
//        annotation.view.pivotX = annotation.viewLayoutParams.width / 2f
//        annotation.view.pivotY = annotation.viewLayoutParams.height / 2f
        annotation.view.rotationX = mapView.getMapboxMap().cameraState.pitch.toFloat()
      }
    }
    mapView.addView(annotation.view, annotation.viewLayoutParams)
  }

  fun removeViewAnnotation(view: View) {
    annotations.remove(view.hashCode())
  }

  fun destroy() {
    mapView.getMapboxMap().removeOnCameraChangeListener(this)
    annotations.clear()
  }

  // this should be some new API from gl-native returning array of [id, ScreenCoordinate, width, height]
  // however it terms of prototyping we use camera change callback and calculate ScreenCoordinate by ourselves
  override fun onCameraChanged() {
    annotations.forEach {
      with(it.value) {
        mapView.removeView(view)
        val zoom = mapView.getMapboxMap().cameraState.zoom
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
        // we must have new callback from gl-native returning ORDERED array of [viewId, ScreenCoordinate]
        // in this case we calculate it ourselves (bind to hardcoded point)
        val leftTopInner = mapView.getMapboxMap().pixelForCoordinate(point)
        // TODO ideally need to depend on transformed dimensions (take pitch / bearing into account)
        val visibleOnX = leftTopInner.x >= -viewLayoutParams.width && leftTopInner.x <= mapView.getMapboxMap().getSize().width
        val visibleOnY = leftTopInner.y >= -viewLayoutParams.height && leftTopInner.y <= mapView.getMapboxMap().getSize().height
        if (visibleOnX && visibleOnY) {
          viewLayoutParams.updateMargins(leftTopInner.x.toInt(), leftTopInner.y.toInt(), 0, 0)
          when (descriptor) {
            is ViewAnnotationDescriptor.Callout -> {
              // TBD but nothing comes to mind right now
            }
            is ViewAnnotationDescriptor.Native -> {
              view.pivotX = 0f
              view.pivotY = 0f
              view.rotation = -(mapView.getMapboxMap().cameraState.bearing - descriptor.initialCamera.bearing).toFloat()
              // TODO not working as expected
//              view.pivotX = viewLayoutParams.width / 2f
//              view.pivotY = viewLayoutParams.height / 2f
              view.rotationX = mapView.getMapboxMap().cameraState.pitch.toFloat()
            }
          }
          mapView.addView(view, viewLayoutParams)
        }
      }
    }
  }
}