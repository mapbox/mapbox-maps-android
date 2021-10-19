package com.mapbox.maps.viewannotation

import android.view.View
import android.widget.FrameLayout

internal data class ViewAnnotation(
  /**
   * String id needed to call functions from gl-native
   */
  val id: String,
  /**
   * Inflated Android view.
   */
  val view: View,
  /**
   * We handle visibility automatically in the manager
   * if user did not specify [ViewAnnotationOptions.visible]  explicitly  during add / update operation
   */
  var handleVisibility: Boolean,
  /**
   * Layout params needed to position Android view correctly on the screen.
   */
  var viewLayoutParams: FrameLayout.LayoutParams,
)