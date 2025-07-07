package com.mapbox.maps.testapp.examples.customlayer.globe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.CustomLayer
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.customLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityGlobeCustomLayerBinding

/**
 * Test activity showcasing the Custom Layer API in globe projection.
 * */
class GlobeCustomLayerActivity : AppCompatActivity() {

  private var _binding: ActivityGlobeCustomLayerBinding? = null
  private val binding get() = _binding!!
  private val mapboxMap get() = binding.mapView.mapboxMap
  private var _host: GlobeCustomLayerHost? = null
  private val host: GlobeCustomLayerHost get() = _host!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityGlobeCustomLayerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val repaint = Runnable(mapboxMap::triggerRepaint)
    _host = GlobeCustomLayerHost {
      binding.mapView.post(repaint)
    }
    mapboxMap.loadStyle(
      style(Style.STANDARD) {
        +customLayer(
          layerId = CUSTOM_LAYER_ID,
          host = host
        )
        +projection(ProjectionName.GLOBE)
      }
    ) {
      mapboxMap.setCamera(CAMERA)
      binding.fabSwapVisibility.setOnClickListener {
        swapCustomLayer()
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
    _host = null
  }

  private fun addCustomLayer(style: Style) {
    style.addLayer(
      CustomLayer(CUSTOM_LAYER_ID, host),
    )
    binding.fabSwapVisibility.setImageResource(R.drawable.ic_layers_clear)
  }

  private fun swapCustomLayer() {
    val style = mapboxMap.style ?: return
    if (style.styleLayerExists(CUSTOM_LAYER_ID)) {
      style.removeStyleLayer(CUSTOM_LAYER_ID)
      binding.fabSwapVisibility.setImageResource(R.drawable.ic_layers)
    } else {
      addCustomLayer(style)
    }
  }

  companion object {
    private const val CUSTOM_LAYER_ID = "customId"
    private val CAMERA =
      CameraOptions.Builder().center(Point.fromLngLat(20.0, 58.0)).pitch(0.0).zoom(1.0).build()
  }
}