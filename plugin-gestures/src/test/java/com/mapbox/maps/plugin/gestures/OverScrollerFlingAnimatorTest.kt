package com.mapbox.maps.plugin.gestures

import android.content.Context
import android.view.Choreographer
import android.widget.OverScroller
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OverScrollerFlingAnimatorTest {

  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk(relaxUnitFun = true)
  private val choreographer: Choreographer = mockk(relaxUnitFun = true)
  private val context: Context = mockk(relaxed = true)

  private lateinit var animator: OverScrollerFlingAnimator

  @Before
  fun setUp() {
    mockkStatic(Choreographer::class)
    every { Choreographer.getInstance() } returns choreographer

    mockkConstructor(OverScroller::class)
    every { anyConstructed<OverScroller>().fling(any(), any(), any(), any(), any(), any(), any(), any()) } just Runs
    every { anyConstructed<OverScroller>().forceFinished(any()) } just Runs

    animator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate)
  }

  @After
  fun tearDown() {
    unmockkAll()
    cleanupShadows()
  }

  @Test
  fun `fling sets isRunning and invokes onAnimationStart`() {
    var startCalled = false
    animator.onAnimationStart = { startCalled = true }

    animator.fling(1000, 1000, ScreenCoordinate(100.0, 100.0))

    assertTrue(animator.isRunning)
    assertTrue(startCalled)
    verify { choreographer.postFrameCallback(animator.frameCallback) }
  }

  @Test
  fun `forceStop sets isRunning false and invokes onAnimationEnd`() {
    animator.fling(1000, 1000, ScreenCoordinate(100.0, 100.0))

    var endCalled = false
    animator.onAnimationEnd = { endCalled = true }

    animator.forceStop()

    assertFalse(animator.isRunning)
    assertTrue(endCalled)
    verify { anyConstructed<OverScroller>().forceFinished(true) }
    verify { choreographer.removeFrameCallback(animator.frameCallback) }
  }

  @Test
  fun `forceStop when not running is a no-op`() {
    var endCalled = false
    animator.onAnimationEnd = { endCalled = true }

    animator.forceStop()

    assertFalse(animator.isRunning)
    assertFalse(endCalled)
  }

  @Test
  fun `fling calls forceStop on previous fling before starting new one`() {
    animator.fling(1000, 1000, ScreenCoordinate(100.0, 100.0))

    var endCalled = false
    animator.onAnimationEnd = { endCalled = true }

    animator.fling(500, 500, ScreenCoordinate(200.0, 200.0))

    assertTrue(endCalled)
    assertTrue(animator.isRunning)
  }

  @Test
  fun `doFrame with no scroll offset finishes animation`() {
    animator.fling(1000, 1000, ScreenCoordinate(100.0, 100.0))

    var endCalled = false
    animator.onAnimationEnd = { endCalled = true }

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns false

    animator.frameCallback.doFrame(0)

    assertFalse(animator.isRunning)
    assertTrue(endCalled)
  }

  @Test
  fun `doFrame applies camera update via cameraForDrag and setCamera`() {
    val origin = ScreenCoordinate(100.0, 200.0)
    animator.fling(1000, 1000, origin)

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 10
    every { anyConstructed<OverScroller>().currY } returns 20

    val cameraOptions = mockk<CameraOptions>()
    val expectedTo = ScreenCoordinate(110.0, 220.0)
    every { mapCameraManagerDelegate.cameraForDrag(origin, expectedTo) } returns cameraOptions

    animator.frameCallback.doFrame(0)

    verify { mapCameraManagerDelegate.cameraForDrag(origin, expectedTo) }
    verify { mapCameraManagerDelegate.setCamera(cameraOptions) }
    verify(atLeast = 2) { choreographer.postFrameCallback(animator.frameCallback) }
  }

  @Test
  fun `limitHorizontal zeroes X delta`() {
    val origin = ScreenCoordinate(100.0, 200.0)
    animator.limitHorizontal = true
    animator.fling(1000, 1000, origin)

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 10
    every { anyConstructed<OverScroller>().currY } returns 20

    val cameraOptions = mockk<CameraOptions>()
    val expectedTo = ScreenCoordinate(100.0, 220.0)
    every { mapCameraManagerDelegate.cameraForDrag(origin, expectedTo) } returns cameraOptions

    animator.frameCallback.doFrame(0)

    verify { mapCameraManagerDelegate.cameraForDrag(origin, expectedTo) }
    verify { mapCameraManagerDelegate.setCamera(cameraOptions) }
  }

  @Test
  fun `limitVertical zeroes Y delta`() {
    val origin = ScreenCoordinate(100.0, 200.0)
    animator.limitVertical = true
    animator.fling(1000, 1000, origin)

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 10
    every { anyConstructed<OverScroller>().currY } returns 20

    val cameraOptions = mockk<CameraOptions>()
    val expectedTo = ScreenCoordinate(110.0, 200.0)
    every { mapCameraManagerDelegate.cameraForDrag(origin, expectedTo) } returns cameraOptions

    animator.frameCallback.doFrame(0)

    verify { mapCameraManagerDelegate.cameraForDrag(origin, expectedTo) }
    verify { mapCameraManagerDelegate.setCamera(cameraOptions) }
  }
}