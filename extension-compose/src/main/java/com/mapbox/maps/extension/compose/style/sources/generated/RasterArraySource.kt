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
 * A raster array source
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#raster_array)
 *
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun RasterArraySource(
  sourceId: String,
  url: Url = Url.default,
  tiles: Tiles = Tiles.default,
  bounds: Bounds = Bounds.default,
  minZoom: MinZoom = MinZoom.default,
  maxZoom: MaxZoom = MaxZoom.default,
  tileSize: TileSize = TileSize.default,
  attribution: Attribution = Attribution.default,
  rasterLayers: RasterLayers = RasterLayers.default,
  tileCacheBudget: TileCacheBudget = TileCacheBudget.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of Geo_jsonLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<SourceNode, MapApplier>(
    factory = {
      SourceNode(
        map = mapApplier.mapView.mapboxMap,
        sourceType = "raster-array",
        sourceId = sourceId,
        cacheProperties = hashMapOf<String, Value>().apply {
          if (url != Url.default) put(Url.NAME, url.value)
          if (tiles != Tiles.default) put(Tiles.NAME, tiles.value)
          if (bounds != Bounds.default) put(Bounds.NAME, bounds.value)
          if (minZoom != MinZoom.default) put(MinZoom.NAME, minZoom.value)
          if (maxZoom != MaxZoom.default) put(MaxZoom.NAME, maxZoom.value)
          if (tileSize != TileSize.default) put(TileSize.NAME, tileSize.value)
          if (attribution != Attribution.default) put(Attribution.NAME, attribution.value)
          if (rasterLayers != RasterLayers.default) put(RasterLayers.NAME, rasterLayers.value)
          if (tileCacheBudget != TileCacheBudget.default) put(TileCacheBudget.NAME, tileCacheBudget.value)
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
      update(tiles) {
        setBuilderProperty(Tiles.NAME, tiles.value)
      }
      update(bounds) {
        setBuilderProperty(Bounds.NAME, bounds.value)
      }
      update(minZoom) {
        setBuilderProperty(MinZoom.NAME, minZoom.value)
      }
      update(maxZoom) {
        setBuilderProperty(MaxZoom.NAME, maxZoom.value)
      }
      update(tileSize) {
        setBuilderProperty(TileSize.NAME, tileSize.value)
      }
      update(attribution) {
        setBuilderProperty(Attribution.NAME, attribution.value)
      }
      update(rasterLayers) {
        setBuilderProperty(RasterLayers.NAME, rasterLayers.value)
      }
      update(tileCacheBudget) {
        setProperty(TileCacheBudget.NAME, tileCacheBudget.value)
      }
    }
  )
}
// End of generated file.