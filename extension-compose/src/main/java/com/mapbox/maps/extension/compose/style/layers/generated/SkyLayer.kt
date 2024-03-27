// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode

/**
 * A spherical dome around the map that is always rendered behind all other layers.
 *
 * **Warning**: As of v10.6.0, [Atmosphere] is the preferred method for atmospheric styling.
 * Sky layer is not supported by the globe projection, and will be phased out in future major release.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-sky)
 *
 * @param layerId the ID of the layer
 * @param skyAtmosphereColor A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
 * @param skyAtmosphereHaloColor A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
 * @param skyAtmosphereSun Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position.
 * @param skyAtmosphereSunIntensity Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky.
 * @param skyGradient Defines a radial color gradient with which to color the sky. The color values can be interpolated with an expression using `sky-radial-progress`. The range [0, 1] for the interpolant covers a radial distance (in degrees) of [0, `sky-gradient-radius`] centered at the position specified by `sky-gradient-center`.
 * @param skyGradientCenter Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon.
 * @param skyGradientRadius The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`.
 * @param skyOpacity The opacity of the entire sky layer.
 * @param skyType The type of the sky
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun SkyLayer(
  layerId: String,
  skyAtmosphereColor: SkyAtmosphereColor = SkyAtmosphereColor.default,
  skyAtmosphereHaloColor: SkyAtmosphereHaloColor = SkyAtmosphereHaloColor.default,
  skyAtmosphereSun: SkyAtmosphereSun = SkyAtmosphereSun.default,
  skyAtmosphereSunIntensity: SkyAtmosphereSunIntensity = SkyAtmosphereSunIntensity.default,
  skyGradient: SkyGradient = SkyGradient.default,
  skyGradientCenter: SkyGradientCenter = SkyGradientCenter.default,
  skyGradientRadius: SkyGradientRadius = SkyGradientRadius.default,
  skyOpacity: SkyOpacity = SkyOpacity.default,
  skyOpacityTransition: Transition = Transition.default,
  skyType: SkyType = SkyType.default,
  visibility: Visibility = Visibility.default,
  minZoom: MinZoom = MinZoom.default,
  maxZoom: MaxZoom = MaxZoom.default,
  sourceLayer: SourceLayer = SourceLayer.default,
  filter: Filter = Filter.default,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of SymbolLayer inside unsupported composable function")

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
        if (skyAtmosphereColor != SkyAtmosphereColor.default) {
          setProperty(SkyAtmosphereColor.NAME, skyAtmosphereColor.value)
        }
        if (skyAtmosphereHaloColor != SkyAtmosphereHaloColor.default) {
          setProperty(SkyAtmosphereHaloColor.NAME, skyAtmosphereHaloColor.value)
        }
        if (skyAtmosphereSun != SkyAtmosphereSun.default) {
          setProperty(SkyAtmosphereSun.NAME, skyAtmosphereSun.value)
        }
        if (skyAtmosphereSunIntensity != SkyAtmosphereSunIntensity.default) {
          setProperty(SkyAtmosphereSunIntensity.NAME, skyAtmosphereSunIntensity.value)
        }
        if (skyGradient != SkyGradient.default) {
          setProperty(SkyGradient.NAME, skyGradient.value)
        }
        if (skyGradientCenter != SkyGradientCenter.default) {
          setProperty(SkyGradientCenter.NAME, skyGradientCenter.value)
        }
        if (skyGradientRadius != SkyGradientRadius.default) {
          setProperty(SkyGradientRadius.NAME, skyGradientRadius.value)
        }
        if (skyOpacity != SkyOpacity.default) {
          setProperty(SkyOpacity.NAME, skyOpacity.value)
        }
        if (skyOpacityTransition != Transition.default) {
          setProperty(SkyOpacity.TRANSITION_NAME, skyOpacityTransition.value)
        }
        if (skyType != SkyType.default) {
          setProperty(SkyType.NAME, skyType.value)
        }
        if (visibility != Visibility.default) {
          setProperty(Visibility.NAME, visibility.value)
        }
        if (minZoom != MinZoom.default) {
          setProperty(MinZoom.NAME, minZoom.value)
        }
        if (maxZoom != MaxZoom.default) {
          setProperty(MaxZoom.NAME, maxZoom.value)
        }
        if (sourceLayer != SourceLayer.default) {
          setProperty(SourceLayer.NAME, sourceLayer.value)
        }
        if (filter != Filter.default) {
          setProperty(Filter.NAME, filter.value)
        }
      }
      update(layerId) {
        setConstructorProperty("id", Value(layerId))
      }
      update(skyAtmosphereColor) {
        setProperty(SkyAtmosphereColor.NAME, skyAtmosphereColor.value)
      }
      update(skyAtmosphereHaloColor) {
        setProperty(SkyAtmosphereHaloColor.NAME, skyAtmosphereHaloColor.value)
      }
      update(skyAtmosphereSun) {
        setProperty(SkyAtmosphereSun.NAME, skyAtmosphereSun.value)
      }
      update(skyAtmosphereSunIntensity) {
        setProperty(SkyAtmosphereSunIntensity.NAME, skyAtmosphereSunIntensity.value)
      }
      update(skyGradient) {
        setProperty(SkyGradient.NAME, skyGradient.value)
      }
      update(skyGradientCenter) {
        setProperty(SkyGradientCenter.NAME, skyGradientCenter.value)
      }
      update(skyGradientRadius) {
        setProperty(SkyGradientRadius.NAME, skyGradientRadius.value)
      }
      update(skyOpacity) {
        setProperty(SkyOpacity.NAME, skyOpacity.value)
      }
      update(skyOpacityTransition) {
        setProperty(SkyOpacity.TRANSITION_NAME, skyOpacityTransition.value)
      }
      update(skyType) {
        setProperty(SkyType.NAME, skyType.value)
      }
      update(visibility) {
        setProperty(Visibility.NAME, visibility.value)
      }
      update(minZoom) {
        setProperty(MinZoom.NAME, minZoom.value)
      }
      update(maxZoom) {
        setProperty(MaxZoom.NAME, maxZoom.value)
      }
      update(sourceLayer) {
        setProperty(SourceLayer.NAME, sourceLayer.value)
      }
      update(filter) {
        setProperty(Filter.NAME, filter.value)
      }
    }
  )
}
// End of generated file.