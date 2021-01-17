// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StyleManagerInterface
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

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class ModelLayerTest {
  private val style = mockk<StyleManagerInterface>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)
  private val valueExpected = mockk<Expected<Value, String>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()
  private val valueSlot = slot<Value>()

  @Before
  fun prepareTest() {
    every { style.getStyleLayerProperty(any(), any()) } returns styleProperty
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.setStyleLayerProperty(any(), any(), any()) } returns expected
    every { style.addStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperties(any(), any()) } returns expected
    every { expected.error } returns null

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleLayerPropertyDefaultValue(any(), any()) } returns styleProperty
  }

  @After
  fun cleanup() {
    unmockkAll()
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
    val layer = symbolLayer("id", "source") {}
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

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.filter?.toString())
    verify { style.getStyleLayerProperty("id", "filter") }
  }
  // Property getters and setters

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
  fun getType() {
    val layer = modelLayer("id", "source") { }
    assertEquals("model", layer.getType())
  }

  @Test
  fun getModelLayerTest() {
    val value = HashMap<String, Value>()
    value["id"] = Value("id")
    value["type"] = Value("model")
    value["source"] = Value("source")
    every { style.getStyleLayerProperties("id") } returns valueExpected
    every { valueExpected.error } returns null
    every { valueExpected.value } returns Value(value)
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
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, ModelLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("model", "visibility") }
  }
}

// End of generated file.