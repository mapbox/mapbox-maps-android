// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for RasterParticleLayer
 */
@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
class RasterParticleLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = rasterParticleLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = rasterParticleLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = rasterParticleLayer("id", "source") {
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
    val layer = rasterParticleLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun rasterParticleArrayBandTest() {
    val testValue = "abc"
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleArrayBand(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.rasterParticleArrayBand?.toString())
  }

  @Test
  @UiThreadTest
  fun rasterParticleArrayBandAsExpressionTest() {
    val expression = literal("abc")
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleArrayBand(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.rasterParticleArrayBandAsExpression.toString())
    assertEquals("abc", layer.rasterParticleArrayBand!!)
  }

  @Test
  @UiThreadTest
  fun rasterParticleColorTest() {
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
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.rasterParticleColor?.toString())
  }

  @Test
  @UiThreadTest
  fun rasterParticleColorUseTheme() {
    val theme = "none"
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.rasterParticleColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun rasterParticleCountTest() {
    val testValue = 1L
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleCount(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.rasterParticleCount?.toString())
  }

  @Test
  @UiThreadTest
  fun rasterParticleCountAsExpressionTest() {
    val expression = literal(1L)
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleCount(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.rasterParticleCountAsExpression.toString())
    assertEquals(1L, layer.rasterParticleCount!!)
  }

  @Test
  @UiThreadTest
  fun rasterParticleFadeOpacityFactorTest() {
    val testValue = 1.0
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleFadeOpacityFactor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterParticleFadeOpacityFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleFadeOpacityFactorAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleFadeOpacityFactor(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterParticleFadeOpacityFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleFadeOpacityFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleFadeOpacityFactorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleFadeOpacityFactorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterParticleFadeOpacityFactorTransition)
  }

  @Test
  @UiThreadTest
  fun rasterParticleFadeOpacityFactorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleFadeOpacityFactorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterParticleFadeOpacityFactorTransition)
  }

  @Test
  @UiThreadTest
  fun rasterParticleMaxSpeedTest() {
    val testValue = 1.0
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleMaxSpeed(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterParticleMaxSpeed!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleMaxSpeedAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleMaxSpeed(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterParticleMaxSpeedAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleMaxSpeed!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleResetRateFactorTest() {
    val testValue = 1.0
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleResetRateFactor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterParticleResetRateFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleResetRateFactorAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleResetRateFactor(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterParticleResetRateFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleResetRateFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleSpeedFactorTest() {
    val testValue = 1.0
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleSpeedFactor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.rasterParticleSpeedFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleSpeedFactorAsExpressionTest() {
    val expression = literal(1.0)
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleSpeedFactor(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.rasterParticleSpeedFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleSpeedFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun rasterParticleSpeedFactorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleSpeedFactorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterParticleSpeedFactorTransition)
  }

  @Test
  @UiThreadTest
  fun rasterParticleSpeedFactorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleSpeedFactorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.rasterParticleSpeedFactorTransition)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = rasterParticleLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = rasterParticleLayer("id", "source") {
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
    assertNotNull("defaultVisibility should not be null", RasterParticleLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", RasterParticleLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", RasterParticleLayer.defaultMaxZoom)
    assertNotNull("defaultRasterParticleArrayBand should not be null", RasterParticleLayer.defaultRasterParticleArrayBand)
    assertNotNull("defaultRasterParticleArrayBandAsExpression should not be null", RasterParticleLayer.defaultRasterParticleArrayBandAsExpression)
    assertNotNull("defaultRasterParticleColorUseTheme should not be null", RasterParticleLayer.defaultRasterParticleColorUseTheme)
    assertNotNull("defaultRasterParticleCount should not be null", RasterParticleLayer.defaultRasterParticleCount)
    assertNotNull("defaultRasterParticleCountAsExpression should not be null", RasterParticleLayer.defaultRasterParticleCountAsExpression)
    assertNotNull("defaultRasterParticleFadeOpacityFactor should not be null", RasterParticleLayer.defaultRasterParticleFadeOpacityFactor)
    assertNotNull("defaultRasterParticleFadeOpacityFactorAsExpression should not be null", RasterParticleLayer.defaultRasterParticleFadeOpacityFactorAsExpression)
    assertNotNull("defaultRasterParticleFadeOpacityFactorTransition should not be null", RasterParticleLayer.defaultRasterParticleFadeOpacityFactorTransition)
    assertNotNull("defaultRasterParticleMaxSpeed should not be null", RasterParticleLayer.defaultRasterParticleMaxSpeed)
    assertNotNull("defaultRasterParticleMaxSpeedAsExpression should not be null", RasterParticleLayer.defaultRasterParticleMaxSpeedAsExpression)
    assertNotNull("defaultRasterParticleResetRateFactor should not be null", RasterParticleLayer.defaultRasterParticleResetRateFactor)
    assertNotNull("defaultRasterParticleResetRateFactorAsExpression should not be null", RasterParticleLayer.defaultRasterParticleResetRateFactorAsExpression)
    assertNotNull("defaultRasterParticleSpeedFactor should not be null", RasterParticleLayer.defaultRasterParticleSpeedFactor)
    assertNotNull("defaultRasterParticleSpeedFactorAsExpression should not be null", RasterParticleLayer.defaultRasterParticleSpeedFactorAsExpression)
    assertNotNull("defaultRasterParticleSpeedFactorTransition should not be null", RasterParticleLayer.defaultRasterParticleSpeedFactorTransition)
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
    val rasterParticleArrayBandTestValue = "abc"
    val rasterParticleColorTestValue = interpolate {
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
    val rasterParticleColorUseThemeTestValue = "default"
    val rasterParticleCountTestValue = 1L
    val rasterParticleFadeOpacityFactorTestValue = 1.0
    val rasterParticleMaxSpeedTestValue = 1.0
    val rasterParticleResetRateFactorTestValue = 1.0
    val rasterParticleSpeedFactorTestValue = 1.0

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = rasterParticleLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      rasterParticleArrayBand(rasterParticleArrayBandTestValue)
      rasterParticleColor(rasterParticleColorTestValue)
      rasterParticleColorUseTheme(rasterParticleColorUseThemeTestValue)
      rasterParticleCount(rasterParticleCountTestValue)
      rasterParticleFadeOpacityFactor(rasterParticleFadeOpacityFactorTestValue)
      rasterParticleMaxSpeed(rasterParticleMaxSpeedTestValue)
      rasterParticleResetRateFactor(rasterParticleResetRateFactorTestValue)
      rasterParticleSpeedFactor(rasterParticleSpeedFactorTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as RasterParticleLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(rasterParticleArrayBandTestValue, cachedLayer.rasterParticleArrayBand)
    assertEquals(rasterParticleColorTestValue, cachedLayer.rasterParticleColor)
    assertEquals(rasterParticleColorUseThemeTestValue, cachedLayer.rasterParticleColorUseTheme)
    assertEquals(rasterParticleCountTestValue, cachedLayer.rasterParticleCount)
    assertEquals(rasterParticleFadeOpacityFactorTestValue, cachedLayer.rasterParticleFadeOpacityFactor)
    assertEquals(rasterParticleMaxSpeedTestValue, cachedLayer.rasterParticleMaxSpeed)
    assertEquals(rasterParticleResetRateFactorTestValue, cachedLayer.rasterParticleResetRateFactor)
    assertEquals(rasterParticleSpeedFactorTestValue, cachedLayer.rasterParticleSpeedFactor)
  }
}

// End of generated file.