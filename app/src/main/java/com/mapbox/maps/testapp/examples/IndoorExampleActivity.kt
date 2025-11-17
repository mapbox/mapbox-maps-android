package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.IndoorManager
import com.mapbox.maps.IndoorState
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
      },
      styleUri = STYLE_URI
    )

    val mapView = MapView(this, mapInitOptions)
    setContentView(mapView)
    val mapboxMap = mapView.mapboxMap

    mapboxMap.getStyle {
      mapboxMap.indoor.setOnIndoorUpdatedCallback(object : IndoorManager.OnIndoorUpdatedCallback {
        override fun onOnIndoorUpdated(onIndoorUpdated: IndoorState) {
          logD(TAG, "Indoor_Subscriber_0: Indoor state updated: $onIndoorUpdated")
          logD(TAG, "Indoor_Subscriber_0: Selected floor: ${onIndoorUpdated.selectedFloorId}")
        }
      })

      logD(TAG, "Indoor: selectFloor: $FLOOR_ID")
      mapboxMap.indoor.selectFloor(FLOOR_ID)
    }
  }

  companion object {
    private const val TAG = "IndoorExampleActivity"
    private const val STYLE_URI = "mapbox://styles/mapbox/streets-v12"

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