package com.mapbox.maps.testapp.wallpaper

import android.content.Context
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.mapbox.geojson.Point
import com.mapbox.maps.*

/**
 * Example integration of rendering to a Surface.
 */
class MapWallpaper : WallpaperService() {

  override fun onCreateEngine(): Engine {
    return MapEngine(applicationContext)
  }

  inner class MapEngine(val context: Context) : Engine(), SurfaceHolder.Callback {

    private lateinit var mapSurface: MapSurface
    private lateinit var mapboxMap: MapboxMap

    override fun onCreate(surfaceHolder: SurfaceHolder) {
      super.onCreate(surfaceHolder)
      surfaceHolder.addCallback(this)

      mapSurface = MapSurface(context, surfaceHolder.surface)
      mapboxMap = mapSurface.getMapboxMap()

      // Custom configuration
      mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
      mapboxMap.setCamera(
        CameraOptions.Builder()
          .center(TARGET)
          .zoom(ZOOM_START)
          .build()
      )
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
      mapSurface.surfaceCreated()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
      mapSurface.surfaceChanged(width, height)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
      mapSurface.surfaceDestroyed()
    }

    override fun onDestroy() {
      super.onDestroy()
      Log.i(TAG, "onDestroy")
      mapSurface.onDestroy()
    }

    override fun onVisibilityChanged(visible: Boolean) {
      super.onVisibilityChanged(visible)
      Log.v(TAG, "OnVisibilityChanged $visible")
      if (visible) {
        mapSurface.onStart()
      } else {
        mapSurface.onStop()
      }
    }

    override fun onOffsetsChanged(
      xOffset: Float,
      yOffset: Float,
      xStep: Float,
      yStep: Float,
      xPixels: Int,
      yPixels: Int
    ) {
      super.onOffsetsChanged(xOffset, yOffset, xStep, yStep, xPixels, yPixels)
      val screenAmount = ((1 + xStep) / xStep).toInt()
      val zoom = ZOOM_START + (screenAmount * xOffset)
      val pitch = (PITCH_START * screenAmount * xOffset).toDouble()
      val bearing = BEARING_START * screenAmount * xOffset
      mapboxMap.setCamera(
        CameraOptions.Builder()
          .center(TARGET)
          .zoom(zoom)
          .pitch(pitch)
          .bearing(bearing)
          .build()
      )
    }
  }

  companion object {
    private const val TAG = "MapWallpaper"
    private const val ZOOM_START = 14.0
    private const val PITCH_START = 20
    private const val BEARING_START = 22.5
    private val TARGET: Point = Point.fromLngLat(-122.43277340089034, 37.77625943520696)
  }
}