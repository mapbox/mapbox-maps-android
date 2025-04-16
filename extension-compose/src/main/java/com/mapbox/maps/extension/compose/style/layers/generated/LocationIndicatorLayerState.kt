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
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleRangeValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ImageValue
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [LocationIndicatorLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#location-indicator)
 */
@Stable
public class LocationIndicatorLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialAccuracyRadius: DoubleValue,
  initialAccuracyRadiusTransition: Transition,
  initialAccuracyRadiusBorderColor: ColorValue,
  initialAccuracyRadiusBorderColorUseTheme: StringValue,
  initialAccuracyRadiusBorderColorTransition: Transition,
  initialAccuracyRadiusColor: ColorValue,
  initialAccuracyRadiusColorUseTheme: StringValue,
  initialAccuracyRadiusColorTransition: Transition,
  initialBearing: DoubleValue,
  initialBearingTransition: Transition,
  initialBearingImage: ImageValue,
  initialBearingImageSize: DoubleValue,
  initialBearingImageSizeTransition: Transition,
  initialEmphasisCircleColor: ColorValue,
  initialEmphasisCircleColorUseTheme: StringValue,
  initialEmphasisCircleColorTransition: Transition,
  initialEmphasisCircleGlowRange: DoubleRangeValue,
  initialEmphasisCircleGlowRangeTransition: Transition,
  initialEmphasisCircleRadius: DoubleValue,
  initialEmphasisCircleRadiusTransition: Transition,
  initialImagePitchDisplacement: DoubleValue,
  initialLocation: DoubleListValue,
  initialLocationTransition: Transition,
  initialLocationIndicatorOpacity: DoubleValue,
  initialLocationIndicatorOpacityTransition: Transition,
  initialPerspectiveCompensation: DoubleValue,
  initialShadowImage: ImageValue,
  initialShadowImageSize: DoubleValue,
  initialShadowImageSizeTransition: Transition,
  initialTopImage: ImageValue,
  initialTopImageSize: DoubleValue,
  initialTopImageSizeTransition: Transition,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
) {
  /**
   * Construct an default [LocationIndicatorLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialAccuracyRadius = DoubleValue.INITIAL,
    initialAccuracyRadiusTransition = Transition.INITIAL,
    initialAccuracyRadiusBorderColor = ColorValue.INITIAL,
    initialAccuracyRadiusBorderColorUseTheme = StringValue.INITIAL,
    initialAccuracyRadiusBorderColorTransition = Transition.INITIAL,
    initialAccuracyRadiusColor = ColorValue.INITIAL,
    initialAccuracyRadiusColorUseTheme = StringValue.INITIAL,
    initialAccuracyRadiusColorTransition = Transition.INITIAL,
    initialBearing = DoubleValue.INITIAL,
    initialBearingTransition = Transition.INITIAL,
    initialBearingImage = ImageValue.INITIAL,
    initialBearingImageSize = DoubleValue.INITIAL,
    initialBearingImageSizeTransition = Transition.INITIAL,
    initialEmphasisCircleColor = ColorValue.INITIAL,
    initialEmphasisCircleColorUseTheme = StringValue.INITIAL,
    initialEmphasisCircleColorTransition = Transition.INITIAL,
    initialEmphasisCircleGlowRange = DoubleRangeValue.INITIAL,
    initialEmphasisCircleGlowRangeTransition = Transition.INITIAL,
    initialEmphasisCircleRadius = DoubleValue.INITIAL,
    initialEmphasisCircleRadiusTransition = Transition.INITIAL,
    initialImagePitchDisplacement = DoubleValue.INITIAL,
    initialLocation = DoubleListValue.INITIAL,
    initialLocationTransition = Transition.INITIAL,
    initialLocationIndicatorOpacity = DoubleValue.INITIAL,
    initialLocationIndicatorOpacityTransition = Transition.INITIAL,
    initialPerspectiveCompensation = DoubleValue.INITIAL,
    initialShadowImage = ImageValue.INITIAL,
    initialShadowImageSize = DoubleValue.INITIAL,
    initialShadowImageSizeTransition = Transition.INITIAL,
    initialTopImage = ImageValue.INITIAL,
    initialTopImageSize = DoubleValue.INITIAL,
    initialTopImageSizeTransition = Transition.INITIAL,
    initialVisibility = VisibilityValue.INITIAL,
    initialMinZoom = LongValue.INITIAL,
    initialMaxZoom = LongValue.INITIAL,
    initialSourceLayer = StringValue.INITIAL,
    initialFilter = Filter.INITIAL,
  )

  private val accuracyRadiusState: MutableState<DoubleValue> = mutableStateOf(initialAccuracyRadius)
  /**
   *  The accuracy, in meters, of the position source used to retrieve the position of the location indicator. Default value: 0. The unit of accuracyRadius is in meters.
   */
  public var accuracyRadius: DoubleValue by accuracyRadiusState

  private val accuracyRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialAccuracyRadiusTransition)
  /**
   *  Defines the transition of [accuracyRadius].
   */
  public var accuracyRadiusTransition: Transition by accuracyRadiusTransitionState

  private val accuracyRadiusBorderColorState: MutableState<ColorValue> = mutableStateOf(initialAccuracyRadiusBorderColor)
  /**
   *  The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly. Default value: "#ffffff".
   */
  public var accuracyRadiusBorderColor: ColorValue by accuracyRadiusBorderColorState

  @MapboxExperimental
  private val accuracyRadiusBorderColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialAccuracyRadiusBorderColorUseTheme)
  /**
   *  Overrides applying of color theme for [accuracyRadiusBorderColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var accuracyRadiusBorderColorUseTheme: StringValue by accuracyRadiusBorderColorUseThemeState

  private val accuracyRadiusBorderColorTransitionState: MutableState<Transition> = mutableStateOf(initialAccuracyRadiusBorderColorTransition)
  /**
   *  Defines the transition of [accuracyRadiusBorderColor].
   */
  public var accuracyRadiusBorderColorTransition: Transition by accuracyRadiusBorderColorTransitionState

  private val accuracyRadiusColorState: MutableState<ColorValue> = mutableStateOf(initialAccuracyRadiusColor)
  /**
   *  The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly. Default value: "#ffffff".
   */
  public var accuracyRadiusColor: ColorValue by accuracyRadiusColorState

  @MapboxExperimental
  private val accuracyRadiusColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialAccuracyRadiusColorUseTheme)
  /**
   *  Overrides applying of color theme for [accuracyRadiusColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var accuracyRadiusColorUseTheme: StringValue by accuracyRadiusColorUseThemeState

  private val accuracyRadiusColorTransitionState: MutableState<Transition> = mutableStateOf(initialAccuracyRadiusColorTransition)
  /**
   *  Defines the transition of [accuracyRadiusColor].
   */
  public var accuracyRadiusColorTransition: Transition by accuracyRadiusColorTransitionState

  private val bearingState: MutableState<DoubleValue> = mutableStateOf(initialBearing)
  /**
   *  The bearing of the location indicator. Values under 0.01 degree variation are ignored. Default value: 0. The unit of bearing is in degrees.
   */
  public var bearing: DoubleValue by bearingState

  private val bearingTransitionState: MutableState<Transition> = mutableStateOf(initialBearingTransition)
  /**
   *  Defines the transition of [bearing].
   */
  public var bearingTransition: Transition by bearingTransitionState

  private val bearingImageState: MutableState<ImageValue> = mutableStateOf(initialBearingImage)
  /**
   *  Name of image in sprite to use as the middle of the location indicator.
   */
  public var bearingImage: ImageValue by bearingImageState

  private val bearingImageSizeState: MutableState<DoubleValue> = mutableStateOf(initialBearingImageSize)
  /**
   *  The size of the bearing image, as a scale factor applied to the size of the specified image. Default value: 1. The unit of bearingImageSize is in factor of the original icon size.
   */
  public var bearingImageSize: DoubleValue by bearingImageSizeState

  private val bearingImageSizeTransitionState: MutableState<Transition> = mutableStateOf(initialBearingImageSizeTransition)
  /**
   *  Defines the transition of [bearingImageSize].
   */
  public var bearingImageSizeTransition: Transition by bearingImageSizeTransitionState

  private val emphasisCircleColorState: MutableState<ColorValue> = mutableStateOf(initialEmphasisCircleColor)
  /**
   *  The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly. Default value: "#ffffff".
   */
  public var emphasisCircleColor: ColorValue by emphasisCircleColorState

  @MapboxExperimental
  private val emphasisCircleColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialEmphasisCircleColorUseTheme)
  /**
   *  Overrides applying of color theme for [emphasisCircleColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var emphasisCircleColorUseTheme: StringValue by emphasisCircleColorUseThemeState

  private val emphasisCircleColorTransitionState: MutableState<Transition> = mutableStateOf(initialEmphasisCircleColorTransition)
  /**
   *  Defines the transition of [emphasisCircleColor].
   */
  public var emphasisCircleColorTransition: Transition by emphasisCircleColorTransitionState

  private val emphasisCircleGlowRangeState: MutableState<DoubleRangeValue> = mutableStateOf(initialEmphasisCircleGlowRange)
  /**
   *  Specifies a glow effect range of the emphasis circle, in pixels. If [0,0] values are provided, it renders the circle as a solid color. The first value specifies the start of the glow effect where it is equal to the circle's color, the second is the end, where it's fully transparent. Between the two values the effect is linearly faded out. Default value: [0,0].
   */
  public var emphasisCircleGlowRange: DoubleRangeValue by emphasisCircleGlowRangeState

  private val emphasisCircleGlowRangeTransitionState: MutableState<Transition> = mutableStateOf(initialEmphasisCircleGlowRangeTransition)
  /**
   *  Defines the transition of [emphasisCircleGlowRange].
   */
  public var emphasisCircleGlowRangeTransition: Transition by emphasisCircleGlowRangeTransitionState

  private val emphasisCircleRadiusState: MutableState<DoubleValue> = mutableStateOf(initialEmphasisCircleRadius)
  /**
   *  The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow. Default value: 0. The unit of emphasisCircleRadius is in pixels.
   */
  public var emphasisCircleRadius: DoubleValue by emphasisCircleRadiusState

  private val emphasisCircleRadiusTransitionState: MutableState<Transition> = mutableStateOf(initialEmphasisCircleRadiusTransition)
  /**
   *  Defines the transition of [emphasisCircleRadius].
   */
  public var emphasisCircleRadiusTransition: Transition by emphasisCircleRadiusTransitionState

  private val imagePitchDisplacementState: MutableState<DoubleValue> = mutableStateOf(initialImagePitchDisplacement)
  /**
   *  The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence. Default value: "0". The unit of imagePitchDisplacement is in pixels.
   */
  public var imagePitchDisplacement: DoubleValue by imagePitchDisplacementState

  private val locationState: MutableState<DoubleListValue> = mutableStateOf(initialLocation)
  /**
   *  An array of [latitude, longitude, altitude] position of the location indicator. Values under 0.000001 variation are ignored. Default value: [0,0,0].
   */
  public var location: DoubleListValue by locationState

  private val locationTransitionState: MutableState<Transition> = mutableStateOf(initialLocationTransition)
  /**
   *  Defines the transition of [location].
   */
  public var locationTransition: Transition by locationTransitionState

  private val locationIndicatorOpacityState: MutableState<DoubleValue> = mutableStateOf(initialLocationIndicatorOpacity)
  /**
   *  The opacity of the entire location indicator layer. Default value: 1. Value range: [0, 1]
   */
  public var locationIndicatorOpacity: DoubleValue by locationIndicatorOpacityState

  private val locationIndicatorOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialLocationIndicatorOpacityTransition)
  /**
   *  Defines the transition of [locationIndicatorOpacity].
   */
  public var locationIndicatorOpacityTransition: Transition by locationIndicatorOpacityTransitionState

  private val perspectiveCompensationState: MutableState<DoubleValue> = mutableStateOf(initialPerspectiveCompensation)
  /**
   *  The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection. Default value: "0.85".
   */
  public var perspectiveCompensation: DoubleValue by perspectiveCompensationState

  private val shadowImageState: MutableState<ImageValue> = mutableStateOf(initialShadowImage)
  /**
   *  Name of image in sprite to use as the background of the location indicator.
   */
  public var shadowImage: ImageValue by shadowImageState

  private val shadowImageSizeState: MutableState<DoubleValue> = mutableStateOf(initialShadowImageSize)
  /**
   *  The size of the shadow image, as a scale factor applied to the size of the specified image. Default value: 1. The unit of shadowImageSize is in factor of the original icon size.
   */
  public var shadowImageSize: DoubleValue by shadowImageSizeState

  private val shadowImageSizeTransitionState: MutableState<Transition> = mutableStateOf(initialShadowImageSizeTransition)
  /**
   *  Defines the transition of [shadowImageSize].
   */
  public var shadowImageSizeTransition: Transition by shadowImageSizeTransitionState

  private val topImageState: MutableState<ImageValue> = mutableStateOf(initialTopImage)
  /**
   *  Name of image in sprite to use as the top of the location indicator.
   */
  public var topImage: ImageValue by topImageState

  private val topImageSizeState: MutableState<DoubleValue> = mutableStateOf(initialTopImageSize)
  /**
   *  The size of the top image, as a scale factor applied to the size of the specified image. Default value: 1. The unit of topImageSize is in factor of the original icon size.
   */
  public var topImageSize: DoubleValue by topImageSizeState

  private val topImageSizeTransitionState: MutableState<Transition> = mutableStateOf(initialTopImageSizeTransition)
  /**
   *  Defines the transition of [topImageSize].
   */
  public var topImageSizeTransition: Transition by topImageSizeTransitionState

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

  private val sourceLayerState: MutableState<StringValue> = mutableStateOf(initialSourceLayer)
  /**
   *  Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
   */
  public var sourceLayer: StringValue by sourceLayerState

  private val filterState: MutableState<Filter> = mutableStateOf(initialFilter)
  /**
   *  An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
   */
  public var filter: Filter by filterState

  @Composable
  @OptIn(MapboxExperimental::class)
  internal fun UpdateProperties(layerNode: LayerNode) {
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusState, "accuracy-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusTransitionState, "accuracy-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusBorderColorState, "accuracy-radius-border-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusBorderColorUseThemeState, "accuracy-radius-border-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusBorderColorTransitionState, "accuracy-radius-border-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusColorState, "accuracy-radius-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusColorUseThemeState, "accuracy-radius-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, accuracyRadiusColorTransitionState, "accuracy-radius-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, bearingState, "bearing")
    ActionWhenNotInitial(layerNode.setPropertyAction, bearingTransitionState, "bearing-transition")
    AddImageWhenNotInitial(layerNode, bearingImageState, "bearing-image")
    ActionWhenNotInitial(layerNode.setPropertyAction, bearingImageSizeState, "bearing-image-size")
    ActionWhenNotInitial(layerNode.setPropertyAction, bearingImageSizeTransitionState, "bearing-image-size-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, emphasisCircleColorState, "emphasis-circle-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, emphasisCircleColorUseThemeState, "emphasis-circle-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, emphasisCircleColorTransitionState, "emphasis-circle-color-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, emphasisCircleGlowRangeState, "emphasis-circle-glow-range")
    ActionWhenNotInitial(layerNode.setPropertyAction, emphasisCircleGlowRangeTransitionState, "emphasis-circle-glow-range-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, emphasisCircleRadiusState, "emphasis-circle-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, emphasisCircleRadiusTransitionState, "emphasis-circle-radius-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, imagePitchDisplacementState, "image-pitch-displacement")
    ActionWhenNotInitial(layerNode.setPropertyAction, locationState, "location")
    ActionWhenNotInitial(layerNode.setPropertyAction, locationTransitionState, "location-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, locationIndicatorOpacityState, "location-indicator-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, locationIndicatorOpacityTransitionState, "location-indicator-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, perspectiveCompensationState, "perspective-compensation")
    AddImageWhenNotInitial(layerNode, shadowImageState, "shadow-image")
    ActionWhenNotInitial(layerNode.setPropertyAction, shadowImageSizeState, "shadow-image-size")
    ActionWhenNotInitial(layerNode.setPropertyAction, shadowImageSizeTransitionState, "shadow-image-size-transition")
    AddImageWhenNotInitial(layerNode, topImageState, "top-image")
    ActionWhenNotInitial(layerNode.setPropertyAction, topImageSizeState, "top-image-size")
    ActionWhenNotInitial(layerNode.setPropertyAction, topImageSizeTransitionState, "top-image-size-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.