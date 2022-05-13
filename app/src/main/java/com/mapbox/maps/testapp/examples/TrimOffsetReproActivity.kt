package com.mapbox.maps.testapp.examples

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.testapp.databinding.ActivityTrimOffsetReproBinding
import java.io.File
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Load a raster image to a style using ImageSource and display it on a map as
 * animated weather data using RasterLayer.
 */
class TrimOffsetReproActivity : AppCompatActivity() {

  private val navigationLocationProvider = NavigationLocationProvider()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityTrimOffsetReproBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val file = File(filesDir, "geometry.json")
    val contents = file.readText()
    val source = GeoJsonSource.Builder(
      "source"
    )
      .geometry(LineString.fromJson(contents))
      .lineMetrics(true)
      .build()
    val layer = LineLayer("layer", "source")
      .lineGradient(
        interpolate {
          linear()
          lineProgress()
          literal(0.0)
          rgba {
            literal(6.0)
            literal(1.0)
            literal(255.0)
            literal(1.0)
          }
        }
      )
      .lineColor(
        rgba {
          literal(0.0)
          literal(0.0)
          literal(255.0)
          literal(0.5)
        }
      )
      .lineWidth(15.0)
      .lineTrimOffset(literal(listOf(0.0, 0.9109045680404019)))
    binding.mapView.getMapboxMap().loadStyleUri(
      Style.MAPBOX_STREETS
    ) { style ->
      style.addSource(source)
      style.addPersistentLayer(layer)
    }

    // initialize the location puck
    binding.mapView.location.apply {
      this.locationPuck = LocationPuck2D(
        bearingImage = ContextCompat.getDrawable(
          this@TrimOffsetReproActivity,
          android.R.drawable.ic_media_play
        )
      )
      setLocationProvider(navigationLocationProvider)
      enabled = true
    }

    Handler().postDelayed({
      navigationLocationProvider.changePosition(
        Location("mock").apply {
          latitude = 38.587603914479644
          longitude = -3.4742071266371766
        }
      )
    }, 1000)
    navigationLocationProvider.changePosition(
      Location("mock").apply {
        latitude = 38.587603914479644
        longitude = -3.4742071266371766
      }
    )
  }
}

class NavigationLocationProvider : LocationProvider {

  private val locationConsumers = CopyOnWriteArraySet<LocationConsumer>()

  var lastLocation: Location? = null
    private set

  var lastKeyPoints: List<Location> = emptyList()
    private set

  @SuppressLint("MissingPermission")
  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    if (locationConsumers.add(locationConsumer)) {
      val location = lastLocation
      if (location != null) {
        locationConsumer.notifyLocationUpdates(
          location = location,
          keyPoints = emptyList(),
          latLngTransitionOptions = {
            this.duration = 0
          },
          bearingTransitionOptions = {
            this.duration = 0
          }
        )
      }
    }
  }

  override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
    locationConsumers.remove(locationConsumer)
  }

  fun changePosition(
    location: Location,
    keyPoints: List<Location> = emptyList(),
    latLngTransitionOptions: (ValueAnimator.() -> Unit)? = null,
    bearingTransitionOptions: (ValueAnimator.() -> Unit)? = null
  ) {
    locationConsumers.forEach {
      it.notifyLocationUpdates(
        location,
        keyPoints,
        latLngTransitionOptions,
        bearingTransitionOptions
      )
    }
    lastLocation = location
    lastKeyPoints = keyPoints
  }

  private fun LocationConsumer.notifyLocationUpdates(
    location: Location,
    keyPoints: List<Location>,
    latLngTransitionOptions: (ValueAnimator.() -> Unit)? = null,
    bearingTransitionOptions: (ValueAnimator.() -> Unit)? = null
  ) {
    val latLngUpdates = if (keyPoints.isNotEmpty()) {
      keyPoints.map { Point.fromLngLat(it.longitude, it.latitude) }.toTypedArray()
    } else {
      arrayOf(Point.fromLngLat(location.longitude, location.latitude))
    }
    val bearingUpdates = if (keyPoints.isNotEmpty()) {
      keyPoints.map { it.bearing.toDouble() }.toDoubleArray()
    } else {
      doubleArrayOf(location.bearing.toDouble())
    }
    this.onLocationUpdated(location = latLngUpdates, options = latLngTransitionOptions)
    this.onBearingUpdated(bearing = bearingUpdates, options = bearingTransitionOptions)
  }
}