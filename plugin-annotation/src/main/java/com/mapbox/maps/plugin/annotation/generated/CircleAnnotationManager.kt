// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.mapbox.geojson.*
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.annotation.ClusterAnnotationManager
import com.mapbox.maps.plugin.annotation.OnClusterClickListener
import com.mapbox.maps.plugin.annotation.OnClusterLongClickListener
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.concurrent.atomic.AtomicLong

/**
 * The circleAnnotation manager allows to add circleAnnotations to a map.
 */
class CircleAnnotationManager(
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<Point, CircleAnnotation, CircleAnnotationOptions, OnCircleAnnotationDragListener, OnCircleAnnotationClickListener, OnCircleAnnotationLongClickListener, OnCircleAnnotationInteractionListener, CircleLayer>(
    delegateProvider, annotationConfig, ID_GENERATOR.incrementAndGet(), "circleAnnotation", ::CircleLayer
  ),
  ClusterAnnotationManager {

  /**
   * The Added clusterClickListeners
   */
  override val clusterClickListeners: MutableList<OnClusterClickListener> = mutableListOf()

  /**
   * The Added clusterLongClickListeners
   */
  override val clusterLongClickListeners: MutableList<OnClusterLongClickListener> = mutableListOf()

  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY -> {
        layer.circleSortKey(get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY))
        dragLayer.circleSortKey(get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY))
      }
      CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR -> {
        layer.circleBlur(get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR))
        dragLayer.circleBlur(get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR))
      }
      CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR -> {
        layer.circleColor(get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR))
        dragLayer.circleColor(get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR))
      }
      CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY -> {
        layer.circleOpacity(get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY))
        dragLayer.circleOpacity(get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY))
      }
      CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS -> {
        layer.circleRadius(get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS))
        dragLayer.circleRadius(get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS))
      }
      CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR -> {
        layer.circleStrokeColor(get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR))
        dragLayer.circleStrokeColor(get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR))
      }
      CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY -> {
        layer.circleStrokeOpacity(get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY))
        dragLayer.circleStrokeOpacity(get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY))
      }
      CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH -> {
        layer.circleStrokeWidth(get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH))
        dragLayer.circleStrokeWidth(get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH))
      }
    }
  }

  /**
   * Create a list of circleAnnotations on the map.
   *
   * CircleAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR - String
   * CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR - String
   * CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the circleAnnotation should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of circleAnnotations to build
   * @return the list of built circleAnnotations
   */
  fun create(json: String): List<CircleAnnotation> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of circleAnnotations on the map.
   *
   * CircleAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR - String
   * CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR - String
   * CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY - Double
   * CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the circleAnnotation should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of circleAnnotations to build
   * @return the list of built circleAnnotations
   */
  fun create(featureCollection: FeatureCollection): List<CircleAnnotation> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        CircleAnnotationOptions.fromFeature(it)
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
    return CircleAnnotation.ID_KEY
  }

  // Property accessors
  /**
   * The default circleSortKey for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var circleSortKey: Double?
    /**
     * Get the circleSortKey property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleSortKey property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default circleBlur for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Setting a negative value renders the blur as an inner glow effect. Default value: 0.
   */
  var circleBlur: Double?
    /**
     * Get the circleBlur property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleBlur property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default circleColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The fill color of the circle. Default value: "#000000".
   */
  var circleColorInt: Int?
    /**
     * Get the circleColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the circleColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default circleColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The fill color of the circle. Default value: "#000000".
   */
  var circleColorString: String?
    /**
     * Get the circleColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the circleColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The CircleEmissiveStrength property
   *
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of circleEmissiveStrength is in intensity.
   */
  var circleEmissiveStrength: Double?
    /**
     * Get the CircleEmissiveStrength property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.circleEmissiveStrength
    }
    /**
     * Set the CircleEmissiveStrength property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-emissive-strength").value
      }
      setLayerProperty(wrappedValue, "circle-emissive-strength")
    }

  /**
   * The default circleOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   */
  var circleOpacity: Double?
    /**
     * Get the circleOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The CirclePitchAlignment property
   *
   * Orientation of circle when map is pitched. Default value: "viewport".
   */
  var circlePitchAlignment: CirclePitchAlignment?
    /**
     * Get the CirclePitchAlignment property
     *
     * @return property wrapper value around CirclePitchAlignment
     */
    get(): CirclePitchAlignment? {
      return layer.circlePitchAlignment
    }
    /**
     * Set the CirclePitchAlignment property
     * @param value property wrapper value around CirclePitchAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment").value
      }
      setLayerProperty(wrappedValue, "circle-pitch-alignment")
    }

  /**
   * The CirclePitchScale property
   *
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   */
  var circlePitchScale: CirclePitchScale?
    /**
     * Get the CirclePitchScale property
     *
     * @return property wrapper value around CirclePitchScale
     */
    get(): CirclePitchScale? {
      return layer.circlePitchScale
    }
    /**
     * Set the CirclePitchScale property
     * @param value property wrapper value around CirclePitchScale
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale").value
      }
      setLayerProperty(wrappedValue, "circle-pitch-scale")
    }

  /**
   * The default circleRadius for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Circle radius. Default value: 5. Minimum value: 0. The unit of circleRadius is in pixels.
   */
  var circleRadius: Double?
    /**
     * Get the circleRadius property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleRadius property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default circleStrokeColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The stroke color of the circle. Default value: "#000000".
   */
  var circleStrokeColorInt: Int?
    /**
     * Get the circleStrokeColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the circleStrokeColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default circleStrokeColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The stroke color of the circle. Default value: "#000000".
   */
  var circleStrokeColorString: String?
    /**
     * Get the circleStrokeColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the circleStrokeColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default circleStrokeOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   */
  var circleStrokeOpacity: Double?
    /**
     * Get the circleStrokeOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleStrokeOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default circleStrokeWidth for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0. The unit of circleStrokeWidth is in pixels.
   */
  var circleStrokeWidth: Double?
    /**
     * Get the circleStrokeWidth property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleStrokeWidth property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH, value)
        enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The CircleTranslate property
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of circleTranslate is in pixels.
   */
  var circleTranslate: List<Double>?
    /**
     * Get the CircleTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.circleTranslate
    }
    /**
     * Set the CircleTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate").value
      }
      setLayerProperty(wrappedValue, "circle-translate")
    }

  /**
   * The CircleTranslateAnchor property
   *
   * Controls the frame of reference for `circle-translate`. Default value: "map".
   */
  var circleTranslateAnchor: CircleTranslateAnchor?
    /**
     * Get the CircleTranslateAnchor property
     *
     * @return property wrapper value around CircleTranslateAnchor
     */
    get(): CircleTranslateAnchor? {
      return layer.circleTranslateAnchor
    }
    /**
     * Set the CircleTranslateAnchor property
     * @param value property wrapper value around CircleTranslateAnchor
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor").value
      }
      setLayerProperty(wrappedValue, "circle-translate-anchor")
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
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "slot").value
      }
      setLayerProperty(wrappedValue, "slot")
    }

  /**
   * The filter on the managed circleAnnotations.
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed circleAnnotations.
     *
     * @return expression
     */
    get() = layer.filter
    /**
     * Set filter on the managed circleAnnotations.
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
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY] = false
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR] = false
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR] = false
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY] = false
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS] = false
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR] = false
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY] = false
    dataDrivenPropertyUsageMap[CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH] = false
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
 * Extension function to create a CircleAnnotationManager instance.
 */
fun AnnotationPlugin.createCircleAnnotationManager(
  annotationConfig: AnnotationConfig? = null
): CircleAnnotationManager {
  return createAnnotationManager(AnnotationType.CircleAnnotation, annotationConfig) as CircleAnnotationManager
}