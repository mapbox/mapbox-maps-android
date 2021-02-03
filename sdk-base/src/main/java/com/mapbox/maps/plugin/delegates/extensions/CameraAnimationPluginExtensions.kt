package com.mapbox.maps.plugin.delegates.extensions

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.MapAnimationOptions

/**
 * Extension easeTo() for [MapPluginExtensionsDelegate]
 * Ease the map camera to a given camera options and animation options.
 *
 * @param cameraOptions The camera options to ease to
 * @param animationOptions Transition options (animation duration, listeners etc)
 */
fun MapPluginExtensionsDelegate.easeTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null
) = getCameraAnimationPlugin()?.easeTo(cameraOptions, animationOptions)

/**
 * Extension flyTo() function for [MapPluginExtensionsDelegate]
 * Fly the map camera to a given camera options.
 *
 * @param cameraOptions The camera options to fly to
 * @param animationOptions Transition options (animation duration, listeners etc)
 */
fun MapPluginExtensionsDelegate.flyTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null
) = getCameraAnimationPlugin()?.flyTo(cameraOptions, animationOptions)

/**
 * Extension pitchBy() function for [MapPluginExtensionsDelegate]
 * Pitch the map by with optional animation.
 *
 * @param pitch The amount to pitch by
 * @param animationOptions Transition options (animation duration, listeners etc)
 */
fun MapPluginExtensionsDelegate.pitchBy(
  pitch: Double,
  animationOptions: MapAnimationOptions? = null
) = getCameraAnimationPlugin()?.pitchBy(pitch, animationOptions)

/**
 * Extension scaleBy() function for [MapPluginExtensionsDelegate]
 * Scale the map by with optional animation.
 *
 * @param amount The amount to scale by
 * @param screenCoordinate The optional focal point to scale on
 * @param animationOptions Transition options (animation duration, listeners etc)
 */
fun MapPluginExtensionsDelegate.scaleBy(
  amount: Double,
  screenCoordinate: ScreenCoordinate?,
  animationOptions: MapAnimationOptions? = null
) = getCameraAnimationPlugin()?.scaleBy(amount, screenCoordinate, animationOptions)

/**
 * Extension moveBy() function for [MapPluginExtensionsDelegate]
 * Move the map by a given screen coordinate with optional animation.
 *
 * @param screenCoordinate The screen coordinate distance to move by
 * @param animationOptions Transition options (animation duration, listeners etc)
 */
fun MapPluginExtensionsDelegate.moveBy(
  screenCoordinate: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null
) = getCameraAnimationPlugin()?.moveBy(screenCoordinate, animationOptions)

/**
 * Extension rotateBy() function for [MapPluginExtensionsDelegate]
 * Rotate the map by with optional animation.
 *
 * @param first The first pointer to rotate on
 * @param second The second pointer to rotate on
 * @param animationOptions Transition options (animation duration, listeners etc)
 */
fun MapPluginExtensionsDelegate.rotateBy(
  first: ScreenCoordinate,
  second: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null
) = getCameraAnimationPlugin()?.rotateBy(first, second, animationOptions)