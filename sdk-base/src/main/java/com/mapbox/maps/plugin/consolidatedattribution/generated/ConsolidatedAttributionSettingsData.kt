// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `ConsolidatedAttributionSettings`.

package com.mapbox.maps.plugin.consolidatedattribution.generated

import android.os.Parcelable
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Shows the consolidated attribution control, a single pill combining the Mapbox logo, the data attribution text, and a disclosure chevron.
 */
@MapboxExperimental
@Parcelize
@DataCompat(
    importsForDefaults = [
        "android.graphics.Color",
        "android.view.Gravity",
    ]
)
private data class ConsolidatedAttributionSettingsData(

  /**
   * Whether the consolidated attribution control is visible on the map. When enabled, it replaces the separate Mapbox logo and attribution icon. Default value: false.
   */
  @Default("false")
  var enabled: Boolean,

  /**
   * Defines where the consolidated attribution control is positioned on the map. Default value: "bottom-left".
   */
  @Default("Gravity.BOTTOM or Gravity.START")
  var position: Int,

  /**
   * Defines the margin to the left that the consolidated attribution control honors. Default value: 8. This property is specified in pixels.
   */
  @Default("8f")
  var marginLeft: Float,

  /**
   * Defines the margin to the top that the consolidated attribution control honors. Default value: 8. This property is specified in pixels.
   */
  @Default("8f")
  var marginTop: Float,

  /**
   * Defines the margin to the right that the consolidated attribution control honors. Default value: 8. This property is specified in pixels.
   */
  @Default("8f")
  var marginRight: Float,

  /**
   * Defines the margin to the bottom that the consolidated attribution control honors. Default value: 8. This property is specified in pixels.
   */
  @Default("8f")
  var marginBottom: Float,

  /**
   * Defines how much of the consolidated attribution control remains visible once it collapses. Default value: "icon-and-wordmark".
   */
  @Default("ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK")
  var collapsedStyle: ConsolidatedAttributionCollapsedStyle,

  /**
   * Defines the material filling the background of the consolidated attribution control. Light and dark rendering follows the night mode of the host configuration rather than this property. Default value: "solid".
   */
  @Default("ConsolidatedAttributionMaterial.SOLID")
  var material: ConsolidatedAttributionMaterial,

  /**
   * Defines how often the consolidated attribution control plays its intro expansion. Default value: "once".
   */
  @Default("ConsolidatedAttributionIntroExpansion.ONCE")
  var introExpansion: ConsolidatedAttributionIntroExpansion,

  /**
   * Nudges the background of the consolidated attribution control toward the hue of this color, bringing the control closer to the host application palette. Defaults to fully transparent, which applies no tint. Only the hue is taken, so the background keeps the brightness and the translucency selected by the material. The tint applies to the light appearance only, and the Mapbox logo, the attribution text and the chevron are never tinted. Default value: "#00000000".
   */
  @Default("Color.parseColor(\"#00000000\")")
  var tintColor: Int,
) : Parcelable

// End of generated file.