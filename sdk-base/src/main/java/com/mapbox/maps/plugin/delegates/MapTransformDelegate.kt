package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.*

/**
 * Definition of a map transform delegate.
 */
interface MapTransformDelegate {
  /**
   * Changes the map view by any combination of center, zoom, bearing, and pitch, without an animated transition.
   * The map will retain its current values for any details not passed via the camera options argument.
   *
   * @param cameraOptions New camera options
   */
  fun setCamera(cameraOptions: CameraOptions)

  /**
   * Get the current camera options given an optional padding.
   *
   * @param edgeInsets The optional padding
   */
  fun getCameraOptions(edgeInsets: EdgeInsets? = null): CameraOptions

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
   * Set the map bounds.
   *
   * @param boundOptions the map bound options
   */
  fun setBounds(boundOptions: BoundOptions)

  /**
   * Get the map bounds options.
   *
   * @return Returns the map bounds options
   */
  fun getBounds(): BoundOptions

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

  /**
   * Is terrain enabled for loaded style of the map.
   * @return True if terrain is enabled for given style and false otherwise.
   */
  fun terrainEnabled(): Boolean

  /**
   * Prepares the drag gesture to use the provided screen coordinate as a pivot point.
   * This function should be called each time when user starts a dragging action (e.g. by clicking on the map).
   * The following dragging will be relative to the pivot.
   *
   * @param point The pivot coordinate, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   */
  fun dragStart(point: ScreenCoordinate)

  /**
   * Ends the ongoing drag gesture.
   * This function should be called always after the user has ended a drag gesture initiated by `dragStart`.
   */
  fun dragEnd()

  /**
   * Drags the map from one screen point to another. The method should be called after `dragStart` and before `dragEnd`.
   *
   * @param fromPoint The point to drag the map from, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   * @param toPoint The point to drag the map to, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   * @param animation Optional animation
   */
  fun drag(
    fromPoint: ScreenCoordinate,
    toPoint: ScreenCoordinate,
    animation: AnimationOptions?
  )

  /**
   * Calculates target point where camera should move after drag. The method should be called after `dragStart` and before `dragEnd`.
   *
   * @param fromPoint The point to drag the map from, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   * @param toPoint The point to drag the map to, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   *
   * @return Returns the camera options object showing end point
   */
  fun getDragCameraOptions(
    fromPoint: ScreenCoordinate,
    toPoint: ScreenCoordinate,
  ): CameraOptions
}