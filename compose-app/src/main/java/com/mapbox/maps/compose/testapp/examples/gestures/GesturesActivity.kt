package com.mapbox.maps.compose.testapp.examples.gestures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.gestures.GestureInput
import com.mapbox.maps.extension.compose.rememberMapState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private const val MAX_ALERTS = 30
private val COLOR_START = Color(0xFF669900)
private val COLOR_END = Color(0xFFCC0000)
private val COLOR_PROGRESS = Color(0xFFFF8800)
private val COLOR_FLING = Color(0xFF9933CC)
private val COLOR_CLICK = Color(0xFF0099CC)

private data class GestureAlert(val message: String, val color: Color, val isProgress: Boolean = false)

/**
 * Example to showcase per-gesture listeners using [GestureInput].
 *
 * Displays a label showing the currently active gesture (Move, Scale, Rotate, Shove, Fling)
 * and a log of all gesture events.
 */
@OptIn(MapboxExperimental::class)
public class GesturesActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          var activeGesture by remember { mutableStateOf("None") }
          val alerts = remember { mutableStateListOf<GestureAlert>() }
          val mapState = rememberMapState()

          fun addAlert(alert: GestureAlert) {
            // Deduplicate consecutive progress alerts
            for (existing in alerts) {
              if (!existing.isProgress) break
              if (alert.isProgress && existing.message == alert.message) return
            }
            if (alerts.size >= MAX_ALERTS) alerts.removeAt(alerts.size - 1)
            alerts.add(0, alert)
          }

          Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
              Modifier.fillMaxSize(),
              mapViewportState = rememberMapViewportState {
                setCameraOptions {
                  zoom(ZOOM)
                  center(CityLocations.HELSINKI)
                }
              },
              mapState = mapState,
              onMapClickListener = {
                addAlert(GestureAlert("MAP CLICK", COLOR_CLICK))
                false
              },
              onMapLongClickListener = {
                addAlert(GestureAlert("MAP LONG CLICK", COLOR_CLICK))
                false
              },
            )

            Text(
              text = "Gesture: $activeGesture",
              modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
              color = Color.Black,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
            )

            LazyColumn(
              modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(0.4f)
                .fillMaxHeight(0.5f)
                .padding(top = 48.dp, start = 4.dp),
            ) {
              items(alerts) { alert ->
                Text(
                  text = alert.message,
                  color = alert.color,
                  fontSize = 11.sp,
                )
              }
            }
          }

          mapState.gesturesState.GestureInput {
            coroutineScope {
              launch {
                detectMoveGestures(
                  onMoveBegin = {
                    activeGesture = "Move"
                    addAlert(GestureAlert("MOVE START", COLOR_START))
                  },
                  onMoveEnd = {
                    activeGesture = "None"
                    addAlert(GestureAlert("MOVE END", COLOR_END))
                  },
                  onMove = {
                    addAlert(GestureAlert("MOVE PROGRESS", COLOR_PROGRESS, isProgress = true))
                    false
                  },
                )
              }
              launch {
                detectScaleGestures(
                  onScaleBegin = {
                    activeGesture = "Scale"
                    addAlert(GestureAlert("SCALE START", COLOR_START))
                  },
                  onScaleEnd = {
                    activeGesture = "None"
                    addAlert(GestureAlert("SCALE END", COLOR_END))
                  },
                  onScale = {
                    addAlert(GestureAlert("SCALE PROGRESS", COLOR_PROGRESS, isProgress = true))
                  },
                )
              }
              launch {
                detectRotateGestures(
                  onRotateBegin = {
                    activeGesture = "Rotate"
                    addAlert(GestureAlert("ROTATE START", COLOR_START))
                  },
                  onRotateEnd = {
                    activeGesture = "None"
                    addAlert(GestureAlert("ROTATE END", COLOR_END))
                  },
                  onRotate = {
                    addAlert(GestureAlert("ROTATE PROGRESS", COLOR_PROGRESS, isProgress = true))
                  },
                )
              }
              launch {
                detectShoveGestures(
                  onShoveBegin = {
                    activeGesture = "Shove"
                    addAlert(GestureAlert("SHOVE START", COLOR_START))
                  },
                  onShoveEnd = {
                    activeGesture = "None"
                    addAlert(GestureAlert("SHOVE END", COLOR_END))
                  },
                  onShove = {
                    addAlert(GestureAlert("SHOVE PROGRESS", COLOR_PROGRESS, isProgress = true))
                  },
                )
              }
              launch {
                detectFlingGesture {
                  addAlert(GestureAlert("FLING", COLOR_FLING))
                }
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 12.0
  }
}