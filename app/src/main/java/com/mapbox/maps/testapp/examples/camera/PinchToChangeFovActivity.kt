package com.mapbox.maps.testapp.examples.camera

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityPinchToChangeFovBinding

/**
 * Example demonstrating how to control the camera's vertical field of view via a pinch gesture.
 * Built-in pinch-to-zoom is disabled so the gesture exclusively adjusts FOV.
 * Pinch out (spread) narrows the FOV (telephoto effect); pinch in widens it.
 */
class PinchToChangeFovActivity : AppCompatActivity() {

  private lateinit var binding: ActivityPinchToChangeFovBinding
  private var currentFov = DEFAULT_FOV

  private val scaleGestureDetector by lazy {
    ScaleGestureDetector(
      this,
      object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
          currentFov = (currentFov / detector.scaleFactor).coerceIn(FOV_MIN, FOV_MAX)
          binding.mapView.mapboxMap.setCamera(CameraOptions.Builder().verticalFov(currentFov).build())
          updateLabel(currentFov)
          return true
        }
      }
    )
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityPinchToChangeFovBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mapView.gestures.pinchToZoomEnabled = false

    binding.mapView.setOnTouchListener { v, event ->
      scaleGestureDetector.onTouchEvent(event)
      if (event.action == MotionEvent.ACTION_UP) {
        v.performClick()
      }
      if (!scaleGestureDetector.isInProgress) {
        v.onTouchEvent(event)
      }
      true
    }

    binding.mapView.mapboxMap.loadStyle(Style.STANDARD) {
      binding.mapView.mapboxMap.setCamera(START_CAMERA)
    }

    updateLabel(currentFov)
  }

  private fun updateLabel(fov: Double) {
    binding.fovLabel.text = getString(R.string.fov_label_format, fov)
  }

  companion object {
    private const val DEFAULT_FOV = 36.87
    private const val FOV_MIN = 11.0
    private const val FOV_MAX = 90.0

    private val START_CAMERA = cameraOptions {
      center(Point.fromLngLat(18.0669, 59.3419))
      zoom(16.5)
      bearing(30.0)
      pitch(70.0)
    }
  }
}