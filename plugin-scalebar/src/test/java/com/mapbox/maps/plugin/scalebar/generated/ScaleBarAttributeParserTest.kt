// This file is generated.

package com.mapbox.maps.plugin.scalebar.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
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
class ScaleBarAttributeParserTest {
  private val context: Context = mockk(relaxed = true)

  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  private val drawable = mockk<Drawable>(relaxed = true)

  @Before
  fun setUp() {
    mockkObject(ScaleBarAttributeParser::class)
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
    every { typedArray.getDimension(any(), any()) } returns 4f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(4f, settings.marginLeft)
  }

  @Test
  fun marginTopTest() {
    every { typedArray.getDimension(any(), any()) } returns 4f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(4f, settings.marginTop)
  }

  @Test
  fun marginRightTest() {
    every { typedArray.getDimension(any(), any()) } returns 4f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(4f, settings.marginRight)
  }

  @Test
  fun marginBottomTest() {
    every { typedArray.getDimension(any(), any()) } returns 4f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(4f, settings.marginBottom)
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
    every { typedArray.getDimension(any(), any()) } returns 2f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(2f, settings.borderWidth)
  }

  @Test
  fun heightTest() {
    every { typedArray.getDimension(any(), any()) } returns 2f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(2f, settings.height)
  }

  @Test
  fun textBarMarginTest() {
    every { typedArray.getDimension(any(), any()) } returns 8f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(8f, settings.textBarMargin)
  }

  @Test
  fun textBorderWidthTest() {
    every { typedArray.getDimension(any(), any()) } returns 2f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(2f, settings.textBorderWidth)
  }

  @Test
  fun textSizeTest() {
    every { typedArray.getDimension(any(), any()) } returns 8f
    val settings = ScaleBarAttributeParser.parseScaleBarSettings(context, attrs, 1.2f)
    assertEquals(8f, settings.textSize)
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