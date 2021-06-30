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
class LocationIndicatorLayerTest {
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
  // Property getters and setters

  @Test
  fun bearingImageSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.bearingImage(testValue)
    verify { style.setStyleLayerProperty("id", "bearing-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun bearingImageGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.bearingImage?.toString())
    verify { style.getStyleLayerProperty("id", "bearing-image") }
  }
  // Expression Tests

  @Test
  fun bearingImageAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.bearingImage(expression)
    verify { style.setStyleLayerProperty("id", "bearing-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun bearingImageAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.bearingImageAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "bearing-image") }
  }

  @Test
  fun bearingImageAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.bearingImageAsExpression)
    verify { style.getStyleLayerProperty("id", "bearing-image") }
  }

  @Test
  fun bearingImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals("abc", layer.bearingImageAsExpression.toString())
    assertEquals("abc", layer.bearingImage)
    verify { style.getStyleLayerProperty("id", "bearing-image") }
  }

  @Test
  fun shadowImageSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.shadowImage(testValue)
    verify { style.setStyleLayerProperty("id", "shadow-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun shadowImageGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.shadowImage?.toString())
    verify { style.getStyleLayerProperty("id", "shadow-image") }
  }
  // Expression Tests

  @Test
  fun shadowImageAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.shadowImage(expression)
    verify { style.setStyleLayerProperty("id", "shadow-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun shadowImageAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.shadowImageAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "shadow-image") }
  }

  @Test
  fun shadowImageAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.shadowImageAsExpression)
    verify { style.getStyleLayerProperty("id", "shadow-image") }
  }

  @Test
  fun shadowImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals("abc", layer.shadowImageAsExpression.toString())
    assertEquals("abc", layer.shadowImage)
    verify { style.getStyleLayerProperty("id", "shadow-image") }
  }

  @Test
  fun topImageSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.topImage(testValue)
    verify { style.setStyleLayerProperty("id", "top-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun topImageGet() {
    every { styleProperty.value } returns Value("abc")
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.topImage?.toString())
    verify { style.getStyleLayerProperty("id", "top-image") }
  }
  // Expression Tests

  @Test
  fun topImageAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.topImage(expression)
    verify { style.setStyleLayerProperty("id", "top-image", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun topImageAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.topImageAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "top-image") }
  }

  @Test
  fun topImageAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.topImageAsExpression)
    verify { style.getStyleLayerProperty("id", "top-image") }
  }

  @Test
  fun topImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals("abc", layer.topImageAsExpression.toString())
    assertEquals("abc", layer.topImage)
    verify { style.getStyleLayerProperty("id", "top-image") }
  }

  @Test
  fun accuracyRadiusSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.accuracyRadius(testValue)
    verify { style.setStyleLayerProperty("id", "accuracy-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun accuracyRadiusGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.accuracyRadius?.toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius") }
  }
  // Expression Tests

  @Test
  fun accuracyRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadius(expression)
    verify { style.setStyleLayerProperty("id", "accuracy-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun accuracyRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.accuracyRadiusAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius") }
  }

  @Test
  fun accuracyRadiusAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.accuracyRadiusAsExpression)
    verify { style.getStyleLayerProperty("id", "accuracy-radius") }
  }

  @Test
  fun accuracyRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.accuracyRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.accuracyRadius!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "accuracy-radius") }
  }

  @Test
  fun accuracyRadiusTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "accuracy-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun accuracyRadiusTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.accuracyRadiusTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius-transition") }
  }

  @Test
  fun accuracyRadiusTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "accuracy-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun accuracyRadiusBorderColorSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.accuracyRadiusBorderColor(testValue)
    verify { style.setStyleLayerProperty("id", "accuracy-radius-border-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun accuracyRadiusBorderColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.accuracyRadiusBorderColor?.toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius-border-color") }
  }
  // Expression Tests

  @Test
  fun accuracyRadiusBorderColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusBorderColor(expression)
    verify { style.setStyleLayerProperty("id", "accuracy-radius-border-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun accuracyRadiusBorderColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.accuracyRadiusBorderColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius-border-color") }
  }

  @Test
  fun accuracyRadiusBorderColorAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.accuracyRadiusBorderColorAsExpression)
    verify { style.getStyleLayerProperty("id", "accuracy-radius-border-color") }
  }

  @Test
  fun accuracyRadiusBorderColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.accuracyRadiusBorderColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.accuracyRadiusBorderColor)
    assertEquals(Color.BLACK, layer.accuracyRadiusBorderColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "accuracy-radius-border-color") }
  }

  @Test
  fun accuracyRadiusBorderColorAsColorIntSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusBorderColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "accuracy-radius-border-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun accuracyRadiusBorderColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.accuracyRadiusBorderColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "accuracy-radius-border-color") }
  }

  @Test
  fun accuracyRadiusBorderColorTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusBorderColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "accuracy-radius-border-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun accuracyRadiusBorderColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.accuracyRadiusBorderColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius-border-color-transition") }
  }

  @Test
  fun accuracyRadiusBorderColorTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusBorderColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "accuracy-radius-border-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun accuracyRadiusColorSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.accuracyRadiusColor(testValue)
    verify { style.setStyleLayerProperty("id", "accuracy-radius-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun accuracyRadiusColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.accuracyRadiusColor?.toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius-color") }
  }
  // Expression Tests

  @Test
  fun accuracyRadiusColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusColor(expression)
    verify { style.setStyleLayerProperty("id", "accuracy-radius-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun accuracyRadiusColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.accuracyRadiusColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius-color") }
  }

  @Test
  fun accuracyRadiusColorAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.accuracyRadiusColorAsExpression)
    verify { style.getStyleLayerProperty("id", "accuracy-radius-color") }
  }

  @Test
  fun accuracyRadiusColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.accuracyRadiusColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.accuracyRadiusColor)
    assertEquals(Color.BLACK, layer.accuracyRadiusColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "accuracy-radius-color") }
  }

  @Test
  fun accuracyRadiusColorAsColorIntSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "accuracy-radius-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun accuracyRadiusColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.accuracyRadiusColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "accuracy-radius-color") }
  }

  @Test
  fun accuracyRadiusColorTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "accuracy-radius-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun accuracyRadiusColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.accuracyRadiusColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "accuracy-radius-color-transition") }
  }

  @Test
  fun accuracyRadiusColorTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.accuracyRadiusColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "accuracy-radius-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun bearingSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.bearing(testValue)
    verify { style.setStyleLayerProperty("id", "bearing", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun bearingGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.bearing?.toString())
    verify { style.getStyleLayerProperty("id", "bearing") }
  }
  // Expression Tests

  @Test
  fun bearingAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.bearing(expression)
    verify { style.setStyleLayerProperty("id", "bearing", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun bearingAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.bearingAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "bearing") }
  }

  @Test
  fun bearingAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.bearingAsExpression)
    verify { style.getStyleLayerProperty("id", "bearing") }
  }

  @Test
  fun bearingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.bearingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.bearing!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "bearing") }
  }

  @Test
  fun bearingImageSizeSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.bearingImageSize(testValue)
    verify { style.setStyleLayerProperty("id", "bearing-image-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun bearingImageSizeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.bearingImageSize?.toString())
    verify { style.getStyleLayerProperty("id", "bearing-image-size") }
  }
  // Expression Tests

  @Test
  fun bearingImageSizeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.bearingImageSize(expression)
    verify { style.setStyleLayerProperty("id", "bearing-image-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun bearingImageSizeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.bearingImageSizeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "bearing-image-size") }
  }

  @Test
  fun bearingImageSizeAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.bearingImageSizeAsExpression)
    verify { style.getStyleLayerProperty("id", "bearing-image-size") }
  }

  @Test
  fun bearingImageSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.bearingImageSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.bearingImageSize!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "bearing-image-size") }
  }

  @Test
  fun bearingImageSizeTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.bearingImageSizeTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "bearing-image-size-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun bearingImageSizeTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.bearingImageSizeTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "bearing-image-size-transition") }
  }

  @Test
  fun bearingImageSizeTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.bearingImageSizeTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "bearing-image-size-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun emphasisCircleColorSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = "rgba(0, 0, 0, 1)"
    layer.bindTo(style)
    layer.emphasisCircleColor(testValue)
    verify { style.setStyleLayerProperty("id", "emphasis-circle-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "rgba(0, 0, 0, 1)")
  }

  @Test
  fun emphasisCircleColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), layer.emphasisCircleColor?.toString())
    verify { style.getStyleLayerProperty("id", "emphasis-circle-color") }
  }
  // Expression Tests

  @Test
  fun emphasisCircleColorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.emphasisCircleColor(expression)
    verify { style.setStyleLayerProperty("id", "emphasis-circle-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun emphasisCircleColorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.emphasisCircleColorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "emphasis-circle-color") }
  }

  @Test
  fun emphasisCircleColorAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.emphasisCircleColorAsExpression)
    verify { style.getStyleLayerProperty("id", "emphasis-circle-color") }
  }

  @Test
  fun emphasisCircleColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.emphasisCircleColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.emphasisCircleColor)
    assertEquals(Color.BLACK, layer.emphasisCircleColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "emphasis-circle-color") }
  }

  @Test
  fun emphasisCircleColorAsColorIntSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.emphasisCircleColor(Color.CYAN)
    verify { style.setStyleLayerProperty("id", "emphasis-circle-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun emphasisCircleColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(Color.RED, layer.emphasisCircleColorAsColorInt)
    verify { style.getStyleLayerProperty("id", "emphasis-circle-color") }
  }

  @Test
  fun emphasisCircleColorTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.emphasisCircleColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "emphasis-circle-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun emphasisCircleColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.emphasisCircleColorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "emphasis-circle-color-transition") }
  }

  @Test
  fun emphasisCircleColorTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.emphasisCircleColorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "emphasis-circle-color-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun emphasisCircleRadiusSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.emphasisCircleRadius(testValue)
    verify { style.setStyleLayerProperty("id", "emphasis-circle-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun emphasisCircleRadiusGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.emphasisCircleRadius?.toString())
    verify { style.getStyleLayerProperty("id", "emphasis-circle-radius") }
  }
  // Expression Tests

  @Test
  fun emphasisCircleRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.emphasisCircleRadius(expression)
    verify { style.setStyleLayerProperty("id", "emphasis-circle-radius", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun emphasisCircleRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.emphasisCircleRadiusAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "emphasis-circle-radius") }
  }

  @Test
  fun emphasisCircleRadiusAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.emphasisCircleRadiusAsExpression)
    verify { style.getStyleLayerProperty("id", "emphasis-circle-radius") }
  }

  @Test
  fun emphasisCircleRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.emphasisCircleRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.emphasisCircleRadius!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "emphasis-circle-radius") }
  }

  @Test
  fun emphasisCircleRadiusTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.emphasisCircleRadiusTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "emphasis-circle-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun emphasisCircleRadiusTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.emphasisCircleRadiusTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "emphasis-circle-radius-transition") }
  }

  @Test
  fun emphasisCircleRadiusTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.emphasisCircleRadiusTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "emphasis-circle-radius-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun imagePitchDisplacementSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.imagePitchDisplacement(testValue)
    verify { style.setStyleLayerProperty("id", "image-pitch-displacement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun imagePitchDisplacementGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.imagePitchDisplacement?.toString())
    verify { style.getStyleLayerProperty("id", "image-pitch-displacement") }
  }
  // Expression Tests

  @Test
  fun imagePitchDisplacementAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.imagePitchDisplacement(expression)
    verify { style.setStyleLayerProperty("id", "image-pitch-displacement", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun imagePitchDisplacementAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.imagePitchDisplacementAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "image-pitch-displacement") }
  }

  @Test
  fun imagePitchDisplacementAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.imagePitchDisplacementAsExpression)
    verify { style.getStyleLayerProperty("id", "image-pitch-displacement") }
  }

  @Test
  fun imagePitchDisplacementAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.imagePitchDisplacementAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.imagePitchDisplacement!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "image-pitch-displacement") }
  }

  @Test
  fun locationSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = listOf(0.0, 1.0, 2.0)
    layer.bindTo(style)
    layer.location(testValue)
    verify { style.setStyleLayerProperty("id", "location", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[0.0, 1.0, 2.0]")
  }

  @Test
  fun locationGet() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), layer.location?.toString())
    verify { style.getStyleLayerProperty("id", "location") }
  }
  // Expression Tests

  @Test
  fun locationAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.location(expression)
    verify { style.setStyleLayerProperty("id", "location", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun locationAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.locationAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "location") }
  }

  @Test
  fun locationAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.locationAsExpression)
    verify { style.getStyleLayerProperty("id", "location") }
  }

  @Test
  fun locationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0]]", layer.locationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.location!!)
    verify { style.getStyleLayerProperty("id", "location") }
  }

  @Test
  fun locationTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.locationTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "location-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun locationTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.locationTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "location-transition") }
  }

  @Test
  fun locationTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.locationTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "location-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun perspectiveCompensationSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.perspectiveCompensation(testValue)
    verify { style.setStyleLayerProperty("id", "perspective-compensation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun perspectiveCompensationGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.perspectiveCompensation?.toString())
    verify { style.getStyleLayerProperty("id", "perspective-compensation") }
  }
  // Expression Tests

  @Test
  fun perspectiveCompensationAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.perspectiveCompensation(expression)
    verify { style.setStyleLayerProperty("id", "perspective-compensation", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun perspectiveCompensationAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.perspectiveCompensationAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "perspective-compensation") }
  }

  @Test
  fun perspectiveCompensationAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.perspectiveCompensationAsExpression)
    verify { style.getStyleLayerProperty("id", "perspective-compensation") }
  }

  @Test
  fun perspectiveCompensationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.perspectiveCompensationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.perspectiveCompensation!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "perspective-compensation") }
  }

  @Test
  fun shadowImageSizeSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.shadowImageSize(testValue)
    verify { style.setStyleLayerProperty("id", "shadow-image-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun shadowImageSizeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.shadowImageSize?.toString())
    verify { style.getStyleLayerProperty("id", "shadow-image-size") }
  }
  // Expression Tests

  @Test
  fun shadowImageSizeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.shadowImageSize(expression)
    verify { style.setStyleLayerProperty("id", "shadow-image-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun shadowImageSizeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.shadowImageSizeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "shadow-image-size") }
  }

  @Test
  fun shadowImageSizeAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.shadowImageSizeAsExpression)
    verify { style.getStyleLayerProperty("id", "shadow-image-size") }
  }

  @Test
  fun shadowImageSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.shadowImageSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.shadowImageSize!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "shadow-image-size") }
  }

  @Test
  fun shadowImageSizeTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.shadowImageSizeTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "shadow-image-size-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun shadowImageSizeTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.shadowImageSizeTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "shadow-image-size-transition") }
  }

  @Test
  fun shadowImageSizeTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.shadowImageSizeTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "shadow-image-size-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun topImageSizeSet() {
    val layer = locationIndicatorLayer("id") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.topImageSize(testValue)
    verify { style.setStyleLayerProperty("id", "top-image-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun topImageSizeGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.topImageSize?.toString())
    verify { style.getStyleLayerProperty("id", "top-image-size") }
  }
  // Expression Tests

  @Test
  fun topImageSizeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.topImageSize(expression)
    verify { style.setStyleLayerProperty("id", "top-image-size", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun topImageSizeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.topImageSizeAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "top-image-size") }
  }

  @Test
  fun topImageSizeAsExpressionGetNull() {
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(null, layer.topImageSizeAsExpression)
    verify { style.getStyleLayerProperty("id", "top-image-size") }
  }

  @Test
  fun topImageSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.topImageSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.topImageSize!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "top-image-size") }
  }

  @Test
  fun topImageSizeTransitionSet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.topImageSizeTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "top-image-size-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun topImageSizeTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.topImageSizeTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "top-image-size-transition") }
  }

  @Test
  fun topImageSizeTransitionSetDsl() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.topImageSizeTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "top-image-size-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun visibilitySet() {
    val layer = locationIndicatorLayer("id") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = locationIndicatorLayer("id") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = locationIndicatorLayer("id") { }
    assertEquals("location-indicator", layer.getType())
  }

  @Test
  fun getLocationIndicatorLayerTest() {
    val value = HashMap<String, Value>()
    value["id"] = Value("id")
    value["type"] = Value("location-indicator")
    every { style.getStyleLayerProperties("id") } returns valueExpected
    every { valueExpected.error } returns null
    every { valueExpected.value } returns Value(value)
    val layer = style.getLayer("id") as LocationIndicatorLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("location-indicator", layer.getType())
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultBearingImageTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultBearingImage?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image") }
  }
  // Expression Tests

  @Test
  fun defaultBearingImageAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultBearingImageAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image") }
  }

  @Test
  fun defaultBearingImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", LocationIndicatorLayer.defaultBearingImageAsExpression.toString())
    assertEquals("abc", LocationIndicatorLayer.defaultBearingImage)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image") }
  }

  @Test
  fun defaultShadowImageTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultShadowImage?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image") }
  }
  // Expression Tests

  @Test
  fun defaultShadowImageAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultShadowImageAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image") }
  }

  @Test
  fun defaultShadowImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", LocationIndicatorLayer.defaultShadowImageAsExpression.toString())
    assertEquals("abc", LocationIndicatorLayer.defaultShadowImage)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image") }
  }

  @Test
  fun defaultTopImageTest() {
    every { styleProperty.value } returns Value("abc")

    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultTopImage?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image") }
  }
  // Expression Tests

  @Test
  fun defaultTopImageAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultTopImageAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image") }
  }

  @Test
  fun defaultTopImageAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns Value("abc")

    assertEquals("abc", LocationIndicatorLayer.defaultTopImageAsExpression.toString())
    assertEquals("abc", LocationIndicatorLayer.defaultTopImage)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image") }
  }

  @Test
  fun defaultAccuracyRadiusTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultAccuracyRadius?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius") }
  }
  // Expression Tests

  @Test
  fun defaultAccuracyRadiusAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultAccuracyRadiusAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius") }
  }

  @Test
  fun defaultAccuracyRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultAccuracyRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultAccuracyRadius!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius") }
  }

  @Test
  fun defaultAccuracyRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultAccuracyRadiusTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-transition") }
  }

  @Test
  fun defaultAccuracyRadiusBorderColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultAccuracyRadiusBorderColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-border-color") }
  }
  // Expression Tests

  @Test
  fun defaultAccuracyRadiusBorderColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-border-color") }
  }

  @Test
  fun defaultAccuracyRadiusBorderColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", LocationIndicatorLayer.defaultAccuracyRadiusBorderColor)
    assertEquals(Color.BLACK, LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-border-color") }
  }

  @Test
  fun defaultAccuracyRadiusBorderColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-border-color") }
  }

  @Test
  fun defaultAccuracyRadiusBorderColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultAccuracyRadiusBorderColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-border-color-transition") }
  }

  @Test
  fun defaultAccuracyRadiusColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultAccuracyRadiusColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-color") }
  }
  // Expression Tests

  @Test
  fun defaultAccuracyRadiusColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultAccuracyRadiusColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-color") }
  }

  @Test
  fun defaultAccuracyRadiusColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultAccuracyRadiusColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", LocationIndicatorLayer.defaultAccuracyRadiusColor)
    assertEquals(Color.BLACK, LocationIndicatorLayer.defaultAccuracyRadiusColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-color") }
  }

  @Test
  fun defaultAccuracyRadiusColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, LocationIndicatorLayer.defaultAccuracyRadiusColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-color") }
  }

  @Test
  fun defaultAccuracyRadiusColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultAccuracyRadiusColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-color-transition") }
  }

  @Test
  fun defaultBearingTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultBearing?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing") }
  }
  // Expression Tests

  @Test
  fun defaultBearingAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultBearingAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing") }
  }

  @Test
  fun defaultBearingAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultBearingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultBearing!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing") }
  }

  @Test
  fun defaultBearingImageSizeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultBearingImageSize?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size") }
  }
  // Expression Tests

  @Test
  fun defaultBearingImageSizeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultBearingImageSizeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size") }
  }

  @Test
  fun defaultBearingImageSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultBearingImageSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultBearingImageSize!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size") }
  }

  @Test
  fun defaultBearingImageSizeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultBearingImageSizeTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size-transition") }
  }

  @Test
  fun defaultEmphasisCircleColorTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val expectedValue = "rgba(0, 0, 0, 1)"
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultEmphasisCircleColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-color") }
  }
  // Expression Tests

  @Test
  fun defaultEmphasisCircleColorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultEmphasisCircleColorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-color") }
  }

  @Test
  fun defaultEmphasisCircleColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultEmphasisCircleColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", LocationIndicatorLayer.defaultEmphasisCircleColor)
    assertEquals(Color.BLACK, LocationIndicatorLayer.defaultEmphasisCircleColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-color") }
  }

  @Test
  fun defaultEmphasisCircleColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    assertEquals(Color.RED, LocationIndicatorLayer.defaultEmphasisCircleColorAsColorInt)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-color") }
  }

  @Test
  fun defaultEmphasisCircleColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultEmphasisCircleColorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-color-transition") }
  }

  @Test
  fun defaultEmphasisCircleRadiusTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultEmphasisCircleRadius?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius") }
  }
  // Expression Tests

  @Test
  fun defaultEmphasisCircleRadiusAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultEmphasisCircleRadiusAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius") }
  }

  @Test
  fun defaultEmphasisCircleRadiusAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultEmphasisCircleRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultEmphasisCircleRadius!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius") }
  }

  @Test
  fun defaultEmphasisCircleRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultEmphasisCircleRadiusTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius-transition") }
  }

  @Test
  fun defaultImagePitchDisplacementTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultImagePitchDisplacement?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "image-pitch-displacement") }
  }
  // Expression Tests

  @Test
  fun defaultImagePitchDisplacementAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultImagePitchDisplacementAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "image-pitch-displacement") }
  }

  @Test
  fun defaultImagePitchDisplacementAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultImagePitchDisplacementAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultImagePitchDisplacement!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "image-pitch-displacement") }
  }

  @Test
  fun defaultLocationTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = listOf(0.0, 1.0, 2.0)
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultLocation?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location") }
  }
  // Expression Tests

  @Test
  fun defaultLocationAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultLocationAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location") }
  }

  @Test
  fun defaultLocationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0))
    assertEquals("[literal, [0.0, 1.0, 2.0]]", LocationIndicatorLayer.defaultLocationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), LocationIndicatorLayer.defaultLocation!!)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location") }
  }

  @Test
  fun defaultLocationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultLocationTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location-transition") }
  }

  @Test
  fun defaultPerspectiveCompensationTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultPerspectiveCompensation?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "perspective-compensation") }
  }
  // Expression Tests

  @Test
  fun defaultPerspectiveCompensationAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultPerspectiveCompensationAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "perspective-compensation") }
  }

  @Test
  fun defaultPerspectiveCompensationAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultPerspectiveCompensationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultPerspectiveCompensation!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "perspective-compensation") }
  }

  @Test
  fun defaultShadowImageSizeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultShadowImageSize?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size") }
  }
  // Expression Tests

  @Test
  fun defaultShadowImageSizeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultShadowImageSizeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size") }
  }

  @Test
  fun defaultShadowImageSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultShadowImageSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultShadowImageSize!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size") }
  }

  @Test
  fun defaultShadowImageSizeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultShadowImageSizeTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size-transition") }
  }

  @Test
  fun defaultTopImageSizeTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), LocationIndicatorLayer.defaultTopImageSize?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size") }
  }
  // Expression Tests

  @Test
  fun defaultTopImageSizeAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), LocationIndicatorLayer.defaultTopImageSizeAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size") }
  }

  @Test
  fun defaultTopImageSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, LocationIndicatorLayer.defaultTopImageSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, LocationIndicatorLayer.defaultTopImageSize!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size") }
  }

  @Test
  fun defaultTopImageSizeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), LocationIndicatorLayer.defaultTopImageSizeTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size-transition") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, LocationIndicatorLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "visibility") }
  }
}

// End of generated file.