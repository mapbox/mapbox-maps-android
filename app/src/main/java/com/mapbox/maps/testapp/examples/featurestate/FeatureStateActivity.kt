package com.mapbox.maps.testapp.examples.featurestate

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.geojson.Feature
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.switchCase
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example of feature state API.
 * Which allows to add additional feature state to an existing data source.
 * In this example we add a geojson source that represents the countries around the world.
 * When a country is clicked, we highlight it.
 */
class FeatureStateActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapView.getMapboxMap().apply {
      loadStyleUri(Style.MAPBOX_STREETS) {
        // Add source for the countries data
        it.addSource(geoJsonSource(SOURCE_ID) {
          url(SOURCE_URL)
        })

        // Add fill layer to visualize the countries
        it.addLayerBelow(fillLayer(LAYER_ID, SOURCE_ID) {
          fillColor(Color.RED)
          sourceLayer(SOURCE_LAYER)
          fillOutlineColor(Color.YELLOW)
          fillOpacity(
            switchCase {
              boolean {
                featureState { literal("hover") }
                literal(false)
              }
              literal(1.0)
              literal(0.2)
            })
        }, LAYER_ID_BELOW)
      }

      // Add on map click listener to get notified when the clicks the map
      addOnMapClickListener { point ->
        // Validate if the click occurred on the runtime addded layer
        queryRenderedFeatures(
          pixelForCoordinate(point),
          RenderedQueryOptions(listOf(LAYER_ID), null)
        ) { expectedFeatures ->
          val features = expectedFeatures.value as List<Feature>
          // For every feature clicked, update the feature state
          for (feature in features) {
            val featureId = feature.id()
            if (featureId != null) {
              Logger.i(TAG, "Feature clicked: $feature")

              // update the hover state of a feature to true
              setFeatureState(
                SOURCE_ID, SOURCE_LAYER, featureId,
                Value.valueOf(hashMapOf(Pair("hover", Value.valueOf(true))))
              )

              // test to validate if feature state was updated
              getFeatureState(SOURCE_ID, SOURCE_LAYER, featureId) {
                Logger.i(TAG, "Feature state: " + it.value)
              }
            }
          }
        }
        true
      }
    }
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
    const val TAG = "FeatureState"
    const val LAYER_ID = "fs_layer_id"
    const val LAYER_ID_BELOW = "country-label"
    const val SOURCE_ID = "fs_source_id"
    const val SOURCE_LAYER = "fs_source_layer"
    const val SOURCE_URL =
      "https://raw.githubusercontent.com/johan/world.geo.json/master/countries.geo.json"
  }
}