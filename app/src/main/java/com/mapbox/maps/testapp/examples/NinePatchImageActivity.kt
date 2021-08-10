package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image9Patch
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconTextFit
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R

/**
 * Example showcasing of adding 9-patch image to style
 * and demonstrating how it works when stretching image.
 */
class NinePatchImageActivity : AppCompatActivity() {

  private var appendTextCounter = 1
  private lateinit var style: Style
  private lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)
    mapView.getMapboxMap().loadStyle(
      styleExtension = style(Style.MAPBOX_STREETS) {
        +image9Patch(
          NINE_PATCH_ID,
          BitmapFactory.decodeResource(resources, R.drawable.blue_round_nine)
        )
        +geoJsonSource(SOURCE_ID) {
          feature(Feature.fromGeometry(CENTER))
        }
        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(NINE_PATCH_ID)
          // make sure we stretch image both in X and Y
          iconTextFit(IconTextFit.BOTH)
          iconTextFitPadding(listOf(5.0, 5.0, 5.0, 5.0))
          textField(TEXT_BASE)
        }
      }
    ) {
      style = it
      updateIconText()
    }
    mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .center(CENTER)
        .zoom(ZOOM)
        .build()
    )
  }

  // start appending text in a loop, stretching icon in both X and Y
  private fun updateIconText() {
    mapView.postDelayed(
      {
        val layer = (style.getLayer(LAYER_ID) as SymbolLayer)
        layer.textField("${layer.textField?.getTextAsString()} $TEXT_BASE")
        appendTextCounter++
        if (appendTextCounter.rem(3) == 0) {
          layer.textField("${layer.textField?.getTextAsString()}\n")
        }
        updateIconText()
      },
      TEXT_UPDATE_TIME_MS
    )
  }

  companion object {
    private const val NINE_PATCH_ID = "red"
    private const val SOURCE_ID = "source_id"
    private const val LAYER_ID = "layer_id"
    private val CENTER = Point.fromLngLat(12.554729, 55.70651)
    private const val ZOOM = 9.0
    private const val TEXT_BASE = "Hi!"
    private const val TEXT_UPDATE_TIME_MS = 1500L
  }
}