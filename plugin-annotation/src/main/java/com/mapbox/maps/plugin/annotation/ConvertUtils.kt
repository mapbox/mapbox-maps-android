package com.mapbox.maps.plugin.annotation

import com.google.gson.JsonArray

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
}