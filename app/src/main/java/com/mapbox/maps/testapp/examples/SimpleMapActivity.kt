package com.mapbox.maps.testapp.examples

import android.animation.Animator
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder().center(Point.fromLngLat(LONGITUDE, LATITUDE)).zoom(9.0).build()
        )
      }
  }

  private fun test(mapboxMap: MapboxMap, cameraPlugin: CameraAnimationsPlugin) {
    val listener = object : Animator.AnimatorListener {
      override fun onAnimationRepeat(animation: Animator?) {
        Log.e("TEST_CAMERA", "onAnimationRepeat: " + animation)
      }

      override fun onAnimationEnd(animation: Animator?) {
        Log.e("TEST_CAMERA", "onAnimationEnd: " + animation)
        Log.e("TEST_CAMERA", "onAnimationEnd options: " + mapboxMap.cameraState)
      }

      override fun onAnimationCancel(animation: Animator?) {
        Log.e("TEST_CAMERA", "onAnimationCancel: " + animation)
      }

      override fun onAnimationStart(animation: Animator?) {
        Log.e("TEST_CAMERA", "onAnimationCancel: " + animation)
      }
    }
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(17.045988781311138, 51.12341909978734))
        .bearing(27.254667247679752)
        .pitch(0.0)
        .padding(EdgeInsets(140.0, 140.0, 140.0, 140.0))
        .zoom(13.282170222962456)
        .build()
    )
    mapboxMap.addOnMapClickListener(object : OnMapClickListener {
      var firstClick = true
      override fun onMapClick(point: Point): Boolean {
        Log.e("TEST_CAMERA", "current options: " + mapboxMap.cameraState)
        if (firstClick) {
          Log.e("TEST_CAMERA", "first click")
          firstClick = false

          val centerAnimator = cameraPlugin.createCenterAnimator(
            cameraAnimatorOptions(
              Point.fromLngLat(
                17.04809100000034,
                51.12074813941152
              )
            )
          ) {
            duration = 1500L
          }

          val bearingAnimator = cameraPlugin.createBearingAnimator(
            cameraAnimatorOptions(-.000000000000024868995751603507)
          ) {
            duration = 1800L
          }

          cameraPlugin.registerAnimators(centerAnimator, bearingAnimator)

          val set = AnimatorSet()
          set.playTogether(centerAnimator, bearingAnimator)
          set.addListener(listener)
          set.start()
        } else {
          Log.e("TEST_CAMERA", "second click")
          val centerAnimator = cameraPlugin.createCenterAnimator(
            cameraAnimatorOptions(
              Point.fromLngLat(
                17.04809100000034,
                51.12074813941152
              )
            )
          ) {
            duration = 1500L
          }

          val bearingAnimator = cameraPlugin.createBearingAnimator(
            cameraAnimatorOptions(360.0)
          ) {
            duration = 1800L
          }

          cameraPlugin.registerAnimators(centerAnimator, bearingAnimator)

          val set = AnimatorSet()
          set.playTogether(centerAnimator, bearingAnimator)
          set.addListener(listener)
          set.start()
        }
        return true
      }
    })
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
    test(mapView.getMapboxMap(), mapView.camera)
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

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}