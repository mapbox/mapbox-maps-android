package com.mapbox.maps.testapp.examples.camera

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.CameraBoundsOptions
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityRestrictBoundsBinding

/**
 * Test activity showcasing restricting user gestures to a bounds around Iceland, almost worldview and IDL.
 */
class RestrictBoundsActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var binding: ActivityRestrictBoundsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRestrictBoundsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.loadStyle(
      style(Style.MAPBOX_STREETS) {
        +geoJsonSource(BOUNDS_ID) {
          featureCollection(FeatureCollection.fromFeatures(listOf()))
        }
      }
    ) { setupBounds(SAN_FRANCISCO_BOUND) }
    showCrosshair()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_bounds, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_action_san_francisco_bounds -> {
        setupBounds(SAN_FRANCISCO_BOUND)
        true
      }
      R.id.menu_action_allmost_world_bounds -> {
        setupBounds(ALMOST_WORLD_BOUNDS)
        true
      }
      R.id.menu_action_cross_idl -> {
        setupBounds(CROSS_IDL_BOUNDS)
        true
      }
      R.id.menu_action_reset -> {
        setupBounds(INFINITE_BOUNDS)
        true
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }

  private fun setupBounds(bounds: CameraBoundsOptions) {
    mapboxMap.setBounds(bounds)
    showBoundsArea(bounds)
  }

  private fun showBoundsArea(boundsOptions: CameraBoundsOptions) {
    val source = mapboxMap.getStyle()!!.getSource(BOUNDS_ID) as GeoJsonSource
    val bounds = boundsOptions.bounds
    val list = mutableListOf<List<Point>>()
    bounds?.let {
      if (!it.infiniteBounds) {
        val northEast = it.northeast
        val southWest = it.southwest
        val northWest = Point.fromLngLat(southWest.longitude(), northEast.latitude())
        val southEast = Point.fromLngLat(northEast.longitude(), southWest.latitude())
        list.add(
          mutableListOf(northEast, southEast, southWest, northWest, northEast)
        )
      }
    }
    source.geometry(
      Polygon.fromLngLats(
        list
      )
    )
  }

  private fun showCrosshair() {
    val crosshair = View(this)
    crosshair.layoutParams = FrameLayout.LayoutParams(10, 10, Gravity.CENTER)
    crosshair.setBackgroundColor(Color.BLUE)
    binding.mapView.addView(crosshair)
  }

  companion object {
    private const val BOUNDS_ID = "BOUNDS_ID"
    private val SAN_FRANCISCO_BOUND: CameraBoundsOptions = CameraBoundsOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(-122.66336, 37.492987),
          Point.fromLngLat(-122.250481, 37.87165),
          false
        )
      )
      .minZoom(10.0)
      .build()

    private val ALMOST_WORLD_BOUNDS: CameraBoundsOptions = CameraBoundsOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(-170.0, -20.0),
          Point.fromLngLat(170.0, 20.0),
          false
        )
      )
      .minZoom(2.0)
      .build()

    @SuppressLint("Range")
    private val CROSS_IDL_BOUNDS: CameraBoundsOptions = CameraBoundsOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(170.0202020, -20.0),
          Point.fromLngLat(190.0, 20.0),
          false
        )
      )
      .minZoom(2.0)
      .build()

    private val INFINITE_BOUNDS: CameraBoundsOptions = CameraBoundsOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(0.0, 0.0),
          Point.fromLngLat(0.0, 0.0),
          true
        )
      )
      .build()
  }
}