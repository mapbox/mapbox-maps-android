package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.importConfigs
import com.mapbox.maps.extension.compose.style.imports.rememberStyleImportState
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundLayer
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeatureStateKey
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Example to showcase usage of style imports.
 */
@OptIn(MapboxExperimental::class)
public class StyleImportsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var showSatelliteImport by remember {
        mutableStateOf(true)
      }
      var showStandardImport by remember {
        mutableStateOf(true)
      }
      var showBackgroundLayer by remember {
        mutableStateOf(true)
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showStandardImport = !showStandardImport
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle standard import to ${if (showStandardImport) "disabled" else "enabled"}"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showSatelliteImport = !showSatelliteImport
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle satellite import to ${if (showSatelliteImport) "disabled" else "enabled"}"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showBackgroundLayer = !showBackgroundLayer
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle background layer at top slot to ${if (showBackgroundLayer) "disabled" else "enabled"}"
                )
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions(
                cameraOptions {
                  center(Point.fromLngLat(-73.99, 40.72))
                  zoom(11.0)
                  pitch(45.0)
                }
              )
            },
            style = {
              GenericStyle(
                /*
                 * Load the map with an empty style.
                 * The map will use `StyleImport`s to load the desired styles.
                 */
                style = "",
                styleImportsContent = {
                  if (showStandardImport) {
                    StyleImport(
                      importId = STANDARD_STYLE_IMPORT_ID,
                      style = "asset://fragment-realestate-NY.json",
                      styleImportState = rememberStyleImportState {
                        importConfigs = importConfigs {
                          config("showTransitLabels", Value(false))
                        }
                        var selectedPriceLabel: FeaturesetFeature<FeatureState>? =
                          null
                        interactionsState
                          .onFeaturesetClicked(id = "hotels-price") { priceLabel, _ ->
                            if (selectedPriceLabel?.id != priceLabel.id) {
                              selectedPriceLabel?.removeFeatureState(FeatureStateKey.create("active"))
                              selectedPriceLabel = priceLabel
                            }
                            val isActive = (priceLabel.state.getBooleanState("active") ?: false).not()
                            priceLabel.setFeatureState(
                              FeatureState {
                                addBooleanState("active", isActive)
                              }
                            )
                            return@onFeaturesetClicked true
                          }
                          .onMapClicked {
                            selectedPriceLabel?.removeFeatureState(FeatureStateKey.create("active"))
                            selectedPriceLabel = null
                            true
                          }
                      }
                    )
                  }
                  if (showSatelliteImport) {
                    StyleImport(
                      importId = SATELLITE_STYLE_IMPORT_ID,
                      style = Style.SATELLITE,
                      styleImportState = rememberStyleImportState()
                    )
                  }
                },
                slotsContent {
                  slot("top") {
                    if (showBackgroundLayer) {
                      BackgroundLayer {
                        backgroundColor = ColorValue(Color.Red)
                        backgroundOpacity = DoubleValue(0.5)
                      }
                    }
                  }
                }
              )
            }
          )
        }
      }
    }
  }

  private companion object {
    const val STANDARD_STYLE_IMPORT_ID = "standard"
    const val SATELLITE_STYLE_IMPORT_ID = "satellite"
  }
}