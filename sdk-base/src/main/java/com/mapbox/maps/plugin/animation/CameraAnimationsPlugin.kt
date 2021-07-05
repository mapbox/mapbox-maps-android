package com.mapbox.maps.plugin.animation

import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.MapPlugin

/**
 * Interface to interact with Camera Animations plugin
 */
interface CameraAnimationsPlugin : MapPlugin {

  /**
   * If debug mode is enabled extra logs will be written about animation lifecycle and
   * some other events that may be useful for debugging.
   */
  var debugMode: Boolean

  /**
   * Map camera anchor value.
   * Default value is NULL meaning center of given map view.
   * Left-top corner is represented as [ScreenCoordinate] (0.0, 0.0).
   *
   * If [anchor] is set to some specific value (set directly or by some running anchor animation)
   * it will be used as anchor for all upcoming animations even if they do not animate anchor directly.
   *
   * **Note**: If anchor animator is started and no start value is specified explicitly
   * and [anchor] = NULL - then start value will be set to ScreenCoordinate(0.0, 0.0) automatically
   * and it will be start point for interpolation.
   */
  var anchor: ScreenCoordinate?

  /**
   * Ease the map camera to a given camera options.
   *
   * @param cameraOptions The camera options to ease to
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  fun easeTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null
  ): Cancelable

  /**
   * Scale the map by with optional animation.
   *
   * @param amount The amount to scale by
   * @param screenCoordinate The optional focal point to scale on
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  fun scaleBy(
    amount: Double,
    screenCoordinate: ScreenCoordinate?,
    animationOptions: MapAnimationOptions? = null
  ): Cancelable

  /**
   * Move the map by a given screen coordinate with optional animation.
   *
   * @param screenCoordinate The screen coordinate distance to move by
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  fun moveBy(
    screenCoordinate: ScreenCoordinate,
    animationOptions: MapAnimationOptions? = null
  ): Cancelable

  /**
   * Rotate the map by with optional animation.
   *
   * @param first The first pointer to rotate on
   * @param second The second pointer to rotate on
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  fun rotateBy(
    first: ScreenCoordinate,
    second: ScreenCoordinate,
    animationOptions: MapAnimationOptions? = null
  ): Cancelable

  /**
   * Pitch the map by with optional animation.
   *
   * @param pitch The amount to pitch by
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  fun pitchBy(
    pitch: Double,
    animationOptions: MapAnimationOptions? = null
  ): Cancelable

  /**
   * Fly the map camera to a given camera options.
   *
   * This method implements an “optimal path” animation, as detailed in:

   * Van Wijk, Jarke J.; Nuij, Wim A. A. “Smooth and efficient zooming and
   * panning.” INFOVIS ’03. pp. 15–22.
   * [The online documentation](https://www.win.tue.nl/~vanwijk/zoompan.pdf#page=5).

   * Where applicable, local variable documentation begins with the associated
   * variable or function in van Wijk (2003).
   *
   * @param cameraOptions The camera options to fly to
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  fun flyTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions? = null
  ): Cancelable

  /**
   * Create CameraZoomAnimator
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  fun createZoomAnimator(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)? = null
  ): ValueAnimator

  /**
   * Create CameraAnchorAnimator
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  fun createAnchorAnimator(
    options: CameraAnimatorOptions<ScreenCoordinate>,
    block: (ValueAnimator.() -> Unit)? = null
  ): ValueAnimator

  /**
   * Create CameraBearingAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param useShortestPath if set to True shortest bearing path will be applied while animating bearing values.
   * If set to False clock-wise rotation will be used if next target is greater or equal than current one
   * and counter clock-wise rotation will be used if next target less than current one.
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  fun createBearingAnimator(
    options: CameraAnimatorOptions<Double>,
    useShortestPath: Boolean = true,
    block: (ValueAnimator.() -> Unit)? = null
  ): ValueAnimator

  /**
   * Create CameraPitchAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  fun createPitchAnimator(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)? = null
  ): ValueAnimator

  /**
   * Create CameraPaddingAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  fun createPaddingAnimator(
    options: CameraAnimatorOptions<EdgeInsets>,
    block: (ValueAnimator.() -> Unit)? = null
  ): ValueAnimator

  /**
   * Create CameraCenterAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  fun createCenterAnimator(
    options: CameraAnimatorOptions<Point>,
    block: (ValueAnimator.() -> Unit)? = null
  ): ValueAnimator

  /**
   * Register an animator
   */
  fun registerAnimators(vararg cameraAnimators: ValueAnimator)

  /**
   * Cancel all animators except ones owned by [exceptOwnerList] list.
   */
  fun cancelAllAnimators(exceptOwnerList: List<String> = emptyList())

  /**
   * Unregister all animators
   */
  fun unregisterAnimators(
    vararg cameraAnimators: ValueAnimator,
    cancelAnimators: Boolean = true
  )

  /**
   * Utility method to calculate scale by using the amount and current zoom
   */
  fun calculateScaleBy(amount: Double, currentZoom: Double): Double

  /**
   * Add camera bearing change listener
   *
   * @param listener to be invoked when camera bearing value changes
   */
  fun addCameraBearingChangeListener(listener: CameraAnimatorChangeListener<Double>)

  /**
   * Add camera pitch change listener
   *
   * @param listener to be invoked when camera bearing value changes
   */
  fun addCameraPitchChangeListener(listener: CameraAnimatorChangeListener<Double>)

  /**
   * Add camera zoom change listener
   *
   * @param listener to be invoked when camera bearing value changes
   */
  fun addCameraZoomChangeListener(listener: CameraAnimatorChangeListener<Double>)

  /**
   * Remove camera zoom change listener
   *
   * @param listener to be invoked when camera zoom value changes
   */
  fun removeCameraZoomChangeListener(listener: CameraAnimatorChangeListener<Double>)

  /**
   * Add camera center change listener
   *
   * @param listener to be invoked when camera bearing value changes
   */
  fun addCameraCenterChangeListener(listener: CameraAnimatorChangeListener<Point>)

  /**
   * Remove camera center change listener
   *
   * @param listener to be invoked when camera center value changes
   */
  fun removeCameraCenterChangeListener(listener: CameraAnimatorChangeListener<Point>)

  /**
   * Add camera padding change listener
   *
   * @param listener to be invoked when camera padding value changes
   */
  fun addCameraPaddingChangeListener(listener: CameraAnimatorChangeListener<EdgeInsets>)

  /**
   * Remove camera padding change listener
   *
   * @param listener to be invoked when camera padding value changes
   */
  fun removeCameraPaddingChangeListener(listener: CameraAnimatorChangeListener<EdgeInsets>)

  /**
   * Add camera anchor change listener
   *
   * @param listener to be invoked when camera bearing value changes
   */
  fun addCameraAnchorChangeListener(listener: CameraAnimatorNullableChangeListener<ScreenCoordinate?>)

  /**
   * Remove camera anchor change listener
   *
   * @param listener to be invoked when camera anchor value changes
   */
  fun removeCameraAnchorChangeListener(listener: CameraAnimatorNullableChangeListener<ScreenCoordinate?>)

  /**
   * Add camera bearing change listener
   *
   * @param listener to be invoked when camera bearing value changes
   */
  fun removeCameraBearingChangeListener(listener: CameraAnimatorChangeListener<Double>)

  /**
   * Remove camera pitch change listener
   *
   * @param listener to be invoked when camera pitch value changes
   */
  fun removeCameraPitchChangeListener(listener: CameraAnimatorChangeListener<Double>)

  /**
   * Add given [CameraAnimationsLifecycleListener] for capturing all events about animators lifecycle.
   *
   */
  fun addCameraAnimationsLifecycleListener(listener: CameraAnimationsLifecycleListener)

  /**
   * Remove given [CameraAnimationsLifecycleListener] for capturing all events about animators lifecycle.
   *
   */
  fun removeCameraAnimationsLifecycleListener(listener: CameraAnimationsLifecycleListener)

  /**
   * Play given [ValueAnimator]'s together
   *
   * @param animators Variable number of [ValueAnimator]'s
   */
  fun playAnimatorsTogether(vararg animators: ValueAnimator)

  /**
   * Play given [ValueAnimator]'s sequentially
   *
   * @param animators Variable number of [ValueAnimator]'s
   */
  fun playAnimatorsSequentially(vararg animators: ValueAnimator)
}