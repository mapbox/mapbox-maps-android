package com.mapbox.maps.compose.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.examples.utils.AnnotationUtils
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.ThemeValue
import com.mapbox.maps.extension.compose.style.standard.rememberStandardStyleState
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Example to showcase usage of clustering of point annotations.
 */
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
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.WASHINGTON)
              }
            },
            style = {
              MapboxStandardStyle(
                standardStyleState = rememberStandardStyleState {
                  configurationsState.apply {
                    theme = ThemeValue.MONOCHROME
                  }
                }
              )
            }
          ) {
            val marker = rememberIconImage(R.drawable.ic_red_marker)
            PointAnnotationGroup(
              annotations = points.map {
                PointAnnotationOptions()
                  .withPoint(it)
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
            ) {
              iconImage = marker
              interactionsState.isDraggable = true

              interactionsState.onClicked {
                Toast.makeText(
                  this@PointAnnotationClusterActivity,
                  "Clicked on Point Annotation Cluster's item: $it",
                  Toast.LENGTH_SHORT
                ).show()
                true
              }
                .onLongClicked {
                  Toast.makeText(
                    this@PointAnnotationClusterActivity,
                    "Long clicked on Circle Annotation Cluster's item: $it",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
                .onClusterClicked {
                  Toast.makeText(
                    this@PointAnnotationClusterActivity,
                    "On cluster Click - ID: ${it.clusterId}, points:  ${it.pointCount}, abbreviatedCount: ${it.pointCountAbbreviated}",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
                .onClusterLongClicked {
                  Toast.makeText(
                    this@PointAnnotationClusterActivity,
                    "On cluster Long Click - ID: ${it.clusterId}, points:  ${it.pointCount}, abbreviatedCount: ${it.pointCountAbbreviated}",
                    Toast.LENGTH_SHORT
                  ).show()
                  true
                }
            }
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

  private fun loadData(): List<Point> {
    val json = AnnotationUtils.loadStringFromNet(this@PointAnnotationClusterActivity, POINTS_URL)
    if (json == null) {
      runOnUiThread {
        Toast.makeText(this@PointAnnotationClusterActivity, "Failed to download data from network", Toast.LENGTH_LONG).show()
      }
      return emptyList()
    }
    return try {
      FeatureCollection.fromJson(json).features()?.let { features ->
        features.shuffled().take(AMOUNT).map { feature ->
          feature.geometry() as Point
        }
      } ?: emptyList()
    } catch (e: Exception) {
      logE(TAG, "Failed to parse GeoJSON: ${e.message}")
      runOnUiThread {
        Toast.makeText(this@PointAnnotationClusterActivity, "Failed to parse GeoJSON: ${e.message}", Toast.LENGTH_LONG).show()
      }
      emptyList()
    }
  }

  private companion object {
    const val TAG = "PointAnnotationCluster"
    const val ZOOM: Double = 10.0
    const val AMOUNT = 10000
    private const val POINTS_URL =
      "https://opendata.arcgis.com/datasets/01d0ff375695466d93d1fa2a976e2bdd_5.geojson"
  }
}