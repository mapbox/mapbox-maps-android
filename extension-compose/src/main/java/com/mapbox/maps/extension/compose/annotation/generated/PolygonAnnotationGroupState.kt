// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.layers.properties.generated.FillElevationReference
import com.mapbox.maps.extension.style.layers.properties.generated.FillTranslateAnchor
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager

/**
 * The state holder for [PolygonAnnotationGroup] properties.
 */
@Stable
public class PolygonAnnotationGroupState private constructor(
  initialFillConstructBridgeGuardRail: Boolean?,
  initialFillElevationReference: FillElevationReference?,
  initialFillSortKey: Double?,
  initialFillAntialias: Boolean?,
  initialFillBridgeGuardRailColor: Color?,
  initialFillColor: Color?,
  initialFillEmissiveStrength: Double?,
  initialFillOpacity: Double?,
  initialFillOutlineColor: Color?,
  initialFillPattern: String?,
  initialFillTranslate: List<Double>?,
  initialFillTranslateAnchor: FillTranslateAnchor?,
  initialFillTunnelStructureColor: Color?,
  initialFillZOffset: Double?,
  initialMaxZoom: Double?,
  initialMinZoom: Double?,
  initialFillBridgeGuardRailColorUseTheme: String?,
  initialFillColorUseTheme: String?,
  initialFillOutlineColorUseTheme: String?,
  initialFillTunnelStructureColorUseTheme: String?,
  initialPolygonAnnotationGroupInteractionsState: PolygonAnnotationGroupInteractionsState,
) {
  public constructor() : this(
    initialFillConstructBridgeGuardRail = null,
    initialFillElevationReference = null,
    initialFillSortKey = null,
    initialFillAntialias = null,
    initialFillBridgeGuardRailColor = null,
    initialFillColor = null,
    initialFillEmissiveStrength = null,
    initialFillOpacity = null,
    initialFillOutlineColor = null,
    initialFillPattern = null,
    initialFillTranslate = null,
    initialFillTranslateAnchor = null,
    initialFillTunnelStructureColor = null,
    initialFillZOffset = null,
    initialMaxZoom = null,
    initialMinZoom = null,
    initialFillBridgeGuardRailColorUseTheme = null,
    initialFillColorUseTheme = null,
    initialFillOutlineColorUseTheme = null,
    initialFillTunnelStructureColorUseTheme = null,
    initialPolygonAnnotationGroupInteractionsState = PolygonAnnotationGroupInteractionsState(),
  )

  /**
   * Holds all interactions with [PointAnnotationGroup]
   */
  public var interactionsState: PolygonAnnotationGroupInteractionsState by mutableStateOf(initialPolygonAnnotationGroupInteractionsState)
  /**
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   */
  @MapboxExperimental
  public var fillConstructBridgeGuardRail: Boolean? by mutableStateOf(initialFillConstructBridgeGuardRail)
  /**
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   */
  @MapboxExperimental
  public var fillElevationReference: FillElevationReference? by mutableStateOf(initialFillElevationReference)
  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  public var fillSortKey: Double? by mutableStateOf(initialFillSortKey)
  /**
   * Whether or not the fill should be antialiased. Default value: true.
   */
  public var fillAntialias: Boolean? by mutableStateOf(initialFillAntialias)
  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  public var fillBridgeGuardRailColor: Color? by mutableStateOf(initialFillBridgeGuardRailColor)
  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   */
  public var fillColor: Color? by mutableStateOf(initialFillColor)
  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   */
  public var fillEmissiveStrength: Double? by mutableStateOf(initialFillEmissiveStrength)
  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   */
  public var fillOpacity: Double? by mutableStateOf(initialFillOpacity)
  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  public var fillOutlineColor: Color? by mutableStateOf(initialFillOutlineColor)
  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var fillPattern: String? by mutableStateOf(initialFillPattern)
  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   */
  public var fillTranslate: List<Double>? by mutableStateOf(initialFillTranslate)
  /**
   * Controls the frame of reference for `fill-translate`. Default value: "map".
   */
  public var fillTranslateAnchor: FillTranslateAnchor? by mutableStateOf(initialFillTranslateAnchor)
  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  public var fillTunnelStructureColor: Color? by mutableStateOf(initialFillTunnelStructureColor)
  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillZOffset: Double? by mutableStateOf(initialFillZOffset)
  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: Double? by mutableStateOf(initialMaxZoom)
  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: Double? by mutableStateOf(initialMinZoom)
  /**
   * This property defines whether the `fillBridgeGuardRailColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  public var fillBridgeGuardRailColorUseTheme: String? by mutableStateOf(initialFillBridgeGuardRailColorUseTheme)
  /**
   * This property defines whether the `fillColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  public var fillColorUseTheme: String? by mutableStateOf(initialFillColorUseTheme)
  /**
   * This property defines whether the `fillOutlineColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  public var fillOutlineColorUseTheme: String? by mutableStateOf(initialFillOutlineColorUseTheme)
  /**
   * This property defines whether the `fillTunnelStructureColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  public var fillTunnelStructureColorUseTheme: String? by mutableStateOf(initialFillTunnelStructureColorUseTheme)

  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillConstructBridgeGuardRail(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillConstructBridgeGuardRail = fillConstructBridgeGuardRail
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillElevationReference(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillElevationReference = fillElevationReference
  }
  @Composable
  private fun UpdateFillSortKey(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillSortKey = fillSortKey
  }
  @Composable
  private fun UpdateFillAntialias(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillAntialias = fillAntialias
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillBridgeGuardRailColor(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillBridgeGuardRailColorString = fillBridgeGuardRailColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateFillColor(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillColorString = fillColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateFillEmissiveStrength(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillEmissiveStrength = fillEmissiveStrength
  }
  @Composable
  private fun UpdateFillOpacity(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillOpacity = fillOpacity
  }
  @Composable
  private fun UpdateFillOutlineColor(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillOutlineColorString = fillOutlineColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateFillPattern(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillPattern = fillPattern
  }
  @Composable
  private fun UpdateFillTranslate(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillTranslate = fillTranslate
  }
  @Composable
  private fun UpdateFillTranslateAnchor(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillTranslateAnchor = fillTranslateAnchor
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillTunnelStructureColor(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillTunnelStructureColorString = fillTunnelStructureColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillZOffset(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillZOffset = fillZOffset
  }
  @Composable
  private fun UpdateMaxZoom(annotationManager: PolygonAnnotationManager) {
    annotationManager.maxZoom = maxZoom
  }
  @Composable
  private fun UpdateMinZoom(annotationManager: PolygonAnnotationManager) {
    annotationManager.minZoom = minZoom
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillBridgeGuardRailColorUseTheme(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillBridgeGuardRailColorUseTheme = fillBridgeGuardRailColorUseTheme
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillColorUseTheme(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillColorUseTheme = fillColorUseTheme
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillOutlineColorUseTheme(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillOutlineColorUseTheme = fillOutlineColorUseTheme
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateFillTunnelStructureColorUseTheme(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillTunnelStructureColorUseTheme = fillTunnelStructureColorUseTheme
  }

  @Composable
  internal fun UpdateProperties(annotationManager: PolygonAnnotationManager) {
    UpdateFillConstructBridgeGuardRail(annotationManager)
    UpdateFillElevationReference(annotationManager)
    UpdateFillSortKey(annotationManager)
    UpdateFillAntialias(annotationManager)
    UpdateFillBridgeGuardRailColor(annotationManager)
    UpdateFillColor(annotationManager)
    UpdateFillEmissiveStrength(annotationManager)
    UpdateFillOpacity(annotationManager)
    UpdateFillOutlineColor(annotationManager)
    UpdateFillPattern(annotationManager)
    UpdateFillTranslate(annotationManager)
    UpdateFillTranslateAnchor(annotationManager)
    UpdateFillTunnelStructureColor(annotationManager)
    UpdateFillZOffset(annotationManager)
    UpdateMaxZoom(annotationManager)
    UpdateMinZoom(annotationManager)
    UpdateFillBridgeGuardRailColorUseTheme(annotationManager)
    UpdateFillColorUseTheme(annotationManager)
    UpdateFillOutlineColorUseTheme(annotationManager)
    UpdateFillTunnelStructureColorUseTheme(annotationManager)
  }
}

// End of generated file.