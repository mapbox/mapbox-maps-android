package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.TypeEvaluator
import com.mapbox.geojson.Point

internal object Evaluators {
  /**
   * Type evaluator for Point data
   */
  val POINT = TypeEvaluator<Point> { fraction, startValue, endValue ->
    val longitude = startValue.longitude() + fraction * (endValue.longitude() - startValue.longitude())
    val latitude = startValue.latitude() + fraction * (endValue.latitude() - startValue.latitude())
    if (startValue.hasAltitude() && endValue.hasAltitude()) {
      Point.fromLngLat(
        longitude,
        latitude,
        startValue.altitude() + fraction * (endValue.altitude() - startValue.altitude()),
      )
    } else {
      Point.fromLngLat(longitude, latitude)
    }
  }

  /**
   * Type evaluator for Double data
   */
  val DOUBLE = TypeEvaluator<Double> { fraction, startValue, endValue ->
    startValue + fraction * (endValue - startValue)
  }
}