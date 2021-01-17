package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.ValueConverter
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*
import java.lang.RuntimeException

/**
 * Example showcasing raw expression support through the ValueConverter API.
 * This example manipulates the water fill layer of mapbox-streets to:
 * ```
 *         {
 *           "id": "water",
 *           "type": "fill",
 *           "source": "composite",
 *           "source-layer": "water",
 *           "layout": {},
 *           "paint": {
 *               "fill-color": [
 *                   "interpolate",
 *                   ["linear"],
 *                   ["zoom"],
 *                   0, "hsl(103, 80%, 70%)",
 *                   8.53, "hsl(360, 80%, 70%)",
 *                   22, "hsl(196, 80%, 70%)"
 *               ]
 *           }
 *       }
 * ```
 */
class RawExpressionActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) { addExpressionToStyle(it) }
  }

  private fun addExpressionToStyle(style: Style) {
    val expression = ValueConverter.fromJson(
      """
      [
        "interpolate",
         ["linear"],
         ["zoom"],
         0, "hsl(103, 80%, 70%)",
         8.53, "hsl(360, 80%, 70%)",
         22, "hsl(196, 80%, 70%)"
       ]  
      """.trimIndent()
    )

    if (expression.isValue && expression.value != null) {
      style.setStyleLayerProperty("water", "fill-color", expression.value!!)
    } else {
      throw RuntimeException("Invalid expression")
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