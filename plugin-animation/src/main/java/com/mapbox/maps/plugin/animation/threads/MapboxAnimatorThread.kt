package com.mapbox.maps.plugin.animation.threads

import android.animation.Animator
import android.os.Looper
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logW

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