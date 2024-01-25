// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PointAnnotationManagerNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

/**
 * Composable function to add a [PointAnnotationGroup] to the Map.
 *
 * The [PointAnnotationGroup] is more performant than adding multiple [PointAnnotation] individually,
 * because the [PointAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 *
 * @param annotations List of [PointAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PointAnnotationCluster].
 * @param iconAllowOverlap If true, the icon will be visible even if it collides with other previously drawn symbols.
 * @param iconIgnorePlacement If true, other symbols can be visible even if they collide with the icon.
 * @param iconKeepUpright If true, the icon may be flipped to prevent it from being rendered upside-down.
 * @param iconOptional If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
 * @param iconPadding Size of the additional area around the icon bounding box used for detecting symbol collisions. The unit of iconPadding is in density-independent pixels.
 * @param iconPitchAlignment Orientation of icon when map is pitched.
 * @param iconRotationAlignment In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of icons.
 * @param symbolAvoidEdges If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
 * @param symbolPlacement Label placement relative to its geometry.
 * @param symbolSpacing Distance between two symbol anchors. The unit of symbolSpacing is in density-independent pixels.
 * @param symbolZElevate Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when {@link PropertyFactory#fillExtrusionHeight} is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or {@link PropertyFactory#symbolSortKey} are applied.
 * @param symbolZOrder Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use {@link PropertyFactory#symbolSortKey}.
 * @param textAllowOverlap If true, the text will be visible even if it collides with other previously drawn symbols.
 * @param textFont Font stack to use for displaying text.
 * @param textIgnorePlacement If true, other symbols can be visible even if they collide with the text.
 * @param textKeepUpright If true, the text may be flipped vertically to prevent it from being rendered upside-down.
 * @param textMaxAngle Maximum angle change between adjacent characters. The unit of textMaxAngle is in degrees.
 * @param textOptional If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
 * @param textPadding Size of the additional area around the text bounding box used for detecting symbol collisions. The unit of textPadding is in density-independent pixels.
 * @param textPitchAlignment Orientation of text when map is pitched.
 * @param textRotationAlignment In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of the individual glyphs forming the text.
 * @param textVariableAnchor To increase the chance of placing high-priority labels on the map, you can provide an array of {@link Property.TEXT_ANCHOR} locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the {@link PropertyFactory#textRadialOffset} or the two-dimensional {@link PropertyFactory#textOffset}.
 * @param textWritingMode The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
 * @param iconColorSaturation Controls saturation level of the symbol icon. With the default value of 1 the icon color is preserved while with a value of 0 it is fully desaturated and looks black and white. The unit of iconColorSaturation is in intensity.
 * @param iconTranslate Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. The unit of iconTranslate is in density-independent pixels.
 * @param iconTranslateAnchor Controls the frame of reference for {@link PropertyFactory#iconTranslate}.
 * @param textTranslate Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. The unit of textTranslate is in density-independent pixels.
 * @param textTranslateAnchor Controls the frame of reference for {@link PropertyFactory#textTranslate}.
 * @param onClick Callback to be invoked when one of the [PointAnnotation] in the cluster is clicked. The clicked [PointAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun PointAnnotationGroup(
  annotations: List<PointAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  iconAllowOverlap: Boolean? = null,
  iconIgnorePlacement: Boolean? = null,
  iconKeepUpright: Boolean? = null,
  iconOptional: Boolean? = null,
  iconPadding: Double? = null,
  iconPitchAlignment: IconPitchAlignment? = null,
  iconRotationAlignment: IconRotationAlignment? = null,
  symbolAvoidEdges: Boolean? = null,
  symbolPlacement: SymbolPlacement? = null,
  symbolSpacing: Double? = null,
  symbolZElevate: Boolean? = null,
  symbolZOrder: SymbolZOrder? = null,
  textAllowOverlap: Boolean? = null,
  textFont: List<String>? = null,
  textIgnorePlacement: Boolean? = null,
  textKeepUpright: Boolean? = null,
  textMaxAngle: Double? = null,
  textOptional: Boolean? = null,
  textPadding: Double? = null,
  textPitchAlignment: TextPitchAlignment? = null,
  textRotationAlignment: TextRotationAlignment? = null,
  textVariableAnchor: List<String>? = null,
  textWritingMode: List<String>? = null,
  iconColorSaturation: Double? = null,
  iconTranslate: List<Double>? = null,
  iconTranslateAnchor: IconTranslateAnchor? = null,
  textTranslate: List<Double>? = null,
  textTranslateAnchor: TextTranslateAnchor? = null,
  onClick: (PointAnnotation) -> Boolean = { false },
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PointAnnotationCluster inside unsupported composable function")

  ComposeNode<PointAnnotationManagerNode, MapApplier>(
    factory = {
      val annotationManager =
        mapApplier.mapView.annotations.createPointAnnotationManager(annotationConfig)
      PointAnnotationManagerNode(annotationManager, onClick)
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
      set(iconAllowOverlap) {
        annotationManager.iconAllowOverlap = it
      }
      set(iconIgnorePlacement) {
        annotationManager.iconIgnorePlacement = it
      }
      set(iconKeepUpright) {
        annotationManager.iconKeepUpright = it
      }
      set(iconOptional) {
        annotationManager.iconOptional = it
      }
      set(iconPadding) {
        annotationManager.iconPadding = it
      }
      set(iconPitchAlignment) {
        annotationManager.iconPitchAlignment = it
      }
      set(iconRotationAlignment) {
        annotationManager.iconRotationAlignment = it
      }
      set(symbolAvoidEdges) {
        annotationManager.symbolAvoidEdges = it
      }
      set(symbolPlacement) {
        annotationManager.symbolPlacement = it
      }
      set(symbolSpacing) {
        annotationManager.symbolSpacing = it
      }
      set(symbolZElevate) {
        annotationManager.symbolZElevate = it
      }
      set(symbolZOrder) {
        annotationManager.symbolZOrder = it
      }
      set(textAllowOverlap) {
        annotationManager.textAllowOverlap = it
      }
      set(textFont) {
        annotationManager.textFont = it
      }
      set(textIgnorePlacement) {
        annotationManager.textIgnorePlacement = it
      }
      set(textKeepUpright) {
        annotationManager.textKeepUpright = it
      }
      set(textMaxAngle) {
        annotationManager.textMaxAngle = it
      }
      set(textOptional) {
        annotationManager.textOptional = it
      }
      set(textPadding) {
        annotationManager.textPadding = it
      }
      set(textPitchAlignment) {
        annotationManager.textPitchAlignment = it
      }
      set(textRotationAlignment) {
        annotationManager.textRotationAlignment = it
      }
      set(textVariableAnchor) {
        annotationManager.textVariableAnchor = it
      }
      set(textWritingMode) {
        annotationManager.textWritingMode = it
      }
      set(iconColorSaturation) {
        annotationManager.iconColorSaturation = it
      }
      set(iconTranslate) {
        annotationManager.iconTranslate = it
      }
      set(iconTranslateAnchor) {
        annotationManager.iconTranslateAnchor = it
      }
      set(textTranslate) {
        annotationManager.textTranslate = it
      }
      set(textTranslateAnchor) {
        annotationManager.textTranslateAnchor = it
      }
      update(onClick) {
        onClicked = it
      }
    }
  )
}