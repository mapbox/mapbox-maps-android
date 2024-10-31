package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor
import com.mapbox.maps.logD
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Example to showcase usage of query rendered features.
 */
@OptIn(MapboxExperimental::class)
public class QueryRenderedFeatureActivity : ComponentActivity() {

  private val buildingFeatureset = TypedFeaturesetDescriptor.Layer("building")

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
      var clickedBuilding by remember {
        mutableStateOf(emptyList<List<Point>>())
      }
      var viewportHighlightedBuildings by remember {
        mutableStateOf(emptyList<List<List<Point>>>())
      }
      LaunchedEffect(Unit) {
        with(mapState) {
          // wait and suspend for the first ever map idle event, meaning map is rendered.
          mapIdleEvents.first()
          clickedBuilding =
            queryBuildingCoordinatesAt(CityLocations.HELSINKI)!!
        }
      }
      val coroutineScope = rememberCoroutineScope()
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            FloatingActionButton(
              onClick = {
                coroutineScope.launch {
                  viewportHighlightedBuildings = mapState.queryRenderedFeatures(
                    buildingFeatureset
                  ).map {
                    (it.geometry as? Polygon)?.coordinates()?.toList() ?: emptyList()
                  }
                }
              }
            ) {
              Text(modifier = Modifier.padding(10.dp), text = "Highlight all")
            }
          }
        ) {
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
                mapState.queryBuildingCoordinatesAt(clickedPoint)?.let {
                  clickedBuilding = it
                }
              }
              false
            },
            mapState = mapState
          ) {
            PolygonAnnotation(clickedBuilding) {
              fillColor = Color.Red
              fillOpacity = 0.5
            }
            viewportHighlightedBuildings.forEach { building ->
              PolygonAnnotation(building) {
                fillColor = Color.Blue
                fillOpacity = 0.5
              }
            }
          }
        }
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  private suspend fun MapState.queryBuildingCoordinatesAt(point: Point): List<List<Point>>? {
    val selectedBuildings = queryRenderedFeatures(
      geometry = RenderedQueryGeometry(pixelForCoordinate(point)),
      descriptor = buildingFeatureset
    )
    if (selectedBuildings.isEmpty()) {
      logD(TAG, "Clicked outside of building")
      return null
    }
    logD(TAG, "Feature properties: ${selectedBuildings.first().properties}")
    return (selectedBuildings.first().geometry as? Polygon)?.coordinates()?.toList()
  }

  private companion object {
    const val TAG = "QRFActivity"
    const val ZOOM: Double = 15.5
  }
}