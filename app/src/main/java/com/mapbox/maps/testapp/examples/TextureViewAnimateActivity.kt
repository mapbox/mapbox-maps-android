package com.mapbox.maps.testapp.examples

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.testapp.databinding.ActivityTextureViewBinding

/**
 * Example of applying animation to a map which renders to TextureView.
 */
class TextureViewAnimateActivity : AppCompatActivity() {

  private val handler: Handler = Handler(Looper.getMainLooper())
  private lateinit var animation: ObjectAnimator

  private val places: Array<Point> = arrayOf(
    Point.fromLngLat(-122.4194, 37.7749), // SF
    Point.fromLngLat(-77.0369, 38.9072), // DC
    Point.fromLngLat(4.8952, 52.3702), // AMS
    Point.fromLngLat(24.9384, 60.1699), // HEL
    Point.fromLngLat(-74.2236, -13.1639), // AYA
    Point.fromLngLat(13.4050, 52.5200), // BER
    Point.fromLngLat(77.5946, 12.9716), // BAN
    Point.fromLngLat(121.4737, 31.2304), // SHA
    Point.fromLngLat(27.56667, 53.9) // MSK
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityTextureViewBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    val cameraPlugin = binding.mapView.camera

    for (i in places.indices) {
      handler.postDelayed(
        {
          cameraPlugin.flyTo(
            CameraOptions.Builder()
              .center(places[i])
              .zoom(14.0 - i)
              .build(),
            mapAnimationOptions { duration(DURATION_MS) }
          )
        },
        i * DURATION_MS
      )
    }

    // Animate the map view
    animation = ObjectAnimator.ofFloat(
      binding.mapView,
      "rotationY",
      0.0f,
      360f
    ).apply {
      duration = 5_000L
      repeatCount = ObjectAnimator.INFINITE
      start()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    animation.cancel()
    handler.removeCallbacksAndMessages(null)
  }

  companion object {
    private const val DURATION_MS = 10_000L
  }
}