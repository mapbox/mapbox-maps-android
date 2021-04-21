package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Image
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.sources.generated.ImageSource
import com.mapbox.maps.extension.style.sources.generated.imageSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.sources.updateImage
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes
import kotlinx.android.synthetic.main.activity_animated_imagesource.*
import java.nio.ByteBuffer

class ImageSourceActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image_source)
    val map = mapView.getMapboxMap()

    map.loadStyle(
      style(styleUri = Style.DARK) {
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
        val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
        bitmap.copyPixelsToBuffer(byteBuffer)
        imageSource.updateImage(Image(bitmap.width, bitmap.height, byteBuffer.array()))
      }
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private const val ID_IMAGE_SOURCE = "image_source-id"
    private const val ID_IMAGE_LAYER = "image_layer-id"
  }
}