package com.mapbox.maps.testapp.auto.car

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface
import com.mapbox.maps.extension.androidauto.R
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
  private lateinit var bitmap1: Bitmap
  private lateinit var bitmap2: Bitmap
  private lateinit var bitmapWidget1: BitmapWidget
  private lateinit var bitmapWidget2: BitmapWidget
  private lateinit var onCameraChangeListener: OnCameraChangeListener
  private lateinit var compassBitmap: Bitmap

  private val handler = Handler(Looper.getMainLooper())
  private val bitmapRunnable = object : Runnable {
    override fun run() {
      bitmapWidget1.updateBitmap(bitmap1)

      // Try creating a new widget every time instead. It works, but the widget is not centered horizontally properly.
      mapboxCarMapSurface?.run {
        mapSurface.removeWidget(bitmapWidget2)
        bitmap2.recycle()
        bitmap2 = createBitmap(Color.GREEN)
        bitmapWidget2 = createBitmapWidget(bitmap2)
        mapSurface.addWidget(bitmapWidget2)
      }

      handler.postDelayed(this, 2000)
    }
  }

  override fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onAttached(mapboxCarMapSurface)
    this.mapboxCarMapSurface = mapboxCarMapSurface
    with(mapboxCarMapSurface) {
      logoWidget = LogoWidget(carContext)
      compassBitmap = BitmapFactory.decodeResource(carContext.resources, R.drawable.mapbox_compass_icon)
      compassWidget = CompassWidget(
        carContext,
        marginX = 26f,
        marginY = 120f,
      )

      bitmap1 = createBitmap(color = Color.BLUE)
      bitmapWidget1 = BitmapWidget(
        bitmap1,
        WidgetPosition(WidgetPosition.Horizontal.CENTER, WidgetPosition.Vertical.CENTER)
      )
      bitmap2 = createBitmap(color = Color.GREEN)
      bitmapWidget2 = createBitmapWidget(bitmap2)
      onCameraChangeListener = OnCameraChangeListener { compassWidget.setRotation(-mapSurface.getMapboxMap().cameraState.bearing.toFloat()) }
      mapSurface.addWidget(logoWidget)
      mapSurface.addWidget(compassWidget)
      mapSurface.addWidget(bitmapWidget1)
      mapSurface.addWidget(bitmapWidget2)
      mapSurface.getMapboxMap().addOnCameraChangeListener(onCameraChangeListener)
      handler.postDelayed(bitmapRunnable, 2000)
    }
  }

  override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
    super.onDetached(mapboxCarMapSurface)
    compassBitmap.recycle()
    this.mapboxCarMapSurface = mapboxCarMapSurface
    with(mapboxCarMapSurface) {
      mapSurface.getMapboxMap().removeOnCameraChangeListener(onCameraChangeListener)
      handler.removeCallbacks(bitmapRunnable)
      mapSurface.removeWidget(logoWidget)
      mapSurface.removeWidget(compassWidget)
      mapSurface.removeWidget(bitmapWidget1)
      mapSurface.removeWidget(bitmapWidget2)
    }
  }

  @SuppressLint("NewApi")
  private fun createBitmap(color: Int): Bitmap {
    val width = compassBitmap.width + (Random.nextInt(until = 10) + 1) * 10
    val height = compassBitmap.height + (Random.nextInt(until = 10) + 1) * 10
    val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true).also {
      val canvas = Canvas(it)
      val rectF = RectF(width / 2f - compassBitmap.width / 2f, height / 2f - compassBitmap.height / 2f, width / 2f + compassBitmap.width / 2f, height / 2f + compassBitmap.height / 2f)
      canvas.drawBitmap(compassBitmap, null, rectF, null)
      Paint().apply {
        setColor(color)
        strokeWidth = 5f
        style = Paint.Style.STROKE
      }.let { paint ->
        canvas.drawRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), paint)
      }
      Paint().apply {
        setColor(color)
        strokeWidth = 2f
        style = Paint.Style.STROKE
      }.let { paint ->
        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), paint)
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, paint)
      }
      Paint().apply {
        setColor(Color.RED)
        strokeWidth = 2f
        style = Paint.Style.STROKE
      }.let { paint ->
        canvas.drawRect(rectF, paint)
      }
    }
    return result
  }

  private fun createBitmapWidget(bitmap: Bitmap, marginY: Float = 80f): BitmapWidget {
    return BitmapWidget(
      bitmap,
      WidgetPosition(WidgetPosition.Horizontal.CENTER, WidgetPosition.Vertical.BOTTOM),
      marginY = marginY,
    )
  }
}