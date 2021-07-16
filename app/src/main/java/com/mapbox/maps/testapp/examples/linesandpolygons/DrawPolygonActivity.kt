package com.mapbox.maps.testapp.examples.linesandpolygons

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
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
    mapView.getMapboxMap().setCamera(
      START_CAMERA_POSITION
    )
    mapView.getMapboxMap().loadStyle(
      style(styleUri = Style.LIGHT) {
        +geoJsonSource(SOURCE_ID) {
          url(SOURCE_URL)
        }
        +layerAtPosition(
          fillLayer(LAYER_ID, SOURCE_ID) {
            fillColor(Color.parseColor("#0080ff")).fillOpacity(0.5)
          },
          below = SETTLEMENT_LABEL
        )
        +lineLayer(
          TOP_LAYER_ID, SOURCE_ID
        ) {
          lineColor(ContextCompat.getColor(this@DrawPolygonActivity, R.color.black))
          lineWidth(3.0)
        }
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
    private const val TOP_LAYER_ID = "line-layer"
    private const val SETTLEMENT_LABEL = "settlement-label"
    private const val SOURCE_URL = "asset://maine_polygon.geojson"
    private val START_CAMERA_POSITION = cameraOptions {
      center(
        Point.fromLngLat(-68.137343, 45.137451)
      )
      zoom(5.0)
      bearing(0.0)
      pitch(0.0)
    }
  }
}