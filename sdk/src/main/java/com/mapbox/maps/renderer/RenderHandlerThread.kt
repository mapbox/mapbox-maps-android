package com.mapbox.maps.renderer

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process.THREAD_PRIORITY_DISPLAY
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.logW

internal class RenderHandlerThread {

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal lateinit var handlerThread: HandlerThread
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var handler: Handler? = null

  internal val started
    get() = handlerThread.isAlive

  fun post(task: () -> Unit) {
    postDelayed(task, 0, EventType.SDK)
  }

  fun postDelayed(task: () -> Unit, delayMillis: Long, eventType: EventType = EventType.SDK) {
    handler?.let {
      val message = Message.obtain(it, task)
      message.obj = eventType
      it.sendMessageDelayed(message, delayMillis)
    } ?: logW(TAG, "Thread $HANDLE_THREAD_NAME was not started, ignoring event")
  }

  fun postImmediate(task: () -> Unit) {
    handler?.let {
      val message = Message.obtain(it, task)
      it.sendMessageAtFrontOfQueue(message)
    } ?: logW(TAG, "Thread $HANDLE_THREAD_NAME was not started, ignoring event")
  }

  fun start() {
    handlerThread = HandlerThread(HANDLE_THREAD_NAME, THREAD_PRIORITY_DISPLAY).apply {
      start()
      handler = Handler(this.looper)
    }
  }

  fun stop() {
    handlerThread.quit()
    try {
      handlerThread.join()
    } catch (e: InterruptedException) {
    } finally {
      handler = null
    }
  }

  fun clearMessageQueue(clearAll: Boolean = true) {
    if (clearAll) {
      handler?.removeCallbacksAndMessages(null)
    } else {
      handler?.removeCallbacksAndMessages(EventType.SDK)
    }
  }

  companion object {
    private const val HANDLE_THREAD_NAME = "MapboxRenderThread"
    private const val TAG = "Mbgl-$HANDLE_THREAD_NAME"
  }
}