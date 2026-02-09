package com.mapbox.maps.compose.testapp.examples.model

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.layers.generated.ModelLayer
import com.mapbox.maps.extension.compose.style.layers.generated.ModelTypeValue
import com.mapbox.maps.extension.compose.style.lights.LightsState
import com.mapbox.maps.extension.compose.style.lights.generated.rememberAmbientLightState
import com.mapbox.maps.extension.compose.style.lights.generated.rememberDirectionalLightState
import com.mapbox.maps.extension.compose.style.sources.ModelSourceModelsValue
import com.mapbox.maps.extension.compose.style.sources.generated.rememberModelSourceState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.rememberStandardStyleState
import com.mapbox.maps.extension.style.expressions.dsl.generated.match
import com.mapbox.maps.extension.style.sources.generated.modelSourceModel

/**
 * Showcase interactive 3D model with feature-state driven updates.
 * Demonstrates using expressions and feature state to control model materials and nodes.
 */
public class Interactive3DModelFeatureStateActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        Interactive3DModelFeatureStateScreen()
      }
    }
  }
}

@Composable
private fun Interactive3DModelFeatureStateScreen() {
  // Vehicle parameters
  var doorsFrontLeft by remember { mutableStateOf(0.5f) }
  var doorsFrontRight by remember { mutableStateOf(0f) }
  var trunk by remember { mutableStateOf(0f) }
  var hood by remember { mutableStateOf(0f) }
  var brakeLights by remember { mutableStateOf(0f) }
  var vehicleColor by remember { mutableStateOf(Color.WHITE) }
  var showColorDialog by remember { mutableStateOf(false) }

  val mapViewportState = rememberMapViewportState {
    setCameraOptions {
      center(CAR_POSITION)
      zoom(19.4)
      bearing(35.0)
      pitch(60.0)
      padding(EdgeInsets(0.0, 0.0, 600.0, 0.0))
    }
  }

  val model = modelSourceModel(CAR_MODEL_URI) {
    uri(CAR_MODEL_URI)
    position(listOf(CAR_POSITION.longitude(), CAR_POSITION.latitude()))
    orientation(listOf(0.0, 0.0, 0.0))
    materialOverrideNames(
      listOf(
        "body",
        "lights_brakes",
        "lights-brakes_reverse",
        "lights_brakes_volume",
        "lights-brakes_reverse_volume"
      )
    )
    nodeOverrideNames(
      listOf(
        "doors_front-left",
        "doors_front-right",
        "hood",
        "trunk"
      )
    )
  }

  val modelSourceState = rememberModelSourceState(sourceId = SOURCE_ID) {
    models = ModelSourceModelsValue(hashMapOf(CAR_MODEL_KEY to model))
  }

  val ambientLightState = rememberAmbientLightState("environment") {
    intensity = DoubleValue(0.4)
  }
  val directionalLightState = rememberDirectionalLightState("sun_light") {
    castShadows = BooleanValue(true)
  }

  ExampleScaffold {
    Box(Modifier.fillMaxSize()) {
      MapboxMap(
        modifier = Modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        style = {
          MapboxStandardStyle(
            standardStyleState = rememberStandardStyleState {
                configurationsState.apply {
                  lightsState = LightsState(directionalLightState, ambientLightState)
                  show3dObjects = BooleanValue(false)
                }
            },
          )
        }
      ) {

        ModelLayer(modelSourceState, LAYER_ID) {
          modelScale = DoubleListValue(listOf(10.0, 10.0, 10.0))
          modelType = ModelTypeValue.LOCATION_INDICATOR

          modelColor = ColorValue(
            match {
              get { literal("part") }
              literal("lights_brakes")
              featureState { literal("brake-light-color") }
              literal("lights-brakes_reverse")
              featureState { literal("brake-light-color") }
              literal("lights_brakes_volume")
              featureState { literal("brake-light-color") }
              literal("lights-brakes_reverse_volume")
              featureState { literal("brake-light-color") }
              featureState { literal("vehicle-color") }
            }
          )
          modelColorMixIntensity = DoubleValue(
            match {
              get { literal("part") }
              literal("body")
              literal(1.0)
              literal("lights_brakes")
              featureState { literal("brake-light-emission") }
              literal("lights-brakes_reverse")
              featureState { literal("brake-light-emission") }
              literal("lights_brakes_volume")
              featureState { literal("brake-light-emission") }
              literal("lights-brakes_reverse_volume")
              featureState { literal("brake-light-emission") }
              literal(0.0)
            }
          )
          modelEmissiveStrength = DoubleValue(
            match {
              get { literal("part") }
              literal("lights_brakes")
              featureState { literal("brake-light-emission") }
              literal("lights_brakes_volume")
              featureState { literal("brake-light-emission") }
              literal("lights-brakes_reverse")
              featureState { literal("brake-light-emission") }
              literal("lights-brakes_reverse_volume")
              featureState { literal("brake-light-emission") }
              literal(0.0)
            }
          )
          modelOpacity = DoubleValue(
            match {
              get { literal("part") }
              literal("lights_brakes_volume")
              featureState { literal("brake-light-emission") }
              literal("lights-brakes_reverse_volume")
              featureState { literal("brake-light-emission") }
              literal(1.0)
            }
          )
          modelRotation = DoubleListValue(
            match {
              get { literal("part") }
              literal("doors_front-left")
              featureState { literal("doors-front-left") }
              literal("doors_front-right")
              featureState { literal("doors-front-right") }
              literal("hood")
              featureState { literal("hood") }
              literal("trunk")
              featureState { literal("trunk") }
              literal(listOf(0.0, 0.0, 0.0))
            }
          )
        }

        // Granular feature state updates - each property updates independently
        // setFeatureState merges the provided state with existing state, so we only need to update changed properties
        val doorOpeningDegMax = 80.0
        val mix = { t: Double, a: Double, b: Double -> b * t - a * (t - 1) }

        // Centralized callback handler for feature state updates
        val onFeatureStateResult: (expected: Expected<String, None>) -> Unit = { result ->
          result.error?.let { error ->
            android.util.Log.e("3DModel", "Failed to set feature state: $error")
          }
        }

        // Update vehicle color
        MapEffect(vehicleColor) { mapView ->
          val r = Color.red(vehicleColor)
          val g = Color.green(vehicleColor)
          val b = Color.blue(vehicleColor)
          mapView.mapboxMap.setFeatureState(
            SOURCE_ID,
            null,
            CAR_MODEL_KEY,
            Value.valueOf(hashMapOf("vehicle-color" to Value.valueOf("rgba($r, $g, $b, 1)"))),
            onFeatureStateResult
          )
        }

        // Update brake lights
        MapEffect(brakeLights) { mapView ->
          mapView.mapboxMap.setFeatureState(
            SOURCE_ID,
            null,
            CAR_MODEL_KEY,
            Value.valueOf(hashMapOf("brake-light-emission" to Value.valueOf(brakeLights.toDouble()))),
            onFeatureStateResult
          )
        }

        // Update left door
        MapEffect(doorsFrontLeft) { mapView ->
          mapView.mapboxMap.setFeatureState(
            SOURCE_ID,
            null,
            CAR_MODEL_KEY,
            Value.valueOf(
              hashMapOf(
                "doors-front-left" to Value.valueOf(
                  listOf(
                    Value.valueOf(0.0),
                    Value.valueOf(mix(doorsFrontLeft.toDouble(), 0.0, -doorOpeningDegMax)),
                    Value.valueOf(0.0)
                  )
                )
              )
            ),
            onFeatureStateResult
          )
        }

        // Update right door
        MapEffect(doorsFrontRight) { mapView ->
          mapView.mapboxMap.setFeatureState(
            SOURCE_ID,
            null,
            CAR_MODEL_KEY,
            Value.valueOf(
              hashMapOf(
                "doors-front-right" to Value.valueOf(
                  listOf(
                    Value.valueOf(0.0),
                    Value.valueOf(mix(doorsFrontRight.toDouble(), 0.0, doorOpeningDegMax)),
                    Value.valueOf(0.0)
                  )
                )
              )
            ),
            onFeatureStateResult
          )
        }

        // Update hood
        MapEffect(hood) { mapView ->
          mapView.mapboxMap.setFeatureState(
            SOURCE_ID,
            null,
            CAR_MODEL_KEY,
            Value.valueOf(
              hashMapOf(
                "hood" to Value.valueOf(
                  listOf(
                    Value.valueOf(mix(hood.toDouble(), 0.0, 45.0)),
                    Value.valueOf(0.0),
                    Value.valueOf(0.0)
                  )
                )
              )
            ),
            onFeatureStateResult
          )
        }

        // Update trunk
        MapEffect(trunk) { mapView ->
          mapView.mapboxMap.setFeatureState(
            SOURCE_ID,
            null,
            CAR_MODEL_KEY,
            Value.valueOf(
              hashMapOf(
                "trunk" to Value.valueOf(
                  listOf(
                    Value.valueOf(mix(trunk.toDouble(), 0.0, -60.0)),
                    Value.valueOf(0.0),
                    Value.valueOf(0.0)
                  )
                )
              )
            ),
            onFeatureStateResult
          )
        }

        // Initialize brake light color once (constant value)
        MapEffect(Unit) { mapView ->
          mapView.mapboxMap.setFeatureState(
            SOURCE_ID,
            null,
            CAR_MODEL_KEY,
            Value.valueOf(hashMapOf("brake-light-color" to Value.valueOf("rgba(225, 0, 0, 1)"))),
            onFeatureStateResult
          )
        }
      }

      // Control panel at bottom
      Card(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Color picker
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("Vehicle color", fontSize = 14.sp)
            Box(
              modifier = Modifier
                .size(40.dp)
                .background(
                  androidx.compose.ui.graphics.Color(vehicleColor),
                  RoundedCornerShape(8.dp)
                )
                .border(
                  2.dp,
                  androidx.compose.ui.graphics.Color.Gray,
                  RoundedCornerShape(8.dp)
                )
                .clickable {
                  showColorDialog = true
                }
            )
          }

          // Trunk
          SliderControl("Trunk", trunk) { trunk = it }

          // Hood
          SliderControl("Hood", hood) { hood = it }

          // Left door
          SliderControl("Left door", doorsFrontLeft) { doorsFrontLeft = it }

          // Right door
          SliderControl("Right door", doorsFrontRight) { doorsFrontRight = it }

          // Brake lights
          SliderControl("Brake lights", brakeLights) { brakeLights = it }
        }
      }

      // Color picker dialog (simplified)
      if (showColorDialog) {
        ColorPickerDialog(
          onColorSelected = { color ->
            vehicleColor = color
            showColorDialog = false
          },
          onDismiss = { showColorDialog = false }
        )
      }
    }
  }
}

@Composable
private fun SliderControl(
  label: String,
  value: Float,
  onValueChange: (Float) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      fontSize = 12.sp,
      modifier = Modifier.size(80.dp, 24.dp)
    )
    Slider(
      value = value,
      onValueChange = onValueChange,
      modifier = Modifier.weight(1f)
    )
  }
}

@Composable
private fun ColorPickerDialog(
  onColorSelected: (Int) -> Unit,
  onDismiss: () -> Unit
) {
  val colors = listOf(
    Color.WHITE to "White",
    Color.BLACK to "Black",
    Color.RED to "Red",
    Color.rgb(0, 100, 200) to "Blue",
    Color.rgb(0, 150, 0) to "Green",
    Color.YELLOW to "Yellow",
    Color.rgb(150, 75, 0) to "Brown",
    Color.GRAY to "Gray"
  )

  androidx.compose.material.AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Vehicle Color") },
    buttons = {
      Column {
        colors.forEach { (color, name) ->
          Text(
            text = name,
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onColorSelected(color) }
              .padding(16.dp)
          )
        }
      }
    }
  )
}

private const val SOURCE_ID = "3d-model-source"
private const val LAYER_ID = "3d-model-layer"
private const val CAR_MODEL_KEY = "car"
private const val CAR_MODEL_URI = "https://docs.mapbox.com/mapbox-gl-js/assets/ego_car.glb"
private val CAR_POSITION: Point = Point.fromLngLat(-74.0135, 40.7153)