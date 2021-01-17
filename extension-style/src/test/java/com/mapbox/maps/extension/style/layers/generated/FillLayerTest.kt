// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import android.graphics.Color
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
class FillLayerTest {
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
    val layer = fillLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=fill}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = fillLayer("id", "source") {}
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
    val layer = fillLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=fill, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = fillLayer("id", "source") {}
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
    val layer = fillLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=fill}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = fillLayer("id", "source") {}
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
  fun fillSortKeySet() {
    val layer = fillLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillSortKey(testValue)
    verify { style.setStyleLayerProperty("id", "fill-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillSortKeyGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillSortKey?.toString())
    verify { style.getStyleLayerProperty("id", "fill-sort-key") }
  }
  // Expression Tests

  @Test
  fun fillSortKeyAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillSortKey(expression)
    verify { style.setStyleLayerProperty("id", "fill-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillSortKeyAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillSortKeyAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-sort-key") }
  }

  @Test
  fun fillSortKeyAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillSortKeyAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-sort-key") }
  }

  @Test
  fun fillSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillSortKey!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-sort-key") }
  }

  @Test
  fun fillAntialiasSet() {
    val layer = fillLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.fillAntialias(testValue)
    verify { style.setStyleLayerProperty("id", "fill-antialias", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun fillAntialiasGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.fillAntialias?.toString())
    verify { style.getStyleLayerProperty("id", "fill-antialias") }
  }
  // Expression Tests

  @Test
  fun fillAntialiasAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillAntialias(expression)
    verify { style.setStyleLayerProperty("id", "fill-antialias", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillAntialiasAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillAntialiasAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-antialias") }
  }

  @Test
  fun fillAntialiasAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillAntialiasAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-antialias") }
  }

  @Test
  fun fillAntialiasAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.fillAntialiasAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.fillAntialias)
    verify { style.getStyleLayerProperty("id", "fill-antialias") }
  }

  @Test
  fun fillColorSet() {
    val layer = fillLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.fillColor(testValue)
    verify { style.setStyleLayerProperty("id", "fill-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun fillColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.fillColor?.toString())
    verify { style.getStyleLayerProperty("id", "fill-color") }
  }
  // Expression Tests

  @Test
  fun fillColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillColor(expression)
    verify { style.setStyleLayerProperty("id", "fill-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-color") }
  }

  @Test
  fun fillColorAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillColorAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-color") }
  }

  @Test
  fun fillColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.fillColor)
    assertEquals(Color.BLACK, layer.fillColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "fill-color") }
  }

  @Test
  fun fillColorAsColorIntSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "fill-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun fillColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.fillColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "fill-color") }
  }

  @Test
  fun fillColorTransitionSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-color-transition") }
  }

  @Test
  fun fillColorTransitionSetDsl() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillOpacitySet() {
    val layer = fillLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "fill-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "fill-opacity") }
  }
  // Expression Tests

  @Test
  fun fillOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillOpacity(expression)
    verify { style.setStyleLayerProperty("id", "fill-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-opacity") }
  }

  @Test
  fun fillOpacityAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-opacity") }
  }

  @Test
  fun fillOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-opacity") }
  }

  @Test
  fun fillOpacityTransitionSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-opacity-transition") }
  }

  @Test
  fun fillOpacityTransitionSetDsl() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillOutlineColorSet() {
    val layer = fillLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.fillOutlineColor(testValue)
    verify { style.setStyleLayerProperty("id", "fill-outline-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun fillOutlineColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.fillOutlineColor?.toString())
    verify { style.getStyleLayerProperty("id", "fill-outline-color") }
  }
  // Expression Tests

  @Test
  fun fillOutlineColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillOutlineColor(expression)
    verify { style.setStyleLayerProperty("id", "fill-outline-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillOutlineColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillOutlineColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-outline-color") }
  }

  @Test
  fun fillOutlineColorAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillOutlineColorAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-outline-color") }
  }

  @Test
  fun fillOutlineColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillOutlineColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.fillOutlineColor)
    assertEquals(Color.BLACK, layer.fillOutlineColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "fill-outline-color") }
  }

  @Test
  fun fillOutlineColorAsColorIntSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillOutlineColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "fill-outline-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun fillOutlineColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.fillOutlineColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "fill-outline-color") }
  }

  @Test
  fun fillOutlineColorTransitionSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillOutlineColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-outline-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillOutlineColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillOutlineColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-outline-color-transition") }
  }

  @Test
  fun fillOutlineColorTransitionSetDsl() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillOutlineColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-outline-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillPatternSet() {
    val layer = fillLayer("id", "source") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.fillPattern(testValue)
    verify { style.setStyleLayerProperty("id", "fill-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun fillPatternGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.fillPattern?.toString())
    verify { style.getStyleLayerProperty("id", "fill-pattern") }
  }
  // Expression Tests

  @Test
  fun fillPatternAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillPattern(expression)
    verify { style.setStyleLayerProperty("id", "fill-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillPatternAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillPatternAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-pattern") }
  }

  @Test
  fun fillPatternAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillPatternAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-pattern") }
  }

  @Test
  fun fillPatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("abc", layer.fillPatternAsExpression.toString())
    assertEquals("abc", layer.fillPattern)
    verify { style.getStyleLayerProperty("id", "fill-pattern") }
  }

  @Test
  fun fillPatternTransitionSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillPatternTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-pattern-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillPatternTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillPatternTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-pattern-transition") }
  }

  @Test
  fun fillPatternTransitionSetDsl() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillPatternTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-pattern-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillTranslateSet() {
    val layer = fillLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.fillTranslate(testValue)
    verify { style.setStyleLayerProperty("id", "fill-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun fillTranslateGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.fillTranslate?.toString())
    verify { style.getStyleLayerProperty("id", "fill-translate") }
  }
  // Expression Tests

  @Test
  fun fillTranslateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillTranslate(expression)
    verify { style.setStyleLayerProperty("id", "fill-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillTranslateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillTranslateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-translate") }
  }

  @Test
  fun fillTranslateAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillTranslateAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-translate") }
  }

  @Test
  fun fillTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.fillTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.fillTranslate!!)
    verify { style.getStyleLayerProperty("id", "fill-translate") }
  }

  @Test
  fun fillTranslateTransitionSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillTranslateTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillTranslateTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillTranslateTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-translate-transition") }
  }

  @Test
  fun fillTranslateTransitionSetDsl() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillTranslateTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }
  @Test
  fun fillTranslateAnchorSet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillTranslateAnchor(FillTranslateAnchor.MAP)
    verify { style.setStyleLayerProperty("id", "fill-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun fillTranslateAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(FillTranslateAnchor.MAP, layer.fillTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "fill-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun fillTranslateAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillTranslateAnchor(expression)
    verify { style.setStyleLayerProperty("id", "fill-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillTranslateAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillTranslateAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-translate-anchor") }
  }

  @Test
  fun fillTranslateAnchorAsExpressionGetNull() {
    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillTranslateAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-translate-anchor") }
  }

  @Test
  fun fillTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.fillTranslateAnchorAsExpression?.toString())
    assertEquals(FillTranslateAnchor.MAP.value, layer.fillTranslateAnchorAsExpression.toString())
    assertEquals(FillTranslateAnchor.MAP, layer.fillTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "fill-translate-anchor") }
  }

  @Test
  fun visibilitySet() {
    val layer = fillLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = fillLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = fillLayer("id", "source") { }
    assertEquals("fill", layer.getType())
  }

  @Test
  fun getFillLayerTest() {
    val value = HashMap<String, Value>()
    value["id"] = Value("id")
    value["type"] = Value("fill")
    value["source"] = Value("source")
    every { style.getStyleLayerProperties("id") } returns valueExpected
    every { valueExpected.error } returns null
    every { valueExpected.value } returns Value(value)
    val layer = style.getLayer("id") as FillLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("fill", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultFillSortKeyTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillLayer.defaultFillSortKey?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-sort-key") }
  }
  // Expression Tests

  @Test
  fun defaultFillSortKeyAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillSortKeyAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-sort-key") }
  }

  @Test
  fun defaultFillSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillLayer.defaultFillSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillLayer.defaultFillSortKey!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-sort-key") }
  }

  @Test
  fun defaultFillAntialiasTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), FillLayer.defaultFillAntialias?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias") }
  }
  // Expression Tests

  @Test
  fun defaultFillAntialiasAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillAntialiasAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias") }
  }

  @Test
  fun defaultFillAntialiasAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", FillLayer.defaultFillAntialiasAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, FillLayer.defaultFillAntialias)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias") }
  }

  @Test
  fun defaultFillColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), FillLayer.defaultFillColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color") }
  }
  // Expression Tests

  @Test
  fun defaultFillColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color") }
  }

  @Test
  fun defaultFillColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), FillLayer.defaultFillColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", FillLayer.defaultFillColor)
    assertEquals(Color.BLACK, FillLayer.defaultFillColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color") }
  }

  @Test
  fun defaultFillColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, FillLayer.defaultFillColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color") }
  }

  @Test
  fun defaultFillColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillLayer.defaultFillColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color-transition") }
  }

  @Test
  fun defaultFillOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillLayer.defaultFillOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultFillOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity") }
  }

  @Test
  fun defaultFillOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillLayer.defaultFillOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillLayer.defaultFillOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity") }
  }

  @Test
  fun defaultFillOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillLayer.defaultFillOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity-transition") }
  }

  @Test
  fun defaultFillOutlineColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), FillLayer.defaultFillOutlineColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color") }
  }
  // Expression Tests

  @Test
  fun defaultFillOutlineColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillOutlineColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color") }
  }

  @Test
  fun defaultFillOutlineColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), FillLayer.defaultFillOutlineColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", FillLayer.defaultFillOutlineColor)
    assertEquals(Color.BLACK, FillLayer.defaultFillOutlineColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color") }
  }

  @Test
  fun defaultFillOutlineColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, FillLayer.defaultFillOutlineColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color") }
  }

  @Test
  fun defaultFillOutlineColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillLayer.defaultFillOutlineColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color-transition") }
  }

  @Test
  fun defaultFillPatternTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), FillLayer.defaultFillPattern?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern") }
  }
  // Expression Tests

  @Test
  fun defaultFillPatternAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillPatternAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern") }
  }

  @Test
  fun defaultFillPatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", FillLayer.defaultFillPatternAsExpression.toString())
    assertEquals("abc", FillLayer.defaultFillPattern)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern") }
  }

  @Test
  fun defaultFillPatternTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillLayer.defaultFillPatternTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern-transition") }
  }

  @Test
  fun defaultFillTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), FillLayer.defaultFillTranslate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate") }
  }
  // Expression Tests

  @Test
  fun defaultFillTranslateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillTranslateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate") }
  }

  @Test
  fun defaultFillTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", FillLayer.defaultFillTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), FillLayer.defaultFillTranslate!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate") }
  }

  @Test
  fun defaultFillTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillLayer.defaultFillTranslateTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-transition") }
  }

  @Test
  fun defaultFillTranslateAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(FillTranslateAnchor.MAP, FillLayer.defaultFillTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultFillTranslateAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillLayer.defaultFillTranslateAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor") }
  }

  @Test
  fun defaultFillTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), FillLayer.defaultFillTranslateAnchorAsExpression?.toString())
    assertEquals(FillTranslateAnchor.MAP.value, FillLayer.defaultFillTranslateAnchorAsExpression.toString())
    assertEquals(FillTranslateAnchor.MAP, FillLayer.defaultFillTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, FillLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill", "visibility") }
  }
}

// End of generated file.