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
 * Basic smoke tests for ModelLayer
 */
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
    val expression = literal(listOf(0.0, 1.0, 2.0))
    val layer = modelLayer("id", "source") {
      modelRotation(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelRotationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.modelRotation!!)
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
    val expression = literal(listOf(0.0, 1.0, 2.0))
    val layer = modelLayer("id", "source") {
      modelScale(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelScaleAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.modelScale!!)
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
    val expression = literal(listOf(0.0, 1.0, 2.0))
    val layer = modelLayer("id", "source") {
      modelTranslation(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.modelTranslationAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0), layer.modelTranslation!!)
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

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", ModelLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", ModelLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", ModelLayer.defaultMaxZoom)
    assertNotNull("defaultModelOpacity should not be null", ModelLayer.defaultModelOpacity)
    assertNotNull("defaultModelOpacityAsExpression should not be null", ModelLayer.defaultModelOpacityAsExpression)
    assertNotNull("defaultModelOpacityTransition should not be null", ModelLayer.defaultModelOpacityTransition)
    assertNotNull("defaultModelRotation should not be null", ModelLayer.defaultModelRotation)
    assertNotNull("defaultModelRotationAsExpression should not be null", ModelLayer.defaultModelRotationAsExpression)
    assertNotNull("defaultModelRotationTransition should not be null", ModelLayer.defaultModelRotationTransition)
    assertNotNull("defaultModelScale should not be null", ModelLayer.defaultModelScale)
    assertNotNull("defaultModelScaleAsExpression should not be null", ModelLayer.defaultModelScaleAsExpression)
    assertNotNull("defaultModelTranslation should not be null", ModelLayer.defaultModelTranslation)
    assertNotNull("defaultModelTranslationAsExpression should not be null", ModelLayer.defaultModelTranslationAsExpression)
  }
}

// End of generated file.