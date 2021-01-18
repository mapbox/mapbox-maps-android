package com.mapbox.maps.plugin.locationcomponent.utils

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.location.Location
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement

/**
 * Location utils.
 */
internal object Utils {
  private const val TAG = "Mbgl-Utils"

  /**
   * Util for finding the shortest path from the current rotated degree to the new degree.
   *
   * @param heading         the new position of the rotation
   * @param previousHeading the current position of the rotation
   * @return the shortest degree of rotation possible
   */
  fun shortestRotation(heading: Float, previousHeading: Float): Float {
    var heading = heading
    val diff = previousHeading - heading.toDouble()
    if (diff > 180.0f) {
      heading += 360.0f
    } else if (diff < -180.0f) {
      heading -= 360f
    }
    return heading
  }

  /**
   * Normalizes an angle to be in the [0, 360] range.
   *
   * @param angle the provided angle
   * @return the normalized angle
   */
  fun normalize(angle: Float): Float {
    return (angle % 360 + 360) % 360
  }

  /**
   * We need to ensure that the radius of any [GradientDrawable] is greater than 0 for API levels < 21.
   *
   * @see [mapbox-gl-native-.15026](https://github.com/mapbox/mapbox-gl-native/issues/15026)
   */
  private fun ensureShadowGradientRadius(drawable: Drawable) {
    when (drawable) {
      is GradientDrawable -> {
        drawable.gradientRadius = 1f
      }
      is LayerDrawable -> {
        for (i in 0 until drawable.numberOfLayers) {
          val layers = drawable.getDrawable(i)
          if (layers is GradientDrawable) {
            layers.gradientRadius = 1f
          }
        }
      }
      else -> {
        Logger.e(TAG, "Unsupported drawable: drawable is not GradientDrawable or LayerDrawable.")
      }
    }
  }

  /**
   * Calculate the zoom level radius.
   */
  fun calculateZoomLevelRadius(
    projection: MapProjectionDelegate,
    location: Location?
  ): Float {
    if (location == null) {
      return 0.0f
    }
    val metersPerPixel =
      projection.getMetersPerPixelAtLatitude(location.latitude)
    return (location.accuracy * (1 / metersPerPixel)).toFloat()
  }

  /**
   * Check whether the location puck should be immediately animated from one point to another.
   */
  fun immediateAnimation(
    projection: MapProjectionDelegate,
    current: Point,
    target: Point
  ): Boolean {
    val metersPerPixel =
      projection.getMetersPerPixelAtLatitude((current.latitude() + target.latitude()) / 2)
    val distance =
      TurfMeasurement.distance(current, target, TurfConstants.UNIT_METRES)
    return distance / metersPerPixel > LocationComponentConstants.INSTANT_LOCATION_TRANSITION_THRESHOLD
  }

  /**
   * Casts the value to an even integer.
   */
  private fun toEven(value: Float): Int {
    val i = (value + .5f).toInt()
    return if (i % 2 == 1) {
      i - 1
    } else i
  }
}