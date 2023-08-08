package com.mapbox.maps.compose.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotationGroup
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions

/**
 * Example to showcase usage of CircleAnnotation with Jetpack Compose.
 */
@OptIn(MapboxExperimental::class)
public class CircleAnnotationActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = MapViewportState().apply {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            }
          ) {
            CircleAnnotation(
              point = SINGLE_POINT,
              circleRadius = 20.0,
              circleColorInt = Color.BLUE,
              onClick = {
                Toast.makeText(
                  this@CircleAnnotationActivity,
                  "Clicked on Circle Annotation: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
            )
            CircleAnnotationGroup(
              annotations = CLUSTER_POINTS.map {
                CircleAnnotationOptions()
                  .withPoint(it)
                  .withCircleRadius(10.0)
                  .withCircleColor(Color.RED)
              },
              annotationConfig = AnnotationConfig(
                annotationSourceOptions = AnnotationSourceOptions(
                  clusterOptions = ClusterOptions(
                    textColorExpression = Expression.color(Color.YELLOW),
                    textColor = Color.BLACK, // Will not be applied as textColorExpression has been set
                    textSize = 20.0,
                    circleRadiusExpression = literal(25.0),
                    colorLevels = listOf(
                      Pair(100, Color.RED),
                      Pair(50, Color.BLUE),
                      Pair(0, Color.GREEN)
                    )
                  )
                )
              ),
              onClick = {
                Toast.makeText(
                  this@CircleAnnotationActivity,
                  "Clicked on Circle Annotation Cluster item: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
            )
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