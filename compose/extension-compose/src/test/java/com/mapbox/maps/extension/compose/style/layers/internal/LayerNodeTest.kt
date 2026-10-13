package com.mapbox.maps.extension.compose.style.layers.internal

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.RootMapNode
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [LayerNode].
 */
@RunWith(RobolectricTestRunner::class)
public class LayerNodeTest {

  private val map = mockk<MapboxMap>(relaxed = true)
  private lateinit var scope: CoroutineScope

  @Before
  public fun setUp() {
    scope = CoroutineScope(UnconfinedTestDispatcher())
    every { map.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { map.setStyleLayerProperty(any(), any(), any()) } returns ExpectedFactory.createNone()
    every { map.removeStyleLayer(any()) } returns ExpectedFactory.createNone()
  }

  @After
  public fun tearDown() {
    unmockkAll()
  }

  private fun newNode(layerId: String = "layer-1") = LayerNode(
    map = map,
    layerType = "test",
    layerId = layerId,
    sourceState = null,
    coroutineScope = scope,
  )

  @Test
  public fun `onAttached adds a persistent layer and marks the node attached`() {
    val node = newNode()

    node.onAttached(RootMapNode())

    verify(exactly = 1) { map.addPersistentStyleLayer(any(), any()) }
    assertTrue(node.isAttached)
  }

  @Test
  public fun `setProperty before attach is cached and does not touch the style`() {
    val node = newNode()

    node.setProperty("test-radius", Value(10.0))

    assertFalse(node.isAttached)
    verify(exactly = 0) { map.setStyleLayerProperty(any(), any(), any()) }
  }

  @Test
  public fun `setProperty after attach is applied to the style`() {
    val node = newNode(layerId = "layer-1")
    node.onAttached(RootMapNode())

    node.setProperty("test-radius", Value(10.0))

    verify(exactly = 1) { map.setStyleLayerProperty("layer-1", "test-radius", Value(10.0)) }
  }

  @Test
  public fun `updateLayerId removes the old layer and re-adds under the new id`() {
    val node = newNode(layerId = "old-layer")
    node.onAttached(RootMapNode())

    node.updateLayerId("new-layer")

    verify(exactly = 1) { map.removeStyleLayer("old-layer") }
    // one add on attach, one on re-add
    verify(exactly = 2) { map.addPersistentStyleLayer(any(), any()) }
  }

  @Test
  public fun `onRemoved removes the layer from the style`() {
    val node = newNode(layerId = "layer-1")
    node.onAttached(RootMapNode())

    node.onRemoved(RootMapNode())

    verify(exactly = 1) { map.removeStyleLayer("layer-1") }
  }
}