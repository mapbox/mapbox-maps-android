package com.mapbox.maps.plugin.compass

import android.graphics.drawable.Drawable
import androidx.annotation.Px

/**
 * Interface for compass view.
 *
 * The compass view implementation class should implement both the
 * CompassView interface and a View class(e.g ImageView).
 */
interface CompassView {
  /**
   * Whether the compass view is visible.
   */
  var isCompassVisible: Boolean

  /**
   * Whether the compass view is enabled.
   */
  var isCompassEnabled: Boolean

  /**
   * The CompassView image as a Drawable.
   */
  var compassImage: Drawable

  /**
   * Returns the gravity value of the CompassView.
   */
  var compassGravity: Int

  /**
   * The direction of the CompassView.
   */
  var compassRotation: Float

  /**
   * Set the alpha value of the compass.
   *
   * @param float the alpha value
   */
  fun setCompassAlpha(float: Float)

  /**
   * Set the margins of the compass view.
   *
   * @param left Margin to the left in pixel
   * @param top Margin to the top in pixel
   * @param right Margin to the right in pixel
   * @param bottom Margin to the bottom in pixel
   */
  fun setCompassMargins(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int)

  /**
   * Call this when something has changed which has invalidated the
   * layout of this view. This will schedule a layout pass of the view
   * tree.
   */
  fun requestLayout()
}