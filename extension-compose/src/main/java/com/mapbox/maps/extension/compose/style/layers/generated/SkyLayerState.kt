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
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [SkyLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#sky)
 */
@Stable
public class SkyLayerState
@OptIn(MapboxExperimental::class)
private constructor(
  initialSkyAtmosphereColor: ColorValue,
  initialSkyAtmosphereColorUseTheme: StringValue,
  initialSkyAtmosphereHaloColor: ColorValue,
  initialSkyAtmosphereHaloColorUseTheme: StringValue,
  initialSkyAtmosphereSun: DoubleListValue,
  initialSkyAtmosphereSunIntensity: DoubleValue,
  initialSkyGradient: ColorValue,
  initialSkyGradientUseTheme: StringValue,
  initialSkyGradientCenter: DoubleListValue,
  initialSkyGradientRadius: DoubleValue,
  initialSkyOpacity: DoubleValue,
  initialSkyOpacityTransition: Transition,
  initialSkyType: SkyTypeValue,
  initialVisibility: VisibilityValue,
  initialMinZoom: LongValue,
  initialMaxZoom: LongValue,
  initialSourceLayer: StringValue,
  initialFilter: Filter,
) {
  /**
   * Construct an default [SkyLayerState].
   */
  @OptIn(MapboxExperimental::class)
  public constructor() : this(
    initialSkyAtmosphereColor = ColorValue.INITIAL,
    initialSkyAtmosphereColorUseTheme = StringValue.INITIAL,
    initialSkyAtmosphereHaloColor = ColorValue.INITIAL,
    initialSkyAtmosphereHaloColorUseTheme = StringValue.INITIAL,
    initialSkyAtmosphereSun = DoubleListValue.INITIAL,
    initialSkyAtmosphereSunIntensity = DoubleValue.INITIAL,
    initialSkyGradient = ColorValue.INITIAL,
    initialSkyGradientUseTheme = StringValue.INITIAL,
    initialSkyGradientCenter = DoubleListValue.INITIAL,
    initialSkyGradientRadius = DoubleValue.INITIAL,
    initialSkyOpacity = DoubleValue.INITIAL,
    initialSkyOpacityTransition = Transition.INITIAL,
    initialSkyType = SkyTypeValue.INITIAL,
    initialVisibility = VisibilityValue.INITIAL,
    initialMinZoom = LongValue.INITIAL,
    initialMaxZoom = LongValue.INITIAL,
    initialSourceLayer = StringValue.INITIAL,
    initialFilter = Filter.INITIAL,
  )

  private val skyAtmosphereColorState: MutableState<ColorValue> = mutableStateOf(initialSkyAtmosphereColor)
  /**
   *  A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density. Default value: "white".
   */
  public var skyAtmosphereColor: ColorValue by skyAtmosphereColorState

  @MapboxExperimental
  private val skyAtmosphereColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialSkyAtmosphereColorUseTheme)
  /**
   *  Overrides applying of color theme for [skyAtmosphereColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var skyAtmosphereColorUseTheme: StringValue by skyAtmosphereColorUseThemeState

  private val skyAtmosphereHaloColorState: MutableState<ColorValue> = mutableStateOf(initialSkyAtmosphereHaloColor)
  /**
   *  A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer. Default value: "white".
   */
  public var skyAtmosphereHaloColor: ColorValue by skyAtmosphereHaloColorState

  @MapboxExperimental
  private val skyAtmosphereHaloColorUseThemeState: MutableState<StringValue> = mutableStateOf(initialSkyAtmosphereHaloColorUseTheme)
  /**
   *  Overrides applying of color theme for [skyAtmosphereHaloColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var skyAtmosphereHaloColorUseTheme: StringValue by skyAtmosphereHaloColorUseThemeState

  private val skyAtmosphereSunState: MutableState<DoubleListValue> = mutableStateOf(initialSkyAtmosphereSun)
  /**
   *  Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position. Minimum value: [0,0]. Maximum value: [360,180]. The unit of skyAtmosphereSun is in degrees.
   */
  public var skyAtmosphereSun: DoubleListValue by skyAtmosphereSunState

  private val skyAtmosphereSunIntensityState: MutableState<DoubleValue> = mutableStateOf(initialSkyAtmosphereSunIntensity)
  /**
   *  Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky. Default value: 10. Value range: [0, 100]
   */
  public var skyAtmosphereSunIntensity: DoubleValue by skyAtmosphereSunIntensityState

  private val skyGradientState: MutableState<ColorValue> = mutableStateOf(initialSkyGradient)
  /**
   *  Defines a radial color gradient with which to color the sky. The color values can be interpolated with an expression using `sky-radial-progress`. The range [0, 1] for the interpolant covers a radial distance (in degrees) of [0, `sky-gradient-radius`] centered at the position specified by `sky-gradient-center`. Default value: ["interpolate",["linear"],["sky-radial-progress"],0.8,"#87ceeb",1,"white"].
   */
  public var skyGradient: ColorValue by skyGradientState

  @MapboxExperimental
  private val skyGradientUseThemeState: MutableState<StringValue> = mutableStateOf(initialSkyGradientUseTheme)
  /**
   *  Overrides applying of color theme for [skyGradient] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var skyGradientUseTheme: StringValue by skyGradientUseThemeState

  private val skyGradientCenterState: MutableState<DoubleListValue> = mutableStateOf(initialSkyGradientCenter)
  /**
   *  Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [360,180]. The unit of skyGradientCenter is in degrees.
   */
  public var skyGradientCenter: DoubleListValue by skyGradientCenterState

  private val skyGradientRadiusState: MutableState<DoubleValue> = mutableStateOf(initialSkyGradientRadius)
  /**
   *  The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`. Default value: 90. Value range: [0, 180]
   */
  public var skyGradientRadius: DoubleValue by skyGradientRadiusState

  private val skyOpacityState: MutableState<DoubleValue> = mutableStateOf(initialSkyOpacity)
  /**
   *  The opacity of the entire sky layer. Default value: 1. Value range: [0, 1]
   */
  public var skyOpacity: DoubleValue by skyOpacityState

  private val skyOpacityTransitionState: MutableState<Transition> = mutableStateOf(initialSkyOpacityTransition)
  /**
   *  Defines the transition of [skyOpacity].
   */
  public var skyOpacityTransition: Transition by skyOpacityTransitionState

  private val skyTypeState: MutableState<SkyTypeValue> = mutableStateOf(initialSkyType)
  /**
   *  The type of the sky Default value: "atmosphere".
   */
  public var skyType: SkyTypeValue by skyTypeState

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
    ActionWhenNotInitial(layerNode.setPropertyAction, skyAtmosphereColorState, "sky-atmosphere-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyAtmosphereColorUseThemeState, "sky-atmosphere-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyAtmosphereHaloColorState, "sky-atmosphere-halo-color")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyAtmosphereHaloColorUseThemeState, "sky-atmosphere-halo-color-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyAtmosphereSunState, "sky-atmosphere-sun")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyAtmosphereSunIntensityState, "sky-atmosphere-sun-intensity")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyGradientState, "sky-gradient")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyGradientUseThemeState, "sky-gradient-use-theme")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyGradientCenterState, "sky-gradient-center")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyGradientRadiusState, "sky-gradient-radius")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyOpacityState, "sky-opacity")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyOpacityTransitionState, "sky-opacity-transition")
    ActionWhenNotInitial(layerNode.setPropertyAction, skyTypeState, "sky-type")
    ActionWhenNotInitial(layerNode.setPropertyAction, visibilityState, "visibility")
    ActionWhenNotInitial(layerNode.setPropertyAction, minZoomState, "minzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, maxZoomState, "maxzoom")
    ActionWhenNotInitial(layerNode.setPropertyAction, sourceLayerState, "source-layer")
    ActionWhenNotInitial(layerNode.setPropertyAction, filterState, "filter")
  }
}
// End of generated file.