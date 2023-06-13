package com.mapbox.maps.viewannotation

import android.view.View
import android.widget.FrameLayout
import com.mapbox.maps.ViewAnnotationOptions
import java.util.UUID

internal data class ViewAnnotation(
  /**
   * Inflated Android view.
   */
  val view: View,
  /**
   * Attach state listener needed to control global layout listener lifecycle.
   */
  var attachStateListener: View.OnAttachStateChangeListener? = null,
  /**
   * We handle visibility automatically in the manager
   * if user did not specify [ViewAnnotationOptions.visible]  explicitly  during add / update operation
   */
  var handleVisibilityAutomatically: Boolean,
  /**
   * View annotation visibility state.
   */
  var visibility: ViewAnnotationVisibility,
  /**
   * Layout params needed to position Android view correctly on the screen.
   */
  var viewLayoutParams: FrameLayout.LayoutParams,
  /**
   * Cached value of last measured width. If user did specify width explicitly - will be set to [USER_FIXED_DIMENSION].
   */
  var measuredWidth: Int,
  /**
   * Cached value of last measured height. If user did specify height explicitly - will be set to [USER_FIXED_DIMENSION].
   */
  var measuredHeight: Int,
) {
  /**
   * UUID needed to call functions from gl-native.
   */
  val id: String = UUID.randomUUID().toString()

  /**
   * Helper function to understand if view is visible from Android visibility perspective.
   */
  val isVisible get() = visibility == ViewAnnotationVisibility.VISIBLE_AND_POSITIONED ||
    visibility == ViewAnnotationVisibility.VISIBLE_AND_NOT_POSITIONED

  companion object {
    internal val USER_FIXED_DIMENSION = -1
  }
}