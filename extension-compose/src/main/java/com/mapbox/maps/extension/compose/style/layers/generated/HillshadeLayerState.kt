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
 * The state holder for [HillshadeLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#hillshade)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class HillshadeLayerState private constructor(
  initialHillshadeAccentColor: ColorValue,
  initialHillshadeAccentColorUseTheme: StringValue,
  initialHillshadeAccentColorTransition: Transition,
  initialHillshadeEmissiveStrength: DoubleValue,
  initialHillshadeEmissiveStrengthTransition: Transition,
  initialHillshadeExaggeration: DoubleValue,
  initialHillshadeExaggerationTransition: Transition,
  initialHillshadeHighlightColor: ColorValue,
  initialHillshadeHighlightColorUseTheme: StringValue,
  initialHillshadeHighlightColorTransition: Transition,
  initialHillshadeIlluminationAnchor: HillshadeIlluminationAnchorValue,
  initialHillshadeIlluminationDirection: DoubleValue,
  initialHillshadeShadowColor: ColorValue,
  initialHillshadeShadowColorUseTheme: StringValue,
  initialHillshadeShadowColorTransition: Transition,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [HillshadeLayerState].
   */
  public constructor() : this(
    initialHillshadeAccentColor = ColorValue.INITIAL,
    initialHillshadeAccentColorUseTheme = StringValue.INITIAL,
    initialHillshadeAccentColorTransition = Transition.INITIAL,
    initialHillshadeEmissiveStrength = DoubleValue.INITIAL,
    initialHillshadeEmissiveStrengthTransition = Transition.INITIAL,
    initialHillshadeExaggeration = DoubleValue.INITIAL,
    initialHillshadeExaggerationTransition = Transition.INITIAL,
    initialHillshadeHighlightColor = ColorValue.INITIAL,
    initialHillshadeHighlightColorUseTheme = StringValue.INITIAL,
    initialHillshadeHighlightColorTransition = Transition.INITIAL,
    initialHillshadeIlluminationAnchor = HillshadeIlluminationAnchorValue.INITIAL,
    initialHillshadeIlluminationDirection = DoubleValue.INITIAL,
    initialHillshadeShadowColor = ColorValue.INITIAL,
    initialHillshadeShadowColorUseTheme = StringValue.INITIAL,
    initialHillshadeShadowColorTransition = Transition.INITIAL,
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
   *  The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   */
  public var hillshadeAccentColor: ColorValue by mutableStateOf(initialHillshadeAccentColor)
  /**
   *  Overrides applying of color theme for [hillshadeAccentColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var hillshadeAccentColorUseTheme: StringValue by mutableStateOf(initialHillshadeAccentColorUseTheme)
  /**
   *  Defines the transition of [hillshadeAccentColor].
   */
  public var hillshadeAccentColorTransition: Transition by mutableStateOf(initialHillshadeAccentColorTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   */
  public var hillshadeEmissiveStrength: DoubleValue by mutableStateOf(initialHillshadeEmissiveStrength)
  /**
   *  Defines the transition of [hillshadeEmissiveStrength].
   */
  public var hillshadeEmissiveStrengthTransition: Transition by mutableStateOf(initialHillshadeEmissiveStrengthTransition)
  /**
   *  Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   */
  public var hillshadeExaggeration: DoubleValue by mutableStateOf(initialHillshadeExaggeration)
  /**
   *  Defines the transition of [hillshadeExaggeration].
   */
  public var hillshadeExaggerationTransition: Transition by mutableStateOf(initialHillshadeExaggerationTransition)
  /**
   *  The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   */
  public var hillshadeHighlightColor: ColorValue by mutableStateOf(initialHillshadeHighlightColor)
  /**
   *  Overrides applying of color theme for [hillshadeHighlightColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var hillshadeHighlightColorUseTheme: StringValue by mutableStateOf(initialHillshadeHighlightColorUseTheme)
  /**
   *  Defines the transition of [hillshadeHighlightColor].
   */
  public var hillshadeHighlightColorTransition: Transition by mutableStateOf(initialHillshadeHighlightColorTransition)
  /**
   *  Direction of light source when map is rotated. Default value: "viewport".
   */
  public var hillshadeIlluminationAnchor: HillshadeIlluminationAnchorValue by mutableStateOf(initialHillshadeIlluminationAnchor)
  /**
   *  The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   */
  public var hillshadeIlluminationDirection: DoubleValue by mutableStateOf(initialHillshadeIlluminationDirection)
  /**
   *  The shading color of areas that face away from the light source. Default value: "#000000".
   */
  public var hillshadeShadowColor: ColorValue by mutableStateOf(initialHillshadeShadowColor)
  /**
   *  Overrides applying of color theme for [hillshadeShadowColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var hillshadeShadowColorUseTheme: StringValue by mutableStateOf(initialHillshadeShadowColorUseTheme)
  /**
   *  Defines the transition of [hillshadeShadowColor].
   */
  public var hillshadeShadowColorTransition: Transition by mutableStateOf(initialHillshadeShadowColorTransition)
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
  private fun UpdateHillshadeAccentColor(layerNode: LayerNode) {
    if (hillshadeAccentColor.notInitial) {
      layerNode.setProperty("hillshade-accent-color", hillshadeAccentColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateHillshadeAccentColorUseTheme(layerNode: LayerNode) {
    if (hillshadeAccentColorUseTheme.notInitial) {
      layerNode.setProperty("hillshade-accent-color-use-theme", hillshadeAccentColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateHillshadeAccentColorTransition(layerNode: LayerNode) {
    if (hillshadeAccentColorTransition.notInitial) {
      layerNode.setProperty("hillshade-accent-color-transition", hillshadeAccentColorTransition.value)
    }
  }
  @Composable
  private fun UpdateHillshadeEmissiveStrength(layerNode: LayerNode) {
    if (hillshadeEmissiveStrength.notInitial) {
      layerNode.setProperty("hillshade-emissive-strength", hillshadeEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateHillshadeEmissiveStrengthTransition(layerNode: LayerNode) {
    if (hillshadeEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("hillshade-emissive-strength-transition", hillshadeEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateHillshadeExaggeration(layerNode: LayerNode) {
    if (hillshadeExaggeration.notInitial) {
      layerNode.setProperty("hillshade-exaggeration", hillshadeExaggeration.value)
    }
  }
  @Composable
  private fun UpdateHillshadeExaggerationTransition(layerNode: LayerNode) {
    if (hillshadeExaggerationTransition.notInitial) {
      layerNode.setProperty("hillshade-exaggeration-transition", hillshadeExaggerationTransition.value)
    }
  }
  @Composable
  private fun UpdateHillshadeHighlightColor(layerNode: LayerNode) {
    if (hillshadeHighlightColor.notInitial) {
      layerNode.setProperty("hillshade-highlight-color", hillshadeHighlightColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateHillshadeHighlightColorUseTheme(layerNode: LayerNode) {
    if (hillshadeHighlightColorUseTheme.notInitial) {
      layerNode.setProperty("hillshade-highlight-color-use-theme", hillshadeHighlightColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateHillshadeHighlightColorTransition(layerNode: LayerNode) {
    if (hillshadeHighlightColorTransition.notInitial) {
      layerNode.setProperty("hillshade-highlight-color-transition", hillshadeHighlightColorTransition.value)
    }
  }
  @Composable
  private fun UpdateHillshadeIlluminationAnchor(layerNode: LayerNode) {
    if (hillshadeIlluminationAnchor.notInitial) {
      layerNode.setProperty("hillshade-illumination-anchor", hillshadeIlluminationAnchor.value)
    }
  }
  @Composable
  private fun UpdateHillshadeIlluminationDirection(layerNode: LayerNode) {
    if (hillshadeIlluminationDirection.notInitial) {
      layerNode.setProperty("hillshade-illumination-direction", hillshadeIlluminationDirection.value)
    }
  }
  @Composable
  private fun UpdateHillshadeShadowColor(layerNode: LayerNode) {
    if (hillshadeShadowColor.notInitial) {
      layerNode.setProperty("hillshade-shadow-color", hillshadeShadowColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateHillshadeShadowColorUseTheme(layerNode: LayerNode) {
    if (hillshadeShadowColorUseTheme.notInitial) {
      layerNode.setProperty("hillshade-shadow-color-use-theme", hillshadeShadowColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateHillshadeShadowColorTransition(layerNode: LayerNode) {
    if (hillshadeShadowColorTransition.notInitial) {
      layerNode.setProperty("hillshade-shadow-color-transition", hillshadeShadowColorTransition.value)
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
    UpdateHillshadeAccentColor(layerNode)
    UpdateHillshadeAccentColorUseTheme(layerNode)
    UpdateHillshadeAccentColorTransition(layerNode)
    UpdateHillshadeEmissiveStrength(layerNode)
    UpdateHillshadeEmissiveStrengthTransition(layerNode)
    UpdateHillshadeExaggeration(layerNode)
    UpdateHillshadeExaggerationTransition(layerNode)
    UpdateHillshadeHighlightColor(layerNode)
    UpdateHillshadeHighlightColorUseTheme(layerNode)
    UpdateHillshadeHighlightColorTransition(layerNode)
    UpdateHillshadeIlluminationAnchor(layerNode)
    UpdateHillshadeIlluminationDirection(layerNode)
    UpdateHillshadeShadowColor(layerNode)
    UpdateHillshadeShadowColorUseTheme(layerNode)
    UpdateHillshadeShadowColorTransition(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.