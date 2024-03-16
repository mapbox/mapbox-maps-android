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
 * A raster tile source.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#raster)
 *
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun RasterSource(
  sourceId: String,
  url: Url = Url.default,
  tiles: Tiles = Tiles.default,
  bounds: Bounds = Bounds.default,
  minZoom: MinZoom = MinZoom.default,
  maxZoom: MaxZoom = MaxZoom.default,
  tileSize: TileSize = TileSize.default,
  scheme: Scheme = Scheme.default,
  attribution: Attribution = Attribution.default,
  volatile: Volatile = Volatile.default,
  prefetchZoomDelta: PrefetchZoomDelta = PrefetchZoomDelta.default,
  tileCacheBudget: TileCacheBudget = TileCacheBudget.default,
  minimumTileUpdateInterval: MinimumTileUpdateInterval = MinimumTileUpdateInterval.default,
  maxOverscaleFactorForParentTiles: MaxOverscaleFactorForParentTiles = MaxOverscaleFactorForParentTiles.default,
  tileRequestsDelay: TileRequestsDelay = TileRequestsDelay.default,
  tileNetworkRequestsDelay: TileNetworkRequestsDelay = TileNetworkRequestsDelay.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of Geo_jsonLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<SourceNode, MapApplier>(
    factory = {
      SourceNode(
        map = mapApplier.mapView.mapboxMap,
        sourceType = "raster",
        sourceId = sourceId,
        cacheProperties = hashMapOf<String, Value>().apply {
          if (url != Url.default) put(Url.NAME, url.value)
          if (tiles != Tiles.default) put(Tiles.NAME, tiles.value)
          if (bounds != Bounds.default) put(Bounds.NAME, bounds.value)
          if (minZoom != MinZoom.default) put(MinZoom.NAME, minZoom.value)
          if (maxZoom != MaxZoom.default) put(MaxZoom.NAME, maxZoom.value)
          if (tileSize != TileSize.default) put(TileSize.NAME, tileSize.value)
          if (scheme != Scheme.default) put(Scheme.NAME, scheme.value)
          if (attribution != Attribution.default) put(Attribution.NAME, attribution.value)
          if (volatile != Volatile.default) put(Volatile.NAME, volatile.value)
          if (prefetchZoomDelta != PrefetchZoomDelta.default) put(PrefetchZoomDelta.NAME, prefetchZoomDelta.value)
          if (tileCacheBudget != TileCacheBudget.default) put(TileCacheBudget.NAME, tileCacheBudget.value)
          if (minimumTileUpdateInterval != MinimumTileUpdateInterval.default) put(MinimumTileUpdateInterval.NAME, minimumTileUpdateInterval.value)
          if (maxOverscaleFactorForParentTiles != MaxOverscaleFactorForParentTiles.default) put(MaxOverscaleFactorForParentTiles.NAME, maxOverscaleFactorForParentTiles.value)
          if (tileRequestsDelay != TileRequestsDelay.default) put(TileRequestsDelay.NAME, tileRequestsDelay.value)
          if (tileNetworkRequestsDelay != TileNetworkRequestsDelay.default) put(TileNetworkRequestsDelay.NAME, tileNetworkRequestsDelay.value)
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
      update(scheme) {
        setBuilderProperty(Scheme.NAME, scheme.value)
      }
      update(attribution) {
        setBuilderProperty(Attribution.NAME, attribution.value)
      }
      update(volatile) {
        setBuilderProperty(Volatile.NAME, volatile.value)
      }
      update(prefetchZoomDelta) {
        setProperty(PrefetchZoomDelta.NAME, prefetchZoomDelta.value)
      }
      update(tileCacheBudget) {
        setProperty(TileCacheBudget.NAME, tileCacheBudget.value)
      }
      update(minimumTileUpdateInterval) {
        setProperty(MinimumTileUpdateInterval.NAME, minimumTileUpdateInterval.value)
      }
      update(maxOverscaleFactorForParentTiles) {
        setProperty(MaxOverscaleFactorForParentTiles.NAME, maxOverscaleFactorForParentTiles.value)
      }
      update(tileRequestsDelay) {
        setProperty(TileRequestsDelay.NAME, tileRequestsDelay.value)
      }
      update(tileNetworkRequestsDelay) {
        setProperty(TileNetworkRequestsDelay.NAME, tileNetworkRequestsDelay.value)
      }
    }
  )
}
// End of generated file.