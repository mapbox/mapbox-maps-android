package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mapbox.maps.Image
import com.mapbox.maps.MapboxDelicateApi
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
import com.mapbox.maps.toMapboxImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Load a raster image to a style using ImageSource and display it on a map as
 * animated weather data using RasterLayer.
 */
class AnimatedImageSourceActivity : AppCompatActivity() {

  @OptIn(MapboxDelicateApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityAnimatedImagesourceBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val mapboxMap = binding.mapView.mapboxMap
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
    val drawables: List<Image> = listOf(
      bitmapFromDrawableRes(R.drawable.southeast_radar_0).toMapboxImage(),
      bitmapFromDrawableRes(R.drawable.southeast_radar_1).toMapboxImage(),
      bitmapFromDrawableRes(R.drawable.southeast_radar_2).toMapboxImage(),
      bitmapFromDrawableRes(R.drawable.southeast_radar_3).toMapboxImage(),
    )
    var drawableIndex = 0
    mapboxMap.getStyle {
      val imageSource: ImageSource = it.getSourceAs(ID_IMAGE_SOURCE)!!
      // Create a new coroutine in the lifecycleScope
      lifecycleScope.launch {
        // repeatOnLifecycle launches the block in a new coroutine every time the
        // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
        repeatOnLifecycle(Lifecycle.State.STARTED) {
          while (isActive) {
            imageSource.updateImage(drawables[drawableIndex++])
            drawableIndex %= drawables.size
            delay(1000L)
          }
        }
      }
    }
  }

  companion object {
    private const val ID_IMAGE_SOURCE = "animated_image_source"
    private const val ID_IMAGE_LAYER = "animated_image_layer"
  }
}