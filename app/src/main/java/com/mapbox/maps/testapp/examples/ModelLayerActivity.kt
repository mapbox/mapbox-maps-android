package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.ModelLayer
import com.mapbox.maps.extension.style.layers.generated.modelLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.ModelSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.sources.modelSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_dds_draw_polygon.mapView
import kotlinx.android.synthetic.main.activity_model_layer.*

/**
 * Example of using model layer with 3D model puck.
 */
class ModelLayerActivity : AppCompatActivity() {

  private var orientationX = 90.0
  private var orientationZ = -90.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_model_layer)
    mapView.getMapboxMap().loadStyle(
      style(styleUri = Style.MAPBOX_STREETS) {
        +modelSource(SOURCE_ID) {
          url("asset://race_car_model.gltf")
          position(listOf(116.3912844657898, 39.906345547362555))
          orientation(listOf(orientationX, 0.0, 0.0))

          // Not stable for multi models, may cause crash
          url(ARROW, "asset://arrow.gltf")
          position(ARROW, listOf(116.3912844657898, 39.906445547362555))
          orientation(ARROW, listOf(0.0, 0.0, orientationZ))
        }

        +layerAtPosition(
          modelLayer(LAYER_ID, SOURCE_ID) {
            modelOpacity(0.8)
            modelScale(listOf(5.0, 5.0, 5.0).toList())
          },
          below = SETTLEMENT_LABEL
        )
      }
    )

    update_fab.setOnClickListener {
      mapView.getMapboxMap().getStyle {
        val layer = it.getLayer(LAYER_ID) as ModelLayer
        layer.modelTranslation(listOf(orientationX, 0.0, 0.0))
        val source = it.getSource(SOURCE_ID) as ModelSource
        orientationZ += 90
        source.setPosition(listOf(116.3912844657898, 39.906445547362555))
        source.setOrientation(listOf(orientationX, 0.0, 90 + orientationZ))
        source.setPosition(listOf(116.3912844657898, 39.906345547362555), ARROW)
        source.setOrientation(listOf(0.0, 0.0, orientationZ), ARROW)
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
    private const val LAYER_ID = "layer-id"
    private const val SOURCE_ID = "source-id"
    private const val ARROW = "arrow"
    private const val SETTLEMENT_LABEL = "settlement-label"
  }
}