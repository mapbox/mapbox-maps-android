// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [PolylineAnnotationNode].
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
public class PolylineAnnotationNodeTest {

  private val styleManager = mockk<MapboxStyleManager>(relaxed = true)
  private val annotationManager = mockk<PolylineAnnotationManager>(relaxed = true)
  private val annotation = mockk<PolylineAnnotation>(relaxed = true)
  private lateinit var scope: CoroutineScope

  @Before
  public fun setUp() {
    scope = CoroutineScope(UnconfinedTestDispatcher())
  }

  @After
  public fun tearDown() {
    unmockkAll()
  }

  private fun newNode() =
    PolylineAnnotationNode(styleManager, annotationManager, annotation, scope)

  @Test
  public fun `cleanUp on remove deletes the annotation and destroys the manager`() {
    val node = newNode()

    node.onRemoved(RootMapNode())

    verify(exactly = 1) { annotationManager.delete(annotation) }
    verify(exactly = 1) { annotationManager.onDestroy() }
  }

  @Test
  public fun `cleanUp on clear deletes the annotation and destroys the manager`() {
    val node = newNode()

    node.onClear()

    verify(exactly = 1) { annotationManager.delete(annotation) }
    verify(exactly = 1) { annotationManager.onDestroy() }
  }

  @Test
  public fun `getLayerIds delegates to the manager's associated layers`() {
    every { annotationManager.associatedLayers } returns mutableListOf("annotation-layer")
    val node = newNode()

    assertEquals(listOf("annotation-layer"), node.getLayerIds())
  }
}