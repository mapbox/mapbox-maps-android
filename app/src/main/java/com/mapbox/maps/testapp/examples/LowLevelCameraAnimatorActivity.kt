package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.zoom
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.exponential
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.FillExtrusionLayer
import com.mapbox.maps.extension.style.light.generated.getLight
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.getCameraAnimationsPlugin
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_camera_animate.*

class LowLevelCameraAnimatorActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_camera_animate)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      setupBuildings(it)
      animateCameraDelayed()
    }
  }

  private fun animateCameraDelayed() {
    mapView.getCameraAnimationsPlugin().apply {
      val bearing = createBearingAnimator(cameraAnimatorOptions(0.0, 160.0)) {
        duration = 8500
        interpolator = AnticipateOvershootInterpolator()
      }
      val zoom = createZoomAnimator(
        cameraAnimatorOptions(18.0) {
          startValue = 15.0
        }
      ) {
        duration = 5000
        interpolator = AccelerateDecelerateInterpolator()
      }
      val pitch = createPitchAnimator(
        cameraAnimatorOptions(55.0) {
          startValue = 0.0
        }
      ) {
        duration = 7000
        interpolator = BounceInterpolator()
      }
      playAnimatorsTogether(bearing, zoom, pitch)
    }
  }

  private fun setupBuildings(style: Style) {
    style.getLight().position(1.5, 90.0, 80.0)

    val fillExtrusionLayer = FillExtrusionLayer("3d-buildings", "composite")
    fillExtrusionLayer.sourceLayer("building")
    fillExtrusionLayer.filter(
      eq(
        get("extrude"),
        literal("true")
      )
    )
    fillExtrusionLayer.minZoom(16.5)
    fillExtrusionLayer.fillExtrusionColor(Color.WHITE)
    fillExtrusionLayer.fillExtrusionHeight(
      interpolate(
        exponential(literal(1.0)),
        zoom(),
        literal(16), literal(0),
        literal(18), get("height")
      )
    )
    fillExtrusionLayer.fillExtrusionBase(get(Expression.literal("min_height")))
    fillExtrusionLayer.fillExtrusionOpacity(0.6)
    style.addLayer(fillExtrusionLayer)
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