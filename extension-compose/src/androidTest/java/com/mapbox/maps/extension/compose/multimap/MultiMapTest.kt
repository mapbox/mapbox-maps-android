package com.mapbox.maps.extension.compose.multimap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.internal.utils.CityLocations
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test

@OptIn(MapboxExperimental::class)
public class MultiMapTest {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testNodesManipulationWithMultipleMaps() {
    composeTestRule.setContent {
      var viewAnnotationList1 by remember { mutableStateOf(emptyList<Point>()) }
      var viewAnnotationList2 by remember { mutableStateOf(emptyList<Point>()) }
      var showMap1 by remember { mutableStateOf(true) }

      LaunchedEffect(Unit) {
        launch {
          // add view annotation for map1 after 500ms delay
          // This will add 2 annotation nodes to map1 and 2 annotation nodes to map2
          // There would be in total 4 annotations across 2 maps
          delay(ADD_ANNOTATIONS_DELAY)
          viewAnnotationList1 = points1
          viewAnnotationList2 = points1
          // Remove map1 from the tree, this will clear map1's node tree
          // There would be in total 2 annotations across 2 maps
          showMap1 = false

          // add 2 more view annotation for map2 after 1000ms delay
          // This will add 2 more annotation nodes to map2(relative to map2's node tree)
          // Since map1 is detached, there would be in total 4 annotations across 2 maps
          delay(ADD_ANNOTATIONS_DELAY)
          viewAnnotationList2 = points1 + points2
          // Add map1 back after map2's annotation has been added
          // There would be in total 6 annotations across 2 maps
          showMap1 = true
        }
      }
      Column {
        if (showMap1) {
          MapboxMap(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
            mapViewportState = MapViewportState().apply {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            },
          ) {
            viewAnnotationList1.forEachIndexed { index, point ->
              ViewAnnotation(
                options = viewAnnotationOptions {
                  geometry(point)
                  allowOverlap(true)
                }
              ) {
                Text(text = "Annotation $index", color = Color.Red)
              }
            }
          }
        }
        MapboxMap(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
          mapViewportState = MapViewportState().apply {
            setCameraOptions {
              zoom(ZOOM)
              center(CityLocations.HELSINKI)
            }
          }
        ) {
          viewAnnotationList2.forEachIndexed { index, point ->
            ViewAnnotation(
              options = viewAnnotationOptions {
                geometry(point)
                allowOverlap(true)
              }
            ) {
              Text(text = "Annotation $index", color = Color.Blue)
            }
          }
        }
      }
    }
    // The above use case will result in following crash, if the root node is static and shared across multiple map instances:
    // java.lang.IndexOutOfBoundsException: index=1 count=0

    // The reason is that if these two maps would be using the same tree, removal of the nodes added in map1 would result in new
    // nodes added to map2 to be inserted at non-exist index position.

    // It's important to make sure each MapboxMap node tree have its own RootNode, so the two trees won't interfere with each other.
    composeTestRule.waitUntil(timeoutMillis = TEST_TIMEOUT, condition = {
      composeTestRule
        .onAllNodesWithText(text = "Annotation", substring = true)
        .fetchSemanticsNodes().size == 6
    })
  }

  private companion object {
    const val ZOOM: Double = 13.0
    const val TEST_TIMEOUT = 1500L
    const val ADD_ANNOTATIONS_DELAY = 500L

    val points1 = listOf(
      Point.fromLngLat(24.94216010242652, 60.16876757234266),
      Point.fromLngLat(24.929766009141733, 60.170292490574944),
    )
    val points2 = listOf(
      Point.fromLngLat(24.947481155604, 60.1731440090149),
      Point.fromLngLat(24.937481155604, 60.1631440090149)
    )
  }
}