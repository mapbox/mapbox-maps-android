// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mapbox.maps.plugin.PresetPuckStyle
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
class LocationComponentAttributeParserTest {
  private val context: Context = mockk(relaxed = true)

  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  private val drawable = mockk<Drawable>(relaxed = true)

  @Before
  fun setUp() {
    mockkObject(LocationComponentAttributeParser::class)
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
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(true, settings.enabled)
  }

  @Test
  fun enabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(false, settings.enabled)
  }

  @Test
  fun staleStateEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(true, settings.staleStateEnabled)
  }

  @Test
  fun staleStateEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(false, settings.staleStateEnabled)
  }

  @Test
  fun staleStateTimeoutTest() {
    every { typedArray.getInt(any(), any()) } returns 2
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(2L, settings.staleStateTimeout)
  }

  @Test
  fun minZoomIconScaleTest() {
    every { typedArray.getFloat(any(), any()) } returns 0.6f
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(0.6f, settings.minZoomIconScale)
  }

  @Test
  fun maxZoomIconScaleTest() {
    every { typedArray.getFloat(any(), any()) } returns 1f
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(1f, settings.maxZoomIconScale)
  }

  @Test
  fun layerAboveTest() {
    every { typedArray.getString(any()) } returns null
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(null, settings.layerAbove)
  }

  @Test
  fun layerBelowTest() {
    every { typedArray.getString(any()) } returns null
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(null, settings.layerBelow)
  }
  @Test
  fun presetPuckStyleTest() {
    every { typedArray.getInt(any(), any()) } returns 0
    val settings = LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, 1.2f)
    assertEquals(PresetPuckStyle.PRECISE, settings.presetPuckStyle)
  }
}

// End of generated file.