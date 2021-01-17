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
 * Basic smoke tests for RasterLayer
 */
@RunWith(AndroidJUnit4::class)
class RasterLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = rasterLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = rasterLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = rasterLayer("id", "source") {
      maxZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.maxZoom)
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun rasterBrightnessMaxTest() {
    val testValue = 1.0
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMax(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterBrightnessMax!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterBrightnessMaxAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMax(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterBrightnessMaxAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterBrightnessMax!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterBrightnessMaxTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMaxTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterBrightnessMaxTransition)
  }

  @Test
  @UiThreadTest
  fun rasterBrightnessMaxTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMaxTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterBrightnessMaxTransition)
  }

  @Test
  @UiThreadTest
  fun rasterBrightnessMinTest() {
    val testValue = 1.0
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMin(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterBrightnessMin!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterBrightnessMinAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMin(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterBrightnessMinAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterBrightnessMin!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterBrightnessMinTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMinTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterBrightnessMinTransition)
  }

  @Test
  @UiThreadTest
  fun rasterBrightnessMinTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterBrightnessMinTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterBrightnessMinTransition)
  }

  @Test
  @UiThreadTest
  fun rasterContrastTest() {
    val testValue = 1.0
    val layer = rasterLayer("id", "source") {
      rasterContrast(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterContrast!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterContrastAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterLayer("id", "source") {
      rasterContrast(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterContrastAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterContrast!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterContrastTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterContrastTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterContrastTransition)
  }

  @Test
  @UiThreadTest
  fun rasterContrastTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterContrastTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterContrastTransition)
  }

  @Test
  @UiThreadTest
  fun rasterFadeDurationTest() {
    val testValue = 1.0
    val layer = rasterLayer("id", "source") {
      rasterFadeDuration(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterFadeDuration!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterFadeDurationAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterLayer("id", "source") {
      rasterFadeDuration(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterFadeDurationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterFadeDuration!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterHueRotateTest() {
    val testValue = 1.0
    val layer = rasterLayer("id", "source") {
      rasterHueRotate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterHueRotate!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterHueRotateAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterLayer("id", "source") {
      rasterHueRotate(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterHueRotateAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterHueRotate!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterHueRotateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterHueRotateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterHueRotateTransition)
  }

  @Test
  @UiThreadTest
  fun rasterHueRotateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterHueRotateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterHueRotateTransition)
  }

  @Test
  @UiThreadTest
  fun rasterOpacityTest() {
    val testValue = 1.0
    val layer = rasterLayer("id", "source") {
      rasterOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterLayer("id", "source") {
      rasterOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun rasterOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun rasterResamplingTest() {
    val layer = rasterLayer("id", "source") {
      rasterResampling(RasterResampling.LINEAR)
    }
    setupLayer(layer)
    assertEquals(RasterResampling.LINEAR, layer.rasterResampling)
  }

  @Test
  @UiThreadTest
  fun rasterResamplingAsExpressionTest() {
    val expression = literal("linear")
    val layer = rasterLayer("id", "source") {
      rasterResampling(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.rasterResamplingAsExpression.toString())
    assertEquals(RasterResampling.LINEAR, layer.rasterResampling!!)
  }

  @Test
  @UiThreadTest
  fun rasterSaturationTest() {
    val testValue = 1.0
    val layer = rasterLayer("id", "source") {
      rasterSaturation(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterSaturation!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterSaturationAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterLayer("id", "source") {
      rasterSaturation(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterSaturationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterSaturation!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterSaturationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterSaturationTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterSaturationTransition)
  }

  @Test
  @UiThreadTest
  fun rasterSaturationTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterSaturationTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterSaturationTransition)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = rasterLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", RasterLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", RasterLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", RasterLayer.defaultMaxZoom)
    assertNotNull("defaultRasterBrightnessMax should not be null", RasterLayer.defaultRasterBrightnessMax)
    assertNotNull("defaultRasterBrightnessMaxAsExpression should not be null", RasterLayer.defaultRasterBrightnessMaxAsExpression)
    assertNotNull("defaultRasterBrightnessMaxTransition should not be null", RasterLayer.defaultRasterBrightnessMaxTransition)
    assertNotNull("defaultRasterBrightnessMin should not be null", RasterLayer.defaultRasterBrightnessMin)
    assertNotNull("defaultRasterBrightnessMinAsExpression should not be null", RasterLayer.defaultRasterBrightnessMinAsExpression)
    assertNotNull("defaultRasterBrightnessMinTransition should not be null", RasterLayer.defaultRasterBrightnessMinTransition)
    assertNotNull("defaultRasterContrast should not be null", RasterLayer.defaultRasterContrast)
    assertNotNull("defaultRasterContrastAsExpression should not be null", RasterLayer.defaultRasterContrastAsExpression)
    assertNotNull("defaultRasterContrastTransition should not be null", RasterLayer.defaultRasterContrastTransition)
    assertNotNull("defaultRasterFadeDuration should not be null", RasterLayer.defaultRasterFadeDuration)
    assertNotNull("defaultRasterFadeDurationAsExpression should not be null", RasterLayer.defaultRasterFadeDurationAsExpression)
    assertNotNull("defaultRasterHueRotate should not be null", RasterLayer.defaultRasterHueRotate)
    assertNotNull("defaultRasterHueRotateAsExpression should not be null", RasterLayer.defaultRasterHueRotateAsExpression)
    assertNotNull("defaultRasterHueRotateTransition should not be null", RasterLayer.defaultRasterHueRotateTransition)
    assertNotNull("defaultRasterOpacity should not be null", RasterLayer.defaultRasterOpacity)
    assertNotNull("defaultRasterOpacityAsExpression should not be null", RasterLayer.defaultRasterOpacityAsExpression)
    assertNotNull("defaultRasterOpacityTransition should not be null", RasterLayer.defaultRasterOpacityTransition)
    assertNotNull("defaultRasterResampling should not be null", RasterLayer.defaultRasterResampling)
    assertNotNull("defaultRasterResamplingAsExpression should not be null", RasterLayer.defaultRasterResamplingAsExpression)
    assertNotNull("defaultRasterSaturation should not be null", RasterLayer.defaultRasterSaturation)
    assertNotNull("defaultRasterSaturationAsExpression should not be null", RasterLayer.defaultRasterSaturationAsExpression)
    assertNotNull("defaultRasterSaturationTransition should not be null", RasterLayer.defaultRasterSaturationTransition)
  }
}

// End of generated file.