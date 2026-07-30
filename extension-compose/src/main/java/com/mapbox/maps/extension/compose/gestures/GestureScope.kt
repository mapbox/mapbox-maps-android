package com.mapbox.maps.extension.compose.gestures

import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.android.gestures.ShoveGestureDetector
import com.mapbox.android.gestures.StandardScaleGestureDetector
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.gestures.OnFlingListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.OnRotateListener
import com.mapbox.maps.plugin.gestures.OnScaleListener
import com.mapbox.maps.plugin.gestures.OnShoveListener
import com.mapbox.maps.plugin.gestures.addOnFlingListener
import com.mapbox.maps.plugin.gestures.addOnMoveListener
import com.mapbox.maps.plugin.gestures.addOnRotateListener
import com.mapbox.maps.plugin.gestures.addOnScaleListener
import com.mapbox.maps.plugin.gestures.addOnShoveListener
import com.mapbox.maps.plugin.gestures.removeOnFlingListener
import com.mapbox.maps.plugin.gestures.removeOnMoveListener
import com.mapbox.maps.plugin.gestures.removeOnRotateListener
import com.mapbox.maps.plugin.gestures.removeOnScaleListener
import com.mapbox.maps.plugin.gestures.removeOnShoveListener
import kotlinx.coroutines.awaitCancellation

/**
 * Receiver scope for registering gesture listeners on a [MapboxMap].
 *
 * Each `detect*` function registers the corresponding listener, suspends until the coroutine is
 * canceled, and then automatically removes the listener. Call multiple `detect*` functions
 * concurrently (e.g. with [kotlinx.coroutines.coroutineScope]) to observe several gesture types
 * at once.
 *
 */
@MapboxExperimental
public class GestureScope internal constructor(private val mapboxMap: MapboxMap) {

  /**
   * Registers an [OnMoveListener][com.mapbox.maps.plugin.gestures.OnMoveListener] and
   * suspends until cancellation.
   *
   * @param onMoveBegin called when the move gesture starts.
   * @param onMove called during the move gesture. Return `true` to consume the event.
   * @param onMoveEnd called when the move gesture ends.
   */
  public suspend fun detectMoveGestures(
    onMoveBegin: (MoveGestureDetector) -> Unit = {},
    onMoveEnd: (MoveGestureDetector) -> Unit = {},
    onMove: (MoveGestureDetector) -> Boolean = { false },
  ) {
    val listener = object : OnMoveListener {
      override fun onMoveBegin(detector: MoveGestureDetector) = onMoveBegin(detector)
      override fun onMove(detector: MoveGestureDetector): Boolean = onMove(detector)
      override fun onMoveEnd(detector: MoveGestureDetector) = onMoveEnd(detector)
    }
    try {
      mapboxMap.addOnMoveListener(listener)
      awaitCancellation()
    } finally {
      mapboxMap.removeOnMoveListener(listener)
    }
  }

  /**
   * Registers an [OnRotateListener][com.mapbox.maps.plugin.gestures.OnRotateListener] and
   * suspends until cancellation.
   *
   * @param onRotateBegin called when the rotate gesture starts.
   * @param onRotate called during the rotate gesture.
   * @param onRotateEnd called when the rotate gesture ends.
   */
  public suspend fun detectRotateGestures(
    onRotateBegin: (RotateGestureDetector) -> Unit = {},
    onRotateEnd: (RotateGestureDetector) -> Unit = {},
    onRotate: (RotateGestureDetector) -> Unit = {},
  ) {
    val listener = object : OnRotateListener {
      override fun onRotateBegin(detector: RotateGestureDetector) = onRotateBegin(detector)
      override fun onRotate(detector: RotateGestureDetector) = onRotate(detector)
      override fun onRotateEnd(detector: RotateGestureDetector) = onRotateEnd(detector)
    }
    try {
      mapboxMap.addOnRotateListener(listener)
      awaitCancellation()
    } finally {
      mapboxMap.removeOnRotateListener(listener)
    }
  }

  /**
   * Registers an [OnScaleListener][com.mapbox.maps.plugin.gestures.OnScaleListener] and
   * suspends until cancellation.
   *
   * @param onScaleBegin called when the scale gesture starts.
   * @param onScale called during the scale gesture.
   * @param onScaleEnd called when the scale gesture ends.
   */
  public suspend fun detectScaleGestures(
    onScaleBegin: (StandardScaleGestureDetector) -> Unit = {},
    onScaleEnd: (StandardScaleGestureDetector) -> Unit = {},
    onScale: (StandardScaleGestureDetector) -> Unit = {},
  ) {
    val listener = object : OnScaleListener {
      override fun onScaleBegin(detector: StandardScaleGestureDetector) = onScaleBegin(detector)
      override fun onScale(detector: StandardScaleGestureDetector) = onScale(detector)
      override fun onScaleEnd(detector: StandardScaleGestureDetector) = onScaleEnd(detector)
    }
    try {
      mapboxMap.addOnScaleListener(listener)
      awaitCancellation()
    } finally {
      mapboxMap.removeOnScaleListener(listener)
    }
  }

  /**
   * Registers an [OnShoveListener][com.mapbox.maps.plugin.gestures.OnShoveListener] and
   * suspends until cancellation.
   *
   * @param onShoveBegin called when the shove gesture starts.
   * @param onShove called during the shove gesture.
   * @param onShoveEnd called when the shove gesture ends.
   */
  public suspend fun detectShoveGestures(
    onShoveBegin: (ShoveGestureDetector) -> Unit = {},
    onShoveEnd: (ShoveGestureDetector) -> Unit = {},
    onShove: (ShoveGestureDetector) -> Unit = {},
  ) {
    val listener = object : OnShoveListener {
      override fun onShoveBegin(detector: ShoveGestureDetector) = onShoveBegin(detector)
      override fun onShove(detector: ShoveGestureDetector) = onShove(detector)
      override fun onShoveEnd(detector: ShoveGestureDetector) = onShoveEnd(detector)
    }
    try {
      mapboxMap.addOnShoveListener(listener)
      awaitCancellation()
    } finally {
      mapboxMap.removeOnShoveListener(listener)
    }
  }

  /**
   * Registers an [OnFlingListener][com.mapbox.maps.plugin.gestures.OnFlingListener] and
   * suspends until cancellation.
   *
   * @param onFling called when a fling gesture is detected.
   */
  public suspend fun detectFlingGesture(onFling: () -> Unit) {
    val listener = OnFlingListener { onFling() }
    try {
      mapboxMap.addOnFlingListener(listener)
      awaitCancellation()
    } finally {
      mapboxMap.removeOnFlingListener(listener)
    }
  }
}