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
class SymbolLayerTest {
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
    val layer = symbolLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=symbol}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = symbolLayer("id", "source") {}
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
    val layer = symbolLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=symbol, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = symbolLayer("id", "source") {}
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
    val layer = symbolLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=symbol}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = symbolLayer("id", "source") {}
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
  fun iconAllowOverlapSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.iconAllowOverlap(testValue)
    verify { style.setStyleLayerProperty("id", "icon-allow-overlap", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun iconAllowOverlapGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.iconAllowOverlap?.toString())
    verify { style.getStyleLayerProperty("id", "icon-allow-overlap") }
  }
  // Expression Tests

  @Test
  fun iconAllowOverlapAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconAllowOverlap(expression)
    verify { style.setStyleLayerProperty("id", "icon-allow-overlap", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconAllowOverlapAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconAllowOverlapAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-allow-overlap") }
  }

  @Test
  fun iconAllowOverlapAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconAllowOverlapAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-allow-overlap") }
  }

  @Test
  fun iconAllowOverlapAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.iconAllowOverlapAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.iconAllowOverlap)
    verify { style.getStyleLayerProperty("id", "icon-allow-overlap") }
  }

  @Test
  fun iconAnchorSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconAnchor(IconAnchor.CENTER)
    verify { style.setStyleLayerProperty("id", "icon-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "center")
  }

  @Test
  fun iconAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("center")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(IconAnchor.CENTER, layer.iconAnchor)
    verify { style.getStyleLayerProperty("id", "icon-anchor") }
  }
  // Expression Tests

  @Test
  fun iconAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconAnchor(expression)
    verify { style.setStyleLayerProperty("id", "icon-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-anchor") }
  }

  @Test
  fun iconAnchorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-anchor") }
  }

  @Test
  fun iconAnchorAsExpressionGetFromLiteral() {
    val value = "center"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.iconAnchorAsExpression?.toString())
    assertEquals(IconAnchor.CENTER.value, layer.iconAnchorAsExpression.toString())
    assertEquals(IconAnchor.CENTER, layer.iconAnchor)
    verify { style.getStyleLayerProperty("id", "icon-anchor") }
  }

  @Test
  fun iconIgnorePlacementSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.iconIgnorePlacement(testValue)
    verify { style.setStyleLayerProperty("id", "icon-ignore-placement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun iconIgnorePlacementGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.iconIgnorePlacement?.toString())
    verify { style.getStyleLayerProperty("id", "icon-ignore-placement") }
  }
  // Expression Tests

  @Test
  fun iconIgnorePlacementAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconIgnorePlacement(expression)
    verify { style.setStyleLayerProperty("id", "icon-ignore-placement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconIgnorePlacementAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconIgnorePlacementAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-ignore-placement") }
  }

  @Test
  fun iconIgnorePlacementAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconIgnorePlacementAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-ignore-placement") }
  }

  @Test
  fun iconIgnorePlacementAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.iconIgnorePlacementAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.iconIgnorePlacement)
    verify { style.getStyleLayerProperty("id", "icon-ignore-placement") }
  }

  @Test
  fun iconImageSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.iconImage(testValue)
    verify { style.setStyleLayerProperty("id", "icon-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun iconImageGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.iconImage?.toString())
    verify { style.getStyleLayerProperty("id", "icon-image") }
  }
  // Expression Tests

  @Test
  fun iconImageAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconImage(expression)
    verify { style.setStyleLayerProperty("id", "icon-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconImageAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconImageAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-image") }
  }

  @Test
  fun iconImageAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconImageAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-image") }
  }

  @Test
  fun iconImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("abc", layer.iconImageAsExpression.toString())
    assertEquals("abc", layer.iconImage)
    verify { style.getStyleLayerProperty("id", "icon-image") }
  }

  @Test
  fun iconKeepUprightSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.iconKeepUpright(testValue)
    verify { style.setStyleLayerProperty("id", "icon-keep-upright", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun iconKeepUprightGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.iconKeepUpright?.toString())
    verify { style.getStyleLayerProperty("id", "icon-keep-upright") }
  }
  // Expression Tests

  @Test
  fun iconKeepUprightAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconKeepUpright(expression)
    verify { style.setStyleLayerProperty("id", "icon-keep-upright", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconKeepUprightAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconKeepUprightAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-keep-upright") }
  }

  @Test
  fun iconKeepUprightAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconKeepUprightAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-keep-upright") }
  }

  @Test
  fun iconKeepUprightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.iconKeepUprightAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.iconKeepUpright)
    verify { style.getStyleLayerProperty("id", "icon-keep-upright") }
  }

  @Test
  fun iconOffsetSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.iconOffset(testValue)
    verify { style.setStyleLayerProperty("id", "icon-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun iconOffsetGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.iconOffset?.toString())
    verify { style.getStyleLayerProperty("id", "icon-offset") }
  }
  // Expression Tests

  @Test
  fun iconOffsetAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconOffset(expression)
    verify { style.setStyleLayerProperty("id", "icon-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconOffsetAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconOffsetAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-offset") }
  }

  @Test
  fun iconOffsetAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconOffsetAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-offset") }
  }

  @Test
  fun iconOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.iconOffsetAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.iconOffset!!)
    verify { style.getStyleLayerProperty("id", "icon-offset") }
  }

  @Test
  fun iconOptionalSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.iconOptional(testValue)
    verify { style.setStyleLayerProperty("id", "icon-optional", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun iconOptionalGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.iconOptional?.toString())
    verify { style.getStyleLayerProperty("id", "icon-optional") }
  }
  // Expression Tests

  @Test
  fun iconOptionalAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconOptional(expression)
    verify { style.setStyleLayerProperty("id", "icon-optional", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconOptionalAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconOptionalAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-optional") }
  }

  @Test
  fun iconOptionalAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconOptionalAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-optional") }
  }

  @Test
  fun iconOptionalAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.iconOptionalAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.iconOptional)
    verify { style.getStyleLayerProperty("id", "icon-optional") }
  }

  @Test
  fun iconPaddingSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconPadding(testValue)
    verify { style.setStyleLayerProperty("id", "icon-padding", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconPaddingGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconPadding?.toString())
    verify { style.getStyleLayerProperty("id", "icon-padding") }
  }
  // Expression Tests

  @Test
  fun iconPaddingAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconPadding(expression)
    verify { style.setStyleLayerProperty("id", "icon-padding", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconPaddingAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconPaddingAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-padding") }
  }

  @Test
  fun iconPaddingAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconPaddingAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-padding") }
  }

  @Test
  fun iconPaddingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconPaddingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconPadding!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-padding") }
  }

  @Test
  fun iconPitchAlignmentSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconPitchAlignment(IconPitchAlignment.MAP)
    verify { style.setStyleLayerProperty("id", "icon-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun iconPitchAlignmentGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(IconPitchAlignment.MAP, layer.iconPitchAlignment)
    verify { style.getStyleLayerProperty("id", "icon-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun iconPitchAlignmentAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconPitchAlignment(expression)
    verify { style.setStyleLayerProperty("id", "icon-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconPitchAlignmentAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconPitchAlignmentAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-pitch-alignment") }
  }

  @Test
  fun iconPitchAlignmentAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconPitchAlignmentAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-pitch-alignment") }
  }

  @Test
  fun iconPitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.iconPitchAlignmentAsExpression?.toString())
    assertEquals(IconPitchAlignment.MAP.value, layer.iconPitchAlignmentAsExpression.toString())
    assertEquals(IconPitchAlignment.MAP, layer.iconPitchAlignment)
    verify { style.getStyleLayerProperty("id", "icon-pitch-alignment") }
  }

  @Test
  fun iconRotateSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconRotate(testValue)
    verify { style.setStyleLayerProperty("id", "icon-rotate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconRotateGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconRotate?.toString())
    verify { style.getStyleLayerProperty("id", "icon-rotate") }
  }
  // Expression Tests

  @Test
  fun iconRotateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconRotate(expression)
    verify { style.setStyleLayerProperty("id", "icon-rotate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconRotateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconRotateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-rotate") }
  }

  @Test
  fun iconRotateAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconRotateAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-rotate") }
  }

  @Test
  fun iconRotateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconRotateAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconRotate!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-rotate") }
  }

  @Test
  fun iconRotationAlignmentSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconRotationAlignment(IconRotationAlignment.MAP)
    verify { style.setStyleLayerProperty("id", "icon-rotation-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun iconRotationAlignmentGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(IconRotationAlignment.MAP, layer.iconRotationAlignment)
    verify { style.getStyleLayerProperty("id", "icon-rotation-alignment") }
  }
  // Expression Tests

  @Test
  fun iconRotationAlignmentAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconRotationAlignment(expression)
    verify { style.setStyleLayerProperty("id", "icon-rotation-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconRotationAlignmentAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconRotationAlignmentAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-rotation-alignment") }
  }

  @Test
  fun iconRotationAlignmentAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconRotationAlignmentAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-rotation-alignment") }
  }

  @Test
  fun iconRotationAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.iconRotationAlignmentAsExpression?.toString())
    assertEquals(IconRotationAlignment.MAP.value, layer.iconRotationAlignmentAsExpression.toString())
    assertEquals(IconRotationAlignment.MAP, layer.iconRotationAlignment)
    verify { style.getStyleLayerProperty("id", "icon-rotation-alignment") }
  }

  @Test
  fun iconSizeSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconSize(testValue)
    verify { style.setStyleLayerProperty("id", "icon-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconSizeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconSize?.toString())
    verify { style.getStyleLayerProperty("id", "icon-size") }
  }
  // Expression Tests

  @Test
  fun iconSizeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconSize(expression)
    verify { style.setStyleLayerProperty("id", "icon-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconSizeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconSizeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-size") }
  }

  @Test
  fun iconSizeAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconSizeAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-size") }
  }

  @Test
  fun iconSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconSize!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-size") }
  }

  @Test
  fun iconTextFitSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTextFit(IconTextFit.NONE)
    verify { style.setStyleLayerProperty("id", "icon-text-fit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun iconTextFitGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(IconTextFit.NONE, layer.iconTextFit)
    verify { style.getStyleLayerProperty("id", "icon-text-fit") }
  }
  // Expression Tests

  @Test
  fun iconTextFitAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTextFit(expression)
    verify { style.setStyleLayerProperty("id", "icon-text-fit", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconTextFitAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconTextFitAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-text-fit") }
  }

  @Test
  fun iconTextFitAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconTextFitAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-text-fit") }
  }

  @Test
  fun iconTextFitAsExpressionGetFromLiteral() {
    val value = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.iconTextFitAsExpression?.toString())
    assertEquals(IconTextFit.NONE.value, layer.iconTextFitAsExpression.toString())
    assertEquals(IconTextFit.NONE, layer.iconTextFit)
    verify { style.getStyleLayerProperty("id", "icon-text-fit") }
  }

  @Test
  fun iconTextFitPaddingSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0, 2.0, 3.0)
    layer.bindTo(style)
    layer.iconTextFitPadding(testValue)
    verify { style.setStyleLayerProperty("id", "icon-text-fit-padding", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0, 2.0, 3.0]")
  }

  @Test
  fun iconTextFitPaddingGet() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0, 2.0, 3.0)
    assertEquals(expectedValue.toString(), layer.iconTextFitPadding?.toString())
    verify { style.getStyleLayerProperty("id", "icon-text-fit-padding") }
  }
  // Expression Tests

  @Test
  fun iconTextFitPaddingAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTextFitPadding(expression)
    verify { style.setStyleLayerProperty("id", "icon-text-fit-padding", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconTextFitPaddingAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconTextFitPaddingAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-text-fit-padding") }
  }

  @Test
  fun iconTextFitPaddingAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconTextFitPaddingAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-text-fit-padding") }
  }

  @Test
  fun iconTextFitPaddingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0, 3.0))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0, 3.0]]", layer.iconTextFitPaddingAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0, 3.0), layer.iconTextFitPadding!!)
    verify { style.getStyleLayerProperty("id", "icon-text-fit-padding") }
  }

  @Test
  fun symbolAvoidEdgesSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.symbolAvoidEdges(testValue)
    verify { style.setStyleLayerProperty("id", "symbol-avoid-edges", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun symbolAvoidEdgesGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.symbolAvoidEdges?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-avoid-edges") }
  }
  // Expression Tests

  @Test
  fun symbolAvoidEdgesAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.symbolAvoidEdges(expression)
    verify { style.setStyleLayerProperty("id", "symbol-avoid-edges", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun symbolAvoidEdgesAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.symbolAvoidEdgesAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-avoid-edges") }
  }

  @Test
  fun symbolAvoidEdgesAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.symbolAvoidEdgesAsExpression)
    verify { style.getStyleLayerProperty("id", "symbol-avoid-edges") }
  }

  @Test
  fun symbolAvoidEdgesAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.symbolAvoidEdgesAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.symbolAvoidEdges)
    verify { style.getStyleLayerProperty("id", "symbol-avoid-edges") }
  }

  @Test
  fun symbolPlacementSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.symbolPlacement(SymbolPlacement.POINT)
    verify { style.setStyleLayerProperty("id", "symbol-placement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "point")
  }

  @Test
  fun symbolPlacementGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("point")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(SymbolPlacement.POINT, layer.symbolPlacement)
    verify { style.getStyleLayerProperty("id", "symbol-placement") }
  }
  // Expression Tests

  @Test
  fun symbolPlacementAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.symbolPlacement(expression)
    verify { style.setStyleLayerProperty("id", "symbol-placement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun symbolPlacementAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.symbolPlacementAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-placement") }
  }

  @Test
  fun symbolPlacementAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.symbolPlacementAsExpression)
    verify { style.getStyleLayerProperty("id", "symbol-placement") }
  }

  @Test
  fun symbolPlacementAsExpressionGetFromLiteral() {
    val value = "point"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.symbolPlacementAsExpression?.toString())
    assertEquals(SymbolPlacement.POINT.value, layer.symbolPlacementAsExpression.toString())
    assertEquals(SymbolPlacement.POINT, layer.symbolPlacement)
    verify { style.getStyleLayerProperty("id", "symbol-placement") }
  }

  @Test
  fun symbolSortKeySet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.symbolSortKey(testValue)
    verify { style.setStyleLayerProperty("id", "symbol-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun symbolSortKeyGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.symbolSortKey?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-sort-key") }
  }
  // Expression Tests

  @Test
  fun symbolSortKeyAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.symbolSortKey(expression)
    verify { style.setStyleLayerProperty("id", "symbol-sort-key", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun symbolSortKeyAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.symbolSortKeyAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-sort-key") }
  }

  @Test
  fun symbolSortKeyAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.symbolSortKeyAsExpression)
    verify { style.getStyleLayerProperty("id", "symbol-sort-key") }
  }

  @Test
  fun symbolSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.symbolSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.symbolSortKey!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "symbol-sort-key") }
  }

  @Test
  fun symbolSpacingSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.symbolSpacing(testValue)
    verify { style.setStyleLayerProperty("id", "symbol-spacing", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun symbolSpacingGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.symbolSpacing?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-spacing") }
  }
  // Expression Tests

  @Test
  fun symbolSpacingAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.symbolSpacing(expression)
    verify { style.setStyleLayerProperty("id", "symbol-spacing", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun symbolSpacingAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.symbolSpacingAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-spacing") }
  }

  @Test
  fun symbolSpacingAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.symbolSpacingAsExpression)
    verify { style.getStyleLayerProperty("id", "symbol-spacing") }
  }

  @Test
  fun symbolSpacingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.symbolSpacingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.symbolSpacing!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "symbol-spacing") }
  }

  @Test
  fun symbolZOrderSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.symbolZOrder(SymbolZOrder.AUTO)
    verify { style.setStyleLayerProperty("id", "symbol-z-order", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "auto")
  }

  @Test
  fun symbolZOrderGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("auto")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(SymbolZOrder.AUTO, layer.symbolZOrder)
    verify { style.getStyleLayerProperty("id", "symbol-z-order") }
  }
  // Expression Tests

  @Test
  fun symbolZOrderAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.symbolZOrder(expression)
    verify { style.setStyleLayerProperty("id", "symbol-z-order", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun symbolZOrderAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.symbolZOrderAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "symbol-z-order") }
  }

  @Test
  fun symbolZOrderAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.symbolZOrderAsExpression)
    verify { style.getStyleLayerProperty("id", "symbol-z-order") }
  }

  @Test
  fun symbolZOrderAsExpressionGetFromLiteral() {
    val value = "auto"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.symbolZOrderAsExpression?.toString())
    assertEquals(SymbolZOrder.AUTO.value, layer.symbolZOrderAsExpression.toString())
    assertEquals(SymbolZOrder.AUTO, layer.symbolZOrder)
    verify { style.getStyleLayerProperty("id", "symbol-z-order") }
  }

  @Test
  fun textAllowOverlapSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.textAllowOverlap(testValue)
    verify { style.setStyleLayerProperty("id", "text-allow-overlap", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun textAllowOverlapGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.textAllowOverlap?.toString())
    verify { style.getStyleLayerProperty("id", "text-allow-overlap") }
  }
  // Expression Tests

  @Test
  fun textAllowOverlapAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textAllowOverlap(expression)
    verify { style.setStyleLayerProperty("id", "text-allow-overlap", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textAllowOverlapAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textAllowOverlapAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-allow-overlap") }
  }

  @Test
  fun textAllowOverlapAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textAllowOverlapAsExpression)
    verify { style.getStyleLayerProperty("id", "text-allow-overlap") }
  }

  @Test
  fun textAllowOverlapAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.textAllowOverlapAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.textAllowOverlap)
    verify { style.getStyleLayerProperty("id", "text-allow-overlap") }
  }

  @Test
  fun textAnchorSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textAnchor(TextAnchor.CENTER)
    verify { style.setStyleLayerProperty("id", "text-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "center")
  }

  @Test
  fun textAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("center")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(TextAnchor.CENTER, layer.textAnchor)
    verify { style.getStyleLayerProperty("id", "text-anchor") }
  }
  // Expression Tests

  @Test
  fun textAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textAnchor(expression)
    verify { style.setStyleLayerProperty("id", "text-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-anchor") }
  }

  @Test
  fun textAnchorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "text-anchor") }
  }

  @Test
  fun textAnchorAsExpressionGetFromLiteral() {
    val value = "center"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.textAnchorAsExpression?.toString())
    assertEquals(TextAnchor.CENTER.value, layer.textAnchorAsExpression.toString())
    assertEquals(TextAnchor.CENTER, layer.textAnchor)
    verify { style.getStyleLayerProperty("id", "text-anchor") }
  }

  @Test
  fun textFieldSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = formatted {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    layer.bindTo(style)
    layer.textField(testValue)
    verify { style.setStyleLayerProperty("id", "text-field", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[[cyan, {text-color=rgba(0, 255, 255, 1), font-scale=0.9, text-font=[Open Sans Regular, Arial Unicode MS Regular]}], [black, {text-color=rgba(0, 0, 0, 1), font-scale=2.0, text-font=[Open Sans Regular, Arial Unicode MS Regular]}]]")
  }

  @Test
  fun textFieldGet() {
    val formatExpression = format {
      formatSection("cyan") {
        fontScale(0.9)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(Color.CYAN)
      }
      formatSection("black") {
        fontScale(2.0)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(rgba(0.0, 0.0, 0.0, 1.0))
      }
    }
    every { styleProperty.value } returns formatExpression
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = formatted {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    assertEquals(expectedValue.toString(), layer.textField?.toString())
    verify { style.getStyleLayerProperty("id", "text-field") }
  }
  // Expression Tests

  @Test
  fun textFieldAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textField(expression)
    verify { style.setStyleLayerProperty("id", "text-field", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textFieldAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textFieldAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-field") }
  }

  @Test
  fun textFieldAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textFieldAsExpression)
    verify { style.getStyleLayerProperty("id", "text-field") }
  }

  @Test
  fun textFieldAsExpressionGetFromLiteral() {
    val formatExpression = format {
      formatSection("cyan") {
        fontScale(0.9)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(Color.CYAN)
      }
      formatSection("black") {
        fontScale(2.0)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(rgba(0.0, 0.0, 0.0, 1.0))
      }
    }
    every { styleProperty.value } returns formatExpression
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(formatExpression.toString(), layer.textFieldAsExpression.toString())
    val expectedValue = formatted {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    assertEquals(expectedValue, layer.textField)
    verify { style.getStyleLayerProperty("id", "text-field") }
  }

  @Test
  fun textFieldAsStringSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textField("abc")
    verify { style.setStyleLayerProperty("id", "text-field", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun textFieldAsStringGet() {
    val formatExpression = format {
      formatSection("abc")
    }
    every { styleProperty.value } returns formatExpression
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("abc", layer.textFieldAsString)
    verify { style.getStyleLayerProperty("id", "text-field") }
  }

  @Test
  fun textFieldSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textField {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    verify { style.setStyleLayerProperty("id", "text-field", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[[cyan, {text-color=rgba(0, 255, 255, 1), font-scale=0.9, text-font=[Open Sans Regular, Arial Unicode MS Regular]}], [black, {text-color=rgba(0, 0, 0, 1), font-scale=2.0, text-font=[Open Sans Regular, Arial Unicode MS Regular]}]]")
  }

  @Test
  fun textFontSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf("a", "b", "c")
    layer.bindTo(style)
    layer.textFont(testValue)
    verify { style.setStyleLayerProperty("id", "text-font", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[a, b, c]")
  }

  @Test
  fun textFontGet() {
    val testValue = listOf("a", "b", "c")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf("a", "b", "c")
    assertEquals(expectedValue.toString(), layer.textFont?.toString())
    verify { style.getStyleLayerProperty("id", "text-font") }
  }
  // Expression Tests

  @Test
  fun textFontAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textFont(expression)
    verify { style.setStyleLayerProperty("id", "text-font", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textFontAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textFontAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-font") }
  }

  @Test
  fun textFontAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textFontAsExpression)
    verify { style.getStyleLayerProperty("id", "text-font") }
  }

  @Test
  fun textFontAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [a, b, c]]", layer.textFontAsExpression.toString())
    assertEquals(listOf("a", "b", "c"), layer.textFont!!)
    verify { style.getStyleLayerProperty("id", "text-font") }
  }

  @Test
  fun textIgnorePlacementSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.textIgnorePlacement(testValue)
    verify { style.setStyleLayerProperty("id", "text-ignore-placement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun textIgnorePlacementGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.textIgnorePlacement?.toString())
    verify { style.getStyleLayerProperty("id", "text-ignore-placement") }
  }
  // Expression Tests

  @Test
  fun textIgnorePlacementAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textIgnorePlacement(expression)
    verify { style.setStyleLayerProperty("id", "text-ignore-placement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textIgnorePlacementAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textIgnorePlacementAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-ignore-placement") }
  }

  @Test
  fun textIgnorePlacementAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textIgnorePlacementAsExpression)
    verify { style.getStyleLayerProperty("id", "text-ignore-placement") }
  }

  @Test
  fun textIgnorePlacementAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.textIgnorePlacementAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.textIgnorePlacement)
    verify { style.getStyleLayerProperty("id", "text-ignore-placement") }
  }

  @Test
  fun textJustifySet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textJustify(TextJustify.AUTO)
    verify { style.setStyleLayerProperty("id", "text-justify", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "auto")
  }

  @Test
  fun textJustifyGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("auto")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(TextJustify.AUTO, layer.textJustify)
    verify { style.getStyleLayerProperty("id", "text-justify") }
  }
  // Expression Tests

  @Test
  fun textJustifyAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textJustify(expression)
    verify { style.setStyleLayerProperty("id", "text-justify", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textJustifyAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textJustifyAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-justify") }
  }

  @Test
  fun textJustifyAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textJustifyAsExpression)
    verify { style.getStyleLayerProperty("id", "text-justify") }
  }

  @Test
  fun textJustifyAsExpressionGetFromLiteral() {
    val value = "auto"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.textJustifyAsExpression?.toString())
    assertEquals(TextJustify.AUTO.value, layer.textJustifyAsExpression.toString())
    assertEquals(TextJustify.AUTO, layer.textJustify)
    verify { style.getStyleLayerProperty("id", "text-justify") }
  }

  @Test
  fun textKeepUprightSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.textKeepUpright(testValue)
    verify { style.setStyleLayerProperty("id", "text-keep-upright", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun textKeepUprightGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.textKeepUpright?.toString())
    verify { style.getStyleLayerProperty("id", "text-keep-upright") }
  }
  // Expression Tests

  @Test
  fun textKeepUprightAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textKeepUpright(expression)
    verify { style.setStyleLayerProperty("id", "text-keep-upright", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textKeepUprightAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textKeepUprightAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-keep-upright") }
  }

  @Test
  fun textKeepUprightAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textKeepUprightAsExpression)
    verify { style.getStyleLayerProperty("id", "text-keep-upright") }
  }

  @Test
  fun textKeepUprightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.textKeepUprightAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.textKeepUpright)
    verify { style.getStyleLayerProperty("id", "text-keep-upright") }
  }

  @Test
  fun textLetterSpacingSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textLetterSpacing(testValue)
    verify { style.setStyleLayerProperty("id", "text-letter-spacing", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textLetterSpacingGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textLetterSpacing?.toString())
    verify { style.getStyleLayerProperty("id", "text-letter-spacing") }
  }
  // Expression Tests

  @Test
  fun textLetterSpacingAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textLetterSpacing(expression)
    verify { style.setStyleLayerProperty("id", "text-letter-spacing", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textLetterSpacingAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textLetterSpacingAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-letter-spacing") }
  }

  @Test
  fun textLetterSpacingAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textLetterSpacingAsExpression)
    verify { style.getStyleLayerProperty("id", "text-letter-spacing") }
  }

  @Test
  fun textLetterSpacingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textLetterSpacingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textLetterSpacing!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-letter-spacing") }
  }

  @Test
  fun textLineHeightSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textLineHeight(testValue)
    verify { style.setStyleLayerProperty("id", "text-line-height", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textLineHeightGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textLineHeight?.toString())
    verify { style.getStyleLayerProperty("id", "text-line-height") }
  }
  // Expression Tests

  @Test
  fun textLineHeightAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textLineHeight(expression)
    verify { style.setStyleLayerProperty("id", "text-line-height", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textLineHeightAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textLineHeightAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-line-height") }
  }

  @Test
  fun textLineHeightAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textLineHeightAsExpression)
    verify { style.getStyleLayerProperty("id", "text-line-height") }
  }

  @Test
  fun textLineHeightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textLineHeightAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textLineHeight!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-line-height") }
  }

  @Test
  fun textMaxAngleSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textMaxAngle(testValue)
    verify { style.setStyleLayerProperty("id", "text-max-angle", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textMaxAngleGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textMaxAngle?.toString())
    verify { style.getStyleLayerProperty("id", "text-max-angle") }
  }
  // Expression Tests

  @Test
  fun textMaxAngleAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textMaxAngle(expression)
    verify { style.setStyleLayerProperty("id", "text-max-angle", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textMaxAngleAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textMaxAngleAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-max-angle") }
  }

  @Test
  fun textMaxAngleAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textMaxAngleAsExpression)
    verify { style.getStyleLayerProperty("id", "text-max-angle") }
  }

  @Test
  fun textMaxAngleAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textMaxAngleAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textMaxAngle!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-max-angle") }
  }

  @Test
  fun textMaxWidthSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textMaxWidth(testValue)
    verify { style.setStyleLayerProperty("id", "text-max-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textMaxWidthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textMaxWidth?.toString())
    verify { style.getStyleLayerProperty("id", "text-max-width") }
  }
  // Expression Tests

  @Test
  fun textMaxWidthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textMaxWidth(expression)
    verify { style.setStyleLayerProperty("id", "text-max-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textMaxWidthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textMaxWidthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-max-width") }
  }

  @Test
  fun textMaxWidthAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textMaxWidthAsExpression)
    verify { style.getStyleLayerProperty("id", "text-max-width") }
  }

  @Test
  fun textMaxWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textMaxWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textMaxWidth!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-max-width") }
  }

  @Test
  fun textOffsetSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.textOffset(testValue)
    verify { style.setStyleLayerProperty("id", "text-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun textOffsetGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.textOffset?.toString())
    verify { style.getStyleLayerProperty("id", "text-offset") }
  }
  // Expression Tests

  @Test
  fun textOffsetAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textOffset(expression)
    verify { style.setStyleLayerProperty("id", "text-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textOffsetAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textOffsetAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-offset") }
  }

  @Test
  fun textOffsetAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textOffsetAsExpression)
    verify { style.getStyleLayerProperty("id", "text-offset") }
  }

  @Test
  fun textOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.textOffsetAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.textOffset!!)
    verify { style.getStyleLayerProperty("id", "text-offset") }
  }

  @Test
  fun textOptionalSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = true
    layer.bindTo(style)
    layer.textOptional(testValue)
    verify { style.setStyleLayerProperty("id", "text-optional", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun textOptionalGet() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = true
    assertEquals(expectedValue.toString(), layer.textOptional?.toString())
    verify { style.getStyleLayerProperty("id", "text-optional") }
  }
  // Expression Tests

  @Test
  fun textOptionalAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textOptional(expression)
    verify { style.setStyleLayerProperty("id", "text-optional", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textOptionalAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textOptionalAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-optional") }
  }

  @Test
  fun textOptionalAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textOptionalAsExpression)
    verify { style.getStyleLayerProperty("id", "text-optional") }
  }

  @Test
  fun textOptionalAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("true", layer.textOptionalAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, layer.textOptional)
    verify { style.getStyleLayerProperty("id", "text-optional") }
  }

  @Test
  fun textPaddingSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textPadding(testValue)
    verify { style.setStyleLayerProperty("id", "text-padding", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textPaddingGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textPadding?.toString())
    verify { style.getStyleLayerProperty("id", "text-padding") }
  }
  // Expression Tests

  @Test
  fun textPaddingAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textPadding(expression)
    verify { style.setStyleLayerProperty("id", "text-padding", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textPaddingAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textPaddingAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-padding") }
  }

  @Test
  fun textPaddingAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textPaddingAsExpression)
    verify { style.getStyleLayerProperty("id", "text-padding") }
  }

  @Test
  fun textPaddingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textPaddingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textPadding!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-padding") }
  }

  @Test
  fun textPitchAlignmentSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textPitchAlignment(TextPitchAlignment.MAP)
    verify { style.setStyleLayerProperty("id", "text-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun textPitchAlignmentGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(TextPitchAlignment.MAP, layer.textPitchAlignment)
    verify { style.getStyleLayerProperty("id", "text-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun textPitchAlignmentAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textPitchAlignment(expression)
    verify { style.setStyleLayerProperty("id", "text-pitch-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textPitchAlignmentAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textPitchAlignmentAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-pitch-alignment") }
  }

  @Test
  fun textPitchAlignmentAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textPitchAlignmentAsExpression)
    verify { style.getStyleLayerProperty("id", "text-pitch-alignment") }
  }

  @Test
  fun textPitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.textPitchAlignmentAsExpression?.toString())
    assertEquals(TextPitchAlignment.MAP.value, layer.textPitchAlignmentAsExpression.toString())
    assertEquals(TextPitchAlignment.MAP, layer.textPitchAlignment)
    verify { style.getStyleLayerProperty("id", "text-pitch-alignment") }
  }

  @Test
  fun textRadialOffsetSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textRadialOffset(testValue)
    verify { style.setStyleLayerProperty("id", "text-radial-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textRadialOffsetGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textRadialOffset?.toString())
    verify { style.getStyleLayerProperty("id", "text-radial-offset") }
  }
  // Expression Tests

  @Test
  fun textRadialOffsetAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textRadialOffset(expression)
    verify { style.setStyleLayerProperty("id", "text-radial-offset", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textRadialOffsetAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textRadialOffsetAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-radial-offset") }
  }

  @Test
  fun textRadialOffsetAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textRadialOffsetAsExpression)
    verify { style.getStyleLayerProperty("id", "text-radial-offset") }
  }

  @Test
  fun textRadialOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textRadialOffsetAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textRadialOffset!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-radial-offset") }
  }

  @Test
  fun textRotateSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textRotate(testValue)
    verify { style.setStyleLayerProperty("id", "text-rotate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textRotateGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textRotate?.toString())
    verify { style.getStyleLayerProperty("id", "text-rotate") }
  }
  // Expression Tests

  @Test
  fun textRotateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textRotate(expression)
    verify { style.setStyleLayerProperty("id", "text-rotate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textRotateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textRotateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-rotate") }
  }

  @Test
  fun textRotateAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textRotateAsExpression)
    verify { style.getStyleLayerProperty("id", "text-rotate") }
  }

  @Test
  fun textRotateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textRotateAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textRotate!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-rotate") }
  }

  @Test
  fun textRotationAlignmentSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textRotationAlignment(TextRotationAlignment.MAP)
    verify { style.setStyleLayerProperty("id", "text-rotation-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun textRotationAlignmentGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(TextRotationAlignment.MAP, layer.textRotationAlignment)
    verify { style.getStyleLayerProperty("id", "text-rotation-alignment") }
  }
  // Expression Tests

  @Test
  fun textRotationAlignmentAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textRotationAlignment(expression)
    verify { style.setStyleLayerProperty("id", "text-rotation-alignment", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textRotationAlignmentAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textRotationAlignmentAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-rotation-alignment") }
  }

  @Test
  fun textRotationAlignmentAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textRotationAlignmentAsExpression)
    verify { style.getStyleLayerProperty("id", "text-rotation-alignment") }
  }

  @Test
  fun textRotationAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.textRotationAlignmentAsExpression?.toString())
    assertEquals(TextRotationAlignment.MAP.value, layer.textRotationAlignmentAsExpression.toString())
    assertEquals(TextRotationAlignment.MAP, layer.textRotationAlignment)
    verify { style.getStyleLayerProperty("id", "text-rotation-alignment") }
  }

  @Test
  fun textSizeSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textSize(testValue)
    verify { style.setStyleLayerProperty("id", "text-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textSizeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textSize?.toString())
    verify { style.getStyleLayerProperty("id", "text-size") }
  }
  // Expression Tests

  @Test
  fun textSizeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textSize(expression)
    verify { style.setStyleLayerProperty("id", "text-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textSizeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textSizeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-size") }
  }

  @Test
  fun textSizeAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textSizeAsExpression)
    verify { style.getStyleLayerProperty("id", "text-size") }
  }

  @Test
  fun textSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textSize!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-size") }
  }

  @Test
  fun textTransformSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textTransform(TextTransform.NONE)
    verify { style.setStyleLayerProperty("id", "text-transform", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun textTransformGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(TextTransform.NONE, layer.textTransform)
    verify { style.getStyleLayerProperty("id", "text-transform") }
  }
  // Expression Tests

  @Test
  fun textTransformAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textTransform(expression)
    verify { style.setStyleLayerProperty("id", "text-transform", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textTransformAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textTransformAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-transform") }
  }

  @Test
  fun textTransformAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textTransformAsExpression)
    verify { style.getStyleLayerProperty("id", "text-transform") }
  }

  @Test
  fun textTransformAsExpressionGetFromLiteral() {
    val value = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.textTransformAsExpression?.toString())
    assertEquals(TextTransform.NONE.value, layer.textTransformAsExpression.toString())
    assertEquals(TextTransform.NONE, layer.textTransform)
    verify { style.getStyleLayerProperty("id", "text-transform") }
  }

  @Test
  fun textVariableAnchorSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf("center", "left")
    layer.bindTo(style)
    layer.textVariableAnchor(testValue)
    verify { style.setStyleLayerProperty("id", "text-variable-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[center, left]")
  }

  @Test
  fun textVariableAnchorGet() {
    val testValue = listOf("center", "left")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf("center", "left")
    assertEquals(expectedValue.toString(), layer.textVariableAnchor?.toString())
    verify { style.getStyleLayerProperty("id", "text-variable-anchor") }
  }
  // Expression Tests

  @Test
  fun textVariableAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textVariableAnchor(expression)
    verify { style.setStyleLayerProperty("id", "text-variable-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textVariableAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textVariableAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-variable-anchor") }
  }

  @Test
  fun textVariableAnchorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textVariableAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "text-variable-anchor") }
  }

  @Test
  fun textVariableAnchorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("center", "left"))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [center, left]]", layer.textVariableAnchorAsExpression.toString())
    assertEquals(listOf("center", "left"), layer.textVariableAnchor!!)
    verify { style.getStyleLayerProperty("id", "text-variable-anchor") }
  }

  @Test
  fun textWritingModeSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf("horizontal", "vertical")
    layer.bindTo(style)
    layer.textWritingMode(testValue)
    verify { style.setStyleLayerProperty("id", "text-writing-mode", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[horizontal, vertical]")
  }

  @Test
  fun textWritingModeGet() {
    val testValue = listOf("horizontal", "vertical")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf("horizontal", "vertical")
    assertEquals(expectedValue.toString(), layer.textWritingMode?.toString())
    verify { style.getStyleLayerProperty("id", "text-writing-mode") }
  }
  // Expression Tests

  @Test
  fun textWritingModeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textWritingMode(expression)
    verify { style.setStyleLayerProperty("id", "text-writing-mode", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textWritingModeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textWritingModeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-writing-mode") }
  }

  @Test
  fun textWritingModeAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textWritingModeAsExpression)
    verify { style.getStyleLayerProperty("id", "text-writing-mode") }
  }

  @Test
  fun textWritingModeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("horizontal", "vertical"))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [horizontal, vertical]]", layer.textWritingModeAsExpression.toString())
    assertEquals(listOf("horizontal", "vertical"), layer.textWritingMode!!)
    verify { style.getStyleLayerProperty("id", "text-writing-mode") }
  }

  @Test
  fun iconColorSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.iconColor(testValue)
    verify { style.setStyleLayerProperty("id", "icon-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun iconColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.iconColor?.toString())
    verify { style.getStyleLayerProperty("id", "icon-color") }
  }
  // Expression Tests

  @Test
  fun iconColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconColor(expression)
    verify { style.setStyleLayerProperty("id", "icon-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-color") }
  }

  @Test
  fun iconColorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconColorAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-color") }
  }

  @Test
  fun iconColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.iconColor)
    assertEquals(Color.BLACK, layer.iconColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "icon-color") }
  }

  @Test
  fun iconColorAsColorIntSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "icon-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun iconColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.iconColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "icon-color") }
  }

  @Test
  fun iconColorTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-color-transition") }
  }

  @Test
  fun iconColorTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconEmissiveStrengthSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconEmissiveStrength(testValue)
    verify { style.setStyleLayerProperty("id", "icon-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconEmissiveStrengthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconEmissiveStrength?.toString())
    verify { style.getStyleLayerProperty("id", "icon-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun iconEmissiveStrengthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconEmissiveStrength(expression)
    verify { style.setStyleLayerProperty("id", "icon-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconEmissiveStrengthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconEmissiveStrengthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-emissive-strength") }
  }

  @Test
  fun iconEmissiveStrengthAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconEmissiveStrengthAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-emissive-strength") }
  }

  @Test
  fun iconEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconEmissiveStrength!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-emissive-strength") }
  }

  @Test
  fun iconEmissiveStrengthTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconEmissiveStrengthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconEmissiveStrengthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconEmissiveStrengthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-emissive-strength-transition") }
  }

  @Test
  fun iconEmissiveStrengthTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconEmissiveStrengthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconHaloBlurSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconHaloBlur(testValue)
    verify { style.setStyleLayerProperty("id", "icon-halo-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconHaloBlurGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconHaloBlur?.toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-blur") }
  }
  // Expression Tests

  @Test
  fun iconHaloBlurAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloBlur(expression)
    verify { style.setStyleLayerProperty("id", "icon-halo-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconHaloBlurAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconHaloBlurAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-blur") }
  }

  @Test
  fun iconHaloBlurAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconHaloBlurAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-halo-blur") }
  }

  @Test
  fun iconHaloBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconHaloBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconHaloBlur!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-halo-blur") }
  }

  @Test
  fun iconHaloBlurTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloBlurTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-halo-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconHaloBlurTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconHaloBlurTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-blur-transition") }
  }

  @Test
  fun iconHaloBlurTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloBlurTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-halo-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconHaloColorSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.iconHaloColor(testValue)
    verify { style.setStyleLayerProperty("id", "icon-halo-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun iconHaloColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.iconHaloColor?.toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-color") }
  }
  // Expression Tests

  @Test
  fun iconHaloColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloColor(expression)
    verify { style.setStyleLayerProperty("id", "icon-halo-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconHaloColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconHaloColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-color") }
  }

  @Test
  fun iconHaloColorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconHaloColorAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-halo-color") }
  }

  @Test
  fun iconHaloColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconHaloColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.iconHaloColor)
    assertEquals(Color.BLACK, layer.iconHaloColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "icon-halo-color") }
  }

  @Test
  fun iconHaloColorAsColorIntSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "icon-halo-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun iconHaloColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.iconHaloColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "icon-halo-color") }
  }

  @Test
  fun iconHaloColorTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-halo-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconHaloColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconHaloColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-color-transition") }
  }

  @Test
  fun iconHaloColorTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-halo-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconHaloWidthSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconHaloWidth(testValue)
    verify { style.setStyleLayerProperty("id", "icon-halo-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconHaloWidthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconHaloWidth?.toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-width") }
  }
  // Expression Tests

  @Test
  fun iconHaloWidthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloWidth(expression)
    verify { style.setStyleLayerProperty("id", "icon-halo-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconHaloWidthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconHaloWidthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-width") }
  }

  @Test
  fun iconHaloWidthAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconHaloWidthAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-halo-width") }
  }

  @Test
  fun iconHaloWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconHaloWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconHaloWidth!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-halo-width") }
  }

  @Test
  fun iconHaloWidthTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloWidthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-halo-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconHaloWidthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconHaloWidthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-halo-width-transition") }
  }

  @Test
  fun iconHaloWidthTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconHaloWidthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-halo-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconImageCrossFadeSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconImageCrossFade(testValue)
    verify { style.setStyleLayerProperty("id", "icon-image-cross-fade", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconImageCrossFadeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconImageCrossFade?.toString())
    verify { style.getStyleLayerProperty("id", "icon-image-cross-fade") }
  }
  // Expression Tests

  @Test
  fun iconImageCrossFadeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconImageCrossFade(expression)
    verify { style.setStyleLayerProperty("id", "icon-image-cross-fade", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconImageCrossFadeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconImageCrossFadeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-image-cross-fade") }
  }

  @Test
  fun iconImageCrossFadeAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconImageCrossFadeAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-image-cross-fade") }
  }

  @Test
  fun iconImageCrossFadeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconImageCrossFadeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconImageCrossFade!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-image-cross-fade") }
  }

  @Test
  fun iconImageCrossFadeTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconImageCrossFadeTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-image-cross-fade-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconImageCrossFadeTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconImageCrossFadeTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-image-cross-fade-transition") }
  }

  @Test
  fun iconImageCrossFadeTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconImageCrossFadeTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-image-cross-fade-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconOpacitySet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.iconOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "icon-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun iconOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.iconOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "icon-opacity") }
  }
  // Expression Tests

  @Test
  fun iconOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconOpacity(expression)
    verify { style.setStyleLayerProperty("id", "icon-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-opacity") }
  }

  @Test
  fun iconOpacityAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-opacity") }
  }

  @Test
  fun iconOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.iconOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "icon-opacity") }
  }

  @Test
  fun iconOpacityTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-opacity-transition") }
  }

  @Test
  fun iconOpacityTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconTranslateSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.iconTranslate(testValue)
    verify { style.setStyleLayerProperty("id", "icon-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun iconTranslateGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.iconTranslate?.toString())
    verify { style.getStyleLayerProperty("id", "icon-translate") }
  }
  // Expression Tests

  @Test
  fun iconTranslateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTranslate(expression)
    verify { style.setStyleLayerProperty("id", "icon-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconTranslateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconTranslateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-translate") }
  }

  @Test
  fun iconTranslateAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconTranslateAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-translate") }
  }

  @Test
  fun iconTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.iconTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.iconTranslate!!)
    verify { style.getStyleLayerProperty("id", "icon-translate") }
  }

  @Test
  fun iconTranslateTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTranslateTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "icon-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconTranslateTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.iconTranslateTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "icon-translate-transition") }
  }

  @Test
  fun iconTranslateTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTranslateTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "icon-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun iconTranslateAnchorSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTranslateAnchor(IconTranslateAnchor.MAP)
    verify { style.setStyleLayerProperty("id", "icon-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun iconTranslateAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(IconTranslateAnchor.MAP, layer.iconTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "icon-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun iconTranslateAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.iconTranslateAnchor(expression)
    verify { style.setStyleLayerProperty("id", "icon-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun iconTranslateAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.iconTranslateAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "icon-translate-anchor") }
  }

  @Test
  fun iconTranslateAnchorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.iconTranslateAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "icon-translate-anchor") }
  }

  @Test
  fun iconTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.iconTranslateAnchorAsExpression?.toString())
    assertEquals(IconTranslateAnchor.MAP.value, layer.iconTranslateAnchorAsExpression.toString())
    assertEquals(IconTranslateAnchor.MAP, layer.iconTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "icon-translate-anchor") }
  }

  @Test
  fun textColorSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.textColor(testValue)
    verify { style.setStyleLayerProperty("id", "text-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun textColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.textColor?.toString())
    verify { style.getStyleLayerProperty("id", "text-color") }
  }
  // Expression Tests

  @Test
  fun textColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textColor(expression)
    verify { style.setStyleLayerProperty("id", "text-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-color") }
  }

  @Test
  fun textColorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textColorAsExpression)
    verify { style.getStyleLayerProperty("id", "text-color") }
  }

  @Test
  fun textColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.textColor)
    assertEquals(Color.BLACK, layer.textColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "text-color") }
  }

  @Test
  fun textColorAsColorIntSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "text-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun textColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.textColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "text-color") }
  }

  @Test
  fun textColorTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "text-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.textColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "text-color-transition") }
  }

  @Test
  fun textColorTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "text-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textEmissiveStrengthSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textEmissiveStrength(testValue)
    verify { style.setStyleLayerProperty("id", "text-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textEmissiveStrengthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textEmissiveStrength?.toString())
    verify { style.getStyleLayerProperty("id", "text-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun textEmissiveStrengthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textEmissiveStrength(expression)
    verify { style.setStyleLayerProperty("id", "text-emissive-strength", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textEmissiveStrengthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textEmissiveStrengthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-emissive-strength") }
  }

  @Test
  fun textEmissiveStrengthAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textEmissiveStrengthAsExpression)
    verify { style.getStyleLayerProperty("id", "text-emissive-strength") }
  }

  @Test
  fun textEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textEmissiveStrength!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-emissive-strength") }
  }

  @Test
  fun textEmissiveStrengthTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textEmissiveStrengthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "text-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textEmissiveStrengthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.textEmissiveStrengthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "text-emissive-strength-transition") }
  }

  @Test
  fun textEmissiveStrengthTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textEmissiveStrengthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "text-emissive-strength-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textHaloBlurSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textHaloBlur(testValue)
    verify { style.setStyleLayerProperty("id", "text-halo-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textHaloBlurGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textHaloBlur?.toString())
    verify { style.getStyleLayerProperty("id", "text-halo-blur") }
  }
  // Expression Tests

  @Test
  fun textHaloBlurAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloBlur(expression)
    verify { style.setStyleLayerProperty("id", "text-halo-blur", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textHaloBlurAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textHaloBlurAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-halo-blur") }
  }

  @Test
  fun textHaloBlurAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textHaloBlurAsExpression)
    verify { style.getStyleLayerProperty("id", "text-halo-blur") }
  }

  @Test
  fun textHaloBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textHaloBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textHaloBlur!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-halo-blur") }
  }

  @Test
  fun textHaloBlurTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloBlurTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "text-halo-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textHaloBlurTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.textHaloBlurTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "text-halo-blur-transition") }
  }

  @Test
  fun textHaloBlurTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloBlurTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "text-halo-blur-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textHaloColorSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.textHaloColor(testValue)
    verify { style.setStyleLayerProperty("id", "text-halo-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun textHaloColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.textHaloColor?.toString())
    verify { style.getStyleLayerProperty("id", "text-halo-color") }
  }
  // Expression Tests

  @Test
  fun textHaloColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloColor(expression)
    verify { style.setStyleLayerProperty("id", "text-halo-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textHaloColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textHaloColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-halo-color") }
  }

  @Test
  fun textHaloColorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textHaloColorAsExpression)
    verify { style.getStyleLayerProperty("id", "text-halo-color") }
  }

  @Test
  fun textHaloColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textHaloColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.textHaloColor)
    assertEquals(Color.BLACK, layer.textHaloColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "text-halo-color") }
  }

  @Test
  fun textHaloColorAsColorIntSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "text-halo-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun textHaloColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.textHaloColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "text-halo-color") }
  }

  @Test
  fun textHaloColorTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "text-halo-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textHaloColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.textHaloColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "text-halo-color-transition") }
  }

  @Test
  fun textHaloColorTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "text-halo-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textHaloWidthSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textHaloWidth(testValue)
    verify { style.setStyleLayerProperty("id", "text-halo-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textHaloWidthGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textHaloWidth?.toString())
    verify { style.getStyleLayerProperty("id", "text-halo-width") }
  }
  // Expression Tests

  @Test
  fun textHaloWidthAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloWidth(expression)
    verify { style.setStyleLayerProperty("id", "text-halo-width", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textHaloWidthAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textHaloWidthAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-halo-width") }
  }

  @Test
  fun textHaloWidthAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textHaloWidthAsExpression)
    verify { style.getStyleLayerProperty("id", "text-halo-width") }
  }

  @Test
  fun textHaloWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textHaloWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textHaloWidth!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-halo-width") }
  }

  @Test
  fun textHaloWidthTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloWidthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "text-halo-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textHaloWidthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.textHaloWidthTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "text-halo-width-transition") }
  }

  @Test
  fun textHaloWidthTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textHaloWidthTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "text-halo-width-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textOpacitySet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.textOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "text-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun textOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.textOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "text-opacity") }
  }
  // Expression Tests

  @Test
  fun textOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textOpacity(expression)
    verify { style.setStyleLayerProperty("id", "text-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-opacity") }
  }

  @Test
  fun textOpacityAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "text-opacity") }
  }

  @Test
  fun textOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.textOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "text-opacity") }
  }

  @Test
  fun textOpacityTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "text-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.textOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "text-opacity-transition") }
  }

  @Test
  fun textOpacityTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "text-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textTranslateSet() {
    val layer = symbolLayer("id", "source") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.textTranslate(testValue)
    verify { style.setStyleLayerProperty("id", "text-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun textTranslateGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.textTranslate?.toString())
    verify { style.getStyleLayerProperty("id", "text-translate") }
  }
  // Expression Tests

  @Test
  fun textTranslateAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textTranslate(expression)
    verify { style.setStyleLayerProperty("id", "text-translate", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textTranslateAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textTranslateAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-translate") }
  }

  @Test
  fun textTranslateAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textTranslateAsExpression)
    verify { style.getStyleLayerProperty("id", "text-translate") }
  }

  @Test
  fun textTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.textTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.textTranslate!!)
    verify { style.getStyleLayerProperty("id", "text-translate") }
  }

  @Test
  fun textTranslateTransitionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textTranslateTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "text-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textTranslateTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.textTranslateTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "text-translate-transition") }
  }

  @Test
  fun textTranslateTransitionSetDsl() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textTranslateTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "text-translate-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun textTranslateAnchorSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textTranslateAnchor(TextTranslateAnchor.MAP)
    verify { style.setStyleLayerProperty("id", "text-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "map")
  }

  @Test
  fun textTranslateAnchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(TextTranslateAnchor.MAP, layer.textTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "text-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun textTranslateAnchorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.textTranslateAnchor(expression)
    verify { style.setStyleLayerProperty("id", "text-translate-anchor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun textTranslateAnchorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.textTranslateAnchorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "text-translate-anchor") }
  }

  @Test
  fun textTranslateAnchorAsExpressionGetNull() {
    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.textTranslateAnchorAsExpression)
    verify { style.getStyleLayerProperty("id", "text-translate-anchor") }
  }

  @Test
  fun textTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.textTranslateAnchorAsExpression?.toString())
    assertEquals(TextTranslateAnchor.MAP.value, layer.textTranslateAnchorAsExpression.toString())
    assertEquals(TextTranslateAnchor.MAP, layer.textTranslateAnchor)
    verify { style.getStyleLayerProperty("id", "text-translate-anchor") }
  }

  @Test
  fun visibilitySet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun visibilityAsExpressionSet() {
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(literal("none"))
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityAsExpressionGet() {
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns literal("none")

    val layer = symbolLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(literal("none"), layer.visibilityAsExpression)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = symbolLayer("id", "source") { }
    assertEquals("symbol", layer.getType())
  }

  @Test
  fun getSymbolLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("symbol"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as SymbolLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("symbol", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultIconAllowOverlapTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconAllowOverlap?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap") }
  }
  // Expression Tests

  @Test
  fun defaultIconAllowOverlapAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconAllowOverlapAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap") }
  }

  @Test
  fun defaultIconAllowOverlapAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultIconAllowOverlapAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultIconAllowOverlap)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap") }
  }

  @Test
  fun defaultIconAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("center")

    assertEquals(IconAnchor.CENTER, SymbolLayer.defaultIconAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultIconAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-anchor") }
  }

  @Test
  fun defaultIconAnchorAsExpressionGetFromLiteral() {
    val value = "center"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultIconAnchorAsExpression?.toString())
    assertEquals(IconAnchor.CENTER.value, SymbolLayer.defaultIconAnchorAsExpression.toString())
    assertEquals(IconAnchor.CENTER, SymbolLayer.defaultIconAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-anchor") }
  }

  @Test
  fun defaultIconIgnorePlacementTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconIgnorePlacement?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement") }
  }
  // Expression Tests

  @Test
  fun defaultIconIgnorePlacementAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconIgnorePlacementAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement") }
  }

  @Test
  fun defaultIconIgnorePlacementAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultIconIgnorePlacementAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultIconIgnorePlacement)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement") }
  }

  @Test
  fun defaultIconImageTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconImage?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image") }
  }
  // Expression Tests

  @Test
  fun defaultIconImageAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconImageAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image") }
  }

  @Test
  fun defaultIconImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", SymbolLayer.defaultIconImageAsExpression.toString())
    assertEquals("abc", SymbolLayer.defaultIconImage)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image") }
  }

  @Test
  fun defaultIconKeepUprightTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconKeepUpright?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright") }
  }
  // Expression Tests

  @Test
  fun defaultIconKeepUprightAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconKeepUprightAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright") }
  }

  @Test
  fun defaultIconKeepUprightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultIconKeepUprightAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultIconKeepUpright)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright") }
  }

  @Test
  fun defaultIconOffsetTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconOffset?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-offset") }
  }
  // Expression Tests

  @Test
  fun defaultIconOffsetAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconOffsetAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-offset") }
  }

  @Test
  fun defaultIconOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", SymbolLayer.defaultIconOffsetAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), SymbolLayer.defaultIconOffset!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-offset") }
  }

  @Test
  fun defaultIconOptionalTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconOptional?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional") }
  }
  // Expression Tests

  @Test
  fun defaultIconOptionalAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconOptionalAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional") }
  }

  @Test
  fun defaultIconOptionalAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultIconOptionalAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultIconOptional)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional") }
  }

  @Test
  fun defaultIconPaddingTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconPadding?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding") }
  }
  // Expression Tests

  @Test
  fun defaultIconPaddingAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconPaddingAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding") }
  }

  @Test
  fun defaultIconPaddingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconPaddingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconPadding!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding") }
  }

  @Test
  fun defaultIconPitchAlignmentTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(IconPitchAlignment.MAP, SymbolLayer.defaultIconPitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun defaultIconPitchAlignmentAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconPitchAlignmentAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment") }
  }

  @Test
  fun defaultIconPitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultIconPitchAlignmentAsExpression?.toString())
    assertEquals(IconPitchAlignment.MAP.value, SymbolLayer.defaultIconPitchAlignmentAsExpression.toString())
    assertEquals(IconPitchAlignment.MAP, SymbolLayer.defaultIconPitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment") }
  }

  @Test
  fun defaultIconRotateTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconRotate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotate") }
  }
  // Expression Tests

  @Test
  fun defaultIconRotateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconRotateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotate") }
  }

  @Test
  fun defaultIconRotateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconRotateAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconRotate!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotate") }
  }

  @Test
  fun defaultIconRotationAlignmentTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(IconRotationAlignment.MAP, SymbolLayer.defaultIconRotationAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment") }
  }
  // Expression Tests

  @Test
  fun defaultIconRotationAlignmentAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconRotationAlignmentAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment") }
  }

  @Test
  fun defaultIconRotationAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultIconRotationAlignmentAsExpression?.toString())
    assertEquals(IconRotationAlignment.MAP.value, SymbolLayer.defaultIconRotationAlignmentAsExpression.toString())
    assertEquals(IconRotationAlignment.MAP, SymbolLayer.defaultIconRotationAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment") }
  }

  @Test
  fun defaultIconSizeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconSize?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-size") }
  }
  // Expression Tests

  @Test
  fun defaultIconSizeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconSizeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-size") }
  }

  @Test
  fun defaultIconSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconSize!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-size") }
  }

  @Test
  fun defaultIconTextFitTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(IconTextFit.NONE, SymbolLayer.defaultIconTextFit)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit") }
  }
  // Expression Tests

  @Test
  fun defaultIconTextFitAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconTextFitAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit") }
  }

  @Test
  fun defaultIconTextFitAsExpressionGetFromLiteral() {
    val value = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultIconTextFitAsExpression?.toString())
    assertEquals(IconTextFit.NONE.value, SymbolLayer.defaultIconTextFitAsExpression.toString())
    assertEquals(IconTextFit.NONE, SymbolLayer.defaultIconTextFit)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit") }
  }

  @Test
  fun defaultIconTextFitPaddingTest() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0, 2.0, 3.0)
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconTextFitPadding?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit-padding") }
  }
  // Expression Tests

  @Test
  fun defaultIconTextFitPaddingAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconTextFitPaddingAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit-padding") }
  }

  @Test
  fun defaultIconTextFitPaddingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0, 3.0))
    assertEquals("[literal, [0.0, 1.0, 2.0, 3.0]]", SymbolLayer.defaultIconTextFitPaddingAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0, 3.0), SymbolLayer.defaultIconTextFitPadding!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit-padding") }
  }

  @Test
  fun defaultSymbolAvoidEdgesTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultSymbolAvoidEdges?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges") }
  }
  // Expression Tests

  @Test
  fun defaultSymbolAvoidEdgesAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultSymbolAvoidEdgesAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges") }
  }

  @Test
  fun defaultSymbolAvoidEdgesAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultSymbolAvoidEdgesAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultSymbolAvoidEdges)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges") }
  }

  @Test
  fun defaultSymbolPlacementTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("point")

    assertEquals(SymbolPlacement.POINT, SymbolLayer.defaultSymbolPlacement)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement") }
  }
  // Expression Tests

  @Test
  fun defaultSymbolPlacementAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultSymbolPlacementAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement") }
  }

  @Test
  fun defaultSymbolPlacementAsExpressionGetFromLiteral() {
    val value = "point"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultSymbolPlacementAsExpression?.toString())
    assertEquals(SymbolPlacement.POINT.value, SymbolLayer.defaultSymbolPlacementAsExpression.toString())
    assertEquals(SymbolPlacement.POINT, SymbolLayer.defaultSymbolPlacement)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement") }
  }

  @Test
  fun defaultSymbolSortKeyTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultSymbolSortKey?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-sort-key") }
  }
  // Expression Tests

  @Test
  fun defaultSymbolSortKeyAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultSymbolSortKeyAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-sort-key") }
  }

  @Test
  fun defaultSymbolSortKeyAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultSymbolSortKeyAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultSymbolSortKey!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-sort-key") }
  }

  @Test
  fun defaultSymbolSpacingTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultSymbolSpacing?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing") }
  }
  // Expression Tests

  @Test
  fun defaultSymbolSpacingAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultSymbolSpacingAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing") }
  }

  @Test
  fun defaultSymbolSpacingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultSymbolSpacingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultSymbolSpacing!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing") }
  }

  @Test
  fun defaultSymbolZOrderTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("auto")

    assertEquals(SymbolZOrder.AUTO, SymbolLayer.defaultSymbolZOrder)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order") }
  }
  // Expression Tests

  @Test
  fun defaultSymbolZOrderAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultSymbolZOrderAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order") }
  }

  @Test
  fun defaultSymbolZOrderAsExpressionGetFromLiteral() {
    val value = "auto"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultSymbolZOrderAsExpression?.toString())
    assertEquals(SymbolZOrder.AUTO.value, SymbolLayer.defaultSymbolZOrderAsExpression.toString())
    assertEquals(SymbolZOrder.AUTO, SymbolLayer.defaultSymbolZOrder)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order") }
  }

  @Test
  fun defaultTextAllowOverlapTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextAllowOverlap?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap") }
  }
  // Expression Tests

  @Test
  fun defaultTextAllowOverlapAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextAllowOverlapAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap") }
  }

  @Test
  fun defaultTextAllowOverlapAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultTextAllowOverlapAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultTextAllowOverlap)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap") }
  }

  @Test
  fun defaultTextAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("center")

    assertEquals(TextAnchor.CENTER, SymbolLayer.defaultTextAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultTextAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-anchor") }
  }

  @Test
  fun defaultTextAnchorAsExpressionGetFromLiteral() {
    val value = "center"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultTextAnchorAsExpression?.toString())
    assertEquals(TextAnchor.CENTER.value, SymbolLayer.defaultTextAnchorAsExpression.toString())
    assertEquals(TextAnchor.CENTER, SymbolLayer.defaultTextAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-anchor") }
  }

  @Test
  fun defaultTextFieldTest() {
    val formatExpression = format {
      formatSection("cyan") {
        fontScale(0.9)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(Color.CYAN)
      }
      formatSection("black") {
        fontScale(2.0)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(rgba(0.0, 0.0, 0.0, 1.0))
      }
    }
    every { styleProperty.value } returns formatExpression
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    val expectedValue = formatted {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextField?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-field") }
  }
  // Expression Tests

  @Test
  fun defaultTextFieldAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextFieldAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-field") }
  }

  @Test
  fun defaultTextFieldAsExpressionGetFromLiteral() {
    val formatExpression = format {
      formatSection("cyan") {
        fontScale(0.9)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(Color.CYAN)
      }
      formatSection("black") {
        fontScale(2.0)
        textFont(
          listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
        )
        textColor(rgba(0.0, 0.0, 0.0, 1.0))
      }
    }
    every { styleProperty.value } returns formatExpression
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(formatExpression.toString(), SymbolLayer.defaultTextFieldAsExpression.toString())
    val expectedValue = formatted {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    assertEquals(expectedValue, SymbolLayer.defaultTextField)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-field") }
  }

  @Test
  fun defaultTextFieldAsStringTest() {
    val formatExpression = format {
      formatSection("abc")
    }
    every { styleProperty.value } returns formatExpression
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals("abc", SymbolLayer.defaultTextFieldAsString)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-field") }
  }

  @Test
  fun defaultTextFontTest() {
    val testValue = listOf("a", "b", "c")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf("a", "b", "c")
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextFont?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font") }
  }
  // Expression Tests

  @Test
  fun defaultTextFontAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextFontAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font") }
  }

  @Test
  fun defaultTextFontAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    assertEquals("[literal, [a, b, c]]", SymbolLayer.defaultTextFontAsExpression.toString())
    assertEquals(listOf("a", "b", "c"), SymbolLayer.defaultTextFont!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font") }
  }

  @Test
  fun defaultTextIgnorePlacementTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextIgnorePlacement?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement") }
  }
  // Expression Tests

  @Test
  fun defaultTextIgnorePlacementAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextIgnorePlacementAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement") }
  }

  @Test
  fun defaultTextIgnorePlacementAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultTextIgnorePlacementAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultTextIgnorePlacement)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement") }
  }

  @Test
  fun defaultTextJustifyTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("auto")

    assertEquals(TextJustify.AUTO, SymbolLayer.defaultTextJustify)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-justify") }
  }
  // Expression Tests

  @Test
  fun defaultTextJustifyAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextJustifyAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-justify") }
  }

  @Test
  fun defaultTextJustifyAsExpressionGetFromLiteral() {
    val value = "auto"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultTextJustifyAsExpression?.toString())
    assertEquals(TextJustify.AUTO.value, SymbolLayer.defaultTextJustifyAsExpression.toString())
    assertEquals(TextJustify.AUTO, SymbolLayer.defaultTextJustify)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-justify") }
  }

  @Test
  fun defaultTextKeepUprightTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextKeepUpright?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright") }
  }
  // Expression Tests

  @Test
  fun defaultTextKeepUprightAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextKeepUprightAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright") }
  }

  @Test
  fun defaultTextKeepUprightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultTextKeepUprightAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultTextKeepUpright)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright") }
  }

  @Test
  fun defaultTextLetterSpacingTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextLetterSpacing?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-letter-spacing") }
  }
  // Expression Tests

  @Test
  fun defaultTextLetterSpacingAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextLetterSpacingAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-letter-spacing") }
  }

  @Test
  fun defaultTextLetterSpacingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextLetterSpacingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextLetterSpacing!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-letter-spacing") }
  }

  @Test
  fun defaultTextLineHeightTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextLineHeight?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-line-height") }
  }
  // Expression Tests

  @Test
  fun defaultTextLineHeightAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextLineHeightAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-line-height") }
  }

  @Test
  fun defaultTextLineHeightAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextLineHeightAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextLineHeight!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-line-height") }
  }

  @Test
  fun defaultTextMaxAngleTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextMaxAngle?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle") }
  }
  // Expression Tests

  @Test
  fun defaultTextMaxAngleAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextMaxAngleAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle") }
  }

  @Test
  fun defaultTextMaxAngleAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextMaxAngleAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextMaxAngle!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle") }
  }

  @Test
  fun defaultTextMaxWidthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextMaxWidth?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-width") }
  }
  // Expression Tests

  @Test
  fun defaultTextMaxWidthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextMaxWidthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-width") }
  }

  @Test
  fun defaultTextMaxWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextMaxWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextMaxWidth!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-width") }
  }

  @Test
  fun defaultTextOffsetTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextOffset?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-offset") }
  }
  // Expression Tests

  @Test
  fun defaultTextOffsetAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextOffsetAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-offset") }
  }

  @Test
  fun defaultTextOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", SymbolLayer.defaultTextOffsetAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), SymbolLayer.defaultTextOffset!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-offset") }
  }

  @Test
  fun defaultTextOptionalTest() {
    val testValue = true
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = true
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextOptional?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional") }
  }
  // Expression Tests

  @Test
  fun defaultTextOptionalAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextOptionalAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional") }
  }

  @Test
  fun defaultTextOptionalAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    assertEquals("true", SymbolLayer.defaultTextOptionalAsExpression.toString())
    val expectedValue = true
    assertEquals(expectedValue, SymbolLayer.defaultTextOptional)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional") }
  }

  @Test
  fun defaultTextPaddingTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextPadding?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding") }
  }
  // Expression Tests

  @Test
  fun defaultTextPaddingAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextPaddingAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding") }
  }

  @Test
  fun defaultTextPaddingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextPaddingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextPadding!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding") }
  }

  @Test
  fun defaultTextPitchAlignmentTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(TextPitchAlignment.MAP, SymbolLayer.defaultTextPitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment") }
  }
  // Expression Tests

  @Test
  fun defaultTextPitchAlignmentAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextPitchAlignmentAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment") }
  }

  @Test
  fun defaultTextPitchAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultTextPitchAlignmentAsExpression?.toString())
    assertEquals(TextPitchAlignment.MAP.value, SymbolLayer.defaultTextPitchAlignmentAsExpression.toString())
    assertEquals(TextPitchAlignment.MAP, SymbolLayer.defaultTextPitchAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment") }
  }

  @Test
  fun defaultTextRadialOffsetTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextRadialOffset?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-radial-offset") }
  }
  // Expression Tests

  @Test
  fun defaultTextRadialOffsetAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextRadialOffsetAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-radial-offset") }
  }

  @Test
  fun defaultTextRadialOffsetAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextRadialOffsetAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextRadialOffset!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-radial-offset") }
  }

  @Test
  fun defaultTextRotateTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextRotate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotate") }
  }
  // Expression Tests

  @Test
  fun defaultTextRotateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextRotateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotate") }
  }

  @Test
  fun defaultTextRotateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextRotateAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextRotate!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotate") }
  }

  @Test
  fun defaultTextRotationAlignmentTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(TextRotationAlignment.MAP, SymbolLayer.defaultTextRotationAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment") }
  }
  // Expression Tests

  @Test
  fun defaultTextRotationAlignmentAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextRotationAlignmentAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment") }
  }

  @Test
  fun defaultTextRotationAlignmentAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultTextRotationAlignmentAsExpression?.toString())
    assertEquals(TextRotationAlignment.MAP.value, SymbolLayer.defaultTextRotationAlignmentAsExpression.toString())
    assertEquals(TextRotationAlignment.MAP, SymbolLayer.defaultTextRotationAlignment)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment") }
  }

  @Test
  fun defaultTextSizeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextSize?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-size") }
  }
  // Expression Tests

  @Test
  fun defaultTextSizeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextSizeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-size") }
  }

  @Test
  fun defaultTextSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextSize!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-size") }
  }

  @Test
  fun defaultTextTransformTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(TextTransform.NONE, SymbolLayer.defaultTextTransform)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-transform") }
  }
  // Expression Tests

  @Test
  fun defaultTextTransformAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextTransformAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-transform") }
  }

  @Test
  fun defaultTextTransformAsExpressionGetFromLiteral() {
    val value = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultTextTransformAsExpression?.toString())
    assertEquals(TextTransform.NONE.value, SymbolLayer.defaultTextTransformAsExpression.toString())
    assertEquals(TextTransform.NONE, SymbolLayer.defaultTextTransform)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-transform") }
  }

  @Test
  fun defaultTextVariableAnchorTest() {
    val testValue = listOf("center", "left")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf("center", "left")
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextVariableAnchor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultTextVariableAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextVariableAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor") }
  }

  @Test
  fun defaultTextVariableAnchorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("center", "left"))
    assertEquals("[literal, [center, left]]", SymbolLayer.defaultTextVariableAnchorAsExpression.toString())
    assertEquals(listOf("center", "left"), SymbolLayer.defaultTextVariableAnchor!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor") }
  }

  @Test
  fun defaultTextWritingModeTest() {
    val testValue = listOf("horizontal", "vertical")
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf("horizontal", "vertical")
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextWritingMode?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode") }
  }
  // Expression Tests

  @Test
  fun defaultTextWritingModeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextWritingModeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode") }
  }

  @Test
  fun defaultTextWritingModeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("horizontal", "vertical"))
    assertEquals("[literal, [horizontal, vertical]]", SymbolLayer.defaultTextWritingModeAsExpression.toString())
    assertEquals(listOf("horizontal", "vertical"), SymbolLayer.defaultTextWritingMode!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode") }
  }

  @Test
  fun defaultIconColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color") }
  }
  // Expression Tests

  @Test
  fun defaultIconColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color") }
  }

  @Test
  fun defaultIconColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), SymbolLayer.defaultIconColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", SymbolLayer.defaultIconColor)
    assertEquals(Color.BLACK, SymbolLayer.defaultIconColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color") }
  }

  @Test
  fun defaultIconColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, SymbolLayer.defaultIconColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color") }
  }

  @Test
  fun defaultIconColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color-transition") }
  }

  @Test
  fun defaultIconEmissiveStrengthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconEmissiveStrength?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun defaultIconEmissiveStrengthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconEmissiveStrengthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-emissive-strength") }
  }

  @Test
  fun defaultIconEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconEmissiveStrength!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-emissive-strength") }
  }

  @Test
  fun defaultIconEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconEmissiveStrengthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-emissive-strength-transition") }
  }

  @Test
  fun defaultIconHaloBlurTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconHaloBlur?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-blur") }
  }
  // Expression Tests

  @Test
  fun defaultIconHaloBlurAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconHaloBlurAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-blur") }
  }

  @Test
  fun defaultIconHaloBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconHaloBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconHaloBlur!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-blur") }
  }

  @Test
  fun defaultIconHaloBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconHaloBlurTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-blur-transition") }
  }

  @Test
  fun defaultIconHaloColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconHaloColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-color") }
  }
  // Expression Tests

  @Test
  fun defaultIconHaloColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconHaloColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-color") }
  }

  @Test
  fun defaultIconHaloColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), SymbolLayer.defaultIconHaloColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", SymbolLayer.defaultIconHaloColor)
    assertEquals(Color.BLACK, SymbolLayer.defaultIconHaloColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-color") }
  }

  @Test
  fun defaultIconHaloColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, SymbolLayer.defaultIconHaloColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-color") }
  }

  @Test
  fun defaultIconHaloColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconHaloColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-color-transition") }
  }

  @Test
  fun defaultIconHaloWidthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconHaloWidth?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-width") }
  }
  // Expression Tests

  @Test
  fun defaultIconHaloWidthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconHaloWidthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-width") }
  }

  @Test
  fun defaultIconHaloWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconHaloWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconHaloWidth!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-width") }
  }

  @Test
  fun defaultIconHaloWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconHaloWidthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-width-transition") }
  }

  @Test
  fun defaultIconImageCrossFadeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconImageCrossFade?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image-cross-fade") }
  }
  // Expression Tests

  @Test
  fun defaultIconImageCrossFadeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconImageCrossFadeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image-cross-fade") }
  }

  @Test
  fun defaultIconImageCrossFadeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconImageCrossFadeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconImageCrossFade!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image-cross-fade") }
  }

  @Test
  fun defaultIconImageCrossFadeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconImageCrossFadeTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image-cross-fade-transition") }
  }

  @Test
  fun defaultIconOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultIconOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-opacity") }
  }

  @Test
  fun defaultIconOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultIconOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultIconOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-opacity") }
  }

  @Test
  fun defaultIconOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-opacity-transition") }
  }

  @Test
  fun defaultIconTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), SymbolLayer.defaultIconTranslate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate") }
  }
  // Expression Tests

  @Test
  fun defaultIconTranslateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconTranslateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate") }
  }

  @Test
  fun defaultIconTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", SymbolLayer.defaultIconTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), SymbolLayer.defaultIconTranslate!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate") }
  }

  @Test
  fun defaultIconTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultIconTranslateTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-transition") }
  }

  @Test
  fun defaultIconTranslateAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(IconTranslateAnchor.MAP, SymbolLayer.defaultIconTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultIconTranslateAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultIconTranslateAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor") }
  }

  @Test
  fun defaultIconTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultIconTranslateAnchorAsExpression?.toString())
    assertEquals(IconTranslateAnchor.MAP.value, SymbolLayer.defaultIconTranslateAnchorAsExpression.toString())
    assertEquals(IconTranslateAnchor.MAP, SymbolLayer.defaultIconTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor") }
  }

  @Test
  fun defaultTextColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-color") }
  }
  // Expression Tests

  @Test
  fun defaultTextColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-color") }
  }

  @Test
  fun defaultTextColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), SymbolLayer.defaultTextColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", SymbolLayer.defaultTextColor)
    assertEquals(Color.BLACK, SymbolLayer.defaultTextColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-color") }
  }

  @Test
  fun defaultTextColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, SymbolLayer.defaultTextColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-color") }
  }

  @Test
  fun defaultTextColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultTextColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-color-transition") }
  }

  @Test
  fun defaultTextEmissiveStrengthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextEmissiveStrength?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-emissive-strength") }
  }
  // Expression Tests

  @Test
  fun defaultTextEmissiveStrengthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextEmissiveStrengthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-emissive-strength") }
  }

  @Test
  fun defaultTextEmissiveStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextEmissiveStrength!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-emissive-strength") }
  }

  @Test
  fun defaultTextEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultTextEmissiveStrengthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-emissive-strength-transition") }
  }

  @Test
  fun defaultTextHaloBlurTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextHaloBlur?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-blur") }
  }
  // Expression Tests

  @Test
  fun defaultTextHaloBlurAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextHaloBlurAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-blur") }
  }

  @Test
  fun defaultTextHaloBlurAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextHaloBlurAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextHaloBlur!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-blur") }
  }

  @Test
  fun defaultTextHaloBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultTextHaloBlurTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-blur-transition") }
  }

  @Test
  fun defaultTextHaloColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextHaloColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-color") }
  }
  // Expression Tests

  @Test
  fun defaultTextHaloColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextHaloColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-color") }
  }

  @Test
  fun defaultTextHaloColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), SymbolLayer.defaultTextHaloColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", SymbolLayer.defaultTextHaloColor)
    assertEquals(Color.BLACK, SymbolLayer.defaultTextHaloColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-color") }
  }

  @Test
  fun defaultTextHaloColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, SymbolLayer.defaultTextHaloColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-color") }
  }

  @Test
  fun defaultTextHaloColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultTextHaloColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-color-transition") }
  }

  @Test
  fun defaultTextHaloWidthTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextHaloWidth?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-width") }
  }
  // Expression Tests

  @Test
  fun defaultTextHaloWidthAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextHaloWidthAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-width") }
  }

  @Test
  fun defaultTextHaloWidthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextHaloWidthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextHaloWidth!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-width") }
  }

  @Test
  fun defaultTextHaloWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultTextHaloWidthTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-width-transition") }
  }

  @Test
  fun defaultTextOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultTextOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-opacity") }
  }

  @Test
  fun defaultTextOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SymbolLayer.defaultTextOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SymbolLayer.defaultTextOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-opacity") }
  }

  @Test
  fun defaultTextOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultTextOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-opacity-transition") }
  }

  @Test
  fun defaultTextTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), SymbolLayer.defaultTextTranslate?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate") }
  }
  // Expression Tests

  @Test
  fun defaultTextTranslateAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextTranslateAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate") }
  }

  @Test
  fun defaultTextTranslateAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", SymbolLayer.defaultTextTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), SymbolLayer.defaultTextTranslate!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate") }
  }

  @Test
  fun defaultTextTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SymbolLayer.defaultTextTranslateTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-transition") }
  }

  @Test
  fun defaultTextTranslateAnchorTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    assertEquals(TextTranslateAnchor.MAP, SymbolLayer.defaultTextTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor") }
  }
  // Expression Tests

  @Test
  fun defaultTextTranslateAnchorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SymbolLayer.defaultTextTranslateAnchorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor") }
  }

  @Test
  fun defaultTextTranslateAnchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SymbolLayer.defaultTextTranslateAnchorAsExpression?.toString())
    assertEquals(TextTranslateAnchor.MAP.value, SymbolLayer.defaultTextTranslateAnchorAsExpression.toString())
    assertEquals(TextTranslateAnchor.MAP, SymbolLayer.defaultTextTranslateAnchor)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, SymbolLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("symbol", "visibility") }
  }
}

// End of generated file.