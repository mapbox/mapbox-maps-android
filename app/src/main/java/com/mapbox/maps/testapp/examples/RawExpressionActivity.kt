package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.getLayerAs

/**
 * Example showcasing raw expression support through the Value API.
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) {
      addExpressionToStyle(it)
    }
  }

  private fun addExpressionToStyle(style: Style) {
    style.getLayerAs<FillLayer>("water")?.fillColor(
      Expression.fromRaw(
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
    )
  }
}