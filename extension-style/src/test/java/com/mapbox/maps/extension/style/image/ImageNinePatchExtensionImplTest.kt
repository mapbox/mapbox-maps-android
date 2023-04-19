package com.mapbox.maps.extension.style.image

import android.graphics.Bitmap
import com.mapbox.bindgen.DataRef
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.extension.style.StyleInterface
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ImageNinePatchExtensionImplTest {

  private val style = mockk<StyleInterface>(relaxed = true)
  private val pixelRatio = mockk<Float>(relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxed = true)
  private val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
  private val imageContent = ImageContent(1f, 1f, 1f, 1f)
  private val stretchX = listOf(ImageStretches(1f, 1f))
  private val stretchY = listOf(ImageStretches(2f, 2f))
  private lateinit var image: Image
  private lateinit var ninePatchImage: NinePatchImage

  @Before
  fun prepareTest() {
    mockkStatic("com.mapbox.maps.extension.style.image.NinePatchUtils")
    mockkStatic(DataRef::class)
    val nativeDataRef = mockk<DataRef>(relaxed = true)
    every { DataRef.allocateNative(any()) } returns nativeDataRef
    image = Image(20, 20, nativeDataRef)
    ninePatchImage = NinePatchImage(
      image,
      stretchX,
      stretchY,
      imageContent
    )
    every { style.pixelRatio } returns pixelRatio
    every { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) } returns expected
    every { expected.error } returns null
    every { bitmap.parse9PatchBitmap() } returns ninePatchImage
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun imageDefaultScaleTest() {
    val imagePlugin = image9Patch("imageId", bitmap)
    style.addImage9Patch(imagePlugin)
    verify { style.addStyleImage("imageId", pixelRatio, image, false, stretchX, stretchY, imageContent) }
  }

  @Test
  fun imageUserScaleTest() {
    val imagePlugin = image9Patch("imageId", bitmap) {
      scale(0.4f)
    }
    style.addImage9Patch(imagePlugin)
    verify { style.addStyleImage("imageId", 0.4f, image, false, stretchX, stretchY, imageContent) }
  }

  @Test
  fun sdfTest() {
    val imagePlugin = image9Patch("imageId", bitmap) {
      sdf(true)
    }
    style.addImage9Patch(imagePlugin)
    verify { style.addStyleImage("imageId", pixelRatio, image, true, stretchX, stretchY, imageContent) }
  }
}