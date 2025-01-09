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
import com.mapbox.maps.extension.compose.annotation.IconImage
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.IconPitchAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.IconRotationAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.IconTextFit
import com.mapbox.maps.extension.style.layers.properties.generated.IconTranslateAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.SymbolElevationReference
import com.mapbox.maps.extension.style.layers.properties.generated.SymbolPlacement
import com.mapbox.maps.extension.style.layers.properties.generated.SymbolZOrder
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextJustify
import com.mapbox.maps.extension.style.layers.properties.generated.TextPitchAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.TextRotationAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.TextTransform
import com.mapbox.maps.extension.style.layers.properties.generated.TextTranslateAnchor
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager

/**
 * The state holder for [PointAnnotationGroup] properties.
 */
@Stable
public class PointAnnotationGroupState private constructor(
  initialIconAllowOverlap: Boolean?,
  initialIconAnchor: IconAnchor?,
  initialIconIgnorePlacement: Boolean?,
  initialIconImage: IconImage?,
  initialIconKeepUpright: Boolean?,
  initialIconOffset: List<Double>?,
  initialIconOptional: Boolean?,
  initialIconPadding: Double?,
  initialIconPitchAlignment: IconPitchAlignment?,
  initialIconRotate: Double?,
  initialIconRotationAlignment: IconRotationAlignment?,
  initialIconSize: Double?,
  initialIconSizeScaleRange: List<Double>?,
  initialIconTextFit: IconTextFit?,
  initialIconTextFitPadding: List<Double>?,
  initialSymbolAvoidEdges: Boolean?,
  initialSymbolElevationReference: SymbolElevationReference?,
  initialSymbolPlacement: SymbolPlacement?,
  initialSymbolSortKey: Double?,
  initialSymbolSpacing: Double?,
  initialSymbolZElevate: Boolean?,
  initialSymbolZOrder: SymbolZOrder?,
  initialTextAllowOverlap: Boolean?,
  initialTextAnchor: TextAnchor?,
  initialTextField: String?,
  initialTextFont: List<String>?,
  initialTextIgnorePlacement: Boolean?,
  initialTextJustify: TextJustify?,
  initialTextKeepUpright: Boolean?,
  initialTextLetterSpacing: Double?,
  initialTextLineHeight: Double?,
  initialTextMaxAngle: Double?,
  initialTextMaxWidth: Double?,
  initialTextOffset: List<Double>?,
  initialTextOptional: Boolean?,
  initialTextPadding: Double?,
  initialTextPitchAlignment: TextPitchAlignment?,
  initialTextRadialOffset: Double?,
  initialTextRotate: Double?,
  initialTextRotationAlignment: TextRotationAlignment?,
  initialTextSize: Double?,
  initialTextSizeScaleRange: List<Double>?,
  initialTextTransform: TextTransform?,
  initialTextVariableAnchor: List<String>?,
  initialTextWritingMode: List<String>?,
  initialIconColor: Color?,
  initialIconColorSaturation: Double?,
  initialIconEmissiveStrength: Double?,
  initialIconHaloBlur: Double?,
  initialIconHaloColor: Color?,
  initialIconHaloWidth: Double?,
  initialIconImageCrossFade: Double?,
  initialIconOcclusionOpacity: Double?,
  initialIconOpacity: Double?,
  initialIconTranslate: List<Double>?,
  initialIconTranslateAnchor: IconTranslateAnchor?,
  initialSymbolZOffset: Double?,
  initialTextColor: Color?,
  initialTextEmissiveStrength: Double?,
  initialTextHaloBlur: Double?,
  initialTextHaloColor: Color?,
  initialTextHaloWidth: Double?,
  initialTextOcclusionOpacity: Double?,
  initialTextOpacity: Double?,
  initialTextTranslate: List<Double>?,
  initialTextTranslateAnchor: TextTranslateAnchor?,
  initialPointAnnotationGroupInteractionsState: PointAnnotationGroupInteractionsState,
) {
  public constructor() : this(
    initialIconAllowOverlap = null,
    initialIconAnchor = null,
    initialIconIgnorePlacement = null,
    initialIconImage = null,
    initialIconKeepUpright = null,
    initialIconOffset = null,
    initialIconOptional = null,
    initialIconPadding = null,
    initialIconPitchAlignment = null,
    initialIconRotate = null,
    initialIconRotationAlignment = null,
    initialIconSize = null,
    initialIconSizeScaleRange = null,
    initialIconTextFit = null,
    initialIconTextFitPadding = null,
    initialSymbolAvoidEdges = null,
    initialSymbolElevationReference = null,
    initialSymbolPlacement = null,
    initialSymbolSortKey = null,
    initialSymbolSpacing = null,
    initialSymbolZElevate = null,
    initialSymbolZOrder = null,
    initialTextAllowOverlap = null,
    initialTextAnchor = null,
    initialTextField = null,
    initialTextFont = null,
    initialTextIgnorePlacement = null,
    initialTextJustify = null,
    initialTextKeepUpright = null,
    initialTextLetterSpacing = null,
    initialTextLineHeight = null,
    initialTextMaxAngle = null,
    initialTextMaxWidth = null,
    initialTextOffset = null,
    initialTextOptional = null,
    initialTextPadding = null,
    initialTextPitchAlignment = null,
    initialTextRadialOffset = null,
    initialTextRotate = null,
    initialTextRotationAlignment = null,
    initialTextSize = null,
    initialTextSizeScaleRange = null,
    initialTextTransform = null,
    initialTextVariableAnchor = null,
    initialTextWritingMode = null,
    initialIconColor = null,
    initialIconColorSaturation = null,
    initialIconEmissiveStrength = null,
    initialIconHaloBlur = null,
    initialIconHaloColor = null,
    initialIconHaloWidth = null,
    initialIconImageCrossFade = null,
    initialIconOcclusionOpacity = null,
    initialIconOpacity = null,
    initialIconTranslate = null,
    initialIconTranslateAnchor = null,
    initialSymbolZOffset = null,
    initialTextColor = null,
    initialTextEmissiveStrength = null,
    initialTextHaloBlur = null,
    initialTextHaloColor = null,
    initialTextHaloWidth = null,
    initialTextOcclusionOpacity = null,
    initialTextOpacity = null,
    initialTextTranslate = null,
    initialTextTranslateAnchor = null,
    initialPointAnnotationGroupInteractionsState = PointAnnotationGroupInteractionsState(),
  )

  /**
   * Holds all interactions with [PointAnnotationGroup]
   */
  public var interactionsState: PointAnnotationGroupInteractionsState by mutableStateOf(initialPointAnnotationGroupInteractionsState)
  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  public var iconAllowOverlap: Boolean? by mutableStateOf(initialIconAllowOverlap)
  /**
   * Part of the icon placed closest to the anchor. Default value: "center".
   */
  public var iconAnchor: IconAnchor? by mutableStateOf(initialIconAnchor)
  /**
   * If true, other symbols can be visible even if they collide with the icon. Default value: false.
   */
  public var iconIgnorePlacement: Boolean? by mutableStateOf(initialIconIgnorePlacement)
  /**
   * Name of image in sprite to use for drawing an image background.
   */
  public var iconImage: IconImage? by mutableStateOf(initialIconImage)
  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down. Default value: false.
   */
  public var iconKeepUpright: Boolean? by mutableStateOf(initialIconKeepUpright)
  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up. Default value: [0,0].
   */
  public var iconOffset: List<Double>? by mutableStateOf(initialIconOffset)
  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not. Default value: false.
   */
  public var iconOptional: Boolean? by mutableStateOf(initialIconOptional)
  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of iconPadding is in pixels.
   */
  public var iconPadding: Double? by mutableStateOf(initialIconPadding)
  /**
   * Orientation of icon when map is pitched. Default value: "auto".
   */
  public var iconPitchAlignment: IconPitchAlignment? by mutableStateOf(initialIconPitchAlignment)
  /**
   * Rotates the icon clockwise. Default value: 0. The unit of iconRotate is in degrees.
   */
  public var iconRotate: Double? by mutableStateOf(initialIconRotate)
  /**
   * In combination with `symbol-placement`, determines the rotation behavior of icons. Default value: "auto".
   */
  public var iconRotationAlignment: IconRotationAlignment? by mutableStateOf(initialIconRotationAlignment)
  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image. Default value: 1. Minimum value: 0. The unit of iconSize is in factor of the original icon size.
   */
  public var iconSize: Double? by mutableStateOf(initialIconSize)
  /**
   * Defines the minimum and maximum scaling factors for icon related properties like `icon-size`, `icon-halo-width`, `icon-halo-blur` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  public var iconSizeScaleRange: List<Double>? by mutableStateOf(initialIconSizeScaleRange)
  /**
   * Scales the icon to fit around the associated text. Default value: "none".
   */
  public var iconTextFit: IconTextFit? by mutableStateOf(initialIconTextFit)
  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left. Default value: [0,0,0,0]. The unit of iconTextFitPadding is in pixels.
   */
  public var iconTextFitPadding: List<Double>? by mutableStateOf(initialIconTextFitPadding)
  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries. Default value: false.
   */
  public var symbolAvoidEdges: Boolean? by mutableStateOf(initialSymbolAvoidEdges)
  /**
   * Selects the base of symbol-elevation. Default value: "ground".
   */
  @MapboxExperimental
  public var symbolElevationReference: SymbolElevationReference? by mutableStateOf(initialSymbolElevationReference)
  /**
   * Label placement relative to its geometry. Default value: "point".
   */
  public var symbolPlacement: SymbolPlacement? by mutableStateOf(initialSymbolPlacement)
  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  public var symbolSortKey: Double? by mutableStateOf(initialSymbolSortKey)
  /**
   * Distance between two symbol anchors. Default value: 250. Minimum value: 1. The unit of symbolSpacing is in pixels.
   */
  public var symbolSpacing: Double? by mutableStateOf(initialSymbolSpacing)
  /**
   * Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when `fill-extrusion-height` is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or `symbol-sort-key` are applied. Default value: false.
   */
  public var symbolZElevate: Boolean? by mutableStateOf(initialSymbolZElevate)
  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`. Default value: "auto".
   */
  public var symbolZOrder: SymbolZOrder? by mutableStateOf(initialSymbolZOrder)
  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  public var textAllowOverlap: Boolean? by mutableStateOf(initialTextAllowOverlap)
  /**
   * Part of the text placed closest to the anchor. Default value: "center".
   */
  public var textAnchor: TextAnchor? by mutableStateOf(initialTextAnchor)
  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored. Default value: "".
   */
  public var textField: String? by mutableStateOf(initialTextField)
  /**
   * Font stack to use for displaying text.
   */
  public var textFont: List<String>? by mutableStateOf(initialTextFont)
  /**
   * If true, other symbols can be visible even if they collide with the text. Default value: false.
   */
  public var textIgnorePlacement: Boolean? by mutableStateOf(initialTextIgnorePlacement)
  /**
   * Text justification options. Default value: "center".
   */
  public var textJustify: TextJustify? by mutableStateOf(initialTextJustify)
  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down. Default value: true.
   */
  public var textKeepUpright: Boolean? by mutableStateOf(initialTextKeepUpright)
  /**
   * Text tracking amount. Default value: 0. The unit of textLetterSpacing is in ems.
   */
  public var textLetterSpacing: Double? by mutableStateOf(initialTextLetterSpacing)
  /**
   * Text leading value for multi-line text. Default value: 1.2. The unit of textLineHeight is in ems.
   */
  public var textLineHeight: Double? by mutableStateOf(initialTextLineHeight)
  /**
   * Maximum angle change between adjacent characters. Default value: 45. The unit of textMaxAngle is in degrees.
   */
  public var textMaxAngle: Double? by mutableStateOf(initialTextMaxAngle)
  /**
   * The maximum line width for text wrapping. Default value: 10. Minimum value: 0. The unit of textMaxWidth is in ems.
   */
  public var textMaxWidth: Double? by mutableStateOf(initialTextMaxWidth)
  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. Default value: [0,0]. The unit of textOffset is in ems.
   */
  public var textOffset: List<Double>? by mutableStateOf(initialTextOffset)
  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not. Default value: false.
   */
  public var textOptional: Boolean? by mutableStateOf(initialTextOptional)
  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of textPadding is in pixels.
   */
  public var textPadding: Double? by mutableStateOf(initialTextPadding)
  /**
   * Orientation of text when map is pitched. Default value: "auto".
   */
  public var textPitchAlignment: TextPitchAlignment? by mutableStateOf(initialTextPitchAlignment)
  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present. Default value: 0. The unit of textRadialOffset is in ems.
   */
  public var textRadialOffset: Double? by mutableStateOf(initialTextRadialOffset)
  /**
   * Rotates the text clockwise. Default value: 0. The unit of textRotate is in degrees.
   */
  public var textRotate: Double? by mutableStateOf(initialTextRotate)
  /**
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text. Default value: "auto".
   */
  public var textRotationAlignment: TextRotationAlignment? by mutableStateOf(initialTextRotationAlignment)
  /**
   * Font size. Default value: 16. Minimum value: 0. The unit of textSize is in pixels.
   */
  public var textSize: Double? by mutableStateOf(initialTextSize)
  /**
   * Defines the minimum and maximum scaling factors for text related properties like `text-size`, `text-max-width`, `text-halo-width`, `font-size` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  public var textSizeScaleRange: List<Double>? by mutableStateOf(initialTextSizeScaleRange)
  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property. Default value: "none".
   */
  public var textTransform: TextTransform? by mutableStateOf(initialTextTransform)
  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   */
  public var textVariableAnchor: List<String>? by mutableStateOf(initialTextVariableAnchor)
  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  public var textWritingMode: List<String>? by mutableStateOf(initialTextWritingMode)
  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "#000000".
   */
  public var iconColor: Color? by mutableStateOf(initialIconColor)
  /**
   * Increase or reduce the saturation of the symbol icon. Default value: 0. Value range: [-1, 1]
   */
  public var iconColorSaturation: Double? by mutableStateOf(initialIconColorSaturation)
  /**
   * Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of iconEmissiveStrength is in intensity.
   */
  public var iconEmissiveStrength: Double? by mutableStateOf(initialIconEmissiveStrength)
  /**
   * Fade out the halo towards the outside. Default value: 0. Minimum value: 0. The unit of iconHaloBlur is in pixels.
   */
  public var iconHaloBlur: Double? by mutableStateOf(initialIconHaloBlur)
  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "rgba(0, 0, 0, 0)".
   */
  public var iconHaloColor: Color? by mutableStateOf(initialIconHaloColor)
  /**
   * Distance of halo to the icon outline. Default value: 0. Minimum value: 0. The unit of iconHaloWidth is in pixels.
   */
  public var iconHaloWidth: Double? by mutableStateOf(initialIconHaloWidth)
  /**
   * Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together. Default value: 0. Value range: [0, 1]
   */
  public var iconImageCrossFade: Double? by mutableStateOf(initialIconImageCrossFade)
  /**
   * The opacity at which the icon will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var iconOcclusionOpacity: Double? by mutableStateOf(initialIconOcclusionOpacity)
  /**
   * The opacity at which the icon will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var iconOpacity: Double? by mutableStateOf(initialIconOpacity)
  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of iconTranslate is in pixels.
   */
  public var iconTranslate: List<Double>? by mutableStateOf(initialIconTranslate)
  /**
   * Controls the frame of reference for `icon-translate`. Default value: "map".
   */
  public var iconTranslateAnchor: IconTranslateAnchor? by mutableStateOf(initialIconTranslateAnchor)
  /**
   * Specifies an uniform elevation from the ground, in meters. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var symbolZOffset: Double? by mutableStateOf(initialSymbolZOffset)
  /**
   * The color with which the text will be drawn. Default value: "#000000".
   */
  public var textColor: Color? by mutableStateOf(initialTextColor)
  /**
   * Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of textEmissiveStrength is in intensity.
   */
  public var textEmissiveStrength: Double? by mutableStateOf(initialTextEmissiveStrength)
  /**
   * The halo's fadeout distance towards the outside. Default value: 0. Minimum value: 0. The unit of textHaloBlur is in pixels.
   */
  public var textHaloBlur: Double? by mutableStateOf(initialTextHaloBlur)
  /**
   * The color of the text's halo, which helps it stand out from backgrounds. Default value: "rgba(0, 0, 0, 0)".
   */
  public var textHaloColor: Color? by mutableStateOf(initialTextHaloColor)
  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. Default value: 0. Minimum value: 0. The unit of textHaloWidth is in pixels.
   */
  public var textHaloWidth: Double? by mutableStateOf(initialTextHaloWidth)
  /**
   * The opacity at which the text will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var textOcclusionOpacity: Double? by mutableStateOf(initialTextOcclusionOpacity)
  /**
   * The opacity at which the text will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var textOpacity: Double? by mutableStateOf(initialTextOpacity)
  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of textTranslate is in pixels.
   */
  public var textTranslate: List<Double>? by mutableStateOf(initialTextTranslate)
  /**
   * Controls the frame of reference for `text-translate`. Default value: "map".
   */
  public var textTranslateAnchor: TextTranslateAnchor? by mutableStateOf(initialTextTranslateAnchor)

  @Composable
  private fun UpdateIconAllowOverlap(annotationManager: PointAnnotationManager) {
    annotationManager.iconAllowOverlap = iconAllowOverlap
  }
  @Composable
  private fun UpdateIconAnchor(annotationManager: PointAnnotationManager) {
    annotationManager.iconAnchor = iconAnchor
  }
  @Composable
  private fun UpdateIconIgnorePlacement(annotationManager: PointAnnotationManager) {
    annotationManager.iconIgnorePlacement = iconIgnorePlacement
  }
  @Composable
  private fun UpdateIconImage(annotationManager: PointAnnotationManager) {
    iconImage?.let { iconImage ->
    iconImage.bitmap?.let {
      annotationManager.iconImageBitmap = it
    }
    iconImage.imageId?.let {
      annotationManager.iconImage = it
    }
  }
  }
  @Composable
  private fun UpdateIconKeepUpright(annotationManager: PointAnnotationManager) {
    annotationManager.iconKeepUpright = iconKeepUpright
  }
  @Composable
  private fun UpdateIconOffset(annotationManager: PointAnnotationManager) {
    annotationManager.iconOffset = iconOffset
  }
  @Composable
  private fun UpdateIconOptional(annotationManager: PointAnnotationManager) {
    annotationManager.iconOptional = iconOptional
  }
  @Composable
  private fun UpdateIconPadding(annotationManager: PointAnnotationManager) {
    annotationManager.iconPadding = iconPadding
  }
  @Composable
  private fun UpdateIconPitchAlignment(annotationManager: PointAnnotationManager) {
    annotationManager.iconPitchAlignment = iconPitchAlignment
  }
  @Composable
  private fun UpdateIconRotate(annotationManager: PointAnnotationManager) {
    annotationManager.iconRotate = iconRotate
  }
  @Composable
  private fun UpdateIconRotationAlignment(annotationManager: PointAnnotationManager) {
    annotationManager.iconRotationAlignment = iconRotationAlignment
  }
  @Composable
  private fun UpdateIconSize(annotationManager: PointAnnotationManager) {
    annotationManager.iconSize = iconSize
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateIconSizeScaleRange(annotationManager: PointAnnotationManager) {
    annotationManager.iconSizeScaleRange = iconSizeScaleRange
  }
  @Composable
  private fun UpdateIconTextFit(annotationManager: PointAnnotationManager) {
    annotationManager.iconTextFit = iconTextFit
  }
  @Composable
  private fun UpdateIconTextFitPadding(annotationManager: PointAnnotationManager) {
    annotationManager.iconTextFitPadding = iconTextFitPadding
  }
  @Composable
  private fun UpdateSymbolAvoidEdges(annotationManager: PointAnnotationManager) {
    annotationManager.symbolAvoidEdges = symbolAvoidEdges
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSymbolElevationReference(annotationManager: PointAnnotationManager) {
    annotationManager.symbolElevationReference = symbolElevationReference
  }
  @Composable
  private fun UpdateSymbolPlacement(annotationManager: PointAnnotationManager) {
    annotationManager.symbolPlacement = symbolPlacement
  }
  @Composable
  private fun UpdateSymbolSortKey(annotationManager: PointAnnotationManager) {
    annotationManager.symbolSortKey = symbolSortKey
  }
  @Composable
  private fun UpdateSymbolSpacing(annotationManager: PointAnnotationManager) {
    annotationManager.symbolSpacing = symbolSpacing
  }
  @Composable
  private fun UpdateSymbolZElevate(annotationManager: PointAnnotationManager) {
    annotationManager.symbolZElevate = symbolZElevate
  }
  @Composable
  private fun UpdateSymbolZOrder(annotationManager: PointAnnotationManager) {
    annotationManager.symbolZOrder = symbolZOrder
  }
  @Composable
  private fun UpdateTextAllowOverlap(annotationManager: PointAnnotationManager) {
    annotationManager.textAllowOverlap = textAllowOverlap
  }
  @Composable
  private fun UpdateTextAnchor(annotationManager: PointAnnotationManager) {
    annotationManager.textAnchor = textAnchor
  }
  @Composable
  private fun UpdateTextField(annotationManager: PointAnnotationManager) {
    annotationManager.textField = textField
  }
  @Composable
  private fun UpdateTextFont(annotationManager: PointAnnotationManager) {
    annotationManager.textFont = textFont
  }
  @Composable
  private fun UpdateTextIgnorePlacement(annotationManager: PointAnnotationManager) {
    annotationManager.textIgnorePlacement = textIgnorePlacement
  }
  @Composable
  private fun UpdateTextJustify(annotationManager: PointAnnotationManager) {
    annotationManager.textJustify = textJustify
  }
  @Composable
  private fun UpdateTextKeepUpright(annotationManager: PointAnnotationManager) {
    annotationManager.textKeepUpright = textKeepUpright
  }
  @Composable
  private fun UpdateTextLetterSpacing(annotationManager: PointAnnotationManager) {
    annotationManager.textLetterSpacing = textLetterSpacing
  }
  @Composable
  private fun UpdateTextLineHeight(annotationManager: PointAnnotationManager) {
    annotationManager.textLineHeight = textLineHeight
  }
  @Composable
  private fun UpdateTextMaxAngle(annotationManager: PointAnnotationManager) {
    annotationManager.textMaxAngle = textMaxAngle
  }
  @Composable
  private fun UpdateTextMaxWidth(annotationManager: PointAnnotationManager) {
    annotationManager.textMaxWidth = textMaxWidth
  }
  @Composable
  private fun UpdateTextOffset(annotationManager: PointAnnotationManager) {
    annotationManager.textOffset = textOffset
  }
  @Composable
  private fun UpdateTextOptional(annotationManager: PointAnnotationManager) {
    annotationManager.textOptional = textOptional
  }
  @Composable
  private fun UpdateTextPadding(annotationManager: PointAnnotationManager) {
    annotationManager.textPadding = textPadding
  }
  @Composable
  private fun UpdateTextPitchAlignment(annotationManager: PointAnnotationManager) {
    annotationManager.textPitchAlignment = textPitchAlignment
  }
  @Composable
  private fun UpdateTextRadialOffset(annotationManager: PointAnnotationManager) {
    annotationManager.textRadialOffset = textRadialOffset
  }
  @Composable
  private fun UpdateTextRotate(annotationManager: PointAnnotationManager) {
    annotationManager.textRotate = textRotate
  }
  @Composable
  private fun UpdateTextRotationAlignment(annotationManager: PointAnnotationManager) {
    annotationManager.textRotationAlignment = textRotationAlignment
  }
  @Composable
  private fun UpdateTextSize(annotationManager: PointAnnotationManager) {
    annotationManager.textSize = textSize
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateTextSizeScaleRange(annotationManager: PointAnnotationManager) {
    annotationManager.textSizeScaleRange = textSizeScaleRange
  }
  @Composable
  private fun UpdateTextTransform(annotationManager: PointAnnotationManager) {
    annotationManager.textTransform = textTransform
  }
  @Composable
  private fun UpdateTextVariableAnchor(annotationManager: PointAnnotationManager) {
    annotationManager.textVariableAnchor = textVariableAnchor
  }
  @Composable
  private fun UpdateTextWritingMode(annotationManager: PointAnnotationManager) {
    annotationManager.textWritingMode = textWritingMode
  }
  @Composable
  private fun UpdateIconColor(annotationManager: PointAnnotationManager) {
    annotationManager.iconColorString = iconColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateIconColorSaturation(annotationManager: PointAnnotationManager) {
    annotationManager.iconColorSaturation = iconColorSaturation
  }
  @Composable
  private fun UpdateIconEmissiveStrength(annotationManager: PointAnnotationManager) {
    annotationManager.iconEmissiveStrength = iconEmissiveStrength
  }
  @Composable
  private fun UpdateIconHaloBlur(annotationManager: PointAnnotationManager) {
    annotationManager.iconHaloBlur = iconHaloBlur
  }
  @Composable
  private fun UpdateIconHaloColor(annotationManager: PointAnnotationManager) {
    annotationManager.iconHaloColorString = iconHaloColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateIconHaloWidth(annotationManager: PointAnnotationManager) {
    annotationManager.iconHaloWidth = iconHaloWidth
  }
  @Composable
  private fun UpdateIconImageCrossFade(annotationManager: PointAnnotationManager) {
    annotationManager.iconImageCrossFade = iconImageCrossFade
  }
  @Composable
  private fun UpdateIconOcclusionOpacity(annotationManager: PointAnnotationManager) {
    annotationManager.iconOcclusionOpacity = iconOcclusionOpacity
  }
  @Composable
  private fun UpdateIconOpacity(annotationManager: PointAnnotationManager) {
    annotationManager.iconOpacity = iconOpacity
  }
  @Composable
  private fun UpdateIconTranslate(annotationManager: PointAnnotationManager) {
    annotationManager.iconTranslate = iconTranslate
  }
  @Composable
  private fun UpdateIconTranslateAnchor(annotationManager: PointAnnotationManager) {
    annotationManager.iconTranslateAnchor = iconTranslateAnchor
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSymbolZOffset(annotationManager: PointAnnotationManager) {
    annotationManager.symbolZOffset = symbolZOffset
  }
  @Composable
  private fun UpdateTextColor(annotationManager: PointAnnotationManager) {
    annotationManager.textColorString = textColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateTextEmissiveStrength(annotationManager: PointAnnotationManager) {
    annotationManager.textEmissiveStrength = textEmissiveStrength
  }
  @Composable
  private fun UpdateTextHaloBlur(annotationManager: PointAnnotationManager) {
    annotationManager.textHaloBlur = textHaloBlur
  }
  @Composable
  private fun UpdateTextHaloColor(annotationManager: PointAnnotationManager) {
    annotationManager.textHaloColorString = textHaloColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateTextHaloWidth(annotationManager: PointAnnotationManager) {
    annotationManager.textHaloWidth = textHaloWidth
  }
  @Composable
  private fun UpdateTextOcclusionOpacity(annotationManager: PointAnnotationManager) {
    annotationManager.textOcclusionOpacity = textOcclusionOpacity
  }
  @Composable
  private fun UpdateTextOpacity(annotationManager: PointAnnotationManager) {
    annotationManager.textOpacity = textOpacity
  }
  @Composable
  private fun UpdateTextTranslate(annotationManager: PointAnnotationManager) {
    annotationManager.textTranslate = textTranslate
  }
  @Composable
  private fun UpdateTextTranslateAnchor(annotationManager: PointAnnotationManager) {
    annotationManager.textTranslateAnchor = textTranslateAnchor
  }

  @Composable
  internal fun UpdateProperties(annotationManager: PointAnnotationManager) {
    UpdateIconAllowOverlap(annotationManager)
    UpdateIconAnchor(annotationManager)
    UpdateIconIgnorePlacement(annotationManager)
    UpdateIconImage(annotationManager)
    UpdateIconKeepUpright(annotationManager)
    UpdateIconOffset(annotationManager)
    UpdateIconOptional(annotationManager)
    UpdateIconPadding(annotationManager)
    UpdateIconPitchAlignment(annotationManager)
    UpdateIconRotate(annotationManager)
    UpdateIconRotationAlignment(annotationManager)
    UpdateIconSize(annotationManager)
    UpdateIconSizeScaleRange(annotationManager)
    UpdateIconTextFit(annotationManager)
    UpdateIconTextFitPadding(annotationManager)
    UpdateSymbolAvoidEdges(annotationManager)
    UpdateSymbolElevationReference(annotationManager)
    UpdateSymbolPlacement(annotationManager)
    UpdateSymbolSortKey(annotationManager)
    UpdateSymbolSpacing(annotationManager)
    UpdateSymbolZElevate(annotationManager)
    UpdateSymbolZOrder(annotationManager)
    UpdateTextAllowOverlap(annotationManager)
    UpdateTextAnchor(annotationManager)
    UpdateTextField(annotationManager)
    UpdateTextFont(annotationManager)
    UpdateTextIgnorePlacement(annotationManager)
    UpdateTextJustify(annotationManager)
    UpdateTextKeepUpright(annotationManager)
    UpdateTextLetterSpacing(annotationManager)
    UpdateTextLineHeight(annotationManager)
    UpdateTextMaxAngle(annotationManager)
    UpdateTextMaxWidth(annotationManager)
    UpdateTextOffset(annotationManager)
    UpdateTextOptional(annotationManager)
    UpdateTextPadding(annotationManager)
    UpdateTextPitchAlignment(annotationManager)
    UpdateTextRadialOffset(annotationManager)
    UpdateTextRotate(annotationManager)
    UpdateTextRotationAlignment(annotationManager)
    UpdateTextSize(annotationManager)
    UpdateTextSizeScaleRange(annotationManager)
    UpdateTextTransform(annotationManager)
    UpdateTextVariableAnchor(annotationManager)
    UpdateTextWritingMode(annotationManager)
    UpdateIconColor(annotationManager)
    UpdateIconColorSaturation(annotationManager)
    UpdateIconEmissiveStrength(annotationManager)
    UpdateIconHaloBlur(annotationManager)
    UpdateIconHaloColor(annotationManager)
    UpdateIconHaloWidth(annotationManager)
    UpdateIconImageCrossFade(annotationManager)
    UpdateIconOcclusionOpacity(annotationManager)
    UpdateIconOpacity(annotationManager)
    UpdateIconTranslate(annotationManager)
    UpdateIconTranslateAnchor(annotationManager)
    UpdateSymbolZOffset(annotationManager)
    UpdateTextColor(annotationManager)
    UpdateTextEmissiveStrength(annotationManager)
    UpdateTextHaloBlur(annotationManager)
    UpdateTextHaloColor(annotationManager)
    UpdateTextHaloWidth(annotationManager)
    UpdateTextOcclusionOpacity(annotationManager)
    UpdateTextOpacity(annotationManager)
    UpdateTextTranslate(annotationManager)
    UpdateTextTranslateAnchor(annotationManager)
  }
}

// End of generated file.