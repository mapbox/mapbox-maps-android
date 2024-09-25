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
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.FormattedValue
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.compose.style.layers.generated.IconAnchorValue
import com.mapbox.maps.extension.compose.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.compose.style.rememberStyleImage
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.style.expressions.dsl.generated.switchCase
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeatureState

/**
 * Example to showcase usage of runtime styling with compose.
 */
@OptIn(MapboxExperimental::class)
public class StyleCompositionActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var text by remember {
        mutableStateOf(FormattedValue("Hello Helsinki"))
      }

      var showSymbolLayer by remember {
        mutableStateOf(true)
      }

      var styleUri by remember {
        mutableStateOf(Style.LIGHT)
      }

      var centerLocation by rememberSaveable {
        mutableStateOf(CityLocations.HELSINKI)
      }

      var symbolTextColor by remember {
        mutableStateOf(Color.Red)
      }

      var markerResource by remember {
        mutableStateOf(R.drawable.ic_red_marker)
      }

      val animatedLocation by animateValueAsState(
        targetValue = centerLocation,
        typeConverter = PointToVector,
        animationSpec = TweenSpec(durationMillis = 1_000),
        label = "Animate location"
      )

      val geoJsonSource = rememberGeoJsonSourceState {
        generateId = BooleanValue(true)
      }
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
                  text = FormattedValue("Hello ${count++}")
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
                  centerLocation = centerLocation.shiftPosition()
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Animate Source")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  markerResource =
                    if (markerResource == R.drawable.ic_red_marker) R.drawable.ic_blue_marker else R.drawable.ic_red_marker
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle icon image")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  symbolTextColor = if (symbolTextColor == Color.Red) {
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
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = { MapStyle(style = styleUri) }
          ) {
            if (showSymbolLayer) {
              CircleLayer(
                sourceState = geoJsonSource,
              ) {
                interactionsState.onClicked { featuresetFeature, _ ->
                  val selected = featuresetFeature.state.getBooleanState("selected") ?: false
                  featuresetFeature.setFeatureState(
                    FeatureState {
                      addBooleanState("selected", !selected)
                    }
                  )
                  true
                }
                circleColor = ColorValue(
                  switchCase {
                    boolean {
                      featureState {
                        literal("selected")
                      }
                      literal(false)
                    }
                    literal("yellow")
                    literal("white")
                  }
                )
                circleRadius = DoubleValue(50.0)
                circleRadiusTransition = Transition(durationMillis = 1000L)
              }
              val styleImage = rememberStyleImage(imageId = "icon_id", resourceId = markerResource)

              SymbolLayer(
                sourceState = geoJsonSource
              ) {
                iconImage = ImageValue(styleImage)
                iconAnchor = IconAnchorValue.BOTTOM
                textField = text
                textColor = ColorValue(symbolTextColor)
                textColorTransition = Transition(durationMillis = 1000)
                textSize = DoubleValue(
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
              }
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