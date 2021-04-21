package com.mapbox.maps.extension.style.image

import android.graphics.Bitmap
import com.mapbox.bindgen.Expected
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.extension.style.StyleInterface
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ImagePluginImplTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val pixelRatio = mockk<Float>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)

  @Before
  fun prepareTest() {
    every { style.pixelRatio } returns pixelRatio
    every { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) } returns expected
    every { expected.error } returns null
  }

  @After
  fun cleanup() {
    unmockkAll()
  }

  @Test
  fun imageDefaultScaleTest() {
    val image = mockk<Image>(relaxed = true)
    val imagePlugin = image("imageId") {
      image(image)
    }
    style.addImage(imagePlugin)
    verify { style.addStyleImage("imageId", pixelRatio, image, false, listOf(), listOf(), null) }
  }

  @Test
  fun imageUserScaleTest() {
    val image = mockk<Image>(relaxed = true)
    val imagePlugin = image("imageId") {
      image(image)
      scale(0.4f)
    }
    style.addImage(imagePlugin)
    verify { style.addStyleImage("imageId", 0.4f, image, false, listOf(), listOf(), null) }
  }

  /**
   * Set bitmap data of the image.
   */
  @Test
  fun bitmapDefaultScaleTest() {
    val imageSlot = slot<Image>()
    val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)

    val imagePlugin = image("imageId") {
      bitmap(bitmap)
    }
    style.addImage(imagePlugin)
    verify {
      style.addStyleImage(
        "imageId",
        pixelRatio,
        capture(imageSlot),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    assertEquals(400, imageSlot.captured.width)
    assertEquals(400, imageSlot.captured.height)
    assertEquals(bitmap.byteCount, imageSlot.captured.data.size)
  }

  /**
   * Set bitmap data of the image.
   */
  @Test
  fun bitmapCustomScaleTest() {
    val imageSlot = slot<Image>()
    val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)

    val imagePlugin = image("imageId") {
      bitmap(bitmap)
      scale(1.5f)
    }
    style.addImage(imagePlugin)
    verify {
      style.addStyleImage(
        "imageId",
        1.5f,
        capture(imageSlot),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    assertEquals(400, imageSlot.captured.width)
    assertEquals(400, imageSlot.captured.height)
    assertEquals(bitmap.byteCount, imageSlot.captured.data.size)
  }

  @Test(expected = RuntimeException::class)
  fun imageNotAvailableTest() {
    val imagePlugin = image("imageId") {
      scale(1.1f)
    }
    style.addImage(imagePlugin)
  }

  @Test(expected = RuntimeException::class)
  fun bitmapWrongFormatTest() {
    val imagePlugin = image("imageId") {
      bitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565))
    }
    style.addImage(imagePlugin)
  }

  /**
   * Scale factor for the image.
   */
  @Test
  fun scaleTest() {
    val image = mockk<Image>(relaxed = true)
    val imagePlugin = image("imageId") {
      image(image)
      scale(1.1f)
    }
    style.addImage(imagePlugin)
    verify { style.addStyleImage("imageId", 1.1f, image, false, listOf(), listOf(), null) }
  }

  /**
   * Option to treat whether image is SDF(signed distance field) or not.
   */
  @Test
  fun sdfTest() {
    val image = mockk<Image>(relaxed = true)
    val imagePlugin = image("imageId") {
      image(image)
      sdf(true)
    }
    style.addImage(imagePlugin)
    verify { style.addStyleImage("imageId", pixelRatio, image, true, listOf(), listOf(), null) }
  }

  /**
   * An array of two-element arrays, consisting of two numbers that represent
   * the from position and the to position of areas that can be stretched horizontally.
   */
  @Test
  fun stretchXTest() {
    val image = mockk<Image>(relaxed = true)
    val stretchX = listOf(ImageStretches(1.0f, 1.0f), ImageStretches(2.0f, 2.0f))
    val imagePlugin = image("imageId") {
      image(image)
      stretchX(stretchX)
    }
    style.addImage(imagePlugin)
    verify { style.addStyleImage("imageId", pixelRatio, image, false, stretchX, listOf(), null) }
  }

  /**
   * An array of two-element arrays, consisting of two numbers that represent
   * the from position and the to position of areas that can be stretched vertically.
   */
  @Test
  fun stretchYTest() {
    val image = mockk<Image>(relaxed = true)
    val stretchY = listOf(ImageStretches(1.0f, 1.0f), ImageStretches(2.0f, 2.0f))
    val imagePlugin = image("imageId") {
      image(image)
      stretchY(stretchY)
    }
    style.addImage(imagePlugin)
    verify { style.addStyleImage("imageId", pixelRatio, image, false, listOf(), stretchY, null) }
  }

  /**
   * An array of four numbers, with the first two specifying the left, top
   * corner, and the last two specifying the right, bottom corner. If present, and if the
   * icon uses icon-text-fit, the symbol's text will be fit inside the content box.
   */
  @Test
  fun contentTest() {
    val image = mockk<Image>(relaxed = true)
    val content = ImageContent(1.0f, 2.0f, 3.0f, 4.0f)
    val imagePlugin = image("imageId") {
      image(image)
      content(content)
    }
    style.addImage(imagePlugin)
    verify { style.addStyleImage("imageId", pixelRatio, image, false, listOf(), listOf(), content) }
  }
}