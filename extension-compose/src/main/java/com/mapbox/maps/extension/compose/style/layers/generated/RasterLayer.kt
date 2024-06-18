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
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleRangeValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * Raster map textures such as satellite imagery.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#raster)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param rasterArrayBand Displayed band of raster array source layer
 * @param rasterBrightnessMax Increase or reduce the brightness of the image. The value is the maximum brightness. Default value: 1. Value range: [0, 1]
 * @param rasterBrightnessMaxTransition Defines the transition of [rasterBrightnessMax]. Default value: 1. Value range: [0, 1]
 * @param rasterBrightnessMin Increase or reduce the brightness of the image. The value is the minimum brightness. Default value: 0. Value range: [0, 1]
 * @param rasterBrightnessMinTransition Defines the transition of [rasterBrightnessMin]. Default value: 0. Value range: [0, 1]
 * @param rasterColor Defines a color map by which to colorize a raster layer, parameterized by the `["raster-value"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-color-range`.
 * @param rasterColorMix When `raster-color` is active, specifies the combination of source RGB channels used to compute the raster value. Computed using the equation `mix.r - src.r + mix.g - src.g + mix.b - src.b + mix.a`. The first three components specify the mix of source red, green, and blue channels, respectively. The fourth component serves as a constant offset and is -not- multipled by source alpha. Source alpha is instead carried through and applied as opacity to the colorized result. Default value corresponds to RGB luminosity. Default value: [0.2126,0.7152,0.0722,0].
 * @param rasterColorMixTransition Defines the transition of [rasterColorMix]. Default value: [0.2126,0.7152,0.0722,0].
 * @param rasterColorRange When `raster-color` is active, specifies the range over which `raster-color` is tabulated. Units correspond to the computed raster value via `raster-color-mix`. Default value: [0,1].
 * @param rasterColorRangeTransition Defines the transition of [rasterColorRange]. Default value: [0,1].
 * @param rasterContrast Increase or reduce the contrast of the image. Default value: 0. Value range: [-1, 1]
 * @param rasterContrastTransition Defines the transition of [rasterContrast]. Default value: 0. Value range: [-1, 1]
 * @param rasterElevation Specifies an uniform elevation from the ground, in meters. Only supported with image sources. Default value: 0. Minimum value: 0.
 * @param rasterElevationTransition Defines the transition of [rasterElevation]. Default value: 0. Minimum value: 0.
 * @param rasterEmissiveStrength Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
 * @param rasterEmissiveStrengthTransition Defines the transition of [rasterEmissiveStrength]. Default value: 0. Minimum value: 0.
 * @param rasterFadeDuration Fade duration when a new tile is added. Default value: 300. Minimum value: 0.
 * @param rasterHueRotate Rotates hues around the color wheel. Default value: 0.
 * @param rasterHueRotateTransition Defines the transition of [rasterHueRotate]. Default value: 0.
 * @param rasterOpacity The opacity at which the image will be drawn. Default value: 1. Value range: [0, 1]
 * @param rasterOpacityTransition Defines the transition of [rasterOpacity]. Default value: 1. Value range: [0, 1]
 * @param rasterResampling The resampling/interpolation method to use for overscaling, also known as texture magnification filter Default value: "linear".
 * @param rasterSaturation Increase or reduce the saturation of the image. Default value: 0. Value range: [-1, 1]
 * @param rasterSaturationTransition Defines the transition of [rasterSaturation]. Default value: 0. Value range: [-1, 1]
 * @param visibility Whether this layer is displayed. Default value: "visible".
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun RasterLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("raster")
  },
  rasterArrayBand: StringValue = StringValue.INITIAL,
  rasterBrightnessMax: DoubleValue = DoubleValue.INITIAL,
  rasterBrightnessMaxTransition: Transition = Transition.INITIAL,
  rasterBrightnessMin: DoubleValue = DoubleValue.INITIAL,
  rasterBrightnessMinTransition: Transition = Transition.INITIAL,
  rasterColor: ColorValue = ColorValue.INITIAL,
  rasterColorMix: DoubleListValue = DoubleListValue.INITIAL,
  rasterColorMixTransition: Transition = Transition.INITIAL,
  rasterColorRange: DoubleRangeValue = DoubleRangeValue.INITIAL,
  rasterColorRangeTransition: Transition = Transition.INITIAL,
  rasterContrast: DoubleValue = DoubleValue.INITIAL,
  rasterContrastTransition: Transition = Transition.INITIAL,
  rasterElevation: DoubleValue = DoubleValue.INITIAL,
  rasterElevationTransition: Transition = Transition.INITIAL,
  rasterEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  rasterEmissiveStrengthTransition: Transition = Transition.INITIAL,
  rasterFadeDuration: DoubleValue = DoubleValue.INITIAL,
  rasterHueRotate: DoubleValue = DoubleValue.INITIAL,
  rasterHueRotateTransition: Transition = Transition.INITIAL,
  rasterOpacity: DoubleValue = DoubleValue.INITIAL,
  rasterOpacityTransition: Transition = Transition.INITIAL,
  rasterResampling: RasterResamplingValue = RasterResamplingValue.INITIAL,
  rasterSaturation: DoubleValue = DoubleValue.INITIAL,
  rasterSaturationTransition: Transition = Transition.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of RasterLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "raster",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (rasterArrayBand.notInitial) {
          setProperty("raster-array-band", rasterArrayBand.value)
        }
        if (rasterBrightnessMax.notInitial) {
          setProperty("raster-brightness-max", rasterBrightnessMax.value)
        }
        if (rasterBrightnessMaxTransition.notInitial) {
          setProperty("raster-brightness-max-transition", rasterBrightnessMaxTransition.value)
        }
        if (rasterBrightnessMin.notInitial) {
          setProperty("raster-brightness-min", rasterBrightnessMin.value)
        }
        if (rasterBrightnessMinTransition.notInitial) {
          setProperty("raster-brightness-min-transition", rasterBrightnessMinTransition.value)
        }
        if (rasterColor.notInitial) {
          setProperty("raster-color", rasterColor.value)
        }
        if (rasterColorMix.notInitial) {
          setProperty("raster-color-mix", rasterColorMix.value)
        }
        if (rasterColorMixTransition.notInitial) {
          setProperty("raster-color-mix-transition", rasterColorMixTransition.value)
        }
        if (rasterColorRange.notInitial) {
          setProperty("raster-color-range", rasterColorRange.value)
        }
        if (rasterColorRangeTransition.notInitial) {
          setProperty("raster-color-range-transition", rasterColorRangeTransition.value)
        }
        if (rasterContrast.notInitial) {
          setProperty("raster-contrast", rasterContrast.value)
        }
        if (rasterContrastTransition.notInitial) {
          setProperty("raster-contrast-transition", rasterContrastTransition.value)
        }
        if (rasterElevation.notInitial) {
          setProperty("raster-elevation", rasterElevation.value)
        }
        if (rasterElevationTransition.notInitial) {
          setProperty("raster-elevation-transition", rasterElevationTransition.value)
        }
        if (rasterEmissiveStrength.notInitial) {
          setProperty("raster-emissive-strength", rasterEmissiveStrength.value)
        }
        if (rasterEmissiveStrengthTransition.notInitial) {
          setProperty("raster-emissive-strength-transition", rasterEmissiveStrengthTransition.value)
        }
        if (rasterFadeDuration.notInitial) {
          setProperty("raster-fade-duration", rasterFadeDuration.value)
        }
        if (rasterHueRotate.notInitial) {
          setProperty("raster-hue-rotate", rasterHueRotate.value)
        }
        if (rasterHueRotateTransition.notInitial) {
          setProperty("raster-hue-rotate-transition", rasterHueRotateTransition.value)
        }
        if (rasterOpacity.notInitial) {
          setProperty("raster-opacity", rasterOpacity.value)
        }
        if (rasterOpacityTransition.notInitial) {
          setProperty("raster-opacity-transition", rasterOpacityTransition.value)
        }
        if (rasterResampling.notInitial) {
          setProperty("raster-resampling", rasterResampling.value)
        }
        if (rasterSaturation.notInitial) {
          setProperty("raster-saturation", rasterSaturation.value)
        }
        if (rasterSaturationTransition.notInitial) {
          setProperty("raster-saturation-transition", rasterSaturationTransition.value)
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
      update(rasterArrayBand) {
        setProperty("raster-array-band", rasterArrayBand.value)
      }
      update(rasterBrightnessMax) {
        setProperty("raster-brightness-max", rasterBrightnessMax.value)
      }
      update(rasterBrightnessMaxTransition) {
        setProperty("raster-brightness-max-transition", rasterBrightnessMaxTransition.value)
      }
      update(rasterBrightnessMin) {
        setProperty("raster-brightness-min", rasterBrightnessMin.value)
      }
      update(rasterBrightnessMinTransition) {
        setProperty("raster-brightness-min-transition", rasterBrightnessMinTransition.value)
      }
      update(rasterColor) {
        setProperty("raster-color", rasterColor.value)
      }
      update(rasterColorMix) {
        setProperty("raster-color-mix", rasterColorMix.value)
      }
      update(rasterColorMixTransition) {
        setProperty("raster-color-mix-transition", rasterColorMixTransition.value)
      }
      update(rasterColorRange) {
        setProperty("raster-color-range", rasterColorRange.value)
      }
      update(rasterColorRangeTransition) {
        setProperty("raster-color-range-transition", rasterColorRangeTransition.value)
      }
      update(rasterContrast) {
        setProperty("raster-contrast", rasterContrast.value)
      }
      update(rasterContrastTransition) {
        setProperty("raster-contrast-transition", rasterContrastTransition.value)
      }
      update(rasterElevation) {
        setProperty("raster-elevation", rasterElevation.value)
      }
      update(rasterElevationTransition) {
        setProperty("raster-elevation-transition", rasterElevationTransition.value)
      }
      update(rasterEmissiveStrength) {
        setProperty("raster-emissive-strength", rasterEmissiveStrength.value)
      }
      update(rasterEmissiveStrengthTransition) {
        setProperty("raster-emissive-strength-transition", rasterEmissiveStrengthTransition.value)
      }
      update(rasterFadeDuration) {
        setProperty("raster-fade-duration", rasterFadeDuration.value)
      }
      update(rasterHueRotate) {
        setProperty("raster-hue-rotate", rasterHueRotate.value)
      }
      update(rasterHueRotateTransition) {
        setProperty("raster-hue-rotate-transition", rasterHueRotateTransition.value)
      }
      update(rasterOpacity) {
        setProperty("raster-opacity", rasterOpacity.value)
      }
      update(rasterOpacityTransition) {
        setProperty("raster-opacity-transition", rasterOpacityTransition.value)
      }
      update(rasterResampling) {
        setProperty("raster-resampling", rasterResampling.value)
      }
      update(rasterSaturation) {
        setProperty("raster-saturation", rasterSaturation.value)
      }
      update(rasterSaturationTransition) {
        setProperty("raster-saturation-transition", rasterSaturationTransition.value)
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
  sourceState.UpdateProperties()
}
// End of generated file.