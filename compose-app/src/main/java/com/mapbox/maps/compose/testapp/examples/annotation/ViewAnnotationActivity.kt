package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.maps.*
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations.HELSINKI
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.viewannotation.annotationAnchors
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.util.*

/**
 * Example to showcase usage of MapboxMap.
 */
@OptIn(MapboxExperimental::class)
public class ViewAnnotationActivity : ComponentActivity() {
  private val random = Random()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var buttonColor by remember {
        mutableStateOf(Color.Blue)
      }
      var showViewAnnotation by remember {
        mutableStateOf(true)
      }
      val animatedColor by animateColorAsState(buttonColor, label = "ButtonAnnotationColor")
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            FloatingActionButton(
              onClick = {
                showViewAnnotation = !showViewAnnotation
              },
              shape = RoundedCornerShape(16.dp),
            ) {
              Text(modifier = Modifier.padding(10.dp), text = "Toggle Visibility")
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = MapViewportState().apply {
              setCameraOptions {
                zoom(ZOOM)
                center(HELSINKI)
              }
            }
          ) {
            if (showViewAnnotation) {
              ViewAnnotation(
                options = viewAnnotationOptions {
                  geometry(HELSINKI)
                  annotationAnchors(
                    {
                      anchor(ViewAnnotationAnchor.BOTTOM)
                    }
                  )
                  allowOverlap(false)
                }
              ) {
                Button(
                  onClick = {
                    buttonColor =
                      Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                    Toast.makeText(applicationContext, "Click", LENGTH_SHORT).show()
                  },
                  colors = ButtonDefaults.buttonColors(
                    backgroundColor = animatedColor
                  ),
                ) {
                  Text(
                    "Click me"
                  )
                }
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 9.0
  }
}