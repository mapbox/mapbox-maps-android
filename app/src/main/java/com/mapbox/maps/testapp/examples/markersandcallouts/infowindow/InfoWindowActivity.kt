package com.mapbox.maps.testapp.examples.markersandcallouts.infowindow

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import com.mapbox.maps.plugin.gestures.removeOnMapLongClickListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.BitmapUtils
import java.text.DecimalFormat

/**
 * Test activity showcasing markers above Washington D.C
 * using View Annotation API and point annotations.
 *
 * This example is replicating similar activity from Maps v9 that was using InfoWindow API.
 */
class InfoWindowActivity : AppCompatActivity(), OnMapLongClickListener {

  private lateinit var mapView: MapView
  private lateinit var icon: Bitmap

  private lateinit var markerManager: MarkerManager
  private var customMarker: Marker? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)

    icon = BitmapUtils.bitmapFromDrawableRes(
      this@InfoWindowActivity,
      R.drawable.blue_marker_view
    )!!
    mapView.getMapboxMap().apply {
      setCamera(
        cameraOptions {
          center(Point.fromLngLat(-77.03655168667463, 38.897705003219784))
          zoom(15.0)
        }
      )
      getStyle {
        markerManager = MarkerManager(mapView)
        addMarkers()
      }
      addOnMapLongClickListener(this@InfoWindowActivity)
    }
  }

  private fun addMarkers() {
    markerManager.addMarker(
      Marker(
        title = "Intersection",
        snippet = "H St NW with 15th St NW",
        position = Point.fromLngLat(-77.03364419, 38.9002073),
        icon = icon,
      )
    )
    markerManager.addMarker(
      Marker(
        title = "The Ellipse",
        icon = icon,
        position = Point.fromLngLat(-77.03654, 38.89393)
      )
    )
    val marker = markerManager.addMarker(
      Marker(
        title = "White House",
        snippet = """
          The official residence and principal workplace of the President of the United States,
          located at 1600 Pennsylvania Avenue NW in Washington, D.C. It has been the residence of every
          U.S. president since John Adams in 1800.
        """.trimIndent(),
        icon = icon,
        position = Point.fromLngLat(-77.03655168667463, 38.897705003219784)
      )
    )
    // open InfoWindow at startup
    markerManager.selectMarker(marker)
  }

  override fun onMapLongClick(point: Point): Boolean {
    customMarker?.let {
      markerManager.removeMarker(it)
    }
    customMarker = markerManager.addMarker(
      Marker(
        position = point,
        icon = icon,
        title = "Custom marker",
        snippet = "${DecimalFormat("#.#####").format(point.latitude())}, ${DecimalFormat("#.#####").format(point.longitude())}"
      )
    )
    return true
  }

  override fun onDestroy() {
    mapView.getMapboxMap().removeOnMapLongClickListener(this)
    markerManager.destroy()
    super.onDestroy()
  }
}