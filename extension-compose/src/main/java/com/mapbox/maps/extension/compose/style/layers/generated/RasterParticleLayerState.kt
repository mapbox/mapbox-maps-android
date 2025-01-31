// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [RasterParticleLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster-particle)
 */
@Stable
@MapboxExperimental
public class RasterParticleLayerState private constructor(
  initialRasterParticleArrayBand: StringValue,
  initialRasterParticleColor: ColorValue,
  initialRasterParticleColorUseTheme: StringValue,
  initialRasterParticleCount: LongValue,
  initialRasterParticleFadeOpacityFactor: DoubleValue,
  initialRasterParticleFadeOpacityFactorTransition: Transition,
  initialRasterParticleMaxSpeed: DoubleValue,
  initialRasterParticleResetRateFactor: DoubleValue,
  initialRasterParticleSpeedFactor: DoubleValue,
  initialRasterParticleSpeedFactorTransition: Transition,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [RasterParticleLayerState].
   */
  public constructor() : this(
    initialRasterParticleArrayBand = StringValue.INITIAL,
    initialRasterParticleColor = ColorValue.INITIAL,
    initialRasterParticleColorUseTheme = StringValue.INITIAL,
    initialRasterParticleCount = LongValue.INITIAL,
    initialRasterParticleFadeOpacityFactor = DoubleValue.INITIAL,
    initialRasterParticleFadeOpacityFactorTransition = Transition.INITIAL,
    initialRasterParticleMaxSpeed = DoubleValue.INITIAL,
    initialRasterParticleResetRateFactor = DoubleValue.INITIAL,
    initialRasterParticleSpeedFactor = DoubleValue.INITIAL,
    initialRasterParticleSpeedFactorTransition = Transition.INITIAL,
    initialVisibility = VisibilityValue.INITIAL,
    initialMinZoom = LongValue.INITIAL,
    initialMaxZoom = LongValue.INITIAL,
    initialSourceLayer = StringValue.INITIAL,
    initialFilter = Filter.INITIAL,
    initialInteractionsState = LayerInteractionsState(),
  )

  /**
   * The interactions associated with this layer.
   */
  @MapboxExperimental
  public var interactionsState: LayerInteractionsState by mutableStateOf(initialInteractionsState)

  /**
   *  Displayed band of raster array source layer
   */
  @MapboxExperimental
  public var rasterParticleArrayBand: StringValue by mutableStateOf(initialRasterParticleArrayBand)
  /**
   *  Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
   */
  @MapboxExperimental
  public var rasterParticleColor: ColorValue by mutableStateOf(initialRasterParticleColor)
  /**
   *  Overrides applying of color theme for [rasterParticleColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var rasterParticleColorUseTheme: StringValue by mutableStateOf(initialRasterParticleColorUseTheme)
  /**
   *  Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   */
  @MapboxExperimental
  public var rasterParticleCount: LongValue by mutableStateOf(initialRasterParticleCount)
  /**
   *  Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   */
  @MapboxExperimental
  public var rasterParticleFadeOpacityFactor: DoubleValue by mutableStateOf(initialRasterParticleFadeOpacityFactor)
  /**
   *  Defines the transition of [rasterParticleFadeOpacityFactor].
   */
  @MapboxExperimental
  public var rasterParticleFadeOpacityFactorTransition: Transition by mutableStateOf(initialRasterParticleFadeOpacityFactorTransition)
  /**
   *  Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   */
  @MapboxExperimental
  public var rasterParticleMaxSpeed: DoubleValue by mutableStateOf(initialRasterParticleMaxSpeed)
  /**
   *  Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   */
  @MapboxExperimental
  public var rasterParticleResetRateFactor: DoubleValue by mutableStateOf(initialRasterParticleResetRateFactor)
  /**
   *  Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   */
  @MapboxExperimental
  public var rasterParticleSpeedFactor: DoubleValue by mutableStateOf(initialRasterParticleSpeedFactor)
  /**
   *  Defines the transition of [rasterParticleSpeedFactor].
   */
  @MapboxExperimental
  public var rasterParticleSpeedFactorTransition: Transition by mutableStateOf(initialRasterParticleSpeedFactorTransition)
  /**
   *  Whether this layer is displayed. Default value: "visible".
   */
  public var visibility: VisibilityValue by mutableStateOf(initialVisibility)
  /**
   *  The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: LongValue by mutableStateOf(initialMinZoom)
  /**
   *  The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: LongValue by mutableStateOf(initialMaxZoom)
  /**
   *  Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
   */
  public var sourceLayer: StringValue by mutableStateOf(initialSourceLayer)
  /**
   *  An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
   */
  public var filter: Filter by mutableStateOf(initialFilter)

  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleArrayBand(layerNode: LayerNode) {
    if (rasterParticleArrayBand.notInitial) {
      layerNode.setProperty("raster-particle-array-band", rasterParticleArrayBand.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleColor(layerNode: LayerNode) {
    if (rasterParticleColor.notInitial) {
      layerNode.setProperty("raster-particle-color", rasterParticleColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleColorUseTheme(layerNode: LayerNode) {
    if (rasterParticleColorUseTheme.notInitial) {
      layerNode.setProperty("raster-particle-color-use-theme", rasterParticleColorUseTheme.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleCount(layerNode: LayerNode) {
    if (rasterParticleCount.notInitial) {
      layerNode.setProperty("raster-particle-count", rasterParticleCount.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleFadeOpacityFactor(layerNode: LayerNode) {
    if (rasterParticleFadeOpacityFactor.notInitial) {
      layerNode.setProperty("raster-particle-fade-opacity-factor", rasterParticleFadeOpacityFactor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleFadeOpacityFactorTransition(layerNode: LayerNode) {
    if (rasterParticleFadeOpacityFactorTransition.notInitial) {
      layerNode.setProperty("raster-particle-fade-opacity-factor-transition", rasterParticleFadeOpacityFactorTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleMaxSpeed(layerNode: LayerNode) {
    if (rasterParticleMaxSpeed.notInitial) {
      layerNode.setProperty("raster-particle-max-speed", rasterParticleMaxSpeed.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleResetRateFactor(layerNode: LayerNode) {
    if (rasterParticleResetRateFactor.notInitial) {
      layerNode.setProperty("raster-particle-reset-rate-factor", rasterParticleResetRateFactor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleSpeedFactor(layerNode: LayerNode) {
    if (rasterParticleSpeedFactor.notInitial) {
      layerNode.setProperty("raster-particle-speed-factor", rasterParticleSpeedFactor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterParticleSpeedFactorTransition(layerNode: LayerNode) {
    if (rasterParticleSpeedFactorTransition.notInitial) {
      layerNode.setProperty("raster-particle-speed-factor-transition", rasterParticleSpeedFactorTransition.value)
    }
  }
  @Composable
  private fun UpdateVisibility(layerNode: LayerNode) {
    if (visibility.notInitial) {
      layerNode.setProperty("visibility", visibility.value)
    }
  }
  @Composable
  private fun UpdateMinZoom(layerNode: LayerNode) {
    if (minZoom.notInitial) {
      layerNode.setProperty("minzoom", minZoom.value)
    }
  }
  @Composable
  private fun UpdateMaxZoom(layerNode: LayerNode) {
    if (maxZoom.notInitial) {
      layerNode.setProperty("maxzoom", maxZoom.value)
    }
  }
  @Composable
  private fun UpdateSourceLayer(layerNode: LayerNode) {
    if (sourceLayer.notInitial) {
      layerNode.setProperty("source-layer", sourceLayer.value)
    }
  }
  @Composable
  private fun UpdateFilter(layerNode: LayerNode) {
    if (filter.notInitial) {
      layerNode.setProperty("filter", filter.value)
    }
  }

  @Composable
  internal fun UpdateProperties(layerNode: LayerNode) {
    UpdateRasterParticleArrayBand(layerNode)
    UpdateRasterParticleColor(layerNode)
    UpdateRasterParticleColorUseTheme(layerNode)
    UpdateRasterParticleCount(layerNode)
    UpdateRasterParticleFadeOpacityFactor(layerNode)
    UpdateRasterParticleFadeOpacityFactorTransition(layerNode)
    UpdateRasterParticleMaxSpeed(layerNode)
    UpdateRasterParticleResetRateFactor(layerNode)
    UpdateRasterParticleSpeedFactor(layerNode)
    UpdateRasterParticleSpeedFactorTransition(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.