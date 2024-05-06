// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import com.mapbox.geojson.*
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
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

  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY -> {
        layer.fillSortKey(get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY))
        dragLayer.fillSortKey(get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY))
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
    }
  }

  /**
   * Create a list of polygonAnnotations on the map.
   *
   * PolygonAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_OPACITY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_PATTERN - String
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
   * PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_OPACITY - Double
   * PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR - String
   * PolygonAnnotationOptions.PROPERTY_FILL_PATTERN - String
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
   * The FillAntialias property
   *
   * Whether or not the fill should be antialiased.
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
   * The FillEmissiveStrength property
   *
   * Controls the intensity of light emitted on the source features. The unit of fillEmissiveStrength is in intensity.
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
   * The FillTranslate property
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of fillTranslate is in density-independent pixels.
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
   * Controls the frame of reference for {@link PropertyFactory#fillTranslate}.
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
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_OPACITY] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_PATTERN] = false
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