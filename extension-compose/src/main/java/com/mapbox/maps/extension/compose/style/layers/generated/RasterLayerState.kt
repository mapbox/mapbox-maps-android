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
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleRangeValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [RasterLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class RasterLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialRasterArrayBand: StringValue,
  initialRasterBrightnessMax: DoubleValue,
  initialRasterBrightnessMaxTransition: Transition,
  initialRasterBrightnessMin: DoubleValue,
  initialRasterBrightnessMinTransition: Transition,
  initialRasterColor: ColorValue,
  initialRasterColorUseTheme: StringValue,
  initialRasterColorMix: DoubleListValue,
  initialRasterColorMixTransition: Transition,
  initialRasterColorRange: DoubleRangeValue,
  initialRasterColorRangeTransition: Transition,
  initialRasterContrast: DoubleValue,
  initialRasterContrastTransition: Transition,
  initialRasterElevation: DoubleValue,
  initialRasterElevationTransition: Transition,
  initialRasterEmissiveStrength: DoubleValue,
  initialRasterEmissiveStrengthTransition: Transition,
  initialRasterFadeDuration: DoubleValue,
  initialRasterHueRotate: DoubleValue,
  initialRasterHueRotateTransition: Transition,
  initialRasterOpacity: DoubleValue,
  initialRasterOpacityTransition: Transition,
  initialRasterResampling: RasterResamplingValue,
  initialRasterSaturation: DoubleValue,
  initialRasterSaturationTransition: Transition,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [RasterLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialRasterArrayBand = StringValue.INITIAL,
    initialRasterBrightnessMax = DoubleValue.INITIAL,
    initialRasterBrightnessMaxTransition = Transition.INITIAL,
    initialRasterBrightnessMin = DoubleValue.INITIAL,
    initialRasterBrightnessMinTransition = Transition.INITIAL,
    initialRasterColor = ColorValue.INITIAL,
    initialRasterColorUseTheme = StringValue.INITIAL,
    initialRasterColorMix = DoubleListValue.INITIAL,
    initialRasterColorMixTransition = Transition.INITIAL,
    initialRasterColorRange = DoubleRangeValue.INITIAL,
    initialRasterColorRangeTransition = Transition.INITIAL,
    initialRasterContrast = DoubleValue.INITIAL,
    initialRasterContrastTransition = Transition.INITIAL,
    initialRasterElevation = DoubleValue.INITIAL,
    initialRasterElevationTransition = Transition.INITIAL,
    initialRasterEmissiveStrength = DoubleValue.INITIAL,
    initialRasterEmissiveStrengthTransition = Transition.INITIAL,
    initialRasterFadeDuration = DoubleValue.INITIAL,
    initialRasterHueRotate = DoubleValue.INITIAL,
    initialRasterHueRotateTransition = Transition.INITIAL,
    initialRasterOpacity = DoubleValue.INITIAL,
    initialRasterOpacityTransition = Transition.INITIAL,
    initialRasterResampling = RasterResamplingValue.INITIAL,
    initialRasterSaturation = DoubleValue.INITIAL,
    initialRasterSaturationTransition = Transition.INITIAL,
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
  private val rasterArrayBandState: MutableState<StringValue> = mutableStateOf(initialRasterArrayBand)
  /**
   *  Displayed band of raster array source layer. Defaults to the first band if not set.
   */
  @MapboxExperimental
  public var rasterArrayBand: StringValue by rasterArrayBandState

  private val rasterBrightnessMaxState: MutableState<DoubleValue> = mutableStateOf(initialRasterBrightnessMax)
  /**
   *  Increase or reduce the brightness of the image. The value is the maximum brightness. Default value: 1. Value range: [0, 1]
   */
  public var rasterBrightnessMax: DoubleValue by rasterBrightnessMaxState

  private val rasterBrightnessMaxTransitionState: MutableState<Transition> = mutableStateOf(initialRasterBrightnessMaxTransition)
  /**
   *  Defines the transition of [rasterBrightnessMax].
   */
  public var rasterBrightnessMaxTransition: Transition by rasterBrightnessMaxTransitionState

  private val rasterBrightnessMinState: MutableState<DoubleValue> = mutableStateOf(initialRasterBrightnessMin)
  /**
   *  Increase or reduce the brightness of the image. The value is the minimum brightness. Default value: 0. Value range: [0, 1]
   */
  public var rasterBrightnessMin: DoubleValue by rasterBrightnessMinState

  private val rasterBrightnessMinTransitionState: MutableState<Transition> = mutableStateOf(initialRasterBrightnessMinTransition)
  /**
   *  Defines the transition of [rasterBrightnessMin].
   */
  public var rasterBrightnessMinTransition: Transition by rasterBrightnessMinTransitionState

  private val rasterColorState: MutableState<ColorValue> = mutableStateOf(initialRasterColor)
  /**
   *  Defines a color map by which to colorize a raster layer, parameterized by the `["raster-value"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-color-range`.
   */
  public var rasterColor: ColorValue by rasterColorState

  @MapboxExperimental
  private val rasterColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialRasterColorUseTheme)
  /**
   *  Overrides applying of color theme for [rasterColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var rasterColorUseTheme: StringValue by rasterColorUseThemeState

  private val rasterColorMixState: MutableState<DoubleListValue> = mutableStateOf(initialRasterColorMix)
  /**
   *  When `raster-color` is active, specifies the combination of source RGB channels used to compute the raster value. Computed using the equation `mix.r - src.r + mix.g - src.g + mix.b - src.b + mix.a`. The first three components specify the mix of source red, green, and blue channels, respectively. The fourth component serves as a constant offset and is -not- multipled by source alpha. Source alpha is instead carried through and applied as opacity to the colorized result. Default value corresponds to RGB luminosity. Default value: [0.2126,0.7152,0.0722,0].
   */
  public var rasterColorMix: DoubleListValue by rasterColorMixState

  private val rasterColorMixTransitionState: MutableState<Transition> = mutableStateOf(initialRasterColorMixTransition)
  /**
   *  Defines the transition of [rasterColorMix].
   */
  public var rasterColorMixTransition: Transition by rasterColorMixTransitionState

  private val rasterColorRangeState: MutableState<DoubleRangeValue> = mutableStateOf(initialRasterColorRange)
  /**
   *  When `raster-color` is active, specifies the range over which `raster-color` is tabulated. Units correspond to the computed raster value via `raster-color-mix`. For `rasterarray` sources, if `raster-color-range` is unspecified, the source's stated data range is used.
   */
  public var rasterColorRange: DoubleRangeValue by rasterColorRangeState

  private val rasterColorRangeTransitionState: MutableState<Transition> = mutableStateOf(initialRasterColorRangeTransition)
  /**
   *  Defines the transition of [rasterColorRange].
   */
  public var rasterColorRangeTransition: Transition by rasterColorRangeTransitionState

  private val rasterContrastState: MutableState<DoubleValue> = mutableStateOf(initialRasterContrast)
  /**
   *  Increase or reduce the contrast of the image. Default value: 0. Value range: [-1, 1]
   */
  public var rasterContrast: DoubleValue by rasterContrastState

  private val rasterContrastTransitionState: MutableState<Transition> = mutableStateOf(initialRasterContrastTransition)
  /**
   *  Defines the transition of [rasterContrast].
   */
  public var rasterContrastTransition: Transition by rasterContrastTransitionState

  @MapboxExperimental
  private val rasterElevationState: MutableState<DoubleValue> = mutableStateOf(initialRasterElevation)
  /**
   *  Defines an uniform elevation from the base specified in raster-elevation-reference, in meters. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var rasterElevation: DoubleValue by rasterElevationState

  @MapboxExperimental
  private val rasterElevationTransitionState: MutableState<Transition> = mutableStateOf(initialRasterElevationTransition)
  /**
   *  Defines the transition of [rasterElevation].
   */
  @MapboxExperimental
  public var rasterElevationTransition: Transition by rasterElevationTransitionState

  private val rasterEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialRasterEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of rasterEmissiveStrength is in intensity.
   */
  public var rasterEmissiveStrength: DoubleValue by rasterEmissiveStrengthState

  private val rasterEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialRasterEmissiveStrengthTransition)
  /**
   *  Defines the transition of [rasterEmissiveStrength].
   */
  public var rasterEmissiveStrengthTransition: Transition by rasterEmissiveStrengthTransitionState

  private val rasterFadeDurationState: MutableState<DoubleValue> = mutableStateOf(initialRasterFadeDuration)
  /**
   *  Fade duration when a new tile is added. Default value: 300. Minimum value: 0. The unit of rasterFadeDuration is in milliseconds.
   */
  public var rasterFadeDuration: DoubleValue by rasterFadeDurationState

  private val rasterHueRotateState: MutableState<DoubleValue> = mutableStateOf(initialRasterHueRotate)
  /**
   *  Rotates hues around the color wheel. Default value: 0. The unit of rasterHueRotate is in degrees.
   */
  public var rasterHueRotate: DoubleValue by rasterHueRotateState

  private val rasterHueRotateTransitionState: MutableState<Transition> = mutableStateOf(initialRasterHueRotateTransition)
  /**
   *  Defines the transition of [rasterHueRotate].
   */
  public var rasterHueRotateTransition: Transition by rasterHueRotateTransitionState

  private val rasterOpacityState: MutableState<DoubleValue> = mutableStateOf(initialRasterOpacity)
  /**
   *  The opacity at which the image will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var rasterOpacity: DoubleValue by rasterOpacityState

  private val rasterOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialRasterOpacityTransition)
  /**
   *  Defines the transition of [rasterOpacity].
   */
  public var rasterOpacityTransition: Transition by rasterOpacityTransitionState

  private val rasterResamplingState: MutableState<RasterResamplingValue> = mutableStateOf(initialRasterResampling)
  /**
   *  The resampling/interpolation method to use for overscaling, also known as texture magnification filter Default value: "linear".
   */
  public var rasterResampling: RasterResamplingValue by rasterResamplingState

  private val rasterSaturationState: MutableState<DoubleValue> = mutableStateOf(initialRasterSaturation)
  /**
   *  Increase or reduce the saturation of the image. Default value: 0. Value range: [-1, 1]
   */
  public var rasterSaturation: DoubleValue by rasterSaturationState

  private val rasterSaturationTransitionState: MutableState<Transition> = mutableStateOf(initialRasterSaturationTransition)
  /**
   *  Defines the transition of [rasterSaturation].
   */
  public var rasterSaturationTransition: Transition by rasterSaturationTransitionState

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
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterArrayBandState, "raster-array-band")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterBrightnessMaxState, "raster-brightness-max")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterBrightnessMaxTransitionState, "raster-brightness-max-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterBrightnessMinState, "raster-brightness-min")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterBrightnessMinTransitionState, "raster-brightness-min-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterColorState, "raster-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterColorUseThemeState, "raster-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterColorMixState, "raster-color-mix")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterColorMixTransitionState, "raster-color-mix-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterColorRangeState, "raster-color-range")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterColorRangeTransitionState, "raster-color-range-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterContrastState, "raster-contrast")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterContrastTransitionState, "raster-contrast-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterElevationState, "raster-elevation")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterElevationTransitionState, "raster-elevation-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterEmissiveStrengthState, "raster-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterEmissiveStrengthTransitionState, "raster-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterFadeDurationState, "raster-fade-duration")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterHueRotateState, "raster-hue-rotate")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterHueRotateTransitionState, "raster-hue-rotate-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterOpacityState, "raster-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterOpacityTransitionState, "raster-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterResamplingState, "raster-resampling")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterSaturationState, "raster-saturation")
    ActionWhenNotInitial(layerNode.setPropertyAction, rasterSaturationTransitionState, "raster-saturation-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.