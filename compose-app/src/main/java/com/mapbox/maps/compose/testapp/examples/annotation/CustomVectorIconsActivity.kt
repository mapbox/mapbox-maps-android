package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.style.expressions.dsl.generated.image
import com.mapbox.maps.extension.style.expressions.dsl.generated.switchCase
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Example demonstrating custom vector icons with dynamic styling and interaction.
 * This example shows how to:
 * - Dynamically colorize vector icons based on feature properties using the image expression
 * - Interactively change icon size by tapping on icons
 *
 * Vector icons are parameterized SVG images that can be styled at runtime. In this example,
 * three flag icons are colored red, yellow, and purple using the 'flagColor' property.
 * Tap any flag to toggle its size between 1x and 2x.
 *
 * For this example to work, the SVGs must live inside the map style. The SVG file was uploaded
 * to Mapbox Studio with the name `flag`, making it available for customization at runtime.
 * You can add vector icons to your own style in Mapbox Studio.
 */
@OptIn(MapboxDelicateApi::class, MapboxExperimental::class)
public class CustomVectorIconsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var selectedFlagId by remember { mutableStateOf<String?>(null) }

      // Create GeoJSON source with three flag locations
      val geoJsonSource = rememberGeoJsonSourceState {
        generateId = BooleanValue(true)
      }
      geoJsonSource.data = GeoJSONData(createFlagFeatures())

      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(16.0)
          center(Point.fromLngLat(24.6881, 60.185755))
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = {
              MapStyle(style = "mapbox://styles/mapbox-map-design/cm4r19bcm00ao01qvhp3jc2gi")
            }
          ) {
            // Create symbol layer with parameterized icon
            SymbolLayer(
              sourceState = geoJsonSource,
              layerId = "points"
            ) {
              interactionsState.onClicked { feature, _ ->
                feature.properties.optString("id").takeIf { it.isNotEmpty() }?.let { id ->
                  selectedFlagId = if (selectedFlagId == id) null else id
                }
                true
              }
              iconImage = ImageValue(
                image {
                  literal("flag")
                  imageOptions("flag_color" to Expression.get("flagColor"))
                }
              )
              iconSize = DoubleValue(
                switchCase {
                  eq {
                    get { literal("id") }
                    literal(selectedFlagId ?: "")
                  }
                  literal(2.0)
                  literal(1.0)
                }
              )
              iconAllowOverlap = BooleanValue(true)
            }
          }
        }
      }
    }
  }

  /**
   * Creates GeoJSON features with flag locations and colors.
   */
  private fun createFlagFeatures(): List<Feature> {
    return listOf(
      createFlagFeature("flag-red", 24.68727, 60.185755, "red"),
      createFlagFeature("flag-yellow", 24.68827, 60.186255, "yellow"),
      createFlagFeature("flag-purple", 24.68927, 60.186055, "#800080")
    )
  }

  /**
   * Creates a feature with a flag at the specified location and color.
   */
  private fun createFlagFeature(id: String, longitude: Double, latitude: Double, color: String): Feature {
    val feature = Feature.fromGeometry(Point.fromLngLat(longitude, latitude))
    feature.addStringProperty("id", id)
    feature.addStringProperty("flagColor", color)
    return feature
  }
}