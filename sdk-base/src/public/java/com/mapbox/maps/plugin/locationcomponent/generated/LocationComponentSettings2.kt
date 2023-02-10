// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.graphics.Color
import com.mapbox.maps.plugin.PuckBearingSource
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

  /**
   * Whether the puck rotates to track the bearing source.
   */
  var puckBearingEnabled: Boolean = true,

  /**
   * The enum controls how the puck is oriented
   */
  var puckBearingSource: PuckBearingSource = PuckBearingSource.HEADING,
)

// End of generated file.