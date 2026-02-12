package com.mapbox.maps.compose.testapp

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Customised Scaffold to be applied by all the activities. It adds the following UI elements to the
 * UI:
 *  * TopAppBar that shows the label for current activity.
 *  * Navigation icon that dismisses current activity, if the parent activity exists.
 *
 * Note: Edge-to-edge is enabled by default on Android 15 (API 35+).
 * The TopAppBar extends behind the status bar with internal padding to avoid content overlap.
 * See: https://developer.android.com/about/versions/15/behavior-changes-15#edge-to-edge
 */
@Composable
public fun ExampleScaffold(
  modifier: Modifier = Modifier,
  snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
  floatingActionButton: @Composable () -> Unit = {},
  floatingActionButtonPosition: FabPosition = FabPosition.End,
  bottomBar: @Composable () -> Unit = {},
  content: @Composable (PaddingValues) -> Unit
) {
  val activity = LocalContext.current as? Activity
  val name = remember {
    activity?.title?.toString() ?: "Example"
  }
  val hasParentActivity = remember {
    activity?.parentActivityIntent != null
  }
  Scaffold(
    modifier = modifier,
    snackbarHost = snackbarHost,
    floatingActionButton = floatingActionButton,
    floatingActionButtonPosition = floatingActionButtonPosition,
    topBar = {
      // Surface provides elevation and background for the entire top bar area including status bar
      Surface(
        color = MaterialTheme.colors.primarySurface,
        elevation = 4.dp
      ) {
        Column(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
          TopAppBar(
            title = { Text(text = name) },
            navigationIcon = if (hasParentActivity) {
              {
                IconButton(onClick = { activity?.finish() }) {
                  Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
              }
            } else null,
            elevation = 0.dp  // Prevent TopAppBar shadow (Surface provides elevation)
          )
        }
      }
    },
    bottomBar = bottomBar,
    content = content
  )
}