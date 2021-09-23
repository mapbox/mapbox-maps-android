// This file is generated.

package com.mapbox.maps.plugin.logo.generated

import android.view.Gravity
/**
 * Shows the Mapbox logo on the map.
 */
data class LogoSettings @JvmOverloads constructor(

  /**
   * Whether the logo is visible on the map.
   */
  var enabled: Boolean = true,

  /**
   * Defines where the logo is positioned on the map
   */
  var position: Int = Gravity.BOTTOM or Gravity.START,

  /**
   * Defines the margin to the left that the attribution icon honors.
   */
  var marginLeft: Float = 4f,

  /**
   * Defines the margin to the top that the attribution icon honors.
   */
  var marginTop: Float = 4f,

  /**
   * Defines the margin to the right that the attribution icon honors.
   */
  var marginRight: Float = 4f,

  /**
   * Defines the margin to the bottom that the attribution icon honors.
   */
  var marginBottom: Float = 4f,
)

// End of generated file.