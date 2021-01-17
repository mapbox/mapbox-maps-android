package com.mapbox.maps

import android.content.res.TypedArray
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CameraAttributeParserTest {

  private val typedArray = mockk<TypedArray>()

  @Before
  fun setUp() {
    every { typedArray.getFloat(any(), any()) } returns 0.0f
  }

  @Test
  fun getCameraOptionsNoXmlAttributes() {
    val cameraOptions = CameraAttributeParser.parseCameraOptions(typedArray, 1.0f)
    assertEquals(
      "Camera options should be null",
      null,
      cameraOptions,
    )
  }

  @Test
  fun getCameraOptionsHasFullXmlAttribute() {
    every { typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_cameraBearing, 0f) } returns 5.0f
    val cameraOptions = CameraAttributeParser.parseCameraOptions(typedArray, 1.0f)
    assertEquals(
      "Camera options should have valid attribute",
      5.0,
      cameraOptions?.bearing
    )
  }

  @Test
  fun getCameraOptionsMostPartXmlAttribute() {
    every {
      typedArray.getFloat(
        R.styleable.mapbox_MapView_mapbox_cameraPaddingRight,
        0f
      )
    } returns 5.0f
    val cameraOptions = CameraAttributeParser.parseCameraOptions(typedArray, 99.0f)
    assertEquals(
      "Camera options should have valid attribute parameter and all other defaulted",
      EdgeInsets(0.0, 0.0, 0.0, 5.0),
      cameraOptions?.padding,
    )
  }
}