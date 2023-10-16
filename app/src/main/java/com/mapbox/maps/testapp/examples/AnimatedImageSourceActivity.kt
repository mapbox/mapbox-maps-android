package com.mapbox.maps.testapp.examples

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.sources.generated.ImageSource
import com.mapbox.maps.extension.style.sources.generated.imageSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.sources.updateImage
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityAnimatedImagesourceBinding
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes

/**
 * Load a raster image to a style using ImageSource and display it on a map as
 * animated weather data using RasterLayer.
 */
class AnimatedImageSourceActivity : AppCompatActivity() {

  private val handler: Handler = Handler(Looper.getMainLooper())
  private lateinit var mapboxMap: MapboxMap
  private lateinit var runnable: Runnable

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityAnimatedImagesourceBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.loadStyle(
      style(style = Style.STANDARD) {
        +imageSource(ID_IMAGE_SOURCE) {
          coordinates(
            listOf(
              listOf(-80.425, 46.437),
              listOf(-71.516, 46.437),
              listOf(-71.516, 37.936),
              listOf(-80.425, 37.936)
            )
          )
        }
        +rasterLayer(ID_IMAGE_LAYER, ID_IMAGE_SOURCE) { }
      }
    )
  }

  override fun onStart() {
    super.onStart()
    mapboxMap.getStyle {
      val imageSource: ImageSource = it.getSourceAs(ID_IMAGE_SOURCE)!!
      runnable = RefreshImageRunnable(applicationContext, imageSource, handler)
      handler.postDelayed(runnable, 100)
    }
  }

  override fun onStop() {
    super.onStop()
    if (::runnable.isInitialized) {
      handler.removeCallbacks(runnable)
    }
  }

  private class RefreshImageRunnable constructor(
    appContext: Context,
    private val imageSource: ImageSource,
    private val handler: Handler
  ) :
    Runnable {
    private val drawables: Array<Bitmap?> = arrayOfNulls(4)
    private var drawableIndex: Int

    override fun run() {
      drawables[drawableIndex++]?.let { bitmap ->
        imageSource.updateImage(bitmap)
        if (drawableIndex > 3) {
          drawableIndex = 0
        }
      }
      handler.postDelayed(this, 1000)
    }

    init {
      drawables[0] = bitmapFromDrawableRes(appContext, R.drawable.southeast_radar_0)
      drawables[1] = bitmapFromDrawableRes(appContext, R.drawable.southeast_radar_1)
      drawables[2] = bitmapFromDrawableRes(appContext, R.drawable.southeast_radar_2)
      drawables[3] = bitmapFromDrawableRes(appContext, R.drawable.southeast_radar_3)
      drawableIndex = 1
    }
  }

  companion object {
    private const val ID_IMAGE_SOURCE = "animated_image_source"
    private const val ID_IMAGE_LAYER = "animated_image_layer"
  }
}