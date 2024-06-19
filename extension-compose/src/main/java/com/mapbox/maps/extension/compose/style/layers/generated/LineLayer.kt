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
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * A stroked line.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#line)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param lineCap The display of line endings. Default value: "butt".
 * @param lineJoin The display of lines when joining. Default value: "miter".
 * @param lineMiterLimit Used to automatically convert miter joins to bevel joins for sharp angles. Default value: 2.
 * @param lineRoundLimit Used to automatically convert round joins to miter joins for shallow angles. Default value: 1.05.
 * @param lineSortKey Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
 * @param lineZOffset Vertical offset from ground, in meters. Defaults to 0. Not supported for globe projection at the moment.
 * @param lineBlur Blur applied to the line, in pixels. Default value: 0. Minimum value: 0.
 * @param lineBlurTransition Defines the transition of [lineBlur]. Default value: 0. Minimum value: 0.
 * @param lineBorderColor The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
 * @param lineBorderColorTransition Defines the transition of [lineBorderColor]. Default value: "rgba(0, 0, 0, 0)".
 * @param lineBorderWidth The width of the line border. A value of zero means no border. Default value: 0. Minimum value: 0.
 * @param lineBorderWidthTransition Defines the transition of [lineBorderWidth]. Default value: 0. Minimum value: 0.
 * @param lineColor The color with which the line will be drawn. Default value: "#000000".
 * @param lineColorTransition Defines the transition of [lineColor]. Default value: "#000000".
 * @param lineDasharray Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. Minimum value: 0.
 * @param lineDepthOcclusionFactor Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded. Default value: 1. Value range: [0, 1]
 * @param lineDepthOcclusionFactorTransition Defines the transition of [lineDepthOcclusionFactor]. Default value: 1. Value range: [0, 1]
 * @param lineEmissiveStrength Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
 * @param lineEmissiveStrengthTransition Defines the transition of [lineEmissiveStrength]. Default value: 0. Minimum value: 0.
 * @param lineGapWidth Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. Default value: 0. Minimum value: 0.
 * @param lineGapWidthTransition Defines the transition of [lineGapWidth]. Default value: 0. Minimum value: 0.
 * @param lineGradient A gradient used to color a line feature at various distances along its length. Defined using a `step` or `interpolate` expression which outputs a color for each corresponding `line-progress` input value. `line-progress` is a percentage of the line feature's total length as measured on the webmercator projected coordinate plane (a `number` between `0` and `1`). Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
 * @param lineOcclusionOpacity Opacity multiplier (multiplies line-opacity value) of the line part that is occluded by 3D objects. Value 0 hides occluded part, value 1 means the same opacity as non-occluded part. The property is not supported when `line-opacity` has data-driven styling. Default value: 0. Value range: [0, 1]
 * @param lineOcclusionOpacityTransition Defines the transition of [lineOcclusionOpacity]. Default value: 0. Value range: [0, 1]
 * @param lineOffset The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. Default value: 0.
 * @param lineOffsetTransition Defines the transition of [lineOffset]. Default value: 0.
 * @param lineOpacity The opacity at which the line will be drawn. Default value: 1. Value range: [0, 1]
 * @param lineOpacityTransition Defines the transition of [lineOpacity]. Default value: 1. Value range: [0, 1]
 * @param linePattern Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 * @param lineTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
 * @param lineTranslateTransition Defines the transition of [lineTranslate]. Default value: [0,0].
 * @param lineTranslateAnchor Controls the frame of reference for `line-translate`. Default value: "map".
 * @param lineTrimOffset The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0]. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [1,1].
 * @param lineWidth Stroke thickness. Default value: 1. Minimum value: 0.
 * @param lineWidthTransition Defines the transition of [lineWidth]. Default value: 1. Minimum value: 0.
 * @param visibility Whether this layer is displayed. Default value: "visible".
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun LineLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("line")
  },
  lineCap: LineCapValue = LineCapValue.INITIAL,
  lineJoin: LineJoinValue = LineJoinValue.INITIAL,
  lineMiterLimit: DoubleValue = DoubleValue.INITIAL,
  lineRoundLimit: DoubleValue = DoubleValue.INITIAL,
  lineSortKey: DoubleValue = DoubleValue.INITIAL,
  lineZOffset: DoubleValue = DoubleValue.INITIAL,
  lineBlur: DoubleValue = DoubleValue.INITIAL,
  lineBlurTransition: Transition = Transition.INITIAL,
  lineBorderColor: ColorValue = ColorValue.INITIAL,
  lineBorderColorTransition: Transition = Transition.INITIAL,
  lineBorderWidth: DoubleValue = DoubleValue.INITIAL,
  lineBorderWidthTransition: Transition = Transition.INITIAL,
  lineColor: ColorValue = ColorValue.INITIAL,
  lineColorTransition: Transition = Transition.INITIAL,
  lineDasharray: DoubleListValue = DoubleListValue.INITIAL,
  lineDepthOcclusionFactor: DoubleValue = DoubleValue.INITIAL,
  lineDepthOcclusionFactorTransition: Transition = Transition.INITIAL,
  lineEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  lineEmissiveStrengthTransition: Transition = Transition.INITIAL,
  lineGapWidth: DoubleValue = DoubleValue.INITIAL,
  lineGapWidthTransition: Transition = Transition.INITIAL,
  lineGradient: ColorValue = ColorValue.INITIAL,
  lineOcclusionOpacity: DoubleValue = DoubleValue.INITIAL,
  lineOcclusionOpacityTransition: Transition = Transition.INITIAL,
  lineOffset: DoubleValue = DoubleValue.INITIAL,
  lineOffsetTransition: Transition = Transition.INITIAL,
  lineOpacity: DoubleValue = DoubleValue.INITIAL,
  lineOpacityTransition: Transition = Transition.INITIAL,
  linePattern: ImageValue = ImageValue.INITIAL,
  lineTranslate: DoubleListValue = DoubleListValue.INITIAL,
  lineTranslateTransition: Transition = Transition.INITIAL,
  lineTranslateAnchor: LineTranslateAnchorValue = LineTranslateAnchorValue.INITIAL,
  lineTrimOffset: DoubleListValue = DoubleListValue.INITIAL,
  lineWidth: DoubleValue = DoubleValue.INITIAL,
  lineWidthTransition: Transition = Transition.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of LineLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "line",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (lineCap.notInitial) {
          setProperty("line-cap", lineCap.value)
        }
        if (lineJoin.notInitial) {
          setProperty("line-join", lineJoin.value)
        }
        if (lineMiterLimit.notInitial) {
          setProperty("line-miter-limit", lineMiterLimit.value)
        }
        if (lineRoundLimit.notInitial) {
          setProperty("line-round-limit", lineRoundLimit.value)
        }
        if (lineSortKey.notInitial) {
          setProperty("line-sort-key", lineSortKey.value)
        }
        if (lineZOffset.notInitial) {
          setProperty("line-z-offset", lineZOffset.value)
        }
        if (lineBlur.notInitial) {
          setProperty("line-blur", lineBlur.value)
        }
        if (lineBlurTransition.notInitial) {
          setProperty("line-blur-transition", lineBlurTransition.value)
        }
        if (lineBorderColor.notInitial) {
          setProperty("line-border-color", lineBorderColor.value)
        }
        if (lineBorderColorTransition.notInitial) {
          setProperty("line-border-color-transition", lineBorderColorTransition.value)
        }
        if (lineBorderWidth.notInitial) {
          setProperty("line-border-width", lineBorderWidth.value)
        }
        if (lineBorderWidthTransition.notInitial) {
          setProperty("line-border-width-transition", lineBorderWidthTransition.value)
        }
        if (lineColor.notInitial) {
          setProperty("line-color", lineColor.value)
        }
        if (lineColorTransition.notInitial) {
          setProperty("line-color-transition", lineColorTransition.value)
        }
        if (lineDasharray.notInitial) {
          setProperty("line-dasharray", lineDasharray.value)
        }
        if (lineDepthOcclusionFactor.notInitial) {
          setProperty("line-depth-occlusion-factor", lineDepthOcclusionFactor.value)
        }
        if (lineDepthOcclusionFactorTransition.notInitial) {
          setProperty("line-depth-occlusion-factor-transition", lineDepthOcclusionFactorTransition.value)
        }
        if (lineEmissiveStrength.notInitial) {
          setProperty("line-emissive-strength", lineEmissiveStrength.value)
        }
        if (lineEmissiveStrengthTransition.notInitial) {
          setProperty("line-emissive-strength-transition", lineEmissiveStrengthTransition.value)
        }
        if (lineGapWidth.notInitial) {
          setProperty("line-gap-width", lineGapWidth.value)
        }
        if (lineGapWidthTransition.notInitial) {
          setProperty("line-gap-width-transition", lineGapWidthTransition.value)
        }
        if (lineGradient.notInitial) {
          setProperty("line-gradient", lineGradient.value)
        }
        if (lineOcclusionOpacity.notInitial) {
          setProperty("line-occlusion-opacity", lineOcclusionOpacity.value)
        }
        if (lineOcclusionOpacityTransition.notInitial) {
          setProperty("line-occlusion-opacity-transition", lineOcclusionOpacityTransition.value)
        }
        if (lineOffset.notInitial) {
          setProperty("line-offset", lineOffset.value)
        }
        if (lineOffsetTransition.notInitial) {
          setProperty("line-offset-transition", lineOffsetTransition.value)
        }
        if (lineOpacity.notInitial) {
          setProperty("line-opacity", lineOpacity.value)
        }
        if (lineOpacityTransition.notInitial) {
          setProperty("line-opacity-transition", lineOpacityTransition.value)
        }
        if (linePattern.notInitial) {
          linePattern.styleImage?.let {
            addImage(it)
          }
          setProperty("line-pattern", linePattern.value)
        }
        if (lineTranslate.notInitial) {
          setProperty("line-translate", lineTranslate.value)
        }
        if (lineTranslateTransition.notInitial) {
          setProperty("line-translate-transition", lineTranslateTransition.value)
        }
        if (lineTranslateAnchor.notInitial) {
          setProperty("line-translate-anchor", lineTranslateAnchor.value)
        }
        if (lineTrimOffset.notInitial) {
          setProperty("line-trim-offset", lineTrimOffset.value)
        }
        if (lineWidth.notInitial) {
          setProperty("line-width", lineWidth.value)
        }
        if (lineWidthTransition.notInitial) {
          setProperty("line-width-transition", lineWidthTransition.value)
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
      update(lineCap) {
        setProperty("line-cap", lineCap.value)
      }
      update(lineJoin) {
        setProperty("line-join", lineJoin.value)
      }
      update(lineMiterLimit) {
        setProperty("line-miter-limit", lineMiterLimit.value)
      }
      update(lineRoundLimit) {
        setProperty("line-round-limit", lineRoundLimit.value)
      }
      update(lineSortKey) {
        setProperty("line-sort-key", lineSortKey.value)
      }
      update(lineZOffset) {
        setProperty("line-z-offset", lineZOffset.value)
      }
      update(lineBlur) {
        setProperty("line-blur", lineBlur.value)
      }
      update(lineBlurTransition) {
        setProperty("line-blur-transition", lineBlurTransition.value)
      }
      update(lineBorderColor) {
        setProperty("line-border-color", lineBorderColor.value)
      }
      update(lineBorderColorTransition) {
        setProperty("line-border-color-transition", lineBorderColorTransition.value)
      }
      update(lineBorderWidth) {
        setProperty("line-border-width", lineBorderWidth.value)
      }
      update(lineBorderWidthTransition) {
        setProperty("line-border-width-transition", lineBorderWidthTransition.value)
      }
      update(lineColor) {
        setProperty("line-color", lineColor.value)
      }
      update(lineColorTransition) {
        setProperty("line-color-transition", lineColorTransition.value)
      }
      update(lineDasharray) {
        setProperty("line-dasharray", lineDasharray.value)
      }
      update(lineDepthOcclusionFactor) {
        setProperty("line-depth-occlusion-factor", lineDepthOcclusionFactor.value)
      }
      update(lineDepthOcclusionFactorTransition) {
        setProperty("line-depth-occlusion-factor-transition", lineDepthOcclusionFactorTransition.value)
      }
      update(lineEmissiveStrength) {
        setProperty("line-emissive-strength", lineEmissiveStrength.value)
      }
      update(lineEmissiveStrengthTransition) {
        setProperty("line-emissive-strength-transition", lineEmissiveStrengthTransition.value)
      }
      update(lineGapWidth) {
        setProperty("line-gap-width", lineGapWidth.value)
      }
      update(lineGapWidthTransition) {
        setProperty("line-gap-width-transition", lineGapWidthTransition.value)
      }
      update(lineGradient) {
        setProperty("line-gradient", lineGradient.value)
      }
      update(lineOcclusionOpacity) {
        setProperty("line-occlusion-opacity", lineOcclusionOpacity.value)
      }
      update(lineOcclusionOpacityTransition) {
        setProperty("line-occlusion-opacity-transition", lineOcclusionOpacityTransition.value)
      }
      update(lineOffset) {
        setProperty("line-offset", lineOffset.value)
      }
      update(lineOffsetTransition) {
        setProperty("line-offset-transition", lineOffsetTransition.value)
      }
      update(lineOpacity) {
        setProperty("line-opacity", lineOpacity.value)
      }
      update(lineOpacityTransition) {
        setProperty("line-opacity-transition", lineOpacityTransition.value)
      }
      update(linePattern) {
        linePattern.styleImage?.let {
          addImage(it)
        }
        setProperty("line-pattern", linePattern.value)
      }
      update(lineTranslate) {
        setProperty("line-translate", lineTranslate.value)
      }
      update(lineTranslateTransition) {
        setProperty("line-translate-transition", lineTranslateTransition.value)
      }
      update(lineTranslateAnchor) {
        setProperty("line-translate-anchor", lineTranslateAnchor.value)
      }
      update(lineTrimOffset) {
        setProperty("line-trim-offset", lineTrimOffset.value)
      }
      update(lineWidth) {
        setProperty("line-width", lineWidth.value)
      }
      update(lineWidthTransition) {
        setProperty("line-width-transition", lineWidthTransition.value)
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