package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.getCameraAnimationsPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.overlay.MapOverlayCoordinatesProvider
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin
import com.mapbox.maps.plugin.overlay.getMapOverlayPlugin
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_map_overlay.*

class MapOverlayActivity : AppCompatActivity(), OnMapClickListener {

  private val markerCoordinates = mutableListOf<Point>(
    Point.fromLngLat(-71.065634, 42.354950), // Boston Common Park
    Point.fromLngLat(-71.097293, 42.346645), // Fenway Park
    Point.fromLngLat(-71.053694, 42.363725) // The Paul Revere House
  )
  private lateinit var mapOverlayPlugin: MapOverlayPlugin
  private lateinit var mapboxMap: MapboxMap
  private val provider = MapOverlayCoordinatesProvider { markerCoordinates }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map_overlay)

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyle(
      styleExtension = style(Style.LIGHT) {
        +geoJsonSource(sourceId) {
          featureCollection(
            FeatureCollection.fromFeatures(
              markerCoordinates.map {
                Feature.fromGeometry(it)
              }
            )
          )
        }
        // Add the marker image to map
        +image(imageId) {
          bitmap(BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view))
        }
        +symbolLayer(layerId, sourceId) {
          iconImage(imageId)
          iconAllowOverlap(true)
          iconOffset(listOf(0.0, -9.0))
        }
      }
    ) { mapboxMap.addOnMapClickListener(this@MapOverlayActivity) }
    mapOverlayPlugin = mapView.getMapOverlayPlugin()
      .apply {
        registerMapOverlayCoordinatesProvider(provider)
        registerOverlay(location_top_left)
        registerOverlay(location_top_right)
        registerOverlay(location_bottom_left)
        registerOverlay(location_bottom_right)
        registerOverlay(reframe_button)
        setDisplayingAreaMargins(100, 50, 50, 50)
      }

    val cameraAnimationsPlugin = mapView.getCameraAnimationsPlugin()
    reframe_button.setOnClickListener {
      mapOverlayPlugin.reframe { it?.let { cameraAnimationsPlugin.flyTo(it, mapAnimationOptions { duration(1000L) }) } }
    }
  }

  override fun onMapClick(point: Point): Boolean {
    markerCoordinates.add(point)
    mapboxMap.getStyle()?.getSourceAs<GeoJsonSource>(sourceId)!!.featureCollection(
      FeatureCollection.fromFeatures(
        markerCoordinates.map {
          Feature.fromGeometry(it)
        }
      )
    )
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
    mapView.onDestroy()
  }

  companion object {
    private const val sourceId = "marker-source"
    private const val imageId = "marker-image"
    private const val layerId = "marker-layer"
  }
}