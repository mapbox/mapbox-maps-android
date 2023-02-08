package com.mapbox.maps.plugin.gestures

import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.android.gestures.RotateGestureDetector
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class GestureStateTest(private val type: GestureState.Type) {
  private lateinit var gestureState: GestureState
  private lateinit var moveGestureDetector: MoveGestureDetector
  private lateinit var rotateGestureDetector: RotateGestureDetector

  @Before
  fun setup() {
    val gesturesManager = mockk<AndroidGesturesManager>()
    moveGestureDetector = mockk()
    rotateGestureDetector = mockk()
    every { gesturesManager.moveGestureDetector } returns moveGestureDetector
    every { gesturesManager.rotateGestureDetector } returns rotateGestureDetector
    every { moveGestureDetector.isEnabled = any() } just runs
    every { rotateGestureDetector.isEnabled = any() } just runs
    gestureState = GestureState(gesturesManager)
  }

  @Test
  fun checkPeekReturnsNull() {
    assertNull(gestureState.peek(type))
  }

  private fun checkSaveAndDisableWithValue(value: Boolean) {
    every { moveGestureDetector.isEnabled } returns value
    every { rotateGestureDetector.isEnabled } returns value
    gestureState.saveAndDisable(type)
    assertEquals(value, gestureState.peek(type))
    when (type) {
      GestureState.Type.Scale -> verify { rotateGestureDetector.isEnabled = false }
      else -> verify { moveGestureDetector.isEnabled = false }
    }
    assertNotNull(gestureState.peek(type))
    // Ensure no other gestures were stored.
    for (t in GestureState.Type.values()) {
      if (t != type) {
        assertNull(gestureState.peek(t))
      }
    }
  }

  @Test
  fun checkSaveAndDisableWhenGestureIsDisabled() {
    checkSaveAndDisableWithValue(false)
  }

  @Test
  fun checkSaveAndDisableWhenGestureIsEnabled() {
    checkSaveAndDisableWithValue(true)
  }

  private fun restoreWithValue(value: Boolean) {
    every { moveGestureDetector.isEnabled } returns value
    every { rotateGestureDetector.isEnabled } returns value
    gestureState.saveAndDisable(type)
    assertEquals(value, gestureState.peek(type))
    gestureState.restore(type)
    when (type) {
      GestureState.Type.Scale -> verify { rotateGestureDetector.isEnabled = value }
      else -> verify { moveGestureDetector.isEnabled = value }
    }
    // There should be no gestures stored of any type.
    for (t in GestureState.Type.values()) {
      assertNull(gestureState.peek(t))
    }
  }

  @Test
  fun checkRestoreWhenGestureIsDisabled() {
    restoreWithValue(false)
  }

  @Test
  fun checkRestoreWhenGestureIsEnabled() {
    restoreWithValue(true)
  }

  internal companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "for gesture type {0}")
    fun data() = GestureState.Type.values()
  }
}