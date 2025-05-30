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
   * The fillConstructBridgeGuardRail property
   *
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   */
  var fillConstructBridgeGuardRail: Boolean?
    /**
     * Get the fillConstructBridgeGuardRail property
     *
     * @return property wrapper value around Boolean
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL)
      value?.let {
        return it.asString.toBoolean()
      }
      return null
    }
    /**
     * Set the fillConstructBridgeGuardRail property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Boolean
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL)
      }
    }

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
   * The fillBridgeGuardRailColor property in Int
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   */
  var fillBridgeGuardRailColorInt: Int?
    /**
     * Get the fillBridgeGuardRailColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillBridgeGuardRailColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        jsonObject.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR, ColorUtils.colorToRgbaString(value)
        )
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      }
    }

  /**
   * The fillBridgeGuardRailColor property in String
   *
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   */
  var fillBridgeGuardRailColorString: String?
    /**
     * Get the fillBridgeGuardRailColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillBridgeGuardRailColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
      }
    }

  /**
   * The fillColor property in Int
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
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
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
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
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
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
   * The fillTunnelStructureColor property in Int
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   */
  var fillTunnelStructureColorInt: Int?
    /**
     * Get the fillTunnelStructureColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the fillTunnelStructureColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        jsonObject.addProperty(
          PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR, ColorUtils.colorToRgbaString(value)
        )
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      }
    }

  /**
   * The fillTunnelStructureColor property in String
   *
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   */
  var fillTunnelStructureColorString: String?
    /**
     * Get the fillTunnelStructureColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillTunnelStructureColor property
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
      }
    }

  /**
   * The fillZOffset property
   *
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   */
  var fillZOffset: Double?
    /**
     * Get the fillZOffset property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the fillZOffset property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)
      }
    }

  /**
   * The fillBridgeGuardRailColorUseTheme property
   *
   * This property defines whether the `fillBridgeGuardRailColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  var fillBridgeGuardRailColorUseTheme: String?
    /**
     * Get the fillBridgeGuardRailColorUseTheme property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillBridgeGuardRailColorUseTheme property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME)
      }
    }

  /**
   * The fillColorUseTheme property
   *
   * This property defines whether the `fillColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  var fillColorUseTheme: String?
    /**
     * Get the fillColorUseTheme property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillColorUseTheme property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME)
      }
    }

  /**
   * The fillOutlineColorUseTheme property
   *
   * This property defines whether the `fillOutlineColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  var fillOutlineColorUseTheme: String?
    /**
     * Get the fillOutlineColorUseTheme property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillOutlineColorUseTheme property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME)
      }
    }

  /**
   * The fillTunnelStructureColorUseTheme property
   *
   * This property defines whether the `fillTunnelStructureColor` uses colorTheme from the style or not. By default it will use color defined by the root theme in the style.
   */
  var fillTunnelStructureColorUseTheme: String?
    /**
     * Get the fillTunnelStructureColorUseTheme property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the fillTunnelStructureColorUseTheme property
     *
     * To update the polygonAnnotation on the map use {@link polygonAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        jsonObject.addProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME, value)
      } else {
        jsonObject.remove(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME)
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
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_CONSTRUCT_BRIDGE_GUARD_RAIL)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR)
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
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_BRIDGE_GUARD_RAIL_COLOR_USE_THEME)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_COLOR_USE_THEME)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR_USE_THEME)
    }
    jsonObject.get(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME)?.let {
      annotationManager.enableDataDrivenProperty(PolygonAnnotationOptions.PROPERTY_FILL_TUNNEL_STRUCTURE_COLOR_USE_THEME)
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