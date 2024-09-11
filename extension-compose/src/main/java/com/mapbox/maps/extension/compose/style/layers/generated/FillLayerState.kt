// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [FillLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class FillLayerState private constructor(
  initialFillSortKey: DoubleValue,
  initialFillAntialias: BooleanValue,
  initialFillColor: ColorValue,
  initialFillColorTransition: Transition,
  initialFillEmissiveStrength: DoubleValue,
  initialFillEmissiveStrengthTransition: Transition,
  initialFillOpacity: DoubleValue,
  initialFillOpacityTransition: Transition,
  initialFillOutlineColor: ColorValue,
  initialFillOutlineColorTransition: Transition,
  initialFillPattern: ImageValue,
  initialFillTranslate: DoubleListValue,
  initialFillTranslateTransition: Transition,
  initialFillTranslateAnchor: FillTranslateAnchorValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [FillLayerState].
   */
  public constructor() : this(
    initialFillSortKey = DoubleValue.INITIAL,
    initialFillAntialias = BooleanValue.INITIAL,
    initialFillColor = ColorValue.INITIAL,
    initialFillColorTransition = Transition.INITIAL,
    initialFillEmissiveStrength = DoubleValue.INITIAL,
    initialFillEmissiveStrengthTransition = Transition.INITIAL,
    initialFillOpacity = DoubleValue.INITIAL,
    initialFillOpacityTransition = Transition.INITIAL,
    initialFillOutlineColor = ColorValue.INITIAL,
    initialFillOutlineColorTransition = Transition.INITIAL,
    initialFillPattern = ImageValue.INITIAL,
    initialFillTranslate = DoubleListValue.INITIAL,
    initialFillTranslateTransition = Transition.INITIAL,
    initialFillTranslateAnchor = FillTranslateAnchorValue.INITIAL,
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
   *  Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  public var fillSortKey: DoubleValue by mutableStateOf(initialFillSortKey)
  /**
   *  Whether or not the fill should be antialiased. Default value: true.
   */
  public var fillAntialias: BooleanValue by mutableStateOf(initialFillAntialias)
  /**
   *  The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   */
  public var fillColor: ColorValue by mutableStateOf(initialFillColor)
  /**
   *  Defines the transition of [fillColor]. Default value: "#000000".
   */
  public var fillColorTransition: Transition by mutableStateOf(initialFillColorTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   */
  public var fillEmissiveStrength: DoubleValue by mutableStateOf(initialFillEmissiveStrength)
  /**
   *  Defines the transition of [fillEmissiveStrength]. Default value: 0. Minimum value: 0.
   */
  public var fillEmissiveStrengthTransition: Transition by mutableStateOf(initialFillEmissiveStrengthTransition)
  /**
   *  The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   */
  public var fillOpacity: DoubleValue by mutableStateOf(initialFillOpacity)
  /**
   *  Defines the transition of [fillOpacity]. Default value: 1. Value range: [0, 1]
   */
  public var fillOpacityTransition: Transition by mutableStateOf(initialFillOpacityTransition)
  /**
   *  The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  public var fillOutlineColor: ColorValue by mutableStateOf(initialFillOutlineColor)
  /**
   *  Defines the transition of [fillOutlineColor].
   */
  public var fillOutlineColorTransition: Transition by mutableStateOf(initialFillOutlineColorTransition)
  /**
   *  Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var fillPattern: ImageValue by mutableStateOf(initialFillPattern)
  /**
   *  The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   */
  public var fillTranslate: DoubleListValue by mutableStateOf(initialFillTranslate)
  /**
   *  Defines the transition of [fillTranslate]. Default value: [0,0].
   */
  public var fillTranslateTransition: Transition by mutableStateOf(initialFillTranslateTransition)
  /**
   *  Controls the frame of reference for `fill-translate`. Default value: "map".
   */
  public var fillTranslateAnchor: FillTranslateAnchorValue by mutableStateOf(initialFillTranslateAnchor)
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
  private fun UpdateFillSortKey(layerNode: LayerNode) {
    if (fillSortKey.notInitial) {
      layerNode.setProperty("fill-sort-key", fillSortKey.value)
    }
  }
  @Composable
  private fun UpdateFillAntialias(layerNode: LayerNode) {
    if (fillAntialias.notInitial) {
      layerNode.setProperty("fill-antialias", fillAntialias.value)
    }
  }
  @Composable
  private fun UpdateFillColor(layerNode: LayerNode) {
    if (fillColor.notInitial) {
      layerNode.setProperty("fill-color", fillColor.value)
    }
  }
  @Composable
  private fun UpdateFillColorTransition(layerNode: LayerNode) {
    if (fillColorTransition.notInitial) {
      layerNode.setProperty("fill-color-transition", fillColorTransition.value)
    }
  }
  @Composable
  private fun UpdateFillEmissiveStrength(layerNode: LayerNode) {
    if (fillEmissiveStrength.notInitial) {
      layerNode.setProperty("fill-emissive-strength", fillEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateFillEmissiveStrengthTransition(layerNode: LayerNode) {
    if (fillEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("fill-emissive-strength-transition", fillEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateFillOpacity(layerNode: LayerNode) {
    if (fillOpacity.notInitial) {
      layerNode.setProperty("fill-opacity", fillOpacity.value)
    }
  }
  @Composable
  private fun UpdateFillOpacityTransition(layerNode: LayerNode) {
    if (fillOpacityTransition.notInitial) {
      layerNode.setProperty("fill-opacity-transition", fillOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateFillOutlineColor(layerNode: LayerNode) {
    if (fillOutlineColor.notInitial) {
      layerNode.setProperty("fill-outline-color", fillOutlineColor.value)
    }
  }
  @Composable
  private fun UpdateFillOutlineColorTransition(layerNode: LayerNode) {
    if (fillOutlineColorTransition.notInitial) {
      layerNode.setProperty("fill-outline-color-transition", fillOutlineColorTransition.value)
    }
  }
  @Composable
  private fun UpdateFillPattern(layerNode: LayerNode) {
    if (fillPattern.notInitial) {
      fillPattern.styleImage?.let {
        layerNode.addImage(it)
      }
      layerNode.setProperty("fill-pattern", fillPattern.value)
    }
  }
  @Composable
  private fun UpdateFillTranslate(layerNode: LayerNode) {
    if (fillTranslate.notInitial) {
      layerNode.setProperty("fill-translate", fillTranslate.value)
    }
  }
  @Composable
  private fun UpdateFillTranslateTransition(layerNode: LayerNode) {
    if (fillTranslateTransition.notInitial) {
      layerNode.setProperty("fill-translate-transition", fillTranslateTransition.value)
    }
  }
  @Composable
  private fun UpdateFillTranslateAnchor(layerNode: LayerNode) {
    if (fillTranslateAnchor.notInitial) {
      layerNode.setProperty("fill-translate-anchor", fillTranslateAnchor.value)
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
    UpdateFillSortKey(layerNode)
    UpdateFillAntialias(layerNode)
    UpdateFillColor(layerNode)
    UpdateFillColorTransition(layerNode)
    UpdateFillEmissiveStrength(layerNode)
    UpdateFillEmissiveStrengthTransition(layerNode)
    UpdateFillOpacity(layerNode)
    UpdateFillOpacityTransition(layerNode)
    UpdateFillOutlineColor(layerNode)
    UpdateFillOutlineColorTransition(layerNode)
    UpdateFillPattern(layerNode)
    UpdateFillTranslate(layerNode)
    UpdateFillTranslateTransition(layerNode)
    UpdateFillTranslateAnchor(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.