package com.mapbox.maps.viewannotation

import android.view.View
import android.widget.FrameLayout
import com.mapbox.maps.ViewAnnotationOptions

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
   * Cached value if view is visible before OnGlobalLayoutListener was triggered.
   */
  var visible: Boolean,
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
   * String id needed to call functions from gl-native
   */
  val id: String = (VIEW_ANNOTATION_CURRENT_ID++).toString()

  companion object {
    private var VIEW_ANNOTATION_CURRENT_ID = 42
    internal val USER_FIXED_DIMENSION = -1
  }
}