// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
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

  private val drawable = mockk<Drawable>(relaxed = true)

  @Before
  fun setUp() {
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getInt(any(), any()) } returns 2
    every { typedArray.getColor(any(), any()) } returns Color.RED
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getDrawable(any()) } returns drawable
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
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    verify { typedArray.recycle() }
  }

  @Test
  fun testTypedArrayRecycleWithException() {
    every { typedArray.getBoolean(any(), any()) }.throws(Exception(""))
    try {
      val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    } catch (e: Exception) {
      // do nothing
    }
    verify { typedArray.recycle() }
  }

  @Test
  fun rotateEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.rotateEnabled)
  }

  @Test
  fun rotateEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.rotateEnabled)
  }

  @Test
  fun pinchToZoomEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.pinchToZoomEnabled)
  }

  @Test
  fun pinchToZoomEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.pinchToZoomEnabled)
  }

  @Test
  fun scrollEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.scrollEnabled)
  }

  @Test
  fun scrollEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.scrollEnabled)
  }

  @Test
  fun pitchEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.pitchEnabled)
  }

  @Test
  fun pitchEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.pitchEnabled)
  }
  @Test
  fun scrollModeTest() {
    every { typedArray.getInt(any(), any()) } returns ScrollMode.HORIZONTAL_AND_VERTICAL.ordinal
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(ScrollMode.HORIZONTAL_AND_VERTICAL, settings.scrollMode)
  }

  @Test
  fun doubleTapToZoomInEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.doubleTapToZoomInEnabled)
  }

  @Test
  fun doubleTapToZoomInEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.doubleTapToZoomInEnabled)
  }

  @Test
  fun doubleTouchToZoomOutEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.doubleTouchToZoomOutEnabled)
  }

  @Test
  fun doubleTouchToZoomOutEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.doubleTouchToZoomOutEnabled)
  }

  @Test
  fun quickZoomEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.quickZoomEnabled)
  }

  @Test
  fun quickZoomEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.quickZoomEnabled)
  }

  @Test
  fun focalPointTest() {
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(ScreenCoordinate(10.0, 10.0), settings.focalPoint)
  }

  @Test
  fun pinchToZoomDecelerationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.pinchToZoomDecelerationEnabled)
  }

  @Test
  fun pinchToZoomDecelerationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.pinchToZoomDecelerationEnabled)
  }

  @Test
  fun rotateDecelerationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.rotateDecelerationEnabled)
  }

  @Test
  fun rotateDecelerationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.rotateDecelerationEnabled)
  }

  @Test
  fun scrollDecelerationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.scrollDecelerationEnabled)
  }

  @Test
  fun scrollDecelerationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.scrollDecelerationEnabled)
  }

  @Test
  fun increaseRotateThresholdWhenPinchingToZoomTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.increaseRotateThresholdWhenPinchingToZoom)
  }

  @Test
  fun increaseRotateThresholdWhenPinchingToZoomTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.increaseRotateThresholdWhenPinchingToZoom)
  }

  @Test
  fun increasePinchToZoomThresholdWhenRotatingTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.increasePinchToZoomThresholdWhenRotating)
  }

  @Test
  fun increasePinchToZoomThresholdWhenRotatingTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.increasePinchToZoomThresholdWhenRotating)
  }
  @Test
  fun zoomAnimationAmountTest() {
    every { typedArray.getFloat(any(), any()) } returns 1f
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(1f, settings.zoomAnimationAmount)
  }
}

// End of generated file.