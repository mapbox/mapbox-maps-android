package com.mapbox.maps.compose.testapp.examples.ornaments

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.ornaments.indoorselector.rememberIndoorSelectorState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.StandardStyleState

/**
 * Demonstrates a custom indoor floor-selector UI built on top of `IndoorSelectorControl`.
 *
 * `IndoorSelectorControl` attaches the plugin headlessly inside `indoorSelector = { }`;
 * the floor list is read directly from [IndoorSelectorState][com.mapbox.maps.extension.compose.ornaments.indoorselector.IndoorSelectorState].
 * Uses a plain [Column] (no scroll) so the widget is always wrap-content tall.
 */
@OptIn(MapboxExperimental::class, com.mapbox.annotation.MapboxExperimental::class)
public class CustomIndoorSelectorExampleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          val indoorState = rememberIndoorSelectorState()
          MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(Point.fromLngLat(LONGITUDE, LATITUDE))
                zoom(ZOOM)
                bearing(BEARING)
                pitch(PITCH)
              }
            },
            indoorSelector = {
              // Attach the plugin to indoorState without rendering the default UI.
              IndoorSelectorControl(state = indoorState)

              // Custom floor-selector panel — wrap in Box to get BoxScope alignment.
              // Visible only when an indoor building is in view.
              val floors = indoorState.floors
              if (floors.isEmpty()) return@MapboxMap
              Box(modifier = Modifier.fillMaxSize()) {
                Surface(
                  modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 80.dp, end = 16.dp),
                  shape = RoundedCornerShape(8.dp),
                  elevation = 4.dp,
                ) {
                  val selectedBg = MaterialTheme.colors.primary
                  val selectedText = MaterialTheme.colors.onPrimary
                  val bg = MaterialTheme.colors.surface
                  val text = MaterialTheme.colors.onSurface
                  Column(
                    modifier = Modifier.width(ITEM_SIZE),
                    horizontalAlignment = Alignment.CenterHorizontally,
                  ) {
                    // Building item — clears the active floor selection.
                    val isBuildingSelected = indoorState.selectedFloorId == null
                    Box(
                      modifier = Modifier
                        .size(ITEM_SIZE)
                        .background(if (isBuildingSelected) selectedBg else bg)
                        .clickable { indoorState.selectedFloorId = null },
                      contentAlignment = Alignment.Center,
                    ) {
                      Text(
                        text = "🏢",
                        color = if (isBuildingSelected) selectedText else text,
                        fontSize = ITEM_TEXT_SP,
                        textAlign = TextAlign.Center,
                      )
                    }

                    // One item per floor — no scroll, wrap-content height.
                    floors.forEach { floor ->
                      val isSelected = floor.id == indoorState.selectedFloorId
                      Box(
                        modifier = Modifier
                          .size(ITEM_SIZE)
                          .background(if (isSelected) selectedBg else bg)
                          .clickable {
                            indoorState.selectedFloorId = floor.id
                            Log.d(TAG, "Floor tapped: ${floor.id} (${floor.name})")
                          },
                        contentAlignment = Alignment.Center,
                      ) {
                        Text(
                          text = floor.name.take(FLOOR_LABEL_MAX_CHARS),
                          color = if (isSelected) selectedText else text,
                          fontSize = ITEM_TEXT_SP,
                          textAlign = TextAlign.Center,
                        )
                      }
                    }
                  }
                }
              }
            },
            style = {
              MapboxStandardStyle(
                standardStyleState = remember {
                  StandardStyleState().apply {
                    configurationsState.showIndoor = BooleanValue(true)
                  }
                }
              )
            },
          )
        }
      }
    }
  }

  private companion object {
    private const val TAG = "CustomIndoorExample"
    private const val FLOOR_LABEL_MAX_CHARS = 3
    private val ITEM_SIZE = 44.dp
    private val ITEM_TEXT_SP = 16.sp
    const val LATITUDE = 35.55025
    const val LONGITUDE = 139.794131
    const val ZOOM = 16.0
    const val BEARING = 12.0
    const val PITCH = 60.0
  }
}