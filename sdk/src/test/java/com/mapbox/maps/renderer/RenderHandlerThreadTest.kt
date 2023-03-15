package com.mapbox.maps.renderer

import android.os.Looper
import com.mapbox.maps.logW
import com.mapbox.verifyNo
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class RenderHandlerThreadTest {

  private lateinit var renderHandlerThread: RenderHandlerThread

  @Before
  fun setUp() {
    renderHandlerThread = RenderHandlerThread()
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun startTest() {
    renderHandlerThread.start()
    assert(renderHandlerThread.handler != null)
    assert(renderHandlerThread.handlerThread.isAlive)
    assert(renderHandlerThread.isRunning)
  }

  @Test
  fun startStartTest() {
    renderHandlerThread.start()
    assertThrows(IllegalThreadStateException::class.java) {
      renderHandlerThread.start()
    }
  }

  @Test
  fun startStopTest() {
    renderHandlerThread.start()
    renderHandlerThread.stop()
    assert(renderHandlerThread.handler == null)
  }

  @Test
  fun startStopStartTest() {
    renderHandlerThread.start()
    renderHandlerThread.stop()
    assertThrows(IllegalThreadStateException::class.java) {
      renderHandlerThread.start()
    }
  }

  @Test
  fun isRunningTest() {
    with(renderHandlerThread) {
      assert(!isRunning)
      start()
      assert(isRunning)
      stop()
      assert(!isRunning)
    }
  }

  @Test
  fun clearRenderEventQueueTest() {
    renderHandlerThread.start()
    renderHandlerThread.handler = mockk(relaxed = true)
    renderHandlerThread.clearRenderEventQueue()
    verify { renderHandlerThread.handler?.removeCallbacksAndMessages(null) }
  }

  @Test
  fun postThreadNotStarted() {
    val action = mockk<() -> Unit>(relaxed = true)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    renderHandlerThread.post { action() }
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verifyNo { action.invoke() }
  }

  @Test
  fun postThreadStopped() {
    val actionOne = mockk<() -> Unit>(relaxed = true)
    val actionTwo = mockk<() -> Unit>(relaxed = true)
    val actionThree = mockk<() -> Unit>(relaxed = true)
    val actionFour = mockk<() -> Unit>(relaxed = true)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    renderHandlerThread.apply {
      start()
      post(actionOne)
      postDelayed(
        actionTwo,
        50
      )
      Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(50))
      stop()
      post(actionThree)
      postDelayed(
        actionFour,
        50
      )
    }
    Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(50))
    verify { actionOne.invoke() }
    // action two skipped because of using HandlerThread#quit() and not quitSafely()
    verifyNo { actionTwo.invoke() }
    verifyNo { actionThree.invoke() }
    verifyNo { actionFour.invoke() }
  }

  @Test
  fun postThreadStarted() {
    val actionOne = mockk<() -> Unit>(relaxed = true)
    val actionTwo = mockk<() -> Unit>(relaxed = true)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    renderHandlerThread.apply {
      start()
      post(actionOne)
      postDelayed(actionTwo, 50)
    }
    Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(50))
    verify { actionOne.invoke() }
    verify { actionTwo.invoke() }
  }
}