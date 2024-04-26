package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.AnimationVector3D
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundColor
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundLayer
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundOpacity
import com.mapbox.maps.extension.compose.style.layers.generated.CircleColor
import com.mapbox.maps.extension.compose.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.compose.style.layers.generated.CircleRadius
import com.mapbox.maps.extension.compose.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.compose.style.layers.generated.TextColor
import com.mapbox.maps.extension.compose.style.layers.generated.TextField
import com.mapbox.maps.extension.compose.style.layers.generated.TextSize
import com.mapbox.maps.extension.compose.style.layers.generated.Transition
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Example to showcase usage of runtime styling with compose.
 */
@OptIn(MapboxExperimental::class)
public class StyleCompositionActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var text by remember {
        mutableStateOf(TextField("Hello Helsinki"))
      }

      var showSymbolLayer by remember {
        mutableStateOf(true)
      }

      var styleUri by remember {
        mutableStateOf(Style.LIGHT)
      }

      var showBackground by remember {
        mutableStateOf(true)
      }

      var centerLocation by rememberSaveable {
        mutableStateOf(CityLocations.HELSINKI)
      }

      var textColor by remember {
        mutableStateOf(Color.Red)
      }

      val animatedLocation by animateValueAsState(
        targetValue = centerLocation,
        typeConverter = PointToVector,
        animationSpec = TweenSpec(durationMillis = 1_000),
        label = "Animate location"
      )

      val geoJsonSource = rememberGeoJsonSourceState()
      geoJsonSource.data = GeoJSONData(animatedLocation)

      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  text = TextField("Hello ${count++}")
                  showSymbolLayer = !showSymbolLayer
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle visibility")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  centerLocation = centerLocation.shiftPosition()
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Animate Source")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  textColor = if (textColor == Color.Red) {
                    Color.Yellow
                  } else {
                    Color.Red
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Animate text color transition")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  styleUri = if (styleUri == Style.LIGHT) {
                    Style.STANDARD
                  } else {
                    Style.LIGHT
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle Style")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showBackground = !showBackground
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle BG Layer")
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = {
              GenericStyle(
                style = styleUri,
                slots = slotsContent {
                  if (styleUri == Style.STANDARD) {
                    if (showBackground) {
                      slot("top") {
                        // Only add background layer in top slot for standard style, where the top slot
                        // is available.
                        BackgroundLayer(
                          backgroundColor = BackgroundColor(Color.Yellow),
                          backgroundOpacity = BackgroundOpacity(0.3)
                        )
                      }
                    }
                    slot("middle") {
                      BackgroundLayer(
                        backgroundColor = BackgroundColor(Color.Red),
                        backgroundOpacity = BackgroundOpacity(0.3)
                      )
                    }
                  }
                },
                layerPositions = mapOf(
                  LayerPosition(null, "building", null) to {
                    // only add background layer below building layer if the style is not standard
                    // style, where layers are available in runtime styling.
                    if (styleUri != Style.STANDARD) {
                      if (showBackground) {
                        BackgroundLayer(
                          backgroundColor = BackgroundColor(Color.Red),
                          backgroundOpacity = BackgroundOpacity(0.3)
                        )
                      }
                    }
                  }
                ),
              )
            }
          ) {
            if (showSymbolLayer) {
              CircleLayer(
                sourceState = geoJsonSource,
                circleColor = CircleColor(Color.Cyan),
                circleRadius = CircleRadius(50.0),
                circleRadiusTransition = Transition(duration = 1000L)
              )
              SymbolLayer(
                sourceState = geoJsonSource,
                textField = text,
                textColor = TextColor(textColor),
                textColorTransition = Transition(duration = 1000),
                textSize = TextSize(
                  Expression.interpolate {
                    linear()
                    zoom()
                    stop {
                      literal(0.0)
                      literal(10.0)
                    }
                    stop {
                      literal(10.0)
                      literal(20.0)
                    }
                  }
                )
              )
            }
          }
        }
      }
    }
  }

  private fun Point.shiftPosition(offset: Double = OFFSET): Point {
    return Point.fromLngLat(longitude() + offset, latitude() + offset, altitude())
  }

  private companion object {
    private val PointToVector: TwoWayConverter<Point, AnimationVector3D> =
      TwoWayConverter(
        {
          AnimationVector3D(
            it.longitude().toFloat(),
            it.latitude().toFloat(),
            if (it.altitude().isFinite()) it.altitude().toFloat() else 0f
          )
        },
        { Point.fromLngLat(it.v1.toDouble(), it.v2.toDouble(), it.v3.toDouble()) }
      )
    const val ZOOM: Double = 9.0
    private var count = 0
    private const val OFFSET = 0.03
  }
}