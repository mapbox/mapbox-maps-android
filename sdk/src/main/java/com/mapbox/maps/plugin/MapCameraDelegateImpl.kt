package com.mapbox.maps.plugin

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.delegates.MapCameraDelegate

internal class MapCameraDelegateImpl constructor(private val mapboxMap: MapboxMap) :
  MapCameraDelegate {

  override fun getLat() = mapboxMap.getCameraOptions(null).center?.latitude() ?: 0.0

  override fun getLon() = mapboxMap.getCameraOptions(null).center?.longitude() ?: 0.0

  override fun getZoom() = mapboxMap.getCameraOptions(null).zoom ?: 0.0

  override fun getPitch() = mapboxMap.getCameraOptions(null).pitch ?: 0.0

  override fun getBearing() = mapboxMap.getCameraOptions(null).bearing ?: 0.0

  override fun getPadding() = mapboxMap.getCameraOptions(null).padding?.let { insets ->
    arrayOf(insets.left, insets.top, insets.right, insets.bottom)
  }

  override fun getAnchor() = mapboxMap.getCameraOptions(null).anchor?.let { screenCoordinate ->
    Pair(screenCoordinate.x, screenCoordinate.y)
  }

  override fun getCameraOptions(edgeInsets: EdgeInsets?) = mapboxMap.getCameraOptions(edgeInsets)

  override fun setBearing(bearing: Double) {
    mapboxMap.jumpTo(CameraOptions.Builder().bearing(bearing).build())
  }
}