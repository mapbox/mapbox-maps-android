package com.mapbox.maps.compose.testapp.examples.style

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.rememberStandardStyleState
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Example demonstrating the Appearances API for dynamic icon and text states.
 * Shows how to use appearances with feature-state to change icon images and paint properties
 * based on user interaction.
 * - Default: hotel icon with dark label
 * - Currently Selected: hotel-active icon, floats up with blue label and halo
 * - Previously Clicked: hotel-clicked icon, dimmed
 */
public class AppearancesActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val hotelBitmap = BitmapFactory.decodeResource(resources, R.drawable.hotel)
    val hotelActiveBitmap = BitmapFactory.decodeResource(resources, R.drawable.hotel_active)
    val hotelClickedBitmap = BitmapFactory.decodeResource(resources, R.drawable.hotel_clicked)
    setContent {
      var selectedFeature by remember { mutableStateOf<FeaturesetFeature<FeatureState>?>(null) }
      val clickedFeatures = remember { mutableSetOf<FeaturesetFeature<FeatureState>>() }

      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(Point.fromLngLat(1.8447281852, 42.10025506))
                zoom(15.5)
                pitch(0.0)
                bearing(0.0)
              }
            },
            mapState = rememberMapState(),
            style = {
              MapboxStandardStyle(
                standardStyleState = rememberStandardStyleState {
                  // When a hotel icon is clicked, set the currentlySelected feature state to true,
                  // unselect the previous one if any, and store this feature both as the selected
                  // feature and in the list of features that have been clicked
                  interactionsState.onLayerClicked("points") { feature, _ ->
                    // Clear the currently selected feature by resetting its feature state
                    selectedFeature?.setFeatureState(
                      FeatureState { addBooleanState(CURRENTLY_SELECTED_KEY, false) }
                    ) {}

                    // Store this feature as the currently selected feature and in the list
                    // of features that have been clicked
                    clickedFeatures.add(feature)
                    feature.setFeatureState(
                      FeatureState {
                        addBooleanState(CURRENTLY_SELECTED_KEY, true)
                        addBooleanState(HAS_BEEN_CLICKED_KEY, true)
                      }
                    ) {}
                    selectedFeature = feature
                    true
                  }

                  // When the map is clicked outside of any feature, unselect the currently selected
                  // feature if there's any, or remove all features from the list of features that
                  // have been clicked to get back to the initial state
                  interactionsState.onMapClicked {
                    if (selectedFeature != null) {
                      // Unselect the currently selected feature
                      selectedFeature?.setFeatureState(
                        FeatureState { addBooleanState(CURRENTLY_SELECTED_KEY, false) }
                      ) {}
                      selectedFeature = null
                    } else {
                      // Reset the state of all features to the default one
                      clickedFeatures.forEach { clickedFeature ->
                        clickedFeature.setFeatureState(
                          FeatureState { addBooleanState(HAS_BEEN_CLICKED_KEY, false) }
                        ) {}
                      }
                      clickedFeatures.clear()
                    }
                    true
                  }
                }
              )
            }
          ) {
            // Add images, source, and layer after the style has loaded
            MapEffect(Unit) { mapView ->
              mapView.mapboxMap.subscribeStyleLoaded {
                mapView.mapboxMap.style?.let { style ->

                  // Load an image for every feature state
                  style.addImage("hotel", hotelBitmap)
                  style.addImage("hotel-active", hotelActiveBitmap)
                  style.addImage("hotel-clicked", hotelClickedBitmap)

                  // Add a GeoJSON source with hotel locations
                  style.addStyleSource(
                    "points",
                    Value.fromJson(
                      """
                      {
                        "type": "geojson",
                        "data": $HOTEL_GEOJSON
                      }
                    """.trimIndent()
                    ).value!!
                  )

                  // Add a symbol layer with appearances that change both layout and paint
                  // properties based on feature state:
                  // - "clicked": floats icon up, changes label to blue with halo
                  // - "has-been-clicked": dims icon and label (opacity 0.45)
                  try {
                    style.addStyleLayer(
                      Value.fromJson(POINTS_LAYER_JSON).value!!,
                      LayerPosition(null, null, null)
                    )
                  } catch (e: Exception) {
                    Log.e("Appearances", "Error adding layer", e)
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    private const val CURRENTLY_SELECTED_KEY = "currentlySelected"
    private const val HAS_BEEN_CLICKED_KEY = "hasBeenClicked"
  }
}

private const val POINTS_LAYER_JSON = """
{
  "id": "points",
  "type": "symbol",
  "source": "points",
  "layout": {
    "icon-allow-overlap": true,
    "icon-image": "hotel",
    "icon-size": 0.75,
    "text-field": ["get", "name"],
    "text-font": ["DIN Pro Medium", "Arial Unicode MS Regular"],
    "text-size": 12,
    "text-offset": [0, 1.2],
    "text-anchor": "top",
    "text-allow-overlap": true
  },
  "paint": {
    "text-color": "#333333",
    "text-halo-color": "#ffffff",
    "text-halo-width": 1,
    "icon-translate": [0, 0],
    "text-translate": [0, 0]
  },
  "appearances": [
    {
      "name": "clicked",
      "condition": ["boolean", ["feature-state", "currentlySelected"], false],
      "properties": {
        "icon-image": "hotel-active",
        "icon-translate": [0, -12],
        "text-translate": [0, -12],
        "text-color": "#4264fb",
        "text-halo-color": "#c0caff",
        "text-halo-width": 2
      }
    },
    {
      "name": "has-been-clicked",
      "condition": ["boolean", ["feature-state", "hasBeenClicked"], false],
      "properties": {
        "icon-image": "hotel-clicked",
        "icon-opacity": 0.45,
        "text-opacity": 0.45
      }
    }
  ]
}
"""

private const val HOTEL_GEOJSON = """
{
  "type": "FeatureCollection",
  "features": [
    {"type": "Feature", "id": "1", "properties": {"name": "Hotel Carlemany"}, "geometry": {"type": "Point", "coordinates": [1.8452993238082342, 42.100164223399275]}},
    {"type": "Feature", "id": "2", "properties": {"name": "Hotel Panorama"}, "geometry": {"type": "Point", "coordinates": [1.8438590191857145, 42.1004178052402]}},
    {"type": "Feature", "id": "3", "properties": {"name": "Hotel Andorra"}, "geometry": {"type": "Point", "coordinates": [1.844225198327564, 42.10130533369667]}},
    {"type": "Feature", "id": "4", "properties": {"name": "Hotel Plaza"}, "geometry": {"type": "Point", "coordinates": [1.8443594640122, 42.0990955459275]}},
    {"type": "Feature", "id": "5", "properties": {"name": "Hotel Cervol"}, "geometry": {"type": "Point", "coordinates": [1.8449697625811154, 42.09869705141318]}},
    {"type": "Feature", "id": "6", "properties": {"name": "Hotel Diplomatic"}, "geometry": {"type": "Point", "coordinates": [1.8471058075726603, 42.09978384873651]}},
    {"type": "Feature", "id": "7", "properties": {"name": "Hotel Guillem"}, "geometry": {"type": "Point", "coordinates": [1.8455739474818813, 42.10182152060625]}},
    {"type": "Feature", "id": "8", "properties": {"name": "Hotel Roc Blanc"}, "geometry": {"type": "Point", "coordinates": [1.8427787800360136, 42.10039061289771]}},
    {"type": "Feature", "id": "9", "properties": {"name": "Hotel President"}, "geometry": {"type": "Point", "coordinates": [1.8433280487479635, 42.0994396753579]}}
  ]
}
"""