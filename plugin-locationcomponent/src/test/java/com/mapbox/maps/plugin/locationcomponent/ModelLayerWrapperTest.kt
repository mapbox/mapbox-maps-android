package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
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
  private val layer = ModelLayerWrapper(MODEL_LAYER_ID, MODEL_SOURCE_ID, INITIAL_SCALE, INITIAL_ROTATION, INITIAL_TRANSLATION)
  private val expected: Expected<String, None> = mockk(relaxed = true)

  @Before
  fun setup() {
    every { style.styleLayerExists(any()) } returns true
    every { style.addPersistentStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
    val styleState = mockk<MapStyleStateDelegate>()
    every { styleState.isFullyLoaded() } returns true
    layer.bindTo(style)
  }

  @Test
  fun testInitialProperties() {
    val value = layer.toValue()
    assertEquals("{model-type=location-indicator, model-rotation=[8.0], id=modelLayerId, source=modelSourceId, type=model, model-scale=[6.0], model-translation=[0.0]}", value.toString())
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
  fun testTranslation() {
    val translation = arrayListOf(1.0, 2.0)
    layer.modelTranslation(translation)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-translation", Value(translation.map { Value(it) })) }
  }

  @Test
  fun testLayerNotReady() {
    every { style.styleLayerExists(any()) } returns false
    val scale = arrayListOf(1.0, 2.0)
    layer.modelScale(scale)
    verify(exactly = 0) { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale", Value(scale.map { Value(it) })) }
  }

  @Test
  fun testUpdateStyle() {
    val newStyle = mockk<StyleInterface>(relaxed = true)
    every { newStyle.styleLayerExists(any()) } returns true
    every { newStyle.setStyleLayerProperty(any(), any(), any()) } returns expected
    layer.updateStyle(newStyle)
    val scale = listOf(1.0, 2.0, 3.0)
    layer.modelScale(scale)
    verify(exactly = 0) { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale", Value(scale.map { Value(it) })) }
    verify(exactly = 1) { newStyle.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale", Value(scale.map { Value(it) })) }
  }

  companion object {
    private const val MODEL_LAYER_ID = "modelLayerId"
    private const val MODEL_SOURCE_ID = "modelSourceId"
    private val INITIAL_SCALE = arrayListOf(6.0)
    private val INITIAL_ROTATION = arrayListOf(8.0)
    private val INITIAL_TRANSLATION = arrayListOf(0.0)
  }
}