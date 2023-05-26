package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.overlay.MapOverlayCoordinatesProvider
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin
import com.mapbox.maps.plugin.overlay.mapboxOverlay
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityMapOverlayBinding

@MapboxExperimental
class MapOverlayActivity : AppCompatActivity(), OnMapClickListener {

  private val markerCoordinates = mutableListOf<Point>(
    Point.fromLngLat(-71.065634, 42.354950), // Boston Common Park
    Point.fromLngLat(-71.097293, 42.346645), // Fenway Park
    Point.fromLngLat(-71.053694, 42.363725) // The Paul Revere House
  )
  private lateinit var mapOverlayPlugin: MapOverlayPlugin
  private lateinit var mapboxMap: MapboxMap
  private val provider = MapOverlayCoordinatesProvider { markerCoordinates }
  private lateinit var binding: ActivityMapOverlayBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMapOverlayBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.getMapboxMap()
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
        +image(imageId, BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view))
        +symbolLayer(layerId, sourceId) {
          iconImage(imageId)
          iconAllowOverlap(true)
          iconOffset(listOf(0.0, -9.0))
        }
      }
    ) { mapboxMap.addOnMapClickListener(this@MapOverlayActivity) }
    mapOverlayPlugin = binding.mapView.mapboxOverlay
      .apply {
        registerMapOverlayCoordinatesProvider(provider)
        registerOverlay(binding.locationTopLeft)
        registerOverlay(binding.locationTopRight)
        registerOverlay(binding.locationBottomLeft)
        registerOverlay(binding.locationBottomRight)
        registerOverlay(binding.reframeButton)
        setDisplayingAreaMargins(100, 50, 50, 50)
      }

    val cameraAnimationsPlugin = binding.mapView.camera
    binding.reframeButton.setOnClickListener {
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

  companion object {
    private const val sourceId = "marker-source"
    private const val imageId = "marker-image"
    private const val layerId = "marker-layer"
  }
}