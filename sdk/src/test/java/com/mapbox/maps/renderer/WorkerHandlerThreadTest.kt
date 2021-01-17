package com.mapbox.maps.renderer

import android.os.Handler
import android.os.Looper
import com.mapbox.common.ShadowLogger
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
@LooperMode(LooperMode.Mode.PAUSED)
class WorkerHandlerThreadTest {

  private lateinit var workerHandlerThread: WorkerHandlerThread

  @Before
  fun setUp() {
    workerHandlerThread = WorkerHandlerThread()
  }

  @Test
  fun startTest() {
    workerHandlerThread.start()
    assert(workerHandlerThread.handler != null)
    assert(workerHandlerThread.handlerThread.isAlive)
  }

  @Test
  fun stopTest() {
    workerHandlerThread.start()
    workerHandlerThread.stop()
    assert(workerHandlerThread.handler == null)
    assert(!workerHandlerThread.handlerThread.isAlive)
  }

  @Test
  fun clearMessageQueueTest() {
    workerHandlerThread.start()
    val handler = mockk<Handler>(relaxed = true)
    workerHandlerThread.handler = handler
    workerHandlerThread.clearMessageQueue()
    verify { workerHandlerThread.handler?.removeCallbacksAndMessages(null) }
  }

  @Test
  fun postThreadNotStarted() {
    val action = mockk<() -> Unit>(relaxed = true)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    workerHandlerThread.post { action() }
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { action.invoke() }
  }

  @Test
  fun postThreadStarted() {
    val actionOne = mockk<() -> Unit>(relaxed = true)
    val actionTwo = mockk<() -> Unit>(relaxed = true)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    workerHandlerThread.apply {
      start()
      post { actionOne() }
      post { actionTwo() }
    }
    Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(10))
    verify(exactly = 1) { actionOne.invoke() }
    verify(exactly = 1) { actionTwo.invoke() }
  }
}