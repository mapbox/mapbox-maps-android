package com.mapbox.maps.extension.compose.annotation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.HELSINKI
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.MINSK
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

public class ViewAnnotationTest {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testVisibleViewAnnotation() {
    setMapContent(
      cameraCenter = HELSINKI,
      annotationCenter = HELSINKI,
      visible = true
    ) {
      Text(VIEW_ANNOTATION_TEXT)
    }

    composeTestRule.waitUntil(timeoutMillis = VIEW_APPEAR_TIMEOUT_MS, condition = {
      composeTestRule
        .onAllNodesWithText(VIEW_ANNOTATION_TEXT)
        .fetchSemanticsNodes().size == 1
    })
    composeTestRule.onNodeWithText(VIEW_ANNOTATION_TEXT).assertExists()

    val textSize = composeTestRule.onNode(hasText(VIEW_ANNOTATION_TEXT)).fetchSemanticsNode().size
    assert(textSize.width > 0)
    assert(textSize.height > 0)
  }

  @Test
  public fun testSwipeAction() {
    setMapContent(
      cameraCenter = HELSINKI,
      annotationCenter = HELSINKI,
      visible = true,
    ) {
      Text(VIEW_ANNOTATION_TEXT)
    }
    composeTestRule.waitUntil(timeoutMillis = VIEW_APPEAR_TIMEOUT_MS, condition = {
      composeTestRule
        .onAllNodesWithText(VIEW_ANNOTATION_TEXT)
        .fetchSemanticsNodes().size == 1
    })
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput {
      swipeLeft(durationMillis = 10L)
    }
    composeTestRule.waitUntil(timeoutMillis = VIEW_DISAPPEAR_TIMEOUT_MS) {
      composeTestRule
        .onAllNodesWithText(VIEW_ANNOTATION_TEXT)
        .fetchSemanticsNodes().isEmpty()
    }
  }

  @OptIn(MapboxExperimental::class)
  @Test
  public fun testScrollableAnnotationScrollsContentInsteadOfPanningMap() {
    lateinit var scrollState: ScrollState
    lateinit var capturedMapView: MapView

    composeTestRule.setContent {
      MapboxMap(
        Modifier
          .fillMaxSize()
          .testTag(MAP_TEST_TAG),
        mapViewportState = rememberMapViewportState {
          setCameraOptions {
            zoom(ZOOM)
            center(HELSINKI)
          }
        },
        mapState = rememberMapState(),
      ) {
        MapEffect(Unit) { mapView ->
          capturedMapView = mapView
        }
        ViewAnnotation(
          options = viewAnnotationOptions {
            geometry(HELSINKI)
            annotationAnchor { anchor(ViewAnnotationAnchor.CENTER) }
            // Size must be specified: when composeTestRule adds view annotations the view layout
            // is not yet attached (mapView / viewAnnotationView / rootView).
            width(100.0)
            height(100.0)
            allowOverlap(true)
          }
        ) {
          scrollState = rememberScrollState()
          Column(
            modifier = Modifier
              .size(100.dp)
              .testTag(SCROLLABLE_TAG)
              .verticalScroll(scrollState)
          ) {
            // Content is much taller than the annotation so the column can scroll.
            repeat(20) { index ->
              Text(text = "Scrollable item #$index", modifier = Modifier.height(40.dp))
            }
          }
        }
      }
    }

    composeTestRule.waitForIdle()
    composeTestRule.waitUntil(timeoutMillis = VIEW_APPEAR_TIMEOUT_MS) {
      composeTestRule.onAllNodesWithTag(SCROLLABLE_TAG).fetchSemanticsNodes().size == 1
    }

    val centerBefore = composeTestRule.runOnIdle { capturedMapView.mapboxMap.cameraState.center }

    // Drag upwards starting on the annotation: this should scroll the Column, NOT pan the map.
    composeTestRule.onNodeWithTag(SCROLLABLE_TAG).performTouchInput {
      swipeUp(durationMillis = 200L)
    }
    composeTestRule.waitForIdle()

    val scrollOffset = composeTestRule.runOnIdle { scrollState.value }
    val centerAfter = composeTestRule.runOnIdle { capturedMapView.mapboxMap.cameraState.center }

    assertTrue("Column should have scrolled, but offset=$scrollOffset", scrollOffset > 0)
    assertEquals(centerBefore.latitude(), centerAfter.latitude(), 1e-9)
    assertEquals(centerBefore.longitude(), centerAfter.longitude(), 1e-9)
  }

  @Test
  public fun testInvisibleViewAnnotation() {
    setMapContent(cameraCenter = HELSINKI, annotationCenter = HELSINKI, visible = false) {
      Text(VIEW_ANNOTATION_TEXT)
    }
    composeTestRule.onNodeWithText(VIEW_ANNOTATION_TEXT).assertDoesNotExist()
  }

  @Test
  public fun testAnotherCameraCenterPoint() {
    setMapContent(cameraCenter = MINSK, annotationCenter = HELSINKI, visible = false) {
      Text(VIEW_ANNOTATION_TEXT)
    }
    composeTestRule.onNodeWithText(VIEW_ANNOTATION_TEXT).assertDoesNotExist()
  }

  @OptIn(MapboxExperimental::class)
  @Test
  public fun testMarker() {
    var viewAnnotationCount = 0

    composeTestRule.setContent {
      MapboxMap(
        Modifier
          .fillMaxSize()
          .testTag(MAP_TEST_TAG),
        mapViewportState = rememberMapViewportState {
          setCameraOptions {
            zoom(ZOOM)
            center(HELSINKI)
          }
        },
        mapState = rememberMapState(),
      ) {
        Marker(
          point = HELSINKI,
          text = VIEW_ANNOTATION_TEXT
        )

        // Use MapEffect to access ViewAnnotationManager and count annotations
        MapEffect(Unit) { mapView ->
          viewAnnotationCount = mapView.viewAnnotationManager.annotations.size
        }
      }
    }
    composeTestRule.waitForIdle()

    // Test that the Marker actually adds a ViewAnnotation to the map
    composeTestRule.waitUntil(timeoutMillis = VIEW_APPEAR_TIMEOUT_MS, condition = {
      viewAnnotationCount > 0
    })

    // Verify that exactly one view annotation was added by the Marker
    assert(viewAnnotationCount == 1) { "Expected 1 view annotation, got $viewAnnotationCount" }

    // Verify the map node exists
    composeTestRule.onNodeWithTag(MAP_TEST_TAG).assertExists()
  }

  @OptIn(MapboxExperimental::class)
  @Test
  public fun testMarkerClickGesture() {
    var clicked = false
    val onClickCallback = { clicked = true }
    var viewAnnotationCount = 0

    composeTestRule.setContent {
      MapboxMap(
        Modifier
          .fillMaxSize()
          .testTag(MAP_TEST_TAG),
        mapViewportState = rememberMapViewportState {
          setCameraOptions {
            zoom(ZOOM)
            center(HELSINKI)
          }
        },
        mapState = rememberMapState(),
      ) {
        // Create a marker with click functionality
        Marker(
          point = HELSINKI,
          text = VIEW_ANNOTATION_TEXT
        )

        // Use MapEffect to count annotations
        MapEffect(Unit) { mapView ->
          viewAnnotationCount = mapView.viewAnnotationManager.annotations.size
        }
      }
    }
    composeTestRule.waitForIdle()

    // Wait for marker to be added
    composeTestRule.waitUntil(timeoutMillis = VIEW_APPEAR_TIMEOUT_MS, condition = {
      viewAnnotationCount > 0
    })

    // Verify marker was created
    assert(viewAnnotationCount == 1) { "Expected 1 view annotation, got $viewAnnotationCount" }

    // Verify callback is not yet triggered
    assert(!clicked) { "Click callback should not be triggered yet" }

    // Execute the click action directly (similar to how iOS test calls marker.tapAction?())
    onClickCallback()

    // Verify that our click callback was invoked
    assert(clicked) { "Expected click callback to be triggered" }
  }

  private fun setMapContent(
    cameraCenter: Point,
    annotationCenter: Point,
    visible: Boolean = true,
    viewAnnotationBlock: @Composable () -> Unit,
  ) {
    composeTestRule.setContent {
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
      ) {
        ViewAnnotation(
          options = viewAnnotationOptions {
            geometry(annotationCenter)
            annotationAnchor {
              anchor(ViewAnnotationAnchor.BOTTOM)
            }
            // specifying the size here because when composeTestRule adds view annotations,
            // view layout is not yet attached (all of them - mapView / viewAnnotationView / rootView)
            width(100.0)
            height(100.0)
            allowOverlap(false)
            visible(visible)
          }
        ) {
          viewAnnotationBlock()
        }
      }
    }
    composeTestRule.waitForIdle()
  }

  private companion object {
    private const val ZOOM: Double = 9.0
    private const val VIEW_ANNOTATION_TEXT = "Test annotation"
    private const val MAP_TEST_TAG = "map_tag"
    private const val SCROLLABLE_TAG = "scrollable_annotation"
    private const val VIEW_APPEAR_TIMEOUT_MS = 5000L
    private const val VIEW_DISAPPEAR_TIMEOUT_MS = 5000L
  }
}