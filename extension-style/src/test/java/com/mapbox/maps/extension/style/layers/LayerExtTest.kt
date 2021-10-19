package com.mapbox.maps.extension.style.layers

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class LayerExtTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val booleanExpected =
    mockk<Expected<String, Boolean>>(relaxUnitFun = true, relaxed = true)

  @Before
  fun prepareTest() {
    every { style.isStyleLayerPersistent(any()) } returns booleanExpected
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.addStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperties(any(), any()) } returns expected
    every { expected.error } returns null

    // For default property getters
    mockkStatic(StyleManager::class)
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun testBindPersistentlyTo() {
    val layer = symbolLayer("id", "source") {}
    layer.bindPersistentlyTo(style)
    verify { style.addPersistentStyleLayer(any(), any()) }
  }

  @Test
  fun testAddPersistentLayer() {
    val layer = symbolLayer("id", "source") {}
    style.addPersistentLayer(layer)
    verify { style.addPersistentStyleLayer(any(), any()) }
  }

  @Test
  fun testPersistentGet() {
    every { style.isStyleLayerPersistent(any()) } returns booleanExpected
    every { booleanExpected.value } returns true
    val layer = symbolLayer("id", "source") {}
    layer.bindTo(style)
    assertTrue(layer.isPersistent()!!)
    verify { style.isStyleLayerPersistent("id") }
  }
}