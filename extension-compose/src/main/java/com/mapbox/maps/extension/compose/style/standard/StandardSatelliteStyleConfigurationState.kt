package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.extension.compose.style.BooleanValue

/**
 * The state holder for the [MapboxStandardSatelliteStyle]'s configurations.
 */
@Stable
public class StandardSatelliteStyleConfigurationState private constructor(
  initialShowRoadsAndTransit: BooleanValue,
  initialShowPedestrianRoads: BooleanValue,
) : BaseStyleConfigurationState() {
  /**
   * Public constructor for [StandardSatelliteStyleConfigurationState].
   */
  public constructor() : this(
    initialShowRoadsAndTransit = BooleanValue.INITIAL,
    initialShowPedestrianRoads = BooleanValue.INITIAL,
  )

  /**
   * Whether or not to show all roads and transit networks, default to true.
   */
  public var showRoadsAndTransit: BooleanValue by mutableStateOf(initialShowRoadsAndTransit)

  /**
   * Whether or not to show all pedestrian roads, paths, trails, default to true.
   */
  public var showPedestrianRoads: BooleanValue by mutableStateOf(initialShowPedestrianRoads)

  internal companion object {
    internal const val CONFIG_SHOW_ROADS_AND_TRANSIT = "showRoadsAndTransit"
    internal const val CONFIG_SHOW_PEDESTRIAN_ROADS = "showPedestrianRoads"
  }
}