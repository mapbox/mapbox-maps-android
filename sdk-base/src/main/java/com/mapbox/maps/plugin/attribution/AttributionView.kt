package com.mapbox.maps.plugin.attribution

import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px

/**
 * Interface for attribution view.
 *
 * The attribution view implementation class should implement both the
 * AttributionView interface and a View class (e.g ImageView).
 */
interface AttributionView {
  /**
   * Set whether the attribution view is enabled.
   * @param enabled: the enable state
   */
  fun setEnable(enabled: Boolean)

  /**
   * Set the gravity value of the attribution view.
   * @param gravity view's gravity
   */
  fun setGravity(gravity: Int)

  /**
   * Set the attribution tint color
   * @param color the tint color
   */
  fun setIconColor(@ColorInt color: Int)

  /**
   * Set the margins of the attribution view.
   *
   * @param left Margin to the left in pixel
   * @param top Margin to the top in pixel
   * @param right Margin to the right in pixel
   * @param bottom Margin to the bottom in pixel
   */
  fun setAttributionMargins(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int)

  /**
   * Set an [View.OnClickListener] to AttributionView
   */
  fun setOnClickListener(listener: View.OnClickListener)
}