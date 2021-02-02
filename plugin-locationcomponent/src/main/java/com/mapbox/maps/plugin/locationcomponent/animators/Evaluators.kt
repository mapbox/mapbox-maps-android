package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.TypeEvaluator
import com.mapbox.geojson.Point

internal object Evaluators {
  /**
   * Type evaluator for Point data
   */
  val POINT = TypeEvaluator<Point> { fraction, startValue, endValue ->
    Point.fromLngLat(
      startValue.longitude() + fraction * (endValue.longitude() - startValue.longitude()),
      startValue.latitude() + fraction * (endValue.latitude() - startValue.latitude())
    )
  }

  /**
   * Type evaluator for Double data
   */
  val DOUBLE = TypeEvaluator<Double> { fraction, startValue, endValue ->
    startValue + fraction * (endValue - startValue)
  }
}