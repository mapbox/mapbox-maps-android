package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class LayerSourceProviderTest {

  private lateinit var layerSourceProvider: LayerSourceProvider

  @Before
  fun setup() {
    layerSourceProvider = LayerSourceProvider()
  }

  @Test(expected = RuntimeException::class)
  fun testGetModelSourceUrlEmpty() {
    val locationPuck3D = LocationPuck3D(modelUri = "")
    layerSourceProvider.getModelSource(locationPuck3D)
  }

  @Test
  fun testGetModelSource() {
    val locationPuck3D = LocationPuck3D(modelUri = "testurl")
    val modelSource = layerSourceProvider.getModelSource(locationPuck3D)
    assertEquals(MODEL_SOURCE, modelSource.sourceId)
    assertEquals(
      "{models={defaultModel={orientation=[0.0, 0.0, 0.0], position=[0.0, 0.0], uri=testurl}}, type=model}",
      modelSource.toValue().toString()
    )
  }

  @Test
  fun testGetModelLayer() {
    val locationPuck3D = LocationPuck3D(
      modelUri = "testurl",
      modelScale = listOf(1f, 2f, 3f),
      modelRotation = listOf(3f, 2f, 1f)
    )
    val modelLayer = layerSourceProvider.getModelLayer(locationPuck3D)
    assertEquals(MODEL_LAYER, modelLayer.layerId)
    assertEquals(
      "{model-rotation=[3.0, 2.0, 1.0], id=mapbox-location-model-layer, source=mapbox-location-model-source, type=model, model-scale=[1.0, 2.0, 3.0], model-translation=[0.0, 0.0, 0.0]}",
      modelLayer.toValue().toString()
    )
  }

  @Test
  fun testGetLocationIndicatorLayer() {
    val locationIndicatorLayer = layerSourceProvider.getLocationIndicatorLayer()
    assertEquals(LOCATION_INDICATOR_LAYER, locationIndicatorLayer.layerId)
  }

  @Test
  fun testGetLocationIndicatorLayerRenderer() {
    val locationPuck2D = LocationPuck2D()
    val locationIndicatorLayerRenderer = layerSourceProvider.getLocationIndicatorLayerRenderer(locationPuck2D)
    assertNotNull(locationIndicatorLayerRenderer)
  }

  @Test
  fun testGetModelLayerRenderer() {
    val locationPuck3D = LocationPuck3D(modelUri = "testurl")
    val modelLayerRenderer = layerSourceProvider.getModelLayerRenderer(locationPuck3D)
    assertNotNull(modelLayerRenderer)
  }
}