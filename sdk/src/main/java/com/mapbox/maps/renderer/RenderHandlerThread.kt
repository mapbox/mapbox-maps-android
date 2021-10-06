package com.mapbox.maps.renderer

import android.os.Handler
import android.os.HandlerThread
import android.os.Process.THREAD_PRIORITY_DISPLAY
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Logger

internal class RenderHandlerThread {

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal lateinit var handlerThread: HandlerThread
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var handler: Handler? = null

  internal val started
    get() = handlerThread.isAlive

  fun post(task: () -> Unit) {
    handler?.let {
      it.post { task.invoke() }
    } ?: Logger.w(TAG, "Thread $HANDLE_THREAD_NAME was not started, ignoring event")
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

  fun clearMessageQueue() {
    handler?.removeCallbacksAndMessages(null)
  }

  companion object {
    private const val HANDLE_THREAD_NAME = "MapboxRenderThread"
    private const val TAG = "Mbgl-$HANDLE_THREAD_NAME"
  }
}