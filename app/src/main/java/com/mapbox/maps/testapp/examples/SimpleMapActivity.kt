package com.mapbox.maps.testapp.examples

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(-74.5, 40.0))
            .zoom(9.0)
            .build()
        )
      }
    startAnim()
  }

  private var count = 0

  private fun startAnim() {
    count++
    val point = if (count.rem(2) == 0) Point.fromLngLat(-74.5, 40.0) else Point.fromLngLat(-122.4194, 37.7749)
    val zoom = if (count.rem(2) == 0) 9.0 else 3.0
    mapboxMap.easeTo(
      CameraOptions.Builder().center(point).zoom(zoom).build(),
      MapAnimationOptions.mapAnimationOptions {
        duration(7_000)
        animatorListener(object : Animator.AnimatorListener {
          override fun onAnimationStart(animation: Animator?) {
          }

          override fun onAnimationEnd(animation: Animator?) {
            startAnim()
          }

          override fun onAnimationCancel(animation: Animator?) {
          }

          override fun onAnimationRepeat(animation: Animator?) {
          }

        })
      }
    )
  }
}