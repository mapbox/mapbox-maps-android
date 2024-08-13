// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import com.mapbox.geojson.*
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
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
   * The LineCap property
   *
   * The display of line endings.
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
   * The LineMiterLimit property
   *
   * Used to automatically convert miter joins to bevel joins for sharp angles.
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
   * Used to automatically convert round joins to miter joins for shallow angles.
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
   * The LineDasharray property
   *
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to density-independent pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. The unit of lineDasharray is in line widths.
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
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
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
   * Controls the intensity of light emitted on the source features. The unit of lineEmissiveStrength is in intensity.
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
   * The LineOcclusionOpacity property
   *
   * Opacity multiplier (multiplies line-opacity value) of the line part that is occluded by 3D objects. Value 0 hides occluded part, value 1 means the same opacity as non-occluded part. The property is not supported when {@link PropertyFactory#lineOpacity} has data-driven styling.
   */
  @MapboxExperimental
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
   * The LineTranslate property
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of lineTranslate is in density-independent pixels.
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
   * Controls the frame of reference for {@link PropertyFactory#lineTranslate}.
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
   * The color to be used for rendering the trimmed line section that is defined by the {@link PropertyFactory#lineTrimOffset} property.
   */
  @MapboxExperimental
  var lineTrimColor: String?
    /**
     * Get the LineTrimColor property
     *
     * @return property wrapper value around String
     */
    get(): String? {
      return layer.lineTrimColor
    }
    /**
     * Set the LineTrimColor property
     * @param value property wrapper value around String
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
   * The fade range for the trim-start and trim-end points is defined by the {@link PropertyFactory#lineTrimOffset} property. The first element of the array represents the fade range from the trim-start point toward the end of the line, while the second element defines the fade range from the trim-end point toward the beginning of the line. The fade result is achieved by interpolating between {@link PropertyFactory#lineTrimColor} and the color specified by the {@link PropertyFactory#lineColor} or the {@link PropertyFactory#lineGradient} property.
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
   * The line part between [trim-start, trim-end] will be painted using `line-trim-color,` which is transparent by default to produce a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
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