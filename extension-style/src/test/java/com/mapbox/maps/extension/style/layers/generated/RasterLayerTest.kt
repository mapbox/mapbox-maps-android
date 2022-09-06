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
class RasterLayerTest {
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
    val layer = rasterLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=raster}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = rasterLayer("id", "source") {}
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
    val layer = rasterLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=raster, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = rasterLayer("id", "source") {}
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
    val layer = rasterLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=raster}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = rasterLayer("id", "source") {}
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
  fun rasterBrightnessMaxSet() {
    val layer = rasterLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterBrightnessMax(testValue)
    verify { style.setStyleLayerProperty("id", "raster-brightness-max", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterBrightnessMaxGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterBrightnessMax?.toString())
    verify { style.getStyleLayerProperty("id", "raster-brightness-max") }
  }
  // Expression Tests

  @Test
  fun rasterBrightnessMaxAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterBrightnessMax(expression)
    verify { style.setStyleLayerProperty("id", "raster-brightness-max", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterBrightnessMaxAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterBrightnessMaxAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-brightness-max") }
  }

  @Test
  fun rasterBrightnessMaxAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterBrightnessMaxAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-brightness-max") }
  }

  @Test
  fun rasterBrightnessMaxAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterBrightnessMaxAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterBrightnessMax!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-brightness-max") }
  }

  @Test
  fun rasterBrightnessMaxTransitionSet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterBrightnessMaxTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-brightness-max-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterBrightnessMaxTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterBrightnessMaxTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-brightness-max-transition") }
  }

  @Test
  fun rasterBrightnessMaxTransitionSetDsl() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterBrightnessMaxTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-brightness-max-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterBrightnessMinSet() {
    val layer = rasterLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterBrightnessMin(testValue)
    verify { style.setStyleLayerProperty("id", "raster-brightness-min", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterBrightnessMinGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterBrightnessMin?.toString())
    verify { style.getStyleLayerProperty("id", "raster-brightness-min") }
  }
  // Expression Tests

  @Test
  fun rasterBrightnessMinAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterBrightnessMin(expression)
    verify { style.setStyleLayerProperty("id", "raster-brightness-min", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterBrightnessMinAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterBrightnessMinAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-brightness-min") }
  }

  @Test
  fun rasterBrightnessMinAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterBrightnessMinAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-brightness-min") }
  }

  @Test
  fun rasterBrightnessMinAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterBrightnessMinAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterBrightnessMin!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-brightness-min") }
  }

  @Test
  fun rasterBrightnessMinTransitionSet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterBrightnessMinTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-brightness-min-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterBrightnessMinTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterBrightnessMinTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-brightness-min-transition") }
  }

  @Test
  fun rasterBrightnessMinTransitionSetDsl() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterBrightnessMinTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-brightness-min-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterContrastSet() {
    val layer = rasterLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterContrast(testValue)
    verify { style.setStyleLayerProperty("id", "raster-contrast", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterContrastGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterContrast?.toString())
    verify { style.getStyleLayerProperty("id", "raster-contrast") }
  }
  // Expression Tests

  @Test
  fun rasterContrastAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterContrast(expression)
    verify { style.setStyleLayerProperty("id", "raster-contrast", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterContrastAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterContrastAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-contrast") }
  }

  @Test
  fun rasterContrastAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterContrastAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-contrast") }
  }

  @Test
  fun rasterContrastAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterContrastAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterContrast!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-contrast") }
  }

  @Test
  fun rasterContrastTransitionSet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterContrastTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-contrast-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterContrastTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterContrastTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-contrast-transition") }
  }

  @Test
  fun rasterContrastTransitionSetDsl() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterContrastTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-contrast-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterFadeDurationSet() {
    val layer = rasterLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterFadeDuration(testValue)
    verify { style.setStyleLayerProperty("id", "raster-fade-duration", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterFadeDurationGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterFadeDuration?.toString())
    verify { style.getStyleLayerProperty("id", "raster-fade-duration") }
  }
  // Expression Tests

  @Test
  fun rasterFadeDurationAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterFadeDuration(expression)
    verify { style.setStyleLayerProperty("id", "raster-fade-duration", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterFadeDurationAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterFadeDurationAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-fade-duration") }
  }

  @Test
  fun rasterFadeDurationAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterFadeDurationAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-fade-duration") }
  }

  @Test
  fun rasterFadeDurationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterFadeDurationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterFadeDuration!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-fade-duration") }
  }

  @Test
  fun rasterHueRotateSet() {
    val layer = rasterLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterHueRotate(testValue)
    verify { style.setStyleLayerProperty("id", "raster-hue-rotate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterHueRotateGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterHueRotate?.toString())
    verify { style.getStyleLayerProperty("id", "raster-hue-rotate") }
  }
  // Expression Tests

  @Test
  fun rasterHueRotateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterHueRotate(expression)
    verify { style.setStyleLayerProperty("id", "raster-hue-rotate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterHueRotateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterHueRotateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-hue-rotate") }
  }

  @Test
  fun rasterHueRotateAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterHueRotateAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-hue-rotate") }
  }

  @Test
  fun rasterHueRotateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterHueRotateAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterHueRotate!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-hue-rotate") }
  }

  @Test
  fun rasterHueRotateTransitionSet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterHueRotateTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-hue-rotate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterHueRotateTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterHueRotateTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-hue-rotate-transition") }
  }

  @Test
  fun rasterHueRotateTransitionSetDsl() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterHueRotateTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-hue-rotate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterOpacitySet() {
    val layer = rasterLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "raster-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "raster-opacity") }
  }
  // Expression Tests

  @Test
  fun rasterOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterOpacity(expression)
    verify { style.setStyleLayerProperty("id", "raster-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-opacity") }
  }

  @Test
  fun rasterOpacityAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-opacity") }
  }

  @Test
  fun rasterOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-opacity") }
  }

  @Test
  fun rasterOpacityTransitionSet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-opacity-transition") }
  }

  @Test
  fun rasterOpacityTransitionSetDsl() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterResamplingSet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterResampling(RasterResampling.LINEAR)
    verify { style.setStyleLayerProperty("id", "raster-resampling", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "linear")
  }

  @Test
  fun rasterResamplingGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("linear")

    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(RasterResampling.LINEAR, layer.rasterResampling)
    verify { style.getStyleLayerProperty("id", "raster-resampling") }
  }
  // Expression Tests

  @Test
  fun rasterResamplingAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterResampling(expression)
    verify { style.setStyleLayerProperty("id", "raster-resampling", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterResamplingAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterResamplingAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-resampling") }
  }

  @Test
  fun rasterResamplingAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterResamplingAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-resampling") }
  }

  @Test
  fun rasterResamplingAsExpressionGetFromLiteral() {
    val value = "linear"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.rasterResamplingAsExpression?.toString())
    assertEquals(RasterResampling.LINEAR.value, layer.rasterResamplingAsExpression.toString())
    assertEquals(RasterResampling.LINEAR, layer.rasterResampling)
    verify { style.getStyleLayerProperty("id", "raster-resampling") }
  }

  @Test
  fun rasterSaturationSet() {
    val layer = rasterLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterSaturation(testValue)
    verify { style.setStyleLayerProperty("id", "raster-saturation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterSaturationGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterSaturation?.toString())
    verify { style.getStyleLayerProperty("id", "raster-saturation") }
  }
  // Expression Tests

  @Test
  fun rasterSaturationAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterSaturation(expression)
    verify { style.setStyleLayerProperty("id", "raster-saturation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterSaturationAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterSaturationAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-saturation") }
  }

  @Test
  fun rasterSaturationAsExpressionGetNull() {
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterSaturationAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-saturation") }
  }

  @Test
  fun rasterSaturationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterSaturationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterSaturation!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-saturation") }
  }

  @Test
  fun rasterSaturationTransitionSet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterSaturationTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-saturation-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterSaturationTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterSaturationTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-saturation-transition") }
  }

  @Test
  fun rasterSaturationTransitionSetDsl() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterSaturationTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-saturation-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun visibilitySet() {
    val layer = rasterLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = rasterLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = rasterLayer("id", "source") { }
    assertEquals("raster", layer.getType())
  }

  @Test
  fun getRasterLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("raster"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as RasterLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("raster", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultRasterBrightnessMaxTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterLayer.defaultRasterBrightnessMax?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-max") }
  }
  // Expression Tests

  @Test
  fun defaultRasterBrightnessMaxAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterBrightnessMaxAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-max") }
  }

  @Test
  fun defaultRasterBrightnessMaxAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterLayer.defaultRasterBrightnessMaxAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterLayer.defaultRasterBrightnessMax!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-max") }
  }

  @Test
  fun defaultRasterBrightnessMaxTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterLayer.defaultRasterBrightnessMaxTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-max-transition") }
  }

  @Test
  fun defaultRasterBrightnessMinTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterLayer.defaultRasterBrightnessMin?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-min") }
  }
  // Expression Tests

  @Test
  fun defaultRasterBrightnessMinAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterBrightnessMinAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-min") }
  }

  @Test
  fun defaultRasterBrightnessMinAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterLayer.defaultRasterBrightnessMinAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterLayer.defaultRasterBrightnessMin!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-min") }
  }

  @Test
  fun defaultRasterBrightnessMinTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterLayer.defaultRasterBrightnessMinTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-min-transition") }
  }

  @Test
  fun defaultRasterContrastTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterLayer.defaultRasterContrast?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-contrast") }
  }
  // Expression Tests

  @Test
  fun defaultRasterContrastAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterContrastAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-contrast") }
  }

  @Test
  fun defaultRasterContrastAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterLayer.defaultRasterContrastAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterLayer.defaultRasterContrast!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-contrast") }
  }

  @Test
  fun defaultRasterContrastTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterLayer.defaultRasterContrastTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-contrast-transition") }
  }

  @Test
  fun defaultRasterFadeDurationTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterLayer.defaultRasterFadeDuration?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-fade-duration") }
  }
  // Expression Tests

  @Test
  fun defaultRasterFadeDurationAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterFadeDurationAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-fade-duration") }
  }

  @Test
  fun defaultRasterFadeDurationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterLayer.defaultRasterFadeDurationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterLayer.defaultRasterFadeDuration!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-fade-duration") }
  }

  @Test
  fun defaultRasterHueRotateTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterLayer.defaultRasterHueRotate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-hue-rotate") }
  }
  // Expression Tests

  @Test
  fun defaultRasterHueRotateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterHueRotateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-hue-rotate") }
  }

  @Test
  fun defaultRasterHueRotateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterLayer.defaultRasterHueRotateAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterLayer.defaultRasterHueRotate!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-hue-rotate") }
  }

  @Test
  fun defaultRasterHueRotateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterLayer.defaultRasterHueRotateTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-hue-rotate-transition") }
  }

  @Test
  fun defaultRasterOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterLayer.defaultRasterOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultRasterOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-opacity") }
  }

  @Test
  fun defaultRasterOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterLayer.defaultRasterOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterLayer.defaultRasterOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-opacity") }
  }

  @Test
  fun defaultRasterOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterLayer.defaultRasterOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-opacity-transition") }
  }

  @Test
  fun defaultRasterResamplingTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("linear")

    assertEquals(RasterResampling.LINEAR, RasterLayer.defaultRasterResampling)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-resampling") }
  }
  // Expression Tests

  @Test
  fun defaultRasterResamplingAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterResamplingAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-resampling") }
  }

  @Test
  fun defaultRasterResamplingAsExpressionGetFromLiteral() {
    val value = "linear"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), RasterLayer.defaultRasterResamplingAsExpression?.toString())
    assertEquals(RasterResampling.LINEAR.value, RasterLayer.defaultRasterResamplingAsExpression.toString())
    assertEquals(RasterResampling.LINEAR, RasterLayer.defaultRasterResampling)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-resampling") }
  }

  @Test
  fun defaultRasterSaturationTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterLayer.defaultRasterSaturation?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-saturation") }
  }
  // Expression Tests

  @Test
  fun defaultRasterSaturationAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterLayer.defaultRasterSaturationAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-saturation") }
  }

  @Test
  fun defaultRasterSaturationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterLayer.defaultRasterSaturationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterLayer.defaultRasterSaturation!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-saturation") }
  }

  @Test
  fun defaultRasterSaturationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterLayer.defaultRasterSaturationTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-saturation-transition") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, RasterLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster", "visibility") }
  }
}

// End of generated file.