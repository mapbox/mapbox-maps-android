package com.mapbox.maps.viewannotation

import android.view.View
import com.mapbox.maps.ScreenCoordinate

/**
 * Interface responsible for notifying when core updated view annotation position.
 */
fun interface OnViewAnnotationPositionUpdatedListener {

  /**
   * Callback triggered when core updated view annotation position.
   *
   * Note: although view has already translation updated properly prior to this callback -
   * view could actually still not be there on the screen due to Android rendering invalidate mechanism.
   *
   * @param view view that is updated.
   * @param leftTopCoordinate left-top screen coordinate of the view in pixels;
   * both x and y may be < 0 if view does not fully fits the screen.
   * @param width width > 0 in pixels.
   * @param height height > 0 in pixels.
   */
  fun onViewAnnotationPositionUpdated(
    view: View,
    leftTopCoordinate: ScreenCoordinate,
    width: Int,
    height: Int,
  )
}