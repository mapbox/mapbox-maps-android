package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.FillExtrusionLayer
import com.mapbox.maps.extension.style.light.generated.getLight
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_fill_extrusion.*

/**
 * Test activity showcasing fill extrusions
 */
class FillExtrusionActivity : AppCompatActivity() {

  private var isRedColor: Boolean = false
  private var isInitPosition: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fill_extrusion)
    val mapboxMap = (mapView as MapView).getMapboxMap()

    mapboxMap.jumpTo(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-74.0066, 40.7135))
        .pitch(45.0)
        .zoom(15.0)
        .build()
    )

    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) { style ->
      setupBuildings(style)
      setupLight(style)
    }
  }

  private fun setupBuildings(style: Style) {
    val fillExtrusionLayer = FillExtrusionLayer("3d-buildings", "composite")
    fillExtrusionLayer.sourceLayer("building")
    fillExtrusionLayer.filter(eq(get("extrude"), literal("true")))
    fillExtrusionLayer.minZoom(15.0)
    fillExtrusionLayer.fillExtrusionColor(Color.LTGRAY)
    fillExtrusionLayer.fillExtrusionHeight(get("height"))
    fillExtrusionLayer.fillExtrusionBase(get("min_height"))
    fillExtrusionLayer.fillExtrusionOpacity(0.9)
    style.addLayer(fillExtrusionLayer)
  }

  private fun setupLight(style: Style) {
    val light = style.getLight()
    fabLightPosition.setOnClickListener {
      isInitPosition = !isInitPosition
      if (isInitPosition) {
        light.position(1.5, 90.0, 80.0)
      } else {
        light.position(1.15, 210.0, 30.0)
      }
    }

    fabLightColor.setOnClickListener {
      isRedColor = !isRedColor
      if (isRedColor) {
        light.color(Color.RED)
      } else {
        light.color(Color.BLUE)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }
}