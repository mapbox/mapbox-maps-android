package com.mapbox.maps.plugin.viewport.transition

/**
 * A listener that is notified when a [ViewportCamera] transition ends.
 */
fun interface TransitionEndListener {

  /**
   * Notifies the end of the transition.
   *
   * @param isCanceled whether the transition was canceled.
   */
  fun onTransitionEnd(isCanceled: Boolean)
}