package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.rememberStyleState
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeatureStateKey
import com.mapbox.maps.interactions.FeaturesetFeature

public class InteractionsActivity : ComponentActivity() {

  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {

      var selectedPriceLabel by remember {
        mutableStateOf<FeaturesetFeature<FeatureState>?>(null)
      }

      var isActive: Boolean? by remember {
        // null as we do not know the initial state
        mutableStateOf(null)
      }

      val mapState = rememberMapState()
      val mapViewportState = rememberMapViewportState {
        setCameraOptions(
          cameraOptions {
            center(Point.fromLngLat(-73.99, 40.72))
            zoom(11.0)
            pitch(45.0)
          }
        )
      }

      LaunchedEffect(selectedPriceLabel) {
        selectedPriceLabel?.let {
          val camera = mapViewportState.cameraForCoordinates(
            listOf(it.geometry as Point),
            cameraOptions {
              padding(
                EdgeInsets(100.0, 100.0, 100.0, 100.0)
              )
            },
            maxZoom = 11.0
          )
          mapViewportState.flyTo(camera)
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold {
          Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
              modifier = Modifier.fillMaxSize(),
              mapState = mapState,
              mapViewportState = mapViewportState,
              style = {
                GenericStyle(
                  style = "asset://fragment-realestate-NY.json",
                  styleState = rememberStyleState {
                    styleInteractionsState
                      .onFeaturesetClicked(id = "hotels-price") { priceLabel, _ ->
                        if (selectedPriceLabel?.id != priceLabel.id) {
                          selectedPriceLabel?.removeFeatureState(FeatureStateKey.create("active"))
                          selectedPriceLabel = priceLabel
                        }
                        val newActiveState = priceLabel.state.getBooleanState("active")?.not() ?: true
                        priceLabel.setFeatureState(
                          FeatureState {
                            addBooleanState("active", newActiveState)
                          }
                        ) {
                          isActive = newActiveState
                        }
                        return@onFeaturesetClicked true
                      }
                      .onMapClicked {
                        selectedPriceLabel?.removeFeatureState(FeatureStateKey.create("active"))
                        selectedPriceLabel = null
                        isActive = null
                        true
                      }
                  }
                )
              }
            )
            selectedPriceLabel?.let { actualSelectedPriceLabel ->
              Text(
                modifier = Modifier
                  .wrapContentWidth()
                  .align(Alignment.BottomCenter)
                  .padding(40.dp)
                  .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
                  .background(MaterialTheme.colors.primary, CircleShape)
                  .padding(10.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                text = "Feature with id: ${actualSelectedPriceLabel.id!!}; active: $isActive",
              )
            }
          }
        }
      }
    }
  }
}