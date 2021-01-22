package com.mapbox.maps.plugin

import com.mapbox.bindgen.Value
import com.mapbox.common.ValueConverter
import kotlin.math.abs

/**
 * Extension function to compare two double with delta.
 */
fun Double.equalsWithDelta(other: Double, delta: Double): Boolean {
  return abs(this - other) < delta
}

/**
 * Extension function to compare two double with default delta of 1E-5.
 */
fun Double.roughlyEquals(other: Double): Boolean {
  return this.equalsWithDelta(other, 1E-5)
}

/**
 * Extension function to compare two double with default delta of 1E-5.
 */
fun Value.toJson(): String {
  return ValueConverter.toJson(this)
}