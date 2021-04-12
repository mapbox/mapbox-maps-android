package com.mapbox.maps.plugin

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.delegates.MapCameraDelegate

internal class MapCameraDelegateImpl constructor(private val mapboxMap: MapboxMap) :
  MapCameraDelegate {

  override fun getLat() = mapboxMap.getCameraState().center.latitude()

  override fun getLon() = mapboxMap.getCameraState().center.longitude()

  override fun getZoom() = mapboxMap.getCameraState().zoom

  override fun getPitch() = mapboxMap.getCameraState().pitch

  override fun getBearing() = mapboxMap.getCameraState().bearing

  override fun getPadding() = mapboxMap.getCameraState().padding.let { insets ->
    arrayOf(insets.left, insets.top, insets.right, insets.bottom)
  }

  override fun getCameraState() = mapboxMap.getCameraState()

  override fun setBearing(bearing: Double) {
    mapboxMap.setCamera(CameraOptions.Builder().bearing(bearing).build())
  }
}