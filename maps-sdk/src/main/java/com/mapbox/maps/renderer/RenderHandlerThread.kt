package com.mapbox.maps.renderer

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process.THREAD_PRIORITY_DISPLAY
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.logW

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class RenderHandlerThread(
  mapName: String,
) {

  private val handlerThreadName = if (mapName.isNotBlank())
    "MbxRender$mapName"
  else
    "MapboxRenderThread"

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val handlerThread: HandlerThread =
    HandlerThread(handlerThreadName, THREAD_PRIORITY_DISPLAY)

  @Volatile
  internal var handler: Handler? = null

  internal val isRunning
    get() = handler != null && handlerThread.isAlive

  @Suppress("PrivatePropertyName")
  private val TAG = "Mbgl-$handlerThreadName" + if (mapName.isNotBlank()) "\\$mapName" else ""

  fun post(task: Runnable) {
    postDelayed(task, 0)
  }

  fun postDelayed(task: Runnable, delayMillis: Long) {
    handler?.let {
      val message = Message.obtain(it, task)
      it.sendMessageDelayed(message, delayMillis)
    } ?: logW(TAG, "Thread $handlerThreadName was not started, ignoring event")
  }

  fun start(callback: Handler.Callback? = null): Handler {
    handlerThread.start()
    return Handler(handlerThread.looper, callback).also {
      handler = it
    }
  }

  fun sendMessageDelayed(what: Int, arg1: Int, delayMillis: Long) {
    handler?.let {
      val msg = it.obtainMessage(what, arg1, 0)
      it.sendMessageDelayed(msg, delayMillis)
    } ?: logW(TAG, "Thread $handlerThreadName was not started, ignoring event")
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
}