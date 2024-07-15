// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.layers.properties.generated.IconPitchAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.IconRotationAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.IconTranslateAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.SymbolPlacement
import com.mapbox.maps.extension.style.layers.properties.generated.SymbolZOrder
import com.mapbox.maps.extension.style.layers.properties.generated.TextPitchAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.TextRotationAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.TextTranslateAnchor
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager

/**
 * The state holder for [PointAnnotationGroup] properties.
 */
@MapboxExperimental
@Stable
public class PointAnnotationGroupState private constructor(
  initialIconAllowOverlap: Boolean?,
  initialIconIgnorePlacement: Boolean?,
  initialIconKeepUpright: Boolean?,
  initialIconOptional: Boolean?,
  initialIconPadding: Double?,
  initialIconPitchAlignment: IconPitchAlignment?,
  initialIconRotationAlignment: IconRotationAlignment?,
  initialSymbolAvoidEdges: Boolean?,
  initialSymbolPlacement: SymbolPlacement?,
  initialSymbolSpacing: Double?,
  initialSymbolZElevate: Boolean?,
  initialSymbolZOrder: SymbolZOrder?,
  initialTextAllowOverlap: Boolean?,
  initialTextFont: List<String>?,
  initialTextIgnorePlacement: Boolean?,
  initialTextKeepUpright: Boolean?,
  initialTextMaxAngle: Double?,
  initialTextOptional: Boolean?,
  initialTextPadding: Double?,
  initialTextPitchAlignment: TextPitchAlignment?,
  initialTextRotationAlignment: TextRotationAlignment?,
  initialTextVariableAnchor: List<String>?,
  initialTextWritingMode: List<String>?,
  initialIconColorSaturation: Double?,
  initialIconOcclusionOpacity: Double?,
  initialIconTranslate: List<Double>?,
  initialIconTranslateAnchor: IconTranslateAnchor?,
  initialTextOcclusionOpacity: Double?,
  initialTextTranslate: List<Double>?,
  initialTextTranslateAnchor: TextTranslateAnchor?,
) {
  public constructor() : this(
    initialIconAllowOverlap = null,
    initialIconIgnorePlacement = null,
    initialIconKeepUpright = null,
    initialIconOptional = null,
    initialIconPadding = null,
    initialIconPitchAlignment = null,
    initialIconRotationAlignment = null,
    initialSymbolAvoidEdges = null,
    initialSymbolPlacement = null,
    initialSymbolSpacing = null,
    initialSymbolZElevate = null,
    initialSymbolZOrder = null,
    initialTextAllowOverlap = null,
    initialTextFont = null,
    initialTextIgnorePlacement = null,
    initialTextKeepUpright = null,
    initialTextMaxAngle = null,
    initialTextOptional = null,
    initialTextPadding = null,
    initialTextPitchAlignment = null,
    initialTextRotationAlignment = null,
    initialTextVariableAnchor = null,
    initialTextWritingMode = null,
    initialIconColorSaturation = null,
    initialIconOcclusionOpacity = null,
    initialIconTranslate = null,
    initialIconTranslateAnchor = null,
    initialTextOcclusionOpacity = null,
    initialTextTranslate = null,
    initialTextTranslateAnchor = null,
  )
  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   */
  public var iconAllowOverlap: Boolean? by mutableStateOf(initialIconAllowOverlap)
  /**
   * If true, other symbols can be visible even if they collide with the icon.
   */
  public var iconIgnorePlacement: Boolean? by mutableStateOf(initialIconIgnorePlacement)
  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   */
  public var iconKeepUpright: Boolean? by mutableStateOf(initialIconKeepUpright)
  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   */
  public var iconOptional: Boolean? by mutableStateOf(initialIconOptional)
  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions. The unit of iconPadding is in density-independent pixels.
   */
  public var iconPadding: Double? by mutableStateOf(initialIconPadding)
  /**
   * Orientation of icon when map is pitched.
   */
  public var iconPitchAlignment: IconPitchAlignment? by mutableStateOf(initialIconPitchAlignment)
  /**
   * In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of icons.
   */
  public var iconRotationAlignment: IconRotationAlignment? by mutableStateOf(initialIconRotationAlignment)
  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   */
  public var symbolAvoidEdges: Boolean? by mutableStateOf(initialSymbolAvoidEdges)
  /**
   * Label placement relative to its geometry.
   */
  public var symbolPlacement: SymbolPlacement? by mutableStateOf(initialSymbolPlacement)
  /**
   * Distance between two symbol anchors. The unit of symbolSpacing is in density-independent pixels.
   */
  public var symbolSpacing: Double? by mutableStateOf(initialSymbolSpacing)
  /**
   * Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when {@link PropertyFactory#fillExtrusionHeight} is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or {@link PropertyFactory#symbolSortKey} are applied.
   */
  public var symbolZElevate: Boolean? by mutableStateOf(initialSymbolZElevate)
  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use {@link PropertyFactory#symbolSortKey}.
   */
  public var symbolZOrder: SymbolZOrder? by mutableStateOf(initialSymbolZOrder)
  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   */
  public var textAllowOverlap: Boolean? by mutableStateOf(initialTextAllowOverlap)
  /**
   * Font stack to use for displaying text.
   */
  public var textFont: List<String>? by mutableStateOf(initialTextFont)
  /**
   * If true, other symbols can be visible even if they collide with the text.
   */
  public var textIgnorePlacement: Boolean? by mutableStateOf(initialTextIgnorePlacement)
  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   */
  public var textKeepUpright: Boolean? by mutableStateOf(initialTextKeepUpright)
  /**
   * Maximum angle change between adjacent characters. The unit of textMaxAngle is in degrees.
   */
  public var textMaxAngle: Double? by mutableStateOf(initialTextMaxAngle)
  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   */
  public var textOptional: Boolean? by mutableStateOf(initialTextOptional)
  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions. The unit of textPadding is in density-independent pixels.
   */
  public var textPadding: Double? by mutableStateOf(initialTextPadding)
  /**
   * Orientation of text when map is pitched.
   */
  public var textPitchAlignment: TextPitchAlignment? by mutableStateOf(initialTextPitchAlignment)
  /**
   * In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of the individual glyphs forming the text.
   */
  public var textRotationAlignment: TextRotationAlignment? by mutableStateOf(initialTextRotationAlignment)
  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of {@link Property.TEXT_ANCHOR} locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the {@link PropertyFactory#textRadialOffset} or the two-dimensional {@link PropertyFactory#textOffset}.
   */
  public var textVariableAnchor: List<String>? by mutableStateOf(initialTextVariableAnchor)
  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  public var textWritingMode: List<String>? by mutableStateOf(initialTextWritingMode)
  /**
   * Increase or reduce the saturation of the symbol icon.
   */
  public var iconColorSaturation: Double? by mutableStateOf(initialIconColorSaturation)
  /**
   * The opacity at which the icon will be drawn in case of being depth occluded. Not supported on globe zoom levels.
   */
  @MapboxExperimental
  public var iconOcclusionOpacity: Double? by mutableStateOf(initialIconOcclusionOpacity)
  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. The unit of iconTranslate is in density-independent pixels.
   */
  public var iconTranslate: List<Double>? by mutableStateOf(initialIconTranslate)
  /**
   * Controls the frame of reference for {@link PropertyFactory#iconTranslate}.
   */
  public var iconTranslateAnchor: IconTranslateAnchor? by mutableStateOf(initialIconTranslateAnchor)
  /**
   * The opacity at which the text will be drawn in case of being depth occluded. Not supported on globe zoom levels.
   */
  @MapboxExperimental
  public var textOcclusionOpacity: Double? by mutableStateOf(initialTextOcclusionOpacity)
  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. The unit of textTranslate is in density-independent pixels.
   */
  public var textTranslate: List<Double>? by mutableStateOf(initialTextTranslate)
  /**
   * Controls the frame of reference for {@link PropertyFactory#textTranslate}.
   */
  public var textTranslateAnchor: TextTranslateAnchor? by mutableStateOf(initialTextTranslateAnchor)

  @Composable
  private fun UpdateIconAllowOverlap(annotationManager: PointAnnotationManager) {
    annotationManager.iconAllowOverlap = iconAllowOverlap
  }
  @Composable
  private fun UpdateIconIgnorePlacement(annotationManager: PointAnnotationManager) {
    annotationManager.iconIgnorePlacement = iconIgnorePlacement
  }
  @Composable
  private fun UpdateIconKeepUpright(annotationManager: PointAnnotationManager) {
    annotationManager.iconKeepUpright = iconKeepUpright
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
  private fun UpdateIconRotationAlignment(annotationManager: PointAnnotationManager) {
    annotationManager.iconRotationAlignment = iconRotationAlignment
  }
  @Composable
  private fun UpdateSymbolAvoidEdges(annotationManager: PointAnnotationManager) {
    annotationManager.symbolAvoidEdges = symbolAvoidEdges
  }
  @Composable
  private fun UpdateSymbolPlacement(annotationManager: PointAnnotationManager) {
    annotationManager.symbolPlacement = symbolPlacement
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
  private fun UpdateTextFont(annotationManager: PointAnnotationManager) {
    annotationManager.textFont = textFont
  }
  @Composable
  private fun UpdateTextIgnorePlacement(annotationManager: PointAnnotationManager) {
    annotationManager.textIgnorePlacement = textIgnorePlacement
  }
  @Composable
  private fun UpdateTextKeepUpright(annotationManager: PointAnnotationManager) {
    annotationManager.textKeepUpright = textKeepUpright
  }
  @Composable
  private fun UpdateTextMaxAngle(annotationManager: PointAnnotationManager) {
    annotationManager.textMaxAngle = textMaxAngle
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
  private fun UpdateTextRotationAlignment(annotationManager: PointAnnotationManager) {
    annotationManager.textRotationAlignment = textRotationAlignment
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
  private fun UpdateIconColorSaturation(annotationManager: PointAnnotationManager) {
    annotationManager.iconColorSaturation = iconColorSaturation
  }
  @Composable
  private fun UpdateIconOcclusionOpacity(annotationManager: PointAnnotationManager) {
    annotationManager.iconOcclusionOpacity = iconOcclusionOpacity
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
  private fun UpdateTextOcclusionOpacity(annotationManager: PointAnnotationManager) {
    annotationManager.textOcclusionOpacity = textOcclusionOpacity
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
    UpdateIconIgnorePlacement(annotationManager)
    UpdateIconKeepUpright(annotationManager)
    UpdateIconOptional(annotationManager)
    UpdateIconPadding(annotationManager)
    UpdateIconPitchAlignment(annotationManager)
    UpdateIconRotationAlignment(annotationManager)
    UpdateSymbolAvoidEdges(annotationManager)
    UpdateSymbolPlacement(annotationManager)
    UpdateSymbolSpacing(annotationManager)
    UpdateSymbolZElevate(annotationManager)
    UpdateSymbolZOrder(annotationManager)
    UpdateTextAllowOverlap(annotationManager)
    UpdateTextFont(annotationManager)
    UpdateTextIgnorePlacement(annotationManager)
    UpdateTextKeepUpright(annotationManager)
    UpdateTextMaxAngle(annotationManager)
    UpdateTextOptional(annotationManager)
    UpdateTextPadding(annotationManager)
    UpdateTextPitchAlignment(annotationManager)
    UpdateTextRotationAlignment(annotationManager)
    UpdateTextVariableAnchor(annotationManager)
    UpdateTextWritingMode(annotationManager)
    UpdateIconColorSaturation(annotationManager)
    UpdateIconOcclusionOpacity(annotationManager)
    UpdateIconTranslate(annotationManager)
    UpdateIconTranslateAnchor(annotationManager)
    UpdateTextOcclusionOpacity(annotationManager)
    UpdateTextTranslate(annotationManager)
    UpdateTextTranslateAnchor(annotationManager)
  }
}

// End of generated file.