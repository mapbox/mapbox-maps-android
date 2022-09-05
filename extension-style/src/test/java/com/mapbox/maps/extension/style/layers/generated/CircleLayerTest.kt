// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import android.graphics.Color
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
class CircleLayerTest {
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
    val layer = circleLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=circle}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = circleLayer("id", "source") {}
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
    val layer = circleLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=circle, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = circleLayer("id", "source") {}
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
    val layer = circleLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=circle}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = circleLayer("id", "source") {}
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
  fun circleSortKeySet() {
    val layer = circleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.circleSortKey(testValue)
    verify { style.setStyleLayerProperty("id", "circle-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun circleSortKeyGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.circleSortKey?.toString())
    verify { style.getStyleLayerProperty("id", "circle-sort-key") }
  }
  // Expression Tests

  @Test
  fun circleSortKeyAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleSortKey(expression)
    verify { style.setStyleLayerProperty("id", "circle-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleSortKeyAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleSortKeyAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-sort-key") }
  }

  @Test
  fun circleSortKeyAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleSortKeyAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-sort-key") }
  }

  @Test
  fun circleSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.circleSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.circleSortKey!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "circle-sort-key") }
  }

  @Test
  fun circleBlurSet() {
    val layer = circleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.circleBlur(testValue)
    verify { style.setStyleLayerProperty("id", "circle-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun circleBlurGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.circleBlur?.toString())
    verify { style.getStyleLayerProperty("id", "circle-blur") }
  }
  // Expression Tests

  @Test
  fun circleBlurAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleBlur(expression)
    verify { style.setStyleLayerProperty("id", "circle-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleBlurAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleBlurAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-blur") }
  }

  @Test
  fun circleBlurAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleBlurAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-blur") }
  }

  @Test
  fun circleBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.circleBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.circleBlur!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "circle-blur") }
  }

  @Test
  fun circleBlurTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleBlurTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleBlurTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleBlurTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-blur-transition") }
  }

  @Test
  fun circleBlurTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleBlurTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleColorSet() {
    val layer = circleLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.circleColor(testValue)
    verify { style.setStyleLayerProperty("id", "circle-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun circleColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.circleColor?.toString())
    verify { style.getStyleLayerProperty("id", "circle-color") }
  }
  // Expression Tests

  @Test
  fun circleColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleColor(expression)
    verify { style.setStyleLayerProperty("id", "circle-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-color") }
  }

  @Test
  fun circleColorAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleColorAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-color") }
  }

  @Test
  fun circleColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.circleColor)
    assertEquals(Color.BLACK, layer.circleColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "circle-color") }
  }

  @Test
  fun circleColorAsColorIntSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "circle-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun circleColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.circleColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "circle-color") }
  }

  @Test
  fun circleColorTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-color-transition") }
  }

  @Test
  fun circleColorTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleOpacitySet() {
    val layer = circleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.circleOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "circle-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun circleOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.circleOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "circle-opacity") }
  }
  // Expression Tests

  @Test
  fun circleOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleOpacity(expression)
    verify { style.setStyleLayerProperty("id", "circle-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-opacity") }
  }

  @Test
  fun circleOpacityAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-opacity") }
  }

  @Test
  fun circleOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.circleOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.circleOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "circle-opacity") }
  }

  @Test
  fun circleOpacityTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-opacity-transition") }
  }

  @Test
  fun circleOpacityTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circlePitchAlignmentSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circlePitchAlignment(CirclePitchAlignment.MAP)
    verify { style.setStyleLayerProperty("id", "circle-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun circlePitchAlignmentGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(CirclePitchAlignment.MAP, layer.circlePitchAlignment)
    verify { style.getStyleLayerProperty("id", "circle-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun circlePitchAlignmentAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circlePitchAlignment(expression)
    verify { style.setStyleLayerProperty("id", "circle-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circlePitchAlignmentAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circlePitchAlignmentAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-pitch-alignment") }
  }

  @Test
  fun circlePitchAlignmentAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circlePitchAlignmentAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-pitch-alignment") }
  }

  @Test
  fun circlePitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.circlePitchAlignmentAsExpression?.toString())
    assertEquals(CirclePitchAlignment.MAP.value, layer.circlePitchAlignmentAsExpression.toString())
    assertEquals(CirclePitchAlignment.MAP, layer.circlePitchAlignment)
    verify { style.getStyleLayerProperty("id", "circle-pitch-alignment") }
  }

  @Test
  fun circlePitchScaleSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circlePitchScale(CirclePitchScale.MAP)
    verify { style.setStyleLayerProperty("id", "circle-pitch-scale", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun circlePitchScaleGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(CirclePitchScale.MAP, layer.circlePitchScale)
    verify { style.getStyleLayerProperty("id", "circle-pitch-scale") }
  }
  // Expression Tests

  @Test
  fun circlePitchScaleAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circlePitchScale(expression)
    verify { style.setStyleLayerProperty("id", "circle-pitch-scale", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circlePitchScaleAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circlePitchScaleAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-pitch-scale") }
  }

  @Test
  fun circlePitchScaleAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circlePitchScaleAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-pitch-scale") }
  }

  @Test
  fun circlePitchScaleAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.circlePitchScaleAsExpression?.toString())
    assertEquals(CirclePitchScale.MAP.value, layer.circlePitchScaleAsExpression.toString())
    assertEquals(CirclePitchScale.MAP, layer.circlePitchScale)
    verify { style.getStyleLayerProperty("id", "circle-pitch-scale") }
  }

  @Test
  fun circleRadiusSet() {
    val layer = circleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.circleRadius(testValue)
    verify { style.setStyleLayerProperty("id", "circle-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun circleRadiusGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.circleRadius?.toString())
    verify { style.getStyleLayerProperty("id", "circle-radius") }
  }
  // Expression Tests

  @Test
  fun circleRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleRadius(expression)
    verify { style.setStyleLayerProperty("id", "circle-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleRadiusAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-radius") }
  }

  @Test
  fun circleRadiusAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleRadiusAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-radius") }
  }

  @Test
  fun circleRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.circleRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.circleRadius!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "circle-radius") }
  }

  @Test
  fun circleRadiusTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleRadiusTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleRadiusTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleRadiusTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-radius-transition") }
  }

  @Test
  fun circleRadiusTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleRadiusTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleStrokeColorSet() {
    val layer = circleLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.circleStrokeColor(testValue)
    verify { style.setStyleLayerProperty("id", "circle-stroke-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun circleStrokeColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.circleStrokeColor?.toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-color") }
  }
  // Expression Tests

  @Test
  fun circleStrokeColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeColor(expression)
    verify { style.setStyleLayerProperty("id", "circle-stroke-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleStrokeColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleStrokeColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-color") }
  }

  @Test
  fun circleStrokeColorAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleStrokeColorAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-stroke-color") }
  }

  @Test
  fun circleStrokeColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleStrokeColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.circleStrokeColor)
    assertEquals(Color.BLACK, layer.circleStrokeColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "circle-stroke-color") }
  }

  @Test
  fun circleStrokeColorAsColorIntSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "circle-stroke-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun circleStrokeColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.circleStrokeColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "circle-stroke-color") }
  }

  @Test
  fun circleStrokeColorTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-stroke-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleStrokeColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleStrokeColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-color-transition") }
  }

  @Test
  fun circleStrokeColorTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-stroke-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleStrokeOpacitySet() {
    val layer = circleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.circleStrokeOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "circle-stroke-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun circleStrokeOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.circleStrokeOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-opacity") }
  }
  // Expression Tests

  @Test
  fun circleStrokeOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeOpacity(expression)
    verify { style.setStyleLayerProperty("id", "circle-stroke-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleStrokeOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleStrokeOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-opacity") }
  }

  @Test
  fun circleStrokeOpacityAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleStrokeOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-stroke-opacity") }
  }

  @Test
  fun circleStrokeOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.circleStrokeOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.circleStrokeOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "circle-stroke-opacity") }
  }

  @Test
  fun circleStrokeOpacityTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-stroke-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleStrokeOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleStrokeOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-opacity-transition") }
  }

  @Test
  fun circleStrokeOpacityTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-stroke-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleStrokeWidthSet() {
    val layer = circleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.circleStrokeWidth(testValue)
    verify { style.setStyleLayerProperty("id", "circle-stroke-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun circleStrokeWidthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.circleStrokeWidth?.toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-width") }
  }
  // Expression Tests

  @Test
  fun circleStrokeWidthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeWidth(expression)
    verify { style.setStyleLayerProperty("id", "circle-stroke-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleStrokeWidthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleStrokeWidthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-width") }
  }

  @Test
  fun circleStrokeWidthAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleStrokeWidthAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-stroke-width") }
  }

  @Test
  fun circleStrokeWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.circleStrokeWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.circleStrokeWidth!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "circle-stroke-width") }
  }

  @Test
  fun circleStrokeWidthTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeWidthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-stroke-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleStrokeWidthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleStrokeWidthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-stroke-width-transition") }
  }

  @Test
  fun circleStrokeWidthTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleStrokeWidthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-stroke-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleTranslateSet() {
    val layer = circleLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.circleTranslate(testValue)
    verify { style.setStyleLayerProperty("id", "circle-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun circleTranslateGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.circleTranslate?.toString())
    verify { style.getStyleLayerProperty("id", "circle-translate") }
  }
  // Expression Tests

  @Test
  fun circleTranslateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleTranslate(expression)
    verify { style.setStyleLayerProperty("id", "circle-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleTranslateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleTranslateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-translate") }
  }

  @Test
  fun circleTranslateAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleTranslateAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-translate") }
  }

  @Test
  fun circleTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.circleTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.circleTranslate!!)
    verify { style.getStyleLayerProperty("id", "circle-translate") }
  }

  @Test
  fun circleTranslateTransitionSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleTranslateTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "circle-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleTranslateTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.circleTranslateTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "circle-translate-transition") }
  }

  @Test
  fun circleTranslateTransitionSetDsl() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleTranslateTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "circle-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun circleTranslateAnchorSet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleTranslateAnchor(CircleTranslateAnchor.MAP)
    verify { style.setStyleLayerProperty("id", "circle-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun circleTranslateAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(CircleTranslateAnchor.MAP, layer.circleTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "circle-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun circleTranslateAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.circleTranslateAnchor(expression)
    verify { style.setStyleLayerProperty("id", "circle-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun circleTranslateAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.circleTranslateAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "circle-translate-anchor") }
  }

  @Test
  fun circleTranslateAnchorAsExpressionGetNull() {
    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.circleTranslateAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "circle-translate-anchor") }
  }

  @Test
  fun circleTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.circleTranslateAnchorAsExpression?.toString())
    assertEquals(CircleTranslateAnchor.MAP.value, layer.circleTranslateAnchorAsExpression.toString())
    assertEquals(CircleTranslateAnchor.MAP, layer.circleTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "circle-translate-anchor") }
  }

  @Test
  fun visibilitySet() {
    val layer = circleLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = circleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = circleLayer("id", "source") { }
    assertEquals("circle", layer.getType())
  }

  @Test
  fun getCircleLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("circle"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as CircleLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("circle", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultCircleSortKeyTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleSortKey?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-sort-key") }
  }
  // Expression Tests

  @Test
  fun defaultCircleSortKeyAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleSortKeyAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-sort-key") }
  }

  @Test
  fun defaultCircleSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, CircleLayer.defaultCircleSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, CircleLayer.defaultCircleSortKey!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-sort-key") }
  }

  @Test
  fun defaultCircleBlurTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleBlur?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur") }
  }
  // Expression Tests

  @Test
  fun defaultCircleBlurAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleBlurAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur") }
  }

  @Test
  fun defaultCircleBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, CircleLayer.defaultCircleBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, CircleLayer.defaultCircleBlur!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur") }
  }

  @Test
  fun defaultCircleBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleBlurTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur-transition") }
  }

  @Test
  fun defaultCircleColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-color") }
  }
  // Expression Tests

  @Test
  fun defaultCircleColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-color") }
  }

  @Test
  fun defaultCircleColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), CircleLayer.defaultCircleColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", CircleLayer.defaultCircleColor)
    assertEquals(Color.BLACK, CircleLayer.defaultCircleColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-color") }
  }

  @Test
  fun defaultCircleColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, CircleLayer.defaultCircleColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-color") }
  }

  @Test
  fun defaultCircleColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-color-transition") }
  }

  @Test
  fun defaultCircleOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultCircleOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity") }
  }

  @Test
  fun defaultCircleOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, CircleLayer.defaultCircleOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, CircleLayer.defaultCircleOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity") }
  }

  @Test
  fun defaultCircleOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity-transition") }
  }

  @Test
  fun defaultCirclePitchAlignmentTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(CirclePitchAlignment.MAP, CircleLayer.defaultCirclePitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun defaultCirclePitchAlignmentAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCirclePitchAlignmentAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment") }
  }

  @Test
  fun defaultCirclePitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), CircleLayer.defaultCirclePitchAlignmentAsExpression?.toString())
    assertEquals(CirclePitchAlignment.MAP.value, CircleLayer.defaultCirclePitchAlignmentAsExpression.toString())
    assertEquals(CirclePitchAlignment.MAP, CircleLayer.defaultCirclePitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment") }
  }

  @Test
  fun defaultCirclePitchScaleTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(CirclePitchScale.MAP, CircleLayer.defaultCirclePitchScale)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale") }
  }
  // Expression Tests

  @Test
  fun defaultCirclePitchScaleAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCirclePitchScaleAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale") }
  }

  @Test
  fun defaultCirclePitchScaleAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), CircleLayer.defaultCirclePitchScaleAsExpression?.toString())
    assertEquals(CirclePitchScale.MAP.value, CircleLayer.defaultCirclePitchScaleAsExpression.toString())
    assertEquals(CirclePitchScale.MAP, CircleLayer.defaultCirclePitchScale)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale") }
  }

  @Test
  fun defaultCircleRadiusTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleRadius?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius") }
  }
  // Expression Tests

  @Test
  fun defaultCircleRadiusAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleRadiusAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius") }
  }

  @Test
  fun defaultCircleRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, CircleLayer.defaultCircleRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, CircleLayer.defaultCircleRadius!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius") }
  }

  @Test
  fun defaultCircleRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleRadiusTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius-transition") }
  }

  @Test
  fun defaultCircleStrokeColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleStrokeColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-color") }
  }
  // Expression Tests

  @Test
  fun defaultCircleStrokeColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleStrokeColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-color") }
  }

  @Test
  fun defaultCircleStrokeColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), CircleLayer.defaultCircleStrokeColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", CircleLayer.defaultCircleStrokeColor)
    assertEquals(Color.BLACK, CircleLayer.defaultCircleStrokeColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-color") }
  }

  @Test
  fun defaultCircleStrokeColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, CircleLayer.defaultCircleStrokeColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-color") }
  }

  @Test
  fun defaultCircleStrokeColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleStrokeColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-color-transition") }
  }

  @Test
  fun defaultCircleStrokeOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleStrokeOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultCircleStrokeOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleStrokeOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity") }
  }

  @Test
  fun defaultCircleStrokeOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, CircleLayer.defaultCircleStrokeOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, CircleLayer.defaultCircleStrokeOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity") }
  }

  @Test
  fun defaultCircleStrokeOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleStrokeOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity-transition") }
  }

  @Test
  fun defaultCircleStrokeWidthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleStrokeWidth?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width") }
  }
  // Expression Tests

  @Test
  fun defaultCircleStrokeWidthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleStrokeWidthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width") }
  }

  @Test
  fun defaultCircleStrokeWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, CircleLayer.defaultCircleStrokeWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, CircleLayer.defaultCircleStrokeWidth!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width") }
  }

  @Test
  fun defaultCircleStrokeWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleStrokeWidthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width-transition") }
  }

  @Test
  fun defaultCircleTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), CircleLayer.defaultCircleTranslate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate") }
  }
  // Expression Tests

  @Test
  fun defaultCircleTranslateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleTranslateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate") }
  }

  @Test
  fun defaultCircleTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", CircleLayer.defaultCircleTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), CircleLayer.defaultCircleTranslate!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate") }
  }

  @Test
  fun defaultCircleTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), CircleLayer.defaultCircleTranslateTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-transition") }
  }

  @Test
  fun defaultCircleTranslateAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(CircleTranslateAnchor.MAP, CircleLayer.defaultCircleTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultCircleTranslateAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), CircleLayer.defaultCircleTranslateAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor") }
  }

  @Test
  fun defaultCircleTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), CircleLayer.defaultCircleTranslateAnchorAsExpression?.toString())
    assertEquals(CircleTranslateAnchor.MAP.value, CircleLayer.defaultCircleTranslateAnchorAsExpression.toString())
    assertEquals(CircleTranslateAnchor.MAP, CircleLayer.defaultCircleTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, CircleLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("circle", "visibility") }
  }
}

// End of generated file.