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
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.precipitations.generated.RainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.SnowState
import com.mapbox.maps.extension.compose.style.precipitations.generated.rememberRainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.rememberSnowState
import com.mapbox.maps.extension.compose.style.rememberStyleState

/**
 * Example to showcase usage of [RainState] and [SnowState].
 */
@OptIn(MapboxExperimental::class)
public class PrecipitationsActivity : ComponentActivity() {

  /**
   * Describes the heaviness of precipitation.
   */
  public sealed class PrecipitationState(
    public val intensity: Double,
    public val density: Double,
    public val opacity: Double,
    public val text: String
  ) {
    public object None : PrecipitationState(intensity = 0.0, density = 0.0, opacity = 0.0, text = "no")

    public object Light : PrecipitationState(intensity = 0.2, density = 0.2, opacity = 0.3, text = "light")

    public object Medium : PrecipitationState(intensity = 0.6, density = 0.6, opacity = 0.5, text = "medium")

    public object Heavy : PrecipitationState(intensity = 1.0, density = 1.0, opacity = 0.8, text = "heavy")

    public fun toggleNext(): PrecipitationState {
      return when (this) {
        Heavy -> None
        None -> Light
        Light -> Medium
        Medium -> Heavy
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      var snowPrecipitationState: PrecipitationState by remember {
        mutableStateOf(PrecipitationState.Light)
      }

      var rainPrecipitationState: PrecipitationState by remember {
        mutableStateOf(PrecipitationState.Light)
      }

      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          pitch(PITCH)
          bearing(BEARING)
          center(CityLocations.HELSINKI)
        }
      }

      val rainState = rememberRainState().also {
        it.opacity = DoubleValue(rainPrecipitationState.opacity)
        it.intensity = DoubleValue(rainPrecipitationState.intensity)
        it.density = DoubleValue(rainPrecipitationState.density)
      }

      val snowState = rememberSnowState().also {
        it.opacity = DoubleValue(snowPrecipitationState.opacity)
        it.intensity = DoubleValue(snowPrecipitationState.intensity)
        it.density = DoubleValue(snowPrecipitationState.density)
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  rainPrecipitationState = rainPrecipitationState.toggleNext()
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "${rainPrecipitationState.text} rain"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  snowPrecipitationState = snowPrecipitationState.toggleNext()
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "${snowPrecipitationState.text} snow"
                )
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = {
              MapStyle(
                style = Style.STANDARD,
                styleState = rememberStyleState().apply {
                  this.rainState =
                    if (rainPrecipitationState == PrecipitationState.None) RainState.DISABLED else rainState
                  this.snowState =
                    if (snowPrecipitationState == PrecipitationState.None) SnowState.DISABLED else snowState
                }
              )
            }
          )
        }
      }
    }
  }

  private companion object {
    private const val ZOOM: Double = 16.0
    private const val PITCH: Double = 40.0
    private const val BEARING: Double = 70.0
  }
}