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
class HillshadeLayerTest {
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
    val layer = hillshadeLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=hillshade}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = hillshadeLayer("id", "source") {}
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
    val layer = hillshadeLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=hillshade, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = hillshadeLayer("id", "source") {}
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
    val layer = hillshadeLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=hillshade}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.maxZoom(12.0)
    verify { style.setStyleLayerProperty("id", "maxzoom", capture(valueSlot)) }
    assertEquals(
      "12.0",
      valueSlot.captured.toString()
    )
  }
  // Property getters and setters

  @Test
  fun hillshadeAccentColorSet() {
    val layer = hillshadeLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.hillshadeAccentColor(testValue)
    verify { style.setStyleLayerProperty("id", "hillshade-accent-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun hillshadeAccentColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.hillshadeAccentColor?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-accent-color") }
  }
  // Expression Tests

  @Test
  fun hillshadeAccentColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeAccentColor(expression)
    verify { style.setStyleLayerProperty("id", "hillshade-accent-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun hillshadeAccentColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeAccentColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-accent-color") }
  }

  @Test
  fun hillshadeAccentColorAsExpressionGetNull() {
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.hillshadeAccentColorAsExpression)
    verify { style.getStyleLayerProperty("id", "hillshade-accent-color") }
  }

  @Test
  fun hillshadeAccentColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeAccentColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.hillshadeAccentColor)
    assertEquals(Color.BLACK, layer.hillshadeAccentColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "hillshade-accent-color") }
  }

  @Test
  fun hillshadeAccentColorAsColorIntSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeAccentColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "hillshade-accent-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun hillshadeAccentColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.hillshadeAccentColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "hillshade-accent-color") }
  }

  @Test
  fun hillshadeAccentColorTransitionSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeAccentColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "hillshade-accent-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun hillshadeAccentColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.hillshadeAccentColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "hillshade-accent-color-transition") }
  }

  @Test
  fun hillshadeAccentColorTransitionSetDsl() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeAccentColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "hillshade-accent-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun hillshadeExaggerationSet() {
    val layer = hillshadeLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.hillshadeExaggeration(testValue)
    verify { style.setStyleLayerProperty("id", "hillshade-exaggeration", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun hillshadeExaggerationGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.hillshadeExaggeration?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-exaggeration") }
  }
  // Expression Tests

  @Test
  fun hillshadeExaggerationAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeExaggeration(expression)
    verify { style.setStyleLayerProperty("id", "hillshade-exaggeration", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun hillshadeExaggerationAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeExaggerationAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-exaggeration") }
  }

  @Test
  fun hillshadeExaggerationAsExpressionGetNull() {
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.hillshadeExaggerationAsExpression)
    verify { style.getStyleLayerProperty("id", "hillshade-exaggeration") }
  }

  @Test
  fun hillshadeExaggerationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.hillshadeExaggerationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.hillshadeExaggeration!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "hillshade-exaggeration") }
  }

  @Test
  fun hillshadeExaggerationTransitionSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeExaggerationTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "hillshade-exaggeration-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun hillshadeExaggerationTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.hillshadeExaggerationTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "hillshade-exaggeration-transition") }
  }

  @Test
  fun hillshadeExaggerationTransitionSetDsl() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeExaggerationTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "hillshade-exaggeration-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun hillshadeHighlightColorSet() {
    val layer = hillshadeLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.hillshadeHighlightColor(testValue)
    verify { style.setStyleLayerProperty("id", "hillshade-highlight-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun hillshadeHighlightColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.hillshadeHighlightColor?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-highlight-color") }
  }
  // Expression Tests

  @Test
  fun hillshadeHighlightColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeHighlightColor(expression)
    verify { style.setStyleLayerProperty("id", "hillshade-highlight-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun hillshadeHighlightColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeHighlightColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-highlight-color") }
  }

  @Test
  fun hillshadeHighlightColorAsExpressionGetNull() {
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.hillshadeHighlightColorAsExpression)
    verify { style.getStyleLayerProperty("id", "hillshade-highlight-color") }
  }

  @Test
  fun hillshadeHighlightColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeHighlightColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.hillshadeHighlightColor)
    assertEquals(Color.BLACK, layer.hillshadeHighlightColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "hillshade-highlight-color") }
  }

  @Test
  fun hillshadeHighlightColorAsColorIntSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeHighlightColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "hillshade-highlight-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun hillshadeHighlightColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.hillshadeHighlightColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "hillshade-highlight-color") }
  }

  @Test
  fun hillshadeHighlightColorTransitionSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeHighlightColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "hillshade-highlight-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun hillshadeHighlightColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.hillshadeHighlightColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "hillshade-highlight-color-transition") }
  }

  @Test
  fun hillshadeHighlightColorTransitionSetDsl() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeHighlightColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "hillshade-highlight-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun hillshadeIlluminationAnchorSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeIlluminationAnchor(HillshadeIlluminationAnchor.MAP)
    verify { style.setStyleLayerProperty("id", "hillshade-illumination-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun hillshadeIlluminationAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(HillshadeIlluminationAnchor.MAP, layer.hillshadeIlluminationAnchor)
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-anchor") }
  }
  // Expression Tests

  @Test
  fun hillshadeIlluminationAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeIlluminationAnchor(expression)
    verify { style.setStyleLayerProperty("id", "hillshade-illumination-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun hillshadeIlluminationAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeIlluminationAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-anchor") }
  }

  @Test
  fun hillshadeIlluminationAnchorAsExpressionGetNull() {
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.hillshadeIlluminationAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-anchor") }
  }

  @Test
  fun hillshadeIlluminationAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.hillshadeIlluminationAnchorAsExpression?.toString())
    assertEquals(HillshadeIlluminationAnchor.MAP.value, layer.hillshadeIlluminationAnchorAsExpression.toString())
    assertEquals(HillshadeIlluminationAnchor.MAP, layer.hillshadeIlluminationAnchor)
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-anchor") }
  }

  @Test
  fun hillshadeIlluminationDirectionSet() {
    val layer = hillshadeLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.hillshadeIlluminationDirection(testValue)
    verify { style.setStyleLayerProperty("id", "hillshade-illumination-direction", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun hillshadeIlluminationDirectionGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.hillshadeIlluminationDirection?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-direction") }
  }
  // Expression Tests

  @Test
  fun hillshadeIlluminationDirectionAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeIlluminationDirection(expression)
    verify { style.setStyleLayerProperty("id", "hillshade-illumination-direction", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun hillshadeIlluminationDirectionAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeIlluminationDirectionAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-direction") }
  }

  @Test
  fun hillshadeIlluminationDirectionAsExpressionGetNull() {
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.hillshadeIlluminationDirectionAsExpression)
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-direction") }
  }

  @Test
  fun hillshadeIlluminationDirectionAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.hillshadeIlluminationDirectionAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.hillshadeIlluminationDirection!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "hillshade-illumination-direction") }
  }

  @Test
  fun hillshadeShadowColorSet() {
    val layer = hillshadeLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.hillshadeShadowColor(testValue)
    verify { style.setStyleLayerProperty("id", "hillshade-shadow-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun hillshadeShadowColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.hillshadeShadowColor?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-shadow-color") }
  }
  // Expression Tests

  @Test
  fun hillshadeShadowColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeShadowColor(expression)
    verify { style.setStyleLayerProperty("id", "hillshade-shadow-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun hillshadeShadowColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeShadowColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "hillshade-shadow-color") }
  }

  @Test
  fun hillshadeShadowColorAsExpressionGetNull() {
    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.hillshadeShadowColorAsExpression)
    verify { style.getStyleLayerProperty("id", "hillshade-shadow-color") }
  }

  @Test
  fun hillshadeShadowColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.hillshadeShadowColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.hillshadeShadowColor)
    assertEquals(Color.BLACK, layer.hillshadeShadowColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "hillshade-shadow-color") }
  }

  @Test
  fun hillshadeShadowColorAsColorIntSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeShadowColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "hillshade-shadow-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun hillshadeShadowColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.hillshadeShadowColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "hillshade-shadow-color") }
  }

  @Test
  fun hillshadeShadowColorTransitionSet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeShadowColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "hillshade-shadow-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun hillshadeShadowColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.hillshadeShadowColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "hillshade-shadow-color-transition") }
  }

  @Test
  fun hillshadeShadowColorTransitionSetDsl() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.hillshadeShadowColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "hillshade-shadow-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun visibilitySet() {
    val layer = hillshadeLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = hillshadeLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = hillshadeLayer("id", "source") { }
    assertEquals("hillshade", layer.getType())
  }

  @Test
  fun getHillshadeLayerTest() {
    val value = HashMap<String, Value>()
    value["id"] = Value("id")
    value["type"] = Value("hillshade")
    value["source"] = Value("source")
    every { style.getStyleLayerProperties("id") } returns valueExpected
    every { valueExpected.error } returns null
    every { valueExpected.value } returns Value(value)
    val layer = style.getLayer("id") as HillshadeLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("hillshade", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultHillshadeAccentColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), HillshadeLayer.defaultHillshadeAccentColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color") }
  }
  // Expression Tests

  @Test
  fun defaultHillshadeAccentColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeAccentColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color") }
  }

  @Test
  fun defaultHillshadeAccentColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeAccentColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", HillshadeLayer.defaultHillshadeAccentColor)
    assertEquals(Color.BLACK, HillshadeLayer.defaultHillshadeAccentColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color") }
  }

  @Test
  fun defaultHillshadeAccentColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, HillshadeLayer.defaultHillshadeAccentColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color") }
  }

  @Test
  fun defaultHillshadeAccentColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), HillshadeLayer.defaultHillshadeAccentColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color-transition") }
  }

  @Test
  fun defaultHillshadeExaggerationTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), HillshadeLayer.defaultHillshadeExaggeration?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration") }
  }
  // Expression Tests

  @Test
  fun defaultHillshadeExaggerationAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeExaggerationAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration") }
  }

  @Test
  fun defaultHillshadeExaggerationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, HillshadeLayer.defaultHillshadeExaggerationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, HillshadeLayer.defaultHillshadeExaggeration!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration") }
  }

  @Test
  fun defaultHillshadeExaggerationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), HillshadeLayer.defaultHillshadeExaggerationTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration-transition") }
  }

  @Test
  fun defaultHillshadeHighlightColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), HillshadeLayer.defaultHillshadeHighlightColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color") }
  }
  // Expression Tests

  @Test
  fun defaultHillshadeHighlightColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeHighlightColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color") }
  }

  @Test
  fun defaultHillshadeHighlightColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeHighlightColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", HillshadeLayer.defaultHillshadeHighlightColor)
    assertEquals(Color.BLACK, HillshadeLayer.defaultHillshadeHighlightColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color") }
  }

  @Test
  fun defaultHillshadeHighlightColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, HillshadeLayer.defaultHillshadeHighlightColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color") }
  }

  @Test
  fun defaultHillshadeHighlightColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), HillshadeLayer.defaultHillshadeHighlightColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color-transition") }
  }

  @Test
  fun defaultHillshadeIlluminationAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(HillshadeIlluminationAnchor.MAP, HillshadeLayer.defaultHillshadeIlluminationAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultHillshadeIlluminationAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeIlluminationAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-anchor") }
  }

  @Test
  fun defaultHillshadeIlluminationAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), HillshadeLayer.defaultHillshadeIlluminationAnchorAsExpression?.toString())
    assertEquals(HillshadeIlluminationAnchor.MAP.value, HillshadeLayer.defaultHillshadeIlluminationAnchorAsExpression.toString())
    assertEquals(HillshadeIlluminationAnchor.MAP, HillshadeLayer.defaultHillshadeIlluminationAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-anchor") }
  }

  @Test
  fun defaultHillshadeIlluminationDirectionTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), HillshadeLayer.defaultHillshadeIlluminationDirection?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-direction") }
  }
  // Expression Tests

  @Test
  fun defaultHillshadeIlluminationDirectionAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeIlluminationDirectionAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-direction") }
  }

  @Test
  fun defaultHillshadeIlluminationDirectionAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, HillshadeLayer.defaultHillshadeIlluminationDirectionAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, HillshadeLayer.defaultHillshadeIlluminationDirection!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-direction") }
  }

  @Test
  fun defaultHillshadeShadowColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), HillshadeLayer.defaultHillshadeShadowColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color") }
  }
  // Expression Tests

  @Test
  fun defaultHillshadeShadowColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeShadowColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color") }
  }

  @Test
  fun defaultHillshadeShadowColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), HillshadeLayer.defaultHillshadeShadowColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", HillshadeLayer.defaultHillshadeShadowColor)
    assertEquals(Color.BLACK, HillshadeLayer.defaultHillshadeShadowColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color") }
  }

  @Test
  fun defaultHillshadeShadowColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, HillshadeLayer.defaultHillshadeShadowColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color") }
  }

  @Test
  fun defaultHillshadeShadowColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), HillshadeLayer.defaultHillshadeShadowColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color-transition") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, HillshadeLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "visibility") }
  }
}

// End of generated file.