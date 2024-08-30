// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import android.graphics.Color
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
 * Basic smoke tests for ModelLayer
 */
@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
class ModelLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = modelLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = modelLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = modelLayer("id", "source") {
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
    val layer = modelLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun modelIdTest() {
    val testValue = "abc"
    val layer = modelLayer("id", "source") {
      modelId(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelId?.toString())
  }

  @Test
  @UiThreadTest
  fun modelIdAsExpressionTest() {
    val expression = toString {
      get {
        literal("string")
      }
    }
    val layer = modelLayer("id", "source") {
      modelId(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelIdAsExpression.toString())
    assertEquals(null, layer.modelId)
  }

  @Test
  @UiThreadTest
  fun modelAmbientOcclusionIntensityTest() {
    val testValue = 1.0
    val layer = modelLayer("id", "source") {
      modelAmbientOcclusionIntensity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.modelAmbientOcclusionIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelAmbientOcclusionIntensityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = modelLayer("id", "source") {
      modelAmbientOcclusionIntensity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.modelAmbientOcclusionIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelAmbientOcclusionIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelAmbientOcclusionIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelAmbientOcclusionIntensityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelAmbientOcclusionIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun modelAmbientOcclusionIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelAmbientOcclusionIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelAmbientOcclusionIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun modelCastShadowsTest() {
    val testValue = true
    val layer = modelLayer("id", "source") {
      modelCastShadows(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelCastShadows?.toString())
  }

  @Test
  @UiThreadTest
  fun modelCastShadowsAsExpressionTest() {
    val expression = literal(true)
    val layer = modelLayer("id", "source") {
      modelCastShadows(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelCastShadowsAsExpression.toString())
    assertEquals(true, layer.modelCastShadows!!)
  }

  @Test
  @UiThreadTest
  fun modelColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = modelLayer("id", "source") {
      modelColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelColor?.toString())
  }

  @Test
  @UiThreadTest
  fun modelColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = modelLayer("id", "source") {
      modelColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelColorAsExpression.toString())
    assertEquals(null, layer.modelColor)
  }

  @Test
  @UiThreadTest
  fun modelColorAsColorIntTest() {
    val layer = modelLayer("id", "source") {
      modelColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.modelColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun modelColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelColorTransition)
  }

  @Test
  @UiThreadTest
  fun modelColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelColorTransition)
  }

  @Test
  @UiThreadTest
  fun modelColorMixIntensityTest() {
    val testValue = 1.0
    val layer = modelLayer("id", "source") {
      modelColorMixIntensity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.modelColorMixIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelColorMixIntensityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = modelLayer("id", "source") {
      modelColorMixIntensity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelColorMixIntensityAsExpression.toString())
    assertEquals(null, layer.modelColorMixIntensity)
  }

  @Test
  @UiThreadTest
  fun modelColorMixIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelColorMixIntensityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelColorMixIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun modelColorMixIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelColorMixIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelColorMixIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun modelCutoffFadeRangeTest() {
    val testValue = 1.0
    val layer = modelLayer("id", "source") {
      modelCutoffFadeRange(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.modelCutoffFadeRange!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelCutoffFadeRangeAsExpressionTest() {
    val expression = literal(1.0)
    val layer = modelLayer("id", "source") {
      modelCutoffFadeRange(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.modelCutoffFadeRangeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelCutoffFadeRange!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelEmissiveStrengthTest() {
    val testValue = 1.0
    val layer = modelLayer("id", "source") {
      modelEmissiveStrength(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.modelEmissiveStrength!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelEmissiveStrengthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = modelLayer("id", "source") {
      modelEmissiveStrength(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelEmissiveStrengthAsExpression.toString())
    assertEquals(null, layer.modelEmissiveStrength)
  }

  @Test
  @UiThreadTest
  fun modelEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelEmissiveStrengthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun modelEmissiveStrengthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelEmissiveStrengthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun modelHeightBasedEmissiveStrengthMultiplierTest() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
    val layer = modelLayer("id", "source") {
      modelHeightBasedEmissiveStrengthMultiplier(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelHeightBasedEmissiveStrengthMultiplier?.toString())
  }

  @Test
  @UiThreadTest
  fun modelHeightBasedEmissiveStrengthMultiplierAsExpressionTest() {
    val expression = array {
      literal("number")
      literal(5)
      get {
        literal("array")
      }
    }
    val layer = modelLayer("id", "source") {
      modelHeightBasedEmissiveStrengthMultiplier(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelHeightBasedEmissiveStrengthMultiplierAsExpression.toString())
    assertEquals(null, layer.modelHeightBasedEmissiveStrengthMultiplier)
  }

  @Test
  @UiThreadTest
  fun modelHeightBasedEmissiveStrengthMultiplierTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelHeightBasedEmissiveStrengthMultiplierTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelHeightBasedEmissiveStrengthMultiplierTransition)
  }

  @Test
  @UiThreadTest
  fun modelHeightBasedEmissiveStrengthMultiplierTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelHeightBasedEmissiveStrengthMultiplierTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelHeightBasedEmissiveStrengthMultiplierTransition)
  }

  @Test
  @UiThreadTest
  fun modelOpacityTest() {
    val testValue = 1.0
    val layer = modelLayer("id", "source") {
      modelOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.modelOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = modelLayer("id", "source") {
      modelOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.modelOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.modelOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun modelOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun modelReceiveShadowsTest() {
    val testValue = true
    val layer = modelLayer("id", "source") {
      modelReceiveShadows(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelReceiveShadows?.toString())
  }

  @Test
  @UiThreadTest
  fun modelReceiveShadowsAsExpressionTest() {
    val expression = literal(true)
    val layer = modelLayer("id", "source") {
      modelReceiveShadows(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelReceiveShadowsAsExpression.toString())
    assertEquals(true, layer.modelReceiveShadows!!)
  }

  @Test
  @UiThreadTest
  fun modelRotationTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    val layer = modelLayer("id", "source") {
      modelRotation(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelRotation?.toString())
  }

  @Test
  @UiThreadTest
  fun modelRotationAsExpressionTest() {
    val expression = array {
      literal("number")
      literal(3)
      get {
        literal("array")
      }
    }
    val layer = modelLayer("id", "source") {
      modelRotation(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelRotationAsExpression.toString())
    assertEquals(null, layer.modelRotation)
  }

  @Test
  @UiThreadTest
  fun modelRotationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelRotationTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelRotationTransition)
  }

  @Test
  @UiThreadTest
  fun modelRotationTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelRotationTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelRotationTransition)
  }

  @Test
  @UiThreadTest
  fun modelRoughnessTest() {
    val testValue = 1.0
    val layer = modelLayer("id", "source") {
      modelRoughness(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.modelRoughness!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun modelRoughnessAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = modelLayer("id", "source") {
      modelRoughness(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelRoughnessAsExpression.toString())
    assertEquals(null, layer.modelRoughness)
  }

  @Test
  @UiThreadTest
  fun modelRoughnessTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelRoughnessTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelRoughnessTransition)
  }

  @Test
  @UiThreadTest
  fun modelRoughnessTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelRoughnessTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelRoughnessTransition)
  }

  @Test
  @UiThreadTest
  fun modelScaleTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    val layer = modelLayer("id", "source") {
      modelScale(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelScale?.toString())
  }

  @Test
  @UiThreadTest
  fun modelScaleAsExpressionTest() {
    val expression = array {
      literal("number")
      literal(3)
      get {
        literal("array")
      }
    }
    val layer = modelLayer("id", "source") {
      modelScale(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelScaleAsExpression.toString())
    assertEquals(null, layer.modelScale)
  }

  @Test
  @UiThreadTest
  fun modelScaleTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelScaleTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelScaleTransition)
  }

  @Test
  @UiThreadTest
  fun modelScaleTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelScaleTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelScaleTransition)
  }

  @Test
  @UiThreadTest
  fun modelScaleModeTest() {
    val layer = modelLayer("id", "source") {
      modelScaleMode(ModelScaleMode.MAP)
    }
    setupLayer(layer)
    assertEquals(ModelScaleMode.MAP, layer.modelScaleMode)
  }

  @Test
  @UiThreadTest
  fun modelScaleModeAsExpressionTest() {
    val expression = literal("map")
    val layer = modelLayer("id", "source") {
      modelScaleMode(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelScaleModeAsExpression.toString())
    assertEquals(ModelScaleMode.MAP, layer.modelScaleMode!!)
  }

  @Test
  @UiThreadTest
  fun modelTranslationTest() {
    val testValue = listOf(0.0, 1.0, 2.0)
    val layer = modelLayer("id", "source") {
      modelTranslation(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.modelTranslation?.toString())
  }

  @Test
  @UiThreadTest
  fun modelTranslationAsExpressionTest() {
    val expression = array {
      literal("number")
      literal(3)
      get {
        literal("array")
      }
    }
    val layer = modelLayer("id", "source") {
      modelTranslation(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelTranslationAsExpression.toString())
    assertEquals(null, layer.modelTranslation)
  }

  @Test
  @UiThreadTest
  fun modelTranslationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelTranslationTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelTranslationTransition)
  }

  @Test
  @UiThreadTest
  fun modelTranslationTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = modelLayer("id", "source") {
      modelTranslationTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.modelTranslationTransition)
  }

  @Test
  @UiThreadTest
  fun modelTypeTest() {
    val layer = modelLayer("id", "source") {
      modelType(ModelType.COMMON_3D)
    }
    setupLayer(layer)
    assertEquals(ModelType.COMMON_3D, layer.modelType)
  }

  @Test
  @UiThreadTest
  fun modelTypeAsExpressionTest() {
    val expression = literal("common-3d")
    val layer = modelLayer("id", "source") {
      modelType(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelTypeAsExpression.toString())
    assertEquals(ModelType.COMMON_3D, layer.modelType!!)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = modelLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = modelLayer("id", "source") {
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
    assertNotNull("defaultVisibility should not be null", ModelLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", ModelLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", ModelLayer.defaultMaxZoom)
    assertNotNull("defaultModelId should not be null", ModelLayer.defaultModelId)
    assertNotNull("defaultModelIdAsExpression should not be null", ModelLayer.defaultModelIdAsExpression)
    assertNotNull("defaultModelAmbientOcclusionIntensity should not be null", ModelLayer.defaultModelAmbientOcclusionIntensity)
    assertNotNull("defaultModelAmbientOcclusionIntensityAsExpression should not be null", ModelLayer.defaultModelAmbientOcclusionIntensityAsExpression)
    assertNotNull("defaultModelAmbientOcclusionIntensityTransition should not be null", ModelLayer.defaultModelAmbientOcclusionIntensityTransition)
    assertNotNull("defaultModelCastShadows should not be null", ModelLayer.defaultModelCastShadows)
    assertNotNull("defaultModelCastShadowsAsExpression should not be null", ModelLayer.defaultModelCastShadowsAsExpression)
    assertNotNull("defaultModelColor should not be null", ModelLayer.defaultModelColor)
    assertNotNull("defaultModelColorAsExpression should not be null", ModelLayer.defaultModelColorAsExpression)
    assertNotNull("defaultModelColorAsColorInt should not be null", ModelLayer.defaultModelColorAsColorInt)
    assertNotNull("defaultModelColorTransition should not be null", ModelLayer.defaultModelColorTransition)
    assertNotNull("defaultModelColorMixIntensity should not be null", ModelLayer.defaultModelColorMixIntensity)
    assertNotNull("defaultModelColorMixIntensityAsExpression should not be null", ModelLayer.defaultModelColorMixIntensityAsExpression)
    assertNotNull("defaultModelColorMixIntensityTransition should not be null", ModelLayer.defaultModelColorMixIntensityTransition)
    assertNotNull("defaultModelCutoffFadeRange should not be null", ModelLayer.defaultModelCutoffFadeRange)
    assertNotNull("defaultModelCutoffFadeRangeAsExpression should not be null", ModelLayer.defaultModelCutoffFadeRangeAsExpression)
    assertNotNull("defaultModelEmissiveStrength should not be null", ModelLayer.defaultModelEmissiveStrength)
    assertNotNull("defaultModelEmissiveStrengthAsExpression should not be null", ModelLayer.defaultModelEmissiveStrengthAsExpression)
    assertNotNull("defaultModelEmissiveStrengthTransition should not be null", ModelLayer.defaultModelEmissiveStrengthTransition)
    assertNotNull("defaultModelHeightBasedEmissiveStrengthMultiplier should not be null", ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplier)
    assertNotNull("defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression should not be null", ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression)
    assertNotNull("defaultModelHeightBasedEmissiveStrengthMultiplierTransition should not be null", ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierTransition)
    assertNotNull("defaultModelOpacity should not be null", ModelLayer.defaultModelOpacity)
    assertNotNull("defaultModelOpacityAsExpression should not be null", ModelLayer.defaultModelOpacityAsExpression)
    assertNotNull("defaultModelOpacityTransition should not be null", ModelLayer.defaultModelOpacityTransition)
    assertNotNull("defaultModelReceiveShadows should not be null", ModelLayer.defaultModelReceiveShadows)
    assertNotNull("defaultModelReceiveShadowsAsExpression should not be null", ModelLayer.defaultModelReceiveShadowsAsExpression)
    assertNotNull("defaultModelRotation should not be null", ModelLayer.defaultModelRotation)
    assertNotNull("defaultModelRotationAsExpression should not be null", ModelLayer.defaultModelRotationAsExpression)
    assertNotNull("defaultModelRotationTransition should not be null", ModelLayer.defaultModelRotationTransition)
    assertNotNull("defaultModelRoughness should not be null", ModelLayer.defaultModelRoughness)
    assertNotNull("defaultModelRoughnessAsExpression should not be null", ModelLayer.defaultModelRoughnessAsExpression)
    assertNotNull("defaultModelRoughnessTransition should not be null", ModelLayer.defaultModelRoughnessTransition)
    assertNotNull("defaultModelScale should not be null", ModelLayer.defaultModelScale)
    assertNotNull("defaultModelScaleAsExpression should not be null", ModelLayer.defaultModelScaleAsExpression)
    assertNotNull("defaultModelScaleTransition should not be null", ModelLayer.defaultModelScaleTransition)
    assertNotNull("defaultModelScaleMode should not be null", ModelLayer.defaultModelScaleMode)
    assertNotNull("defaultModelScaleModeAsExpression should not be null", ModelLayer.defaultModelScaleModeAsExpression)
    assertNotNull("defaultModelTranslation should not be null", ModelLayer.defaultModelTranslation)
    assertNotNull("defaultModelTranslationAsExpression should not be null", ModelLayer.defaultModelTranslationAsExpression)
    assertNotNull("defaultModelTranslationTransition should not be null", ModelLayer.defaultModelTranslationTransition)
    assertNotNull("defaultModelType should not be null", ModelLayer.defaultModelType)
    assertNotNull("defaultModelTypeAsExpression should not be null", ModelLayer.defaultModelTypeAsExpression)
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
    val modelIdTestValue = "abc"
    val modelAmbientOcclusionIntensityTestValue = 1.0
    val modelCastShadowsTestValue = true
    val modelColorTestValue = "rgba(0, 0, 0, 1)"
    val modelColorMixIntensityTestValue = 1.0
    val modelCutoffFadeRangeTestValue = 1.0
    val modelEmissiveStrengthTestValue = 1.0
    val modelHeightBasedEmissiveStrengthMultiplierTestValue = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
    val modelOpacityTestValue = 1.0
    val modelReceiveShadowsTestValue = true
    val modelRotationTestValue = listOf(0.0, 1.0, 2.0)
    val modelRoughnessTestValue = 1.0
    val modelScaleTestValue = listOf(0.0, 1.0, 2.0)
    val modelScaleModeTestValue = ModelScaleMode.MAP
    val modelTranslationTestValue = listOf(0.0, 1.0, 2.0)
    val modelTypeTestValue = ModelType.COMMON_3D

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = modelLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      modelId(modelIdTestValue)
      modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensityTestValue)
      modelCastShadows(modelCastShadowsTestValue)
      modelColor(modelColorTestValue)
      modelColorMixIntensity(modelColorMixIntensityTestValue)
      modelCutoffFadeRange(modelCutoffFadeRangeTestValue)
      modelEmissiveStrength(modelEmissiveStrengthTestValue)
      modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplierTestValue)
      modelOpacity(modelOpacityTestValue)
      modelReceiveShadows(modelReceiveShadowsTestValue)
      modelRotation(modelRotationTestValue)
      modelRoughness(modelRoughnessTestValue)
      modelScale(modelScaleTestValue)
      modelScaleMode(modelScaleModeTestValue)
      modelTranslation(modelTranslationTestValue)
      modelType(modelTypeTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as ModelLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(modelIdTestValue, cachedLayer.modelId)
    assertEquals(modelAmbientOcclusionIntensityTestValue, cachedLayer.modelAmbientOcclusionIntensity)
    assertEquals(modelCastShadowsTestValue, cachedLayer.modelCastShadows)
    assertEquals(modelColorTestValue, cachedLayer.modelColor)
    assertEquals(modelColorMixIntensityTestValue, cachedLayer.modelColorMixIntensity)
    assertEquals(modelCutoffFadeRangeTestValue, cachedLayer.modelCutoffFadeRange)
    assertEquals(modelEmissiveStrengthTestValue, cachedLayer.modelEmissiveStrength)
    assertEquals(modelHeightBasedEmissiveStrengthMultiplierTestValue, cachedLayer.modelHeightBasedEmissiveStrengthMultiplier)
    assertEquals(modelOpacityTestValue, cachedLayer.modelOpacity)
    assertEquals(modelReceiveShadowsTestValue, cachedLayer.modelReceiveShadows)
    assertEquals(modelRotationTestValue, cachedLayer.modelRotation)
    assertEquals(modelRoughnessTestValue, cachedLayer.modelRoughness)
    assertEquals(modelScaleTestValue, cachedLayer.modelScale)
    assertEquals(modelScaleModeTestValue, cachedLayer.modelScaleMode)
    assertEquals(modelTranslationTestValue, cachedLayer.modelTranslation)
    assertEquals(modelTypeTestValue, cachedLayer.modelType)
  }
}

// End of generated file.