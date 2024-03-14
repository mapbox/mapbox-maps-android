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
 * A filled circle.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-circle)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 * @param circleSortKey Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
 * @param circleBlur Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
 * @param circleColor The fill color of the circle.
 * @param circleEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param circleOpacity The opacity at which the circle will be drawn.
 * @param circlePitchAlignment Orientation of circle when map is pitched.
 * @param circlePitchScale Controls the scaling behavior of the circle when the map is pitched.
 * @param circleRadius Circle radius.
 * @param circleStrokeColor The stroke color of the circle.
 * @param circleStrokeOpacity The opacity of the circle's stroke.
 * @param circleStrokeWidth The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
 * @param circleTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
 * @param circleTranslateAnchor Controls the frame of reference for `circle-translate`.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun CircleLayer(
  layerId: String,
  sourceId: String,
  circleSortKey: CircleSortKey = CircleSortKey.default,
  circleBlur: CircleBlur = CircleBlur.default,
  circleColor: CircleColor = CircleColor.default,
  circleEmissiveStrength: CircleEmissiveStrength = CircleEmissiveStrength.default,
  circleOpacity: CircleOpacity = CircleOpacity.default,
  circlePitchAlignment: CirclePitchAlignment = CirclePitchAlignment.default,
  circlePitchScale: CirclePitchScale = CirclePitchScale.default,
  circleRadius: CircleRadius = CircleRadius.default,
  circleStrokeColor: CircleStrokeColor = CircleStrokeColor.default,
  circleStrokeOpacity: CircleStrokeOpacity = CircleStrokeOpacity.default,
  circleStrokeWidth: CircleStrokeWidth = CircleStrokeWidth.default,
  circleTranslate: CircleTranslate = CircleTranslate.default,
  circleTranslateAnchor: CircleTranslateAnchor = CircleTranslateAnchor.default,
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
        layerType = "circle",
        layerId = layerId,
        sourceId = sourceId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (circleSortKey != CircleSortKey.default) {
          setProperty(CircleSortKey.NAME, circleSortKey.value)
        }
        if (circleBlur != CircleBlur.default) {
          setProperty(CircleBlur.NAME, circleBlur.value)
        }
        if (circleColor != CircleColor.default) {
          setProperty(CircleColor.NAME, circleColor.value)
        }
        if (circleEmissiveStrength != CircleEmissiveStrength.default) {
          setProperty(CircleEmissiveStrength.NAME, circleEmissiveStrength.value)
        }
        if (circleOpacity != CircleOpacity.default) {
          setProperty(CircleOpacity.NAME, circleOpacity.value)
        }
        if (circlePitchAlignment != CirclePitchAlignment.default) {
          setProperty(CirclePitchAlignment.NAME, circlePitchAlignment.value)
        }
        if (circlePitchScale != CirclePitchScale.default) {
          setProperty(CirclePitchScale.NAME, circlePitchScale.value)
        }
        if (circleRadius != CircleRadius.default) {
          setProperty(CircleRadius.NAME, circleRadius.value)
        }
        if (circleStrokeColor != CircleStrokeColor.default) {
          setProperty(CircleStrokeColor.NAME, circleStrokeColor.value)
        }
        if (circleStrokeOpacity != CircleStrokeOpacity.default) {
          setProperty(CircleStrokeOpacity.NAME, circleStrokeOpacity.value)
        }
        if (circleStrokeWidth != CircleStrokeWidth.default) {
          setProperty(CircleStrokeWidth.NAME, circleStrokeWidth.value)
        }
        if (circleTranslate != CircleTranslate.default) {
          setProperty(CircleTranslate.NAME, circleTranslate.value)
        }
        if (circleTranslateAnchor != CircleTranslateAnchor.default) {
          setProperty(CircleTranslateAnchor.NAME, circleTranslateAnchor.value)
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
      update(circleSortKey) {
        setProperty(CircleSortKey.NAME, circleSortKey.value)
      }
      update(circleBlur) {
        setProperty(CircleBlur.NAME, circleBlur.value)
      }
      update(circleColor) {
        setProperty(CircleColor.NAME, circleColor.value)
      }
      update(circleEmissiveStrength) {
        setProperty(CircleEmissiveStrength.NAME, circleEmissiveStrength.value)
      }
      update(circleOpacity) {
        setProperty(CircleOpacity.NAME, circleOpacity.value)
      }
      update(circlePitchAlignment) {
        setProperty(CirclePitchAlignment.NAME, circlePitchAlignment.value)
      }
      update(circlePitchScale) {
        setProperty(CirclePitchScale.NAME, circlePitchScale.value)
      }
      update(circleRadius) {
        setProperty(CircleRadius.NAME, circleRadius.value)
      }
      update(circleStrokeColor) {
        setProperty(CircleStrokeColor.NAME, circleStrokeColor.value)
      }
      update(circleStrokeOpacity) {
        setProperty(CircleStrokeOpacity.NAME, circleStrokeOpacity.value)
      }
      update(circleStrokeWidth) {
        setProperty(CircleStrokeWidth.NAME, circleStrokeWidth.value)
      }
      update(circleTranslate) {
        setProperty(CircleTranslate.NAME, circleTranslate.value)
      }
      update(circleTranslateAnchor) {
        setProperty(CircleTranslateAnchor.NAME, circleTranslateAnchor.value)
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