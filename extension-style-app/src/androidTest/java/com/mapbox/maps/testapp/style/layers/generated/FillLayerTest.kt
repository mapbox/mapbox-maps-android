// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for FillLayer
 */
@RunWith(AndroidJUnit4::class)
class FillLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = fillLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = fillLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = fillLayer("id", "source") {
      maxZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.maxZoom)
  }

  @Test
  @UiThreadTest
  fun filterTest() {
    val expression = eq {
      get {
        literal("undefined")
      }
      literal(1.0)
    }
    // Set filter property.
    val layer = fillLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun fillSortKeyTest() {
    val testValue = 1.0
    val layer = fillLayer("id", "source") {
      fillSortKey(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillSortKey!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillSortKeyAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillLayer("id", "source") {
      fillSortKey(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillSortKeyAsExpression.toString())
    assertEquals(null, layer.fillSortKey)
  }

  @Test
  @UiThreadTest
  fun fillAntialiasTest() {
    val testValue = true
    val layer = fillLayer("id", "source") {
      fillAntialias(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillAntialias?.toString())
  }

  @Test
  @UiThreadTest
  fun fillAntialiasAsExpressionTest() {
    val expression = literal(true)
    val layer = fillLayer("id", "source") {
      fillAntialias(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillAntialiasAsExpression.toString())
    assertEquals(true, layer.fillAntialias!!)
  }

  @Test
  @UiThreadTest
  fun fillColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillLayer("id", "source") {
      fillColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillLayer("id", "source") {
      fillColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillColorAsExpression.toString())
    assertEquals(null, layer.fillColor)
  }

  @Test
  @UiThreadTest
  fun fillColorAsColorIntTest() {
    val layer = fillLayer("id", "source") {
      fillColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillOpacityTest() {
    val testValue = 1.0
    val layer = fillLayer("id", "source") {
      fillOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillOpacityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillLayer("id", "source") {
      fillOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillOpacityAsExpression.toString())
    assertEquals(null, layer.fillOpacity)
  }

  @Test
  @UiThreadTest
  fun fillOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillLayer("id", "source") {
      fillOutlineColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillOutlineColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillLayer("id", "source") {
      fillOutlineColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillOutlineColorAsExpression.toString())
    assertEquals(null, layer.fillOutlineColor)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorAsColorIntTest() {
    val layer = fillLayer("id", "source") {
      fillOutlineColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillOutlineColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOutlineColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOutlineColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOutlineColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOutlineColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillPatternTest() {
    val testValue = "abc"
    val layer = fillLayer("id", "source") {
      fillPattern(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillPattern?.toString())
  }

  @Test
  @UiThreadTest
  fun fillPatternAsExpressionTest() {
    val expression = image {
      string {
        get {
          literal("resolvedImage")
        }
      }
    }
    val layer = fillLayer("id", "source") {
      fillPattern(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillPatternAsExpression.toString())
    assertEquals(null, layer.fillPattern)
  }

  @Test
  @UiThreadTest
  fun fillPatternTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillPatternTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillPatternTransition)
  }

  @Test
  @UiThreadTest
  fun fillPatternTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillPatternTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillPatternTransition)
  }

  @Test
  @UiThreadTest
  fun fillTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = fillLayer("id", "source") {
      fillTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun fillTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = fillLayer("id", "source") {
      fillTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.fillTranslate!!)
  }

  @Test
  @UiThreadTest
  fun fillTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillTranslateAnchorTest() {
    val layer = fillLayer("id", "source") {
      fillTranslateAnchor(FillTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(FillTranslateAnchor.MAP, layer.fillTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun fillTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = fillLayer("id", "source") {
      fillTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillTranslateAnchorAsExpression.toString())
    assertEquals(FillTranslateAnchor.MAP, layer.fillTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = fillLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", FillLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", FillLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", FillLayer.defaultMaxZoom)
    assertNotNull("defaultFillSortKey should not be null", FillLayer.defaultFillSortKey)
    assertNotNull("defaultFillSortKeyAsExpression should not be null", FillLayer.defaultFillSortKeyAsExpression)
    assertNotNull("defaultFillAntialias should not be null", FillLayer.defaultFillAntialias)
    assertNotNull("defaultFillAntialiasAsExpression should not be null", FillLayer.defaultFillAntialiasAsExpression)
    assertNotNull("defaultFillColor should not be null", FillLayer.defaultFillColor)
    assertNotNull("defaultFillColorAsExpression should not be null", FillLayer.defaultFillColorAsExpression)
    assertNotNull("defaultFillColorAsColorInt should not be null", FillLayer.defaultFillColorAsColorInt)
    assertNotNull("defaultFillColorTransition should not be null", FillLayer.defaultFillColorTransition)
    assertNotNull("defaultFillOpacity should not be null", FillLayer.defaultFillOpacity)
    assertNotNull("defaultFillOpacityAsExpression should not be null", FillLayer.defaultFillOpacityAsExpression)
    assertNotNull("defaultFillOpacityTransition should not be null", FillLayer.defaultFillOpacityTransition)
    assertNotNull("defaultFillOutlineColor should not be null", FillLayer.defaultFillOutlineColor)
    assertNotNull("defaultFillOutlineColorAsExpression should not be null", FillLayer.defaultFillOutlineColorAsExpression)
    assertNotNull("defaultFillOutlineColorAsColorInt should not be null", FillLayer.defaultFillOutlineColorAsColorInt)
    assertNotNull("defaultFillOutlineColorTransition should not be null", FillLayer.defaultFillOutlineColorTransition)
    assertNotNull("defaultFillPattern should not be null", FillLayer.defaultFillPattern)
    assertNotNull("defaultFillPatternAsExpression should not be null", FillLayer.defaultFillPatternAsExpression)
    assertNotNull("defaultFillPatternTransition should not be null", FillLayer.defaultFillPatternTransition)
    assertNotNull("defaultFillTranslate should not be null", FillLayer.defaultFillTranslate)
    assertNotNull("defaultFillTranslateAsExpression should not be null", FillLayer.defaultFillTranslateAsExpression)
    assertNotNull("defaultFillTranslateTransition should not be null", FillLayer.defaultFillTranslateTransition)
    assertNotNull("defaultFillTranslateAnchor should not be null", FillLayer.defaultFillTranslateAnchor)
    assertNotNull("defaultFillTranslateAnchorAsExpression should not be null", FillLayer.defaultFillTranslateAnchorAsExpression)
  }
}

// End of generated file.