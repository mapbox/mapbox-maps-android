// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * Raster map textures such as satellite imagery.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-raster)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 * @param rasterArrayBand Displayed band of raster array source layer
 * @param rasterBrightnessMax Increase or reduce the brightness of the image. The value is the maximum brightness.
 * @param rasterBrightnessMin Increase or reduce the brightness of the image. The value is the minimum brightness.
 * @param rasterColor Defines a color map by which to colorize a raster layer, parameterized by the `["raster-value"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-color-range`.
 * @param rasterColorMix When `raster-color` is active, specifies the combination of source RGB channels used to compute the raster value. Computed using the equation `mix.r * src.r + mix.g * src.g + mix.b * src.b + mix.a`. The first three components specify the mix of source red, green, and blue channels, respectively. The fourth component serves as a constant offset and is *not* multipled by source alpha. Source alpha is instead carried through and applied as opacity to the colorized result. Default value corresponds to RGB luminosity.
 * @param rasterColorRange When `raster-color` is active, specifies the range over which `raster-color` is tabulated. Units correspond to the computed raster value via `raster-color-mix`.
 * @param rasterContrast Increase or reduce the contrast of the image.
 * @param rasterElevation Specifies an uniform elevation from the ground, in meters. Only supported with image sources.
 * @param rasterEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param rasterFadeDuration Fade duration when a new tile is added.
 * @param rasterHueRotate Rotates hues around the color wheel.
 * @param rasterOpacity The opacity at which the image will be drawn.
 * @param rasterResampling The resampling/interpolation method to use for overscaling, also known as texture magnification filter
 * @param rasterSaturation Increase or reduce the saturation of the image.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun RasterLayer(
  layerId: String,
  sourceId: String,
  rasterArrayBand: RasterArrayBand = RasterArrayBand.default,
  rasterBrightnessMax: RasterBrightnessMax = RasterBrightnessMax.default,
  rasterBrightnessMin: RasterBrightnessMin = RasterBrightnessMin.default,
  rasterColor: RasterColor = RasterColor.default,
  rasterColorMix: RasterColorMix = RasterColorMix.default,
  rasterColorRange: RasterColorRange = RasterColorRange.default,
  rasterContrast: RasterContrast = RasterContrast.default,
  rasterElevation: RasterElevation = RasterElevation.default,
  rasterEmissiveStrength: RasterEmissiveStrength = RasterEmissiveStrength.default,
  rasterFadeDuration: RasterFadeDuration = RasterFadeDuration.default,
  rasterHueRotate: RasterHueRotate = RasterHueRotate.default,
  rasterOpacity: RasterOpacity = RasterOpacity.default,
  rasterResampling: RasterResampling = RasterResampling.default,
  rasterSaturation: RasterSaturation = RasterSaturation.default,
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
        layerType = "raster",
        layerId = layerId,
        sourceId = sourceId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (rasterArrayBand != RasterArrayBand.default) {
          setProperty(RasterArrayBand.NAME, rasterArrayBand.value)
        }
        if (rasterBrightnessMax != RasterBrightnessMax.default) {
          setProperty(RasterBrightnessMax.NAME, rasterBrightnessMax.value)
        }
        if (rasterBrightnessMin != RasterBrightnessMin.default) {
          setProperty(RasterBrightnessMin.NAME, rasterBrightnessMin.value)
        }
        if (rasterColor != RasterColor.default) {
          setProperty(RasterColor.NAME, rasterColor.value)
        }
        if (rasterColorMix != RasterColorMix.default) {
          setProperty(RasterColorMix.NAME, rasterColorMix.value)
        }
        if (rasterColorRange != RasterColorRange.default) {
          setProperty(RasterColorRange.NAME, rasterColorRange.value)
        }
        if (rasterContrast != RasterContrast.default) {
          setProperty(RasterContrast.NAME, rasterContrast.value)
        }
        if (rasterElevation != RasterElevation.default) {
          setProperty(RasterElevation.NAME, rasterElevation.value)
        }
        if (rasterEmissiveStrength != RasterEmissiveStrength.default) {
          setProperty(RasterEmissiveStrength.NAME, rasterEmissiveStrength.value)
        }
        if (rasterFadeDuration != RasterFadeDuration.default) {
          setProperty(RasterFadeDuration.NAME, rasterFadeDuration.value)
        }
        if (rasterHueRotate != RasterHueRotate.default) {
          setProperty(RasterHueRotate.NAME, rasterHueRotate.value)
        }
        if (rasterOpacity != RasterOpacity.default) {
          setProperty(RasterOpacity.NAME, rasterOpacity.value)
        }
        if (rasterResampling != RasterResampling.default) {
          setProperty(RasterResampling.NAME, rasterResampling.value)
        }
        if (rasterSaturation != RasterSaturation.default) {
          setProperty(RasterSaturation.NAME, rasterSaturation.value)
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
      update(layerId) {
        setConstructorProperty("id", Value(layerId))
      }
      update(sourceId) {
        setConstructorProperty("source", Value(sourceId))
      }
      update(rasterArrayBand) {
        setProperty(RasterArrayBand.NAME, rasterArrayBand.value)
      }
      update(rasterBrightnessMax) {
        setProperty(RasterBrightnessMax.NAME, rasterBrightnessMax.value)
      }
      update(rasterBrightnessMin) {
        setProperty(RasterBrightnessMin.NAME, rasterBrightnessMin.value)
      }
      update(rasterColor) {
        setProperty(RasterColor.NAME, rasterColor.value)
      }
      update(rasterColorMix) {
        setProperty(RasterColorMix.NAME, rasterColorMix.value)
      }
      update(rasterColorRange) {
        setProperty(RasterColorRange.NAME, rasterColorRange.value)
      }
      update(rasterContrast) {
        setProperty(RasterContrast.NAME, rasterContrast.value)
      }
      update(rasterElevation) {
        setProperty(RasterElevation.NAME, rasterElevation.value)
      }
      update(rasterEmissiveStrength) {
        setProperty(RasterEmissiveStrength.NAME, rasterEmissiveStrength.value)
      }
      update(rasterFadeDuration) {
        setProperty(RasterFadeDuration.NAME, rasterFadeDuration.value)
      }
      update(rasterHueRotate) {
        setProperty(RasterHueRotate.NAME, rasterHueRotate.value)
      }
      update(rasterOpacity) {
        setProperty(RasterOpacity.NAME, rasterOpacity.value)
      }
      update(rasterResampling) {
        setProperty(RasterResampling.NAME, rasterResampling.value)
      }
      update(rasterSaturation) {
        setProperty(RasterSaturation.NAME, rasterSaturation.value)
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