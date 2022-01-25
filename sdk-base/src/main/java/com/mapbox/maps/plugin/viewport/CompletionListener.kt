package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.MapboxExperimental

/**
 * A listener that's notified when the action is completed.
 */
@MapboxExperimental
fun interface CompletionListener {
  /**
   * Notifies the action is completed.
   *
   * @param isFinished true if the transition ran to completion and false
   * if it was canceled.
   */
  fun onComplete(isFinished: Boolean)
}