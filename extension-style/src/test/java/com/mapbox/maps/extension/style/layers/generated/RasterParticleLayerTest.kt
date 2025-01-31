// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
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

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class RasterParticleLayerTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
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
    val layer = rasterParticleLayer("id", "source") {
      sourceLayer("test")
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals(
      "{id=id, source=source, source-layer=test, type=raster-particle}",
      valueSlot.captured.toString()
    )
  }

  @Test
  fun sourceLayerTestSet() {
    val layer = rasterParticleLayer("id", "source") {}
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
    val layer = rasterParticleLayer("id", "source") {
      minZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{id=id, source=source, type=raster-particle, minzoom=10.0}", valueSlot.captured.toString())
  }

  @Test
  fun minZoomTestSet() {
    val layer = rasterParticleLayer("id", "source") {}
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
    val layer = rasterParticleLayer("id", "source") {
      maxZoom(10.0)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertEquals("{maxzoom=10.0, id=id, source=source, type=raster-particle}", valueSlot.captured.toString())
  }

  @Test
  fun maxZoomTestSet() {
    val layer = rasterParticleLayer("id", "source") {}
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
    val layer = rasterParticleLayer("id", "source") {}
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

    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.filter?.toString())
    verify { style.getStyleLayerProperty("id", "filter") }
  }
  // Property getters and setters

  @Test
  fun rasterParticleArrayBandSet() {
    val layer = rasterParticleLayer("id", "source") {}
    val testValue = "abc"
    layer.bindTo(style)
    layer.rasterParticleArrayBand(testValue)
    verify { style.setStyleLayerProperty("id", "raster-particle-array-band", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun rasterParticleArrayBandGet() {
    val testValue = "abc"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), layer.rasterParticleArrayBand?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-array-band") }
  }
  // Expression Tests

  @Test
  fun rasterParticleArrayBandAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleArrayBand(expression)
    verify { style.setStyleLayerProperty("id", "raster-particle-array-band", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterParticleArrayBandAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterParticleArrayBandAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-array-band") }
  }

  @Test
  fun rasterParticleArrayBandAsExpressionGetNull() {
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterParticleArrayBandAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-particle-array-band") }
  }

  @Test
  fun rasterParticleArrayBandAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("abc", layer.rasterParticleArrayBandAsExpression.toString())
    val expectedValue = "abc"
    assertEquals(expectedValue, layer.rasterParticleArrayBand)
    verify { style.getStyleLayerProperty("id", "raster-particle-array-band") }
  }

  @Test
  fun rasterParticleColorSet() {
    val layer = rasterParticleLayer("id", "source") {}
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
    layer.rasterParticleColor(testValue)
    verify { style.setStyleLayerProperty("id", "raster-particle-color", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[interpolate, [linear], [heatmap-density], 0.0, [rgba, 0.0, 0.0, 0.0, 0.0], 1.0, [rgba, 0.0, 255.0, 0.0, 1.0]]")
  }

  @Test
  fun rasterParticleColorGet() {
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
    val layer = rasterParticleLayer("id", "source") { }
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
    assertEquals(expectedValue.toString(), layer.rasterParticleColor?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-color") }
  }

  @Test
  fun rasterParticleColorUseThemeSetAfterInitialization() {
    val layer = rasterParticleLayer("id", "source") {}
    val theme = "none"
    layer.bindTo(style)
    layer.rasterParticleColorUseTheme(theme)
    verify { style.setStyleLayerProperty("id", "raster-particle-color-use-theme", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), theme)
  }

  @Test
  fun rasterParticleColorUseThemeSet() {
    val theme = "none"
    val layer = rasterParticleLayer("id", "source") {
      rasterParticleColorUseTheme(theme)
    }
    layer.bindTo(style)
    verify { style.addStyleLayer(capture(valueSlot), any()) }
    assertTrue(valueSlot.captured.toString().contains("raster-particle-color-use-theme"))
  }

  @Test
  fun rasterParticleColorUseThemeGet() {
    val theme = "none"
    every { styleProperty.value } returns TypeUtils.wrapToValue(theme)
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(theme.toString(), layer.rasterParticleColorUseTheme?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-color-use-theme") }
  }
  // Expression Tests

  @Test
  fun rasterParticleCountSet() {
    val layer = rasterParticleLayer("id", "source") {}
    val testValue = 1L
    layer.bindTo(style)
    layer.rasterParticleCount(testValue)
    verify { style.setStyleLayerProperty("id", "raster-particle-count", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun rasterParticleCountGet() {
    val testValue = 1L
    // Internally rasterParticleCount is not handled as Long type
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue.toDouble())
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1L
    assertEquals(expectedValue.toString(), layer.rasterParticleCount?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-count") }
  }
  // Expression Tests

  @Test
  fun rasterParticleCountAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleCount(expression)
    verify { style.setStyleLayerProperty("id", "raster-particle-count", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterParticleCountAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterParticleCountAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-count") }
  }

  @Test
  fun rasterParticleCountAsExpressionGetNull() {
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterParticleCountAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-particle-count") }
  }

  @Test
  fun rasterParticleCountAsExpressionGetFromLiteral() {
    // Internally rasterParticleCount is not handled as Long type
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L.toDouble())
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals("1", layer.rasterParticleCountAsExpression.toString())
    val expectedValue = 1L
    assertEquals(expectedValue, layer.rasterParticleCount)
    verify { style.getStyleLayerProperty("id", "raster-particle-count") }
  }

  @Test
  fun rasterParticleFadeOpacityFactorSet() {
    val layer = rasterParticleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterParticleFadeOpacityFactor(testValue)
    verify { style.setStyleLayerProperty("id", "raster-particle-fade-opacity-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterParticleFadeOpacityFactorGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterParticleFadeOpacityFactor?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-fade-opacity-factor") }
  }
  // Expression Tests

  @Test
  fun rasterParticleFadeOpacityFactorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleFadeOpacityFactor(expression)
    verify { style.setStyleLayerProperty("id", "raster-particle-fade-opacity-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterParticleFadeOpacityFactorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterParticleFadeOpacityFactorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-fade-opacity-factor") }
  }

  @Test
  fun rasterParticleFadeOpacityFactorAsExpressionGetNull() {
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterParticleFadeOpacityFactorAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-particle-fade-opacity-factor") }
  }

  @Test
  fun rasterParticleFadeOpacityFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterParticleFadeOpacityFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleFadeOpacityFactor!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-particle-fade-opacity-factor") }
  }

  @Test
  fun rasterParticleFadeOpacityFactorTransitionSet() {
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleFadeOpacityFactorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-particle-fade-opacity-factor-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterParticleFadeOpacityFactorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterParticleFadeOpacityFactorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-fade-opacity-factor-transition") }
  }

  @Test
  fun rasterParticleFadeOpacityFactorTransitionSetDsl() {
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleFadeOpacityFactorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-particle-fade-opacity-factor-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterParticleMaxSpeedSet() {
    val layer = rasterParticleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterParticleMaxSpeed(testValue)
    verify { style.setStyleLayerProperty("id", "raster-particle-max-speed", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterParticleMaxSpeedGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterParticleMaxSpeed?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-max-speed") }
  }
  // Expression Tests

  @Test
  fun rasterParticleMaxSpeedAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleMaxSpeed(expression)
    verify { style.setStyleLayerProperty("id", "raster-particle-max-speed", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterParticleMaxSpeedAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterParticleMaxSpeedAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-max-speed") }
  }

  @Test
  fun rasterParticleMaxSpeedAsExpressionGetNull() {
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterParticleMaxSpeedAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-particle-max-speed") }
  }

  @Test
  fun rasterParticleMaxSpeedAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterParticleMaxSpeedAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleMaxSpeed!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-particle-max-speed") }
  }

  @Test
  fun rasterParticleResetRateFactorSet() {
    val layer = rasterParticleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterParticleResetRateFactor(testValue)
    verify { style.setStyleLayerProperty("id", "raster-particle-reset-rate-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterParticleResetRateFactorGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterParticleResetRateFactor?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-reset-rate-factor") }
  }
  // Expression Tests

  @Test
  fun rasterParticleResetRateFactorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleResetRateFactor(expression)
    verify { style.setStyleLayerProperty("id", "raster-particle-reset-rate-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterParticleResetRateFactorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterParticleResetRateFactorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-reset-rate-factor") }
  }

  @Test
  fun rasterParticleResetRateFactorAsExpressionGetNull() {
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterParticleResetRateFactorAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-particle-reset-rate-factor") }
  }

  @Test
  fun rasterParticleResetRateFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterParticleResetRateFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleResetRateFactor!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-particle-reset-rate-factor") }
  }

  @Test
  fun rasterParticleSpeedFactorSet() {
    val layer = rasterParticleLayer("id", "source") {}
    val testValue = 1.0
    layer.bindTo(style)
    layer.rasterParticleSpeedFactor(testValue)
    verify { style.setStyleLayerProperty("id", "raster-particle-speed-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun rasterParticleSpeedFactorGet() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), layer.rasterParticleSpeedFactor?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-speed-factor") }
  }
  // Expression Tests

  @Test
  fun rasterParticleSpeedFactorAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleSpeedFactor(expression)
    verify { style.setStyleLayerProperty("id", "raster-particle-speed-factor", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun rasterParticleSpeedFactorAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(expression.toString(), layer.rasterParticleSpeedFactorAsExpression?.toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-speed-factor") }
  }

  @Test
  fun rasterParticleSpeedFactorAsExpressionGetNull() {
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(null, layer.rasterParticleSpeedFactorAsExpression)
    verify { style.getStyleLayerProperty("id", "raster-particle-speed-factor") }
  }

  @Test
  fun rasterParticleSpeedFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(1.0, layer.rasterParticleSpeedFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.rasterParticleSpeedFactor!!, 1E-5)
    verify { style.getStyleLayerProperty("id", "raster-particle-speed-factor") }
  }

  @Test
  fun rasterParticleSpeedFactorTransitionSet() {
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleSpeedFactorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLayerProperty("id", "raster-particle-speed-factor-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun rasterParticleSpeedFactorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    assertEquals(transition.toValue().toString(), layer.rasterParticleSpeedFactorTransition?.toValue().toString())
    verify { style.getStyleLayerProperty("id", "raster-particle-speed-factor-transition") }
  }

  @Test
  fun rasterParticleSpeedFactorTransitionSetDsl() {
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.rasterParticleSpeedFactorTransition {
      duration(100)
      delay(200)
    }
    verify { style.setStyleLayerProperty("id", "raster-particle-speed-factor-transition", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{duration=100, delay=200}")
  }

  @Test
  fun visibilitySet() {
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(Visibility.NONE)
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(Visibility.NONE, layer.visibility)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun visibilityAsExpressionSet() {
    val layer = rasterParticleLayer("id", "source") {}
    layer.bindTo(style)
    layer.visibility(literal("none"))
    verify { style.setStyleLayerProperty("id", "visibility", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "none")
  }

  @Test
  fun visibilityAsExpressionGet() {
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns literal("none")

    val layer = rasterParticleLayer("id", "source") { }
    layer.bindTo(style)
    assertEquals(literal("none"), layer.visibilityAsExpression)
    verify { style.getStyleLayerProperty("id", "visibility") }
  }

  @Test
  fun getType() {
    val layer = rasterParticleLayer("id", "source") { }
    assertEquals("raster-particle", layer.getType())
  }

  @Test
  fun getRasterParticleLayerTest() {
    every { style.getStyleLayerProperty("id", "source") } returns StylePropertyValue(
      Value("source"),
      StylePropertyValueKind.CONSTANT
    )

    every { style.getStyleLayerProperty("id", "type") } returns StylePropertyValue(
      Value("raster-particle"),
      StylePropertyValueKind.CONSTANT
    )

    val layer = style.getLayer("id") as RasterParticleLayer
    assertNotNull(layer)
    assertNotNull(layer.delegate)
    assertEquals(style, layer.delegate)
    assertEquals("raster-particle", layer.getType())
    assertEquals("source", layer.sourceId)
    assertEquals("id", layer.layerId)
  }

  // Default property getter tests

  @Test
  fun defaultRasterParticleArrayBandTest() {
    val testValue = "abc"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = "abc"
    assertEquals(expectedValue.toString(), RasterParticleLayer.defaultRasterParticleArrayBand?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-array-band") }
  }
  // Expression Tests

  @Test
  fun defaultRasterParticleArrayBandAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterParticleLayer.defaultRasterParticleArrayBandAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-array-band") }
  }

  @Test
  fun defaultRasterParticleArrayBandAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    assertEquals("abc", RasterParticleLayer.defaultRasterParticleArrayBandAsExpression.toString())
    val expectedValue = "abc"
    assertEquals(expectedValue, RasterParticleLayer.defaultRasterParticleArrayBand)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-array-band") }
  }

  @Test
  fun defaultRasterParticleColorTest() {
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
    assertEquals(expectedValue.toString(), RasterParticleLayer.defaultRasterParticleColor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-color") }
  }

  @Test
  fun defaultRasterParticleColorUseThemeTest() {
    val testValue = "default"
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    assertEquals(testValue, RasterParticleLayer.defaultRasterParticleColorUseTheme)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-color-use-theme") }
  }

  // Expression Tests

  @Test
  fun defaultRasterParticleCountTest() {
    val testValue = 1L
    // Internally rasterParticleCount is not handled as Long type
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue.toDouble())
    val expectedValue = 1L
    assertEquals(expectedValue.toString(), RasterParticleLayer.defaultRasterParticleCount?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-count") }
  }
  // Expression Tests

  @Test
  fun defaultRasterParticleCountAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterParticleLayer.defaultRasterParticleCountAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-count") }
  }

  @Test
  fun defaultRasterParticleCountAsExpressionGetFromLiteral() {
    // Internally rasterParticleCount is not handled as Long type
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L.toDouble())
    assertEquals("1", RasterParticleLayer.defaultRasterParticleCountAsExpression.toString())
    val expectedValue = 1L
    assertEquals(expectedValue, RasterParticleLayer.defaultRasterParticleCount)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-count") }
  }

  @Test
  fun defaultRasterParticleFadeOpacityFactorTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterParticleLayer.defaultRasterParticleFadeOpacityFactor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-fade-opacity-factor") }
  }
  // Expression Tests

  @Test
  fun defaultRasterParticleFadeOpacityFactorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterParticleLayer.defaultRasterParticleFadeOpacityFactorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-fade-opacity-factor") }
  }

  @Test
  fun defaultRasterParticleFadeOpacityFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleFadeOpacityFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleFadeOpacityFactor!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-fade-opacity-factor") }
  }

  @Test
  fun defaultRasterParticleFadeOpacityFactorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterParticleLayer.defaultRasterParticleFadeOpacityFactorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-fade-opacity-factor-transition") }
  }

  @Test
  fun defaultRasterParticleMaxSpeedTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterParticleLayer.defaultRasterParticleMaxSpeed?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-max-speed") }
  }
  // Expression Tests

  @Test
  fun defaultRasterParticleMaxSpeedAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterParticleLayer.defaultRasterParticleMaxSpeedAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-max-speed") }
  }

  @Test
  fun defaultRasterParticleMaxSpeedAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleMaxSpeedAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleMaxSpeed!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-max-speed") }
  }

  @Test
  fun defaultRasterParticleResetRateFactorTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterParticleLayer.defaultRasterParticleResetRateFactor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-reset-rate-factor") }
  }
  // Expression Tests

  @Test
  fun defaultRasterParticleResetRateFactorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterParticleLayer.defaultRasterParticleResetRateFactorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-reset-rate-factor") }
  }

  @Test
  fun defaultRasterParticleResetRateFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleResetRateFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleResetRateFactor!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-reset-rate-factor") }
  }

  @Test
  fun defaultRasterParticleSpeedFactorTest() {
    val testValue = 1.0
    every { styleProperty.value } returns TypeUtils.wrapToValue(testValue)
    val expectedValue = 1.0
    assertEquals(expectedValue.toString(), RasterParticleLayer.defaultRasterParticleSpeedFactor?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-speed-factor") }
  }
  // Expression Tests

  @Test
  fun defaultRasterParticleSpeedFactorAsExpressionTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterParticleLayer.defaultRasterParticleSpeedFactorAsExpression?.toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-speed-factor") }
  }

  @Test
  fun defaultRasterParticleSpeedFactorAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleSpeedFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, RasterParticleLayer.defaultRasterParticleSpeedFactor!!, 1E-5)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-speed-factor") }
  }

  @Test
  fun defaultRasterParticleSpeedFactorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    every { styleProperty.kind } returns StylePropertyValueKind.TRANSITION

    assertEquals(transition.toValue().toString(), RasterParticleLayer.defaultRasterParticleSpeedFactorTransition?.toValue().toString())
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-speed-factor-transition") }
  }

  @Test
  fun defaultVisibilityTest() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("none")

    assertEquals(Visibility.NONE, RasterParticleLayer.defaultVisibility)
    verify { StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "visibility") }
  }
}

// End of generated file.