package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.StyleManagerInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class ModelLayerWrapperTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val layer = ModelLayerWrapper(MODEL_LAYER_ID, MODEL_SOURCE_ID, INITIAL_SCALE, INITIAL_ROTATION)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  @Before
  fun setup() {
    every { style.styleLayerExists(any()) } returns true
    every { style.addStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
    every { style.isStyleFullyLoaded } returns true
    layer.bindTo(style)
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
  fun testRotation() {
    val rotation = arrayListOf(1.0, 2.0)
    layer.modelRotation(rotation)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-rotation", Value(rotation.map { Value(it) })) }
  }

  @Test
  fun testLayerNotReady() {
    every { style.styleLayerExists(any()) } returns false
    val scale = arrayListOf(1.0, 2.0)
    layer.modelScale(scale)
    verify(exactly = 0) { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale", Value(scale.map { Value(it) })) }
  }

  companion object {
    private const val MODEL_LAYER_ID = "modelLayerId"
    private const val MODEL_SOURCE_ID = "modelSourceId"
    private val INITIAL_SCALE = arrayListOf(6.0)
    private val INITIAL_ROTATION = arrayListOf(8.0)
  }
}