// This file is generated.

package com.mapbox.maps.plugin.compass.generated

import android.graphics.drawable.Drawable
import android.view.Gravity
/**
 * Shows the compass on the map.
 */
data class CompassSettings @JvmOverloads constructor(

  /**
   * Whether the compass is visible on the map.
   */
  var enabled: Boolean = true,

  /**
   * Defines where the compass is positioned on the map
   */
  var position: Int = Gravity.TOP or Gravity.END,

  /**
   * Defines the margin to the left that the compass icon honors.
   */
  var marginLeft: Float = 4f,

  /**
   * Defines the margin to the top that the compass icon honors.
   */
  var marginTop: Float = 4f,

  /**
   * Defines the margin to the right that the compass icon honors.
   */
  var marginRight: Float = 4f,

  /**
   * Defines the margin to the bottom that the compass icon honors.
   */
  var marginBottom: Float = 4f,

  /**
   * The alpha channel value of the compass image
   */
  var opacity: Float = 1f,

  /**
   * The clockwise rotation value in degrees of the compass.
   */
  var rotation: Float = 0f,

  /**
   * Whether the compass is displayed.
   */
  var visibility: Boolean = true,

  /**
   * Whether the compass fades out to invisible when facing north direction.
   */
  var fadeWhenFacingNorth: Boolean = true,

  /**
   * Whether the compass can be clicked and click events can be registered.
   */
  var clickable: Boolean = true,

  /**
   * The compass image, the visual representation of the compass.
   */
  var image: Drawable? = null,
)

// End of generated file.