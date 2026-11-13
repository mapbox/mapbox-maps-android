// This file is generated.

package com.mapbox.maps.plugin.consolidatedattribution.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import com.mapbox.maps.plugin.consolidatedattribution.R
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

class ConsolidatedAttributionAttributeParserTest {
  private val context: Context = mockk(relaxed = true)

  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    mockkStatic(Color::class)
    every { Color.parseColor(any()) } returns Color.WHITE
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
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    verify { typedArray.recycle() }
  }

  @Test
  fun testTypedArrayRecycleWithException() {
    every { typedArray.getBoolean(any(), any()) }.throws(Exception(""))
    try {
      val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    } catch (e: Exception) {
      // do nothing
    }
    verify { typedArray.recycle() }
  }

  @Test
  fun enabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(true, settings.enabled)
  }

  @Test
  fun enabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(false, settings.enabled)
  }

  @Test
  fun positionTest() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionGravity, any()) } returns 100
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(100, settings.position)
  }

  @Test
  fun marginLeftTest() {
    val pixelRatio = 1.2f
    val inputValue = 8f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginLeft)
  }

  @Test
  fun marginTopTest() {
    val pixelRatio = 1.2f
    val inputValue = 8f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginTop)
  }

  @Test
  fun marginRightTest() {
    val pixelRatio = 1.2f
    val inputValue = 8f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginRight)
  }

  @Test
  fun marginBottomTest() {
    val pixelRatio = 1.2f
    val inputValue = 8f
    every { typedArray.getDimension(any(), pixelRatio * inputValue) } returns pixelRatio * inputValue
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, pixelRatio)
    assertEquals(pixelRatio * inputValue, settings.marginBottom)
  }

  @Test
  fun collapsedStyleTestICON_ONLY() {
    every { typedArray.getInt(any(), any()) } returns ConsolidatedAttributionCollapsedStyle.ICON_ONLY.ordinal
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(ConsolidatedAttributionCollapsedStyle.ICON_ONLY, settings.collapsedStyle)
  }

  @Test
  fun collapsedStyleTestICON_AND_WORDMARK() {
    every { typedArray.getInt(any(), any()) } returns ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK.ordinal
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK, settings.collapsedStyle)
  }

  @Test
  fun materialTestSOLID() {
    every { typedArray.getInt(any(), any()) } returns ConsolidatedAttributionMaterial.SOLID.ordinal
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(ConsolidatedAttributionMaterial.SOLID, settings.material)
  }

  @Test
  fun materialTestTRANSLUCENT() {
    every { typedArray.getInt(any(), any()) } returns ConsolidatedAttributionMaterial.TRANSLUCENT.ordinal
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(ConsolidatedAttributionMaterial.TRANSLUCENT, settings.material)
  }

  @Test
  fun introExpansionTestONCE() {
    every { typedArray.getInt(any(), any()) } returns ConsolidatedAttributionIntroExpansion.ONCE.ordinal
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(ConsolidatedAttributionIntroExpansion.ONCE, settings.introExpansion)
  }

  @Test
  fun introExpansionTestEVERY_LAUNCH() {
    every { typedArray.getInt(any(), any()) } returns ConsolidatedAttributionIntroExpansion.EVERY_LAUNCH.ordinal
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(ConsolidatedAttributionIntroExpansion.EVERY_LAUNCH, settings.introExpansion)
  }
  @Test
  fun tintColorTest() {
    every { typedArray.getColor(any(), any()) } returns Color.parseColor("#00000000")
    val settings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(context, attrs, 1.2f)
    assertEquals(Color.parseColor("#00000000"), settings.tintColor)
  }
}

// End of generated file.