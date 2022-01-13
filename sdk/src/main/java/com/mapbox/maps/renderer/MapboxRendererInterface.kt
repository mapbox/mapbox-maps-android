package com.mapbox.maps.renderer

import android.graphics.Bitmap
import com.mapbox.maps.MapClient
import com.mapbox.maps.MapInterface
import com.mapbox.maps.MapView

internal interface MapboxRendererInterface {

  fun onStart()

  fun onStop()

  fun onDestroy()

  fun queueRenderEvent(runnable: Runnable)

  fun queueEvent(runnable: Runnable)

  fun snapshot(): Bitmap?

  fun snapshot(listener: MapView.OnSnapshotReady)

  fun setMaximumFps(fps: Int)

  fun setOnFpsChangedListener(listener: OnFpsChangedListener)
}