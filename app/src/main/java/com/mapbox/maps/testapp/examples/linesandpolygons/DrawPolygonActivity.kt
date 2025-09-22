package com.mapbox.maps.testapp.examples.linesandpolygons

import android.graphics.Color
import android.os.Bundle
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
import com.mapbox.maps.testapp.databinding.ActivityDdsDrawPolygonBinding

/**
 * Draw a vector polygon on a map with the Mapbox Android SDK.
 */
class DrawPolygonActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityDdsDrawPolygonBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.mapboxMap.setCamera(
      START_CAMERA_POSITION
    )
    binding.mapView.mapboxMap.loadStyle(
      style(style = Style.LIGHT) {
        +geoJsonSource(SOURCE_ID) {
          data(SOURCE_URL)
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
    binding.patternFab.setOnClickListener {
      binding.mapView.mapboxMap.getStyle { style ->
        val bitmap = ContextCompat.getDrawable(this@DrawPolygonActivity, R.drawable.pattern)
          ?.toBitmap(128, 128)!!
        style.addImage(IMAGE_ID, bitmap)
        val layer = style.getLayer(LAYER_ID) as FillLayer
        layer.fillPattern(IMAGE_ID)
        layer.fillOpacity(0.7)
      }
    }
  }

  companion object {
    private const val IMAGE_ID = "stripe-pattern"
    private const val LAYER_ID = "layer-id"
    private const val SOURCE_ID = "source-id"
    private const val TOP_LAYER_ID = "line-layer"
    private const val SETTLEMENT_LABEL = "settlement-major-label"
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