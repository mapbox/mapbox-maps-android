package com.mapbox.maps.plugin.viewport

/**
 * A listener that's notified when the action is completed.
 */
fun interface CompletionListener {
  /**
   * Notifies the action is completed.
   *
   * @param isFinished true if the transition ran to completion and false
   * if it was canceled.
   */
  fun onComplete(isFinished: Boolean)
}