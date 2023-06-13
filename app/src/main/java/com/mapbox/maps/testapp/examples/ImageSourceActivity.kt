package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.sources.generated.ImageSource
import com.mapbox.maps.extension.style.sources.generated.imageSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.sources.updateImage
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityImageSourceBinding
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes

class ImageSourceActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityImageSourceBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val map = binding.mapView.getMapboxMap()

    map.loadStyle(
      style(style = Style.DARK) {
        +imageSource(ID_IMAGE_SOURCE) {
          coordinates(
            listOf(
              listOf(-80.11725, 25.7836),
              listOf(-80.1397431334, 25.783548),
              listOf(-80.13964, 25.7680),
              listOf(-80.11725, 25.76795)
            )
          )
        }
        +rasterLayer(ID_IMAGE_LAYER, ID_IMAGE_SOURCE) {}
      }
    ) {
      bitmapFromDrawableRes(this, R.drawable.miami_beach)?.let { bitmap ->
        val imageSource: ImageSource = it.getSourceAs(ID_IMAGE_SOURCE)!!
        imageSource.updateImage(bitmap)
      }
    }
  }

  companion object {
    private const val ID_IMAGE_SOURCE = "image_source-id"
    private const val ID_IMAGE_LAYER = "image_layer-id"
  }
}