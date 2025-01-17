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
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.layers.generated.ClipLayer
import com.mapbox.maps.extension.compose.style.layers.generated.ClipLayerTypes
import com.mapbox.maps.extension.compose.style.layers.generated.ClipLayerTypesListValue
import com.mapbox.maps.extension.compose.style.layers.generated.FillLayer
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState

/**
 * Example showcasing the usage of [com.mapbox.maps.extension.style.layers.generated.ClipLayer].
 */
public class ClipLayerActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var clipLayerTypesListValue by remember {
        mutableStateOf(ClipLayerTypesListValue(emptyList()))
      }
      var showClipLayer by remember {
        mutableStateOf(false)
      }
      val geoJsonSource = rememberGeoJsonSourceState {
        data = GeoJSONData(
          listOf(
            Feature.fromGeometry(Polygon.fromLngLats(POLYGON_POINTS))
          )
        )
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showClipLayer = true
                  clipLayerTypesListValue = ClipLayerTypesListValue(listOf(ClipLayerTypes.SYMBOL))
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Clip symbol")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showClipLayer = true
                  clipLayerTypesListValue = ClipLayerTypesListValue(listOf(ClipLayerTypes.MODEL))
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Clip model")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showClipLayer = true
                  clipLayerTypesListValue = ClipLayerTypesListValue(
                    listOf(ClipLayerTypes.MODEL, ClipLayerTypes.SYMBOL)
                  )
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Clip model and symbol")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showClipLayer = false
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Hide clip layer")
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions(START_CAMERA_POSITION)
            },
            style = {
              GenericStyle(
                style = Style.STANDARD,
                slotsContent = slotsContent {
                  slot("bottom") {
                    FillLayer(sourceState = geoJsonSource) {
                      fillColor = ColorValue(Color.Blue)
                      fillOpacity = DoubleValue(0.4)
                    }
                  }
                }
              )
            }
          ) {
            if (showClipLayer) {
              ClipLayer(
                sourceState = geoJsonSource,
              ) {
                clipLayerTypes = clipLayerTypesListValue
              }
            }
          }
        }
      }
    }
  }

  public companion object {
    private val CENTER = Point.fromLngLat(-74.0027, 40.7130)
    private val START_CAMERA_POSITION = cameraOptions {
      center(CENTER)
      zoom(16.5)
      bearing(60.0)
      pitch(30.0)
    }

    private val POLYGON_POINTS = listOf(
      listOf(
        Point.fromLngLat(
          -74.00438542864366,
          40.71275107696869
        ),
        Point.fromLngLat(
          -74.00465916994656,
          40.712458268827675
        ),
        Point.fromLngLat(
          -74.00417333128154,
          40.71212099900339
        ),
        Point.fromLngLat(
          -74.00314623457163,
          40.71238635014873
        ),
        Point.fromLngLat(
          -74.00088173461268,
          40.71296692136764
        ),
        Point.fromLngLat(
          -74.00081475001514,
          40.713220461793924
        ),
        Point.fromLngLat(
          -74.0024425998592,
          40.71419501190087
        ),
        Point.fromLngLat(
          -74.00341033210208,
          40.71374214594772
        ),
        Point.fromLngLat(
          -74.00438542864366,
          40.71275107696869
        ),
      )
    )
  }
}