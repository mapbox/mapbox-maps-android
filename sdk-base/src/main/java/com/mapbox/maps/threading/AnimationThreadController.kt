package com.mapbox.maps.threading

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logW

/**
 * Experimental controller singleton allowing to use background thread for all Mapbox animations.
 * Most likely should not be used directly, calling [useBackgroundThread] should do all the work.
 *
 * By default all Mapbox animations are running on Android Main thread.
 *
 * Following Mapbox plugins are updated to make use of background animation thread:
 *  - plugin-locationcomponent
 *  - plugin-animation
 *  - plugin-viewport
 *
 *  Switching between [useBackgroundThread] and [useMainThread] could be done at any point in runtime.
 *
 *  Using this class is extremely unsafe and error prone, it should be used in very specific use-cases!
 *  Even when it's essential to use it (for example Android Auto use-case on specific devices when locking the device)
 *  it would be safer to use Android Main thread as much as possible and switch to background thread
 *  only when it's needed.
 */
@MapboxExperimental
object AnimationThreadController {

  /**
   * Indicates if background thread is used now to run Mapbox animators.
   */
  @Volatile
  var usingBackgroundThread = false
    private set
  private val mainHandler = Handler(Looper.getMainLooper())

  private var backgroundAnimationThread: HandlerThread? = null
  private var backgroundAnimationHandler: Handler? = null

  /**
   * Spawn new animation [HandlerThread] that will be used for all Mapbox animators.
   * If animation thread is already running - function is no-op.
   *
   * Behaviour is undefined if any animators are running when this method is called.
   */
  fun useBackgroundThread() {
    if (usingBackgroundThread) {
      return
    }
    usingBackgroundThread = true
    backgroundAnimationThread?.quit()
    backgroundAnimationThread = HandlerThread(BACKGROUND_ANIMATION_THREAD_NAME).apply {
      start()
      backgroundAnimationHandler = Handler(looper)
    }
  }

  /**
   * Execute [function] on Android Main thread.
   *
   * If called from Android Main thread - will be executed immediately in the same callchain
   * otherwise will be posted on Android Main thread explicitly.
   */
  fun postOnMainThread(function: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      function.invoke()
    } else {
      mainHandler.post { function.invoke() }
    }
  }

  /**
   * Execute [function] on animator thread.
   *
   * If called after [useBackgroundThread] from background animator thread - will be executed immediately in the same callchain
   * otherwise will be posted on background animator thread explicitly.
   *
   * If called after [useMainThread] from Android Main thread - will be executed immediately in the same callchain
   * otherwise will be posted on Android Main thread explicitly.
   */
  fun postOnAnimatorThread(function: () -> Unit) {
    if (usingBackgroundThread) {
      if (Looper.myLooper() == backgroundAnimationThread?.looper) {
        function.invoke()
      } else backgroundAnimationHandler?.let { backgroundHandler ->
        backgroundHandler.post { function.invoke() }
      } ?: logW(TAG, "useBackgroundThread was called but handler is null, animator event is skipped!")
    } else {
      postOnMainThread(function)
    }
  }

  /**
   * If [useBackgroundThread] was called beforehand - kill background animator thread
   * and use Android Main thread for all Mapbox animators. Otherwise function is no-op.
   *
   * Behaviour is undefined if any animators are running when this method is called.
   */
  fun useMainThread() {
    if (usingBackgroundThread) {
      backgroundAnimationHandler?.removeCallbacksAndMessages(null)
      backgroundAnimationThread?.quit()
      usingBackgroundThread = false
      backgroundAnimationThread = null
      backgroundAnimationHandler = null
    }
  }

  private const val TAG = "AnimThreadController"
  private const val BACKGROUND_ANIMATION_THREAD_NAME = "MapboxAnimThread"
}