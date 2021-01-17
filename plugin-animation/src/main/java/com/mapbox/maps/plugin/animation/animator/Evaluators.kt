package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate

/**
 * Contains custom animator evaluators related to animating camera properties
 */
object Evaluators {

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

  /**
   * Type evaluator for EdgeInsets data
   */
  val EDGE_INSET = TypeEvaluator<EdgeInsets> { fraction, startValue, endValue ->
    EdgeInsets(
      startValue.top + fraction * (endValue.top - startValue.top),
      startValue.left + fraction * (endValue.left - startValue.left),
      startValue.bottom + fraction * (endValue.bottom - startValue.bottom),
      startValue.right + fraction * (endValue.right - startValue.right)
    )
  }

  /**
   * Type evaluator for ScreenCoordinate data
   */
  val SCREEN_COORDINATE = TypeEvaluator<ScreenCoordinate> { fraction, startValue, endValue ->
    ScreenCoordinate(
      startValue.x + fraction * (endValue.x - startValue.x),
      startValue.y + fraction * (endValue.y - startValue.y)
    )
  }

  /**
   * Type evaluator for Object data
   */
  val OBJECT = TypeEvaluator<Any> { _, _, _ -> Any() }
}