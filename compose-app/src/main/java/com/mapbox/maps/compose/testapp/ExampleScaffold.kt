package com.mapbox.maps.compose.testapp

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

/**
 * Customised Scaffold to be applied by all the activities. It adds the following UI elements to the
 * UI:
 *  * TopAppBar that shows the label for current activity.
 *  * Navigation icon that dismisses current activity, if the parent activity exists.
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
      TopAppBar(
        title = {
          Text(text = name)
        },
        navigationIcon = if (hasParentActivity) {
          {
            IconButton(onClick = { activity?.finish() }) {
              Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
              )
            }
          }
        } else {
          null
        }
      )
    },
    bottomBar = bottomBar,
    content = content
  )
}