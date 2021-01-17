package com.mapbox.maps.plugin.location

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ModelLocationLayerWrapperTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val layer = ModelLocationLayerWrapper(MODEL_LAYER_ID, MODEL_SOURCE_ID, INITIAL_SCALE, INITIAL_ROTATION)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  @Before
  fun setup() {
    every { style.addStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
    val styleState = mockk<MapStyleStateDelegate>()
    every { styleState.isFullyLoaded() } returns true
    layer.bindTo(style, styleState)
  }

  @Test
  fun testInitialProperties() {
    val value = layer.toValue()
    assertEquals("{model-rotation=[8.0], id=modelLayerId, source=modelSourceId, type=model, model-scale=[6.0]}", value.toString())
  }

  @Test
  fun testScale() {
    val scale = arrayListOf(1.0, 2.0)
    layer.modelScale(scale)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale", Value(scale.map { Value(it) })) }
  }

  @Test
  fun test() {
    val rotation = arrayListOf(1.0, 2.0)
    layer.modelRotation(rotation)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-rotation", Value(rotation.map { Value(it) })) }
  }

  companion object {
    private const val MODEL_LAYER_ID = "modelLayerId"
    private const val MODEL_SOURCE_ID = "modelSourceId"
    private val INITIAL_SCALE = arrayListOf(6.0)
    private val INITIAL_ROTATION = arrayListOf(8.0)
  }
}