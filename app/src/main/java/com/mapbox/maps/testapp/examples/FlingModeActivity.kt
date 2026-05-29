package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.ScrollMode
import com.mapbox.maps.plugin.ScrollMode.*
import com.mapbox.maps.plugin.gestures.gestures

/**
 * Playground for fling deceleration and scroll mode.
 *
 * - FAB (bottom-end): toggle native OverScroller vs legacy easeTo fling.
 * - Button (top-start): cycle scroll mode (H+V / H only / V only).
 */
@OptIn(MapboxExperimental::class)
class FlingModeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val root = FrameLayout(this)
    val mapView = MapView(this)
    root.addView(
      mapView,
      FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
      )
    )

    val fab = FloatingActionButton(this).apply {
      layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT,
        Gravity.BOTTOM or Gravity.END
      ).apply {
        setMargins(0, 0, 48, 48)
      }
    }
    root.addView(fab)

    val scrollModeButton = Button(this).apply {
      layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT,
        Gravity.TOP or Gravity.START
      ).apply {
        setMargins(24, 24, 0, 0)
      }
    }
    root.addView(scrollModeButton)

    setContentView(root)

    mapView.mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-74.5, 40.0))
        .zoom(12.0)
        .build()
    )

    val gesturesPlugin = mapView.gestures
    gesturesPlugin.useNativeFlingDeceleration = true
    updateFab(fab, true)
    updateScrollModeButton(scrollModeButton, gesturesPlugin.scrollMode)

    fab.setOnClickListener {
      val newValue = !gesturesPlugin.useNativeFlingDeceleration
      gesturesPlugin.useNativeFlingDeceleration = newValue
      updateFab(fab, newValue)
    }

    scrollModeButton.setOnClickListener {
      val next = when (gesturesPlugin.scrollMode) {
        HORIZONTAL_AND_VERTICAL -> HORIZONTAL
        HORIZONTAL -> VERTICAL
        VERTICAL -> HORIZONTAL_AND_VERTICAL
      }
      gesturesPlugin.scrollMode = next
      updateScrollModeButton(scrollModeButton, next)
    }
  }

  private fun updateFab(fab: FloatingActionButton, native: Boolean) {
    fab.setImageResource(
      if (native) android.R.drawable.ic_media_pause
      else android.R.drawable.ic_media_play
    )
    Toast.makeText(
      this,
      if (native) "Native fling" else "Legacy fling",
      Toast.LENGTH_SHORT
    ).show()
  }

  private fun updateScrollModeButton(button: Button, mode: ScrollMode) {
    button.text = when (mode) {
      HORIZONTAL_AND_VERTICAL -> "Scroll: H+V"
      HORIZONTAL -> "Scroll: H only"
      VERTICAL -> "Scroll: V only"
    }
  }
}