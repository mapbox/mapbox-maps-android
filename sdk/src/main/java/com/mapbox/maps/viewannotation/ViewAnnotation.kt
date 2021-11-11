package com.mapbox.maps.viewannotation

import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.mapbox.maps.ViewAnnotationOptions
import java.util.*

internal data class ViewAnnotation(
  /**
   * Inflated Android view.
   */
  val view: View,
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
) {
  /**
   * String id needed to call functions from gl-native
   */
  val id: String = (VIEW_ANNOTATION_CURRENT_ID++).toString()

  /**
   * Global layout listener to control positioning on the screen based on view's actual visibility.
   */
  var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

  private companion object {
    var VIEW_ANNOTATION_CURRENT_ID = 42
  }
}