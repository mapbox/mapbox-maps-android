// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.view.View
import com.mapbox.geojson.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.concurrent.atomic.AtomicLong

/**
 * The fill manager allows to add fills to a map.
 */
class FillManager(
  mapView: View,
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<Polygon, Fill, FillOptions, OnFillDragListener, OnFillClickListener, OnFillLongClickListener, FillLayer>(
    mapView, delegateProvider, annotationConfig
  ) {
  private val id = ID_GENERATOR.incrementAndGet()
  override val layerId = annotationConfig?.layerId ?: "mapbox-android-fill-layer-$id"
  override val sourceId = annotationConfig?.sourceId ?: "mapbox-android-fill-source-$id"

  init {
    delegateProvider.getStyle {
      style = it
      initLayerAndSource()
    }
  }

  override fun initializeDataDrivenPropertyMap() {
    dataDrivenPropertyUsageMap[FillOptions.PROPERTY_FILL_SORT_KEY] = false
    dataDrivenPropertyUsageMap[FillOptions.PROPERTY_FILL_COLOR] = false
    dataDrivenPropertyUsageMap[FillOptions.PROPERTY_FILL_OPACITY] = false
    dataDrivenPropertyUsageMap[FillOptions.PROPERTY_FILL_OUTLINE_COLOR] = false
    dataDrivenPropertyUsageMap[FillOptions.PROPERTY_FILL_PATTERN] = false
  }

  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      FillOptions.PROPERTY_FILL_SORT_KEY -> layer?.fillSortKey(get(FillOptions.PROPERTY_FILL_SORT_KEY))
      FillOptions.PROPERTY_FILL_COLOR -> layer?.fillColor(get(FillOptions.PROPERTY_FILL_COLOR))
      FillOptions.PROPERTY_FILL_OPACITY -> layer?.fillOpacity(get(FillOptions.PROPERTY_FILL_OPACITY))
      FillOptions.PROPERTY_FILL_OUTLINE_COLOR -> layer?.fillOutlineColor(get(FillOptions.PROPERTY_FILL_OUTLINE_COLOR))
      FillOptions.PROPERTY_FILL_PATTERN -> layer?.fillPattern(get(FillOptions.PROPERTY_FILL_PATTERN))
    }
  }

  /**
   * Create a list of fills on the map.
   * <p>
   * Fills are going to be created only for features with a matching geometry.
   * <p>
   * All supported properties are:<br>
   * FillOptions.PROPERTY_FILL_SORT_KEY - Double<br>
   * FillOptions.PROPERTY_FILL_COLOR - String<br>
   * FillOptions.PROPERTY_FILL_OPACITY - Double<br>
   * FillOptions.PROPERTY_FILL_OUTLINE_COLOR - String<br>
   * FillOptions.PROPERTY_FILL_PATTERN - String<br>
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   * <p>
   * Out of spec properties:<br>
   * "is-draggable" - Boolean, true if the fill should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of fills to build
   * @return the list of built fills
   */
  fun create(json: String): List<Fill> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of fills on the map.
   * <p>
   * Fills are going to be created only for features with a matching geometry.
   * <p>
   * All supported properties are:<br>
   * FillOptions.PROPERTY_FILL_SORT_KEY - Double<br>
   * FillOptions.PROPERTY_FILL_COLOR - String<br>
   * FillOptions.PROPERTY_FILL_OPACITY - Double<br>
   * FillOptions.PROPERTY_FILL_OUTLINE_COLOR - String<br>
   * FillOptions.PROPERTY_FILL_PATTERN - String<br>
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   * <p>
   * Out of spec properties:<br>
   * "is-draggable" - Boolean, true if the fill should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of fills to build
   * @return the list of built fills
   */
  fun create(featureCollection: FeatureCollection): List<Fill> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        FillOptions.fromFeature(it)
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
    return Fill.ID_KEY
  }

  // Property accessors
  /**
   * The FillAntialias property
   * <p>
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
      value?.let {
        layer?.fillAntialias(it)
      }
    }

  /**
   * The FillTranslate property
   * <p>
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
      value?.let {
        layer?.fillTranslate(it)
      }
    }

  /**
   * The FillTranslateAnchor property
   * <p>
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
      value?.let {
        layer?.fillTranslateAnchor(it)
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

  /**
   * The filter on the managed fills.
   *
   * @param expression expression
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed fills.
     *
     * @return expression
     */
    get() = layer?.filter
    /**
     * Set filter on the managed fills.
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
 * Extension function to create a FillManager instance.
 */
fun AnnotationPlugin.createFillManager(
  mapView: View,
  annotationConfig: AnnotationConfig? = null
): FillManager {
  return createAnnotationManager(mapView, AnnotationType.Fill, annotationConfig) as FillManager
}