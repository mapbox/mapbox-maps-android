package com.mapbox.maps.extension.compose.annotation

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.HELSINKI
import com.mapbox.maps.extension.compose.internal.utils.CityLocations.MINSK
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
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
      visible = true
    ) {
      Text(VIEW_ANNOTATION_TEXT)
    }
    composeTestRule.waitForIdle()
    composeTestRule.waitUntil(timeoutMillis = VIEW_APPEAR_TIMEOUT_MS, condition = {
      composeTestRule
        .onAllNodesWithText(VIEW_ANNOTATION_TEXT)
        .fetchSemanticsNodes().size == 1
    })
    composeTestRule.onNodeWithText(VIEW_ANNOTATION_TEXT).assertExists(VIEW_ANNOTATION_TEXT)

    repeat(40) {
      composeTestRule.onNodeWithTag(MAP_TEST_TAG).performTouchInput {
        swipeLeft()
      }
      composeTestRule.waitForIdle()
    }
    composeTestRule.onNodeWithText(VIEW_ANNOTATION_TEXT).assertDoesNotExist()
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
        mapViewportState = MapViewportState().apply {
          setCameraOptions {
            zoom(ZOOM)
            center(cameraCenter)
          }
        }
      ) {
        ViewAnnotation(
          layoutParams = ViewGroup.LayoutParams(
            100, 100
          ),
          options = viewAnnotationOptions {
            geometry(annotationCenter)
            annotationAnchor {
              anchor(ViewAnnotationAnchor.BOTTOM)
            }
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
    private const val VIEW_APPEAR_TIMEOUT_MS = 5000L
  }
}