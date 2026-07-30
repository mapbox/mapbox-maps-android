package com.mapbox.maps.extension.compose.gestures

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(MapboxExperimental::class)
public class GesturesStateTest {

  @Test
  public fun defaultGesturesSettings() {
    val state = GesturesState()
    assertEquals(GesturesSettings { }, state.gesturesSettings)
  }

  @Test
  public fun customInitialGesturesSettings() {
    val settings = GesturesSettings { rotateEnabled = false }
    val state = GesturesState(settings)
    assertEquals(settings, state.gesturesSettings)
    assertFalse(state.gesturesSettings.rotateEnabled)
  }

  @Test
  public fun gesturesSettingsIsMutable() {
    val state = GesturesState()
    assertTrue(state.gesturesSettings.rotateEnabled)

    val updated = GesturesSettings { rotateEnabled = false }
    state.gesturesSettings = updated
    assertEquals(updated, state.gesturesSettings)
  }

  @Test
  public fun mapboxMapFlowStartsNull() {
    val state = GesturesState()
    assertNull(state.mapboxMapFlow.value)
  }

  @Test
  public fun isGestureInProgress_returnsFalseWhenNotBound() {
    val state = GesturesState()
    assertFalse(state.isGestureInProgress())
  }

  @Test
  public fun isGestureInProgress_delegatesToMapboxMap() {
    val mapboxMap = mockk<MapboxMap> {
      every { isGestureInProgress() } returns true
    }
    val state = GesturesState()
    state.mapboxMapFlow.value = mapboxMap

    assertTrue(state.isGestureInProgress())
  }

  @Test
  public fun isUserAnimationInProgress_returnsFalseWhenNotBound() {
    val state = GesturesState()
    assertFalse(state.isUserAnimationInProgress())
  }

  @Test
  public fun isUserAnimationInProgress_delegatesToMapboxMap() {
    val mapboxMap = mockk<MapboxMap> {
      every { isUserAnimationInProgress() } returns true
    }
    val state = GesturesState()
    state.mapboxMapFlow.value = mapboxMap

    assertTrue(state.isUserAnimationInProgress())
  }
}