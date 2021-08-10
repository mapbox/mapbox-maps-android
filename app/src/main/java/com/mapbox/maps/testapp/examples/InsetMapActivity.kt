package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityInsetMapBinding
import com.mapbox.maps.testapp.examples.fragment.MapFragment

/**
 * Example demonstrating displaying two maps: main map and small map with lower zoom
 * in bottom-right corner with optional bounds showing what area is covered by main map.
 */
class InsetMapActivity : AppCompatActivity(), OnCameraChangeListener {

  private lateinit var mainMapboxMap: MapboxMap
  private lateinit var insetMapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityInsetMapBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mainMapboxMap = binding.mapView.getMapboxMap()
    mainMapboxMap.loadStyleUri(
      styleUri = STYLE_URL
    ) { mainMapboxMap.addOnCameraChangeListener(this@InsetMapActivity) }

    var insetMapFragment: MapFragment? =
      supportFragmentManager.findFragmentByTag(INSET_FRAGMENT_TAG) as? MapFragment
    if (insetMapFragment == null) {
      // Create fragment transaction for the inset fragment
      val transaction = supportFragmentManager.beginTransaction()

      insetMapFragment = MapFragment()
      insetMapFragment.getMapAsync {
        insetMapboxMap = it
        insetMapboxMap.loadStyleUri(
          styleUri = STYLE_URL
        ) { style ->
          val source = geoJsonSource(BOUNDS_LINE_LAYER_SOURCE_ID) {
            feature(Feature.fromGeometry(LineString.fromLngLats(getRectanglePoints())))
          }
          style.addSource(source)

          // The layer properties for our line. This is where we make the line dotted, set the color, etc.
          val layer = lineLayer(BOUNDS_LINE_LAYER_LAYER_ID, BOUNDS_LINE_LAYER_SOURCE_ID) {
            lineCap(LineCap.ROUND)
            lineJoin(LineJoin.ROUND)
            lineWidth(3.0)
            lineColor(Color.YELLOW)
            visibility(Visibility.VISIBLE)
          }
          style.addLayer(layer)
          updateInsetMapLineLayerBounds(style)
        }
        insetMapFragment.getMapView()?.apply {
          logo.enabled = false
          scalebar.enabled = false
          attribution.enabled = false
          compass.enabled = false

          gestures.updateSettings {
            scrollEnabled = false
            zoomEnabled = false
          }
        }
      }

      // Add fragmentMap fragment to parent container
      transaction.add(R.id.mini_map_fragment_container, insetMapFragment, INSET_FRAGMENT_TAG)
      transaction.commit()
    }

    binding.showBoundsToggleFab.setOnClickListener {
      // Toggle the visibility of the camera bounds LineLayer
      insetMapboxMap.getStyle { style ->
        style.getLayer(BOUNDS_LINE_LAYER_LAYER_ID)?.apply {
          visibility(if (visibility == Visibility.VISIBLE) Visibility.NONE else Visibility.VISIBLE)
        }
      }
    }
  }

  override fun onCameraChanged() {
    val mainCameraPosition = mainMapboxMap.cameraState
    val insetCameraPosition = CameraOptions.Builder()
      .zoom(mainCameraPosition.zoom.minus(ZOOM_DISTANCE_BETWEEN_MAIN_AND_INSET_MAPS))
      .pitch(mainCameraPosition.pitch)
      .bearing(mainCameraPosition.bearing)
      .center(mainCameraPosition.center)
      .build()
    insetMapboxMap.setCamera(insetCameraPosition)
    insetMapboxMap.getStyle { style -> updateInsetMapLineLayerBounds(style) }
  }

  private fun updateInsetMapLineLayerBounds(fullyLoadedStyle: Style) {
    (fullyLoadedStyle.getSource(BOUNDS_LINE_LAYER_SOURCE_ID) as? GeoJsonSource)?.apply {
      feature(Feature.fromGeometry(LineString.fromLngLats(getRectanglePoints())))
    }
  }

  private fun getRectanglePoints(): List<Point> {
    val bounds = mainMapboxMap.coordinateBoundsForCamera(
      mainMapboxMap.cameraState.toCameraOptions()
    )
    return listOf(
      Point.fromLngLat(bounds.northeast.longitude(), bounds.northeast.latitude()),
      Point.fromLngLat(bounds.northeast.longitude(), bounds.southwest.latitude()),
      Point.fromLngLat(bounds.southwest.longitude(), bounds.southwest.latitude()),
      Point.fromLngLat(bounds.southwest.longitude(), bounds.northeast.latitude()),
      Point.fromLngLat(bounds.northeast.longitude(), bounds.northeast.latitude())
    )
  }

  override fun onDestroy() {
    super.onDestroy()
    mainMapboxMap.removeOnCameraChangeListener(this)
  }

  companion object {
    private const val STYLE_URL = "mapbox://styles/mapbox/cj5l80zrp29942rmtg0zctjto"
    private const val INSET_FRAGMENT_TAG = "com.mapbox.insetMapFragment"
    private const val BOUNDS_LINE_LAYER_SOURCE_ID = "BOUNDS_LINE_LAYER_SOURCE_ID"
    private const val BOUNDS_LINE_LAYER_LAYER_ID = "BOUNDS_LINE_LAYER_LAYER_ID"
    private const val ZOOM_DISTANCE_BETWEEN_MAIN_AND_INSET_MAPS = 3
  }
}