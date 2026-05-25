package com.mapbox.maps.renderer

import com.mapbox.maps.logW
import com.mapbox.verifyNo
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
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
    renderHandlerThread = RenderHandlerThread(mapName = "")
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
  }

  @After
  fun cleanUp() {
    cleanupShadows()
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    renderHandlerThread.stop()
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
    val action = mockk<Runnable>(relaxed = true)
    renderHandlerThread.post(action)
    verifyNo { action.run() }
  }

  @Test
  fun postThreadStopped() {
    val actionOne = mockk<Runnable>(relaxed = true)
    val actionTwo = mockk<Runnable>(relaxed = true)
    val actionThree = mockk<Runnable>(relaxed = true)
    val actionFour = mockk<Runnable>(relaxed = true)
    renderHandlerThread.apply {
      start()
      val handlerLooperShadow = Shadows.shadowOf(renderHandlerThread.handlerThread.looper)
      handlerLooperShadow.pause()
      post(actionOne)
      postDelayed(actionTwo, 50)
      // Idle handler thread looper to run actionOne (0ms delay)
      // but not actionTwo (50ms delay)
      handlerLooperShadow.idleFor(Duration.ofMillis(40))
      stop()
      post(actionThree)
      postDelayed(
        actionFour,
        50
      )
    }
    verify { actionOne.run() }
    // action two skipped because of using HandlerThread#quit() and not quitSafely()
    verifyNo { actionTwo.run() }
    verifyNo { actionThree.run() }
    verifyNo { actionFour.run() }
  }

  @Test
  fun postThreadStarted() {
    val actionOne = mockk<Runnable>(relaxed = true)
    val actionTwo = mockk<Runnable>(relaxed = true)
    renderHandlerThread.start()
    val handlerLooperShadow = Shadows.shadowOf(renderHandlerThread.handlerThread.looper)
    renderHandlerThread.apply {
      post(actionOne)
      postDelayed(actionTwo, 50)
    }
    // Advance time by 50ms and idle to run both actions
    handlerLooperShadow.idleFor(Duration.ofMillis(50))
    verify { actionOne.run() }
    verify { actionTwo.run() }
  }

  @Test
  fun sendMessageDelayedDispatchesTypedMessageWithArgs() {
    val testWhat = 7
    var dispatchedArg1: Int? = null
    val callback = android.os.Handler.Callback { msg ->
      if (msg.what == testWhat) {
        dispatchedArg1 = msg.arg1
        true
      } else {
        false
      }
    }
    renderHandlerThread.start(callback)
    val handlerLooperShadow = Shadows.shadowOf(renderHandlerThread.handlerThread.looper)
    renderHandlerThread.sendMessageDelayed(what = testWhat, arg1 = 42, delayMillis = 0)
    handlerLooperShadow.idleFor(Duration.ofMillis(0))
    assertEquals(42, dispatchedArg1)
  }

  @Test
  fun sendMessageDelayedThreadNotStarted() {
    var dispatchedArg1: Int? = null
    // NOTE: deliberately not calling renderHandlerThread.start() — thread is not started.
    renderHandlerThread.sendMessageDelayed(what = 7, arg1 = 42, delayMillis = 0)
    assertEquals(null, dispatchedArg1)
  }
}