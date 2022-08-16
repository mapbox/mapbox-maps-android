package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.content.pm.PackageManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import com.mapbox.common.MapboxSDKCommon
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logD
import java.util.*

/**
 * Allows you to run animations on a background thread.
 *
 *  ```xml
 *  <meta-data
 *    android:name="com.mapbox.maps.BackgroundAnimatorThread"
 *    android:value="true" />
 * ```
 */
@MapboxExperimental
class MapboxAnimatorThread {
  private val animatorThread: AnimatorThread by lazy {
    if (backgroundAnimatorThreadEnabled()) {
      BackgroundAnimatorThread()
    } else {
      MainAnimatorThread()
    }
  }

  fun onAttached() {
    animatorThread.onAttached()
  }

  /**
   * Allows you to post any job onto the animator thread. This can be used to trigger animations
   * that may not be using the [Animator].
   */
  fun post(runnable: Runnable): Boolean {
    return animatorThread.post(runnable)
  }

  fun onDetached() {
    animatorThread.onDetached()
  }

  private companion object {
    private const val METADATA_KEY = "com.mapbox.maps.BackgroundAnimatorThread"

    /**
     * Perform manifest metadata lookup for boolean value of
     * com.mapbox.maps.BackgroundAnimatorThread. Returns false by default.
     */
    private fun backgroundAnimatorThreadEnabled(): Boolean {
      val context = MapboxSDKCommon.getContext()
      val packageManager: PackageManager = context.packageManager
      val appInfo = packageManager.getApplicationInfo(
        context.packageName,
        PackageManager.GET_META_DATA
      )

      val metaData = appInfo.metaData
      return metaData.getBoolean(METADATA_KEY, false)
    }
  }
}

private interface AnimatorThread {
  fun onAttached()
  fun post(runnable: Runnable): Boolean
  fun onDetached()
}

private class BackgroundAnimatorThread : AnimatorThread {
  private val event: Any = Object()
  private val threadName by lazy {
    "animation_thread_${UUID.randomUUID()}"
  }

  private var startTime = 0L
  private var isRunning = false
  private var handlerThread: HandlerThread? = null
  private var handler: Handler? = null

  override fun onAttached() {
    logD(TAG, "onAttached thread $threadName")
    isRunning = true
    startTime = System.nanoTime()
    handlerThread = HandlerThread(threadName, Process.THREAD_PRIORITY_DISPLAY).apply {
      start()
      handler = Handler(this.looper)
    }
  }

  override fun post(runnable: Runnable): Boolean {
    return handler?.post(runnable) ?: false
  }

  override fun onDetached() {
    handler?.removeCallbacksAndMessages(event)
    handlerThread?.quitSafely()
    handler = null
    isRunning = false
    logD(TAG, "onDetached thread $threadName")
  }

  private companion object {
    private const val TAG = "BackgroundAnimatorThread"
  }
}

private class MainAnimatorThread : AnimatorThread {
  private var mainThreadHandler: Handler? = null

  override fun onAttached() {
    mainThreadHandler = Handler(Looper.getMainLooper())
  }

  override fun post(runnable: Runnable): Boolean {
    return if (mainThreadHandler != null && Looper.myLooper() == Looper.getMainLooper()) {
      runnable.run()
      true
    } else {
      mainThreadHandler?.post(runnable) ?: false
    }
  }

  override fun onDetached() {
    mainThreadHandler = null
  }
}