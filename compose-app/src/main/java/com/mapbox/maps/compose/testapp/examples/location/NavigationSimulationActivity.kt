package com.mapbox.maps.compose.testapp.examples.location

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.AnnotationUtils
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.examples.utils.SimulateRouteLocationProvider
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.compose.testapp.utils.RecordFrameStats
import com.mapbox.maps.extension.compose.DisposableMapEffect
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.layers.generated.LineCapValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineJoinValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineLayer
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Example to showcase usage of runtime styling with compose.
 */
@OptIn(MapboxExperimental::class)
public class NavigationSimulationActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          center(CityLocations.NULLISLAND)
          zoom(0.0)
          pitch(0.0)
          bearing(0.0)
        }
      }
      var progress by remember {
        mutableStateOf(0.0)
      }
      var routeLine by remember {
        mutableStateOf<LineString?>(null)
      }

      var lightPreset by remember {
        mutableStateOf(LightPresetValue.DAY)
      }

      LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
          routeLine = LineString.fromPolyline(
            DirectionsResponse.fromJson(
              AnnotationUtils.loadStringFromAssets(
                this@NavigationSimulationActivity,
                NAVIGATION_ROUTE_JSON_NAME
              )
            ).routes()[0].geometry()!!,
            Constants.PRECISION_6
          )
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              if (routeLine != null) {
                FloatingActionButton(
                  modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.End),
                  onClick = {
                    if ((mapViewportState.mapViewportStatus as? ViewportStatus.State)?.state is FollowPuckViewportState) {
                      routeLine?.let {
                        mapViewportState.transitionToOverviewState(
                          overviewViewportStateOptions = OverviewViewportStateOptions.Builder()
                            .geometry(it)
                            .padding(EdgeInsets(10.0, 10.0, 10.0, 10.0))
                            .build()
                        )
                      }
                    } else {
                      mapViewportState.transitionToFollowPuckState()
                    }
                  }
                ) {
                  if ((mapViewportState.mapViewportStatus as? ViewportStatus.State)?.state is FollowPuckViewportState) {
                    Image(
                      painter = painterResource(id = android.R.drawable.ic_menu_directions),
                      contentDescription = "Overview button"
                    )
                  } else {
                    Image(
                      painter = painterResource(id = android.R.drawable.ic_menu_mylocation),
                      contentDescription = "Follow puck button"
                    )
                  }
                }
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  lightPreset = if (lightPreset == LightPresetValue.DAY) {
                    LightPresetValue.NIGHT
                  } else {
                    LightPresetValue.DAY
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle light preset")
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = {
              NavigationStyle(routeLine = routeLine, progress = progress, lightPreset = lightPreset)
            }
          ) {
            RecordFrameStats()
            MapEffect(routeLine) { map ->
              routeLine?.let {
                map.location.setLocationProvider(SimulateRouteLocationProvider(it))
                map.location.enabled = true
                mapViewportState.transitionToFollowPuckState()
              }
            }
            DisposableMapEffect(Unit) { map ->
              map.location.updateSettings {
                locationPuck = createDefault2DPuck(withBearing = true)
                puckBearingEnabled = true
                puckBearing = PuckBearing.HEADING
              }
              val locationListener = OnIndicatorPositionChangedListener { point ->
                // in SimulateRouteLocationProvider we use altitude field to insert animated progress info.
                progress = point.altitude()
              }
              map.location.addOnIndicatorPositionChangedListener(locationListener)
              onDispose {
                map.location.removeOnIndicatorPositionChangedListener(locationListener)
              }
            }
          }
        }
      }
    }
  }

  @MapboxStyleComposable
  @Composable
  public fun NavigationStyle(routeLine: LineString?, progress: Double, lightPreset: LightPresetValue) {
    val geoJsonSource = rememberGeoJsonSourceState {
      lineMetrics = BooleanValue(true)
    }
    LaunchedEffect(routeLine) {
      routeLine?.let {
        geoJsonSource.data = GeoJSONData(it)
      }
    }
    MapboxStandardStyle(
      lightPreset = lightPreset,
      topSlot = {
        if (routeLine != null) {
          LineLayer(
            sourceState = geoJsonSource,
            lineTrimOffset = DoubleListValue(listOf(0.0, progress)),
            lineWidth = DoubleValue(
              interpolate {
                exponential {
                  literal(1.5)
                }
                zoom()
                stop {
                  literal(10)
                  product(7.0, 1.0)
                }
                stop {
                  literal(14.0)
                  product(10.5, 1.0)
                }
                stop {
                  literal(16.5)
                  product(15.5, 1.0)
                }
                stop {
                  literal(19.0)
                  product(24.0, 1.0)
                }
                stop {
                  literal(22.0)
                  product(29.0, 1.0)
                }
              }
            ),
            lineCap = LineCapValue.ROUND,
            lineJoin = LineJoinValue.ROUND,
            lineGradient = ColorValue(
              interpolate {
                linear()
                lineProgress()
                stop {
                  literal(0)
                  rgba(47.0, 122.0, 198.0, 1.0)
                }
                stop {
                  literal(1.0)
                  rgba(47.0, 122.0, 198.0, 1.0)
                }
              }
            )
          )
          LineLayer(
            sourceState = geoJsonSource,
            lineTrimOffset = DoubleListValue(listOf(0.0, progress)),
            lineWidth = DoubleValue(
              interpolate {
                exponential {
                  literal(1.5)
                }
                zoom()
                stop {
                  literal(4.0)
                  product(3.0, 1.0)
                }
                stop {
                  literal(10.0)
                  product(4.0, 1.0)
                }
                stop {
                  literal(13.0)
                  product(6.0, 1.0)
                }
                stop {
                  literal(16.0)
                  product(10.0, 1.0)
                }
                stop {
                  literal(19.0)
                  product(14.0, 1.0)
                }
                stop {
                  literal(22.0)
                  product(18.0, 1.0)
                }
              }
            ),
            lineCap = LineCapValue.ROUND,
            lineJoin = LineJoinValue.ROUND,
            lineGradient = ColorValue(
              interpolate {
                linear()
                lineProgress()
                // blue
                stop { literal(0.0); rgb { literal(6); literal(1); literal(255) } }
                // royal blue
                stop { literal(0.1); rgb { literal(59); literal(118); literal(227) } }
                // cyan
                stop { literal(0.3); rgb { literal(7); literal(238); literal(251) } }
                // lime
                stop { literal(0.5); rgb { literal(0); literal(255); literal(42) } }
                // yellow
                stop { literal(0.7); rgb { literal(255); literal(252); literal(0) } }
                // red
                stop { literal(1.0); rgb { literal(255); literal(30); literal(0) } }
              }
            )
          )
        }
      }
    )
  }

  private companion object {
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
  }
}