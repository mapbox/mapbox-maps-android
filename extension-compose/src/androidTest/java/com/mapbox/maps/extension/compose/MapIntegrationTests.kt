package com.mapbox.maps.extension.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeUp
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.internal.utils.CityLocations
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

public class MapIntegrationTests {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testMapViewWhenMapIsLoaded() {
    var mapView: MapView? = null
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapState = rememberMapState()
        ) {
          MapEffect(Unit) {
            mapView = it
          }
        }
      }
    }
    composeTestRule.waitUntil(timeoutMillis = 3_000) { mapView != null }
  }

  @Test
  public fun testMapDoubleTap() {
    lateinit var cameraState: CameraState
    composeTestRule.setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }
      val mapState = rememberMapState()
      LaunchedEffect(Unit) {
        mapState.cameraChangedEvents.collect {
          cameraState = it.cameraState
        }
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = mapViewportState,
          mapState = mapState
        )
      }
    }
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput { doubleClick() }
    composeTestRule.waitUntil {
      cameraState.zoom == ZOOM + 1
    }
  }

  @Test
  public fun testMapDoubleTapDisabled() {
    lateinit var cameraState: CameraState
    composeTestRule.setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }
      val mapState = rememberMapState {
        gesturesSettings = GesturesSettings { doubleTapToZoomInEnabled = false }
      }
      LaunchedEffect(Unit) {
        mapState.cameraChangedEvents.collect {
          cameraState = it.cameraState
        }
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = mapViewportState,
          mapState = mapState,
        )
      }
    }
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput { doubleClick() }
    assertEquals(ZOOM, cameraState.zoom, EPS)
  }

  @Test
  public fun testMapPinchToZoomOut() {
    lateinit var cameraState: CameraState
    composeTestRule.setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }
      val mapState = rememberMapState()
      LaunchedEffect(Unit) {
        mapState.cameraChangedEvents.collect {
          cameraState = it.cameraState
        }
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = mapViewportState,
          mapState = mapState,
        )
      }
    }
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput {
      down(0, center + Offset(0f, -10f))
      down(1, center + Offset(0f, 10f))

      moveBy(0, Offset(0f, -50f))
      moveBy(1, Offset(0f, +50f))
      up(0)
      up(1)
    }
    composeTestRule.waitUntil {
      cameraState.zoom < ZOOM
    }
  }

  @Test
  public fun testMapScrollDisabled() {
    lateinit var cameraState: CameraState
    composeTestRule.setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }
      val mapState = rememberMapState {
        gesturesSettings = GesturesSettings {
          scrollEnabled = false
        }
      }

      LaunchedEffect(Unit) {
        mapState.cameraChangedEvents.collect {
          cameraState = it.cameraState
        }
      }

      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapState = mapState,
          mapViewportState = mapViewportState,
        )
      }
    }
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput {
      swipeLeft()
      swipeUp()
    }
    assertEquals(CityLocations.HELSINKI.latitude(), cameraState.center.latitude(), EPS)
    assertEquals(CityLocations.HELSINKI.longitude(), cameraState.center.longitude(), EPS)
  }

  @Test
  public fun testMapScrollHorizontally() {
    lateinit var cameraState: CameraState
    composeTestRule.setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }
      val mapState = rememberMapState()
      LaunchedEffect(Unit) {
        mapState.cameraChangedEvents.collect {
          cameraState = it.cameraState
        }
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = mapViewportState,
          mapState = mapState,
        )
      }
    }
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput {
      swipeLeft()
      swipeUp()
    }
    composeTestRule.waitUntil {
      cameraState.center.longitude() != CityLocations.HELSINKI.longitude() &&
        cameraState.center.latitude() != CityLocations.HELSINKI.latitude()
    }
  }

  @Test
  public fun testMapPinchToZoomDisabled() {
    lateinit var cameraState: CameraState
    composeTestRule.setContent {
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }
      val mapState = rememberMapState {
        gesturesSettings = GesturesSettings { pinchToZoomEnabled = false }
      }
      LaunchedEffect(Unit) {
        mapState.cameraChangedEvents.collect {
          cameraState = it.cameraState
        }
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = mapViewportState,
          mapState = mapState,
        )
      }
    }
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput {
      down(0, center + Offset(0f, -10f))
      down(1, center + Offset(0f, 10f))

      moveBy(0, Offset(0f, -50f))
      moveBy(1, Offset(0f, +50f))
      up(0)
      up(1)
    }
    composeTestRule.waitUntil { ZOOM == cameraState.zoom }
  }

  @Test
  public fun testMapOrnaments() {
    val compassTag = "compass"
    val scaleBarTag = "scalebar"
    val logoTag = "logo"
    val attributionTag = "attribution"
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = rememberMapViewportState {
            setCameraOptions {
              zoom(ZOOM)
              center(CityLocations.HELSINKI)
              bearing(30.0)
            }
          },
          compass = {
            Compass(modifier = Modifier.testTag(compassTag))
          },
          scaleBar = {
            ScaleBar(modifier = Modifier.testTag(scaleBarTag))
          },
          logo = {
            Logo(modifier = Modifier.testTag(logoTag))
          },
          attribution = {
            Attribution(modifier = Modifier.testTag(attributionTag))
          }
        )
      }
    }
    composeTestRule.onNodeWithTag(compassTag).assertExists()
    composeTestRule.onNodeWithTag(scaleBarTag).assertExists()
    composeTestRule.onNodeWithTag(logoTag).assertExists()
    composeTestRule.onNodeWithTag(attributionTag).assertExists().performClick()
    val attributionDialogTitle = InstrumentationRegistry
      .getInstrumentation()
      .context
      .resources
      .getString(R.string.mapbox_attributionsDialogTitle)
    composeTestRule.onNodeWithText(attributionDialogTitle).assertExists()
  }

  @Test
  public fun testMapWithCompass() {
    val compassTag = "compass"
    var bearing = -1.0
    composeTestRule.setContent {
      val mapState = rememberMapState()
      LaunchedEffect(Unit) {
        mapState.cameraChangedEvents.collect {
          bearing = it.cameraState.bearing
        }
      }
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = rememberMapViewportState {
            setCameraOptions {
              zoom(ZOOM)
              center(CityLocations.HELSINKI)
              bearing(30.0)
            }
          },
          mapState = mapState,
          compass = {
            Compass(modifier = Modifier.testTag(compassTag))
          }
        )
      }
    }
    composeTestRule.onNodeWithTag(compassTag)
      .assertExists()
      .performClick()
    composeTestRule.onNodeWithContentDescription(compassTag).assertDoesNotExist()
    composeTestRule.waitUntil { bearing == 0.0 }
  }

  @Test
  public fun testMapEffectWithState() {
    var actualRefreshCount = 0
    val contentDescription = "Increment"
    composeTestRule.setContent {
      var count by remember {
        mutableStateOf(0)
      }
      Scaffold(
        floatingActionButton = {
          FloatingActionButton(onClick = { count += 1 }) {
            Icon(Icons.Filled.Add, contentDescription)
          }
        }
      ) { padding ->
        MapboxMap(
          modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .testTag(MAP_TEST_TAG)
        ) {
          MapEffect(count) {
            actualRefreshCount += 1
          }
        }
      }
    }
    composeTestRule.onNodeWithContentDescription(contentDescription)
      .performClick()
      .performClick()
    composeTestRule.waitUntil { actualRefreshCount == 2 }
  }

  @Test
  public fun testMapViewPort() {
    var actualZoom = 0.0
    lateinit var actualCenter: Point
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = rememberMapViewportState {
            setCameraOptions {
              zoom(ZOOM)
              center(CityLocations.HELSINKI)
            }
          },
        ) {
          MapEffect(Unit) {
            actualZoom = it.mapboxMap.cameraState.zoom
            actualCenter = it.mapboxMap.cameraState.center
          }
        }
      }
    }
    assertEquals(ZOOM, actualZoom, EPS)
    assertEquals(CityLocations.HELSINKI.latitude(), actualCenter.latitude(), EPS)
    assertEquals(CityLocations.HELSINKI.longitude(), actualCenter.longitude(), EPS)
  }

  private companion object {
    private const val MAP_TEST_TAG = "map_tag"
    private const val ZOOM: Double = 10.0
    private const val EPS = 0.0001
  }
}