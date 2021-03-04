package com.mapbox.maps

import android.os.Build
import com.mapbox.common.ShadowLogger
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class], sdk = [Build.VERSION_CODES.O])
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