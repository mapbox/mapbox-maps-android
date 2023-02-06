package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.TileCoverOptions

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.getMapboxMap()
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(9.0)
            .build()
        )
      }

    val tileCoverOptions = TileCoverOptions.Builder().tileSize(256).build()
    val tiles = mapboxMap.getTileCover(tileCoverOptions)
    for (tile in tiles) {
      Log.d("TILECOVER", "tile x = ${tile.x} y= ${tile.y}, z= ${tile.z}, tile = $tile")
    }
  }

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}