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
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineElevationReference
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.layers.properties.generated.LineTranslateAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.LineWidthUnit
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager

/**
 * The state holder for [PolylineAnnotationGroup] properties.
 */
@Stable
public class PolylineAnnotationGroupState private constructor(
  initialLineCap: LineCap?,
  initialLineCrossSlope: Double?,
  initialLineElevationReference: LineElevationReference?,
  initialLineJoin: LineJoin?,
  initialLineMiterLimit: Double?,
  initialLineRoundLimit: Double?,
  initialLineSortKey: Double?,
  initialLineWidthUnit: LineWidthUnit?,
  initialLineZOffset: Double?,
  initialLineBlur: Double?,
  initialLineBorderColor: Color?,
  initialLineBorderWidth: Double?,
  initialLineColor: Color?,
  initialLineDasharray: List<Double>?,
  initialLineDepthOcclusionFactor: Double?,
  initialLineEmissiveStrength: Double?,
  initialLineGapWidth: Double?,
  initialLineOcclusionOpacity: Double?,
  initialLineOffset: Double?,
  initialLineOpacity: Double?,
  initialLinePattern: String?,
  initialLineTranslate: List<Double>?,
  initialLineTranslateAnchor: LineTranslateAnchor?,
  initialLineTrimColor: Color?,
  initialLineTrimFadeRange: List<Double>?,
  initialLineTrimOffset: List<Double>?,
  initialLineWidth: Double?,
  initialPolylineAnnotationGroupInteractionsState: PolylineAnnotationGroupInteractionsState,
) {
  public constructor() : this(
    initialLineCap = null,
    initialLineCrossSlope = null,
    initialLineElevationReference = null,
    initialLineJoin = null,
    initialLineMiterLimit = null,
    initialLineRoundLimit = null,
    initialLineSortKey = null,
    initialLineWidthUnit = null,
    initialLineZOffset = null,
    initialLineBlur = null,
    initialLineBorderColor = null,
    initialLineBorderWidth = null,
    initialLineColor = null,
    initialLineDasharray = null,
    initialLineDepthOcclusionFactor = null,
    initialLineEmissiveStrength = null,
    initialLineGapWidth = null,
    initialLineOcclusionOpacity = null,
    initialLineOffset = null,
    initialLineOpacity = null,
    initialLinePattern = null,
    initialLineTranslate = null,
    initialLineTranslateAnchor = null,
    initialLineTrimColor = null,
    initialLineTrimFadeRange = null,
    initialLineTrimOffset = null,
    initialLineWidth = null,
    initialPolylineAnnotationGroupInteractionsState = PolylineAnnotationGroupInteractionsState(),
  )

  /**
   * Holds all interactions with [PointAnnotationGroup]
   */
  public var interactionsState: PolylineAnnotationGroupInteractionsState by mutableStateOf(initialPolylineAnnotationGroupInteractionsState)
  /**
   * The display of line endings. Default value: "butt".
   */
  public var lineCap: LineCap? by mutableStateOf(initialLineCap)
  /**
   * Defines the slope of an elevated line. A value of 0 creates a horizontal line. A value of 1 creates a vertical line. Other values are currently not supported. If undefined, the line follows the terrain slope. This is an experimental property with some known issues:  - Vertical lines don't support line caps  - `line-join: round` is not supported with this property
   */
  @MapboxExperimental
  public var lineCrossSlope: Double? by mutableStateOf(initialLineCrossSlope)
  /**
   * Selects the base of line-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   */
  @MapboxExperimental
  public var lineElevationReference: LineElevationReference? by mutableStateOf(initialLineElevationReference)
  /**
   * The display of lines when joining. Default value: "miter".
   */
  public var lineJoin: LineJoin? by mutableStateOf(initialLineJoin)
  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles. Default value: 2.
   */
  public var lineMiterLimit: Double? by mutableStateOf(initialLineMiterLimit)
  /**
   * Used to automatically convert round joins to miter joins for shallow angles. Default value: 1.05.
   */
  public var lineRoundLimit: Double? by mutableStateOf(initialLineRoundLimit)
  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  public var lineSortKey: Double? by mutableStateOf(initialLineSortKey)
  /**
   * Selects the unit of line-width. The same unit is automatically used for line-blur and line-offset. Note: This is an experimental property and might be removed in a future release. Default value: "pixels".
   */
  @MapboxExperimental
  public var lineWidthUnit: LineWidthUnit? by mutableStateOf(initialLineWidthUnit)
  /**
   * Vertical offset from ground, in meters. Defaults to 0. This is an experimental property with some known issues:  - Not supported for globe projection at the moment  - Elevated line discontinuity is possible on tile borders with terrain enabled  - Rendering artifacts can happen near line joins and line caps depending on the line styling  - Rendering artifacts relating to `line-opacity` and `line-blur`  - Elevated line visibility is determined by layer order  - Z-fighting issues can happen with intersecting elevated lines  - Elevated lines don't cast shadows Default value: 0.
   */
  @MapboxExperimental
  public var lineZOffset: Double? by mutableStateOf(initialLineZOffset)
  /**
   * Blur applied to the line, in pixels. Default value: 0. Minimum value: 0. The unit of lineBlur is in pixels.
   */
  public var lineBlur: Double? by mutableStateOf(initialLineBlur)
  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
   */
  public var lineBorderColor: Color? by mutableStateOf(initialLineBorderColor)
  /**
   * The width of the line border. A value of zero means no border. Default value: 0. Minimum value: 0.
   */
  public var lineBorderWidth: Double? by mutableStateOf(initialLineBorderWidth)
  /**
   * The color with which the line will be drawn. Default value: "#000000".
   */
  public var lineColor: Color? by mutableStateOf(initialLineColor)
  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. Minimum value: 0. The unit of lineDasharray is in line widths.
   */
  public var lineDasharray: List<Double>? by mutableStateOf(initialLineDasharray)
  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded. Default value: 1. Value range: [0, 1]
   */
  public var lineDepthOcclusionFactor: Double? by mutableStateOf(initialLineDepthOcclusionFactor)
  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of lineEmissiveStrength is in intensity.
   */
  public var lineEmissiveStrength: Double? by mutableStateOf(initialLineEmissiveStrength)
  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. Default value: 0. Minimum value: 0. The unit of lineGapWidth is in pixels.
   */
  public var lineGapWidth: Double? by mutableStateOf(initialLineGapWidth)
  /**
   * Opacity multiplier (multiplies line-opacity value) of the line part that is occluded by 3D objects. Value 0 hides occluded part, value 1 means the same opacity as non-occluded part. The property is not supported when `line-opacity` has data-driven styling. Default value: 0. Value range: [0, 1]
   */
  public var lineOcclusionOpacity: Double? by mutableStateOf(initialLineOcclusionOpacity)
  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. Default value: 0. The unit of lineOffset is in pixels.
   */
  public var lineOffset: Double? by mutableStateOf(initialLineOffset)
  /**
   * The opacity at which the line will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var lineOpacity: Double? by mutableStateOf(initialLineOpacity)
  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var linePattern: String? by mutableStateOf(initialLinePattern)
  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of lineTranslate is in pixels.
   */
  public var lineTranslate: List<Double>? by mutableStateOf(initialLineTranslate)
  /**
   * Controls the frame of reference for `line-translate`. Default value: "map".
   */
  public var lineTranslateAnchor: LineTranslateAnchor? by mutableStateOf(initialLineTranslateAnchor)
  /**
   * The color to be used for rendering the trimmed line section that is defined by the `line-trim-offset` property. Default value: "transparent".
   */
  @MapboxExperimental
  public var lineTrimColor: Color? by mutableStateOf(initialLineTrimColor)
  /**
   * The fade range for the trim-start and trim-end points is defined by the `line-trim-offset` property. The first element of the array represents the fade range from the trim-start point toward the end of the line, while the second element defines the fade range from the trim-end point toward the beginning of the line. The fade result is achieved by interpolating between `line-trim-color` and the color specified by the `line-color` or the `line-gradient` property. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  @MapboxExperimental
  public var lineTrimFadeRange: List<Double>? by mutableStateOf(initialLineTrimFadeRange)
  /**
   * The line part between [trim-start, trim-end] will be painted using `line-trim-color,` which is transparent by default to produce a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0]. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  public var lineTrimOffset: List<Double>? by mutableStateOf(initialLineTrimOffset)
  /**
   * Stroke thickness. Default value: 1. Minimum value: 0. The unit of lineWidth is in pixels.
   */
  public var lineWidth: Double? by mutableStateOf(initialLineWidth)

  @Composable
  private fun UpdateLineCap(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineCap = lineCap
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineCrossSlope(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineCrossSlope = lineCrossSlope
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineElevationReference(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineElevationReference = lineElevationReference
  }
  @Composable
  private fun UpdateLineJoin(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineJoin = lineJoin
  }
  @Composable
  private fun UpdateLineMiterLimit(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineMiterLimit = lineMiterLimit
  }
  @Composable
  private fun UpdateLineRoundLimit(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineRoundLimit = lineRoundLimit
  }
  @Composable
  private fun UpdateLineSortKey(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineSortKey = lineSortKey
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineWidthUnit(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineWidthUnit = lineWidthUnit
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineZOffset(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineZOffset = lineZOffset
  }
  @Composable
  private fun UpdateLineBlur(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineBlur = lineBlur
  }
  @Composable
  private fun UpdateLineBorderColor(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineBorderColorString = lineBorderColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateLineBorderWidth(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineBorderWidth = lineBorderWidth
  }
  @Composable
  private fun UpdateLineColor(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineColorString = lineColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateLineDasharray(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineDasharray = lineDasharray
  }
  @Composable
  private fun UpdateLineDepthOcclusionFactor(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineDepthOcclusionFactor = lineDepthOcclusionFactor
  }
  @Composable
  private fun UpdateLineEmissiveStrength(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineEmissiveStrength = lineEmissiveStrength
  }
  @Composable
  private fun UpdateLineGapWidth(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineGapWidth = lineGapWidth
  }
  @Composable
  private fun UpdateLineOcclusionOpacity(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineOcclusionOpacity = lineOcclusionOpacity
  }
  @Composable
  private fun UpdateLineOffset(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineOffset = lineOffset
  }
  @Composable
  private fun UpdateLineOpacity(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineOpacity = lineOpacity
  }
  @Composable
  private fun UpdateLinePattern(annotationManager: PolylineAnnotationManager) {
    annotationManager.linePattern = linePattern
  }
  @Composable
  private fun UpdateLineTranslate(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTranslate = lineTranslate
  }
  @Composable
  private fun UpdateLineTranslateAnchor(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTranslateAnchor = lineTranslateAnchor
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineTrimColor(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTrimColorString = lineTrimColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineTrimFadeRange(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTrimFadeRange = lineTrimFadeRange
  }
  @Composable
  private fun UpdateLineTrimOffset(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTrimOffset = lineTrimOffset
  }
  @Composable
  private fun UpdateLineWidth(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineWidth = lineWidth
  }

  @Composable
  internal fun UpdateProperties(annotationManager: PolylineAnnotationManager) {
    UpdateLineCap(annotationManager)
    UpdateLineCrossSlope(annotationManager)
    UpdateLineElevationReference(annotationManager)
    UpdateLineJoin(annotationManager)
    UpdateLineMiterLimit(annotationManager)
    UpdateLineRoundLimit(annotationManager)
    UpdateLineSortKey(annotationManager)
    UpdateLineWidthUnit(annotationManager)
    UpdateLineZOffset(annotationManager)
    UpdateLineBlur(annotationManager)
    UpdateLineBorderColor(annotationManager)
    UpdateLineBorderWidth(annotationManager)
    UpdateLineColor(annotationManager)
    UpdateLineDasharray(annotationManager)
    UpdateLineDepthOcclusionFactor(annotationManager)
    UpdateLineEmissiveStrength(annotationManager)
    UpdateLineGapWidth(annotationManager)
    UpdateLineOcclusionOpacity(annotationManager)
    UpdateLineOffset(annotationManager)
    UpdateLineOpacity(annotationManager)
    UpdateLinePattern(annotationManager)
    UpdateLineTranslate(annotationManager)
    UpdateLineTranslateAnchor(annotationManager)
    UpdateLineTrimColor(annotationManager)
    UpdateLineTrimFadeRange(annotationManager)
    UpdateLineTrimOffset(annotationManager)
    UpdateLineWidth(annotationManager)
  }
}

// End of generated file.