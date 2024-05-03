package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.layers.generated.ModelAmbientOcclusionIntensity
import com.mapbox.maps.extension.compose.style.layers.generated.ModelId
import com.mapbox.maps.extension.compose.style.layers.generated.ModelLayer
import com.mapbox.maps.extension.compose.style.layers.generated.ModelOpacity
import com.mapbox.maps.extension.compose.style.layers.generated.ModelRotation
import com.mapbox.maps.extension.compose.style.layers.generated.ModelScale
import com.mapbox.maps.extension.compose.style.layers.generated.ModelTranslation
import com.mapbox.maps.extension.compose.style.layers.generated.ModelType
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.model.addModel
import com.mapbox.maps.extension.style.model.model
import com.mapbox.turf.TurfMeasurement

/**
 * Showcase adding 3D models using model layer.
 */
@OptIn(MapboxExperimental::class)
public class ModelLayerActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(TurfMeasurement.midpoint(MODEL1_COORDINATES, MAPBOX_HELSINKI))
                zoom(CAMERA_ZOOM)
                pitch(CAMERA_PITCH)
              }
            },
          ) {
            MapEffect(Unit) {
              it.mapboxMap.apply {
                addModel(model(MODEL_ID_1) { uri(SAMPLE_MODEL_URI_1) })
                addModel(model(MODEL_ID_2) { uri(SAMPLE_MODEL_URI_2) })
              }
            }
            ModelLayer(
              sourceState = rememberGeoJsonSourceState {
                data = GeoJSONData(
                  listOf(
                    Feature.fromGeometry(MODEL1_COORDINATES)
                      .also { it.addStringProperty(MODEL_ID_KEY, MODEL_ID_1) },
                    Feature.fromGeometry(MAPBOX_HELSINKI)
                      .also { it.addStringProperty(MODEL_ID_KEY, MODEL_ID_2) }
                  )
                )
              },
              modelId = ModelId(Expression.get(MODEL_ID_KEY)),
              modelType = ModelType.COMMON_3D,
              modelScale = ModelScale(listOf(40.0, 40.0, 40.0)),
              modelTranslation = ModelTranslation(listOf(0.0, 0.0, 0.0)),
              modelRotation = ModelRotation(listOf(0.0, 0.0, 90.0)),
              modelOpacity = ModelOpacity(0.7),
              modelAmbientOcclusionIntensity = ModelAmbientOcclusionIntensity(1.0)
            )
          }
        }
      }
    }
  }

  private companion object {
    const val CAMERA_ZOOM = 16.0
    const val CAMERA_PITCH = 45.0
    const val MODEL_ID_KEY = "model-id-key"
    const val MODEL_ID_1 = "model-id-1"
    const val MODEL_ID_2 = "model-id-2"
    const val SAMPLE_MODEL_URI_1 =
      "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf"
    const val SAMPLE_MODEL_URI_2 = "asset://sportcar.glb"
    val MAPBOX_HELSINKI: Point = Point.fromLngLat(24.945389069265598, 60.17195694011002)
    val MODEL1_COORDINATES: Point =
      Point.fromLngLat(MAPBOX_HELSINKI.longitude() - 0.002, MAPBOX_HELSINKI.latitude() + 0.002)
  }
}