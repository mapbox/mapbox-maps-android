package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImpl.Companion.TAG

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

  private val zeroEdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)

  /**
   * Type evaluator for EdgeInsets data
   */
  val EDGE_INSET = TypeEvaluator { fraction, startValue: EdgeInsets?, endValue: EdgeInsets? ->
    // We have seen in the wild that under some conditions we get null values. So let's guard
    // against possible null start/end
    val nonNullStart = if (startValue != null) {
      startValue
    } else {
      logW(TAG, "Start edge insets are null (fraction: $fraction)")
      zeroEdgeInsets
    }
    val nonNullEnd = if (endValue != null) {
      endValue
    } else {
      logW(TAG, "End edge insets are null (fraction: $fraction)")
      zeroEdgeInsets
    }
    EdgeInsets(
      nonNullStart.top + fraction * (nonNullEnd.top - nonNullStart.top),
      nonNullStart.left + fraction * (nonNullEnd.left - nonNullStart.left),
      nonNullStart.bottom + fraction * (nonNullEnd.bottom - nonNullStart.bottom),
      nonNullStart.right + fraction * (nonNullEnd.right - nonNullStart.right)
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
}