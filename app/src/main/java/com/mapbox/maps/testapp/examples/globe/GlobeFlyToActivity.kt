package com.mapbox.maps.testapp.examples.globe

import android.graphics.Color.rgb
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.testapp.R

/**
 * Use [MapboxMap.flyTo] on a map with globe projection to slowly zoom to a location.
 */
class GlobeFlyToActivity : AppCompatActivity(), OnMapClickListener {

  private lateinit var mapboxMap: MapboxMap
  private var isAtStart = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.mapboxMap
    mapboxMap.loadStyle(
      style(Style.SATELLITE_STREETS) {
        +projection(ProjectionName.GLOBE)
        +atmosphere {
          color(rgb(220, 159, 159)) // Pink fog / lower atmosphere
          highColor(rgb(220, 159, 159)) // Blue sky / upper atmosphere
          horizonBlend(0.4) // Exaggerate atmosphere (default is .1)
        }
        +rasterDemSource("raster-dem") {
          url("mapbox://mapbox.terrain-rgb")
        }
        +terrain("raster-dem") {
          // camera seems to be a bit jumping on high zoom level - check with gl-native
          exaggeration(1.5)
        }
      }
    ) {
      // Toast instructing user to tap on the map
      Toast.makeText(
        this@GlobeFlyToActivity,
        getString(R.string.tap_on_map_instruction),
        Toast.LENGTH_LONG
      ).show()
      mapboxMap.addOnMapClickListener(this@GlobeFlyToActivity)
    }
  }

  override fun onMapClick(point: Point): Boolean {
    val target = if (isAtStart) CAMERA_END else CAMERA_START
    isAtStart = !isAtStart
    mapboxMap.flyTo(
      target,
      mapAnimationOptions {
        duration(12_000)
      }
    )
    return true
  }

  private companion object {
    private val CAMERA_START = cameraOptions {
      center(Point.fromLngLat(80.0, 36.0))
      zoom(1.0)
      pitch(0.0)
      bearing(0.0)
    }
    private val CAMERA_END = cameraOptions {
      center(Point.fromLngLat(8.11862, 46.58842))
      zoom(12.5)
      pitch(75.0)
      bearing(130.0)
    }
  }
}