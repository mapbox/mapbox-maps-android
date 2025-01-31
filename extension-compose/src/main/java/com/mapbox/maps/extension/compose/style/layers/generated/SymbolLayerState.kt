// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
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
public class SymbolLayerState private constructor(
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

  /**
   *  If true, the icon will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  public var iconAllowOverlap: BooleanValue by mutableStateOf(initialIconAllowOverlap)
  /**
   *  Part of the icon placed closest to the anchor. Default value: "center".
   */
  public var iconAnchor: IconAnchorValue by mutableStateOf(initialIconAnchor)
  /**
   *  If true, other symbols can be visible even if they collide with the icon. Default value: false.
   */
  public var iconIgnorePlacement: BooleanValue by mutableStateOf(initialIconIgnorePlacement)
  /**
   *  Name of image in sprite to use for drawing an image background.
   */
  public var iconImage: ImageValue by mutableStateOf(initialIconImage)
  /**
   *  If true, the icon may be flipped to prevent it from being rendered upside-down. Default value: false.
   */
  public var iconKeepUpright: BooleanValue by mutableStateOf(initialIconKeepUpright)
  /**
   *  Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up. Default value: [0,0].
   */
  public var iconOffset: DoubleListValue by mutableStateOf(initialIconOffset)
  /**
   *  If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not. Default value: false.
   */
  public var iconOptional: BooleanValue by mutableStateOf(initialIconOptional)
  /**
   *  Size of the additional area around the icon bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of iconPadding is in pixels.
   */
  public var iconPadding: DoubleValue by mutableStateOf(initialIconPadding)
  /**
   *  Orientation of icon when map is pitched. Default value: "auto".
   */
  public var iconPitchAlignment: IconPitchAlignmentValue by mutableStateOf(initialIconPitchAlignment)
  /**
   *  Rotates the icon clockwise. Default value: 0. The unit of iconRotate is in degrees.
   */
  public var iconRotate: DoubleValue by mutableStateOf(initialIconRotate)
  /**
   *  In combination with `symbol-placement`, determines the rotation behavior of icons. Default value: "auto".
   */
  public var iconRotationAlignment: IconRotationAlignmentValue by mutableStateOf(initialIconRotationAlignment)
  /**
   *  Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image. Default value: 1. Minimum value: 0. The unit of iconSize is in factor of the original icon size.
   */
  public var iconSize: DoubleValue by mutableStateOf(initialIconSize)
  /**
   *  Defines the minimum and maximum scaling factors for icon related properties like `icon-size`, `icon-halo-width`, `icon-halo-blur` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  public var iconSizeScaleRange: DoubleRangeValue by mutableStateOf(initialIconSizeScaleRange)
  /**
   *  Scales the icon to fit around the associated text. Default value: "none".
   */
  public var iconTextFit: IconTextFitValue by mutableStateOf(initialIconTextFit)
  /**
   *  Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left. Default value: [0,0,0,0]. The unit of iconTextFitPadding is in pixels.
   */
  public var iconTextFitPadding: DoubleListValue by mutableStateOf(initialIconTextFitPadding)
  /**
   *  If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries. Default value: false.
   */
  public var symbolAvoidEdges: BooleanValue by mutableStateOf(initialSymbolAvoidEdges)
  /**
   *  Selects the base of symbol-elevation. Default value: "ground".
   */
  @MapboxExperimental
  public var symbolElevationReference: SymbolElevationReferenceValue by mutableStateOf(initialSymbolElevationReference)
  /**
   *  Label placement relative to its geometry. Default value: "point".
   */
  public var symbolPlacement: SymbolPlacementValue by mutableStateOf(initialSymbolPlacement)
  /**
   *  Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  public var symbolSortKey: DoubleValue by mutableStateOf(initialSymbolSortKey)
  /**
   *  Distance between two symbol anchors. Default value: 250. Minimum value: 1. The unit of symbolSpacing is in pixels.
   */
  public var symbolSpacing: DoubleValue by mutableStateOf(initialSymbolSpacing)
  /**
   *  Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when `fill-extrusion-height` is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or `symbol-sort-key` are applied. Default value: false.
   */
  public var symbolZElevate: BooleanValue by mutableStateOf(initialSymbolZElevate)
  /**
   *  Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`. Default value: "auto".
   */
  public var symbolZOrder: SymbolZOrderValue by mutableStateOf(initialSymbolZOrder)
  /**
   *  If true, the text will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  public var textAllowOverlap: BooleanValue by mutableStateOf(initialTextAllowOverlap)
  /**
   *  Part of the text placed closest to the anchor. Default value: "center".
   */
  public var textAnchor: TextAnchorValue by mutableStateOf(initialTextAnchor)
  /**
   *  Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored. Default value: "".
   */
  public var textField: FormattedValue by mutableStateOf(initialTextField)
  /**
   *  Font stack to use for displaying text.
   */
  public var textFont: StringListValue by mutableStateOf(initialTextFont)
  /**
   *  If true, other symbols can be visible even if they collide with the text. Default value: false.
   */
  public var textIgnorePlacement: BooleanValue by mutableStateOf(initialTextIgnorePlacement)
  /**
   *  Text justification options. Default value: "center".
   */
  public var textJustify: TextJustifyValue by mutableStateOf(initialTextJustify)
  /**
   *  If true, the text may be flipped vertically to prevent it from being rendered upside-down. Default value: true.
   */
  public var textKeepUpright: BooleanValue by mutableStateOf(initialTextKeepUpright)
  /**
   *  Text tracking amount. Default value: 0. The unit of textLetterSpacing is in ems.
   */
  public var textLetterSpacing: DoubleValue by mutableStateOf(initialTextLetterSpacing)
  /**
   *  Text leading value for multi-line text. Default value: 1.2. The unit of textLineHeight is in ems.
   */
  public var textLineHeight: DoubleValue by mutableStateOf(initialTextLineHeight)
  /**
   *  Maximum angle change between adjacent characters. Default value: 45. The unit of textMaxAngle is in degrees.
   */
  public var textMaxAngle: DoubleValue by mutableStateOf(initialTextMaxAngle)
  /**
   *  The maximum line width for text wrapping. Default value: 10. Minimum value: 0. The unit of textMaxWidth is in ems.
   */
  public var textMaxWidth: DoubleValue by mutableStateOf(initialTextMaxWidth)
  /**
   *  Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. Default value: [0,0]. The unit of textOffset is in ems.
   */
  public var textOffset: DoubleListValue by mutableStateOf(initialTextOffset)
  /**
   *  If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not. Default value: false.
   */
  public var textOptional: BooleanValue by mutableStateOf(initialTextOptional)
  /**
   *  Size of the additional area around the text bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of textPadding is in pixels.
   */
  public var textPadding: DoubleValue by mutableStateOf(initialTextPadding)
  /**
   *  Orientation of text when map is pitched. Default value: "auto".
   */
  public var textPitchAlignment: TextPitchAlignmentValue by mutableStateOf(initialTextPitchAlignment)
  /**
   *  Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present. Default value: 0. The unit of textRadialOffset is in ems.
   */
  public var textRadialOffset: DoubleValue by mutableStateOf(initialTextRadialOffset)
  /**
   *  Rotates the text clockwise. Default value: 0. The unit of textRotate is in degrees.
   */
  public var textRotate: DoubleValue by mutableStateOf(initialTextRotate)
  /**
   *  In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text. Default value: "auto".
   */
  public var textRotationAlignment: TextRotationAlignmentValue by mutableStateOf(initialTextRotationAlignment)
  /**
   *  Font size. Default value: 16. Minimum value: 0. The unit of textSize is in pixels.
   */
  public var textSize: DoubleValue by mutableStateOf(initialTextSize)
  /**
   *  Defines the minimum and maximum scaling factors for text related properties like `text-size`, `text-max-width`, `text-halo-width`, `font-size` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  public var textSizeScaleRange: DoubleRangeValue by mutableStateOf(initialTextSizeScaleRange)
  /**
   *  Specifies how to capitalize text, similar to the CSS `text-transform` property. Default value: "none".
   */
  public var textTransform: TextTransformValue by mutableStateOf(initialTextTransform)
  /**
   *  To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   */
  public var textVariableAnchor: TextVariableAnchorListValue by mutableStateOf(initialTextVariableAnchor)
  /**
   *  The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  public var textWritingMode: TextWritingModeListValue by mutableStateOf(initialTextWritingMode)
  /**
   *  The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "#000000".
   */
  public var iconColor: ColorValue by mutableStateOf(initialIconColor)
  /**
   *  Overrides applying of color theme for [iconColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var iconColorUseTheme: StringValue by mutableStateOf(initialIconColorUseTheme)
  /**
   *  Defines the transition of [iconColor].
   */
  public var iconColorTransition: Transition by mutableStateOf(initialIconColorTransition)
  /**
   *  Increase or reduce the saturation of the symbol icon. Default value: 0. Value range: [-1, 1]
   */
  public var iconColorSaturation: DoubleValue by mutableStateOf(initialIconColorSaturation)
  /**
   *  Defines the transition of [iconColorSaturation].
   */
  public var iconColorSaturationTransition: Transition by mutableStateOf(initialIconColorSaturationTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of iconEmissiveStrength is in intensity.
   */
  public var iconEmissiveStrength: DoubleValue by mutableStateOf(initialIconEmissiveStrength)
  /**
   *  Defines the transition of [iconEmissiveStrength].
   */
  public var iconEmissiveStrengthTransition: Transition by mutableStateOf(initialIconEmissiveStrengthTransition)
  /**
   *  Fade out the halo towards the outside. Default value: 0. Minimum value: 0. The unit of iconHaloBlur is in pixels.
   */
  public var iconHaloBlur: DoubleValue by mutableStateOf(initialIconHaloBlur)
  /**
   *  Defines the transition of [iconHaloBlur].
   */
  public var iconHaloBlurTransition: Transition by mutableStateOf(initialIconHaloBlurTransition)
  /**
   *  The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "rgba(0, 0, 0, 0)".
   */
  public var iconHaloColor: ColorValue by mutableStateOf(initialIconHaloColor)
  /**
   *  Overrides applying of color theme for [iconHaloColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var iconHaloColorUseTheme: StringValue by mutableStateOf(initialIconHaloColorUseTheme)
  /**
   *  Defines the transition of [iconHaloColor].
   */
  public var iconHaloColorTransition: Transition by mutableStateOf(initialIconHaloColorTransition)
  /**
   *  Distance of halo to the icon outline. Default value: 0. Minimum value: 0. The unit of iconHaloWidth is in pixels.
   */
  public var iconHaloWidth: DoubleValue by mutableStateOf(initialIconHaloWidth)
  /**
   *  Defines the transition of [iconHaloWidth].
   */
  public var iconHaloWidthTransition: Transition by mutableStateOf(initialIconHaloWidthTransition)
  /**
   *  Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together. Default value: 0. Value range: [0, 1]
   */
  public var iconImageCrossFade: DoubleValue by mutableStateOf(initialIconImageCrossFade)
  /**
   *  Defines the transition of [iconImageCrossFade].
   */
  public var iconImageCrossFadeTransition: Transition by mutableStateOf(initialIconImageCrossFadeTransition)
  /**
   *  The opacity at which the icon will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var iconOcclusionOpacity: DoubleValue by mutableStateOf(initialIconOcclusionOpacity)
  /**
   *  Defines the transition of [iconOcclusionOpacity].
   */
  public var iconOcclusionOpacityTransition: Transition by mutableStateOf(initialIconOcclusionOpacityTransition)
  /**
   *  The opacity at which the icon will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var iconOpacity: DoubleValue by mutableStateOf(initialIconOpacity)
  /**
   *  Defines the transition of [iconOpacity].
   */
  public var iconOpacityTransition: Transition by mutableStateOf(initialIconOpacityTransition)
  /**
   *  Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of iconTranslate is in pixels.
   */
  public var iconTranslate: DoubleListValue by mutableStateOf(initialIconTranslate)
  /**
   *  Defines the transition of [iconTranslate].
   */
  public var iconTranslateTransition: Transition by mutableStateOf(initialIconTranslateTransition)
  /**
   *  Controls the frame of reference for `icon-translate`. Default value: "map".
   */
  public var iconTranslateAnchor: IconTranslateAnchorValue by mutableStateOf(initialIconTranslateAnchor)
  /**
   *  Specifies an uniform elevation from the ground, in meters. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var symbolZOffset: DoubleValue by mutableStateOf(initialSymbolZOffset)
  /**
   *  Defines the transition of [symbolZOffset].
   */
  @MapboxExperimental
  public var symbolZOffsetTransition: Transition by mutableStateOf(initialSymbolZOffsetTransition)
  /**
   *  The color with which the text will be drawn. Default value: "#000000".
   */
  public var textColor: ColorValue by mutableStateOf(initialTextColor)
  /**
   *  Overrides applying of color theme for [textColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var textColorUseTheme: StringValue by mutableStateOf(initialTextColorUseTheme)
  /**
   *  Defines the transition of [textColor].
   */
  public var textColorTransition: Transition by mutableStateOf(initialTextColorTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of textEmissiveStrength is in intensity.
   */
  public var textEmissiveStrength: DoubleValue by mutableStateOf(initialTextEmissiveStrength)
  /**
   *  Defines the transition of [textEmissiveStrength].
   */
  public var textEmissiveStrengthTransition: Transition by mutableStateOf(initialTextEmissiveStrengthTransition)
  /**
   *  The halo's fadeout distance towards the outside. Default value: 0. Minimum value: 0. The unit of textHaloBlur is in pixels.
   */
  public var textHaloBlur: DoubleValue by mutableStateOf(initialTextHaloBlur)
  /**
   *  Defines the transition of [textHaloBlur].
   */
  public var textHaloBlurTransition: Transition by mutableStateOf(initialTextHaloBlurTransition)
  /**
   *  The color of the text's halo, which helps it stand out from backgrounds. Default value: "rgba(0, 0, 0, 0)".
   */
  public var textHaloColor: ColorValue by mutableStateOf(initialTextHaloColor)
  /**
   *  Overrides applying of color theme for [textHaloColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var textHaloColorUseTheme: StringValue by mutableStateOf(initialTextHaloColorUseTheme)
  /**
   *  Defines the transition of [textHaloColor].
   */
  public var textHaloColorTransition: Transition by mutableStateOf(initialTextHaloColorTransition)
  /**
   *  Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. Default value: 0. Minimum value: 0. The unit of textHaloWidth is in pixels.
   */
  public var textHaloWidth: DoubleValue by mutableStateOf(initialTextHaloWidth)
  /**
   *  Defines the transition of [textHaloWidth].
   */
  public var textHaloWidthTransition: Transition by mutableStateOf(initialTextHaloWidthTransition)
  /**
   *  The opacity at which the text will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var textOcclusionOpacity: DoubleValue by mutableStateOf(initialTextOcclusionOpacity)
  /**
   *  Defines the transition of [textOcclusionOpacity].
   */
  public var textOcclusionOpacityTransition: Transition by mutableStateOf(initialTextOcclusionOpacityTransition)
  /**
   *  The opacity at which the text will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var textOpacity: DoubleValue by mutableStateOf(initialTextOpacity)
  /**
   *  Defines the transition of [textOpacity].
   */
  public var textOpacityTransition: Transition by mutableStateOf(initialTextOpacityTransition)
  /**
   *  Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of textTranslate is in pixels.
   */
  public var textTranslate: DoubleListValue by mutableStateOf(initialTextTranslate)
  /**
   *  Defines the transition of [textTranslate].
   */
  public var textTranslateTransition: Transition by mutableStateOf(initialTextTranslateTransition)
  /**
   *  Controls the frame of reference for `text-translate`. Default value: "map".
   */
  public var textTranslateAnchor: TextTranslateAnchorValue by mutableStateOf(initialTextTranslateAnchor)
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
  private fun UpdateIconAllowOverlap(layerNode: LayerNode) {
    if (iconAllowOverlap.notInitial) {
      layerNode.setProperty("icon-allow-overlap", iconAllowOverlap.value)
    }
  }
  @Composable
  private fun UpdateIconAnchor(layerNode: LayerNode) {
    if (iconAnchor.notInitial) {
      layerNode.setProperty("icon-anchor", iconAnchor.value)
    }
  }
  @Composable
  private fun UpdateIconIgnorePlacement(layerNode: LayerNode) {
    if (iconIgnorePlacement.notInitial) {
      layerNode.setProperty("icon-ignore-placement", iconIgnorePlacement.value)
    }
  }
  @Composable
  private fun UpdateIconImage(layerNode: LayerNode) {
    if (iconImage.notInitial) {
      iconImage.styleImage?.let {
        layerNode.addImage(it)
      }
      layerNode.setProperty("icon-image", iconImage.value)
    }
  }
  @Composable
  private fun UpdateIconKeepUpright(layerNode: LayerNode) {
    if (iconKeepUpright.notInitial) {
      layerNode.setProperty("icon-keep-upright", iconKeepUpright.value)
    }
  }
  @Composable
  private fun UpdateIconOffset(layerNode: LayerNode) {
    if (iconOffset.notInitial) {
      layerNode.setProperty("icon-offset", iconOffset.value)
    }
  }
  @Composable
  private fun UpdateIconOptional(layerNode: LayerNode) {
    if (iconOptional.notInitial) {
      layerNode.setProperty("icon-optional", iconOptional.value)
    }
  }
  @Composable
  private fun UpdateIconPadding(layerNode: LayerNode) {
    if (iconPadding.notInitial) {
      layerNode.setProperty("icon-padding", iconPadding.value)
    }
  }
  @Composable
  private fun UpdateIconPitchAlignment(layerNode: LayerNode) {
    if (iconPitchAlignment.notInitial) {
      layerNode.setProperty("icon-pitch-alignment", iconPitchAlignment.value)
    }
  }
  @Composable
  private fun UpdateIconRotate(layerNode: LayerNode) {
    if (iconRotate.notInitial) {
      layerNode.setProperty("icon-rotate", iconRotate.value)
    }
  }
  @Composable
  private fun UpdateIconRotationAlignment(layerNode: LayerNode) {
    if (iconRotationAlignment.notInitial) {
      layerNode.setProperty("icon-rotation-alignment", iconRotationAlignment.value)
    }
  }
  @Composable
  private fun UpdateIconSize(layerNode: LayerNode) {
    if (iconSize.notInitial) {
      layerNode.setProperty("icon-size", iconSize.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateIconSizeScaleRange(layerNode: LayerNode) {
    if (iconSizeScaleRange.notInitial) {
      layerNode.setProperty("icon-size-scale-range", iconSizeScaleRange.value)
    }
  }
  @Composable
  private fun UpdateIconTextFit(layerNode: LayerNode) {
    if (iconTextFit.notInitial) {
      layerNode.setProperty("icon-text-fit", iconTextFit.value)
    }
  }
  @Composable
  private fun UpdateIconTextFitPadding(layerNode: LayerNode) {
    if (iconTextFitPadding.notInitial) {
      layerNode.setProperty("icon-text-fit-padding", iconTextFitPadding.value)
    }
  }
  @Composable
  private fun UpdateSymbolAvoidEdges(layerNode: LayerNode) {
    if (symbolAvoidEdges.notInitial) {
      layerNode.setProperty("symbol-avoid-edges", symbolAvoidEdges.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSymbolElevationReference(layerNode: LayerNode) {
    if (symbolElevationReference.notInitial) {
      layerNode.setProperty("symbol-elevation-reference", symbolElevationReference.value)
    }
  }
  @Composable
  private fun UpdateSymbolPlacement(layerNode: LayerNode) {
    if (symbolPlacement.notInitial) {
      layerNode.setProperty("symbol-placement", symbolPlacement.value)
    }
  }
  @Composable
  private fun UpdateSymbolSortKey(layerNode: LayerNode) {
    if (symbolSortKey.notInitial) {
      layerNode.setProperty("symbol-sort-key", symbolSortKey.value)
    }
  }
  @Composable
  private fun UpdateSymbolSpacing(layerNode: LayerNode) {
    if (symbolSpacing.notInitial) {
      layerNode.setProperty("symbol-spacing", symbolSpacing.value)
    }
  }
  @Composable
  private fun UpdateSymbolZElevate(layerNode: LayerNode) {
    if (symbolZElevate.notInitial) {
      layerNode.setProperty("symbol-z-elevate", symbolZElevate.value)
    }
  }
  @Composable
  private fun UpdateSymbolZOrder(layerNode: LayerNode) {
    if (symbolZOrder.notInitial) {
      layerNode.setProperty("symbol-z-order", symbolZOrder.value)
    }
  }
  @Composable
  private fun UpdateTextAllowOverlap(layerNode: LayerNode) {
    if (textAllowOverlap.notInitial) {
      layerNode.setProperty("text-allow-overlap", textAllowOverlap.value)
    }
  }
  @Composable
  private fun UpdateTextAnchor(layerNode: LayerNode) {
    if (textAnchor.notInitial) {
      layerNode.setProperty("text-anchor", textAnchor.value)
    }
  }
  @Composable
  private fun UpdateTextField(layerNode: LayerNode) {
    if (textField.notInitial) {
      layerNode.setProperty("text-field", textField.value)
    }
  }
  @Composable
  private fun UpdateTextFont(layerNode: LayerNode) {
    if (textFont.notInitial) {
      layerNode.setProperty("text-font", textFont.value)
    }
  }
  @Composable
  private fun UpdateTextIgnorePlacement(layerNode: LayerNode) {
    if (textIgnorePlacement.notInitial) {
      layerNode.setProperty("text-ignore-placement", textIgnorePlacement.value)
    }
  }
  @Composable
  private fun UpdateTextJustify(layerNode: LayerNode) {
    if (textJustify.notInitial) {
      layerNode.setProperty("text-justify", textJustify.value)
    }
  }
  @Composable
  private fun UpdateTextKeepUpright(layerNode: LayerNode) {
    if (textKeepUpright.notInitial) {
      layerNode.setProperty("text-keep-upright", textKeepUpright.value)
    }
  }
  @Composable
  private fun UpdateTextLetterSpacing(layerNode: LayerNode) {
    if (textLetterSpacing.notInitial) {
      layerNode.setProperty("text-letter-spacing", textLetterSpacing.value)
    }
  }
  @Composable
  private fun UpdateTextLineHeight(layerNode: LayerNode) {
    if (textLineHeight.notInitial) {
      layerNode.setProperty("text-line-height", textLineHeight.value)
    }
  }
  @Composable
  private fun UpdateTextMaxAngle(layerNode: LayerNode) {
    if (textMaxAngle.notInitial) {
      layerNode.setProperty("text-max-angle", textMaxAngle.value)
    }
  }
  @Composable
  private fun UpdateTextMaxWidth(layerNode: LayerNode) {
    if (textMaxWidth.notInitial) {
      layerNode.setProperty("text-max-width", textMaxWidth.value)
    }
  }
  @Composable
  private fun UpdateTextOffset(layerNode: LayerNode) {
    if (textOffset.notInitial) {
      layerNode.setProperty("text-offset", textOffset.value)
    }
  }
  @Composable
  private fun UpdateTextOptional(layerNode: LayerNode) {
    if (textOptional.notInitial) {
      layerNode.setProperty("text-optional", textOptional.value)
    }
  }
  @Composable
  private fun UpdateTextPadding(layerNode: LayerNode) {
    if (textPadding.notInitial) {
      layerNode.setProperty("text-padding", textPadding.value)
    }
  }
  @Composable
  private fun UpdateTextPitchAlignment(layerNode: LayerNode) {
    if (textPitchAlignment.notInitial) {
      layerNode.setProperty("text-pitch-alignment", textPitchAlignment.value)
    }
  }
  @Composable
  private fun UpdateTextRadialOffset(layerNode: LayerNode) {
    if (textRadialOffset.notInitial) {
      layerNode.setProperty("text-radial-offset", textRadialOffset.value)
    }
  }
  @Composable
  private fun UpdateTextRotate(layerNode: LayerNode) {
    if (textRotate.notInitial) {
      layerNode.setProperty("text-rotate", textRotate.value)
    }
  }
  @Composable
  private fun UpdateTextRotationAlignment(layerNode: LayerNode) {
    if (textRotationAlignment.notInitial) {
      layerNode.setProperty("text-rotation-alignment", textRotationAlignment.value)
    }
  }
  @Composable
  private fun UpdateTextSize(layerNode: LayerNode) {
    if (textSize.notInitial) {
      layerNode.setProperty("text-size", textSize.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateTextSizeScaleRange(layerNode: LayerNode) {
    if (textSizeScaleRange.notInitial) {
      layerNode.setProperty("text-size-scale-range", textSizeScaleRange.value)
    }
  }
  @Composable
  private fun UpdateTextTransform(layerNode: LayerNode) {
    if (textTransform.notInitial) {
      layerNode.setProperty("text-transform", textTransform.value)
    }
  }
  @Composable
  private fun UpdateTextVariableAnchor(layerNode: LayerNode) {
    if (textVariableAnchor.notInitial) {
      layerNode.setProperty("text-variable-anchor", textVariableAnchor.value)
    }
  }
  @Composable
  private fun UpdateTextWritingMode(layerNode: LayerNode) {
    if (textWritingMode.notInitial) {
      layerNode.setProperty("text-writing-mode", textWritingMode.value)
    }
  }
  @Composable
  private fun UpdateIconColor(layerNode: LayerNode) {
    if (iconColor.notInitial) {
      layerNode.setProperty("icon-color", iconColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateIconColorUseTheme(layerNode: LayerNode) {
    if (iconColorUseTheme.notInitial) {
      layerNode.setProperty("icon-color-use-theme", iconColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateIconColorTransition(layerNode: LayerNode) {
    if (iconColorTransition.notInitial) {
      layerNode.setProperty("icon-color-transition", iconColorTransition.value)
    }
  }
  @Composable
  private fun UpdateIconColorSaturation(layerNode: LayerNode) {
    if (iconColorSaturation.notInitial) {
      layerNode.setProperty("icon-color-saturation", iconColorSaturation.value)
    }
  }
  @Composable
  private fun UpdateIconColorSaturationTransition(layerNode: LayerNode) {
    if (iconColorSaturationTransition.notInitial) {
      layerNode.setProperty("icon-color-saturation-transition", iconColorSaturationTransition.value)
    }
  }
  @Composable
  private fun UpdateIconEmissiveStrength(layerNode: LayerNode) {
    if (iconEmissiveStrength.notInitial) {
      layerNode.setProperty("icon-emissive-strength", iconEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateIconEmissiveStrengthTransition(layerNode: LayerNode) {
    if (iconEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("icon-emissive-strength-transition", iconEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateIconHaloBlur(layerNode: LayerNode) {
    if (iconHaloBlur.notInitial) {
      layerNode.setProperty("icon-halo-blur", iconHaloBlur.value)
    }
  }
  @Composable
  private fun UpdateIconHaloBlurTransition(layerNode: LayerNode) {
    if (iconHaloBlurTransition.notInitial) {
      layerNode.setProperty("icon-halo-blur-transition", iconHaloBlurTransition.value)
    }
  }
  @Composable
  private fun UpdateIconHaloColor(layerNode: LayerNode) {
    if (iconHaloColor.notInitial) {
      layerNode.setProperty("icon-halo-color", iconHaloColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateIconHaloColorUseTheme(layerNode: LayerNode) {
    if (iconHaloColorUseTheme.notInitial) {
      layerNode.setProperty("icon-halo-color-use-theme", iconHaloColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateIconHaloColorTransition(layerNode: LayerNode) {
    if (iconHaloColorTransition.notInitial) {
      layerNode.setProperty("icon-halo-color-transition", iconHaloColorTransition.value)
    }
  }
  @Composable
  private fun UpdateIconHaloWidth(layerNode: LayerNode) {
    if (iconHaloWidth.notInitial) {
      layerNode.setProperty("icon-halo-width", iconHaloWidth.value)
    }
  }
  @Composable
  private fun UpdateIconHaloWidthTransition(layerNode: LayerNode) {
    if (iconHaloWidthTransition.notInitial) {
      layerNode.setProperty("icon-halo-width-transition", iconHaloWidthTransition.value)
    }
  }
  @Composable
  private fun UpdateIconImageCrossFade(layerNode: LayerNode) {
    if (iconImageCrossFade.notInitial) {
      layerNode.setProperty("icon-image-cross-fade", iconImageCrossFade.value)
    }
  }
  @Composable
  private fun UpdateIconImageCrossFadeTransition(layerNode: LayerNode) {
    if (iconImageCrossFadeTransition.notInitial) {
      layerNode.setProperty("icon-image-cross-fade-transition", iconImageCrossFadeTransition.value)
    }
  }
  @Composable
  private fun UpdateIconOcclusionOpacity(layerNode: LayerNode) {
    if (iconOcclusionOpacity.notInitial) {
      layerNode.setProperty("icon-occlusion-opacity", iconOcclusionOpacity.value)
    }
  }
  @Composable
  private fun UpdateIconOcclusionOpacityTransition(layerNode: LayerNode) {
    if (iconOcclusionOpacityTransition.notInitial) {
      layerNode.setProperty("icon-occlusion-opacity-transition", iconOcclusionOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateIconOpacity(layerNode: LayerNode) {
    if (iconOpacity.notInitial) {
      layerNode.setProperty("icon-opacity", iconOpacity.value)
    }
  }
  @Composable
  private fun UpdateIconOpacityTransition(layerNode: LayerNode) {
    if (iconOpacityTransition.notInitial) {
      layerNode.setProperty("icon-opacity-transition", iconOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateIconTranslate(layerNode: LayerNode) {
    if (iconTranslate.notInitial) {
      layerNode.setProperty("icon-translate", iconTranslate.value)
    }
  }
  @Composable
  private fun UpdateIconTranslateTransition(layerNode: LayerNode) {
    if (iconTranslateTransition.notInitial) {
      layerNode.setProperty("icon-translate-transition", iconTranslateTransition.value)
    }
  }
  @Composable
  private fun UpdateIconTranslateAnchor(layerNode: LayerNode) {
    if (iconTranslateAnchor.notInitial) {
      layerNode.setProperty("icon-translate-anchor", iconTranslateAnchor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSymbolZOffset(layerNode: LayerNode) {
    if (symbolZOffset.notInitial) {
      layerNode.setProperty("symbol-z-offset", symbolZOffset.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSymbolZOffsetTransition(layerNode: LayerNode) {
    if (symbolZOffsetTransition.notInitial) {
      layerNode.setProperty("symbol-z-offset-transition", symbolZOffsetTransition.value)
    }
  }
  @Composable
  private fun UpdateTextColor(layerNode: LayerNode) {
    if (textColor.notInitial) {
      layerNode.setProperty("text-color", textColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateTextColorUseTheme(layerNode: LayerNode) {
    if (textColorUseTheme.notInitial) {
      layerNode.setProperty("text-color-use-theme", textColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateTextColorTransition(layerNode: LayerNode) {
    if (textColorTransition.notInitial) {
      layerNode.setProperty("text-color-transition", textColorTransition.value)
    }
  }
  @Composable
  private fun UpdateTextEmissiveStrength(layerNode: LayerNode) {
    if (textEmissiveStrength.notInitial) {
      layerNode.setProperty("text-emissive-strength", textEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateTextEmissiveStrengthTransition(layerNode: LayerNode) {
    if (textEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("text-emissive-strength-transition", textEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateTextHaloBlur(layerNode: LayerNode) {
    if (textHaloBlur.notInitial) {
      layerNode.setProperty("text-halo-blur", textHaloBlur.value)
    }
  }
  @Composable
  private fun UpdateTextHaloBlurTransition(layerNode: LayerNode) {
    if (textHaloBlurTransition.notInitial) {
      layerNode.setProperty("text-halo-blur-transition", textHaloBlurTransition.value)
    }
  }
  @Composable
  private fun UpdateTextHaloColor(layerNode: LayerNode) {
    if (textHaloColor.notInitial) {
      layerNode.setProperty("text-halo-color", textHaloColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateTextHaloColorUseTheme(layerNode: LayerNode) {
    if (textHaloColorUseTheme.notInitial) {
      layerNode.setProperty("text-halo-color-use-theme", textHaloColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateTextHaloColorTransition(layerNode: LayerNode) {
    if (textHaloColorTransition.notInitial) {
      layerNode.setProperty("text-halo-color-transition", textHaloColorTransition.value)
    }
  }
  @Composable
  private fun UpdateTextHaloWidth(layerNode: LayerNode) {
    if (textHaloWidth.notInitial) {
      layerNode.setProperty("text-halo-width", textHaloWidth.value)
    }
  }
  @Composable
  private fun UpdateTextHaloWidthTransition(layerNode: LayerNode) {
    if (textHaloWidthTransition.notInitial) {
      layerNode.setProperty("text-halo-width-transition", textHaloWidthTransition.value)
    }
  }
  @Composable
  private fun UpdateTextOcclusionOpacity(layerNode: LayerNode) {
    if (textOcclusionOpacity.notInitial) {
      layerNode.setProperty("text-occlusion-opacity", textOcclusionOpacity.value)
    }
  }
  @Composable
  private fun UpdateTextOcclusionOpacityTransition(layerNode: LayerNode) {
    if (textOcclusionOpacityTransition.notInitial) {
      layerNode.setProperty("text-occlusion-opacity-transition", textOcclusionOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateTextOpacity(layerNode: LayerNode) {
    if (textOpacity.notInitial) {
      layerNode.setProperty("text-opacity", textOpacity.value)
    }
  }
  @Composable
  private fun UpdateTextOpacityTransition(layerNode: LayerNode) {
    if (textOpacityTransition.notInitial) {
      layerNode.setProperty("text-opacity-transition", textOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateTextTranslate(layerNode: LayerNode) {
    if (textTranslate.notInitial) {
      layerNode.setProperty("text-translate", textTranslate.value)
    }
  }
  @Composable
  private fun UpdateTextTranslateTransition(layerNode: LayerNode) {
    if (textTranslateTransition.notInitial) {
      layerNode.setProperty("text-translate-transition", textTranslateTransition.value)
    }
  }
  @Composable
  private fun UpdateTextTranslateAnchor(layerNode: LayerNode) {
    if (textTranslateAnchor.notInitial) {
      layerNode.setProperty("text-translate-anchor", textTranslateAnchor.value)
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
    UpdateIconAllowOverlap(layerNode)
    UpdateIconAnchor(layerNode)
    UpdateIconIgnorePlacement(layerNode)
    UpdateIconImage(layerNode)
    UpdateIconKeepUpright(layerNode)
    UpdateIconOffset(layerNode)
    UpdateIconOptional(layerNode)
    UpdateIconPadding(layerNode)
    UpdateIconPitchAlignment(layerNode)
    UpdateIconRotate(layerNode)
    UpdateIconRotationAlignment(layerNode)
    UpdateIconSize(layerNode)
    UpdateIconSizeScaleRange(layerNode)
    UpdateIconTextFit(layerNode)
    UpdateIconTextFitPadding(layerNode)
    UpdateSymbolAvoidEdges(layerNode)
    UpdateSymbolElevationReference(layerNode)
    UpdateSymbolPlacement(layerNode)
    UpdateSymbolSortKey(layerNode)
    UpdateSymbolSpacing(layerNode)
    UpdateSymbolZElevate(layerNode)
    UpdateSymbolZOrder(layerNode)
    UpdateTextAllowOverlap(layerNode)
    UpdateTextAnchor(layerNode)
    UpdateTextField(layerNode)
    UpdateTextFont(layerNode)
    UpdateTextIgnorePlacement(layerNode)
    UpdateTextJustify(layerNode)
    UpdateTextKeepUpright(layerNode)
    UpdateTextLetterSpacing(layerNode)
    UpdateTextLineHeight(layerNode)
    UpdateTextMaxAngle(layerNode)
    UpdateTextMaxWidth(layerNode)
    UpdateTextOffset(layerNode)
    UpdateTextOptional(layerNode)
    UpdateTextPadding(layerNode)
    UpdateTextPitchAlignment(layerNode)
    UpdateTextRadialOffset(layerNode)
    UpdateTextRotate(layerNode)
    UpdateTextRotationAlignment(layerNode)
    UpdateTextSize(layerNode)
    UpdateTextSizeScaleRange(layerNode)
    UpdateTextTransform(layerNode)
    UpdateTextVariableAnchor(layerNode)
    UpdateTextWritingMode(layerNode)
    UpdateIconColor(layerNode)
    UpdateIconColorUseTheme(layerNode)
    UpdateIconColorTransition(layerNode)
    UpdateIconColorSaturation(layerNode)
    UpdateIconColorSaturationTransition(layerNode)
    UpdateIconEmissiveStrength(layerNode)
    UpdateIconEmissiveStrengthTransition(layerNode)
    UpdateIconHaloBlur(layerNode)
    UpdateIconHaloBlurTransition(layerNode)
    UpdateIconHaloColor(layerNode)
    UpdateIconHaloColorUseTheme(layerNode)
    UpdateIconHaloColorTransition(layerNode)
    UpdateIconHaloWidth(layerNode)
    UpdateIconHaloWidthTransition(layerNode)
    UpdateIconImageCrossFade(layerNode)
    UpdateIconImageCrossFadeTransition(layerNode)
    UpdateIconOcclusionOpacity(layerNode)
    UpdateIconOcclusionOpacityTransition(layerNode)
    UpdateIconOpacity(layerNode)
    UpdateIconOpacityTransition(layerNode)
    UpdateIconTranslate(layerNode)
    UpdateIconTranslateTransition(layerNode)
    UpdateIconTranslateAnchor(layerNode)
    UpdateSymbolZOffset(layerNode)
    UpdateSymbolZOffsetTransition(layerNode)
    UpdateTextColor(layerNode)
    UpdateTextColorUseTheme(layerNode)
    UpdateTextColorTransition(layerNode)
    UpdateTextEmissiveStrength(layerNode)
    UpdateTextEmissiveStrengthTransition(layerNode)
    UpdateTextHaloBlur(layerNode)
    UpdateTextHaloBlurTransition(layerNode)
    UpdateTextHaloColor(layerNode)
    UpdateTextHaloColorUseTheme(layerNode)
    UpdateTextHaloColorTransition(layerNode)
    UpdateTextHaloWidth(layerNode)
    UpdateTextHaloWidthTransition(layerNode)
    UpdateTextOcclusionOpacity(layerNode)
    UpdateTextOcclusionOpacityTransition(layerNode)
    UpdateTextOpacity(layerNode)
    UpdateTextOpacityTransition(layerNode)
    UpdateTextTranslate(layerNode)
    UpdateTextTranslateTransition(layerNode)
    UpdateTextTranslateAnchor(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.