// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import com.mapbox.geojson.*
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.concurrent.atomic.AtomicLong

/**
 * The pointAnnotation manager allows to add pointAnnotations to a map.
 */
class PointAnnotationManager(
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<Point, PointAnnotation, PointAnnotationOptions, OnPointAnnotationDragListener, OnPointAnnotationClickListener, OnPointAnnotationLongClickListener, OnPointAnnotationInteractionListener, SymbolLayer>(
    delegateProvider, annotationConfig, ID_GENERATOR.incrementAndGet(), "pointAnnotation", ::SymbolLayer
  ) {

  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      PointAnnotationOptions.PROPERTY_ICON_ANCHOR -> {
        layer.iconAnchor(get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR))
        dragLayer.iconAnchor(get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR))
      }
      PointAnnotationOptions.PROPERTY_ICON_IMAGE -> {
        layer.iconImage(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE))
        dragLayer.iconImage(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE))
      }
      PointAnnotationOptions.PROPERTY_ICON_OFFSET -> {
        layer.iconOffset(get(PointAnnotationOptions.PROPERTY_ICON_OFFSET))
        dragLayer.iconOffset(get(PointAnnotationOptions.PROPERTY_ICON_OFFSET))
      }
      PointAnnotationOptions.PROPERTY_ICON_ROTATE -> {
        layer.iconRotate(get(PointAnnotationOptions.PROPERTY_ICON_ROTATE))
        dragLayer.iconRotate(get(PointAnnotationOptions.PROPERTY_ICON_ROTATE))
      }
      PointAnnotationOptions.PROPERTY_ICON_SIZE -> {
        layer.iconSize(get(PointAnnotationOptions.PROPERTY_ICON_SIZE))
        dragLayer.iconSize(get(PointAnnotationOptions.PROPERTY_ICON_SIZE))
      }
      PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT -> {
        layer.iconTextFit(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT))
        dragLayer.iconTextFit(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT))
      }
      PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING -> {
        layer.iconTextFitPadding(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING))
        dragLayer.iconTextFitPadding(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING))
      }
      PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY -> {
        layer.symbolSortKey(get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY))
        dragLayer.symbolSortKey(get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY))
      }
      PointAnnotationOptions.PROPERTY_TEXT_ANCHOR -> {
        layer.textAnchor(get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR))
        dragLayer.textAnchor(get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_FIELD -> {
        layer.textField(get(PointAnnotationOptions.PROPERTY_TEXT_FIELD))
        dragLayer.textField(get(PointAnnotationOptions.PROPERTY_TEXT_FIELD))
      }
      PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY -> {
        layer.textJustify(get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY))
        dragLayer.textJustify(get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY))
      }
      PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING -> {
        layer.textLetterSpacing(get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING))
        dragLayer.textLetterSpacing(get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING))
      }
      PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT -> {
        layer.textLineHeight(get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT))
        dragLayer.textLineHeight(get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT))
      }
      PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH -> {
        layer.textMaxWidth(get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH))
        dragLayer.textMaxWidth(get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH))
      }
      PointAnnotationOptions.PROPERTY_TEXT_OFFSET -> {
        layer.textOffset(get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET))
        dragLayer.textOffset(get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET))
      }
      PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET -> {
        layer.textRadialOffset(get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET))
        dragLayer.textRadialOffset(get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET))
      }
      PointAnnotationOptions.PROPERTY_TEXT_ROTATE -> {
        layer.textRotate(get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE))
        dragLayer.textRotate(get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE))
      }
      PointAnnotationOptions.PROPERTY_TEXT_SIZE -> {
        layer.textSize(get(PointAnnotationOptions.PROPERTY_TEXT_SIZE))
        dragLayer.textSize(get(PointAnnotationOptions.PROPERTY_TEXT_SIZE))
      }
      PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM -> {
        layer.textTransform(get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM))
        dragLayer.textTransform(get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM))
      }
      PointAnnotationOptions.PROPERTY_ICON_COLOR -> {
        layer.iconColor(get(PointAnnotationOptions.PROPERTY_ICON_COLOR))
        dragLayer.iconColor(get(PointAnnotationOptions.PROPERTY_ICON_COLOR))
      }
      PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH -> {
        layer.iconEmissiveStrength(get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH))
        dragLayer.iconEmissiveStrength(get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH))
      }
      PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR -> {
        layer.iconHaloBlur(get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR))
        dragLayer.iconHaloBlur(get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR))
      }
      PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR -> {
        layer.iconHaloColor(get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR))
        dragLayer.iconHaloColor(get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR))
      }
      PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH -> {
        layer.iconHaloWidth(get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH))
        dragLayer.iconHaloWidth(get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH))
      }
      PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE -> {
        layer.iconImageCrossFade(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE))
        dragLayer.iconImageCrossFade(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE))
      }
      PointAnnotationOptions.PROPERTY_ICON_OPACITY -> {
        layer.iconOpacity(get(PointAnnotationOptions.PROPERTY_ICON_OPACITY))
        dragLayer.iconOpacity(get(PointAnnotationOptions.PROPERTY_ICON_OPACITY))
      }
      PointAnnotationOptions.PROPERTY_TEXT_COLOR -> {
        layer.textColor(get(PointAnnotationOptions.PROPERTY_TEXT_COLOR))
        dragLayer.textColor(get(PointAnnotationOptions.PROPERTY_TEXT_COLOR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH -> {
        layer.textEmissiveStrength(get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH))
        dragLayer.textEmissiveStrength(get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH))
      }
      PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR -> {
        layer.textHaloBlur(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR))
        dragLayer.textHaloBlur(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR -> {
        layer.textHaloColor(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR))
        dragLayer.textHaloColor(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH -> {
        layer.textHaloWidth(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH))
        dragLayer.textHaloWidth(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH))
      }
      PointAnnotationOptions.PROPERTY_TEXT_OPACITY -> {
        layer.textOpacity(get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY))
        dragLayer.textOpacity(get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY))
      }
    }
  }

  /**
   * Create a list of pointAnnotations on the map.
   *
   * PointAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PointAnnotationOptions.PROPERTY_ICON_ANCHOR - IconAnchor
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE - String
   * PointAnnotationOptions.PROPERTY_ICON_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_ICON_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_ICON_SIZE - Double
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT - IconTextFit
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING - List<Double>
   * PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ANCHOR - TextAnchor
   * PointAnnotationOptions.PROPERTY_TEXT_FIELD - String
   * PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY - TextJustify
   * PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING - Double
   * PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT - Double
   * PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_SIZE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM - TextTransform
   * PointAnnotationOptions.PROPERTY_ICON_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE - Double
   * PointAnnotationOptions.PROPERTY_ICON_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OPACITY - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the pointAnnotation should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of pointAnnotations to build
   * @return the list of built pointAnnotations
   */
  fun create(json: String): List<PointAnnotation> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of pointAnnotations on the map.
   *
   * PointAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PointAnnotationOptions.PROPERTY_ICON_ANCHOR - IconAnchor
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE - String
   * PointAnnotationOptions.PROPERTY_ICON_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_ICON_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_ICON_SIZE - Double
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT - IconTextFit
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING - List<Double>
   * PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ANCHOR - TextAnchor
   * PointAnnotationOptions.PROPERTY_TEXT_FIELD - String
   * PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY - TextJustify
   * PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING - Double
   * PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT - Double
   * PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_SIZE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM - TextTransform
   * PointAnnotationOptions.PROPERTY_ICON_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE - Double
   * PointAnnotationOptions.PROPERTY_ICON_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OPACITY - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the pointAnnotation should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of pointAnnotations to build
   * @return the list of built pointAnnotations
   */
  fun create(featureCollection: FeatureCollection): List<PointAnnotation> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        PointAnnotationOptions.fromFeature(it)
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
    return PointAnnotation.ID_KEY
  }

  // Property accessors
  /**
   * The IconAllowOverlap property
   *
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   */
  var iconAllowOverlap: Boolean?
    /**
     * Get the IconAllowOverlap property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconAllowOverlap
    }
    /**
     * Set the IconAllowOverlap property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap").value
      }
      setLayerProperty(wrappedValue, "icon-allow-overlap")
    }

  /**
   * The IconIgnorePlacement property
   *
   * If true, other symbols can be visible even if they collide with the icon.
   */
  var iconIgnorePlacement: Boolean?
    /**
     * Get the IconIgnorePlacement property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconIgnorePlacement
    }
    /**
     * Set the IconIgnorePlacement property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement").value
      }
      setLayerProperty(wrappedValue, "icon-ignore-placement")
    }

  /**
   * The IconKeepUpright property
   *
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   */
  var iconKeepUpright: Boolean?
    /**
     * Get the IconKeepUpright property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconKeepUpright
    }
    /**
     * Set the IconKeepUpright property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright").value
      }
      setLayerProperty(wrappedValue, "icon-keep-upright")
    }

  /**
   * The IconOptional property
   *
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   */
  var iconOptional: Boolean?
    /**
     * Get the IconOptional property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconOptional
    }
    /**
     * Set the IconOptional property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional").value
      }
      setLayerProperty(wrappedValue, "icon-optional")
    }

  /**
   * The IconPadding property
   *
   * Size of the additional area around the icon bounding box used for detecting symbol collisions. The unit of iconPadding is in density-independent pixels.
   */
  var iconPadding: Double?
    /**
     * Get the IconPadding property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.iconPadding
    }
    /**
     * Set the IconPadding property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding").value
      }
      setLayerProperty(wrappedValue, "icon-padding")
    }

  /**
   * The IconPitchAlignment property
   *
   * Orientation of icon when map is pitched.
   */
  var iconPitchAlignment: IconPitchAlignment?
    /**
     * Get the IconPitchAlignment property
     *
     * @return property wrapper value around IconPitchAlignment
     */
    get(): IconPitchAlignment? {
      return layer.iconPitchAlignment
    }
    /**
     * Set the IconPitchAlignment property
     * @param value property wrapper value around IconPitchAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment").value
      }
      setLayerProperty(wrappedValue, "icon-pitch-alignment")
    }

  /**
   * The IconRotationAlignment property
   *
   * In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of icons.
   */
  var iconRotationAlignment: IconRotationAlignment?
    /**
     * Get the IconRotationAlignment property
     *
     * @return property wrapper value around IconRotationAlignment
     */
    get(): IconRotationAlignment? {
      return layer.iconRotationAlignment
    }
    /**
     * Set the IconRotationAlignment property
     * @param value property wrapper value around IconRotationAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment").value
      }
      setLayerProperty(wrappedValue, "icon-rotation-alignment")
    }

  /**
   * The SymbolAvoidEdges property
   *
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   */
  var symbolAvoidEdges: Boolean?
    /**
     * Get the SymbolAvoidEdges property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.symbolAvoidEdges
    }
    /**
     * Set the SymbolAvoidEdges property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges").value
      }
      setLayerProperty(wrappedValue, "symbol-avoid-edges")
    }

  /**
   * The SymbolPlacement property
   *
   * Label placement relative to its geometry.
   */
  var symbolPlacement: SymbolPlacement?
    /**
     * Get the SymbolPlacement property
     *
     * @return property wrapper value around SymbolPlacement
     */
    get(): SymbolPlacement? {
      return layer.symbolPlacement
    }
    /**
     * Set the SymbolPlacement property
     * @param value property wrapper value around SymbolPlacement
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement").value
      }
      setLayerProperty(wrappedValue, "symbol-placement")
    }

  /**
   * The SymbolSpacing property
   *
   * Distance between two symbol anchors. The unit of symbolSpacing is in density-independent pixels.
   */
  var symbolSpacing: Double?
    /**
     * Get the SymbolSpacing property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.symbolSpacing
    }
    /**
     * Set the SymbolSpacing property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing").value
      }
      setLayerProperty(wrappedValue, "symbol-spacing")
    }

  /**
   * The SymbolZElevate property
   *
   * Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when {@link PropertyFactory#fillExtrusionHeight} is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or {@link PropertyFactory#symbolSortKey} are applied.
   */
  var symbolZElevate: Boolean?
    /**
     * Get the SymbolZElevate property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.symbolZElevate
    }
    /**
     * Set the SymbolZElevate property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-elevate").value
      }
      setLayerProperty(wrappedValue, "symbol-z-elevate")
    }

  /**
   * The SymbolZOrder property
   *
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use {@link PropertyFactory#symbolSortKey}.
   */
  var symbolZOrder: SymbolZOrder?
    /**
     * Get the SymbolZOrder property
     *
     * @return property wrapper value around SymbolZOrder
     */
    get(): SymbolZOrder? {
      return layer.symbolZOrder
    }
    /**
     * Set the SymbolZOrder property
     * @param value property wrapper value around SymbolZOrder
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order").value
      }
      setLayerProperty(wrappedValue, "symbol-z-order")
    }

  /**
   * The TextAllowOverlap property
   *
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   */
  var textAllowOverlap: Boolean?
    /**
     * Get the TextAllowOverlap property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textAllowOverlap
    }
    /**
     * Set the TextAllowOverlap property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap").value
      }
      setLayerProperty(wrappedValue, "text-allow-overlap")
    }

  /**
   * The TextFont property
   *
   * Font stack to use for displaying text.
   */
  var textFont: List<String>?
    /**
     * Get the TextFont property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer.textFont
    }
    /**
     * Set the TextFont property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font").value
      }
      setLayerProperty(wrappedValue, "text-font")
    }

  /**
   * The TextIgnorePlacement property
   *
   * If true, other symbols can be visible even if they collide with the text.
   */
  var textIgnorePlacement: Boolean?
    /**
     * Get the TextIgnorePlacement property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textIgnorePlacement
    }
    /**
     * Set the TextIgnorePlacement property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement").value
      }
      setLayerProperty(wrappedValue, "text-ignore-placement")
    }

  /**
   * The TextKeepUpright property
   *
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   */
  var textKeepUpright: Boolean?
    /**
     * Get the TextKeepUpright property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textKeepUpright
    }
    /**
     * Set the TextKeepUpright property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright").value
      }
      setLayerProperty(wrappedValue, "text-keep-upright")
    }

  /**
   * The TextMaxAngle property
   *
   * Maximum angle change between adjacent characters. The unit of textMaxAngle is in degrees.
   */
  var textMaxAngle: Double?
    /**
     * Get the TextMaxAngle property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.textMaxAngle
    }
    /**
     * Set the TextMaxAngle property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle").value
      }
      setLayerProperty(wrappedValue, "text-max-angle")
    }

  /**
   * The TextOptional property
   *
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   */
  var textOptional: Boolean?
    /**
     * Get the TextOptional property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textOptional
    }
    /**
     * Set the TextOptional property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional").value
      }
      setLayerProperty(wrappedValue, "text-optional")
    }

  /**
   * The TextPadding property
   *
   * Size of the additional area around the text bounding box used for detecting symbol collisions. The unit of textPadding is in density-independent pixels.
   */
  var textPadding: Double?
    /**
     * Get the TextPadding property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.textPadding
    }
    /**
     * Set the TextPadding property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding").value
      }
      setLayerProperty(wrappedValue, "text-padding")
    }

  /**
   * The TextPitchAlignment property
   *
   * Orientation of text when map is pitched.
   */
  var textPitchAlignment: TextPitchAlignment?
    /**
     * Get the TextPitchAlignment property
     *
     * @return property wrapper value around TextPitchAlignment
     */
    get(): TextPitchAlignment? {
      return layer.textPitchAlignment
    }
    /**
     * Set the TextPitchAlignment property
     * @param value property wrapper value around TextPitchAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment").value
      }
      setLayerProperty(wrappedValue, "text-pitch-alignment")
    }

  /**
   * The TextRotationAlignment property
   *
   * In combination with {@link Property.SYMBOL_PLACEMENT}, determines the rotation behavior of the individual glyphs forming the text.
   */
  var textRotationAlignment: TextRotationAlignment?
    /**
     * Get the TextRotationAlignment property
     *
     * @return property wrapper value around TextRotationAlignment
     */
    get(): TextRotationAlignment? {
      return layer.textRotationAlignment
    }
    /**
     * Set the TextRotationAlignment property
     * @param value property wrapper value around TextRotationAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment").value
      }
      setLayerProperty(wrappedValue, "text-rotation-alignment")
    }

  /**
   * The TextVariableAnchor property
   *
   * To increase the chance of placing high-priority labels on the map, you can provide an array of {@link Property.TEXT_ANCHOR} locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the {@link PropertyFactory#textRadialOffset} or the two-dimensional {@link PropertyFactory#textOffset}.
   */
  var textVariableAnchor: List<String>?
    /**
     * Get the TextVariableAnchor property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer.textVariableAnchor
    }
    /**
     * Set the TextVariableAnchor property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor").value
      }
      setLayerProperty(wrappedValue, "text-variable-anchor")
    }

  /**
   * The TextWritingMode property
   *
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  var textWritingMode: List<String>?
    /**
     * Get the TextWritingMode property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer.textWritingMode
    }
    /**
     * Set the TextWritingMode property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode").value
      }
      setLayerProperty(wrappedValue, "text-writing-mode")
    }

  /**
   * The IconColorSaturation property
   *
   * Increase or reduce the saturation of the symbol icon.
   */
  var iconColorSaturation: Double?
    /**
     * Get the IconColorSaturation property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.iconColorSaturation
    }
    /**
     * Set the IconColorSaturation property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color-saturation").value
      }
      setLayerProperty(wrappedValue, "icon-color-saturation")
    }

  /**
   * The IconTranslate property
   *
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. The unit of iconTranslate is in density-independent pixels.
   */
  var iconTranslate: List<Double>?
    /**
     * Get the IconTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.iconTranslate
    }
    /**
     * Set the IconTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate").value
      }
      setLayerProperty(wrappedValue, "icon-translate")
    }

  /**
   * The IconTranslateAnchor property
   *
   * Controls the frame of reference for {@link PropertyFactory#iconTranslate}.
   */
  var iconTranslateAnchor: IconTranslateAnchor?
    /**
     * Get the IconTranslateAnchor property
     *
     * @return property wrapper value around IconTranslateAnchor
     */
    get(): IconTranslateAnchor? {
      return layer.iconTranslateAnchor
    }
    /**
     * Set the IconTranslateAnchor property
     * @param value property wrapper value around IconTranslateAnchor
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor").value
      }
      setLayerProperty(wrappedValue, "icon-translate-anchor")
    }

  /**
   * The TextTranslate property
   *
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. The unit of textTranslate is in density-independent pixels.
   */
  var textTranslate: List<Double>?
    /**
     * Get the TextTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.textTranslate
    }
    /**
     * Set the TextTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate").value
      }
      setLayerProperty(wrappedValue, "text-translate")
    }

  /**
   * The TextTranslateAnchor property
   *
   * Controls the frame of reference for {@link PropertyFactory#textTranslate}.
   */
  var textTranslateAnchor: TextTranslateAnchor?
    /**
     * Get the TextTranslateAnchor property
     *
     * @return property wrapper value around TextTranslateAnchor
     */
    get(): TextTranslateAnchor? {
      return layer.textTranslateAnchor
    }
    /**
     * Set the TextTranslateAnchor property
     * @param value property wrapper value around TextTranslateAnchor
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor").value
      }
      setLayerProperty(wrappedValue, "text-translate-anchor")
    }

  /**
   * The Slot property
   *
   * The slot this layer is assigned to. If specified, and a slot with that name exists, it will be placed at that position in the layer order.
   */
  var slot: String?
    /**
     * Get the Slot property
     *
     * @return property wrapper value around String
     */
    get(): String? {
      return layer.slot
    }
    /**
     * Set the Slot property
     * @param value property wrapper value around String
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "slot").value
      }
      setLayerProperty(wrappedValue, "slot")
    }

  /**
   * The TextLineHeight property
   *
   * Text leading value for multi-line text.
   */
  @Deprecated(
    "text-line-height property is now data driven, use `PointAnnotation.textLineHeight` instead.",
    ReplaceWith("PointAnnotation.textLineHeight")
  )
  var textLineHeight: Double?
    /**
     * Get the TextLineHeight property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.textLineHeight
    }
    /**
     * Set the TextLineHeight property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("pointAnnotation", "text-line-height").value
      }
      setLayerProperty(wrappedValue, "text-line-height")
    }

  /**
   * The IconTextFit property
   *
   * Scales the icon to fit around the associated text.
   */
  @Deprecated(
    "icon-text-fit property is now data driven, use `PointAnnotation.iconTextFit` instead.",
    ReplaceWith("PointAnnotation.iconTextFit")
  )
  var iconTextFit: IconTextFit?
    /**
     * Get the IconTextFit property
     *
     * @return property wrapper value around IconTextFit
     */
    get(): IconTextFit? {
      return layer.iconTextFit
    }
    /**
     * Set the IconTextFit property
     * @param value property wrapper value around IconTextFit
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("pointAnnotation", "icon-text-fit").value
      }
      setLayerProperty(wrappedValue, "icon-text-fit")
    }

  /**
   * The IconTextFitPadding property
   *
   * Size of the additional area added to dimensions determined by {@link Property.ICON_TEXT_FIT}, in clockwise order: top, right, bottom, left.
   */
  @Deprecated(
    "icon-text-fit-padding property is now data driven, use `PointAnnotation.iconTextFitPadding` instead.",
    ReplaceWith("PointAnnotation.iconTextFitPadding")
  )
  var iconTextFitPadding: List<Double>?
    /**
     * Get the IconTextFitPadding property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.iconTextFitPadding
    }
    /**
     * Set the IconTextFitPadding property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("pointAnnotation", "icon-text-fit-padding").value
      }
      setLayerProperty(wrappedValue, "icon-text-fit-padding")
    }
  /**
   * The filter on the managed pointAnnotations.
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed pointAnnotations.
     *
     * @return expression
     */
    get() = layer.filter
    /**
     * Set filter on the managed pointAnnotations.
     *
     * @param value expression
     */
    set(value) {
      value?.let {
        layer.filter(it)
        dragLayer.filter(it)
      }
    }

  init {
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_ANCHOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_IMAGE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_OFFSET] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_ROTATE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_SIZE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_ANCHOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_FIELD] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_OFFSET] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_ROTATE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_SIZE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_OPACITY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_OPACITY] = false

    // Show all icons and texts by default.
    iconAllowOverlap = true
    textAllowOverlap = true
    iconIgnorePlacement = true
    textIgnorePlacement = true
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
 * Extension function to create a PointAnnotationManager instance.
 */
fun AnnotationPlugin.createPointAnnotationManager(
  annotationConfig: AnnotationConfig? = null
): PointAnnotationManager {
  return createAnnotationManager(AnnotationType.PointAnnotation, annotationConfig) as PointAnnotationManager
}