package com.mapbox.maps.plugin.annotation

import com.google.gson.JsonArray
import com.mapbox.geojson.Point
import com.mapbox.maps.MercatorCoordinate
import com.mapbox.maps.Projection

/**
 * Util class for converting properties.
 */
object ConvertUtils {
  /**
   * Convert a list into a JsonArray with string format
   */
  fun convertStringArray(value: List<Any>?): JsonArray {
    val jsonArray = JsonArray()
    if (value != null) {
      for (element in value) {
        jsonArray.add(element.toString())
      }
    }
    return jsonArray
  }

  /**
   * Convert a list into a JsonArray with double format
   */
  fun convertDoubleArray(value: List<Any>?): JsonArray {
    val jsonArray = JsonArray()
    if (value != null) {
      for (element in value) {
        jsonArray.add(element.toString().toDouble())
      }
    }
    return jsonArray
  }

  /**
   * Convert a JsonArray to a list of Double
   */
  fun toDoubleArray(jsonArray: JsonArray?): List<Double> {
    val array = mutableListOf<Double>()
    if (jsonArray != null) {
      for (i in 0 until jsonArray.size()) {
        array[i] = jsonArray[i].asDouble
      }
    }
    return array
  }

  /**
   * Convert a JsonArray to a list of String
   */
  fun toStringArray(jsonArray: JsonArray?): List<String> {
    val array = mutableListOf<String>()
    if (jsonArray != null) {
      for (i in 0 until jsonArray.size()) {
        array[i] = jsonArray[i].asString
      }
    }
    return array
  }

  /**
   * Calculate the shift between two [Point]s in Mercator coordinate.
   *
   * @param startPoint the start point for the calculation.
   * @param endPoint the end point for the calculation.
   * @param zoomLevel the zoom level that apply the calculation.
   *
   * @return A [MercatorCoordinate] represent the shift between startPoint and endPoint.
   */
  fun calculateMercatorCoordinateShift(
    startPoint: Point,
    endPoint: Point,
    zoomLevel: Double
  ): MercatorCoordinate {
    val centerMercatorCoordinate = Projection.project(startPoint, zoomLevel)
    val targetMercatorCoordinate = Projection.project(endPoint, zoomLevel)

    // Get the shift in Mercator coordinates
    return MercatorCoordinate(
      targetMercatorCoordinate.x - centerMercatorCoordinate.x,
      targetMercatorCoordinate.y - centerMercatorCoordinate.y
    )
  }

  /**
   * Apply a [MercatorCoordinate] to the original point.
   *
   * @param point the point needs to shift.
   * @param shiftMercatorCoordinate the shift that applied to the original point.
   * @param zoomLevel the zoom level that apply the calculation.
   *
   * @return a shift point that applied the shift MercatorCoordinate.
   */
  fun shiftPointWithMercatorCoordinate(
    point: Point,
    shiftMercatorCoordinate: MercatorCoordinate,
    zoomLevel: Double
  ): Point {
    // transform point to Mercator coordinate
    val mercatorCoordinate = Projection.project(point, zoomLevel)
    // calculate the shifted Mercator coordinate
    val shiftedMercatorCoordinate = MercatorCoordinate(
      mercatorCoordinate.x + shiftMercatorCoordinate.x,
      mercatorCoordinate.y + shiftMercatorCoordinate.y
    )
    // transform Mercator coordinate to point
    return Projection.unproject(shiftedMercatorCoordinate, zoomLevel)
  }
}