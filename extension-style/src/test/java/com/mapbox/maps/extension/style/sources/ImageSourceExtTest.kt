package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.Image
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.sources.generated.imageSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ImageSourceExtTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val image = mockk<Image>(relaxed = true, relaxUnitFun = true)

  @Before
  fun prepareTest() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.updateStyleImageSourceImage(any(), any()) } returns expected
    every { expected.error } returns null
  }

  @Test
  fun updateImageTest() {
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.updateImage(image)
    verify { style.updateStyleImageSourceImage("testId", image) }
  }
}