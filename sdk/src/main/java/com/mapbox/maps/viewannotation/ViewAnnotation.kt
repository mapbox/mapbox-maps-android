package com.mapbox.maps.viewannotation

import android.view.View
import android.widget.FrameLayout
import com.mapbox.maps.MapView

/**
 * Class representing view annotation anchored to the map.
 *
 * @param id needed for [ViewAnnotationManager.updateViewAnnotation], [ViewAnnotationManager.removeViewAnnotation], [ViewAnnotationManager.getViewAnnotationById] and [ViewAnnotationManager.getViewAnnotationOptionsById].
 * @param view instance of Android inflated [View] added above [MapView].
 */
data class ViewAnnotation(
  /**
   * Id needed for update, delete and get operations.
   */
  val id: String,
  /**
   * Instance of inflated Android [View].
   */
  val view: View,
  internal var viewLayoutParams: FrameLayout.LayoutParams
)