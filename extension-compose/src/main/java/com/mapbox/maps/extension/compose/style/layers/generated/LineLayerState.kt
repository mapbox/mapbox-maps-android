// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleRangeValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [LineLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#line)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class LineLayerState private constructor(
  initialLineCap: LineCapValue,
  initialLineCrossSlope: DoubleValue,
  initialLineElevationReference: LineElevationReferenceValue,
  initialLineJoin: LineJoinValue,
  initialLineMiterLimit: DoubleValue,
  initialLineRoundLimit: DoubleValue,
  initialLineSortKey: DoubleValue,
  initialLineWidthUnit: LineWidthUnitValue,
  initialLineZOffset: DoubleValue,
  initialLineBlur: DoubleValue,
  initialLineBlurTransition: Transition,
  initialLineBorderColor: ColorValue,
  initialLineBorderColorUseTheme: StringValue,
  initialLineBorderColorTransition: Transition,
  initialLineBorderWidth: DoubleValue,
  initialLineBorderWidthTransition: Transition,
  initialLineColor: ColorValue,
  initialLineColorUseTheme: StringValue,
  initialLineColorTransition: Transition,
  initialLineDasharray: DoubleListValue,
  initialLineDepthOcclusionFactor: DoubleValue,
  initialLineDepthOcclusionFactorTransition: Transition,
  initialLineEmissiveStrength: DoubleValue,
  initialLineEmissiveStrengthTransition: Transition,
  initialLineGapWidth: DoubleValue,
  initialLineGapWidthTransition: Transition,
  initialLineGradient: ColorValue,
  initialLineGradientUseTheme: StringValue,
  initialLineOcclusionOpacity: DoubleValue,
  initialLineOcclusionOpacityTransition: Transition,
  initialLineOffset: DoubleValue,
  initialLineOffsetTransition: Transition,
  initialLineOpacity: DoubleValue,
  initialLineOpacityTransition: Transition,
  initialLinePattern: ImageValue,
  initialLineTranslate: DoubleListValue,
  initialLineTranslateTransition: Transition,
  initialLineTranslateAnchor: LineTranslateAnchorValue,
  initialLineTrimColor: ColorValue,
  initialLineTrimColorUseTheme: StringValue,
  initialLineTrimColorTransition: Transition,
  initialLineTrimFadeRange: DoubleRangeValue,
  initialLineTrimOffset: DoubleListValue,
  initialLineWidth: DoubleValue,
  initialLineWidthTransition: Transition,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [LineLayerState].
   */
  public constructor() : this(
    initialLineCap = LineCapValue.INITIAL,
    initialLineCrossSlope = DoubleValue.INITIAL,
    initialLineElevationReference = LineElevationReferenceValue.INITIAL,
    initialLineJoin = LineJoinValue.INITIAL,
    initialLineMiterLimit = DoubleValue.INITIAL,
    initialLineRoundLimit = DoubleValue.INITIAL,
    initialLineSortKey = DoubleValue.INITIAL,
    initialLineWidthUnit = LineWidthUnitValue.INITIAL,
    initialLineZOffset = DoubleValue.INITIAL,
    initialLineBlur = DoubleValue.INITIAL,
    initialLineBlurTransition = Transition.INITIAL,
    initialLineBorderColor = ColorValue.INITIAL,
    initialLineBorderColorUseTheme = StringValue.INITIAL,
    initialLineBorderColorTransition = Transition.INITIAL,
    initialLineBorderWidth = DoubleValue.INITIAL,
    initialLineBorderWidthTransition = Transition.INITIAL,
    initialLineColor = ColorValue.INITIAL,
    initialLineColorUseTheme = StringValue.INITIAL,
    initialLineColorTransition = Transition.INITIAL,
    initialLineDasharray = DoubleListValue.INITIAL,
    initialLineDepthOcclusionFactor = DoubleValue.INITIAL,
    initialLineDepthOcclusionFactorTransition = Transition.INITIAL,
    initialLineEmissiveStrength = DoubleValue.INITIAL,
    initialLineEmissiveStrengthTransition = Transition.INITIAL,
    initialLineGapWidth = DoubleValue.INITIAL,
    initialLineGapWidthTransition = Transition.INITIAL,
    initialLineGradient = ColorValue.INITIAL,
    initialLineGradientUseTheme = StringValue.INITIAL,
    initialLineOcclusionOpacity = DoubleValue.INITIAL,
    initialLineOcclusionOpacityTransition = Transition.INITIAL,
    initialLineOffset = DoubleValue.INITIAL,
    initialLineOffsetTransition = Transition.INITIAL,
    initialLineOpacity = DoubleValue.INITIAL,
    initialLineOpacityTransition = Transition.INITIAL,
    initialLinePattern = ImageValue.INITIAL,
    initialLineTranslate = DoubleListValue.INITIAL,
    initialLineTranslateTransition = Transition.INITIAL,
    initialLineTranslateAnchor = LineTranslateAnchorValue.INITIAL,
    initialLineTrimColor = ColorValue.INITIAL,
    initialLineTrimColorUseTheme = StringValue.INITIAL,
    initialLineTrimColorTransition = Transition.INITIAL,
    initialLineTrimFadeRange = DoubleRangeValue.INITIAL,
    initialLineTrimOffset = DoubleListValue.INITIAL,
    initialLineWidth = DoubleValue.INITIAL,
    initialLineWidthTransition = Transition.INITIAL,
    initialVisibility = VisibilityValue.INITIAL,
    initialMinZoom = LongValue.INITIAL,
    initialMaxZoom = LongValue.INITIAL,
    initialSourceLayer = StringValue.INITIAL,
    initialFilter = Filter.INITIAL,
    initialInteractionsState = LayerInteractionsState(),
  )

  /**
   * The interactions associated with this layer.
   */
  @MapboxExperimental
  public var interactionsState: LayerInteractionsState by mutableStateOf(initialInteractionsState)

  /**
   *  The display of line endings. Default value: "butt".
   */
  public var lineCap: LineCapValue by mutableStateOf(initialLineCap)
  /**
   *  Defines the slope of an elevated line. A value of 0 creates a horizontal line. A value of 1 creates a vertical line. Other values are currently not supported. If undefined, the line follows the terrain slope. This is an experimental property with some known issues:  - Vertical lines don't support line caps  - `line-join: round` is not supported with this property
   */
  @MapboxExperimental
  public var lineCrossSlope: DoubleValue by mutableStateOf(initialLineCrossSlope)
  /**
   *  Selects the base of line-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   */
  @MapboxExperimental
  public var lineElevationReference: LineElevationReferenceValue by mutableStateOf(initialLineElevationReference)
  /**
   *  The display of lines when joining. Default value: "miter".
   */
  public var lineJoin: LineJoinValue by mutableStateOf(initialLineJoin)
  /**
   *  Used to automatically convert miter joins to bevel joins for sharp angles. Default value: 2.
   */
  public var lineMiterLimit: DoubleValue by mutableStateOf(initialLineMiterLimit)
  /**
   *  Used to automatically convert round joins to miter joins for shallow angles. Default value: 1.05.
   */
  public var lineRoundLimit: DoubleValue by mutableStateOf(initialLineRoundLimit)
  /**
   *  Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  public var lineSortKey: DoubleValue by mutableStateOf(initialLineSortKey)
  /**
   *  Selects the unit of line-width. The same unit is automatically used for line-blur and line-offset. Note: This is an experimental property and might be removed in a future release. Default value: "pixels".
   */
  @MapboxExperimental
  public var lineWidthUnit: LineWidthUnitValue by mutableStateOf(initialLineWidthUnit)
  /**
   *  Vertical offset from ground, in meters. Defaults to 0. This is an experimental property with some known issues:  - Not supported for globe projection at the moment  - Elevated line discontinuity is possible on tile borders with terrain enabled  - Rendering artifacts can happen near line joins and line caps depending on the line styling  - Rendering artifacts relating to `line-opacity` and `line-blur`  - Elevated line visibility is determined by layer order  - Z-fighting issues can happen with intersecting elevated lines  - Elevated lines don't cast shadows Default value: 0.
   */
  @MapboxExperimental
  public var lineZOffset: DoubleValue by mutableStateOf(initialLineZOffset)
  /**
   *  Blur applied to the line, in pixels. Default value: 0. Minimum value: 0. The unit of lineBlur is in pixels.
   */
  public var lineBlur: DoubleValue by mutableStateOf(initialLineBlur)
  /**
   *  Defines the transition of [lineBlur].
   */
  public var lineBlurTransition: Transition by mutableStateOf(initialLineBlurTransition)
  /**
   *  The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
   */
  public var lineBorderColor: ColorValue by mutableStateOf(initialLineBorderColor)
  /**
   *  Overrides applying of color theme for [lineBorderColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineBorderColorUseTheme: StringValue by mutableStateOf(initialLineBorderColorUseTheme)
  /**
   *  Defines the transition of [lineBorderColor].
   */
  public var lineBorderColorTransition: Transition by mutableStateOf(initialLineBorderColorTransition)
  /**
   *  The width of the line border. A value of zero means no border. Default value: 0. Minimum value: 0.
   */
  public var lineBorderWidth: DoubleValue by mutableStateOf(initialLineBorderWidth)
  /**
   *  Defines the transition of [lineBorderWidth].
   */
  public var lineBorderWidthTransition: Transition by mutableStateOf(initialLineBorderWidthTransition)
  /**
   *  The color with which the line will be drawn. Default value: "#000000".
   */
  public var lineColor: ColorValue by mutableStateOf(initialLineColor)
  /**
   *  Overrides applying of color theme for [lineColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineColorUseTheme: StringValue by mutableStateOf(initialLineColorUseTheme)
  /**
   *  Defines the transition of [lineColor].
   */
  public var lineColorTransition: Transition by mutableStateOf(initialLineColorTransition)
  /**
   *  Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. Minimum value: 0. The unit of lineDasharray is in line widths.
   */
  public var lineDasharray: DoubleListValue by mutableStateOf(initialLineDasharray)
  /**
   *  Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded. Default value: 1. Value range: [0, 1]
   */
  public var lineDepthOcclusionFactor: DoubleValue by mutableStateOf(initialLineDepthOcclusionFactor)
  /**
   *  Defines the transition of [lineDepthOcclusionFactor].
   */
  public var lineDepthOcclusionFactorTransition: Transition by mutableStateOf(initialLineDepthOcclusionFactorTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of lineEmissiveStrength is in intensity.
   */
  public var lineEmissiveStrength: DoubleValue by mutableStateOf(initialLineEmissiveStrength)
  /**
   *  Defines the transition of [lineEmissiveStrength].
   */
  public var lineEmissiveStrengthTransition: Transition by mutableStateOf(initialLineEmissiveStrengthTransition)
  /**
   *  Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. Default value: 0. Minimum value: 0. The unit of lineGapWidth is in pixels.
   */
  public var lineGapWidth: DoubleValue by mutableStateOf(initialLineGapWidth)
  /**
   *  Defines the transition of [lineGapWidth].
   */
  public var lineGapWidthTransition: Transition by mutableStateOf(initialLineGapWidthTransition)
  /**
   *  A gradient used to color a line feature at various distances along its length. Defined using a `step` or `interpolate` expression which outputs a color for each corresponding `line-progress` input value. `line-progress` is a percentage of the line feature's total length as measured on the webmercator projected coordinate plane (a `number` between `0` and `1`). Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
   */
  public var lineGradient: ColorValue by mutableStateOf(initialLineGradient)
  /**
   *  Overrides applying of color theme for [lineGradient] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineGradientUseTheme: StringValue by mutableStateOf(initialLineGradientUseTheme)
  /**
   *  Opacity multiplier (multiplies line-opacity value) of the line part that is occluded by 3D objects. Value 0 hides occluded part, value 1 means the same opacity as non-occluded part. The property is not supported when `line-opacity` has data-driven styling. Default value: 0. Value range: [0, 1]
   */
  public var lineOcclusionOpacity: DoubleValue by mutableStateOf(initialLineOcclusionOpacity)
  /**
   *  Defines the transition of [lineOcclusionOpacity].
   */
  public var lineOcclusionOpacityTransition: Transition by mutableStateOf(initialLineOcclusionOpacityTransition)
  /**
   *  The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. Default value: 0. The unit of lineOffset is in pixels.
   */
  public var lineOffset: DoubleValue by mutableStateOf(initialLineOffset)
  /**
   *  Defines the transition of [lineOffset].
   */
  public var lineOffsetTransition: Transition by mutableStateOf(initialLineOffsetTransition)
  /**
   *  The opacity at which the line will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var lineOpacity: DoubleValue by mutableStateOf(initialLineOpacity)
  /**
   *  Defines the transition of [lineOpacity].
   */
  public var lineOpacityTransition: Transition by mutableStateOf(initialLineOpacityTransition)
  /**
   *  Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var linePattern: ImageValue by mutableStateOf(initialLinePattern)
  /**
   *  The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of lineTranslate is in pixels.
   */
  public var lineTranslate: DoubleListValue by mutableStateOf(initialLineTranslate)
  /**
   *  Defines the transition of [lineTranslate].
   */
  public var lineTranslateTransition: Transition by mutableStateOf(initialLineTranslateTransition)
  /**
   *  Controls the frame of reference for `line-translate`. Default value: "map".
   */
  public var lineTranslateAnchor: LineTranslateAnchorValue by mutableStateOf(initialLineTranslateAnchor)
  /**
   *  The color to be used for rendering the trimmed line section that is defined by the `line-trim-offset` property. Default value: "transparent".
   */
  @MapboxExperimental
  public var lineTrimColor: ColorValue by mutableStateOf(initialLineTrimColor)
  /**
   *  Overrides applying of color theme for [lineTrimColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineTrimColorUseTheme: StringValue by mutableStateOf(initialLineTrimColorUseTheme)
  /**
   *  Defines the transition of [lineTrimColor].
   */
  @MapboxExperimental
  public var lineTrimColorTransition: Transition by mutableStateOf(initialLineTrimColorTransition)
  /**
   *  The fade range for the trim-start and trim-end points is defined by the `line-trim-offset` property. The first element of the array represents the fade range from the trim-start point toward the end of the line, while the second element defines the fade range from the trim-end point toward the beginning of the line. The fade result is achieved by interpolating between `line-trim-color` and the color specified by the `line-color` or the `line-gradient` property. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  @MapboxExperimental
  public var lineTrimFadeRange: DoubleRangeValue by mutableStateOf(initialLineTrimFadeRange)
  /**
   *  The line part between [trim-start, trim-end] will be painted using `line-trim-color,` which is transparent by default to produce a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0]. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  public var lineTrimOffset: DoubleListValue by mutableStateOf(initialLineTrimOffset)
  /**
   *  Stroke thickness. Default value: 1. Minimum value: 0. The unit of lineWidth is in pixels.
   */
  public var lineWidth: DoubleValue by mutableStateOf(initialLineWidth)
  /**
   *  Defines the transition of [lineWidth].
   */
  public var lineWidthTransition: Transition by mutableStateOf(initialLineWidthTransition)
  /**
   *  Whether this layer is displayed. Default value: "visible".
   */
  public var visibility: VisibilityValue by mutableStateOf(initialVisibility)
  /**
   *  The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: LongValue by mutableStateOf(initialMinZoom)
  /**
   *  The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: LongValue by mutableStateOf(initialMaxZoom)
  /**
   *  Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
   */
  public var sourceLayer: StringValue by mutableStateOf(initialSourceLayer)
  /**
   *  An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
   */
  public var filter: Filter by mutableStateOf(initialFilter)

  @Composable
  private fun UpdateLineCap(layerNode: LayerNode) {
    if (lineCap.notInitial) {
      layerNode.setProperty("line-cap", lineCap.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineCrossSlope(layerNode: LayerNode) {
    if (lineCrossSlope.notInitial) {
      layerNode.setProperty("line-cross-slope", lineCrossSlope.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineElevationReference(layerNode: LayerNode) {
    if (lineElevationReference.notInitial) {
      layerNode.setProperty("line-elevation-reference", lineElevationReference.value)
    }
  }
  @Composable
  private fun UpdateLineJoin(layerNode: LayerNode) {
    if (lineJoin.notInitial) {
      layerNode.setProperty("line-join", lineJoin.value)
    }
  }
  @Composable
  private fun UpdateLineMiterLimit(layerNode: LayerNode) {
    if (lineMiterLimit.notInitial) {
      layerNode.setProperty("line-miter-limit", lineMiterLimit.value)
    }
  }
  @Composable
  private fun UpdateLineRoundLimit(layerNode: LayerNode) {
    if (lineRoundLimit.notInitial) {
      layerNode.setProperty("line-round-limit", lineRoundLimit.value)
    }
  }
  @Composable
  private fun UpdateLineSortKey(layerNode: LayerNode) {
    if (lineSortKey.notInitial) {
      layerNode.setProperty("line-sort-key", lineSortKey.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineWidthUnit(layerNode: LayerNode) {
    if (lineWidthUnit.notInitial) {
      layerNode.setProperty("line-width-unit", lineWidthUnit.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineZOffset(layerNode: LayerNode) {
    if (lineZOffset.notInitial) {
      layerNode.setProperty("line-z-offset", lineZOffset.value)
    }
  }
  @Composable
  private fun UpdateLineBlur(layerNode: LayerNode) {
    if (lineBlur.notInitial) {
      layerNode.setProperty("line-blur", lineBlur.value)
    }
  }
  @Composable
  private fun UpdateLineBlurTransition(layerNode: LayerNode) {
    if (lineBlurTransition.notInitial) {
      layerNode.setProperty("line-blur-transition", lineBlurTransition.value)
    }
  }
  @Composable
  private fun UpdateLineBorderColor(layerNode: LayerNode) {
    if (lineBorderColor.notInitial) {
      layerNode.setProperty("line-border-color", lineBorderColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineBorderColorUseTheme(layerNode: LayerNode) {
    if (lineBorderColorUseTheme.notInitial) {
      layerNode.setProperty("line-border-color-use-theme", lineBorderColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateLineBorderColorTransition(layerNode: LayerNode) {
    if (lineBorderColorTransition.notInitial) {
      layerNode.setProperty("line-border-color-transition", lineBorderColorTransition.value)
    }
  }
  @Composable
  private fun UpdateLineBorderWidth(layerNode: LayerNode) {
    if (lineBorderWidth.notInitial) {
      layerNode.setProperty("line-border-width", lineBorderWidth.value)
    }
  }
  @Composable
  private fun UpdateLineBorderWidthTransition(layerNode: LayerNode) {
    if (lineBorderWidthTransition.notInitial) {
      layerNode.setProperty("line-border-width-transition", lineBorderWidthTransition.value)
    }
  }
  @Composable
  private fun UpdateLineColor(layerNode: LayerNode) {
    if (lineColor.notInitial) {
      layerNode.setProperty("line-color", lineColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineColorUseTheme(layerNode: LayerNode) {
    if (lineColorUseTheme.notInitial) {
      layerNode.setProperty("line-color-use-theme", lineColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateLineColorTransition(layerNode: LayerNode) {
    if (lineColorTransition.notInitial) {
      layerNode.setProperty("line-color-transition", lineColorTransition.value)
    }
  }
  @Composable
  private fun UpdateLineDasharray(layerNode: LayerNode) {
    if (lineDasharray.notInitial) {
      layerNode.setProperty("line-dasharray", lineDasharray.value)
    }
  }
  @Composable
  private fun UpdateLineDepthOcclusionFactor(layerNode: LayerNode) {
    if (lineDepthOcclusionFactor.notInitial) {
      layerNode.setProperty("line-depth-occlusion-factor", lineDepthOcclusionFactor.value)
    }
  }
  @Composable
  private fun UpdateLineDepthOcclusionFactorTransition(layerNode: LayerNode) {
    if (lineDepthOcclusionFactorTransition.notInitial) {
      layerNode.setProperty("line-depth-occlusion-factor-transition", lineDepthOcclusionFactorTransition.value)
    }
  }
  @Composable
  private fun UpdateLineEmissiveStrength(layerNode: LayerNode) {
    if (lineEmissiveStrength.notInitial) {
      layerNode.setProperty("line-emissive-strength", lineEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateLineEmissiveStrengthTransition(layerNode: LayerNode) {
    if (lineEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("line-emissive-strength-transition", lineEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateLineGapWidth(layerNode: LayerNode) {
    if (lineGapWidth.notInitial) {
      layerNode.setProperty("line-gap-width", lineGapWidth.value)
    }
  }
  @Composable
  private fun UpdateLineGapWidthTransition(layerNode: LayerNode) {
    if (lineGapWidthTransition.notInitial) {
      layerNode.setProperty("line-gap-width-transition", lineGapWidthTransition.value)
    }
  }
  @Composable
  private fun UpdateLineGradient(layerNode: LayerNode) {
    if (lineGradient.notInitial) {
      layerNode.setProperty("line-gradient", lineGradient.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineGradientUseTheme(layerNode: LayerNode) {
    if (lineGradientUseTheme.notInitial) {
      layerNode.setProperty("line-gradient-use-theme", lineGradientUseTheme.value)
    }
  }
  @Composable
  private fun UpdateLineOcclusionOpacity(layerNode: LayerNode) {
    if (lineOcclusionOpacity.notInitial) {
      layerNode.setProperty("line-occlusion-opacity", lineOcclusionOpacity.value)
    }
  }
  @Composable
  private fun UpdateLineOcclusionOpacityTransition(layerNode: LayerNode) {
    if (lineOcclusionOpacityTransition.notInitial) {
      layerNode.setProperty("line-occlusion-opacity-transition", lineOcclusionOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateLineOffset(layerNode: LayerNode) {
    if (lineOffset.notInitial) {
      layerNode.setProperty("line-offset", lineOffset.value)
    }
  }
  @Composable
  private fun UpdateLineOffsetTransition(layerNode: LayerNode) {
    if (lineOffsetTransition.notInitial) {
      layerNode.setProperty("line-offset-transition", lineOffsetTransition.value)
    }
  }
  @Composable
  private fun UpdateLineOpacity(layerNode: LayerNode) {
    if (lineOpacity.notInitial) {
      layerNode.setProperty("line-opacity", lineOpacity.value)
    }
  }
  @Composable
  private fun UpdateLineOpacityTransition(layerNode: LayerNode) {
    if (lineOpacityTransition.notInitial) {
      layerNode.setProperty("line-opacity-transition", lineOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateLinePattern(layerNode: LayerNode) {
    if (linePattern.notInitial) {
      linePattern.styleImage?.let {
        layerNode.addImage(it)
      }
      layerNode.setProperty("line-pattern", linePattern.value)
    }
  }
  @Composable
  private fun UpdateLineTranslate(layerNode: LayerNode) {
    if (lineTranslate.notInitial) {
      layerNode.setProperty("line-translate", lineTranslate.value)
    }
  }
  @Composable
  private fun UpdateLineTranslateTransition(layerNode: LayerNode) {
    if (lineTranslateTransition.notInitial) {
      layerNode.setProperty("line-translate-transition", lineTranslateTransition.value)
    }
  }
  @Composable
  private fun UpdateLineTranslateAnchor(layerNode: LayerNode) {
    if (lineTranslateAnchor.notInitial) {
      layerNode.setProperty("line-translate-anchor", lineTranslateAnchor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineTrimColor(layerNode: LayerNode) {
    if (lineTrimColor.notInitial) {
      layerNode.setProperty("line-trim-color", lineTrimColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineTrimColorUseTheme(layerNode: LayerNode) {
    if (lineTrimColorUseTheme.notInitial) {
      layerNode.setProperty("line-trim-color-use-theme", lineTrimColorUseTheme.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineTrimColorTransition(layerNode: LayerNode) {
    if (lineTrimColorTransition.notInitial) {
      layerNode.setProperty("line-trim-color-transition", lineTrimColorTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineTrimFadeRange(layerNode: LayerNode) {
    if (lineTrimFadeRange.notInitial) {
      layerNode.setProperty("line-trim-fade-range", lineTrimFadeRange.value)
    }
  }
  @Composable
  private fun UpdateLineTrimOffset(layerNode: LayerNode) {
    if (lineTrimOffset.notInitial) {
      layerNode.setProperty("line-trim-offset", lineTrimOffset.value)
    }
  }
  @Composable
  private fun UpdateLineWidth(layerNode: LayerNode) {
    if (lineWidth.notInitial) {
      layerNode.setProperty("line-width", lineWidth.value)
    }
  }
  @Composable
  private fun UpdateLineWidthTransition(layerNode: LayerNode) {
    if (lineWidthTransition.notInitial) {
      layerNode.setProperty("line-width-transition", lineWidthTransition.value)
    }
  }
  @Composable
  private fun UpdateVisibility(layerNode: LayerNode) {
    if (visibility.notInitial) {
      layerNode.setProperty("visibility", visibility.value)
    }
  }
  @Composable
  private fun UpdateMinZoom(layerNode: LayerNode) {
    if (minZoom.notInitial) {
      layerNode.setProperty("minzoom", minZoom.value)
    }
  }
  @Composable
  private fun UpdateMaxZoom(layerNode: LayerNode) {
    if (maxZoom.notInitial) {
      layerNode.setProperty("maxzoom", maxZoom.value)
    }
  }
  @Composable
  private fun UpdateSourceLayer(layerNode: LayerNode) {
    if (sourceLayer.notInitial) {
      layerNode.setProperty("source-layer", sourceLayer.value)
    }
  }
  @Composable
  private fun UpdateFilter(layerNode: LayerNode) {
    if (filter.notInitial) {
      layerNode.setProperty("filter", filter.value)
    }
  }

  @Composable
  internal fun UpdateProperties(layerNode: LayerNode) {
    UpdateLineCap(layerNode)
    UpdateLineCrossSlope(layerNode)
    UpdateLineElevationReference(layerNode)
    UpdateLineJoin(layerNode)
    UpdateLineMiterLimit(layerNode)
    UpdateLineRoundLimit(layerNode)
    UpdateLineSortKey(layerNode)
    UpdateLineWidthUnit(layerNode)
    UpdateLineZOffset(layerNode)
    UpdateLineBlur(layerNode)
    UpdateLineBlurTransition(layerNode)
    UpdateLineBorderColor(layerNode)
    UpdateLineBorderColorUseTheme(layerNode)
    UpdateLineBorderColorTransition(layerNode)
    UpdateLineBorderWidth(layerNode)
    UpdateLineBorderWidthTransition(layerNode)
    UpdateLineColor(layerNode)
    UpdateLineColorUseTheme(layerNode)
    UpdateLineColorTransition(layerNode)
    UpdateLineDasharray(layerNode)
    UpdateLineDepthOcclusionFactor(layerNode)
    UpdateLineDepthOcclusionFactorTransition(layerNode)
    UpdateLineEmissiveStrength(layerNode)
    UpdateLineEmissiveStrengthTransition(layerNode)
    UpdateLineGapWidth(layerNode)
    UpdateLineGapWidthTransition(layerNode)
    UpdateLineGradient(layerNode)
    UpdateLineGradientUseTheme(layerNode)
    UpdateLineOcclusionOpacity(layerNode)
    UpdateLineOcclusionOpacityTransition(layerNode)
    UpdateLineOffset(layerNode)
    UpdateLineOffsetTransition(layerNode)
    UpdateLineOpacity(layerNode)
    UpdateLineOpacityTransition(layerNode)
    UpdateLinePattern(layerNode)
    UpdateLineTranslate(layerNode)
    UpdateLineTranslateTransition(layerNode)
    UpdateLineTranslateAnchor(layerNode)
    UpdateLineTrimColor(layerNode)
    UpdateLineTrimColorUseTheme(layerNode)
    UpdateLineTrimColorTransition(layerNode)
    UpdateLineTrimFadeRange(layerNode)
    UpdateLineTrimOffset(layerNode)
    UpdateLineWidth(layerNode)
    UpdateLineWidthTransition(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.