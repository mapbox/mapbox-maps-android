package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.ModelScaleMode
import com.mapbox.maps.plugin.locationcomponent.utils.take
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ModelLayerWrapperTest {

  private val style: MapboxStyleManager = mockk(relaxed = true)
  private val layer = ModelLayerWrapper(
    MODEL_LAYER_ID,
    MODEL_SOURCE_ID,
    INITIAL_SCALE,
    INITIAL_ROTATION,
    INITIAL_TRANSLATION,
    INITIAL_CAST_SHADOWS,
    INITIAL_RECEIVE_SHADOWS,
    INITIAL_OPACITY,
    INITIAL_SCALE_MODE,
    INITIAL_MODEL_EMISSIVE_STENGTH,
    null
  )
  private val layerWithEmissiveStrengthExpression = ModelLayerWrapper(
    MODEL_LAYER_ID,
    MODEL_SOURCE_ID,
    INITIAL_SCALE,
    INITIAL_ROTATION,
    INITIAL_TRANSLATION,
    INITIAL_CAST_SHADOWS,
    INITIAL_RECEIVE_SHADOWS,
    INITIAL_OPACITY,
    INITIAL_SCALE_MODE,
    INITIAL_MODEL_EMISSIVE_STENGTH,
    INITIAL_MODEL_EMISSIVE_STENGTH_EXPRESSION
  )
  private val expected: Expected<String, None> = mockk(relaxed = true)

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    every { style.addPersistentStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
    layer.bindTo(style)
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun testInitialProperties() {
    assertEquals(
      hashMapOf(
        "model-type" to "location-indicator",
        "model-rotation" to INITIAL_ROTATION,
        "model-scale" to INITIAL_SCALE,
        "model-translation" to INITIAL_TRANSLATION,
        "model-opacity" to INITIAL_OPACITY,
        "model-scale-mode" to INITIAL_SCALE_MODE.value,
        "id" to MODEL_LAYER_ID,
        "source" to MODEL_SOURCE_ID,
        "type" to "model",
        "model-receive-shadows" to INITIAL_RECEIVE_SHADOWS,
        "model-cast-shadows" to INITIAL_CAST_SHADOWS,
        "model-scale-transition" to hashMapOf("duration" to 0, "delay" to 0),
        "model-rotation-transition" to hashMapOf("duration" to 0, "delay" to 0),
        "model-emissive-strength" to INITIAL_MODEL_EMISSIVE_STENGTH
      ).toValue(),
      layer.toValue(),
    )
  }

  @Test
  fun testInitialPropertiesWithEmissiveStrengthExpression() {
    assertEquals(
      hashMapOf(
        "model-type" to "location-indicator",
        "model-rotation" to INITIAL_ROTATION,
        "model-scale" to INITIAL_SCALE,
        "model-translation" to INITIAL_TRANSLATION,
        "model-opacity" to INITIAL_OPACITY,
        "model-scale-mode" to INITIAL_SCALE_MODE.value,
        "id" to MODEL_LAYER_ID,
        "source" to MODEL_SOURCE_ID,
        "type" to "model",
        "model-receive-shadows" to INITIAL_RECEIVE_SHADOWS,
        "model-cast-shadows" to INITIAL_CAST_SHADOWS,
        "model-scale-transition" to hashMapOf("duration" to 0, "delay" to 0),
        "model-rotation-transition" to hashMapOf("duration" to 0, "delay" to 0),
        "model-emissive-strength" to INITIAL_MODEL_EMISSIVE_STENGTH_EXPRESSION
      ).toValue(),
      layerWithEmissiveStrengthExpression.toValue(),
    )
  }

  @Test
  fun testScale() {
    val scale = arrayListOf(1.0, 2.0)
    layer.modelScale(scale)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale", Value(scale.map(::Value))) }
  }

  @Test
  fun testRotation() {
    val rotation = arrayListOf(1.0, 2.0)
    layer.modelRotation(rotation)
    verify {
      style.setStyleLayerProperty(
        MODEL_LAYER_ID,
        "model-rotation",
        Value(rotation.map(::Value))
      )
    }
  }

  @Test
  fun testTranslation() {
    val translation = arrayListOf(1.0, 2.0)
    layer.modelTranslation(translation)
    verify {
      style.setStyleLayerProperty(
        MODEL_LAYER_ID,
        "model-translation",
        Value(translation.map(::Value))
      )
    }
  }

  @Test
  fun testLayerNotReady() {
    every {
      style.setStyleLayerProperty(
        any(),
        any(),
        any()
      )
    } returns ExpectedFactory.createError("error")
    val scale = arrayListOf(1.0, 2.0)
    layer.modelScale(scale)
    verify(exactly = 1) {
      style.setStyleLayerProperty(
        MODEL_LAYER_ID,
        "model-scale",
        Value(scale.map(::Value))
      )
    }
    verify(exactly = 1) { logE(any(), any()) }
  }

  @Test
  fun testUpdateStyle() {
    val newStyle = mockk<MapboxStyleManager>(relaxed = true)
    every { newStyle.setStyleLayerProperty(any(), any(), any()) } returns expected
    layer.updateStyle(newStyle)
    val scale = listOf(1.0, 2.0, 3.0)
    layer.modelScale(scale)
    verify(exactly = 0) {
      style.setStyleLayerProperty(
        MODEL_LAYER_ID,
        "model-scale",
        Value(scale.map(::Value))
      )
    }
    verify(exactly = 1) {
      newStyle.setStyleLayerProperty(
        MODEL_LAYER_ID,
        "model-scale",
        Value(scale.map(::Value))
      )
    }
  }

  @Test
  fun testModelOpacity() {
    layer.modelOpacity(0.8)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-opacity", Value(0.8)) }
  }

  @Test
  fun testSlot() {
    val middleSlot = "middle"
    layer.slot(middleSlot)
    verify(exactly = 1) { style.setStyleLayerProperty(MODEL_LAYER_ID, "slot", Value(middleSlot)) }
  }

  @Test
  fun testModelScaleModeViewPort() {
    setModelScaleMode(ModelScaleMode.VIEWPORT)
  }

  @Test
  fun testModelScaleModeMap() {
    setModelScaleMode(ModelScaleMode.MAP)
  }

  private fun setModelScaleMode(mode: ModelScaleMode) {
    layer.modelScaleMode(mode)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale-mode", Value(mode.value)) }
  }

  companion object {
    private const val MODEL_LAYER_ID = "modelLayerId"
    private const val MODEL_SOURCE_ID = "modelSourceId"
    private val INITIAL_SCALE = arrayListOf(6.0)
    private val INITIAL_ROTATION = arrayListOf(8.0)
    private val INITIAL_TRANSLATION = arrayListOf(0.0)
    private val INITIAL_CAST_SHADOWS = true
    private val INITIAL_RECEIVE_SHADOWS = true
    private const val INITIAL_OPACITY = 1.0
    private val INITIAL_SCALE_MODE = ModelScaleMode.VIEWPORT
    private val INITIAL_MODEL_EMISSIVE_STENGTH = 1.0
    private val INITIAL_MODEL_EMISSIVE_STENGTH_EXPRESSION = Value.fromJson(
      """
      ["get", "emissivelight"]
    """.trimIndent()
    ).take()
  }
}