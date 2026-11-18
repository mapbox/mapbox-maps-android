package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations.HELSINKI
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.debugoptions.MapViewDebugOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.Marker
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.mbxViewAnnotationCollisionBox
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Shows which parts of a compose view annotation take part in collision.
 *
 * The collision debug overlay is on, so the map draws a red frame around every rectangle it was
 * given. Use the button to switch marking on and off:
 *
 * - marked: one frame around the pin and one around the label, so only those two count;
 * - not marked: one frame around the whole annotation, which is far bigger than what is drawn.
 *
 * The [Marker] to the east marks its own pin and text, so it always shows two frames.
 *
 * This mirrors the view-based `ViewAnnotationCollisionActivity` in the `app` module.
 */
@OptIn(MapboxExperimental::class)
public class ViewAnnotationCollisionActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var marked by remember { mutableStateOf(true) }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            FloatingActionButton(
              modifier = Modifier.padding(bottom = 10.dp),
              onClick = { marked = !marked },
              shape = RoundedCornerShape(16.dp),
            ) {
              Text(
                modifier = Modifier.padding(10.dp),
                text = if (marked) "Marked parts" else "Whole annotation"
              )
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(HELSINKI)
              }
            }
          ) {
            // Draw a red frame around every rectangle the map was given for an annotation.
            MapEffect(Unit) { mapView ->
              mapView.debugOptions = setOf(MapViewDebugOptions.COLLISION)
            }

            ViewAnnotation(
              options = viewAnnotationOptions {
                geometry(HELSINKI)
                enableSymbolLayerCollision(true)
                annotationAnchor { anchor(ViewAnnotationAnchor.BOTTOM) }
              }
            ) {
              Pin(marked = marked)
            }

            Marker(point = MARKER_POINT, text = "Marker")
          }
        }
      }
    }
  }

  /**
   * A pin and a label inside a deliberately oversized box, so the difference between "the whole
   * annotation" and "only the marked parts" is easy to see on screen.
   */
  @Composable
  private fun Pin(marked: Boolean) {
    Box(
      modifier = Modifier.size(180.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Box(
          modifier = Modifier
            .mbxViewAnnotationCollisionBox(enabled = marked)
            .size(28.dp)
            .background(Color(0xFF3A59FA), CircleShape)
        )
        Text(
          modifier = Modifier
            .mbxViewAnnotationCollisionBox(enabled = marked)
            .padding(top = 4.dp)
            .background(Color.White),
          text = "Helsinki",
          fontSize = 14.sp,
          color = Color.Black
        )
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 14.0
    val MARKER_POINT: Point = Point.fromLngLat(24.946, 60.169)
  }
}