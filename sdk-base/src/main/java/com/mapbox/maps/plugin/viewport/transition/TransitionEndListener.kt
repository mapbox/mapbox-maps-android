package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.maps.plugin.viewport.ViewportPlugin

/**
 * A listener that is notified when a [ViewportPlugin] transition ends.
 */
fun interface TransitionEndListener {

  /**
   * Notifies the end of the transition.
   *
   * @param isCanceled whether the transition was canceled.
   */
  fun onTransitionEnd(isCanceled: Boolean)
}