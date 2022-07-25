package com.mapbox.maps.testapp.auto.car

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface
import com.mapbox.maps.extension.androidauto.widgets.CompassWidget
import com.mapbox.maps.extension.androidauto.widgets.LogoWidget
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.WidgetPosition
import kotlin.random.Random

/**
 * Note that the Widgets are only available when using android auto extension together with the
 * Android Maps SDK v10.4.0 and above, otherwise you will get compilation errors.
 */
@OptIn(MapboxExperimental::class)
class CarMapWidgets : MapboxCarMapObserver {
  private var mapboxCarMapSurface: MapboxCarMapSurface? = null
  private lateinit var logoWidget: LogoWidget
  private lateinit var compassWidget: CompassWidget
  private lateinit var bitmapWidget: BitmapWidget
  private lateinit var onCameraChangeListener: OnCameraChangeListener

  private val handler = Handler(Looper.getMainLooper())
  private val bitmapRunnable = object : Runnable {
    override fun run() {
      bitmapWidget.updateBitmap(createBitmap())

      // Try creating a new widget every time instead. It works, but the widget is not centered horizontally properly.
//      mapboxCarMapSurface?.run {
//        mapSurface.removeWidget(bitmapWidget)
//        bitmapWidget = createBitmapWidget()
//        mapSurface.addWidget(bitmapWidget)
//      }

      handler.postDelayed(this, 2000)
    }
  }

  override fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onAttached(mapboxCarMapSurface)
    this.mapboxCarMapSurface = mapboxCarMapSurface
    with(mapboxCarMapSurface) {
      logoWidget = LogoWidget(carContext)
      compassWidget = CompassWidget(
        carContext,
        marginX = 26f,
        marginY = 120f,
      )
      bitmapWidget = createBitmapWidget()
      onCameraChangeListener = OnCameraChangeListener { compassWidget.setRotation(-mapSurface.getMapboxMap().cameraState.bearing.toFloat()) }
      mapSurface.addWidget(logoWidget)
      mapSurface.addWidget(compassWidget)
      mapSurface.addWidget(bitmapWidget)
      mapSurface.getMapboxMap().addOnCameraChangeListener(onCameraChangeListener)
      handler.postDelayed(bitmapRunnable, 2000)
    }
  }

  override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onDetached(mapboxCarMapSurface)
    this.mapboxCarMapSurface = mapboxCarMapSurface
    with(mapboxCarMapSurface) {
      mapSurface.getMapboxMap().removeOnCameraChangeListener(onCameraChangeListener)
      handler.removeCallbacks(bitmapRunnable)
      mapSurface.removeWidget(logoWidget)
      mapSurface.removeWidget(compassWidget)
      mapSurface.removeWidget(bitmapWidget)
    }
  }

  private fun createBitmap(): Bitmap {
    val width = (Random.nextInt(until = 10) + 1) * 10
    val height = (Random.nextInt(until = 10) + 1) * 10
    return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).also { it.eraseColor(Color.GREEN) }
  }

  private fun createBitmapWidget(): BitmapWidget {
    return BitmapWidget(
      createBitmap(),
      WidgetPosition(WidgetPosition.Horizontal.CENTER, WidgetPosition.Vertical.BOTTOM),
      marginY = 50f,
    )
  }
}