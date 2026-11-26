package com.mapbox.maps.plugin.delegates

import android.os.Looper
import com.mapbox.maps.MapboxExperimental
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * The thread that drives a map: the thread the Mapbox SDKs are configured to run on, which is the
 * main thread by default. Plugins use it to run map work on that thread.
 */
@MapboxExperimental
interface MapThreadDelegate {
  /**
   * Coroutine scope that dispatches onto the map's thread. Cancelled when the map is destroyed.
   */
  val scope: CoroutineScope

  /**
   * Like [scope], but runs inline when already on the map's thread instead of re-dispatching.
   */
  val immediateScope: CoroutineScope

  /**
   * The [Looper] of the map's thread.
   */
  val looper: Looper

  /**
   * The dispatcher backing [scope]. It always posts, even when called from the map's thread; use
   * its [immediate][MainCoroutineDispatcher.immediate] to run inline there.
   */
  val dispatcher: MainCoroutineDispatcher
}