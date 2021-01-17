package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.*
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_surface.*

/**
 * Example integration with MapSurface through using SurfaceView directly.
 */
class SurfaceActivity : AppCompatActivity(), SurfaceHolder.Callback {

  private lateinit var surfaceHolder: SurfaceHolder
  private lateinit var mapSurface: MapSurface

  @SuppressLint("ClickableViewAccessibility")
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_surface)

    // Setup map surface
    surfaceHolder = surface.holder
    surfaceHolder.addCallback(this)
    mapSurface = MapSurface(
      this,
      surfaceHolder.surface,
      SurfaceObserver()
    )

    // Load a map style
    mapSurface.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

    // Touch handling (verify plugin integration)
    surface.setOnTouchListener { _, event -> mapSurface.onTouchEvent(event) }
    surface.setOnGenericMotionListener { _, event -> mapSurface.onGenericMotionEvent(event) }
  }

  override fun surfaceCreated(holder: SurfaceHolder?) {
    mapSurface.surfaceCreated()
  }

  override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    mapSurface.surfaceChanged(width, height)
  }

  override fun surfaceDestroyed(holder: SurfaceHolder?) {
    mapSurface.surfaceDestroyed()
  }

  override fun onStart() {
    super.onStart()
    mapSurface.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapSurface.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapSurface.onDestroy()
  }

  inner class SurfaceObserver : MapObserver() {
    override fun onCameraChange(change: CameraChange, mode: CameraChangeMode) {
      Log.i(TAG, "onMapChanged: $change: $mode")
    }

    override fun onMapChanged(change: MapChange) {
      Log.i(TAG, "onMapChanged: $change")
    }

    override fun onMapLoadError(error: MapLoadError, message: String) {
      Log.e(TAG, "OnMapLoadError: $error: $message")
    }

    override fun onDidFinishRenderingFrame(status: RenderFrameStatus) {
      Log.i(TAG, "onDidFinishRenderingFrame: $status")
    }

    override fun onDidFinishRenderingMap(mode: RenderMode) {
      Log.i(TAG, "onDidFinishRenderingMap: $mode")
    }

    override fun onSourceChanged(sourceId: String) {
      Log.i(TAG, "onSourceChanged: $sourceId")
    }

    override fun onStyleImageMissing(imageId: String) {
      Log.i(TAG, "onStyleImageMissing: $imageId")
    }

    override fun onCanRemoveUnusedStyleImage(imageId: String): Boolean {
      Log.i(TAG, "onCanRemoveUnusedStyleImage: $imageId")
      return false
    }
  }

  companion object {
    const val TAG = "SurfaceActivity"
  }
}