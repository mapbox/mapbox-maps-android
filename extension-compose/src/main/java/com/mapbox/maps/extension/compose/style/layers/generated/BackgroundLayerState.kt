// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ActionWhenNotInitial
import com.mapbox.maps.extension.compose.style.AddImageWhenNotInitial
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
public class BackgroundLayerState
@OptIn(MapboxExperimental::class)
private constructor(
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
  @OptIn(MapboxExperimental::class)
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

  private val backgroundColorState: MutableState<ColorValue> = mutableStateOf(initialBackgroundColor)
  /**
   *  The color with which the background will be drawn. Default value: "#000000".
   */
  public var backgroundColor: ColorValue by backgroundColorState

  @MapboxExperimental
  private val backgroundColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialBackgroundColorUseTheme)
  /**
   *  Overrides applying of color theme for [backgroundColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var backgroundColorUseTheme: StringValue by backgroundColorUseThemeState

  private val backgroundColorTransitionState: MutableState<Transition> = mutableStateOf(initialBackgroundColorTransition)
  /**
   *  Defines the transition of [backgroundColor].
   */
  public var backgroundColorTransition: Transition by backgroundColorTransitionState

  private val backgroundEmissiveStrengthState: MutableState<DoubleValue> = mutableStateOf(initialBackgroundEmissiveStrength)
  /**
   *  Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of backgroundEmissiveStrength is in intensity.
   */
  public var backgroundEmissiveStrength: DoubleValue by backgroundEmissiveStrengthState

  private val backgroundEmissiveStrengthTransitionState: MutableState<Transition> = mutableStateOf(initialBackgroundEmissiveStrengthTransition)
  /**
   *  Defines the transition of [backgroundEmissiveStrength].
   */
  public var backgroundEmissiveStrengthTransition: Transition by backgroundEmissiveStrengthTransitionState

  private val backgroundOpacityState: MutableState<DoubleValue> = mutableStateOf(initialBackgroundOpacity)
  /**
   *  The opacity at which the background will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var backgroundOpacity: DoubleValue by backgroundOpacityState

  private val backgroundOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialBackgroundOpacityTransition)
  /**
   *  Defines the transition of [backgroundOpacity].
   */
  public var backgroundOpacityTransition: Transition by backgroundOpacityTransitionState

  private val backgroundPatternState: MutableState<ImageValue> = mutableStateOf(initialBackgroundPattern)
  /**
   *  Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var backgroundPattern: ImageValue by backgroundPatternState

  @MapboxExperimental
  private val backgroundPitchAlignmentState: MutableState<BackgroundPitchAlignmentValue> = mutableStateOf(initialBackgroundPitchAlignment)
  /**
   *  Orientation of background layer. Default value: "map".
   */
  @MapboxExperimental
  public var backgroundPitchAlignment: BackgroundPitchAlignmentValue by backgroundPitchAlignmentState

  private val visibilityState: MutableState<VisibilityValue> = mutableStateOf(initialVisibility)
  /**
   *  Whether this layer is displayed. Default value: "visible".
   */
  public var visibility: VisibilityValue by visibilityState

  private val minZoomState: MutableState<LongValue> = mutableStateOf(initialMinZoom)
  /**
   *  The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var minZoom: LongValue by minZoomState

  private val maxZoomState: MutableState<LongValue> = mutableStateOf(initialMaxZoom)
  /**
   *  The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
   */
  public var maxZoom: LongValue by maxZoomState

  @Composable
  @OptIn(MapboxExperimental::class)
  internal fun UpdateProperties(layerNode: LayerNode) {
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundColorState, "background-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundColorUseThemeState, "background-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundColorTransitionState, "background-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundEmissiveStrengthState, "background-emissive-strength")
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundEmissiveStrengthTransitionState, "background-emissive-strength-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundOpacityState, "background-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundOpacityTransitionState, "background-opacity-transition")
    AddImageWhenNotInitial(layerNode, backgroundPatternState, "background-pattern")
    ActionWhenNotInitial(layerNode.setPropertyAction, backgroundPitchAlignmentState, "background-pitch-alignment")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
  }
}
// End of generated file.