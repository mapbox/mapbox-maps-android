package com.mapbox.maps.testapp.auto.lib

import android.graphics.Rect
import android.util.Log
import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.SurfaceCallback
import androidx.car.app.SurfaceContainer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mapbox.common.Logger
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.ScreenCoordinate

/**
 * MapSurface ready callback, will be called when the MapSurface is created successfully.
 */
fun interface MapSurfaceReadyCallback {
  /**
   * Map surface is ready.
   */
  fun onMapSurfaceReady(mapSurface: MapSurface)
}

/**
 * Fired when there's a map scroll event.
 */
fun interface OnMapScrollListener {
  /**
   * Map scroll event fired.
   */
  fun onMapScroll()
}

/**
 * Fired when there's a map scale event.
 */
fun interface OnMapScaleListener {
  /**
   * Map scroll event fired.
   */
  fun onMapScale()
}

/**
 * Utilities to help initialise Mapbox MapSurface and handle Android Auto Gestures.
 */
object MapboxCarUtils {
  /**
   * Init Mapbox Map surface for the car app.
   *
   * @param carContext
   * @param lifeCycle
   * @param mapInitOptions
   * @param scrollListener
   * @param scaleListener
   * @param mapSurfaceReadyCallback
   */
  fun initMapSurface(
    carContext: CarContext,
    lifeCycle: Lifecycle,
    mapInitOptions: MapInitOptions = MapInitOptions(carContext),
    scrollListener: OnMapScrollListener? = null,
    scaleListener: OnMapScaleListener? = null,
    mapSurfaceReadyCallback: MapSurfaceReadyCallback
  ) {
    var mapSurface: MapSurface? = null
    val surfaceCallback: SurfaceCallback = object : SurfaceCallback {
      override fun onSurfaceAvailable(surfaceContainer: SurfaceContainer) {
        synchronized(this) {
          Log.i(TAG, "Surface available $surfaceContainer")
          surfaceContainer.surface?.let { surface ->
            mapSurface = MapSurface(carContext, surface, mapInitOptions).also { surface ->
              surface.surfaceCreated()
              surface.surfaceChanged(surfaceContainer.width, surfaceContainer.height)
              mapSurfaceReadyCallback.onMapSurfaceReady(surface)
            }
          }
        }
      }

      override fun onVisibleAreaChanged(visibleArea: Rect) {
        synchronized(this) {
          Log.i(
            TAG,
            "Visible area changed $visibleArea"
          )
        }
      }

      override fun onStableAreaChanged(stableArea: Rect) {
        synchronized(this) {
          Log.i(
            TAG,
            "Stable area changed $stableArea"
          )
        }
      }

      override fun onSurfaceDestroyed(surfaceContainer: SurfaceContainer) {
        synchronized(this) {
          Log.i(TAG, "Surface destroyed")
          mapSurface?.surfaceDestroyed()
        }
      }

      override fun onScroll(distanceX: Float, distanceY: Float) {
        Logger.e(TAG, "onScroll $distanceX, $distanceY")
        synchronized(this) {
          mapSurface?.onScroll(distanceX, distanceY)
          scrollListener?.onMapScroll()
        }
      }

      override fun onScale(focusX: Float, focusY: Float, scaleFactor: Float) {
        Logger.e(TAG, "onScroll $focusX, $focusY, $scaleFactor")
        synchronized(this) {
          mapSurface?.onScale(focusX, focusY, scaleFactor)
          scaleListener?.onMapScale()
        }
      }
    }

    lifeCycle.addObserver(
      object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
          Log.i(TAG, "SurfaceRenderer created")
          carContext.getCarService(AppManager::class.java)
            .setSurfaceCallback(surfaceCallback)
        }

        override fun onStart(owner: LifecycleOwner) {
          Log.i(TAG, "onStart")
          mapSurface?.onStart()
        }

        override fun onStop(owner: LifecycleOwner) {
          Log.i(TAG, "onStop")
          mapSurface?.onStop()
        }

        override fun onDestroy(owner: LifecycleOwner) {
          Log.i(TAG, "onDestroy")
          mapSurface?.onDestroy()
        }
      }
    )
  }

  fun MapSurface.onScroll(distanceX: Float, distanceY: Float) {
    val centerScreen = ScreenCoordinate(0.0, 0.0)
    getMapboxMap().apply {
      dragStart(centerScreen)
      val newCamera = getMapboxMap().getDragCameraOptions(
        centerScreen,
        ScreenCoordinate(
          centerScreen.x - distanceX,
          centerScreen.y - distanceY
        )
      )
      setCamera(newCamera)
      dragEnd()
    }
  }

  fun MapSurface.onScale(focusX: Float, focusY: Float, scaleFactor: Float) {
    Logger.e(TAG, "handleScale $focusX, $focusY. $scaleFactor")
    synchronized(this) {
      val cameraState = getMapboxMap().cameraState
      Logger.e(TAG, "setting zoom ${cameraState.zoom * scaleFactor}")
      val newZoom = cameraState.zoom * scaleFactor
      val cameraOptionsBuilder = CameraOptions.Builder().zoom(newZoom)
      if (focusX >= 0 && focusY >= 0) {
        cameraOptionsBuilder.anchor(
          ScreenCoordinate(
            focusX.toDouble(),
            focusY.toDouble()
          )
        )
      }
      getMapboxMap().setCamera(cameraOptionsBuilder.build())
    }
  }

  private const val TAG = "MapboxCarMap"
}