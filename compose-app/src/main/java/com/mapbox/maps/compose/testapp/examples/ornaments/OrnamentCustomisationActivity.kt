package com.mapbox.maps.compose.testapp.examples.ornaments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import java.util.Random

/**
 * Example to showcase usage of ornaments customisation.
 */
@OptIn(MapboxExperimental::class)
public class OrnamentCustomisationActivity : ComponentActivity() {
  private val random = Random()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var showScaleBar by remember {
        mutableStateOf(true)
      }
      var color by remember {
        mutableStateOf(Color.Blue)
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier
                  .padding(bottom = 10.dp)
                  .align(Alignment.End),
                onClick = {
                  showScaleBar = !showScaleBar
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle ScaleBar Visibility"
                )
              }

              FloatingActionButton(
                modifier = Modifier
                  .padding(bottom = 10.dp)
                  .align(Alignment.End),
                onClick = {
                  color = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle Color"
                )
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            compass = {
              // Add a default Compass at default position.
              Compass()
              // Add another custom Compass.
              Compass(
                modifier = Modifier.padding(0.dp),
                alignment = Alignment.CenterEnd,
              ) {
                Image(
                  painter = painterResource(id = R.drawable.mapbox_user_puck_icon),
                  alpha = 0.9f,
                  modifier = Modifier
                    .height(55.dp)
                    .width(55.dp),
                  contentDescription = "My customised compass",
                  colorFilter = ColorFilter.lighting(
                    multiply = Color.White,
                    add = color
                  )
                )
              }
            },
            scaleBar = {
              // Toggle ScaleBar visibility based on a mutable state.
              if (showScaleBar) {
                ScaleBar(
                  primaryColor = color
                )
              }
            },
            attribution = {
              Attribution(iconColor = color)
            },
            mapViewportState = MapViewportState().apply {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            }
          )
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 9.0
  }
}