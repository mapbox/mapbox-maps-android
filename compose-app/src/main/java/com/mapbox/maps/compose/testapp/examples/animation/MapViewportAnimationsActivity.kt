package com.mapbox.maps.compose.testapp.examples.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.MultiPoint
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.*
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions

/**
 * Showcase the basic camera animations based on [MapViewport] API.
 */
@OptIn(MapboxExperimental::class)
public class MapViewportAnimationsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          center(CityLocations.BERLIN)
          zoom(15.0)
        }
        flyTo(
          cameraOptions {
            center(CityLocations.HELSINKI)
          },
          MapAnimationOptions.mapAnimationOptions { duration(5000) }
        )
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  val currentCenter = mapViewportState.cameraState.center
                  mapViewportState.easeTo(
                    cameraOptions = cameraOptions {
                      center(currentCenter.offset())
                    }
                  )
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "EaseTo Animation")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  mapViewportState.flyTo(
                    cameraOptions = cameraOptions {
                      center(CityLocations.BERLIN)
                      zoom(ZOOM)
                      pitch(PITCH)
                    },
                    MapAnimationOptions.mapAnimationOptions { duration(3000) }
                  )
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "FlyTo Animation")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  mapViewportState.transitionToOverviewState(
                    overviewViewportStateOptions = OverviewViewportStateOptions.Builder()
                      .geometry(
                        MultiPoint.fromLngLats(
                          listOf(
                            CityLocations.HELSINKI,
                            CityLocations.BERLIN
                          )
                        )
                      )
                      .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0))
                      .build(),
                    defaultTransitionOptions = DefaultViewportTransitionOptions.Builder()
                      .maxDurationMs(5000L).build()
                  )
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Transition to Overview")
              }
            }
          }
        ) {
          Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
              Modifier.fillMaxSize(),
              mapViewportState = mapViewportState
            ) {
              PolylineAnnotation(points = listOf(CityLocations.HELSINKI, CityLocations.BERLIN))
            }
            Column(
              Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(fraction = 0.9f)
                .background(MaterialTheme.colors.background.copy(alpha = 0.6f))
                .padding(top = 30.dp)
            ) {
              Text(
                modifier = Modifier
                  .padding(10.dp),
                textAlign = TextAlign.Center,
                text = mapViewportState.mapViewportStatus.toString()
              )
              Divider()
              Text(
                modifier = Modifier
                  .padding(10.dp),
                textAlign = TextAlign.Center,
                text = mapViewportState.mapViewportStatusChangedReason.toString()
              )
              Divider()
              Text(
                modifier = Modifier
                  .padding(10.dp),
                textAlign = TextAlign.Center,
                text = mapViewportState.cameraState.toString()
              )
            }
          }
        }
      }
    }
  }

  /**
   * Produce a new [Point] that offsets [offset] in both latitude and longitude.
   */
  private fun Point.offset(offset: Double = 0.01) =
    Point.fromLngLat(longitude() + offset, latitude() + offset)

  private companion object {
    const val ZOOM = 15.7
    const val PITCH = 60.0
  }
}