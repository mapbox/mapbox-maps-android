package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.*
import com.mapbox.maps.testapp.databinding.ActivitySurfaceBinding

/**
 * Example integration with MapSurface through using SurfaceView directly.
 */
class SurfaceActivity : AppCompatActivity(), SurfaceHolder.Callback {

  private lateinit var surfaceHolder: SurfaceHolder
  private lateinit var mapSurface: MapSurface

  @SuppressLint("ClickableViewAccessibility")
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivitySurfaceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Setup map surface
    surfaceHolder = binding.surface.holder
    surfaceHolder.addCallback(this)
    mapSurface = MapSurface(
      this,
      surfaceHolder.surface
    )

    // Load a map style
    mapSurface.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

    // Touch handling (verify plugin integration)
    binding.surface.setOnTouchListener { _, event -> mapSurface.onTouchEvent(event) }
    binding.surface.setOnGenericMotionListener { _, event -> mapSurface.onGenericMotionEvent(event) }
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

  companion object {
    const val TAG = "SurfaceActivity"
  }
}