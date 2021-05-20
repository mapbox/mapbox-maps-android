package com.mapbox.maps.extension.style.light

import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.types.LightDsl
import com.mapbox.maps.extension.style.utils.TypeUtils

/**
 * Position of the light source relative to lit (extruded) geometries.
 *
 *
 * The position is constructed out of a radial coordinate, an azimuthal angle and a polar angle.
 * where the radial coordinate indicates the distance from the center of the base of an object to its light, the
 * azimuthal angle indicates the position of the light relative to 0 degree (0 degree when
 * [com.mapbox.mapboxsdk.style.layers.Property.ANCHOR] is set to viewport corresponds to the top of the
 * viewport, or 0 degree when [com.mapbox.mapboxsdk.style.layers.Property.ANCHOR] is set to map corresponds to due
 * north, and degrees proceed clockwise), and polar indicates the height of the light
 * (from 0 degree, directly above, to 180 degree, directly below).
 *
 * @param radialCoordinate the distance from the center of the base of an object to its light
 * @param azimuthalAngle the position of the light relative to 0 degree
 * @param polarAngle the height of the light
 */
@LightDsl
data class LightPosition(
  private val radialCoordinate: Double,
  private val azimuthalAngle: Double,
  private val polarAngle: Double
) {
  /**
   * Convert this position to a [Value].
   */
  fun toValue(): Value {
    return TypeUtils.wrapToValue(doubleArrayOf(radialCoordinate, azimuthalAngle, polarAngle))
  }

  /**
   * Convert this position to a [DoubleArray].
   */
  fun toDoubleArray(): DoubleArray {
    return doubleArrayOf(radialCoordinate, azimuthalAngle, polarAngle)
  }

  /**
   * Convert this position to a [DoubleArray].
   */
  fun toList(): List<Double> {
    return listOf(radialCoordinate, azimuthalAngle, polarAngle)
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * Construct a [LightPosition] object from [DoubleArray].
     */
    fun fromArray(
      positionArray: DoubleArray
    ): LightPosition {
      return LightPosition(
        positionArray[0],
        positionArray[1],
        positionArray[2]
      )
    }

    /**
     * Construct a [LightPosition] object from [List].
     */
    fun fromList(
      positionList: List<Double>
    ): LightPosition {
      return LightPosition(
        positionList[0],
        positionList[1],
        positionList[2]
      )
    }
  }
}