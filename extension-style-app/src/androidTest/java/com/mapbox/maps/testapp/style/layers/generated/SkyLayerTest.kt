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
 * Basic smoke tests for SkyLayer
 */
@RunWith(AndroidJUnit4::class)
class SkyLayerTest : BaseStyleTest() {

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
    val layer = skyLayer("id") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun skyAtmosphereColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = skyLayer("id") {
      skyAtmosphereColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.skyAtmosphereColor?.toString())
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = skyLayer("id") {
      skyAtmosphereColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.skyAtmosphereColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.skyAtmosphereColor)
    assertEquals(Color.BLACK, layer.skyAtmosphereColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereColorAsColorIntTest() {
    val layer = skyLayer("id") {
      skyAtmosphereColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.skyAtmosphereColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereHaloColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = skyLayer("id") {
      skyAtmosphereHaloColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.skyAtmosphereHaloColor?.toString())
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereHaloColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = skyLayer("id") {
      skyAtmosphereHaloColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.skyAtmosphereHaloColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.skyAtmosphereHaloColor)
    assertEquals(Color.BLACK, layer.skyAtmosphereHaloColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereHaloColorAsColorIntTest() {
    val layer = skyLayer("id") {
      skyAtmosphereHaloColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.skyAtmosphereHaloColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereSunTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = skyLayer("id") {
      skyAtmosphereSun(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.skyAtmosphereSun?.toString())
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereSunAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = skyLayer("id") {
      skyAtmosphereSun(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.skyAtmosphereSunAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.skyAtmosphereSun!!)
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereSunIntensityTest() {
    val testValue = 1.0
    val layer = skyLayer("id") {
      skyAtmosphereSunIntensity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.skyAtmosphereSunIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun skyAtmosphereSunIntensityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = skyLayer("id") {
      skyAtmosphereSunIntensity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.skyAtmosphereSunIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.skyAtmosphereSunIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun skyGradientTest() {
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
    val layer = skyLayer("id") {
      skyGradient(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.skyGradient?.toString())
  }

  @Test
  @UiThreadTest
  fun skyGradientCenterTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = skyLayer("id") {
      skyGradientCenter(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.skyGradientCenter?.toString())
  }

  @Test
  @UiThreadTest
  fun skyGradientCenterAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = skyLayer("id") {
      skyGradientCenter(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.skyGradientCenterAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.skyGradientCenter!!)
  }

  @Test
  @UiThreadTest
  fun skyGradientRadiusTest() {
    val testValue = 1.0
    val layer = skyLayer("id") {
      skyGradientRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.skyGradientRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun skyGradientRadiusAsExpressionTest() {
    val expression = literal(1.0)
    val layer = skyLayer("id") {
      skyGradientRadius(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.skyGradientRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.skyGradientRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun skyOpacityTest() {
    val testValue = 1.0
    val layer = skyLayer("id") {
      skyOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.skyOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun skyOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = skyLayer("id") {
      skyOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.skyOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.skyOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun skyOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = skyLayer("id") {
      skyOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.skyOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun skyOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = skyLayer("id") {
      skyOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.skyOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun skyTypeTest() {
    val layer = skyLayer("id") {
      skyType(SkyType.GRADIENT)
    }
    setupLayer(layer)
    assertEquals(SkyType.GRADIENT, layer.skyType)
  }

  @Test
  @UiThreadTest
  fun skyTypeAsExpressionTest() {
    val expression = literal("gradient")
    val layer = skyLayer("id") {
      skyType(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.skyTypeAsExpression.toString())
    assertEquals(SkyType.GRADIENT, layer.skyType!!)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = skyLayer("id") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = skyLayer("id") {
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
    assertNotNull("defaultVisibility should not be null", SkyLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", SkyLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", SkyLayer.defaultMaxZoom)
    assertNotNull("defaultSkyAtmosphereColor should not be null", SkyLayer.defaultSkyAtmosphereColor)
    assertNotNull("defaultSkyAtmosphereColorAsExpression should not be null", SkyLayer.defaultSkyAtmosphereColorAsExpression)
    assertNotNull("defaultSkyAtmosphereColorAsColorInt should not be null", SkyLayer.defaultSkyAtmosphereColorAsColorInt)
    assertNotNull("defaultSkyAtmosphereHaloColor should not be null", SkyLayer.defaultSkyAtmosphereHaloColor)
    assertNotNull("defaultSkyAtmosphereHaloColorAsExpression should not be null", SkyLayer.defaultSkyAtmosphereHaloColorAsExpression)
    assertNotNull("defaultSkyAtmosphereHaloColorAsColorInt should not be null", SkyLayer.defaultSkyAtmosphereHaloColorAsColorInt)
    assertNotNull("defaultSkyAtmosphereSun should not be null", SkyLayer.defaultSkyAtmosphereSun)
    assertNotNull("defaultSkyAtmosphereSunAsExpression should not be null", SkyLayer.defaultSkyAtmosphereSunAsExpression)
    assertNotNull("defaultSkyAtmosphereSunIntensity should not be null", SkyLayer.defaultSkyAtmosphereSunIntensity)
    assertNotNull("defaultSkyAtmosphereSunIntensityAsExpression should not be null", SkyLayer.defaultSkyAtmosphereSunIntensityAsExpression)
    assertNotNull("defaultSkyGradient should not be null", SkyLayer.defaultSkyGradient)
    assertNotNull("defaultSkyGradientCenter should not be null", SkyLayer.defaultSkyGradientCenter)
    assertNotNull("defaultSkyGradientCenterAsExpression should not be null", SkyLayer.defaultSkyGradientCenterAsExpression)
    assertNotNull("defaultSkyGradientRadius should not be null", SkyLayer.defaultSkyGradientRadius)
    assertNotNull("defaultSkyGradientRadiusAsExpression should not be null", SkyLayer.defaultSkyGradientRadiusAsExpression)
    assertNotNull("defaultSkyOpacity should not be null", SkyLayer.defaultSkyOpacity)
    assertNotNull("defaultSkyOpacityAsExpression should not be null", SkyLayer.defaultSkyOpacityAsExpression)
    assertNotNull("defaultSkyOpacityTransition should not be null", SkyLayer.defaultSkyOpacityTransition)
    assertNotNull("defaultSkyType should not be null", SkyLayer.defaultSkyType)
    assertNotNull("defaultSkyTypeAsExpression should not be null", SkyLayer.defaultSkyTypeAsExpression)
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
    val skyAtmosphereColorTestValue = "rgba(0, 0, 0, 1)"
    val skyAtmosphereHaloColorTestValue = "rgba(0, 0, 0, 1)"
    val skyAtmosphereSunTestValue = listOf(0.0, 1.0)
    val skyAtmosphereSunIntensityTestValue = 1.0
    val skyGradientTestValue = interpolate {
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
    val skyGradientCenterTestValue = listOf(0.0, 1.0)
    val skyGradientRadiusTestValue = 1.0
    val skyOpacityTestValue = 1.0
    val skyTypeTestValue = SkyType.GRADIENT

    val layer = skyLayer("id") {
      filter(filterTestValue)
      skyAtmosphereColor(skyAtmosphereColorTestValue)
      skyAtmosphereHaloColor(skyAtmosphereHaloColorTestValue)
      skyAtmosphereSun(skyAtmosphereSunTestValue)
      skyAtmosphereSunIntensity(skyAtmosphereSunIntensityTestValue)
      skyGradient(skyGradientTestValue)
      skyGradientCenter(skyGradientCenterTestValue)
      skyGradientRadius(skyGradientRadiusTestValue)
      skyOpacity(skyOpacityTestValue)
      skyType(skyTypeTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as SkyLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(skyAtmosphereColorTestValue, cachedLayer.skyAtmosphereColor)
    assertEquals(skyAtmosphereHaloColorTestValue, cachedLayer.skyAtmosphereHaloColor)
    assertEquals(skyAtmosphereSunTestValue, cachedLayer.skyAtmosphereSun)
    assertEquals(skyAtmosphereSunIntensityTestValue, cachedLayer.skyAtmosphereSunIntensity)
    assertEquals(skyGradientTestValue, cachedLayer.skyGradient)
    assertEquals(skyGradientCenterTestValue, cachedLayer.skyGradientCenter)
    assertEquals(skyGradientRadiusTestValue, cachedLayer.skyGradientRadius)
    assertEquals(skyOpacityTestValue, cachedLayer.skyOpacity)
    assertEquals(skyTypeTestValue, cachedLayer.skyType)
  }
}

// End of generated file.