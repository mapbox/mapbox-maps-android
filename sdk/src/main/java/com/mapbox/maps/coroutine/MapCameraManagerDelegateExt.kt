package com.mapbox.maps.coroutine

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
 * @return the [CameraOptions] object representing the provided parameters. Empty [CameraOptions] (see [CameraOptions.isEmpty]) could be returned only if an internal error occurred.
 */
@JvmSynthetic
suspend fun MapCameraManagerDelegate.awaitCameraForCoordinates(
  coordinates: List<Point>,
  camera: CameraOptions,
  coordinatesPadding: EdgeInsets? = null,
  maxZoom: Double? = null,
  offset: ScreenCoordinate? = null,
): CameraOptions = suspendCoroutine { continuation ->
  cameraForCoordinates(
    coordinates = coordinates,
    camera = camera,
    coordinatesPadding = coordinatesPadding,
    maxZoom = maxZoom,
    offset = offset,
    result = continuation::resume
  )
}