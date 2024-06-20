// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class ModelLayerTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val valueExpected = mockk<Expected<String, Value>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()
  private val valueSlot = slot<Value>()

  @Before
  fun prepareTest() {
    every { style.getStyleLayerProperty(any(), any()) } returns styleProperty
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.setStyleLayerProperty(any(), any(), any()) } returns expected
    every { style.addStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperties(any(), any()) } returns expected
    every { style.getStyleLayerProperties(any()) } returns valueExpected
    every { expected.error } returns null
    every { valueExpected.value } returns null

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleLayerPropertyDefaultValue(any(), any()) } returns styleProperty
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun sourceLayerTestDsl() {
    val layer = modelLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=model}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.sourceLayer("test")
    verify { style.setStyleLayerProperty("id", "source-layer", capture(valueSlot)) }
    assertEquals(
      "test",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun minZoomTestDsl() {
    val layer = modelLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=model, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.minZoom(12.0)
    verify { style.setStyleLayerProperty("id", "minzoom", capture(valueSlot)) }
    assertEquals(
      "12.0",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun maxZoomTestDsl() {
    val layer = modelLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=model}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.maxZoom(12.0)
    verify { style.setStyleLayerProperty("id", "maxzoom", capture(valueSlot)) }
    assertEquals(
      "12.0",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun filterSet() {
    val expression = eq {
      get {
        literal("count")
      }
      literal(0)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.filter(expression)
    verify { style.setStyleLayerProperty("id", "filter", capture(valueSlot)) }
    assertEquals(expression.toString(), valueSlot.captured.toString())
  }

  @Test
  fun filterGet() {
    val expression = eq {
      get {
        literal("count")
      }
      literal(0)
    }
    every { styleProperty.value } returns expression
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.filter?.toString())
    verify { style.getStyleLayerProperty("id", "filter") }
  }
  // Property getters and setters

  @Test
  fun modelIdSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.modelId(testValue)
    verify { style.setStyleLayerProperty("id", "model-id", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun modelIdGet() {
    val testValue = "abc"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.modelId?.toString())
    verify { style.getStyleLayerProperty("id", "model-id") }
  }
  // Expression Tests

  @Test
  fun modelIdAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelId(expression)
    verify { style.setStyleLayerProperty("id", "model-id", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelIdAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelIdAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-id") }
  }

  @Test
  fun modelIdAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelIdAsExpression)
    verify { style.getStyleLayerProperty("id", "model-id") }
  }

  @Test
  fun modelIdAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("abc", layer.modelIdAsExpression.toString())
    val expectedValue = "abc"
    assertEquals(expectedValue, layer.modelId)
    verify { style.getStyleLayerProperty("id", "model-id") }
  }

  @Test
  fun modelAmbientOcclusionIntensitySet() {
    val layer = modelLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.modelAmbientOcclusionIntensity(testValue)
    verify { style.setStyleLayerProperty("id", "model-ambient-occlusion-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun modelAmbientOcclusionIntensityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.modelAmbientOcclusionIntensity?.toString())
    verify { style.getStyleLayerProperty("id", "model-ambient-occlusion-intensity") }
  }
  // Expression Tests

  @Test
  fun modelAmbientOcclusionIntensityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelAmbientOcclusionIntensity(expression)
    verify { style.setStyleLayerProperty("id", "model-ambient-occlusion-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelAmbientOcclusionIntensityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelAmbientOcclusionIntensityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-ambient-occlusion-intensity") }
  }

  @Test
  fun modelAmbientOcclusionIntensityAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelAmbientOcclusionIntensityAsExpression)
    verify { style.getStyleLayerProperty("id", "model-ambient-occlusion-intensity") }
  }

  @Test
  fun modelAmbientOcclusionIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.modelAmbientOcclusionIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelAmbientOcclusionIntensity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "model-ambient-occlusion-intensity") }
  }

  @Test
  fun modelAmbientOcclusionIntensityTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelAmbientOcclusionIntensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-ambient-occlusion-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelAmbientOcclusionIntensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelAmbientOcclusionIntensityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-ambient-occlusion-intensity-transition") }
  }

  @Test
  fun modelAmbientOcclusionIntensityTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelAmbientOcclusionIntensityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-ambient-occlusion-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelCastShadowsSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.modelCastShadows(testValue)
    verify { style.setStyleLayerProperty("id", "model-cast-shadows", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun modelCastShadowsGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.modelCastShadows?.toString())
    verify { style.getStyleLayerProperty("id", "model-cast-shadows") }
  }
  // Expression Tests

  @Test
  fun modelCastShadowsAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelCastShadows(expression)
    verify { style.setStyleLayerProperty("id", "model-cast-shadows", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelCastShadowsAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelCastShadowsAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-cast-shadows") }
  }

  @Test
  fun modelCastShadowsAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelCastShadowsAsExpression)
    verify { style.getStyleLayerProperty("id", "model-cast-shadows") }
  }

  @Test
  fun modelCastShadowsAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.modelCastShadowsAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.modelCastShadows)
    verify { style.getStyleLayerProperty("id", "model-cast-shadows") }
  }

  @Test
  fun modelColorSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.modelColor(testValue)
    verify { style.setStyleLayerProperty("id", "model-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun modelColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.modelColor?.toString())
    verify { style.getStyleLayerProperty("id", "model-color") }
  }
  // Expression Tests

  @Test
  fun modelColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelColor(expression)
    verify { style.setStyleLayerProperty("id", "model-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-color") }
  }

  @Test
  fun modelColorAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelColorAsExpression)
    verify { style.getStyleLayerProperty("id", "model-color") }
  }

  @Test
  fun modelColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.modelColor)
    assertEquals(Color.BLACK, layer.modelColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "model-color") }
  }

  @Test
  fun modelColorAsColorIntSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "model-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun modelColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.modelColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "model-color") }
  }

  @Test
  fun modelColorTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-color-transition") }
  }

  @Test
  fun modelColorTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelColorMixIntensitySet() {
    val layer = modelLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.modelColorMixIntensity(testValue)
    verify { style.setStyleLayerProperty("id", "model-color-mix-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun modelColorMixIntensityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.modelColorMixIntensity?.toString())
    verify { style.getStyleLayerProperty("id", "model-color-mix-intensity") }
  }
  // Expression Tests

  @Test
  fun modelColorMixIntensityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelColorMixIntensity(expression)
    verify { style.setStyleLayerProperty("id", "model-color-mix-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelColorMixIntensityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelColorMixIntensityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-color-mix-intensity") }
  }

  @Test
  fun modelColorMixIntensityAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelColorMixIntensityAsExpression)
    verify { style.getStyleLayerProperty("id", "model-color-mix-intensity") }
  }

  @Test
  fun modelColorMixIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.modelColorMixIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelColorMixIntensity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "model-color-mix-intensity") }
  }

  @Test
  fun modelColorMixIntensityTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelColorMixIntensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-color-mix-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelColorMixIntensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelColorMixIntensityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-color-mix-intensity-transition") }
  }

  @Test
  fun modelColorMixIntensityTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelColorMixIntensityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-color-mix-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelCutoffFadeRangeSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.modelCutoffFadeRange(testValue)
    verify { style.setStyleLayerProperty("id", "model-cutoff-fade-range", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun modelCutoffFadeRangeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.modelCutoffFadeRange?.toString())
    verify { style.getStyleLayerProperty("id", "model-cutoff-fade-range") }
  }
  // Expression Tests

  @Test
  fun modelCutoffFadeRangeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelCutoffFadeRange(expression)
    verify { style.setStyleLayerProperty("id", "model-cutoff-fade-range", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelCutoffFadeRangeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelCutoffFadeRangeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-cutoff-fade-range") }
  }

  @Test
  fun modelCutoffFadeRangeAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelCutoffFadeRangeAsExpression)
    verify { style.getStyleLayerProperty("id", "model-cutoff-fade-range") }
  }

  @Test
  fun modelCutoffFadeRangeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.modelCutoffFadeRangeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelCutoffFadeRange!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "model-cutoff-fade-range") }
  }

  @Test
  fun modelEmissiveStrengthSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.modelEmissiveStrength(testValue)
    verify { style.setStyleLayerProperty("id", "model-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun modelEmissiveStrengthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.modelEmissiveStrength?.toString())
    verify { style.getStyleLayerProperty("id", "model-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun modelEmissiveStrengthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelEmissiveStrength(expression)
    verify { style.setStyleLayerProperty("id", "model-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelEmissiveStrengthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelEmissiveStrengthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-emissive-strength") }
  }

  @Test
  fun modelEmissiveStrengthAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelEmissiveStrengthAsExpression)
    verify { style.getStyleLayerProperty("id", "model-emissive-strength") }
  }

  @Test
  fun modelEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.modelEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelEmissiveStrength!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "model-emissive-strength") }
  }

  @Test
  fun modelEmissiveStrengthTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelEmissiveStrengthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelEmissiveStrengthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelEmissiveStrengthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-emissive-strength-transition") }
  }

  @Test
  fun modelEmissiveStrengthTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelEmissiveStrengthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelFrontCutoffSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0, 2.0)
    layer.bindTo(style)
    layer.modelFrontCutoff(testValue)
    verify { style.setStyleLayerProperty("id", "model-front-cutoff", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0, 2.0]")
  }

  @Test
  fun modelFrontCutoffGet() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), layer.modelFrontCutoff?.toString())
    verify { style.getStyleLayerProperty("id", "model-front-cutoff") }
  }
  // Expression Tests

  @Test
  fun modelFrontCutoffAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelFrontCutoff(expression)
    verify { style.setStyleLayerProperty("id", "model-front-cutoff", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelFrontCutoffAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelFrontCutoffAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-front-cutoff") }
  }

  @Test
  fun modelFrontCutoffAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelFrontCutoffAsExpression)
    verify { style.getStyleLayerProperty("id", "model-front-cutoff") }
  }

  @Test
  fun modelFrontCutoffAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0]]", layer.modelFrontCutoffAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.modelFrontCutoff!!)
    verify { style.getStyleLayerProperty("id", "model-front-cutoff") }
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
    layer.bindTo(style)
    layer.modelHeightBasedEmissiveStrengthMultiplier(testValue)
    verify { style.setStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0, 2.0, 3.0, 4.0]")
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierGet() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
    assertEquals(expectedValue.toString(), layer.modelHeightBasedEmissiveStrengthMultiplier?.toString())
    verify { style.getStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier") }
  }
  // Expression Tests

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelHeightBasedEmissiveStrengthMultiplier(expression)
    verify { style.setStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelHeightBasedEmissiveStrengthMultiplierAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier") }
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelHeightBasedEmissiveStrengthMultiplierAsExpression)
    verify { style.getStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier") }
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0, 3.0, 4.0))
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0, 3.0, 4.0]]", layer.modelHeightBasedEmissiveStrengthMultiplierAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0, 3.0, 4.0), layer.modelHeightBasedEmissiveStrengthMultiplier!!)
    verify { style.getStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier") }
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelHeightBasedEmissiveStrengthMultiplierTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelHeightBasedEmissiveStrengthMultiplierTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier-transition") }
  }

  @Test
  fun modelHeightBasedEmissiveStrengthMultiplierTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelHeightBasedEmissiveStrengthMultiplierTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-height-based-emissive-strength-multiplier-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelOpacitySet() {
    val layer = modelLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.modelOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "model-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun modelOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.modelOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "model-opacity") }
  }
  // Expression Tests

  @Test
  fun modelOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelOpacity(expression)
    verify { style.setStyleLayerProperty("id", "model-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-opacity") }
  }

  @Test
  fun modelOpacityAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "model-opacity") }
  }

  @Test
  fun modelOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.modelOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "model-opacity") }
  }

  @Test
  fun modelOpacityTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-opacity-transition") }
  }

  @Test
  fun modelOpacityTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelReceiveShadowsSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.modelReceiveShadows(testValue)
    verify { style.setStyleLayerProperty("id", "model-receive-shadows", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun modelReceiveShadowsGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.modelReceiveShadows?.toString())
    verify { style.getStyleLayerProperty("id", "model-receive-shadows") }
  }
  // Expression Tests

  @Test
  fun modelReceiveShadowsAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelReceiveShadows(expression)
    verify { style.setStyleLayerProperty("id", "model-receive-shadows", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelReceiveShadowsAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelReceiveShadowsAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-receive-shadows") }
  }

  @Test
  fun modelReceiveShadowsAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelReceiveShadowsAsExpression)
    verify { style.getStyleLayerProperty("id", "model-receive-shadows") }
  }

  @Test
  fun modelReceiveShadowsAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.modelReceiveShadowsAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.modelReceiveShadows)
    verify { style.getStyleLayerProperty("id", "model-receive-shadows") }
  }

  @Test
  fun modelRotationSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0, 2.0)
    layer.bindTo(style)
    layer.modelRotation(testValue)
    verify { style.setStyleLayerProperty("id", "model-rotation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0, 2.0]")
  }

  @Test
  fun modelRotationGet() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), layer.modelRotation?.toString())
    verify { style.getStyleLayerProperty("id", "model-rotation") }
  }
  // Expression Tests

  @Test
  fun modelRotationAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelRotation(expression)
    verify { style.setStyleLayerProperty("id", "model-rotation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelRotationAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelRotationAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-rotation") }
  }

  @Test
  fun modelRotationAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelRotationAsExpression)
    verify { style.getStyleLayerProperty("id", "model-rotation") }
  }

  @Test
  fun modelRotationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0]]", layer.modelRotationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.modelRotation!!)
    verify { style.getStyleLayerProperty("id", "model-rotation") }
  }

  @Test
  fun modelRotationTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelRotationTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-rotation-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelRotationTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelRotationTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-rotation-transition") }
  }

  @Test
  fun modelRotationTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelRotationTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-rotation-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelRoughnessSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.modelRoughness(testValue)
    verify { style.setStyleLayerProperty("id", "model-roughness", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun modelRoughnessGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.modelRoughness?.toString())
    verify { style.getStyleLayerProperty("id", "model-roughness") }
  }
  // Expression Tests

  @Test
  fun modelRoughnessAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelRoughness(expression)
    verify { style.setStyleLayerProperty("id", "model-roughness", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelRoughnessAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelRoughnessAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-roughness") }
  }

  @Test
  fun modelRoughnessAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelRoughnessAsExpression)
    verify { style.getStyleLayerProperty("id", "model-roughness") }
  }

  @Test
  fun modelRoughnessAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.modelRoughnessAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelRoughness!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "model-roughness") }
  }

  @Test
  fun modelRoughnessTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelRoughnessTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-roughness-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelRoughnessTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelRoughnessTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-roughness-transition") }
  }

  @Test
  fun modelRoughnessTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelRoughnessTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-roughness-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelScaleSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0, 2.0)
    layer.bindTo(style)
    layer.modelScale(testValue)
    verify { style.setStyleLayerProperty("id", "model-scale", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0, 2.0]")
  }

  @Test
  fun modelScaleGet() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), layer.modelScale?.toString())
    verify { style.getStyleLayerProperty("id", "model-scale") }
  }
  // Expression Tests

  @Test
  fun modelScaleAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelScale(expression)
    verify { style.setStyleLayerProperty("id", "model-scale", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelScaleAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelScaleAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-scale") }
  }

  @Test
  fun modelScaleAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelScaleAsExpression)
    verify { style.getStyleLayerProperty("id", "model-scale") }
  }

  @Test
  fun modelScaleAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0]]", layer.modelScaleAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.modelScale!!)
    verify { style.getStyleLayerProperty("id", "model-scale") }
  }

  @Test
  fun modelScaleTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelScaleTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-scale-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelScaleTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelScaleTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-scale-transition") }
  }

  @Test
  fun modelScaleTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelScaleTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-scale-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelScaleModeSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelScaleMode(ModelScaleMode.MAP)
    verify { style.setStyleLayerProperty("id", "model-scale-mode", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun modelScaleModeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(ModelScaleMode.MAP, layer.modelScaleMode)
    verify { style.getStyleLayerProperty("id", "model-scale-mode") }
  }
  // Expression Tests

  @Test
  fun modelScaleModeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelScaleMode(expression)
    verify { style.setStyleLayerProperty("id", "model-scale-mode", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelScaleModeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelScaleModeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-scale-mode") }
  }

  @Test
  fun modelScaleModeAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelScaleModeAsExpression)
    verify { style.getStyleLayerProperty("id", "model-scale-mode") }
  }

  @Test
  fun modelScaleModeAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.modelScaleModeAsExpression?.toString())
    assertEquals(ModelScaleMode.MAP.value, layer.modelScaleModeAsExpression.toString())
    assertEquals(ModelScaleMode.MAP, layer.modelScaleMode)
    verify { style.getStyleLayerProperty("id", "model-scale-mode") }
  }

  @Test
  fun modelTranslationSet() {
    val layer = modelLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0, 2.0)
    layer.bindTo(style)
    layer.modelTranslation(testValue)
    verify { style.setStyleLayerProperty("id", "model-translation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0, 2.0]")
  }

  @Test
  fun modelTranslationGet() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), layer.modelTranslation?.toString())
    verify { style.getStyleLayerProperty("id", "model-translation") }
  }
  // Expression Tests

  @Test
  fun modelTranslationAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelTranslation(expression)
    verify { style.setStyleLayerProperty("id", "model-translation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelTranslationAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelTranslationAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-translation") }
  }

  @Test
  fun modelTranslationAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelTranslationAsExpression)
    verify { style.getStyleLayerProperty("id", "model-translation") }
  }

  @Test
  fun modelTranslationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0]]", layer.modelTranslationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.modelTranslation!!)
    verify { style.getStyleLayerProperty("id", "model-translation") }
  }

  @Test
  fun modelTranslationTransitionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelTranslationTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "model-translation-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelTranslationTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.modelTranslationTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "model-translation-transition") }
  }

  @Test
  fun modelTranslationTransitionSetDsl() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelTranslationTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "model-translation-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun modelTypeSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelType(ModelType.COMMON_3D)
    verify { style.setStyleLayerProperty("id", "model-type", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "common-3d")
  }

  @Test
  fun modelTypeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("common-3d")

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(ModelType.COMMON_3D, layer.modelType)
    verify { style.getStyleLayerProperty("id", "model-type") }
  }
  // Expression Tests

  @Test
  fun modelTypeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.modelType(expression)
    verify { style.setStyleLayerProperty("id", "model-type", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun modelTypeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.modelTypeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "model-type") }
  }

  @Test
  fun modelTypeAsExpressionGetNull() {
    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.modelTypeAsExpression)
    verify { style.getStyleLayerProperty("id", "model-type") }
  }

  @Test
  fun modelTypeAsExpressionGetFromLiteral() {
    val value = "common-3d"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.modelTypeAsExpression?.toString())
    assertEquals(ModelType.COMMON_3D.value, layer.modelTypeAsExpression.toString())
    assertEquals(ModelType.COMMON_3D, layer.modelType)
    verify { style.getStyleLayerProperty("id", "model-type") }
  }

  @Test
  fun visibilitySet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun visibilityAsExpressionSet() {
    val layer = modelLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(literal("none"))
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityAsExpressionGet() {
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns literal("none")

    val layer = modelLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(literal("none"), layer.visibilityAsExpression)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = modelLayer("id", "source") { }
    assertEquals("model", layer.getType())
  }

  @Test
  fun getModelLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("model"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as ModelLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("model", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultModelIdTest() {
    val testValue = "abc"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelId?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-id") }
  }
  // Expression Tests

  @Test
  fun defaultModelIdAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelIdAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-id") }
  }

  @Test
  fun defaultModelIdAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    assertEquals("abc", ModelLayer.defaultModelIdAsExpression.toString())
    val expectedValue = "abc"
    assertEquals(expectedValue, ModelLayer.defaultModelId)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-id") }
  }

  @Test
  fun defaultModelAmbientOcclusionIntensityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelAmbientOcclusionIntensity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity") }
  }
  // Expression Tests

  @Test
  fun defaultModelAmbientOcclusionIntensityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelAmbientOcclusionIntensityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity") }
  }

  @Test
  fun defaultModelAmbientOcclusionIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, ModelLayer.defaultModelAmbientOcclusionIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, ModelLayer.defaultModelAmbientOcclusionIntensity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity") }
  }

  @Test
  fun defaultModelAmbientOcclusionIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelAmbientOcclusionIntensityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity-transition") }
  }

  @Test
  fun defaultModelCastShadowsTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelCastShadows?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cast-shadows") }
  }
  // Expression Tests

  @Test
  fun defaultModelCastShadowsAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelCastShadowsAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cast-shadows") }
  }

  @Test
  fun defaultModelCastShadowsAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", ModelLayer.defaultModelCastShadowsAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, ModelLayer.defaultModelCastShadows)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cast-shadows") }
  }

  @Test
  fun defaultModelColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color") }
  }
  // Expression Tests

  @Test
  fun defaultModelColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color") }
  }

  @Test
  fun defaultModelColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), ModelLayer.defaultModelColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", ModelLayer.defaultModelColor)
    assertEquals(Color.BLACK, ModelLayer.defaultModelColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color") }
  }

  @Test
  fun defaultModelColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, ModelLayer.defaultModelColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color") }
  }

  @Test
  fun defaultModelColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-transition") }
  }

  @Test
  fun defaultModelColorMixIntensityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelColorMixIntensity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity") }
  }
  // Expression Tests

  @Test
  fun defaultModelColorMixIntensityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelColorMixIntensityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity") }
  }

  @Test
  fun defaultModelColorMixIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, ModelLayer.defaultModelColorMixIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, ModelLayer.defaultModelColorMixIntensity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity") }
  }

  @Test
  fun defaultModelColorMixIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelColorMixIntensityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity-transition") }
  }

  @Test
  fun defaultModelCutoffFadeRangeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelCutoffFadeRange?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cutoff-fade-range") }
  }
  // Expression Tests

  @Test
  fun defaultModelCutoffFadeRangeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelCutoffFadeRangeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cutoff-fade-range") }
  }

  @Test
  fun defaultModelCutoffFadeRangeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, ModelLayer.defaultModelCutoffFadeRangeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, ModelLayer.defaultModelCutoffFadeRange!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cutoff-fade-range") }
  }

  @Test
  fun defaultModelEmissiveStrengthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelEmissiveStrength?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun defaultModelEmissiveStrengthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelEmissiveStrengthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength") }
  }

  @Test
  fun defaultModelEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, ModelLayer.defaultModelEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, ModelLayer.defaultModelEmissiveStrength!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength") }
  }

  @Test
  fun defaultModelEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelEmissiveStrengthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength-transition") }
  }

  @Test
  fun defaultModelFrontCutoffTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelFrontCutoff?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-front-cutoff") }
  }
  // Expression Tests

  @Test
  fun defaultModelFrontCutoffAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelFrontCutoffAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-front-cutoff") }
  }

  @Test
  fun defaultModelFrontCutoffAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    assertEquals("[literal, [0.0, 1.0, 2.0]]", ModelLayer.defaultModelFrontCutoffAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), ModelLayer.defaultModelFrontCutoff!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-front-cutoff") }
  }

  @Test
  fun defaultModelHeightBasedEmissiveStrengthMultiplierTest() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplier?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier") }
  }
  // Expression Tests

  @Test
  fun defaultModelHeightBasedEmissiveStrengthMultiplierAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier") }
  }

  @Test
  fun defaultModelHeightBasedEmissiveStrengthMultiplierAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0, 3.0, 4.0))
    assertEquals("[literal, [0.0, 1.0, 2.0, 3.0, 4.0]]", ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0, 3.0, 4.0), ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplier!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier") }
  }

  @Test
  fun defaultModelHeightBasedEmissiveStrengthMultiplierTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier-transition") }
  }

  @Test
  fun defaultModelOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultModelOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity") }
  }

  @Test
  fun defaultModelOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, ModelLayer.defaultModelOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, ModelLayer.defaultModelOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity") }
  }

  @Test
  fun defaultModelOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity-transition") }
  }

  @Test
  fun defaultModelReceiveShadowsTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelReceiveShadows?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-receive-shadows") }
  }
  // Expression Tests

  @Test
  fun defaultModelReceiveShadowsAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelReceiveShadowsAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-receive-shadows") }
  }

  @Test
  fun defaultModelReceiveShadowsAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", ModelLayer.defaultModelReceiveShadowsAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, ModelLayer.defaultModelReceiveShadows)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-receive-shadows") }
  }

  @Test
  fun defaultModelRotationTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelRotation?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation") }
  }
  // Expression Tests

  @Test
  fun defaultModelRotationAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelRotationAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation") }
  }

  @Test
  fun defaultModelRotationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    assertEquals("[literal, [0.0, 1.0, 2.0]]", ModelLayer.defaultModelRotationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), ModelLayer.defaultModelRotation!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation") }
  }

  @Test
  fun defaultModelRotationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelRotationTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation-transition") }
  }

  @Test
  fun defaultModelRoughnessTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelRoughness?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness") }
  }
  // Expression Tests

  @Test
  fun defaultModelRoughnessAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelRoughnessAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness") }
  }

  @Test
  fun defaultModelRoughnessAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, ModelLayer.defaultModelRoughnessAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, ModelLayer.defaultModelRoughness!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness") }
  }

  @Test
  fun defaultModelRoughnessTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelRoughnessTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness-transition") }
  }

  @Test
  fun defaultModelScaleTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelScale?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale") }
  }
  // Expression Tests

  @Test
  fun defaultModelScaleAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelScaleAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale") }
  }

  @Test
  fun defaultModelScaleAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    assertEquals("[literal, [0.0, 1.0, 2.0]]", ModelLayer.defaultModelScaleAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), ModelLayer.defaultModelScale!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale") }
  }

  @Test
  fun defaultModelScaleTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelScaleTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-transition") }
  }

  @Test
  fun defaultModelScaleModeTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(ModelScaleMode.MAP, ModelLayer.defaultModelScaleMode)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-mode") }
  }
  // Expression Tests

  @Test
  fun defaultModelScaleModeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelScaleModeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-mode") }
  }

  @Test
  fun defaultModelScaleModeAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), ModelLayer.defaultModelScaleModeAsExpression?.toString())
    assertEquals(ModelScaleMode.MAP.value, ModelLayer.defaultModelScaleModeAsExpression.toString())
    assertEquals(ModelScaleMode.MAP, ModelLayer.defaultModelScaleMode)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-mode") }
  }

  @Test
  fun defaultModelTranslationTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), ModelLayer.defaultModelTranslation?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation") }
  }
  // Expression Tests

  @Test
  fun defaultModelTranslationAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelTranslationAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation") }
  }

  @Test
  fun defaultModelTranslationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    assertEquals("[literal, [0.0, 1.0, 2.0]]", ModelLayer.defaultModelTranslationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), ModelLayer.defaultModelTranslation!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation") }
  }

  @Test
  fun defaultModelTranslationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), ModelLayer.defaultModelTranslationTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation-transition") }
  }

  @Test
  fun defaultModelTypeTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("common-3d")

    assertEquals(ModelType.COMMON_3D, ModelLayer.defaultModelType)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-type") }
  }
  // Expression Tests

  @Test
  fun defaultModelTypeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ModelLayer.defaultModelTypeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-type") }
  }

  @Test
  fun defaultModelTypeAsExpressionGetFromLiteral() {
    val value = "common-3d"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), ModelLayer.defaultModelTypeAsExpression?.toString())
    assertEquals(ModelType.COMMON_3D.value, ModelLayer.defaultModelTypeAsExpression.toString())
    assertEquals(ModelType.COMMON_3D, ModelLayer.defaultModelType)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "model-type") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, ModelLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "visibility") }
  }
}

// End of generated file.