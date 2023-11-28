package com.mapbox.maps.testapp.wallpaper

import android.content.Context
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.mapbox.bindgen.Value
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

    private var lightPreset: LightPresets = LightPresets.DAY
      set(value) {
        if (field != value) {
          mapboxMap.getStyle { style ->
            style.setStyleImportConfigProperty(
              IMPORT_ID_FOR_STANDARD_STYLE,
              "lightPreset",
              value.value
            )
          }
        }
        field = value
      }

    override fun onCreate(surfaceHolder: SurfaceHolder) {
      super.onCreate(surfaceHolder)
      surfaceHolder.addCallback(this)

      mapSurface = MapSurface(context, surfaceHolder.surface)
      mapboxMap = mapSurface.mapboxMap

      // Custom configuration
      mapboxMap.loadStyle(Style.STANDARD) { style ->
        style.setStyleImportConfigProperty(
          IMPORT_ID_FOR_STANDARD_STYLE,
          "showPlaceLabels",
          Value.valueOf(false)
        )
        style.setStyleImportConfigProperty(
          IMPORT_ID_FOR_STANDARD_STYLE,
          "showRoadLabels",
          Value.valueOf(false)
        )
        style.setStyleImportConfigProperty(
          IMPORT_ID_FOR_STANDARD_STYLE,
          "showPointInterestLabels",
          Value.valueOf(true)
        )
        style.setStyleImportConfigProperty(
          IMPORT_ID_FOR_STANDARD_STYLE,
          "showTransitLabels",
          Value.valueOf(false)
        )
      }
      mapboxMap.setCamera(
        CameraOptions.Builder()
          .center(TARGET)
          .zoom(ZOOM_START)
          .pitch(PITCH_START)
          .bearing(BEARING_START)
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
      val zoom = ZOOM_START + xOffset * (ZOOM_END - ZOOM_START)
      val pitch = PITCH_START + xOffset * (PITCH_END - PITCH_START)
      val bearing = BEARING_START + xOffset * (BEARING_END - BEARING_START)
      mapboxMap.setCamera(
        CameraOptions.Builder()
          .center(TARGET)
          .zoom(zoom)
          .pitch(pitch)
          .bearing(bearing)
          .build()
      )
      lightPreset = when (xOffset) {
        in 0f..0.25f -> LightPresets.DAY
        in 0.25f..0.5f -> LightPresets.DAWN
        in 0.5f..0.75f -> LightPresets.DUSK
        else -> LightPresets.NIGHT
      }
    }
  }

  private enum class LightPresets(val value: Value) {
    DAY(Value.valueOf("day")),
    DAWN(Value.valueOf("dawn")),
    DUSK(Value.valueOf("dusk")),
    NIGHT(Value.valueOf("night"))
  }

  companion object {
    private const val TAG = "MapWallpaper"
    private const val ZOOM_START = 15.5
    private const val PITCH_START = 40.0
    private const val BEARING_START = 22.5
    private val TARGET: Point = Point.fromLngLat(2.294694, 48.858093)

    private const val ZOOM_END = 16.0
    private const val PITCH_END = 60.0
    private const val BEARING_END = 135.0

    private const val IMPORT_ID_FOR_STANDARD_STYLE = "basemap"
  }
}