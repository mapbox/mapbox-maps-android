package com.mapbox.maps.testapp.examples.customlayer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityCustomLayerBinding

/**
 * Test activity showcasing the Custom Layer API
 */
class TriangleCustomLayerActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var binding: ActivityCustomLayerBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCustomLayerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      mapboxMap.setCamera(CAMERA)
      addCustomLayer(it)
      initFab()
    }
  }

  private fun addCustomLayer(style: Style) {
    val expected = style.addStyleCustomLayer(
      layerId = CUSTOM_LAYER_ID,
      TriangleCustomLayer(),
      LayerPosition(null, "building", null),
    )
    expected.error?.let {
      Logger.e(TAG, "Add custom layer exception $it")
    }
    binding.fab.setImageResource(R.drawable.ic_layers_clear)
  }

  private fun initFab() {
    binding.fab.setOnClickListener {
      swapCustomLayer()
    }
  }

  private fun swapCustomLayer() {
    mapboxMap.getStyle()?.let { style ->
      if (style.styleLayerExists(CUSTOM_LAYER_ID)) {
        style.removeStyleLayer(CUSTOM_LAYER_ID)
        binding.fab.setImageResource(R.drawable.ic_layers)
      } else {
        addCustomLayer(style)
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
        TriangleCustomLayer.color = floatArrayOf(
          1.0f, 0.0f, 0.0f, 0.5f,
          0.0f, 1.0f, 0.0f, 0.5f,
          0.0f, 0.0f, 1.0f, 0.5f,
        )
        true
      }
      R.id.action_set_color_green -> {
        TriangleCustomLayer.color = floatArrayOf(
          0.0f, 1.0f, 0.0f, 0.5f,
          0.0f, 0.0f, 1.0f, 0.5f,
          1.0f, 0.0f, 0.0f, 0.5f,
        )
        true
      }
      R.id.action_set_color_blue -> {
        TriangleCustomLayer.color = floatArrayOf(
          0.0f, 0.0f, 1.0f, 0.5f,
          1.0f, 0.0f, 0.0f, 0.5f,
          0.0f, 1.0f, 0.0f, 0.5f,
        )
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  companion object {
    private const val CUSTOM_LAYER_ID = "custom"
    private const val TAG = "TriangleCustomLayerActivity"
    private val CAMERA =
      CameraOptions.Builder().center(Point.fromLngLat(20.0, 58.0)).zoom(3.0).build()
  }
}