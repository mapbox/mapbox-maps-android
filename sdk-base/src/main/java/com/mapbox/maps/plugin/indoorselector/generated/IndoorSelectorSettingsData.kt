// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `IndoorSelectorSettings`.

package com.mapbox.maps.plugin.indoorselector.generated

import android.os.Parcelable
import com.mapbox.maps.MapboxExperimental
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Settings for the indoor floor selector.
 */
@MapboxExperimental
@Parcelize
@DataCompat(
    importsForDefaults = [
        "android.view.Gravity",
    ]
)
private data class IndoorSelectorSettingsData(

  /**
   * Whether the indoor selector is visible on the map. Default value: true.
   */
  @Default("true")
  var enabled: Boolean,

  /**
   * Defines where the indoor selector is positioned on the map. Default value: "top-right".
   */
  @Default("Gravity.TOP or Gravity.END")
  var position: Int,

  /**
   * Defines the margin to the left that the indoor selector honors. Default value: 8. This property is specified in pixels.
   */
  @Default("8f")
  var marginLeft: Float,

  /**
   * Defines the margin to the top that the indoor selector honors. Default value: 60. This property is specified in pixels.
   */
  @Default("60f")
  var marginTop: Float,

  /**
   * Defines the margin to the right that the indoor selector honors. Default value: 8. This property is specified in pixels.
   */
  @Default("8f")
  var marginRight: Float,

  /**
   * Defines the margin to the bottom that the indoor selector honors. Default value: 8. This property is specified in pixels.
   */
  @Default("8f")
  var marginBottom: Float,
) : Parcelable

// End of generated file.