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
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [BackgroundLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#background)
 */
@Stable
public class BackgroundLayerState private constructor(
  initialBackgroundColor: ColorValue,
  initialBackgroundColorUseTheme: StringValue,
  initialBackgroundColorTransition: Transition,
  initialBackgroundEmissiveStrength: DoubleValue,
  initialBackgroundEmissiveStrengthTransition: Transition,
  initialBackgroundOpacity: DoubleValue,
  initialBackgroundOpacityTransition: Transition,
  initialBackgroundPattern: ImageValue,
  initialBackgroundPitchAlignment: BackgroundPitchAlignmentValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
) {
  /**
   * Construct an default [BackgroundLayerState].
   */
  public constructor() : this(
    initialBackgroundColor = ColorValue.INITIAL,
    initialBackgroundColorUseTheme = StringValue.INITIAL,
    initialBackgroundColorTransition = Transition.INITIAL,
    initialBackgroundEmissiveStrength = DoubleValue.INITIAL,
    initialBackgroundEmissiveStrengthTransition = Transition.INITIAL,
    initialBackgroundOpacity = DoubleValue.INITIAL,
    initialBackgroundOpacityTransition = Transition.INITIAL,
    initialBackgroundPattern = ImageValue.INITIAL,
    initialBackgroundPitchAlignment = BackgroundPitchAlignmentValue.INITIAL,
    initialVisibility = VisibilityValue.INITIAL,
    initialMinZoom = LongValue.INITIAL,
    initialMaxZoom = LongValue.INITIAL,
  )

  /**
   *  The color with which the background will be drawn. Default value: "#000000".
   */
  public var backgroundColor: ColorValue by mutableStateOf(initialBackgroundColor)
  /**
   *  Overrides applying of color theme for [backgroundColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var backgroundColorUseTheme: StringValue by mutableStateOf(initialBackgroundColorUseTheme)
  /**
   *  Defines the transition of [backgroundColor].
   */
  public var backgroundColorTransition: Transition by mutableStateOf(initialBackgroundColorTransition)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of backgroundEmissiveStrength is in intensity.
   */
  public var backgroundEmissiveStrength: DoubleValue by mutableStateOf(initialBackgroundEmissiveStrength)
  /**
   *  Defines the transition of [backgroundEmissiveStrength].
   */
  public var backgroundEmissiveStrengthTransition: Transition by mutableStateOf(initialBackgroundEmissiveStrengthTransition)
  /**
   *  The opacity at which the background will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var backgroundOpacity: DoubleValue by mutableStateOf(initialBackgroundOpacity)
  /**
   *  Defines the transition of [backgroundOpacity].
   */
  public var backgroundOpacityTransition: Transition by mutableStateOf(initialBackgroundOpacityTransition)
  /**
   *  Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var backgroundPattern: ImageValue by mutableStateOf(initialBackgroundPattern)
  /**
   *  Orientation of background layer. Default value: "map".
   */
  @MapboxExperimental
  public var backgroundPitchAlignment: BackgroundPitchAlignmentValue by mutableStateOf(initialBackgroundPitchAlignment)
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

  @Composable
  private fun UpdateBackgroundColor(layerNode: LayerNode) {
    if (backgroundColor.notInitial) {
      layerNode.setProperty("background-color", backgroundColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateBackgroundColorUseTheme(layerNode: LayerNode) {
    if (backgroundColorUseTheme.notInitial) {
      layerNode.setProperty("background-color-use-theme", backgroundColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateBackgroundColorTransition(layerNode: LayerNode) {
    if (backgroundColorTransition.notInitial) {
      layerNode.setProperty("background-color-transition", backgroundColorTransition.value)
    }
  }
  @Composable
  private fun UpdateBackgroundEmissiveStrength(layerNode: LayerNode) {
    if (backgroundEmissiveStrength.notInitial) {
      layerNode.setProperty("background-emissive-strength", backgroundEmissiveStrength.value)
    }
  }
  @Composable
  private fun UpdateBackgroundEmissiveStrengthTransition(layerNode: LayerNode) {
    if (backgroundEmissiveStrengthTransition.notInitial) {
      layerNode.setProperty("background-emissive-strength-transition", backgroundEmissiveStrengthTransition.value)
    }
  }
  @Composable
  private fun UpdateBackgroundOpacity(layerNode: LayerNode) {
    if (backgroundOpacity.notInitial) {
      layerNode.setProperty("background-opacity", backgroundOpacity.value)
    }
  }
  @Composable
  private fun UpdateBackgroundOpacityTransition(layerNode: LayerNode) {
    if (backgroundOpacityTransition.notInitial) {
      layerNode.setProperty("background-opacity-transition", backgroundOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateBackgroundPattern(layerNode: LayerNode) {
    if (backgroundPattern.notInitial) {
      backgroundPattern.styleImage?.let {
        layerNode.addImage(it)
      }
      layerNode.setProperty("background-pattern", backgroundPattern.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateBackgroundPitchAlignment(layerNode: LayerNode) {
    if (backgroundPitchAlignment.notInitial) {
      layerNode.setProperty("background-pitch-alignment", backgroundPitchAlignment.value)
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
  internal fun UpdateProperties(layerNode: LayerNode) {
    UpdateBackgroundColor(layerNode)
    UpdateBackgroundColorUseTheme(layerNode)
    UpdateBackgroundColorTransition(layerNode)
    UpdateBackgroundEmissiveStrength(layerNode)
    UpdateBackgroundEmissiveStrengthTransition(layerNode)
    UpdateBackgroundOpacity(layerNode)
    UpdateBackgroundOpacityTransition(layerNode)
    UpdateBackgroundPattern(layerNode)
    UpdateBackgroundPitchAlignment(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
  }
}
// End of generated file.