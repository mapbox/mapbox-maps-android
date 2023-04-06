// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `CompassSettings`.

package com.mapbox.maps.plugin.compass.generated

import android.os.Parcelable
import com.mapbox.maps.ImageHolder
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Shows the compass on the map.
 */
@Parcelize
@DataCompat(
    importsForDefaults = [
        "android.view.Gravity",
    ]
)
private data class CompassSettingsData(

  /**
   * Whether the compass is visible on the map.
   */
  @Default("true")
  var enabled: Boolean,

  /**
   * Defines where the compass is positioned on the map
   */
  @Default("Gravity.TOP or Gravity.END")
  var position: Int,

  /**
   * Defines the margin to the left that the compass icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginLeft: Float,

  /**
   * Defines the margin to the top that the compass icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginTop: Float,

  /**
   * Defines the margin to the right that the compass icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginRight: Float,

  /**
   * Defines the margin to the bottom that the compass icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginBottom: Float,

  /**
   * The alpha channel value of the compass image
   */
  @Default("1f")
  var opacity: Float,

  /**
   * The clockwise rotation value in degrees of the compass.
   */
  @Default("0f")
  var rotation: Float,

  /**
   * Whether the compass is displayed.
   */
  @Default("true")
  var visibility: Boolean,

  /**
   * Whether the compass fades out to invisible when facing north direction.
   */
  @Default("true")
  var fadeWhenFacingNorth: Boolean,

  /**
   * Whether the compass can be clicked and click events can be registered.
   */
  @Default("true")
  var clickable: Boolean,

  /**
   * The compass image, the visual representation of the compass.
   */
  var image: ImageHolder? = null,
) : Parcelable

// End of generated file.