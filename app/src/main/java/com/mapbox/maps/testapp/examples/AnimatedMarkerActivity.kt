package com.mapbox.maps.testapp.examples

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
import kotlinx.android.synthetic.main.activity_animated_marker.*

/**
 * Example of animating a map marker on click.
 */
class AnimatedMarkerActivity : AppCompatActivity(), OnMapClickListener {

  private lateinit var geojsonSource: GeoJsonSource
  private var currentPoint = Point.fromLngLat(-18.167040, 64.900932)
  private var animator: ValueAnimator? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animated_marker)

    geojsonSource = geoJsonSource("source-id") {
      feature(Feature.fromGeometry(currentPoint))
    }

    val mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyle(
      style(Style.SATELLITE_STREETS) {
        +image("marker_icon") {
          bitmap(BitmapFactory.decodeResource(resources, R.drawable.red_marker))
        }
        +geojsonSource
        +symbolLayer(layerId = "layer-id", sourceId = "source-id") {
          iconImage("marker_icon")
          iconSize(0.4)
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
    var tick = 0
    animator = ValueAnimator().apply {
      setObjectValues(currentPoint, point)
      setEvaluator(pointEvaluator)
      addUpdateListener {
        // TODO remove throttle when https://github.com/mapbox/mapbox-maps-android/issues/554 fixed
        if (++tick % 2 == 0) {
          geojsonSource.geometry(it.animatedValue as Point)
        }
      }
      duration = 2000
      start()
    }
    currentPoint = point
    return true
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
    animator?.cancel()
    mapView.getMapboxMap().removeOnMapClickListener(this)
    mapView.onDestroy()
  }
}