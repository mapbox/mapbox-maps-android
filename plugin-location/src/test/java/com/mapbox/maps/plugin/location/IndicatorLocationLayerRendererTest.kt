package com.mapbox.maps.plugin.location

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.location.LocationComponentConstants.*
import com.mapbox.maps.plugin.location.modes.RenderMode
import com.mapbox.maps.plugin.location.utils.BitmapUtils
import io.mockk.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IndicatorLocationLayerRendererTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val layerSourceProvider: LayerSourceProvider = mockk(relaxed = true)
  private val layerWrapper: IndicatorLocationLayerWrapper = mockk(relaxed = true)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  private val expressionSlot = CapturingSlot<List<Value>>()
  private val doubleListSlot = CapturingSlot<List<Double>>()

  private lateinit var locationLayerRenderer: IndicatorLocationLayerRenderer

  @Before
  fun setup() {

    every { style.removeStyleLayer(any()) } returns expected
    every { expected.error } returns null
    every { layerSourceProvider.getIndicatorLocationLayer() } returns layerWrapper
    every { layerWrapper.layerId } returns "id"

    locationLayerRenderer = IndicatorLocationLayerRenderer(layerSourceProvider)
    locationLayerRenderer.initializeComponents(style, mockk(relaxed = true))
  }

  @Test
  fun sanity() {
    Assert.assertNotNull(locationLayerRenderer)
  }

  @Test
  fun initializeComponents_withLocation() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    val bearing = 11f
    val accuracy = 65f
    locationLayerRenderer.setLatLng(latLng)
    locationLayerRenderer.setGpsBearing(bearing)
    locationLayerRenderer.setAccuracyRadius(accuracy)

    locationLayerRenderer.initializeComponents(style, mockk(relaxed = true))

    verify { layerWrapper.location(capture(doubleListSlot)) }
    assertEquals(latLng.toLocationList(), doubleListSlot.captured)
    verify { layerWrapper.bearing(bearing.toDouble()) }
    verify { layerWrapper.accuracyRadius(accuracy.toDouble()) }
  }

  @Test
  fun addLayers() {
    val positionManager: LocationComponentPositionManager = mockk(relaxUnitFun = true)

    locationLayerRenderer.addLayers(positionManager)

    verify { positionManager.addLayerToMap(layerWrapper) }
  }

  @Test
  fun removeLayers() {
    locationLayerRenderer.removeLayers()

    verify { style.removeStyleLayer("id") }
  }

  @Test
  fun hide() {
    locationLayerRenderer.hide()

    verify { layerWrapper.visibility(false) }
  }

  @Test
  fun show() {
    locationLayerRenderer.show(RenderMode.NORMAL, false)

    verify { layerWrapper.visibility(true) }
  }

  @Test
  fun show_normal_notStale() {
    locationLayerRenderer.show(RenderMode.NORMAL, false)

    verify { layerWrapper.topImage(FOREGROUND_ICON) }
    verify { layerWrapper.bearingImage(BACKGROUND_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_ICON) }
  }

  @Test
  fun show_compass_notStale() {
    locationLayerRenderer.show(RenderMode.COMPASS, false)

    verify { layerWrapper.topImage(FOREGROUND_ICON) }
    verify { layerWrapper.bearingImage(BEARING_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_ICON) }
  }

  @Test
  fun show_gps_notStale() {
    locationLayerRenderer.show(RenderMode.GPS, false)

    verify { layerWrapper.topImage("") }
    verify { layerWrapper.bearingImage(FOREGROUND_ICON) }
    verify { layerWrapper.shadowImage(BACKGROUND_ICON) }
    verify { layerWrapper.accuracyRadius(0.0) }
  }

  @Test
  fun show_normal_stale() {
    locationLayerRenderer.show(RenderMode.NORMAL, true)

    verify { layerWrapper.topImage(FOREGROUND_STALE_ICON) }
    verify { layerWrapper.bearingImage(BACKGROUND_STALE_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_ICON) }
  }

  @Test
  fun show_compass_stale() {
    locationLayerRenderer.show(RenderMode.COMPASS, true)

    verify { layerWrapper.topImage(FOREGROUND_STALE_ICON) }
    verify { layerWrapper.bearingImage(BEARING_STALE_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_ICON) }
  }

  @Test
  fun show_gps_stale() {
    locationLayerRenderer.show(RenderMode.GPS, true)

    verify { layerWrapper.topImage("") }
    verify { layerWrapper.bearingImage(FOREGROUND_STALE_ICON) }
    verify { layerWrapper.shadowImage(BACKGROUND_STALE_ICON) }
    verify { layerWrapper.accuracyRadius(0.0) }
  }

  @Test
  fun styleAccuracy() {
    val colorArray: FloatArray = IndicatorLocationLayerRenderer.colorToRgbaArray(Color.RED)
    colorArray[3] = 1.0f
    val rgbaExpression = IndicatorLocationLayerRenderer.buildRGBAExpression(colorArray)

    locationLayerRenderer.styleAccuracy(1.0f, Color.RED)

    verify { layerWrapper.accuracyRadiusColor(capture(expressionSlot)) }
    assertEquals(rgbaExpression.toString(), expressionSlot.captured.toString())
    verify { layerWrapper.accuracyRadiusBorderColor(capture(expressionSlot)) }
    assertEquals(rgbaExpression.toString(), expressionSlot.captured.toString())
  }

  @Test
  fun setLatLng() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    locationLayerRenderer.setLatLng(latLng)

    verify {
      layerWrapper.location(latLng.toLocationList())
    }
  }

  @Test
  fun setGpsBearing() {
    val bearing = 30.0
    locationLayerRenderer.setGpsBearing(bearing.toFloat())

    verify { layerWrapper.bearing(bearing) }
  }

  @Test
  fun setCompassBearing() {
    val bearing = 30.0
    locationLayerRenderer.setCompassBearing(bearing.toFloat())

    verify { layerWrapper.bearing(bearing) }
  }

  @Test
  fun setAccuracyRadius() {
    val radius = 40.0
    locationLayerRenderer.setAccuracyRadius(radius.toFloat())

    verify { layerWrapper.accuracyRadius(radius) }
  }

  @Test
  fun styleScaling() {
    val exp = arrayListOf(Value(""))
    locationLayerRenderer.styleScaling(exp)

    verify { layerWrapper.shadowImageSize(exp) }
    verify { layerWrapper.bearingImageSize(exp) }
    verify { layerWrapper.topImageSize(exp) }
  }

  @Test
  fun setLocationStale() {
    locationLayerRenderer.setLocationStale(true, RenderMode.NORMAL)

    verify { layerWrapper.topImage(FOREGROUND_STALE_ICON) }
    verify { layerWrapper.bearingImage(BACKGROUND_STALE_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_ICON) }
  }

  @Test
  fun addBitmaps_shadow() {
    addBitmaps(withShadow = true, renderMode = RenderMode.NORMAL)
    verify {
      style.addStyleImage(
        SHADOW_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
  }

  @Test
  fun addBitmaps_noShadow() {
    addBitmaps(withShadow = false, renderMode = RenderMode.NORMAL)
    verify { style.removeStyleImage(SHADOW_ICON) }
  }

  @Test
  fun addBitmaps_normal() {
    addBitmaps(withShadow = true, renderMode = RenderMode.NORMAL)
    verify {
      style.addStyleImage(
        FOREGROUND_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        FOREGROUND_STALE_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        BACKGROUND_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        BACKGROUND_STALE_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        BEARING_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
  }

  @Test
  fun addBitmaps_compass() {
    every { bearingBitmap.width } returns 40
    every { bearingBitmap.height } returns 40
    every { backgroundBitmap.width } returns 20
    every { backgroundBitmap.height } returns 10

    val mergedBitmap = mockk<Bitmap>(relaxed = true)
    mockkStatic(BitmapUtils::class)
    every {
      BitmapUtils.mergeBitmap(
        bearingBitmap,
        backgroundBitmap,
        10f,
        15f
      )
    } returns mergedBitmap

    every { bearingBitmap.width } returns 40
    every { bearingBitmap.height } returns 40
    every { backgroundStaleBitmap.width } returns 30
    every { backgroundStaleBitmap.height } returns 10

    val mergedStaleBitmap = mockk<Bitmap>(relaxed = true)
    every {
      BitmapUtils.mergeBitmap(
        bearingBitmap,
        backgroundStaleBitmap,
        5f,
        15f
      )
    } returns mergedStaleBitmap

    addBitmaps(withShadow = true, renderMode = RenderMode.COMPASS)

    verify {
      style.addStyleImage(
        BEARING_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        BEARING_STALE_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }

    unmockkStatic(BitmapUtils::class)
  }

  @Test
  fun addBitmaps_gps() {
    addBitmaps(withShadow = true, renderMode = RenderMode.GPS)
    verify {
      style.addStyleImage(
        FOREGROUND_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        FOREGROUND_STALE_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        BACKGROUND_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        BACKGROUND_STALE_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
    verify {
      style.addStyleImage(
        BEARING_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
  }

  private val shadowBitmap = mockk<Bitmap>(relaxed = true)
  private val backgroundBitmap = mockk<Bitmap>(relaxed = true)
  private val backgroundStaleBitmap = mockk<Bitmap>(relaxed = true)
  private val bearingBitmap = mockk<Bitmap>(relaxed = true)
  private val foregroundBitmap = mockk<Bitmap>(relaxed = true)
  private val foregroundStaleBitmap = mockk<Bitmap>(relaxed = true)

  private fun addBitmaps(withShadow: Boolean, renderMode: RenderMode) {
    locationLayerRenderer.addBitmaps(
      renderMode,
      if (withShadow) shadowBitmap else null,
      backgroundBitmap,
      backgroundStaleBitmap,
      bearingBitmap,
      foregroundBitmap,
      foregroundStaleBitmap
    )
  }

  private fun Point.toLocationList() = listOf(latitude(), longitude(), 0.0)

  private val defaultPixelRatio = Resources.getSystem().displayMetrics.density
}