// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.mapbox.geojson.*
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong

/**
 * The polylineAnnotation manager allows to add polylineAnnotations to a map.
 */
class PolylineAnnotationManager(
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<LineString, PolylineAnnotation, PolylineAnnotationOptions, OnPolylineAnnotationDragListener, OnPolylineAnnotationClickListener, OnPolylineAnnotationLongClickListener, OnPolylineAnnotationInteractionListener, LineLayer>(
    delegateProvider, annotationConfig, ID_GENERATOR.incrementAndGet(), "polylineAnnotation", ::LineLayer
  ) {

  @OptIn(MapboxExperimental::class)
  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      PolylineAnnotationOptions.PROPERTY_LINE_JOIN -> {
        layer.lineJoin(get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN))
        dragLayer.lineJoin(get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY -> {
        layer.lineSortKey(get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY))
        dragLayer.lineSortKey(get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET -> {
        layer.lineZOffset(get(PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET))
        dragLayer.lineZOffset(get(PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_BLUR -> {
        layer.lineBlur(get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR))
        dragLayer.lineBlur(get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR -> {
        layer.lineBorderColor(get(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR))
        dragLayer.lineBorderColor(get(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH -> {
        layer.lineBorderWidth(get(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH))
        dragLayer.lineBorderWidth(get(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_COLOR -> {
        layer.lineColor(get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR))
        dragLayer.lineColor(get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH -> {
        layer.lineGapWidth(get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH))
        dragLayer.lineGapWidth(get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_OFFSET -> {
        layer.lineOffset(get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET))
        dragLayer.lineOffset(get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_OPACITY -> {
        layer.lineOpacity(get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY))
        dragLayer.lineOpacity(get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_PATTERN -> {
        layer.linePattern(get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN))
        dragLayer.linePattern(get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN))
      }
      PolylineAnnotationOptions.PROPERTY_LINE_WIDTH -> {
        layer.lineWidth(get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH))
        dragLayer.lineWidth(get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH))
      }
    }
  }

  /**
   * Create a list of polylineAnnotations on the map.
   *
   * PolylineAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PolylineAnnotationOptions.PROPERTY_LINE_JOIN - LineJoin
   * PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_BLUR - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR - String
   * PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_COLOR - String
   * PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_OFFSET - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_OPACITY - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_PATTERN - String
   * PolylineAnnotationOptions.PROPERTY_LINE_WIDTH - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the polylineAnnotation should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of polylineAnnotations to build
   * @return the list of built polylineAnnotations
   */
  fun create(json: String): List<PolylineAnnotation> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of polylineAnnotations on the map.
   *
   * PolylineAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PolylineAnnotationOptions.PROPERTY_LINE_JOIN - LineJoin
   * PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_BLUR - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR - String
   * PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_COLOR - String
   * PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_OFFSET - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_OPACITY - Double
   * PolylineAnnotationOptions.PROPERTY_LINE_PATTERN - String
   * PolylineAnnotationOptions.PROPERTY_LINE_WIDTH - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the polylineAnnotation should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of polylineAnnotations to build
   * @return the list of built polylineAnnotations
   */
  fun create(featureCollection: FeatureCollection): List<PolylineAnnotation> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        PolylineAnnotationOptions.fromFeature(it)
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
    return PolylineAnnotation.ID_KEY
  }

  // Property accessors
  /**
   * The LineTrimColor property
   *
   * The color to be used for rendering the trimmed line section that is defined by the {@link PropertyFactory#lineTrimOffset} property.
   */
  @MapboxExperimental
  @Deprecated(
    "This property is deprecated, and will be removed in next major release. Use [lineTrimColorString] property instead.",
    replaceWith = ReplaceWith("lineTrimColorString")
  )
  var lineTrimColor: String?
    /**
     * Get the lineTrimColor property.
     * @return color value for String
     */
    get() {
      return lineTrimColorString
    }
    /**
     * Set the lineTrimColor property.
     *
     * @param value color value for String
     */
    set(value) {
      lineTrimColorString = value
    }

  /**
   * The LineCap property
   *
   * The display of line endings. Default value: "butt".
   */
  var lineCap: LineCap?
    /**
     * Get the LineCap property
     *
     * @return property wrapper value around LineCap
     */
    get(): LineCap? {
      return layer.lineCap
    }
    /**
     * Set the LineCap property
     * @param value property wrapper value around LineCap
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cap").value
      }
      setLayerProperty(wrappedValue, "line-cap")
    }

  /**
   * The LineCrossSlope property
   *
   * Defines the slope of an elevated line. A value of 0 creates a horizontal line. A value of 1 creates a vertical line. Other values are currently not supported. If undefined, the line follows the terrain slope. This is an experimental property with some known issues:  - Vertical lines don't support line caps  - `line-join: round` is not supported with this property
   */
  @MapboxExperimental
  var lineCrossSlope: Double?
    /**
     * Get the LineCrossSlope property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.lineCrossSlope
    }
    /**
     * Set the LineCrossSlope property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cross-slope").value
      }
      setLayerProperty(wrappedValue, "line-cross-slope")
    }

  /**
   * The LineElevationReference property
   *
   * Selects the base of line-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   */
  @MapboxExperimental
  var lineElevationReference: LineElevationReference?
    /**
     * Get the LineElevationReference property
     *
     * @return property wrapper value around LineElevationReference
     */
    get(): LineElevationReference? {
      return layer.lineElevationReference
    }
    /**
     * Set the LineElevationReference property
     * @param value property wrapper value around LineElevationReference
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-elevation-reference").value
      }
      setLayerProperty(wrappedValue, "line-elevation-reference")
    }

  /**
   * The default lineJoin for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The display of lines when joining. Default value: "miter".
   */
  var lineJoin: LineJoin?
    /**
     * Get the lineJoin property.
     *
     * @return property wrapper value around LineJoin
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)
      value?.let {
        return LineJoin.valueOf(it.asString.uppercase(Locale.US))
      }
      return null
    }
    /**
     * Set the lineJoin property.
     *
     * @param value constant property value for LineJoin
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_JOIN, value.value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The LineMiterLimit property
   *
   * Used to automatically convert miter joins to bevel joins for sharp angles. Default value: 2.
   */
  var lineMiterLimit: Double?
    /**
     * Get the LineMiterLimit property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.lineMiterLimit
    }
    /**
     * Set the LineMiterLimit property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit").value
      }
      setLayerProperty(wrappedValue, "line-miter-limit")
    }

  /**
   * The LineRoundLimit property
   *
   * Used to automatically convert round joins to miter joins for shallow angles. Default value: 1.05.
   */
  var lineRoundLimit: Double?
    /**
     * Get the LineRoundLimit property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.lineRoundLimit
    }
    /**
     * Set the LineRoundLimit property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit").value
      }
      setLayerProperty(wrappedValue, "line-round-limit")
    }

  /**
   * The default lineSortKey for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var lineSortKey: Double?
    /**
     * Get the lineSortKey property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineSortKey property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The LineWidthUnit property
   *
   * Selects the unit of line-width. The same unit is automatically used for line-blur and line-offset. Note: This is an experimental property and might be removed in a future release. Default value: "pixels".
   */
  @MapboxExperimental
  var lineWidthUnit: LineWidthUnit?
    /**
     * Get the LineWidthUnit property
     *
     * @return property wrapper value around LineWidthUnit
     */
    get(): LineWidthUnit? {
      return layer.lineWidthUnit
    }
    /**
     * Set the LineWidthUnit property
     * @param value property wrapper value around LineWidthUnit
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width-unit").value
      }
      setLayerProperty(wrappedValue, "line-width-unit")
    }

  /**
   * The default lineZOffset for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Vertical offset from ground, in meters. Defaults to 0. This is an experimental property with some known issues:  - Not supported for globe projection at the moment  - Elevated line discontinuity is possible on tile borders with terrain enabled  - Rendering artifacts can happen near line joins and line caps depending on the line styling  - Rendering artifacts relating to `line-opacity` and `line-blur`  - Elevated line visibility is determined by layer order  - Z-fighting issues can happen with intersecting elevated lines  - Elevated lines don't cast shadows Default value: 0.
   */
  @MapboxExperimental
  var lineZOffset: Double?
    /**
     * Get the lineZOffset property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineZOffset property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default lineBlur for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Blur applied to the line, in pixels. Default value: 0. Minimum value: 0. The unit of lineBlur is in pixels.
   */
  var lineBlur: Double?
    /**
     * Get the lineBlur property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineBlur property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_BLUR, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default lineBorderColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
   */
  var lineBorderColorInt: Int?
    /**
     * Get the lineBorderColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the lineBorderColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default lineBorderColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
   */
  var lineBorderColorString: String?
    /**
     * Get the lineBorderColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the lineBorderColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default lineBorderWidth for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The width of the line border. A value of zero means no border. Default value: 0. Minimum value: 0.
   */
  var lineBorderWidth: Double?
    /**
     * Get the lineBorderWidth property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineBorderWidth property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default lineColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color with which the line will be drawn. Default value: "#000000".
   */
  var lineColorInt: Int?
    /**
     * Get the lineColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the lineColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PolylineAnnotationOptions.PROPERTY_LINE_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default lineColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color with which the line will be drawn. Default value: "#000000".
   */
  var lineColorString: String?
    /**
     * Get the lineColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the lineColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_COLOR, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The LineDasharray property
   *
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. Minimum value: 0. The unit of lineDasharray is in line widths.
   */
  var lineDasharray: List<Double>?
    /**
     * Get the LineDasharray property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.lineDasharray
    }
    /**
     * Set the LineDasharray property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray").value
      }
      setLayerProperty(wrappedValue, "line-dasharray")
    }

  /**
   * The LineDepthOcclusionFactor property
   *
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded. Default value: 1. Value range: [0, 1]
   */
  var lineDepthOcclusionFactor: Double?
    /**
     * Get the LineDepthOcclusionFactor property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.lineDepthOcclusionFactor
    }
    /**
     * Set the LineDepthOcclusionFactor property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor").value
      }
      setLayerProperty(wrappedValue, "line-depth-occlusion-factor")
    }

  /**
   * The LineEmissiveStrength property
   *
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of lineEmissiveStrength is in intensity.
   */
  var lineEmissiveStrength: Double?
    /**
     * Get the LineEmissiveStrength property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.lineEmissiveStrength
    }
    /**
     * Set the LineEmissiveStrength property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength").value
      }
      setLayerProperty(wrappedValue, "line-emissive-strength")
    }

  /**
   * The default lineGapWidth for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. Default value: 0. Minimum value: 0. The unit of lineGapWidth is in pixels.
   */
  var lineGapWidth: Double?
    /**
     * Get the lineGapWidth property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineGapWidth property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The LineOcclusionOpacity property
   *
   * Opacity multiplier (multiplies line-opacity value) of the line part that is occluded by 3D objects. Value 0 hides occluded part, value 1 means the same opacity as non-occluded part. The property is not supported when `line-opacity` has data-driven styling. Default value: 0. Value range: [0, 1]
   */
  var lineOcclusionOpacity: Double?
    /**
     * Get the LineOcclusionOpacity property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.lineOcclusionOpacity
    }
    /**
     * Set the LineOcclusionOpacity property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-occlusion-opacity").value
      }
      setLayerProperty(wrappedValue, "line-occlusion-opacity")
    }

  /**
   * The default lineOffset for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. Default value: 0. The unit of lineOffset is in pixels.
   */
  var lineOffset: Double?
    /**
     * Get the lineOffset property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineOffset property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default lineOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity at which the line will be drawn. Default value: 1. Value range: [0, 1]
   */
  var lineOpacity: Double?
    /**
     * Get the lineOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default linePattern for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  var linePattern: String?
    /**
     * Get the linePattern property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the linePattern property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The LineTranslate property
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of lineTranslate is in pixels.
   */
  var lineTranslate: List<Double>?
    /**
     * Get the LineTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.lineTranslate
    }
    /**
     * Set the LineTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate").value
      }
      setLayerProperty(wrappedValue, "line-translate")
    }

  /**
   * The LineTranslateAnchor property
   *
   * Controls the frame of reference for `line-translate`. Default value: "map".
   */
  var lineTranslateAnchor: LineTranslateAnchor?
    /**
     * Get the LineTranslateAnchor property
     *
     * @return property wrapper value around LineTranslateAnchor
     */
    get(): LineTranslateAnchor? {
      return layer.lineTranslateAnchor
    }
    /**
     * Set the LineTranslateAnchor property
     * @param value property wrapper value around LineTranslateAnchor
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-anchor").value
      }
      setLayerProperty(wrappedValue, "line-translate-anchor")
    }

  /**
   * The LineTrimColor property
   *
   * The color to be used for rendering the trimmed line section that is defined by the `line-trim-offset` property. Default value: "transparent".
   */
  @MapboxExperimental
  var lineTrimColorInt: Int?
    /**
     * Get the lineTrimColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = layer.lineTrimColor
      value?.let {
        ColorUtils.rgbaToColor(it)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the lineTrimColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(ColorUtils.colorToRgbaString(value))
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color").value
      }
      setLayerProperty(wrappedValue, "line-trim-color")
    }

  /**
   * Get the LineTrimColor property
   *
   * The color to be used for rendering the trimmed line section that is defined by the `line-trim-offset` property. Default value: "transparent".
   */
  @MapboxExperimental
  var lineTrimColorString: String?
    /**
     * Get the lineTrimColor property.
     * @return color value for String
     */
    get() {
      return layer.lineTrimColor
    }
    /**
     * Set the lineTrimColor property.
     *
     * @param value color value for String
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-color").value
      }
      setLayerProperty(wrappedValue, "line-trim-color")
    }

  /**
   * The LineTrimFadeRange property
   *
   * The fade range for the trim-start and trim-end points is defined by the `line-trim-offset` property. The first element of the array represents the fade range from the trim-start point toward the end of the line, while the second element defines the fade range from the trim-end point toward the beginning of the line. The fade result is achieved by interpolating between `line-trim-color` and the color specified by the `line-color` or the `line-gradient` property. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  @MapboxExperimental
  var lineTrimFadeRange: List<Double>?
    /**
     * Get the LineTrimFadeRange property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.lineTrimFadeRange
    }
    /**
     * Set the LineTrimFadeRange property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-fade-range").value
      }
      setLayerProperty(wrappedValue, "line-trim-fade-range")
    }

  /**
   * The LineTrimOffset property
   *
   * The line part between [trim-start, trim-end] will be painted using `line-trim-color,` which is transparent by default to produce a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0]. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
   */
  var lineTrimOffset: List<Double>?
    /**
     * Get the LineTrimOffset property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.lineTrimOffset
    }
    /**
     * Set the LineTrimOffset property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-offset").value
      }
      setLayerProperty(wrappedValue, "line-trim-offset")
    }

  /**
   * The default lineWidth for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Stroke thickness. Default value: 1. Minimum value: 0. The unit of lineWidth is in pixels.
   */
  var lineWidth: Double?
    /**
     * Get the lineWidth property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineWidth property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH, value)
        enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)
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
        StyleManager.getStyleLayerPropertyDefaultValue("line", "slot").value
      }
      setLayerProperty(wrappedValue, "slot")
    }

  /**
   * The filter on the managed polylineAnnotations.
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed polylineAnnotations.
     *
     * @return expression
     */
    get() = layer.filter
    /**
     * Set filter on the managed polylineAnnotations.
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
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_JOIN] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_Z_OFFSET] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_BLUR] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_BORDER_COLOR] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_BORDER_WIDTH] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_COLOR] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_OFFSET] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_OPACITY] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_PATTERN] = false
    dataDrivenPropertyUsageMap[PolylineAnnotationOptions.PROPERTY_LINE_WIDTH] = false
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
 * Extension function to create a PolylineAnnotationManager instance.
 */
fun AnnotationPlugin.createPolylineAnnotationManager(
  annotationConfig: AnnotationConfig? = null
): PolylineAnnotationManager {
  return createAnnotationManager(AnnotationType.PolylineAnnotation, annotationConfig) as PolylineAnnotationManager
}