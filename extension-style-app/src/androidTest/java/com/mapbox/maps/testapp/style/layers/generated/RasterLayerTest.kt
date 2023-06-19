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
  fun rasterColorTest() {
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
    val layer = rasterLayer("id", "source") {
      rasterColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.rasterColor?.toString())
  }

  @Test
  @UiThreadTest
  fun rasterColorMixTest() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0)
    val layer = rasterLayer("id", "source") {
      rasterColorMix(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.rasterColorMix?.toString())
  }

  @Test
  @UiThreadTest
  fun rasterColorMixAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0, 2.0, 3.0))
    val layer = rasterLayer("id", "source") {
      rasterColorMix(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.rasterColorMixAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0, 3.0), layer.rasterColorMix!!)
  }

  @Test
  @UiThreadTest
  fun rasterColorMixTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterColorMixTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterColorMixTransition)
  }

  @Test
  @UiThreadTest
  fun rasterColorMixTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterColorMixTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterColorMixTransition)
  }

  @Test
  @UiThreadTest
  fun rasterColorRangeTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = rasterLayer("id", "source") {
      rasterColorRange(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.rasterColorRange?.toString())
  }

  @Test
  @UiThreadTest
  fun rasterColorRangeAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = rasterLayer("id", "source") {
      rasterColorRange(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.rasterColorRangeAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.rasterColorRange!!)
  }

  @Test
  @UiThreadTest
  fun rasterColorRangeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterColorRangeTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterColorRangeTransition)
  }

  @Test
  @UiThreadTest
  fun rasterColorRangeTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterLayer("id", "source") {
      rasterColorRangeTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterColorRangeTransition)
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
    assertNotNull("defaultRasterColorMix should not be null", RasterLayer.defaultRasterColorMix)
    assertNotNull("defaultRasterColorMixAsExpression should not be null", RasterLayer.defaultRasterColorMixAsExpression)
    assertNotNull("defaultRasterColorMixTransition should not be null", RasterLayer.defaultRasterColorMixTransition)
    assertNotNull("defaultRasterColorRange should not be null", RasterLayer.defaultRasterColorRange)
    assertNotNull("defaultRasterColorRangeAsExpression should not be null", RasterLayer.defaultRasterColorRangeAsExpression)
    assertNotNull("defaultRasterColorRangeTransition should not be null", RasterLayer.defaultRasterColorRangeTransition)
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

  @Test
  @UiThreadTest
  fun getLayerTest() {
    val rasterBrightnessMaxTestValue = 1.0
    val rasterBrightnessMinTestValue = 1.0
    val rasterColorTestValue = interpolate {
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
    val rasterColorMixTestValue = listOf(0.0, 1.0, 2.0, 3.0)
    val rasterColorRangeTestValue = listOf(0.0, 1.0)
    val rasterContrastTestValue = 1.0
    val rasterFadeDurationTestValue = 1.0
    val rasterHueRotateTestValue = 1.0
    val rasterOpacityTestValue = 1.0
    val rasterResamplingTestValue = RasterResampling.LINEAR
    val rasterSaturationTestValue = 1.0

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = rasterLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      rasterBrightnessMax(rasterBrightnessMaxTestValue)
      rasterBrightnessMin(rasterBrightnessMinTestValue)
      rasterColor(rasterColorTestValue)
      rasterColorMix(rasterColorMixTestValue)
      rasterColorRange(rasterColorRangeTestValue)
      rasterContrast(rasterContrastTestValue)
      rasterFadeDuration(rasterFadeDurationTestValue)
      rasterHueRotate(rasterHueRotateTestValue)
      rasterOpacity(rasterOpacityTestValue)
      rasterResampling(rasterResamplingTestValue)
      rasterSaturation(rasterSaturationTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as RasterLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(rasterBrightnessMaxTestValue, cachedLayer.rasterBrightnessMax)
    assertEquals(rasterBrightnessMinTestValue, cachedLayer.rasterBrightnessMin)
    assertEquals(rasterColorTestValue, cachedLayer.rasterColor)
    assertEquals(rasterColorMixTestValue, cachedLayer.rasterColorMix)
    assertEquals(rasterColorRangeTestValue, cachedLayer.rasterColorRange)
    assertEquals(rasterContrastTestValue, cachedLayer.rasterContrast)
    assertEquals(rasterFadeDurationTestValue, cachedLayer.rasterFadeDuration)
    assertEquals(rasterHueRotateTestValue, cachedLayer.rasterHueRotate)
    assertEquals(rasterOpacityTestValue, cachedLayer.rasterOpacity)
    assertEquals(rasterResamplingTestValue, cachedLayer.rasterResampling)
    assertEquals(rasterSaturationTestValue, cachedLayer.rasterSaturation)
  }
}

// End of generated file.