package com.mapbox.maps.plugin.locationcomponent

import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.ModelElevationReference
import com.mapbox.maps.plugin.ModelScaleMode
import com.mapbox.maps.plugin.locationcomponent.utils.take
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(MapboxExperimental::class)
class ModelLayerWrapperTest {

  private val style: MapboxStyleManager = mockk(relaxed = true)
  private val layer = ModelLayerWrapper(
    layerId = MODEL_LAYER_ID,
    sourceId = MODEL_SOURCE_ID,
    modelScale = INITIAL_SCALE,
    modelRotation = INITIAL_ROTATION,
    modelRotationExpression = null,
    modelTranslation = INITIAL_TRANSLATION,
    modelCastShadows = INITIAL_CAST_SHADOWS,
    modelReceiveShadows = INITIAL_RECEIVE_SHADOWS,
    modelOpacity = INITIAL_OPACITY,
    modelOpacityExpression = null,
    modelScaleMode = INITIAL_SCALE_MODE,
    modelEmissiveStrength = INITIAL_MODEL_EMISSIVE_STENGTH,
    modelEmissiveStrengthExpression = null,
    modelColor = INITIAL_MODEL_COLOR,
    modelColorExpression = null,
    modelColorMixIntensity = INITIAL_MODEL_COLOR_MIX_INTENSITY,
    modelColorMixIntensityExpression = null,
    modelElevationReference = INITIAL_MODEL_ELEVATION_REFERENCE,
  )
  private val layerWithExpression = ModelLayerWrapper(
    layerId = MODEL_LAYER_ID,
    sourceId = MODEL_SOURCE_ID,
    modelScale = INITIAL_SCALE,
    modelRotation = INITIAL_ROTATION,
    modelRotationExpression = INITIAL_ROTATION_EXPRESSION,
    modelTranslation = INITIAL_TRANSLATION,
    modelCastShadows = INITIAL_CAST_SHADOWS,
    modelReceiveShadows = INITIAL_RECEIVE_SHADOWS,
    modelOpacity = INITIAL_OPACITY,
    modelOpacityExpression = INITIAL_OPACITY_EXPRESSION,
    modelScaleMode = INITIAL_SCALE_MODE,
    modelEmissiveStrength = INITIAL_MODEL_EMISSIVE_STENGTH,
    modelEmissiveStrengthExpression = INITIAL_MODEL_EMISSIVE_STENGTH_EXPRESSION,
    modelColor = INITIAL_MODEL_COLOR,
    modelColorExpression = INITIAL_MODEL_COLOR_EXPRESSION,
    modelColorMixIntensity = INITIAL_MODEL_COLOR_MIX_INTENSITY,
    modelColorMixIntensityExpression = INITIAL_MODEL_COLOR_MIX_INTENSITY_EXPRESSION,
    modelElevationReference = INITIAL_MODEL_ELEVATION_REFERENCE,
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
        "model-emissive-strength" to INITIAL_MODEL_EMISSIVE_STENGTH,
        "model-color" to colorIntToRgbaExpression(INITIAL_MODEL_COLOR),
        "model-color-mix-intensity" to INITIAL_MODEL_COLOR_MIX_INTENSITY,
        "model-elevation-reference" to INITIAL_MODEL_ELEVATION_REFERENCE.value,
      ).toValue(),
      layer.toValue(),
    )
  }

  @Test
  fun testInitialPropertiesWithExpressions() {
    assertEquals(
      hashMapOf(
        "model-type" to "location-indicator",
        "model-rotation" to INITIAL_ROTATION_EXPRESSION,
        "model-scale" to INITIAL_SCALE,
        "model-translation" to INITIAL_TRANSLATION,
        "model-opacity" to INITIAL_OPACITY_EXPRESSION,
        "model-scale-mode" to INITIAL_SCALE_MODE.value,
        "id" to MODEL_LAYER_ID,
        "source" to MODEL_SOURCE_ID,
        "type" to "model",
        "model-receive-shadows" to INITIAL_RECEIVE_SHADOWS,
        "model-cast-shadows" to INITIAL_CAST_SHADOWS,
        "model-scale-transition" to hashMapOf("duration" to 0, "delay" to 0),
        "model-rotation-transition" to hashMapOf("duration" to 0, "delay" to 0),
        "model-emissive-strength" to INITIAL_MODEL_EMISSIVE_STENGTH_EXPRESSION,
        "model-color" to INITIAL_MODEL_COLOR_EXPRESSION,
        "model-color-mix-intensity" to INITIAL_MODEL_COLOR_MIX_INTENSITY_EXPRESSION,
        "model-elevation-reference" to INITIAL_MODEL_ELEVATION_REFERENCE.value,
      ).toValue(),
      layerWithExpression.toValue(),
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
  fun testNullSlot() {
    layer.slot(null)
    verify(exactly = 1) { style.setStyleLayerProperty(MODEL_LAYER_ID, "slot", Value("")) }
  }

  @Test
  fun testModelScaleModeViewPort() {
    setModelScaleMode(ModelScaleMode.VIEWPORT)
  }

  @Test
  fun testModelScaleModeMap() {
    setModelScaleMode(ModelScaleMode.MAP)
  }

  @Test
  fun testModelElevationReferenceGround() {
    setModelElevationReference(ModelElevationReference.GROUND)
  }

  @Test
  fun testModelElevationReferenceSea() {
    setModelElevationReference(ModelElevationReference.SEA)
  }

  private fun setModelScaleMode(mode: ModelScaleMode) {
    layer.modelScaleMode(mode)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-scale-mode", Value(mode.value)) }
  }

  private fun setModelElevationReference(reference: ModelElevationReference) {
    layer.modelElevationReference(reference)
    verify { style.setStyleLayerProperty(MODEL_LAYER_ID, "model-elevation-reference", Value(reference.value)) }
  }

  companion object {
    private const val MODEL_LAYER_ID = "modelLayerId"
    private const val MODEL_SOURCE_ID = "modelSourceId"
    private val INITIAL_SCALE = arrayListOf(6.0)
    private val INITIAL_ROTATION = arrayListOf(8.0)
    private val INITIAL_ROTATION_EXPRESSION = Value.fromJson(
      """
      ["get", "rotation"]
    """.trimIndent()
    ).take()
    private val INITIAL_TRANSLATION = arrayListOf(0.0)
    private val INITIAL_CAST_SHADOWS = true
    private val INITIAL_RECEIVE_SHADOWS = true
    private const val INITIAL_OPACITY = 1.0
    private val INITIAL_OPACITY_EXPRESSION = Value.fromJson(
      """
      ["get", "opacity"]
    """.trimIndent()
    ).take()
    private val INITIAL_SCALE_MODE = ModelScaleMode.VIEWPORT
    private val INITIAL_MODEL_ELEVATION_REFERENCE = ModelElevationReference.GROUND
    private val INITIAL_MODEL_EMISSIVE_STENGTH = 1.0
    private val INITIAL_MODEL_EMISSIVE_STENGTH_EXPRESSION = Value.fromJson(
      """
      ["get", "emissivelight"]
    """.trimIndent()
    ).take()
    private const val INITIAL_MODEL_COLOR = Color.WHITE
    private val INITIAL_MODEL_COLOR_EXPRESSION = Value.fromJson(
      """
      ["get", "color"]
    """.trimIndent()
    ).take()
    private const val INITIAL_MODEL_COLOR_MIX_INTENSITY = 1.0
    private val INITIAL_MODEL_COLOR_MIX_INTENSITY_EXPRESSION = Value.fromJson(
      """
      ["get", "colorMixIntensity"]
    """.trimIndent()
    ).take()
  }
}