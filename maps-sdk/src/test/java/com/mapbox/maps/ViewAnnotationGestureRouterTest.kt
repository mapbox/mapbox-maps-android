package com.mapbox.maps

import android.view.MotionEvent
import android.view.View
import com.mapbox.maps.viewannotation.ViewAnnotationManagerImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ViewAnnotationGestureRouterTest {

  private lateinit var mapView: MapView
  private lateinit var viewAnnotationManager: ViewAnnotationManagerImpl
  private lateinit var router: ViewAnnotationGestureRouter

  @Before
  fun setUp() {
    mapView = mockk(relaxed = true)
    viewAnnotationManager = mockk(relaxed = true)
    every { mapView.viewAnnotationManager } returns viewAnnotationManager
    every { mapView.isViewAnnotationManagerInitialized } returns true
    router = ViewAnnotationGestureRouter(mapView)
  }

  @Test
  fun `off annotation pans once past slop`() {
    every { viewAnnotationManager.viewAnnotationViewAt(any(), any()) } returns null
    router.onDown(downEvent())

    assertFalse(router.shouldInterceptMove(crossedSlop = false))
    assertTrue(router.shouldInterceptMove(crossedSlop = true))
  }

  @Test
  fun `over annotation yields the decision move then pans when child does not claim`() {
    every { viewAnnotationManager.viewAnnotationViewAt(any(), any()) } returns annotationView()
    router.onDown(downEvent())

    // Sub-slop moves fall through to the child.
    assertFalse(router.shouldInterceptMove(crossedSlop = false))
    // The decision move is handed to the child.
    assertFalse(router.shouldInterceptMove(crossedSlop = true))
    // The child did not claim, so the map pans.
    assertTrue(router.shouldInterceptMove(crossedSlop = true))
  }

  @Test
  fun `over annotation keeps yielding once the child claims`() {
    every { viewAnnotationManager.viewAnnotationViewAt(any(), any()) } returns annotationView()
    router.onDown(downEvent())

    assertFalse(router.shouldInterceptMove(crossedSlop = true))
    router.requestDisallowInterceptTouchEvent(true)
    assertFalse(router.shouldInterceptMove(crossedSlop = true))
    assertFalse(router.shouldInterceptMove(crossedSlop = true))
  }

  @Test
  fun `a claimed child wins even off annotation`() {
    router.requestDisallowInterceptTouchEvent(true)

    assertFalse(router.shouldInterceptMove(crossedSlop = true))
  }

  @Test
  fun `onDown resets state for the next gesture`() {
    every { viewAnnotationManager.viewAnnotationViewAt(any(), any()) } returns annotationView()
    router.onDown(downEvent())
    router.requestDisallowInterceptTouchEvent(true)
    router.shouldInterceptMove(crossedSlop = true)

    every { viewAnnotationManager.viewAnnotationViewAt(any(), any()) } returns null
    router.onDown(downEvent())

    assertFalse(router.shouldInterceptMove(crossedSlop = false))
    assertTrue(router.shouldInterceptMove(crossedSlop = true))
  }

  @Test
  fun `does not touch the manager when it is not initialized`() {
    every { mapView.isViewAnnotationManagerInitialized } returns false
    router.onDown(downEvent())

    assertTrue(router.shouldInterceptMove(crossedSlop = true))
    verify(exactly = 0) { mapView.viewAnnotationManager }
  }

  private fun downEvent(x: Float = 5f, y: Float = 5f) = mockk<MotionEvent>().also {
    every { it.x } returns x
    every { it.y } returns y
  }

  private fun annotationView() = mockk<View>()
}