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

/**
 * Class for PolygonAnnotation
 */
class PolygonAnnotation(
  id: String,
  /** The annotation manager that manipulate this annotation */
  private val annotationManager: AnnotationManager<Polygon, PolygonAnnotation, *, *, *, *, *>,
  jsonObject: JsonObject,
  geometry: Polygon
) : Annotation<Polygon>(id, jsonObject, geometry) {

  init {
    jsonObject.addProperty(ID_KEY, id)
  }

  /**
   * Get the type of this annotation
   */
  override fun getType(): AnnotationType {
    return AnnotationType.PolygonAnnotation
  }

  /**
   * A list of lists of Point for the fill, which represents the locations of the fill on the map
   *
   * To update the polygonAnnotation on the map use {@link PolygonAnnotationManager#update(Annotation)}.
   */
  var points: List<List<Point>>
    get() {
      return geometry.coordinates()
    }
    set(value) {
      geometry = Polygon.fromLngLats(value)
    }

  // Property accessors
  /**
   * The fillSortKey property
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var fillSortKey: Double?
    /**
     * Get the fillSortKey property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillSortKey property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)
      }
    }

  /**
   * The fillColor property in Int
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   */
  var fillColorInt: Int?
    /**
     * Get the fillColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        jsonObject.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_COLOR, ColorUtils.colorToRgbaString(value)
        )
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      }
    }

  /**
   * The fillColor property in String
   *
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   */
  var fillColorString: String?
    /**
     * Get the fillColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
      }
    }

  /**
   * The fillOpacity property
   *
   * The opacity of the entire fill layer. In contrast to the {@link PropertyFactory#fillColor}, this value will also affect the 1px stroke around the fill, if the stroke is used.
   */
  var fillOpacity: Double?
    /**
     * Get the fillOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillOpacity property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)
      }
    }

  /**
   * The fillOutlineColor property in Int
   * The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified.
   */
  var fillOutlineColorInt: Int?
    /**
     * Get the fillOutlineColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillOutlineColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        jsonObject.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      }
    }

  /**
   * The fillOutlineColor property in String
   *
   * The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified.
   */
  var fillOutlineColorString: String?
    /**
     * Get the fillOutlineColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillOutlineColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
      }
    }

  /**
   * The fillPattern property
   *
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  var fillPattern: String?
    /**
     * Get the fillPattern property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillPattern property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)
      }
    }

  /**
   * Get the offset geometry for the touch point
   */
  override fun getOffsetGeometry(
    mapCameraManagerDelegate: MapCameraManagerDelegate,
    moveDistancesObject: MoveDistancesObject
  ): Polygon? {
    val points = geometry.outer()?.coordinates()
    if (points == null || points.isEmpty()) return null

    val centerPoint = Point.fromLngLat(points.map { it.longitude() }.average(), points.map { it.latitude() }.average())

    val centerScreenCoordinate = mapCameraManagerDelegate.pixelForCoordinate(centerPoint)
    val targetPoint = mapCameraManagerDelegate.coordinateForPixel(
      ScreenCoordinate(
        centerScreenCoordinate.x - moveDistancesObject.distanceXSinceLast,
        centerScreenCoordinate.y - moveDistancesObject.distanceYSinceLast
      )
    )

    val shiftMercatorCoordinate = ConvertUtils.calculateMercatorCoordinateShift(centerPoint, targetPoint, mapCameraManagerDelegate.cameraState.zoom)
    val targetPoints = geometry.coordinates().map { sublist ->
      sublist.map { ConvertUtils.shiftPointWithMercatorCoordinate(it, shiftMercatorCoordinate, mapCameraManagerDelegate.cameraState.zoom) }
    }
    if (targetPoints.any { subPointList -> subPointList.any { it.latitude() > MAX_MERCATOR_LATITUDE || it.latitude() < MIN_MERCATOR_LATITUDE } }) {
      return null
    }
    return Polygon.fromLngLats(targetPoints)
  }

  /**
   * Set the used data-driven properties
   */
  override fun setUsedDataDrivenProperties() {
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** the Id key for annotation */
    const val ID_KEY: String = "PolygonAnnotation"
  }
}