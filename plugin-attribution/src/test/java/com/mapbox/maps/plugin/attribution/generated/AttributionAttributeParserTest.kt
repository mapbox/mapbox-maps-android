// This file is generated.

package com.mapbox.maps.plugin.attribution.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AttributionAttributeParserTest {
  private val context: Context = mockk(relaxed = true)

  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  private val drawable = mockk<Drawable>(relaxed = true)

  @Before
  fun setUp() {
    mockkStatic(Color::class)
    every { Color.parseColor(any()) } returns Color.WHITE
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
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    verify { typedArray.recycle() }
  }

  @Test
  fun testTypedArrayRecycleWithException() {
    every { typedArray.getBoolean(any(), any()) }.throws(Exception(""))
    try {
      val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    } catch (e: Exception) {
      // do nothing
    }
    verify { typedArray.recycle() }
  }

  @Test
  fun enabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    assertEquals(true, settings.enabled)
  }

  @Test
  fun enabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    assertEquals(false, settings.enabled)
  }
  @Test
  fun iconColorTest() {
    every { typedArray.getColor(any(), any()) } returns Color.parseColor("#FF1E8CAB")
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    assertEquals(Color.parseColor("#FF1E8CAB"), settings.iconColor)
  }

  @Test
  fun positionTest() {
    every { typedArray.getInt(any(), any()) } returns 100
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    assertEquals(100, settings.position)
  }

  @Test
  fun marginLeftTest() {
    val pixelRatio = 1.2f
    val inputValue = 92f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginLeft)
  }

  @Test
  fun marginTopTest() {
    val pixelRatio = 1.2f
    val inputValue = 4f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginTop)
  }

  @Test
  fun marginRightTest() {
    val pixelRatio = 1.2f
    val inputValue = 4f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginRight)
  }

  @Test
  fun marginBottomTest() {
    val pixelRatio = 1.2f
    val inputValue = 4f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginBottom)
  }

  @Test
  fun clickableTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    assertEquals(true, settings.clickable)
  }

  @Test
  fun clickableTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = AttributionAttributeParser.parseAttributionSettings(context, attrs, 1.2f)
    assertEquals(false, settings.clickable)
  }
}

// End of generated file.