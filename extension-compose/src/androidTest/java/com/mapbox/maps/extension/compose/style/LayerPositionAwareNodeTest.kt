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
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.coroutine.styleDataLoadedEvents
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
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
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@OptIn(MapboxExperimental::class)
public class LayerPositionAwareNodeTest {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testLayerPositionsUnderMapboxMap() {
    val mapLoadLatch = CountDownLatch(1)
    lateinit var mapboxMap: MapboxMap
    setMapContent(
      cameraCenter = HELSINKI,
      onStyleLoaded = {
        mapboxMap = it
        mapLoadLatch.countDown()
      },
      mapContent = { btn1State, btn2State ->
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
    )
    Assert.assertTrue(mapLoadLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))

    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("mapbox-android-circleAnnotation-layer-.*"),
        Regex("mapbox-android-circleAnnotation-draglayer-.*"),
        Regex("circle-layer-2")
      )
    )

    // Hide circle 1
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("mapbox-android-circleAnnotation-layer-.*"),
        Regex("mapbox-android-circleAnnotation-draglayer-.*"),
        Regex("circle-layer-2")
      )
    )
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("mapbox-android-circleAnnotation-layer-.*"),
        Regex("mapbox-android-circleAnnotation-draglayer-.*"),
        Regex("circle-layer-2")
      )
    )

    // Hide circle annotation in middle
    composeTestRule.onNodeWithTag(BTN2_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("circle-layer-2")
      )
    )
    // Show circle annotation in middle again, it should be placed under circle 3
    composeTestRule.onNodeWithTag(BTN2_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("mapbox-android-circleAnnotation-layer-.*"),
        Regex("mapbox-android-circleAnnotation-draglayer-.*"),
        Regex("circle-layer-2")
      )
    )
  }

  @Test
  public fun testLayerPositionsWithClusterUnderMapboxMap() {
    val mapLoadLatch = CountDownLatch(1)
    lateinit var mapboxMap: MapboxMap
    setMapContent(
      cameraCenter = HELSINKI,
      onStyleLoaded = {
        mapboxMap = it
        mapLoadLatch.countDown()
      },
      mapContent = { btn1State, _ ->
        CircleLayer(
          sourceState = rememberGeoJsonSourceState {
            data = GeoJSONData(HELSINKI.offset(0.005))
          },
          layerId = remember { "circle-layer-1" }
        ) {
          circleColor = ColorValue(Color.Red)
          circleRadius = DoubleValue(30.0)
        }
        if (btn1State) {
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
    )
    Assert.assertTrue(mapLoadLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))

    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("mapbox-android-circleAnnotation-layer-.*"),
        Regex("mapbox-android-circleAnnotation-draglayer-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-circle-layer-0-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-circle-layer-1-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-circle-layer-2-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-text-layer-.*"),
        Regex("circle-layer-2")
      )
    )

    // Hide circle annotation cluster
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("circle-layer-2")
      )
    )
    // Show circle annotation cluster again, it should be placed under circle 2 and above circle 1
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("mapbox-android-circleAnnotation-layer-.*"),
        Regex("mapbox-android-circleAnnotation-draglayer-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-circle-layer-0-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-circle-layer-1-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-circle-layer-2-.*"),
        Regex("mapbox-android-circleAnnotation-cluster-text-layer-.*"),
        Regex("circle-layer-2")
      )
    )
  }

  @Test
  public fun testLayerPositionUnderStyleSlot() {
    val mapLoadLatch = CountDownLatch(1)
    lateinit var mapboxMap: MapboxMap
    setMapContent(
      cameraCenter = HELSINKI,
      onStyleLoaded = {
        mapboxMap = it
        mapLoadLatch.countDown()
      },
      slotContent = { btn1State, btn2State ->
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
    )
    Assert.assertTrue(mapLoadLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))

    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("circle-layer-2"),
        Regex("circle-layer-3")
      )
    )

    // Hide circle 1
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-2"),
        Regex("circle-layer-3")
      )
    )
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("circle-layer-2"),
        Regex("circle-layer-3")
      )
    )

    // Hide circle 2
    composeTestRule.onNodeWithTag(BTN2_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("circle-layer-3")
      )
    )
    // Show circle 2, it should be placed above circle 1 and below circle 3
    composeTestRule.onNodeWithTag(BTN2_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.assertLayersPattern(
      listOf(
        Regex("circle-layer-1"),
        Regex("circle-layer-2"),
        Regex("circle-layer-3")
      )
    )
  }

  @Test
  public fun testLayerPositionUnderLayerPositionedContent() {
    val mapLoadLatch = CountDownLatch(1)
    lateinit var mapboxMap: MapboxMap
    setMapContent(
      cameraCenter = HELSINKI,
      onStyleLoaded = {
        mapboxMap = it
        mapLoadLatch.countDown()
      },
      layerPositionedContent = { btn1State, btn2State ->
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
    Assert.assertTrue(mapLoadLatch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS))

    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id }.takeLast(5) }.onEach {
      logD(TAG, it)
    }.assertLayersPattern(
      listOf(
        Regex("state-label"),
        Regex("circle-layer-1"),
        Regex("circle-layer-2"),
        Regex("country-label"),
        Regex("continent-label")
      )
    )

    // Hide circle 1
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.takeLast(5).onEach {
      logD(TAG, it)
    }.assertLayersPattern(
      listOf(
        Regex("settlement-major-label"),
        Regex("state-label"),
        Regex("circle-layer-2"),
        Regex("country-label"),
        Regex("continent-label")
      )
    )
    // Show circle 1 again, it should be placed under circle 2
    composeTestRule.onNodeWithTag(BTN1_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.takeLast(5).onEach {
      logD(TAG, it)
    }.assertLayersPattern(
      listOf(
        Regex("state-label"),
        Regex("circle-layer-1"),
        Regex("circle-layer-2"),
        Regex("country-label"),
        Regex("continent-label")
      )
    )

    // Hide circle 2
    composeTestRule.onNodeWithTag(BTN2_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.takeLast(5)
      .assertLayersPattern(
        listOf(
          Regex("settlement-major-label"),
          Regex("state-label"),
          Regex("circle-layer-1"),
          Regex("country-label"),
          Regex("continent-label")
        )
      )
    // Show circle 2, it should be placed above circle 1 and below "country-label"
    composeTestRule.onNodeWithTag(BTN2_TAG).performClick()
    composeTestRule.waitForIdle()
    composeTestRule.runOnUiThread { mapboxMap.styleLayers.map { it.id } }.takeLast(5)
      .assertLayersPattern(
        listOf(
          Regex("state-label"),
          Regex("circle-layer-1"),
          Regex("circle-layer-2"),
          Regex("country-label"),
          Regex("continent-label")
        )
      )
  }

  private fun List<String>.assertLayersPattern(expectedPatterns: List<Regex>) {
    Assert.assertEquals(expectedPatterns.size, this.size)
    forEachIndexed { index, s ->
      Assert.assertTrue(
        "match pattern failed for $s, pattern: ${expectedPatterns[index].pattern}",
        s.matches(expectedPatterns[index])
      )
    }
  }

  private fun setMapContent(
    cameraCenter: Point = HELSINKI,
    onStyleLoaded: (MapboxMap) -> Unit = {},
    // if not null, try to load mapbox-street style so layer positions from style are available, and content will be inserted below "country-label" layer
    layerPositionedContent: (@Composable @MapboxMapComposable (Boolean, Boolean) -> Unit)? = null,
    slotContent: @Composable @MapboxMapComposable (Boolean, Boolean) -> Unit = { _, _ -> },
    mapContent: @Composable @MapboxMapComposable (Boolean, Boolean) -> Unit = { _, _ -> },
  ) {
    composeTestRule.setContent {
      var btn1State by remember {
        mutableStateOf(true)
      }
      var btn2State by remember {
        mutableStateOf(true)
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
            Modifier
                .fillMaxSize()
                .testTag(MAP_TEST_TAG),
          mapViewportState = MapViewportState().apply {
            setCameraOptions {
              zoom(ZOOM)
              center(cameraCenter)
            }
          },
          mapState = rememberMapState(),
          style = {
            layerPositionedContent?.let {
              GenericStyle(
                style = Style.MAPBOX_STREETS,
                layerPositionedContent = layerPositionedContent {
                  belowLayer("country-label") {
                    it(btn1State, btn2State)
                  }
                }
              )
            } ?: MapboxStandardStyle(
              topSlot = {
                slotContent(btn1State, btn2State)
              }
            )
          }
        ) {
          MapEffect(Unit) {
            it.mapboxMap.apply {
              styleDataLoadedEvents.collect {
                onStyleLoaded(this)
              }
            }
          }
          mapContent(btn1State, btn2State)
        }
        Column {
          Button(
            modifier = Modifier.testTag(BTN1_TAG),
            onClick = {
              btn1State = !btn1State
            }
          ) {
            Text(text = "Button1")
          }
          Button(
            modifier = Modifier.testTag(BTN2_TAG),
            onClick = {
              btn2State = !btn2State
            }
          ) {
            Text(text = "Button2")
          }
        }
      }
    }

    composeTestRule.waitForIdle()
  }

  private companion object {
    private const val TAG = "LayerPositionAwareNodeTest"
    private const val ZOOM: Double = 9.0
    private const val BTN1_TAG = "button1"
    private const val BTN2_TAG = "button2"
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
}