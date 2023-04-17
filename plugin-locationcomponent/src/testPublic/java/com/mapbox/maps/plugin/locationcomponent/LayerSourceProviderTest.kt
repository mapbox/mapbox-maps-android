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
      mapOf(
        "models" to mapOf(
          "defaultModel" to mapOf(
            "orientation" to listOf(0.0, 0.0, 0.0),
            "position" to listOf(0.0, 0.0),
            "uri" to "testurl"
          )
        ),
        "type" to "model",
      ).toValue(),
      modelSource.toValue(),
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
      hashMapOf(
        "model-type" to "location-indicator",
        "model-rotation" to listOf(3.0, 2.0, 1.0),
        "id" to "mapbox-location-model-layer",
        "source" to "mapbox-location-model-source",
        "type" to "model",
        "model-opacity" to 1.0,
        "model-scale" to listOf(1.0, 2.0, 3.0),
        "model-translation" to listOf(0.0, 0.0, 0.0),
        "model-scale-transition" to hashMapOf("duration" to 0, "delay" to 0),
        "model-rotation-transition" to hashMapOf("duration" to 0, "delay" to 0),
      ).toValue(),
      modelLayer.toValue()
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
    val locationIndicatorLayerRenderer =
      layerSourceProvider.getLocationIndicatorLayerRenderer(locationPuck2D)
    assertNotNull(locationIndicatorLayerRenderer)
  }

  @Test
  fun testGetModelLayerRenderer() {
    val locationPuck3D = LocationPuck3D(modelUri = "testurl")
    val modelLayerRenderer = layerSourceProvider.getModelLayerRenderer(locationPuck3D)
    assertNotNull(modelLayerRenderer)
  }
}