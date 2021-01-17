// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.google.gson.*
import com.mapbox.android.gestures.MoveDistancesObject
import com.mapbox.geojson.*
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.Annotation
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate

/**
 * Class for Circle
 */
class Circle(
  id: Long,
  /** The annotation manger that manipulate this annotation */
  val annotationManager: AnnotationManager<Point, Circle, *, *, *, *>,
  jsonObject: JsonObject,
  geometry: Point
) : Annotation<Point>(id, jsonObject, geometry) {

  init {
    jsonObject.addProperty(ID_KEY, id)
  }

  /**
   * Get the type of this annotation
   */
  override fun getType(): AnnotationType {
    return AnnotationType.Circle
  }

  /**
   * The Point of the circle, which represents the location of the circle on the map
   * <p>
   * To update the circle on the map use {@link CircleManager#update(Annotation)}.
   * <p>
   *
   */
  var point: Point
    get() {
      return geometry
    }
    set(value) {
      geometry = value
    }

  // Property accessors
  /**
   * The circleSortKey property
   * <p>
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   * </p>
   */
  var circleSortKey: Double?
    /**
     * Get the circleSortKey property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_SORT_KEY)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleSortKey property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleOptions.PROPERTY_CIRCLE_SORT_KEY, it)
      }
    }

  /**
   * The circleRadius property
   * <p>
   * Circle radius.
   * </p>
   */
  var circleRadius: Double?
    /**
     * Get the circleRadius property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_RADIUS)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleRadius property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleOptions.PROPERTY_CIRCLE_RADIUS, it)
      }
    }

  /**
   * The circleColor property in Int
   * <p>
   * The fill color of the circle.
   * </p>
   */
  var circleColorInt: Int?
    /**
     * Get the circleColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_COLOR)
      if (!value.isJsonNull) {
        ColorUtils.rgbaToColor(value.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the circleColor property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          CircleOptions.PROPERTY_CIRCLE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The circleColor property in String
   * <p>
   * The fill color of the circle.
   * </p>
   */
  var circleColorString: String?
    /**
     * Get the circleColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_COLOR)
      if (!value.isJsonNull) {
        return value.asString.toString()
      }
      return null
    }
    /**
     * Set the circleColor property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty("circle-color", value)
    }

  /**
   * The circleBlur property
   * <p>
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   * </p>
   */
  var circleBlur: Double?
    /**
     * Get the circleBlur property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_BLUR)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleBlur property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleOptions.PROPERTY_CIRCLE_BLUR, it)
      }
    }

  /**
   * The circleOpacity property
   * <p>
   * The opacity at which the circle will be drawn.
   * </p>
   */
  var circleOpacity: Double?
    /**
     * Get the circleOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_OPACITY)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleOpacity property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleOptions.PROPERTY_CIRCLE_OPACITY, it)
      }
    }

  /**
   * The circleStrokeWidth property
   * <p>
   * The width of the circle's stroke. Strokes are placed outside of the {@link PropertyFactory#circleRadius}.
   * </p>
   */
  var circleStrokeWidth: Double?
    /**
     * Get the circleStrokeWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleStrokeWidth property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH, it)
      }
    }

  /**
   * The circleStrokeColor property in Int
   * <p>
   * The stroke color of the circle.
   * </p>
   */
  var circleStrokeColorInt: Int?
    /**
     * Get the circleStrokeColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      if (!value.isJsonNull) {
        ColorUtils.rgbaToColor(value.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the circleStrokeColor property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The circleStrokeColor property in String
   * <p>
   * The stroke color of the circle.
   * </p>
   */
  var circleStrokeColorString: String?
    /**
     * Get the circleStrokeColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      if (!value.isJsonNull) {
        return value.asString.toString()
      }
      return null
    }
    /**
     * Set the circleStrokeColor property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty("circle-stroke-color", value)
    }

  /**
   * The circleStrokeOpacity property
   * <p>
   * The opacity of the circle's stroke.
   * </p>
   */
  var circleStrokeOpacity: Double?
    /**
     * Get the circleStrokeOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleStrokeOpacity property
     * <p>
     * To update the circle on the map use {@link circleManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY, it)
      }
    }

  /**
   * Get the offset geometry for the touch point
   */
  override fun getOffsetGeometry(
    projectionDelegate: MapProjectionDelegate,
    moveDistancesObject: MoveDistancesObject,
    touchAreaShiftX: Int,
    touchAreaShiftY: Int
  ): Point? {
    val point = projectionDelegate.coordinateForPixel(
      ScreenCoordinate(
        (moveDistancesObject.currentX - touchAreaShiftX).toDouble(),
        (moveDistancesObject.currentY - touchAreaShiftY).toDouble(),
      )
    )
    if (point.latitude() > MAX_MERCATOR_LATITUDE || point.latitude() < MIN_MERCATOR_LATITUDE) {
      return null
    }
    return point
  }

  /**
   * Set the used data-driven properties
   */
  override fun setUsedDataDrivenProperties() {
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_SORT_KEY).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_SORT_KEY)
    }
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_RADIUS).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_RADIUS)
    }
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_COLOR).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_COLOR)
    }
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_BLUR).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_BLUR)
    }
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_OPACITY).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_OPACITY)
    }
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH)
    }
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)
    }
    if (!(jsonObject.get(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** the Id key for annotation */
    const val ID_KEY: String = "Circle"
  }
}