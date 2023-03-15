package com.mapbox.maps.testapp.examples.markersandcallouts

import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.removeOnMapClickListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityIconSizeChangeOnClickBinding

class IconSizeChangeOnClickActivity : AppCompatActivity(), OnMapClickListener {

  private lateinit var mapboxMap: MapboxMap
  private var markerSelected = false
  private var markerAnimator = ValueAnimator()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityIconSizeChangeOnClickBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.loadStyle(
      styleExtension = style(Style.DARK) {
        +geoJsonSource("marker-source") {
          featureCollection(
            FeatureCollection.fromFeatures(markerCoordinates.map { Feature.fromGeometry(it) })
          )
        }
        // Add the marker image to map
        +image("my-marker-image") {
          bitmap(BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view))
        }
        // Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
        // middle of the icon being fixed to the coordinate point.
        +symbolLayer("marker-layer", "marker-source") {
          iconImage("my-marker-image")
          iconAllowOverlap(true)
          iconOffset(listOf(0.0, -9.0))
        }
        // Add the selected marker source and layer
        +geoJsonSource("selected-marker") {
          geometry(Point.fromLngLat(0.0, 0.0))
        }
        // Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
        // middle of the icon being fixed to the coordinate point.
        +symbolLayer("selected-marker-layer", "selected-marker") {
          iconImage("my-marker-image")
          iconAllowOverlap(true)
          iconOffset(listOf(0.0, -9.0))
        }
      }
    ) { mapboxMap.addOnMapClickListener(this@IconSizeChangeOnClickActivity) }
  }

  override fun onMapClick(point: Point): Boolean {
    mapboxMap.getStyle {
      val selectedMarkerSymbolLayer = it.getLayer("selected-marker-layer") as SymbolLayer
      val pixel = mapboxMap.pixelForCoordinate(point)
      mapboxMap.queryRenderedFeatures(
        RenderedQueryGeometry(screenBoxFromPixel(pixel)), RenderedQueryOptions(listOf("selected-marker-layer"), null)
      ) { expected: Expected<String, MutableList<QueriedRenderedFeature>> ->
        if (expected.value!!.isNotEmpty() && markerSelected) {
          return@queryRenderedFeatures
        }

        mapboxMap.queryRenderedFeatures(
          RenderedQueryGeometry(screenBoxFromPixel(pixel)), RenderedQueryOptions(listOf("marker-layer"), null)
        ) InnerRenderedQueryOptions@{ expectedFeatures ->
          val queriedFeatures = expectedFeatures.value!!
          if (queriedFeatures.isEmpty()) {
            if (markerSelected) {
              updateMarker(selectedMarkerSymbolLayer, false)
            }
            return@InnerRenderedQueryOptions
          }

          it.getSourceAs<GeoJsonSource>("selected-marker")!!.apply {
            queriedFeatures[0].queriedFeature.feature.geometry()?.let { value ->
              geometry(value)
            }
          }

          if (markerSelected) {
            updateMarker(selectedMarkerSymbolLayer, false)
          }
          if (queriedFeatures.isNotEmpty()) {
            updateMarker(selectedMarkerSymbolLayer, true)
          }
        }
      }
    }
    return true
  }

  private fun updateMarker(iconLayer: SymbolLayer, select: Boolean) {
    if (select) markerAnimator = ValueAnimator()
    markerAnimator.apply {
      if (select) {
        setFloatValues(1.0f, 2.0f)
      } else {
        setFloatValues(2.0f, 1.0f)
      }
      duration = 300
      addUpdateListener {
        iconLayer.iconSize((it.animatedValue as Float).toDouble())
      }
      start()
    }
    markerSelected = select
  }

  override fun onDestroy() {
    super.onDestroy()
    markerAnimator.cancel()
    mapboxMap.removeOnMapClickListener(this)
  }

  private fun screenBoxFromPixel(pixel: ScreenCoordinate) = ScreenBox(
    ScreenCoordinate(pixel.x - 25.0, pixel.y - 25.0),
    ScreenCoordinate(pixel.x + 25.0, pixel.y + 25.0)
  )

  companion object {
    private val markerCoordinates = arrayListOf<Point>(
      Point.fromLngLat(-71.065634, 42.354950), // Boston Common Park
      Point.fromLngLat(-71.097293, 42.346645), // Fenway Park
      Point.fromLngLat(-71.053694, 42.363725) // The Paul Revere House
    )
  }
}