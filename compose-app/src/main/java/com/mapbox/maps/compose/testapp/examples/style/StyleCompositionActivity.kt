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
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundColor
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundLayer
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundOpacity
import com.mapbox.maps.extension.compose.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.compose.style.layers.generated.TextColor
import com.mapbox.maps.extension.compose.style.layers.generated.TextField
import com.mapbox.maps.extension.compose.style.layers.generated.TextSize
import com.mapbox.maps.extension.compose.style.layers.generated.Transition
import com.mapbox.maps.extension.compose.style.projection.Projection
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJsonSource
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

      var centerLocation by remember {
        mutableStateOf(CityLocations.HELSINKI)
      }

      var projection by remember {
        mutableStateOf(Projection.MERCATOR)
      }

      var textColor by remember {
        mutableStateOf(Color.Red)
      }

      val animatedLocation by animateValueAsState(
        targetValue = centerLocation,
        typeConverter = PointToVector,
        animationSpec = TweenSpec(durationMillis = 1000),
        label = "Animate location"
      )

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
                  projection = when (projection) {
                      Projection.default -> {
                        Projection.GLOBE
                      }
                      Projection.GLOBE -> {
                        Projection.MERCATOR
                      }
                      else -> {
                        Projection.default
                      }
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Change projection")
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = MapViewportState().apply {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            },
            style = {
              GenericStyle(
                style = styleUri,
                slots = mapOf(
                  "top" to {
                    BackgroundLayer(
                      layerId = "background-layer-at-top-slot",
                      backgroundColor = BackgroundColor(Color.Yellow),
                      backgroundOpacity = BackgroundOpacity(0.3)
                    )
                  }
                ),
                layerPositions = mapOf(
                  LayerPosition(null, "building", null) to {
                    BackgroundLayer(
                      layerId = "background-layer-at-position",
                      backgroundColor = BackgroundColor(Color.Red),
                      backgroundOpacity = BackgroundOpacity(0.3)
                    )
                  }
                ),
                projection = projection
              )
            }
          ) {
            if (showSymbolLayer) {
              SymbolLayer(
                layerId = "symbol-layer",
                sourceId = "sourceId",
                textField = text,
                textColor = TextColor(textColor),
                textColorTransition = Transition(duration = 3000),
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
              GeoJsonSource(
                sourceId = "sourceId",
                data = GeoJSONData(animatedLocation)
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