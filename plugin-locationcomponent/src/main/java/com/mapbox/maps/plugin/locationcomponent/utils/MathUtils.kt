package com.mapbox.maps.plugin.locationcomponent.utils

internal object MathUtils {
  /**
   * Util for finding the shortest path from the current rotated degree to the new degree.
   *
   * @param targetHeading  the new position of the rotation
   * @param previousHeading the current position of the rotation
   * @return the shortest degree of rotation possible
   */
  fun shortestRotation(targetHeading: Double, previousHeading: Double): Double {
    val diff = previousHeading - targetHeading
    return when {
      diff > 180.0f -> {
        targetHeading + 360.0f
      }
      diff < -180.0f -> {
        targetHeading - 360f
      }
      else -> {
        targetHeading
      }
    }
  }

  /**
   * Normalizes an angle to be in the [0, 360] range.
   *
   * @param angle the provided angle
   * @return the normalized angle
   */
  fun normalize(angle: Double): Double {
    return (angle % 360.0 + 360.0) % 360.0
  }
}