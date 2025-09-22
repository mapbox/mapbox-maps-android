package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.PointListValue
import com.mapbox.maps.extension.compose.style.layers.generated.RasterLayer
import com.mapbox.maps.extension.compose.style.sources.generated.rememberImageSourceState
import com.mapbox.maps.extension.style.sources.generated.ImageSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.sources.updateImage
import com.mapbox.maps.toMapboxImage

/**
 * Example to showcase usage of ImageSource.
 */
public class ImageSourceActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(Point.fromLngLat(-80.1263, 25.7845))
                zoom(12.0)
              }
            },
            style = {
              MapStyle(style = Style.DARK)
            }
          ) {
            @OptIn(MapboxDelicateApi::class)
            val bitmap = ImageBitmap.imageResource(R.drawable.miami_beach).asAndroidBitmap().toMapboxImage()
            MapEffect(Unit) {
              val imageSource: ImageSource = it.mapboxMap.getSourceAs(ID_IMAGE_SOURCE)!!
              imageSource.updateImage(bitmap)
            }
            RasterLayer(
              sourceState = rememberImageSourceState(sourceId = ID_IMAGE_SOURCE) {
                coordinates = PointListValue(
                  Point.fromLngLat(-80.11725, 25.7836),
                  Point.fromLngLat(-80.1397431334, 25.783548),
                  Point.fromLngLat(-80.13964, 25.7680),
                  Point.fromLngLat(-80.11725, 25.76795)
                )
              }
            )
          }
        }
      }
    }
  }

  private companion object {
    private const val ID_IMAGE_SOURCE = "image_source-id"
  }
}