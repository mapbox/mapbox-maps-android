// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `LocationComponentSettings`.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.os.Parcelable
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.PuckBearingSource
import com.tobrun.datacompat.annotation.DataCompat
import com.tobrun.datacompat.annotation.Default
import kotlinx.parcelize.Parcelize

/**
 * Shows a location puck on the map.
 */
@Parcelize
@DataCompat(
    importsForDefaults = [
        "android.graphics.Color",
    ]
)
private data class LocationComponentSettingsData(

  /**
   * Whether the user location is visible on the map.
   */
  @Default("false")
  var enabled: Boolean,

  /**
   * Whether the location puck is pulsing on the map. Works for 2D location puck only.
   */
  @Default("false")
  var pulsingEnabled: Boolean,

  /**
   * The color of the pulsing circle. Works for 2D location puck only.
   */
  @Default("Color.parseColor(\"#4A90E2\")")
  var pulsingColor: Int,

  /**
   * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the pulsing circle's maximum radius to follow location accuracy circle. This property is specified in pixels.
   */
  @Default("10f")
  var pulsingMaxRadius: Float,

  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  @Default("false")
  var showAccuracyRing: Boolean,

  /**
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  @Default("Color.parseColor(\"#4d89cff0\")")
  var accuracyRingColor: Int,

  /**
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  @Default("Color.parseColor(\"#4d89cff0\")")
  var accuracyRingBorderColor: Int,

  /**
   * Sets the id of the layer that's added above to when placing the component on the map.
   */
  var layerAbove: String? = null,

  /**
   * Sets the id of the layer that's added below to when placing the component on the map.
   */
  var layerBelow: String? = null,

  /**
   * Whether the puck rotates to track the bearing source.
   */
  @Default("true")
  var puckBearingEnabled: Boolean,

  /**
   * The enum controls how the puck is oriented
   */
  @Default("PuckBearingSource.HEADING")
  var puckBearingSource: PuckBearingSource,

  /**
   * Defines what the customised look of the location puck. Note that direct changes to the puck variables won't have any effect, a new puck needs to be set every time.
   */
  var locationPuck: LocationPuck,
) : Parcelable

// End of generated file.