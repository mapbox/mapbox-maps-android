@file:JvmName("CameraAnimationsUtils")
package com.mapbox.maps.plugin.animation

import android.animation.Animator
import com.mapbox.common.Cancelable
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_CAMERA_PLUGIN_ID
import com.mapbox.maps.plugin.delegates.MapPluginExtensionsDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Camera Animations plugin instance.
 */
val MapPluginProviderDelegate.camera: CameraAnimationsPlugin
  @JvmName("getCamera")
  get() = this.getPlugin(MAPBOX_CAMERA_PLUGIN_ID)!!

private val emptyCancelable = Cancelable { }

/**
 * Extension easeTo() for [MapPluginExtensionsDelegate]
 * Ease the map camera to a given camera options and animation options
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param cameraOptions The camera options to ease to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.easeTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { easeTo(cameraOptions, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension flyTo() function for [MapPluginExtensionsDelegate]
 * Fly the map camera to a given camera options.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param cameraOptions The camera options to fly to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.flyTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { flyTo(cameraOptions, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension pitchBy() function for [MapPluginExtensionsDelegate]
 * Pitch the map by with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param pitch The amount to pitch by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.pitchBy(
  pitch: Double,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { pitchBy(pitch, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension scaleBy() function for [MapPluginExtensionsDelegate]
 * Scale the map by with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param amount The amount to scale by
 * @param screenCoordinate The optional focal point to scale on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.scaleBy(
  amount: Double,
  screenCoordinate: ScreenCoordinate?,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { scaleBy(amount, screenCoordinate, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension moveBy() function for [MapPluginExtensionsDelegate]
 * Move the map by a given screen coordinate with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param screenCoordinate The screen coordinate distance to move by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.moveBy(
  screenCoordinate: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { moveBy(screenCoordinate, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension rotateBy() function for [MapPluginExtensionsDelegate]
 * Rotate the map by with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param first The first pointer to rotate on
 * @param second The second pointer to rotate on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.rotateBy(
  first: ScreenCoordinate,
  second: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { rotateBy(first, second, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable