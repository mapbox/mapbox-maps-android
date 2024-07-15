package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.atmosphere.generated.rememberAtmosphereState
import com.mapbox.maps.extension.compose.style.lights.LightsState
import com.mapbox.maps.extension.compose.style.lights.generated.rememberAmbientLightState
import com.mapbox.maps.extension.compose.style.lights.generated.rememberDirectionalLightState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import kotlin.math.round
import kotlin.random.Random

/**
 * Example to showcase usage of [AtmosphereState] and [Projection].
 */
public class StyleStatesActivity : ComponentActivity() {

  private val projections =
  // Standard style default projection is GLOBE so we make sure that MERCATOR is before and after
    // default so the map changes visually.
    listOf(Projection.DEFAULT, Projection.MERCATOR, Projection.GLOBE, Projection.MERCATOR)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {

      var starIntensityIncrease by remember {
        mutableStateOf(true)
      }

      var currentProjectionIdx by rememberSaveable {
        mutableStateOf(0)
      }

      var currentLightIdx by rememberSaveable {
        mutableStateOf(0)
      }

      var currentLightPresetIdx by rememberSaveable {
        mutableStateOf(0)
      }

      val initialAtmosphereState = rememberAtmosphereState {
        spaceColor = ColorValue(Color.Black)
        starIntensity = DoubleValue(0.8)
        horizonBlend = DoubleValue(0.01)
      }
      val anotherAtmosphereState = rememberAtmosphereState {
        spaceColor = randomColor()
        horizonBlend = DoubleValue(0.2)
        starIntensity = DoubleValue(0.0)
      }

      var currentAtmosphereState by remember {
        mutableStateOf(initialAtmosphereState)
      }

      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }

      val directionalLightState = rememberDirectionalLightState {
        color = ColorValue(Color.Yellow)
        intensity = DoubleValue(0.9)
        castShadows = BooleanValue(true)
        direction = DoubleListValue(0.0, 15.0)
      }
      val ambientLightState = rememberAmbientLightState {
        color = ColorValue(Color.White)
        intensity = DoubleValue(0.5)
      }

      val dynamicLight = remember {
        LightsState(directionalLightState, ambientLightState)
      }

      val lightsStates = remember {
        listOf(LightsState.DEFAULT, dynamicLight)
      }
      val lightsStatesNames = remember {
        listOf("default", "3d light")
      }
      val lightsPresets = remember {
        listOf(LightPresetValue.DAY, LightPresetValue.NIGHT)
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  var starIntensity = currentAtmosphereState.starIntensity.doubleOrNull!!
                  if (starIntensityIncrease) {
                    starIntensity += 0.1
                  } else {
                    starIntensity -= 0.1
                  }
                  starIntensity = (round(starIntensity * 100)) / 100.0
                  if (starIntensity >= 1.0) {
                    starIntensityIncrease = false
                  }
                  if (starIntensity <= 0.0) {
                    starIntensityIncrease = true
                  }
                  currentAtmosphereState.starIntensity = DoubleValue(starIntensity)
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                val starIntensity = currentAtmosphereState.starIntensity.doubleOrNull!!
                val action = if (starIntensityIncrease) "Increase" else "Decrease"
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "$action star intensity ($starIntensity)"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentAtmosphereState.spaceColor = randomColor()
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Change atmosphere to random color")
              }

              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentLightIdx = (currentLightIdx + 1) % lightsStates.size
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Change Light to ${lightsStatesNames[(currentLightIdx + 1) % lightsStates.size]}")
              }
              val enableLightColorBtn = lightsStates[currentLightIdx] != LightsState.DEFAULT
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (enableLightColorBtn) MaterialTheme.colors.secondary else Color.LightGray,
                onClick = {
                  if (enableLightColorBtn) {
                    directionalLightState.color = randomColor()
                    ambientLightState.color = randomColor()
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Change Light to random color")
              }
              val enableLightPresetBtn = lightsStates[currentLightIdx] == LightsState.DEFAULT
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (enableLightPresetBtn) MaterialTheme.colors.secondary else Color.LightGray,
                onClick = {
                  if (enableLightPresetBtn) {
                    currentLightPresetIdx = (currentLightPresetIdx + 1) % lightsPresets.size
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Change Light preset to ${lightsPresets[(currentLightPresetIdx + 1) % lightsPresets.size].presetNameOrNull}")
              }
              val enableProjectionBt = (mapViewportState.cameraState?.zoom ?: Double.NaN) < 6.0
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (enableProjectionBt) MaterialTheme.colors.secondary else Color.LightGray,
                onClick = {
                  if (enableProjectionBt) {
                    currentProjectionIdx = (currentProjectionIdx + 1) % projections.size
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                val nextProjectionName =
                  projections[(currentProjectionIdx + 1) % projections.size].projectionNameOrNull
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Change projection to $nextProjectionName"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentAtmosphereState = if (currentAtmosphereState == initialAtmosphereState)
                    anotherAtmosphereState
                  else
                    initialAtmosphereState
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Switch Atmosphere"
                )
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = {
              MapboxStandardStyle(
                projection = projections[currentProjectionIdx],
                atmosphereState = currentAtmosphereState,
                lightsState = lightsStates[currentLightIdx],
              ) {
                lightPreset = lightsPresets[currentLightPresetIdx]
              }
            }
          )
        }
      }
    }
  }

  private fun randomColor() = ColorValue(
    Color(
      Random.nextInt(255),
      Random.nextInt(255),
      Random.nextInt(255),
    )
  )

  private companion object {
    private const val ZOOM: Double = 1.0
  }
}