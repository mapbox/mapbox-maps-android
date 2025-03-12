// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
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
public class RasterLayerState private constructor(
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

  /**
   *  Displayed band of raster array source layer. Defaults to the first band if not set.
   */
  @MapboxExperimental
  public var rasterArrayBand: StringValue by mutableStateOf(initialRasterArrayBand)
  /**
   *  Increase or reduce the brightness of the image. The value is the maximum brightness. Default value: 1. Value range: [0, 1]
   */
  public var rasterBrightnessMax: DoubleValue by mutableStateOf(initialRasterBrightnessMax)
  /**
   *  Defines the transition of [rasterBrightnessMax].
   */
  public var rasterBrightnessMaxTransition: Transition by mutableStateOf(initialRasterBrightnessMaxTransition)
  /**
   *  Increase or reduce the brightness of the image. The value is the minimum brightness. Default value: 0. Value range: [0, 1]
   */
  public var rasterBrightnessMin: DoubleValue by mutableStateOf(initialRasterBrightnessMin)
  /**
   *  Defines the transition of [rasterBrightnessMin].
   */
  public var rasterBrightnessMinTransition: Transition by mutableStateOf(initialRasterBrightnessMinTransition)
  /**
   *  Defines a color map by which to colorize a raster layer, parameterized by the `["raster-value"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-color-range`.
   */
  public var rasterColor: ColorValue by mutableStateOf(initialRasterColor)
  /**
   *  Overrides applying of color theme for [rasterColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var rasterColorUseTheme: StringValue by mutableStateOf(initialRasterColorUseTheme)
  /**
   *  When `raster-color` is active, specifies the combination of source RGB channels used to compute the raster value. Computed using the equation `mix.r - src.r + mix.g - src.g + mix.b - src.b + mix.a`. The first three components specify the mix of source red, green, and blue channels, respectively. The fourth component serves as a constant offset and is -not- multipled by source alpha. Source alpha is instead carried through and applied as opacity to the colorized result. Default value corresponds to RGB luminosity. Default value: [0.2126,0.7152,0.0722,0].
   */
  public var rasterColorMix: DoubleListValue by mutableStateOf(initialRasterColorMix)
  /**
   *  Defines the transition of [rasterColorMix].
   */
  public var rasterColorMixTransition: Transition by mutableStateOf(initialRasterColorMixTransition)
  /**
   *  When `raster-color` is active, specifies the range over which `raster-color` is tabulated. Units correspond to the computed raster value via `raster-color-mix`. For `rasterarray` sources, if `raster-color-range` is unspecified, the source's stated data range is used.
   */
  public var rasterColorRange: DoubleRangeValue by mutableStateOf(initialRasterColorRange)
  /**
   *  Defines the transition of [rasterColorRange].
   */
  public var rasterColorRangeTransition: Transition by mutableStateOf(initialRasterColorRangeTransition)
  /**
   *  Increase or reduce the contrast of the image. Default value: 0. Value range: [-1, 1]
   */
  public var rasterContrast: DoubleValue by mutableStateOf(initialRasterContrast)
  /**
   *  Defines the transition of [rasterContrast].
   */
  public var rasterContrastTransition: Transition by mutableStateOf(initialRasterContrastTransition)
  /**
   *  Specifies an uniform elevation from the ground, in meters. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var rasterElevation: DoubleValue by mutableStateOf(initialRasterElevation)
  /**
   *  Defines the transition of [rasterElevation].
   */
  @MapboxExperimental
  public var rasterElevationTransition: Transition by mutableStateOf(initialRasterElevationTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of rasterEmissiveStrength is in intensity.
   */
  public var rasterEmissiveStrength: DoubleValue by mutableStateOf(initialRasterEmissiveStrength)
  /**
   *  Defines the transition of [rasterEmissiveStrength].
   */
  public var rasterEmissiveStrengthTransition: Transition by mutableStateOf(initialRasterEmissiveStrengthTransition)
  /**
   *  Fade duration when a new tile is added. Default value: 300. Minimum value: 0. The unit of rasterFadeDuration is in milliseconds.
   */
  public var rasterFadeDuration: DoubleValue by mutableStateOf(initialRasterFadeDuration)
  /**
   *  Rotates hues around the color wheel. Default value: 0. The unit of rasterHueRotate is in degrees.
   */
  public var rasterHueRotate: DoubleValue by mutableStateOf(initialRasterHueRotate)
  /**
   *  Defines the transition of [rasterHueRotate].
   */
  public var rasterHueRotateTransition: Transition by mutableStateOf(initialRasterHueRotateTransition)
  /**
   *  The opacity at which the image will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var rasterOpacity: DoubleValue by mutableStateOf(initialRasterOpacity)
  /**
   *  Defines the transition of [rasterOpacity].
   */
  public var rasterOpacityTransition: Transition by mutableStateOf(initialRasterOpacityTransition)
  /**
   *  The resampling/interpolation method to use for overscaling, also known as texture magnification filter Default value: "linear".
   */
  public var rasterResampling: RasterResamplingValue by mutableStateOf(initialRasterResampling)
  /**
   *  Increase or reduce the saturation of the image. Default value: 0. Value range: [-1, 1]
   */
  public var rasterSaturation: DoubleValue by mutableStateOf(initialRasterSaturation)
  /**
   *  Defines the transition of [rasterSaturation].
   */
  public var rasterSaturationTransition: Transition by mutableStateOf(initialRasterSaturationTransition)
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
  private fun UpdateRasterArrayBand(layerNode: LayerNode) {
    if (rasterArrayBand.notInitial) {
      layerNode.setProperty("raster-array-band", rasterArrayBand.value)
    }
  }
  @Composable
  private fun UpdateRasterBrightnessMax(layerNode: LayerNode) {
    if (rasterBrightnessMax.notInitial) {
      layerNode.setProperty("raster-brightness-max", rasterBrightnessMax.value)
    }
  }
  @Composable
  private fun UpdateRasterBrightnessMaxTransition(layerNode: LayerNode) {
    if (rasterBrightnessMaxTransition.notInitial) {
      layerNode.setProperty("raster-brightness-max-transition", rasterBrightnessMaxTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterBrightnessMin(layerNode: LayerNode) {
    if (rasterBrightnessMin.notInitial) {
      layerNode.setProperty("raster-brightness-min", rasterBrightnessMin.value)
    }
  }
  @Composable
  private fun UpdateRasterBrightnessMinTransition(layerNode: LayerNode) {
    if (rasterBrightnessMinTransition.notInitial) {
      layerNode.setProperty("raster-brightness-min-transition", rasterBrightnessMinTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterColor(layerNode: LayerNode) {
    if (rasterColor.notInitial) {
      layerNode.setProperty("raster-color", rasterColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterColorUseTheme(layerNode: LayerNode) {
    if (rasterColorUseTheme.notInitial) {
      layerNode.setProperty("raster-color-use-theme", rasterColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateRasterColorMix(layerNode: LayerNode) {
    if (rasterColorMix.notInitial) {
      layerNode.setProperty("raster-color-mix", rasterColorMix.value)
    }
  }
  @Composable
  private fun UpdateRasterColorMixTransition(layerNode: LayerNode) {
    if (rasterColorMixTransition.notInitial) {
      layerNode.setProperty("raster-color-mix-transition", rasterColorMixTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterColorRange(layerNode: LayerNode) {
    if (rasterColorRange.notInitial) {
      layerNode.setProperty("raster-color-range", rasterColorRange.value)
    }
  }
  @Composable
  private fun UpdateRasterColorRangeTransition(layerNode: LayerNode) {
    if (rasterColorRangeTransition.notInitial) {
      layerNode.setProperty("raster-color-range-transition", rasterColorRangeTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterContrast(layerNode: LayerNode) {
    if (rasterContrast.notInitial) {
      layerNode.setProperty("raster-contrast", rasterContrast.value)
    }
  }
  @Composable
  private fun UpdateRasterContrastTransition(layerNode: LayerNode) {
    if (rasterContrastTransition.notInitial) {
      layerNode.setProperty("raster-contrast-transition", rasterContrastTransition.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterElevation(layerNode: LayerNode) {
    if (rasterElevation.notInitial) {
      layerNode.setProperty("raster-elevation", rasterElevation.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateRasterElevationTransition(layerNode: LayerNode) {
    if (rasterElevationTransition.notInitial) {
      layerNode.setProperty("raster-elevation-transition", rasterElevationTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterEmissiveStrength(layerNode: LayerNode) {
    if (rasterEmissiveStrength.notInitial) {
      layerNode.setProperty("raster-emissive-strength", rasterEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateRasterEmissiveStrengthTransition(layerNode: LayerNode) {
    if (rasterEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("raster-emissive-strength-transition", rasterEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterFadeDuration(layerNode: LayerNode) {
    if (rasterFadeDuration.notInitial) {
      layerNode.setProperty("raster-fade-duration", rasterFadeDuration.value)
    }
  }
  @Composable
  private fun UpdateRasterHueRotate(layerNode: LayerNode) {
    if (rasterHueRotate.notInitial) {
      layerNode.setProperty("raster-hue-rotate", rasterHueRotate.value)
    }
  }
  @Composable
  private fun UpdateRasterHueRotateTransition(layerNode: LayerNode) {
    if (rasterHueRotateTransition.notInitial) {
      layerNode.setProperty("raster-hue-rotate-transition", rasterHueRotateTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterOpacity(layerNode: LayerNode) {
    if (rasterOpacity.notInitial) {
      layerNode.setProperty("raster-opacity", rasterOpacity.value)
    }
  }
  @Composable
  private fun UpdateRasterOpacityTransition(layerNode: LayerNode) {
    if (rasterOpacityTransition.notInitial) {
      layerNode.setProperty("raster-opacity-transition", rasterOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateRasterResampling(layerNode: LayerNode) {
    if (rasterResampling.notInitial) {
      layerNode.setProperty("raster-resampling", rasterResampling.value)
    }
  }
  @Composable
  private fun UpdateRasterSaturation(layerNode: LayerNode) {
    if (rasterSaturation.notInitial) {
      layerNode.setProperty("raster-saturation", rasterSaturation.value)
    }
  }
  @Composable
  private fun UpdateRasterSaturationTransition(layerNode: LayerNode) {
    if (rasterSaturationTransition.notInitial) {
      layerNode.setProperty("raster-saturation-transition", rasterSaturationTransition.value)
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
    UpdateRasterArrayBand(layerNode)
    UpdateRasterBrightnessMax(layerNode)
    UpdateRasterBrightnessMaxTransition(layerNode)
    UpdateRasterBrightnessMin(layerNode)
    UpdateRasterBrightnessMinTransition(layerNode)
    UpdateRasterColor(layerNode)
    UpdateRasterColorUseTheme(layerNode)
    UpdateRasterColorMix(layerNode)
    UpdateRasterColorMixTransition(layerNode)
    UpdateRasterColorRange(layerNode)
    UpdateRasterColorRangeTransition(layerNode)
    UpdateRasterContrast(layerNode)
    UpdateRasterContrastTransition(layerNode)
    UpdateRasterElevation(layerNode)
    UpdateRasterElevationTransition(layerNode)
    UpdateRasterEmissiveStrength(layerNode)
    UpdateRasterEmissiveStrengthTransition(layerNode)
    UpdateRasterFadeDuration(layerNode)
    UpdateRasterHueRotate(layerNode)
    UpdateRasterHueRotateTransition(layerNode)
    UpdateRasterOpacity(layerNode)
    UpdateRasterOpacityTransition(layerNode)
    UpdateRasterResampling(layerNode)
    UpdateRasterSaturation(layerNode)
    UpdateRasterSaturationTransition(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.