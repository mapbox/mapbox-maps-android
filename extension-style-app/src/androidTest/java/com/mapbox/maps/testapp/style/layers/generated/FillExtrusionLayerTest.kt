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
 * Basic smoke tests for FillExtrusionLayer
 */
@RunWith(AndroidJUnit4::class)
class FillExtrusionLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = fillExtrusionLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = fillExtrusionLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = fillExtrusionLayer("id", "source") {
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
    val layer = fillExtrusionLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun fillExtrusionEdgeRadiusTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionEdgeRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionEdgeRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionEdgeRadiusAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionEdgeRadius(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionEdgeRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionEdgeRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundAttenuationTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundAttenuation(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionAmbientOcclusionGroundAttenuation!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundAttenuationAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundAttenuation(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionGroundAttenuationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionGroundAttenuation!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundAttenuationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundAttenuationTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionGroundAttenuationTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundAttenuationTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundAttenuationTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionGroundAttenuationTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundRadiusTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionAmbientOcclusionGroundRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundRadiusAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundRadius(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionGroundRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionGroundRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionGroundRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionGroundRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionGroundRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionGroundRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionAmbientOcclusionIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionAmbientOcclusionRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadius(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionWallRadiusTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionWallRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionAmbientOcclusionWallRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionWallRadiusAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionWallRadius(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionWallRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionWallRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionWallRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionWallRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionWallRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionWallRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionWallRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionWallRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBase(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionBase!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBase(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionBaseAsExpression.toString())
    assertEquals(null, layer.fillExtrusionBase)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBaseTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionBaseTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBaseTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionBaseTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionColorAsExpression.toString())
    assertEquals(null, layer.fillExtrusionColor)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorAsColorIntTest() {
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillExtrusionColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionCutoffFadeRangeTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionCutoffFadeRange(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionCutoffFadeRange!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionCutoffFadeRangeAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionCutoffFadeRange(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionCutoffFadeRangeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionCutoffFadeRange!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionFloodLightColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionFloodLightColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.fillExtrusionFloodLightColor)
    assertEquals(Color.BLACK, layer.fillExtrusionFloodLightColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightColorAsColorIntTest() {
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillExtrusionFloodLightColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundAttenuationTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundAttenuation(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionFloodLightGroundAttenuation!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundAttenuationAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundAttenuation(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionFloodLightGroundAttenuationAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionFloodLightGroundAttenuation!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundAttenuationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundAttenuationTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightGroundAttenuationTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundAttenuationTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundAttenuationTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightGroundAttenuationTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundRadiusTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionFloodLightGroundRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundRadiusAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundRadius(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionFloodLightGroundRadiusAsExpression.toString())
    assertEquals(null, layer.fillExtrusionFloodLightGroundRadius)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightGroundRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightGroundRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightGroundRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightGroundRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightIntensityTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightIntensity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionFloodLightIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightIntensityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightIntensity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionFloodLightIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionFloodLightIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightIntensityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightWallRadiusTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightWallRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionFloodLightWallRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightWallRadiusAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightWallRadius(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionFloodLightWallRadiusAsExpression.toString())
    assertEquals(null, layer.fillExtrusionFloodLightWallRadius)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightWallRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightWallRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightWallRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionFloodLightWallRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionFloodLightWallRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionFloodLightWallRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeight(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionHeight!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeight(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionHeightAsExpression.toString())
    assertEquals(null, layer.fillExtrusionHeight)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeightTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionHeightTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeightTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionHeightTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionPatternTest() {
    val testValue = "abc"
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionPattern(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionPattern?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionPatternAsExpressionTest() {
    val expression = image {
      string {
        get {
          literal("resolvedImage")
        }
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionPattern(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionPatternAsExpression.toString())
    assertEquals(null, layer.fillExtrusionPattern)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionRoundedRoofTest() {
    val testValue = true
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionRoundedRoof(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionRoundedRoof?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionRoundedRoofAsExpressionTest() {
    val expression = literal(true)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionRoundedRoof(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionRoundedRoofAsExpression.toString())
    assertEquals(true, layer.fillExtrusionRoundedRoof!!)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.fillExtrusionTranslate!!)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateAnchorTest() {
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateAnchor(FillExtrusionTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(FillExtrusionTranslateAnchor.MAP, layer.fillExtrusionTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionTranslateAnchorAsExpression.toString())
    assertEquals(FillExtrusionTranslateAnchor.MAP, layer.fillExtrusionTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalGradientTest() {
    val testValue = true
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalGradient(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionVerticalGradient?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalGradientAsExpressionTest() {
    val expression = literal(true)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalGradient(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionVerticalGradientAsExpression.toString())
    assertEquals(true, layer.fillExtrusionVerticalGradient!!)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalScaleTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalScale(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionVerticalScale!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalScaleAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalScale(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionVerticalScaleAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionVerticalScale!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalScaleTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalScaleTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionVerticalScaleTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalScaleTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalScaleTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionVerticalScaleTransition)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = fillExtrusionLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = fillExtrusionLayer("id", "source") {
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
    assertNotNull("defaultVisibility should not be null", FillExtrusionLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", FillExtrusionLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", FillExtrusionLayer.defaultMaxZoom)
    assertNotNull("defaultFillExtrusionEdgeRadius should not be null", FillExtrusionLayer.defaultFillExtrusionEdgeRadius)
    assertNotNull("defaultFillExtrusionEdgeRadiusAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionEdgeRadiusAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionGroundAttenuation should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuation)
    assertNotNull("defaultFillExtrusionAmbientOcclusionGroundAttenuationAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuationAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionGroundAttenuationTransition should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuationTransition)
    assertNotNull("defaultFillExtrusionAmbientOcclusionGroundRadius should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadius)
    assertNotNull("defaultFillExtrusionAmbientOcclusionGroundRadiusAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadiusAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionGroundRadiusTransition should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadiusTransition)
    assertNotNull("defaultFillExtrusionAmbientOcclusionIntensity should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensity)
    assertNotNull("defaultFillExtrusionAmbientOcclusionIntensityAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionIntensityTransition should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityTransition)
    assertNotNull("defaultFillExtrusionAmbientOcclusionRadius should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadius)
    assertNotNull("defaultFillExtrusionAmbientOcclusionRadiusAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionRadiusTransition should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusTransition)
    assertNotNull("defaultFillExtrusionAmbientOcclusionWallRadius should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadius)
    assertNotNull("defaultFillExtrusionAmbientOcclusionWallRadiusAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadiusAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionWallRadiusTransition should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadiusTransition)
    assertNotNull("defaultFillExtrusionBase should not be null", FillExtrusionLayer.defaultFillExtrusionBase)
    assertNotNull("defaultFillExtrusionBaseAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionBaseAsExpression)
    assertNotNull("defaultFillExtrusionBaseTransition should not be null", FillExtrusionLayer.defaultFillExtrusionBaseTransition)
    assertNotNull("defaultFillExtrusionColor should not be null", FillExtrusionLayer.defaultFillExtrusionColor)
    assertNotNull("defaultFillExtrusionColorAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionColorAsExpression)
    assertNotNull("defaultFillExtrusionColorAsColorInt should not be null", FillExtrusionLayer.defaultFillExtrusionColorAsColorInt)
    assertNotNull("defaultFillExtrusionColorTransition should not be null", FillExtrusionLayer.defaultFillExtrusionColorTransition)
    assertNotNull("defaultFillExtrusionCutoffFadeRange should not be null", FillExtrusionLayer.defaultFillExtrusionCutoffFadeRange)
    assertNotNull("defaultFillExtrusionCutoffFadeRangeAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionCutoffFadeRangeAsExpression)
    assertNotNull("defaultFillExtrusionFloodLightColor should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightColor)
    assertNotNull("defaultFillExtrusionFloodLightColorAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightColorAsExpression)
    assertNotNull("defaultFillExtrusionFloodLightColorAsColorInt should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightColorAsColorInt)
    assertNotNull("defaultFillExtrusionFloodLightColorTransition should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightColorTransition)
    assertNotNull("defaultFillExtrusionFloodLightGroundAttenuation should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuation)
    assertNotNull("defaultFillExtrusionFloodLightGroundAttenuationAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuationAsExpression)
    assertNotNull("defaultFillExtrusionFloodLightGroundAttenuationTransition should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuationTransition)
    assertNotNull("defaultFillExtrusionFloodLightGroundRadius should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadius)
    assertNotNull("defaultFillExtrusionFloodLightGroundRadiusAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadiusAsExpression)
    assertNotNull("defaultFillExtrusionFloodLightGroundRadiusTransition should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadiusTransition)
    assertNotNull("defaultFillExtrusionFloodLightIntensity should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightIntensity)
    assertNotNull("defaultFillExtrusionFloodLightIntensityAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightIntensityAsExpression)
    assertNotNull("defaultFillExtrusionFloodLightIntensityTransition should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightIntensityTransition)
    assertNotNull("defaultFillExtrusionFloodLightWallRadius should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadius)
    assertNotNull("defaultFillExtrusionFloodLightWallRadiusAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadiusAsExpression)
    assertNotNull("defaultFillExtrusionFloodLightWallRadiusTransition should not be null", FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadiusTransition)
    assertNotNull("defaultFillExtrusionHeight should not be null", FillExtrusionLayer.defaultFillExtrusionHeight)
    assertNotNull("defaultFillExtrusionHeightAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionHeightAsExpression)
    assertNotNull("defaultFillExtrusionHeightTransition should not be null", FillExtrusionLayer.defaultFillExtrusionHeightTransition)
    assertNotNull("defaultFillExtrusionOpacity should not be null", FillExtrusionLayer.defaultFillExtrusionOpacity)
    assertNotNull("defaultFillExtrusionOpacityAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionOpacityAsExpression)
    assertNotNull("defaultFillExtrusionOpacityTransition should not be null", FillExtrusionLayer.defaultFillExtrusionOpacityTransition)
    assertNotNull("defaultFillExtrusionPattern should not be null", FillExtrusionLayer.defaultFillExtrusionPattern)
    assertNotNull("defaultFillExtrusionPatternAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionPatternAsExpression)
    assertNotNull("defaultFillExtrusionRoundedRoof should not be null", FillExtrusionLayer.defaultFillExtrusionRoundedRoof)
    assertNotNull("defaultFillExtrusionRoundedRoofAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionRoundedRoofAsExpression)
    assertNotNull("defaultFillExtrusionTranslate should not be null", FillExtrusionLayer.defaultFillExtrusionTranslate)
    assertNotNull("defaultFillExtrusionTranslateAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateAsExpression)
    assertNotNull("defaultFillExtrusionTranslateTransition should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateTransition)
    assertNotNull("defaultFillExtrusionTranslateAnchor should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateAnchor)
    assertNotNull("defaultFillExtrusionTranslateAnchorAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateAnchorAsExpression)
    assertNotNull("defaultFillExtrusionVerticalGradient should not be null", FillExtrusionLayer.defaultFillExtrusionVerticalGradient)
    assertNotNull("defaultFillExtrusionVerticalGradientAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionVerticalGradientAsExpression)
    assertNotNull("defaultFillExtrusionVerticalScale should not be null", FillExtrusionLayer.defaultFillExtrusionVerticalScale)
    assertNotNull("defaultFillExtrusionVerticalScaleAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionVerticalScaleAsExpression)
    assertNotNull("defaultFillExtrusionVerticalScaleTransition should not be null", FillExtrusionLayer.defaultFillExtrusionVerticalScaleTransition)
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
    val fillExtrusionEdgeRadiusTestValue = 1.0
    val fillExtrusionAmbientOcclusionGroundAttenuationTestValue = 1.0
    val fillExtrusionAmbientOcclusionGroundRadiusTestValue = 1.0
    val fillExtrusionAmbientOcclusionIntensityTestValue = 1.0
    val fillExtrusionAmbientOcclusionRadiusTestValue = 1.0
    val fillExtrusionAmbientOcclusionWallRadiusTestValue = 1.0
    val fillExtrusionBaseTestValue = 1.0
    val fillExtrusionColorTestValue = "rgba(0, 0, 0, 1)"
    val fillExtrusionCutoffFadeRangeTestValue = 1.0
    val fillExtrusionFloodLightColorTestValue = "rgba(0, 0, 0, 1)"
    val fillExtrusionFloodLightGroundAttenuationTestValue = 1.0
    val fillExtrusionFloodLightGroundRadiusTestValue = 1.0
    val fillExtrusionFloodLightIntensityTestValue = 1.0
    val fillExtrusionFloodLightWallRadiusTestValue = 1.0
    val fillExtrusionHeightTestValue = 1.0
    val fillExtrusionOpacityTestValue = 1.0
    val fillExtrusionPatternTestValue = "abc"
    val fillExtrusionRoundedRoofTestValue = true
    val fillExtrusionTranslateTestValue = listOf(0.0, 1.0)
    val fillExtrusionTranslateAnchorTestValue = FillExtrusionTranslateAnchor.MAP
    val fillExtrusionVerticalGradientTestValue = true
    val fillExtrusionVerticalScaleTestValue = 1.0

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = fillExtrusionLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      fillExtrusionEdgeRadius(fillExtrusionEdgeRadiusTestValue)
      fillExtrusionAmbientOcclusionGroundAttenuation(fillExtrusionAmbientOcclusionGroundAttenuationTestValue)
      fillExtrusionAmbientOcclusionGroundRadius(fillExtrusionAmbientOcclusionGroundRadiusTestValue)
      fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensityTestValue)
      fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadiusTestValue)
      fillExtrusionAmbientOcclusionWallRadius(fillExtrusionAmbientOcclusionWallRadiusTestValue)
      fillExtrusionBase(fillExtrusionBaseTestValue)
      fillExtrusionColor(fillExtrusionColorTestValue)
      fillExtrusionCutoffFadeRange(fillExtrusionCutoffFadeRangeTestValue)
      fillExtrusionFloodLightColor(fillExtrusionFloodLightColorTestValue)
      fillExtrusionFloodLightGroundAttenuation(fillExtrusionFloodLightGroundAttenuationTestValue)
      fillExtrusionFloodLightGroundRadius(fillExtrusionFloodLightGroundRadiusTestValue)
      fillExtrusionFloodLightIntensity(fillExtrusionFloodLightIntensityTestValue)
      fillExtrusionFloodLightWallRadius(fillExtrusionFloodLightWallRadiusTestValue)
      fillExtrusionHeight(fillExtrusionHeightTestValue)
      fillExtrusionOpacity(fillExtrusionOpacityTestValue)
      fillExtrusionPattern(fillExtrusionPatternTestValue)
      fillExtrusionRoundedRoof(fillExtrusionRoundedRoofTestValue)
      fillExtrusionTranslate(fillExtrusionTranslateTestValue)
      fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchorTestValue)
      fillExtrusionVerticalGradient(fillExtrusionVerticalGradientTestValue)
      fillExtrusionVerticalScale(fillExtrusionVerticalScaleTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as FillExtrusionLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(fillExtrusionEdgeRadiusTestValue, cachedLayer.fillExtrusionEdgeRadius)
    assertEquals(fillExtrusionAmbientOcclusionGroundAttenuationTestValue, cachedLayer.fillExtrusionAmbientOcclusionGroundAttenuation)
    assertEquals(fillExtrusionAmbientOcclusionGroundRadiusTestValue, cachedLayer.fillExtrusionAmbientOcclusionGroundRadius)
    assertEquals(fillExtrusionAmbientOcclusionIntensityTestValue, cachedLayer.fillExtrusionAmbientOcclusionIntensity)
    assertEquals(fillExtrusionAmbientOcclusionRadiusTestValue, cachedLayer.fillExtrusionAmbientOcclusionRadius)
    assertEquals(fillExtrusionAmbientOcclusionWallRadiusTestValue, cachedLayer.fillExtrusionAmbientOcclusionWallRadius)
    assertEquals(fillExtrusionBaseTestValue, cachedLayer.fillExtrusionBase)
    assertEquals(fillExtrusionColorTestValue, cachedLayer.fillExtrusionColor)
    assertEquals(fillExtrusionCutoffFadeRangeTestValue, cachedLayer.fillExtrusionCutoffFadeRange)
    assertEquals(fillExtrusionFloodLightColorTestValue, cachedLayer.fillExtrusionFloodLightColor)
    assertEquals(fillExtrusionFloodLightGroundAttenuationTestValue, cachedLayer.fillExtrusionFloodLightGroundAttenuation)
    assertEquals(fillExtrusionFloodLightGroundRadiusTestValue, cachedLayer.fillExtrusionFloodLightGroundRadius)
    assertEquals(fillExtrusionFloodLightIntensityTestValue, cachedLayer.fillExtrusionFloodLightIntensity)
    assertEquals(fillExtrusionFloodLightWallRadiusTestValue, cachedLayer.fillExtrusionFloodLightWallRadius)
    assertEquals(fillExtrusionHeightTestValue, cachedLayer.fillExtrusionHeight)
    assertEquals(fillExtrusionOpacityTestValue, cachedLayer.fillExtrusionOpacity)
    assertEquals(fillExtrusionPatternTestValue, cachedLayer.fillExtrusionPattern)
    assertEquals(fillExtrusionRoundedRoofTestValue, cachedLayer.fillExtrusionRoundedRoof)
    assertEquals(fillExtrusionTranslateTestValue, cachedLayer.fillExtrusionTranslate)
    assertEquals(fillExtrusionTranslateAnchorTestValue, cachedLayer.fillExtrusionTranslateAnchor)
    assertEquals(fillExtrusionVerticalGradientTestValue, cachedLayer.fillExtrusionVerticalGradient)
    assertEquals(fillExtrusionVerticalScaleTestValue, cachedLayer.fillExtrusionVerticalScale)
  }
}

// End of generated file.