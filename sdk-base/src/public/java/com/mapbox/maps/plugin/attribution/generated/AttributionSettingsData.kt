// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `AttributionSettings`.

package com.mapbox.maps.plugin.attribution.generated

import android.os.Parcelable
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Shows the attribution icon on the map.
 */
@Parcelize
@DataCompat(
    importsForDefaults = [
        "android.graphics.Color",
        "android.view.Gravity",
    ]
)
private data class AttributionSettingsData(

  /**
   * Whether the attribution icon is visible on the map.
   */
  @Default("true")
  var enabled: Boolean,

  /**
   * Defines text color of the attribution icon.
   */
  @Default("Color.parseColor(\"#FF1E8CAB\")")
  var iconColor: Int,

  /**
   * Defines where the attribution icon is positioned on the map
   */
  @Default("Gravity.BOTTOM or Gravity.START")
  var position: Int,

  /**
   * Defines the margin to the left that the attribution icon honors. This property is specified in pixels.
   */
  @Default("92f")
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

  /**
   * Whether the attribution can be clicked and click events can be registered.
   */
  @Default("true")
  var clickable: Boolean,
) : Parcelable

// End of generated file.