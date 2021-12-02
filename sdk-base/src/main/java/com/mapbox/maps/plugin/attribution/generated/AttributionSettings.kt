// This file is generated.

package com.mapbox.maps.plugin.attribution.generated

import android.graphics.Color
import android.view.Gravity
/**
 * Shows the attribution icon on the map.
 */
data class AttributionSettings @JvmOverloads constructor(

  /**
   * Whether the attribution icon is visible on the map.
   */
  var enabled: Boolean = true,

  /**
   * Defines text color of the attribution icon.
   */
  var iconColor: Int = Color.parseColor("#FF1E8CAB"),

  /**
   * Defines where the attribution icon is positioned on the map
   */
  var position: Int = Gravity.BOTTOM or Gravity.START,

  /**
   * Defines the margin to the left that the attribution icon honors. This property is specified in pixels.
   */
  var marginLeft: Float = 92f,

  /**
   * Defines the margin to the top that the attribution icon honors. This property is specified in pixels.
   */
  var marginTop: Float = 4f,

  /**
   * Defines the margin to the right that the attribution icon honors. This property is specified in pixels.
   */
  var marginRight: Float = 4f,

  /**
   * Defines the margin to the bottom that the attribution icon honors. This property is specified in pixels.
   */
  var marginBottom: Float = 4f,

  /**
   * Whether the attribution can be clicked and click events can be registered.
   */
  var clickable: Boolean = true,
)

// End of generated file.