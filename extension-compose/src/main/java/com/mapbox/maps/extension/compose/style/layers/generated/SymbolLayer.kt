// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * An icon or a text label.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-symbol)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 * @param iconAllowOverlap If true, the icon will be visible even if it collides with other previously drawn symbols.
 * @param iconAnchor Part of the icon placed closest to the anchor.
 * @param iconIgnorePlacement If true, other symbols can be visible even if they collide with the icon.
 * @param iconImage Name of image in sprite to use for drawing an image background.
 * @param iconKeepUpright If true, the icon may be flipped to prevent it from being rendered upside-down.
 * @param iconOffset Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
 * @param iconOptional If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
 * @param iconPadding Size of the additional area around the icon bounding box used for detecting symbol collisions.
 * @param iconPitchAlignment Orientation of icon when map is pitched.
 * @param iconRotate Rotates the icon clockwise.
 * @param iconRotationAlignment In combination with `symbol-placement`, determines the rotation behavior of icons.
 * @param iconSize Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
 * @param iconTextFit Scales the icon to fit around the associated text.
 * @param iconTextFitPadding Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
 * @param symbolAvoidEdges If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
 * @param symbolPlacement Label placement relative to its geometry.
 * @param symbolSortKey Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
 * @param symbolSpacing Distance between two symbol anchors.
 * @param symbolZElevate Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when `fill-extrusion-height` is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or `symbol-sort-key` are applied.
 * @param symbolZOrder Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
 * @param textAllowOverlap If true, the text will be visible even if it collides with other previously drawn symbols.
 * @param textAnchor Part of the text placed closest to the anchor.
 * @param textField Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
 * @param textFont Font stack to use for displaying text.
 * @param textIgnorePlacement If true, other symbols can be visible even if they collide with the text.
 * @param textJustify Text justification options.
 * @param textKeepUpright If true, the text may be flipped vertically to prevent it from being rendered upside-down.
 * @param textLetterSpacing Text tracking amount.
 * @param textLineHeight Text leading value for multi-line text.
 * @param textMaxAngle Maximum angle change between adjacent characters.
 * @param textMaxWidth The maximum line width for text wrapping.
 * @param textOffset Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
 * @param textOptional If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
 * @param textPadding Size of the additional area around the text bounding box used for detecting symbol collisions.
 * @param textPitchAlignment Orientation of text when map is pitched.
 * @param textRadialOffset Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
 * @param textRotate Rotates the text clockwise.
 * @param textRotationAlignment In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
 * @param textSize Font size.
 * @param textTransform Specifies how to capitalize text, similar to the CSS `text-transform` property.
 * @param textVariableAnchor To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
 * @param textWritingMode The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
 * @param iconColor The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
 * @param iconColorSaturation Controls saturation level of the symbol icon. With the default value of 1 the icon color is preserved while with a value of 0 it is fully desaturated and looks black and white.
 * @param iconEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param iconHaloBlur Fade out the halo towards the outside.
 * @param iconHaloColor The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
 * @param iconHaloWidth Distance of halo to the icon outline.
 * @param iconImageCrossFade Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together.
 * @param iconOpacity The opacity at which the icon will be drawn.
 * @param iconTranslate Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
 * @param iconTranslateAnchor Controls the frame of reference for `icon-translate`.
 * @param textColor The color with which the text will be drawn.
 * @param textEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param textHaloBlur The halo's fadeout distance towards the outside.
 * @param textHaloColor The color of the text's halo, which helps it stand out from backgrounds.
 * @param textHaloWidth Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
 * @param textOpacity The opacity at which the text will be drawn.
 * @param textTranslate Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
 * @param textTranslateAnchor Controls the frame of reference for `text-translate`.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun SymbolLayer(
  layerId: String,
  sourceId: String,
  iconAllowOverlap: IconAllowOverlap = IconAllowOverlap.default,
  iconAnchor: IconAnchor = IconAnchor.default,
  iconIgnorePlacement: IconIgnorePlacement = IconIgnorePlacement.default,
  iconImage: IconImage = IconImage.default,
  iconKeepUpright: IconKeepUpright = IconKeepUpright.default,
  iconOffset: IconOffset = IconOffset.default,
  iconOptional: IconOptional = IconOptional.default,
  iconPadding: IconPadding = IconPadding.default,
  iconPitchAlignment: IconPitchAlignment = IconPitchAlignment.default,
  iconRotate: IconRotate = IconRotate.default,
  iconRotationAlignment: IconRotationAlignment = IconRotationAlignment.default,
  iconSize: IconSize = IconSize.default,
  iconTextFit: IconTextFit = IconTextFit.default,
  iconTextFitPadding: IconTextFitPadding = IconTextFitPadding.default,
  symbolAvoidEdges: SymbolAvoidEdges = SymbolAvoidEdges.default,
  symbolPlacement: SymbolPlacement = SymbolPlacement.default,
  symbolSortKey: SymbolSortKey = SymbolSortKey.default,
  symbolSpacing: SymbolSpacing = SymbolSpacing.default,
  symbolZElevate: SymbolZElevate = SymbolZElevate.default,
  symbolZOrder: SymbolZOrder = SymbolZOrder.default,
  textAllowOverlap: TextAllowOverlap = TextAllowOverlap.default,
  textAnchor: TextAnchor = TextAnchor.default,
  textField: TextField = TextField.default,
  textFont: TextFont = TextFont.default,
  textIgnorePlacement: TextIgnorePlacement = TextIgnorePlacement.default,
  textJustify: TextJustify = TextJustify.default,
  textKeepUpright: TextKeepUpright = TextKeepUpright.default,
  textLetterSpacing: TextLetterSpacing = TextLetterSpacing.default,
  textLineHeight: TextLineHeight = TextLineHeight.default,
  textMaxAngle: TextMaxAngle = TextMaxAngle.default,
  textMaxWidth: TextMaxWidth = TextMaxWidth.default,
  textOffset: TextOffset = TextOffset.default,
  textOptional: TextOptional = TextOptional.default,
  textPadding: TextPadding = TextPadding.default,
  textPitchAlignment: TextPitchAlignment = TextPitchAlignment.default,
  textRadialOffset: TextRadialOffset = TextRadialOffset.default,
  textRotate: TextRotate = TextRotate.default,
  textRotationAlignment: TextRotationAlignment = TextRotationAlignment.default,
  textSize: TextSize = TextSize.default,
  textTransform: TextTransform = TextTransform.default,
  textVariableAnchor: TextVariableAnchor = TextVariableAnchor.default,
  textWritingMode: TextWritingMode = TextWritingMode.default,
  iconColor: IconColor = IconColor.default,
  iconColorTransition: Transition = Transition.default,
  iconColorSaturation: IconColorSaturation = IconColorSaturation.default,
  iconColorSaturationTransition: Transition = Transition.default,
  iconEmissiveStrength: IconEmissiveStrength = IconEmissiveStrength.default,
  iconEmissiveStrengthTransition: Transition = Transition.default,
  iconHaloBlur: IconHaloBlur = IconHaloBlur.default,
  iconHaloBlurTransition: Transition = Transition.default,
  iconHaloColor: IconHaloColor = IconHaloColor.default,
  iconHaloColorTransition: Transition = Transition.default,
  iconHaloWidth: IconHaloWidth = IconHaloWidth.default,
  iconHaloWidthTransition: Transition = Transition.default,
  iconImageCrossFade: IconImageCrossFade = IconImageCrossFade.default,
  iconImageCrossFadeTransition: Transition = Transition.default,
  iconOpacity: IconOpacity = IconOpacity.default,
  iconOpacityTransition: Transition = Transition.default,
  iconTranslate: IconTranslate = IconTranslate.default,
  iconTranslateTransition: Transition = Transition.default,
  iconTranslateAnchor: IconTranslateAnchor = IconTranslateAnchor.default,
  textColor: TextColor = TextColor.default,
  textColorTransition: Transition = Transition.default,
  textEmissiveStrength: TextEmissiveStrength = TextEmissiveStrength.default,
  textEmissiveStrengthTransition: Transition = Transition.default,
  textHaloBlur: TextHaloBlur = TextHaloBlur.default,
  textHaloBlurTransition: Transition = Transition.default,
  textHaloColor: TextHaloColor = TextHaloColor.default,
  textHaloColorTransition: Transition = Transition.default,
  textHaloWidth: TextHaloWidth = TextHaloWidth.default,
  textHaloWidthTransition: Transition = Transition.default,
  textOpacity: TextOpacity = TextOpacity.default,
  textOpacityTransition: Transition = Transition.default,
  textTranslate: TextTranslate = TextTranslate.default,
  textTranslateTransition: Transition = Transition.default,
  textTranslateAnchor: TextTranslateAnchor = TextTranslateAnchor.default,
  visibility: Visibility = Visibility.default,
  minZoom: MinZoom = MinZoom.default,
  maxZoom: MaxZoom = MaxZoom.default,
  sourceLayer: SourceLayer = SourceLayer.default,
  filter: Filter = Filter.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SymbolLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "symbol",
        layerId = layerId,
        sourceId = sourceId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (iconAllowOverlap != IconAllowOverlap.default) {
          setProperty(IconAllowOverlap.NAME, iconAllowOverlap.value)
        }
        if (iconAnchor != IconAnchor.default) {
          setProperty(IconAnchor.NAME, iconAnchor.value)
        }
        if (iconIgnorePlacement != IconIgnorePlacement.default) {
          setProperty(IconIgnorePlacement.NAME, iconIgnorePlacement.value)
        }
        if (iconImage != IconImage.default) {
          setProperty(IconImage.NAME, iconImage.value)
        }
        if (iconKeepUpright != IconKeepUpright.default) {
          setProperty(IconKeepUpright.NAME, iconKeepUpright.value)
        }
        if (iconOffset != IconOffset.default) {
          setProperty(IconOffset.NAME, iconOffset.value)
        }
        if (iconOptional != IconOptional.default) {
          setProperty(IconOptional.NAME, iconOptional.value)
        }
        if (iconPadding != IconPadding.default) {
          setProperty(IconPadding.NAME, iconPadding.value)
        }
        if (iconPitchAlignment != IconPitchAlignment.default) {
          setProperty(IconPitchAlignment.NAME, iconPitchAlignment.value)
        }
        if (iconRotate != IconRotate.default) {
          setProperty(IconRotate.NAME, iconRotate.value)
        }
        if (iconRotationAlignment != IconRotationAlignment.default) {
          setProperty(IconRotationAlignment.NAME, iconRotationAlignment.value)
        }
        if (iconSize != IconSize.default) {
          setProperty(IconSize.NAME, iconSize.value)
        }
        if (iconTextFit != IconTextFit.default) {
          setProperty(IconTextFit.NAME, iconTextFit.value)
        }
        if (iconTextFitPadding != IconTextFitPadding.default) {
          setProperty(IconTextFitPadding.NAME, iconTextFitPadding.value)
        }
        if (symbolAvoidEdges != SymbolAvoidEdges.default) {
          setProperty(SymbolAvoidEdges.NAME, symbolAvoidEdges.value)
        }
        if (symbolPlacement != SymbolPlacement.default) {
          setProperty(SymbolPlacement.NAME, symbolPlacement.value)
        }
        if (symbolSortKey != SymbolSortKey.default) {
          setProperty(SymbolSortKey.NAME, symbolSortKey.value)
        }
        if (symbolSpacing != SymbolSpacing.default) {
          setProperty(SymbolSpacing.NAME, symbolSpacing.value)
        }
        if (symbolZElevate != SymbolZElevate.default) {
          setProperty(SymbolZElevate.NAME, symbolZElevate.value)
        }
        if (symbolZOrder != SymbolZOrder.default) {
          setProperty(SymbolZOrder.NAME, symbolZOrder.value)
        }
        if (textAllowOverlap != TextAllowOverlap.default) {
          setProperty(TextAllowOverlap.NAME, textAllowOverlap.value)
        }
        if (textAnchor != TextAnchor.default) {
          setProperty(TextAnchor.NAME, textAnchor.value)
        }
        if (textField != TextField.default) {
          setProperty(TextField.NAME, textField.value)
        }
        if (textFont != TextFont.default) {
          setProperty(TextFont.NAME, textFont.value)
        }
        if (textIgnorePlacement != TextIgnorePlacement.default) {
          setProperty(TextIgnorePlacement.NAME, textIgnorePlacement.value)
        }
        if (textJustify != TextJustify.default) {
          setProperty(TextJustify.NAME, textJustify.value)
        }
        if (textKeepUpright != TextKeepUpright.default) {
          setProperty(TextKeepUpright.NAME, textKeepUpright.value)
        }
        if (textLetterSpacing != TextLetterSpacing.default) {
          setProperty(TextLetterSpacing.NAME, textLetterSpacing.value)
        }
        if (textLineHeight != TextLineHeight.default) {
          setProperty(TextLineHeight.NAME, textLineHeight.value)
        }
        if (textMaxAngle != TextMaxAngle.default) {
          setProperty(TextMaxAngle.NAME, textMaxAngle.value)
        }
        if (textMaxWidth != TextMaxWidth.default) {
          setProperty(TextMaxWidth.NAME, textMaxWidth.value)
        }
        if (textOffset != TextOffset.default) {
          setProperty(TextOffset.NAME, textOffset.value)
        }
        if (textOptional != TextOptional.default) {
          setProperty(TextOptional.NAME, textOptional.value)
        }
        if (textPadding != TextPadding.default) {
          setProperty(TextPadding.NAME, textPadding.value)
        }
        if (textPitchAlignment != TextPitchAlignment.default) {
          setProperty(TextPitchAlignment.NAME, textPitchAlignment.value)
        }
        if (textRadialOffset != TextRadialOffset.default) {
          setProperty(TextRadialOffset.NAME, textRadialOffset.value)
        }
        if (textRotate != TextRotate.default) {
          setProperty(TextRotate.NAME, textRotate.value)
        }
        if (textRotationAlignment != TextRotationAlignment.default) {
          setProperty(TextRotationAlignment.NAME, textRotationAlignment.value)
        }
        if (textSize != TextSize.default) {
          setProperty(TextSize.NAME, textSize.value)
        }
        if (textTransform != TextTransform.default) {
          setProperty(TextTransform.NAME, textTransform.value)
        }
        if (textVariableAnchor != TextVariableAnchor.default) {
          setProperty(TextVariableAnchor.NAME, textVariableAnchor.value)
        }
        if (textWritingMode != TextWritingMode.default) {
          setProperty(TextWritingMode.NAME, textWritingMode.value)
        }
        if (iconColor != IconColor.default) {
          setProperty(IconColor.NAME, iconColor.value)
        }
        if (iconColorTransition != Transition.default) {
          setProperty(IconColor.TRANSITION_NAME, iconColorTransition.value)
        }
        if (iconColorSaturation != IconColorSaturation.default) {
          setProperty(IconColorSaturation.NAME, iconColorSaturation.value)
        }
        if (iconColorSaturationTransition != Transition.default) {
          setProperty(IconColorSaturation.TRANSITION_NAME, iconColorSaturationTransition.value)
        }
        if (iconEmissiveStrength != IconEmissiveStrength.default) {
          setProperty(IconEmissiveStrength.NAME, iconEmissiveStrength.value)
        }
        if (iconEmissiveStrengthTransition != Transition.default) {
          setProperty(IconEmissiveStrength.TRANSITION_NAME, iconEmissiveStrengthTransition.value)
        }
        if (iconHaloBlur != IconHaloBlur.default) {
          setProperty(IconHaloBlur.NAME, iconHaloBlur.value)
        }
        if (iconHaloBlurTransition != Transition.default) {
          setProperty(IconHaloBlur.TRANSITION_NAME, iconHaloBlurTransition.value)
        }
        if (iconHaloColor != IconHaloColor.default) {
          setProperty(IconHaloColor.NAME, iconHaloColor.value)
        }
        if (iconHaloColorTransition != Transition.default) {
          setProperty(IconHaloColor.TRANSITION_NAME, iconHaloColorTransition.value)
        }
        if (iconHaloWidth != IconHaloWidth.default) {
          setProperty(IconHaloWidth.NAME, iconHaloWidth.value)
        }
        if (iconHaloWidthTransition != Transition.default) {
          setProperty(IconHaloWidth.TRANSITION_NAME, iconHaloWidthTransition.value)
        }
        if (iconImageCrossFade != IconImageCrossFade.default) {
          setProperty(IconImageCrossFade.NAME, iconImageCrossFade.value)
        }
        if (iconImageCrossFadeTransition != Transition.default) {
          setProperty(IconImageCrossFade.TRANSITION_NAME, iconImageCrossFadeTransition.value)
        }
        if (iconOpacity != IconOpacity.default) {
          setProperty(IconOpacity.NAME, iconOpacity.value)
        }
        if (iconOpacityTransition != Transition.default) {
          setProperty(IconOpacity.TRANSITION_NAME, iconOpacityTransition.value)
        }
        if (iconTranslate != IconTranslate.default) {
          setProperty(IconTranslate.NAME, iconTranslate.value)
        }
        if (iconTranslateTransition != Transition.default) {
          setProperty(IconTranslate.TRANSITION_NAME, iconTranslateTransition.value)
        }
        if (iconTranslateAnchor != IconTranslateAnchor.default) {
          setProperty(IconTranslateAnchor.NAME, iconTranslateAnchor.value)
        }
        if (textColor != TextColor.default) {
          setProperty(TextColor.NAME, textColor.value)
        }
        if (textColorTransition != Transition.default) {
          setProperty(TextColor.TRANSITION_NAME, textColorTransition.value)
        }
        if (textEmissiveStrength != TextEmissiveStrength.default) {
          setProperty(TextEmissiveStrength.NAME, textEmissiveStrength.value)
        }
        if (textEmissiveStrengthTransition != Transition.default) {
          setProperty(TextEmissiveStrength.TRANSITION_NAME, textEmissiveStrengthTransition.value)
        }
        if (textHaloBlur != TextHaloBlur.default) {
          setProperty(TextHaloBlur.NAME, textHaloBlur.value)
        }
        if (textHaloBlurTransition != Transition.default) {
          setProperty(TextHaloBlur.TRANSITION_NAME, textHaloBlurTransition.value)
        }
        if (textHaloColor != TextHaloColor.default) {
          setProperty(TextHaloColor.NAME, textHaloColor.value)
        }
        if (textHaloColorTransition != Transition.default) {
          setProperty(TextHaloColor.TRANSITION_NAME, textHaloColorTransition.value)
        }
        if (textHaloWidth != TextHaloWidth.default) {
          setProperty(TextHaloWidth.NAME, textHaloWidth.value)
        }
        if (textHaloWidthTransition != Transition.default) {
          setProperty(TextHaloWidth.TRANSITION_NAME, textHaloWidthTransition.value)
        }
        if (textOpacity != TextOpacity.default) {
          setProperty(TextOpacity.NAME, textOpacity.value)
        }
        if (textOpacityTransition != Transition.default) {
          setProperty(TextOpacity.TRANSITION_NAME, textOpacityTransition.value)
        }
        if (textTranslate != TextTranslate.default) {
          setProperty(TextTranslate.NAME, textTranslate.value)
        }
        if (textTranslateTransition != Transition.default) {
          setProperty(TextTranslate.TRANSITION_NAME, textTranslateTransition.value)
        }
        if (textTranslateAnchor != TextTranslateAnchor.default) {
          setProperty(TextTranslateAnchor.NAME, textTranslateAnchor.value)
        }
        if (visibility != Visibility.default) {
          setProperty(Visibility.NAME, visibility.value)
        }
        if (minZoom != MinZoom.default) {
          setProperty(MinZoom.NAME, minZoom.value)
        }
        if (maxZoom != MaxZoom.default) {
          setProperty(MaxZoom.NAME, maxZoom.value)
        }
        if (sourceLayer != SourceLayer.default) {
          setProperty(SourceLayer.NAME, sourceLayer.value)
        }
        if (filter != Filter.default) {
          setProperty(Filter.NAME, filter.value)
        }
      }
      update(layerId) {
        setConstructorProperty("id", Value(layerId))
      }
      update(sourceId) {
        setConstructorProperty("source", Value(sourceId))
      }
      update(iconAllowOverlap) {
        setProperty(IconAllowOverlap.NAME, iconAllowOverlap.value)
      }
      update(iconAnchor) {
        setProperty(IconAnchor.NAME, iconAnchor.value)
      }
      update(iconIgnorePlacement) {
        setProperty(IconIgnorePlacement.NAME, iconIgnorePlacement.value)
      }
      update(iconImage) {
        setProperty(IconImage.NAME, iconImage.value)
      }
      update(iconKeepUpright) {
        setProperty(IconKeepUpright.NAME, iconKeepUpright.value)
      }
      update(iconOffset) {
        setProperty(IconOffset.NAME, iconOffset.value)
      }
      update(iconOptional) {
        setProperty(IconOptional.NAME, iconOptional.value)
      }
      update(iconPadding) {
        setProperty(IconPadding.NAME, iconPadding.value)
      }
      update(iconPitchAlignment) {
        setProperty(IconPitchAlignment.NAME, iconPitchAlignment.value)
      }
      update(iconRotate) {
        setProperty(IconRotate.NAME, iconRotate.value)
      }
      update(iconRotationAlignment) {
        setProperty(IconRotationAlignment.NAME, iconRotationAlignment.value)
      }
      update(iconSize) {
        setProperty(IconSize.NAME, iconSize.value)
      }
      update(iconTextFit) {
        setProperty(IconTextFit.NAME, iconTextFit.value)
      }
      update(iconTextFitPadding) {
        setProperty(IconTextFitPadding.NAME, iconTextFitPadding.value)
      }
      update(symbolAvoidEdges) {
        setProperty(SymbolAvoidEdges.NAME, symbolAvoidEdges.value)
      }
      update(symbolPlacement) {
        setProperty(SymbolPlacement.NAME, symbolPlacement.value)
      }
      update(symbolSortKey) {
        setProperty(SymbolSortKey.NAME, symbolSortKey.value)
      }
      update(symbolSpacing) {
        setProperty(SymbolSpacing.NAME, symbolSpacing.value)
      }
      update(symbolZElevate) {
        setProperty(SymbolZElevate.NAME, symbolZElevate.value)
      }
      update(symbolZOrder) {
        setProperty(SymbolZOrder.NAME, symbolZOrder.value)
      }
      update(textAllowOverlap) {
        setProperty(TextAllowOverlap.NAME, textAllowOverlap.value)
      }
      update(textAnchor) {
        setProperty(TextAnchor.NAME, textAnchor.value)
      }
      update(textField) {
        setProperty(TextField.NAME, textField.value)
      }
      update(textFont) {
        setProperty(TextFont.NAME, textFont.value)
      }
      update(textIgnorePlacement) {
        setProperty(TextIgnorePlacement.NAME, textIgnorePlacement.value)
      }
      update(textJustify) {
        setProperty(TextJustify.NAME, textJustify.value)
      }
      update(textKeepUpright) {
        setProperty(TextKeepUpright.NAME, textKeepUpright.value)
      }
      update(textLetterSpacing) {
        setProperty(TextLetterSpacing.NAME, textLetterSpacing.value)
      }
      update(textLineHeight) {
        setProperty(TextLineHeight.NAME, textLineHeight.value)
      }
      update(textMaxAngle) {
        setProperty(TextMaxAngle.NAME, textMaxAngle.value)
      }
      update(textMaxWidth) {
        setProperty(TextMaxWidth.NAME, textMaxWidth.value)
      }
      update(textOffset) {
        setProperty(TextOffset.NAME, textOffset.value)
      }
      update(textOptional) {
        setProperty(TextOptional.NAME, textOptional.value)
      }
      update(textPadding) {
        setProperty(TextPadding.NAME, textPadding.value)
      }
      update(textPitchAlignment) {
        setProperty(TextPitchAlignment.NAME, textPitchAlignment.value)
      }
      update(textRadialOffset) {
        setProperty(TextRadialOffset.NAME, textRadialOffset.value)
      }
      update(textRotate) {
        setProperty(TextRotate.NAME, textRotate.value)
      }
      update(textRotationAlignment) {
        setProperty(TextRotationAlignment.NAME, textRotationAlignment.value)
      }
      update(textSize) {
        setProperty(TextSize.NAME, textSize.value)
      }
      update(textTransform) {
        setProperty(TextTransform.NAME, textTransform.value)
      }
      update(textVariableAnchor) {
        setProperty(TextVariableAnchor.NAME, textVariableAnchor.value)
      }
      update(textWritingMode) {
        setProperty(TextWritingMode.NAME, textWritingMode.value)
      }
      update(iconColor) {
        setProperty(IconColor.NAME, iconColor.value)
      }
      update(iconColorTransition) {
        setProperty(IconColor.TRANSITION_NAME, iconColorTransition.value)
      }
      update(iconColorSaturation) {
        setProperty(IconColorSaturation.NAME, iconColorSaturation.value)
      }
      update(iconColorSaturationTransition) {
        setProperty(IconColorSaturation.TRANSITION_NAME, iconColorSaturationTransition.value)
      }
      update(iconEmissiveStrength) {
        setProperty(IconEmissiveStrength.NAME, iconEmissiveStrength.value)
      }
      update(iconEmissiveStrengthTransition) {
        setProperty(IconEmissiveStrength.TRANSITION_NAME, iconEmissiveStrengthTransition.value)
      }
      update(iconHaloBlur) {
        setProperty(IconHaloBlur.NAME, iconHaloBlur.value)
      }
      update(iconHaloBlurTransition) {
        setProperty(IconHaloBlur.TRANSITION_NAME, iconHaloBlurTransition.value)
      }
      update(iconHaloColor) {
        setProperty(IconHaloColor.NAME, iconHaloColor.value)
      }
      update(iconHaloColorTransition) {
        setProperty(IconHaloColor.TRANSITION_NAME, iconHaloColorTransition.value)
      }
      update(iconHaloWidth) {
        setProperty(IconHaloWidth.NAME, iconHaloWidth.value)
      }
      update(iconHaloWidthTransition) {
        setProperty(IconHaloWidth.TRANSITION_NAME, iconHaloWidthTransition.value)
      }
      update(iconImageCrossFade) {
        setProperty(IconImageCrossFade.NAME, iconImageCrossFade.value)
      }
      update(iconImageCrossFadeTransition) {
        setProperty(IconImageCrossFade.TRANSITION_NAME, iconImageCrossFadeTransition.value)
      }
      update(iconOpacity) {
        setProperty(IconOpacity.NAME, iconOpacity.value)
      }
      update(iconOpacityTransition) {
        setProperty(IconOpacity.TRANSITION_NAME, iconOpacityTransition.value)
      }
      update(iconTranslate) {
        setProperty(IconTranslate.NAME, iconTranslate.value)
      }
      update(iconTranslateTransition) {
        setProperty(IconTranslate.TRANSITION_NAME, iconTranslateTransition.value)
      }
      update(iconTranslateAnchor) {
        setProperty(IconTranslateAnchor.NAME, iconTranslateAnchor.value)
      }
      update(textColor) {
        setProperty(TextColor.NAME, textColor.value)
      }
      update(textColorTransition) {
        setProperty(TextColor.TRANSITION_NAME, textColorTransition.value)
      }
      update(textEmissiveStrength) {
        setProperty(TextEmissiveStrength.NAME, textEmissiveStrength.value)
      }
      update(textEmissiveStrengthTransition) {
        setProperty(TextEmissiveStrength.TRANSITION_NAME, textEmissiveStrengthTransition.value)
      }
      update(textHaloBlur) {
        setProperty(TextHaloBlur.NAME, textHaloBlur.value)
      }
      update(textHaloBlurTransition) {
        setProperty(TextHaloBlur.TRANSITION_NAME, textHaloBlurTransition.value)
      }
      update(textHaloColor) {
        setProperty(TextHaloColor.NAME, textHaloColor.value)
      }
      update(textHaloColorTransition) {
        setProperty(TextHaloColor.TRANSITION_NAME, textHaloColorTransition.value)
      }
      update(textHaloWidth) {
        setProperty(TextHaloWidth.NAME, textHaloWidth.value)
      }
      update(textHaloWidthTransition) {
        setProperty(TextHaloWidth.TRANSITION_NAME, textHaloWidthTransition.value)
      }
      update(textOpacity) {
        setProperty(TextOpacity.NAME, textOpacity.value)
      }
      update(textOpacityTransition) {
        setProperty(TextOpacity.TRANSITION_NAME, textOpacityTransition.value)
      }
      update(textTranslate) {
        setProperty(TextTranslate.NAME, textTranslate.value)
      }
      update(textTranslateTransition) {
        setProperty(TextTranslate.TRANSITION_NAME, textTranslateTransition.value)
      }
      update(textTranslateAnchor) {
        setProperty(TextTranslateAnchor.NAME, textTranslateAnchor.value)
      }
      update(visibility) {
        setProperty(Visibility.NAME, visibility.value)
      }
      update(minZoom) {
        setProperty(MinZoom.NAME, minZoom.value)
      }
      update(maxZoom) {
        setProperty(MaxZoom.NAME, maxZoom.value)
      }
      update(sourceLayer) {
        setProperty(SourceLayer.NAME, sourceLayer.value)
      }
      update(filter) {
        setProperty(Filter.NAME, filter.value)
      }
    }
  )
}
// End of generated file.