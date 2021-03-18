// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.view.View
import com.mapbox.geojson.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.concurrent.atomic.AtomicLong

/**
 * The symbol manager allows to add symbols to a map.
 */
class SymbolManager(
  mapView: View,
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<Point, Symbol, SymbolOptions, OnSymbolDragListener, OnSymbolClickListener, OnSymbolLongClickListener, SymbolLayer>(
    mapView, delegateProvider, annotationConfig
  ) {
  private val id = ID_GENERATOR.incrementAndGet()
  override val layerId = annotationConfig?.layerId ?: "mapbox-android-symbol-layer-$id"
  override val sourceId = annotationConfig?.sourceId ?: "mapbox-android-symbol-source-$id"

  init {
    delegateProvider.getStyle {
      style = it
      initLayerAndSource()
      // Show all icons and texts by default. 
      iconAllowOverlap = true
      textAllowOverlap = true
      iconIgnorePlacement = true
      textIgnorePlacement = true
    }
  }

  override fun initializeDataDrivenPropertyMap() {
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_ANCHOR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_IMAGE] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_OFFSET] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_ROTATE] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_SIZE] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_SYMBOL_SORT_KEY] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_ANCHOR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_FIELD] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_FONT] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_JUSTIFY] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_LETTER_SPACING] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_MAX_WIDTH] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_OFFSET] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_RADIAL_OFFSET] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_ROTATE] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_SIZE] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_TRANSFORM] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_COLOR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_HALO_BLUR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_HALO_COLOR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_HALO_WIDTH] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_ICON_OPACITY] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_COLOR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_HALO_BLUR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_HALO_COLOR] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_HALO_WIDTH] = false
    dataDrivenPropertyUsageMap[SymbolOptions.PROPERTY_TEXT_OPACITY] = false
  }

  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      SymbolOptions.PROPERTY_ICON_ANCHOR -> layer?.iconAnchor(get(SymbolOptions.PROPERTY_ICON_ANCHOR))
      SymbolOptions.PROPERTY_ICON_IMAGE -> layer?.iconImage(get(SymbolOptions.PROPERTY_ICON_IMAGE))
      SymbolOptions.PROPERTY_ICON_OFFSET -> layer?.iconOffset(get(SymbolOptions.PROPERTY_ICON_OFFSET))
      SymbolOptions.PROPERTY_ICON_ROTATE -> layer?.iconRotate(get(SymbolOptions.PROPERTY_ICON_ROTATE))
      SymbolOptions.PROPERTY_ICON_SIZE -> layer?.iconSize(get(SymbolOptions.PROPERTY_ICON_SIZE))
      SymbolOptions.PROPERTY_SYMBOL_SORT_KEY -> layer?.symbolSortKey(get(SymbolOptions.PROPERTY_SYMBOL_SORT_KEY))
      SymbolOptions.PROPERTY_TEXT_ANCHOR -> layer?.textAnchor(get(SymbolOptions.PROPERTY_TEXT_ANCHOR))
      SymbolOptions.PROPERTY_TEXT_FIELD -> layer?.textField(get(SymbolOptions.PROPERTY_TEXT_FIELD))
      SymbolOptions.PROPERTY_TEXT_FONT -> layer?.textFont(get(SymbolOptions.PROPERTY_TEXT_FONT))
      SymbolOptions.PROPERTY_TEXT_JUSTIFY -> layer?.textJustify(get(SymbolOptions.PROPERTY_TEXT_JUSTIFY))
      SymbolOptions.PROPERTY_TEXT_LETTER_SPACING -> layer?.textLetterSpacing(get(SymbolOptions.PROPERTY_TEXT_LETTER_SPACING))
      SymbolOptions.PROPERTY_TEXT_MAX_WIDTH -> layer?.textMaxWidth(get(SymbolOptions.PROPERTY_TEXT_MAX_WIDTH))
      SymbolOptions.PROPERTY_TEXT_OFFSET -> layer?.textOffset(get(SymbolOptions.PROPERTY_TEXT_OFFSET))
      SymbolOptions.PROPERTY_TEXT_RADIAL_OFFSET -> layer?.textRadialOffset(get(SymbolOptions.PROPERTY_TEXT_RADIAL_OFFSET))
      SymbolOptions.PROPERTY_TEXT_ROTATE -> layer?.textRotate(get(SymbolOptions.PROPERTY_TEXT_ROTATE))
      SymbolOptions.PROPERTY_TEXT_SIZE -> layer?.textSize(get(SymbolOptions.PROPERTY_TEXT_SIZE))
      SymbolOptions.PROPERTY_TEXT_TRANSFORM -> layer?.textTransform(get(SymbolOptions.PROPERTY_TEXT_TRANSFORM))
      SymbolOptions.PROPERTY_ICON_COLOR -> layer?.iconColor(get(SymbolOptions.PROPERTY_ICON_COLOR))
      SymbolOptions.PROPERTY_ICON_HALO_BLUR -> layer?.iconHaloBlur(get(SymbolOptions.PROPERTY_ICON_HALO_BLUR))
      SymbolOptions.PROPERTY_ICON_HALO_COLOR -> layer?.iconHaloColor(get(SymbolOptions.PROPERTY_ICON_HALO_COLOR))
      SymbolOptions.PROPERTY_ICON_HALO_WIDTH -> layer?.iconHaloWidth(get(SymbolOptions.PROPERTY_ICON_HALO_WIDTH))
      SymbolOptions.PROPERTY_ICON_OPACITY -> layer?.iconOpacity(get(SymbolOptions.PROPERTY_ICON_OPACITY))
      SymbolOptions.PROPERTY_TEXT_COLOR -> layer?.textColor(get(SymbolOptions.PROPERTY_TEXT_COLOR))
      SymbolOptions.PROPERTY_TEXT_HALO_BLUR -> layer?.textHaloBlur(get(SymbolOptions.PROPERTY_TEXT_HALO_BLUR))
      SymbolOptions.PROPERTY_TEXT_HALO_COLOR -> layer?.textHaloColor(get(SymbolOptions.PROPERTY_TEXT_HALO_COLOR))
      SymbolOptions.PROPERTY_TEXT_HALO_WIDTH -> layer?.textHaloWidth(get(SymbolOptions.PROPERTY_TEXT_HALO_WIDTH))
      SymbolOptions.PROPERTY_TEXT_OPACITY -> layer?.textOpacity(get(SymbolOptions.PROPERTY_TEXT_OPACITY))
    }
  }

  /**
   * Create a list of symbols on the map.
   * <p>
   * Symbols are going to be created only for features with a matching geometry.
   * <p>
   * All supported properties are:<br>
   * SymbolOptions.PROPERTY_ICON_ANCHOR - IconAnchor<br>
   * SymbolOptions.PROPERTY_ICON_IMAGE - String<br>
   * SymbolOptions.PROPERTY_ICON_OFFSET - List<Double><br>
   * SymbolOptions.PROPERTY_ICON_ROTATE - Double<br>
   * SymbolOptions.PROPERTY_ICON_SIZE - Double<br>
   * SymbolOptions.PROPERTY_SYMBOL_SORT_KEY - Double<br>
   * SymbolOptions.PROPERTY_TEXT_ANCHOR - TextAnchor<br>
   * SymbolOptions.PROPERTY_TEXT_FIELD - String<br>
   * SymbolOptions.PROPERTY_TEXT_FONT - List<String><br>
   * SymbolOptions.PROPERTY_TEXT_JUSTIFY - TextJustify<br>
   * SymbolOptions.PROPERTY_TEXT_LETTER_SPACING - Double<br>
   * SymbolOptions.PROPERTY_TEXT_MAX_WIDTH - Double<br>
   * SymbolOptions.PROPERTY_TEXT_OFFSET - List<Double><br>
   * SymbolOptions.PROPERTY_TEXT_RADIAL_OFFSET - Double<br>
   * SymbolOptions.PROPERTY_TEXT_ROTATE - Double<br>
   * SymbolOptions.PROPERTY_TEXT_SIZE - Double<br>
   * SymbolOptions.PROPERTY_TEXT_TRANSFORM - TextTransform<br>
   * SymbolOptions.PROPERTY_ICON_COLOR - String<br>
   * SymbolOptions.PROPERTY_ICON_HALO_BLUR - Double<br>
   * SymbolOptions.PROPERTY_ICON_HALO_COLOR - String<br>
   * SymbolOptions.PROPERTY_ICON_HALO_WIDTH - Double<br>
   * SymbolOptions.PROPERTY_ICON_OPACITY - Double<br>
   * SymbolOptions.PROPERTY_TEXT_COLOR - String<br>
   * SymbolOptions.PROPERTY_TEXT_HALO_BLUR - Double<br>
   * SymbolOptions.PROPERTY_TEXT_HALO_COLOR - String<br>
   * SymbolOptions.PROPERTY_TEXT_HALO_WIDTH - Double<br>
   * SymbolOptions.PROPERTY_TEXT_OPACITY - Double<br>
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   * <p>
   * Out of spec properties:<br>
   * "is-draggable" - Boolean, true if the symbol should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of symbols to build
   * @return the list of built symbols
   */
  fun create(json: String): List<Symbol> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of symbols on the map.
   * <p>
   * Symbols are going to be created only for features with a matching geometry.
   * <p>
   * All supported properties are:<br>
   * SymbolOptions.PROPERTY_ICON_ANCHOR - IconAnchor<br>
   * SymbolOptions.PROPERTY_ICON_IMAGE - String<br>
   * SymbolOptions.PROPERTY_ICON_OFFSET - List<Double><br>
   * SymbolOptions.PROPERTY_ICON_ROTATE - Double<br>
   * SymbolOptions.PROPERTY_ICON_SIZE - Double<br>
   * SymbolOptions.PROPERTY_SYMBOL_SORT_KEY - Double<br>
   * SymbolOptions.PROPERTY_TEXT_ANCHOR - TextAnchor<br>
   * SymbolOptions.PROPERTY_TEXT_FIELD - String<br>
   * SymbolOptions.PROPERTY_TEXT_FONT - List<String><br>
   * SymbolOptions.PROPERTY_TEXT_JUSTIFY - TextJustify<br>
   * SymbolOptions.PROPERTY_TEXT_LETTER_SPACING - Double<br>
   * SymbolOptions.PROPERTY_TEXT_MAX_WIDTH - Double<br>
   * SymbolOptions.PROPERTY_TEXT_OFFSET - List<Double><br>
   * SymbolOptions.PROPERTY_TEXT_RADIAL_OFFSET - Double<br>
   * SymbolOptions.PROPERTY_TEXT_ROTATE - Double<br>
   * SymbolOptions.PROPERTY_TEXT_SIZE - Double<br>
   * SymbolOptions.PROPERTY_TEXT_TRANSFORM - TextTransform<br>
   * SymbolOptions.PROPERTY_ICON_COLOR - String<br>
   * SymbolOptions.PROPERTY_ICON_HALO_BLUR - Double<br>
   * SymbolOptions.PROPERTY_ICON_HALO_COLOR - String<br>
   * SymbolOptions.PROPERTY_ICON_HALO_WIDTH - Double<br>
   * SymbolOptions.PROPERTY_ICON_OPACITY - Double<br>
   * SymbolOptions.PROPERTY_TEXT_COLOR - String<br>
   * SymbolOptions.PROPERTY_TEXT_HALO_BLUR - Double<br>
   * SymbolOptions.PROPERTY_TEXT_HALO_COLOR - String<br>
   * SymbolOptions.PROPERTY_TEXT_HALO_WIDTH - Double<br>
   * SymbolOptions.PROPERTY_TEXT_OPACITY - Double<br>
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   * <p>
   * Out of spec properties:<br>
   * "is-draggable" - Boolean, true if the symbol should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of symbols to build
   * @return the list of built symbols
   */
  fun create(featureCollection: FeatureCollection): List<Symbol> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        SymbolOptions.fromFeature(it)
      }
      return create(options)
    }
    return listOf()
  }

  /**
   * Get the key of the id of the annotation.
   *
   * @return the key of the id of the annotation
   */
  override fun getAnnotationIdKey(): String {
    return Symbol.ID_KEY
  }

  // Property accessors
  /**
   * The IconAllowOverlap property
   * <p>
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   */
  var iconAllowOverlap: Boolean?
    /**
     * Get the IconAllowOverlap property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.iconAllowOverlap
    }
    /**
     * Set the IconAllowOverlap property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.iconAllowOverlap(it)
      }
    }

  /**
   * The IconIgnorePlacement property
   * <p>
   * If true, other symbols can be visible even if they collide with the icon.
   */
  var iconIgnorePlacement: Boolean?
    /**
     * Get the IconIgnorePlacement property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.iconIgnorePlacement
    }
    /**
     * Set the IconIgnorePlacement property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.iconIgnorePlacement(it)
      }
    }

  /**
   * The IconKeepUpright property
   * <p>
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   */
  var iconKeepUpright: Boolean?
    /**
     * Get the IconKeepUpright property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.iconKeepUpright
    }
    /**
     * Set the IconKeepUpright property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.iconKeepUpright(it)
      }
    }

  /**
   * The IconOptional property
   * <p>
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   */
  var iconOptional: Boolean?
    /**
     * Get the IconOptional property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.iconOptional
    }
    /**
     * Set the IconOptional property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.iconOptional(it)
      }
    }

  /**
   * The IconPadding property
   * <p>
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   */
  var iconPadding: Double?
    /**
     * Get the IconPadding property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer?.iconPadding
    }
    /**
     * Set the IconPadding property
     * @param value property wrapper value around Double
     */
    set(value) {
      value?.let {
        layer?.iconPadding(it)
      }
    }

  /**
   * The IconPitchAlignment property
   * <p>
   * Orientation of icon when map is pitched.
   */
  var iconPitchAlignment: IconPitchAlignment?
    /**
     * Get the IconPitchAlignment property
     *
     * @return property wrapper value around IconPitchAlignment
     */
    get(): IconPitchAlignment? {
      return layer?.iconPitchAlignment
    }
    /**
     * Set the IconPitchAlignment property
     * @param value property wrapper value around IconPitchAlignment
     */
    set(value) {
      value?.let {
        layer?.iconPitchAlignment(it)
      }
    }

  /**
   * The IconRotationAlignment property
   * <p>
   * In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of icons.
   */
  var iconRotationAlignment: IconRotationAlignment?
    /**
     * Get the IconRotationAlignment property
     *
     * @return property wrapper value around IconRotationAlignment
     */
    get(): IconRotationAlignment? {
      return layer?.iconRotationAlignment
    }
    /**
     * Set the IconRotationAlignment property
     * @param value property wrapper value around IconRotationAlignment
     */
    set(value) {
      value?.let {
        layer?.iconRotationAlignment(it)
      }
    }

  /**
   * The IconTextFit property
   * <p>
   * Scales the icon to fit around the associated text.
   */
  var iconTextFit: IconTextFit?
    /**
     * Get the IconTextFit property
     *
     * @return property wrapper value around IconTextFit
     */
    get(): IconTextFit? {
      return layer?.iconTextFit
    }
    /**
     * Set the IconTextFit property
     * @param value property wrapper value around IconTextFit
     */
    set(value) {
      value?.let {
        layer?.iconTextFit(it)
      }
    }

  /**
   * The IconTextFitPadding property
   * <p>
   * Size of the additional area added to dimensions determined by {@link Property.ICON_TEXT_FIT}, in clockwise order: top, right, bottom, left.
   */
  var iconTextFitPadding: List<Double>?
    /**
     * Get the IconTextFitPadding property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer?.iconTextFitPadding
    }
    /**
     * Set the IconTextFitPadding property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      value?.let {
        layer?.iconTextFitPadding(it)
      }
    }

  /**
   * The SymbolAvoidEdges property
   * <p>
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   */
  var symbolAvoidEdges: Boolean?
    /**
     * Get the SymbolAvoidEdges property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.symbolAvoidEdges
    }
    /**
     * Set the SymbolAvoidEdges property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.symbolAvoidEdges(it)
      }
    }

  /**
   * The SymbolPlacement property
   * <p>
   * Label placement relative to its geometry.
   */
  var symbolPlacement: SymbolPlacement?
    /**
     * Get the SymbolPlacement property
     *
     * @return property wrapper value around SymbolPlacement
     */
    get(): SymbolPlacement? {
      return layer?.symbolPlacement
    }
    /**
     * Set the SymbolPlacement property
     * @param value property wrapper value around SymbolPlacement
     */
    set(value) {
      value?.let {
        layer?.symbolPlacement(it)
      }
    }

  /**
   * The SymbolSpacing property
   * <p>
   * Distance between two symbol anchors.
   */
  var symbolSpacing: Double?
    /**
     * Get the SymbolSpacing property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer?.symbolSpacing
    }
    /**
     * Set the SymbolSpacing property
     * @param value property wrapper value around Double
     */
    set(value) {
      value?.let {
        layer?.symbolSpacing(it)
      }
    }

  /**
   * The TextAllowOverlap property
   * <p>
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   */
  var textAllowOverlap: Boolean?
    /**
     * Get the TextAllowOverlap property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.textAllowOverlap
    }
    /**
     * Set the TextAllowOverlap property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.textAllowOverlap(it)
      }
    }

  /**
   * The TextIgnorePlacement property
   * <p>
   * If true, other symbols can be visible even if they collide with the text.
   */
  var textIgnorePlacement: Boolean?
    /**
     * Get the TextIgnorePlacement property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.textIgnorePlacement
    }
    /**
     * Set the TextIgnorePlacement property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.textIgnorePlacement(it)
      }
    }

  /**
   * The TextKeepUpright property
   * <p>
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   */
  var textKeepUpright: Boolean?
    /**
     * Get the TextKeepUpright property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.textKeepUpright
    }
    /**
     * Set the TextKeepUpright property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.textKeepUpright(it)
      }
    }

  /**
   * The TextLineHeight property
   * <p>
   * Text leading value for multi-line text.
   */
  var textLineHeight: Double?
    /**
     * Get the TextLineHeight property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer?.textLineHeight
    }
    /**
     * Set the TextLineHeight property
     * @param value property wrapper value around Double
     */
    set(value) {
      value?.let {
        layer?.textLineHeight(it)
      }
    }

  /**
   * The TextMaxAngle property
   * <p>
   * Maximum angle change between adjacent characters.
   */
  var textMaxAngle: Double?
    /**
     * Get the TextMaxAngle property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer?.textMaxAngle
    }
    /**
     * Set the TextMaxAngle property
     * @param value property wrapper value around Double
     */
    set(value) {
      value?.let {
        layer?.textMaxAngle(it)
      }
    }

  /**
   * The TextOptional property
   * <p>
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   */
  var textOptional: Boolean?
    /**
     * Get the TextOptional property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer?.textOptional
    }
    /**
     * Set the TextOptional property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      value?.let {
        layer?.textOptional(it)
      }
    }

  /**
   * The TextPadding property
   * <p>
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   */
  var textPadding: Double?
    /**
     * Get the TextPadding property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer?.textPadding
    }
    /**
     * Set the TextPadding property
     * @param value property wrapper value around Double
     */
    set(value) {
      value?.let {
        layer?.textPadding(it)
      }
    }

  /**
   * The TextPitchAlignment property
   * <p>
   * Orientation of text when map is pitched.
   */
  var textPitchAlignment: TextPitchAlignment?
    /**
     * Get the TextPitchAlignment property
     *
     * @return property wrapper value around TextPitchAlignment
     */
    get(): TextPitchAlignment? {
      return layer?.textPitchAlignment
    }
    /**
     * Set the TextPitchAlignment property
     * @param value property wrapper value around TextPitchAlignment
     */
    set(value) {
      value?.let {
        layer?.textPitchAlignment(it)
      }
    }

  /**
   * The TextRotationAlignment property
   * <p>
   * In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of the individual glyphs forming the text.
   */
  var textRotationAlignment: TextRotationAlignment?
    /**
     * Get the TextRotationAlignment property
     *
     * @return property wrapper value around TextRotationAlignment
     */
    get(): TextRotationAlignment? {
      return layer?.textRotationAlignment
    }
    /**
     * Set the TextRotationAlignment property
     * @param value property wrapper value around TextRotationAlignment
     */
    set(value) {
      value?.let {
        layer?.textRotationAlignment(it)
      }
    }

  /**
   * The TextVariableAnchor property
   * <p>
   * To increase the chance of placing high-priority labels on the map, you can provide an array of {@link Property.TEXT_ANCHOR} locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the {@link PropertyFactory#textRadialOffset} or the two-dimensional {@link PropertyFactory#textOffset}.
   */
  var textVariableAnchor: List<String>?
    /**
     * Get the TextVariableAnchor property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer?.textVariableAnchor
    }
    /**
     * Set the TextVariableAnchor property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      value?.let {
        layer?.textVariableAnchor(it)
      }
    }

  /**
   * The TextWritingMode property
   * <p>
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. The order of elements in an array define priority order for the placement of an orientation variant.
   */
  var textWritingMode: List<String>?
    /**
     * Get the TextWritingMode property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer?.textWritingMode
    }
    /**
     * Set the TextWritingMode property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      value?.let {
        layer?.textWritingMode(it)
      }
    }

  /**
   * The IconTranslate property
   * <p>
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   */
  var iconTranslate: List<Double>?
    /**
     * Get the IconTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer?.iconTranslate
    }
    /**
     * Set the IconTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      value?.let {
        layer?.iconTranslate(it)
      }
    }

  /**
   * The IconTranslateAnchor property
   * <p>
   * Controls the frame of reference for {@link PropertyFactory#iconTranslate}.
   */
  var iconTranslateAnchor: IconTranslateAnchor?
    /**
     * Get the IconTranslateAnchor property
     *
     * @return property wrapper value around IconTranslateAnchor
     */
    get(): IconTranslateAnchor? {
      return layer?.iconTranslateAnchor
    }
    /**
     * Set the IconTranslateAnchor property
     * @param value property wrapper value around IconTranslateAnchor
     */
    set(value) {
      value?.let {
        layer?.iconTranslateAnchor(it)
      }
    }

  /**
   * The TextTranslate property
   * <p>
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   */
  var textTranslate: List<Double>?
    /**
     * Get the TextTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer?.textTranslate
    }
    /**
     * Set the TextTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      value?.let {
        layer?.textTranslate(it)
      }
    }

  /**
   * The TextTranslateAnchor property
   * <p>
   * Controls the frame of reference for {@link PropertyFactory#textTranslate}.
   */
  var textTranslateAnchor: TextTranslateAnchor?
    /**
     * Get the TextTranslateAnchor property
     *
     * @return property wrapper value around TextTranslateAnchor
     */
    get(): TextTranslateAnchor? {
      return layer?.textTranslateAnchor
    }
    /**
     * Set the TextTranslateAnchor property
     * @param value property wrapper value around TextTranslateAnchor
     */
    set(value) {
      value?.let {
        layer?.textTranslateAnchor(it)
      }
    }

  /**
   * Create the layer for managed annotations
   *
   * @return the layer created
   */
  override fun createLayer(): SymbolLayer {
    return symbolLayer(layerId, sourceId) {}
  }

  /**
   * The filter on the managed symbols.
   *
   * @param expression expression
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed symbols.
     *
     * @return expression
     */
    get() = layer?.filter
    /**
     * Set filter on the managed symbols.
     *
     * @param expression expression
     */
    set(value) {
      value?.let { layer?.filter(it) }
    }

  /**
   * Static variables and methods.
   */
  companion object {
    /** The generator for id */
    var ID_GENERATOR = AtomicLong(0)
  }
}

/**
 * Extension function to create a SymbolManager instance.
 */
fun AnnotationPlugin.createSymbolManager(
  mapView: View,
  annotationConfig: AnnotationConfig? = null
): SymbolManager {
  return createAnnotationManager(mapView, AnnotationType.Symbol, annotationConfig) as SymbolManager
}