package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.databinding.ActivityShowHideLayersBinding

/**
 * Example for showing / hiding layers on button click.
 */
class ShowHideLayersActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityShowHideLayersBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.getMapboxMap().loadStyle(
      style(Style.LIGHT) {
        +vectorSource(SOURCE_ID) {
          url(SOURCE_URL)
        }
        +circleLayer(LAYER_ID, SOURCE_ID) {
          sourceLayer(SOURCE_LAYER)
          visibility(Visibility.VISIBLE)
          circleRadius(8.0)
          circleColor(Color.argb(255, 55, 148, 179))
        }
      }
    )
    binding.fabLayerToggle.setOnClickListener {
      binding.mapView.getMapboxMap().getStyle {
        it.getLayer(LAYER_ID)?.let { layer ->
          if (layer.visibility == Visibility.VISIBLE) {
            layer.visibility(Visibility.NONE)
          } else {
            layer.visibility(Visibility.VISIBLE)
          }
        }
      }
    }
  }

  companion object {
    private const val SOURCE_ID = "museums_source"
    private const val SOURCE_URL = "mapbox://mapbox.2opop9hr"
    private const val SOURCE_LAYER = "museum-cusco"
    private const val LAYER_ID = "museums"
  }
}