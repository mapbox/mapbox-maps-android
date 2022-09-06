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
 * Basic smoke tests for HillshadeLayer
 */
@RunWith(AndroidJUnit4::class)
class HillshadeLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = hillshadeLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = hillshadeLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = hillshadeLayer("id", "source") {
      maxZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.maxZoom)
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun hillshadeAccentColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = hillshadeLayer("id", "source") {
      hillshadeAccentColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.hillshadeAccentColor?.toString())
  }

  @Test
  @UiThreadTest
  fun hillshadeAccentColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeAccentColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.hillshadeAccentColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.hillshadeAccentColor)
    assertEquals(Color.BLACK, layer.hillshadeAccentColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun hillshadeAccentColorAsColorIntTest() {
    val layer = hillshadeLayer("id", "source") {
      hillshadeAccentColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.hillshadeAccentColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun hillshadeAccentColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeAccentColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeAccentColorTransition)
  }

  @Test
  @UiThreadTest
  fun hillshadeAccentColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeAccentColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeAccentColorTransition)
  }

  @Test
  @UiThreadTest
  fun hillshadeExaggerationTest() {
    val testValue = 1.0
    val layer = hillshadeLayer("id", "source") {
      hillshadeExaggeration(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.hillshadeExaggeration!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun hillshadeExaggerationAsExpressionTest() {
    val expression = literal(1.0)
    val layer = hillshadeLayer("id", "source") {
      hillshadeExaggeration(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.hillshadeExaggerationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.hillshadeExaggeration!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun hillshadeExaggerationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeExaggerationTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeExaggerationTransition)
  }

  @Test
  @UiThreadTest
  fun hillshadeExaggerationTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeExaggerationTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeExaggerationTransition)
  }

  @Test
  @UiThreadTest
  fun hillshadeHighlightColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = hillshadeLayer("id", "source") {
      hillshadeHighlightColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.hillshadeHighlightColor?.toString())
  }

  @Test
  @UiThreadTest
  fun hillshadeHighlightColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeHighlightColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.hillshadeHighlightColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.hillshadeHighlightColor)
    assertEquals(Color.BLACK, layer.hillshadeHighlightColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun hillshadeHighlightColorAsColorIntTest() {
    val layer = hillshadeLayer("id", "source") {
      hillshadeHighlightColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.hillshadeHighlightColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun hillshadeHighlightColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeHighlightColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeHighlightColorTransition)
  }

  @Test
  @UiThreadTest
  fun hillshadeHighlightColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeHighlightColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeHighlightColorTransition)
  }

  @Test
  @UiThreadTest
  fun hillshadeIlluminationAnchorTest() {
    val layer = hillshadeLayer("id", "source") {
      hillshadeIlluminationAnchor(HillshadeIlluminationAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(HillshadeIlluminationAnchor.MAP, layer.hillshadeIlluminationAnchor)
  }

  @Test
  @UiThreadTest
  fun hillshadeIlluminationAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = hillshadeLayer("id", "source") {
      hillshadeIlluminationAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.hillshadeIlluminationAnchorAsExpression.toString())
    assertEquals(HillshadeIlluminationAnchor.MAP, layer.hillshadeIlluminationAnchor!!)
  }

  @Test
  @UiThreadTest
  fun hillshadeIlluminationDirectionTest() {
    val testValue = 1.0
    val layer = hillshadeLayer("id", "source") {
      hillshadeIlluminationDirection(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.hillshadeIlluminationDirection!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun hillshadeIlluminationDirectionAsExpressionTest() {
    val expression = literal(1.0)
    val layer = hillshadeLayer("id", "source") {
      hillshadeIlluminationDirection(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.hillshadeIlluminationDirectionAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.hillshadeIlluminationDirection!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun hillshadeShadowColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = hillshadeLayer("id", "source") {
      hillshadeShadowColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.hillshadeShadowColor?.toString())
  }

  @Test
  @UiThreadTest
  fun hillshadeShadowColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeShadowColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.hillshadeShadowColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.hillshadeShadowColor)
    assertEquals(Color.BLACK, layer.hillshadeShadowColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun hillshadeShadowColorAsColorIntTest() {
    val layer = hillshadeLayer("id", "source") {
      hillshadeShadowColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.hillshadeShadowColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun hillshadeShadowColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeShadowColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeShadowColorTransition)
  }

  @Test
  @UiThreadTest
  fun hillshadeShadowColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = hillshadeLayer("id", "source") {
      hillshadeShadowColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.hillshadeShadowColorTransition)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = hillshadeLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", HillshadeLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", HillshadeLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", HillshadeLayer.defaultMaxZoom)
    assertNotNull("defaultHillshadeAccentColor should not be null", HillshadeLayer.defaultHillshadeAccentColor)
    assertNotNull("defaultHillshadeAccentColorAsExpression should not be null", HillshadeLayer.defaultHillshadeAccentColorAsExpression)
    assertNotNull("defaultHillshadeAccentColorAsColorInt should not be null", HillshadeLayer.defaultHillshadeAccentColorAsColorInt)
    assertNotNull("defaultHillshadeAccentColorTransition should not be null", HillshadeLayer.defaultHillshadeAccentColorTransition)
    assertNotNull("defaultHillshadeExaggeration should not be null", HillshadeLayer.defaultHillshadeExaggeration)
    assertNotNull("defaultHillshadeExaggerationAsExpression should not be null", HillshadeLayer.defaultHillshadeExaggerationAsExpression)
    assertNotNull("defaultHillshadeExaggerationTransition should not be null", HillshadeLayer.defaultHillshadeExaggerationTransition)
    assertNotNull("defaultHillshadeHighlightColor should not be null", HillshadeLayer.defaultHillshadeHighlightColor)
    assertNotNull("defaultHillshadeHighlightColorAsExpression should not be null", HillshadeLayer.defaultHillshadeHighlightColorAsExpression)
    assertNotNull("defaultHillshadeHighlightColorAsColorInt should not be null", HillshadeLayer.defaultHillshadeHighlightColorAsColorInt)
    assertNotNull("defaultHillshadeHighlightColorTransition should not be null", HillshadeLayer.defaultHillshadeHighlightColorTransition)
    assertNotNull("defaultHillshadeIlluminationAnchor should not be null", HillshadeLayer.defaultHillshadeIlluminationAnchor)
    assertNotNull("defaultHillshadeIlluminationAnchorAsExpression should not be null", HillshadeLayer.defaultHillshadeIlluminationAnchorAsExpression)
    assertNotNull("defaultHillshadeIlluminationDirection should not be null", HillshadeLayer.defaultHillshadeIlluminationDirection)
    assertNotNull("defaultHillshadeIlluminationDirectionAsExpression should not be null", HillshadeLayer.defaultHillshadeIlluminationDirectionAsExpression)
    assertNotNull("defaultHillshadeShadowColor should not be null", HillshadeLayer.defaultHillshadeShadowColor)
    assertNotNull("defaultHillshadeShadowColorAsExpression should not be null", HillshadeLayer.defaultHillshadeShadowColorAsExpression)
    assertNotNull("defaultHillshadeShadowColorAsColorInt should not be null", HillshadeLayer.defaultHillshadeShadowColorAsColorInt)
    assertNotNull("defaultHillshadeShadowColorTransition should not be null", HillshadeLayer.defaultHillshadeShadowColorTransition)
  }

  @Test
  @UiThreadTest
  fun getLayerTest() {
    val hillshadeAccentColorTestValue = "rgba(0, 0, 0, 1)"
    val hillshadeExaggerationTestValue = 1.0
    val hillshadeHighlightColorTestValue = "rgba(0, 0, 0, 1)"
    val hillshadeIlluminationAnchorTestValue = HillshadeIlluminationAnchor.MAP
    val hillshadeIlluminationDirectionTestValue = 1.0
    val hillshadeShadowColorTestValue = "rgba(0, 0, 0, 1)"

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = hillshadeLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      hillshadeAccentColor(hillshadeAccentColorTestValue)
      hillshadeExaggeration(hillshadeExaggerationTestValue)
      hillshadeHighlightColor(hillshadeHighlightColorTestValue)
      hillshadeIlluminationAnchor(hillshadeIlluminationAnchorTestValue)
      hillshadeIlluminationDirection(hillshadeIlluminationDirectionTestValue)
      hillshadeShadowColor(hillshadeShadowColorTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as HillshadeLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(hillshadeAccentColorTestValue, cachedLayer.hillshadeAccentColor)
    assertEquals(hillshadeExaggerationTestValue, cachedLayer.hillshadeExaggeration)
    assertEquals(hillshadeHighlightColorTestValue, cachedLayer.hillshadeHighlightColor)
    assertEquals(hillshadeIlluminationAnchorTestValue, cachedLayer.hillshadeIlluminationAnchor)
    assertEquals(hillshadeIlluminationDirectionTestValue, cachedLayer.hillshadeIlluminationDirection)
    assertEquals(hillshadeShadowColorTestValue, cachedLayer.hillshadeShadowColor)
  }
}

// End of generated file.