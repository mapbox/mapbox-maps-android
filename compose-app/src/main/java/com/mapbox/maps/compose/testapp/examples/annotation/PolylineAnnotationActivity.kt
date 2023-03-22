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
import com.mapbox.maps.compose.testapp.examples.utils.AnnotationUtils
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotationCluster
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import java.util.*

/**
 * Example to showcase usage of PolylineAnnotation with Jetpack Compose.
 */
@OptIn(MapboxExperimental::class)
public class PolylineAnnotationActivity : ComponentActivity() {
  private val random = Random()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize()
          ) {
            PolylineAnnotation(
              points = POLYLINE_POINTS,
              lineColorInt = Color.RED,
              lineWidth = 5.0,
              onClick = {
                Toast.makeText(
                  this@PolylineAnnotationActivity,
                  "Clicked on Polygon Annotation: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
            )
            PolylineAnnotationCluster(
              annotations = mutableListOf<PolylineAnnotationOptions>().apply {
                repeat(100) {
                  add(
                    PolylineAnnotationOptions()
                      .withPoints(AnnotationUtils.createRandomPoints())
                      .withLineColor(
                        Color.rgb(
                          random.nextInt(256),
                          random.nextInt(256),
                          random.nextInt(256),
                        )
                      )
                  )
                }
              },
              annotationConfig = AnnotationConfig(
                PITCH_OUTLINE, LAYER_ID, SOURCE_ID
              )
            )
          }
        }
      }
    }
  }

  private companion object {
    val POLYLINE_POINTS = listOf(
      Point.fromLngLat(-4.375974, -2.178992),
      Point.fromLngLat(-7.639772, -4.107888),
      Point.fromLngLat(-11.439207, 2.798737),
    )
    private const val LAYER_ID = "line_layer"
    private const val SOURCE_ID = "line_source"
    private const val PITCH_OUTLINE = "pitch-outline"
  }
}