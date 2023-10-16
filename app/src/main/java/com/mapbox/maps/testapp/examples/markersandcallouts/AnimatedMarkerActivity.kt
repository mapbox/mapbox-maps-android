package com.mapbox.maps.testapp.examples.markersandcallouts

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.removeOnMapClickListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityAnimatedMarkerBinding

/**
 * Example of animating a map marker on click.
 */
class AnimatedMarkerActivity : AppCompatActivity(), OnMapClickListener {

  private lateinit var geojsonSource: GeoJsonSource
  private var currentPoint = Point.fromLngLat(-18.167040, 64.900932)
  private var animator: ValueAnimator? = null
  private lateinit var binding: ActivityAnimatedMarkerBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAnimatedMarkerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    geojsonSource = geoJsonSource("source-id") {
      feature(Feature.fromGeometry(currentPoint))
    }

    val mapboxMap = binding.mapView.mapboxMap
    mapboxMap.loadStyle(
      style(Style.SATELLITE_STREETS) {
        +image("marker_icon", BitmapFactory.decodeResource(resources, R.drawable.red_marker))
        +geojsonSource
        +symbolLayer(layerId = "layer-id", sourceId = "source-id") {
          iconImage("marker_icon")
          iconIgnorePlacement(true)
          iconAllowOverlap(true)
        }
      }
    ) {
      Toast.makeText(
        this@AnimatedMarkerActivity,
        getString(R.string.tap_on_map_instruction),
        Toast.LENGTH_LONG
      ).show()
      mapboxMap.addOnMapClickListener(this@AnimatedMarkerActivity)
    }
  }

  override fun onMapClick(point: Point): Boolean {
    // When the user clicks on the map, we want to animate the marker to that location.
    animator?.let {
      if (it.isStarted) {
        currentPoint = it.animatedValue as Point
        it.cancel()
      }
    }

    val pointEvaluator = TypeEvaluator<Point> { fraction, startValue, endValue ->
      Point.fromLngLat(
        startValue.longitude() + fraction * (endValue.longitude() - startValue.longitude()),
        startValue.latitude() + fraction * (endValue.latitude() - startValue.latitude())
      )
    }
    animator = ValueAnimator().apply {
      setObjectValues(currentPoint, point)
      setEvaluator(pointEvaluator)
      addUpdateListener {
        geojsonSource.geometry(it.animatedValue as Point)
      }
      duration = 2000
      start()
    }
    currentPoint = point
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    animator?.cancel()
    binding.mapView.mapboxMap.removeOnMapClickListener(this)
  }
}