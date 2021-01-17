package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_dds_draw_polygon.*

/**
 * Draw a vector polygon on a map with the Mapbox Android SDK.
 */
class DrawPolygonActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dds_draw_polygon)
    mapView.getMapboxMap().loadStyle(
      style(styleUri = Style.MAPBOX_STREETS) {
        +geoJsonSource(SOURCE_ID) {
          feature(Feature.fromGeometry(Polygon.fromLngLats(POINTS)))
        }
        +layerAtPosition(
          fillLayer(LAYER_ID, SOURCE_ID) {
            fillColor(ContextCompat.getColor(this@DrawPolygonActivity, R.color.polygon))
          },
          below = SETTLEMENT_LABEL
        )
      }
    )
    findViewById<View>(R.id.pattern_fab).setOnClickListener {
      mapView.getMapboxMap().getStyle { style ->
        val bitmap = ContextCompat.getDrawable(this@DrawPolygonActivity, R.drawable.pattern)
          ?.toBitmap(128, 128)!!
        style.addImage(IMAGE_ID, bitmap)
        val layer = style.getLayer(LAYER_ID) as FillLayer
        layer.fillPattern(IMAGE_ID)
        layer.fillOpacity(0.7)
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

  companion object {
    private const val IMAGE_ID = "stripe-pattern"
    private const val LAYER_ID = "layer-id"
    private const val SOURCE_ID = "source-id"
    private const val SETTLEMENT_LABEL = "settlement-label"
    private val POINTS = listOf(
      listOf(
        Point.fromLngLat(-122.685699, 45.522585),
        Point.fromLngLat(-122.685699, 45.522585),
        Point.fromLngLat(-122.708873, 45.534611),
        Point.fromLngLat(-122.678833, 45.530883),
        Point.fromLngLat(-122.667503, 45.547115),
        Point.fromLngLat(-122.660121, 45.530643),
        Point.fromLngLat(-122.636260, 45.533529),
        Point.fromLngLat(-122.659091, 45.521743),
        Point.fromLngLat(-122.648792, 45.510677),
        Point.fromLngLat(-122.664070, 45.515008),
        Point.fromLngLat(-122.669048, 45.502496),
        Point.fromLngLat(-122.678489, 45.515369),
        Point.fromLngLat(-122.702007, 45.506346),
        Point.fromLngLat(-122.685699, 45.522585)
      )
    )
  }
}