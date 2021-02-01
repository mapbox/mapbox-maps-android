package com.mapbox.maps.testapp.examples

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_multi_display.*
import kotlinx.android.synthetic.main.activity_simple_map.mapView

/**
 * Example of displaying a map on the second screen.
 */
class MultiDisplayActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_multi_display)
    mapView.getMapboxMap().loadStyleUri(Style.DARK)
    button.setOnClickListener {
      displayMapInSecondaryScreen()
    }
  }

  private fun displayMapInSecondaryScreen() {
    val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    val displays = displayManager.displays
    if (displays.size > 1) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Use activity options to select the display screen.
        val options = ActivityOptions.makeBasic()
        options.launchDisplayId = displays[1].displayId
        // To display on the second screen, the intent must be launched using singleTask launchMode.
        startActivity(
          Intent(this, SecondaryDisplaySimpleMapActivity::class.java),
          options.toBundle()
        )
      }
    } else {
      Toast.makeText(this, "Second screen not found.", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }
}