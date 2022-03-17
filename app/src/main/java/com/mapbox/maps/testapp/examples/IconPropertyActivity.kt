package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.match
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.databinding.ActivityIconPropertyBinding

/**
 * Add point data and several images to a style and use the switchCase and
 * get expressions to choose which image to display at each point in a SymbolLayer
 * based on a data property.
 */
class IconPropertyActivity : AppCompatActivity() {

  var currentIdx = 0
  var tailLen = 5
  lateinit var mainHandler: Handler
  private lateinit var style: Style
  private val layerList = listOf(
    LAYER_LOW_ID,
    LAYER_MED_ID,
    LAYER_HIGH_ID,
  )

  private val updateTextTask = object : Runnable {
    override fun run() {
      currentIdx++
      updateLayer(currentIdx)
      if (currentIdx > 16) {
        currentIdx = 1
      }
      mainHandler.postDelayed(this, 200)
    }
  }

  override fun onPause() {
    super.onPause()
    mainHandler.removeCallbacks(updateTextTask)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityIconPropertyBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mainHandler = Handler(Looper.getMainLooper())

    binding.mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(139.0, 36.475))
        .zoom(4.1)
        .build()
    )

    binding.mapView.getMapboxMap().loadStyle(
      styleExtension = style(Style.DARK) {
        +vectorSource(SOURCE_LOW) {
          url(SOURCE_LOW_URI)
        }
        +circleLayer(LAYER_LOW_ID, SOURCE_LOW) {
          sourceLayer(SOURCE_LAYER_ID)
          circleRadius(2.0)
          circleColor("hsla(0, 0%, 100%, 0)")
          minZoom(3.0)
          maxZoom(6.0)
        }
        +vectorSource(SOURCE_MED) {
          url(SOURCE_MED_URI)
        }
        +circleLayer(LAYER_MED_ID, SOURCE_MED) {
          sourceLayer(SOURCE_LAYER_ID)
          circleRadius(2.0)
          circleColor("hsla(0, 0%, 100%, 0)")
          minZoom(6.0)
          maxZoom(9.0)
        }
        +vectorSource(SOURCE_HIGH) {
          url(SOURCE_HIGH_URI)
        }
        +circleLayer(LAYER_HIGH_ID, SOURCE_HIGH) {
          sourceLayer(SOURCE_LAYER_ID)
          circleRadius(3.0)
          circleColor("hsla(0, 0%, 100%, 0)")
          minZoom(9.0)
          maxZoom(22.0)
        }
      }
    ) {
      style = it
      mainHandler.post(updateTextTask)
    }
  }

  fun updateLayer(layerId: Int) {
    Logger.i("layer", "updatelayer")

    for (layer in layerList) {
      style.setStyleLayerProperty(
        layer,
        "circle-color",
        match {
          get {
            literal("value")
          }
          // head color
          literal(layerId.toLong())
          literal("hsla(0, 0%, 100%, 0.3)")

          // tail color
          repeat(tailLen) { i ->
            literal((layerId - (i + 1)).toLong())
            literal("hsla(0, 14%, 93%, 0.15)")
          }

          // rest are transparent
          literal("hsla(0, 0%, 100%, 0)")
        })
    }
  }

  companion object {
    private const val SOURCE_LAYER_ID = "wind-line"

    private const val SOURCE_LOW_URI = "mapbox://takutosuzukimapbox.wind-lowzoom-planet"
    private const val SOURCE_LOW = "source-low"
    private const val LAYER_LOW_ID = "layer-low"

    private const val SOURCE_MED_URI = "mapbox://takutosuzukimapbox.wind-midzoom-planet"
    private const val SOURCE_MED = "source-med"
    private const val LAYER_MED_ID = "layer-med"

    private const val SOURCE_HIGH_URI = "mapbox://takutosuzukimapbox.wind-highzoom-planet"
    private const val SOURCE_HIGH = "source-high"
    private const val LAYER_HIGH_ID = "layer-high"
  }
}