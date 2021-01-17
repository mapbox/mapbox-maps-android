// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mapbox.maps.ScreenCoordinate
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GesturesAttributeParserTest {
  private val context: Context = mockk(relaxed = true)

  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  private val drawable = mockk<Drawable>(relaxed = true)

  @Before
  fun setUp() {
    mockkObject(GesturesAttributeParser::class)
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getInt(any(), any()) } returns 2
    every { typedArray.getColor(any(), any()) } returns Color.RED
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getDrawable(any()) } returns drawable
    every { typedArray.hasValue(any()) } returns true
  }

  @After
  fun cleanUp() {
    unmockkAll()
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
  fun zoomEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.zoomEnabled)
  }

  @Test
  fun zoomEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.zoomEnabled)
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
  fun doubleTapToZoomEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.doubleTapToZoomEnabled)
  }

  @Test
  fun doubleTapToZoomEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.doubleTapToZoomEnabled)
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
  fun scaleVelocityAnimationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.scaleVelocityAnimationEnabled)
  }

  @Test
  fun scaleVelocityAnimationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.scaleVelocityAnimationEnabled)
  }

  @Test
  fun rotateVelocityAnimationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.rotateVelocityAnimationEnabled)
  }

  @Test
  fun rotateVelocityAnimationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.rotateVelocityAnimationEnabled)
  }

  @Test
  fun flingVelocityAnimationEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.flingVelocityAnimationEnabled)
  }

  @Test
  fun flingVelocityAnimationEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.flingVelocityAnimationEnabled)
  }

  @Test
  fun increaseRotateThresholdWhenScalingTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.increaseRotateThresholdWhenScaling)
  }

  @Test
  fun increaseRotateThresholdWhenScalingTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.increaseRotateThresholdWhenScaling)
  }

  @Test
  fun disableRotateWhenScalingTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.disableRotateWhenScaling)
  }

  @Test
  fun disableRotateWhenScalingTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.disableRotateWhenScaling)
  }

  @Test
  fun increaseScaleThresholdWhenRotatingTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(true, settings.increaseScaleThresholdWhenRotating)
  }

  @Test
  fun increaseScaleThresholdWhenRotatingTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(false, settings.increaseScaleThresholdWhenRotating)
  }

  @Test
  fun zoomRateTest() {
    every { typedArray.getFloat(any(), any()) } returns 1f
    val settings = GesturesAttributeParser.parseGesturesSettings(context, attrs, 1.2f)
    assertEquals(1f, settings.zoomRate)
  }
}

// End of generated file.