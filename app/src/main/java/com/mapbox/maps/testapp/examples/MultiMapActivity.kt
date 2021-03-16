package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.fragment.MapFragment

/**
 * Example showing using several map views in one activity.
 */
class MultiMapActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_multi_map)
    initFragmentStyle(R.id.map1, Style.MAPBOX_STREETS, generateCamera(38.913187, -77.032546, 12.0))
    initFragmentStyle(R.id.map2, Style.LIGHT, generateCamera(37.775732, -122.413985, 13.0))
    initFragmentStyle(R.id.map3, Style.SATELLITE_STREETS, generateCamera(12.97913, 77.59188, 14.0))
    initFragmentStyle(R.id.map4, Style.DARK, generateCamera(-13.155980, -74.217134, 15.0))
  }

  private fun initFragmentStyle(
    fragmentId: Int,
    styleId: String,
    cameraOptions: CameraOptions
  ) {
    val fragment = supportFragmentManager.findFragmentById(fragmentId) as MapFragment
    fragment.getMapAsync {
      it.setCamera(cameraOptions)
      it.loadStyleUri(styleId)
    }
  }

  private fun generateCamera(lat: Double, lng: Double, zoom: Double): CameraOptions {
    return CameraOptions.Builder().center(Point.fromLngLat(lng, lat)).zoom(zoom).build()
  }
}