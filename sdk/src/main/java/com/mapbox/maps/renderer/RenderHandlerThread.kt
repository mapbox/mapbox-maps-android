package com.mapbox.maps.renderer

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process.THREAD_PRIORITY_DISPLAY
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.logW

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class RenderHandlerThread {

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val handlerThread: HandlerThread =
    HandlerThread(HANDLE_THREAD_NAME, THREAD_PRIORITY_DISPLAY)

  internal var handler: Handler? = null

  internal val isRunning
    get() = handler != null && handlerThread.isAlive

  fun post(task: () -> Unit) {
    postDelayed(task, 0)
  }

  fun postDelayed(task: () -> Unit, delayMillis: Long) {
    handler?.let {
      val message = Message.obtain(it, task)
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
    handlerThread.quit()
    handler = null
  }

  /**
   * Clears all messages.
   */
  fun clearRenderEventQueue() {
    handler?.removeCallbacksAndMessages(null)
  }

  companion object {
    private const val HANDLE_THREAD_NAME = "MapboxRenderThread"
    private const val TAG = "Mbgl-$HANDLE_THREAD_NAME"
  }
}