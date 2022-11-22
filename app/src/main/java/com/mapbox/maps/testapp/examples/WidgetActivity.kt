@file:OptIn(MapboxExperimental::class)

package com.mapbox.maps.testapp.examples

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.Widget
import com.mapbox.maps.renderer.widget.WidgetPosition

/**
 * Adding a widget to the Map and demonstrating setRotation and setTranslation.
 */
class WidgetActivity : AppCompatActivity() {
  private lateinit var widget: Widget
  private var animator = ValueAnimator.ofFloat(0f, 1f).also {
    it.duration = ANIMATION_DURATION
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapOptions = MapInitOptions.getDefaultMapOptions(this).toBuilder().also {
      it.contextMode(ContextMode.SHARED)
    }.build()
    val mapInitOptions = MapInitOptions(this, mapOptions = mapOptions)
    val mapView = MapView(this, mapInitOptions = mapInitOptions).apply {
      attribution.enabled = false
      compass.enabled = false
      logo.enabled = false
      scalebar.enabled = false
    }
    setContentView(mapView)
    mapView.getMapboxMap().apply {
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(LONGITUDE, LATITUDE))
          .zoom(9.0)
          .build()
      )
    }
    val widgetPosition = WidgetPosition
      .Builder()
      .setHorizontalAlignment(WidgetPosition.Horizontal.CENTER)
      .setVerticalAlignment(WidgetPosition.Vertical.CENTER)
      .build()
    widget = LogoWidget(this, widgetPosition)
    mapView.addWidget(widget)
    animator.addUpdateListener {
      val angle = it.animatedFraction * 360f
      widget.setRotation(angle)
    }
    animator.start()
  }

  override fun onResume() {
    super.onResume()
    animator.resume()
  }

  override fun onPause() {
    super.onPause()
    animator.pause()
  }

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}

private class LogoWidget constructor(context: Context, position: WidgetPosition) : BitmapWidget(
  BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon),
  position,
)

private const val ANIMATION_DURATION = 10000L