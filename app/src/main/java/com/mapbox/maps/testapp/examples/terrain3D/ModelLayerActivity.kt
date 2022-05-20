package com.mapbox.maps.testapp.examples.terrain3D

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.generated.modelLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ModelType
import com.mapbox.maps.extension.style.model.model
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style

/**
 * Showcase adding a 3D model using model layer.
 */
class ModelLayerActivity : AppCompatActivity() {
  private lateinit var mapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)

    mapView.getMapboxMap().apply {
      setCamera(
        cameraOptions {
          center(HELSINKI)
          zoom(15.0)
          pitch(45.0)
        }
      )
      loadStyle(
        style(Style.LIGHT) {
          +model(MODEL_ID) {
            uri(SAMPLE_MODEL_URI)
          }
          +geoJsonSource(SOURCE_ID) {
            geometry(HELSINKI)
          }
          +modelLayer(MODEL_LAYER_ID, SOURCE_ID) {
            modelId(MODEL_ID)
            modelType(ModelType.COMMON_3D)
            modelScale(listOf(100.0, 100.0, 100.0))
          }
        }
      )
    }

  }

  private companion object {
    const val SOURCE_ID = "source-id"
    const val MODEL_LAYER_ID = "model-layer-id"
    const val MODEL_ID = "model-id"
    const val SAMPLE_MODEL_URI =
      "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf"
    val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
  }
}