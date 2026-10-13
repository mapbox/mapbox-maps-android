// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [CircleAnnotationManagerNode].
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
public class CircleAnnotationManagerNodeTest {

  private val styleManager = mockk<MapboxStyleManager>(relaxed = true)
  private val annotationManager = mockk<CircleAnnotationManager>(relaxed = true)
  private lateinit var scope: CoroutineScope

  @Before
  public fun setUp() {
    scope = CoroutineScope(UnconfinedTestDispatcher())
  }

  @After
  public fun tearDown() {
    unmockkAll()
  }

  private fun newNode() = CircleAnnotationManagerNode(styleManager, annotationManager, scope)

  @Test
  public fun `assigning cluster items creates them through the manager`() {
    val options = listOf(mockk<CircleAnnotationOptions>())
    val created = mutableListOf(mockk<CircleAnnotation>())
    every { annotationManager.create(options) } returns created
    val node = newNode()

    node.annotationClusterItems = options

    verify(exactly = 1) { annotationManager.create(options) }
    assertEquals(created, node.currentAnnotations)
  }

  @Test
  public fun `reassigning cluster items deletes the previous batch before creating the new one`() {
    val options1 = listOf(mockk<CircleAnnotationOptions>())
    val created1 = mutableListOf(mockk<CircleAnnotation>())
    val options2 = listOf(mockk<CircleAnnotationOptions>(), mockk<CircleAnnotationOptions>())
    val created2 = mutableListOf(mockk<CircleAnnotation>(), mockk<CircleAnnotation>())
    every { annotationManager.create(options1) } returns created1
    every { annotationManager.create(options2) } returns created2
    val node = newNode()

    node.annotationClusterItems = options1
    node.annotationClusterItems = options2

    verifyOrder {
      annotationManager.create(options1)
      annotationManager.delete(any<List<CircleAnnotation>>())
      annotationManager.create(options2)
    }
    assertEquals(created2, node.currentAnnotations)
  }

  @Test
  public fun `cleanUp on clear deletes all annotations and destroys the manager`() {
    val options = listOf(mockk<CircleAnnotationOptions>())
    every { annotationManager.create(options) } returns mutableListOf(mockk())
    val node = newNode()
    node.annotationClusterItems = options

    node.onClear()

    verify(exactly = 1) { annotationManager.deleteAll() }
    verify(exactly = 1) { annotationManager.onDestroy() }
    assertTrue(node.currentAnnotations.isEmpty())
  }

  @Test
  public fun `getLayerIds delegates to the manager's associated layers`() {
    every { annotationManager.associatedLayers } returns mutableListOf("annotation-layer")
    val node = newNode()

    assertEquals(listOf("annotation-layer"), node.getLayerIds())
  }

  @Test
  public fun `onRemoved cleans up`() {
    val node = newNode()

    node.onRemoved(RootMapNode())

    verify(exactly = 1) { annotationManager.deleteAll() }
    verify(exactly = 1) { annotationManager.onDestroy() }
  }
}