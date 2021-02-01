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
 * Class for Fill
 */
class Fill(
  id: Long,
  /** The annotation manger that manipulate this annotation */
  val annotationManager: AnnotationManager<Polygon, Fill, *, *, *, *>,
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
    return AnnotationType.Fill
  }

  /**
   * A list of lists of Point for the fill, which represents the locations of the fill on the map
   * <p>
   * To update the fill on the map use {@link FillManager#update(Annotation)}.
   * <p>
   *
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
   * <p>
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   * </p>
   */
  var fillSortKey: Double?
    /**
     * Get the fillSortKey property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(FillOptions.PROPERTY_FILL_SORT_KEY)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillSortKey property
     * <p>
     * To update the fill on the map use {@link fillManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(FillOptions.PROPERTY_FILL_SORT_KEY, it)
      }
    }

  /**
   * The fillColor property in Int
   * <p>
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   * </p>
   */
  var fillColorInt: Int?
    /**
     * Get the fillColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(FillOptions.PROPERTY_FILL_COLOR)
      if (!value.isJsonNull) {
        ColorUtils.rgbaToColor(value.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillColor property
     * <p>
     * To update the fill on the map use {@link fillManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          FillOptions.PROPERTY_FILL_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The fillColor property in String
   * <p>
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   * </p>
   */
  var fillColorString: String?
    /**
     * Get the fillColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(FillOptions.PROPERTY_FILL_COLOR)
      if (!value.isJsonNull) {
        return value.asString.toString()
      }
      return null
    }
    /**
     * Set the fillColor property
     * <p>
     * To update the fill on the map use {@link fillManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty("fill-color", value)
    }

  /**
   * The fillOpacity property
   * <p>
   * The opacity of the entire fill layer. In contrast to the {@link PropertyFactory#fillColor}, this value will also affect the 1px stroke around the fill, if the stroke is used.
   * </p>
   */
  var fillOpacity: Double?
    /**
     * Get the fillOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(FillOptions.PROPERTY_FILL_OPACITY)
      if (!value.isJsonNull) {
        return value.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillOpacity property
     * <p>
     * To update the fill on the map use {@link fillManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(FillOptions.PROPERTY_FILL_OPACITY, it)
      }
    }

  /**
   * The fillOutlineColor property in Int
   * <p>
   * The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified.
   * </p>
   */
  var fillOutlineColorInt: Int?
    /**
     * Get the fillOutlineColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(FillOptions.PROPERTY_FILL_OUTLINE_COLOR)
      if (!value.isJsonNull) {
        ColorUtils.rgbaToColor(value.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillOutlineColor property
     * <p>
     * To update the fill on the map use {@link fillManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          FillOptions.PROPERTY_FILL_OUTLINE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The fillOutlineColor property in String
   * <p>
   * The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified.
   * </p>
   */
  var fillOutlineColorString: String?
    /**
     * Get the fillOutlineColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(FillOptions.PROPERTY_FILL_OUTLINE_COLOR)
      if (!value.isJsonNull) {
        return value.asString.toString()
      }
      return null
    }
    /**
     * Set the fillOutlineColor property
     * <p>
     * To update the fill on the map use {@link fillManager#update(Annotation)}.
     * <p>
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty("fill-outline-color", value)
    }

  /**
   * The fillPattern property
   * <p>
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   * </p>
   */
  var fillPattern: String?
    /**
     * Get the fillPattern property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(FillOptions.PROPERTY_FILL_PATTERN)
      if (!value.isJsonNull) {
        return value.asString.toString()
      }
      return null
    }
    /**
     * Set the fillPattern property
     * <p>
     * To update the fill on the map use {@link fillManager#update(Annotation)}.
     * <p>
     *
     * @param value constant property value for String
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(FillOptions.PROPERTY_FILL_PATTERN, it)
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
  ): Polygon? {
    val points = geometry.coordinates()
      .map {
        it.map {
          projectionDelegate.pixelForCoordinate(it)
        }.map {
          projectionDelegate.coordinateForPixel(
            ScreenCoordinate(
              it.x - moveDistancesObject.distanceXSinceLast,
              it.y - moveDistancesObject.distanceYSinceLast
            )
          )
        }
      }

    if (points.any { it.any { it.latitude() > MAX_MERCATOR_LATITUDE || it.latitude() < MIN_MERCATOR_LATITUDE } }) {
      return null
    }
    return Polygon.fromLngLats(points)
  }

  /**
   * Set the used data-driven properties
   */
  override fun setUsedDataDrivenProperties() {
    if (!(jsonObject.get(FillOptions.PROPERTY_FILL_SORT_KEY).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(FillOptions.PROPERTY_FILL_SORT_KEY)
    }
    if (!(jsonObject.get(FillOptions.PROPERTY_FILL_COLOR).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(FillOptions.PROPERTY_FILL_COLOR)
    }
    if (!(jsonObject.get(FillOptions.PROPERTY_FILL_OPACITY).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(FillOptions.PROPERTY_FILL_OPACITY)
    }
    if (!(jsonObject.get(FillOptions.PROPERTY_FILL_OUTLINE_COLOR).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(FillOptions.PROPERTY_FILL_OUTLINE_COLOR)
    }
    if (!(jsonObject.get(FillOptions.PROPERTY_FILL_PATTERN).isJsonNull)) {
      annotationManager.enableDataDrivenProperty(FillOptions.PROPERTY_FILL_PATTERN)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** the Id key for annotation */
    const val ID_KEY: String = "Fill"
  }
}