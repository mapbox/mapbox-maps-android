// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

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
 * Basic smoke tests for HeatmapLayer
 */
@RunWith(AndroidJUnit4::class)
class HeatmapLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = heatmapLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = heatmapLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = heatmapLayer("id", "source") {
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
    val layer = heatmapLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun heatmapColorTest() {
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
    val layer = heatmapLayer("id", "source") {
      heatmapColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.heatmapColor?.toString())
  }

  @Test
  @UiThreadTest
  fun heatmapIntensityTest() {
    val testValue = 1.0
    val layer = heatmapLayer("id", "source") {
      heatmapIntensity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.heatmapIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun heatmapIntensityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = heatmapLayer("id", "source") {
      heatmapIntensity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.heatmapIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.heatmapIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun heatmapIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = heatmapLayer("id", "source") {
      heatmapIntensityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.heatmapIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun heatmapIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = heatmapLayer("id", "source") {
      heatmapIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.heatmapIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun heatmapOpacityTest() {
    val testValue = 1.0
    val layer = heatmapLayer("id", "source") {
      heatmapOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.heatmapOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun heatmapOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = heatmapLayer("id", "source") {
      heatmapOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.heatmapOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.heatmapOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun heatmapOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = heatmapLayer("id", "source") {
      heatmapOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.heatmapOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun heatmapOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = heatmapLayer("id", "source") {
      heatmapOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.heatmapOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun heatmapRadiusTest() {
    val testValue = 1.0
    val layer = heatmapLayer("id", "source") {
      heatmapRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.heatmapRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun heatmapRadiusAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = heatmapLayer("id", "source") {
      heatmapRadius(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.heatmapRadiusAsExpression.toString())
    assertEquals(null, layer.heatmapRadius)
  }

  @Test
  @UiThreadTest
  fun heatmapRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = heatmapLayer("id", "source") {
      heatmapRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.heatmapRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun heatmapRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = heatmapLayer("id", "source") {
      heatmapRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.heatmapRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun heatmapWeightTest() {
    val testValue = 1.0
    val layer = heatmapLayer("id", "source") {
      heatmapWeight(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.heatmapWeight!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun heatmapWeightAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = heatmapLayer("id", "source") {
      heatmapWeight(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.heatmapWeightAsExpression.toString())
    assertEquals(null, layer.heatmapWeight)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = heatmapLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", HeatmapLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", HeatmapLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", HeatmapLayer.defaultMaxZoom)
    assertNotNull("defaultHeatmapColor should not be null", HeatmapLayer.defaultHeatmapColor)
    assertNotNull("defaultHeatmapIntensity should not be null", HeatmapLayer.defaultHeatmapIntensity)
    assertNotNull("defaultHeatmapIntensityAsExpression should not be null", HeatmapLayer.defaultHeatmapIntensityAsExpression)
    assertNotNull("defaultHeatmapIntensityTransition should not be null", HeatmapLayer.defaultHeatmapIntensityTransition)
    assertNotNull("defaultHeatmapOpacity should not be null", HeatmapLayer.defaultHeatmapOpacity)
    assertNotNull("defaultHeatmapOpacityAsExpression should not be null", HeatmapLayer.defaultHeatmapOpacityAsExpression)
    assertNotNull("defaultHeatmapOpacityTransition should not be null", HeatmapLayer.defaultHeatmapOpacityTransition)
    assertNotNull("defaultHeatmapRadius should not be null", HeatmapLayer.defaultHeatmapRadius)
    assertNotNull("defaultHeatmapRadiusAsExpression should not be null", HeatmapLayer.defaultHeatmapRadiusAsExpression)
    assertNotNull("defaultHeatmapRadiusTransition should not be null", HeatmapLayer.defaultHeatmapRadiusTransition)
    assertNotNull("defaultHeatmapWeight should not be null", HeatmapLayer.defaultHeatmapWeight)
    assertNotNull("defaultHeatmapWeightAsExpression should not be null", HeatmapLayer.defaultHeatmapWeightAsExpression)
  }
}

// End of generated file.