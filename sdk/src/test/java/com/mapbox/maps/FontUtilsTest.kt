package com.mapbox.maps

import io.mockk.*
import org.junit.After
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

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
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