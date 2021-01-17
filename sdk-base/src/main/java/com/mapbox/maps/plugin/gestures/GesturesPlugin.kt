package com.mapbox.maps.plugin.gestures

import android.view.MotionEvent
import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.maps.plugin.ContextBinder
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapSizePlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.gestures.generated.GesturesSettingsInterface

/**
 * Define the interfaces for the Layer plugin.
 */
interface GesturesPlugin : MapPlugin, ContextBinder, MapSizePlugin, GesturesSettingsInterface {

  /**
   * Called when user touches the screen, all positions are absolute.
   *
   *
   * Forwards event to the related gesture detectors.
   *
   *
   * @param motionEvent the MotionEvent
   * @return True if touch event is handled
   */
  fun onTouchEvent(motionEvent: MotionEvent?): Boolean

  /**
   * Called for events that don't fit the other handlers.
   *
   *
   * Examples of such events are mouse scroll events, mouse moves, joystick & trackpad.
   *
   *
   * @param event The MotionEvent occurred
   * @return True is the event is handled
   */
  fun onGenericMotionEvent(event: MotionEvent): Boolean

  /**
   * Add a map click listener that is invoked when a map is clicked.
   *
   * @param onMapClickListener The map click listener to be added
   */
  fun addOnMapClickListener(onMapClickListener: OnMapClickListener)

  /**
   * Remove a map click listener that is invoked when a map is clicked.
   *
   * @param onMapClickListener The map click listener to be removed
   */
  fun removeOnMapClickListener(onMapClickListener: OnMapClickListener)

  /**
   * Add a map long click listener that is invoked when a map is clicked.
   *
   * @param onMapLongClickListener The map long click listener to be added
   */
  fun addOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener)

  /**
   * Add a map long click listener that is invoked when a map is clicked.
   *
   * @param onMapLongClickListener The map long click listener to be removed
   */
  fun removeOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener)

  /**
   * Set internal gesture manager
   *
   * @param internalGesturesManager The gesture manager to override
   * @param attachDefaultListeners The flag to attach default gesture listeners
   * @param setDefaultMutuallyExclusives The flag to set default mutually exclusive gestures
   */
  fun setGesturesManager(
    internalGesturesManager: AndroidGesturesManager,
    attachDefaultListeners: Boolean,
    setDefaultMutuallyExclusives: Boolean
  )

  /**
   * Get the current configured gesture manager
   */
  fun getGesturesManager(): AndroidGesturesManager

  /**
   * Add a rotation gesture listener that is invoked when a map is rotated
   *
   * @param onRotateListener The rotate gesture listener to be added
   */
  fun addOnRotateListener(onRotateListener: OnRotateListener)

  /**
   * Remove a rotate gesture listener that is invoked when a map is rotated
   *
   * @param listener The rotate gesture listener to be removed
   */
  fun removeOnRotateListener(listener: OnRotateListener)

  /**
   * Add a fling gesture listener that is invoked when a map is flinged
   *
   * @param onFlingListener The fling gesture listener to be added
   */
  fun addOnFlingListener(onFlingListener: OnFlingListener)

  /**
   * Remove a fling gesture listener that is invoked when a map is flinged
   *
   * @param onFlingListener The fling gesture listener to be removed
   */
  fun removeOnFlingListener(onFlingListener: OnFlingListener)

  /**
   * Add a move gesture listener that is invoked when a map is moved
   *
   * @param onMoveListener The move gesture listener to be added
   */
  fun addOnMoveListener(onMoveListener: OnMoveListener)

  /**
   * Remove a move gesture listener that is invoked when a map is moved
   *
   * @param listener The move gesture listener to be removed
   */
  fun removeOnMoveListener(listener: OnMoveListener)

  /**
   * Add a scale gesture listener that is invoked when a map is scaled
   *
   * @param onScaleListener The scale gesture listener to be added
   */
  fun addOnScaleListener(onScaleListener: OnScaleListener)

  /**
   * Add a shove gesture listener tha is invoked when a map is shoved
   *
   * @param onShoveListener The shove listener to be added
   */
  fun addOnShoveListener(onShoveListener: OnShoveListener)

  /**
   * Remove a shove gesture listener that is invoked wen a map is shoved
   *
   * @param listener The shove listener to be removed
   */
  fun removeOnShoveListener(listener: OnShoveListener)

  /**
   * Add animator owner (see [CameraAnimatorOptions.owner] or [MapAnimationOptions.owner]
   * which animation will not be canceled with when gesture animation is about to start.
   * When specified, you are responsible for listening to gesture interactions and canceling the specified owners' animations to avoid competing with gestures.
   */
  fun addProtectedAnimationOwner(owner: String)

  /**
   * Remove animator owner (see [CameraAnimatorOptions.owner] or [MapAnimationOptions.owner])
   * which animation will not be canceled with when gesture animation is about to start.
   * When specified, you are responsible for listening to gesture interactions and canceling the specified owners' animations to avoid competing with gestures.
   */
  fun removeProtectedAnimationOwner(owner: String)
}