package com.mapbox.maps.plugin.delegates

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.*

/**
 * Definition of a camera delegate. Any invocation will interact with the map's actual camera.
 */
interface MapCameraManagerDelegate {

  /**
   * Represents current camera state.
   */
  val cameraState: CameraState

  /**
   * Convenience method that returns the camera options object for given arguments
   *
   * @param bounds The coordinate bounds of the map
   * @param padding The edge padding of the map
   * @param bearing The bearing of the map
   * @param pitch The pitch of the map
   *
   * @return Returns the camera options object representing the provided params
   */
  fun cameraForCoordinateBounds(
    bounds: CoordinateBounds,
    padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0),
    bearing: Double? = null,
    pitch: Double? = null
  ): CameraOptions

  /**
   * Convenience method that returns the camera options object for given arguments
   *
   * @param coordinates The coordinates representing the bounds of the map
   * @param padding The edge padding of the map
   * @param bearing The bearing of the map
   * @param pitch The pitch of the map
   *
   * @return Returns the camera options object representing the provided params
   */
  fun cameraForCoordinates(
    coordinates: List<Point>,
    padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0),
    bearing: Double? = null,
    pitch: Double? = null
  ): CameraOptions

  /**
   * Convenience method that adjusts the provided camera options object for given arguments
   *
   * Returns the provided \p camera with zoom adjusted to fit \p coordinates into \p box, so that coordinates on the left,
   * top and right of the effective camera center at the principal point of the projection (defined by padding) fit into \p box.
   * Returns the provided camera options object unchanged upon error.
   * Note that this method may fail if the principal point of the projection is not inside \p box or
   * if there is no sufficient screen space, defined by principal point and box, to fit the geometry.
   *
   * @param coordinates The coordinates representing the bounds of the map
   * @param box The box into which \p coordinates should fit
   * @param camera The camera for which zoom should be adjusted. Note that \p camera.center is required.
   *
   * @return Returns the camera options object with the zoom level adjusted to fit \p coordinates into \p box.
   */
  fun cameraForCoordinates(
    coordinates: List<Point>,
    camera: CameraOptions,
    box: ScreenBox
  ): CameraOptions

  /**
   * Convenience method that returns the camera options object for given arguments
   *
   * @param geometry The geometry of the map
   * @param padding The edge padding of the map
   * @param bearing The bearing of the map
   * @param pitch The pitch of the map
   *
   * @return Returns the camera options object representing the provided params
   */
  fun cameraForGeometry(
    geometry: Geometry,
    padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0),
    bearing: Double? = null,
    pitch: Double? = null
  ): CameraOptions

  /**
   * Returns the coordinate bounds for a given camera.
   *
   * @param camera The camera information to use
   *
   * @return Returns the CoordinateBounds object representing the provided params
   *
   * Note that if the given camera shows the antimeridian, the returned wrapped bounds
   * might not represent the minimum bounding box.
   */
  fun coordinateBoundsForCamera(camera: CameraOptions): CoordinateBounds

  /**
   * Returns the coordinate bounds and zoom for a given camera.
   *
   * @param camera The camera information to use
   *
   * @return Returns the CoordinateBoundsZoom object representing the provided params
   *
   * Note that if the given camera shows the antimeridian, the returned wrapped bounds
   * might not represent the minimum bounding box.
   * @sa coordinateBoundsZoomForCameraUnwrapped()
   */
  fun coordinateBoundsZoomForCamera(camera: CameraOptions): CoordinateBoundsZoom

  /**
   * Returns the unwrapped coordinate bounds and zoom for a given camera.
   *
   * @param camera The camera information to use
   *
   * @return Returns the CoordinateBoundsZoom object representing the provided params
   *
   * This method is particularly useful, if the camera shows the antimeridian.
   */
  fun coordinateBoundsZoomForCameraUnwrapped(camera: CameraOptions): CoordinateBoundsZoom

  /**
   * Calculates a screen coordinate that corresponds to a geographical coordinate
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinate is in \link MapOptions#size platform pixels \endlink relative to the top left
   * of the map (not of the whole screen).
   *
   * @param coordinate A geographical coordinate on the map to convert to a screen coordinate.
   *
   * @return Returns a screen coordinate on the screen in \link MapOptions#size platform pixels \endlink.
   */
  fun pixelForCoordinate(coordinate: Point): ScreenCoordinate

  /**
   * Calculates a geographical coordinate(i.e., longitude-latitude pair) that corresponds
   * to a screen coordinate.
   *
   * The screen coordinate is in \link MapOptions#size platform pixels \endlink relative to the top left
   * of the map (not of the whole screen).
   *
   * @param pixel A screen coordinate on the screen in \link MapOptions#size platform pixels \endlink.
   *
   * @return Returns a geographical coordinate corresponding to the ScreenCoordinate
   * on the screen.
   */
  fun coordinateForPixel(pixel: ScreenCoordinate): Point

  /**
   * Calculates screen coordinates that corresponds to geographical coordinates
   * (i.e., longitude-latitude pair).
   *
   * The screen coordinates are in \link MapOptions#size platform pixels \endlink relative to the top left
   * of the map (not of the whole screen).
   *
   * @param coordinates A batch of geographical coordinates on the map to convert to screen coordinates.
   *
   * @return Returns a batch of screen coordinates on the screen in \link MapOptions#size platform pixels \endlink.
   */
  fun pixelsForCoordinates(coordinates: List<Point>): List<ScreenCoordinate>

  /**
   * Calculates geographical coordinates(i.e., longitude-latitude pair) that corresponds
   * to screen coordinates.
   *
   * The screen coordinates are in \link MapOptions#size platform pixels \endlink relative to the top left
   * of the map (not of the whole screen).
   *
   * @param pixels A batch of screen coordinates on the screen in \link MapOptions#size platform pixels \endlink.
   *
   * @return Returns a batch of geographical coordinates corresponding to the screen coordinates
   * on the screen.
   */
  fun coordinatesForPixels(pixels: List<ScreenCoordinate>): List<Point>

  /**
   * Changes the map view by any combination of center, zoom, bearing, and pitch, without an animated transition.
   * The map will retain its current values for any details not passed via the camera options argument.
   * It is not guaranteed that the provided CameraOptions will be set, the map may apply constraints resulting in a
   * different CameraState.
   *
   * @param cameraOptions New camera options
   */
  fun setCamera(cameraOptions: CameraOptions)

  /**
   * Sets the map view with the free camera options.
   *
   * FreeCameraOptions provides more direct access to the underlying camera entity.
   * For backwards compatibility the state set using this API must be representable with
   * `CameraOptions` as well. Parameters are clamped to a valid range or discarded as invalid
   * if the conversion to the pitch and bearing presentation is ambiguous. For example orientation
   * can be invalid if it leads to the camera being upside down or the quaternion has zero length.
   *
   * @param freeCameraOptions The free camera options to set.
   */
  fun setCamera(freeCameraOptions: FreeCameraOptions)

  /**
   * Gets the map's current free camera options. After mutation, it should be set back to the map.
   * @return The current free camera options.
   */
  fun getFreeCameraOptions(): FreeCameraOptions

  /**
   * Sets the bounds of the map.
   *
   * @param options
   */
  fun setBounds(options: CameraBoundsOptions): Expected<String, None>

  /**
   * Returns the bounds of the map.
   */
  fun getBounds(): CameraBounds

  /**
   * Prepares the drag gesture to use the provided screen coordinate as a pivot point. This function should be called each time when user starts a dragging action (e.g. by clicking on the map). The following dragging will be relative to the pivot.
   *
   * @param point The pivot coordinate, measured in \link MapOptions#size platform pixels \endlink from top to bottom and from left to right.
   */
  fun dragStart(point: ScreenCoordinate)

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
    toPoint: ScreenCoordinate
  ): CameraOptions

  /**
   * Ends the ongoing drag gesture.
   * This function should be called always after the user has ended a drag gesture initiated by `dragStart`.
   */
  fun dragEnd()
}