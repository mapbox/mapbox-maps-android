package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_ICON
import com.mapbox.maps.plugin.locationcomponent.utils.BitmapUtils
import io.mockk.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class LocationIndicatorLayerRendererTest {

  private val style: Style = mockk(relaxed = true)
  private val layerWrapper: LocationIndicatorLayerWrapper = mockk(relaxed = true)
  private val expected: Expected<String, None> = mockk(relaxed = true)

  private val expressionSlot = CapturingSlot<List<Value>>()
  private val doubleListSlot = CapturingSlot<List<Double>>()

  private val puckOptions = mockk<LocationPuck2D>(relaxed = true)
  private val topBitmap = mockk<Bitmap>(relaxed = true)
  private val bearingBitmap = mockk<Bitmap>(relaxed = true)
  private val shadowBitmap = mockk<Bitmap>(relaxed = true)
  private val topDrawableResId = 1
  private val bearingDrawableResId = 2
  private val shadowDrawableResId = 3
  private val topImageHolder = ImageHolder.from(topDrawableResId)
  private val bearingImageHolder = ImageHolder.from(bearingDrawableResId)
  private val shadowImageHolder = ImageHolder.from(shadowDrawableResId)

  private lateinit var locationLayerRenderer: LocationIndicatorLayerRenderer

  @Before
  fun setup() {
    mockkObject(BitmapUtils)
    every { puckOptions.topImage } returns topImageHolder
    every { puckOptions.bearingImage } returns bearingImageHolder
    every { puckOptions.shadowImage } returns shadowImageHolder
    every { puckOptions.opacity } returns 0.5f
    every { BitmapUtils.getBitmapFromDrawableRes(any(), topDrawableResId) } returns topBitmap
    every { BitmapUtils.getBitmapFromDrawableRes(any(), bearingDrawableResId) } returns bearingBitmap
    every { BitmapUtils.getBitmapFromDrawableRes(any(), shadowDrawableResId) } returns shadowBitmap
    every { style.removeStyleLayer(any()) } returns expected
    every { style.styleLayerExists(any()) } returns true
    every { expected.error } returns null
    mockkObject(LayerSourceProvider)
    every { LayerSourceProvider.getLocationIndicatorLayer() } returns layerWrapper
    every { layerWrapper.layerId } returns "id"

    locationLayerRenderer = LocationIndicatorLayerRenderer(
      puckOptions,
      WeakReference(mockk<Context>(relaxed = true)),
    )
    locationLayerRenderer.initializeComponents(style)
  }

  @Test
  fun sanity() {
    Assert.assertNotNull(locationLayerRenderer)
  }

  /**
   * Verify that [LocationLayerRenderer.initializeComponents] in above [setup] calls the right
   * methods.
   */
  @Test
  fun initializeComponents() {
    verify(exactly = 1) { layerWrapper.topImage(TOP_ICON) }
    verify(exactly = 1) { style.addImage(TOP_ICON, topBitmap) }

    verify(exactly = 1) { layerWrapper.bearingImage(BEARING_ICON) }
    verify(exactly = 1) { style.addImage(BEARING_ICON, bearingBitmap) }

    verify(exactly = 1) { layerWrapper.shadowImage(SHADOW_ICON) }
    verify(exactly = 1) { style.addImage(SHADOW_ICON, shadowBitmap) }

    verify(exactly = 1) { layerWrapper.opacity(eq(0.5)) }
  }

  @Test
  fun verifyUseBitmapImage() {
    val topBitmap2 = mockk<Bitmap>(relaxed = true)
    val bearingBitmap2 = mockk<Bitmap>(relaxed = true)
    val shadowBitmap2 = mockk<Bitmap>(relaxed = true)
    val puckOptions2 = mockk<LocationPuck2D>(relaxed = true) {
      every { topImage } returns ImageHolder.from(topBitmap2)
      every { bearingImage } returns ImageHolder.from(bearingBitmap2)
      every { shadowImage } returns ImageHolder.from(shadowBitmap2)
      every { opacity } returns 0.3F
    }
    val locationLayerRenderer2 = LocationIndicatorLayerRenderer(
      puckOptions2,
      WeakReference(mockk<Context>(relaxed = true)),
    )
    locationLayerRenderer2.initializeComponents(style)

    verify(exactly = 1) { style.addImage(TOP_ICON, topBitmap2) }
    verify(exactly = 1) { style.addImage(BEARING_ICON, bearingBitmap2) }
    verify(exactly = 1) { style.addImage(SHADOW_ICON, shadowBitmap2) }
  }

  @Test
  fun initializeComponents_withLocation() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    val bearing = 11.0
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
    locationLayerRenderer.show()

    verify { layerWrapper.visibility(true) }
  }

  @Test
  fun styleAccuracy() {
    val colorArray: FloatArray = LocationIndicatorLayerRenderer.colorToRgbaArray(Color.RED)
    colorArray[3] = 1.0f
    val rgbaExpression = LocationIndicatorLayerRenderer.buildRGBAExpression(colorArray)

    locationLayerRenderer.styleAccuracy(Color.RED, Color.RED)

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
    locationLayerRenderer.setBearing(bearing)
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
    val exp = Value("")
    locationLayerRenderer.styleScaling(exp)

    verify { layerWrapper.shadowImageSize(exp) }
    verify { layerWrapper.bearingImageSize(exp) }
    verify { layerWrapper.topImageSize(exp) }
  }

  @Test
  fun testAddBitmaps() {
    verify {
      style.addImage(
        TOP_ICON,
        any() as Bitmap
      )
    }
    verify(exactly = 1) {
      style.addImage(
        BEARING_ICON,
        any() as Bitmap
      )
    }
    verify {
      style.addImage(
        SHADOW_ICON,
        any() as Bitmap
      )
    }
  }

  @Test
  fun updateStyle() {
    val newStyle = mockk<Style>(relaxed = true)
    locationLayerRenderer.updateStyle(newStyle)
    verify { layerWrapper.updateStyle(newStyle) }
    locationLayerRenderer.removeLayers()
    verify(exactly = 0) { style.removeStyleLayer(any()) }
    verify(exactly = 1) { newStyle.removeStyleLayer(any()) }
  }

  private fun Point.toLocationList() = listOf(latitude(), longitude(), 0.0)
}