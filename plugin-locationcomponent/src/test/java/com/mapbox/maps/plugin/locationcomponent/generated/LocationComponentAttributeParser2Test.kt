// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mapbox.maps.plugin.PuckBearingSource
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

class LocationComponentAttributeParser2Test {
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
    every { typedArray.getInt(any(), any()) } returns 0
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
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    verify { typedArray.recycle() }
  }

  @Test
  fun testTypedArrayRecycleWithException() {
    every { typedArray.getBoolean(any(), any()) }.throws(Exception(""))
    try {
      val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    } catch (e: Exception) {
      // do nothing
    }
    verify { typedArray.recycle() }
  }

  @Test
  fun showAccuracyRingTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    assertEquals(true, settings.showAccuracyRing)
  }

  @Test
  fun showAccuracyRingTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    assertEquals(false, settings.showAccuracyRing)
  }
  @Test
  fun accuracyRingColorTest() {
    every { typedArray.getColor(any(), any()) } returns Color.parseColor("#4d89cff0")
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    assertEquals(Color.parseColor("#4d89cff0"), settings.accuracyRingColor)
  }
  @Test
  fun accuracyRingBorderColorTest() {
    every { typedArray.getColor(any(), any()) } returns Color.parseColor("#4d89cff0")
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    assertEquals(Color.parseColor("#4d89cff0"), settings.accuracyRingBorderColor)
  }

  @Test
  fun puckBearingEnabledTestTrue() {
    every { typedArray.getBoolean(any(), any()) } returns true
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    assertEquals(true, settings.puckBearingEnabled)
  }

  @Test
  fun puckBearingEnabledTestFalse() {
    every { typedArray.getBoolean(any(), any()) } returns false
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    assertEquals(false, settings.puckBearingEnabled)
  }
  @Test
  fun puckBearingSourceTest() {
    every { typedArray.getInt(any(), any()) } returns PuckBearingSource.HEADING.ordinal
    val settings = LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, 1.2f)
    assertEquals(PuckBearingSource.HEADING, settings.puckBearingSource)
  }
}

// End of generated file.