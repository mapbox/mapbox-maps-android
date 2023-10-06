package com.mapbox.maps.testapp.style.layers

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.CustomLayer
import com.mapbox.maps.extension.style.layers.customLayer
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for CustomLayer
 */
@RunWith(AndroidJUnit4::class)
class CustomLayerTest : BaseStyleTest() {

  private val emptyHost = object : CustomLayerHost {
    override fun initialize() {
    }

    override fun render(parameters: CustomLayerRenderParameters) {
    }

    override fun contextLost() {
    }

    override fun deinitialize() {
    }
  }

  // Property getters and setters

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val testValue = 10.0
    val layer = customLayer("id", emptyHost) {
      maxZoom(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.maxZoom?.toString())
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val testValue = 12.0
    val layer = customLayer("id", emptyHost) {
      minZoom(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.minZoom?.toString())
  }

  @Test
  @UiThreadTest
  fun slotTest() {
    val testValue = "bottom"
    val layer = customLayer("id", emptyHost) {
      slot(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.slot)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = customLayer("id", emptyHost) {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = customLayer("id", emptyHost) {
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
    assertNotNull("defaultVisibility should not be null", CustomLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", CustomLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", CustomLayer.defaultMaxZoom)
  }

  @Test
  @UiThreadTest
  fun getLayerTest() {
    val slotTestValue = "middle"
    val visibilityTestValue = Visibility.NONE
    val maxZoomTestValue = 15.0
    val minZoomTestValue = 10.0

    val layer = customLayer("id", emptyHost) {
      slot(slotTestValue)
      visibility(visibilityTestValue)
      maxZoom(maxZoomTestValue)
      minZoom(minZoomTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as CustomLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals(slotTestValue, cachedLayer.slot)
    assertEquals(visibilityTestValue, cachedLayer.visibility)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
  }
}