package com.mapbox.maps

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FontUtilsTest {

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
  }

  @Test
  fun testExtractedFontShouldMatchDefault() {
    val actual = FontUtils.extractValidFont("foo")
    assertEquals("Selected font should match", "sans-serif", actual)
  }

  @Test
  fun testExtractedFontShouldMatchSansSerif() {
    val actual = FontUtils.extractValidFont("sans-serif")
    assertEquals("Selected font should match", "sans-serif", actual)
  }
}