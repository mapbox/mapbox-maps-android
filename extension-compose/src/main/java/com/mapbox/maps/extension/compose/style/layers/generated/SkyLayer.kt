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
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * A spherical dome around the map that is always rendered behind all other layers.
 *
 * **Warning**: As of v10.6.0, [Atmosphere] is the preferred method for atmospheric styling.
 * Sky layer is not supported by the globe projection, and will be phased out in future major release.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#sky)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param skyAtmosphereColor A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density. Default value: "white".
 * @param skyAtmosphereHaloColor A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer. Default value: "white".
 * @param skyAtmosphereSun Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position. Minimum value: [0,0]. Maximum value: [360,180].
 * @param skyAtmosphereSunIntensity Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky. Default value: 10. Value range: [0, 100]
 * @param skyGradient Defines a radial color gradient with which to color the sky. The color values can be interpolated with an expression using `sky-radial-progress`. The range [0, 1] for the interpolant covers a radial distance (in degrees) of [0, `sky-gradient-radius`] centered at the position specified by `sky-gradient-center`. Default value: ["interpolate",["linear"],["sky-radial-progress"],0.8,"#87ceeb",1,"white"].
 * @param skyGradientCenter Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon. Default value: [0,0]. Minimum value: [0,0]. Maximum value: [360,180].
 * @param skyGradientRadius The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`. Default value: 90. Value range: [0, 180]
 * @param skyOpacity The opacity of the entire sky layer. Default value: 1. Value range: [0, 1]
 * @param skyOpacityTransition Defines the transition of [skyOpacity]. Default value: 1. Value range: [0, 1]
 * @param skyType The type of the sky Default value: "atmosphere".
 * @param visibility Whether this layer is displayed. Default value: "visible".
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden. Value range: [0, 24]
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden. Value range: [0, 24]
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun SkyLayer(
  layerId: String = remember {
    generateRandomLayerId("sky")
  },
  skyAtmosphereColor: ColorValue = ColorValue.INITIAL,
  skyAtmosphereHaloColor: ColorValue = ColorValue.INITIAL,
  skyAtmosphereSun: DoubleListValue = DoubleListValue.INITIAL,
  skyAtmosphereSunIntensity: DoubleValue = DoubleValue.INITIAL,
  skyGradient: ColorValue = ColorValue.INITIAL,
  skyGradientCenter: DoubleListValue = DoubleListValue.INITIAL,
  skyGradientRadius: DoubleValue = DoubleValue.INITIAL,
  skyOpacity: DoubleValue = DoubleValue.INITIAL,
  skyOpacityTransition: Transition = Transition.INITIAL,
  skyType: SkyTypeValue = SkyTypeValue.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SkyLayer inside unsupported composable function")

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<LayerNode, MapApplier>(
    factory = {
      LayerNode(
        map = mapApplier.mapView.mapboxMap,
        layerType = "sky",
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (skyAtmosphereColor.notInitial) {
          setProperty("sky-atmosphere-color", skyAtmosphereColor.value)
        }
        if (skyAtmosphereHaloColor.notInitial) {
          setProperty("sky-atmosphere-halo-color", skyAtmosphereHaloColor.value)
        }
        if (skyAtmosphereSun.notInitial) {
          setProperty("sky-atmosphere-sun", skyAtmosphereSun.value)
        }
        if (skyAtmosphereSunIntensity.notInitial) {
          setProperty("sky-atmosphere-sun-intensity", skyAtmosphereSunIntensity.value)
        }
        if (skyGradient.notInitial) {
          setProperty("sky-gradient", skyGradient.value)
        }
        if (skyGradientCenter.notInitial) {
          setProperty("sky-gradient-center", skyGradientCenter.value)
        }
        if (skyGradientRadius.notInitial) {
          setProperty("sky-gradient-radius", skyGradientRadius.value)
        }
        if (skyOpacity.notInitial) {
          setProperty("sky-opacity", skyOpacity.value)
        }
        if (skyOpacityTransition.notInitial) {
          setProperty("sky-opacity-transition", skyOpacityTransition.value)
        }
        if (skyType.notInitial) {
          setProperty("sky-type", skyType.value)
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
      update(layerId) {
        updateLayerId(layerId)
      }
      update(skyAtmosphereColor) {
        setProperty("sky-atmosphere-color", skyAtmosphereColor.value)
      }
      update(skyAtmosphereHaloColor) {
        setProperty("sky-atmosphere-halo-color", skyAtmosphereHaloColor.value)
      }
      update(skyAtmosphereSun) {
        setProperty("sky-atmosphere-sun", skyAtmosphereSun.value)
      }
      update(skyAtmosphereSunIntensity) {
        setProperty("sky-atmosphere-sun-intensity", skyAtmosphereSunIntensity.value)
      }
      update(skyGradient) {
        setProperty("sky-gradient", skyGradient.value)
      }
      update(skyGradientCenter) {
        setProperty("sky-gradient-center", skyGradientCenter.value)
      }
      update(skyGradientRadius) {
        setProperty("sky-gradient-radius", skyGradientRadius.value)
      }
      update(skyOpacity) {
        setProperty("sky-opacity", skyOpacity.value)
      }
      update(skyOpacityTransition) {
        setProperty("sky-opacity-transition", skyOpacityTransition.value)
      }
      update(skyType) {
        setProperty("sky-type", skyType.value)
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
}
// End of generated file.