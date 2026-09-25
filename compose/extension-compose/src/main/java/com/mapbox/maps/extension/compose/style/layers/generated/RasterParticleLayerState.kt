// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ActionWhenNotInitial
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
public class RasterParticleLayerState
@OptIn(MapboxExperimental::class)
private constructor(
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
  @OptIn(MapboxExperimental::class)
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

  @MapboxExperimental
  private val rasterParticleArrayBandState: MutableState<StringValue> = mutableStateOf(initialRasterParticleArrayBand)
  /**
   *  Displayed band of raster array source layer
   */
  @MapboxExperimental
  public var rasterParticleArrayBand: StringValue by rasterParticleArrayBandState

  @MapboxExperimental
  private val rasterParticleColorState: MutableState<ColorValue> = mutableStateOf(initialRasterParticleColor)
  /**
   *  Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
   */
  @MapboxExperimental
  public var rasterParticleColor: ColorValue by rasterParticleColorState

  @MapboxExperimental
  private val rasterParticleColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialRasterParticleColorUseTheme)
  /**
   *  Overrides applying of color theme for [rasterParticleColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var rasterParticleColorUseTheme: StringValue by rasterParticleColorUseThemeState

  @MapboxExperimental
  private val rasterParticleCountState: MutableState<LongValue> = mutableStateOf(initialRasterParticleCount)
  /**
   *  Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   */
  @MapboxExperimental
  public var rasterParticleCount: LongValue by rasterParticleCountState

  @MapboxExperimental
  private val rasterParticleFadeOpacityFactorState: MutableState<DoubleValue> = mutableStateOf(initialRasterParticleFadeOpacityFactor)
  /**
   *  Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   */
  @MapboxExperimental
  public var rasterParticleFadeOpacityFactor: DoubleValue by rasterParticleFadeOpacityFactorState

  @MapboxExperimental
  private val rasterParticleFadeOpacityFactorTransitionState: MutableState<Transition> = mutableStateOf(initialRasterParticleFadeOpacityFactorTransition)
  /**
   *  Defines the transition of [rasterParticleFadeOpacityFactor].
   */
  @MapboxExperimental
  public var rasterParticleFadeOpacityFactorTransition: Transition by rasterParticleFadeOpacityFactorTransitionState

  @MapboxExperimental
  private val rasterParticleMaxSpeedState: MutableState<DoubleValue> = mutableStateOf(initialRasterParticleMaxSpeed)
  /**
   *  Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   */
  @MapboxExperimental
  public var rasterParticleMaxSpeed: DoubleValue by rasterParticleMaxSpeedState

  @MapboxExperimental
  private val rasterParticleResetRateFactorState: MutableState<DoubleValue> = mutableStateOf(initialRasterParticleResetRateFactor)
  /**
   *  Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   */
  @MapboxExperimental
  public var rasterParticleResetRateFactor: DoubleValue by rasterParticleResetRateFactorState

  @MapboxExperimental
  private val rasterParticleSpeedFactorState: MutableState<DoubleValue> = mutableStateOf(initialRasterParticleSpeedFactor)
  /**
   *  Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   */
  @MapboxExperimental
  public var rasterParticleSpeedFactor: DoubleValue by rasterParticleSpeedFactorState

  @MapboxExperimental
  private val rasterParticleSpeedFactorTransitionState: MutableState<Transition> = mutableStateOf(initialRasterParticleSpeedFactorTransition)
  /**
   *  Defines the transition of [rasterParticleSpeedFactor].
   */
  @MapboxExperimental
  public var rasterParticleSpeedFactorTransition: Transition by rasterParticleSpeedFactorTransitionState

  private val visibilityState: MutableState<VisibilityValue> = mutableStateOf(initialVisibility)
  /**
   *  Whether this layer is displayed. Default value: "visible".
   */
  public var visibility: VisibilityValue by visibilityState

  private val minZoomState: MutableState<LongValue> = mutableStateOf(initialMinZoom)
  /**
   *  The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: LongValue by minZoomState

  private val maxZoomState: MutableState<LongValue> = mutableStateOf(initialMaxZoom)
  /**
   *  The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: LongValue by maxZoomState

  private val sourceLayerState: MutableState<StringValue> = mutableStateOf(initialSourceLayer)
  /**
   *  Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
   */
  public var sourceLayer: StringValue by sourceLayerState

  private val filterState: MutableState<Filter> = mutableStateOf(initialFilter)
  /**
   *  An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
   */
  public var filter: Filter by filterState

  @Composable
  @OptIn(MapboxExperimental::class)
  internal fun UpdateProperties(layerNode: LayerNode) {
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleArrayBandState, "raster-particle-array-band")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleColorState, "raster-particle-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleColorUseThemeState, "raster-particle-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleCountState, "raster-particle-count")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleFadeOpacityFactorState, "raster-particle-fade-opacity-factor")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleFadeOpacityFactorTransitionState, "raster-particle-fade-opacity-factor-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleMaxSpeedState, "raster-particle-max-speed")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleResetRateFactorState, "raster-particle-reset-rate-factor")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleSpeedFactorState, "raster-particle-speed-factor")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterParticleSpeedFactorTransitionState, "raster-particle-speed-factor-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.