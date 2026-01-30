package com.mapbox.maps.extension.compose.style

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.coroutine.styleDataLoadedEvents
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotationGroup
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.HELSINKI
import com.mapbox.maps.extension.compose.internal.utils.offset
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.logD
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

public class LayerPositionAwareNodeTest {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testLayersInMapContent() {
    val mapboxMap = setMapContent(
      mapContent = { btn1State, btn2State, btn3State ->
        if (btn1State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(0.005))
            },
            layerId = remember { "circle-layer-1" }
          ) {
            circleColor = ColorValue(Color.Red)
            circleRadius = DoubleValue(30.0)
          }
        }
        if (btn2State) {
          CircleAnnotation(
            point = HELSINKI
          ) {
            circleRadius = 30.0
            circleColor = Color.Yellow
          }
        }
        if (btn3State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(-0.005))
            },
            layerId = remember { "circle-layer-2" }
          ) {
            circleColor = ColorValue(Color.Blue)
            circleRadius = DoubleValue(30.0)
          }
        }
      }
    )

    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-2",
        )
      )
    }
    // Hide circle 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-2",
        )
      )
    }
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-2",
        )
      )
    }

    // Hide circle annotation in middle
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2",
        )
      )
    }
    // Show circle annotation in middle again, it should be placed under circle 3
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-2",
        )
      )
    }

    // Hide everything
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(emptyList())
    }

    // Show middle circle annotation
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }

    // Show circle 2, it should be placed above circle annotation
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-2",
        )
      )
    }

    // Show circle 1, it should be placed below circle annotation
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-2",
        )
      )
    }
  }

  @Test
  public fun testLayersWithClusterInMapContent() {
    val mapboxMap = setMapContent(
      mapContent = { btn1State, btn2State, btn3State ->
        if (btn1State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(0.005))
            },
            layerId = remember { "circle-layer-1" }
          ) {
            circleColor = ColorValue(Color.Red)
            circleRadius = DoubleValue(30.0)
          }
        }
        if (btn2State) {
          CircleAnnotationGroup(
            annotations = CLUSTER_POINTS.map {
              CircleAnnotationOptions()
                .withPoint(it)
                .withCircleRadius(10.0)
                .withCircleColor(android.graphics.Color.RED)
            },
            annotationConfig = AnnotationConfig(
              annotationSourceOptions = AnnotationSourceOptions(
                clusterOptions = ClusterOptions(
                  textColorExpression = Expression.color(android.graphics.Color.YELLOW),
                  textColor = android.graphics.Color.BLACK, // Will not be applied as textColorExpression has been set
                  textSize = 20.0,
                  circleRadiusExpression = literal(25.0),
                  colorLevels = listOf(
                    Pair(100, android.graphics.Color.RED),
                    Pair(50, android.graphics.Color.BLUE),
                    Pair(0, android.graphics.Color.GREEN)
                  )
                )
              )
            )
          )
        }
        if (btn3State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(-0.005))
            },
            layerId = remember { "circle-layer-2" }
          ) {
            circleColor = ColorValue(Color.Blue)
            circleRadius = DoubleValue(30.0)
          }
        }
      }
    )

    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
          "circle-layer-2"
        )
      )
    }
    // Hide circle annotation cluster
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2"
        )
      )
    }
    // Show circle annotation cluster again, it should be placed under circle 2 and above circle 1
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
          "circle-layer-2"
        )
      )
    }

    // Hide circle layer 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
          "circle-layer-2"
        )
      )
    }
    // Show circle layer 1, it should be placed under circle annotation cluster
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
          "circle-layer-2"
        )
      )
    }

    // Hide circle layer 2
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
        )
      )
    }
    // Show circle layer 2, it should be placed above circle annotation cluster
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
          "circle-layer-2"
        )
      )
    }

    // Hide everything
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        emptyList()
      )
    }

    // Show circle layer 2
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-2"
        )
      )
    }

    // Show circle annotation cluster again, it should be placed under circle 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
          "circle-layer-2"
        )
      )
    }
    // Show circle layer 1, it should be placed under circle annotation cluster
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-0-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-1-.*",
          "mapbox-android-circleAnnotation-cluster-circle-layer-2-.*",
          "mapbox-android-circleAnnotation-cluster-text-layer-.*",
          "circle-layer-2"
        )
      )
    }
  }

  @Test
  public fun testAnnotationsInStyleSlot() {
    val mapboxMap = setMapContent(
      slotContent = { btn1State, btn2State, btn3State ->
        if (btn1State) {
          CircleAnnotation(
            point = HELSINKI.offset(0.005)
          ) {
            circleRadius = 30.0
            circleColor = Color.Red
          }
        }
        if (btn2State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI)
            },
            layerId = remember { "circle-layer-1" }
          ) {
            circleColor = ColorValue(Color.Yellow)
            circleRadius = DoubleValue(30.0)
          }
        }
        if (btn3State) {
          CircleAnnotation(
            point = HELSINKI.offset(-0.005)
          ) {
            circleRadius = 30.0
            circleColor = Color.Blue
          }
        }
      }
    )

    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }
    // Hide circle annotation 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }
    // Show circle 1 again, it should be placed under circle layer 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }

    // Hide circle layer 1
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }
    // Show circle layer 1, it should be placed above circle annotation 1 and below circle annotation 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }

    // Hide circle 3
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-1",
        )
      )
    }
    // Show circle annotation 2, it should be placed above circle layer 1
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }

    // Hide everything
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(emptyList())
    }

    // Show circle annotation 2
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          )
      )
    }
    // Show circle layer 1, it should be placed below circle annotation 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }
    // Show circle annotation 1 again, it should be placed below circle layer 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
          "circle-layer-1",
          "mapbox-android-circleAnnotation-layer-.*",
          "mapbox-android-circleAnnotation-draglayer-.*",
        )
      )
    }
  }

  @Test
  public fun testLayerInStyleSlot() {
    val mapboxMap = setMapContent(
      slotContent = { btn1State, btn2State, btn3State ->
        if (btn1State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(0.005))
            },
            layerId = remember { "circle-layer-1" }
          ) {
            circleColor = ColorValue(Color.Red)
            circleRadius = DoubleValue(30.0)
          }
        }
        if (btn2State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI)
            },
            layerId = remember { "circle-layer-2" }
          ) {
            circleColor = ColorValue(Color.Yellow)
            circleRadius = DoubleValue(30.0)
          }
        }
        if (btn3State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(-0.005))
            },
            layerId = remember { "circle-layer-3" }
          ) {
            circleColor = ColorValue(Color.Blue)
            circleRadius = DoubleValue(30.0)
          }
        }
      }
    )

    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }
    // Hide circle 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }

    // Hide circle 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-3"
        )
      )
    }
    // Show circle 2, it should be placed above circle 1 and below circle 3
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }

    // Hide circle 3
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2"
        )
      )
    }
    // Show circle 3, it should be placed above circle 2
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }

    // Hide circle 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }
    // Hide circle 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-3"
        )
      )
    }
    // Hide circle 3
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        emptyList()
      )
    }

    // Show circle 3, it should be placed above circle 2
    composeTestRule.clickAndWait(BTN3_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-3"
        )
      )
    }
    // Show circle 2, it should be placed above circle 1 and below circle 3
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "circle-layer-1",
          "circle-layer-2",
          "circle-layer-3"
        )
      )
    }
  }

  @Test
  public fun testLayersInBelowLayerContent() {
    val mapboxMap = setMapContent(
      contentBelowCountryLabel = { btn1State, btn2State, _ ->
        if (btn1State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(0.005))
            },
            layerId = remember { "circle-layer-1" }
          ) {
            circleColor = ColorValue(Color.Red)
            circleRadius = DoubleValue(30.0)
          }
        }
        if (btn2State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI)
            },
            layerId = remember { "circle-layer-2" }
          ) {
            circleColor = ColorValue(Color.Yellow)
            circleRadius = DoubleValue(30.0)
          }
        }
      }
    )

    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "circle-layer-1",
          "circle-layer-2",
          "country-label",
          "continent-label"
        )
      )
    }

    // Hide circle 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "settlement-major-label",
          "state-label",
          "circle-layer-2",
          "country-label",
          "continent-label"
        )
      )
    }
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "circle-layer-1",
          "circle-layer-2",
          "country-label",
          "continent-label"
        )
      )
    }

    // Hide circle 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "settlement-major-label",
          "state-label",
          "circle-layer-1",
          "country-label",
          "continent-label"
        )
      )
    }
    // Show circle 2, it should be placed above circle 1 and below "country-label"
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "circle-layer-1",
          "circle-layer-2",
          "country-label",
          "continent-label"
        )
      )
    }

    // Hide circle 1 and 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "settlement-major-label",
          "state-label",
          "country-label",
          "continent-label"
        )
      )
    }

    // Show circle 2, it should be placed  below "country-label"
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "circle-layer-2",
          "country-label",
          "continent-label"
        )
      )
    }
    // Show circle 1, it should be placed below circle 1 and below "country-label"
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "circle-layer-1",
          "circle-layer-2",
          "country-label",
          "continent-label"
        )
      )
    }
  }

  @Test
  public fun testLayersInAboveLayerContent() {
    val mapboxMap = setMapContent(
      contentAboveCountryLabel = { btn1State, btn2State, _ ->
        if (btn1State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI.offset(0.005))
            },
            layerId = remember { "circle-layer-1" }
          ) {
            circleColor = ColorValue(Color.Red)
            circleRadius = DoubleValue(30.0)
          }
        }
        if (btn2State) {
          CircleLayer(
            sourceState = rememberGeoJsonSourceState {
              data = GeoJSONData(HELSINKI)
            },
            layerId = remember { "circle-layer-2" }
          ) {
            circleColor = ColorValue(Color.Yellow)
            circleRadius = DoubleValue(30.0)
          }
        }
      }
    )

    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "country-label",
          "circle-layer-1",
          "circle-layer-2",
          "continent-label"
        )
      )
    }

    // Hide circle 1
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "settlement-major-label",
          "state-label",
          "country-label",
          "circle-layer-2",
          "continent-label"
        )
      )
    }
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "country-label",
          "circle-layer-1",
          "circle-layer-2",
          "continent-label"
        )
      )
    }

    // Hide circle 2
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "settlement-major-label",
          "state-label",
          "country-label",
          "circle-layer-1",
          "continent-label"
        )
      )
    }
    // Show circle 2, it should be placed above circle 1 and above "country-label"
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "country-label",
          "circle-layer-1",
          "circle-layer-2",
          "continent-label"
        )
      )
    }

    // Hide both circle, it should be placed above circle 1 and above "country-label"
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "country-label",
          "continent-label"
        )
      )
    }

    // Show circle 2, it should be placed above "country-label"
    composeTestRule.clickAndWait(BTN2_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "country-label",
          "circle-layer-2",
          "continent-label"
        )
      )
    }

    // Show circle 1, it should be placed above "country-label" but below circle 2
    composeTestRule.clickAndWait(BTN1_TAG)
    composeTestRule.runOnUiThread {
      mapboxMap.assertLayersPattern(
        listOf(
          "state-label",
          "country-label",
          "circle-layer-1",
          "circle-layer-2",
          "continent-label"
        )
      )
    }
  }

  private fun setMapContent(
    cameraCenter: Point = HELSINKI,
    // if not null, try to load mapbox-street style so layer positions from style are available, and content will be inserted below "country-label" layer
    contentBelowCountryLabel: (@Composable @MapboxMapComposable (Boolean, Boolean, Boolean) -> Unit)? = null,
    slotContent: @Composable @MapboxMapComposable ((Boolean, Boolean, Boolean) -> Unit)? = null,
    mapContent: @Composable @MapboxMapComposable (Boolean, Boolean, Boolean) -> Unit = { _, _, _ -> },
    contentAboveCountryLabel: (@Composable @MapboxMapComposable (Boolean, Boolean, Boolean) -> Unit)? = null,
  ): MapboxMap {
    val mapLoadLatch = CountDownLatch(1)
    lateinit var mapboxMap: MapboxMap
    composeTestRule.setContent {
      var btn1State by remember {
        mutableStateOf(true)
      }
      var btn2State by remember {
        mutableStateOf(true)
      }
      var btn3State by remember {
        mutableStateOf(true)
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          Modifier
            .fillMaxSize()
            .testTag(MAP_TEST_TAG),
          mapViewportState = rememberMapViewportState {
            setCameraOptions {
              zoom(ZOOM)
              center(cameraCenter)
            }
          },
          mapState = rememberMapState(),
          style = {
            if (contentBelowCountryLabel != null || contentAboveCountryLabel != null) {
              GenericStyle(
                style = Style.MAPBOX_STREETS,
                layerPositionedContent = layerPositionedContent {
                  belowLayer("country-label") {
                    contentBelowCountryLabel?.invoke(btn1State, btn2State, btn3State)
                  }
                  aboveLayer("country-label") {
                    contentAboveCountryLabel?.invoke(btn1State, btn2State, btn3State)
                  }
                }
              )
            } else if (slotContent != null) {
              MapboxStandardStyle(
                topSlot = {
                  slotContent(btn1State, btn2State, btn3State)
                }
              )
            } else {
              GenericStyle(style = "{}")
            }
          }
        ) {
          MapEffect(Unit) {
            it.mapboxMap.apply {
              styleDataLoadedEvents.first()
              mapboxMap = this
              mapLoadLatch.countDown()
            }
          }
          mapContent(btn1State, btn2State, btn3State)
        }
        Column {
          Button(
            modifier = Modifier.testTag(BTN1_TAG),
            onClick = { btn1State = !btn1State }
          ) {
            Text(text = "Button1")
          }
          Button(
            modifier = Modifier.testTag(BTN2_TAG),
            onClick = { btn2State = !btn2State }
          ) {
            Text(text = "Button2")
          }
          Button(
            modifier = Modifier.testTag(BTN3_TAG),
            onClick = { btn3State = !btn3State }
          ) {
            Text(text = "Button3")
          }
        }
      }
    }

    composeTestRule.waitForIdle()
    Assert.assertTrue(mapLoadLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))
    return mapboxMap
  }

  private companion object {
    private const val TAG = "LayerPositionAwareNodeTest"
    private const val ZOOM: Double = 9.0
    private const val BTN1_TAG = "button1"
    private const val BTN2_TAG = "button2"
    private const val BTN3_TAG = "button3"
    private const val MAP_TEST_TAG = "map_tag"
    private const val TEST_TIMEOUT_MS = 5000L
    private const val INTERVAL = 0.01
    private val CLUSTER_POINTS: List<Point> by lazy {
      mutableListOf<Point>().apply {
        repeat(10) { x ->
          repeat(10) { y ->
            add(
              Point.fromLngLat(
                HELSINKI.longitude() + INTERVAL * x,
                HELSINKI.latitude() + INTERVAL * y
              )
            )
          }
        }
      }
    }
  }

  private fun ComposeContentTestRule.clickAndWait(tag: String) {
    onNodeWithTag(tag).performClick()
    waitForIdle()
  }

  private fun MapboxMap.assertLayersPattern(
    layersPattern: List<String>,
  ) {
    styleLayers.map { it.id }.takeLast(layersPattern.size)
      .onEach { logD(TAG, it) }
      .forEachIndexed { index, layer ->
        val regex = layersPattern[index].toRegex()
        Assert.assertTrue(
          "match pattern failed for $layer, pattern: $regex",
          layer.matches(regex)
        )
      }
  }
}