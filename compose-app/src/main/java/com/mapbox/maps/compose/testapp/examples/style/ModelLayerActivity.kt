package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.layers.ModelIdValue
import com.mapbox.maps.extension.compose.style.layers.generated.ModelLayer
import com.mapbox.maps.extension.compose.style.layers.generated.ModelTypeValue
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
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
      var showModelLayer by remember {
        mutableStateOf(true)
      }
      var modelUri by remember {
        mutableStateOf(SAMPLE_MODEL_URI_1)
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showModelLayer = !showModelLayer
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle model layers")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  modelUri =
                    if (modelUri == SAMPLE_MODEL_URI_1) SAMPLE_MODEL_URI_2 else SAMPLE_MODEL_URI_1
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle model uri")
              }
            }
          }
        ) {
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
            if (showModelLayer) {
              // Add model through data driven expression.
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
                }
              ) {
                modelId = ModelIdValue(Expression.get(MODEL_ID_KEY))
                modelType = ModelTypeValue.COMMON_3D
                modelScale = DoubleListValue(listOf(40.0, 40.0, 40.0))
                modelTranslation = DoubleListValue(listOf(0.0, 0.0, 0.0))
                modelRotation = DoubleListValue(listOf(0.0, 0.0, 90.0))
                modelOpacity = DoubleValue(0.7)
                modelAmbientOcclusionIntensity = DoubleValue(1.0)
              }
              // Add model through inlined model uri.
              ModelLayer(
                sourceState = rememberGeoJsonSourceState {
                  data = GeoJSONData(CityLocations.HELSINKI)
                }
              ) {
                modelId = ModelIdValue(modelId = MODEL_ID_3, uri = modelUri)
                modelType = ModelTypeValue.COMMON_3D
                modelScale = DoubleListValue(listOf(40.0, 40.0, 40.0))
                modelAmbientOcclusionIntensity = DoubleValue(1.0)
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val CAMERA_ZOOM = 15.0
    const val CAMERA_PITCH = 45.0
    const val MODEL_ID_KEY = "model-id-key"
    const val MODEL_ID_1 = "model-id-1"
    const val MODEL_ID_2 = "model-id-2"
    const val MODEL_ID_3 = "model-id-3"
    const val SAMPLE_MODEL_URI_1 =
      "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf"
    const val SAMPLE_MODEL_URI_2 = "asset://sportcar.glb"
    val MAPBOX_HELSINKI: Point = Point.fromLngLat(24.945389069265598, 60.17195694011002)
    val MODEL1_COORDINATES: Point =
      Point.fromLngLat(MAPBOX_HELSINKI.longitude() - 0.002, MAPBOX_HELSINKI.latitude() + 0.002)
  }
}