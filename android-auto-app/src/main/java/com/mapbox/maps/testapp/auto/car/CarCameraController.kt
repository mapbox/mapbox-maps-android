package com.mapbox.maps.testapp.auto.car

import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapSurface
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.androidauto.OnMapScrollListener
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener

/**
 * Controller class to handle map camera changes.
 */
class CarCameraController : OnIndicatorPositionChangedListener, OnIndicatorBearingChangedListener, OnMapScrollListener {

  private var lastGpsLocation: Point = HELSINKI
  private var isTrackingPuck = true

  private lateinit var surface: MapSurface
  private var insets: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)

  /**
   * Initialise the car camera controller with a map surface.
   */
  fun init(mapSurface: MapSurface, edgeInsets: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)) {
    insets = edgeInsets
    surface = mapSurface
    surface.getMapboxMap().setCamera(
      cameraOptions {
        pitch(INITIAL_PITCH)
        zoom(INITIAL_ZOOM)
        center(lastGpsLocation)
      }
    )
  }

  override fun onIndicatorPositionChanged(point: Point) {
    lastGpsLocation = point
    if (isTrackingPuck) {
      surface.getMapboxMap().setCamera(
        cameraOptions {
          center(point)
          padding(insets)
        }
      )
    }
  }

  override fun onIndicatorBearingChanged(bearing: Double) {
    if (isTrackingPuck) {
      surface.getMapboxMap().setCamera(
        cameraOptions {
          bearing(bearing)
        }
      )
    }
  }

  override fun onMapScroll() {
    dismissTracking()
  }

  /**
   * Make camera center to location puck and track the location puck's position.
   */
  fun focusOnLocationPuck() {
    surface.camera.flyTo(
      cameraOptions {
        center(lastGpsLocation)
      }
    )
    isTrackingPuck = true
  }

  private fun dismissTracking() {
    isTrackingPuck = false
  }

  /**
   * Adjust the camera's zoom level by the given scale factor.
   *
   * @param scaleFactor the scale factor to be applied to the camera's current zoom level.
   */
  fun zoomBy(scaleFactor: Double) {
    val cameraState = surface.getMapboxMap().cameraState
    surface.camera.easeTo(
      cameraOptions {
        zoom(scaleFactor * cameraState.zoom)
      }
    )
  }

  companion object {
    private val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
    private const val INITIAL_ZOOM = 16.0
    private const val INITIAL_PITCH = 75.0
    private const val TAG = "CarCameraController"
  }
}