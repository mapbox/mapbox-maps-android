// This file is generated and will be overwritten automatically.

package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.StringValue

/**
 * The state holder for the StandardState's configurations.
 */
@Stable
public class StandardStyleConfigurationState private constructor(
  initialTheme: ThemeValue,
  initialShowPedestrianRoads: BooleanValue,
  initialShow3dObjects: BooleanValue,
  initialBackgroundPointOfInterestLabels: BackgroundPointOfInterestLabelsValue,
  initialColorAdminBoundaries: ColorValue,
  initialColorBuildingHighlight: ColorValue,
  initialColorBuildings: ColorValue,
  initialColorBuildingSelect: ColorValue,
  initialColorCommercial: ColorValue,
  initialColorEducation: ColorValue,
  initialColorGreenspace: ColorValue,
  initialColorIndustrial: ColorValue,
  initialColorLand: ColorValue,
  initialColorMedical: ColorValue,
  initialColorModePointOfInterestLabels: ColorModePointOfInterestLabelsValue,
  initialColorMotorways: ColorValue,
  initialColorPlaceLabelHighlight: ColorValue,
  initialColorPlaceLabels: ColorValue,
  initialColorPlaceLabelSelect: ColorValue,
  initialColorPointOfInterestLabels: ColorValue,
  initialColorRoadLabels: ColorValue,
  initialColorRoads: ColorValue,
  initialColorTrunks: ColorValue,
  initialColorWater: ColorValue,
  initialDensityPointOfInterestLabels: DoubleValue,
  initialFuelingStationModePointOfInterestLabels: StringValue,
  initialRoadsBrightness: DoubleValue,
  initialShow3dBuildings: BooleanValue,
  initialShow3dFacades: BooleanValue,
  initialShow3dLandmarks: BooleanValue,
  initialShow3dTrees: BooleanValue,
  initialShowAdminBoundaries: BooleanValue,
  initialShowLandmarkIconLabels: BooleanValue,
  initialShowLandmarkIcons: BooleanValue,
  initialThemeData: StringValue
) : BaseStyleConfigurationState() {

  /**
   * Public constructor for StandardStyleConfigurationState.
   */
  public constructor() : this(
    initialTheme = ThemeValue.INITIAL,
    initialShowPedestrianRoads = BooleanValue.INITIAL,
    initialShow3dObjects = BooleanValue.INITIAL,
    initialBackgroundPointOfInterestLabels = BackgroundPointOfInterestLabelsValue.INITIAL,
    initialColorAdminBoundaries = ColorValue.INITIAL,
    initialColorBuildingHighlight = ColorValue.INITIAL,
    initialColorBuildings = ColorValue.INITIAL,
    initialColorBuildingSelect = ColorValue.INITIAL,
    initialColorCommercial = ColorValue.INITIAL,
    initialColorEducation = ColorValue.INITIAL,
    initialColorGreenspace = ColorValue.INITIAL,
    initialColorIndustrial = ColorValue.INITIAL,
    initialColorLand = ColorValue.INITIAL,
    initialColorMedical = ColorValue.INITIAL,
    initialColorModePointOfInterestLabels = ColorModePointOfInterestLabelsValue.INITIAL,
    initialColorMotorways = ColorValue.INITIAL,
    initialColorPlaceLabelHighlight = ColorValue.INITIAL,
    initialColorPlaceLabels = ColorValue.INITIAL,
    initialColorPlaceLabelSelect = ColorValue.INITIAL,
    initialColorPointOfInterestLabels = ColorValue.INITIAL,
    initialColorRoadLabels = ColorValue.INITIAL,
    initialColorRoads = ColorValue.INITIAL,
    initialColorTrunks = ColorValue.INITIAL,
    initialColorWater = ColorValue.INITIAL,
    initialDensityPointOfInterestLabels = DoubleValue.INITIAL,
    initialFuelingStationModePointOfInterestLabels = StringValue.INITIAL,
    initialRoadsBrightness = DoubleValue.INITIAL,
    initialShow3dBuildings = BooleanValue.INITIAL,
    initialShow3dFacades = BooleanValue.INITIAL,
    initialShow3dLandmarks = BooleanValue.INITIAL,
    initialShow3dTrees = BooleanValue.INITIAL,
    initialShowAdminBoundaries = BooleanValue.INITIAL,
    initialShowLandmarkIconLabels = BooleanValue.INITIAL,
    initialShowLandmarkIcons = BooleanValue.INITIAL,
    initialThemeData = StringValue.INITIAL
  )

  /**
   * Switch between predefined themes or set a custom theme.
   */
  public var theme: ThemeValue by mutableStateOf(initialTheme)

  /**
   * Show or hide pedestrian roads, paths, and trails.
   */
  public var showPedestrianRoads: BooleanValue by mutableStateOf(initialShowPedestrianRoads)

  /**
   * Show or hide all 3D objects, including buildings, landmarks, and trees.
   */
  public var show3dObjects: BooleanValue by mutableStateOf(initialShow3dObjects)

  /**
   * Set background shape for POI labels.
   */
  public var backgroundPointOfInterestLabels: BackgroundPointOfInterestLabelsValue by mutableStateOf(initialBackgroundPointOfInterestLabels)

  /**
   * Set a custom color for administrative boundaries.
   */
  public var colorAdminBoundaries: ColorValue by mutableStateOf(initialColorAdminBoundaries)

  /**
   * Set a custom color for building fill extrusion when setting highlight state.
   */
  public var colorBuildingHighlight: ColorValue by mutableStateOf(initialColorBuildingHighlight)

  /**
   * Set a custom color for 3D &amp; 2D buildings.
   */
  public var colorBuildings: ColorValue by mutableStateOf(initialColorBuildings)

  /**
   * Set a custom color for building fill extrusion when setting select state.
   */
  public var colorBuildingSelect: ColorValue by mutableStateOf(initialColorBuildingSelect)

  /**
   * Set a custom color for commercial areas.
   */
  public var colorCommercial: ColorValue by mutableStateOf(initialColorCommercial)

  /**
   * Set a custom color for education areas.
   */
  public var colorEducation: ColorValue by mutableStateOf(initialColorEducation)

  /**
   * Set a custom color for greenspaces such as forests, parks, and woods.
   */
  public var colorGreenspace: ColorValue by mutableStateOf(initialColorGreenspace)

  /**
   * Set a custom color for industrial areas and airports.
   */
  public var colorIndustrial: ColorValue by mutableStateOf(initialColorIndustrial)

  /**
   * Set a custom color for land.
   */
  public var colorLand: ColorValue by mutableStateOf(initialColorLand)

  /**
   * Set a custom color for medical areas.
   */
  public var colorMedical: ColorValue by mutableStateOf(initialColorMedical)

  /**
   * Use the default categorical colors or set a single custom color for POI labels.
   */
  public var colorModePointOfInterestLabels: ColorModePointOfInterestLabelsValue by mutableStateOf(initialColorModePointOfInterestLabels)

  /**
   * Set a custom color for motorway roads.
   */
  public var colorMotorways: ColorValue by mutableStateOf(initialColorMotorways)

  /**
   * Set a custom color for place labels when setting highlight state.
   */
  public var colorPlaceLabelHighlight: ColorValue by mutableStateOf(initialColorPlaceLabelHighlight)

  /**
   * Set a custom color for place labels.
   */
  public var colorPlaceLabels: ColorValue by mutableStateOf(initialColorPlaceLabels)

  /**
   * Set a custom color for place labels when setting select state.
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
   * Set a custom color for other roads.
   */
  public var colorRoads: ColorValue by mutableStateOf(initialColorRoads)

  /**
   * Set a custom color for trunk roads.
   */
  public var colorTrunks: ColorValue by mutableStateOf(initialColorTrunks)

  /**
   * Set a custom color for water.
   */
  public var colorWater: ColorValue by mutableStateOf(initialColorWater)

  /**
   * Set the density of POI labels.
   */
  public var densityPointOfInterestLabels: DoubleValue by mutableStateOf(initialDensityPointOfInterestLabels)

  /**
   * Control the visibility of fuel and electric charging station POI labels. Default displays both types.
   */
  public var fuelingStationModePointOfInterestLabels: StringValue by mutableStateOf(initialFuelingStationModePointOfInterestLabels)

  /**
   * Control how bright roads appear in dark styles.
   */
  public var roadsBrightness: DoubleValue by mutableStateOf(initialRoadsBrightness)

  /**
   * Show or hide 3D buildings.
   */
  public var show3dBuildings: BooleanValue by mutableStateOf(initialShow3dBuildings)

  /**
   * Show or hide 3D building facades.
   */
  public var show3dFacades: BooleanValue by mutableStateOf(initialShow3dFacades)

  /**
   * Show or hide 3D landmark buildings.
   */
  public var show3dLandmarks: BooleanValue by mutableStateOf(initialShow3dLandmarks)

  /**
   * Show or hide 3D trees.
   */
  public var show3dTrees: BooleanValue by mutableStateOf(initialShow3dTrees)

  /**
   * Show or hide administrative boundaries.
   */
  public var showAdminBoundaries: BooleanValue by mutableStateOf(initialShowAdminBoundaries)

  /**
   * Show or hide Landmark icon labels.
   */
  public var showLandmarkIconLabels: BooleanValue by mutableStateOf(initialShowLandmarkIconLabels)

  /**
   * Show or hide Landmark icons.
   */
  public var showLandmarkIcons: BooleanValue by mutableStateOf(initialShowLandmarkIcons)

  /**
   * Set a custom theme based on a look-up table (LUT).
   */
  public var themeData: StringValue by mutableStateOf(initialThemeData)

  internal companion object {
    internal const val CONFIG_THEME = "theme"
    internal const val CONFIG_LIGHT_PRESET = "lightPreset"
    internal const val CONFIG_FONT = "font"
    internal const val CONFIG_SHOW_POINT_OF_INTEREST_LABELS = "showPointOfInterestLabels"
    internal const val CONFIG_SHOW_TRANSIT_LABELS = "showTransitLabels"
    internal const val CONFIG_SHOW_PLACE_LABELS = "showPlaceLabels"
    internal const val CONFIG_SHOW_ROAD_LABELS = "showRoadLabels"
    internal const val CONFIG_SHOW_PEDESTRIAN_ROADS = "showPedestrianRoads"
    internal const val CONFIG_SHOW3D_OBJECTS = "show3dObjects"
    internal const val CONFIG_BACKGROUND_POINT_OF_INTEREST_LABELS = "backgroundPointOfInterestLabels"
    internal const val CONFIG_COLOR_ADMIN_BOUNDARIES = "colorAdminBoundaries"
    internal const val CONFIG_COLOR_BUILDING_HIGHLIGHT = "colorBuildingHighlight"
    internal const val CONFIG_COLOR_BUILDINGS = "colorBuildings"
    internal const val CONFIG_COLOR_BUILDING_SELECT = "colorBuildingSelect"
    internal const val CONFIG_COLOR_COMMERCIAL = "colorCommercial"
    internal const val CONFIG_COLOR_EDUCATION = "colorEducation"
    internal const val CONFIG_COLOR_GREENSPACE = "colorGreenspace"
    internal const val CONFIG_COLOR_INDUSTRIAL = "colorIndustrial"
    internal const val CONFIG_COLOR_LAND = "colorLand"
    internal const val CONFIG_COLOR_MEDICAL = "colorMedical"
    internal const val CONFIG_COLOR_MODE_POINT_OF_INTEREST_LABELS = "colorModePointOfInterestLabels"
    internal const val CONFIG_COLOR_MOTORWAYS = "colorMotorways"
    internal const val CONFIG_COLOR_PLACE_LABEL_HIGHLIGHT = "colorPlaceLabelHighlight"
    internal const val CONFIG_COLOR_PLACE_LABELS = "colorPlaceLabels"
    internal const val CONFIG_COLOR_PLACE_LABEL_SELECT = "colorPlaceLabelSelect"
    internal const val CONFIG_COLOR_POINT_OF_INTEREST_LABELS = "colorPointOfInterestLabels"
    internal const val CONFIG_COLOR_ROAD_LABELS = "colorRoadLabels"
    internal const val CONFIG_COLOR_ROADS = "colorRoads"
    internal const val CONFIG_COLOR_TRUNKS = "colorTrunks"
    internal const val CONFIG_COLOR_WATER = "colorWater"
    internal const val CONFIG_DENSITY_POINT_OF_INTEREST_LABELS = "densityPointOfInterestLabels"
    internal const val CONFIG_FUELING_STATION_MODE_POINT_OF_INTEREST_LABELS = "fuelingStationModePointOfInterestLabels"
    internal const val CONFIG_ROADS_BRIGHTNESS = "roadsBrightness"
    internal const val CONFIG_SHOW3D_BUILDINGS = "show3dBuildings"
    internal const val CONFIG_SHOW3D_FACADES = "show3dFacades"
    internal const val CONFIG_SHOW3D_LANDMARKS = "show3dLandmarks"
    internal const val CONFIG_SHOW3D_TREES = "show3dTrees"
    internal const val CONFIG_SHOW_ADMIN_BOUNDARIES = "showAdminBoundaries"
    internal const val CONFIG_SHOW_LANDMARK_ICON_LABELS = "showLandmarkIconLabels"
    internal const val CONFIG_SHOW_LANDMARK_ICONS = "showLandmarkIcons"
    internal const val CONFIG_THEME_DATA = "theme-data"
  }
}