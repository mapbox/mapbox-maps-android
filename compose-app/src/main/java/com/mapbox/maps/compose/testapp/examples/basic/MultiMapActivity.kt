package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Example to showcase usage of multiple of MapboxMap in the same screen.
 */
@OptIn(MapboxExperimental::class)
public class MultiMapActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var viewAnnotationList1 by remember { mutableStateOf(emptyList<Point>()) }
      var viewAnnotationList2 by remember { mutableStateOf(emptyList<Point>()) }

      LaunchedEffect(Unit) {
        launch {
          delay(1000L)
          viewAnnotationList1 = points1
          viewAnnotationList2 = points1
        }
        launch {
          delay(2000L)
          viewAnnotationList2 = points1 + points2
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold {
          Column(modifier = Modifier.padding(it)) {
            MapboxMap(
              modifier = Modifier
                  .weight(1f)
                  .fillMaxWidth(),
              mapViewportState = MapViewportState().apply {
                setCameraOptions {
                  zoom(ZOOM)
                  center(CityLocations.HELSINKI)
                }
              },
              style = {
                MapStyle(style = Style.DARK)
              }
            ) {
              viewAnnotationList1.forEachIndexed { index, point ->
                ViewAnnotation(
                  options = viewAnnotationOptions {
                    geometry(point)
                    allowOverlap(true)
                  }
                ) {
                  Text(text = "Annotation $index", color = Color.Red)
                }
              }
            }
            MapboxMap(
              modifier = Modifier
                  .weight(1f)
                  .fillMaxWidth(),
              mapViewportState = MapViewportState().apply {
                setCameraOptions {
                  zoom(ZOOM)
                  center(CityLocations.HELSINKI)
                }
              },
              style = {
                MapStyle(style = Style.LIGHT)
              }
            ) {
              viewAnnotationList2.forEachIndexed { index, point ->
                ViewAnnotation(
                  options = viewAnnotationOptions {
                    geometry(point)
                    allowOverlap(true)
                  }
                ) {
                  Text(text = "Annotation $index", color = Color.Blue)
                }
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 13.0
    val points1 = listOf(
      Point.fromLngLat(24.94216010242652, 60.16876757234266),
      Point.fromLngLat(24.929766009141733, 60.170292490574944),
    )
    val points2 = listOf(
      Point.fromLngLat(24.947481155604, 60.1731440090149),
      Point.fromLngLat(24.937481155604, 60.1631440090149)
    )
  }
}