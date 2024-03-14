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
 * A GeoJSON data source.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#geojson)
 *
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun GeoJsonSource(
  sourceId: String,
  data: GeoJSONData = GeoJSONData.default,
  maxZoom: MaxZoom = MaxZoom.default,
  attribution: Attribution = Attribution.default,
  buffer: Buffer = Buffer.default,
  tolerance: Tolerance = Tolerance.default,
  cluster: Cluster = Cluster.default,
  clusterRadius: ClusterRadius = ClusterRadius.default,
  clusterMaxZoom: ClusterMaxZoom = ClusterMaxZoom.default,
  clusterProperties: ClusterProperties = ClusterProperties.default,
  lineMetrics: LineMetrics = LineMetrics.default,
  generateId: GenerateId = GenerateId.default,
  promoteId: PromoteId = PromoteId.default,
  prefetchZoomDelta: PrefetchZoomDelta = PrefetchZoomDelta.default,
  tileCacheBudget: TileCacheBudget = TileCacheBudget.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of Geo_jsonLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<SourceNode, MapApplier>(
    factory = {
      SourceNode(
        map = mapApplier.mapView.mapboxMap,
        sourceType = "geojson",
        sourceId = sourceId,
        cacheProperties = hashMapOf<String, Value>().apply {
          put("data", Value.nullValue())
          if (maxZoom != MaxZoom.default) put(MaxZoom.NAME, maxZoom.value)
          if (attribution != Attribution.default) put(Attribution.NAME, attribution.value)
          if (buffer != Buffer.default) put(Buffer.NAME, buffer.value)
          if (tolerance != Tolerance.default) put(Tolerance.NAME, tolerance.value)
          if (cluster != Cluster.default) put(Cluster.NAME, cluster.value)
          if (clusterRadius != ClusterRadius.default) put(ClusterRadius.NAME, clusterRadius.value)
          if (clusterMaxZoom != ClusterMaxZoom.default) put(ClusterMaxZoom.NAME, clusterMaxZoom.value)
          if (clusterProperties != ClusterProperties.default) put(ClusterProperties.NAME, clusterProperties.value)
          if (lineMetrics != LineMetrics.default) put(LineMetrics.NAME, lineMetrics.value)
          if (generateId != GenerateId.default) put(GenerateId.NAME, generateId.value)
          if (promoteId != PromoteId.default) put(PromoteId.NAME, promoteId.value)
          if (prefetchZoomDelta != PrefetchZoomDelta.default) put(PrefetchZoomDelta.NAME, prefetchZoomDelta.value)
          if (tileCacheBudget != TileCacheBudget.default) put(TileCacheBudget.NAME, tileCacheBudget.value)
        },
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        setGeoJsonSourceData(data.data)
      }
      update(sourceId) {
        updateSourceId(sourceId)
      }
      update(maxZoom) {
        setBuilderProperty(MaxZoom.NAME, maxZoom.value)
      }
      update(attribution) {
        setBuilderProperty(Attribution.NAME, attribution.value)
      }
      update(buffer) {
        setBuilderProperty(Buffer.NAME, buffer.value)
      }
      update(tolerance) {
        setBuilderProperty(Tolerance.NAME, tolerance.value)
      }
      update(cluster) {
        setBuilderProperty(Cluster.NAME, cluster.value)
      }
      update(clusterRadius) {
        setBuilderProperty(ClusterRadius.NAME, clusterRadius.value)
      }
      update(clusterMaxZoom) {
        setBuilderProperty(ClusterMaxZoom.NAME, clusterMaxZoom.value)
      }
      update(clusterProperties) {
        setBuilderProperty(ClusterProperties.NAME, clusterProperties.value)
      }
      update(lineMetrics) {
        setBuilderProperty(LineMetrics.NAME, lineMetrics.value)
      }
      update(generateId) {
        setBuilderProperty(GenerateId.NAME, generateId.value)
      }
      update(promoteId) {
        setBuilderProperty(PromoteId.NAME, promoteId.value)
      }
      update(prefetchZoomDelta) {
        setProperty(PrefetchZoomDelta.NAME, prefetchZoomDelta.value)
      }
      update(tileCacheBudget) {
        setProperty(TileCacheBudget.NAME, tileCacheBudget.value)
      }
      update(data) {
        setGeoJsonSourceData(data.data)
      }
    }
  )
}
// End of generated file.