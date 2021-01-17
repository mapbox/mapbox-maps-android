package com.mapbox.maps

import com.mapbox.common.ShadowLogger
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class FontUtilsTest {
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