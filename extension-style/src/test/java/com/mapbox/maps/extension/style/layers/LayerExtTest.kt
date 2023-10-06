package com.mapbox.maps.extension.style.layers

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters
import com.mapbox.maps.Style
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.ShadowStyleManager
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

  private val style = mockk<Style>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val booleanExpected =
    mockk<Expected<String, Boolean>>(relaxUnitFun = true, relaxed = true)
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

  @Before
  fun prepareTest() {
    every { style.isStyleLayerPersistent(any()) } returns booleanExpected
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.addPersistentStyleCustomLayer(any(), any(), any()) } returns ExpectedFactory.createNone()
    every { style.addStyleLayer(any(), any()) } returns expected
    every { style.addStyleCustomLayer(any(), any(), any()) } returns expected
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
    verify(exactly = 1) { style.addPersistentStyleLayer(any(), any()) }
  }

  @Test
  fun testAddLayer() {
    val layer = symbolLayer("id", "source") {}
    style.addLayer(layer)
    verify(exactly = 1) { style.addStyleLayer(any(), any()) }
  }

  @Test
  fun testAddCustomLayer() {
    val layer = customLayer("id", emptyHost)
    style.addLayer(layer)
    verifySequence {
      style.addStyleCustomLayer("id", emptyHost, any())
      style.setStyleLayerProperties("id", any())
    }
  }

  @Test
  fun testAddPersistentLayer() {
    val layer = symbolLayer("id", "source") {}
    style.addPersistentLayer(layer)
    verify(exactly = 1) { style.addPersistentStyleLayer(any(), any()) }
  }

  @Test
  fun testAddPersistentCustomLayer() {
    val layer = customLayer("id", emptyHost)
    style.addPersistentLayer(layer)
    verifySequence {
      style.addPersistentStyleCustomLayer("id", emptyHost, any())
      style.setStyleLayerProperties("id", any())
    }
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