package com.mapbox.maps.extension.compose.animation

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import com.mapbox.geojson.Point


private val defaultPointAnimation = tween<Point>(
  durationMillis = 1000,
  easing = LinearOutSlowInEasing
)

/**
 * A [TwoWayConverter] that converts [Point] from and to [AnimationVector3D]
 */
internal val PointToVectorConverter: TwoWayConverter<Point, AnimationVector3D> =
  TwoWayConverter(
    convertToVector = {
      AnimationVector3D(
        it.longitude().toFloat(),
        it.latitude().toFloat(),
        it.altitude().toFloat()
      )
    },
    convertFromVector = {
      Point.fromLngLat(it.v1.toDouble(), it.v2.toDouble(), it.v3.toDouble())
    }
  )

@Composable
public fun animatePointAsState(
  targetValue: Point,
  animationSpec: AnimationSpec<Point> = defaultPointAnimation,
  durationMillis: Int = 1000,
  finishedListener: ((Point) -> Unit)? = null
): State<Point> {
  val resolvedAnimSpec =
    if (animationSpec === defaultPointAnimation) {
      remember(durationMillis) { tween(durationMillis = durationMillis) }
    } else {
      animationSpec
    }
  return animateValueAsState(
    targetValue,
    PointToVectorConverter,
    resolvedAnimSpec,
    null,
    finishedListener
  )
}