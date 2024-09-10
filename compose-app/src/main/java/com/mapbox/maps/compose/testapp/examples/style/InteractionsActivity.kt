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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.rememberStyleState
import com.mapbox.maps.interactions.FeatureStateValue
import com.mapbox.maps.interactions.FeaturesetHolder
import com.mapbox.maps.interactions.InteractiveFeature
import org.json.JSONObject

public class InteractionsActivity : ComponentActivity() {

  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {

      var selectedPriceLabel by remember {
        mutableStateOf<InteractiveFeature<FeaturesetHolder.Featureset>?>(null)
      }

      var isActive: Boolean? by remember {
        // null as we do not know the initial state
        mutableStateOf(null)
      }

      val mapState = rememberMapState()

      MapboxMapComposeTheme {
        ExampleScaffold {
          Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
              modifier = Modifier.fillMaxSize(),
              mapState = mapState,
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
                  style = "asset://fragment-realestate-NY.json",
                  styleState = rememberStyleState {
                    styleInteractionsState
                      .onFeaturesetClicked(featuresetId = "hotels-price") { priceLabel, _ ->
                        if (selectedPriceLabel?.feature?.id() != priceLabel.feature.id()) {
                          selectedPriceLabel?.removeFeatureState("active")
                          selectedPriceLabel = priceLabel
                        }
                        isActive =
                          (
                            !JSONObject(priceLabel.state.toJson()).optBoolean("active", false)
                            ).also {
                              priceLabel.setFeatureState(
                                FeatureStateValue(
                                  "active",
                                  Value.valueOf(it)
                                )
                              )
                            }
                        return@onFeaturesetClicked true
                      }
                      .onMapClicked {
                        selectedPriceLabel?.removeFeatureState("active")
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
                text = "Feature with id: ${actualSelectedPriceLabel.feature.id()!!}; active: $isActive",
              )
            }
          }
        }
      }
    }
  }
}