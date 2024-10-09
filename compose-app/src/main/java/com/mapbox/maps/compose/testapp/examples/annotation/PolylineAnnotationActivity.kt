package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mapbox.geojson.Point
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.AnnotationUtils
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotationGroup
import com.mapbox.maps.extension.compose.annotation.generated.withLineColor
import com.mapbox.maps.logD
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import java.util.Random

/**
 * Example to showcase usage of PolylineAnnotation with Jetpack Compose.
 */
public class PolylineAnnotationActivity : ComponentActivity() {
  private val random = Random()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          val mapViewportState = rememberMapViewportState {
            setCameraOptions {
              center(CityLocations.NULLISLAND)
              zoom(3.0)
              pitch(0.0)
              bearing(0.0)
            }
          }
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState
          ) {
            PolylineAnnotation(
              points = POLYLINE_POINTS,
            ) {
              interactionsState.onClicked {
                Toast.makeText(
                  this@PolylineAnnotationActivity,
                  "Clicked on Polygon Annotation: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
                .onLongClicked {
                  Toast.makeText(
                    this@PolylineAnnotationActivity,
                    "Long Clicked on Polygon Annotation: $it",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
                .onDragged {
                  logD(
                    this.javaClass.simpleName,
                    "Dragging Polyline Annotation: $it"
                  )
                }.also { it.isDraggable = true }
              lineColor = Color.Red
              lineWidth = 5.0
            }
            PolylineAnnotationGroup(
              annotations = mutableListOf<PolylineAnnotationOptions>().apply {
                repeat(100) {
                  add(
                    PolylineAnnotationOptions()
                      .withPoints(AnnotationUtils.createRandomPoints())
                      .withLineColor(
                        Color(
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