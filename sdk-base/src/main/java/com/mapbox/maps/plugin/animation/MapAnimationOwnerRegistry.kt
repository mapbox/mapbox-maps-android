package com.mapbox.maps.plugin.animation

/**
 * Registry for all plugins using Camera Animation system.
 * This means that all animations controlled by any plugin will create animators with their owner.
 */
object MapAnimationOwnerRegistry {
  /**
   * Internal animation system owner.
   */
  const val INTERNAL = "Maps-CameraInternal"
  /**
   * Gestures animator owner.
   */
  const val GESTURES = "Maps-Gestures"
  /**
   * Compass animator owner.
   */
  const val COMPASS = "Maps-Compass"
  /**
   * Location animator owner.
   */
  const val LOCATION = "Maps-Location"
}