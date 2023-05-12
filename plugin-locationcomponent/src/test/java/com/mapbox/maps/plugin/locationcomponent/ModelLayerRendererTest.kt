package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.LocationPuck3D
import io.mockk.*
import org.junit.Before
import org.junit.Test

class ModelLayerRendererTest {

  private val style: Style = mockk(relaxed = true)
  private val layerSourceProvider: LayerSourceProvider = mockk(relaxed = true)
  private val layerWrapper: ModelLayerWrapper = mockk(relaxed = true)
  private val sourceWrapper: ModelSourceWrapper = mockk(relaxUnitFun = true, relaxed = true)
  private val option: LocationPuck3D = mockk(relaxed = true)
  private val expected: Expected<String, None> = mockk(relaxed = true)

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
    every { option.modelOpacity } returns 0.5f
    locationLayerRenderer = ModelLayerRenderer(layerSourceProvider, option)
    locationLayerRenderer.initializeComponents(style)
  }

  /**
   * Verify that [LocationLayerRenderer.initializeComponents] in above [setup] calls the right
   * methods.
   */
  @Test
  fun initializeComponents() {
    verify(exactly = 1) { sourceWrapper.bindTo(style) }
  }

  @Test
  fun initializeComponents_withLocation() {
    val latLng = Point.fromLngLat(10.0, 20.0)
    val bearing = 11.0
    locationLayerRenderer.setLatLng(latLng)
    locationLayerRenderer.setBearing(bearing)

    locationLayerRenderer.initializeComponents(style)

    verify { sourceWrapper.setPositionAndOrientation(listOf(10.0, 20.0), listOf(0.0, 0.0, 11.0)) }
    verify(exactly = 0) { layerWrapper.modelRotation(any()) }
  }

  @Test
  fun addLayers() {
    val positionManager: LocationComponentPositionManager = mockk(relaxUnitFun = true)

    locationLayerRenderer.addLayers(positionManager)

    verify { positionManager.addLayerToMap(layerWrapper) }
    verify(exactly = 1) { option.modelOpacity }
    verify(exactly = 1) { layerWrapper.modelOpacity(0.5) }
    verify(exactly = 1) { layerWrapper.modelRotation(listOf(0.0, 0.0, 0.0)) }
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
      sourceWrapper.setPositionAndOrientation(listOf(10.0, 20.0), listOf(0.0, 0.0, 0.0))
    }
  }

  @Test
  fun setBearingWithoutLastLocation() {
    val bearing = 30.0
    locationLayerRenderer.show()
    locationLayerRenderer.setBearing(bearing)
    verify(exactly = 0) { sourceWrapper.setPositionAndOrientation(any(), any()) }
  }

  @Test
  fun setBearingWithLastLocation() {
    val bearing = 30.0
    locationLayerRenderer.show()
    locationLayerRenderer.lastLocation = Point.fromLngLat(0.0, 0.0)
    locationLayerRenderer.setBearing(bearing)
    verify(exactly = 1) { sourceWrapper.setPositionAndOrientation(any(), listOf(0.0, 0.0, bearing)) }
  }

  @Test
  fun updateStyle() {
    val newStyle = mockk<Style>(relaxed = true)
    locationLayerRenderer.updateStyle(newStyle)
    verify {
      sourceWrapper.updateStyle(newStyle)
      layerWrapper.updateStyle(newStyle)
    }
    locationLayerRenderer.removeLayers()
    verify(exactly = 0) { style.removeStyleLayer(any()) }
    verify(exactly = 1) { newStyle.removeStyleLayer(any()) }
  }
}