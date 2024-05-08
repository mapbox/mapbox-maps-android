// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * Particle animation driven by textures such as wind maps.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster-particle)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param rasterParticleArrayBand Displayed band of raster array source layer
 * @param rasterParticleColor Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
 * @param rasterParticleCount Defines the amount of particles per tile.
 * @param rasterParticleFadeOpacityFactor Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail.
 * @param rasterParticleMaxSpeed Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value.
 * @param rasterParticleResetRateFactor Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles).
 * @param rasterParticleSpeedFactor Defines a coefficient for the speed of particles’ motion.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun RasterParticleLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("raster-particle")
  },
  rasterParticleArrayBand: RasterParticleArrayBand = RasterParticleArrayBand.default,
  rasterParticleColor: RasterParticleColor = RasterParticleColor.default,
  rasterParticleCount: RasterParticleCount = RasterParticleCount.default,
  rasterParticleFadeOpacityFactor: RasterParticleFadeOpacityFactor = RasterParticleFadeOpacityFactor.default,
  rasterParticleFadeOpacityFactorTransition: Transition = Transition.default,
  rasterParticleMaxSpeed: RasterParticleMaxSpeed = RasterParticleMaxSpeed.default,
  rasterParticleResetRateFactor: RasterParticleResetRateFactor = RasterParticleResetRateFactor.default,
  rasterParticleSpeedFactor: RasterParticleSpeedFactor = RasterParticleSpeedFactor.default,
  rasterParticleSpeedFactorTransition: Transition = Transition.default,
  visibility: Visibility = Visibility.default,
  minZoom: MinZoom = MinZoom.default,
  maxZoom: MaxZoom = MaxZoom.default,
  sourceLayer: SourceLayer = SourceLayer.default,
  filter: Filter = Filter.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SymbolLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "raster-particle",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (rasterParticleArrayBand != RasterParticleArrayBand.default) {
          setProperty(RasterParticleArrayBand.NAME, rasterParticleArrayBand.value)
        }
        if (rasterParticleColor != RasterParticleColor.default) {
          setProperty(RasterParticleColor.NAME, rasterParticleColor.value)
        }
        if (rasterParticleCount != RasterParticleCount.default) {
          setProperty(RasterParticleCount.NAME, rasterParticleCount.value)
        }
        if (rasterParticleFadeOpacityFactor != RasterParticleFadeOpacityFactor.default) {
          setProperty(RasterParticleFadeOpacityFactor.NAME, rasterParticleFadeOpacityFactor.value)
        }
        if (rasterParticleFadeOpacityFactorTransition != Transition.default) {
          setProperty(RasterParticleFadeOpacityFactor.TRANSITION_NAME, rasterParticleFadeOpacityFactorTransition.value)
        }
        if (rasterParticleMaxSpeed != RasterParticleMaxSpeed.default) {
          setProperty(RasterParticleMaxSpeed.NAME, rasterParticleMaxSpeed.value)
        }
        if (rasterParticleResetRateFactor != RasterParticleResetRateFactor.default) {
          setProperty(RasterParticleResetRateFactor.NAME, rasterParticleResetRateFactor.value)
        }
        if (rasterParticleSpeedFactor != RasterParticleSpeedFactor.default) {
          setProperty(RasterParticleSpeedFactor.NAME, rasterParticleSpeedFactor.value)
        }
        if (rasterParticleSpeedFactorTransition != Transition.default) {
          setProperty(RasterParticleSpeedFactor.TRANSITION_NAME, rasterParticleSpeedFactorTransition.value)
        }
        if (visibility != Visibility.default) {
          setProperty(Visibility.NAME, visibility.value)
        }
        if (minZoom != MinZoom.default) {
          setProperty(MinZoom.NAME, minZoom.value)
        }
        if (maxZoom != MaxZoom.default) {
          setProperty(MaxZoom.NAME, maxZoom.value)
        }
        if (sourceLayer != SourceLayer.default) {
          setProperty(SourceLayer.NAME, sourceLayer.value)
        }
        if (filter != Filter.default) {
          setProperty(Filter.NAME, filter.value)
        }
      }
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(rasterParticleArrayBand) {
        setProperty(RasterParticleArrayBand.NAME, rasterParticleArrayBand.value)
      }
      update(rasterParticleColor) {
        setProperty(RasterParticleColor.NAME, rasterParticleColor.value)
      }
      update(rasterParticleCount) {
        setProperty(RasterParticleCount.NAME, rasterParticleCount.value)
      }
      update(rasterParticleFadeOpacityFactor) {
        setProperty(RasterParticleFadeOpacityFactor.NAME, rasterParticleFadeOpacityFactor.value)
      }
      update(rasterParticleFadeOpacityFactorTransition) {
        setProperty(RasterParticleFadeOpacityFactor.TRANSITION_NAME, rasterParticleFadeOpacityFactorTransition.value)
      }
      update(rasterParticleMaxSpeed) {
        setProperty(RasterParticleMaxSpeed.NAME, rasterParticleMaxSpeed.value)
      }
      update(rasterParticleResetRateFactor) {
        setProperty(RasterParticleResetRateFactor.NAME, rasterParticleResetRateFactor.value)
      }
      update(rasterParticleSpeedFactor) {
        setProperty(RasterParticleSpeedFactor.NAME, rasterParticleSpeedFactor.value)
      }
      update(rasterParticleSpeedFactorTransition) {
        setProperty(RasterParticleSpeedFactor.TRANSITION_NAME, rasterParticleSpeedFactorTransition.value)
      }
      update(visibility) {
        setProperty(Visibility.NAME, visibility.value)
      }
      update(minZoom) {
        setProperty(MinZoom.NAME, minZoom.value)
      }
      update(maxZoom) {
        setProperty(MaxZoom.NAME, maxZoom.value)
      }
      update(sourceLayer) {
        setProperty(SourceLayer.NAME, sourceLayer.value)
      }
      update(filter) {
        setProperty(Filter.NAME, filter.value)
      }
    }
  )
}
// End of generated file.