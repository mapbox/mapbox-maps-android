package com.mapbox.maps.testapp.examples.annotation

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import com.mapbox.geojson.Point
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.MapLoaded
import com.mapbox.maps.MapLoadedCallback
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.properties.generated.IconPitchAlignment
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes
import com.mapbox.maps.toCameraOptions
import com.mapbox.turf.TurfMeasurement
import java.util.Random

/**
 * Example showing how to add point annotations and animate them
 */
class AnimatePointAnnotationActivity : AppCompatActivity(), MapLoadedCallback {
  private var pointAnnotationManager: PointAnnotationManager? = null
  private var animateCarList = listOf<PointAnnotation>()
  private val animators: MutableList<ValueAnimator> = mutableListOf()
  private var noAnimateCarNum: Int = 10
  private var animateCarNum: Int = 10
  private var animateDuration = 5000L
  private lateinit var mapboxMap: MapboxMap
  private lateinit var binding: ActivityAnnotationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAnnotationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.apply {
      setCamera(
        cameraOptions {
          center(
            Point.fromLngLat(
              LONGITUDE,
              LATITUDE
            )
          )
          zoom(12.0)
        }
      )
      subscribeMapLoaded(this@AnimatePointAnnotationActivity)
    }

    binding.deleteAll.visibility = View.GONE
    binding.changeStyle.visibility = View.GONE
    binding.changeSlot.visibility = View.GONE
  }

  override fun run(eventData: MapLoaded) {
    pointAnnotationManager = binding.mapView.annotations.createPointAnnotationManager().apply {
      iconPitchAlignment = IconPitchAlignment.MAP
      val carTop = bitmapFromDrawableRes(R.drawable.ic_car_top)
      val noAnimationOptionList = List(noAnimateCarNum) {
        PointAnnotationOptions()
          .withPoint(getPointInBounds())
          .withIconImage(carTop)
      }
      create(noAnimationOptionList)

      val taxiTop = bitmapFromDrawableRes(R.drawable.ic_taxi_top)
      val animationOptionList = List(animateCarNum) {
        PointAnnotationOptions()
          .withPoint(getPointInBounds())
          .withIconImage(taxiTop)
      }
      animateCarList = create(animationOptionList)
    }
    animateCars()
  }

  override fun onDestroy() {
    super.onDestroy()
    cleanAnimation()
  }

  private fun animateCars() {
    cleanAnimation()
    val carEvaluator = CarEvaluator()
    animateCarList.forEach { animatedCar ->
      val nextPoint = getPointInBounds()
      val animator =
        ValueAnimator.ofObject(
          carEvaluator,
          animatedCar.point,
          nextPoint
        ).setDuration(animateDuration)
      animatedCar.iconRotate = TurfMeasurement.bearing(animatedCar.point, nextPoint)
      animator.interpolator = LinearInterpolator()
      animator.addUpdateListener { valueAnimator ->
        animatedCar.point = valueAnimator.animatedValue as Point
      }
      animator.start()
      animators.add(animator)
    }
    // Endless repeat animations
    animators[0].addListener(
      onEnd = {
        runOnUiThread {
          animateCars()
        }
      }
    )
    animators[0].addUpdateListener {
      // Update all annotations in batch
      pointAnnotationManager?.update(animateCarList)
    }
  }

  private fun getPointInBounds(): Point {
    val bounds: CoordinateBounds =
      mapboxMap.coordinateBoundsForCamera(mapboxMap.cameraState.toCameraOptions())
    val generator = Random()
    val lat =
      bounds.southwest.latitude() + (bounds.northeast.latitude() - bounds.southwest.latitude()) * generator.nextDouble()
    val lon =
      bounds.southwest.longitude() + (bounds.northeast.longitude() - bounds.southwest.longitude()) * generator.nextDouble()
    return Point.fromLngLat(lon, lat)
  }

  private fun cleanAnimation() {
    animators.forEach {
      it.removeAllListeners()
      it.cancel()
    }
    animators.clear()
  }

  private class CarEvaluator : TypeEvaluator<Point> {
    override fun evaluate(
      fraction: Float,
      startValue: Point,
      endValue: Point
    ): Point {
      val lat =
        startValue.latitude() + (endValue.latitude() - startValue.latitude()) * fraction
      val lon =
        startValue.longitude() + (endValue.longitude() - startValue.longitude()) * fraction
      return Point.fromLngLat(lon, lat)
    }
  }

  companion object {
    private const val LATITUDE = 55.665957
    private const val LONGITUDE = 12.550343
  }
}