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
class LineLayerTest {
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
    val layer = lineLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=line}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = lineLayer("id", "source") {}
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
    val layer = lineLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=line, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = lineLayer("id", "source") {}
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
    val layer = lineLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=line}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = lineLayer("id", "source") {}
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
    val layer = lineLayer("id", "source") {}
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

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.filter?.toString())
    verify { style.getStyleLayerProperty("id", "filter") }
  }
  // Property getters and setters

  @Test
  fun lineCapSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineCap(LineCap.BUTT)
    verify { style.setStyleLayerProperty("id", "line-cap", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "butt")
  }

  @Test
  fun lineCapGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("butt")

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(LineCap.BUTT, layer.lineCap)
    verify { style.getStyleLayerProperty("id", "line-cap") }
  }
  // Expression Tests

  @Test
  fun lineCapAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineCap(expression)
    verify { style.setStyleLayerProperty("id", "line-cap", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineCapAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineCapAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-cap") }
  }

  @Test
  fun lineCapAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineCapAsExpression)
    verify { style.getStyleLayerProperty("id", "line-cap") }
  }

  @Test
  fun lineCapAsExpressionGetFromLiteral() {
    val value = "butt"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.lineCapAsExpression?.toString())
    assertEquals(LineCap.BUTT.value, layer.lineCapAsExpression.toString())
    assertEquals(LineCap.BUTT, layer.lineCap)
    verify { style.getStyleLayerProperty("id", "line-cap") }
  }

  @Test
  fun lineCrossSlopeSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineCrossSlope(testValue)
    verify { style.setStyleLayerProperty("id", "line-cross-slope", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineCrossSlopeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineCrossSlope?.toString())
    verify { style.getStyleLayerProperty("id", "line-cross-slope") }
  }
  // Expression Tests

  @Test
  fun lineCrossSlopeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineCrossSlope(expression)
    verify { style.setStyleLayerProperty("id", "line-cross-slope", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineCrossSlopeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineCrossSlopeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-cross-slope") }
  }

  @Test
  fun lineCrossSlopeAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineCrossSlopeAsExpression)
    verify { style.getStyleLayerProperty("id", "line-cross-slope") }
  }

  @Test
  fun lineCrossSlopeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineCrossSlopeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineCrossSlope!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-cross-slope") }
  }

  @Test
  fun lineElevationReferenceSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineElevationReference(LineElevationReference.NONE)
    verify { style.setStyleLayerProperty("id", "line-elevation-reference", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun lineElevationReferenceGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(LineElevationReference.NONE, layer.lineElevationReference)
    verify { style.getStyleLayerProperty("id", "line-elevation-reference") }
  }
  // Expression Tests

  @Test
  fun lineElevationReferenceAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineElevationReference(expression)
    verify { style.setStyleLayerProperty("id", "line-elevation-reference", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineElevationReferenceAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineElevationReferenceAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-elevation-reference") }
  }

  @Test
  fun lineElevationReferenceAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineElevationReferenceAsExpression)
    verify { style.getStyleLayerProperty("id", "line-elevation-reference") }
  }

  @Test
  fun lineElevationReferenceAsExpressionGetFromLiteral() {
    val value = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.lineElevationReferenceAsExpression?.toString())
    assertEquals(LineElevationReference.NONE.value, layer.lineElevationReferenceAsExpression.toString())
    assertEquals(LineElevationReference.NONE, layer.lineElevationReference)
    verify { style.getStyleLayerProperty("id", "line-elevation-reference") }
  }

  @Test
  fun lineJoinSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineJoin(LineJoin.BEVEL)
    verify { style.setStyleLayerProperty("id", "line-join", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "bevel")
  }

  @Test
  fun lineJoinGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("bevel")

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(LineJoin.BEVEL, layer.lineJoin)
    verify { style.getStyleLayerProperty("id", "line-join") }
  }
  // Expression Tests

  @Test
  fun lineJoinAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineJoin(expression)
    verify { style.setStyleLayerProperty("id", "line-join", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineJoinAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineJoinAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-join") }
  }

  @Test
  fun lineJoinAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineJoinAsExpression)
    verify { style.getStyleLayerProperty("id", "line-join") }
  }

  @Test
  fun lineJoinAsExpressionGetFromLiteral() {
    val value = "bevel"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.lineJoinAsExpression?.toString())
    assertEquals(LineJoin.BEVEL.value, layer.lineJoinAsExpression.toString())
    assertEquals(LineJoin.BEVEL, layer.lineJoin)
    verify { style.getStyleLayerProperty("id", "line-join") }
  }

  @Test
  fun lineMiterLimitSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineMiterLimit(testValue)
    verify { style.setStyleLayerProperty("id", "line-miter-limit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineMiterLimitGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineMiterLimit?.toString())
    verify { style.getStyleLayerProperty("id", "line-miter-limit") }
  }
  // Expression Tests

  @Test
  fun lineMiterLimitAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineMiterLimit(expression)
    verify { style.setStyleLayerProperty("id", "line-miter-limit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineMiterLimitAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineMiterLimitAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-miter-limit") }
  }

  @Test
  fun lineMiterLimitAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineMiterLimitAsExpression)
    verify { style.getStyleLayerProperty("id", "line-miter-limit") }
  }

  @Test
  fun lineMiterLimitAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineMiterLimitAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineMiterLimit!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-miter-limit") }
  }

  @Test
  fun lineRoundLimitSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineRoundLimit(testValue)
    verify { style.setStyleLayerProperty("id", "line-round-limit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineRoundLimitGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineRoundLimit?.toString())
    verify { style.getStyleLayerProperty("id", "line-round-limit") }
  }
  // Expression Tests

  @Test
  fun lineRoundLimitAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineRoundLimit(expression)
    verify { style.setStyleLayerProperty("id", "line-round-limit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineRoundLimitAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineRoundLimitAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-round-limit") }
  }

  @Test
  fun lineRoundLimitAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineRoundLimitAsExpression)
    verify { style.getStyleLayerProperty("id", "line-round-limit") }
  }

  @Test
  fun lineRoundLimitAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineRoundLimitAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineRoundLimit!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-round-limit") }
  }

  @Test
  fun lineSortKeySet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineSortKey(testValue)
    verify { style.setStyleLayerProperty("id", "line-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineSortKeyGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineSortKey?.toString())
    verify { style.getStyleLayerProperty("id", "line-sort-key") }
  }
  // Expression Tests

  @Test
  fun lineSortKeyAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineSortKey(expression)
    verify { style.setStyleLayerProperty("id", "line-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineSortKeyAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineSortKeyAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-sort-key") }
  }

  @Test
  fun lineSortKeyAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineSortKeyAsExpression)
    verify { style.getStyleLayerProperty("id", "line-sort-key") }
  }

  @Test
  fun lineSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineSortKey!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-sort-key") }
  }

  @Test
  fun lineWidthUnitSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineWidthUnit(LineWidthUnit.PIXELS)
    verify { style.setStyleLayerProperty("id", "line-width-unit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "pixels")
  }

  @Test
  fun lineWidthUnitGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("pixels")

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(LineWidthUnit.PIXELS, layer.lineWidthUnit)
    verify { style.getStyleLayerProperty("id", "line-width-unit") }
  }
  // Expression Tests

  @Test
  fun lineWidthUnitAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineWidthUnit(expression)
    verify { style.setStyleLayerProperty("id", "line-width-unit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineWidthUnitAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineWidthUnitAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-width-unit") }
  }

  @Test
  fun lineWidthUnitAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineWidthUnitAsExpression)
    verify { style.getStyleLayerProperty("id", "line-width-unit") }
  }

  @Test
  fun lineWidthUnitAsExpressionGetFromLiteral() {
    val value = "pixels"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.lineWidthUnitAsExpression?.toString())
    assertEquals(LineWidthUnit.PIXELS.value, layer.lineWidthUnitAsExpression.toString())
    assertEquals(LineWidthUnit.PIXELS, layer.lineWidthUnit)
    verify { style.getStyleLayerProperty("id", "line-width-unit") }
  }

  @Test
  fun lineZOffsetSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineZOffset(testValue)
    verify { style.setStyleLayerProperty("id", "line-z-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineZOffsetGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineZOffset?.toString())
    verify { style.getStyleLayerProperty("id", "line-z-offset") }
  }
  // Expression Tests

  @Test
  fun lineZOffsetAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineZOffset(expression)
    verify { style.setStyleLayerProperty("id", "line-z-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineZOffsetAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineZOffsetAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-z-offset") }
  }

  @Test
  fun lineZOffsetAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineZOffsetAsExpression)
    verify { style.getStyleLayerProperty("id", "line-z-offset") }
  }

  @Test
  fun lineZOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineZOffsetAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineZOffset!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-z-offset") }
  }

  @Test
  fun lineBlurSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineBlur(testValue)
    verify { style.setStyleLayerProperty("id", "line-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineBlurGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineBlur?.toString())
    verify { style.getStyleLayerProperty("id", "line-blur") }
  }
  // Expression Tests

  @Test
  fun lineBlurAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBlur(expression)
    verify { style.setStyleLayerProperty("id", "line-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineBlurAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineBlurAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-blur") }
  }

  @Test
  fun lineBlurAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineBlurAsExpression)
    verify { style.getStyleLayerProperty("id", "line-blur") }
  }

  @Test
  fun lineBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineBlur!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-blur") }
  }

  @Test
  fun lineBlurTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBlurTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineBlurTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineBlurTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-blur-transition") }
  }

  @Test
  fun lineBlurTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBlurTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineBorderColorSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.lineBorderColor(testValue)
    verify { style.setStyleLayerProperty("id", "line-border-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun lineBorderColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.lineBorderColor?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-color") }
  }

  @Test
  fun lineBorderColorUseThemeSetAfterInitialization() {
    val layer = lineLayer("id", "source") {}
    val theme = "none"
    layer.bindTo(style)
    layer.lineBorderColorUseTheme(theme)
    verify { style.setStyleLayerProperty("id", "line-border-color-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), theme)
  }

  @Test
  fun lineBorderColorUseThemeSet() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineBorderColorUseTheme(theme)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertTrue(valueSlot.captured.toString().contains("line-border-color-use-theme"))
  }

  @Test
  fun lineBorderColorUseThemeGet() {
    val theme = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(theme)
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(theme.toString(), layer.lineBorderColorUseTheme?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-color-use-theme") }
  }
  // Expression Tests

  @Test
  fun lineBorderColorUseThemeAsExpressionSet() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderColorUseTheme(expression)
    verify { style.setStyleLayerProperty("id", "line-border-color-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun lineBorderColorUseThemeAsExpressionGet() {
    val expression = literal("none")
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineBorderColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-color-use-theme") }
  }

  @Test
  fun lineBorderColorUseThemeAsExpressionGetNull() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(null, layer.lineBorderColorUseThemeAsExpression)
    verify { style.getStyleLayerProperty("id", "line-border-color-use-theme") }
  }

  @Test
  fun lineBorderColorUseThemeAsExpressionGetFromLiteral() {
    val expression = literal("none")
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineBorderColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-color-use-theme") }
  }

  @Test
  fun lineBorderColorUseThemeAsExpressionGetFromString() {
    val testValue = "none"
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") {
      lineBorderColorUseTheme(testValue)
    }
    layer.bindTo(style)
    assertEquals(literal(testValue).toString(), layer.lineBorderColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-color-use-theme") }
  }

  @Test
  fun lineBorderColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderColor(expression)
    verify { style.setStyleLayerProperty("id", "line-border-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineBorderColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineBorderColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-color") }
  }

  @Test
  fun lineBorderColorAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineBorderColorAsExpression)
    verify { style.getStyleLayerProperty("id", "line-border-color") }
  }

  @Test
  fun lineBorderColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineBorderColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.lineBorderColor)
    assertEquals(Color.BLACK, layer.lineBorderColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "line-border-color") }
  }

  @Test
  fun lineBorderColorAsColorIntSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "line-border-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun lineBorderColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.lineBorderColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "line-border-color") }
  }

  @Test
  fun lineBorderColorTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-border-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineBorderColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineBorderColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-border-color-transition") }
  }

  @Test
  fun lineBorderColorTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-border-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineBorderWidthSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineBorderWidth(testValue)
    verify { style.setStyleLayerProperty("id", "line-border-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineBorderWidthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineBorderWidth?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-width") }
  }
  // Expression Tests

  @Test
  fun lineBorderWidthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderWidth(expression)
    verify { style.setStyleLayerProperty("id", "line-border-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineBorderWidthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineBorderWidthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-border-width") }
  }

  @Test
  fun lineBorderWidthAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineBorderWidthAsExpression)
    verify { style.getStyleLayerProperty("id", "line-border-width") }
  }

  @Test
  fun lineBorderWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineBorderWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineBorderWidth!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-border-width") }
  }

  @Test
  fun lineBorderWidthTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderWidthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-border-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineBorderWidthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineBorderWidthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-border-width-transition") }
  }

  @Test
  fun lineBorderWidthTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineBorderWidthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-border-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineColorSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.lineColor(testValue)
    verify { style.setStyleLayerProperty("id", "line-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun lineColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.lineColor?.toString())
    verify { style.getStyleLayerProperty("id", "line-color") }
  }

  @Test
  fun lineColorUseThemeSetAfterInitialization() {
    val layer = lineLayer("id", "source") {}
    val theme = "none"
    layer.bindTo(style)
    layer.lineColorUseTheme(theme)
    verify { style.setStyleLayerProperty("id", "line-color-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), theme)
  }

  @Test
  fun lineColorUseThemeSet() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineColorUseTheme(theme)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertTrue(valueSlot.captured.toString().contains("line-color-use-theme"))
  }

  @Test
  fun lineColorUseThemeGet() {
    val theme = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(theme)
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(theme.toString(), layer.lineColorUseTheme?.toString())
    verify { style.getStyleLayerProperty("id", "line-color-use-theme") }
  }
  // Expression Tests

  @Test
  fun lineColorUseThemeAsExpressionSet() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineColorUseTheme(expression)
    verify { style.setStyleLayerProperty("id", "line-color-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun lineColorUseThemeAsExpressionGet() {
    val expression = literal("none")
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-color-use-theme") }
  }

  @Test
  fun lineColorUseThemeAsExpressionGetNull() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(null, layer.lineColorUseThemeAsExpression)
    verify { style.getStyleLayerProperty("id", "line-color-use-theme") }
  }

  @Test
  fun lineColorUseThemeAsExpressionGetFromLiteral() {
    val expression = literal("none")
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-color-use-theme") }
  }

  @Test
  fun lineColorUseThemeAsExpressionGetFromString() {
    val testValue = "none"
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") {
      lineColorUseTheme(testValue)
    }
    layer.bindTo(style)
    assertEquals(literal(testValue).toString(), layer.lineColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-color-use-theme") }
  }

  @Test
  fun lineColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineColor(expression)
    verify { style.setStyleLayerProperty("id", "line-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-color") }
  }

  @Test
  fun lineColorAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineColorAsExpression)
    verify { style.getStyleLayerProperty("id", "line-color") }
  }

  @Test
  fun lineColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.lineColor)
    assertEquals(Color.BLACK, layer.lineColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "line-color") }
  }

  @Test
  fun lineColorAsColorIntSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "line-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun lineColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.lineColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "line-color") }
  }

  @Test
  fun lineColorTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-color-transition") }
  }

  @Test
  fun lineColorTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineDasharraySet() {
    val layer = lineLayer("id", "source") {}
    val testValue = listOf(1.0, 2.0)
    layer.bindTo(style)
    layer.lineDasharray(testValue)
    verify { style.setStyleLayerProperty("id", "line-dasharray", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[1.0, 2.0]")
  }

  @Test
  fun lineDasharrayGet() {
    val testValue = listOf(1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(1.0, 2.0)
    assertEquals(expectedValue.toString(), layer.lineDasharray?.toString())
    verify { style.getStyleLayerProperty("id", "line-dasharray") }
  }
  // Expression Tests

  @Test
  fun lineDasharrayAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineDasharray(expression)
    verify { style.setStyleLayerProperty("id", "line-dasharray", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineDasharrayAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineDasharrayAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-dasharray") }
  }

  @Test
  fun lineDasharrayAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineDasharrayAsExpression)
    verify { style.getStyleLayerProperty("id", "line-dasharray") }
  }

  @Test
  fun lineDasharrayAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(1.0, 2.0))
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [1.0, 2.0]]", layer.lineDasharrayAsExpression.toString())
    assertEquals(listOf(1.0, 2.0), layer.lineDasharray!!)
    verify { style.getStyleLayerProperty("id", "line-dasharray") }
  }

  @Test
  fun lineDepthOcclusionFactorSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineDepthOcclusionFactor(testValue)
    verify { style.setStyleLayerProperty("id", "line-depth-occlusion-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineDepthOcclusionFactorGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineDepthOcclusionFactor?.toString())
    verify { style.getStyleLayerProperty("id", "line-depth-occlusion-factor") }
  }
  // Expression Tests

  @Test
  fun lineDepthOcclusionFactorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineDepthOcclusionFactor(expression)
    verify { style.setStyleLayerProperty("id", "line-depth-occlusion-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineDepthOcclusionFactorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineDepthOcclusionFactorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-depth-occlusion-factor") }
  }

  @Test
  fun lineDepthOcclusionFactorAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineDepthOcclusionFactorAsExpression)
    verify { style.getStyleLayerProperty("id", "line-depth-occlusion-factor") }
  }

  @Test
  fun lineDepthOcclusionFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineDepthOcclusionFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineDepthOcclusionFactor!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-depth-occlusion-factor") }
  }

  @Test
  fun lineDepthOcclusionFactorTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineDepthOcclusionFactorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-depth-occlusion-factor-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineDepthOcclusionFactorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineDepthOcclusionFactorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-depth-occlusion-factor-transition") }
  }

  @Test
  fun lineDepthOcclusionFactorTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineDepthOcclusionFactorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-depth-occlusion-factor-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineEmissiveStrengthSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineEmissiveStrength(testValue)
    verify { style.setStyleLayerProperty("id", "line-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineEmissiveStrengthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineEmissiveStrength?.toString())
    verify { style.getStyleLayerProperty("id", "line-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun lineEmissiveStrengthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineEmissiveStrength(expression)
    verify { style.setStyleLayerProperty("id", "line-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineEmissiveStrengthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineEmissiveStrengthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-emissive-strength") }
  }

  @Test
  fun lineEmissiveStrengthAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineEmissiveStrengthAsExpression)
    verify { style.getStyleLayerProperty("id", "line-emissive-strength") }
  }

  @Test
  fun lineEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineEmissiveStrength!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-emissive-strength") }
  }

  @Test
  fun lineEmissiveStrengthTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineEmissiveStrengthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineEmissiveStrengthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineEmissiveStrengthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-emissive-strength-transition") }
  }

  @Test
  fun lineEmissiveStrengthTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineEmissiveStrengthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineGapWidthSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineGapWidth(testValue)
    verify { style.setStyleLayerProperty("id", "line-gap-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineGapWidthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineGapWidth?.toString())
    verify { style.getStyleLayerProperty("id", "line-gap-width") }
  }
  // Expression Tests

  @Test
  fun lineGapWidthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineGapWidth(expression)
    verify { style.setStyleLayerProperty("id", "line-gap-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineGapWidthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineGapWidthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-gap-width") }
  }

  @Test
  fun lineGapWidthAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineGapWidthAsExpression)
    verify { style.getStyleLayerProperty("id", "line-gap-width") }
  }

  @Test
  fun lineGapWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineGapWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineGapWidth!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-gap-width") }
  }

  @Test
  fun lineGapWidthTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineGapWidthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-gap-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineGapWidthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineGapWidthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-gap-width-transition") }
  }

  @Test
  fun lineGapWidthTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineGapWidthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-gap-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineGradientSet() {
    val layer = lineLayer("id", "source") {}
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
    layer.lineGradient(testValue)
    verify { style.setStyleLayerProperty("id", "line-gradient", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[interpolate, [linear], [heatmap-density], 0.0, [rgba, 0.0, 0.0, 0.0, 0.0], 1.0, [rgba, 0.0, 255.0, 0.0, 1.0]]")
  }

  @Test
  fun lineGradientGet() {
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
    val layer = lineLayer("id", "source") { }
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
    assertEquals(expectedValue.toString(), layer.lineGradient?.toString())
    verify { style.getStyleLayerProperty("id", "line-gradient") }
  }

  @Test
  fun lineGradientUseThemeSetAfterInitialization() {
    val layer = lineLayer("id", "source") {}
    val theme = "none"
    layer.bindTo(style)
    layer.lineGradientUseTheme(theme)
    verify { style.setStyleLayerProperty("id", "line-gradient-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), theme)
  }

  @Test
  fun lineGradientUseThemeSet() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineGradientUseTheme(theme)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertTrue(valueSlot.captured.toString().contains("line-gradient-use-theme"))
  }

  @Test
  fun lineGradientUseThemeGet() {
    val theme = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(theme)
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(theme.toString(), layer.lineGradientUseTheme?.toString())
    verify { style.getStyleLayerProperty("id", "line-gradient-use-theme") }
  }
  // Expression Tests

  @Test
  fun lineOcclusionOpacitySet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineOcclusionOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "line-occlusion-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineOcclusionOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineOcclusionOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "line-occlusion-opacity") }
  }
  // Expression Tests

  @Test
  fun lineOcclusionOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOcclusionOpacity(expression)
    verify { style.setStyleLayerProperty("id", "line-occlusion-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineOcclusionOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineOcclusionOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-occlusion-opacity") }
  }

  @Test
  fun lineOcclusionOpacityAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineOcclusionOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "line-occlusion-opacity") }
  }

  @Test
  fun lineOcclusionOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineOcclusionOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineOcclusionOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-occlusion-opacity") }
  }

  @Test
  fun lineOcclusionOpacityTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOcclusionOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-occlusion-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineOcclusionOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineOcclusionOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-occlusion-opacity-transition") }
  }

  @Test
  fun lineOcclusionOpacityTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOcclusionOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-occlusion-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineOffsetSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineOffset(testValue)
    verify { style.setStyleLayerProperty("id", "line-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineOffsetGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineOffset?.toString())
    verify { style.getStyleLayerProperty("id", "line-offset") }
  }
  // Expression Tests

  @Test
  fun lineOffsetAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOffset(expression)
    verify { style.setStyleLayerProperty("id", "line-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineOffsetAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineOffsetAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-offset") }
  }

  @Test
  fun lineOffsetAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineOffsetAsExpression)
    verify { style.getStyleLayerProperty("id", "line-offset") }
  }

  @Test
  fun lineOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineOffsetAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineOffset!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-offset") }
  }

  @Test
  fun lineOffsetTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOffsetTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-offset-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineOffsetTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineOffsetTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-offset-transition") }
  }

  @Test
  fun lineOffsetTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOffsetTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-offset-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineOpacitySet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "line-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "line-opacity") }
  }
  // Expression Tests

  @Test
  fun lineOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOpacity(expression)
    verify { style.setStyleLayerProperty("id", "line-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-opacity") }
  }

  @Test
  fun lineOpacityAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "line-opacity") }
  }

  @Test
  fun lineOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-opacity") }
  }

  @Test
  fun lineOpacityTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-opacity-transition") }
  }

  @Test
  fun lineOpacityTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun linePatternSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.linePattern(testValue)
    verify { style.setStyleLayerProperty("id", "line-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun linePatternGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.linePattern?.toString())
    verify { style.getStyleLayerProperty("id", "line-pattern") }
  }
  // Expression Tests

  @Test
  fun linePatternAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.linePattern(expression)
    verify { style.setStyleLayerProperty("id", "line-pattern", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun linePatternAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.linePatternAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-pattern") }
  }

  @Test
  fun linePatternAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.linePatternAsExpression)
    verify { style.getStyleLayerProperty("id", "line-pattern") }
  }

  @Test
  fun linePatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("abc", layer.linePatternAsExpression.toString())
    assertEquals("abc", layer.linePattern)
    verify { style.getStyleLayerProperty("id", "line-pattern") }
  }

  @Test
  fun linePatternCrossFadeSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.linePatternCrossFade(testValue)
    verify { style.setStyleLayerProperty("id", "line-pattern-cross-fade", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun linePatternCrossFadeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.linePatternCrossFade?.toString())
    verify { style.getStyleLayerProperty("id", "line-pattern-cross-fade") }
  }
  // Expression Tests

  @Test
  fun linePatternCrossFadeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.linePatternCrossFade(expression)
    verify { style.setStyleLayerProperty("id", "line-pattern-cross-fade", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun linePatternCrossFadeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.linePatternCrossFadeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-pattern-cross-fade") }
  }

  @Test
  fun linePatternCrossFadeAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.linePatternCrossFadeAsExpression)
    verify { style.getStyleLayerProperty("id", "line-pattern-cross-fade") }
  }

  @Test
  fun linePatternCrossFadeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.linePatternCrossFadeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.linePatternCrossFade!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-pattern-cross-fade") }
  }

  @Test
  fun lineTranslateSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.lineTranslate(testValue)
    verify { style.setStyleLayerProperty("id", "line-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun lineTranslateGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.lineTranslate?.toString())
    verify { style.getStyleLayerProperty("id", "line-translate") }
  }
  // Expression Tests

  @Test
  fun lineTranslateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTranslate(expression)
    verify { style.setStyleLayerProperty("id", "line-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineTranslateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTranslateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-translate") }
  }

  @Test
  fun lineTranslateAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineTranslateAsExpression)
    verify { style.getStyleLayerProperty("id", "line-translate") }
  }

  @Test
  fun lineTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.lineTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.lineTranslate!!)
    verify { style.getStyleLayerProperty("id", "line-translate") }
  }

  @Test
  fun lineTranslateTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTranslateTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineTranslateTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineTranslateTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-translate-transition") }
  }

  @Test
  fun lineTranslateTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTranslateTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineTranslateAnchorSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTranslateAnchor(LineTranslateAnchor.MAP)
    verify { style.setStyleLayerProperty("id", "line-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun lineTranslateAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(LineTranslateAnchor.MAP, layer.lineTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "line-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun lineTranslateAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTranslateAnchor(expression)
    verify { style.setStyleLayerProperty("id", "line-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineTranslateAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTranslateAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-translate-anchor") }
  }

  @Test
  fun lineTranslateAnchorAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineTranslateAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "line-translate-anchor") }
  }

  @Test
  fun lineTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.lineTranslateAnchorAsExpression?.toString())
    assertEquals(LineTranslateAnchor.MAP.value, layer.lineTranslateAnchorAsExpression.toString())
    assertEquals(LineTranslateAnchor.MAP, layer.lineTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "line-translate-anchor") }
  }

  @Test
  fun lineTrimColorSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.lineTrimColor(testValue)
    verify { style.setStyleLayerProperty("id", "line-trim-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun lineTrimColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.lineTrimColor?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-color") }
  }

  @Test
  fun lineTrimColorUseThemeSetAfterInitialization() {
    val layer = lineLayer("id", "source") {}
    val theme = "none"
    layer.bindTo(style)
    layer.lineTrimColorUseTheme(theme)
    verify { style.setStyleLayerProperty("id", "line-trim-color-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), theme)
  }

  @Test
  fun lineTrimColorUseThemeSet() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineTrimColorUseTheme(theme)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertTrue(valueSlot.captured.toString().contains("line-trim-color-use-theme"))
  }

  @Test
  fun lineTrimColorUseThemeGet() {
    val theme = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(theme)
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(theme.toString(), layer.lineTrimColorUseTheme?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-color-use-theme") }
  }
  // Expression Tests

  @Test
  fun lineTrimColorUseThemeAsExpressionSet() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTrimColorUseTheme(expression)
    verify { style.setStyleLayerProperty("id", "line-trim-color-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun lineTrimColorUseThemeAsExpressionGet() {
    val expression = literal("none")
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTrimColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-color-use-theme") }
  }

  @Test
  fun lineTrimColorUseThemeAsExpressionGetNull() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(null, layer.lineTrimColorUseThemeAsExpression)
    verify { style.getStyleLayerProperty("id", "line-trim-color-use-theme") }
  }

  @Test
  fun lineTrimColorUseThemeAsExpressionGetFromLiteral() {
    val expression = literal("none")
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTrimColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-color-use-theme") }
  }

  @Test
  fun lineTrimColorUseThemeAsExpressionGetFromString() {
    val testValue = "none"
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") {
      lineTrimColorUseTheme(testValue)
    }
    layer.bindTo(style)
    assertEquals(literal(testValue).toString(), layer.lineTrimColorUseThemeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-color-use-theme") }
  }

  @Test
  fun lineTrimColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTrimColor(expression)
    verify { style.setStyleLayerProperty("id", "line-trim-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineTrimColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTrimColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-color") }
  }

  @Test
  fun lineTrimColorAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineTrimColorAsExpression)
    verify { style.getStyleLayerProperty("id", "line-trim-color") }
  }

  @Test
  fun lineTrimColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTrimColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.lineTrimColor)
    assertEquals(Color.BLACK, layer.lineTrimColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "line-trim-color") }
  }

  @Test
  fun lineTrimColorAsColorIntSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTrimColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "line-trim-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun lineTrimColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.lineTrimColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "line-trim-color") }
  }

  @Test
  fun lineTrimColorTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTrimColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-trim-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineTrimColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineTrimColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-trim-color-transition") }
  }

  @Test
  fun lineTrimColorTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTrimColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-trim-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineTrimFadeRangeSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.lineTrimFadeRange(testValue)
    verify { style.setStyleLayerProperty("id", "line-trim-fade-range", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun lineTrimFadeRangeGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.lineTrimFadeRange?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-fade-range") }
  }
  // Expression Tests

  @Test
  fun lineTrimFadeRangeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTrimFadeRange(expression)
    verify { style.setStyleLayerProperty("id", "line-trim-fade-range", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineTrimFadeRangeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTrimFadeRangeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-fade-range") }
  }

  @Test
  fun lineTrimFadeRangeAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineTrimFadeRangeAsExpression)
    verify { style.getStyleLayerProperty("id", "line-trim-fade-range") }
  }

  @Test
  fun lineTrimFadeRangeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.lineTrimFadeRangeAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.lineTrimFadeRange!!)
    verify { style.getStyleLayerProperty("id", "line-trim-fade-range") }
  }

  @Test
  fun lineTrimOffsetSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.lineTrimOffset(testValue)
    verify { style.setStyleLayerProperty("id", "line-trim-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun lineTrimOffsetGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.lineTrimOffset?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-offset") }
  }
  // Expression Tests

  @Test
  fun lineTrimOffsetAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineTrimOffset(expression)
    verify { style.setStyleLayerProperty("id", "line-trim-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineTrimOffsetAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineTrimOffsetAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-trim-offset") }
  }

  @Test
  fun lineTrimOffsetAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineTrimOffsetAsExpression)
    verify { style.getStyleLayerProperty("id", "line-trim-offset") }
  }

  @Test
  fun lineTrimOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.lineTrimOffsetAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.lineTrimOffset!!)
    verify { style.getStyleLayerProperty("id", "line-trim-offset") }
  }

  @Test
  fun lineWidthSet() {
    val layer = lineLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.lineWidth(testValue)
    verify { style.setStyleLayerProperty("id", "line-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun lineWidthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.lineWidth?.toString())
    verify { style.getStyleLayerProperty("id", "line-width") }
  }
  // Expression Tests

  @Test
  fun lineWidthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineWidth(expression)
    verify { style.setStyleLayerProperty("id", "line-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun lineWidthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.lineWidthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "line-width") }
  }

  @Test
  fun lineWidthAsExpressionGetNull() {
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.lineWidthAsExpression)
    verify { style.getStyleLayerProperty("id", "line-width") }
  }

  @Test
  fun lineWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.lineWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineWidth!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "line-width") }
  }

  @Test
  fun lineWidthTransitionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineWidthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "line-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun lineWidthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.lineWidthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "line-width-transition") }
  }

  @Test
  fun lineWidthTransitionSetDsl() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.lineWidthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "line-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun visibilitySet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun visibilityAsExpressionSet() {
    val layer = lineLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(literal("none"))
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityAsExpressionGet() {
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns literal("none")

    val layer = lineLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(literal("none"), layer.visibilityAsExpression)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = lineLayer("id", "source") { }
    assertEquals("line", layer.getType())
  }

  @Test
  fun getLineLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("line"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as LineLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("line", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultLineCapTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("butt")

    assertEquals(LineCap.BUTT, LineLayer.defaultLineCap)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cap") }
  }
  // Expression Tests

  @Test
  fun defaultLineCapAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineCapAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cap") }
  }

  @Test
  fun defaultLineCapAsExpressionGetFromLiteral() {
    val value = "butt"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), LineLayer.defaultLineCapAsExpression?.toString())
    assertEquals(LineCap.BUTT.value, LineLayer.defaultLineCapAsExpression.toString())
    assertEquals(LineCap.BUTT, LineLayer.defaultLineCap)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cap") }
  }

  @Test
  fun defaultLineCrossSlopeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineCrossSlope?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cross-slope") }
  }
  // Expression Tests

  @Test
  fun defaultLineCrossSlopeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineCrossSlopeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cross-slope") }
  }

  @Test
  fun defaultLineCrossSlopeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineCrossSlopeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineCrossSlope!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cross-slope") }
  }

  @Test
  fun defaultLineElevationReferenceTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(LineElevationReference.NONE, LineLayer.defaultLineElevationReference)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-elevation-reference") }
  }
  // Expression Tests

  @Test
  fun defaultLineElevationReferenceAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineElevationReferenceAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-elevation-reference") }
  }

  @Test
  fun defaultLineElevationReferenceAsExpressionGetFromLiteral() {
    val value = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), LineLayer.defaultLineElevationReferenceAsExpression?.toString())
    assertEquals(LineElevationReference.NONE.value, LineLayer.defaultLineElevationReferenceAsExpression.toString())
    assertEquals(LineElevationReference.NONE, LineLayer.defaultLineElevationReference)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-elevation-reference") }
  }

  @Test
  fun defaultLineJoinTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("bevel")

    assertEquals(LineJoin.BEVEL, LineLayer.defaultLineJoin)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-join") }
  }
  // Expression Tests

  @Test
  fun defaultLineJoinAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineJoinAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-join") }
  }

  @Test
  fun defaultLineJoinAsExpressionGetFromLiteral() {
    val value = "bevel"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), LineLayer.defaultLineJoinAsExpression?.toString())
    assertEquals(LineJoin.BEVEL.value, LineLayer.defaultLineJoinAsExpression.toString())
    assertEquals(LineJoin.BEVEL, LineLayer.defaultLineJoin)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-join") }
  }

  @Test
  fun defaultLineMiterLimitTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineMiterLimit?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit") }
  }
  // Expression Tests

  @Test
  fun defaultLineMiterLimitAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineMiterLimitAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit") }
  }

  @Test
  fun defaultLineMiterLimitAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineMiterLimitAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineMiterLimit!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit") }
  }

  @Test
  fun defaultLineRoundLimitTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineRoundLimit?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit") }
  }
  // Expression Tests

  @Test
  fun defaultLineRoundLimitAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineRoundLimitAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit") }
  }

  @Test
  fun defaultLineRoundLimitAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineRoundLimitAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineRoundLimit!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit") }
  }

  @Test
  fun defaultLineSortKeyTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineSortKey?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-sort-key") }
  }
  // Expression Tests

  @Test
  fun defaultLineSortKeyAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineSortKeyAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-sort-key") }
  }

  @Test
  fun defaultLineSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineSortKey!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-sort-key") }
  }

  @Test
  fun defaultLineWidthUnitTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("pixels")

    assertEquals(LineWidthUnit.PIXELS, LineLayer.defaultLineWidthUnit)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width-unit") }
  }
  // Expression Tests

  @Test
  fun defaultLineWidthUnitAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineWidthUnitAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width-unit") }
  }

  @Test
  fun defaultLineWidthUnitAsExpressionGetFromLiteral() {
    val value = "pixels"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), LineLayer.defaultLineWidthUnitAsExpression?.toString())
    assertEquals(LineWidthUnit.PIXELS.value, LineLayer.defaultLineWidthUnitAsExpression.toString())
    assertEquals(LineWidthUnit.PIXELS, LineLayer.defaultLineWidthUnit)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width-unit") }
  }

  @Test
  fun defaultLineZOffsetTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineZOffset?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-z-offset") }
  }
  // Expression Tests

  @Test
  fun defaultLineZOffsetAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineZOffsetAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-z-offset") }
  }

  @Test
  fun defaultLineZOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineZOffsetAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineZOffset!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-z-offset") }
  }

  @Test
  fun defaultLineBlurTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineBlur?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur") }
  }
  // Expression Tests

  @Test
  fun defaultLineBlurAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineBlurAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur") }
  }

  @Test
  fun defaultLineBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineBlur!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur") }
  }

  @Test
  fun defaultLineBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineBlurTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur-transition") }
  }

  @Test
  fun defaultLineBorderColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), LineLayer.defaultLineBorderColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color") }
  }

  @Test
  fun defaultLineBorderColorUseThemeTest() {
    val testValue = "default"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    assertEquals(testValue, LineLayer.defaultLineBorderColorUseTheme)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color-use-theme") }
  }

  // Expression Tests

  @Test
  fun defaultLineBorderColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineBorderColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color") }
  }

  @Test
  fun defaultLineBorderColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), LineLayer.defaultLineBorderColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", LineLayer.defaultLineBorderColor)
    assertEquals(Color.BLACK, LineLayer.defaultLineBorderColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color") }
  }

  @Test
  fun defaultLineBorderColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, LineLayer.defaultLineBorderColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color") }
  }

  @Test
  fun defaultLineBorderColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineBorderColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color-transition") }
  }

  @Test
  fun defaultLineBorderWidthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineBorderWidth?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-width") }
  }
  // Expression Tests

  @Test
  fun defaultLineBorderWidthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineBorderWidthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-width") }
  }

  @Test
  fun defaultLineBorderWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineBorderWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineBorderWidth!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-width") }
  }

  @Test
  fun defaultLineBorderWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineBorderWidthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-width-transition") }
  }

  @Test
  fun defaultLineColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), LineLayer.defaultLineColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color") }
  }

  @Test
  fun defaultLineColorUseThemeTest() {
    val testValue = "default"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    assertEquals(testValue, LineLayer.defaultLineColorUseTheme)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color-use-theme") }
  }

  // Expression Tests

  @Test
  fun defaultLineColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color") }
  }

  @Test
  fun defaultLineColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), LineLayer.defaultLineColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", LineLayer.defaultLineColor)
    assertEquals(Color.BLACK, LineLayer.defaultLineColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color") }
  }

  @Test
  fun defaultLineColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, LineLayer.defaultLineColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color") }
  }

  @Test
  fun defaultLineColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color-transition") }
  }

  @Test
  fun defaultLineDasharrayTest() {
    val testValue = listOf(1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(1.0, 2.0)
    assertEquals(expectedValue.toString(), LineLayer.defaultLineDasharray?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray") }
  }
  // Expression Tests

  @Test
  fun defaultLineDasharrayAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineDasharrayAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray") }
  }

  @Test
  fun defaultLineDasharrayAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(1.0, 2.0))
    assertEquals("[literal, [1.0, 2.0]]", LineLayer.defaultLineDasharrayAsExpression.toString())
    assertEquals(listOf(1.0, 2.0), LineLayer.defaultLineDasharray!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray") }
  }

  @Test
  fun defaultLineDepthOcclusionFactorTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineDepthOcclusionFactor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor") }
  }
  // Expression Tests

  @Test
  fun defaultLineDepthOcclusionFactorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineDepthOcclusionFactorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor") }
  }

  @Test
  fun defaultLineDepthOcclusionFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineDepthOcclusionFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineDepthOcclusionFactor!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor") }
  }

  @Test
  fun defaultLineDepthOcclusionFactorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineDepthOcclusionFactorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor-transition") }
  }

  @Test
  fun defaultLineEmissiveStrengthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineEmissiveStrength?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun defaultLineEmissiveStrengthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineEmissiveStrengthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength") }
  }

  @Test
  fun defaultLineEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineEmissiveStrength!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength") }
  }

  @Test
  fun defaultLineEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineEmissiveStrengthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength-transition") }
  }

  @Test
  fun defaultLineGapWidthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineGapWidth?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width") }
  }
  // Expression Tests

  @Test
  fun defaultLineGapWidthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineGapWidthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width") }
  }

  @Test
  fun defaultLineGapWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineGapWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineGapWidth!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width") }
  }

  @Test
  fun defaultLineGapWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineGapWidthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width-transition") }
  }

  @Test
  fun defaultLineGradientUseThemeTest() {
    val testValue = "default"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    assertEquals(testValue, LineLayer.defaultLineGradientUseTheme)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gradient-use-theme") }
  }

  // Expression Tests

  @Test
  fun defaultLineOcclusionOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineOcclusionOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-occlusion-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultLineOcclusionOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineOcclusionOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-occlusion-opacity") }
  }

  @Test
  fun defaultLineOcclusionOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineOcclusionOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineOcclusionOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-occlusion-opacity") }
  }

  @Test
  fun defaultLineOcclusionOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineOcclusionOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-occlusion-opacity-transition") }
  }

  @Test
  fun defaultLineOffsetTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineOffset?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset") }
  }
  // Expression Tests

  @Test
  fun defaultLineOffsetAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineOffsetAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset") }
  }

  @Test
  fun defaultLineOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineOffsetAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineOffset!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset") }
  }

  @Test
  fun defaultLineOffsetTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineOffsetTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset-transition") }
  }

  @Test
  fun defaultLineOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultLineOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity") }
  }

  @Test
  fun defaultLineOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity") }
  }

  @Test
  fun defaultLineOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity-transition") }
  }

  @Test
  fun defaultLinePatternTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), LineLayer.defaultLinePattern?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern") }
  }
  // Expression Tests

  @Test
  fun defaultLinePatternAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLinePatternAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern") }
  }

  @Test
  fun defaultLinePatternAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", LineLayer.defaultLinePatternAsExpression.toString())
    assertEquals("abc", LineLayer.defaultLinePattern)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern") }
  }

  @Test
  fun defaultLinePatternCrossFadeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLinePatternCrossFade?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern-cross-fade") }
  }
  // Expression Tests

  @Test
  fun defaultLinePatternCrossFadeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLinePatternCrossFadeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern-cross-fade") }
  }

  @Test
  fun defaultLinePatternCrossFadeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLinePatternCrossFadeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLinePatternCrossFade!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern-cross-fade") }
  }

  @Test
  fun defaultLineTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), LineLayer.defaultLineTranslate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate") }
  }
  // Expression Tests

  @Test
  fun defaultLineTranslateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineTranslateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate") }
  }

  @Test
  fun defaultLineTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", LineLayer.defaultLineTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), LineLayer.defaultLineTranslate!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate") }
  }

  @Test
  fun defaultLineTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineTranslateTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-transition") }
  }

  @Test
  fun defaultLineTranslateAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(LineTranslateAnchor.MAP, LineLayer.defaultLineTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultLineTranslateAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineTranslateAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-anchor") }
  }

  @Test
  fun defaultLineTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), LineLayer.defaultLineTranslateAnchorAsExpression?.toString())
    assertEquals(LineTranslateAnchor.MAP.value, LineLayer.defaultLineTranslateAnchorAsExpression.toString())
    assertEquals(LineTranslateAnchor.MAP, LineLayer.defaultLineTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-anchor") }
  }

  @Test
  fun defaultLineTrimColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), LineLayer.defaultLineTrimColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color") }
  }

  @Test
  fun defaultLineTrimColorUseThemeTest() {
    val testValue = "default"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    assertEquals(testValue, LineLayer.defaultLineTrimColorUseTheme)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color-use-theme") }
  }

  // Expression Tests

  @Test
  fun defaultLineTrimColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineTrimColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color") }
  }

  @Test
  fun defaultLineTrimColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), LineLayer.defaultLineTrimColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", LineLayer.defaultLineTrimColor)
    assertEquals(Color.BLACK, LineLayer.defaultLineTrimColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color") }
  }

  @Test
  fun defaultLineTrimColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, LineLayer.defaultLineTrimColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color") }
  }

  @Test
  fun defaultLineTrimColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineTrimColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color-transition") }
  }

  @Test
  fun defaultLineTrimFadeRangeTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), LineLayer.defaultLineTrimFadeRange?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-fade-range") }
  }
  // Expression Tests

  @Test
  fun defaultLineTrimFadeRangeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineTrimFadeRangeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-fade-range") }
  }

  @Test
  fun defaultLineTrimFadeRangeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", LineLayer.defaultLineTrimFadeRangeAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), LineLayer.defaultLineTrimFadeRange!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-fade-range") }
  }

  @Test
  fun defaultLineTrimOffsetTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), LineLayer.defaultLineTrimOffset?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-offset") }
  }
  // Expression Tests

  @Test
  fun defaultLineTrimOffsetAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineTrimOffsetAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-offset") }
  }

  @Test
  fun defaultLineTrimOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", LineLayer.defaultLineTrimOffsetAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), LineLayer.defaultLineTrimOffset!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-offset") }
  }

  @Test
  fun defaultLineWidthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LineLayer.defaultLineWidth?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width") }
  }
  // Expression Tests

  @Test
  fun defaultLineWidthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LineLayer.defaultLineWidthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width") }
  }

  @Test
  fun defaultLineWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LineLayer.defaultLineWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LineLayer.defaultLineWidth!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width") }
  }

  @Test
  fun defaultLineWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LineLayer.defaultLineWidthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width-transition") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, LineLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("line", "visibility") }
  }
}

// End of generated file.