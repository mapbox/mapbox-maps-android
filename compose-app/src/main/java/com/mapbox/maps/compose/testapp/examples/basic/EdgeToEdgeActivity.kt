package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

/**
 * This example shows how to handle system insets (status bar, navigation bar, display cutouts)
 * in the Edge-to-Edge layout using Jetpack Compose to prevent map ornaments from being obscured,
 *
 * Key concepts:
 * - Combine WindowInsets.systemBars and WindowInsets.displayCutout for comprehensive coverage
 * - Use Modifier.windowInsetsPadding() to apply combined insets to map ornaments
 * - Use contentPadding parameter to preserve default map ornament spacing
 * - Configure system bar appearance.
 *
 * Note: Starting with Android 15 (API 35), edge-to-edge is enforced by default for all apps
 * targeting API 35+. See:
 * https://developer.android.com/about/versions/15/behavior-changes-15#edge-to-edge
 *
 * For apps targeting API 34 or lower, you need to explicitly enable edge-to-edge by calling
 * WindowCompat.setDecorFitsSystemWindows(window, false) or WindowCompat.enableEdgeToEdge(). See:
 * https://developer.android.com/develop/ui/views/layout/edge-to-edge#enable-edge-to-edge-display
 *
 * The contentPadding parameter was introduced in v11.3.0-rc.1 to address the issue where
 * supplying custom padding via Modifier would unexpectedly remove the default ornament padding.
 * Now the default padding is separated into a contentPadding parameter, allowing you to add
 * system bar padding without losing the ornament's internal spacing.
 * See: https://github.com/mapbox/mapbox-maps-android/issues/2317
 */
public class EdgeToEdgeActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MapboxMapComposeTheme {
        // Combine system bars (status bar, navigation bar) and cutout insets (punch-holes, notches)
        val safeInsets = WindowInsets.systemBars.union(WindowInsets.displayCutout)

        Box(modifier = Modifier.fillMaxSize()) {
          MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            },
            logo = {
              Logo(
                // Apply safe insets to respect system UI and display cutouts
                modifier = Modifier.windowInsetsPadding(safeInsets),
                // Preserve the default 4dp content padding for the logo
                // This is the key improvement from v11.3.0-rc.1:
                // contentPadding is separate from the Modifier padding
                contentPadding = PaddingValues(ORNAMENT_PADDING),
                alignment = Alignment.BottomStart
              )
            },
            // Customize Attribution ornament with edge-to-edge handling
            attribution = {
              Attribution(
                // Apply safe insets to respect system UI and display cutouts
                modifier = Modifier.windowInsetsPadding(safeInsets),
                // Preserve the default 4dp content padding for the attribution
                contentPadding = PaddingValues(ORNAMENT_PADDING),
                alignment = Alignment.BottomEnd
              )
            },
            // Customize Compass ornament with edge-to-edge handling
            compass = {
              Compass(
                // Apply safe insets to respect system UI and display cutouts
                modifier = Modifier.windowInsetsPadding(safeInsets),
                // Preserve the default 4dp content padding for the compass
                contentPadding = PaddingValues(ORNAMENT_PADDING),
                alignment = Alignment.TopEnd
              )
            },
            // Customize ScaleBar ornament with edge-to-edge handling
            scaleBar = {
              ScaleBar(
                // Apply safe insets to respect system UI and display cutouts
                modifier = Modifier.windowInsetsPadding(safeInsets),
                // Preserve the default 4dp content padding for the scale bar
                contentPadding = PaddingValues(ORNAMENT_PADDING),
                alignment = Alignment.TopStart
              )
            }
          )
        }
      }
    }

    // Configure system bar appearance (after setContent)
    configureSystemBarAppearance()
  }

  /**
   * Configure system bar appearance for edge-to-edge display.
   *
   * This example uses dark system bar icons (for better visibility on light map backgrounds).
   * For theme-aware icon colors or other customizations, see:
   * https://developer.android.com/develop/ui/views/layout/edge-to-edge#system-bar-icons
   */
  @Suppress("DEPRECATION")
  private fun configureSystemBarAppearance() {
    // Disable automatic contrast enforcement for true transparency (API 29+)
    // Note: This only affects 3-button navigation (removes scrim). Gesture navigation is unaffected.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      window.isNavigationBarContrastEnforced = false
      window.isStatusBarContrastEnforced = false
    }

    // Set dark system bar icons (more visible on light map backgrounds)
    val insetsController = WindowCompat.getInsetsController(window, window.decorView)
    insetsController.apply {
      isAppearanceLightStatusBars = true // true = dark icons
      isAppearanceLightNavigationBars = true
    }
  }

  private companion object {
    const val ZOOM: Double = 12.0
    val ORNAMENT_PADDING = 4.dp
  }
}