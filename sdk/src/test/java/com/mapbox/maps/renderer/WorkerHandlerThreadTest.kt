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
    val action = mockk<() -> Unit>(relaxed = true)
    workerHandlerThread.start()
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    workerHandlerThread.post { action() }
    workerHandlerThread.post { action() }
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 2) { action.invoke() }
  }
}