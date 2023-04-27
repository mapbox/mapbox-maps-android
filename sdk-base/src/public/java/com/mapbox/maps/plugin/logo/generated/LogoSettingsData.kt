// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `LogoSettings`.

package com.mapbox.maps.plugin.logo.generated

import android.os.Parcelable
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Shows the Mapbox logo on the map.
 */
@Parcelize
@DataCompat(
    importsForDefaults = [
        "android.view.Gravity",
    ]
)
private data class LogoSettingsData(

  /**
   * Whether the logo is visible on the map.
   */
  @Default("true")
  var enabled: Boolean,

  /**
   * Defines where the logo is positioned on the map
   */
  @Default("Gravity.BOTTOM or Gravity.START")
  var position: Int,

  /**
   * Defines the margin to the left that the attribution icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginLeft: Float,

  /**
   * Defines the margin to the top that the attribution icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginTop: Float,

  /**
   * Defines the margin to the right that the attribution icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginRight: Float,

  /**
   * Defines the margin to the bottom that the attribution icon honors. This property is specified in pixels.
   */
  @Default("4f")
  var marginBottom: Float,
) : Parcelable

// End of generated file.