// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.graphics.Color
/**
 * Shows a location puck on the map.
 */
data class LocationComponentSettings2 @JvmOverloads constructor(

  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  var showAccuracyRing: Boolean = false,

  /**
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  var accuracyRingColor: Int = Color.parseColor("#4d89cff0"),

  /**
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  var accuracyRingBorderColor: Int = Color.parseColor("#4d89cff0"),
)

// End of generated file.