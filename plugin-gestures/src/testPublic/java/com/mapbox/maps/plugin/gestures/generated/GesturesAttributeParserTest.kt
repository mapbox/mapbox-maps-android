// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GesturesAttributeParserTest {
  private val context: Context = mockk(relaxed = true)

  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getInt(any(), any()) } returns 0
    every { typedArray.getColor(any(), any()) } returns Color.RED
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getResourceId(any(), -1) } returns 1
    every { typedArray.hasValue(any()) } returns true
    every { typedArray.recycle() } just Runs
  }

  @After
  fun cleanUp() {
    clearAllMocks()
  }

  @Test
  fun testTypedArrayRecycle() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    verify { typedArray.recycle() }
  }

  @Test
  fun testTypedArrayRecycleWithException() {
    every { typedArray.getBoolean(any(), any()) }.throws(Exception(""))
    try {
      val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    } catch (e: Exception) {
      // do nothing
    }
    verify { typedArray.recycle() }
  }

  @Test
  fun rotateEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.rotateEnabled)
  }

  @Test
  fun rotateEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.rotateEnabled)
  }

  @Test
  fun pinchToZoomEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.pinchToZoomEnabled)
  }

  @Test
  fun pinchToZoomEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.pinchToZoomEnabled)
  }

  @Test
  fun scrollEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.scrollEnabled)
  }

  @Test
  fun scrollEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.scrollEnabled)
  }

  @Test
  fun simultaneousRotateAndPinchToZoomEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.simultaneousRotateAndPinchToZoomEnabled)
  }

  @Test
  fun simultaneousRotateAndPinchToZoomEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.simultaneousRotateAndPinchToZoomEnabled)
  }

  @Test
  fun pitchEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.pitchEnabled)
  }

  @Test
  fun pitchEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.pitchEnabled)
  }

  @Test
  fun scrollModeTestHORIZONTAL() {
    every { typedArray.getInt(any(), any()) } returns ScrollMode.HORIZONTAL.ordinal
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(ScrollMode.HORIZONTAL, settings.scrollMode)
  }

  @Test
  fun scrollModeTestVERTICAL() {
    every { typedArray.getInt(any(), any()) } returns ScrollMode.VERTICAL.ordinal
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(ScrollMode.VERTICAL, settings.scrollMode)
  }

  @Test
  fun scrollModeTestHORIZONTAL_AND_VERTICAL() {
    every { typedArray.getInt(any(), any()) } returns ScrollMode.HORIZONTAL_AND_VERTICAL.ordinal
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(ScrollMode.HORIZONTAL_AND_VERTICAL, settings.scrollMode)
  }

  @Test
  fun doubleTapToZoomInEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.doubleTapToZoomInEnabled)
  }

  @Test
  fun doubleTapToZoomInEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.doubleTapToZoomInEnabled)
  }

  @Test
  fun doubleTouchToZoomOutEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.doubleTouchToZoomOutEnabled)
  }

  @Test
  fun doubleTouchToZoomOutEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.doubleTouchToZoomOutEnabled)
  }

  @Test
  fun quickZoomEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.quickZoomEnabled)
  }

  @Test
  fun quickZoomEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.quickZoomEnabled)
  }

  @Test
  fun focalPointTest() {
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(ScreenCoordinate(10.0, 10.0), settings.focalPoint)
  }

  @Test
  fun pinchToZoomDecelerationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.pinchToZoomDecelerationEnabled)
  }

  @Test
  fun pinchToZoomDecelerationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.pinchToZoomDecelerationEnabled)
  }

  @Test
  fun rotateDecelerationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.rotateDecelerationEnabled)
  }

  @Test
  fun rotateDecelerationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.rotateDecelerationEnabled)
  }

  @Test
  fun scrollDecelerationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.scrollDecelerationEnabled)
  }

  @Test
  fun scrollDecelerationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.scrollDecelerationEnabled)
  }

  @Test
  fun increaseRotateThresholdWhenPinchingToZoomTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.increaseRotateThresholdWhenPinchingToZoom)
  }

  @Test
  fun increaseRotateThresholdWhenPinchingToZoomTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.increaseRotateThresholdWhenPinchingToZoom)
  }

  @Test
  fun increasePinchToZoomThresholdWhenRotatingTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.increasePinchToZoomThresholdWhenRotating)
  }

  @Test
  fun increasePinchToZoomThresholdWhenRotatingTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.increasePinchToZoomThresholdWhenRotating)
  }
  @Test
  fun zoomAnimationAmountTest() {
    every { typedArray.getFloat(any(), any()) } returns 1f
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(1f, settings.zoomAnimationAmount)
  }

  @Test
  fun pinchScrollEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(true, settings.pinchScrollEnabled)
  }

  @Test
  fun pinchScrollEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs)
    assertEquals(false, settings.pinchScrollEnabled)
  }
}

// End of generated file.