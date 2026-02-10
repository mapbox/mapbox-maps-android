// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class ModelSourceTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val expectedDelta = mockk<Expected<String, Byte>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { expected.error } returns null
    every { expectedDelta.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT

    mockkStyle(style)

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleSourcePropertyDefaultValue(any(), any()) } returns styleProperty
  }

  private fun mockkStyle(style: MapboxStyleManager) {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
  }

  @Test
  fun getTypeTest() {
    val testSource = modelSource("testId") {}
    assertEquals("model", testSource.getType())
  }

  @Test
  fun urlSet() {
    val testSource = modelSource("testId") {
      url("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("url=abc"))
  }

  @Test
  fun urlSetAfterBind() {
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.url("abc")

    verify { style.setStyleSourceProperty("testId", "url", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun urlGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.url?.toString())
    verify { style.getStyleSourceProperty("testId", "url") }
  }

  @Test
  fun maxzoomSet() {
    val testSource = modelSource("testId") {
      maxzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("maxzoom=1"))
  }

  @Test
  fun maxzoomSetAfterBind() {
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.maxzoom(1L)

    verify { style.setStyleSourceProperty("testId", "maxzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun maxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "maxzoom") }
  }

  @Test
  fun minzoomSet() {
    val testSource = modelSource("testId") {
      minzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("minzoom=1"))
  }

  @Test
  fun minzoomSetAfterBind() {
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.minzoom(1L)

    verify { style.setStyleSourceProperty("testId", "minzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun minzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.minzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "minzoom") }
  }

  @Test
  fun tilesSet() {
    val testSource = modelSource("testId") {
      tiles(listOf("a", "b", "c"))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tiles=[a, b, c]"))
  }

  @Test
  fun tilesSetAfterBind() {
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.tiles(listOf("a", "b", "c"))

    verify { style.setStyleSourceProperty("testId", "tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[a, b, c]")
  }

  @Test
  fun tilesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf("a", "b", "c").toString(), testSource.tiles?.toString())
    verify { style.getStyleSourceProperty("testId", "tiles") }
  }

  @Test
  fun modelsSet() {
    val testSource = modelSource("testId") {
      models(listOf(ModelSourceModel.Builder("model").uri("something://somewhere").orientation(listOf(0.0, 0.0, 0.0)).position(listOf(0.0, 0.0)).build()))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    val modelSlot = slot<Value>()
    verify { style.setStyleSourceProperty("testId", "models", capture(modelSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{type=model}"))
    assertEquals(modelSlot.captured, TypeUtils.wrapToValue(hashMapOf("model" to ModelSourceModel.Builder("model").uri("something://somewhere").orientation(listOf(0.0, 0.0, 0.0)).position(listOf(0.0, 0.0)).build())))
  }

  @Test
  fun modelsSetAfterBind() {
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.models(listOf(ModelSourceModel.Builder("model").uri("something://somewhere").orientation(listOf(0.0, 0.0, 0.0)).position(listOf(0.0, 0.0)).build()))

    verify { style.setStyleSourceProperty("testId", "models", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{model={orientation=[0.0, 0.0, 0.0], position=[0.0, 0.0], uri=something://somewhere}}")
  }

  @Test
  fun modelsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(hashMapOf("model" to ModelSourceModel.Builder("model").uri("something://somewhere").orientation(listOf(0.0, 0.0, 0.0)).position(listOf(0.0, 0.0)).build()))
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)

    assertEquals("model", testSource.models?.first()?.id)
    verify { style.getStyleSourceProperty("testId", "models") }
  }
  // Default source property getters tests

  @Test
  fun defaultMaxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), ModelSource.defaultMaxzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("model", "maxzoom") }
  }

  @Test
  fun defaultMinzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), ModelSource.defaultMinzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("model", "minzoom") }
  }
}

// End of generated file.