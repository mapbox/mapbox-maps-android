package com.mapbox.maps.renderer

import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.FpsManager.Companion.VSYNC_COUNT_TILL_IDLE
import io.mockk.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowChoreographer
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FpsManagerTest {

  private lateinit var fpsManager: FpsManager

  @Before
  fun setUp() {
    fpsManager = FpsManager(Handler(Looper.getMainLooper()), mapName = "")
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    // simulate all the tests on 60 Hz screen, meaning we have 16.6 ms time to render a frame
    ShadowChoreographer.setPostFrameCallbackDelay(CHOREOGRAPHER_POST_FRAME_CALLBACK_DELAY_MS)
    fpsManager.setScreenRefreshRate(SCREEN_FPS)
  }

  @Test
  fun userRefreshRateNotSetTest() {
    pauseHandler()
    idleHandler(vsyncCount = 10) {
      assert(fpsManager.preRender(it))
      fpsManager.postRender()
    }
  }

  @Test
  fun userRefreshRate24Test() {
    fpsManager.setUserRefreshRate(24)
    pauseHandler()
    val frameRenderedPattern = mutableListOf<Boolean>()
    idleHandler(vsyncCount = 10) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      arrayOf(
        false, false, true, false, true,
        false, false, true, false, true
      ),
      frameRenderedPattern.toTypedArray()
    )
  }

  @Test
  fun userRefreshRate7Test() {
    fpsManager.setUserRefreshRate(7)
    pauseHandler()
    val vsyncCount = 18
    val frameRenderedPattern = mutableListOf<Boolean>()
    idleHandler(vsyncCount = vsyncCount) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      Array(vsyncCount) { it == 8 || it == 17 },
      frameRenderedPattern.toTypedArray()
    )
  }

  @Test
  fun userRefreshRate3for2secondsTest() {
    fpsManager.setUserRefreshRate(3)
    pauseHandler()
    val vsyncCount = 120
    val frameRenderedPattern = mutableListOf<Boolean>()
    idleHandler(vsyncCount = vsyncCount) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      // 6 frames should be rendered in total during 2 seconds with 3 FPS
      Array(vsyncCount) { it == 19 || it == 39 || it == 59 || it == 79 || it == 99 || it == 119 },
      frameRenderedPattern.toTypedArray()
    )
  }

  @Test
  fun userRefreshRate1Test() {
    fpsManager.setUserRefreshRate(1)
    pauseHandler()
    val vsyncCount = 120
    val frameRenderedPattern = mutableListOf<Boolean>()
    idleHandler(vsyncCount = vsyncCount) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      Array(vsyncCount) { it == 59 || it == 119 },
      frameRenderedPattern.toTypedArray()
    )
  }

  @Test
  fun userRefreshRateMaxIntTest() {
    fpsManager.setUserRefreshRate(Int.MAX_VALUE)
    pauseHandler()
    val vsyncCount = 1000
    val frameRenderedPattern = mutableListOf<Boolean>()
    idleHandler(vsyncCount = vsyncCount) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      Array(vsyncCount) { true },
      frameRenderedPattern.toTypedArray()
    )
  }

  @Test
  fun userRefreshRateSameAsScreenRefreshRateTest() {
    fpsManager.setUserRefreshRate(SCREEN_FPS)
    pauseHandler()
    val frameRenderedPattern = mutableListOf<Boolean>()
    idleHandler(vsyncCount = 5 * SCREEN_FPS) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      Array(5 * SCREEN_FPS) { true },
      frameRenderedPattern.toTypedArray()
    )
  }

  @Test
  fun `user refresh rate changes mid render with listener`() {
    val fpsValueArray = mutableListOf<Double>()
    val fpsChangedListener = OnFpsChangedListener {
      fpsValueArray.add(it)
    }
    fpsManager.fpsChangedListener = fpsChangedListener
    pauseHandler()
    val frameRenderedPattern = mutableListOf<Boolean>()
    val vsyncCount = 90

    // First set user refresh rate to be the same as screen refresh rate
    fpsManager.setUserRefreshRate(SCREEN_FPS)
    idleHandler(vsyncCount = vsyncCount) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      Array(vsyncCount) { true },
      frameRenderedPattern.toTypedArray()
    )
    Assert.assertEquals(1, fpsValueArray.size)
    Assert.assertEquals(SCREEN_FPS.toDouble(), fpsValueArray.last(), EPS)

    frameRenderedPattern.clear()

    // Now change the user refresh ratio to be half of the screen refresh rate
    fpsManager.setUserRefreshRate(SCREEN_FPS / 2)
    // That triggers a new listener with the FPS value for the remaining 30 frames out of the 90 above
    Assert.assertEquals(2, fpsValueArray.size)
    Assert.assertEquals(SCREEN_FPS.toDouble(), fpsValueArray.last(), EPS)

    // Now render vsyncCount frames with the new user refresh rate
    idleHandler(vsyncCount = vsyncCount) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      // We expect every other frame to be rendered (user SCREEN_FPS/2 FPS)
      Array(vsyncCount) { it % 2 != 0 },
      frameRenderedPattern.toTypedArray()
    )
    Assert.assertEquals(3, fpsValueArray.size)
    Assert.assertEquals(SCREEN_FPS / 2.0, fpsValueArray.last(), EPS)

    frameRenderedPattern.clear()

    // Reset user refresh rate to be the same as screen one
    fpsManager.setUserRefreshRate(SCREEN_FPS)
    // That triggers a new listener with the FPS value for the remaining 30 frames out of the 90 above
    Assert.assertEquals(4, fpsValueArray.size)
    Assert.assertEquals(SCREEN_FPS / 2.0, fpsValueArray.last(), EPS)

    idleHandler(vsyncCount = vsyncCount) {
      frameRenderedPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      Array(vsyncCount) { true },
      frameRenderedPattern.toTypedArray()
    )
    Assert.assertEquals(5, fpsValueArray.size)
    Assert.assertEquals(SCREEN_FPS.toDouble(), fpsValueArray.last(), EPS)

    fpsManager.onSurfaceDestroyed()
    // One last FPS value for the remaining 30 frames out of the 90 above
    Assert.assertEquals(6, fpsValueArray.size)
    Assert.assertEquals(SCREEN_FPS.toDouble(), fpsValueArray.last(), EPS)
  }

  @Test
  fun userFpsListenerTest() {
    val fpsValueArray = mutableListOf<Double>()
    val fpsChangedListener = OnFpsChangedListener {
      fpsValueArray.add(it)
    }
    fpsManager.fpsChangedListener = fpsChangedListener
    pauseHandler()
    // render a bit more frames than screen frequency
    idleHandler(vsyncCount = SCREEN_FPS + 10) {
      assert(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    assert(fpsValueArray.size == 1)
    Assert.assertEquals(SCREEN_FPS.toDouble(), fpsValueArray[0], EPS)
  }

  @Test
  fun mapBecomeIdleDuringRenderingNoFpsListenerTest() {
    mapIdleTest()
  }

  @Test
  fun mapBecomeIdleDuringRenderingWithFpsListenerTest() {
    var fpsValue = 0.0
    val fpsChangedListener = OnFpsChangedListener {
      fpsValue = it
    }
    fpsManager.fpsChangedListener = fpsChangedListener
    mapIdleTest()
    Assert.assertEquals(40.0, fpsValue, EPS)
  }

  private fun mapIdleTest() {
    // pattern is [0, 1, 1, 1, 1]
    fpsManager.setUserRefreshRate(48)
    pauseHandler()
    val frameRenderedBeforePattern = mutableListOf<Boolean>()
    // first we render 3 frames
    idleHandler(vsyncCount = 3) {
      frameRenderedBeforePattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    // then we simulate map being IDLE for several vsync ticks - counters should be reset, fps should be reported
    idleHandler(vsyncCount = VSYNC_COUNT_TILL_IDLE + 1) {}
    val frameRenderedAfterPattern = mutableListOf<Boolean>()
    // and render 5 more frames
    idleHandler(vsyncCount = 5) {
      frameRenderedAfterPattern.add(fpsManager.preRender(it))
      fpsManager.postRender()
    }
    Assert.assertArrayEquals(
      arrayOf(
        false, true, true,
        false, true, true, true, true
      ),
      frameRenderedBeforePattern.plus(frameRenderedAfterPattern).toTypedArray()
    )
  }

  private fun pauseHandler() = Shadows.shadowOf(Looper.getMainLooper()).pause()

  private fun idleHandler(vsyncCount: Int, actionOnVsync: ((Long) -> Unit)) {
    val choreographer = Choreographer.getInstance()
    val looper = Shadows.shadowOf(Looper.getMainLooper())
    repeat(vsyncCount) {
      choreographer.postFrameCallback(actionOnVsync)
      looper.idleFor(CHOREOGRAPHER_POST_FRAME_CALLBACK_DELAY_MS.toLong(), TimeUnit.MILLISECONDS)
    }
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  private companion object {
    private const val EPS = 0.0001
    private const val SCREEN_FPS = 60
    private const val CHOREOGRAPHER_POST_FRAME_CALLBACK_DELAY_MS = 16
  }
}