// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.mapbox.geojson.*
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.concurrent.atomic.AtomicLong

/**
 * The polygonAnnotation manager allows to add polygonAnnotations to a map.
 */
class PolygonAnnotationManager(
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<Polygon, PolygonAnnotation, PolygonAnnotationOptions, OnPolygonAnnotationDragListener, OnPolygonAnnotationClickListener, OnPolygonAnnotationLongClickListener, OnPolygonAnnotationInteractionListener, FillLayer>(
    delegateProvider, annotationConfig, ID_GENERATOR.incrementAndGet(), "polygonAnnotation", ::FillLayer
  ) {

  @OptIn(MapboxExperimental::class)
  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL -> {
        layer.fillConstructBridgeGuardRail(get(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL))
        dragLayer.fillConstructBridgeGuardRail(get(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY -> {
        layer.fillSortKey(get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY))
        dragLayer.fillSortKey(get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR -> {
        layer.fillBridgeGuardRailColor(get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR))
        dragLayer.fillBridgeGuardRailColor(get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_COLOR -> {
        layer.fillColor(get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR))
        dragLayer.fillColor(get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_OPACITY -> {
        layer.fillOpacity(get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY))
        dragLayer.fillOpacity(get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR -> {
        layer.fillOutlineColor(get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR))
        dragLayer.fillOutlineColor(get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_PATTERN -> {
        layer.fillPattern(get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN))
        dragLayer.fillPattern(get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR -> {
        layer.fillTunnelStructureColor(get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR))
        dragLayer.fillTunnelStructureColor(get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET -> {
        layer.fillZOffset(get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET))
        dragLayer.fillZOffset(get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME -> {
        layer.fillBridgeGuardRailColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME))
        dragLayer.fillBridgeGuardRailColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME -> {
        layer.fillColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME))
        dragLayer.fillColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME -> {
        layer.fillOutlineColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME))
        dragLayer.fillOutlineColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME -> {
        layer.fillTunnelStructureColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME))
        dragLayer.fillTunnelStructureColorUseTheme(get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME))
      }
    }
  }

  /**
   * Create a list of polygonAnnotations on the map.
   *
   * PolygonAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL - Boolean
   * PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_OPACITY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_PATTERN - String
   * PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME - String
   * PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME - String
   * PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME - String
   * PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME - String
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the polygonAnnotation should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of polygonAnnotations to build
   * @return the list of built polygonAnnotations
   */
  fun create(json: String): List<PolygonAnnotation> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of polygonAnnotations on the map.
   *
   * PolygonAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL - Boolean
   * PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_OPACITY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_PATTERN - String
   * PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME - String
   * PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME - String
   * PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME - String
   * PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME - String
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the polygonAnnotation should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of polygonAnnotations to build
   * @return the list of built polygonAnnotations
   */
  fun create(featureCollection: FeatureCollection): List<PolygonAnnotation> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        PolygonAnnotationOptions.fromFeature(it)
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
    return PolygonAnnotation.ID_KEY
  }

  // Property accessors
  /**
   * The default fillConstructBridgeGuardRail for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   */
  @MapboxExperimental
  var fillConstructBridgeGuardRail: Boolean?
    /**
     * Get the fillConstructBridgeGuardRail property.
     *
     * @return property wrapper value around Boolean
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL)
      value?.let {
        return it.asString.toBoolean()
      }
      return null
    }
    /**
     * Set the fillConstructBridgeGuardRail property.
     *
     * @param value constant property value for Boolean
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The FillElevationReference property
   *
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   */
  @MapboxExperimental
  var fillElevationReference: FillElevationReference?
    /**
     * Get the FillElevationReference property
     *
     * @return property wrapper value around FillElevationReference
     */
    get(): FillElevationReference? {
      return layer.fillElevationReference
    }
    /**
     * Set the FillElevationReference property
     * @param value property wrapper value around FillElevationReference
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-elevation-reference").value
      }
      setLayerProperty(wrappedValue, "fill-elevation-reference")
    }

  /**
   * The default fillSortKey for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var fillSortKey: Double?
    /**
     * Get the fillSortKey property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillSortKey property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The FillAntialias property
   *
   * Whether or not the fill should be antialiased. Default value: true.
   */
  var fillAntialias: Boolean?
    /**
     * Get the FillAntialias property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.fillAntialias
    }
    /**
     * Set the FillAntialias property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias").value
      }
      setLayerProperty(wrappedValue, "fill-antialias")
    }

  /**
   * The default fillBridgeGuardRailColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  var fillBridgeGuardRailColorInt: Int?
    /**
     * Get the fillBridgeGuardRailColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillBridgeGuardRailColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillBridgeGuardRailColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  var fillBridgeGuardRailColorString: String?
    /**
     * Get the fillBridgeGuardRailColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillBridgeGuardRailColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   */
  var fillColorInt: Int?
    /**
     * Get the fillColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   */
  var fillColorString: String?
    /**
     * Get the fillColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The FillEmissiveStrength property
   *
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   */
  var fillEmissiveStrength: Double?
    /**
     * Get the FillEmissiveStrength property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.fillEmissiveStrength
    }
    /**
     * Set the FillEmissiveStrength property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-emissive-strength").value
      }
      setLayerProperty(wrappedValue, "fill-emissive-strength")
    }

  /**
   * The default fillOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   */
  var fillOpacity: Double?
    /**
     * Get the fillOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillOutlineColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  var fillOutlineColorInt: Int?
    /**
     * Get the fillOutlineColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillOutlineColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillOutlineColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  var fillOutlineColorString: String?
    /**
     * Get the fillOutlineColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillOutlineColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillPattern for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  var fillPattern: String?
    /**
     * Get the fillPattern property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillPattern property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The FillTranslate property
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   */
  var fillTranslate: List<Double>?
    /**
     * Get the FillTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.fillTranslate
    }
    /**
     * Set the FillTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate").value
      }
      setLayerProperty(wrappedValue, "fill-translate")
    }

  /**
   * The FillTranslateAnchor property
   *
   * Controls the frame of reference for `fill-translate`. Default value: "map".
   */
  var fillTranslateAnchor: FillTranslateAnchor?
    /**
     * Get the FillTranslateAnchor property
     *
     * @return property wrapper value around FillTranslateAnchor
     */
    get(): FillTranslateAnchor? {
      return layer.fillTranslateAnchor
    }
    /**
     * Set the FillTranslateAnchor property
     * @param value property wrapper value around FillTranslateAnchor
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor").value
      }
      setLayerProperty(wrappedValue, "fill-translate-anchor")
    }

  /**
   * The default fillTunnelStructureColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  var fillTunnelStructureColorInt: Int?
    /**
     * Get the fillTunnelStructureColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillTunnelStructureColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillTunnelStructureColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  var fillTunnelStructureColorString: String?
    /**
     * Get the fillTunnelStructureColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillTunnelStructureColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillZOffset for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  var fillZOffset: Double?
    /**
     * Get the fillZOffset property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillZOffset property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)
      }
      // Update child annotation property if not being set.
      update(annotations)
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
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "slot").value
      }
      setLayerProperty(wrappedValue, "slot")
    }

  /**
   * The MaxZoom property
   *
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  var maxZoom: Double?
    /**
     * Get the MaxZoom property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.maxZoom
    }
    /**
     * Set the MaxZoom property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "maxzoom").value
      }
      setLayerProperty(wrappedValue, "maxzoom")
    }

  /**
   * The MinZoom property
   *
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  var minZoom: Double?
    /**
     * Get the MinZoom property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.minZoom
    }
    /**
     * Set the MinZoom property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "minzoom").value
      }
      setLayerProperty(wrappedValue, "minzoom")
    }

  /**
   * The default fillBridgeGuardRailColorUseTheme for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * This property defines whether the `fillBridgeGuardRailColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  var fillBridgeGuardRailColorUseTheme: String?
    /**
     * Get the fillBridgeGuardRailColorUseTheme property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillBridgeGuardRailColorUseTheme property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillColorUseTheme for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * This property defines whether the `fillColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  var fillColorUseTheme: String?
    /**
     * Get the fillColorUseTheme property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillColorUseTheme property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillOutlineColorUseTheme for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * This property defines whether the `fillOutlineColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  var fillOutlineColorUseTheme: String?
    /**
     * Get the fillOutlineColorUseTheme property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillOutlineColorUseTheme property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default fillTunnelStructureColorUseTheme for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * This property defines whether the `fillTunnelStructureColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  @MapboxExperimental
  var fillTunnelStructureColorUseTheme: String?
    /**
     * Get the fillTunnelStructureColorUseTheme property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillTunnelStructureColorUseTheme property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME, value)
        enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The filter on the managed polygonAnnotations.
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed polygonAnnotations.
     *
     * @return expression
     */
    get() = layer.filter
    /**
     * Set filter on the managed polygonAnnotations.
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
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_OPACITY] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_PATTERN] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME] = false
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
 * Extension function to create a PolygonAnnotationManager instance.
 */
fun AnnotationPlugin.createPolygonAnnotationManager(
  annotationConfig: AnnotationConfig? = null
): PolygonAnnotationManager {
  return createAnnotationManager(AnnotationType.PolygonAnnotation, annotationConfig) as PolygonAnnotationManager
}