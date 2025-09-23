package com.mapbox.maps.testapp.examples.customlayer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.CustomLayer
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.customLayer
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityCustomLayerBinding

/**
 * Test activity showcasing the Custom Layer API
 */
class CustomLayerActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var binding: ActivityCustomLayerBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCustomLayerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.loadStyle(
      style(Style.MAPBOX_STREETS) {
        +layerAtPosition(
          customLayer(
            layerId = CUSTOM_LAYER_ID,
            host = ExampleCustomLayer()
          ),
          below = "building"
        )
      }
    ) {
      mapboxMap.setCamera(
        cameraOptions {
          center(Point.fromLngLat(116.39053, 39.91448))
          pitch(0.0)
          bearing(0.0)
          zoom(10.0)
        }
      )
      initFab()
    }
  }

  private fun initFab() {
    binding.fab.setOnClickListener {
      swapCustomLayer()
    }
  }

  private fun swapCustomLayer() {
    mapboxMap.style?.let { style ->
      if (style.styleLayerExists(CUSTOM_LAYER_ID)) {
        style.removeStyleLayer(CUSTOM_LAYER_ID)
        binding.fab.setImageResource(R.drawable.ic_layers)
      } else {
        style.addLayerBelow(
          CustomLayer(CUSTOM_LAYER_ID, ExampleCustomLayer()),
          below = "building"
        )
        binding.fab.setImageResource(R.drawable.ic_layers_clear)
      }
    }
  }

  private fun updateLayer() {
    mapboxMap.triggerRepaint()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_custom_layer, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_update_layer -> {
        updateLayer()
        true
      }

      R.id.action_set_color_red -> {
        ExampleCustomLayer.color = floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f)
        true
      }

      R.id.action_set_color_green -> {
        ExampleCustomLayer.color = floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f)
        true
      }

      R.id.action_set_color_blue -> {
        ExampleCustomLayer.color = floatArrayOf(0.0f, 0.0f, 1.0f, 1.0f)
        true
      }

      else -> super.onOptionsItemSelected(item)
    }
  }

  companion object {
    private const val CUSTOM_LAYER_ID = "customId"
  }
}