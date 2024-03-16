// This file is generated.

package com.mapbox.maps.extension.compose.style.sources.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.sources.internal.SourceNode

/**
 * An image data source.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#image)
 *
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun ImageSource(
  sourceId: String,
  url: Url = Url.default,
  coordinates: Coordinates = Coordinates.default,
  prefetchZoomDelta: PrefetchZoomDelta = PrefetchZoomDelta.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of Geo_jsonLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<SourceNode, MapApplier>(
    factory = {
      SourceNode(
        map = mapApplier.mapView.mapboxMap,
        sourceType = "image",
        sourceId = sourceId,
        cacheProperties = hashMapOf<String, Value>().apply {
          if (url != Url.default) put(Url.NAME, url.value)
          if (coordinates != Coordinates.default) put(Coordinates.NAME, coordinates.value)
          if (prefetchZoomDelta != PrefetchZoomDelta.default) put(PrefetchZoomDelta.NAME, prefetchZoomDelta.value)
        },
        coroutineScope = coroutineScope
      )
    },
    update = {
      update(sourceId) {
        updateSourceId(sourceId)
      }
      update(url) {
        setBuilderProperty(Url.NAME, url.value)
      }
      update(coordinates) {
        setBuilderProperty(Coordinates.NAME, coordinates.value)
      }
      update(prefetchZoomDelta) {
        setProperty(PrefetchZoomDelta.NAME, prefetchZoomDelta.value)
      }
    }
  )
}
// End of generated file.