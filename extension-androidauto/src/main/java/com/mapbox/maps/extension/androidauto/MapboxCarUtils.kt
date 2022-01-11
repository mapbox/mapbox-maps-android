package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import androidx.car.app.AppManager
import androidx.car.app.Session
import androidx.car.app.SurfaceCallback
import androidx.car.app.SurfaceContainer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mapbox.common.Logger
import com.mapbox.maps.*

/**
 * MapSurface ready callback, will be called when the MapSurface is created successfully.
 */
@MapboxExperimental
fun interface MapSurfaceReadyCallback {
  /**
   * Map surface is ready.
   */
  fun onMapSurfaceReady(mapSurface: MapSurface)
}

/**
 * Fired when there's a map scroll event.
 */
@MapboxExperimental
fun interface OnMapScrollListener {
  /**
   * Map scroll event fired.
   */
  fun onMapScroll()
}

/**
 * Fired when there's a map scale event.
 */
@MapboxExperimental
fun interface OnMapScaleListener {
  /**
   * Map scroll event fired.
   */
  fun onMapScale()
}

/**
 * Init Mapbox Map surface for the car app.
 *
 * @param mapInitOptions options to initialise a MapboxMap
 * @param scrollListener
 * @param scaleListener
 * @param mapSurfaceReadyCallback
 */
@MapboxExperimental
@JvmOverloads
fun Session.initMapSurface(
  mapInitOptions: MapInitOptions = MapInitOptions(carContext),
  scrollListener: OnMapScrollListener? = null,
  scaleListener: OnMapScaleListener? = null,
  callback: SurfaceCallback? = null,
  mapSurfaceReadyCallback: MapSurfaceReadyCallback
) {
  var mapSurface: MapSurface? = null
  val surfaceCallback: SurfaceCallback = object : SurfaceCallback {
    override fun onSurfaceAvailable(surfaceContainer: SurfaceContainer) {
      callback?.onSurfaceAvailable(surfaceContainer)
      synchronized(this) {
        Logger.i(TAG, "Surface available $surfaceContainer")
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
        Logger.i(
          TAG,
          "Visible area changed $visibleArea"
        )
      }
    }

    override fun onStableAreaChanged(stableArea: Rect) {
      synchronized(this) {
        Logger.i(
          TAG,
          "Stable area changed $stableArea"
        )
      }
    }

    override fun onSurfaceDestroyed(surfaceContainer: SurfaceContainer) {
      synchronized(this) {
        Logger.i(TAG, "Surface destroyed")
        mapSurface?.surfaceDestroyed()
      }
    }

    override fun onScroll(distanceX: Float, distanceY: Float) {
      Logger.d(TAG, "onScroll $distanceX, $distanceY")
      synchronized(this) {
        mapSurface?.onScroll(distanceX, distanceY)
        scrollListener?.onMapScroll()
      }
    }

    override fun onScale(focusX: Float, focusY: Float, scaleFactor: Float) {
      Logger.d(TAG, "onScroll $focusX, $focusY, $scaleFactor")
      synchronized(this) {
        mapSurface?.onScale(focusX, focusY, scaleFactor)
        scaleListener?.onMapScale()
      }
    }
  }

  lifecycle.addObserver(
    object : DefaultLifecycleObserver {
      override fun onCreate(owner: LifecycleOwner) {
        synchronized(this) {
          carContext.getCarService(AppManager::class.java)
            .setSurfaceCallback(surfaceCallback)
        }
      }

      override fun onStart(owner: LifecycleOwner) {
        synchronized(this) {
          mapSurface?.onStart()
        }
      }

      override fun onStop(owner: LifecycleOwner) {
        synchronized(this) {
          mapSurface?.onStop()
        }
      }

      override fun onDestroy(owner: LifecycleOwner) {
        synchronized(this) {
          mapSurface?.onDestroy()
        }
      }
    }
  )
}

private fun MapSurface.onScroll(distanceX: Float, distanceY: Float) {
  synchronized(this) {
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
}

private fun MapSurface.onScale(focusX: Float, focusY: Float, scaleFactor: Float) {
  synchronized(this) {
    val cameraState = getMapboxMap().cameraState
    Logger.i(TAG, "setting zoom ${cameraState.zoom * scaleFactor}")
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