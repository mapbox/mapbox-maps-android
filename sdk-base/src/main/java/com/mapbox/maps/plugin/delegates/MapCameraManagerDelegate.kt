package com.mapbox.maps.plugin.delegates

import android.app.Activity
import android.graphics.RectF
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
   * Convenience method that returns the [CameraOptions] object for given parameters.
   *
   * In order for this method to produce correct results `MapView` must be already
   * measured and inflated to have correct width and height values.
   * Calling this method in [Activity.onCreate] will lead to incorrect results.
   *
   * @param bounds The [CoordinateBounds] of the camera.
   * @param boundsPadding The amount of padding in [EdgeInsets] to add to the given bounds.
   * @param bearing The bearing of the camera.
   * @param pitch The pitch of the camera.
   * @param maxZoom The maximum zoom level allowed in the returned camera options.
   * @param offset The center of the given bounds relative to map center in pixels.
   *
   * @return The [CameraOptions] object representing the provided parameters.
   */
  fun cameraForCoordinateBounds(
    bounds: CoordinateBounds,
    boundsPadding: EdgeInsets? = null,
    bearing: Double? = null,
    pitch: Double? = null,
    maxZoom: Double? = null,
    offset: ScreenCoordinate? = null,
  ): CameraOptions

  /**
   * Convenience method that returns the [CameraOptions] object for given parameters.
   * This method takes into account the current map padding in addition to the padding provided in parameters.
   *
   * @param coordinates The `coordinates` representing the bounds of the camera.
   * @param coordinatesPadding The amount of padding in pixels to add to the given `coordinates`.
   * @param bearing The bearing of the camera.
   * @param pitch The pitch of the camera.
   *
   * @return The [CameraOptions] object representing the provided parameters. Padding is absent in the returned [CameraOptions] as the zoom level already accounts for the padding.
   */
  fun cameraForCoordinates(
    coordinates: List<Point>,
    coordinatesPadding: EdgeInsets? = null,
    bearing: Double? = null,
    pitch: Double? = null
  ): CameraOptions

  /**
   * Convenience method that adjusts the provided [CameraOptions] object for given parameters.
   *
   * Returns the provided `camera` options with zoom adjusted to fit `coordinates` into the `box`, so that `coordinates` on the left,
   * top, right, and bottom of the effective `camera` center at the principal point of the projection (defined by `padding`) fit into the `box`.
   * Returns the provided `camera` options object unchanged upon an error.
   *
   * The method fails if the principal point is positioned outside of the `box`
   * or if there is no sufficient screen space, defined by principal point and the `box`, to fit the geometry.
   * Additionally, in cases when the principal point is positioned exactly on one of the edges of the `box`,
   * any geometry point that spans further than that edge on the same axis cannot possibly be framed and is ignored for zoom level calculation purposes.
   *
   * This API isn't supported by Globe projection.
   *
   * @param coordinates The `coordinates` representing the bounds of the camera.
   * @param camera The [CameraOptions] for which zoom should be adjusted. Note that the `camera.center`, and `camera.zoom` (as fallback) is required.
   * @param box The `screen box` into which `coordinates` should fit.
   *
   * @return The [CameraOptions] object with the zoom level adjusted to fit `coordinates` into the `box`.
   */
  fun cameraForCoordinates(
    coordinates: List<Point>,
    camera: CameraOptions,
    box: ScreenBox
  ): CameraOptions

  /**
   * Convenience method that returns the [CameraOptions] object for given parameters.
   *
   * @param coordinates The `coordinates` representing the bounds of the camera.
   * @param camera The [CameraOptions] which will be applied before calculating the camera for the coordinates. If any of the fields in [CameraOptions] are not provided then the current value from the map for that field will be used.
   * @param coordinatesPadding The amount of padding in pixels to add to the given `coordinates`.
   *                           Note: This padding is not applied to the map but to the coordinates provided. If you want to apply padding to the map use param `camera`.
   * @param maxZoom The maximum zoom level allowed in the returned camera options.
   * @param offset The center of the given bounds relative to map center in pixels.
   *
   * @return The [CameraOptions] object representing the provided parameters. Padding is absent in the returned [CameraOptions] as the zoom level already accounts for the padding.
   */
  fun cameraForCoordinates(
    coordinates: List<Point>,
    camera: CameraOptions,
    coordinatesPadding: EdgeInsets?,
    maxZoom: Double?,
    offset: ScreenCoordinate?
  ): CameraOptions

  /**
   * Convenience method that returns the camera options object for given arguments
   *
   * This API isn't supported by Globe projection.
   *
   * @param geometry The geometry of the map
   * @param geometryPadding The edge padding of the map
   * @param bearing The bearing of the map
   * @param pitch The pitch of the map
   *
   * @return Returns the camera options object representing the provided params
   */
  fun cameraForGeometry(
    geometry: Geometry,
    geometryPadding: EdgeInsets? = null,
    bearing: Double? = null,
    pitch: Double? = null
  ): CameraOptions

  /**
   * Returns the [CoordinateBounds] for a given camera.
   *
   * Note that if the given `camera` shows the antimeridian, the returned wrapped [CoordinateBounds]
   * might not represent the minimum bounding box.
   *
   * This API isn't supported by Globe projection.
   *
   * @param camera The [CameraOptions] to use for calculating [CoordinateBounds].
   *
   * @return The [CoordinateBounds] object representing a given `camera`.
   *
   */
  fun coordinateBoundsForCamera(camera: CameraOptions): CoordinateBounds

  /**
   * Returns the [CoordinateBounds] for a given camera.
   *
   * This method is useful if the `camera` shows the antimeridian.
   *
   * This API isn't supported by Globe projection.
   *
   * @param camera The [CameraOptions] to use for calculating [CoordinateBounds].
   *
   * @return The [CoordinateBounds] object representing a given `camera`.
   */
  fun coordinateBoundsForCameraUnwrapped(camera: CameraOptions): CoordinateBounds

  /**
   * Returns the coordinate bounds and zoom for a given camera.
   *
   * This API isn't supported by Globe projection.
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
   * This API isn't supported by Globe projection.
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
   * This API isn't supported by Globe projection.
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
   * This API isn't supported by Globe projection.
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
   * This API isn't supported by Globe projection.
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
   * This API isn't supported by Globe projection.
   *
   * @param pixels A batch of screen coordinates on the screen in \link MapOptions#size platform pixels \endlink.
   *
   * @return Returns a batch of geographical coordinates corresponding to the screen coordinates
   * on the screen.
   */
  fun coordinatesForPixels(pixels: List<ScreenCoordinate>): List<Point>

  /**
   * Returns the [CoordinateBounds] for given [RectF] defined in screen points.
   *
   * The screen points are in `platform pixels` relative to the top left corner
   * of the map (not of the whole screen).
   *
   * This API isn't supported by Globe projection.
   *
   * @param rectF rectangle area defined in screen points.
   * @return [CoordinateBounds] representing given [RectF].
   * @throws [IllegalArgumentException] if [RectF] is empty
   */
  fun coordinateBoundsForRect(rectF: RectF): CoordinateBounds

  /**
   * Calculates the geographical coordinate information that corresponds to a given screen coordinate.
   *
   * The screen coordinate is in platform pixels, relative to the top left corner of the map (not the whole screen).
   *
   * The returned coordinate will be the closest position projected onto the map surface,
   * in case the screen coordinate does not intersect with the map surface.
   *
   * @param pixel The screen coordinate on the map, in platform pixels.
   *
   * @return A CoordinateInfo record containing information about the geographical coordinate corresponding to the given screen coordinate, including whether it is on the map surface.
   *
   */
  fun coordinateInfoForPixel(pixel: ScreenCoordinate): CoordinateInfo

  /**
   * Calculates the geographical coordinates information that corresponds to the given screen coordinates.
   *
   * The screen coordinates are in platform pixels, relative to the top left corner of the map (not the whole screen).
   *
   * The returned coordinate will be the closest position projected onto the map surface,
   * in case the screen coordinate does not intersect with the map surface.
   *
   * @param pixels The list of screen coordinates on the map, in platform pixels.
   *
   * @return The CoordinateInfo records containing information about the geographical coordinates corresponding to the given screen coordinates, including whether they are on the map surface.
   *
   */
  fun coordinatesInfoForPixels(pixels: List<ScreenCoordinate>): List<CoordinateInfo>

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
   * Sets the map `center altitude mode` that defines behavior of the center point
   * altitude for all subsequent camera manipulations.
   *
   * Note: any gesture changing the map camera will set [MapCenterAltitudeMode.TERRAIN]
   * when finished.
   */
  fun setCenterAltitudeMode(mode: MapCenterAltitudeMode)

  /**
   * Calculates a target point where the camera should move after dragging from
   * a screen coordinate `startCoordinate` to another coordinate `endCoordinate`.
   *
   * @param fromPoint The `screen coordinate` to drag the map from, measured in `platform pixels` from top to bottom and from left to right.
   * @param toPoint The `screen coordinate` to drag the map to, measured in `platform pixels` from top to bottom and from left to right.
   *
   * @return The [CameraOptions] object with the center variable set to the computed target location.
   */
  fun cameraForDrag(fromPoint: ScreenCoordinate, toPoint: ScreenCoordinate): CameraOptions
}