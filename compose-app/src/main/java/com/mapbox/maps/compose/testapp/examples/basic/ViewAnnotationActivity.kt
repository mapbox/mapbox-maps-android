package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mapbox.maps.*
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations.HELSINKI
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.internal.ViewAnnotation
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
      val animatedColor by animateColorAsState(buttonColor)
      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapInitOptionsFactory = { context ->
              MapInitOptions(
                context,
                cameraOptions = cameraOptions {
                  zoom(ZOOM)
                  center(HELSINKI)
                }
              )
            }
          ) {
            ViewAnnotation(
              options = viewAnnotationOptions {
                geometry(HELSINKI)
                anchor(ViewAnnotationAnchor.BOTTOM)
                allowOverlap(false)
              }
            ) {
              Button(
                onClick = {
                  buttonColor = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
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

  private companion object {
    const val ZOOM: Double = 9.0
  }
}