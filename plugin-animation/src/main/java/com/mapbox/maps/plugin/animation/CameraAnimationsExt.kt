@file:JvmName("CameraAnimationsUtils")
package com.mapbox.maps.plugin.animation

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_CAMERA_PLUGIN_ID
import com.mapbox.maps.plugin.delegates.MapPluginExtensionsDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Camera Animations plugin instance.
 */
val MapPluginProviderDelegate.camera: CameraAnimationsPlugin
  @JvmName("getCamera")
  get() = this.getPlugin(MAPBOX_CAMERA_PLUGIN_ID)!!

/**
 * Extension easeTo() for [MapPluginExtensionsDelegate]
 * Ease the map camera to a given camera options and animation options.
 *
 * @param cameraOptions The camera options to ease to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.easeTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { easeTo(cameraOptions, animationOptions) } as Cancelable

/**
 * Extension flyTo() function for [MapPluginExtensionsDelegate]
 * Fly the map camera to a given camera options.
 *
 * @param cameraOptions The camera options to fly to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.flyTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { flyTo(cameraOptions, animationOptions) } as Cancelable

/**
 * Extension pitchBy() function for [MapPluginExtensionsDelegate]
 * Pitch the map by with optional animation.
 *
 * @param pitch The amount to pitch by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.pitchBy(
  pitch: Double,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { pitchBy(pitch, animationOptions) } as Cancelable

/**
 * Extension scaleBy() function for [MapPluginExtensionsDelegate]
 * Scale the map by with optional animation.
 *
 * @param amount The amount to scale by
 * @param screenCoordinate The optional focal point to scale on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.scaleBy(
  amount: Double,
  screenCoordinate: ScreenCoordinate?,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { scaleBy(amount, screenCoordinate, animationOptions) } as Cancelable

/**
 * Extension moveBy() function for [MapPluginExtensionsDelegate]
 * Move the map by a given screen coordinate with optional animation.
 *
 * @param screenCoordinate The screen coordinate distance to move by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.moveBy(
  screenCoordinate: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { moveBy(screenCoordinate, animationOptions) } as Cancelable

/**
 * Extension rotateBy() function for [MapPluginExtensionsDelegate]
 * Rotate the map by with optional animation.
 *
 * @param first The first pointer to rotate on
 * @param second The second pointer to rotate on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.rotateBy(
  first: ScreenCoordinate,
  second: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { rotateBy(first, second, animationOptions) } as Cancelable