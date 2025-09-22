package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle

/**
 * Example to showcase usage of PolygonAnnotation with Jetpack Compose.
 */
public class PolygonAnnotationActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var color by remember {
        mutableStateOf(Color.Red)
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            FloatingActionButton(
              modifier = Modifier.padding(bottom = 10.dp),
              onClick = {
                color = if (color == Color.Red) Color.Blue else Color.Red
              },
              shape = RoundedCornerShape(16.dp),
            ) {
              Text(modifier = Modifier.padding(10.dp), text = "Toggle color")
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CAMERA_CENTER)
              }
            },
            style = {
              MapStyle(style = Style.LIGHT)
            },
          ) {
            PolygonAnnotation(
              points = POLYGON_POINTS,
            ) {
              interactionsState.isDraggable = true
              interactionsState.onClicked {
                Toast.makeText(
                  this@PolygonAnnotationActivity,
                  "Clicked on Polygon Annotation: $it",
                  Toast.LENGTH_SHORT
                ).show()
                interactionsState.isDraggable = false
                true
              }
                .onLongClicked {
                  Toast.makeText(
                    this@PolygonAnnotationActivity,
                    "Long Clicked on Polygon Annotation: $it",
                    Toast.LENGTH_SHORT
                  ).show()
                  interactionsState.isDraggable = true
                  true
                }
              fillColor = color
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 5.0
    val CAMERA_CENTER: Point = Point.fromLngLat(-88.90136, 25.04579)
    val POLYGON_POINTS = listOf(
      listOf(
        Point.fromLngLat(-89.857177734375, 24.51713945052515),
        Point.fromLngLat(-87.967529296875, 24.51713945052515),
        Point.fromLngLat(-87.967529296875, 26.244156283890756),
        Point.fromLngLat(-89.857177734375, 26.244156283890756),
        Point.fromLngLat(-89.857177734375, 24.51713945052515)
      )
    )
  }
}