package com.mapbox.maps.testapp.utils.viewannotations

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.updateMargins
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.testapp.R

class ViewAnnotationPlugin(
  val mapView: MapView
): OnCameraChangeListener {

  // we need id to pass it to gl-native and not pass view itself as it's useless
  private val annotations = LinkedHashMap<Int, ViewAnnotation>()

  init {
    mapView.getMapboxMap().addOnCameraChangeListener(this)
  }

  // not sure if it's useful
  fun addViewAnnotation(view: View, width: Int, height: Int, point: Point) {
    val layoutParams = FrameLayout.LayoutParams(
      view.layoutParams.width,
      view.layoutParams.height
    )
    if (layoutParams.width == 0 || layoutParams.height == 0) {
      throw RuntimeException("Layout params width and height must be != 0!")
    }
    val annotation = ViewAnnotation(
      view = view,
      viewLayoutParams = layoutParams,
      width = width,
      height = height,
      point = point
    )
    annotations[view.hashCode()] = annotation
    displayAnnotation(annotation)
  }

  // preferable way to add annotations - simply provide us layout
  // so that we inflate it and return actual [View]
  fun addViewAnnotation(@LayoutRes id: Int, point: Point): View {
    // inflate only once and save view to some collection
    val nativeView = LayoutInflater.from(mapView.context)
      .inflate(R.layout.item_callout_view, mapView, false)
    val nativeViewLayout = nativeView.layoutParams as FrameLayout.LayoutParams
    val annotation = ViewAnnotation(
      view = nativeView,
      viewLayoutParams = nativeViewLayout,
      width = nativeView.layoutParams.width,
      height = nativeView.layoutParams.height,
      point = point
    )
    annotations[nativeView.hashCode()] = annotation
    displayAnnotation(annotation)
    return nativeView
  }

  // that's a temp solution to initially display view annotation on map (if map camera is not changing)
  // will be removed with new gl-native API
  private fun displayAnnotation(annotation: ViewAnnotation) {
    val leftTop = mapView.getMapboxMap().pixelForCoordinate(annotation.point)
    annotation.viewLayoutParams.updateMargins(leftTop.x.toInt(), leftTop.y.toInt(), 0, 0)
    mapView.addView(annotation.view, annotation.viewLayoutParams)
  }

  fun removeViewAnnotation(view: View) {
    annotations.remove(view.hashCode())
  }

  fun destroy() {
    mapView.getMapboxMap().removeOnCameraChangeListener(this)
  }

  // this should be some new API from gl-native returning array of [id, ScreenCoordinate]
  // however it terms of prototyping we use camera change callback and calculate ScreenCoordinate by ourselves
  override fun onCameraChanged() {
    annotations.forEach {
      mapView.removeView(it.value.view)
      // we must have new callback from gl-native returning ORDERED array of [viewId, ScreenCoordinate]
      // in this case we calculate it ourselves (bind to hardcoded point)
      val leftTopInner = mapView.getMapboxMap().pixelForCoordinate(it.value.point)
      val visibleOnX = leftTopInner.x >= -it.value.viewLayoutParams.width && leftTopInner.x <= mapView.getMapboxMap().getSize().width
      val visibleOnY = leftTopInner.y >= -it.value.viewLayoutParams.height && leftTopInner.y <= mapView.getMapboxMap().getSize().height
      if (visibleOnX && visibleOnY) {
        it.value.viewLayoutParams.updateMargins(leftTopInner.x.toInt(), leftTopInner.y.toInt(), 0, 0)
        mapView.addView(it.value.view, it.value.viewLayoutParams)
      }
    }
  }
}