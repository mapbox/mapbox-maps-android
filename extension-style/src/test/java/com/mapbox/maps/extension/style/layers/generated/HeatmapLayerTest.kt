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
class HeatmapLayerTest {
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
    unmockkAll()
  }

  @Test
  fun sourceLayerTestDsl() {
    val layer = heatmapLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=heatmap}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = heatmapLayer("id", "source") {}
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
    val layer = heatmapLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=heatmap, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = heatmapLayer("id", "source") {}
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
    val layer = heatmapLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=heatmap}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = heatmapLayer("id", "source") {}
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
  fun heatmapColorSet() {
    val layer = heatmapLayer("id", "source") {}
    val testValue = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(255.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }
    layer.bindTo(style)
    layer.heatmapColor(testValue)
    verify { style.setStyleLayerProperty("id", "heatmap-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[interpolate, [linear], [heatmap-density], 0.0, [rgba, 0.0, 0.0, 0.0, 0.0], 1.0, [rgba, 0.0, 255.0, 0.0, 1.0]]")
  }

  @Test
  fun heatmapColorGet() {
    val testValue = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(255.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(255.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }
    assertEquals(expectedValue.toString(), layer.heatmapColor?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-color") }
  }
  // Expression Tests

  @Test
  fun heatmapIntensitySet() {
    val layer = heatmapLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.heatmapIntensity(testValue)
    verify { style.setStyleLayerProperty("id", "heatmap-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun heatmapIntensityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.heatmapIntensity?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-intensity") }
  }
  // Expression Tests

  @Test
  fun heatmapIntensityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapIntensity(expression)
    verify { style.setStyleLayerProperty("id", "heatmap-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun heatmapIntensityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.heatmapIntensityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-intensity") }
  }

  @Test
  fun heatmapIntensityAsExpressionGetNull() {
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.heatmapIntensityAsExpression)
    verify { style.getStyleLayerProperty("id", "heatmap-intensity") }
  }

  @Test
  fun heatmapIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.heatmapIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.heatmapIntensity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "heatmap-intensity") }
  }

  @Test
  fun heatmapIntensityTransitionSet() {
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapIntensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "heatmap-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun heatmapIntensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.heatmapIntensityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "heatmap-intensity-transition") }
  }

  @Test
  fun heatmapIntensityTransitionSetDsl() {
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapIntensityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "heatmap-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun heatmapOpacitySet() {
    val layer = heatmapLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.heatmapOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "heatmap-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun heatmapOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.heatmapOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-opacity") }
  }
  // Expression Tests

  @Test
  fun heatmapOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapOpacity(expression)
    verify { style.setStyleLayerProperty("id", "heatmap-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun heatmapOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.heatmapOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-opacity") }
  }

  @Test
  fun heatmapOpacityAsExpressionGetNull() {
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.heatmapOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "heatmap-opacity") }
  }

  @Test
  fun heatmapOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.heatmapOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.heatmapOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "heatmap-opacity") }
  }

  @Test
  fun heatmapOpacityTransitionSet() {
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "heatmap-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun heatmapOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.heatmapOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "heatmap-opacity-transition") }
  }

  @Test
  fun heatmapOpacityTransitionSetDsl() {
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "heatmap-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun heatmapRadiusSet() {
    val layer = heatmapLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.heatmapRadius(testValue)
    verify { style.setStyleLayerProperty("id", "heatmap-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun heatmapRadiusGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.heatmapRadius?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-radius") }
  }
  // Expression Tests

  @Test
  fun heatmapRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapRadius(expression)
    verify { style.setStyleLayerProperty("id", "heatmap-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun heatmapRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.heatmapRadiusAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-radius") }
  }

  @Test
  fun heatmapRadiusAsExpressionGetNull() {
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.heatmapRadiusAsExpression)
    verify { style.getStyleLayerProperty("id", "heatmap-radius") }
  }

  @Test
  fun heatmapRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.heatmapRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.heatmapRadius!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "heatmap-radius") }
  }

  @Test
  fun heatmapRadiusTransitionSet() {
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapRadiusTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "heatmap-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun heatmapRadiusTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.heatmapRadiusTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "heatmap-radius-transition") }
  }

  @Test
  fun heatmapRadiusTransitionSetDsl() {
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapRadiusTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "heatmap-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun heatmapWeightSet() {
    val layer = heatmapLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.heatmapWeight(testValue)
    verify { style.setStyleLayerProperty("id", "heatmap-weight", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun heatmapWeightGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.heatmapWeight?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-weight") }
  }
  // Expression Tests

  @Test
  fun heatmapWeightAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.heatmapWeight(expression)
    verify { style.setStyleLayerProperty("id", "heatmap-weight", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun heatmapWeightAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.heatmapWeightAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "heatmap-weight") }
  }

  @Test
  fun heatmapWeightAsExpressionGetNull() {
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.heatmapWeightAsExpression)
    verify { style.getStyleLayerProperty("id", "heatmap-weight") }
  }

  @Test
  fun heatmapWeightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.heatmapWeightAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.heatmapWeight!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "heatmap-weight") }
  }

  @Test
  fun visibilitySet() {
    val layer = heatmapLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = heatmapLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = heatmapLayer("id", "source") { }
    assertEquals("heatmap", layer.getType())
  }

  @Test
  fun getHeatmapLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )
    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("heatmap"),
      StylePropertyValueKind.CONSTANT
    )
    val layer = style.getLayer("id") as HeatmapLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("heatmap", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultHeatmapColorTest() {
    val testValue = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(255.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val expectedValue = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(255.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }
    assertEquals(expectedValue.toString(), HeatmapLayer.defaultHeatmapColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-color") }
  }
  // Expression Tests

  @Test
  fun defaultHeatmapIntensityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), HeatmapLayer.defaultHeatmapIntensity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity") }
  }
  // Expression Tests

  @Test
  fun defaultHeatmapIntensityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HeatmapLayer.defaultHeatmapIntensityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity") }
  }

  @Test
  fun defaultHeatmapIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapIntensity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity") }
  }

  @Test
  fun defaultHeatmapIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), HeatmapLayer.defaultHeatmapIntensityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity-transition") }
  }

  @Test
  fun defaultHeatmapOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), HeatmapLayer.defaultHeatmapOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultHeatmapOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HeatmapLayer.defaultHeatmapOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity") }
  }

  @Test
  fun defaultHeatmapOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity") }
  }

  @Test
  fun defaultHeatmapOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), HeatmapLayer.defaultHeatmapOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity-transition") }
  }

  @Test
  fun defaultHeatmapRadiusTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), HeatmapLayer.defaultHeatmapRadius?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius") }
  }
  // Expression Tests

  @Test
  fun defaultHeatmapRadiusAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HeatmapLayer.defaultHeatmapRadiusAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius") }
  }

  @Test
  fun defaultHeatmapRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapRadius!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius") }
  }

  @Test
  fun defaultHeatmapRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), HeatmapLayer.defaultHeatmapRadiusTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius-transition") }
  }

  @Test
  fun defaultHeatmapWeightTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), HeatmapLayer.defaultHeatmapWeight?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-weight") }
  }
  // Expression Tests

  @Test
  fun defaultHeatmapWeightAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), HeatmapLayer.defaultHeatmapWeightAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-weight") }
  }

  @Test
  fun defaultHeatmapWeightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapWeightAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, HeatmapLayer.defaultHeatmapWeight!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-weight") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, HeatmapLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "visibility") }
  }
}

// End of generated file.