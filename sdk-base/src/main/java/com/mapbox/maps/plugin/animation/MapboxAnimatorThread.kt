package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import androidx.annotation.VisibleForTesting
import com.mapbox.common.MapboxSDKCommon
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logW
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
  constructor() : this(AndroidManifestMetaData())

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(manifestMetaData: AndroidManifestMetaData) {
    this.manifestMetaData = manifestMetaData
  }

  private val manifestMetaData: AndroidManifestMetaData
  private val mainAnimatorThread: MainAnimatorThread = MainAnimatorThread()
  private var backgroundAnimatorThread: BackgroundAnimatorThread? = null

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val backgroundLooper: Looper?
    get() = backgroundAnimatorThread?.handler?.looper

  /**
   * The beginning of the animator thread lifecycle. This will create resources for running
   * animator threads.
   */
  fun onAttached() {
    mainAnimatorThread.onAttached()
    if (manifestMetaData.backgroundAnimatorThreadEnabled()) {
      backgroundAnimatorThread = BackgroundAnimatorThread().onAttached()
    }
  }

  /**
   * Allows you to post any job onto the animator thread. This can be used to trigger animations
   * that may not be using the [Animator].
   *
   * Run immediate if my looper is the designated looper, otherwise post the work to a handler
   * queue. This is to reduce thread context switches while running animations.
   */
  fun post(runnable: Runnable): Boolean {
    return (backgroundAnimatorThread ?: mainAnimatorThread).post(runnable).also { posted ->
      if (!posted) {
        logW(
          TAG,
          "post did not happen, ensure the app is in foreground. background  animator " +
            "thread: ${backgroundAnimatorThread != null}"
        )
      }
    }
  }

  /**
   * Mapbox gl-native libraries require the main thread. Use this function whenever an animation
   * task should be posted on the main thread, even when background threads are enabled.
   *
   * Run immediate if my looper is the designated looper, otherwise post the work to a handler
   * queue. This is to reduce thread context switches while running animations.
   */
  fun postMain(runnable: Runnable): Boolean {
    return mainAnimatorThread.post(runnable).also { posted ->
      if (!posted) {
        logW(TAG, "postMain did not happen, ensure the app is in foreground")
      }
    }
  }

  /**
   * The beginning of the animator thread lifecycle. This will create resources for running
   * animator threads.
   */
  fun onDetached() {
    mainAnimatorThread.onDetached()
    backgroundAnimatorThread?.onDetached()
    backgroundAnimatorThread = null
  }

  private companion object {
    private const val TAG = "MapboxAnimatorThread"
  }
}

/**
 * The [PackageManager] has issues being mocked. This class makes it so usages of the package
 * meta data can be tested without mocking the application info metadata.
 *
 * https://github.com/mockk/mockk/issues/182
 */
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal class AndroidManifestMetaData {

  private fun metaData(): Bundle {
    val context = MapboxSDKCommon.getContext()
    val packageManager: PackageManager = context.packageManager
    return packageManager.getApplicationInfo(
      context.packageName,
      PackageManager.GET_META_DATA
    ).metaData
  }

  /**
   * Perform manifest metadata lookup for boolean value of
   * com.mapbox.maps.BackgroundAnimatorThread. Returns false by default.
   */
  fun backgroundAnimatorThreadEnabled(): Boolean {
    return metaData().getBoolean(BACKGROUND_ANIMATOR_THREAD_KEY, false)
  }

  private companion object {
    private const val BACKGROUND_ANIMATOR_THREAD_KEY = "com.mapbox.maps.BackgroundAnimatorThread"
  }
}

private interface AnimatorThread {
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  val handler: Handler?

  fun onAttached(): AnimatorThread
  fun onDetached()

  fun post(runnable: Runnable): Boolean {
    return if (Looper.myLooper() == handler?.looper) {
      runnable.run()
      true
    } else {
      handler?.post(runnable) ?: false
    }
  }
}

private class BackgroundAnimatorThread : AnimatorThread {
  private val threadName = "animation_thread_${UUID.randomUUID()}"
  private var handlerThread: HandlerThread = HandlerThread(
    threadName,
    Process.THREAD_PRIORITY_DISPLAY
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

private class MainAnimatorThread : AnimatorThread {
  override var handler: Handler? = null
    private set

  override fun onAttached() = apply {
    handler = Handler(Looper.getMainLooper())
  }

  override fun onDetached() {
    handler?.removeCallbacksAndMessages(null)
    handler = null
  }
}