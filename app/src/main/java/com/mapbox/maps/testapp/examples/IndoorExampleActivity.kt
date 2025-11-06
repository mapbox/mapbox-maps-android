package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.logD

/**
 * Example showcasing the Indoor Manager API for controlling indoor map floor display.
 *
 */
class IndoorExampleActivity : AppCompatActivity() {

  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Initialize MapView with MapInitOptions and camera focused on JFK Airport
    // Do not commit staging or internal style URIs.
    val mapInitOptions = MapInitOptions(
      context = this,
      cameraOptions = cameraOptions {
        center(Point.fromLngLat(LONGITUDE, LATITUDE))
        zoom(ZOOM)
        bearing(BEARING)
        pitch(PITCH)
      }
    )

    val mapView = MapView(this, mapInitOptions)
    setContentView(mapView)
    val mapboxMap = mapView.mapboxMap

    mapboxMap.getStyle {
      // Select a specific floor (this ID should match a floor in your indoor data)
      // Example floor ID - replace with actual floor ID from your indoor data
      mapboxMap.indoor.selectFloor(FLOOR_ID)

      // Listen to indoor state updates
      mapboxMap.indoor.onIndoorUpdate { indoorState ->
        logD(TAG, "Indoor state updated: $indoorState")
        logD(TAG, "Available floors: ${indoorState.floors}")
        logD(TAG, "Selected floor: ${indoorState.selectedFloorId}")
      }
    }
  }

  companion object {
    private const val TAG = "IndoorExampleActivity"

    // JFK Airport coordinates
    private const val LATITUDE = 40.6441
    private const val LONGITUDE = -73.7824
    private const val ZOOM = 16.0
    private const val BEARING = 12.0
    private const val PITCH = 60.0

    // Example floor ID - should match a floor in your indoor data
    private const val FLOOR_ID = "b937e2aa3423453ab0552d9f"
  }
}