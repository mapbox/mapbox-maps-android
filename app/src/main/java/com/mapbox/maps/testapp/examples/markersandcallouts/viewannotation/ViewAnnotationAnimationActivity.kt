package com.mapbox.maps.testapp.examples.markersandcallouts.viewannotation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ItemCalloutViewBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.turf.TurfConstants.UNIT_DEFAULT
import com.mapbox.turf.TurfMeasurement
import kotlin.math.roundToLong

/**
 * Example how to animate a view annotations on top of a route.
 */
class ViewAnnotationAnimationActivity : AppCompatActivity() {

  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private lateinit var annotationView: View
  private lateinit var textView: TextView
  private var routeCoordinateList = mutableListOf<Point>()
  private var currentRouteCoordinate = 0
  private var totalLength = 0.0
  private var currentAnimator: Animator? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    // load feature collection from assets file
    val featureCollection = FeatureCollection.fromJson(
      AnnotationUtils.loadStringFromAssets(
        this, ROUTE_FILE_NAME
      )!!
    )

    // calculate the route length, get coordinates from route geometry
    val lineString = featureCollection.features()!![0].geometry() as LineString
    routeCoordinateList = lineString.coordinates()
    totalLength = TurfMeasurement.length(lineString, UNIT_DEFAULT)

    // initialize the mapview
    mapView.mapboxMap.apply {
      loadStyle(
        style(Style.LIGHT) {
          // source for displaying the route
          +geoJsonSource(SOURCE_ID) {
            featureCollection(featureCollection)
          }
          // layer for displaying the route
          +lineLayer(LINE_ID, SOURCE_ID) {
            lineColor(Color.parseColor(COLOR_PINK_HEX))
            lineWidth(4.0)
          }
        }
      ) {
        // center camera around SF airport
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(-122.3915, 37.6177))
            .zoom(11.0)
            .build()
        )
        // get initial point
        val initialPoint = routeCoordinateList[0]

        // create view annotation callout item
        viewAnnotationManager = mapView.viewAnnotationManager
        annotationView = viewAnnotationManager.addViewAnnotation(
          R.layout.item_callout_view,
          ViewAnnotationOptions.Builder()
            .geometry(initialPoint)
            .build()
        )

        ItemCalloutViewBinding.bind(annotationView).apply {
          textView = textNativeView
        }

        animateNextStep()
      }
    }
  }

  override fun onStop() {
    super.onStop()
    currentAnimator?.removeAllListeners()
    currentAnimator?.cancel()
  }

  private fun animateNextStep() {
    currentRouteCoordinate %= (routeCoordinateList.size - 1)
    val currentPoint = routeCoordinateList[currentRouteCoordinate]
    val nextPoint = routeCoordinateList[currentRouteCoordinate + 1]

    val progress = TurfMeasurement.distance(currentPoint, nextPoint) / totalLength
    val animateDuration = progress * ANIMATION_DURATION_TOTAL_MS
    currentAnimator = ValueAnimator.ofObject(pointEvaluator, currentPoint, nextPoint).apply {
      duration = animateDuration.roundToLong()
      addUpdateListener(animatorUpdateListener)
      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          super.onAnimationEnd(animation)
          currentRouteCoordinate++
          animateNextStep()
        }
      })
      start()
    }
  }

  private val animatorUpdateListener =
    AnimatorUpdateListener { valueAnimator ->
      val animatedPosition: Point = valueAnimator.animatedValue as Point
      viewAnnotationManager.updateViewAnnotation(
        annotationView,
        ViewAnnotationOptions.Builder().geometry(animatedPosition).build()
      )
      textView.text = "lat=%.4f\nlon=%.4f".format(
        animatedPosition.latitude(),
        animatedPosition.longitude()
      )
    }

  private val pointEvaluator: TypeEvaluator<Point> =
    TypeEvaluator<Point> { fraction, startValue, endValue ->
      Point.fromLngLat(
        (
          startValue.longitude() +
            ((endValue.longitude() - startValue.longitude()) * fraction)
          ),
        startValue.latitude() +
          (endValue.latitude() - startValue.latitude()) * fraction
      )
    }

  private companion object {
    const val LINE_ID = "line"
    const val SOURCE_ID = "source"
    const val COLOR_PINK_HEX = "#F13C6E"
    const val ANIMATION_DURATION_TOTAL_MS = 50000
    const val ROUTE_FILE_NAME = "sf_airport_route.geojson"
  }
}