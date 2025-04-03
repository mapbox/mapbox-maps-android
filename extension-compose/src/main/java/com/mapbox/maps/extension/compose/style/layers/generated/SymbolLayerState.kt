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
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleRangeValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringListValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.FormattedValue
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [SymbolLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#symbol)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class SymbolLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialIconAllowOverlap: BooleanValue,
  initialIconAnchor: IconAnchorValue,
  initialIconIgnorePlacement: BooleanValue,
  initialIconImage: ImageValue,
  initialIconKeepUpright: BooleanValue,
  initialIconOffset: DoubleListValue,
  initialIconOptional: BooleanValue,
  initialIconPadding: DoubleValue,
  initialIconPitchAlignment: IconPitchAlignmentValue,
  initialIconRotate: DoubleValue,
  initialIconRotationAlignment: IconRotationAlignmentValue,
  initialIconSize: DoubleValue,
  initialIconSizeScaleRange: DoubleRangeValue,
  initialIconTextFit: IconTextFitValue,
  initialIconTextFitPadding: DoubleListValue,
  initialSymbolAvoidEdges: BooleanValue,
  initialSymbolElevationReference: SymbolElevationReferenceValue,
  initialSymbolPlacement: SymbolPlacementValue,
  initialSymbolSortKey: DoubleValue,
  initialSymbolSpacing: DoubleValue,
  initialSymbolZElevate: BooleanValue,
  initialSymbolZOrder: SymbolZOrderValue,
  initialTextAllowOverlap: BooleanValue,
  initialTextAnchor: TextAnchorValue,
  initialTextField: FormattedValue,
  initialTextFont: StringListValue,
  initialTextIgnorePlacement: BooleanValue,
  initialTextJustify: TextJustifyValue,
  initialTextKeepUpright: BooleanValue,
  initialTextLetterSpacing: DoubleValue,
  initialTextLineHeight: DoubleValue,
  initialTextMaxAngle: DoubleValue,
  initialTextMaxWidth: DoubleValue,
  initialTextOffset: DoubleListValue,
  initialTextOptional: BooleanValue,
  initialTextPadding: DoubleValue,
  initialTextPitchAlignment: TextPitchAlignmentValue,
  initialTextRadialOffset: DoubleValue,
  initialTextRotate: DoubleValue,
  initialTextRotationAlignment: TextRotationAlignmentValue,
  initialTextSize: DoubleValue,
  initialTextSizeScaleRange: DoubleRangeValue,
  initialTextTransform: TextTransformValue,
  initialTextVariableAnchor: TextVariableAnchorListValue,
  initialTextWritingMode: TextWritingModeListValue,
  initialIconColor: ColorValue,
  initialIconColorUseTheme: StringValue,
  initialIconColorTransition: Transition,
  initialIconColorSaturation: DoubleValue,
  initialIconColorSaturationTransition: Transition,
  initialIconEmissiveStrength: DoubleValue,
  initialIconEmissiveStrengthTransition: Transition,
  initialIconHaloBlur: DoubleValue,
  initialIconHaloBlurTransition: Transition,
  initialIconHaloColor: ColorValue,
  initialIconHaloColorUseTheme: StringValue,
  initialIconHaloColorTransition: Transition,
  initialIconHaloWidth: DoubleValue,
  initialIconHaloWidthTransition: Transition,
  initialIconImageCrossFade: DoubleValue,
  initialIconImageCrossFadeTransition: Transition,
  initialIconOcclusionOpacity: DoubleValue,
  initialIconOcclusionOpacityTransition: Transition,
  initialIconOpacity: DoubleValue,
  initialIconOpacityTransition: Transition,
  initialIconTranslate: DoubleListValue,
  initialIconTranslateTransition: Transition,
  initialIconTranslateAnchor: IconTranslateAnchorValue,
  initialSymbolZOffset: DoubleValue,
  initialSymbolZOffsetTransition: Transition,
  initialTextColor: ColorValue,
  initialTextColorUseTheme: StringValue,
  initialTextColorTransition: Transition,
  initialTextEmissiveStrength: DoubleValue,
  initialTextEmissiveStrengthTransition: Transition,
  initialTextHaloBlur: DoubleValue,
  initialTextHaloBlurTransition: Transition,
  initialTextHaloColor: ColorValue,
  initialTextHaloColorUseTheme: StringValue,
  initialTextHaloColorTransition: Transition,
  initialTextHaloWidth: DoubleValue,
  initialTextHaloWidthTransition: Transition,
  initialTextOcclusionOpacity: DoubleValue,
  initialTextOcclusionOpacityTransition: Transition,
  initialTextOpacity: DoubleValue,
  initialTextOpacityTransition: Transition,
  initialTextTranslate: DoubleListValue,
  initialTextTranslateTransition: Transition,
  initialTextTranslateAnchor: TextTranslateAnchorValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [SymbolLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialIconAllowOverlap = BooleanValue.INITIAL,
    initialIconAnchor = IconAnchorValue.INITIAL,
    initialIconIgnorePlacement = BooleanValue.INITIAL,
    initialIconImage = ImageValue.INITIAL,
    initialIconKeepUpright = BooleanValue.INITIAL,
    initialIconOffset = DoubleListValue.INITIAL,
    initialIconOptional = BooleanValue.INITIAL,
    initialIconPadding = DoubleValue.INITIAL,
    initialIconPitchAlignment = IconPitchAlignmentValue.INITIAL,
    initialIconRotate = DoubleValue.INITIAL,
    initialIconRotationAlignment = IconRotationAlignmentValue.INITIAL,
    initialIconSize = DoubleValue.INITIAL,
    initialIconSizeScaleRange = DoubleRangeValue.INITIAL,
    initialIconTextFit = IconTextFitValue.INITIAL,
    initialIconTextFitPadding = DoubleListValue.INITIAL,
    initialSymbolAvoidEdges = BooleanValue.INITIAL,
    initialSymbolElevationReference = SymbolElevationReferenceValue.INITIAL,
    initialSymbolPlacement = SymbolPlacementValue.INITIAL,
    initialSymbolSortKey = DoubleValue.INITIAL,
    initialSymbolSpacing = DoubleValue.INITIAL,
    initialSymbolZElevate = BooleanValue.INITIAL,
    initialSymbolZOrder = SymbolZOrderValue.INITIAL,
    initialTextAllowOverlap = BooleanValue.INITIAL,
    initialTextAnchor = TextAnchorValue.INITIAL,
    initialTextField = FormattedValue.INITIAL,
    initialTextFont = StringListValue.INITIAL,
    initialTextIgnorePlacement = BooleanValue.INITIAL,
    initialTextJustify = TextJustifyValue.INITIAL,
    initialTextKeepUpright = BooleanValue.INITIAL,
    initialTextLetterSpacing = DoubleValue.INITIAL,
    initialTextLineHeight = DoubleValue.INITIAL,
    initialTextMaxAngle = DoubleValue.INITIAL,
    initialTextMaxWidth = DoubleValue.INITIAL,
    initialTextOffset = DoubleListValue.INITIAL,
    initialTextOptional = BooleanValue.INITIAL,
    initialTextPadding = DoubleValue.INITIAL,
    initialTextPitchAlignment = TextPitchAlignmentValue.INITIAL,
    initialTextRadialOffset = DoubleValue.INITIAL,
    initialTextRotate = DoubleValue.INITIAL,
    initialTextRotationAlignment = TextRotationAlignmentValue.INITIAL,
    initialTextSize = DoubleValue.INITIAL,
    initialTextSizeScaleRange = DoubleRangeValue.INITIAL,
    initialTextTransform = TextTransformValue.INITIAL,
    initialTextVariableAnchor = TextVariableAnchorListValue.INITIAL,
    initialTextWritingMode = TextWritingModeListValue.INITIAL,
    initialIconColor = ColorValue.INITIAL,
    initialIconColorUseTheme = StringValue.INITIAL,
    initialIconColorTransition = Transition.INITIAL,
    initialIconColorSaturation = DoubleValue.INITIAL,
    initialIconColorSaturationTransition = Transition.INITIAL,
    initialIconEmissiveStrength = DoubleValue.INITIAL,
    initialIconEmissiveStrengthTransition = Transition.INITIAL,
    initialIconHaloBlur = DoubleValue.INITIAL,
    initialIconHaloBlurTransition = Transition.INITIAL,
    initialIconHaloColor = ColorValue.INITIAL,
    initialIconHaloColorUseTheme = StringValue.INITIAL,
    initialIconHaloColorTransition = Transition.INITIAL,
    initialIconHaloWidth = DoubleValue.INITIAL,
    initialIconHaloWidthTransition = Transition.INITIAL,
    initialIconImageCrossFade = DoubleValue.INITIAL,
    initialIconImageCrossFadeTransition = Transition.INITIAL,
    initialIconOcclusionOpacity = DoubleValue.INITIAL,
    initialIconOcclusionOpacityTransition = Transition.INITIAL,
    initialIconOpacity = DoubleValue.INITIAL,
    initialIconOpacityTransition = Transition.INITIAL,
    initialIconTranslate = DoubleListValue.INITIAL,
    initialIconTranslateTransition = Transition.INITIAL,
    initialIconTranslateAnchor = IconTranslateAnchorValue.INITIAL,
    initialSymbolZOffset = DoubleValue.INITIAL,
    initialSymbolZOffsetTransition = Transition.INITIAL,
    initialTextColor = ColorValue.INITIAL,
    initialTextColorUseTheme = StringValue.INITIAL,
    initialTextColorTransition = Transition.INITIAL,
    initialTextEmissiveStrength = DoubleValue.INITIAL,
    initialTextEmissiveStrengthTransition = Transition.INITIAL,
    initialTextHaloBlur = DoubleValue.INITIAL,
    initialTextHaloBlurTransition = Transition.INITIAL,
    initialTextHaloColor = ColorValue.INITIAL,
    initialTextHaloColorUseTheme = StringValue.INITIAL,
    initialTextHaloColorTransition = Transition.INITIAL,
    initialTextHaloWidth = DoubleValue.INITIAL,
    initialTextHaloWidthTransition = Transition.INITIAL,
    initialTextOcclusionOpacity = DoubleValue.INITIAL,
    initialTextOcclusionOpacityTransition = Transition.INITIAL,
    initialTextOpacity = DoubleValue.INITIAL,
    initialTextOpacityTransition = Transition.INITIAL,
    initialTextTranslate = DoubleListValue.INITIAL,
    initialTextTranslateTransition = Transition.INITIAL,
    initialTextTranslateAnchor = TextTranslateAnchorValue.INITIAL,
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

  private val iconAllowOverlapState: MutableState<BooleanValue> = mutableStateOf(initialIconAllowOverlap)
  /**
   *  If true, the icon will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  public var iconAllowOverlap: BooleanValue by iconAllowOverlapState

  private val iconAnchorState: MutableState<IconAnchorValue> = mutableStateOf(initialIconAnchor)
  /**
   *  Part of the icon placed closest to the anchor. Default value: "center".
   */
  public var iconAnchor: IconAnchorValue by iconAnchorState

  private val iconIgnorePlacementState: MutableState<BooleanValue> = mutableStateOf(initialIconIgnorePlacement)
  /**
   *  If true, other symbols can be visible even if they collide with the icon. Default value: false.
   */
  public var iconIgnorePlacement: BooleanValue by iconIgnorePlacementState

  private val iconImageState: MutableState<ImageValue> = mutableStateOf(initialIconImage)
  /**
   *  Name of image in sprite to use for drawing an image background.
   */
  public var iconImage: ImageValue by iconImageState

  private val iconKeepUprightState: MutableState<BooleanValue> = mutableStateOf(initialIconKeepUpright)
  /**
   *  If true, the icon may be flipped to prevent it from being rendered upside-down. Default value: false.
   */
  public var iconKeepUpright: BooleanValue by iconKeepUprightState

  private val iconOffsetState: MutableState<DoubleListValue> = mutableStateOf(initialIconOffset)
  /**
   *  Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up. Default value: [0,0].
   */
  public var iconOffset: DoubleListValue by iconOffsetState

  private val iconOptionalState: MutableState<BooleanValue> = mutableStateOf(initialIconOptional)
  /**
   *  If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not. Default value: false.
   */
  public var iconOptional: BooleanValue by iconOptionalState

  private val iconPaddingState: MutableState<DoubleValue> = mutableStateOf(initialIconPadding)
  /**
   *  Size of the additional area around the icon bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of iconPadding is in pixels.
   */
  public var iconPadding: DoubleValue by iconPaddingState

  private val iconPitchAlignmentState: MutableState<IconPitchAlignmentValue> = mutableStateOf(initialIconPitchAlignment)
  /**
   *  Orientation of icon when map is pitched. Default value: "auto".
   */
  public var iconPitchAlignment: IconPitchAlignmentValue by iconPitchAlignmentState

  private val iconRotateState: MutableState<DoubleValue> = mutableStateOf(initialIconRotate)
  /**
   *  Rotates the icon clockwise. Default value: 0. The unit of iconRotate is in degrees.
   */
  public var iconRotate: DoubleValue by iconRotateState

  private val iconRotationAlignmentState: MutableState<IconRotationAlignmentValue> = mutableStateOf(initialIconRotationAlignment)
  /**
   *  In combination with `symbol-placement`, determines the rotation behavior of icons. Default value: "auto".
   */
  public var iconRotationAlignment: IconRotationAlignmentValue by iconRotationAlignmentState

  private val iconSizeState: MutableState<DoubleValue> = mutableStateOf(initialIconSize)
  /**
   *  Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image. Default value: 1. Minimum value: 0. The unit of iconSize is in factor of the original icon size.
   */
  public var iconSize: DoubleValue by iconSizeState

  @MapboxExperimental
  private val iconSizeScaleRangeState: MutableState<DoubleRangeValue> = mutableStateOf(initialIconSizeScaleRange)
  /**
   *  Defines the minimum and maximum scaling factors for icon related properties like `icon-size`, `icon-halo-width`, `icon-halo-blur` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  public var iconSizeScaleRange: DoubleRangeValue by iconSizeScaleRangeState

  private val iconTextFitState: MutableState<IconTextFitValue> = mutableStateOf(initialIconTextFit)
  /**
   *  Scales the icon to fit around the associated text. Default value: "none".
   */
  public var iconTextFit: IconTextFitValue by iconTextFitState

  private val iconTextFitPaddingState: MutableState<DoubleListValue> = mutableStateOf(initialIconTextFitPadding)
  /**
   *  Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left. Default value: [0,0,0,0]. The unit of iconTextFitPadding is in pixels.
   */
  public var iconTextFitPadding: DoubleListValue by iconTextFitPaddingState

  private val symbolAvoidEdgesState: MutableState<BooleanValue> = mutableStateOf(initialSymbolAvoidEdges)
  /**
   *  If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries. Default value: false.
   */
  public var symbolAvoidEdges: BooleanValue by symbolAvoidEdgesState

  @MapboxExperimental
  private val symbolElevationReferenceState: MutableState<SymbolElevationReferenceValue> = mutableStateOf(initialSymbolElevationReference)
  /**
   *  Selects the base of symbol-elevation. Default value: "ground".
   */
  @MapboxExperimental
  public var symbolElevationReference: SymbolElevationReferenceValue by symbolElevationReferenceState

  private val symbolPlacementState: MutableState<SymbolPlacementValue> = mutableStateOf(initialSymbolPlacement)
  /**
   *  Label placement relative to its geometry. Default value: "point".
   */
  public var symbolPlacement: SymbolPlacementValue by symbolPlacementState

  private val symbolSortKeyState: MutableState<DoubleValue> = mutableStateOf(initialSymbolSortKey)
  /**
   *  Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  public var symbolSortKey: DoubleValue by symbolSortKeyState

  private val symbolSpacingState: MutableState<DoubleValue> = mutableStateOf(initialSymbolSpacing)
  /**
   *  Distance between two symbol anchors. Default value: 250. Minimum value: 1. The unit of symbolSpacing is in pixels.
   */
  public var symbolSpacing: DoubleValue by symbolSpacingState

  private val symbolZElevateState: MutableState<BooleanValue> = mutableStateOf(initialSymbolZElevate)
  /**
   *  Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when `fill-extrusion-height` is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or `symbol-sort-key` are applied. Default value: false.
   */
  public var symbolZElevate: BooleanValue by symbolZElevateState

  private val symbolZOrderState: MutableState<SymbolZOrderValue> = mutableStateOf(initialSymbolZOrder)
  /**
   *  Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`. Default value: "auto".
   */
  public var symbolZOrder: SymbolZOrderValue by symbolZOrderState

  private val textAllowOverlapState: MutableState<BooleanValue> = mutableStateOf(initialTextAllowOverlap)
  /**
   *  If true, the text will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  public var textAllowOverlap: BooleanValue by textAllowOverlapState

  private val textAnchorState: MutableState<TextAnchorValue> = mutableStateOf(initialTextAnchor)
  /**
   *  Part of the text placed closest to the anchor. Default value: "center".
   */
  public var textAnchor: TextAnchorValue by textAnchorState

  private val textFieldState: MutableState<FormattedValue> = mutableStateOf(initialTextField)
  /**
   *  Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored. Default value: "".
   */
  public var textField: FormattedValue by textFieldState

  private val textFontState: MutableState<StringListValue> = mutableStateOf(initialTextFont)
  /**
   *  Font stack to use for displaying text.
   */
  public var textFont: StringListValue by textFontState

  private val textIgnorePlacementState: MutableState<BooleanValue> = mutableStateOf(initialTextIgnorePlacement)
  /**
   *  If true, other symbols can be visible even if they collide with the text. Default value: false.
   */
  public var textIgnorePlacement: BooleanValue by textIgnorePlacementState

  private val textJustifyState: MutableState<TextJustifyValue> = mutableStateOf(initialTextJustify)
  /**
   *  Text justification options. Default value: "center".
   */
  public var textJustify: TextJustifyValue by textJustifyState

  private val textKeepUprightState: MutableState<BooleanValue> = mutableStateOf(initialTextKeepUpright)
  /**
   *  If true, the text may be flipped vertically to prevent it from being rendered upside-down. Default value: true.
   */
  public var textKeepUpright: BooleanValue by textKeepUprightState

  private val textLetterSpacingState: MutableState<DoubleValue> = mutableStateOf(initialTextLetterSpacing)
  /**
   *  Text tracking amount. Default value: 0. The unit of textLetterSpacing is in ems.
   */
  public var textLetterSpacing: DoubleValue by textLetterSpacingState

  private val textLineHeightState: MutableState<DoubleValue> = mutableStateOf(initialTextLineHeight)
  /**
   *  Text leading value for multi-line text. Default value: 1.2. The unit of textLineHeight is in ems.
   */
  public var textLineHeight: DoubleValue by textLineHeightState

  private val textMaxAngleState: MutableState<DoubleValue> = mutableStateOf(initialTextMaxAngle)
  /**
   *  Maximum angle change between adjacent characters. Default value: 45. The unit of textMaxAngle is in degrees.
   */
  public var textMaxAngle: DoubleValue by textMaxAngleState

  private val textMaxWidthState: MutableState<DoubleValue> = mutableStateOf(initialTextMaxWidth)
  /**
   *  The maximum line width for text wrapping. Default value: 10. Minimum value: 0. The unit of textMaxWidth is in ems.
   */
  public var textMaxWidth: DoubleValue by textMaxWidthState

  private val textOffsetState: MutableState<DoubleListValue> = mutableStateOf(initialTextOffset)
  /**
   *  Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. Default value: [0,0]. The unit of textOffset is in ems.
   */
  public var textOffset: DoubleListValue by textOffsetState

  private val textOptionalState: MutableState<BooleanValue> = mutableStateOf(initialTextOptional)
  /**
   *  If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not. Default value: false.
   */
  public var textOptional: BooleanValue by textOptionalState

  private val textPaddingState: MutableState<DoubleValue> = mutableStateOf(initialTextPadding)
  /**
   *  Size of the additional area around the text bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of textPadding is in pixels.
   */
  public var textPadding: DoubleValue by textPaddingState

  private val textPitchAlignmentState: MutableState<TextPitchAlignmentValue> = mutableStateOf(initialTextPitchAlignment)
  /**
   *  Orientation of text when map is pitched. Default value: "auto".
   */
  public var textPitchAlignment: TextPitchAlignmentValue by textPitchAlignmentState

  private val textRadialOffsetState: MutableState<DoubleValue> = mutableStateOf(initialTextRadialOffset)
  /**
   *  Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present. Default value: 0. The unit of textRadialOffset is in ems.
   */
  public var textRadialOffset: DoubleValue by textRadialOffsetState

  private val textRotateState: MutableState<DoubleValue> = mutableStateOf(initialTextRotate)
  /**
   *  Rotates the text clockwise. Default value: 0. The unit of textRotate is in degrees.
   */
  public var textRotate: DoubleValue by textRotateState

  private val textRotationAlignmentState: MutableState<TextRotationAlignmentValue> = mutableStateOf(initialTextRotationAlignment)
  /**
   *  In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text. Default value: "auto".
   */
  public var textRotationAlignment: TextRotationAlignmentValue by textRotationAlignmentState

  private val textSizeState: MutableState<DoubleValue> = mutableStateOf(initialTextSize)
  /**
   *  Font size. Default value: 16. Minimum value: 0. The unit of textSize is in pixels.
   */
  public var textSize: DoubleValue by textSizeState

  @MapboxExperimental
  private val textSizeScaleRangeState: MutableState<DoubleRangeValue> = mutableStateOf(initialTextSizeScaleRange)
  /**
   *  Defines the minimum and maximum scaling factors for text related properties like `text-size`, `text-max-width`, `text-halo-width`, `font-size` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  public var textSizeScaleRange: DoubleRangeValue by textSizeScaleRangeState

  private val textTransformState: MutableState<TextTransformValue> = mutableStateOf(initialTextTransform)
  /**
   *  Specifies how to capitalize text, similar to the CSS `text-transform` property. Default value: "none".
   */
  public var textTransform: TextTransformValue by textTransformState

  private val textVariableAnchorState: MutableState<TextVariableAnchorListValue> = mutableStateOf(initialTextVariableAnchor)
  /**
   *  To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   */
  public var textVariableAnchor: TextVariableAnchorListValue by textVariableAnchorState

  private val textWritingModeState: MutableState<TextWritingModeListValue> = mutableStateOf(initialTextWritingMode)
  /**
   *  The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  public var textWritingMode: TextWritingModeListValue by textWritingModeState

  private val iconColorState: MutableState<ColorValue> = mutableStateOf(initialIconColor)
  /**
   *  The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "#000000".
   */
  public var iconColor: ColorValue by iconColorState

  @MapboxExperimental
  private val iconColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialIconColorUseTheme)
  /**
   *  Overrides applying of color theme for [iconColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var iconColorUseTheme: StringValue by iconColorUseThemeState

  private val iconColorTransitionState: MutableState<Transition> = mutableStateOf(initialIconColorTransition)
  /**
   *  Defines the transition of [iconColor].
   */
  public var iconColorTransition: Transition by iconColorTransitionState

  private val iconColorSaturationState: MutableState<DoubleValue> = mutableStateOf(initialIconColorSaturation)
  /**
   *  Increase or reduce the saturation of the symbol icon. Default value: 0. Value range: [-1, 1]
   */
  public var iconColorSaturation: DoubleValue by iconColorSaturationState

  private val iconColorSaturationTransitionState: MutableState<Transition> = mutableStateOf(initialIconColorSaturationTransition)
  /**
   *  Defines the transition of [iconColorSaturation].
   */
  public var iconColorSaturationTransition: Transition by iconColorSaturationTransitionState

  private val iconEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialIconEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of iconEmissiveStrength is in intensity.
   */
  public var iconEmissiveStrength: DoubleValue by iconEmissiveStrengthState

  private val iconEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialIconEmissiveStrengthTransition)
  /**
   *  Defines the transition of [iconEmissiveStrength].
   */
  public var iconEmissiveStrengthTransition: Transition by iconEmissiveStrengthTransitionState

  private val iconHaloBlurState: MutableState<DoubleValue> = mutableStateOf(initialIconHaloBlur)
  /**
   *  Fade out the halo towards the outside. Default value: 0. Minimum value: 0. The unit of iconHaloBlur is in pixels.
   */
  public var iconHaloBlur: DoubleValue by iconHaloBlurState

  private val iconHaloBlurTransitionState: MutableState<Transition> = mutableStateOf(initialIconHaloBlurTransition)
  /**
   *  Defines the transition of [iconHaloBlur].
   */
  public var iconHaloBlurTransition: Transition by iconHaloBlurTransitionState

  private val iconHaloColorState: MutableState<ColorValue> = mutableStateOf(initialIconHaloColor)
  /**
   *  The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "rgba(0, 0, 0, 0)".
   */
  public var iconHaloColor: ColorValue by iconHaloColorState

  @MapboxExperimental
  private val iconHaloColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialIconHaloColorUseTheme)
  /**
   *  Overrides applying of color theme for [iconHaloColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var iconHaloColorUseTheme: StringValue by iconHaloColorUseThemeState

  private val iconHaloColorTransitionState: MutableState<Transition> = mutableStateOf(initialIconHaloColorTransition)
  /**
   *  Defines the transition of [iconHaloColor].
   */
  public var iconHaloColorTransition: Transition by iconHaloColorTransitionState

  private val iconHaloWidthState: MutableState<DoubleValue> = mutableStateOf(initialIconHaloWidth)
  /**
   *  Distance of halo to the icon outline. Default value: 0. Minimum value: 0. The unit of iconHaloWidth is in pixels.
   */
  public var iconHaloWidth: DoubleValue by iconHaloWidthState

  private val iconHaloWidthTransitionState: MutableState<Transition> = mutableStateOf(initialIconHaloWidthTransition)
  /**
   *  Defines the transition of [iconHaloWidth].
   */
  public var iconHaloWidthTransition: Transition by iconHaloWidthTransitionState

  private val iconImageCrossFadeState: MutableState<DoubleValue> = mutableStateOf(initialIconImageCrossFade)
  /**
   *  Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together. Default value: 0. Value range: [0, 1]
   */
  public var iconImageCrossFade: DoubleValue by iconImageCrossFadeState

  private val iconImageCrossFadeTransitionState: MutableState<Transition> = mutableStateOf(initialIconImageCrossFadeTransition)
  /**
   *  Defines the transition of [iconImageCrossFade].
   */
  public var iconImageCrossFadeTransition: Transition by iconImageCrossFadeTransitionState

  private val iconOcclusionOpacityState: MutableState<DoubleValue> = mutableStateOf(initialIconOcclusionOpacity)
  /**
   *  The opacity at which the icon will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var iconOcclusionOpacity: DoubleValue by iconOcclusionOpacityState

  private val iconOcclusionOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialIconOcclusionOpacityTransition)
  /**
   *  Defines the transition of [iconOcclusionOpacity].
   */
  public var iconOcclusionOpacityTransition: Transition by iconOcclusionOpacityTransitionState

  private val iconOpacityState: MutableState<DoubleValue> = mutableStateOf(initialIconOpacity)
  /**
   *  The opacity at which the icon will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var iconOpacity: DoubleValue by iconOpacityState

  private val iconOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialIconOpacityTransition)
  /**
   *  Defines the transition of [iconOpacity].
   */
  public var iconOpacityTransition: Transition by iconOpacityTransitionState

  private val iconTranslateState: MutableState<DoubleListValue> = mutableStateOf(initialIconTranslate)
  /**
   *  Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of iconTranslate is in pixels.
   */
  public var iconTranslate: DoubleListValue by iconTranslateState

  private val iconTranslateTransitionState: MutableState<Transition> = mutableStateOf(initialIconTranslateTransition)
  /**
   *  Defines the transition of [iconTranslate].
   */
  public var iconTranslateTransition: Transition by iconTranslateTransitionState

  private val iconTranslateAnchorState: MutableState<IconTranslateAnchorValue> = mutableStateOf(initialIconTranslateAnchor)
  /**
   *  Controls the frame of reference for `icon-translate`. Default value: "map".
   */
  public var iconTranslateAnchor: IconTranslateAnchorValue by iconTranslateAnchorState

  @MapboxExperimental
  private val symbolZOffsetState: MutableState<DoubleValue> = mutableStateOf(initialSymbolZOffset)
  /**
   *  Specifies an uniform elevation from the ground, in meters. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var symbolZOffset: DoubleValue by symbolZOffsetState

  @MapboxExperimental
  private val symbolZOffsetTransitionState: MutableState<Transition> = mutableStateOf(initialSymbolZOffsetTransition)
  /**
   *  Defines the transition of [symbolZOffset].
   */
  @MapboxExperimental
  public var symbolZOffsetTransition: Transition by symbolZOffsetTransitionState

  private val textColorState: MutableState<ColorValue> = mutableStateOf(initialTextColor)
  /**
   *  The color with which the text will be drawn. Default value: "#000000".
   */
  public var textColor: ColorValue by textColorState

  @MapboxExperimental
  private val textColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialTextColorUseTheme)
  /**
   *  Overrides applying of color theme for [textColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var textColorUseTheme: StringValue by textColorUseThemeState

  private val textColorTransitionState: MutableState<Transition> = mutableStateOf(initialTextColorTransition)
  /**
   *  Defines the transition of [textColor].
   */
  public var textColorTransition: Transition by textColorTransitionState

  private val textEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialTextEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of textEmissiveStrength is in intensity.
   */
  public var textEmissiveStrength: DoubleValue by textEmissiveStrengthState

  private val textEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialTextEmissiveStrengthTransition)
  /**
   *  Defines the transition of [textEmissiveStrength].
   */
  public var textEmissiveStrengthTransition: Transition by textEmissiveStrengthTransitionState

  private val textHaloBlurState: MutableState<DoubleValue> = mutableStateOf(initialTextHaloBlur)
  /**
   *  The halo's fadeout distance towards the outside. Default value: 0. Minimum value: 0. The unit of textHaloBlur is in pixels.
   */
  public var textHaloBlur: DoubleValue by textHaloBlurState

  private val textHaloBlurTransitionState: MutableState<Transition> = mutableStateOf(initialTextHaloBlurTransition)
  /**
   *  Defines the transition of [textHaloBlur].
   */
  public var textHaloBlurTransition: Transition by textHaloBlurTransitionState

  private val textHaloColorState: MutableState<ColorValue> = mutableStateOf(initialTextHaloColor)
  /**
   *  The color of the text's halo, which helps it stand out from backgrounds. Default value: "rgba(0, 0, 0, 0)".
   */
  public var textHaloColor: ColorValue by textHaloColorState

  @MapboxExperimental
  private val textHaloColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialTextHaloColorUseTheme)
  /**
   *  Overrides applying of color theme for [textHaloColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var textHaloColorUseTheme: StringValue by textHaloColorUseThemeState

  private val textHaloColorTransitionState: MutableState<Transition> = mutableStateOf(initialTextHaloColorTransition)
  /**
   *  Defines the transition of [textHaloColor].
   */
  public var textHaloColorTransition: Transition by textHaloColorTransitionState

  private val textHaloWidthState: MutableState<DoubleValue> = mutableStateOf(initialTextHaloWidth)
  /**
   *  Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. Default value: 0. Minimum value: 0. The unit of textHaloWidth is in pixels.
   */
  public var textHaloWidth: DoubleValue by textHaloWidthState

  private val textHaloWidthTransitionState: MutableState<Transition> = mutableStateOf(initialTextHaloWidthTransition)
  /**
   *  Defines the transition of [textHaloWidth].
   */
  public var textHaloWidthTransition: Transition by textHaloWidthTransitionState

  private val textOcclusionOpacityState: MutableState<DoubleValue> = mutableStateOf(initialTextOcclusionOpacity)
  /**
   *  The opacity at which the text will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var textOcclusionOpacity: DoubleValue by textOcclusionOpacityState

  private val textOcclusionOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialTextOcclusionOpacityTransition)
  /**
   *  Defines the transition of [textOcclusionOpacity].
   */
  public var textOcclusionOpacityTransition: Transition by textOcclusionOpacityTransitionState

  private val textOpacityState: MutableState<DoubleValue> = mutableStateOf(initialTextOpacity)
  /**
   *  The opacity at which the text will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var textOpacity: DoubleValue by textOpacityState

  private val textOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialTextOpacityTransition)
  /**
   *  Defines the transition of [textOpacity].
   */
  public var textOpacityTransition: Transition by textOpacityTransitionState

  private val textTranslateState: MutableState<DoubleListValue> = mutableStateOf(initialTextTranslate)
  /**
   *  Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of textTranslate is in pixels.
   */
  public var textTranslate: DoubleListValue by textTranslateState

  private val textTranslateTransitionState: MutableState<Transition> = mutableStateOf(initialTextTranslateTransition)
  /**
   *  Defines the transition of [textTranslate].
   */
  public var textTranslateTransition: Transition by textTranslateTransitionState

  private val textTranslateAnchorState: MutableState<TextTranslateAnchorValue> = mutableStateOf(initialTextTranslateAnchor)
  /**
   *  Controls the frame of reference for `text-translate`. Default value: "map".
   */
  public var textTranslateAnchor: TextTranslateAnchorValue by textTranslateAnchorState

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
    ActionWhenNotInitial(layerNode.setPropertyAction, iconAllowOverlapState, "icon-allow-overlap")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconAnchorState, "icon-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconIgnorePlacementState, "icon-ignore-placement")
    AddImageWhenNotInitial(layerNode, iconImageState, "icon-image")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconKeepUprightState, "icon-keep-upright")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconOffsetState, "icon-offset")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconOptionalState, "icon-optional")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconPaddingState, "icon-padding")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconPitchAlignmentState, "icon-pitch-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconRotateState, "icon-rotate")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconRotationAlignmentState, "icon-rotation-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconSizeState, "icon-size")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconSizeScaleRangeState, "icon-size-scale-range")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconTextFitState, "icon-text-fit")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconTextFitPaddingState, "icon-text-fit-padding")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolAvoidEdgesState, "symbol-avoid-edges")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolElevationReferenceState, "symbol-elevation-reference")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolPlacementState, "symbol-placement")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolSortKeyState, "symbol-sort-key")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolSpacingState, "symbol-spacing")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolZElevateState, "symbol-z-elevate")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolZOrderState, "symbol-z-order")
    ActionWhenNotInitial(layerNode.setPropertyAction, textAllowOverlapState, "text-allow-overlap")
    ActionWhenNotInitial(layerNode.setPropertyAction, textAnchorState, "text-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, textFieldState, "text-field")
    ActionWhenNotInitial(layerNode.setPropertyAction, textFontState, "text-font")
    ActionWhenNotInitial(layerNode.setPropertyAction, textIgnorePlacementState, "text-ignore-placement")
    ActionWhenNotInitial(layerNode.setPropertyAction, textJustifyState, "text-justify")
    ActionWhenNotInitial(layerNode.setPropertyAction, textKeepUprightState, "text-keep-upright")
    ActionWhenNotInitial(layerNode.setPropertyAction, textLetterSpacingState, "text-letter-spacing")
    ActionWhenNotInitial(layerNode.setPropertyAction, textLineHeightState, "text-line-height")
    ActionWhenNotInitial(layerNode.setPropertyAction, textMaxAngleState, "text-max-angle")
    ActionWhenNotInitial(layerNode.setPropertyAction, textMaxWidthState, "text-max-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, textOffsetState, "text-offset")
    ActionWhenNotInitial(layerNode.setPropertyAction, textOptionalState, "text-optional")
    ActionWhenNotInitial(layerNode.setPropertyAction, textPaddingState, "text-padding")
    ActionWhenNotInitial(layerNode.setPropertyAction, textPitchAlignmentState, "text-pitch-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, textRadialOffsetState, "text-radial-offset")
    ActionWhenNotInitial(layerNode.setPropertyAction, textRotateState, "text-rotate")
    ActionWhenNotInitial(layerNode.setPropertyAction, textRotationAlignmentState, "text-rotation-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, textSizeState, "text-size")
    ActionWhenNotInitial(layerNode.setPropertyAction, textSizeScaleRangeState, "text-size-scale-range")
    ActionWhenNotInitial(layerNode.setPropertyAction, textTransformState, "text-transform")
    ActionWhenNotInitial(layerNode.setPropertyAction, textVariableAnchorState, "text-variable-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, textWritingModeState, "text-writing-mode")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconColorState, "icon-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconColorUseThemeState, "icon-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconColorTransitionState, "icon-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconColorSaturationState, "icon-color-saturation")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconColorSaturationTransitionState, "icon-color-saturation-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconEmissiveStrengthState, "icon-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconEmissiveStrengthTransitionState, "icon-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconHaloBlurState, "icon-halo-blur")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconHaloBlurTransitionState, "icon-halo-blur-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconHaloColorState, "icon-halo-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconHaloColorUseThemeState, "icon-halo-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconHaloColorTransitionState, "icon-halo-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconHaloWidthState, "icon-halo-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconHaloWidthTransitionState, "icon-halo-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconImageCrossFadeState, "icon-image-cross-fade")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconImageCrossFadeTransitionState, "icon-image-cross-fade-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconOcclusionOpacityState, "icon-occlusion-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconOcclusionOpacityTransitionState, "icon-occlusion-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconOpacityState, "icon-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconOpacityTransitionState, "icon-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconTranslateState, "icon-translate")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconTranslateTransitionState, "icon-translate-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, iconTranslateAnchorState, "icon-translate-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolZOffsetState, "symbol-z-offset")
    ActionWhenNotInitial(layerNode.setPropertyAction, symbolZOffsetTransitionState, "symbol-z-offset-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textColorState, "text-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, textColorUseThemeState, "text-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, textColorTransitionState, "text-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textEmissiveStrengthState, "text-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, textEmissiveStrengthTransitionState, "text-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textHaloBlurState, "text-halo-blur")
    ActionWhenNotInitial(layerNode.setPropertyAction, textHaloBlurTransitionState, "text-halo-blur-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textHaloColorState, "text-halo-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, textHaloColorUseThemeState, "text-halo-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, textHaloColorTransitionState, "text-halo-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textHaloWidthState, "text-halo-width")
    ActionWhenNotInitial(layerNode.setPropertyAction, textHaloWidthTransitionState, "text-halo-width-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textOcclusionOpacityState, "text-occlusion-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, textOcclusionOpacityTransitionState, "text-occlusion-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textOpacityState, "text-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, textOpacityTransitionState, "text-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textTranslateState, "text-translate")
    ActionWhenNotInitial(layerNode.setPropertyAction, textTranslateTransitionState, "text-translate-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, textTranslateAnchorState, "text-translate-anchor")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.