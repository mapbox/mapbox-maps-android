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
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.layers.internal.LayerNode
import com.mapbox.maps.extension.compose.style.sources.SourceState

/**
 * An extruded (3D) polygon.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#fill-extrusion)
 *
 * @param sourceState the source that drives this layer.
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param fillExtrusionEdgeRadius Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
 * @param fillExtrusionAmbientOcclusionGroundAttenuation Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother.
 * @param fillExtrusionAmbientOcclusionGroundRadius The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters.
 * @param fillExtrusionAmbientOcclusionIntensity Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
 * @param fillExtrusionAmbientOcclusionRadius Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead.
 * @param fillExtrusionAmbientOcclusionWallRadius Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
 * @param fillExtrusionBase The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
 * @param fillExtrusionColor The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
 * @param fillExtrusionCutoffFadeRange This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled.
 * @param fillExtrusionEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param fillExtrusionFloodLightColor The color of the flood light effect on the walls of the extruded buildings.
 * @param fillExtrusionFloodLightGroundAttenuation Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother.
 * @param fillExtrusionFloodLightGroundRadius The extent of the flood light effect on the ground beneath the extruded buildings in meters.
 * @param fillExtrusionFloodLightIntensity The intensity of the flood light color.
 * @param fillExtrusionFloodLightWallRadius The extent of the flood light effect on the walls of the extruded buildings in meters.
 * @param fillExtrusionHeight The height with which to extrude this layer.
 * @param fillExtrusionOpacity The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
 * @param fillExtrusionPattern Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 * @param fillExtrusionRoundedRoof Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
 * @param fillExtrusionTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
 * @param fillExtrusionTranslateAnchor Controls the frame of reference for `fill-extrusion-translate`.
 * @param fillExtrusionVerticalGradient Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
 * @param fillExtrusionVerticalScale A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun FillExtrusionLayer(
  sourceState: SourceState,
  layerId: String = remember {
    generateRandomLayerId("fill-extrusion")
  },
  fillExtrusionEdgeRadius: FillExtrusionEdgeRadius = FillExtrusionEdgeRadius.default,
  fillExtrusionAmbientOcclusionGroundAttenuation: FillExtrusionAmbientOcclusionGroundAttenuation = FillExtrusionAmbientOcclusionGroundAttenuation.default,
  fillExtrusionAmbientOcclusionGroundAttenuationTransition: Transition = Transition.default,
  fillExtrusionAmbientOcclusionGroundRadius: FillExtrusionAmbientOcclusionGroundRadius = FillExtrusionAmbientOcclusionGroundRadius.default,
  fillExtrusionAmbientOcclusionGroundRadiusTransition: Transition = Transition.default,
  fillExtrusionAmbientOcclusionIntensity: FillExtrusionAmbientOcclusionIntensity = FillExtrusionAmbientOcclusionIntensity.default,
  fillExtrusionAmbientOcclusionIntensityTransition: Transition = Transition.default,
  fillExtrusionAmbientOcclusionRadius: FillExtrusionAmbientOcclusionRadius = FillExtrusionAmbientOcclusionRadius.default,
  fillExtrusionAmbientOcclusionRadiusTransition: Transition = Transition.default,
  fillExtrusionAmbientOcclusionWallRadius: FillExtrusionAmbientOcclusionWallRadius = FillExtrusionAmbientOcclusionWallRadius.default,
  fillExtrusionAmbientOcclusionWallRadiusTransition: Transition = Transition.default,
  fillExtrusionBase: FillExtrusionBase = FillExtrusionBase.default,
  fillExtrusionBaseTransition: Transition = Transition.default,
  fillExtrusionColor: FillExtrusionColor = FillExtrusionColor.default,
  fillExtrusionColorTransition: Transition = Transition.default,
  fillExtrusionCutoffFadeRange: FillExtrusionCutoffFadeRange = FillExtrusionCutoffFadeRange.default,
  fillExtrusionEmissiveStrength: FillExtrusionEmissiveStrength = FillExtrusionEmissiveStrength.default,
  fillExtrusionEmissiveStrengthTransition: Transition = Transition.default,
  fillExtrusionFloodLightColor: FillExtrusionFloodLightColor = FillExtrusionFloodLightColor.default,
  fillExtrusionFloodLightColorTransition: Transition = Transition.default,
  fillExtrusionFloodLightGroundAttenuation: FillExtrusionFloodLightGroundAttenuation = FillExtrusionFloodLightGroundAttenuation.default,
  fillExtrusionFloodLightGroundAttenuationTransition: Transition = Transition.default,
  fillExtrusionFloodLightGroundRadius: FillExtrusionFloodLightGroundRadius = FillExtrusionFloodLightGroundRadius.default,
  fillExtrusionFloodLightGroundRadiusTransition: Transition = Transition.default,
  fillExtrusionFloodLightIntensity: FillExtrusionFloodLightIntensity = FillExtrusionFloodLightIntensity.default,
  fillExtrusionFloodLightIntensityTransition: Transition = Transition.default,
  fillExtrusionFloodLightWallRadius: FillExtrusionFloodLightWallRadius = FillExtrusionFloodLightWallRadius.default,
  fillExtrusionFloodLightWallRadiusTransition: Transition = Transition.default,
  fillExtrusionHeight: FillExtrusionHeight = FillExtrusionHeight.default,
  fillExtrusionHeightTransition: Transition = Transition.default,
  fillExtrusionOpacity: FillExtrusionOpacity = FillExtrusionOpacity.default,
  fillExtrusionOpacityTransition: Transition = Transition.default,
  fillExtrusionPattern: FillExtrusionPattern = FillExtrusionPattern.default,
  fillExtrusionRoundedRoof: FillExtrusionRoundedRoof = FillExtrusionRoundedRoof.default,
  fillExtrusionTranslate: FillExtrusionTranslate = FillExtrusionTranslate.default,
  fillExtrusionTranslateTransition: Transition = Transition.default,
  fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor.default,
  fillExtrusionVerticalGradient: FillExtrusionVerticalGradient = FillExtrusionVerticalGradient.default,
  fillExtrusionVerticalScale: FillExtrusionVerticalScale = FillExtrusionVerticalScale.default,
  fillExtrusionVerticalScaleTransition: Transition = Transition.default,
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
        layerType = "fill-extrusion",
        sourceState = sourceState,
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (fillExtrusionEdgeRadius != FillExtrusionEdgeRadius.default) {
          setProperty(FillExtrusionEdgeRadius.NAME, fillExtrusionEdgeRadius.value)
        }
        if (fillExtrusionAmbientOcclusionGroundAttenuation != FillExtrusionAmbientOcclusionGroundAttenuation.default) {
          setProperty(FillExtrusionAmbientOcclusionGroundAttenuation.NAME, fillExtrusionAmbientOcclusionGroundAttenuation.value)
        }
        if (fillExtrusionAmbientOcclusionGroundAttenuationTransition != Transition.default) {
          setProperty(FillExtrusionAmbientOcclusionGroundAttenuation.TRANSITION_NAME, fillExtrusionAmbientOcclusionGroundAttenuationTransition.value)
        }
        if (fillExtrusionAmbientOcclusionGroundRadius != FillExtrusionAmbientOcclusionGroundRadius.default) {
          setProperty(FillExtrusionAmbientOcclusionGroundRadius.NAME, fillExtrusionAmbientOcclusionGroundRadius.value)
        }
        if (fillExtrusionAmbientOcclusionGroundRadiusTransition != Transition.default) {
          setProperty(FillExtrusionAmbientOcclusionGroundRadius.TRANSITION_NAME, fillExtrusionAmbientOcclusionGroundRadiusTransition.value)
        }
        if (fillExtrusionAmbientOcclusionIntensity != FillExtrusionAmbientOcclusionIntensity.default) {
          setProperty(FillExtrusionAmbientOcclusionIntensity.NAME, fillExtrusionAmbientOcclusionIntensity.value)
        }
        if (fillExtrusionAmbientOcclusionIntensityTransition != Transition.default) {
          setProperty(FillExtrusionAmbientOcclusionIntensity.TRANSITION_NAME, fillExtrusionAmbientOcclusionIntensityTransition.value)
        }
        if (fillExtrusionAmbientOcclusionRadius != FillExtrusionAmbientOcclusionRadius.default) {
          setProperty(FillExtrusionAmbientOcclusionRadius.NAME, fillExtrusionAmbientOcclusionRadius.value)
        }
        if (fillExtrusionAmbientOcclusionRadiusTransition != Transition.default) {
          setProperty(FillExtrusionAmbientOcclusionRadius.TRANSITION_NAME, fillExtrusionAmbientOcclusionRadiusTransition.value)
        }
        if (fillExtrusionAmbientOcclusionWallRadius != FillExtrusionAmbientOcclusionWallRadius.default) {
          setProperty(FillExtrusionAmbientOcclusionWallRadius.NAME, fillExtrusionAmbientOcclusionWallRadius.value)
        }
        if (fillExtrusionAmbientOcclusionWallRadiusTransition != Transition.default) {
          setProperty(FillExtrusionAmbientOcclusionWallRadius.TRANSITION_NAME, fillExtrusionAmbientOcclusionWallRadiusTransition.value)
        }
        if (fillExtrusionBase != FillExtrusionBase.default) {
          setProperty(FillExtrusionBase.NAME, fillExtrusionBase.value)
        }
        if (fillExtrusionBaseTransition != Transition.default) {
          setProperty(FillExtrusionBase.TRANSITION_NAME, fillExtrusionBaseTransition.value)
        }
        if (fillExtrusionColor != FillExtrusionColor.default) {
          setProperty(FillExtrusionColor.NAME, fillExtrusionColor.value)
        }
        if (fillExtrusionColorTransition != Transition.default) {
          setProperty(FillExtrusionColor.TRANSITION_NAME, fillExtrusionColorTransition.value)
        }
        if (fillExtrusionCutoffFadeRange != FillExtrusionCutoffFadeRange.default) {
          setProperty(FillExtrusionCutoffFadeRange.NAME, fillExtrusionCutoffFadeRange.value)
        }
        if (fillExtrusionEmissiveStrength != FillExtrusionEmissiveStrength.default) {
          setProperty(FillExtrusionEmissiveStrength.NAME, fillExtrusionEmissiveStrength.value)
        }
        if (fillExtrusionEmissiveStrengthTransition != Transition.default) {
          setProperty(FillExtrusionEmissiveStrength.TRANSITION_NAME, fillExtrusionEmissiveStrengthTransition.value)
        }
        if (fillExtrusionFloodLightColor != FillExtrusionFloodLightColor.default) {
          setProperty(FillExtrusionFloodLightColor.NAME, fillExtrusionFloodLightColor.value)
        }
        if (fillExtrusionFloodLightColorTransition != Transition.default) {
          setProperty(FillExtrusionFloodLightColor.TRANSITION_NAME, fillExtrusionFloodLightColorTransition.value)
        }
        if (fillExtrusionFloodLightGroundAttenuation != FillExtrusionFloodLightGroundAttenuation.default) {
          setProperty(FillExtrusionFloodLightGroundAttenuation.NAME, fillExtrusionFloodLightGroundAttenuation.value)
        }
        if (fillExtrusionFloodLightGroundAttenuationTransition != Transition.default) {
          setProperty(FillExtrusionFloodLightGroundAttenuation.TRANSITION_NAME, fillExtrusionFloodLightGroundAttenuationTransition.value)
        }
        if (fillExtrusionFloodLightGroundRadius != FillExtrusionFloodLightGroundRadius.default) {
          setProperty(FillExtrusionFloodLightGroundRadius.NAME, fillExtrusionFloodLightGroundRadius.value)
        }
        if (fillExtrusionFloodLightGroundRadiusTransition != Transition.default) {
          setProperty(FillExtrusionFloodLightGroundRadius.TRANSITION_NAME, fillExtrusionFloodLightGroundRadiusTransition.value)
        }
        if (fillExtrusionFloodLightIntensity != FillExtrusionFloodLightIntensity.default) {
          setProperty(FillExtrusionFloodLightIntensity.NAME, fillExtrusionFloodLightIntensity.value)
        }
        if (fillExtrusionFloodLightIntensityTransition != Transition.default) {
          setProperty(FillExtrusionFloodLightIntensity.TRANSITION_NAME, fillExtrusionFloodLightIntensityTransition.value)
        }
        if (fillExtrusionFloodLightWallRadius != FillExtrusionFloodLightWallRadius.default) {
          setProperty(FillExtrusionFloodLightWallRadius.NAME, fillExtrusionFloodLightWallRadius.value)
        }
        if (fillExtrusionFloodLightWallRadiusTransition != Transition.default) {
          setProperty(FillExtrusionFloodLightWallRadius.TRANSITION_NAME, fillExtrusionFloodLightWallRadiusTransition.value)
        }
        if (fillExtrusionHeight != FillExtrusionHeight.default) {
          setProperty(FillExtrusionHeight.NAME, fillExtrusionHeight.value)
        }
        if (fillExtrusionHeightTransition != Transition.default) {
          setProperty(FillExtrusionHeight.TRANSITION_NAME, fillExtrusionHeightTransition.value)
        }
        if (fillExtrusionOpacity != FillExtrusionOpacity.default) {
          setProperty(FillExtrusionOpacity.NAME, fillExtrusionOpacity.value)
        }
        if (fillExtrusionOpacityTransition != Transition.default) {
          setProperty(FillExtrusionOpacity.TRANSITION_NAME, fillExtrusionOpacityTransition.value)
        }
        if (fillExtrusionPattern != FillExtrusionPattern.default) {
          setProperty(FillExtrusionPattern.NAME, fillExtrusionPattern.value)
        }
        if (fillExtrusionRoundedRoof != FillExtrusionRoundedRoof.default) {
          setProperty(FillExtrusionRoundedRoof.NAME, fillExtrusionRoundedRoof.value)
        }
        if (fillExtrusionTranslate != FillExtrusionTranslate.default) {
          setProperty(FillExtrusionTranslate.NAME, fillExtrusionTranslate.value)
        }
        if (fillExtrusionTranslateTransition != Transition.default) {
          setProperty(FillExtrusionTranslate.TRANSITION_NAME, fillExtrusionTranslateTransition.value)
        }
        if (fillExtrusionTranslateAnchor != FillExtrusionTranslateAnchor.default) {
          setProperty(FillExtrusionTranslateAnchor.NAME, fillExtrusionTranslateAnchor.value)
        }
        if (fillExtrusionVerticalGradient != FillExtrusionVerticalGradient.default) {
          setProperty(FillExtrusionVerticalGradient.NAME, fillExtrusionVerticalGradient.value)
        }
        if (fillExtrusionVerticalScale != FillExtrusionVerticalScale.default) {
          setProperty(FillExtrusionVerticalScale.NAME, fillExtrusionVerticalScale.value)
        }
        if (fillExtrusionVerticalScaleTransition != Transition.default) {
          setProperty(FillExtrusionVerticalScale.TRANSITION_NAME, fillExtrusionVerticalScaleTransition.value)
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
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(fillExtrusionEdgeRadius) {
        setProperty(FillExtrusionEdgeRadius.NAME, fillExtrusionEdgeRadius.value)
      }
      update(fillExtrusionAmbientOcclusionGroundAttenuation) {
        setProperty(FillExtrusionAmbientOcclusionGroundAttenuation.NAME, fillExtrusionAmbientOcclusionGroundAttenuation.value)
      }
      update(fillExtrusionAmbientOcclusionGroundAttenuationTransition) {
        setProperty(FillExtrusionAmbientOcclusionGroundAttenuation.TRANSITION_NAME, fillExtrusionAmbientOcclusionGroundAttenuationTransition.value)
      }
      update(fillExtrusionAmbientOcclusionGroundRadius) {
        setProperty(FillExtrusionAmbientOcclusionGroundRadius.NAME, fillExtrusionAmbientOcclusionGroundRadius.value)
      }
      update(fillExtrusionAmbientOcclusionGroundRadiusTransition) {
        setProperty(FillExtrusionAmbientOcclusionGroundRadius.TRANSITION_NAME, fillExtrusionAmbientOcclusionGroundRadiusTransition.value)
      }
      update(fillExtrusionAmbientOcclusionIntensity) {
        setProperty(FillExtrusionAmbientOcclusionIntensity.NAME, fillExtrusionAmbientOcclusionIntensity.value)
      }
      update(fillExtrusionAmbientOcclusionIntensityTransition) {
        setProperty(FillExtrusionAmbientOcclusionIntensity.TRANSITION_NAME, fillExtrusionAmbientOcclusionIntensityTransition.value)
      }
      update(fillExtrusionAmbientOcclusionRadius) {
        setProperty(FillExtrusionAmbientOcclusionRadius.NAME, fillExtrusionAmbientOcclusionRadius.value)
      }
      update(fillExtrusionAmbientOcclusionRadiusTransition) {
        setProperty(FillExtrusionAmbientOcclusionRadius.TRANSITION_NAME, fillExtrusionAmbientOcclusionRadiusTransition.value)
      }
      update(fillExtrusionAmbientOcclusionWallRadius) {
        setProperty(FillExtrusionAmbientOcclusionWallRadius.NAME, fillExtrusionAmbientOcclusionWallRadius.value)
      }
      update(fillExtrusionAmbientOcclusionWallRadiusTransition) {
        setProperty(FillExtrusionAmbientOcclusionWallRadius.TRANSITION_NAME, fillExtrusionAmbientOcclusionWallRadiusTransition.value)
      }
      update(fillExtrusionBase) {
        setProperty(FillExtrusionBase.NAME, fillExtrusionBase.value)
      }
      update(fillExtrusionBaseTransition) {
        setProperty(FillExtrusionBase.TRANSITION_NAME, fillExtrusionBaseTransition.value)
      }
      update(fillExtrusionColor) {
        setProperty(FillExtrusionColor.NAME, fillExtrusionColor.value)
      }
      update(fillExtrusionColorTransition) {
        setProperty(FillExtrusionColor.TRANSITION_NAME, fillExtrusionColorTransition.value)
      }
      update(fillExtrusionCutoffFadeRange) {
        setProperty(FillExtrusionCutoffFadeRange.NAME, fillExtrusionCutoffFadeRange.value)
      }
      update(fillExtrusionEmissiveStrength) {
        setProperty(FillExtrusionEmissiveStrength.NAME, fillExtrusionEmissiveStrength.value)
      }
      update(fillExtrusionEmissiveStrengthTransition) {
        setProperty(FillExtrusionEmissiveStrength.TRANSITION_NAME, fillExtrusionEmissiveStrengthTransition.value)
      }
      update(fillExtrusionFloodLightColor) {
        setProperty(FillExtrusionFloodLightColor.NAME, fillExtrusionFloodLightColor.value)
      }
      update(fillExtrusionFloodLightColorTransition) {
        setProperty(FillExtrusionFloodLightColor.TRANSITION_NAME, fillExtrusionFloodLightColorTransition.value)
      }
      update(fillExtrusionFloodLightGroundAttenuation) {
        setProperty(FillExtrusionFloodLightGroundAttenuation.NAME, fillExtrusionFloodLightGroundAttenuation.value)
      }
      update(fillExtrusionFloodLightGroundAttenuationTransition) {
        setProperty(FillExtrusionFloodLightGroundAttenuation.TRANSITION_NAME, fillExtrusionFloodLightGroundAttenuationTransition.value)
      }
      update(fillExtrusionFloodLightGroundRadius) {
        setProperty(FillExtrusionFloodLightGroundRadius.NAME, fillExtrusionFloodLightGroundRadius.value)
      }
      update(fillExtrusionFloodLightGroundRadiusTransition) {
        setProperty(FillExtrusionFloodLightGroundRadius.TRANSITION_NAME, fillExtrusionFloodLightGroundRadiusTransition.value)
      }
      update(fillExtrusionFloodLightIntensity) {
        setProperty(FillExtrusionFloodLightIntensity.NAME, fillExtrusionFloodLightIntensity.value)
      }
      update(fillExtrusionFloodLightIntensityTransition) {
        setProperty(FillExtrusionFloodLightIntensity.TRANSITION_NAME, fillExtrusionFloodLightIntensityTransition.value)
      }
      update(fillExtrusionFloodLightWallRadius) {
        setProperty(FillExtrusionFloodLightWallRadius.NAME, fillExtrusionFloodLightWallRadius.value)
      }
      update(fillExtrusionFloodLightWallRadiusTransition) {
        setProperty(FillExtrusionFloodLightWallRadius.TRANSITION_NAME, fillExtrusionFloodLightWallRadiusTransition.value)
      }
      update(fillExtrusionHeight) {
        setProperty(FillExtrusionHeight.NAME, fillExtrusionHeight.value)
      }
      update(fillExtrusionHeightTransition) {
        setProperty(FillExtrusionHeight.TRANSITION_NAME, fillExtrusionHeightTransition.value)
      }
      update(fillExtrusionOpacity) {
        setProperty(FillExtrusionOpacity.NAME, fillExtrusionOpacity.value)
      }
      update(fillExtrusionOpacityTransition) {
        setProperty(FillExtrusionOpacity.TRANSITION_NAME, fillExtrusionOpacityTransition.value)
      }
      update(fillExtrusionPattern) {
        setProperty(FillExtrusionPattern.NAME, fillExtrusionPattern.value)
      }
      update(fillExtrusionRoundedRoof) {
        setProperty(FillExtrusionRoundedRoof.NAME, fillExtrusionRoundedRoof.value)
      }
      update(fillExtrusionTranslate) {
        setProperty(FillExtrusionTranslate.NAME, fillExtrusionTranslate.value)
      }
      update(fillExtrusionTranslateTransition) {
        setProperty(FillExtrusionTranslate.TRANSITION_NAME, fillExtrusionTranslateTransition.value)
      }
      update(fillExtrusionTranslateAnchor) {
        setProperty(FillExtrusionTranslateAnchor.NAME, fillExtrusionTranslateAnchor.value)
      }
      update(fillExtrusionVerticalGradient) {
        setProperty(FillExtrusionVerticalGradient.NAME, fillExtrusionVerticalGradient.value)
      }
      update(fillExtrusionVerticalScale) {
        setProperty(FillExtrusionVerticalScale.NAME, fillExtrusionVerticalScale.value)
      }
      update(fillExtrusionVerticalScaleTransition) {
        setProperty(FillExtrusionVerticalScale.TRANSITION_NAME, fillExtrusionVerticalScaleTransition.value)
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