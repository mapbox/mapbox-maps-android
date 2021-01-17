package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.LocationPuck3D
import io.mockk.*
import org.junit.Before
import org.junit.Test

class ModelLayerRendererTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val layerSourceProvider: LayerSourceProvider = mockk(relaxed = true)
  private val layerWrapper: ModelLayerWrapper = mockk(relaxed = true)
  private val sourceWrapper: ModelSourceWrapper = mockk(relaxUnitFun = true, relaxed = true)
  private val option: LocationPuck3D = mockk(relaxed = true)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  private lateinit var locationLayerRenderer: ModelLayerRenderer

  @Before
  fun setup() {
    every { style.removeStyleLayer(any()) } returns expected
    every { style.styleLayerExists(any()) } returns true
    every { style.styleSourceExists(any()) } returns true
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
    every { layerSourceProvider.getModelLayer(any()) } returns layerWrapper
    every { layerSourceProvider.getModelSource(any()) } returns sourceWrapper
    every { layerWrapper.layerId } returns "id"
    every { option.modelRotation } returns listOf(0.0f, 0.0f, 0.0f)
    locationLayerRenderer = ModelLayerRenderer(layerSourceProvider, option)
    locationLayerRenderer.initializeComponents(style)
  }

  @Test
  fun initializeComponents_withLocation() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    val bearing = 11.0
    locationLayerRenderer.setLatLng(latLng)
    locationLayerRenderer.setBearing(bearing)

    locationLayerRenderer.initializeComponents(style)

    verify { sourceWrapper.setPosition(listOf(10.0, 20.0)) }
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, 11.0)) }
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
  fun setLatLng() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    locationLayerRenderer.setLatLng(latLng)

    verify {
      sourceWrapper.setPosition(listOf(10.0, 20.0))
    }
  }

  @Test
  fun setBearing() {
    val bearing = 30.0
    locationLayerRenderer.show()
    locationLayerRenderer.setBearing(bearing)
    verify { layerWrapper.modelRotation(listOf(0.0, 0.0, bearing)) }
  }
}