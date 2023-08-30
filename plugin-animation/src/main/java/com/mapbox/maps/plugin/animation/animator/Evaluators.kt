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
    // If fraction is 1 or 0 then return `endValue`/`startValue` directly without creating new Point
    if (fraction == 1F) {
      endValue
    } else if (fraction == 0F) {
      startValue
    } else {
      val longitudeDelta = endValue.longitude() - startValue.longitude()
      val latitudeDelta = endValue.latitude() - startValue.latitude()
      if (longitudeDelta == 0.0 && latitudeDelta == 0.0) {
        startValue
      } else {
        Point.fromLngLat(
          startValue.longitude() + fraction * longitudeDelta,
          startValue.latitude() + fraction * latitudeDelta
        )
      }
    }
  }

  /**
   * Type evaluator for Double data
   */
  val DOUBLE = TypeEvaluator<Double> { fraction, startValue, endValue ->
    // If fraction is 1 or 0 then return `endValue`/`startValue` directly without creating new Double
    if (fraction == 1F) {
      endValue
    } else if (fraction == 0F) {
      startValue
    } else {
      val delta = endValue - startValue
      if (delta == 0.0) {
        startValue
      } else {
        startValue + fraction * delta
      }
    }
  }

  private val zeroEdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)

  /**
   * Type evaluator for EdgeInsets data
   */
  val EDGE_INSET = TypeEvaluator { fraction, startValue: EdgeInsets?, endValue: EdgeInsets? ->
    // We have seen in the wild that under some conditions we get null values. So let's guard
    // against possible null start/end
    val nonNullEnd = if (endValue != null) {
      endValue
    } else {
      logW(TAG, "End edge insets are null (fraction: $fraction)")
      zeroEdgeInsets
    }

    // If fraction is 1 then we can return the `endValue` directly without creating new EdgeInsets
    if (fraction == 1F) {
      return@TypeEvaluator nonNullEnd
    }
    val nonNullStart = if (startValue != null) {
      startValue
    } else {
      logW(TAG, "Start edge insets are null (fraction: $fraction)")
      zeroEdgeInsets
    }

    // If fraction is 0 then we can return the `startValue` directly without creating new EdgeInsets
    if (fraction == 0F) {
      return@TypeEvaluator nonNullStart
    }

    val topDelta = nonNullEnd.top - nonNullStart.top
    val leftDelta = nonNullEnd.left - nonNullStart.left
    val bottomDelta = nonNullEnd.bottom - nonNullStart.bottom
    val rightDelta = nonNullEnd.right - nonNullStart.right

    // As an optimization we can avoid creating EdgeInsets object if there's no delta for any side
    if (topDelta == 0.0 && leftDelta == 0.0 && bottomDelta == 0.0 && rightDelta == 0.0) {
      nonNullStart
    } else {
      EdgeInsets(
        nonNullStart.top + fraction * topDelta,
        nonNullStart.left + fraction * leftDelta,
        nonNullStart.bottom + fraction * bottomDelta,
        nonNullStart.right + fraction * rightDelta
      )
    }
  }

  /**
   * Type evaluator for ScreenCoordinate data
   */
  val SCREEN_COORDINATE = TypeEvaluator<ScreenCoordinate> { fraction, startValue, endValue ->
    // If fraction is 1 or 0 then return `endValue`/`startValue` directly without creating new ScreenCoordinate
    if (fraction == 1F) {
      endValue
    } else if (fraction == 0F) {
      startValue
    } else {
      val xDelta = endValue.x - startValue.x
      val yDelta = endValue.y - startValue.y
      if (xDelta == 0.0 && yDelta == 0.0) {
        startValue
      } else {
        ScreenCoordinate(startValue.x + fraction * xDelta, startValue.y + fraction * yDelta)
      }
    }
  }
}