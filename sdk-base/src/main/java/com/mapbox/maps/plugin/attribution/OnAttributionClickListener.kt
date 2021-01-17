package com.mapbox.maps.plugin.attribution

/**
 * Listener to get OnClick event on the view.
 */
fun interface OnAttributionClickListener {

  /**
   * Invoked when the attribution is clicked.
   */
  fun onAttributionClick()
}