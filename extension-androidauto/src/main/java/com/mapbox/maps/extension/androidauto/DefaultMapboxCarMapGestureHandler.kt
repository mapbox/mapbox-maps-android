package com.mapbox.maps.extension.androidauto

import androidx.car.app.SurfaceCallback
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.PlatformEventInfo
import com.mapbox.maps.PlatformEventType
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.util.CoreGesturesHandler

/**
 * This class contains the default map gestures. It Handles the gestures received from
 * [SurfaceCallback] and applies them to the [MapboxMap] camera. If you would like to customize
 * the map gestures, use [MapboxCarMap.setGestureHandler].
 */
open class DefaultMapboxCarMapGestureHandler : MapboxCarMapGestureHandler {
  private var coreGestureHandler: CoreGesturesHandler? = null

  /**
   * Dispatches the click to the [MapboxMap], so that map interactions registered with
   * [MapboxMap.addInteraction] receive it, e.g. a [com.mapbox.maps.ClickInteraction].
   *
   * @see [MapboxCarMapGestureHandler.onClick]
   * @see [SurfaceCallback.onClick] for instructions to enable.
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   * @param x the horizontal screen coordinate of the click in pixels
   * @param y the vertical screen coordinate of the click in pixels
   */
  @OptIn(MapboxExperimental::class)
  override fun onClick(
    mapboxCarMapSurface: MapboxCarMapSurface,
    x: Float,
    y: Float
  ) {
    logI(TAG, "click $x, $y")
    mapboxCarMapSurface.mapSurface.mapboxMap.dispatch(
      PlatformEventInfo(PlatformEventType.CLICK, ScreenCoordinate(x.toDouble(), y.toDouble()))
    )
  }

  /**
   * @see [MapboxCarMapGestureHandler.onScroll]
   * @see [SurfaceCallback.onScroll] for instructions to enable.
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   * @param distanceX the distance in pixels along the X axis
   * @param distanceY the distance in pixels along the Y axis
   */
  override fun onScroll(
    mapboxCarMapSurface: MapboxCarMapSurface,
    visibleCenter: ScreenCoordinate,
    distanceX: Float,
    distanceY: Float
  ) {
    with(mapboxCarMapSurface.mapSurface.mapboxMap) {
      notifyCoreGestureStarted()
      val toCoordinate = ScreenCoordinate(
        visibleCenter.x - distanceX,
        visibleCenter.y - distanceY
      )
      logI(TAG, "scroll from $visibleCenter to $toCoordinate")
      setCamera(cameraForDrag(visibleCenter, toCoordinate))
      coreGestureHandler?.notifyCoreTouchEnded()
    }
  }

  /**
   * @see [MapboxCarMapGestureHandler.onFling]
   * @see [SurfaceCallback.onFling]
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   * @param velocityX the velocity of this fling measured in pixels per second along the x axis
   * @param velocityY the velocity of this fling measured in pixels per second along the y axis
   */
  override fun onFling(
    mapboxCarMapSurface: MapboxCarMapSurface,
    velocityX: Float,
    velocityY: Float
  ) {
    logI(TAG, "fling $velocityX, $velocityY")
    // TODO implement fling
    // https://github.com/mapbox/mapbox-navigation-android-examples/issues/67
  }

  /**
   * @see [MapboxCarMapGestureHandler.onScale]
   * @see [SurfaceCallback.onScale]
   *
   * @param mapboxCarMapSurface loaded and ready car map surface
   * @param focusX x coordinate of the focal point in pixels. A negative value indicates that the focal point is unavailable.
   * @param focusY y coordinate of the focal point in pixels. A negative value indicates that the focal point is unavailable.
   * @param scaleFactor the scaling factor from the previous state to the current state during the scale event. This value is defined as (current state) / (previous state)
   */
  @Suppress("LongParameterList")
  override fun onScale(
    mapboxCarMapSurface: MapboxCarMapSurface,
    focusX: Float,
    focusY: Float,
    scaleFactor: Float
  ) {
    with(mapboxCarMapSurface.mapSurface) {
      val fromZoom = mapboxMap.cameraState.zoom
      val toZoom = fromZoom - (1.0 - scaleFactor.toDouble())
      val anchor = ScreenCoordinate(
        focusX.toDouble(),
        focusY.toDouble()
      )

      val cameraOptions = CameraOptions.Builder()
        .zoom(toZoom)
        .anchor(anchor)
        .build()

      logI(TAG, "scale with $focusX, $focusY $scaleFactor -> $fromZoom $toZoom")
      if (scaleFactor == DOUBLE_TAP_SCALE_FACTOR) {
        camera.easeTo(cameraOptions)
      } else {
        mapboxMap.setCamera(cameraOptions)
      }
    }
  }

  private fun MapboxMap.notifyCoreGestureStarted() {
    if (coreGestureHandler == null) {
      coreGestureHandler = CoreGesturesHandler(
        mapTransformDelegate = this,
        mapCameraManagerDelegate = this
      )
    }
    coreGestureHandler?.notifyCoreGestureStarted()
  }

  private companion object {
    private const val TAG = "DefaultMapboxCarMapGestureHandler"

    /**
     * This appears to be undocumented from android auto. But when running from the emulator,
     * you can double tap the screen and zoom in to reproduce this value.
     * It is a jarring experience if you do not easeTo the zoom.
     */
    private const val DOUBLE_TAP_SCALE_FACTOR = 2.0f
  }
}