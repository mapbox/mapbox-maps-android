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
 * Class for Line
 */
class Line(
  id: Long,
  /** The annotation manger that manipulate this annotation */
  val annotationManager: AnnotationManager<LineString, Line, *, *, *, *>,
  jsonObject: JsonObject,
  geometry: LineString
) : Annotation<LineString>(id, jsonObject, geometry) {

  init {
    jsonObject.addProperty(ID_KEY, id)
  }

  /**
   * Get the type of this annotation
   */
  override fun getType(): AnnotationType {
    return AnnotationType.Line
  }

  /**
   * A list of Point for the line, which represents the locations of the line on the map
   * <p>
   * To update the line on the map use {@link LineManager#update(Annotation)}.
   * <p>
   *
   */
  var points: List<Point>
    get() {
      return geometry.coordinates()
    }
    set(value) {
      geometry = LineString.fromLngLats(value)
    }

  // Property accessors
  /**
   * The lineJoin property
   * <p>
   * The display of lines when joining.
   * </p>
   */
  var lineJoin: LineJoin?
    /**
     * Get the lineJoin property
     *
     * @return property wrapper value around LineJoin
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_JOIN)
      if (!value.isJsonNull) {
        return LineJoin.valueOf(value.asString)
      }
      return null
    }
    /**
     * Set the lineJoin property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for LineJoin
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_JOIN, it.value)
      }
    }

  /**
   * The lineSortKey property
   * <p>
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   * </p>
   */
  var lineSortKey: Double?
    /**
     * Get the lineSortKey property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_SORT_KEY)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineSortKey property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_SORT_KEY, it)
      }
    }

  /**
   * The lineBlur property
   * <p>
   * Blur applied to the line, in density-independent pixels.
   * </p>
   */
  var lineBlur: Double?
    /**
     * Get the lineBlur property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_BLUR)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineBlur property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_BLUR, it)
      }
    }

  /**
   * The lineColor property in Int
   * <p>
   * The color with which the line will be drawn.
   * </p>
   */
  var lineColorInt: Int?
    /**
     * Get the lineColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_COLOR)
      if (!value.isJsonNull) {
        ColorUtils.rgbaToColor(value.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the lineColor property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          LineOptions.PROPERTY_LINE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The lineColor property in String
   * <p>
   * The color with which the line will be drawn.
   * </p>
   */
  var lineColorString: String?
    /**
     * Get the lineColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_COLOR)
      if (!value.isJsonNull) {
        return value.asString.toString()
      }
      return null
    }
    /**
     * Set the lineColor property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty("line-color", value)
    }

  /**
   * The lineGapWidth property
   * <p>
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   * </p>
   */
  var lineGapWidth: Double?
    /**
     * Get the lineGapWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_GAP_WIDTH)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineGapWidth property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_GAP_WIDTH, it)
      }
    }

  /**
   * The lineOffset property
   * <p>
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   * </p>
   */
  var lineOffset: Double?
    /**
     * Get the lineOffset property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_OFFSET)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineOffset property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_OFFSET, it)
      }
    }

  /**
   * The lineOpacity property
   * <p>
   * The opacity at which the line will be drawn.
   * </p>
   */
  var lineOpacity: Double?
    /**
     * Get the lineOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_OPACITY)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineOpacity property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_OPACITY, it)
      }
    }

  /**
   * The linePattern property
   * <p>
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   * </p>
   */
  var linePattern: String?
    /**
     * Get the linePattern property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_PATTERN)
      if (!value.isJsonNull) {
        return value.asString.toString()
      }
      return null
    }
    /**
     * Set the linePattern property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for String
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_PATTERN, it)
      }
    }

  /**
   * The lineWidth property
   * <p>
   * Stroke thickness.
   * </p>
   */
  var lineWidth: Double?
    /**
     * Get the lineWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(LineOptions.PROPERTY_LINE_WIDTH)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineWidth property
     * <p>
     * To update the line on the map use {@link lineManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(LineOptions.PROPERTY_LINE_WIDTH, it)
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
  ): LineString? {
    val points = geometry.coordinates()
      .map {
        projectionDelegate.pixelForCoordinate(it)
      }.map {
        projectionDelegate.coordinateForPixel(
          ScreenCoordinate(
            it.x - moveDistancesObject.distanceXSinceLast,
            it.y - moveDistancesObject.distanceYSinceLast
          )
        )
      }
    if (points.any { it.latitude() > MAX_MERCATOR_LATITUDE || it.latitude() < MIN_MERCATOR_LATITUDE }) {
      return null
    }
    return LineString.fromLngLats(points)
  }

  /**
   * Set the used data-driven properties
   */
  override fun setUsedDataDrivenProperties() {
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_JOIN).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_JOIN)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_SORT_KEY).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_SORT_KEY)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_BLUR).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_BLUR)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_COLOR).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_COLOR)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_GAP_WIDTH).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_GAP_WIDTH)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_OFFSET).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_OFFSET)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_OPACITY).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_OPACITY)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_PATTERN).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_PATTERN)
    }
    if (!(jsonObject.get(LineOptions.PROPERTY_LINE_WIDTH).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(LineOptions.PROPERTY_LINE_WIDTH)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** the Id key for annotation */
    const val ID_KEY: String = "Line"
  }
}