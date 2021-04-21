package com.mapbox.maps.plugin.locationcomponent

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleInterface
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

@RunWith(RobolectricTestRunner::class)
class LocationIndicatorLayerRendererTest {

  private val style: StyleInterface = mockk(relaxed = true)
  private val layerSourceProvider: LayerSourceProvider = mockk(relaxed = true)
  private val layerWrapper: LocationIndicatorLayerWrapper = mockk(relaxed = true)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  private val expressionSlot = CapturingSlot<List<Value>>()
  private val doubleListSlot = CapturingSlot<List<Double>>()

  private val puckOptions = mockk<LocationPuck2D>(relaxed = true)
  private val bitmap = mockk<Bitmap>(relaxed = true)
  private val drawable = mockk<Drawable>(relaxed = true)

  private lateinit var locationLayerRenderer: LocationIndicatorLayerRenderer

  @Before
  fun setup() {
    mockkObject(BitmapUtils)
    every { puckOptions.topImage } returns drawable
    every { puckOptions.bearingImage } returns drawable
    every { puckOptions.shadowImage } returns drawable
    every { BitmapUtils.getBitmapFromDrawable(drawable) } returns bitmap
    every { style.removeStyleLayer(any()) } returns expected
    every { style.styleLayerExists(any()) } returns true
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
        any()
      )
    }
    verify {
      style.addImage(
        BEARING_ICON,
        any()
      )
    }
    verify {
      style.addImage(
        SHADOW_ICON,
        any()
      )
    }
  }

  private fun Point.toLocationList() = listOf(latitude(), longitude(), 0.0)
}