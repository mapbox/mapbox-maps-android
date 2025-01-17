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
class BackgroundLayerTest {
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
  // Property getters and setters

  @Test
  fun backgroundColorSet() {
    val layer = backgroundLayer("id") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.backgroundColor(testValue)
    verify { style.setStyleLayerProperty("id", "background-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun backgroundColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.backgroundColor?.toString())
    verify { style.getStyleLayerProperty("id", "background-color") }
  }
  // Expression Tests

  @Test
  fun backgroundColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundColor(expression)
    verify { style.setStyleLayerProperty("id", "background-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun backgroundColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.backgroundColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "background-color") }
  }

  @Test
  fun backgroundColorAsExpressionGetNull() {
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.backgroundColorAsExpression)
    verify { style.getStyleLayerProperty("id", "background-color") }
  }

  @Test
  fun backgroundColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.backgroundColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.backgroundColor)
    assertEquals(Color.BLACK, layer.backgroundColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "background-color") }
  }

  @Test
  fun backgroundColorAsColorIntSet() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "background-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun backgroundColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.backgroundColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "background-color") }
  }

  @Test
  fun backgroundColorTransitionSet() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "background-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun backgroundColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.backgroundColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "background-color-transition") }
  }

  @Test
  fun backgroundColorTransitionSetDsl() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "background-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun backgroundEmissiveStrengthSet() {
    val layer = backgroundLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.backgroundEmissiveStrength(testValue)
    verify { style.setStyleLayerProperty("id", "background-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun backgroundEmissiveStrengthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.backgroundEmissiveStrength?.toString())
    verify { style.getStyleLayerProperty("id", "background-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun backgroundEmissiveStrengthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundEmissiveStrength(expression)
    verify { style.setStyleLayerProperty("id", "background-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun backgroundEmissiveStrengthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.backgroundEmissiveStrengthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "background-emissive-strength") }
  }

  @Test
  fun backgroundEmissiveStrengthAsExpressionGetNull() {
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.backgroundEmissiveStrengthAsExpression)
    verify { style.getStyleLayerProperty("id", "background-emissive-strength") }
  }

  @Test
  fun backgroundEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.backgroundEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.backgroundEmissiveStrength!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "background-emissive-strength") }
  }

  @Test
  fun backgroundEmissiveStrengthTransitionSet() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundEmissiveStrengthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "background-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun backgroundEmissiveStrengthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.backgroundEmissiveStrengthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "background-emissive-strength-transition") }
  }

  @Test
  fun backgroundEmissiveStrengthTransitionSetDsl() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundEmissiveStrengthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "background-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun backgroundOpacitySet() {
    val layer = backgroundLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.backgroundOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "background-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun backgroundOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.backgroundOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "background-opacity") }
  }
  // Expression Tests

  @Test
  fun backgroundOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundOpacity(expression)
    verify { style.setStyleLayerProperty("id", "background-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun backgroundOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.backgroundOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "background-opacity") }
  }

  @Test
  fun backgroundOpacityAsExpressionGetNull() {
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.backgroundOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "background-opacity") }
  }

  @Test
  fun backgroundOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.backgroundOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.backgroundOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "background-opacity") }
  }

  @Test
  fun backgroundOpacityTransitionSet() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "background-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun backgroundOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.backgroundOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "background-opacity-transition") }
  }

  @Test
  fun backgroundOpacityTransitionSetDsl() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "background-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun backgroundPatternSet() {
    val layer = backgroundLayer("id") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.backgroundPattern(testValue)
    verify { style.setStyleLayerProperty("id", "background-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun backgroundPatternGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.backgroundPattern?.toString())
    verify { style.getStyleLayerProperty("id", "background-pattern") }
  }
  // Expression Tests

  @Test
  fun backgroundPatternAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundPattern(expression)
    verify { style.setStyleLayerProperty("id", "background-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun backgroundPatternAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.backgroundPatternAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "background-pattern") }
  }

  @Test
  fun backgroundPatternAsExpressionGetNull() {
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.backgroundPatternAsExpression)
    verify { style.getStyleLayerProperty("id", "background-pattern") }
  }

  @Test
  fun backgroundPatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals("abc", layer.backgroundPatternAsExpression.toString())
    assertEquals("abc", layer.backgroundPattern)
    verify { style.getStyleLayerProperty("id", "background-pattern") }
  }

  @Test
  fun backgroundPitchAlignmentSet() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundPitchAlignment(BackgroundPitchAlignment.MAP)
    verify { style.setStyleLayerProperty("id", "background-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun backgroundPitchAlignmentGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(BackgroundPitchAlignment.MAP, layer.backgroundPitchAlignment)
    verify { style.getStyleLayerProperty("id", "background-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun backgroundPitchAlignmentAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.backgroundPitchAlignment(expression)
    verify { style.setStyleLayerProperty("id", "background-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun backgroundPitchAlignmentAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.backgroundPitchAlignmentAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "background-pitch-alignment") }
  }

  @Test
  fun backgroundPitchAlignmentAsExpressionGetNull() {
    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.backgroundPitchAlignmentAsExpression)
    verify { style.getStyleLayerProperty("id", "background-pitch-alignment") }
  }

  @Test
  fun backgroundPitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.backgroundPitchAlignmentAsExpression?.toString())
    assertEquals(BackgroundPitchAlignment.MAP.value, layer.backgroundPitchAlignmentAsExpression.toString())
    assertEquals(BackgroundPitchAlignment.MAP, layer.backgroundPitchAlignment)
    verify { style.getStyleLayerProperty("id", "background-pitch-alignment") }
  }

  @Test
  fun visibilitySet() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun visibilityAsExpressionSet() {
    val layer = backgroundLayer("id") {}
    layer.bindTo(style)
    layer.visibility(literal("none"))
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityAsExpressionGet() {
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns literal("none")

    val layer = backgroundLayer("id") { }
    layer.bindTo(style)
    assertEquals(literal("none"), layer.visibilityAsExpression)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = backgroundLayer("id") { }
    assertEquals("background", layer.getType())
  }

  @Test
  fun getBackgroundLayerTest() {

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("background"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as BackgroundLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("background", layer.getType())
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultBackgroundColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), BackgroundLayer.defaultBackgroundColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-color") }
  }
  // Expression Tests

  @Test
  fun defaultBackgroundColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), BackgroundLayer.defaultBackgroundColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-color") }
  }

  @Test
  fun defaultBackgroundColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), BackgroundLayer.defaultBackgroundColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", BackgroundLayer.defaultBackgroundColor)
    assertEquals(Color.BLACK, BackgroundLayer.defaultBackgroundColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-color") }
  }

  @Test
  fun defaultBackgroundColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, BackgroundLayer.defaultBackgroundColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-color") }
  }

  @Test
  fun defaultBackgroundColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), BackgroundLayer.defaultBackgroundColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-color-transition") }
  }

  @Test
  fun defaultBackgroundEmissiveStrengthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), BackgroundLayer.defaultBackgroundEmissiveStrength?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun defaultBackgroundEmissiveStrengthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), BackgroundLayer.defaultBackgroundEmissiveStrengthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-emissive-strength") }
  }

  @Test
  fun defaultBackgroundEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, BackgroundLayer.defaultBackgroundEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, BackgroundLayer.defaultBackgroundEmissiveStrength!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-emissive-strength") }
  }

  @Test
  fun defaultBackgroundEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), BackgroundLayer.defaultBackgroundEmissiveStrengthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-emissive-strength-transition") }
  }

  @Test
  fun defaultBackgroundOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), BackgroundLayer.defaultBackgroundOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultBackgroundOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), BackgroundLayer.defaultBackgroundOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-opacity") }
  }

  @Test
  fun defaultBackgroundOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, BackgroundLayer.defaultBackgroundOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, BackgroundLayer.defaultBackgroundOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-opacity") }
  }

  @Test
  fun defaultBackgroundOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), BackgroundLayer.defaultBackgroundOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-opacity-transition") }
  }

  @Test
  fun defaultBackgroundPatternTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), BackgroundLayer.defaultBackgroundPattern?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pattern") }
  }
  // Expression Tests

  @Test
  fun defaultBackgroundPatternAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), BackgroundLayer.defaultBackgroundPatternAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pattern") }
  }

  @Test
  fun defaultBackgroundPatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", BackgroundLayer.defaultBackgroundPatternAsExpression.toString())
    assertEquals("abc", BackgroundLayer.defaultBackgroundPattern)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pattern") }
  }

  @Test
  fun defaultBackgroundPitchAlignmentTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(BackgroundPitchAlignment.MAP, BackgroundLayer.defaultBackgroundPitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun defaultBackgroundPitchAlignmentAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), BackgroundLayer.defaultBackgroundPitchAlignmentAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pitch-alignment") }
  }

  @Test
  fun defaultBackgroundPitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), BackgroundLayer.defaultBackgroundPitchAlignmentAsExpression?.toString())
    assertEquals(BackgroundPitchAlignment.MAP.value, BackgroundLayer.defaultBackgroundPitchAlignmentAsExpression.toString())
    assertEquals(BackgroundPitchAlignment.MAP, BackgroundLayer.defaultBackgroundPitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pitch-alignment") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, BackgroundLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("background", "visibility") }
  }
}

// End of generated file.