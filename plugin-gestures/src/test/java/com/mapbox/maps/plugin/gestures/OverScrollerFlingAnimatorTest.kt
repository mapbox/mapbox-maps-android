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
  fun `maxFps null processes every frame`() {
    val testAnimator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate, maxFpsProvider = { null })
    testAnimator.fling(1000, 1000, ScreenCoordinate(100.0, 200.0))

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 10
    every { anyConstructed<OverScroller>().currY } returns 20
    every { mapCameraManagerDelegate.cameraForDrag(any(), any()) } returns mockk()

    testAnimator.frameCallback.doFrame(0L)
    testAnimator.frameCallback.doFrame(8_333_333L)
    testAnimator.frameCallback.doFrame(16_666_666L)

    // computeScrollOffset is called on every processed frame
    verify(exactly = 3) { anyConstructed<OverScroller>().computeScrollOffset() }
  }

  @Test
  fun `maxFps 30 on 120Hz stream skips intermediate frames`() {
    val testAnimator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate, maxFpsProvider = { 30 })
    testAnimator.fling(1000, 1000, ScreenCoordinate(100.0, 200.0))

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 10
    every { anyConstructed<OverScroller>().currY } returns 20
    every { mapCameraManagerDelegate.cameraForDrag(any(), any()) } returns mockk()

    // 30 fps target period = 1_000_000_000/30 = 33_333_333 ns
    // Feed 5 vsyncs at ~8.33 ms apart; frame 4 at 34ms clears the period boundary
    testAnimator.frameCallback.doFrame(0L) // processed (lastProcessed = -1)
    testAnimator.frameCallback.doFrame(8_333_333L) // skipped (8.33ms elapsed < 33.33ms)
    testAnimator.frameCallback.doFrame(16_666_666L) // skipped
    testAnimator.frameCallback.doFrame(24_999_999L) // skipped
    testAnimator.frameCallback.doFrame(34_000_000L) // processed (34ms >= 33.33ms period)

    verify(exactly = 2) { anyConstructed<OverScroller>().computeScrollOffset() }
    // with a cap set, every doFrame call - processed or skipped - reposts via a delayed
    // callback timed to the next expected frame, not an immediate next-vsync repost.
    verify(exactly = 5) { choreographer.postFrameCallbackDelayed(testAnimator.frameCallback, any()) }
  }

  @Test
  fun `skipped frame delays the next callback by the remaining time to the expected frame`() {
    val testAnimator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate, maxFpsProvider = { 30 })
    testAnimator.fling(1000, 1000, ScreenCoordinate(0.0, 0.0))

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 5
    every { anyConstructed<OverScroller>().currY } returns 5
    every { mapCameraManagerDelegate.cameraForDrag(any(), any()) } returns mockk()

    // 30fps period ~= 33ms; processing the first frame schedules the next one a full period out.
    testAnimator.frameCallback.doFrame(0L)
    verify { choreographer.postFrameCallbackDelayed(testAnimator.frameCallback, 33L) }

    // 8ms elapsed since the processed frame -> skip, delay only the ~25ms remaining, not the full period.
    testAnimator.frameCallback.doFrame(8_000_000L)
    verify { choreographer.postFrameCallbackDelayed(testAnimator.frameCallback, 25L) }
  }

  @Test
  fun `skipped frames do not call cameraForDrag`() {
    val testAnimator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate, maxFpsProvider = { 30 })
    testAnimator.fling(1000, 1000, ScreenCoordinate(0.0, 0.0))

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 5
    every { anyConstructed<OverScroller>().currY } returns 5
    every { mapCameraManagerDelegate.cameraForDrag(any(), any()) } returns mockk()

    testAnimator.frameCallback.doFrame(0L) // processed
    testAnimator.frameCallback.doFrame(8_000_000L) // skipped (8ms < 33.33ms period)

    // cameraForDrag only called on the one processed frame
    verify(exactly = 1) { mapCameraManagerDelegate.cameraForDrag(any(), any()) }
    verify(exactly = 1) { anyConstructed<OverScroller>().computeScrollOffset() }
  }

  @Test
  fun `fling resets pacing so first frame always processes`() {
    val testAnimator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate, maxFpsProvider = { 30 })
    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 5
    every { anyConstructed<OverScroller>().currY } returns 5
    every { mapCameraManagerDelegate.cameraForDrag(any(), any()) } returns mockk()

    testAnimator.fling(1000, 1000, ScreenCoordinate(0.0, 0.0))
    testAnimator.frameCallback.doFrame(0L) // processed
    testAnimator.frameCallback.doFrame(8_000_000L) // skipped (< 33ms)

    // second fling resets lastProcessedFrameTimeNanos to -1
    testAnimator.fling(500, 500, ScreenCoordinate(0.0, 0.0))
    testAnimator.frameCallback.doFrame(10_000_000L) // processed (first frame of new fling)

    // frame 0 of fling1 + frame 0 of fling2 = 2 processed frames
    verify(exactly = 2) { anyConstructed<OverScroller>().computeScrollOffset() }
  }

  @Test
  fun `runtime maxFps raise applies on next frame without stall`() {
    var currentFps = 30
    val testAnimator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate, maxFpsProvider = { currentFps })
    testAnimator.fling(1000, 1000, ScreenCoordinate(0.0, 0.0))

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 5
    every { anyConstructed<OverScroller>().currY } returns 5
    every { mapCameraManagerDelegate.cameraForDrag(any(), any()) } returns mockk()

    testAnimator.frameCallback.doFrame(0L) // processed (lastProcessed=-1)
    testAnimator.frameCallback.doFrame(8_000_000L) // skipped under 30fps (8ms < 33.33ms)

    // raise to 120fps → period ≈ 8.33ms; 9ms since last processed frame qualifies
    currentFps = 120
    testAnimator.frameCallback.doFrame(9_000_000L) // processed

    verify(exactly = 2) { anyConstructed<OverScroller>().computeScrollOffset() }
  }

  @Test
  fun `runtime maxFps cleared mid-fling reverts to unthrottled`() {
    var currentFps: Int? = 30
    val testAnimator = OverScrollerFlingAnimator(context, mapCameraManagerDelegate, maxFpsProvider = { currentFps })
    testAnimator.fling(1000, 1000, ScreenCoordinate(0.0, 0.0))

    every { anyConstructed<OverScroller>().computeScrollOffset() } returns true
    every { anyConstructed<OverScroller>().currX } returns 5
    every { anyConstructed<OverScroller>().currY } returns 5
    every { mapCameraManagerDelegate.cameraForDrag(any(), any()) } returns mockk()

    testAnimator.frameCallback.doFrame(0L) // processed
    testAnimator.frameCallback.doFrame(8_000_000L) // skipped (< 33ms period)

    currentFps = null // cleared → no throttle branch
    testAnimator.frameCallback.doFrame(16_000_000L) // processed
    testAnimator.frameCallback.doFrame(24_000_000L) // processed

    verify(exactly = 3) { anyConstructed<OverScroller>().computeScrollOffset() }
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