package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.debugoptions.MapViewDebugOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.mbxViewAnnotationCollisionBox
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Shows that marking, moving, hiding and removing parts of a compose view annotation all update
 * its collision boxes on their own, with no call from the app.
 *
 * This is the compose counterpart of the view based `ViewAnnotationCollisionObservingActivity`
 * in the `app` module. Same pin, same grid, same camera and the same switches in the same order,
 * so the two can be put side by side and compared.
 *
 * An active collision box shows up as a hole in the grid of crosses, because the annotation is
 * placed first and the crosses inside its boxes are suppressed. The collision debug overlay is
 * also on, so the map draws a red frame around every rectangle it was given.
 *
 * One switch means something a little different here. On the view based screen, `View.GONE`
 * takes the red circle out of layout, so it stops colliding and also disappears. A composable
 * has no such state: it is either in the composition or it is not, and not composing it is what
 * "Remove red box" already does. The compose way to stop something colliding while leaving it on
 * screen is to turn the modifier off, so that is what this switch does, and the circle stays
 * where it is. The result on the map is the same as removing it, by a different route.
 */
@OptIn(MapboxExperimental::class)
public class ViewAnnotationCollisionObservingActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val blueMarked = remember { mutableStateOf(false) }
      val redShifted = remember { mutableStateOf(false) }
      val redInvisible = remember { mutableStateOf(false) }
      val redRemoved = remember { mutableStateOf(false) }
      val redUnmarked = remember { mutableStateOf(false) }
      val avoidRegion = remember { mutableStateOf(false) }
      // Where the red circle was when the region switch went on. Pinned, so the region stays
      // put afterwards and shifting the circle is what moves it in or out. The view based
      // screen does the same: it measures the red circle once, as the switch flips.
      val regionShift = remember(avoidRegion.value) { if (redShifted.value) SHIFT_DP else 0 }
      MapboxMapComposeTheme {
        ExampleScaffold {
          Box(Modifier.fillMaxSize()) {
            MapboxMap(
              Modifier.fillMaxSize(),
              mapViewportState = rememberMapViewportState {
                setCameraOptions {
                  zoom(ZOOM)
                  center(CENTER)
                }
              },
              // A blank style covered in crosses. An active collision box shows up as a hole in
              // the grid, which is far easier to read than watching city labels.
              style = { MapStyle(style = remember { gridStyleJson() }) }
            ) {
              MapEffect(avoidRegion.value) { mapView ->
                mapView.debugOptions = setOf(MapViewDebugOptions.COLLISION)
                mapView.viewAnnotationManager.viewAnnotationAvoidRegions =
                  if (avoidRegion.value) {
                    val density = resources.displayMetrics.density
                    val cx = mapView.width / 2.0
                    val cy = mapView.height / 2.0
                    val top = cy + (RED_BOTTOM_DP + regionShift + REGION_GAP_DP) * density
                    listOf(
                      ScreenBox(
                        ScreenCoordinate(cx + RED_LEFT_DP * density, top),
                        ScreenCoordinate(
                          cx + RED_RIGHT_DP * density,
                          top + REGION_HEIGHT_DP * density
                        )
                      )
                    )
                  } else {
                    emptyList()
                  }
              }

              ViewAnnotation(
                options = viewAnnotationOptions {
                  geometry(CENTER)
                  // The annotation is placed before the style's own symbols, so the crosses
                  // inside its collision boxes are suppressed and leave a hole in the grid.
                  enableSymbolLayerCollision(true)
                  // Without this, symbol layer collision gives the annotation priority and it
                  // never hides when it lands in an avoid region.
                  enableAvoidRegions(true)
                  annotationAnchor { anchor(ViewAnnotationAnchor.CENTER) }
                }
              ) {
                TwoBoxPin(
                  blueMarked = blueMarked.value,
                  redShifted = redShifted.value,
                  redInvisible = redInvisible.value,
                  redRemoved = redRemoved.value,
                  redUnmarked = redUnmarked.value
                )
              }
            }

            // The avoid region itself draws nothing, so show where it is. Only an aid for
            // reading the screen; it takes no part in collision.
            if (avoidRegion.value) {
              Box(
                Modifier
                  .align(Alignment.Center)
                  .offset(
                    x = ((RED_LEFT_DP + RED_RIGHT_DP) / 2).dp,
                    y = (RED_BOTTOM_DP + regionShift + REGION_GAP_DP + REGION_HEIGHT_DP / 2).dp
                  )
                  .width((RED_RIGHT_DP - RED_LEFT_DP).dp)
                  .height(REGION_HEIGHT_DP.dp)
                  .background(Color(0x55FF0000))
              )
            }

            // Same control panel as the view based screen, value for value.
            Column(
              Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                .background(Color(PANEL_COLOR), RoundedCornerShape(PANEL_CORNER_DP.dp))
                .padding(horizontal = PANEL_PADDING_DP.dp, vertical = PANEL_PADDING_DP.dp / 2)
            ) {
              Toggle("Mark blue as collision box", blueMarked)
              Toggle("Shift red box down", redShifted)
              Toggle("Red box invisible (still collides)", redInvisible)
              Toggle("Remove red box (last box → full bounds)", redRemoved)
              Toggle("Red box stops colliding (stays visible)", redUnmarked)
              Toggle("Avoid region below red box", avoidRegion)
            }
          }
        }
      }
    }
  }

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  private fun Toggle(label: String, state: MutableState<Boolean>) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = ROW_PADDING_DP.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = label,
        modifier = Modifier.weight(1f),
        fontSize = ROW_TEXT_SP.sp,
        color = Color.White
      )
      // A switch reserves a 48dp touch target of its own, which would make every row twice as
      // tall as the view based screen's rows.
      CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        Switch(
          checked = state.value,
          onCheckedChange = { state.value = it },
          colors = SwitchDefaults.colors(uncheckedThumbColor = Color.White)
        )
      }
    }
  }

  /**
   * Two circles side by side, 110x50 with the red one and 50x50 without it, matching the view
   * based screen's pin and the iOS example it in turn mirrors. Only the red circle is marked to
   * begin with.
   */
  @Composable
  private fun TwoBoxPin(
    blueMarked: Boolean,
    redShifted: Boolean,
    redInvisible: Boolean,
    redRemoved: Boolean,
    redUnmarked: Boolean
  ) {
    Row(
      modifier = Modifier.padding(PIN_PADDING_DP.dp),
      horizontalArrangement = Arrangement.spacedBy(CIRCLE_DP.dp)
    ) {
      if (!redRemoved) {
        Circle(
          color = Color(0xFFFF3B30),
          modifier = Modifier
            // A real move: the circle is placed somewhere else, so the box follows it.
            .offset(y = if (redShifted) SHIFT_DP.dp else 0.dp)
            // Alpha leaves layout untouched, so the circle still collides while invisible.
            .alpha(if (redInvisible) 0f else 1f)
            // Turning the flag off withdraws the box and leaves the circle on screen. This is
            // as close as compose gets to View.GONE, which also stops the box but hides the
            // view along with it.
            .mbxViewAnnotationCollisionBox(enabled = !redUnmarked)
        )
      }
      Circle(
        color = Color(0xFF007AFF),
        modifier = Modifier.mbxViewAnnotationCollisionBox(enabled = blueMarked)
      )
    }
  }

  @Composable
  private fun Circle(color: Color, modifier: Modifier = Modifier) {
    Box(
      modifier = modifier
        .size(CIRCLE_DP.dp)
        .background(color, CircleShape)
        .border(2.dp, Color.White, CircleShape)
    )
  }

  /** The same grid the view based screen uses, so the two can be compared cross for cross. */
  private fun gridStyleJson(): String {
    val features = mutableListOf<Feature>()
    for (row in -20..20) {
      for (column in -12..12) {
        features.add(
          Feature.fromGeometry(
            Point.fromLngLat(
              CENTER.longitude() + column * 0.0006,
              CENTER.latitude() + row * 0.0004
            )
          )
        )
      }
    }
    val data = FeatureCollection.fromFeatures(features).toJson()
    return """
      {"version":8,
       "glyphs":"mapbox://fonts/mapbox/{fontstack}/{range}.pbf",
       "sources":{"grid":{"type":"geojson","data":$data}},
       "layers":[
         {"id":"bg","type":"background","paint":{"background-color":"#FFFFFF"}},
         {"id":"grid-symbols","type":"symbol","source":"grid",
          "layout":{"text-field":"✕","text-size":14},
          "paint":{"text-color":"#555555"}}
       ]}
    """.trimIndent()
  }

  private companion object {
    val CENTER: Point = Point.fromLngLat(-73.9866, 40.7306)
    const val ZOOM: Double = 14.79

    // The pin, in density independent pixels: two circles with a gap of one circle between them,
    // and the same padding all round.
    const val CIRCLE_DP: Int = 30
    const val PIN_PADDING_DP: Int = 10

    // How far "Shift red box down" moves the red circle.
    const val SHIFT_DP: Int = 20

    // The red circle's edges, measured from the middle of the pin, which is where the annotation
    // sits on the map. Used to place the avoid region just below it.
    const val RED_LEFT_DP: Int = -45
    const val RED_RIGHT_DP: Int = -15
    const val RED_BOTTOM_DP: Int = 15
    const val REGION_GAP_DP: Int = 5
    const val REGION_HEIGHT_DP: Int = 30

    // The control panel, copied value for value from the view based screen. Black at 60 percent.
    const val PANEL_COLOR: Long = 0x99000000
    const val PANEL_CORNER_DP: Int = 12
    const val PANEL_PADDING_DP: Int = 12
    const val ROW_PADDING_DP: Int = 1
    const val ROW_TEXT_SP: Int = 12
  }
}