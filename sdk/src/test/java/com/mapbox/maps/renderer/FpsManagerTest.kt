package com.mapbox.maps.renderer

import android.os.Looper
import android.view.Choreographer
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.FpsManager.Companion.IDLE_TIMEOUT_MS
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
    fpsManager = FpsManager()
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
    val frameTotalNumber = 10
    var frameRenderedCount = 0
    for (i in 0 until frameTotalNumber) {
      Choreographer.getInstance().postFrameCallback {
        assert(fpsManager.preRender(it))
        frameRenderedCount++
      }
    }
    idleHandler(vsyncCount = frameTotalNumber)
    assert(frameRenderedCount == frameTotalNumber)
  }

  @Test
  fun userRefreshRate24Test() {
    fpsManager.setUserRefreshRate(24)
    pauseHandler()
    val frameTotalNumber = 10
    val frameRenderedPattern = mutableListOf<Boolean>()
    for (i in 0 until frameTotalNumber) {
      Choreographer.getInstance().postFrameCallback {
        frameRenderedPattern.add(fpsManager.preRender(it))
      }
    }
    idleHandler(vsyncCount = frameTotalNumber)
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
    val frameTotalNumber = 18
    val frameRenderedPattern = mutableListOf<Boolean>()
    for (i in 0 until frameTotalNumber) {
      Choreographer.getInstance().postFrameCallback {
        frameRenderedPattern.add(fpsManager.preRender(it))
      }
    }
    idleHandler(vsyncCount = frameTotalNumber)
    Assert.assertArrayEquals(
      arrayOf(
        false, false, false, false, false, false, false, false, true,
        false, false, false, false, false, false, false, false, true,
      ),
      frameRenderedPattern.toTypedArray()
    )
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
    val frameTotalNumber = SCREEN_FPS + 10
    for (i in 0 until frameTotalNumber) {
      Choreographer.getInstance().postFrameCallback {
        assert(fpsManager.preRender(it))
        fpsManager.postRender()
      }
    }
    idleHandler(vsyncCount = frameTotalNumber)
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
    // first we render 3 frames
    val frameTotalNumberBefore = 3
    val frameRenderedBeforePattern = mutableListOf<Boolean>()
    for (i in 0 until frameTotalNumberBefore) {
      Choreographer.getInstance().postFrameCallback {
        frameRenderedBeforePattern.add(fpsManager.preRender(it))
        fpsManager.postRender()
      }
    }
    idleHandler(vsyncCount = frameTotalNumberBefore)
    // then we simulate map being IDLE for several vsync ticks - counters should be reset, fps should be reported
    val frameTotalNumberWhenIdle = (IDLE_TIMEOUT_MS / CHOREOGRAPHER_POST_FRAME_CALLBACK_DELAY_MS).toInt() + 1
    idleHandler(vsyncCount = frameTotalNumberWhenIdle)
    // and render 5 more frames
    val frameTotalNumberAfter = 5
    val frameRenderedAfterPattern = mutableListOf<Boolean>()
    for (i in 0 until frameTotalNumberAfter) {
      Choreographer.getInstance().postFrameCallback {
        frameRenderedAfterPattern.add(fpsManager.preRender(it))
        fpsManager.postRender()
      }
    }
    idleHandler(vsyncCount = frameTotalNumberAfter)
    Assert.assertArrayEquals(
      arrayOf(
        false, true, true,
        false, true, true, true, true
      ),
      frameRenderedBeforePattern.plus(frameRenderedAfterPattern).toTypedArray()
    )
  }

  private fun pauseHandler() = Shadows.shadowOf(Looper.getMainLooper()).pause()

  private fun idleHandler(vsyncCount: Int) = Shadows.shadowOf(Looper.getMainLooper())
    .idleFor(CHOREOGRAPHER_POST_FRAME_CALLBACK_DELAY_MS.toLong() * vsyncCount, TimeUnit.MILLISECONDS)

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