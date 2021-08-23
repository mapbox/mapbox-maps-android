package com.mapbox.maps.testapp.examples.camera

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.testapp.databinding.ActivityCameraAnimateBinding

class LowLevelCameraAnimatorActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var binding: ActivityCameraAnimateBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCameraAnimateBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      animateCameraDelayed()
    }
  }

  private fun animateCameraDelayed() {
    binding.mapView.camera.apply {
      val bearing = createBearingAnimator(cameraAnimatorOptions(-45.0)) {
        duration = 4000
        interpolator = AccelerateDecelerateInterpolator()
      }
      val zoom = createZoomAnimator(
        cameraAnimatorOptions(14.0) {
          startValue(3.0)
        }
      ) {
        duration = 4000
        interpolator = AccelerateDecelerateInterpolator()
      }
      val pitch = createPitchAnimator(
        cameraAnimatorOptions(55.0) {
          startValue(0.0)
        }
      ) {
        duration = 4000
        interpolator = AccelerateDecelerateInterpolator()
      }
      playAnimatorsSequentially(zoom, pitch, bearing)
    }
  }
}