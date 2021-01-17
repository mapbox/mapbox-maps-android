// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import com.mapbox.geojson.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.concurrent.atomic.AtomicLong

/**
 * The circle manager allows to add circles to a map.
 */
class CircleManager(
  delegateProvider: MapDelegateProvider,
  belowLayerId: String?,
  touchAreaShiftX: Int,
  touchAreaShiftY: Int
) :
  AnnotationManagerImpl<Point, Circle, CircleOptions, OnCircleDragListener, OnCircleClickListener, OnCircleLongClickListener, CircleLayer>(
    delegateProvider,
    belowLayerId,
    touchAreaShiftX,
    touchAreaShiftY
  ) {
  private val layerId: String
  private val sourceId: String

  init {
    val id = ID_GENERATOR.incrementAndGet()
    layerId = "mapbox-android-circle-layer-$id"
    sourceId = "mapbox-android-circle-source-$id"
    delegateProvider.getStyle {
      style = it
      initLayerAndSource()
    }
  }

  override fun initializeDataDrivenPropertyMap() {
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_SORT_KEY] = false
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_RADIUS] = false
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_COLOR] = false
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_BLUR] = false
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_OPACITY] = false
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH] = false
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR] = false
    dataDrivenPropertyUsageMap[CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY] = false
  }

  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      CircleOptions.PROPERTY_CIRCLE_SORT_KEY -> layer.circleSortKey(get(CircleOptions.PROPERTY_CIRCLE_SORT_KEY))
      CircleOptions.PROPERTY_CIRCLE_RADIUS -> layer.circleRadius(get(CircleOptions.PROPERTY_CIRCLE_RADIUS))
      CircleOptions.PROPERTY_CIRCLE_COLOR -> layer.circleColor(get(CircleOptions.PROPERTY_CIRCLE_COLOR))
      CircleOptions.PROPERTY_CIRCLE_BLUR -> layer.circleBlur(get(CircleOptions.PROPERTY_CIRCLE_BLUR))
      CircleOptions.PROPERTY_CIRCLE_OPACITY -> layer.circleOpacity(get(CircleOptions.PROPERTY_CIRCLE_OPACITY))
      CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH -> layer.circleStrokeWidth(get(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH))
      CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR -> layer.circleStrokeColor(get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR))
      CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY -> layer.circleStrokeOpacity(get(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY))
    }
  }

  /**
   * Create a list of circles on the map.
   * <p>
   * Circles are going to be created only for features with a matching geometry.
   * <p>
   * All supported properties are:<br>
   * CircleOptions.PROPERTY_CIRCLE_SORT_KEY - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_RADIUS - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_COLOR - String<br>
   * CircleOptions.PROPERTY_CIRCLE_BLUR - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_OPACITY - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR - String<br>
   * CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY - Double<br>
   * Learn more about above properties in the <a href="https://www.mapbox.com/mapbox-gl-js/style-spec/">Style specification</a>.
   * <p>
   * Out of spec properties:<br>
   * "is-draggable" - Boolean, true if the circle should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of circles to build
   * @return the list of built circles
   */
  fun create(json: String): List<Circle> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of circles on the map.
   * <p>
   * Circles are going to be created only for features with a matching geometry.
   * <p>
   * All supported properties are:<br>
   * CircleOptions.PROPERTY_CIRCLE_SORT_KEY - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_RADIUS - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_COLOR - String<br>
   * CircleOptions.PROPERTY_CIRCLE_BLUR - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_OPACITY - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH - Double<br>
   * CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR - String<br>
   * CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY - Double<br>
   * Learn more about above properties in the <a href="https://www.mapbox.com/mapbox-gl-js/style-spec/">Style specification</a>.
   * <p>
   * Out of spec properties:<br>
   * "is-draggable" - Boolean, true if the circle should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of circles to build
   * @return the list of built circles
   */
  fun create(featureCollection: FeatureCollection): List<Circle> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        CircleOptions.fromFeature(it)
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
    return Circle.ID_KEY
  }

  // Property accessors
  /**
   * The CircleTranslate property
   * <p>
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
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
      value?.let {
        layer.circleTranslate(it)
      }
    }

  /**
   * The CircleTranslateAnchor property
   * <p>
   * Controls the frame of reference for {@link PropertyFactory#circleTranslate}.
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
      value?.let {
        layer.circleTranslateAnchor(it)
      }
    }

  /**
   * The CirclePitchScale property
   * <p>
   * Controls the scaling behavior of the circle when the map is pitched.
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
      value?.let {
        layer.circlePitchScale(it)
      }
    }

  /**
   * The CirclePitchAlignment property
   * <p>
   * Orientation of circle when map is pitched.
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
      value?.let {
        layer.circlePitchAlignment(it)
      }
    }

  /**
   * Create the source for managed annotations
   *
   * @return the GeoJsonSource created
   */
  override fun createSource(): GeoJsonSource {
    return geoJsonSource(sourceId) {}
  }

  /**
   * Create the layer for managed annotations
   *
   * @return the layer created
   */
  override fun createLayer(): CircleLayer {
    return CircleLayer(layerId, sourceId)
  }

  /**
   * The filter on the managed circles.
   *
   * @param expression expression
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed circles.
     *
     * @return expression
     */
    get() = layer.filter
    /**
     * Set filter on the managed circles.
     *
     * @param expression expression
     */
    set(value) {
      value?.let { layer.filter(it) }
    }

  /**
   * Static variables and methods.
   */
  companion object {
    /** The generator for id */
    var ID_GENERATOR = AtomicLong(0)

    /** The property for circle-translate */
    private val PROPERTY_CIRCLE_TRANSLATE = "circle-translate"

    /** The property for circle-translate-anchor */
    private val PROPERTY_CIRCLE_TRANSLATE_ANCHOR = "circle-translate-anchor"

    /** The property for circle-pitch-scale */
    private val PROPERTY_CIRCLE_PITCH_SCALE = "circle-pitch-scale"

    /** The property for circle-pitch-alignment */
    private val PROPERTY_CIRCLE_PITCH_ALIGNMENT = "circle-pitch-alignment"
  }
}

/**
 * Extension function to get CircleManager instance
 */
fun AnnotationPlugin.getCircleManager(
  belowLayerId: String? = null,
  touchAreaShiftX: Int = 0,
  touchAreaShiftY: Int = 0
): CircleManager {
  return getAnnotationManager(
    AnnotationType.Circle,
    belowLayerId,
    touchAreaShiftX,
    touchAreaShiftY
  ) as CircleManager
}