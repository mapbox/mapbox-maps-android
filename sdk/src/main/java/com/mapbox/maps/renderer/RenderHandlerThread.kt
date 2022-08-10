package com.mapbox.maps.renderer

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process.THREAD_PRIORITY_DISPLAY
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.logW

internal class RenderHandlerThread {

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val handlerThread: HandlerThread =
    HandlerThread(HANDLE_THREAD_NAME, THREAD_PRIORITY_DISPLAY)

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var handler: Handler? = null

  internal val isRunning
    get() = handler != null && handlerThread.isAlive

  fun post(task: () -> Unit) {
    postDelayed(task, 0, EventType.DEFAULT)
  }

  fun postDelayed(task: () -> Unit, delayMillis: Long, eventType: EventType = EventType.DEFAULT) {
    handler?.let {
      val message = Message.obtain(it, task)
      message.obj = eventType
      it.sendMessageDelayed(message, delayMillis)
    } ?: logW(TAG, "Thread $HANDLE_THREAD_NAME was not started, ignoring event")
  }

  fun start(): Handler {
    handlerThread.start()
    return Handler(handlerThread.looper).also {
      handler = it
    }
  }

  fun stop() {
    // quit safely to guarantee all DESTROY_RENDERER messages
    // that may be in the queue will be executed
    handlerThread.quitSafely()
    handler = null
  }

  /**
   * Clears all messages except of [EventType.DESTROY_RENDERER] messages, since they clear
   * various resources allocated in core and we don't want to leak those resources.
   */
  fun clearDefaultMessages() {
    handler?.removeCallbacksAndMessages(EventType.DEFAULT)
  }

  companion object {
    private const val HANDLE_THREAD_NAME = "MapboxRenderThread"
    private const val TAG = "Mbgl-$HANDLE_THREAD_NAME"
  }
}