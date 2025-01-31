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
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * The state holder for [SkyLayer]'s layer properties.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#sky)
 */
@Stable
public class SkyLayerState private constructor(
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

  /**
   *  A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density. Default value: "white".
   */
  public var skyAtmosphereColor: ColorValue by mutableStateOf(initialSkyAtmosphereColor)
  /**
   *  Overrides applying of color theme for [skyAtmosphereColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var skyAtmosphereColorUseTheme: StringValue by mutableStateOf(initialSkyAtmosphereColorUseTheme)
  /**
   *  A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer. Default value: "white".
   */
  public var skyAtmosphereHaloColor: ColorValue by mutableStateOf(initialSkyAtmosphereHaloColor)
  /**
   *  Overrides applying of color theme for [skyAtmosphereHaloColor] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var skyAtmosphereHaloColorUseTheme: StringValue by mutableStateOf(initialSkyAtmosphereHaloColorUseTheme)
  /**
   *  Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position. Minimum value: [0,0]. Maximum value: [360,180]. The unit of skyAtmosphereSun is in degrees.
   */
  public var skyAtmosphereSun: DoubleListValue by mutableStateOf(initialSkyAtmosphereSun)
  /**
   *  Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky. Default value: 10. Value range: [0, 100]
   */
  public var skyAtmosphereSunIntensity: DoubleValue by mutableStateOf(initialSkyAtmosphereSunIntensity)
  /**
   *  Defines a radial color gradient with which to color the sky. The color values can be interpolated with an expression using `sky-radial-progress`. The range [0, 1] for the interpolant covers a radial distance (in degrees) of [0, `sky-gradient-radius`] centered at the position specified by `sky-gradient-center`. Default value: ["interpolate",["linear"],["sky-radial-progress"],0.8,"#87ceeb",1,"white"].
   */
  public var skyGradient: ColorValue by mutableStateOf(initialSkyGradient)
  /**
   *  Overrides applying of color theme for [skyGradient] if "none" is set. To follow default theme "default" should be set. Default value: "default".
   */
  @MapboxExperimental
  public var skyGradientUseTheme: StringValue by mutableStateOf(initialSkyGradientUseTheme)
  /**
   *  Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [360,180]. The unit of skyGradientCenter is in degrees.
   */
  public var skyGradientCenter: DoubleListValue by mutableStateOf(initialSkyGradientCenter)
  /**
   *  The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`. Default value: 90. Value range: [0, 180]
   */
  public var skyGradientRadius: DoubleValue by mutableStateOf(initialSkyGradientRadius)
  /**
   *  The opacity of the entire sky layer. Default value: 1. Value range: [0, 1]
   */
  public var skyOpacity: DoubleValue by mutableStateOf(initialSkyOpacity)
  /**
   *  Defines the transition of [skyOpacity].
   */
  public var skyOpacityTransition: Transition by mutableStateOf(initialSkyOpacityTransition)
  /**
   *  The type of the sky Default value: "atmosphere".
   */
  public var skyType: SkyTypeValue by mutableStateOf(initialSkyType)
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
  private fun UpdateSkyAtmosphereColor(layerNode: LayerNode) {
    if (skyAtmosphereColor.notInitial) {
      layerNode.setProperty("sky-atmosphere-color", skyAtmosphereColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSkyAtmosphereColorUseTheme(layerNode: LayerNode) {
    if (skyAtmosphereColorUseTheme.notInitial) {
      layerNode.setProperty("sky-atmosphere-color-use-theme", skyAtmosphereColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateSkyAtmosphereHaloColor(layerNode: LayerNode) {
    if (skyAtmosphereHaloColor.notInitial) {
      layerNode.setProperty("sky-atmosphere-halo-color", skyAtmosphereHaloColor.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSkyAtmosphereHaloColorUseTheme(layerNode: LayerNode) {
    if (skyAtmosphereHaloColorUseTheme.notInitial) {
      layerNode.setProperty("sky-atmosphere-halo-color-use-theme", skyAtmosphereHaloColorUseTheme.value)
    }
  }
  @Composable
  private fun UpdateSkyAtmosphereSun(layerNode: LayerNode) {
    if (skyAtmosphereSun.notInitial) {
      layerNode.setProperty("sky-atmosphere-sun", skyAtmosphereSun.value)
    }
  }
  @Composable
  private fun UpdateSkyAtmosphereSunIntensity(layerNode: LayerNode) {
    if (skyAtmosphereSunIntensity.notInitial) {
      layerNode.setProperty("sky-atmosphere-sun-intensity", skyAtmosphereSunIntensity.value)
    }
  }
  @Composable
  private fun UpdateSkyGradient(layerNode: LayerNode) {
    if (skyGradient.notInitial) {
      layerNode.setProperty("sky-gradient", skyGradient.value)
    }
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateSkyGradientUseTheme(layerNode: LayerNode) {
    if (skyGradientUseTheme.notInitial) {
      layerNode.setProperty("sky-gradient-use-theme", skyGradientUseTheme.value)
    }
  }
  @Composable
  private fun UpdateSkyGradientCenter(layerNode: LayerNode) {
    if (skyGradientCenter.notInitial) {
      layerNode.setProperty("sky-gradient-center", skyGradientCenter.value)
    }
  }
  @Composable
  private fun UpdateSkyGradientRadius(layerNode: LayerNode) {
    if (skyGradientRadius.notInitial) {
      layerNode.setProperty("sky-gradient-radius", skyGradientRadius.value)
    }
  }
  @Composable
  private fun UpdateSkyOpacity(layerNode: LayerNode) {
    if (skyOpacity.notInitial) {
      layerNode.setProperty("sky-opacity", skyOpacity.value)
    }
  }
  @Composable
  private fun UpdateSkyOpacityTransition(layerNode: LayerNode) {
    if (skyOpacityTransition.notInitial) {
      layerNode.setProperty("sky-opacity-transition", skyOpacityTransition.value)
    }
  }
  @Composable
  private fun UpdateSkyType(layerNode: LayerNode) {
    if (skyType.notInitial) {
      layerNode.setProperty("sky-type", skyType.value)
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
    UpdateSkyAtmosphereColor(layerNode)
    UpdateSkyAtmosphereColorUseTheme(layerNode)
    UpdateSkyAtmosphereHaloColor(layerNode)
    UpdateSkyAtmosphereHaloColorUseTheme(layerNode)
    UpdateSkyAtmosphereSun(layerNode)
    UpdateSkyAtmosphereSunIntensity(layerNode)
    UpdateSkyGradient(layerNode)
    UpdateSkyGradientUseTheme(layerNode)
    UpdateSkyGradientCenter(layerNode)
    UpdateSkyGradientRadius(layerNode)
    UpdateSkyOpacity(layerNode)
    UpdateSkyOpacityTransition(layerNode)
    UpdateSkyType(layerNode)
    UpdateVisibility(layerNode)
    UpdateMinZoom(layerNode)
    UpdateMaxZoom(layerNode)
    UpdateSourceLayer(layerNode)
    UpdateFilter(layerNode)
  }
}
// End of generated file.