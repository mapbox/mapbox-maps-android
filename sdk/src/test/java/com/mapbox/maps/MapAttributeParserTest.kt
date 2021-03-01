package com.mapbox.maps

import android.content.res.TypedArray
import com.mapbox.common.ShadowLogger
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapAttributeParserTest {

  private lateinit var typedArray: TypedArray

  @Before
  fun setUp() {
    unmockkAll()
    typedArray = mockk(relaxed = true)
    every { typedArray.getString(any()) } returns null
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getFloat(any(), any()) } returns 99.0f
    every { typedArray.getInt(any(), any()) } returns 0
    every {
      typedArray.getInt(
        R.styleable.mapbox_MapView_mapbox_mapGlyphRasterizationMode,
        any()
      )
    } returns 1
  }

  @Test
  fun default() {
    val mapOptions = MapAttributeParser.parseMapOptions(typedArray, 99.0f)
    assertEquals(ConstrainMode.NONE, mapOptions.constrainMode)
    assertEquals(ContextMode.UNIQUE, mapOptions.contextMode)
    assertEquals(true, mapOptions.crossSourceCollisions)
    assertEquals("sans-serif", mapOptions.glyphsRasterizationOptions!!.fontFamily)
    assertEquals(
      GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY,
      mapOptions.glyphsRasterizationOptions!!.rasterizationMode,
    )
    assertEquals(NorthOrientation.UPWARDS, mapOptions.orientation)
    assertEquals(99.0f, mapOptions.pixelRatio)
    assertEquals(null, mapOptions.size)
    assertEquals(ViewportMode.DEFAULT, mapOptions.viewportMode)
  }

  @Test
  fun fontFamilyNone() {
    every {
      typedArray.getInt(
        R.styleable.mapbox_MapView_mapbox_mapGlyphRasterizationMode,
        any()
      )
    } returns 0
    val mapOptions = MapAttributeParser.parseMapOptions(typedArray, 99.0f)
    assertNull("monospace", mapOptions.glyphsRasterizationOptions!!.fontFamily)
  }

  @Test
  fun fontFamilyNope() {
    every { typedArray.getString(R.styleable.mapbox_MapView_mapbox_mapFontFamily) } returns "foobar"
    val mapOptions = MapAttributeParser.parseMapOptions(typedArray, 99.0f)
    assertEquals("sans-serif", mapOptions.glyphsRasterizationOptions!!.fontFamily)
  }

  @Test
  fun constraintModeNone() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapConstrainMode, 1) } returns 0
    assertEquals(
      ConstrainMode.NONE,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).constrainMode
    )
  }

  @Test
  fun constraintModeHeightOnly() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapConstrainMode, 1) } returns 1
    assertEquals(
      ConstrainMode.HEIGHT_ONLY,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).constrainMode
    )
  }

  @Test
  fun constraintModeWidthAndHeight() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapConstrainMode, 1) } returns 2
    assertEquals(
      ConstrainMode.WIDTH_AND_HEIGHT,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).constrainMode
    )
  }

  @Test
  fun contextUnique() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapContextMode, 0) } returns 0
    assertEquals(
      ContextMode.UNIQUE,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).contextMode
    )
  }

  @Test
  fun contextModeShared() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapContextMode, 0) } returns 1
    assertEquals(
      ContextMode.SHARED,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).contextMode
    )
  }

  @Test
  fun mapAttributeParserCrossSourceCollisionsTrue() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_mapCrossSourceCollisionsEnabled,
        true
      )
    } returns true
    assertEquals(
      true,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).crossSourceCollisions
    )
  }

  @Test
  fun mapAttributeParserCrossSourceCollisionsFalse() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_mapCrossSourceCollisionsEnabled,
        true
      )
    } returns false
    assertEquals(
      false,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).crossSourceCollisions
    )
  }

  @Test
  fun northOrientationUpwards() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapOrientation, 0) } returns 0
    assertEquals(
      NorthOrientation.UPWARDS,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).orientation
    )
  }

  @Test
  fun northOrientationDownwards() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapOrientation, 0) } returns 2
    assertEquals(
      NorthOrientation.DOWNWARDS,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).orientation
    )
  }

  @Test
  fun northOrientationLeftwards() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapOrientation, 0) } returns 3
    assertEquals(
      NorthOrientation.LEFTWARDS,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).orientation
    )
  }

  @Test
  fun northOrientationRightwards() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapOrientation, 0) } returns 1
    assertEquals(
      NorthOrientation.RIGHTWARDS,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).orientation
    )
  }

  @Test
  fun viewportModeDefault() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapViewportMode, 0) } returns 0
    assertEquals(
      ViewportMode.DEFAULT,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).viewportMode
    )
  }

  @Test
  fun viewportModeYFlipped() {
    every { typedArray.getInt(R.styleable.mapbox_MapView_mapbox_mapViewportMode, 0) } returns 1
    assertEquals(
      ViewportMode.FLIPPED_Y,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).viewportMode
    )
  }

  @Test
  fun pixelRatio() {
    every {
      typedArray.getFloat(
        R.styleable.mapbox_MapView_mapbox_mapPixelRatio,
        any()
      )
    } returns 9000.0f
    assertEquals(
      9000.0f,
      MapAttributeParser.parseMapOptions(typedArray, 99.0f).pixelRatio
    )
  }
}