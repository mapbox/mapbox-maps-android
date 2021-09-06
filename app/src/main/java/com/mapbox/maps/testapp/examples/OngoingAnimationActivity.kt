package com.mapbox.maps.testapp.examples

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.gestures.gestures
import java.util.concurrent.TimeUnit

class OngoingAnimationActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(9.0)
            .build()
        )
      }

    mapView.gestures.addProtectedAnimationOwner(OWNER)

    val anim = mapView.camera.createPitchAnimator(
      CameraAnimatorOptions.cameraAnimatorOptions(0.0, 30.0) {
        owner(OWNER)
      }
    ) {
      repeatCount = ValueAnimator.INFINITE
      repeatMode = ValueAnimator.REVERSE
      duration = TimeUnit.SECONDS.toMillis(2)
      interpolator = LinearInterpolator()
    }

    mapView.camera.registerAnimators(anim)

    anim.start()
  }

  companion object {
    private const val OWNER = "Example"
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}