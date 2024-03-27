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
 * Client-side hillshading visualization based on DEM data. Currently, the implementation only supports Mapbox Terrain RGB and Mapzen Terrarium tiles.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-hillshade)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 * @param hillshadeAccentColor The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
 * @param hillshadeEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param hillshadeExaggeration Intensity of the hillshade
 * @param hillshadeHighlightColor The shading color of areas that faces towards the light source.
 * @param hillshadeIlluminationAnchor Direction of light source when map is rotated.
 * @param hillshadeIlluminationDirection The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead.
 * @param hillshadeShadowColor The shading color of areas that face away from the light source.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun HillshadeLayer(
  layerId: String,
  sourceId: String,
  hillshadeAccentColor: HillshadeAccentColor = HillshadeAccentColor.default,
  hillshadeAccentColorTransition: Transition = Transition.default,
  hillshadeEmissiveStrength: HillshadeEmissiveStrength = HillshadeEmissiveStrength.default,
  hillshadeEmissiveStrengthTransition: Transition = Transition.default,
  hillshadeExaggeration: HillshadeExaggeration = HillshadeExaggeration.default,
  hillshadeExaggerationTransition: Transition = Transition.default,
  hillshadeHighlightColor: HillshadeHighlightColor = HillshadeHighlightColor.default,
  hillshadeHighlightColorTransition: Transition = Transition.default,
  hillshadeIlluminationAnchor: HillshadeIlluminationAnchor = HillshadeIlluminationAnchor.default,
  hillshadeIlluminationDirection: HillshadeIlluminationDirection = HillshadeIlluminationDirection.default,
  hillshadeShadowColor: HillshadeShadowColor = HillshadeShadowColor.default,
  hillshadeShadowColorTransition: Transition = Transition.default,
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
        layerType = "hillshade",
        layerId = layerId,
        sourceId = sourceId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (hillshadeAccentColor != HillshadeAccentColor.default) {
          setProperty(HillshadeAccentColor.NAME, hillshadeAccentColor.value)
        }
        if (hillshadeAccentColorTransition != Transition.default) {
          setProperty(HillshadeAccentColor.TRANSITION_NAME, hillshadeAccentColorTransition.value)
        }
        if (hillshadeEmissiveStrength != HillshadeEmissiveStrength.default) {
          setProperty(HillshadeEmissiveStrength.NAME, hillshadeEmissiveStrength.value)
        }
        if (hillshadeEmissiveStrengthTransition != Transition.default) {
          setProperty(HillshadeEmissiveStrength.TRANSITION_NAME, hillshadeEmissiveStrengthTransition.value)
        }
        if (hillshadeExaggeration != HillshadeExaggeration.default) {
          setProperty(HillshadeExaggeration.NAME, hillshadeExaggeration.value)
        }
        if (hillshadeExaggerationTransition != Transition.default) {
          setProperty(HillshadeExaggeration.TRANSITION_NAME, hillshadeExaggerationTransition.value)
        }
        if (hillshadeHighlightColor != HillshadeHighlightColor.default) {
          setProperty(HillshadeHighlightColor.NAME, hillshadeHighlightColor.value)
        }
        if (hillshadeHighlightColorTransition != Transition.default) {
          setProperty(HillshadeHighlightColor.TRANSITION_NAME, hillshadeHighlightColorTransition.value)
        }
        if (hillshadeIlluminationAnchor != HillshadeIlluminationAnchor.default) {
          setProperty(HillshadeIlluminationAnchor.NAME, hillshadeIlluminationAnchor.value)
        }
        if (hillshadeIlluminationDirection != HillshadeIlluminationDirection.default) {
          setProperty(HillshadeIlluminationDirection.NAME, hillshadeIlluminationDirection.value)
        }
        if (hillshadeShadowColor != HillshadeShadowColor.default) {
          setProperty(HillshadeShadowColor.NAME, hillshadeShadowColor.value)
        }
        if (hillshadeShadowColorTransition != Transition.default) {
          setProperty(HillshadeShadowColor.TRANSITION_NAME, hillshadeShadowColorTransition.value)
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
      update(hillshadeAccentColor) {
        setProperty(HillshadeAccentColor.NAME, hillshadeAccentColor.value)
      }
      update(hillshadeAccentColorTransition) {
        setProperty(HillshadeAccentColor.TRANSITION_NAME, hillshadeAccentColorTransition.value)
      }
      update(hillshadeEmissiveStrength) {
        setProperty(HillshadeEmissiveStrength.NAME, hillshadeEmissiveStrength.value)
      }
      update(hillshadeEmissiveStrengthTransition) {
        setProperty(HillshadeEmissiveStrength.TRANSITION_NAME, hillshadeEmissiveStrengthTransition.value)
      }
      update(hillshadeExaggeration) {
        setProperty(HillshadeExaggeration.NAME, hillshadeExaggeration.value)
      }
      update(hillshadeExaggerationTransition) {
        setProperty(HillshadeExaggeration.TRANSITION_NAME, hillshadeExaggerationTransition.value)
      }
      update(hillshadeHighlightColor) {
        setProperty(HillshadeHighlightColor.NAME, hillshadeHighlightColor.value)
      }
      update(hillshadeHighlightColorTransition) {
        setProperty(HillshadeHighlightColor.TRANSITION_NAME, hillshadeHighlightColorTransition.value)
      }
      update(hillshadeIlluminationAnchor) {
        setProperty(HillshadeIlluminationAnchor.NAME, hillshadeIlluminationAnchor.value)
      }
      update(hillshadeIlluminationDirection) {
        setProperty(HillshadeIlluminationDirection.NAME, hillshadeIlluminationDirection.value)
      }
      update(hillshadeShadowColor) {
        setProperty(HillshadeShadowColor.NAME, hillshadeShadowColor.value)
      }
      update(hillshadeShadowColorTransition) {
        setProperty(HillshadeShadowColor.TRANSITION_NAME, hillshadeShadowColorTransition.value)
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