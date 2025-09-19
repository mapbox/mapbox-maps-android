// This file is generated and will be overwritten automatically.

package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue

/**
 * The state holder for the StandardSatelliteState's configurations.
 */
@Stable
public class StandardSatelliteStyleConfigurationState private constructor(
  initialShowRoadsAndTransit: BooleanValue,
  initialShowPedestrianRoads: BooleanValue,
  initialBackgroundPointOfInterestLabels: BackgroundPointOfInterestLabelsValue,
  initialColorAdminBoundaries: ColorValue,
  initialColorModePointOfInterestLabels: ColorModePointOfInterestLabelsValue,
  initialColorMotorways: ColorValue,
  initialColorPlaceLabelHighlight: ColorValue,
  initialColorPlaceLabels: ColorValue,
  initialColorPlaceLabelSelect: ColorValue,
  initialColorPointOfInterestLabels: ColorValue,
  initialColorRoadLabels: ColorValue,
  initialColorRoads: ColorValue,
  initialColorTrunks: ColorValue,
  initialDensityPointOfInterestLabels: DoubleValue,
  initialRoadsBrightness: DoubleValue,
  initialShowAdminBoundaries: BooleanValue
) : BaseStyleConfigurationState() {

  /**
   * Public constructor for StandardSatelliteStyleConfigurationState.
   */
  public constructor() : this(
    initialShowRoadsAndTransit = BooleanValue.INITIAL,
    initialShowPedestrianRoads = BooleanValue.INITIAL,
    initialBackgroundPointOfInterestLabels = BackgroundPointOfInterestLabelsValue.INITIAL,
    initialColorAdminBoundaries = ColorValue.INITIAL,
    initialColorModePointOfInterestLabels = ColorModePointOfInterestLabelsValue.INITIAL,
    initialColorMotorways = ColorValue.INITIAL,
    initialColorPlaceLabelHighlight = ColorValue.INITIAL,
    initialColorPlaceLabels = ColorValue.INITIAL,
    initialColorPlaceLabelSelect = ColorValue.INITIAL,
    initialColorPointOfInterestLabels = ColorValue.INITIAL,
    initialColorRoadLabels = ColorValue.INITIAL,
    initialColorRoads = ColorValue.INITIAL,
    initialColorTrunks = ColorValue.INITIAL,
    initialDensityPointOfInterestLabels = DoubleValue.INITIAL,
    initialRoadsBrightness = DoubleValue.INITIAL,
    initialShowAdminBoundaries = BooleanValue.INITIAL
  )

  /**
   * Show or hide roads and transit networks.
   */
  public var showRoadsAndTransit: BooleanValue by mutableStateOf(initialShowRoadsAndTransit)

  /**
   * Show or hide pedestrian roads, paths, and trails.
   */
  public var showPedestrianRoads: BooleanValue by mutableStateOf(initialShowPedestrianRoads)

  /**
   * Set background shape for POI labels.
   */
  public var backgroundPointOfInterestLabels: BackgroundPointOfInterestLabelsValue by mutableStateOf(initialBackgroundPointOfInterestLabels)

  /**
   * Set a custom color for administrative boundaries.
   */
  public var colorAdminBoundaries: ColorValue by mutableStateOf(initialColorAdminBoundaries)

  /**
   * Use the default categorical colors or set a single custom color for POI labels.
   */
  public var colorModePointOfInterestLabels: ColorModePointOfInterestLabelsValue by mutableStateOf(initialColorModePointOfInterestLabels)

  /**
   * Set a custom color for motorway roads.
   */
  public var colorMotorways: ColorValue by mutableStateOf(initialColorMotorways)

  /**
   * Place label color used when setting highlight state.
   */
  public var colorPlaceLabelHighlight: ColorValue by mutableStateOf(initialColorPlaceLabelHighlight)

  /**
   * Set a custom color for place labels.
   */
  public var colorPlaceLabels: ColorValue by mutableStateOf(initialColorPlaceLabels)

  /**
   * Place label color used when setting select state.
   */
  public var colorPlaceLabelSelect: ColorValue by mutableStateOf(initialColorPlaceLabelSelect)

  /**
   * Set a custom color for POI labels.
   */
  public var colorPointOfInterestLabels: ColorValue by mutableStateOf(initialColorPointOfInterestLabels)

  /**
   * Set a custom color for road labels.
   */
  public var colorRoadLabels: ColorValue by mutableStateOf(initialColorRoadLabels)

  /**
   * Set a custom color for roads.
   */
  public var colorRoads: ColorValue by mutableStateOf(initialColorRoads)

  /**
   * Set a custom color for trunk roads.
   */
  public var colorTrunks: ColorValue by mutableStateOf(initialColorTrunks)

  /**
   * Set the density of POI labels.
   */
  public var densityPointOfInterestLabels: DoubleValue by mutableStateOf(initialDensityPointOfInterestLabels)

  /**
   * Control how bright road network appear in dark styles.
   */
  public var roadsBrightness: DoubleValue by mutableStateOf(initialRoadsBrightness)

  /**
   * Show or hide administrative boundaries.
   */
  public var showAdminBoundaries: BooleanValue by mutableStateOf(initialShowAdminBoundaries)

  internal companion object {
    internal const val CONFIG_LIGHT_PRESET = "lightPreset"
    internal const val CONFIG_FONT = "font"
    internal const val CONFIG_SHOW_POINT_OF_INTEREST_LABELS = "showPointOfInterestLabels"
    internal const val CONFIG_SHOW_TRANSIT_LABELS = "showTransitLabels"
    internal const val CONFIG_SHOW_PLACE_LABELS = "showPlaceLabels"
    internal const val CONFIG_SHOW_ROAD_LABELS = "showRoadLabels"
    internal const val CONFIG_SHOW_ROADS_AND_TRANSIT = "showRoadsAndTransit"
    internal const val CONFIG_SHOW_PEDESTRIAN_ROADS = "showPedestrianRoads"
    internal const val CONFIG_BACKGROUND_POINT_OF_INTEREST_LABELS = "backgroundPointOfInterestLabels"
    internal const val CONFIG_COLOR_ADMIN_BOUNDARIES = "colorAdminBoundaries"
    internal const val CONFIG_COLOR_MODE_POINT_OF_INTEREST_LABELS = "colorModePointOfInterestLabels"
    internal const val CONFIG_COLOR_MOTORWAYS = "colorMotorways"
    internal const val CONFIG_COLOR_PLACE_LABEL_HIGHLIGHT = "colorPlaceLabelHighlight"
    internal const val CONFIG_COLOR_PLACE_LABELS = "colorPlaceLabels"
    internal const val CONFIG_COLOR_PLACE_LABEL_SELECT = "colorPlaceLabelSelect"
    internal const val CONFIG_COLOR_POINT_OF_INTEREST_LABELS = "colorPointOfInterestLabels"
    internal const val CONFIG_COLOR_ROAD_LABELS = "colorRoadLabels"
    internal const val CONFIG_COLOR_ROADS = "colorRoads"
    internal const val CONFIG_COLOR_TRUNKS = "colorTrunks"
    internal const val CONFIG_DENSITY_POINT_OF_INTEREST_LABELS = "densityPointOfInterestLabels"
    internal const val CONFIG_ROADS_BRIGHTNESS = "roadsBrightness"
    internal const val CONFIG_SHOW_ADMIN_BOUNDARIES = "showAdminBoundaries"
  }
}