package com.mapbox.maps.viewannotation

import android.view.View
import com.mapbox.maps.MapView
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.ViewAnnotationOptions

/**
 * Interface responsible for notifying when core updated view annotation position or visibility.
 */
interface OnViewAnnotationUpdatedListener {

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

  /**
   * Callback triggered when view visibility has changed.
   * By visibility we understand whether view is shown or hidden and that's not precisely equivalent
   * listening to [View.getVisibility] as view may be visible but not yet shown.
   *
   * Note: this callback is triggered when using view annotation with not explicitly setting
   * [ViewAnnotationOptions.visible] meaning view annotation manager handles showing / hiding automatically
   * based on [View.getVisibility] on top of the map until it is not positioned correctly.
   *
   * @param view view that is updated.
   * @param visible true when [view] is shown on top of the map
   * and false if it's hidden either due to visibility or when it's removed from [MapView] completely.
   */
  fun onViewAnnotationVisibilityUpdated(
    view: View,
    visible: Boolean,
  )
}