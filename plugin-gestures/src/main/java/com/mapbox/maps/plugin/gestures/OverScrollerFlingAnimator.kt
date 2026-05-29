package com.mapbox.maps.plugin.gestures

import android.content.Context
import android.view.Choreographer
import android.widget.OverScroller
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

/**
 * Drives fling animation using Android's [OverScroller] for physics-based deceleration.
 *
 * Instead of computing a single target and easing to it, this class:
 * 1. Calls [OverScroller.fling] with the initial velocity
 * 2. Each frame, computes the incremental delta from the previous position
 * 3. Converts that delta to a camera update via [MapCameraManagerDelegate.cameraForDrag]
 * 4. Applies the camera update immediately via [MapCameraManagerDelegate.setCamera]
 */
internal class OverScrollerFlingAnimator(
  context: Context,
  private val mapCameraManagerDelegate: MapCameraManagerDelegate,
) {

  private val overScroller = OverScroller(context)

  private val choreographer: Choreographer = Choreographer.getInstance()

  private var prevX = 0
  private var prevY = 0

  var isRunning = false
    private set

  private var fromPoint = ScreenCoordinate(0.0, 0.0)

  var onAnimationStart: (() -> Unit)? = null
  var onAnimationEnd: (() -> Unit)? = null

  var limitHorizontal = false
  var limitVertical = false

  @VisibleForTesting
  val frameCallback = object : Choreographer.FrameCallback {
    override fun doFrame(frameTimeNanos: Long) {
      if (!overScroller.computeScrollOffset()) {
        if (isRunning) {
          choreographer.removeFrameCallback(this)
          isRunning = false
          onAnimationEnd?.invoke()
        }
        return
      }
      val currentX = overScroller.currX
      val currentY = overScroller.currY
      val deltaX = if (limitHorizontal) 0 else (currentX - prevX)
      val deltaY = if (limitVertical) 0 else (currentY - prevY)
      prevX = currentX
      prevY = currentY

      if (deltaX != 0 || deltaY != 0) {
        val toPoint = ScreenCoordinate(
          fromPoint.x + deltaX.toDouble(),
          fromPoint.y + deltaY.toDouble()
        )
        val cameraOptions = mapCameraManagerDelegate.cameraForDrag(fromPoint, toPoint)
        mapCameraManagerDelegate.setCamera(cameraOptions)
      }

      choreographer.postFrameCallback(this)
    }
  }

  /**
   * Start a fling animation.
   *
   * @param velocityX horizontal velocity in pixels/sec
   * @param velocityY vertical velocity in pixels/sec
   * @param simulateOrigin the simulated touch origin point for [MapCameraManagerDelegate.cameraForDrag]
   */
  fun fling(
    velocityX: Int,
    velocityY: Int,
    simulateOrigin: ScreenCoordinate
  ) {
    forceStop()
    fromPoint = simulateOrigin
    prevX = 0
    prevY = 0
    isRunning = true

    overScroller.fling(
      0,
      0,
      velocityX,
      velocityY,
      Int.MIN_VALUE / 2,
      Int.MAX_VALUE / 2,
      Int.MIN_VALUE / 2,
      Int.MAX_VALUE / 2
    )

    onAnimationStart?.invoke()
    choreographer.postFrameCallback(frameCallback)
  }

  /**
   * Immediately stop the fling animation. Called on ACTION_DOWN.
   */
  fun forceStop() {
    if (isRunning) {
      overScroller.forceFinished(true)
      choreographer.removeFrameCallback(frameCallback)
      isRunning = false
      onAnimationEnd?.invoke()
    }
  }
}