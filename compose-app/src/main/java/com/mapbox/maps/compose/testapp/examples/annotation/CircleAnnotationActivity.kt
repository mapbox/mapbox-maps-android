package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotationGroup
import com.mapbox.maps.extension.compose.annotation.generated.withCircleColor
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.logD
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions

/**
 * Example to showcase usage of CircleAnnotation with Jetpack Compose.
 */
public class CircleAnnotationActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var groupColor by remember {
        mutableStateOf(Color.White)
      }
      var individualColor by remember {
        mutableStateOf(Color.Red)
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  groupColor = if (groupColor == Color.Black) Color.White else Color.Black
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle group color")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  individualColor = if (individualColor == Color.Red) Color.Yellow else Color.Red
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Toggle individual color")
              }
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
            }
          ) {
            CircleAnnotation(
              point = SINGLE_POINT,
            ) {
              interactionsState.onClicked {
                Toast.makeText(
                  this@CircleAnnotationActivity,
                  "Clicked on single Circle Annotation: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
                .onLongClicked {
                  Toast.makeText(
                    this@CircleAnnotationActivity,
                    "Long Clicked on single Circle Annotation: $it",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
                .onDragged {
                  logD(
                    this.javaClass.simpleName,
                    "Dragging single Circle Annotation: $it",
                  )
                }
              circleRadius = 20.0
              circleColor = Color.Blue
            }
            CircleAnnotationGroup(
              annotations = CLUSTER_POINTS.mapIndexed { index, item ->
                CircleAnnotationOptions()
                  .withPoint(item)
                  .withCircleRadius(10.0)
                  .apply {
                    // Apply circle color for selected annotation to overwrite the group settings
                    if (index % 3 == 0) {
                      withCircleColor(individualColor)
                    }
                  }
              },
              annotationConfig = AnnotationConfig(
                annotationSourceOptions = AnnotationSourceOptions(
                  clusterOptions = ClusterOptions(
                    textColorExpression = Expression.color(Color.Yellow.toArgb()),
                    textColor = Color.Black.toArgb(), // Will not be applied as textColorExpression has been set
                    textSize = 20.0,
                    circleRadiusExpression = literal(25.0),
                    colorLevels = listOf(
                      Pair(100, Color.Red.toArgb()),
                      Pair(50, Color.Blue.toArgb()),
                      Pair(0, Color.Green.toArgb())
                    )
                  )
                )
              )
            ) {
              // Apply circle color to the whole CircleAnnotationGroup
              circleColor = groupColor

              interactionsState.isDraggable = true
              interactionsState.onClicked {
                Toast.makeText(
                  this@CircleAnnotationActivity,
                  "Clicked on Circle Annotation Cluster's item: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
                .onLongClicked {
                  Toast.makeText(
                    this@CircleAnnotationActivity,
                    "Long clicked on Circle Annotation Cluster's item: $it",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
                .onClusterClicked {
                  Toast.makeText(
                    this@CircleAnnotationActivity,
                    "On cluster Click - ID: ${it.clusterId}, points:  ${it.pointCount}, abbreviatedCount: ${it.pointCountAbbreviated}",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
                .onClusterLongClicked {
                  Toast.makeText(
                    this@CircleAnnotationActivity,
                    "On cluster Long Click - ID: ${it.clusterId}, points:  ${it.pointCount}, abbreviatedCount: ${it.pointCountAbbreviated}",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
                .onDragged {
                  logD(
                    this.javaClass.simpleName,
                    "Dragging Circle Annotation Cluster's item: $it"
                  )
                }
                .onDragStarted {
                  Toast.makeText(
                    this@CircleAnnotationActivity,
                    "Dragged Started for Circle Annotation Cluster's item: $it",
                    Toast.LENGTH_SHORT
                  ).show()
                }
                .onDragFinished {
                  interactionsState.onDragged {
                    logD(
                      this.javaClass.simpleName,
                      "Updated dragging Circle Annotation Cluster's item: $it"
                    )
                  }
                }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 10.0
    const val INTERVAL = 0.01
    val CLUSTER_POINTS: List<Point> by lazy {
      mutableListOf<Point>().apply {
        repeat(10) { x ->
          repeat(10) { y ->
            add(
              Point.fromLngLat(
                CityLocations.HELSINKI.longitude() + INTERVAL * x,
                CityLocations.HELSINKI.latitude() + INTERVAL * y
              )
            )
          }
        }
      }
    }
    val SINGLE_POINT: Point = Point.fromLngLat(
      CityLocations.HELSINKI.longitude() - INTERVAL,
      CityLocations.HELSINKI.latitude() - INTERVAL
    )
  }
}