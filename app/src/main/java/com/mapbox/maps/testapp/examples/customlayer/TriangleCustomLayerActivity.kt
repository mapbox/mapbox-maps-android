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
import kotlinx.android.synthetic.main.activity_custom_layer.*

/**
 * Test activity showcasing the Custom Layer API
 */
class TriangleCustomLayerActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_custom_layer)
    mapboxMap = mapView.getMapboxMap()
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
    fab.setImageResource(R.drawable.ic_layers_clear)
  }

  private fun initFab() {
    fab.setOnClickListener {
      swapCustomLayer()
    }
  }

  private fun swapCustomLayer() {
    mapboxMap.getStyle()?.let { style ->
      if (style.styleLayerExists(CUSTOM_LAYER_ID)) {
        style.removeStyleLayer(CUSTOM_LAYER_ID)
        fab.setImageResource(R.drawable.ic_layers)
      } else {
        addCustomLayer(style)
      }
    }
  }

  private fun updateLayer() {
    mapboxMap.triggerRepaint()
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
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