// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.StyleInterface
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
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
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
    every { expected.error } returns null

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