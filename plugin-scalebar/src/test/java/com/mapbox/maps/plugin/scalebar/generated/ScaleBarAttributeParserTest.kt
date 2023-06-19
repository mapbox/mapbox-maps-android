// This file is generated.

package com.mapbox.maps.plugin.scalebar.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
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

class ScaleBarAttributeParserTest {
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
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    verify { typedArray.recycle() }
  }

  @Test
  fun testTypedArrayRecycleWithException() {
    every { typedArray.getBoolean(any(), any()) }.throws(Exception(""))
    try {
      val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    } catch (e: Exception) {
      // do nothing
    }
    verify { typedArray.recycle() }
  }

  @Test
  fun enabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(true, settings.enabled)
  }

  @Test
  fun enabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(false, settings.enabled)
  }

  @Test
  fun positionTest() {
    every { typedArray.getInt(any(), any()) } returns 100
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(100, settings.position)
  }

  @Test
  fun marginLeftTest() {
    val pixelRatio = 1.2f
    val inputValue = 4f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginLeft)
  }

  @Test
  fun marginTopTest() {
    val pixelRatio = 1.2f
    val inputValue = 4f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginTop)
  }

  @Test
  fun marginRightTest() {
    val pixelRatio = 1.2f
    val inputValue = 4f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginRight)
  }

  @Test
  fun marginBottomTest() {
    val pixelRatio = 1.2f
    val inputValue = 4f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginBottom)
  }
  @Test
  fun textColorTest() {
    every { typedArray.getColor(any(), any()) } returns Color.BLACK
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(Color.BLACK, settings.textColor)
  }
  @Test
  fun primaryColorTest() {
    every { typedArray.getColor(any(), any()) } returns Color.BLACK
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(Color.BLACK, settings.primaryColor)
  }
  @Test
  fun secondaryColorTest() {
    every { typedArray.getColor(any(), any()) } returns Color.WHITE
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(Color.WHITE, settings.secondaryColor)
  }

  @Test
  fun borderWidthTest() {
    val pixelRatio = 1.2f
    val inputValue = 2f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.borderWidth)
  }

  @Test
  fun heightTest() {
    val pixelRatio = 1.2f
    val inputValue = 2f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.height)
  }

  @Test
  fun textBarMarginTest() {
    val pixelRatio = 1.2f
    val inputValue = 8f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.textBarMargin)
  }

  @Test
  fun textBorderWidthTest() {
    val pixelRatio = 1.2f
    val inputValue = 2f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.textBorderWidth)
  }

  @Test
  fun textSizeTest() {
    val pixelRatio = 1.2f
    val inputValue = 8f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.textSize)
  }

  @Test
  fun isMetricUnitsTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(true, settings.isMetricUnits)
  }

  @Test
  fun isMetricUnitsTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(false, settings.isMetricUnits)
  }

  @Test
  fun refreshIntervalTest() {
    every { typedArray.getInt(any(), any()) } returns 2
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(2L, settings.refreshInterval)
  }

  @Test
  fun showTextBorderTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(true, settings.showTextBorder)
  }

  @Test
  fun showTextBorderTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(false, settings.showTextBorder)
  }
  @Test
  fun ratioTest() {
    every { typedArray.getFloat(any(), any()) } returns 0.5f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(0.5f, settings.ratio)
  }

  @Test
  fun useContinuousRenderingTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(true, settings.useContinuousRendering)
  }

  @Test
  fun useContinuousRenderingTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(false, settings.useContinuousRendering)
  }
}

// End of generated file.