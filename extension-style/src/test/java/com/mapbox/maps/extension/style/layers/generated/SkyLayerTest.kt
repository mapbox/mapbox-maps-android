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
class SkyLayerTest {
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
    clearAllMocks()
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
  fun skyAtmosphereColorSet() {
    val layer = skyLayer("id") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.skyAtmosphereColor(testValue)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun skyAtmosphereColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = skyLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.skyAtmosphereColor?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-color") }
  }
  // Expression Tests

  @Test
  fun skyAtmosphereColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyAtmosphereColor(expression)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyAtmosphereColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyAtmosphereColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-color") }
  }

  @Test
  fun skyAtmosphereColorAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyAtmosphereColorAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-color") }
  }

  @Test
  fun skyAtmosphereColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyAtmosphereColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.skyAtmosphereColor)
    assertEquals(Color.BLACK, layer.skyAtmosphereColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-color") }
  }

  @Test
  fun skyAtmosphereColorAsColorIntSet() {
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyAtmosphereColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun skyAtmosphereColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = skyLayer("id") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.skyAtmosphereColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-color") }
  }

  @Test
  fun skyAtmosphereHaloColorSet() {
    val layer = skyLayer("id") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.skyAtmosphereHaloColor(testValue)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-halo-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun skyAtmosphereHaloColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = skyLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.skyAtmosphereHaloColor?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-halo-color") }
  }
  // Expression Tests

  @Test
  fun skyAtmosphereHaloColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyAtmosphereHaloColor(expression)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-halo-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyAtmosphereHaloColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyAtmosphereHaloColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-halo-color") }
  }

  @Test
  fun skyAtmosphereHaloColorAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyAtmosphereHaloColorAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-halo-color") }
  }

  @Test
  fun skyAtmosphereHaloColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyAtmosphereHaloColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.skyAtmosphereHaloColor)
    assertEquals(Color.BLACK, layer.skyAtmosphereHaloColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-halo-color") }
  }

  @Test
  fun skyAtmosphereHaloColorAsColorIntSet() {
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyAtmosphereHaloColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-halo-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun skyAtmosphereHaloColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = skyLayer("id") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.skyAtmosphereHaloColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-halo-color") }
  }

  @Test
  fun skyAtmosphereSunSet() {
    val layer = skyLayer("id") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.skyAtmosphereSun(testValue)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-sun", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun skyAtmosphereSunGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.skyAtmosphereSun?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun") }
  }
  // Expression Tests

  @Test
  fun skyAtmosphereSunAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyAtmosphereSun(expression)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-sun", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyAtmosphereSunAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyAtmosphereSunAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun") }
  }

  @Test
  fun skyAtmosphereSunAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyAtmosphereSunAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun") }
  }

  @Test
  fun skyAtmosphereSunAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.skyAtmosphereSunAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.skyAtmosphereSun!!)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun") }
  }

  @Test
  fun skyAtmosphereSunIntensitySet() {
    val layer = skyLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.skyAtmosphereSunIntensity(testValue)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-sun-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun skyAtmosphereSunIntensityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.skyAtmosphereSunIntensity?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun-intensity") }
  }
  // Expression Tests

  @Test
  fun skyAtmosphereSunIntensityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyAtmosphereSunIntensity(expression)
    verify { style.setStyleLayerProperty("id", "sky-atmosphere-sun-intensity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyAtmosphereSunIntensityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyAtmosphereSunIntensityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun-intensity") }
  }

  @Test
  fun skyAtmosphereSunIntensityAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyAtmosphereSunIntensityAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun-intensity") }
  }

  @Test
  fun skyAtmosphereSunIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.skyAtmosphereSunIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.skyAtmosphereSunIntensity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "sky-atmosphere-sun-intensity") }
  }

  @Test
  fun skyGradientSet() {
    val layer = skyLayer("id") {}
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
    layer.skyGradient(testValue)
    verify { style.setStyleLayerProperty("id", "sky-gradient", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[interpolate, [linear], [heatmap-density], 0.0, [rgba, 0.0, 0.0, 0.0, 0.0], 1.0, [rgba, 0.0, 255.0, 0.0, 1.0]]")
  }

  @Test
  fun skyGradientGet() {
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
    val layer = skyLayer("id") { }
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
    assertEquals(expectedValue.toString(), layer.skyGradient?.toString())
    verify { style.getStyleLayerProperty("id", "sky-gradient") }
  }
  // Expression Tests

  @Test
  fun skyGradientCenterSet() {
    val layer = skyLayer("id") {}
    val testValue = listOf(0.0, 1.0)
    layer.bindTo(style)
    layer.skyGradientCenter(testValue)
    verify { style.setStyleLayerProperty("id", "sky-gradient-center", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0]")
  }

  @Test
  fun skyGradientCenterGet() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), layer.skyGradientCenter?.toString())
    verify { style.getStyleLayerProperty("id", "sky-gradient-center") }
  }
  // Expression Tests

  @Test
  fun skyGradientCenterAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyGradientCenter(expression)
    verify { style.setStyleLayerProperty("id", "sky-gradient-center", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyGradientCenterAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyGradientCenterAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-gradient-center") }
  }

  @Test
  fun skyGradientCenterAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyGradientCenterAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-gradient-center") }
  }

  @Test
  fun skyGradientCenterAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", layer.skyGradientCenterAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.skyGradientCenter!!)
    verify { style.getStyleLayerProperty("id", "sky-gradient-center") }
  }

  @Test
  fun skyGradientRadiusSet() {
    val layer = skyLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.skyGradientRadius(testValue)
    verify { style.setStyleLayerProperty("id", "sky-gradient-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun skyGradientRadiusGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.skyGradientRadius?.toString())
    verify { style.getStyleLayerProperty("id", "sky-gradient-radius") }
  }
  // Expression Tests

  @Test
  fun skyGradientRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyGradientRadius(expression)
    verify { style.setStyleLayerProperty("id", "sky-gradient-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyGradientRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyGradientRadiusAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-gradient-radius") }
  }

  @Test
  fun skyGradientRadiusAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyGradientRadiusAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-gradient-radius") }
  }

  @Test
  fun skyGradientRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.skyGradientRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.skyGradientRadius!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "sky-gradient-radius") }
  }

  @Test
  fun skyOpacitySet() {
    val layer = skyLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.skyOpacity(testValue)
    verify { style.setStyleLayerProperty("id", "sky-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun skyOpacityGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.skyOpacity?.toString())
    verify { style.getStyleLayerProperty("id", "sky-opacity") }
  }
  // Expression Tests

  @Test
  fun skyOpacityAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyOpacity(expression)
    verify { style.setStyleLayerProperty("id", "sky-opacity", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyOpacityAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyOpacityAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-opacity") }
  }

  @Test
  fun skyOpacityAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyOpacityAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-opacity") }
  }

  @Test
  fun skyOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.skyOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.skyOpacity!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "sky-opacity") }
  }

  @Test
  fun skyOpacityTransitionSet() {
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyOpacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "sky-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun skyOpacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.skyOpacityTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "sky-opacity-transition") }
  }

  @Test
  fun skyOpacityTransitionSetDsl() {
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyOpacityTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "sky-opacity-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun skyTypeSet() {
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyType(SkyType.GRADIENT)
    verify { style.setStyleLayerProperty("id", "sky-type", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "gradient")
  }

  @Test
  fun skyTypeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("gradient")

    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(SkyType.GRADIENT, layer.skyType)
    verify { style.getStyleLayerProperty("id", "sky-type") }
  }
  // Expression Tests

  @Test
  fun skyTypeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.skyType(expression)
    verify { style.setStyleLayerProperty("id", "sky-type", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun skyTypeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.skyTypeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "sky-type") }
  }

  @Test
  fun skyTypeAsExpressionGetNull() {
    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.skyTypeAsExpression)
    verify { style.getStyleLayerProperty("id", "sky-type") }
  }

  @Test
  fun skyTypeAsExpressionGetFromLiteral() {
    val value = "gradient"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(value.toString(), layer.skyTypeAsExpression?.toString())
    assertEquals(SkyType.GRADIENT.value, layer.skyTypeAsExpression.toString())
    assertEquals(SkyType.GRADIENT, layer.skyType)
    verify { style.getStyleLayerProperty("id", "sky-type") }
  }

  @Test
  fun visibilitySet() {
    val layer = skyLayer("id") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = skyLayer("id") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = skyLayer("id") { }
    assertEquals("sky", layer.getType())
  }

  @Test
  fun getSkyLayerTest() {

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("sky"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as SkyLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("sky", layer.getType())
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultSkyAtmosphereColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyAtmosphereColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-color") }
  }
  // Expression Tests

  @Test
  fun defaultSkyAtmosphereColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyAtmosphereColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-color") }
  }

  @Test
  fun defaultSkyAtmosphereColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), SkyLayer.defaultSkyAtmosphereColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", SkyLayer.defaultSkyAtmosphereColor)
    assertEquals(Color.BLACK, SkyLayer.defaultSkyAtmosphereColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-color") }
  }

  @Test
  fun defaultSkyAtmosphereColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, SkyLayer.defaultSkyAtmosphereColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-color") }
  }

  @Test
  fun defaultSkyAtmosphereHaloColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyAtmosphereHaloColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-halo-color") }
  }
  // Expression Tests

  @Test
  fun defaultSkyAtmosphereHaloColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyAtmosphereHaloColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-halo-color") }
  }

  @Test
  fun defaultSkyAtmosphereHaloColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), SkyLayer.defaultSkyAtmosphereHaloColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", SkyLayer.defaultSkyAtmosphereHaloColor)
    assertEquals(Color.BLACK, SkyLayer.defaultSkyAtmosphereHaloColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-halo-color") }
  }

  @Test
  fun defaultSkyAtmosphereHaloColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, SkyLayer.defaultSkyAtmosphereHaloColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-halo-color") }
  }

  @Test
  fun defaultSkyAtmosphereSunTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyAtmosphereSun?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun") }
  }
  // Expression Tests

  @Test
  fun defaultSkyAtmosphereSunAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyAtmosphereSunAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun") }
  }

  @Test
  fun defaultSkyAtmosphereSunAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", SkyLayer.defaultSkyAtmosphereSunAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), SkyLayer.defaultSkyAtmosphereSun!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun") }
  }

  @Test
  fun defaultSkyAtmosphereSunIntensityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyAtmosphereSunIntensity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun-intensity") }
  }
  // Expression Tests

  @Test
  fun defaultSkyAtmosphereSunIntensityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyAtmosphereSunIntensityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun-intensity") }
  }

  @Test
  fun defaultSkyAtmosphereSunIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SkyLayer.defaultSkyAtmosphereSunIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SkyLayer.defaultSkyAtmosphereSunIntensity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun-intensity") }
  }

  @Test
  fun defaultSkyGradientTest() {
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
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyGradient?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient") }
  }
  // Expression Tests

  @Test
  fun defaultSkyGradientCenterTest() {
    val testValue = listOf(0.0, 1.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0)
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyGradientCenter?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-center") }
  }
  // Expression Tests

  @Test
  fun defaultSkyGradientCenterAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyGradientCenterAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-center") }
  }

  @Test
  fun defaultSkyGradientCenterAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    assertEquals("[literal, [0.0, 1.0]]", SkyLayer.defaultSkyGradientCenterAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), SkyLayer.defaultSkyGradientCenter!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-center") }
  }

  @Test
  fun defaultSkyGradientRadiusTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyGradientRadius?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-radius") }
  }
  // Expression Tests

  @Test
  fun defaultSkyGradientRadiusAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyGradientRadiusAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-radius") }
  }

  @Test
  fun defaultSkyGradientRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SkyLayer.defaultSkyGradientRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SkyLayer.defaultSkyGradientRadius!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-radius") }
  }

  @Test
  fun defaultSkyOpacityTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), SkyLayer.defaultSkyOpacity?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-opacity") }
  }
  // Expression Tests

  @Test
  fun defaultSkyOpacityAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyOpacityAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-opacity") }
  }

  @Test
  fun defaultSkyOpacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, SkyLayer.defaultSkyOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, SkyLayer.defaultSkyOpacity!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-opacity") }
  }

  @Test
  fun defaultSkyOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), SkyLayer.defaultSkyOpacityTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-opacity-transition") }
  }

  @Test
  fun defaultSkyTypeTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("gradient")

    assertEquals(SkyType.GRADIENT, SkyLayer.defaultSkyType)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-type") }
  }
  // Expression Tests

  @Test
  fun defaultSkyTypeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), SkyLayer.defaultSkyTypeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-type") }
  }

  @Test
  fun defaultSkyTypeAsExpressionGetFromLiteral() {
    val value = "gradient"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    assertEquals(value.toString(), SkyLayer.defaultSkyTypeAsExpression?.toString())
    assertEquals(SkyType.GRADIENT.value, SkyLayer.defaultSkyTypeAsExpression.toString())
    assertEquals(SkyType.GRADIENT, SkyLayer.defaultSkyType)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-type") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, SkyLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("sky", "visibility") }
  }
}

// End of generated file.