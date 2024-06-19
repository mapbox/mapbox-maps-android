// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringListValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.FormattedValue
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * An icon or a text label.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#symbol)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param iconAllowOverlap If true, the icon will be visible even if it collides with other previously drawn symbols. Default value: false.
 * @param iconAnchor Part of the icon placed closest to the anchor. Default value: "center".
 * @param iconIgnorePlacement If true, other symbols can be visible even if they collide with the icon. Default value: false.
 * @param iconImage Name of image in sprite to use for drawing an image background.
 * @param iconKeepUpright If true, the icon may be flipped to prevent it from being rendered upside-down. Default value: false.
 * @param iconOffset Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up. Default value: [0,0].
 * @param iconOptional If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not. Default value: false.
 * @param iconPadding Size of the additional area around the icon bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0.
 * @param iconPitchAlignment Orientation of icon when map is pitched. Default value: "auto".
 * @param iconRotate Rotates the icon clockwise. Default value: 0.
 * @param iconRotationAlignment In combination with `symbol-placement`, determines the rotation behavior of icons. Default value: "auto".
 * @param iconSize Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image. Default value: 1. Minimum value: 0.
 * @param iconTextFit Scales the icon to fit around the associated text. Default value: "none".
 * @param iconTextFitPadding Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left. Default value: [0,0,0,0].
 * @param symbolAvoidEdges If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries. Default value: false.
 * @param symbolPlacement Label placement relative to its geometry. Default value: "point".
 * @param symbolSortKey Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
 * @param symbolSpacing Distance between two symbol anchors. Default value: 250. Minimum value: 1.
 * @param symbolZElevate Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when `fill-extrusion-height` is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or `symbol-sort-key` are applied. Default value: false.
 * @param symbolZOrder Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`. Default value: "auto".
 * @param textAllowOverlap If true, the text will be visible even if it collides with other previously drawn symbols. Default value: false.
 * @param textAnchor Part of the text placed closest to the anchor. Default value: "center".
 * @param textField Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored. Default value: "".
 * @param textFont Font stack to use for displaying text.
 * @param textIgnorePlacement If true, other symbols can be visible even if they collide with the text. Default value: false.
 * @param textJustify Text justification options. Default value: "center".
 * @param textKeepUpright If true, the text may be flipped vertically to prevent it from being rendered upside-down. Default value: true.
 * @param textLetterSpacing Text tracking amount. Default value: 0.
 * @param textLineHeight Text leading value for multi-line text. Default value: 1.2.
 * @param textMaxAngle Maximum angle change between adjacent characters. Default value: 45.
 * @param textMaxWidth The maximum line width for text wrapping. Default value: 10. Minimum value: 0.
 * @param textOffset Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. Default value: [0,0].
 * @param textOptional If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not. Default value: false.
 * @param textPadding Size of the additional area around the text bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0.
 * @param textPitchAlignment Orientation of text when map is pitched. Default value: "auto".
 * @param textRadialOffset Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present. Default value: 0.
 * @param textRotate Rotates the text clockwise. Default value: 0.
 * @param textRotationAlignment In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text. Default value: "auto".
 * @param textSize Font size. Default value: 16. Minimum value: 0.
 * @param textTransform Specifies how to capitalize text, similar to the CSS `text-transform` property. Default value: "none".
 * @param textVariableAnchor To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
 * @param textWritingMode The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
 * @param iconColor The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "#000000".
 * @param iconColorTransition Defines the transition of [iconColor]. Default value: "#000000".
 * @param iconColorSaturation Increase or reduce the saturation of the symbol icon. Default value: 0. Value range: [-1, 1]
 * @param iconColorSaturationTransition Defines the transition of [iconColorSaturation]. Default value: 0. Value range: [-1, 1]
 * @param iconEmissiveStrength Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0.
 * @param iconEmissiveStrengthTransition Defines the transition of [iconEmissiveStrength]. Default value: 1. Minimum value: 0.
 * @param iconHaloBlur Fade out the halo towards the outside. Default value: 0. Minimum value: 0.
 * @param iconHaloBlurTransition Defines the transition of [iconHaloBlur]. Default value: 0. Minimum value: 0.
 * @param iconHaloColor The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "rgba(0, 0, 0, 0)".
 * @param iconHaloColorTransition Defines the transition of [iconHaloColor]. Default value: "rgba(0, 0, 0, 0)".
 * @param iconHaloWidth Distance of halo to the icon outline. Default value: 0. Minimum value: 0.
 * @param iconHaloWidthTransition Defines the transition of [iconHaloWidth]. Default value: 0. Minimum value: 0.
 * @param iconImageCrossFade Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together. Default value: 0. Value range: [0, 1]
 * @param iconImageCrossFadeTransition Defines the transition of [iconImageCrossFade]. Default value: 0. Value range: [0, 1]
 * @param iconOpacity The opacity at which the icon will be drawn. Default value: 1. Value range: [0, 1]
 * @param iconOpacityTransition Defines the transition of [iconOpacity]. Default value: 1. Value range: [0, 1]
 * @param iconTranslate Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0].
 * @param iconTranslateTransition Defines the transition of [iconTranslate]. Default value: [0,0].
 * @param iconTranslateAnchor Controls the frame of reference for `icon-translate`. Default value: "map".
 * @param textColor The color with which the text will be drawn. Default value: "#000000".
 * @param textColorTransition Defines the transition of [textColor]. Default value: "#000000".
 * @param textEmissiveStrength Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0.
 * @param textEmissiveStrengthTransition Defines the transition of [textEmissiveStrength]. Default value: 1. Minimum value: 0.
 * @param textHaloBlur The halo's fadeout distance towards the outside. Default value: 0. Minimum value: 0.
 * @param textHaloBlurTransition Defines the transition of [textHaloBlur]. Default value: 0. Minimum value: 0.
 * @param textHaloColor The color of the text's halo, which helps it stand out from backgrounds. Default value: "rgba(0, 0, 0, 0)".
 * @param textHaloColorTransition Defines the transition of [textHaloColor]. Default value: "rgba(0, 0, 0, 0)".
 * @param textHaloWidth Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. Default value: 0. Minimum value: 0.
 * @param textHaloWidthTransition Defines the transition of [textHaloWidth]. Default value: 0. Minimum value: 0.
 * @param textOpacity The opacity at which the text will be drawn. Default value: 1. Value range: [0, 1]
 * @param textOpacityTransition Defines the transition of [textOpacity]. Default value: 1. Value range: [0, 1]
 * @param textTranslate Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0].
 * @param textTranslateTransition Defines the transition of [textTranslate]. Default value: [0,0].
 * @param textTranslateAnchor Controls the frame of reference for `text-translate`. Default value: "map".
 * @param visibility Whether this layer is displayed. Default value: "visible".
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun SymbolLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("symbol")
  },
  iconAllowOverlap: BooleanValue = BooleanValue.INITIAL,
  iconAnchor: IconAnchorValue = IconAnchorValue.INITIAL,
  iconIgnorePlacement: BooleanValue = BooleanValue.INITIAL,
  iconImage: ImageValue = ImageValue.INITIAL,
  iconKeepUpright: BooleanValue = BooleanValue.INITIAL,
  iconOffset: DoubleListValue = DoubleListValue.INITIAL,
  iconOptional: BooleanValue = BooleanValue.INITIAL,
  iconPadding: DoubleValue = DoubleValue.INITIAL,
  iconPitchAlignment: IconPitchAlignmentValue = IconPitchAlignmentValue.INITIAL,
  iconRotate: DoubleValue = DoubleValue.INITIAL,
  iconRotationAlignment: IconRotationAlignmentValue = IconRotationAlignmentValue.INITIAL,
  iconSize: DoubleValue = DoubleValue.INITIAL,
  iconTextFit: IconTextFitValue = IconTextFitValue.INITIAL,
  iconTextFitPadding: DoubleListValue = DoubleListValue.INITIAL,
  symbolAvoidEdges: BooleanValue = BooleanValue.INITIAL,
  symbolPlacement: SymbolPlacementValue = SymbolPlacementValue.INITIAL,
  symbolSortKey: DoubleValue = DoubleValue.INITIAL,
  symbolSpacing: DoubleValue = DoubleValue.INITIAL,
  symbolZElevate: BooleanValue = BooleanValue.INITIAL,
  symbolZOrder: SymbolZOrderValue = SymbolZOrderValue.INITIAL,
  textAllowOverlap: BooleanValue = BooleanValue.INITIAL,
  textAnchor: TextAnchorValue = TextAnchorValue.INITIAL,
  textField: FormattedValue = FormattedValue.INITIAL,
  textFont: StringListValue = StringListValue.INITIAL,
  textIgnorePlacement: BooleanValue = BooleanValue.INITIAL,
  textJustify: TextJustifyValue = TextJustifyValue.INITIAL,
  textKeepUpright: BooleanValue = BooleanValue.INITIAL,
  textLetterSpacing: DoubleValue = DoubleValue.INITIAL,
  textLineHeight: DoubleValue = DoubleValue.INITIAL,
  textMaxAngle: DoubleValue = DoubleValue.INITIAL,
  textMaxWidth: DoubleValue = DoubleValue.INITIAL,
  textOffset: DoubleListValue = DoubleListValue.INITIAL,
  textOptional: BooleanValue = BooleanValue.INITIAL,
  textPadding: DoubleValue = DoubleValue.INITIAL,
  textPitchAlignment: TextPitchAlignmentValue = TextPitchAlignmentValue.INITIAL,
  textRadialOffset: DoubleValue = DoubleValue.INITIAL,
  textRotate: DoubleValue = DoubleValue.INITIAL,
  textRotationAlignment: TextRotationAlignmentValue = TextRotationAlignmentValue.INITIAL,
  textSize: DoubleValue = DoubleValue.INITIAL,
  textTransform: TextTransformValue = TextTransformValue.INITIAL,
  textVariableAnchor: TextVariableAnchorListValue = TextVariableAnchorListValue.INITIAL,
  textWritingMode: TextWritingModeListValue = TextWritingModeListValue.INITIAL,
  iconColor: ColorValue = ColorValue.INITIAL,
  iconColorTransition: Transition = Transition.INITIAL,
  iconColorSaturation: DoubleValue = DoubleValue.INITIAL,
  iconColorSaturationTransition: Transition = Transition.INITIAL,
  iconEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  iconEmissiveStrengthTransition: Transition = Transition.INITIAL,
  iconHaloBlur: DoubleValue = DoubleValue.INITIAL,
  iconHaloBlurTransition: Transition = Transition.INITIAL,
  iconHaloColor: ColorValue = ColorValue.INITIAL,
  iconHaloColorTransition: Transition = Transition.INITIAL,
  iconHaloWidth: DoubleValue = DoubleValue.INITIAL,
  iconHaloWidthTransition: Transition = Transition.INITIAL,
  iconImageCrossFade: DoubleValue = DoubleValue.INITIAL,
  iconImageCrossFadeTransition: Transition = Transition.INITIAL,
  iconOpacity: DoubleValue = DoubleValue.INITIAL,
  iconOpacityTransition: Transition = Transition.INITIAL,
  iconTranslate: DoubleListValue = DoubleListValue.INITIAL,
  iconTranslateTransition: Transition = Transition.INITIAL,
  iconTranslateAnchor: IconTranslateAnchorValue = IconTranslateAnchorValue.INITIAL,
  textColor: ColorValue = ColorValue.INITIAL,
  textColorTransition: Transition = Transition.INITIAL,
  textEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  textEmissiveStrengthTransition: Transition = Transition.INITIAL,
  textHaloBlur: DoubleValue = DoubleValue.INITIAL,
  textHaloBlurTransition: Transition = Transition.INITIAL,
  textHaloColor: ColorValue = ColorValue.INITIAL,
  textHaloColorTransition: Transition = Transition.INITIAL,
  textHaloWidth: DoubleValue = DoubleValue.INITIAL,
  textHaloWidthTransition: Transition = Transition.INITIAL,
  textOpacity: DoubleValue = DoubleValue.INITIAL,
  textOpacityTransition: Transition = Transition.INITIAL,
  textTranslate: DoubleListValue = DoubleListValue.INITIAL,
  textTranslateTransition: Transition = Transition.INITIAL,
  textTranslateAnchor: TextTranslateAnchorValue = TextTranslateAnchorValue.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SymbolLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "symbol",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (iconAllowOverlap.notInitial) {
          setProperty("icon-allow-overlap", iconAllowOverlap.value)
        }
        if (iconAnchor.notInitial) {
          setProperty("icon-anchor", iconAnchor.value)
        }
        if (iconIgnorePlacement.notInitial) {
          setProperty("icon-ignore-placement", iconIgnorePlacement.value)
        }
        if (iconImage.notInitial) {
          iconImage.styleImage?.let {
            addImage(it)
          }
          setProperty("icon-image", iconImage.value)
        }
        if (iconKeepUpright.notInitial) {
          setProperty("icon-keep-upright", iconKeepUpright.value)
        }
        if (iconOffset.notInitial) {
          setProperty("icon-offset", iconOffset.value)
        }
        if (iconOptional.notInitial) {
          setProperty("icon-optional", iconOptional.value)
        }
        if (iconPadding.notInitial) {
          setProperty("icon-padding", iconPadding.value)
        }
        if (iconPitchAlignment.notInitial) {
          setProperty("icon-pitch-alignment", iconPitchAlignment.value)
        }
        if (iconRotate.notInitial) {
          setProperty("icon-rotate", iconRotate.value)
        }
        if (iconRotationAlignment.notInitial) {
          setProperty("icon-rotation-alignment", iconRotationAlignment.value)
        }
        if (iconSize.notInitial) {
          setProperty("icon-size", iconSize.value)
        }
        if (iconTextFit.notInitial) {
          setProperty("icon-text-fit", iconTextFit.value)
        }
        if (iconTextFitPadding.notInitial) {
          setProperty("icon-text-fit-padding", iconTextFitPadding.value)
        }
        if (symbolAvoidEdges.notInitial) {
          setProperty("symbol-avoid-edges", symbolAvoidEdges.value)
        }
        if (symbolPlacement.notInitial) {
          setProperty("symbol-placement", symbolPlacement.value)
        }
        if (symbolSortKey.notInitial) {
          setProperty("symbol-sort-key", symbolSortKey.value)
        }
        if (symbolSpacing.notInitial) {
          setProperty("symbol-spacing", symbolSpacing.value)
        }
        if (symbolZElevate.notInitial) {
          setProperty("symbol-z-elevate", symbolZElevate.value)
        }
        if (symbolZOrder.notInitial) {
          setProperty("symbol-z-order", symbolZOrder.value)
        }
        if (textAllowOverlap.notInitial) {
          setProperty("text-allow-overlap", textAllowOverlap.value)
        }
        if (textAnchor.notInitial) {
          setProperty("text-anchor", textAnchor.value)
        }
        if (textField.notInitial) {
          setProperty("text-field", textField.value)
        }
        if (textFont.notInitial) {
          setProperty("text-font", textFont.value)
        }
        if (textIgnorePlacement.notInitial) {
          setProperty("text-ignore-placement", textIgnorePlacement.value)
        }
        if (textJustify.notInitial) {
          setProperty("text-justify", textJustify.value)
        }
        if (textKeepUpright.notInitial) {
          setProperty("text-keep-upright", textKeepUpright.value)
        }
        if (textLetterSpacing.notInitial) {
          setProperty("text-letter-spacing", textLetterSpacing.value)
        }
        if (textLineHeight.notInitial) {
          setProperty("text-line-height", textLineHeight.value)
        }
        if (textMaxAngle.notInitial) {
          setProperty("text-max-angle", textMaxAngle.value)
        }
        if (textMaxWidth.notInitial) {
          setProperty("text-max-width", textMaxWidth.value)
        }
        if (textOffset.notInitial) {
          setProperty("text-offset", textOffset.value)
        }
        if (textOptional.notInitial) {
          setProperty("text-optional", textOptional.value)
        }
        if (textPadding.notInitial) {
          setProperty("text-padding", textPadding.value)
        }
        if (textPitchAlignment.notInitial) {
          setProperty("text-pitch-alignment", textPitchAlignment.value)
        }
        if (textRadialOffset.notInitial) {
          setProperty("text-radial-offset", textRadialOffset.value)
        }
        if (textRotate.notInitial) {
          setProperty("text-rotate", textRotate.value)
        }
        if (textRotationAlignment.notInitial) {
          setProperty("text-rotation-alignment", textRotationAlignment.value)
        }
        if (textSize.notInitial) {
          setProperty("text-size", textSize.value)
        }
        if (textTransform.notInitial) {
          setProperty("text-transform", textTransform.value)
        }
        if (textVariableAnchor.notInitial) {
          setProperty("text-variable-anchor", textVariableAnchor.value)
        }
        if (textWritingMode.notInitial) {
          setProperty("text-writing-mode", textWritingMode.value)
        }
        if (iconColor.notInitial) {
          setProperty("icon-color", iconColor.value)
        }
        if (iconColorTransition.notInitial) {
          setProperty("icon-color-transition", iconColorTransition.value)
        }
        if (iconColorSaturation.notInitial) {
          setProperty("icon-color-saturation", iconColorSaturation.value)
        }
        if (iconColorSaturationTransition.notInitial) {
          setProperty("icon-color-saturation-transition", iconColorSaturationTransition.value)
        }
        if (iconEmissiveStrength.notInitial) {
          setProperty("icon-emissive-strength", iconEmissiveStrength.value)
        }
        if (iconEmissiveStrengthTransition.notInitial) {
          setProperty("icon-emissive-strength-transition", iconEmissiveStrengthTransition.value)
        }
        if (iconHaloBlur.notInitial) {
          setProperty("icon-halo-blur", iconHaloBlur.value)
        }
        if (iconHaloBlurTransition.notInitial) {
          setProperty("icon-halo-blur-transition", iconHaloBlurTransition.value)
        }
        if (iconHaloColor.notInitial) {
          setProperty("icon-halo-color", iconHaloColor.value)
        }
        if (iconHaloColorTransition.notInitial) {
          setProperty("icon-halo-color-transition", iconHaloColorTransition.value)
        }
        if (iconHaloWidth.notInitial) {
          setProperty("icon-halo-width", iconHaloWidth.value)
        }
        if (iconHaloWidthTransition.notInitial) {
          setProperty("icon-halo-width-transition", iconHaloWidthTransition.value)
        }
        if (iconImageCrossFade.notInitial) {
          setProperty("icon-image-cross-fade", iconImageCrossFade.value)
        }
        if (iconImageCrossFadeTransition.notInitial) {
          setProperty("icon-image-cross-fade-transition", iconImageCrossFadeTransition.value)
        }
        if (iconOpacity.notInitial) {
          setProperty("icon-opacity", iconOpacity.value)
        }
        if (iconOpacityTransition.notInitial) {
          setProperty("icon-opacity-transition", iconOpacityTransition.value)
        }
        if (iconTranslate.notInitial) {
          setProperty("icon-translate", iconTranslate.value)
        }
        if (iconTranslateTransition.notInitial) {
          setProperty("icon-translate-transition", iconTranslateTransition.value)
        }
        if (iconTranslateAnchor.notInitial) {
          setProperty("icon-translate-anchor", iconTranslateAnchor.value)
        }
        if (textColor.notInitial) {
          setProperty("text-color", textColor.value)
        }
        if (textColorTransition.notInitial) {
          setProperty("text-color-transition", textColorTransition.value)
        }
        if (textEmissiveStrength.notInitial) {
          setProperty("text-emissive-strength", textEmissiveStrength.value)
        }
        if (textEmissiveStrengthTransition.notInitial) {
          setProperty("text-emissive-strength-transition", textEmissiveStrengthTransition.value)
        }
        if (textHaloBlur.notInitial) {
          setProperty("text-halo-blur", textHaloBlur.value)
        }
        if (textHaloBlurTransition.notInitial) {
          setProperty("text-halo-blur-transition", textHaloBlurTransition.value)
        }
        if (textHaloColor.notInitial) {
          setProperty("text-halo-color", textHaloColor.value)
        }
        if (textHaloColorTransition.notInitial) {
          setProperty("text-halo-color-transition", textHaloColorTransition.value)
        }
        if (textHaloWidth.notInitial) {
          setProperty("text-halo-width", textHaloWidth.value)
        }
        if (textHaloWidthTransition.notInitial) {
          setProperty("text-halo-width-transition", textHaloWidthTransition.value)
        }
        if (textOpacity.notInitial) {
          setProperty("text-opacity", textOpacity.value)
        }
        if (textOpacityTransition.notInitial) {
          setProperty("text-opacity-transition", textOpacityTransition.value)
        }
        if (textTranslate.notInitial) {
          setProperty("text-translate", textTranslate.value)
        }
        if (textTranslateTransition.notInitial) {
          setProperty("text-translate-transition", textTranslateTransition.value)
        }
        if (textTranslateAnchor.notInitial) {
          setProperty("text-translate-anchor", textTranslateAnchor.value)
        }
        if (visibility.notInitial) {
          setProperty("visibility", visibility.value)
        }
        if (minZoom.notInitial) {
          setProperty("min-zoom", minZoom.value)
        }
        if (maxZoom.notInitial) {
          setProperty("max-zoom", maxZoom.value)
        }
        if (sourceLayer.notInitial) {
          setProperty("source-layer", sourceLayer.value)
        }
        if (filter.notInitial) {
          setProperty("filter", filter.value)
        }
      }
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(iconAllowOverlap) {
        setProperty("icon-allow-overlap", iconAllowOverlap.value)
      }
      update(iconAnchor) {
        setProperty("icon-anchor", iconAnchor.value)
      }
      update(iconIgnorePlacement) {
        setProperty("icon-ignore-placement", iconIgnorePlacement.value)
      }
      update(iconImage) {
        iconImage.styleImage?.let {
          addImage(it)
        }
        setProperty("icon-image", iconImage.value)
      }
      update(iconKeepUpright) {
        setProperty("icon-keep-upright", iconKeepUpright.value)
      }
      update(iconOffset) {
        setProperty("icon-offset", iconOffset.value)
      }
      update(iconOptional) {
        setProperty("icon-optional", iconOptional.value)
      }
      update(iconPadding) {
        setProperty("icon-padding", iconPadding.value)
      }
      update(iconPitchAlignment) {
        setProperty("icon-pitch-alignment", iconPitchAlignment.value)
      }
      update(iconRotate) {
        setProperty("icon-rotate", iconRotate.value)
      }
      update(iconRotationAlignment) {
        setProperty("icon-rotation-alignment", iconRotationAlignment.value)
      }
      update(iconSize) {
        setProperty("icon-size", iconSize.value)
      }
      update(iconTextFit) {
        setProperty("icon-text-fit", iconTextFit.value)
      }
      update(iconTextFitPadding) {
        setProperty("icon-text-fit-padding", iconTextFitPadding.value)
      }
      update(symbolAvoidEdges) {
        setProperty("symbol-avoid-edges", symbolAvoidEdges.value)
      }
      update(symbolPlacement) {
        setProperty("symbol-placement", symbolPlacement.value)
      }
      update(symbolSortKey) {
        setProperty("symbol-sort-key", symbolSortKey.value)
      }
      update(symbolSpacing) {
        setProperty("symbol-spacing", symbolSpacing.value)
      }
      update(symbolZElevate) {
        setProperty("symbol-z-elevate", symbolZElevate.value)
      }
      update(symbolZOrder) {
        setProperty("symbol-z-order", symbolZOrder.value)
      }
      update(textAllowOverlap) {
        setProperty("text-allow-overlap", textAllowOverlap.value)
      }
      update(textAnchor) {
        setProperty("text-anchor", textAnchor.value)
      }
      update(textField) {
        setProperty("text-field", textField.value)
      }
      update(textFont) {
        setProperty("text-font", textFont.value)
      }
      update(textIgnorePlacement) {
        setProperty("text-ignore-placement", textIgnorePlacement.value)
      }
      update(textJustify) {
        setProperty("text-justify", textJustify.value)
      }
      update(textKeepUpright) {
        setProperty("text-keep-upright", textKeepUpright.value)
      }
      update(textLetterSpacing) {
        setProperty("text-letter-spacing", textLetterSpacing.value)
      }
      update(textLineHeight) {
        setProperty("text-line-height", textLineHeight.value)
      }
      update(textMaxAngle) {
        setProperty("text-max-angle", textMaxAngle.value)
      }
      update(textMaxWidth) {
        setProperty("text-max-width", textMaxWidth.value)
      }
      update(textOffset) {
        setProperty("text-offset", textOffset.value)
      }
      update(textOptional) {
        setProperty("text-optional", textOptional.value)
      }
      update(textPadding) {
        setProperty("text-padding", textPadding.value)
      }
      update(textPitchAlignment) {
        setProperty("text-pitch-alignment", textPitchAlignment.value)
      }
      update(textRadialOffset) {
        setProperty("text-radial-offset", textRadialOffset.value)
      }
      update(textRotate) {
        setProperty("text-rotate", textRotate.value)
      }
      update(textRotationAlignment) {
        setProperty("text-rotation-alignment", textRotationAlignment.value)
      }
      update(textSize) {
        setProperty("text-size", textSize.value)
      }
      update(textTransform) {
        setProperty("text-transform", textTransform.value)
      }
      update(textVariableAnchor) {
        setProperty("text-variable-anchor", textVariableAnchor.value)
      }
      update(textWritingMode) {
        setProperty("text-writing-mode", textWritingMode.value)
      }
      update(iconColor) {
        setProperty("icon-color", iconColor.value)
      }
      update(iconColorTransition) {
        setProperty("icon-color-transition", iconColorTransition.value)
      }
      update(iconColorSaturation) {
        setProperty("icon-color-saturation", iconColorSaturation.value)
      }
      update(iconColorSaturationTransition) {
        setProperty("icon-color-saturation-transition", iconColorSaturationTransition.value)
      }
      update(iconEmissiveStrength) {
        setProperty("icon-emissive-strength", iconEmissiveStrength.value)
      }
      update(iconEmissiveStrengthTransition) {
        setProperty("icon-emissive-strength-transition", iconEmissiveStrengthTransition.value)
      }
      update(iconHaloBlur) {
        setProperty("icon-halo-blur", iconHaloBlur.value)
      }
      update(iconHaloBlurTransition) {
        setProperty("icon-halo-blur-transition", iconHaloBlurTransition.value)
      }
      update(iconHaloColor) {
        setProperty("icon-halo-color", iconHaloColor.value)
      }
      update(iconHaloColorTransition) {
        setProperty("icon-halo-color-transition", iconHaloColorTransition.value)
      }
      update(iconHaloWidth) {
        setProperty("icon-halo-width", iconHaloWidth.value)
      }
      update(iconHaloWidthTransition) {
        setProperty("icon-halo-width-transition", iconHaloWidthTransition.value)
      }
      update(iconImageCrossFade) {
        setProperty("icon-image-cross-fade", iconImageCrossFade.value)
      }
      update(iconImageCrossFadeTransition) {
        setProperty("icon-image-cross-fade-transition", iconImageCrossFadeTransition.value)
      }
      update(iconOpacity) {
        setProperty("icon-opacity", iconOpacity.value)
      }
      update(iconOpacityTransition) {
        setProperty("icon-opacity-transition", iconOpacityTransition.value)
      }
      update(iconTranslate) {
        setProperty("icon-translate", iconTranslate.value)
      }
      update(iconTranslateTransition) {
        setProperty("icon-translate-transition", iconTranslateTransition.value)
      }
      update(iconTranslateAnchor) {
        setProperty("icon-translate-anchor", iconTranslateAnchor.value)
      }
      update(textColor) {
        setProperty("text-color", textColor.value)
      }
      update(textColorTransition) {
        setProperty("text-color-transition", textColorTransition.value)
      }
      update(textEmissiveStrength) {
        setProperty("text-emissive-strength", textEmissiveStrength.value)
      }
      update(textEmissiveStrengthTransition) {
        setProperty("text-emissive-strength-transition", textEmissiveStrengthTransition.value)
      }
      update(textHaloBlur) {
        setProperty("text-halo-blur", textHaloBlur.value)
      }
      update(textHaloBlurTransition) {
        setProperty("text-halo-blur-transition", textHaloBlurTransition.value)
      }
      update(textHaloColor) {
        setProperty("text-halo-color", textHaloColor.value)
      }
      update(textHaloColorTransition) {
        setProperty("text-halo-color-transition", textHaloColorTransition.value)
      }
      update(textHaloWidth) {
        setProperty("text-halo-width", textHaloWidth.value)
      }
      update(textHaloWidthTransition) {
        setProperty("text-halo-width-transition", textHaloWidthTransition.value)
      }
      update(textOpacity) {
        setProperty("text-opacity", textOpacity.value)
      }
      update(textOpacityTransition) {
        setProperty("text-opacity-transition", textOpacityTransition.value)
      }
      update(textTranslate) {
        setProperty("text-translate", textTranslate.value)
      }
      update(textTranslateTransition) {
        setProperty("text-translate-transition", textTranslateTransition.value)
      }
      update(textTranslateAnchor) {
        setProperty("text-translate-anchor", textTranslateAnchor.value)
      }
      update(visibility) {
        setProperty("visibility", visibility.value)
      }
      update(minZoom) {
        setProperty("min-zoom", minZoom.value)
      }
      update(maxZoom) {
        setProperty("max-zoom", maxZoom.value)
      }
      update(sourceLayer) {
        setProperty("source-layer", sourceLayer.value)
      }
      update(filter) {
        setProperty("filter", filter.value)
      }
    }
  )
  sourceState.UpdateProperties()
}
// End of generated file.