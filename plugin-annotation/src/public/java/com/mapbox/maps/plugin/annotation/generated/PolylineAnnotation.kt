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
import com.mapbox.maps.plugin.annotation.ConvertUtils
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import java.util.Locale

/**
 * Class for PolylineAnnotation
 */
class PolylineAnnotation(
  id: Long,
  /** The annotation manager that manipulate this annotation */
  private val annotationManager: AnnotationManager<LineString, PolylineAnnotation, *, *, *, *, *>,
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
    return AnnotationType.PolylineAnnotation
  }

  /**
   * A list of Point for the line, which represents the locations of the line on the map
   *
   * To update the polylineAnnotation on the map use {@link PolylineAnnotationManager#update(Annotation)}.
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
   *
   * The display of lines when joining.
   */
  var lineJoin: LineJoin?
    /**
     * Get the lineJoin property
     *
     * @return property wrapper value around LineJoin
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)
      value?.let {
        return LineJoin.valueOf(it.asString.toUpperCase(Locale.US))
      }
      return null
    }
    /**
     * Set the lineJoin property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for LineJoin
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_JOIN, value.value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)
      }
    }

  /**
   * The lineSortKey property
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var lineSortKey: Double?
    /**
     * Get the lineSortKey property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineSortKey property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)
      }
    }

  /**
   * The lineBlur property
   *
   * Blur applied to the line, in density-independent pixels. The unit of lineBlur is in pixels.
   */
  var lineBlur: Double?
    /**
     * Get the lineBlur property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineBlur property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_BLUR, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)
      }
    }

  /**
   * The lineColor property in Int
   * The color with which the line will be drawn.
   */
  var lineColorInt: Int?
    /**
     * Get the lineColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the lineColor property
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        jsonObject.addProperty(
          PolylineAnnotationOptions.PROPERTY_LINE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      }
    }

  /**
   * The lineColor property in String
   *
   * The color with which the line will be drawn.
   */
  var lineColorString: String?
    /**
     * Get the lineColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the lineColor property
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_COLOR, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
      }
    }

  /**
   * The lineGapWidth property
   *
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. The unit of lineGapWidth is in density-independent pixels.
   */
  var lineGapWidth: Double?
    /**
     * Get the lineGapWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineGapWidth property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)
      }
    }

  /**
   * The lineOffset property
   *
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. The unit of lineOffset is in density-independent pixels.
   */
  var lineOffset: Double?
    /**
     * Get the lineOffset property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineOffset property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)
      }
    }

  /**
   * The lineOpacity property
   *
   * The opacity at which the line will be drawn.
   */
  var lineOpacity: Double?
    /**
     * Get the lineOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineOpacity property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)
      }
    }

  /**
   * The linePattern property
   *
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  var linePattern: String?
    /**
     * Get the linePattern property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the linePattern property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)
      }
    }

  /**
   * The lineWidth property
   *
   * Stroke thickness. The unit of lineWidth is in density-independent pixels.
   */
  var lineWidth: Double?
    /**
     * Get the lineWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the lineWidth property
     *
     * To update the polylineAnnotation on the map use {@link polylineAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH, value)
      } else {
        jsonObject.remove(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)
      }
    }

  /**
   * Get the offset geometry for the touch point
   */
  override fun getOffsetGeometry(
    mapCameraManagerDelegate: MapCameraManagerDelegate,
    moveDistancesObject: MoveDistancesObject
  ): LineString? {
    val points = geometry.coordinates()
    if (points.isEmpty()) return null

    val centerPoint = Point.fromLngLat(points.map { it.longitude() }.average(), points.map { it.latitude() }.average())
    val centerScreenCoordinate = mapCameraManagerDelegate.pixelForCoordinate(centerPoint)
    val targetPoint = mapCameraManagerDelegate.coordinateForPixel(
      ScreenCoordinate(
        centerScreenCoordinate.x - moveDistancesObject.distanceXSinceLast,
        centerScreenCoordinate.y - moveDistancesObject.distanceYSinceLast
      )
    )

    val shiftMercatorCoordinate = ConvertUtils.calculateMercatorCoordinateShift(centerPoint, targetPoint, mapCameraManagerDelegate.cameraState.zoom)
    val targetPoints =
      points.map { ConvertUtils.shiftPointWithMercatorCoordinate(it, shiftMercatorCoordinate, mapCameraManagerDelegate.cameraState.zoom) }
    if (targetPoints.any { it.latitude() > MAX_MERCATOR_LATITUDE || it.latitude() < MIN_MERCATOR_LATITUDE }) {
      return null
    }
    return LineString.fromLngLats(targetPoints)
  }

  /**
   * Set the used data-driven properties
   */
  override fun setUsedDataDrivenProperties() {
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)
    }
    jsonObject.get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)?.let {
      annotationManager.enableDataDrivenProperty(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** the Id key for annotation */
    const val ID_KEY: String = "PolylineAnnotation"
  }
}