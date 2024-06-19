// This file is generated.
// This class is annotated with `DataCompat`.
// Therefore, it is used to auto-generate `LocationComponentSettings`.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.os.Parcelable
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.PuckBearing
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
   * Whether the user location is visible on the map. Default value: false.
   */
  @Default("false")
  var enabled: Boolean,

  /**
   * Whether the location puck is pulsing on the map. Works for 2D location puck only. Default value: false.
   */
  @Default("false")
  var pulsingEnabled: Boolean,

  /**
   * The color of the pulsing circle. Works for 2D location puck only. Default value: "#4A90E2".
   */
  @Default("Color.parseColor(\"#4A90E2\")")
  var pulsingColor: Int,

  /**
   * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the pulsing circle's maximum radius to follow location accuracy circle. Default value: 10. This property is specified in pixels.
   */
  @Default("10f")
  var pulsingMaxRadius: Float,

  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only. Default value: false.
   */
  @Default("false")
  var showAccuracyRing: Boolean,

  /**
   * The color of the accuracy ring. Works for 2D location puck only. Default value: "#4d89cff0".
   */
  @Default("Color.parseColor(\"#4d89cff0\")")
  var accuracyRingColor: Int,

  /**
   * The color of the accuracy ring border. Works for 2D location puck only. Default value: "#4d89cff0".
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
   * Whether the puck rotates to track the bearing source. Default value: false.
   */
  @Default("false")
  var puckBearingEnabled: Boolean,

  /**
   * The enum controls how the puck is oriented Default value: "heading".
   */
  @Default("PuckBearing.HEADING")
  var puckBearing: PuckBearing,

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists, it will be placed at that position in the layer order.
   */
  var slot: String? = null,

  /**
   * Defines what the customised look of the location puck. Note that direct changes to the puck variables won't have any effect, a new puck needs to be set every time.
   */
  var locationPuck: LocationPuck,
) : Parcelable

// End of generated file.