package com.mapbox.maps.extension.compose.annotation.internal

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.extension.compose.style.internal.StyleSlotNode
import com.mapbox.maps.extension.compose.style.internal.TestMapStyleNode
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [BaseAnnotationNode].
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
public class BaseAnnotationNodeTest {

  private val styleManager = mockk<MapboxStyleManager>(relaxed = true)
  private lateinit var scope: CoroutineScope

  private inner class TestAnnotationNode(
    private val ids: List<String>,
  ) : BaseAnnotationNode(styleManager, scope) {
    var cleanUpCount = 0
    override fun cleanUp() {
      cleanUpCount++
    }
    override fun getLayerIds(): List<String> = ids
  }

  @Before
  public fun setUp() {
    Dispatchers.setMain(UnconfinedTestDispatcher())
    scope = CoroutineScope(UnconfinedTestDispatcher())
  }

  @After
  public fun tearDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }

  @Test
  public fun `onAttached repositions and marks attached without touching slot for a non-slot parent`() {
    val node = TestAnnotationNode(listOf("layer-a"))

    node.onAttached(RootMapNode())

    assertTrue(node.isAttached)
    verify(exactly = 1) { styleManager.moveStyleLayer("layer-a", any()) }
    verify(exactly = 0) { styleManager.setStyleLayerProperty(any(), "slot", any()) }
  }

  @Test
  public fun `onAttached inside a slot sets the slot property on every layer`() {
    val styleNode = TestMapStyleNode(mockk<MapboxMap>(relaxed = true))
    val slotNode = StyleSlotNode("my-slot").apply { onAttached(styleNode.node) }
    val node = TestAnnotationNode(listOf("layer-a", "layer-b"))

    node.onAttached(slotNode)
    // held until the style is ready
    verify(exactly = 0) { styleManager.setStyleLayerProperty(any(), "slot", any()) }

    styleNode.emitStyleLoaded()

    verify(exactly = 1) { styleManager.setStyleLayerProperty("layer-a", "slot", Value("my-slot")) }
    verify(exactly = 1) { styleManager.setStyleLayerProperty("layer-b", "slot", Value("my-slot")) }
    assertTrue(node.isAttached)
  }

  @Test
  public fun `onMoved repositions again`() {
    val node = TestAnnotationNode(listOf("layer-a"))
    node.onAttached(RootMapNode())

    verify(exactly = 1) { styleManager.moveStyleLayer("layer-a", any()) }

    node.onMoved(RootMapNode())

    verify(exactly = 2) { styleManager.moveStyleLayer("layer-a", any()) }
  }

  @Test
  public fun `onRemoved cleans up`() {
    val node = TestAnnotationNode(listOf("layer-a"))

    node.onRemoved(RootMapNode())

    assertEquals(1, node.cleanUpCount)
  }

  @Test
  public fun `onClear cleans up`() {
    val node = TestAnnotationNode(listOf("layer-a"))

    node.onClear()

    assertEquals(1, node.cleanUpCount)
  }
}