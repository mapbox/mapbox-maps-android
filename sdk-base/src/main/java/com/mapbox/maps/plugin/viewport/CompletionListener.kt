package com.mapbox.maps.plugin.viewport

/**
 * A listener that's notified when the action is completed.
 */
fun interface CompletionListener {
  /**
   * Notifies the action is completed.
   *
   * Transition may end early if they fail or are interrupted(e.g. by another call to [ViewportPlugin.transitionTo]
   * or [ViewportPlugin.idle]).
   *
   * @param isFinished true if the transition ran to completion and false
   * if it was canceled or interrupted.
   */
  fun onComplete(isFinished: Boolean)
}