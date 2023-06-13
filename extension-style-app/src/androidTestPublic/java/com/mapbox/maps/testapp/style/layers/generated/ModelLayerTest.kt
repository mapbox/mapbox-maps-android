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

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", ModelLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", ModelLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", ModelLayer.defaultMaxZoom)
    assertNotNull("defaultModelId should not be null", ModelLayer.defaultModelId)
    assertNotNull("defaultModelIdAsExpression should not be null", ModelLayer.defaultModelIdAsExpression)
    assertNotNull("defaultModelRotation should not be null", ModelLayer.defaultModelRotation)
    assertNotNull("defaultModelRotationAsExpression should not be null", ModelLayer.defaultModelRotationAsExpression)
    assertNotNull("defaultModelRotationTransition should not be null", ModelLayer.defaultModelRotationTransition)
    assertNotNull("defaultModelScale should not be null", ModelLayer.defaultModelScale)
    assertNotNull("defaultModelScaleAsExpression should not be null", ModelLayer.defaultModelScaleAsExpression)
    assertNotNull("defaultModelScaleTransition should not be null", ModelLayer.defaultModelScaleTransition)
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
    val modelRotationTestValue = listOf(0.0, 1.0, 2.0)
    val modelScaleTestValue = listOf(0.0, 1.0, 2.0)
    val modelTypeTestValue = ModelType.COMMON_3D

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = modelLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      modelId(modelIdTestValue)
      modelRotation(modelRotationTestValue)
      modelScale(modelScaleTestValue)
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
    assertEquals(modelRotationTestValue, cachedLayer.modelRotation)
    assertEquals(modelScaleTestValue, cachedLayer.modelScale)
    assertEquals(modelTypeTestValue, cachedLayer.modelType)
  }
}

// End of generated file.