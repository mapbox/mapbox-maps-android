package com.mapbox.maps.testapp.examples

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
import com.mapbox.maps.BoundOptions
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_restrict_bounds.*

/**
 * Test activity showcasing restricting user gestures to a bounds around Iceland, almost worldview and IDL.
 */
class RestrictBoundsActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_restrict_bounds)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyle(
      style(Style.SATELLITE_STREETS) {
        +geoJsonSource(BOUNDS_ID) {
          featureCollection(FeatureCollection.fromFeatures(listOf()))
        }
        +fillLayer(BOUNDS_ID, BOUNDS_ID) {
          fillColor(Color.RED)
          fillOpacity(0.333)
        }
      }
    ) { setupBounds(ICELAND_BOUNDS) }
    showCrosshair()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_bounds, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_action_iceland_bounds -> {
        setupBounds(ICELAND_BOUNDS)
        return true
      }
      R.id.menu_action_allmost_world_bounds -> {
        setupBounds(ALMOST_WORLD_BOUNDS)
        return true
      }
      R.id.menu_action_cross_idl -> {
        setupBounds(CROSS_IDL_BOUNDS)
        return true
      }
      R.id.menu_action_reset -> {
        setupBounds(INFINITE_BOUNDS)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun setupBounds(bounds: BoundOptions) {
    mapboxMap.setBounds(bounds)
    showBoundsArea(bounds)
  }

  private fun showBoundsArea(boundsOptions: BoundOptions) {
    val source = mapboxMap.getStyle()!!.getSource(BOUNDS_ID) as GeoJsonSource
    val bounds = boundsOptions.bounds
    val list = mutableListOf<List<Point>>()
    if (!boundsOptions.infiniteBounds && bounds != null) {
      val northEast = bounds.northeast
      val southWest = bounds.southwest
      val northWest = Point.fromLngLat(southWest.longitude(), northEast.latitude())
      val southEast = Point.fromLngLat(northEast.longitude(), southWest.latitude())
      list.add(
        mutableListOf(northEast, southEast, southWest, northWest, northEast)
      )
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
    mapView.addView(crosshair)
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

  companion object {
    private val BOUNDS_ID = "BOUNDS_ID"
    private val ICELAND_BOUNDS: BoundOptions = BoundOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(-25.985652, 62.985661),
          Point.fromLngLat(-12.626277, 66.852863)
        )
      )
      .minZoom(2.0)
      .build()

    private val ALMOST_WORLD_BOUNDS: BoundOptions = BoundOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(-170.0, -20.0),
          Point.fromLngLat(170.0, 20.0)
        )
      )
      .minZoom(2.0)
      .build()

    @SuppressLint("Range")
    private val CROSS_IDL_BOUNDS: BoundOptions = BoundOptions.Builder()
      .bounds(
        CoordinateBounds(
          Point.fromLngLat(170.0202020, -20.0),
          Point.fromLngLat(190.0, 20.0)
        )
      )
      .minZoom(2.0)
      .build()

    private val INFINITE_BOUNDS: BoundOptions = BoundOptions.Builder().infiniteBounds(true).build()
  }
}