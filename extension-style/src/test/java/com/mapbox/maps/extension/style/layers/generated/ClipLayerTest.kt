// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
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
class ClipLayerTest {
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
    val layer = clipLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=clip}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = clipLayer("id", "source") {}
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
    val layer = clipLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=clip, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = clipLayer("id", "source") {}
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
    val layer = clipLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=clip}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = clipLayer("id", "source") {}
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
    val layer = clipLayer("id", "source") {}
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

    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.filter?.toString())
    verify { style.getStyleLayerProperty("id", "filter") }
  }
  // Property getters and setters

  @Test
  fun clipLayerScopeSet() {
    val layer = clipLayer("id", "source") {}
    val testValue = listOf("a", "b", "c")
    layer.bindTo(style)
    layer.clipLayerScope(testValue)
    verify { style.setStyleLayerProperty("id", "clip-layer-scope", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[a, b, c]")
  }

  @Test
  fun clipLayerScopeGet() {
    val testValue = listOf("a", "b", "c")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf("a", "b", "c")
    assertEquals(expectedValue.toString(), layer.clipLayerScope?.toString())
    verify { style.getStyleLayerProperty("id", "clip-layer-scope") }
  }
  // Expression Tests

  @Test
  fun clipLayerScopeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = clipLayer("id", "source") {}
    layer.bindTo(style)
    layer.clipLayerScope(expression)
    verify { style.setStyleLayerProperty("id", "clip-layer-scope", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun clipLayerScopeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.clipLayerScopeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "clip-layer-scope") }
  }

  @Test
  fun clipLayerScopeAsExpressionGetNull() {
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.clipLayerScopeAsExpression)
    verify { style.getStyleLayerProperty("id", "clip-layer-scope") }
  }

  @Test
  fun clipLayerScopeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [a, b, c]]", layer.clipLayerScopeAsExpression.toString())
    assertEquals(listOf("a", "b", "c"), layer.clipLayerScope!!)
    verify { style.getStyleLayerProperty("id", "clip-layer-scope") }
  }

  @Test
  fun clipLayerTypesSet() {
    val layer = clipLayer("id", "source") {}
    val testValue = listOf("model", "symbol")
    layer.bindTo(style)
    layer.clipLayerTypes(testValue)
    verify { style.setStyleLayerProperty("id", "clip-layer-types", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[model, symbol]")
  }

  @Test
  fun clipLayerTypesGet() {
    val testValue = listOf("model", "symbol")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf("model", "symbol")
    assertEquals(expectedValue.toString(), layer.clipLayerTypes?.toString())
    verify { style.getStyleLayerProperty("id", "clip-layer-types") }
  }
  // Expression Tests

  @Test
  fun clipLayerTypesAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = clipLayer("id", "source") {}
    layer.bindTo(style)
    layer.clipLayerTypes(expression)
    verify { style.setStyleLayerProperty("id", "clip-layer-types", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun clipLayerTypesAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.clipLayerTypesAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "clip-layer-types") }
  }

  @Test
  fun clipLayerTypesAsExpressionGetNull() {
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.clipLayerTypesAsExpression)
    verify { style.getStyleLayerProperty("id", "clip-layer-types") }
  }

  @Test
  fun clipLayerTypesAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("model", "symbol"))
    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [model, symbol]]", layer.clipLayerTypesAsExpression.toString())
    assertEquals(listOf("model", "symbol"), layer.clipLayerTypes!!)
    verify { style.getStyleLayerProperty("id", "clip-layer-types") }
  }

  @Test
  fun visibilitySet() {
    val layer = clipLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun visibilityAsExpressionSet() {
    val layer = clipLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(literal("none"))
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityAsExpressionGet() {
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns literal("none")

    val layer = clipLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(literal("none"), layer.visibilityAsExpression)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = clipLayer("id", "source") { }
    assertEquals("clip", layer.getType())
  }

  @Test
  fun getClipLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("clip"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as ClipLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("clip", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultClipLayerScopeTest() {
    val testValue = listOf("a", "b", "c")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf("a", "b", "c")
    assertEquals(expectedValue.toString(), ClipLayer.defaultClipLayerScope?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-scope") }
  }
  // Expression Tests

  @Test
  fun defaultClipLayerScopeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ClipLayer.defaultClipLayerScopeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-scope") }
  }

  @Test
  fun defaultClipLayerScopeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    assertEquals("[literal, [a, b, c]]", ClipLayer.defaultClipLayerScopeAsExpression.toString())
    assertEquals(listOf("a", "b", "c"), ClipLayer.defaultClipLayerScope!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-scope") }
  }

  @Test
  fun defaultClipLayerTypesTest() {
    val testValue = listOf("model", "symbol")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf("model", "symbol")
    assertEquals(expectedValue.toString(), ClipLayer.defaultClipLayerTypes?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-types") }
  }
  // Expression Tests

  @Test
  fun defaultClipLayerTypesAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ClipLayer.defaultClipLayerTypesAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-types") }
  }

  @Test
  fun defaultClipLayerTypesAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("model", "symbol"))
    assertEquals("[literal, [model, symbol]]", ClipLayer.defaultClipLayerTypesAsExpression.toString())
    assertEquals(listOf("model", "symbol"), ClipLayer.defaultClipLayerTypes!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-types") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, ClipLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("clip", "visibility") }
  }
}

// End of generated file.