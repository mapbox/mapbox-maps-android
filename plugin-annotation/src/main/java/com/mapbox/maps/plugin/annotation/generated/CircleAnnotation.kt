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
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

/**
 * Class for CircleAnnotation
 */
class CircleAnnotation(
  id: Long,
  /** The annotation manger that manipulate this annotation */
  private val annotationManager: AnnotationManager<Point, CircleAnnotation, *, *, *, *, *>,
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
    return AnnotationType.CircleAnnotation
  }

  /**
   * The Point of the circleAnnotation, which represents the location of the circleAnnotation on the map
   *
   * To update the circleAnnotation on the map use {@link CircleAnnotationManager#update(Annotation)}.
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
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var circleSortKey: Double?
    /**
     * Get the circleSortKey property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleSortKey property
     *
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY, it)
      }
    }

  /**
   * The circleBlur property
   *
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   */
  var circleBlur: Double?
    /**
     * Get the circleBlur property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleBlur property
     *
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR, it)
      }
    }

  /**
   * The circleColor property in Int
   * The fill color of the circle.
   */
  var circleColorInt: Int?
    /**
     * Get the circleColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the circleColor property
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The circleColor property in String
   *
   * The fill color of the circle.
   */
  var circleColorString: String?
    /**
     * Get the circleColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the circleColor property
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR, value)
    }

  /**
   * The circleOpacity property
   *
   * The opacity at which the circle will be drawn.
   */
  var circleOpacity: Double?
    /**
     * Get the circleOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleOpacity property
     *
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY, it)
      }
    }

  /**
   * The circleRadius property
   *
   * Circle radius.
   */
  var circleRadius: Double?
    /**
     * Get the circleRadius property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleRadius property
     *
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS, it)
      }
    }

  /**
   * The circleStrokeColor property in Int
   * The stroke color of the circle.
   */
  var circleStrokeColorInt: Int?
    /**
     * Get the circleStrokeColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the circleStrokeColor property
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The circleStrokeColor property in String
   *
   * The stroke color of the circle.
   */
  var circleStrokeColorString: String?
    /**
     * Get the circleStrokeColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the circleStrokeColor property
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR, value)
    }

  /**
   * The circleStrokeOpacity property
   *
   * The opacity of the circle's stroke.
   */
  var circleStrokeOpacity: Double?
    /**
     * Get the circleStrokeOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleStrokeOpacity property
     *
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY, it)
      }
    }

  /**
   * The circleStrokeWidth property
   *
   * The width of the circle's stroke. Strokes are placed outside of the {@link PropertyFactory#circleRadius}.
   */
  var circleStrokeWidth: Double?
    /**
     * Get the circleStrokeWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the circleStrokeWidth property
     *
     * To update the circleAnnotation on the map use {@link circleAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH, it)
      }
    }

  /**
   * Get the offset geometry for the touch point
   */
  override fun getOffsetGeometry(
    mapCameraManagerDelegate: MapCameraManagerDelegate,
    moveDistancesObject: MoveDistancesObject,
    touchAreaShiftX: Int,
    touchAreaShiftY: Int
  ): Point? {
    val point = mapCameraManagerDelegate.coordinateForPixel(
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
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)
    }
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)
    }
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)
    }
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)
    }
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)
    }
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)
    }
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)
    }
    jsonObject.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)?.let {
      annotationManager.enableDataDrivenProperty(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** the Id key for annotation */
    const val ID_KEY: String = "CircleAnnotation"
  }
}