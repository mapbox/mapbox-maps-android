package com.mapbox.maps.plugin.gestures

import androidx.annotation.RestrictTo
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.android.gestures.ShoveGestureDetector
import com.mapbox.android.gestures.StandardScaleGestureDetector
import com.mapbox.geojson.Point

/**
 * Interface definition for a callback to be invoked when the map is flinged.
 */
fun interface OnFlingListener {
  /**
   * Called when the map is flinged.
   */
  fun onFling()
}

/**
 * For internal usage.
 *
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
interface TopPriorityAsyncMapClickListener : OnMapClickListener, OnMapLongClickListener {

  /**
   * For internal usage.
   */
  fun asyncHandleClick(point: Point, continueToNextListener: () -> Unit)
}

/**
 * Interface definition for a callback to be invoked when the user clicks on the map view.
 */
fun interface OnMapClickListener {
  /**
   * Called when the user clicks on the map view.
   *
   * @param point The projected map coordinate the user clicked on.
   * @return True if this click should be consumed and not passed further to other listeners registered afterwards,
   * false otherwise.
   */
  fun onMapClick(point: Point): Boolean
}

/**
 * Interface definition for a callback to be invoked when the user long clicks on the map view.
 */
fun interface OnMapLongClickListener {
  /**
   * Called when the user long clicks on the map view.
   *
   * @param point The projected map coordinate the user long clicked on.
   * @return True if this click should be consumed and not passed further to other listeners registered afterwards,
   * false otherwise.
   */
  fun onMapLongClick(point: Point): Boolean
}

/**
 * Represents the top priority [OnMoveListener] which will be always added
 * in front of the listener list via [GesturesPlugin.addOnMoveListener].
 *
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
interface TopPriorityOnMoveListener : OnMoveListener

/**
 * Interface definition for a callback to be invoked when the map is moved.
 */
interface OnMoveListener {
  /**
   * Called when the move gesture is starting.
   */
  fun onMoveBegin(detector: MoveGestureDetector)

  /**
   * Called when the move gesture is executing.
   *
   * @return true if the gesture was handled, false otherwise
   */
  fun onMove(detector: MoveGestureDetector): Boolean

  /**
   * Called when the move gesture is ending.
   */
  fun onMoveEnd(detector: MoveGestureDetector)
}

/**
 * Interface definition for a callback to be invoked when the map is rotated.
 */
interface OnRotateListener {
  /**
   * Called when the rotate gesture is starting.
   */
  fun onRotateBegin(detector: RotateGestureDetector)

  /**
   * Called when the rotate gesture is executing.
   */
  fun onRotate(detector: RotateGestureDetector)

  /**
   * Called when the rotate gesture is ending.
   */
  fun onRotateEnd(detector: RotateGestureDetector)
}

/**
 * Interface definition for a callback to be invoked when the map is scaled.
 */
interface OnScaleListener {
  /**
   * Called when the scale gesture is starting.
   */
  fun onScaleBegin(detector: StandardScaleGestureDetector)

  /**
   * Called when the scale gesture is executing.
   */
  fun onScale(detector: StandardScaleGestureDetector)

  /**
   * called when the scale gesture has ended.
   */
  fun onScaleEnd(detector: StandardScaleGestureDetector)
}

/**
 * Interface definition for a callback to be invoked when the map is shoved with two fingers.
 */
interface OnShoveListener {
  /**
   * Called when the shove gesture is starting.
   */
  fun onShoveBegin(detector: ShoveGestureDetector)

  /**
   * Called when the shove gesture is executing.
   */
  fun onShove(detector: ShoveGestureDetector)

  /**
   * Called when the shove gesture has ended.
   */
  fun onShoveEnd(detector: ShoveGestureDetector)
}