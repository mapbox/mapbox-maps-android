package com.mapbox.maps.util

/**
 * Math utils class.
 */
object MathUtils {

  /**
   * Util function to transform given bearing values to optimal ones so that shortest path is
   * always taken.
   */
  fun prepareOptimalBearingPath(targets: DoubleArray): DoubleArray {
    val optimized = DoubleArray(targets.size)
    targets.apply {
      for (i in 0 until size) {
        optimized[i] = if (i == 0)
          normalize(get(i))
        else
          shortestRotation(normalize(get(i)), optimized[i - 1])
      }
    }
    return optimized
  }

  /**
   * Util for finding the shortest path from the current rotated degree to the new degree.
   *
   * @param currentHeading  the new position of the rotation
   * @param previousHeading the current position of the rotation
   * @return the shortest degree of rotation possible
   */
  internal fun shortestRotation(currentHeading: Double, previousHeading: Double): Double {
    val diff = previousHeading - currentHeading
    return when {
      diff > 180.0f -> {
        currentHeading + 360.0f
      }
      diff < -180.0f -> {
        currentHeading - 360.0f
      }
      else -> {
        currentHeading
      }
    }
  }

  /**
   * Normalizes an angle to be in the [0, 360] range.
   *
   * @param angle the provided angle
   * @return the normalized angle
   */
  private fun normalize(angle: Double): Double {
    return (angle % 360.0 + 360.0) % 360.0
  }
}