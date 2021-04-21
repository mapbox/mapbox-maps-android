package com.mapbox.maps.plugin.location

import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.location.modes.RenderMode
import io.mockk.*
import org.junit.Before
import org.junit.Test

class ModelLocationLayerRendererTest {

  private val style: StyleInterface = mockk(relaxed = true)
  private val layerSourceProvider: LayerSourceProvider = mockk(relaxed = true)
  private val layerWrapper: ModelLocationLayerWrapper = mockk(relaxed = true)
  private val sourceWrapper: ModelLocationSourceWrapper = mockk(relaxUnitFun = true, relaxed = true)
  private val option: LocationModelLayerOptions = mockk(relaxed = true)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  private val doubleArraySlot = CapturingSlot<DoubleArray>()

  private lateinit var locationLayerRenderer: ModelLocationLayerRenderer

  @Before
  fun setup() {
    every { style.removeStyleLayer(any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
    every { layerSourceProvider.getModelLayer(any()) } returns layerWrapper
    every { layerSourceProvider.getModelSource(any()) } returns sourceWrapper
    every { layerWrapper.layerId } returns "id"
    every { option.modelRotation } returns listOf(0.0, 0.0, 0.0)
    locationLayerRenderer = ModelLocationLayerRenderer(layerSourceProvider, option)
    locationLayerRenderer.initializeComponents(style, mockk(relaxed = true))
  }

  @Test
  fun initializeComponents_withLocation() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    val bearing = 11f
    locationLayerRenderer.setLatLng(latLng)
    locationLayerRenderer.setGpsBearing(bearing)

    locationLayerRenderer.initializeComponents(style, mockk(relaxed = true))

    verify { sourceWrapper.setPosition(listOf(10.0, 20.0)) }
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, 0.0)) }
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
  fun setLatLng() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    locationLayerRenderer.setLatLng(latLng)

    verify {
      sourceWrapper.setPosition(listOf(10.0, 20.0))
    }
  }

  @Test
  fun setGpsBearing() {
    val bearing = 30.0
    locationLayerRenderer.setGpsBearing(bearing.toFloat())
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, 0.0)) }
    locationLayerRenderer.show(RenderMode.COMPASS, false)
    locationLayerRenderer.setGpsBearing(bearing.toFloat())
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, bearing)) }
    locationLayerRenderer.show(RenderMode.GPS, false)
    locationLayerRenderer.setGpsBearing(bearing.toFloat())
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, bearing)) }
    locationLayerRenderer.show(RenderMode.NORMAL, false)
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, 0.0)) }
  }

  @Test
  fun setCompassBearing() {
    locationLayerRenderer.show(RenderMode.COMPASS, false)
    val bearing = 30.0
    locationLayerRenderer.setCompassBearing(bearing.toFloat())
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, 30.0)) }
  }
}