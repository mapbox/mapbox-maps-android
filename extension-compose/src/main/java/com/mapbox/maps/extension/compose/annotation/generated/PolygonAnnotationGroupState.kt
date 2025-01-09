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
  initialFillElevationReference: FillElevationReference?,
  initialFillSortKey: Double?,
  initialFillAntialias: Boolean?,
  initialFillColor: Color?,
  initialFillEmissiveStrength: Double?,
  initialFillOpacity: Double?,
  initialFillOutlineColor: Color?,
  initialFillPattern: String?,
  initialFillTranslate: List<Double>?,
  initialFillTranslateAnchor: FillTranslateAnchor?,
  initialFillZOffset: Double?,
  initialPolygonAnnotationGroupInteractionsState: PolygonAnnotationGroupInteractionsState,
) {
  public constructor() : this(
    initialFillElevationReference = null,
    initialFillSortKey = null,
    initialFillAntialias = null,
    initialFillColor = null,
    initialFillEmissiveStrength = null,
    initialFillOpacity = null,
    initialFillOutlineColor = null,
    initialFillPattern = null,
    initialFillTranslate = null,
    initialFillTranslateAnchor = null,
    initialFillZOffset = null,
    initialPolygonAnnotationGroupInteractionsState = PolygonAnnotationGroupInteractionsState(),
  )

  /**
   * Holds all interactions with [PointAnnotationGroup]
   */
  public var interactionsState: PolygonAnnotationGroupInteractionsState by mutableStateOf(initialPolygonAnnotationGroupInteractionsState)
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
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillZOffset: Double? by mutableStateOf(initialFillZOffset)

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
  private fun UpdateFillZOffset(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillZOffset = fillZOffset
  }

  @Composable
  internal fun UpdateProperties(annotationManager: PolygonAnnotationManager) {
    UpdateFillElevationReference(annotationManager)
    UpdateFillSortKey(annotationManager)
    UpdateFillAntialias(annotationManager)
    UpdateFillColor(annotationManager)
    UpdateFillEmissiveStrength(annotationManager)
    UpdateFillOpacity(annotationManager)
    UpdateFillOutlineColor(annotationManager)
    UpdateFillPattern(annotationManager)
    UpdateFillTranslate(annotationManager)
    UpdateFillTranslateAnchor(annotationManager)
    UpdateFillZOffset(annotationManager)
  }
}

// End of generated file.