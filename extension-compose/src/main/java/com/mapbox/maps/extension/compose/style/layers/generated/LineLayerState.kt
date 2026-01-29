// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ActionWhenNotInitial
import com.mapbox.maps.extension.compose.style.AddImageWhenNotInitial
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
public class LineLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialLineCap: LineCapValue,
  initialLineCrossSlope: DoubleValue,
  initialLineElevationGroundScale: DoubleValue,
  initialLineElevationGroundScaleTransition: Transition,
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
  initialLineCutoutFadeWidth: DoubleValue,
  initialLineCutoutFadeWidthTransition: Transition,
  initialLineCutoutOpacity: DoubleValue,
  initialLineCutoutOpacityTransition: Transition,
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
  initialLinePatternCrossFade: DoubleValue,
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
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialLineCap = LineCapValue.INITIAL,
    initialLineCrossSlope = DoubleValue.INITIAL,
    initialLineElevationGroundScale = DoubleValue.INITIAL,
    initialLineElevationGroundScaleTransition = Transition.INITIAL,
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
    initialLineCutoutFadeWidth = DoubleValue.INITIAL,
    initialLineCutoutFadeWidthTransition = Transition.INITIAL,
    initialLineCutoutOpacity = DoubleValue.INITIAL,
    initialLineCutoutOpacityTransition = Transition.INITIAL,
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
    initialLinePatternCrossFade = DoubleValue.INITIAL,
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

  private val lineCapState: MutableState<LineCapValue> = mutableStateOf(initialLineCap)
  /**
   *  The display of line endings. Default value: "butt".
   */
  public var lineCap: LineCapValue by lineCapState

  @MapboxExperimental
  private val lineCrossSlopeState: MutableState<DoubleValue> = mutableStateOf(initialLineCrossSlope)
  /**
   *  Defines the slope of an elevated line. A value of 0 creates a horizontal line. A value of 1 creates a vertical line. Other values are currently not supported. If undefined, the line follows the terrain slope. This is an experimental property with some known issues:  - Vertical lines don't support line caps  - `line-join: round` is not supported with this property
   */
  @MapboxExperimental
  public var lineCrossSlope: DoubleValue by lineCrossSlopeState

  private val lineElevationGroundScaleState: MutableState<DoubleValue> = mutableStateOf(initialLineElevationGroundScale)
  /**
   *  Controls how much the elevation of lines with `line-elevation-reference` set to `sea` scales with terrain exaggeration. A value of 0 keeps the line at a fixed altitude above sea level. A value of 1 scales the elevation proportionally with terrain exaggeration. Default value: 0. Value range: [0, 1]
   */
  public var lineElevationGroundScale: DoubleValue by lineElevationGroundScaleState

  private val lineElevationGroundScaleTransitionState: MutableState<Transition> = mutableStateOf(initialLineElevationGroundScaleTransition)
  /**
   *  Defines the transition of [lineElevationGroundScale].
   */
  public var lineElevationGroundScaleTransition: Transition by lineElevationGroundScaleTransitionState

  @MapboxExperimental
  private val lineElevationReferenceState: MutableState<LineElevationReferenceValue> = mutableStateOf(initialLineElevationReference)
  /**
   *  Selects the base of line-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   */
  @MapboxExperimental
  public var lineElevationReference: LineElevationReferenceValue by lineElevationReferenceState

  private val lineJoinState: MutableState<LineJoinValue> = mutableStateOf(initialLineJoin)
  /**
   *  The display of lines when joining. Default value: "miter".
   */
  public var lineJoin: LineJoinValue by lineJoinState

  private val lineMiterLimitState: MutableState<DoubleValue> = mutableStateOf(initialLineMiterLimit)
  /**
   *  Used to automatically convert miter joins to bevel joins for sharp angles. Default value: 2.
   */
  public var lineMiterLimit: DoubleValue by lineMiterLimitState

  private val lineRoundLimitState: MutableState<DoubleValue> = mutableStateOf(initialLineRoundLimit)
  /**
   *  Used to automatically convert round joins to miter joins for shallow angles. Default value: 1.05.
   */
  public var lineRoundLimit: DoubleValue by lineRoundLimitState

  private val lineSortKeyState: MutableState<DoubleValue> = mutableStateOf(initialLineSortKey)
  /**
   *  Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  public var lineSortKey: DoubleValue by lineSortKeyState

  @MapboxExperimental
  private val lineWidthUnitState: MutableState<LineWidthUnitValue> = mutableStateOf(initialLineWidthUnit)
  /**
   *  Selects the unit of line-width. The same unit is automatically used for line-blur and line-offset. Note: This is an experimental property and might be removed in a future release. Default value: "pixels".
   */
  @MapboxExperimental
  public var lineWidthUnit: LineWidthUnitValue by lineWidthUnitState

  @MapboxExperimental
  private val lineZOffsetState: MutableState<DoubleValue> = mutableStateOf(initialLineZOffset)
  /**
   *  Vertical offset from ground, in meters. Defaults to 0. This is an experimental property with some known issues:  - Not supported for globe projection at the moment  - Elevated line discontinuity is possible on tile borders with terrain enabled  - Rendering artifacts can happen near line joins and line caps depending on the line styling  - Rendering artifacts relating to `line-opacity` and `line-blur`  - Elevated line visibility is determined by layer order  - Z-fighting issues can happen with intersecting elevated lines  - Elevated lines don't cast shadows Default value: 0.
   */
  @MapboxExperimental
  public var lineZOffset: DoubleValue by lineZOffsetState

  private val lineBlurState: MutableState<DoubleValue> = mutableStateOf(initialLineBlur)
  /**
   *  Blur applied to the line, in pixels. Default value: 0. Minimum value: 0. The unit of lineBlur is in pixels.
   */
  public var lineBlur: DoubleValue by lineBlurState

  private val lineBlurTransitionState: MutableState<Transition> = mutableStateOf(initialLineBlurTransition)
  /**
   *  Defines the transition of [lineBlur].
   */
  public var lineBlurTransition: Transition by lineBlurTransitionState

  private val lineBorderColorState: MutableState<ColorValue> = mutableStateOf(initialLineBorderColor)
  /**
   *  The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
   */
  public var lineBorderColor: ColorValue by lineBorderColorState

  @MapboxExperimental
  private val lineBorderColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialLineBorderColorUseTheme)
  /**
   *  Overrides applying of color theme for [lineBorderColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineBorderColorUseTheme: StringValue by lineBorderColorUseThemeState

  private val lineBorderColorTransitionState: MutableState<Transition> = mutableStateOf(initialLineBorderColorTransition)
  /**
   *  Defines the transition of [lineBorderColor].
   */
  public var lineBorderColorTransition: Transition by lineBorderColorTransitionState

  private val lineBorderWidthState: MutableState<DoubleValue> = mutableStateOf(initialLineBorderWidth)
  /**
   *  The width of the line border. A value of zero means no border. Default value: 0. Minimum value: 0.
   */
  public var lineBorderWidth: DoubleValue by lineBorderWidthState

  private val lineBorderWidthTransitionState: MutableState<Transition> = mutableStateOf(initialLineBorderWidthTransition)
  /**
   *  Defines the transition of [lineBorderWidth].
   */
  public var lineBorderWidthTransition: Transition by lineBorderWidthTransitionState

  private val lineColorState: MutableState<ColorValue> = mutableStateOf(initialLineColor)
  /**
   *  The color with which the line will be drawn. Default value: "#000000".
   */
  public var lineColor: ColorValue by lineColorState

  @MapboxExperimental
  private val lineColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialLineColorUseTheme)
  /**
   *  Overrides applying of color theme for [lineColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineColorUseTheme: StringValue by lineColorUseThemeState

  private val lineColorTransitionState: MutableState<Transition> = mutableStateOf(initialLineColorTransition)
  /**
   *  Defines the transition of [lineColor].
   */
  public var lineColorTransition: Transition by lineColorTransitionState

  @MapboxExperimental
  private val lineCutoutFadeWidthState: MutableState<DoubleValue> = mutableStateOf(initialLineCutoutFadeWidth)
  /**
   *  The width of the cutout fade effect as a proportion of the cutout width. Default value: 0.4. Value range: [0, 1]
   */
  @MapboxExperimental
  public var lineCutoutFadeWidth: DoubleValue by lineCutoutFadeWidthState

  @MapboxExperimental
  private val lineCutoutFadeWidthTransitionState: MutableState<Transition> = mutableStateOf(initialLineCutoutFadeWidthTransition)
  /**
   *  Defines the transition of [lineCutoutFadeWidth].
   */
  @MapboxExperimental
  public var lineCutoutFadeWidthTransition: Transition by lineCutoutFadeWidthTransitionState

  @MapboxExperimental
  private val lineCutoutOpacityState: MutableState<DoubleValue> = mutableStateOf(initialLineCutoutOpacity)
  /**
   *  The opacity of the aboveground objects affected by the line cutout. Cutout for tunnels isn't affected by this property, If set to 0, the cutout is fully transparent. Cutout opacity should have the same value for all layers that specify it. If all layers don't have the same value, it is not specified which value is used. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  public var lineCutoutOpacity: DoubleValue by lineCutoutOpacityState

  @MapboxExperimental
  private val lineCutoutOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialLineCutoutOpacityTransition)
  /**
   *  Defines the transition of [lineCutoutOpacity].
   */
  @MapboxExperimental
  public var lineCutoutOpacityTransition: Transition by lineCutoutOpacityTransitionState

  private val lineDasharrayState: MutableState<DoubleListValue> = mutableStateOf(initialLineDasharray)
  /**
   *  Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. Minimum value: 0. The unit of lineDasharray is in line widths.
   */
  public var lineDasharray: DoubleListValue by lineDasharrayState

  private val lineDepthOcclusionFactorState: MutableState<DoubleValue> = mutableStateOf(initialLineDepthOcclusionFactor)
  /**
   *  This property is deprecated and replaced by line-occlusion-opacity. Value 0 disables occlusion, value 1 means fully occluded. Note: line-occlusion-opacity has the opposite effect - value 1 disables occlusion, value 0 means fully occluded. Default value: 1. Value range: [0, 1]
   */
  public var lineDepthOcclusionFactor: DoubleValue by lineDepthOcclusionFactorState

  private val lineDepthOcclusionFactorTransitionState: MutableState<Transition> = mutableStateOf(initialLineDepthOcclusionFactorTransition)
  /**
   *  Defines the transition of [lineDepthOcclusionFactor].
   */
  public var lineDepthOcclusionFactorTransition: Transition by lineDepthOcclusionFactorTransitionState

  private val lineEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialLineEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of lineEmissiveStrength is in intensity.
   */
  public var lineEmissiveStrength: DoubleValue by lineEmissiveStrengthState

  private val lineEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialLineEmissiveStrengthTransition)
  /**
   *  Defines the transition of [lineEmissiveStrength].
   */
  public var lineEmissiveStrengthTransition: Transition by lineEmissiveStrengthTransitionState

  private val lineGapWidthState: MutableState<DoubleValue> = mutableStateOf(initialLineGapWidth)
  /**
   *  Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. Default value: 0. Minimum value: 0. The unit of lineGapWidth is in pixels.
   */
  public var lineGapWidth: DoubleValue by lineGapWidthState

  private val lineGapWidthTransitionState: MutableState<Transition> = mutableStateOf(initialLineGapWidthTransition)
  /**
   *  Defines the transition of [lineGapWidth].
   */
  public var lineGapWidthTransition: Transition by lineGapWidthTransitionState

  private val lineGradientState: MutableState<ColorValue> = mutableStateOf(initialLineGradient)
  /**
   *  A gradient used to color a line feature at various distances along its length. Defined using a `step` or `interpolate` expression which outputs a color for each corresponding `line-progress` input value. `line-progress` is a percentage of the line feature's total length as measured on the webmercator projected coordinate plane (a `number` between `0` and `1`). Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
   */
  public var lineGradient: ColorValue by lineGradientState

  @MapboxExperimental
  private val lineGradientUseThemeState: MutableState<StringValue> = mutableStateOf(initialLineGradientUseTheme)
  /**
   *  Overrides applying of color theme for [lineGradient] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineGradientUseTheme: StringValue by lineGradientUseThemeState

  private val lineOcclusionOpacityState: MutableState<DoubleValue> = mutableStateOf(initialLineOcclusionOpacity)
  /**
   *  Opacity multiplier (multiplies line-opacity value) of the line part that is occluded by 3D objects. Value 0 hides occluded part, value 1 means the same opacity as non-occluded part. The property is not supported when `line-opacity` has data-driven styling. Default value: 0. Value range: [0, 1]
   */
  public var lineOcclusionOpacity: DoubleValue by lineOcclusionOpacityState

  private val lineOcclusionOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialLineOcclusionOpacityTransition)
  /**
   *  Defines the transition of [lineOcclusionOpacity].
   */
  public var lineOcclusionOpacityTransition: Transition by lineOcclusionOpacityTransitionState

  private val lineOffsetState: MutableState<DoubleValue> = mutableStateOf(initialLineOffset)
  /**
   *  The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. Default value: 0. The unit of lineOffset is in pixels.
   */
  public var lineOffset: DoubleValue by lineOffsetState

  private val lineOffsetTransitionState: MutableState<Transition> = mutableStateOf(initialLineOffsetTransition)
  /**
   *  Defines the transition of [lineOffset].
   */
  public var lineOffsetTransition: Transition by lineOffsetTransitionState

  private val lineOpacityState: MutableState<DoubleValue> = mutableStateOf(initialLineOpacity)
  /**
   *  The opacity at which the line will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var lineOpacity: DoubleValue by lineOpacityState

  private val lineOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialLineOpacityTransition)
  /**
   *  Defines the transition of [lineOpacity].
   */
  public var lineOpacityTransition: Transition by lineOpacityTransitionState

  private val linePatternState: MutableState<ImageValue> = mutableStateOf(initialLinePattern)
  /**
   *  Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var linePattern: ImageValue by linePatternState

  private val linePatternCrossFadeState: MutableState<DoubleValue> = mutableStateOf(initialLinePatternCrossFade)
  /**
   *  Controls the transition progress between the image variants of line-pattern. Zero means the first variant is used, one is the second, and in between they are blended together. Both images should be the same size and have the same type (either raster or vector). Default value: 0. Value range: [0, 1]
   */
  public var linePatternCrossFade: DoubleValue by linePatternCrossFadeState

  private val lineTranslateState: MutableState<DoubleListValue> = mutableStateOf(initialLineTranslate)
  /**
   *  The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of lineTranslate is in pixels.
   */
  public var lineTranslate: DoubleListValue by lineTranslateState

  private val lineTranslateTransitionState: MutableState<Transition> = mutableStateOf(initialLineTranslateTransition)
  /**
   *  Defines the transition of [lineTranslate].
   */
  public var lineTranslateTransition: Transition by lineTranslateTransitionState

  private val lineTranslateAnchorState: MutableState<LineTranslateAnchorValue> = mutableStateOf(initialLineTranslateAnchor)
  /**
   *  Controls the frame of reference for `line-translate`. Default value: "map".
   */
  public var lineTranslateAnchor: LineTranslateAnchorValue by lineTranslateAnchorState

  @MapboxExperimental
  private val lineTrimColorState: MutableState<ColorValue> = mutableStateOf(initialLineTrimColor)
  /**
   *  The color to be used for rendering the trimmed line section that is defined by the `line-trim-offset` property. Default value: "transparent".
   */
  @MapboxExperimental
  public var lineTrimColor: ColorValue by lineTrimColorState

  @MapboxExperimental
  private val lineTrimColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialLineTrimColorUseTheme)
  /**
   *  Overrides applying of color theme for [lineTrimColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var lineTrimColorUseTheme: StringValue by lineTrimColorUseThemeState

  @MapboxExperimental
  private val lineTrimColorTransitionState: MutableState<Transition> = mutableStateOf(initialLineTrimColorTransition)
  /**
   *  Defines the transition of [lineTrimColor].
   */
  @MapboxExperimental
  public var lineTrimColorTransition: Transition by lineTrimColorTransitionState

  @MapboxExperimental
  private val lineTrimFadeRangeState: MutableState<DoubleRangeValue> = mutableStateOf(initialLineTrimFadeRange)
  /**
   *  The fade range for the trim-start and trim-end points is defined by the `line-trim-offset` property. The first element of the array represents the fade range from the trim-start point toward the end of the line, while the second element defines the fade range from the trim-end point toward the beginning of the line. The fade result is achieved by interpolating between `line-trim-color` and the color specified by the `line-color` or the `line-gradient` property. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  @MapboxExperimental
  public var lineTrimFadeRange: DoubleRangeValue by lineTrimFadeRangeState

  private val lineTrimOffsetState: MutableState<DoubleListValue> = mutableStateOf(initialLineTrimOffset)
  /**
   *  The line part between [trim-start, trim-end] will be painted using `line-trim-color,` which is transparent by default to produce a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0]. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  public var lineTrimOffset: DoubleListValue by lineTrimOffsetState

  private val lineWidthState: MutableState<DoubleValue> = mutableStateOf(initialLineWidth)
  /**
   *  Stroke thickness. Default value: 1. Minimum value: 0. The unit of lineWidth is in pixels.
   */
  public var lineWidth: DoubleValue by lineWidthState

  private val lineWidthTransitionState: MutableState<Transition> = mutableStateOf(initialLineWidthTransition)
  /**
   *  Defines the transition of [lineWidth].
   */
  public var lineWidthTransition: Transition by lineWidthTransitionState

  private val visibilityState: MutableState<VisibilityValue> = mutableStateOf(initialVisibility)
  /**
   *  Whether this layer is displayed. Default value: "visible".
   */
  public var visibility: VisibilityValue by visibilityState

  private val minZoomState: MutableState<LongValue> = mutableStateOf(initialMinZoom)
  /**
   *  The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: LongValue by minZoomState

  private val maxZoomState: MutableState<LongValue> = mutableStateOf(initialMaxZoom)
  /**
   *  The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: LongValue by maxZoomState

  private val sourceLayerState: MutableState<StringValue> = mutableStateOf(initialSourceLayer)
  /**
   *  Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
   */
  public var sourceLayer: StringValue by sourceLayerState

  private val filterState: MutableState<Filter> = mutableStateOf(initialFilter)
  /**
   *  An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
   */
  public var filter: Filter by filterState

  @Composable
  @OptIn(MapboxExperimental::class)
  internal fun UpdateProperties(layerNode: LayerNode) {
    ActionWhenNotInitial(layerNode.setPropertyAction, lineCapState, "line-cap")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineCrossSlopeState, "line-cross-slope")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineElevationGroundScaleState, "line-elevation-ground-scale")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineElevationGroundScaleTransitionState, "line-elevation-ground-scale-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineElevationReferenceState, "line-elevation-reference")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineJoinState, "line-join")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineMiterLimitState, "line-miter-limit")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineRoundLimitState, "line-round-limit")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineSortKeyState, "line-sort-key")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineWidthUnitState, "line-width-unit")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineZOffsetState, "line-z-offset")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineBlurState, "line-blur")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineBlurTransitionState, "line-blur-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineBorderColorState, "line-border-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineBorderColorUseThemeState, "line-border-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineBorderColorTransitionState, "line-border-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineBorderWidthState, "line-border-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineBorderWidthTransitionState, "line-border-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineColorState, "line-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineColorUseThemeState, "line-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineColorTransitionState, "line-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineCutoutFadeWidthState, "line-cutout-fade-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineCutoutFadeWidthTransitionState, "line-cutout-fade-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineCutoutOpacityState, "line-cutout-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineCutoutOpacityTransitionState, "line-cutout-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineDasharrayState, "line-dasharray")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineDepthOcclusionFactorState, "line-depth-occlusion-factor")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineDepthOcclusionFactorTransitionState, "line-depth-occlusion-factor-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineEmissiveStrengthState, "line-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineEmissiveStrengthTransitionState, "line-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineGapWidthState, "line-gap-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineGapWidthTransitionState, "line-gap-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineGradientState, "line-gradient")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineGradientUseThemeState, "line-gradient-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineOcclusionOpacityState, "line-occlusion-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineOcclusionOpacityTransitionState, "line-occlusion-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineOffsetState, "line-offset")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineOffsetTransitionState, "line-offset-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineOpacityState, "line-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineOpacityTransitionState, "line-opacity-transition")
    AddImageWhenNotInitial(layerNode, linePatternState, "line-pattern")
    ActionWhenNotInitial(layerNode.setPropertyAction, linePatternCrossFadeState, "line-pattern-cross-fade")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTranslateState, "line-translate")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTranslateTransitionState, "line-translate-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTranslateAnchorState, "line-translate-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTrimColorState, "line-trim-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTrimColorUseThemeState, "line-trim-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTrimColorTransitionState, "line-trim-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTrimFadeRangeState, "line-trim-fade-range")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineTrimOffsetState, "line-trim-offset")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineWidthState, "line-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, lineWidthTransitionState, "line-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.