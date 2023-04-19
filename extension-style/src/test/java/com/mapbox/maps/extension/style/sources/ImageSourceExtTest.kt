package com.mapbox.maps.extension.style.sources

import android.graphics.Bitmap
import com.mapbox.bindgen.DataRef
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.Image
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.sources.generated.imageSource
import io.mockk.*
import org.junit.Before
import org.junit.Test

class ImageSourceExtTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)

  @Before
  fun prepareTest() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.updateStyleImageSourceImage(any(), any()) } returns expected
    every { expected.error } returns null
  }

  @Test
  fun updateImageImageTest() {
    val image = mockk<Image>()
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.updateImage(image)
    verify(exactly = 1) {
      style.updateStyleImageSourceImage(
        "testId",
        image
      )
    }
  }

  @Test
  fun updateImageBitmapTest() {
    val bitmap = mockk<Bitmap>()
    val bitmapWidth = 64
    val bitmapHeight = 64
    val bitmapByteCount = bitmapWidth * bitmapHeight * 4
    every { bitmap.config } returns Bitmap.Config.ARGB_8888
    every { bitmap.byteCount } returns bitmapByteCount
    every { bitmap.width } returns bitmapWidth
    every { bitmap.height } returns bitmapHeight
    every { bitmap.copyPixelsToBuffer(any()) } just Runs
    mockkStatic(DataRef::class)
    val nativeDataRef = mockk<DataRef>(relaxed = true)
    every { DataRef.allocateNative(bitmapByteCount) } returns nativeDataRef
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.updateImage(bitmap)
    verify(exactly = 1) {
      style.updateStyleImageSourceImage(
        "testId",
        Image(
          bitmapWidth,
          bitmapHeight,
          nativeDataRef
        )
      )
    }
    unmockkStatic(DataRef::class)
  }
}