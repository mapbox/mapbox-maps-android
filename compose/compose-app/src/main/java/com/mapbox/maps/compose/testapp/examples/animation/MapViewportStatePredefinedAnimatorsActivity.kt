package com.mapbox.maps.compose.testapp.examples.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.animation.MapAnimationOptions

/**
 * Showcase incremental camera animations (rotateBy, moveBy, pitchBy, scaleBy) using [com.mapbox.maps.extension.compose.animation.viewport.MapViewportState].
 */
public class MapViewportStatePredefinedAnimatorsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions(START_CAMERA)
      }
      MapboxMapComposeTheme {
        var menuExpanded by remember { mutableStateOf(false) }

        fun jumpToStart() {
          mapViewportState.setCameraOptions(START_CAMERA)
        }

        Scaffold(
          topBar = {
            Surface(
              color = MaterialTheme.colors.primarySurface,
              elevation = 4.dp
            ) {
              Column(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
                TopAppBar(
                  title = { Text(text = title.toString()) },
                  navigationIcon = {
                    IconButton(onClick = { finish() }) {
                      Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                  },
                  actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                      Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                      expanded = menuExpanded,
                      onDismissRequest = { menuExpanded = false }
                    ) {
                      DropdownMenuItem(onClick = {
                        menuExpanded = false
                        jumpToStart()
                      }) { Text("Jump to Start") }
                      DropdownMenuItem(onClick = {
                        menuExpanded = false
                        jumpToStart()
                        mapViewportState.rotateBy(
                          first = ScreenCoordinate(0.0, 0.0),
                          second = ScreenCoordinate(500.0, 500.0),
                          animationOptions = ANIMATION_OPTIONS
                        )
                      }) { Text("Rotate By") }
                      DropdownMenuItem(onClick = {
                        menuExpanded = false
                        jumpToStart()
                        mapViewportState.moveBy(
                          screenCoordinate = ScreenCoordinate(500.0, 500.0),
                          animationOptions = ANIMATION_OPTIONS
                        )
                      }) { Text("Move By") }
                      DropdownMenuItem(onClick = {
                        menuExpanded = false
                        jumpToStart()
                        mapViewportState.pitchBy(
                          pitch = 70.0,
                          animationOptions = ANIMATION_OPTIONS
                        )
                      }) { Text("Pitch By") }
                      DropdownMenuItem(onClick = {
                        menuExpanded = false
                        jumpToStart()
                        mapViewportState.scaleBy(
                          amount = 15.0,
                          screenCoordinate = ScreenCoordinate(10.0, 10.0),
                          animationOptions = ANIMATION_OPTIONS
                        )
                      }) { Text("Scale By") }
                    }
                  },
                  elevation = 0.dp
                )
              }
            }
          }
        ) {
          Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
              modifier = Modifier.fillMaxSize(),
              mapViewportState = mapViewportState,
            )
          }
        }
      }
    }
  }

  private companion object {
    val START_CAMERA = cameraOptions {
      center(Point.fromLngLat(-0.11968, 51.50325))
      zoom(15.0)
      bearing(0.0)
      pitch(0.0)
    }
    val ANIMATION_OPTIONS: MapAnimationOptions =
      MapAnimationOptions.mapAnimationOptions { duration(2000) }
  }
}