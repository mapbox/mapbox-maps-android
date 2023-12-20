package com.mapbox.maps.testapp.examples

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.*
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.WidgetPosition
import com.mapbox.maps.testapp.databinding.ActivitySurfaceBinding

/**
 * Example integration with MapSurface through using SurfaceView directly.
 */
@OptIn(MapboxExperimental::class)
class SurfaceActivity : AppCompatActivity(), SurfaceHolder.Callback {

  private lateinit var surfaceHolder: SurfaceHolder
  private lateinit var mapSurface: MapSurface
  private val animator = ValueAnimator.ofFloat(0f, 1f).also {
    it.duration = ANIMATION_DURATION
    it.repeatCount = ValueAnimator.INFINITE
  }

  @SuppressLint("ClickableViewAccessibility")
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivitySurfaceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Setup map surface
    surfaceHolder = binding.surface.holder
    surfaceHolder.addCallback(this)
    val mapOptions = MapInitOptions.getDefaultMapOptions(this).toBuilder().also {
      it.contextMode(ContextMode.SHARED)
    }.build()
    val mapInitOptions = MapInitOptions(this, mapOptions = mapOptions)
    mapSurface = MapSurface(
      this,
      surfaceHolder.surface,
      mapInitOptions,
    )

    // Load a map style
    mapSurface.mapboxMap.loadStyle(Style.STANDARD)

    // Touch handling (verify plugin integration)
    binding.surface.setOnTouchListener { _, event -> mapSurface.onTouchEvent(event) }
    binding.surface.setOnGenericMotionListener { _, event -> mapSurface.onGenericMotionEvent(event) }

    val widgetPosition = WidgetPosition
      .Builder()
      .setHorizontalAlignment(WidgetPosition.Horizontal.CENTER)
      .setVerticalAlignment(WidgetPosition.Vertical.CENTER)
      .build()
    val rotatingWidget = LogoWidget(this, widgetPosition)
    mapSurface.addWidget(rotatingWidget)
    animator.addUpdateListener {
      val angle = (it.animatedFraction * 360f) % 360f
      rotatingWidget.setRotation(angle)
    }
    animator.start()

    // add second widget to make sure both are rendered
    val staticWidgetPosition = WidgetPosition
      .Builder()
      .setHorizontalAlignment(WidgetPosition.Horizontal.LEFT)
      .setVerticalAlignment(WidgetPosition.Vertical.BOTTOM)
      .setOffsetX(20f)
      .setOffsetY(-20f)
      .build()
    mapSurface.addWidget(LogoWidget(this, staticWidgetPosition))
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

  override fun onResume() {
    super.onResume()
    animator.resume()
  }

  override fun onPause() {
    super.onPause()
    animator.pause()
  }

  private companion object {
    private const val ANIMATION_DURATION = 10000L
  }

  private class LogoWidget constructor(context: Context, position: WidgetPosition) : BitmapWidget(
    BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon),
    position,
  )
}