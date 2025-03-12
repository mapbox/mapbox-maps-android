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
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.LayerInteractionsState
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [CircleLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#circle)
 */
@Stable
@OptIn(MapboxExperimental::class)
public class CircleLayerState private constructor(
  initialCircleSortKey: DoubleValue,
  initialCircleBlur: DoubleValue,
  initialCircleBlurTransition: Transition,
  initialCircleColor: ColorValue,
  initialCircleColorUseTheme: StringValue,
  initialCircleColorTransition: Transition,
  initialCircleEmissiveStrength: DoubleValue,
  initialCircleEmissiveStrengthTransition: Transition,
  initialCircleOpacity: DoubleValue,
  initialCircleOpacityTransition: Transition,
  initialCirclePitchAlignment: CirclePitchAlignmentValue,
  initialCirclePitchScale: CirclePitchScaleValue,
  initialCircleRadius: DoubleValue,
  initialCircleRadiusTransition: Transition,
  initialCircleStrokeColor: ColorValue,
  initialCircleStrokeColorUseTheme: StringValue,
  initialCircleStrokeColorTransition: Transition,
  initialCircleStrokeOpacity: DoubleValue,
  initialCircleStrokeOpacityTransition: Transition,
  initialCircleStrokeWidth: DoubleValue,
  initialCircleStrokeWidthTransition: Transition,
  initialCircleTranslate: DoubleListValue,
  initialCircleTranslateTransition: Transition,
  initialCircleTranslateAnchor: CircleTranslateAnchorValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
  initialInteractionsState: LayerInteractionsState,
) {
  /**
   * Construct an default [CircleLayerState].
   */
  public constructor() : this(
    initialCircleSortKey = DoubleValue.INITIAL,
    initialCircleBlur = DoubleValue.INITIAL,
    initialCircleBlurTransition = Transition.INITIAL,
    initialCircleColor = ColorValue.INITIAL,
    initialCircleColorUseTheme = StringValue.INITIAL,
    initialCircleColorTransition = Transition.INITIAL,
    initialCircleEmissiveStrength = DoubleValue.INITIAL,
    initialCircleEmissiveStrengthTransition = Transition.INITIAL,
    initialCircleOpacity = DoubleValue.INITIAL,
    initialCircleOpacityTransition = Transition.INITIAL,
    initialCirclePitchAlignment = CirclePitchAlignmentValue.INITIAL,
    initialCirclePitchScale = CirclePitchScaleValue.INITIAL,
    initialCircleRadius = DoubleValue.INITIAL,
    initialCircleRadiusTransition = Transition.INITIAL,
    initialCircleStrokeColor = ColorValue.INITIAL,
    initialCircleStrokeColorUseTheme = StringValue.INITIAL,
    initialCircleStrokeColorTransition = Transition.INITIAL,
    initialCircleStrokeOpacity = DoubleValue.INITIAL,
    initialCircleStrokeOpacityTransition = Transition.INITIAL,
    initialCircleStrokeWidth = DoubleValue.INITIAL,
    initialCircleStrokeWidthTransition = Transition.INITIAL,
    initialCircleTranslate = DoubleListValue.INITIAL,
    initialCircleTranslateTransition = Transition.INITIAL,
    initialCircleTranslateAnchor = CircleTranslateAnchorValue.INITIAL,
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
  public var circleSortKey: DoubleValue by mutableStateOf(initialCircleSortKey)
  /**
   *  Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Setting a negative value renders the blur as an inner glow effect. Default value: 0.
   */
  public var circleBlur: DoubleValue by mutableStateOf(initialCircleBlur)
  /**
   *  Defines the transition of [circleBlur].
   */
  public var circleBlurTransition: Transition by mutableStateOf(initialCircleBlurTransition)
  /**
   *  The fill color of the circle. Default value: "#000000".
   */
  public var circleColor: ColorValue by mutableStateOf(initialCircleColor)
  /**
   *  Overrides applying of color theme for [circleColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var circleColorUseTheme: StringValue by mutableStateOf(initialCircleColorUseTheme)
  /**
   *  Defines the transition of [circleColor].
   */
  public var circleColorTransition: Transition by mutableStateOf(initialCircleColorTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of circleEmissiveStrength is in intensity.
   */
  public var circleEmissiveStrength: DoubleValue by mutableStateOf(initialCircleEmissiveStrength)
  /**
   *  Defines the transition of [circleEmissiveStrength].
   */
  public var circleEmissiveStrengthTransition: Transition by mutableStateOf(initialCircleEmissiveStrengthTransition)
  /**
   *  The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var circleOpacity: DoubleValue by mutableStateOf(initialCircleOpacity)
  /**
   *  Defines the transition of [circleOpacity].
   */
  public var circleOpacityTransition: Transition by mutableStateOf(initialCircleOpacityTransition)
  /**
   *  Orientation of circle when map is pitched. Default value: "viewport".
   */
  public var circlePitchAlignment: CirclePitchAlignmentValue by mutableStateOf(initialCirclePitchAlignment)
  /**
   *  Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   */
  public var circlePitchScale: CirclePitchScaleValue by mutableStateOf(initialCirclePitchScale)
  /**
   *  Circle radius. Default value: 5. Minimum value: 0. The unit of circleRadius is in pixels.
   */
  public var circleRadius: DoubleValue by mutableStateOf(initialCircleRadius)
  /**
   *  Defines the transition of [circleRadius].
   */
  public var circleRadiusTransition: Transition by mutableStateOf(initialCircleRadiusTransition)
  /**
   *  The stroke color of the circle. Default value: "#000000".
   */
  public var circleStrokeColor: ColorValue by mutableStateOf(initialCircleStrokeColor)
  /**
   *  Overrides applying of color theme for [circleStrokeColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var circleStrokeColorUseTheme: StringValue by mutableStateOf(initialCircleStrokeColorUseTheme)
  /**
   *  Defines the transition of [circleStrokeColor].
   */
  public var circleStrokeColorTransition: Transition by mutableStateOf(initialCircleStrokeColorTransition)
  /**
   *  The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   */
  public var circleStrokeOpacity: DoubleValue by mutableStateOf(initialCircleStrokeOpacity)
  /**
   *  Defines the transition of [circleStrokeOpacity].
   */
  public var circleStrokeOpacityTransition: Transition by mutableStateOf(initialCircleStrokeOpacityTransition)
  /**
   *  The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0. The unit of circleStrokeWidth is in pixels.
   */
  public var circleStrokeWidth: DoubleValue by mutableStateOf(initialCircleStrokeWidth)
  /**
   *  Defines the transition of [circleStrokeWidth].
   */
  public var circleStrokeWidthTransition: Transition by mutableStateOf(initialCircleStrokeWidthTransition)
  /**
   *  The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of circleTranslate is in pixels.
   */
  public var circleTranslate: DoubleListValue by mutableStateOf(initialCircleTranslate)
  /**
   *  Defines the transition of [circleTranslate].
   */
  public var circleTranslateTransition: Transition by mutableStateOf(initialCircleTranslateTransition)
  /**
   *  Controls the frame of reference for `circle-translate`. Default value: "map".
   */
  public var circleTranslateAnchor: CircleTranslateAnchorValue by mutableStateOf(initialCircleTranslateAnchor)
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
  private fun UpdateCircleSortKey(layerNode: LayerNode) {
    if (circleSortKey.notInitial) {
      layerNode.setProperty("circle-sort-key", circleSortKey.value)
    }
  }
  @Composable
  private fun UpdateCircleBlur(layerNode: LayerNode) {
    if (circleBlur.notInitial) {
      layerNode.setProperty("circle-blur", circleBlur.value)
    }
  }
  @Composable
  private fun UpdateCircleBlurTransition(layerNode: LayerNode) {
    if (circleBlurTransition.notInitial) {
      layerNode.setProperty("circle-blur-transition", circleBlurTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleColor(layerNode: LayerNode) {
    if (circleColor.notInitial) {
      layerNode.setProperty("circle-color", circleColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateCircleColorUseTheme(layerNode: LayerNode) {
    if (circleColorUseTheme.notInitial) {
      layerNode.setProperty("circle-color-use-theme", circleColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateCircleColorTransition(layerNode: LayerNode) {
    if (circleColorTransition.notInitial) {
      layerNode.setProperty("circle-color-transition", circleColorTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleEmissiveStrength(layerNode: LayerNode) {
    if (circleEmissiveStrength.notInitial) {
      layerNode.setProperty("circle-emissive-strength", circleEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateCircleEmissiveStrengthTransition(layerNode: LayerNode) {
    if (circleEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("circle-emissive-strength-transition", circleEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleOpacity(layerNode: LayerNode) {
    if (circleOpacity.notInitial) {
      layerNode.setProperty("circle-opacity", circleOpacity.value)
    }
  }
  @Composable
  private fun UpdateCircleOpacityTransition(layerNode: LayerNode) {
    if (circleOpacityTransition.notInitial) {
      layerNode.setProperty("circle-opacity-transition", circleOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateCirclePitchAlignment(layerNode: LayerNode) {
    if (circlePitchAlignment.notInitial) {
      layerNode.setProperty("circle-pitch-alignment", circlePitchAlignment.value)
    }
  }
  @Composable
  private fun UpdateCirclePitchScale(layerNode: LayerNode) {
    if (circlePitchScale.notInitial) {
      layerNode.setProperty("circle-pitch-scale", circlePitchScale.value)
    }
  }
  @Composable
  private fun UpdateCircleRadius(layerNode: LayerNode) {
    if (circleRadius.notInitial) {
      layerNode.setProperty("circle-radius", circleRadius.value)
    }
  }
  @Composable
  private fun UpdateCircleRadiusTransition(layerNode: LayerNode) {
    if (circleRadiusTransition.notInitial) {
      layerNode.setProperty("circle-radius-transition", circleRadiusTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleStrokeColor(layerNode: LayerNode) {
    if (circleStrokeColor.notInitial) {
      layerNode.setProperty("circle-stroke-color", circleStrokeColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateCircleStrokeColorUseTheme(layerNode: LayerNode) {
    if (circleStrokeColorUseTheme.notInitial) {
      layerNode.setProperty("circle-stroke-color-use-theme", circleStrokeColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateCircleStrokeColorTransition(layerNode: LayerNode) {
    if (circleStrokeColorTransition.notInitial) {
      layerNode.setProperty("circle-stroke-color-transition", circleStrokeColorTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleStrokeOpacity(layerNode: LayerNode) {
    if (circleStrokeOpacity.notInitial) {
      layerNode.setProperty("circle-stroke-opacity", circleStrokeOpacity.value)
    }
  }
  @Composable
  private fun UpdateCircleStrokeOpacityTransition(layerNode: LayerNode) {
    if (circleStrokeOpacityTransition.notInitial) {
      layerNode.setProperty("circle-stroke-opacity-transition", circleStrokeOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleStrokeWidth(layerNode: LayerNode) {
    if (circleStrokeWidth.notInitial) {
      layerNode.setProperty("circle-stroke-width", circleStrokeWidth.value)
    }
  }
  @Composable
  private fun UpdateCircleStrokeWidthTransition(layerNode: LayerNode) {
    if (circleStrokeWidthTransition.notInitial) {
      layerNode.setProperty("circle-stroke-width-transition", circleStrokeWidthTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleTranslate(layerNode: LayerNode) {
    if (circleTranslate.notInitial) {
      layerNode.setProperty("circle-translate", circleTranslate.value)
    }
  }
  @Composable
  private fun UpdateCircleTranslateTransition(layerNode: LayerNode) {
    if (circleTranslateTransition.notInitial) {
      layerNode.setProperty("circle-translate-transition", circleTranslateTransition.value)
    }
  }
  @Composable
  private fun UpdateCircleTranslateAnchor(layerNode: LayerNode) {
    if (circleTranslateAnchor.notInitial) {
      layerNode.setProperty("circle-translate-anchor", circleTranslateAnchor.value)
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
    UpdateCircleSortKey(layerNode)
    UpdateCircleBlur(layerNode)
    UpdateCircleBlurTransition(layerNode)
    UpdateCircleColor(layerNode)
    UpdateCircleColorUseTheme(layerNode)
    UpdateCircleColorTransition(layerNode)
    UpdateCircleEmissiveStrength(layerNode)
    UpdateCircleEmissiveStrengthTransition(layerNode)
    UpdateCircleOpacity(layerNode)
    UpdateCircleOpacityTransition(layerNode)
    UpdateCirclePitchAlignment(layerNode)
    UpdateCirclePitchScale(layerNode)
    UpdateCircleRadius(layerNode)
    UpdateCircleRadiusTransition(layerNode)
    UpdateCircleStrokeColor(layerNode)
    UpdateCircleStrokeColorUseTheme(layerNode)
    UpdateCircleStrokeColorTransition(layerNode)
    UpdateCircleStrokeOpacity(layerNode)
    UpdateCircleStrokeOpacityTransition(layerNode)
    UpdateCircleStrokeWidth(layerNode)
    UpdateCircleStrokeWidthTransition(layerNode)
    UpdateCircleTranslate(layerNode)
    UpdateCircleTranslateTransition(layerNode)
    UpdateCircleTranslateAnchor(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.