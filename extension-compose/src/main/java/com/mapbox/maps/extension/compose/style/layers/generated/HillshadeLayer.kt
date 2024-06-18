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
 * Client-side hillshading visualization based on DEM data. Currently, the implementation only supports Mapbox Terrain RGB and Mapzen Terrarium tiles.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#hillshade)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param hillshadeAccentColor The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
 * @param hillshadeAccentColorTransition Defines the transition of [hillshadeAccentColor]. Default value: "#000000".
 * @param hillshadeEmissiveStrength Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
 * @param hillshadeEmissiveStrengthTransition Defines the transition of [hillshadeEmissiveStrength]. Default value: 0. Minimum value: 0.
 * @param hillshadeExaggeration Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
 * @param hillshadeExaggerationTransition Defines the transition of [hillshadeExaggeration]. Default value: 0.5. Value range: [0, 1]
 * @param hillshadeHighlightColor The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
 * @param hillshadeHighlightColorTransition Defines the transition of [hillshadeHighlightColor]. Default value: "#FFFFFF".
 * @param hillshadeIlluminationAnchor Direction of light source when map is rotated. Default value: "viewport".
 * @param hillshadeIlluminationDirection The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
 * @param hillshadeShadowColor The shading color of areas that face away from the light source. Default value: "#000000".
 * @param hillshadeShadowColorTransition Defines the transition of [hillshadeShadowColor]. Default value: "#000000".
 * @param visibility Whether this layer is displayed. Default value: "visible".
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun HillshadeLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("hillshade")
  },
  hillshadeAccentColor: ColorValue = ColorValue.INITIAL,
  hillshadeAccentColorTransition: Transition = Transition.INITIAL,
  hillshadeEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  hillshadeEmissiveStrengthTransition: Transition = Transition.INITIAL,
  hillshadeExaggeration: DoubleValue = DoubleValue.INITIAL,
  hillshadeExaggerationTransition: Transition = Transition.INITIAL,
  hillshadeHighlightColor: ColorValue = ColorValue.INITIAL,
  hillshadeHighlightColorTransition: Transition = Transition.INITIAL,
  hillshadeIlluminationAnchor: HillshadeIlluminationAnchorValue = HillshadeIlluminationAnchorValue.INITIAL,
  hillshadeIlluminationDirection: DoubleValue = DoubleValue.INITIAL,
  hillshadeShadowColor: ColorValue = ColorValue.INITIAL,
  hillshadeShadowColorTransition: Transition = Transition.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of HillshadeLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "hillshade",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (hillshadeAccentColor.notInitial) {
          setProperty("hillshade-accent-color", hillshadeAccentColor.value)
        }
        if (hillshadeAccentColorTransition.notInitial) {
          setProperty("hillshade-accent-color-transition", hillshadeAccentColorTransition.value)
        }
        if (hillshadeEmissiveStrength.notInitial) {
          setProperty("hillshade-emissive-strength", hillshadeEmissiveStrength.value)
        }
        if (hillshadeEmissiveStrengthTransition.notInitial) {
          setProperty("hillshade-emissive-strength-transition", hillshadeEmissiveStrengthTransition.value)
        }
        if (hillshadeExaggeration.notInitial) {
          setProperty("hillshade-exaggeration", hillshadeExaggeration.value)
        }
        if (hillshadeExaggerationTransition.notInitial) {
          setProperty("hillshade-exaggeration-transition", hillshadeExaggerationTransition.value)
        }
        if (hillshadeHighlightColor.notInitial) {
          setProperty("hillshade-highlight-color", hillshadeHighlightColor.value)
        }
        if (hillshadeHighlightColorTransition.notInitial) {
          setProperty("hillshade-highlight-color-transition", hillshadeHighlightColorTransition.value)
        }
        if (hillshadeIlluminationAnchor.notInitial) {
          setProperty("hillshade-illumination-anchor", hillshadeIlluminationAnchor.value)
        }
        if (hillshadeIlluminationDirection.notInitial) {
          setProperty("hillshade-illumination-direction", hillshadeIlluminationDirection.value)
        }
        if (hillshadeShadowColor.notInitial) {
          setProperty("hillshade-shadow-color", hillshadeShadowColor.value)
        }
        if (hillshadeShadowColorTransition.notInitial) {
          setProperty("hillshade-shadow-color-transition", hillshadeShadowColorTransition.value)
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
      update(hillshadeAccentColor) {
        setProperty("hillshade-accent-color", hillshadeAccentColor.value)
      }
      update(hillshadeAccentColorTransition) {
        setProperty("hillshade-accent-color-transition", hillshadeAccentColorTransition.value)
      }
      update(hillshadeEmissiveStrength) {
        setProperty("hillshade-emissive-strength", hillshadeEmissiveStrength.value)
      }
      update(hillshadeEmissiveStrengthTransition) {
        setProperty("hillshade-emissive-strength-transition", hillshadeEmissiveStrengthTransition.value)
      }
      update(hillshadeExaggeration) {
        setProperty("hillshade-exaggeration", hillshadeExaggeration.value)
      }
      update(hillshadeExaggerationTransition) {
        setProperty("hillshade-exaggeration-transition", hillshadeExaggerationTransition.value)
      }
      update(hillshadeHighlightColor) {
        setProperty("hillshade-highlight-color", hillshadeHighlightColor.value)
      }
      update(hillshadeHighlightColorTransition) {
        setProperty("hillshade-highlight-color-transition", hillshadeHighlightColorTransition.value)
      }
      update(hillshadeIlluminationAnchor) {
        setProperty("hillshade-illumination-anchor", hillshadeIlluminationAnchor.value)
      }
      update(hillshadeIlluminationDirection) {
        setProperty("hillshade-illumination-direction", hillshadeIlluminationDirection.value)
      }
      update(hillshadeShadowColor) {
        setProperty("hillshade-shadow-color", hillshadeShadowColor.value)
      }
      update(hillshadeShadowColorTransition) {
        setProperty("hillshade-shadow-color-transition", hillshadeShadowColorTransition.value)
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