package com.mapbox.maps.compose.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation

/**
 * Example to showcase usage of PolygonAnnotation with Jetpack Compose.
 */
@OptIn(MapboxExperimental::class)
public class PolygonAnnotationActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapInitOptionsFactory = { context ->
              MapInitOptions(
                context,
                cameraOptions = cameraOptions {
                  zoom(ZOOM)
                  center(CAMERA_CENTER)
                },
                styleUri = Style.LIGHT
              )
            }
          ) {
            PolygonAnnotation(
              points = POLYGON_POINTS,
              fillColorInt = Color.RED,
              onClick = {
                Toast.makeText(
                  this@PolygonAnnotationActivity,
                  "Clicked on Polygon Annotation: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
            )
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