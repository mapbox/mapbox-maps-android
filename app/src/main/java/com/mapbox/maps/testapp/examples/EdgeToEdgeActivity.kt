package com.mapbox.maps.testapp.examples

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.testapp.databinding.ActivityEdgeToEdgeBinding

/**
 * This example shows how to handle system insets (status bar, navigation bar, display cutouts)
 * in the Edge-to-Edge layout to prevent map ornaments from being obscured.
 *
 * Key concepts:
 * - Combine WindowInsetsCompat.Type.systemBars() and WindowInsetsCompat.Type.displayCutout()
 * - Apply insets to map ornaments (logo, attribution, compass, scale bar)
 * - Configure system bar appearance.
 *
 * Note: Starting with Android 15 (API 35), edge-to-edge is enforced by default for all apps
 * targeting API 35+. See:
 * https://developer.android.com/about/versions/15/behavior-changes-15#edge-to-edge
 *
 * For apps targeting API 34 or lower, you need to explicitly enable edge-to-edge by calling
 * WindowCompat.setDecorFitsSystemWindows(window, false) or WindowCompat.enableEdgeToEdge(). See:
 * https://developer.android.com/develop/ui/views/layout/edge-to-edge#enable-edge-to-edge-display
 */
class EdgeToEdgeActivity : AppCompatActivity() {

  private lateinit var binding: ActivityEdgeToEdgeBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityEdgeToEdgeBinding.inflate(layoutInflater)
    setContentView(binding.root)
    // Configure system bar appearance
    configureSystemBarAppearance()

    binding.mapView.mapboxMap.apply {
      loadStyle(Style.STANDARD)
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(LONGITUDE, LATITUDE))
          .zoom(ZOOM)
          .build()
      )
    }

    setupEdgeToEdgeInsets()
  }

  /**
   * Set up window insets handling for edge-to-edge layout.
   *
   * This method applies system insets to map ornaments to ensure they don't overlap
   * with system UI elements like status bar, navigation bar or display cutouts.
   */
  private fun setupEdgeToEdgeInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
      val systemInsets = insets.getInsets(
        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
      )

      val padding4Dp = dpToPx(4f)
      val padding8Dp = dpToPx(8f)
      val logoSize = dpToPx(82f)

      with(binding.mapView.logo) {
        marginLeft = systemInsets.left.toFloat() + padding4Dp
        marginTop = systemInsets.top.toFloat()
        marginRight = systemInsets.right.toFloat()
        marginBottom = systemInsets.bottom.toFloat() + padding4Dp
      }

      with(binding.mapView.attribution) {
        marginLeft = systemInsets.left.toFloat() + logoSize + padding8Dp
        marginTop = systemInsets.top.toFloat()
        marginRight = systemInsets.right.toFloat() + padding4Dp
        marginBottom = systemInsets.bottom.toFloat() + padding4Dp
      }

      with(binding.mapView.compass) {
        marginLeft = systemInsets.left.toFloat()
        marginTop = systemInsets.top.toFloat() + padding8Dp
        marginRight = systemInsets.right.toFloat() + padding8Dp
        marginBottom = systemInsets.bottom.toFloat()
      }

      with(binding.mapView.scalebar) {
        marginLeft = systemInsets.left.toFloat() + padding8Dp
        marginTop = systemInsets.top.toFloat() + padding8Dp
        marginRight = systemInsets.right.toFloat()
        marginBottom = systemInsets.bottom.toFloat()
      }

      insets
    }
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
    WindowCompat.getInsetsController(window, window.decorView).apply {
      isAppearanceLightStatusBars = true  // true = dark icons
      isAppearanceLightNavigationBars = true
    }
  }

  private fun dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      dp,
      resources.displayMetrics
    )
  }

  companion object {
    private const val LATITUDE = 40.7128
    private const val LONGITUDE = -74.0060
    private const val ZOOM = 12.0
  }
}