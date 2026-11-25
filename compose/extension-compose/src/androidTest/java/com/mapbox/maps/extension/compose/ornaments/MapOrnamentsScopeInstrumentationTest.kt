package com.mapbox.maps.extension.compose.ornaments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import org.junit.Rule
import org.junit.Test

@OptIn(MapboxExperimental::class)
public class MapOrnamentsScopeInstrumentationTest {

  @get:Rule
  public val composeTestRule: ComposeContentTestRule = createComposeRule()

  @Test
  public fun testDefaultsRendersStandardOrnaments() {
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          // Compass fades out when facing north, so start bearing away from 0.
          mapViewportState = rememberMapViewportState { setCameraOptions { bearing(30.0) } },
          ornaments = { Defaults() },
        )
      }
    }
    awaitNodeWithContentDescription("Mapbox Compass")
    composeTestRule.onNodeWithContentDescription("Mapbox Logo").assertExists()
    composeTestRule.onNodeWithContentDescription("Mapbox Attribution").assertExists()
  }

  @Test
  public fun testDefaultsHidesOrnamentWhenSlotIsNull() {
    // The logo is used rather than the compass: the compass hides itself when the camera faces
    // north, so asserting its absence would pass whether or not the slot was honoured.
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          ornaments = { Defaults { logo = null } },
        )
      }
    }
    // Once a sibling default ornament is present, the ornaments slot has run, so an absent logo
    // can only be the result of the null slot.
    awaitNodeWithContentDescription("Mapbox Attribution")
    composeTestRule.onNodeWithContentDescription("Mapbox Logo").assertDoesNotExist()
  }

  @Test
  public fun testDefaultsRendersReplacementInsteadOfDefault() {
    val customCompassTag = "customCompass"
    val customScaleBarTag = "customScaleBar"
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = rememberMapViewportState { setCameraOptions { bearing(30.0) } },
          ornaments = {
            Defaults {
              compass = { Compass(modifier = Modifier.testTag(customCompassTag)) }
              scaleBar = { ScaleBar(modifier = Modifier.testTag(customScaleBarTag)) }
            }
          },
        )
      }
    }
    awaitNodeWithTag(customCompassTag)
    composeTestRule.onNodeWithTag(customScaleBarTag).assertExists()
    // Untouched slots still render their default ornament.
    composeTestRule.onNodeWithContentDescription("Mapbox Logo").assertExists()
  }

  @Test
  public fun testIndoorSelectorComposesAlongsideDefaults() {
    var indoorSelectorInvoked = false
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          mapViewportState = rememberMapViewportState { setCameraOptions { bearing(30.0) } },
          ornaments = {
            Defaults()
            indoorSelector {
              indoorSelectorInvoked = true
              IndoorSelector()
            }
          },
        )
      }
    }
    awaitNodeWithContentDescription("Mapbox Compass")
    // The default IndoorSelector draws nothing until an indoor building is in view, so there is
    // no UI to look for. Assert that the slot itself ran instead.
    composeTestRule.waitUntil(timeoutMillis = ORNAMENT_TIMEOUT_MS) { indoorSelectorInvoked }
  }

  @Test
  public fun testTrailingLambdaStillBindsToContent() {
    var contentInvoked = false
    composeTestRule.setContent {
      Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
          modifier = Modifier.testTag(MAP_TEST_TAG),
          ornaments = { Defaults() },
        ) {
          MapEffect(Unit) {
            contentInvoked = true
          }
        }
      }
    }
    composeTestRule.waitUntil(timeoutMillis = ORNAMENT_TIMEOUT_MS) { contentInvoked }
  }

  /**
   * The compass only enters the composition once a camera-changed event arrives from the native
   * map. That happens after [MapboxMap] binds the viewport state, which is after the ornaments
   * slot has already been composed, and Compose idleness cannot wait for a native callback - so
   * poll for the node instead of asserting on it straight away.
   */
  private fun awaitNodeWithContentDescription(description: String) {
    composeTestRule.waitUntil(timeoutMillis = ORNAMENT_TIMEOUT_MS) {
      composeTestRule.onAllNodesWithContentDescription(description)
        .fetchSemanticsNodes().isNotEmpty()
    }
  }

  private fun awaitNodeWithTag(tag: String) {
    composeTestRule.waitUntil(timeoutMillis = ORNAMENT_TIMEOUT_MS) {
      composeTestRule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
    }
  }

  private companion object {
    private const val MAP_TEST_TAG = "map_tag"
    private const val ORNAMENT_TIMEOUT_MS = 5_000L
  }
}