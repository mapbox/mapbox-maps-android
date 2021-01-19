package com.mapbox.maps.plugin.locationcomponent

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_STALE_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_STALE_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_STALE_ICON
import io.mockk.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationIndicatorLayerRendererTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val layerSourceProvider: LayerSourceProvider = mockk(relaxed = true)
  private val layerWrapper: LocationIndicatorLayerWrapper = mockk(relaxed = true)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  private val expressionSlot = CapturingSlot<List<Value>>()
  private val doubleListSlot = CapturingSlot<List<Double>>()

  private val puckOptions = LocationPuck2D()

  private lateinit var locationLayerRenderer: LocationIndicatorLayerRenderer

  @Before
  fun setup() {

    every { style.removeStyleLayer(any()) } returns expected
    every { expected.error } returns null
    every { layerSourceProvider.getLocationIndicatorLayer() } returns layerWrapper
    every { layerWrapper.layerId } returns "id"

    locationLayerRenderer = LocationIndicatorLayerRenderer(puckOptions, layerSourceProvider)
    locationLayerRenderer.initializeComponents(style)
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
    locationLayerRenderer.setBearing(bearing)
    locationLayerRenderer.setAccuracyRadius(accuracy)

    locationLayerRenderer.initializeComponents(style)

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
    locationLayerRenderer.show(false)

    verify { layerWrapper.visibility(true) }
  }

  @Test
  fun show_notStale() {
    locationLayerRenderer.show(false)

    verify { layerWrapper.topImage(TOP_ICON) }
    verify { layerWrapper.bearingImage(BEARING_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_ICON) }
  }

  @Test
  fun show_stale() {
    locationLayerRenderer.show(true)

    verify { layerWrapper.topImage(TOP_STALE_ICON) }
    verify { layerWrapper.bearingImage(BEARING_STALE_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_STALE_ICON) }
  }

  @Test
  fun styleAccuracy() {
    val colorArray: FloatArray = LocationIndicatorLayerRenderer.colorToRgbaArray(Color.RED)
    colorArray[3] = 1.0f
    val rgbaExpression = LocationIndicatorLayerRenderer.buildRGBAExpression(colorArray)

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
  fun setBearing() {
    val bearing = 30.0
    locationLayerRenderer.setBearing(bearing.toFloat())

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
    locationLayerRenderer.setLocationStale(true)

    verify { layerWrapper.topImage(TOP_STALE_ICON) }
    verify { layerWrapper.bearingImage(BEARING_STALE_ICON) }
    verify { layerWrapper.shadowImage(SHADOW_STALE_ICON) }
  }

  @Test
  fun testAddBitmaps() {
    addBitmaps()
    verify {
      style.addStyleImage(
        TOP_ICON,
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
        TOP_STALE_ICON,
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
    verify {
      style.addStyleImage(
        SHADOW_STALE_ICON,
        defaultPixelRatio,
        any(),
        false,
        listOf(),
        listOf(),
        null
      )
    }
  }

  private val topBitmap = mockk<Bitmap>(relaxed = true)
  private val topStaleBitmap = mockk<Bitmap>(relaxed = true)
  private val bearingBitmap = mockk<Bitmap>(relaxed = true)
  private val bearingStaleBitmap = mockk<Bitmap>(relaxed = true)
  private val shadowBitmap = mockk<Bitmap>(relaxed = true)
  private val shadowStaleBitmap = mockk<Bitmap>(relaxed = true)

  private fun addBitmaps() {
    locationLayerRenderer.addBitmaps(
      topBitmap,
      topStaleBitmap,
      bearingBitmap,
      bearingStaleBitmap,
      shadowBitmap,
      shadowStaleBitmap
    )
  }

  private fun Point.toLocationList() = listOf(latitude(), longitude(), 0.0)

  private val defaultPixelRatio = Resources.getSystem().displayMetrics.density
}