package com.mapbox.maps.plugin.animation.threads

import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import java.util.*

internal class BackgroundAnimatorThread : AnimatorThread {
  private val threadName = "animation_thread_${UUID.randomUUID()}"
  private var handlerThread: HandlerThread = HandlerThread(
    threadName,
    Process.THREAD_PRIORITY_FOREGROUND
  )

  override var handler: Handler? = null
    private set

  override fun onAttached() = apply {
    handlerThread.start()
    handler = Handler(handlerThread.looper)
  }

  override fun onDetached() {
    handler?.removeCallbacksAndMessages(null)
    handlerThread.quitSafely()
    handler = null
  }
}