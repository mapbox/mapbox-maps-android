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
class RenderHandlerThreadTest {

  private lateinit var renderHandlerThread: RenderHandlerThread

  @Before
  fun setUp() {
    renderHandlerThread = RenderHandlerThread()
  }

  @Test
  fun startTest() {
    renderHandlerThread.start()
    assert(renderHandlerThread.handler != null)
    assert(renderHandlerThread.handlerThread.isAlive)
  }

  @Test
  fun stopTest() {
    renderHandlerThread.start()
    renderHandlerThread.stop()
    assert(renderHandlerThread.handler == null)
    assert(!renderHandlerThread.handlerThread.isAlive)
  }

  @Test
  fun clearMessageQueueAllTest() {
    renderHandlerThread.start()
    val handler = mockk<Handler>(relaxed = true)
    renderHandlerThread.handler = handler
    renderHandlerThread.clearMessageQueue()
    verify { renderHandlerThread.handler?.removeCallbacksAndMessages(null) }
  }

  @Test
  fun clearMessageQueueSDKTest() {
    renderHandlerThread.start()
    val handler = mockk<Handler>(relaxed = true)
    renderHandlerThread.handler = handler
    renderHandlerThread.clearMessageQueue(false)
    verify { renderHandlerThread.handler?.removeCallbacksAndMessages(EventType.SDK) }
  }

  @Test
  fun postThreadNotStarted() {
    val action = mockk<() -> Unit>(relaxed = true)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    renderHandlerThread.post { action() }
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { action.invoke() }
  }

  @Test
  fun postThreadStarted() {
    val actionOne = mockk<() -> Unit>(relaxed = true)
    val actionTwo = mockk<() -> Unit>(relaxed = true)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    renderHandlerThread.apply {
      start()
      post { actionOne() }
      post { actionTwo() }
    }
    Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(50))
    verify(exactly = 1) { actionOne.invoke() }
    verify(exactly = 1) { actionTwo.invoke() }
  }
}