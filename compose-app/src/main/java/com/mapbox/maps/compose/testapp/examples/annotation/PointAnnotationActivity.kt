package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.logD

/**
 * Example to showcase usage of point annotations with text and icon image.
 */
public class PointAnnotationActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      var text by remember {
        mutableStateOf("Hello!")
      }

      var markerResourceId by remember {
        mutableStateOf(R.drawable.ic_blue_marker)
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  text = "$text!"
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Update text field")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  markerResourceId =
                    if (markerResourceId == R.drawable.ic_red_marker) R.drawable.ic_blue_marker else R.drawable.ic_red_marker
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Update icon image")
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            },
            style = {
              MapStyle(style = Style.LIGHT)
            }
          ) {
            val marker =
              rememberIconImage(key = markerResourceId, painter = painterResource(markerResourceId))
            PointAnnotation(point = CityLocations.HELSINKI) {
              iconImage = marker
              textField = text
              interactionsState.onClicked {
                Toast.makeText(this@PointAnnotationActivity, "First", Toast.LENGTH_SHORT).show()
                interactionsState.onClicked {
                  Toast.makeText(this@PointAnnotationActivity, "Second", Toast.LENGTH_SHORT).show()
                  true
                }
                true
              }
                .onDragged {
                  logD(
                    this.javaClass.simpleName,
                    "onDragged"
                  )
                }.also { it.isDraggable = true }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 10.0
  }
}