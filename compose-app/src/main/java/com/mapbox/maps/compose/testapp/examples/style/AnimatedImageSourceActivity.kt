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
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.PointListValue
import com.mapbox.maps.extension.compose.style.layers.generated.RasterLayer
import com.mapbox.maps.extension.compose.style.sources.generated.rememberImageSourceState
import com.mapbox.maps.extension.style.sources.generated.ImageSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.sources.updateImage
import com.mapbox.maps.toMapboxImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Load a raster image to a style using ImageSource and display it on a map as
 * animated weather data using RasterLayer.
 */
public class AnimatedImageSourceActivity : ComponentActivity() {

  @OptIn(MapboxDelicateApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(Point.fromLngLat(-76.1255491524373, 43.043493218594804))
                zoom(6.0)
              }
            }
          ) {
            val images = listOf(
              ImageBitmap.imageResource(R.drawable.southeast_radar_0).asAndroidBitmap().toMapboxImage(),
              ImageBitmap.imageResource(R.drawable.southeast_radar_1).asAndroidBitmap().toMapboxImage(),
              ImageBitmap.imageResource(R.drawable.southeast_radar_2).asAndroidBitmap().toMapboxImage(),
              ImageBitmap.imageResource(R.drawable.southeast_radar_3).asAndroidBitmap().toMapboxImage(),
            )
            MapEffect(Unit) {
              val imageSource: ImageSource = it.mapboxMap.getSourceAs(ID_IMAGE_SOURCE)!!
              var index = 0
              while (isActive) {
                imageSource.updateImage(images[index++])
                index %= images.size
                delay(1000L)
              }
            }
            RasterLayer(
              sourceState = rememberImageSourceState(sourceId = ID_IMAGE_SOURCE) {
                coordinates = PointListValue(
                  Point.fromLngLat(-80.425, 46.437),
                  Point.fromLngLat(-71.516, 46.437),
                  Point.fromLngLat(-71.516, 37.936),
                  Point.fromLngLat(-80.425, 37.936)
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