package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.atmosphere.generated.rememberAtmosphereState
import com.mapbox.maps.extension.compose.style.rememberColorTheme
import com.mapbox.maps.extension.compose.style.rememberStyleColorTheme
import com.mapbox.maps.extension.compose.style.rememberStyleState
import com.mapbox.maps.extension.style.color.colorTheme

/**
 * Example showcasing the usage of Color Theme API.
 * To create custom LUT, you can follow [this](https://docs.mapbox.com/help/tutorials/create-a-custom-color-theme/) guide.
 */
public class ColorThemeActivity : ComponentActivity() {

  @OptIn(MapboxExperimental::class, ExperimentalMaterialApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      // creating and style color theme using base64 encoded LUT image
      val base64ColorTheme = rememberColorTheme(base64 = BASE64_ENCODED_RED_THEME)
      // creating monochrome StyleColorTheme from a drawable LUT resource
      val monochromeColorTheme = rememberColorTheme(resourceId = R.drawable.monochrome_lut)
      // holder for current color theme
      var currentColorTheme by remember {
        mutableStateOf(base64ColorTheme)
      }
      val currentStyleColorTheme = rememberStyleColorTheme(currentColorTheme)
      var atmosphereUseTheme by remember {
        mutableStateOf(true)
      }
      val initialAtmosphereState = rememberAtmosphereState {
        color = ColorValue(Color.Green)
      }
      val currentAtmosphereState by remember {
        mutableStateOf(initialAtmosphereState)
      }

      // When state is toggled, update atmosphere state with correct string value.
      // Setting it to "none" means that color theme will not affect atmosphere.
      currentAtmosphereState.colorUseTheme = StringValue(
        if (atmosphereUseTheme) "default" else "none"
      )

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentColorTheme = monochromeColorTheme
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Monochrome")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentColorTheme = base64ColorTheme
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Red")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  // creating empty style color theme which will reset color theme when set to color theme state.
                  currentColorTheme = colorTheme()
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Reset")
              }

              FilterChip(
                onClick = {
                  atmosphereUseTheme = !atmosphereUseTheme
                },
                content = {
                  Text("Use theme color for Atmosphere")
                },
                selected = atmosphereUseTheme,
                leadingIcon = if (atmosphereUseTheme) {
                  {
                    Icon(
                      imageVector = Icons.Filled.Done,
                      contentDescription = null,
                    )
                  }
                } else {
                  null
                },
              )
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions(START_CAMERA_POSITION)
            },
            style = {
              GenericStyle(
                style = Style.MAPBOX_STREETS,
                styleState = rememberStyleState {
                  styleColorTheme = currentStyleColorTheme
                  atmosphereState = currentAtmosphereState
                },
              )
            }
          )
        }
      }
    }
  }

  public companion object {
    /**
     * Base64 encoded version of a custom LUT (Look-Up Table) image.
     * LUT image can be created by following [this](https://docs.mapbox.com/help/tutorials/create-a-custom-color-theme/) guide.
     *
     * To convert your LUT image to a Base64 string, you can use an online tool or a script.
     * For example, you can use the following command in a terminal:
     *
     * ```sh
     * base64 -i path/to/your/lut-image.png -o output.txt
     * ```
     */
    private const val BASE64_ENCODED_RED_THEME =
      "iVBORw0KGgoAAAANSUhEUgAABAAAAAAgCAYAAACM/gqmAAAAAXNSR0IArs4c6QAABSFJREFUeF7t3cFO40AQAFHnBv//wSAEEgmJPeUDsid5h9VqtcMiZsfdPdXVzmVZlo+3ZVm+fr3//L7257Lm778x+prL1ff0/b//H+z/4/M4OkuP/n70Nc7f+nnb+yzb//sY6vxt5xXPn+dP/aH+GsXJekb25izxR/ypZ6ucUefv9g4z2jPP3/HPHwAAgABAABgACIACkAAsAL1SD4yKWQAUAHUBdAG8buKNYoYL8PEX4FcHQAAAAAAAAAAAAAAAAAAAAAAA8LAeGF1mABAABAABQACQbZP7+hk5AwACAAAAAAAAAAAAAAAAAAAAAAAA4EE9AICMx4QBAAAAAAAANgvJsxGQV1dA/PxmMEtxU9YoABQACoC5CgDxX/wvsb2sEf/Ff/Ff/N96l5n73+/5YAB4CeBqx2VvMqXgUfD2npkzBCAXEBeQcrkoa5x/FxAXEBcQF5A2Wy3/t32qNYr8I//Mln+MABgBMAJgBMAIgBEAIwBGAIwAGAEwAmAE4K4eAGCNQIw+qQ0AmQ+AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB/6gEABAB5RgACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAN/UAAPKcAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgEFNODICRtDkDO/gAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOhvlPUWem+h9xKQ+V4CUt9wO6KZnn/Pv+ff8z/bW5DFP59CUnJbWSP+iX/iX78znqED/urxnwHAAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABgADoNMcHUAdQAQcAUfAe8xEwH0O86t3IPz8OvClu17WqD/UH+oP9cf1Gdia01d/LQsDgAHAAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABkCnSQwABgACj8Aj8D1mItAMAB1wHfDS3S5r5F/5V/6Vf3XAW12h/mIArHY89iZTAAQA2XtmBKAWqOslyf4rgBXACmAFcIur8k/bJ/mnQTr5V/6Vf+fKv0YAjAAYATACYATACIARACMARgCMABgBMAJgBMAIgBEAIwCdZuiA64AjwAgwAtxjpg6cDlztLlLA7/Pr1gueyr56/jx/5ZzUNeof9Y/6R/0zk4HGAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABgADgAHQaQ4DgAGAgCPgCHiPmTqQOpC1u8gAYACMjAf5V/6Vf+XfmTrQ8l97v8Z/5X8GAAOAAcAAYAAwABgADAAGAAOAAcAAYAAwABgADIBO0xgADAAdCB0IHYgeMxkADAAdkGM7IPbf/pfuWlmj/lH/qH/UPzMZGAwABgADgAHAAGAAMAAYAAwABgADgAHAAGAAMAAYAJ3mMAAYAAg4Ao6A95jJAGAA6EDrQJfuclkj/8q/8q/8O1MHWv47Nv8xABgADAAGAAOAAcAAYAAwABgADAAGAAOAAcAAYAB0msYAYADoQOhA6ED0mMkAYADogBzbAbH/9r/YFWWN+kf9o/5R/8xkYDAAGAAMAAYAA4ABwABgADAAGAAMAAYAA4ABwABgAHSawwBgACDgCDgC3mMmA4ABoAOtA126y2WN/Cv/yr/y70wdaPnv2PzHAGAAMAAYAAwABgADgAHAAGAAMAAYAAwABgADgAHQaRoDgAGgA6EDoQPRYyYDgAGgA3JsB8T+2/9iV5Q16h/1j/pH/TOTgcEAYAAwABgADAAGAAOAAcAAYAAwABgADAAGAAPgyQ2AT4NBIB3ew5dkAAAAAElFTkSuQmCC"
    private const val LATITUDE = 40.72
    private const val LONGITUDE = -73.99
    private val CENTER = Point.fromLngLat(LONGITUDE, LATITUDE)
    private val START_CAMERA_POSITION = cameraOptions {
      center(CENTER)
      zoom(2.0)
      pitch(45.0)
    }
  }
}