package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
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
 * Shows which transforms of a marked composable are reported as collision boxes, which is the
 * one thing the KDoc of [mbxViewAnnotationCollisionBox] cannot show in words.
 *
 * There is no view based counterpart: a `View` has no `graphicsLayer`, so this question only
 * comes up in compose.
 *
 * The collision debug overlay is on, so the map draws a red frame around every rectangle it was
 * given. One blue square is marked as a collision box. Each switch applies one kind of transform
 * to that square.
 *
 * What to look for: the red frame should sit exactly on the blue square. The frame is only
 * drawn once the annotation is placed against the style's symbols, so the screen starts with
 * no frame at all. Turn on "Annotation wins over labels" first.
 *
 * - Translate, Scale and Rotate use `graphicsLayer`. The frame follows the square, growing for
 *   scale and becoming the rotated bounding box for rotation.
 * - Draw-time move uses a draw modifier that translates the canvas. The square moves and the
 *   frame stays where it was, because no layout pass happens and the box is never re-reported.
 *   This is the one limit the modifier's KDoc calls out.
 * - Deferred scale reads its flag inside the `graphicsLayer` lambda. On a freshly opened screen
 *   flipping it changes nothing at all. Once one of the switches above has been used, the same
 *   flip does scale the square, and the reported box grows with it. Either way the pixels and
 *   the box never disagree.
 */
@OptIn(MapboxExperimental::class)
public class ViewAnnotationCollisionTransformActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val translate = remember { mutableStateOf(false) }
      val scale = remember { mutableStateOf(false) }
      val rotate = remember { mutableStateOf(false) }
      val drawOnly = remember { mutableStateOf(false) }
      val deferredScale = remember { mutableStateOf(false) }
      val avoidRegion = remember { mutableStateOf(false) }
      val symbolCollision = remember { mutableStateOf(false) }

      // The avoid region is written in dp and converted here, so it lands in the same place on
      // every screen. The reading aid below is laid out from the same dp values.
      val bandHalfWidthPx: Double
      val bandTopPx: Double
      val bandBottomPx: Double
      with(LocalDensity.current) {
        bandHalfWidthPx = BAND_HALF_WIDTH_DP.dp.toPx().toDouble()
        bandTopPx = BAND_TOP_DP.dp.toPx().toDouble()
        bandBottomPx = BAND_BOTTOM_DP.dp.toPx().toDouble()
      }

      MapboxMapComposeTheme {
        ExampleScaffold {
          Box(Modifier.fillMaxSize()) {
            MapboxMap(
              Modifier.fillMaxSize(),
              mapViewportState = rememberMapViewportState {
                setCameraOptions {
                  zoom(ZOOM)
                  center(GRID_CENTER)
                }
              },
              // A blank style covered in small crosses. An active collision box shows up as a
              // hole in the grid, which is far easier to read than watching city labels.
              style = { MapStyle(style = remember { gridStyleJson() }) }
            ) {
              MapEffect(avoidRegion.value) { mapView ->
                mapView.debugOptions = setOf(MapViewDebugOptions.COLLISION)
                // A band just above the square. The square clears it at rest and runs into it
                // once any transform makes the reported box taller or moves it up.
                mapView.viewAnnotationManager.viewAnnotationAvoidRegions =
                  if (avoidRegion.value) {
                    val cx = mapView.width / 2.0
                    val cy = mapView.height / 2.0
                    listOf(
                      ScreenBox(
                        ScreenCoordinate(cx - bandHalfWidthPx, cy - bandTopPx),
                        ScreenCoordinate(cx + bandHalfWidthPx, cy - bandBottomPx)
                      )
                    )
                  } else {
                    emptyList()
                  }
              }

              ViewAnnotation(
                options = viewAnnotationOptions {
                  geometry(GRID_CENTER)
                  enableAvoidRegions(true)
                  // Decides who wins between this annotation and style symbols. When true the
                  // annotation is placed first and symbols inside its boxes are suppressed, which
                  // shows up as a hole in the grid. When false the annotation is instead tested
                  // against avoidSymbolLayerIds, which is empty here, so nothing happens.
                  enableSymbolLayerCollision(symbolCollision.value)
                  annotationAnchor { anchor(ViewAnnotationAnchor.CENTER) }
                }
              ) {
                MarkedSquare(
                  translate = translate.value,
                  scale = scale.value,
                  rotate = rotate.value,
                  drawOnly = drawOnly.value,
                  deferredScale = deferredScale,
                )
              }
            }

            if (avoidRegion.value) {
              Box(
                Modifier
                  .align(Alignment.Center)
                  .offset(y = (-(BAND_TOP_DP + BAND_BOTTOM_DP) / 2).dp)
                  .width((BAND_HALF_WIDTH_DP * 2).dp)
                  .height((BAND_TOP_DP - BAND_BOTTOM_DP).dp)
                  .background(Color(0x55FF0000))
              )
            }

            // Same control panel as the view based collision playground in the `app` module, so
            // the two can be recorded side by side: pinned to the bottom, full width, black at
            // 60 percent, one row per toggle with the label on the left.
            Column(
              Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                .background(Color(PANEL_COLOR), RoundedCornerShape(PANEL_CORNER_DP.dp))
                .padding(horizontal = PANEL_PADDING_DP.dp, vertical = PANEL_PADDING_DP.dp / 2)
            ) {
              Toggle("Translate (graphicsLayer)", translate)
              Toggle("Scale (graphicsLayer)", scale)
              Toggle("Rotate (graphicsLayer)", rotate)
              Toggle("Draw-time move (no layout)", drawOnly)
              Toggle("Deferred scale (read inside lambda)", deferredScale)
              Toggle("Avoid region above square", avoidRegion)
              Toggle("Annotation wins over labels (enableSymbolLayerCollision)", symbolCollision)
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
      // tall as the view based playground's rows. The panel is a debug surface, so the smaller
      // target is fine here.
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
   * One marked square inside an oversized container, so there is room for it to move without the
   * annotation itself being resized.
   */
  @Composable
  private fun MarkedSquare(
    translate: Boolean,
    scale: Boolean,
    rotate: Boolean,
    drawOnly: Boolean,
    deferredScale: State<Boolean>,
  ) {
    Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
      Box(
        modifier = Modifier
          .graphicsLayer {
            // deferredScale.value is read here, inside the lambda. Compose records it as a layer
            // read, so flipping it invalidates only the layer and runs no layout pass.
            val deferred = if (deferredScale.value) 2.5f else 1f
            translationY = if (translate) -SHIFT_DP.dp.toPx() else 0f
            scaleX = (if (scale) 2.5f else 1f) * deferred
            scaleY = (if (scale) 2.5f else 1f) * deferred
            rotationZ = if (rotate) 45f else 0f
          }
          .then(
            if (drawOnly) {
              Modifier.drawWithContent {
                translate(top = SHIFT_DP.dp.toPx()) { this@drawWithContent.drawContent() }
              }
            } else {
              Modifier
            }
          )
          .mbxViewAnnotationCollisionBox()
          // Large enough to create an obvious collision hole, not confused with grid gaps.
          .size(95.dp)
          // Outline with a see-through fill, so a cross that is still drawn inside the box means
          // it was not suppressed. An opaque fill would hide crosses with paint instead.
          .background(Color(0x223A59FA))
          .border(2.dp, Color(0xFF3A59FA))
      )
    }
  }

  /** Same grid the view-based collision playground uses, so the two can be compared. */
  private fun gridStyleJson(): String {
    val features = mutableListOf<Feature>()
    for (row in -24..24) {
      for (column in -15..15) {
        features.add(
          Feature.fromGeometry(
            Point.fromLngLat(
              // Both steps land near 55px on screen at ZOOM, so the grid is square. Any tighter
              // and the crosses start hiding each other, which would look like the annotation
              // did it.
              GRID_CENTER.longitude() + column * 0.00052,
              GRID_CENTER.latitude() + row * 0.0004
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
          "layout":{"text-field":"\u2715","text-size":14},
          "paint":{"text-color":"#555555"}}
       ]}
    """.trimIndent()
  }

  private companion object {
    val GRID_CENTER: Point = Point.fromLngLat(-73.9866, 40.7306)
    const val ZOOM: Double = 14.79
    const val SHIFT_DP: Float = 55f

    // The square is 95dp, so it reaches 47.5dp above the centre at rest and 67.2dp above it
    // once rotated by 45 degrees. The band's lower edge sits between the two, which is what
    // makes the square clear the band at rest and run into it under every transform.
    const val BAND_HALF_WIDTH_DP: Float = 115f
    const val BAND_TOP_DP: Float = 105f
    const val BAND_BOTTOM_DP: Float = 57f

    // The control panel, copied value for value from the view based collision playground so the
    // two examples look the same in a side by side recording. Black at 60 percent.
    const val PANEL_COLOR: Long = 0x99000000
    const val PANEL_CORNER_DP: Int = 12
    const val PANEL_PADDING_DP: Int = 12
    const val ROW_PADDING_DP: Int = 1
    const val ROW_TEXT_SP: Int = 12
  }
}