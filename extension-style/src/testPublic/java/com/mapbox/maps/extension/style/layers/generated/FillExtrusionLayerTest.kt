// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.Style
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

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class FillExtrusionLayerTest {
  private val style = mockk<Style>(relaxUnitFun = true, relaxed = true)
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
    val layer = fillExtrusionLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=fill-extrusion}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = fillExtrusionLayer("id", "source") {}
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
    val layer = fillExtrusionLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=fill-extrusion, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = fillExtrusionLayer("id", "source") {}
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
    val layer = fillExtrusionLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=fill-extrusion}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = fillExtrusionLayer("id", "source") {}
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
  fun fillExtrusionEdgeRadiusSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillExtrusionEdgeRadius(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-edge-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillExtrusionEdgeRadiusGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillExtrusionEdgeRadius?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-edge-radius") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionEdgeRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionEdgeRadius(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-edge-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionEdgeRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionEdgeRadiusAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-edge-radius") }
  }

  @Test
  fun fillExtrusionEdgeRadiusAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionEdgeRadiusAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-edge-radius") }
  }

  @Test
  fun fillExtrusionEdgeRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillExtrusionEdgeRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionEdgeRadius!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-edge-radius") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensitySet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionIntensity(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillExtrusionAmbientOcclusionIntensity?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionAmbientOcclusionIntensityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionIntensity(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionAmbientOcclusionIntensityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensityAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionAmbientOcclusionIntensityAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionIntensity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensityTransitionSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionIntensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillExtrusionAmbientOcclusionIntensityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity-transition") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionIntensityTransitionSetDsl() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionIntensityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-intensity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionRadius(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillExtrusionAmbientOcclusionRadius?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionAmbientOcclusionRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionRadius(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionAmbientOcclusionRadiusAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionAmbientOcclusionRadiusAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionRadius!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusTransitionSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionRadiusTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillExtrusionAmbientOcclusionRadiusTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius-transition") }
  }

  @Test
  fun fillExtrusionAmbientOcclusionRadiusTransitionSetDsl() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionAmbientOcclusionRadiusTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-extrusion-ambient-occlusion-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionBaseSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillExtrusionBase(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-base", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillExtrusionBaseGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillExtrusionBase?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-base") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionBaseAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionBase(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-base", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionBaseAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionBaseAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-base") }
  }

  @Test
  fun fillExtrusionBaseAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionBaseAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-base") }
  }

  @Test
  fun fillExtrusionBaseAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillExtrusionBaseAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionBase!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-base") }
  }

  @Test
  fun fillExtrusionBaseTransitionSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionBaseTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-extrusion-base-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionBaseTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillExtrusionBaseTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-base-transition") }
  }

  @Test
  fun fillExtrusionBaseTransitionSetDsl() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionBaseTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-extrusion-base-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionColorSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.fillExtrusionColor(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun fillExtrusionColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.fillExtrusionColor?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-color") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionColor(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-color") }
  }

  @Test
  fun fillExtrusionColorAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionColorAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-color") }
  }

  @Test
  fun fillExtrusionColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.fillExtrusionColor)
    assertEquals(Color.BLACK, layer.fillExtrusionColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-color") }
  }

  @Test
  fun fillExtrusionColorAsColorIntSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun fillExtrusionColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.fillExtrusionColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-color") }
  }

  @Test
  fun fillExtrusionColorTransitionSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-extrusion-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillExtrusionColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-color-transition") }
  }

  @Test
  fun fillExtrusionColorTransitionSetDsl() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-extrusion-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionHeightSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillExtrusionHeight(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-height", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillExtrusionHeightGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillExtrusionHeight?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-height") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionHeightAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionHeight(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-height", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionHeightAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionHeightAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-height") }
  }

  @Test
  fun fillExtrusionHeightAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionHeightAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-height") }
  }

  @Test
  fun fillExtrusionHeightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillExtrusionHeightAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionHeight!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-height") }
  }

  @Test
  fun fillExtrusionHeightTransitionSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionHeightTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-extrusion-height-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionHeightTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillExtrusionHeightTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-height-transition") }
  }

  @Test
  fun fillExtrusionHeightTransitionSetDsl() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionHeightTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-extrusion-height-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionOpacitySet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.fillExtrusionOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun fillExtrusionOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.fillExtrusionOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-opacity") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionOpacity(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-opacity") }
  }

  @Test
  fun fillExtrusionOpacityAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-opacity") }
  }

  @Test
  fun fillExtrusionOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.fillExtrusionOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-opacity") }
  }

  @Test
  fun fillExtrusionOpacityTransitionSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-extrusion-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillExtrusionOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-opacity-transition") }
  }

  @Test
  fun fillExtrusionOpacityTransitionSetDsl() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-extrusion-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionPatternSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.fillExtrusionPattern(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun fillExtrusionPatternGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.fillExtrusionPattern?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-pattern") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionPatternAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionPattern(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionPatternAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionPatternAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-pattern") }
  }

  @Test
  fun fillExtrusionPatternAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionPatternAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-pattern") }
  }

  @Test
  fun fillExtrusionPatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("abc", layer.fillExtrusionPatternAsExpression.toString())
    assertEquals("abc", layer.fillExtrusionPattern)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-pattern") }
  }

  @Test
  fun fillExtrusionRoundedRoofSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.fillExtrusionRoundedRoof(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-rounded-roof", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun fillExtrusionRoundedRoofGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.fillExtrusionRoundedRoof?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-rounded-roof") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionRoundedRoofAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionRoundedRoof(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-rounded-roof", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionRoundedRoofAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionRoundedRoofAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-rounded-roof") }
  }

  @Test
  fun fillExtrusionRoundedRoofAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionRoundedRoofAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-rounded-roof") }
  }

  @Test
  fun fillExtrusionRoundedRoofAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.fillExtrusionRoundedRoofAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.fillExtrusionRoundedRoof)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-rounded-roof") }
  }

  @Test
  fun fillExtrusionTranslateSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.fillExtrusionTranslate(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun fillExtrusionTranslateGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.fillExtrusionTranslate?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionTranslateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionTranslate(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionTranslateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionTranslateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate") }
  }

  @Test
  fun fillExtrusionTranslateAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionTranslateAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate") }
  }

  @Test
  fun fillExtrusionTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.fillExtrusionTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.fillExtrusionTranslate!!)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate") }
  }

  @Test
  fun fillExtrusionTranslateTransitionSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionTranslateTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "fill-extrusion-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionTranslateTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.fillExtrusionTranslateTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate-transition") }
  }

  @Test
  fun fillExtrusionTranslateTransitionSetDsl() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionTranslateTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "fill-extrusion-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun fillExtrusionTranslateAnchorSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionTranslateAnchor(FillExtrusionTranslateAnchor.MAP)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun fillExtrusionTranslateAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(FillExtrusionTranslateAnchor.MAP, layer.fillExtrusionTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionTranslateAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionTranslateAnchor(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionTranslateAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionTranslateAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate-anchor") }
  }

  @Test
  fun fillExtrusionTranslateAnchorAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionTranslateAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate-anchor") }
  }

  @Test
  fun fillExtrusionTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.fillExtrusionTranslateAnchorAsExpression?.toString())
    assertEquals(FillExtrusionTranslateAnchor.MAP.value, layer.fillExtrusionTranslateAnchorAsExpression.toString())
    assertEquals(FillExtrusionTranslateAnchor.MAP, layer.fillExtrusionTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-translate-anchor") }
  }

  @Test
  fun fillExtrusionVerticalGradientSet() {
    val layer = fillExtrusionLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.fillExtrusionVerticalGradient(testValue)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-vertical-gradient", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun fillExtrusionVerticalGradientGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.fillExtrusionVerticalGradient?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-vertical-gradient") }
  }
  // Expression Tests

  @Test
  fun fillExtrusionVerticalGradientAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.fillExtrusionVerticalGradient(expression)
    verify { style.setStyleLayerProperty("id", "fill-extrusion-vertical-gradient", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun fillExtrusionVerticalGradientAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.fillExtrusionVerticalGradientAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "fill-extrusion-vertical-gradient") }
  }

  @Test
  fun fillExtrusionVerticalGradientAsExpressionGetNull() {
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.fillExtrusionVerticalGradientAsExpression)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-vertical-gradient") }
  }

  @Test
  fun fillExtrusionVerticalGradientAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.fillExtrusionVerticalGradientAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.fillExtrusionVerticalGradient)
    verify { style.getStyleLayerProperty("id", "fill-extrusion-vertical-gradient") }
  }

  @Test
  fun visibilitySet() {
    val layer = fillExtrusionLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = fillExtrusionLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = fillExtrusionLayer("id", "source") { }
    assertEquals("fill-extrusion", layer.getType())
  }

  @Test
  fun getFillExtrusionLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("fill-extrusion"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as FillExtrusionLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("fill-extrusion", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultFillExtrusionEdgeRadiusTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionEdgeRadius?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-edge-radius") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionEdgeRadiusAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionEdgeRadiusAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-edge-radius") }
  }

  @Test
  fun defaultFillExtrusionEdgeRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionEdgeRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionEdgeRadius!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-edge-radius") }
  }

  @Test
  fun defaultFillExtrusionAmbientOcclusionIntensityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionAmbientOcclusionIntensityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity") }
  }

  @Test
  fun defaultFillExtrusionAmbientOcclusionIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity") }
  }

  @Test
  fun defaultFillExtrusionAmbientOcclusionIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity-transition") }
  }

  @Test
  fun defaultFillExtrusionAmbientOcclusionRadiusTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadius?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionAmbientOcclusionRadiusAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius") }
  }

  @Test
  fun defaultFillExtrusionAmbientOcclusionRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadius!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius") }
  }

  @Test
  fun defaultFillExtrusionAmbientOcclusionRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius-transition") }
  }

  @Test
  fun defaultFillExtrusionBaseTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionBase?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionBaseAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionBaseAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base") }
  }

  @Test
  fun defaultFillExtrusionBaseAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionBaseAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionBase!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base") }
  }

  @Test
  fun defaultFillExtrusionBaseTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillExtrusionLayer.defaultFillExtrusionBaseTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base-transition") }
  }

  @Test
  fun defaultFillExtrusionColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color") }
  }

  @Test
  fun defaultFillExtrusionColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", FillExtrusionLayer.defaultFillExtrusionColor)
    assertEquals(Color.BLACK, FillExtrusionLayer.defaultFillExtrusionColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color") }
  }

  @Test
  fun defaultFillExtrusionColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, FillExtrusionLayer.defaultFillExtrusionColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color") }
  }

  @Test
  fun defaultFillExtrusionColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillExtrusionLayer.defaultFillExtrusionColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color-transition") }
  }

  @Test
  fun defaultFillExtrusionHeightTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionHeight?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionHeightAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionHeightAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height") }
  }

  @Test
  fun defaultFillExtrusionHeightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionHeightAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionHeight!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height") }
  }

  @Test
  fun defaultFillExtrusionHeightTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillExtrusionLayer.defaultFillExtrusionHeightTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height-transition") }
  }

  @Test
  fun defaultFillExtrusionOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity") }
  }

  @Test
  fun defaultFillExtrusionOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, FillExtrusionLayer.defaultFillExtrusionOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity") }
  }

  @Test
  fun defaultFillExtrusionOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillExtrusionLayer.defaultFillExtrusionOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity-transition") }
  }

  @Test
  fun defaultFillExtrusionPatternTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionPattern?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionPatternAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionPatternAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern") }
  }

  @Test
  fun defaultFillExtrusionPatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", FillExtrusionLayer.defaultFillExtrusionPatternAsExpression.toString())
    assertEquals("abc", FillExtrusionLayer.defaultFillExtrusionPattern)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern") }
  }

  @Test
  fun defaultFillExtrusionRoundedRoofTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionRoundedRoof?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-rounded-roof") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionRoundedRoofAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionRoundedRoofAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-rounded-roof") }
  }

  @Test
  fun defaultFillExtrusionRoundedRoofAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", FillExtrusionLayer.defaultFillExtrusionRoundedRoofAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, FillExtrusionLayer.defaultFillExtrusionRoundedRoof)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-rounded-roof") }
  }

  @Test
  fun defaultFillExtrusionTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionTranslate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionTranslateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionTranslateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate") }
  }

  @Test
  fun defaultFillExtrusionTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", FillExtrusionLayer.defaultFillExtrusionTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), FillExtrusionLayer.defaultFillExtrusionTranslate!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate") }
  }

  @Test
  fun defaultFillExtrusionTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), FillExtrusionLayer.defaultFillExtrusionTranslateTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-transition") }
  }

  @Test
  fun defaultFillExtrusionTranslateAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(FillExtrusionTranslateAnchor.MAP, FillExtrusionLayer.defaultFillExtrusionTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionTranslateAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionTranslateAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-anchor") }
  }

  @Test
  fun defaultFillExtrusionTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), FillExtrusionLayer.defaultFillExtrusionTranslateAnchorAsExpression?.toString())
    assertEquals(FillExtrusionTranslateAnchor.MAP.value, FillExtrusionLayer.defaultFillExtrusionTranslateAnchorAsExpression.toString())
    assertEquals(FillExtrusionTranslateAnchor.MAP, FillExtrusionLayer.defaultFillExtrusionTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-anchor") }
  }

  @Test
  fun defaultFillExtrusionVerticalGradientTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), FillExtrusionLayer.defaultFillExtrusionVerticalGradient?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient") }
  }
  // Expression Tests

  @Test
  fun defaultFillExtrusionVerticalGradientAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), FillExtrusionLayer.defaultFillExtrusionVerticalGradientAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient") }
  }

  @Test
  fun defaultFillExtrusionVerticalGradientAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", FillExtrusionLayer.defaultFillExtrusionVerticalGradientAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, FillExtrusionLayer.defaultFillExtrusionVerticalGradient)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, FillExtrusionLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "visibility") }
  }
}

// End of generated file.