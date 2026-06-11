package com.mapbox.maps.compose.testapp.examples.style

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.layers.FormattedValue
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.generated.IconTextFitValue
import com.mapbox.maps.extension.compose.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.compose.style.remember9PatchStyleImage
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Example showcasing how to add a nine-patch image to a symbol layer and watch it stretch
 * as the label text grows.
 */
@OptIn(MapboxDelicateApi::class)
public class NinePatchImageActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val bitmap = remember { BitmapFactory.decodeResource(resources, R.drawable.blue_round_nine) }
      var labelText by remember { mutableStateOf(TEXT_BASE) }
      var appendCount by remember { mutableStateOf(1) }

      LaunchedEffect(Unit) {
        while (isActive) {
          delay(TEXT_UPDATE_TIME_MS)
          labelText = "$labelText $TEXT_BASE"
          appendCount++
          if (appendCount.rem(3) == 0) {
            labelText = "$labelText\n"
          }
        }
      }

      val styleImage = remember9PatchStyleImage(imageId = NINE_PATCH_ID, bitmap = bitmap)

      val sourceState = rememberGeoJsonSourceState()
      sourceState.data = GeoJSONData(Feature.fromGeometry(CENTER))

      MapboxMapComposeTheme {
        ExampleScaffold {
          MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(CENTER)
                zoom(ZOOM)
              }
            },
            style = { MapboxStandardStyle() },
          ) {
            SymbolLayer(sourceState = sourceState) {
              iconImage = ImageValue(styleImage)
              iconTextFit = IconTextFitValue.BOTH
              iconTextFitPadding = DoubleListValue(5.0, 5.0, 5.0, 5.0)
              textField = FormattedValue(labelText)
            }
          }
        }
      }
    }
  }

  private companion object {
    const val NINE_PATCH_ID = "nine-patch-image"
    val CENTER: Point = Point.fromLngLat(12.554729, 55.70651)
    const val ZOOM = 9.0
    const val TEXT_BASE = "Hi!"
    const val TEXT_UPDATE_TIME_MS = 1500L
  }
}