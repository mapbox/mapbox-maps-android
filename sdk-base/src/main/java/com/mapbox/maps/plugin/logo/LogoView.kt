package com.mapbox.maps.plugin.logo

import androidx.annotation.Px

/**
 * Interface for logo view.
 *
 * The logo view implementation class should implement both the
 * LogoView interface and a View class (e.g ImageView).
 */
interface LogoView {
  /**
   * Whether the logo view is enabled.
   */
  var logoEnabled: Boolean

  /**
   * Returns the gravity value of the logo view.
   */
  var logoGravity: Int

  /**
   * Set the margins of the logo view.
   *
   * @param left Margin to the left in pixel
   * @param top Margin to the top in pixel
   * @param right Margin to the right in pixel
   * @param bottom Margin to the bottom in pixel
   */
  fun setLogoMargins(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int)

  /**
   * Call this when something has changed which has invalidated the
   * layout of this view. This will schedule a layout pass of the view
   * tree.
   */
  fun requestLayout()
}