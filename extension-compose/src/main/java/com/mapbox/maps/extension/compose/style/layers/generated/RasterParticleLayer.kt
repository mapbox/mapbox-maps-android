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
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
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
 * @param rasterParticleFadeOpacityFactorTransition Defines the transition of [rasterParticleFadeOpacityFactor].
 * @param rasterParticleMaxSpeed Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value.
 * @param rasterParticleResetRateFactor Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles).
 * @param rasterParticleSpeedFactor Defines a coefficient for the speed of particles’ motion.
 * @param rasterParticleSpeedFactorTransition Defines the transition of [rasterParticleSpeedFactor].
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
  rasterParticleArrayBand: StringValue = StringValue.INITIAL,
  rasterParticleColor: ColorValue = ColorValue.INITIAL,
  rasterParticleCount: LongValue = LongValue.INITIAL,
  rasterParticleFadeOpacityFactor: DoubleValue = DoubleValue.INITIAL,
  rasterParticleFadeOpacityFactorTransition: Transition = Transition.INITIAL,
  rasterParticleMaxSpeed: DoubleValue = DoubleValue.INITIAL,
  rasterParticleResetRateFactor: DoubleValue = DoubleValue.INITIAL,
  rasterParticleSpeedFactor: DoubleValue = DoubleValue.INITIAL,
  rasterParticleSpeedFactorTransition: Transition = Transition.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of RasterParticleLayer inside unsupported composable function")

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
        if (rasterParticleArrayBand.notInitial) {
          setProperty("raster-particle-array-band", rasterParticleArrayBand.value)
        }
        if (rasterParticleColor.notInitial) {
          setProperty("raster-particle-color", rasterParticleColor.value)
        }
        if (rasterParticleCount.notInitial) {
          setProperty("raster-particle-count", rasterParticleCount.value)
        }
        if (rasterParticleFadeOpacityFactor.notInitial) {
          setProperty("raster-particle-fade-opacity-factor", rasterParticleFadeOpacityFactor.value)
        }
        if (rasterParticleFadeOpacityFactorTransition.notInitial) {
          setProperty("raster-particle-fade-opacity-factor-transition", rasterParticleFadeOpacityFactorTransition.value)
        }
        if (rasterParticleMaxSpeed.notInitial) {
          setProperty("raster-particle-max-speed", rasterParticleMaxSpeed.value)
        }
        if (rasterParticleResetRateFactor.notInitial) {
          setProperty("raster-particle-reset-rate-factor", rasterParticleResetRateFactor.value)
        }
        if (rasterParticleSpeedFactor.notInitial) {
          setProperty("raster-particle-speed-factor", rasterParticleSpeedFactor.value)
        }
        if (rasterParticleSpeedFactorTransition.notInitial) {
          setProperty("raster-particle-speed-factor-transition", rasterParticleSpeedFactorTransition.value)
        }
        if (visibility.notInitial) {
          setProperty("visibility", visibility.value)
        }
        if (minZoom.notInitial) {
          setProperty("min-zoom", minZoom.value)
        }
        if (maxZoom.notInitial) {
          setProperty("max-zoom", maxZoom.value)
        }
        if (sourceLayer.notInitial) {
          setProperty("source-layer", sourceLayer.value)
        }
        if (filter.notInitial) {
          setProperty("filter", filter.value)
        }
      }
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(rasterParticleArrayBand) {
        setProperty("raster-particle-array-band", rasterParticleArrayBand.value)
      }
      update(rasterParticleColor) {
        setProperty("raster-particle-color", rasterParticleColor.value)
      }
      update(rasterParticleCount) {
        setProperty("raster-particle-count", rasterParticleCount.value)
      }
      update(rasterParticleFadeOpacityFactor) {
        setProperty("raster-particle-fade-opacity-factor", rasterParticleFadeOpacityFactor.value)
      }
      update(rasterParticleFadeOpacityFactorTransition) {
        setProperty("raster-particle-fade-opacity-factor-transition", rasterParticleFadeOpacityFactorTransition.value)
      }
      update(rasterParticleMaxSpeed) {
        setProperty("raster-particle-max-speed", rasterParticleMaxSpeed.value)
      }
      update(rasterParticleResetRateFactor) {
        setProperty("raster-particle-reset-rate-factor", rasterParticleResetRateFactor.value)
      }
      update(rasterParticleSpeedFactor) {
        setProperty("raster-particle-speed-factor", rasterParticleSpeedFactor.value)
      }
      update(rasterParticleSpeedFactorTransition) {
        setProperty("raster-particle-speed-factor-transition", rasterParticleSpeedFactorTransition.value)
      }
      update(visibility) {
        setProperty("visibility", visibility.value)
      }
      update(minZoom) {
        setProperty("min-zoom", minZoom.value)
      }
      update(maxZoom) {
        setProperty("max-zoom", maxZoom.value)
      }
      update(sourceLayer) {
        setProperty("source-layer", sourceLayer.value)
      }
      update(filter) {
        setProperty("filter", filter.value)
      }
    }
  )
}
// End of generated file.