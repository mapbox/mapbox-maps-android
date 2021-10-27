// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.view.View
import com.mapbox.geojson.*
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * The polygonAnnotation manager allows to add polygonAnnotations to a map.
 */
class PolygonAnnotationManager(
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<Polygon, PolygonAnnotation, PolygonAnnotationOptions, OnPolygonAnnotationDragListener, OnPolygonAnnotationClickListener, OnPolygonAnnotationLongClickListener, OnPolygonAnnotationInteractionListener, FillLayer>(
    delegateProvider, annotationConfig
  ) {
  private val id = ID_GENERATOR.incrementAndGet()
  override val layerId = annotationConfig?.layerId ?: "mapbox-android-polygonAnnotation-layer-$id"
  override val sourceId = annotationConfig?.sourceId ?: "mapbox-android-polygonAnnotation-source-$id"
  override val dragLayerId = "mapbox-android-polygonAnnotation-draglayer-$id"
  override val dragSourceId = "mapbox-android-polygonAnnotation-dragsource-$id"
  init {
    delegateProvider.getStyle {
      initLayerAndSource(it)
    }
  }

  override fun initializeDataDrivenPropertyMap() {
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_OPACITY] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR] = false
    dataDrivenPropertyUsageMap[PolygonAnnotationOptions.PROPERTY_FILL_PATTERN] = false
  }

  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY -> {
        layer?.fillSortKey(get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY))
        dragLayer?.fillSortKey(get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_COLOR -> {
        layer?.fillColor(get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR))
        dragLayer?.fillColor(get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_OPACITY -> {
        layer?.fillOpacity(get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY))
        dragLayer?.fillOpacity(get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR -> {
        layer?.fillOutlineColor(get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR))
        dragLayer?.fillOutlineColor(get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR))
      }
      PolygonAnnotationOptions.PROPERTY_FILL_PATTERN -> {
        layer?.fillPattern(get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN))
        dragLayer?.fillPattern(get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN))
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
      return layer?.fillAntialias
    }
    /**
     * Set the FillAntialias property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val newValue = value ?: StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias").silentUnwrap()
      newValue?.let {
        layer?.fillAntialias(it)
        dragLayer?.fillAntialias(it)
      }
    }

  /**
   * The FillTranslate property
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  var fillTranslate: List<Double>?
    /**
     * Get the FillTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer?.fillTranslate
    }
    /**
     * Set the FillTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val newValue = value ?: StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate").silentUnwrap()
      newValue?.let {
        layer?.fillTranslate(it)
        dragLayer?.fillTranslate(it)
      }
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
      return layer?.fillTranslateAnchor
    }
    /**
     * Set the FillTranslateAnchor property
     * @param value property wrapper value around FillTranslateAnchor
     */
    set(value) {
      val newValue = value ?: FillTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
      newValue?.let {
        layer?.fillTranslateAnchor(it)
        dragLayer?.fillTranslateAnchor(it)
      }
    }

  /**
   * Create the layer for managed annotations
   *
   * @return the layer created
   */
  override fun createLayer(): FillLayer {
    return fillLayer(layerId, sourceId) {}
  }
  override fun createDragLayer(): FillLayer {
    return fillLayer(dragLayerId, dragSourceId) {}
  }
  /**
   * The filter on the managed polygonAnnotations.
   *
   * @param expression expression
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed polygonAnnotations.
     *
     * @return expression
     */
    get() = layer?.filter
    /**
     * Set filter on the managed polygonAnnotations.
     *
     * @param expression expression
     */
    set(value) {
      value?.let {
        layer?.filter(it)
        dragLayer?.filter(it)
      }
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
@Deprecated(
  "mapView parameter is not needed",
  ReplaceWith("createPolygonAnnotationManager(annotationConfig)")
)
@JvmOverloads
fun AnnotationPlugin.createPolygonAnnotationManager(
  mapView: View,
  annotationConfig: AnnotationConfig? = null
): PolygonAnnotationManager = createPolygonAnnotationManager(annotationConfig)

/**
 * Extension function to create a PolygonAnnotationManager instance.
 */
fun AnnotationPlugin.createPolygonAnnotationManager(
  annotationConfig: AnnotationConfig? = null
): PolygonAnnotationManager {
  return createAnnotationManager(AnnotationType.PolygonAnnotation, annotationConfig) as PolygonAnnotationManager
}