package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapState
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.interactions.FeaturesetHolder
import com.mapbox.maps.logD
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Example to showcase usage of query rendered features.
 */
public class QueryRenderedFeatureActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val mapState = rememberMapState {
        gesturesSettings = GesturesSettings {
          doubleTapToZoomInEnabled = false
          quickZoomEnabled = false
          doubleTouchToZoomOutEnabled = false
          pinchToZoomEnabled = false
        }
      }
      var highlightedBuilding by remember {
        mutableStateOf(emptyList<List<Point>>())
      }
      LaunchedEffect(Unit) {
        with(mapState) {
          // wait and suspend for the first ever map idle event, meaning map is rendered.
          mapIdleEvents.first()
          highlightedBuilding =
            queryBuildingCoordinatesAt(CityLocations.HELSINKI)
        }
      }
      MapboxMapComposeTheme {
        ExampleScaffold {
          val coroutineScope = rememberCoroutineScope()
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            },
            style = {
              MapStyle(style = Style.MAPBOX_STREETS)
            },
            onMapClickListener = { clickedPoint ->
              coroutineScope.launch {
                highlightedBuilding = mapState.queryBuildingCoordinatesAt(clickedPoint)
              }
              false
            },
            mapState = mapState
          ) {
            PolygonAnnotation(highlightedBuilding) {
              fillColor = Color.Red
              fillOpacity = 0.5
            }
          }
        }
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  private suspend fun MapState.queryBuildingCoordinatesAt(point: Point): List<List<Point>> {
    val selectedBuilding = queryRenderedFeature(
      geometry = RenderedQueryGeometry(pixelForCoordinate(point)),
      featuresetHolder = FeaturesetHolder.Layer("building")
    )
    logD(TAG, "Feature properties: ${selectedBuilding.feature.properties()}")
    return (selectedBuilding.feature.geometry() as? Polygon)?.coordinates()?.toList() ?: emptyList()
  }

  private companion object {
    const val TAG = "QRFActivity"
    const val ZOOM: Double = 15.5
  }
}