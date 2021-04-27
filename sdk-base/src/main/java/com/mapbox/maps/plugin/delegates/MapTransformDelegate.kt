package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.*

/**
 * Definition of a map transform delegate.
 */
interface MapTransformDelegate {
  /**
   * Notify map about gesture being in progress.
   *
   * @param inProgress True if gesture is in progress
   */
  fun setGestureInProgress(inProgress: Boolean)

  /**
   * Returns if a gesture is in progress.
   *
   * @return Returns if a gesture is in progress
   */
  fun isGestureInProgress(): Boolean

  /**
   * Set the map north orientation
   *
   * @param northOrientation The map north orientation to set
   */
  fun setNorthOrientation(northOrientation: NorthOrientation)

  /**
   * Set the map constrain mode
   *
   * @param constrainMode The map constraint mode to set
   */
  fun setConstrainMode(constrainMode: ConstrainMode)

  /**
   * Set the map viewport mode
   *
   * @param viewportMode The map viewport mode to set
   */
  fun setViewportMode(viewportMode: ViewportMode)

  /**
   * Tells the map rendering engine that the animation is currently performed by the
   * user (e.g. with a `setCamera()` calls series). It adjusts the engine for the animation use case.
   * In particular, it brings more stability to symbol placement and rendering.
   *
   * @param inProgress Bool representing if user animation is in progress
   */
  fun setUserAnimationInProgress(inProgress: Boolean)

  /**
   * Returns if user animation is currently in progress.
   *
   * @return Return true if a user animation is in progress.
   */
  fun isUserAnimationInProgress(): Boolean

  /**
   * Get map options.
   *
   * @return Returns map options
   */
  fun getMapOptions(): MapOptions

  /**
   * Get minimum zoom.
   *
   * @return Zoom value.
   */
  fun getMinZoom(): Double

  /**
   * Get maximum zoom.
   *
   * @return Zoom value.
   */
  fun getMaxZoom(): Double

  /**
   * The current zoom factor applied on the map meaning 2 ^ Current zoom
   *
   * @return Scale value.
   */
  fun getScale(): Double

  /**
   * Gets the size of the map.
   *
   * @return size The size of the map in MapOptions#size platform pixels
   */
  fun getSize(): Size
}