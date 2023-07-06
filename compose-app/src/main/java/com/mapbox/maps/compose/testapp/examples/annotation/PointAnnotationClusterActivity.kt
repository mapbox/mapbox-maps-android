package com.mapbox.maps.compose.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.AnnotationUtils
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import kotlinx.coroutines.*

/**
 * Example to showcase usage of clustering of point annotations.
 */
@OptIn(MapboxExperimental::class)
public class PointAnnotationClusterActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      var isLoadingInProgress by remember {
        mutableStateOf(true)
      }
      var points by remember {
        mutableStateOf<List<Point>>(listOf())
      }

      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapInitOptionsFactory = { context ->
              MapInitOptions(
                context,
                cameraOptions = cameraOptions {
                  zoom(ZOOM)
                  center(CityLocations.WASHINGTON)
                }
              )
            },
          ) {
            PointAnnotationGroup(
              annotations = points.map {
                PointAnnotationOptions()
                  .withPoint(it)
                  .withIconImage(ICON_FIRE_STATION)
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
                  this@PointAnnotationClusterActivity,
                  "Clicked on Point Annotation Cluster: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
            )
          }
          LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
              points = loadData()
              isLoadingInProgress = false
            }
          }
          if (isLoadingInProgress) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier.fillMaxSize()
            ) {
              CircularProgressIndicator()
            }
          }
        }
      }
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  private suspend fun loadData(): List<Point> {
    return suspendCancellableCoroutine { continuation ->
      AnnotationUtils.loadStringFromNet(this@PointAnnotationClusterActivity, POINTS_URL)
        ?.let {
          FeatureCollection.fromJson(it).features()?.let { features ->
            features.shuffle()
            continuation.resume(
              features.take(AMOUNT).map { feature ->
                feature.geometry() as Point
              },
              onCancellation = null
            )
          }
        }
    }
  }

  private companion object {
    const val ZOOM: Double = 10.0
    const val AMOUNT = 10000
    const val ICON_FIRE_STATION = "fire-station-11"
    private const val POINTS_URL =
      "https://opendata.arcgis.com/datasets/01d0ff375695466d93d1fa2a976e2bdd_5.geojson"
  }
}