package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.style.internal.MapStyleNode
import com.mapbox.maps.extension.compose.style.internal.StyleAwareNode
import com.mapbox.maps.extension.compose.style.internal.TestMapStyleNode
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [StyleLifecycleAwareNode]
 *
 * The contract:
 * - parent is *not* a [StyleAwareNode]  -> the style manager is immediately available, action runs now.
 * - parent *is* a [StyleAwareNode]      -> action is held until the parent's `styleDataLoaded` fires.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
public class StyleLifecycleAwareNodeTest {

  private val styleManager = mockk<MapboxStyleManager>(relaxed = true)
  private lateinit var scope: CoroutineScope

  private inner class RecordingNode : StyleLifecycleAwareNode(styleManager, scope) {
    val received = mutableListOf<MapboxStyleManager>()
    fun dispatch() = dispatchWhenReady { received.add(it) }
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
  public fun `dispatch runs immediately when parent is not style-aware`() {
    val node = RecordingNode()
    node.onAttached(RootMapNode())

    node.dispatch()

    assertEquals(listOf(styleManager), node.received)
  }

  @Test
  public fun `dispatch is deferred until style loads when parent is style-aware`() {
    val styleNode = TestMapStyleNode(mockk<MapboxMap>(relaxed = true))
    val styleAwareParent = object : StyleAwareNode() {
      override val mapStyleNode: MapStyleNode = styleNode.node
    }
    val node = RecordingNode()
    node.onAttached(styleAwareParent)

    // Style not loaded yet: the action must be held back.
    node.dispatch()
    assertEquals(emptyList<MapboxStyleManager>(), node.received)

    // Once the style loads, the queued action is released exactly once.
    styleNode.emitStyleLoaded()
    assertEquals(listOf(styleManager), node.received)
  }

  @Test
  public fun `dispatch after style already loaded runs immediately`() {
    val styleNode = TestMapStyleNode(mockk<MapboxMap>(relaxed = true))
    val styleAwareParent = object : StyleAwareNode() {
      override val mapStyleNode: MapStyleNode = styleNode.node
    }
    val node = RecordingNode()
    node.onAttached(styleAwareParent)
    styleNode.emitStyleLoaded()

    // Style is ready before we dispatch: no coroutine suspension, action runs synchronously.
    node.dispatch()

    assertEquals(listOf(styleManager), node.received)
  }
}