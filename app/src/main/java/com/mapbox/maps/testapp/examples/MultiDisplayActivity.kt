package com.mapbox.maps.testapp.examples

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityMultiDisplayBinding

/**
 * Example of displaying a map on the second screen.
 *
 * To use this example, you need to attach a second display to your device, or
 * have the simulated secondary display enabled in the developer settings.
 */
class MultiDisplayActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMultiDisplayBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMultiDisplayBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.mapboxMap.loadStyle(
      style(Style.DARK) {
        +image(IMAGE_ID, BitmapFactory.decodeResource(resources, R.drawable.red_marker))
        +geoJsonSource(SOURCE_ID) {
          geometry(HELSINKI)
        }
        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(IMAGE_ID)
          iconAllowOverlap(true)
        }
      }
    )

    binding.displayOnSecondDisplayButton.setOnClickListener {
      displayMapInSecondaryScreen()
    }
    binding.moveCameraButton.setOnClickListener {
      moveCamera()
    }
  }

  private fun moveCamera() {
    val cameraOption = CameraOptions.Builder()
      .center(HELSINKI)
      .zoom(ZOOM)
      .build()

    binding.mapView.mapboxMap.flyTo(
      cameraOption,
      mapAnimationOptions {
        duration(DURATION)
      }
    )
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
          Intent(this, SecondaryDisplayActivity::class.java),
          options.toBundle()
        )
      }
    } else {
      Toast.makeText(this, "Second screen not found.", Toast.LENGTH_SHORT).show()
    }
  }

  companion object {
    private val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
    private const val ZOOM = 14.0
    private const val DURATION = 3000L
    private const val IMAGE_ID = "test-image"
    private const val LAYER_ID = "symbol-layer-id"
    private const val SOURCE_ID = "source-id"
  }
}