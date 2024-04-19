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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.atmosphere.generated.SpaceColor
import com.mapbox.maps.extension.compose.style.atmosphere.generated.StarIntensity
import com.mapbox.maps.extension.compose.style.atmosphere.generated.rememberAtmosphereState
import com.mapbox.maps.extension.compose.style.projection.Projection
import kotlin.random.Random

/**
 * Example to showcase usage of [AtmosphereState] and [Projection].
 */
@OptIn(MapboxExperimental::class)
public class StyleStatesActivity : ComponentActivity() {

  private val projections =
    // Standard style default projection is GLOBE so we make sure that MERCATOR is before and after
    // default so the map changes visually.
    listOf(Projection.default, Projection.MERCATOR, Projection.GLOBE, Projection.MERCATOR)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {

      var starIntensityIncrease by remember {
        mutableStateOf(true)
      }

      var currentProjectionIdx by remember {
        mutableStateOf(0)
      }

      val initialAtmosphereState = rememberAtmosphereState()
      val anotherAtmosphereState = rememberAtmosphereState {
        this.spaceColor = SpaceColor.default
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

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  var starIntensity = currentAtmosphereState.starIntensity.value.contents as Double? ?: 0.0
                  if (starIntensityIncrease) {
                    starIntensity += 0.1
                  } else {
                    starIntensity -= 0.1
                  }
                  if (starIntensity >= 1.0) {
                    starIntensityIncrease = false
                  }
                  if (starIntensity <= 0.0) {
                    starIntensityIncrease = true
                  }
                  currentAtmosphereState.starIntensity = StarIntensity(starIntensity)
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "${if (starIntensityIncrease) { "Increase" } else { "Decrease" }} star intensity"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentAtmosphereState.spaceColor = SpaceColor(
                    Color(
                      Random.nextInt(255),
                      Random.nextInt(255),
                      Random.nextInt(255),
                    )
                  )
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Change to random color")
              }
              val enableProjectionBt = mapViewportState.cameraState.zoom < 6.0
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
                  projections[(currentProjectionIdx + 1) % projections.size].friendlyName()
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
              GenericStyle(
                style = Style.STANDARD,
                projection = projections[currentProjectionIdx],
                atmosphereState = currentAtmosphereState
              )
            }
          )
        }
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  private fun Projection.friendlyName(): String {
    return when (this) {
      Projection.GLOBE -> "globe"

      Projection.MERCATOR -> "mercator"

      Projection.default -> "default"

      else -> "Unknown"
    }
  }

  private companion object {
    private const val ZOOM: Double = 1.0
  }
}