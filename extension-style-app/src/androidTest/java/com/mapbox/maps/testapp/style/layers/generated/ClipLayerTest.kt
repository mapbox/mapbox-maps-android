// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for ClipLayer
 */
@RunWith(AndroidJUnit4::class)
class ClipLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = clipLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = clipLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = clipLayer("id", "source") {
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
    val layer = clipLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun clipLayerScopeTest() {
    val testValue = listOf("a", "b", "c")
    val layer = clipLayer("id", "source") {
      clipLayerScope(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.clipLayerScope?.toString())
  }

  @Test
  @UiThreadTest
  fun clipLayerScopeAsExpressionTest() {
    val expression = literal(listOf("a", "b", "c"))
    val layer = clipLayer("id", "source") {
      clipLayerScope(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.clipLayerScopeAsExpression.toString())
    assertEquals(listOf("a", "b", "c"), layer.clipLayerScope!!)
  }

  @Test
  @UiThreadTest
  fun clipLayerTypesTest() {
    val testValue = listOf("model", "symbol")
    val layer = clipLayer("id", "source") {
      clipLayerTypes(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.clipLayerTypes?.toString())
  }

  @Test
  @UiThreadTest
  fun clipLayerTypesAsExpressionTest() {
    val expression = literal(listOf("model", "symbol"))
    val layer = clipLayer("id", "source") {
      clipLayerTypes(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.clipLayerTypesAsExpression.toString())
    assertEquals(listOf("model", "symbol"), layer.clipLayerTypes!!)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = clipLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = clipLayer("id", "source") {
      visibility(
        concat {
          literal("no")
          literal("ne")
        }
      )
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", ClipLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", ClipLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", ClipLayer.defaultMaxZoom)
    assertNotNull("defaultClipLayerScope should not be null", ClipLayer.defaultClipLayerScope)
    assertNotNull("defaultClipLayerScopeAsExpression should not be null", ClipLayer.defaultClipLayerScopeAsExpression)
    assertNotNull("defaultClipLayerTypes should not be null", ClipLayer.defaultClipLayerTypes)
    assertNotNull("defaultClipLayerTypesAsExpression should not be null", ClipLayer.defaultClipLayerTypesAsExpression)
  }

  @Test
  @UiThreadTest
  fun getLayerTest() {
    val filterTestValue = eq {
      get {
        literal("undefined")
      }
      literal(1.0)
    }
    val clipLayerScopeTestValue = listOf("a", "b", "c")
    val clipLayerTypesTestValue = listOf("model", "symbol")

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = clipLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      clipLayerScope(clipLayerScopeTestValue)
      clipLayerTypes(clipLayerTypesTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as ClipLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(clipLayerScopeTestValue, cachedLayer.clipLayerScope)
    assertEquals(clipLayerTypesTestValue, cachedLayer.clipLayerTypes)
  }
}

// End of generated file.