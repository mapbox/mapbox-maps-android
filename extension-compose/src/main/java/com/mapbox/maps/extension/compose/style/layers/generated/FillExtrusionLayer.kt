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
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleListValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.IdGenerator.generateRandomLayerId
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layers.Filter
import com.mapbox.maps.extension.compose.style.layers.ImageValue
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
 * @param fillExtrusionAmbientOcclusionGroundAttenuationTransition Defines the transition of [fillExtrusionAmbientOcclusionGroundAttenuation].
 * @param fillExtrusionAmbientOcclusionGroundRadius The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters.
 * @param fillExtrusionAmbientOcclusionGroundRadiusTransition Defines the transition of [fillExtrusionAmbientOcclusionGroundRadius].
 * @param fillExtrusionAmbientOcclusionIntensity Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
 * @param fillExtrusionAmbientOcclusionIntensityTransition Defines the transition of [fillExtrusionAmbientOcclusionIntensity].
 * @param fillExtrusionAmbientOcclusionRadius Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead.
 * @param fillExtrusionAmbientOcclusionRadiusTransition Defines the transition of [fillExtrusionAmbientOcclusionRadius].
 * @param fillExtrusionAmbientOcclusionWallRadius Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
 * @param fillExtrusionAmbientOcclusionWallRadiusTransition Defines the transition of [fillExtrusionAmbientOcclusionWallRadius].
 * @param fillExtrusionBase The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
 * @param fillExtrusionBaseTransition Defines the transition of [fillExtrusionBase].
 * @param fillExtrusionColor The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
 * @param fillExtrusionColorTransition Defines the transition of [fillExtrusionColor].
 * @param fillExtrusionCutoffFadeRange This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled.
 * @param fillExtrusionEmissiveStrength Controls the intensity of light emitted on the source features.
 * @param fillExtrusionEmissiveStrengthTransition Defines the transition of [fillExtrusionEmissiveStrength].
 * @param fillExtrusionFloodLightColor The color of the flood light effect on the walls of the extruded buildings.
 * @param fillExtrusionFloodLightColorTransition Defines the transition of [fillExtrusionFloodLightColor].
 * @param fillExtrusionFloodLightGroundAttenuation Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother.
 * @param fillExtrusionFloodLightGroundAttenuationTransition Defines the transition of [fillExtrusionFloodLightGroundAttenuation].
 * @param fillExtrusionFloodLightGroundRadius The extent of the flood light effect on the ground beneath the extruded buildings in meters.
 * @param fillExtrusionFloodLightGroundRadiusTransition Defines the transition of [fillExtrusionFloodLightGroundRadius].
 * @param fillExtrusionFloodLightIntensity The intensity of the flood light color.
 * @param fillExtrusionFloodLightIntensityTransition Defines the transition of [fillExtrusionFloodLightIntensity].
 * @param fillExtrusionFloodLightWallRadius The extent of the flood light effect on the walls of the extruded buildings in meters.
 * @param fillExtrusionFloodLightWallRadiusTransition Defines the transition of [fillExtrusionFloodLightWallRadius].
 * @param fillExtrusionHeight The height with which to extrude this layer.
 * @param fillExtrusionHeightTransition Defines the transition of [fillExtrusionHeight].
 * @param fillExtrusionOpacity The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
 * @param fillExtrusionOpacityTransition Defines the transition of [fillExtrusionOpacity].
 * @param fillExtrusionPattern Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 * @param fillExtrusionRoundedRoof Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
 * @param fillExtrusionTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
 * @param fillExtrusionTranslateTransition Defines the transition of [fillExtrusionTranslate].
 * @param fillExtrusionTranslateAnchor Controls the frame of reference for `fill-extrusion-translate`.
 * @param fillExtrusionVerticalGradient Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
 * @param fillExtrusionVerticalScale A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions.
 * @param fillExtrusionVerticalScaleTransition Defines the transition of [fillExtrusionVerticalScale].
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
  fillExtrusionEdgeRadius: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionAmbientOcclusionGroundAttenuation: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionAmbientOcclusionGroundAttenuationTransition: Transition = Transition.INITIAL,
  fillExtrusionAmbientOcclusionGroundRadius: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionAmbientOcclusionGroundRadiusTransition: Transition = Transition.INITIAL,
  fillExtrusionAmbientOcclusionIntensity: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionAmbientOcclusionIntensityTransition: Transition = Transition.INITIAL,
  fillExtrusionAmbientOcclusionRadius: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionAmbientOcclusionRadiusTransition: Transition = Transition.INITIAL,
  fillExtrusionAmbientOcclusionWallRadius: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionAmbientOcclusionWallRadiusTransition: Transition = Transition.INITIAL,
  fillExtrusionBase: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionBaseTransition: Transition = Transition.INITIAL,
  fillExtrusionColor: ColorValue = ColorValue.INITIAL,
  fillExtrusionColorTransition: Transition = Transition.INITIAL,
  fillExtrusionCutoffFadeRange: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionEmissiveStrength: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionEmissiveStrengthTransition: Transition = Transition.INITIAL,
  fillExtrusionFloodLightColor: ColorValue = ColorValue.INITIAL,
  fillExtrusionFloodLightColorTransition: Transition = Transition.INITIAL,
  fillExtrusionFloodLightGroundAttenuation: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionFloodLightGroundAttenuationTransition: Transition = Transition.INITIAL,
  fillExtrusionFloodLightGroundRadius: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionFloodLightGroundRadiusTransition: Transition = Transition.INITIAL,
  fillExtrusionFloodLightIntensity: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionFloodLightIntensityTransition: Transition = Transition.INITIAL,
  fillExtrusionFloodLightWallRadius: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionFloodLightWallRadiusTransition: Transition = Transition.INITIAL,
  fillExtrusionHeight: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionHeightTransition: Transition = Transition.INITIAL,
  fillExtrusionOpacity: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionOpacityTransition: Transition = Transition.INITIAL,
  fillExtrusionPattern: ImageValue = ImageValue.INITIAL,
  fillExtrusionRoundedRoof: BooleanValue = BooleanValue.INITIAL,
  fillExtrusionTranslate: DoubleListValue = DoubleListValue.INITIAL,
  fillExtrusionTranslateTransition: Transition = Transition.INITIAL,
  fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchorValue = FillExtrusionTranslateAnchorValue.INITIAL,
  fillExtrusionVerticalGradient: BooleanValue = BooleanValue.INITIAL,
  fillExtrusionVerticalScale: DoubleValue = DoubleValue.INITIAL,
  fillExtrusionVerticalScaleTransition: Transition = Transition.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of FillExtrusionLayer inside unsupported composable function")

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
        if (fillExtrusionEdgeRadius.notInitial) {
          setProperty("fill-extrusion-edge-radius", fillExtrusionEdgeRadius.value)
        }
        if (fillExtrusionAmbientOcclusionGroundAttenuation.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-ground-attenuation", fillExtrusionAmbientOcclusionGroundAttenuation.value)
        }
        if (fillExtrusionAmbientOcclusionGroundAttenuationTransition.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-ground-attenuation-transition", fillExtrusionAmbientOcclusionGroundAttenuationTransition.value)
        }
        if (fillExtrusionAmbientOcclusionGroundRadius.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-ground-radius", fillExtrusionAmbientOcclusionGroundRadius.value)
        }
        if (fillExtrusionAmbientOcclusionGroundRadiusTransition.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-ground-radius-transition", fillExtrusionAmbientOcclusionGroundRadiusTransition.value)
        }
        if (fillExtrusionAmbientOcclusionIntensity.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-intensity", fillExtrusionAmbientOcclusionIntensity.value)
        }
        if (fillExtrusionAmbientOcclusionIntensityTransition.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-intensity-transition", fillExtrusionAmbientOcclusionIntensityTransition.value)
        }
        if (fillExtrusionAmbientOcclusionRadius.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-radius", fillExtrusionAmbientOcclusionRadius.value)
        }
        if (fillExtrusionAmbientOcclusionRadiusTransition.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-radius-transition", fillExtrusionAmbientOcclusionRadiusTransition.value)
        }
        if (fillExtrusionAmbientOcclusionWallRadius.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-wall-radius", fillExtrusionAmbientOcclusionWallRadius.value)
        }
        if (fillExtrusionAmbientOcclusionWallRadiusTransition.notInitial) {
          setProperty("fill-extrusion-ambient-occlusion-wall-radius-transition", fillExtrusionAmbientOcclusionWallRadiusTransition.value)
        }
        if (fillExtrusionBase.notInitial) {
          setProperty("fill-extrusion-base", fillExtrusionBase.value)
        }
        if (fillExtrusionBaseTransition.notInitial) {
          setProperty("fill-extrusion-base-transition", fillExtrusionBaseTransition.value)
        }
        if (fillExtrusionColor.notInitial) {
          setProperty("fill-extrusion-color", fillExtrusionColor.value)
        }
        if (fillExtrusionColorTransition.notInitial) {
          setProperty("fill-extrusion-color-transition", fillExtrusionColorTransition.value)
        }
        if (fillExtrusionCutoffFadeRange.notInitial) {
          setProperty("fill-extrusion-cutoff-fade-range", fillExtrusionCutoffFadeRange.value)
        }
        if (fillExtrusionEmissiveStrength.notInitial) {
          setProperty("fill-extrusion-emissive-strength", fillExtrusionEmissiveStrength.value)
        }
        if (fillExtrusionEmissiveStrengthTransition.notInitial) {
          setProperty("fill-extrusion-emissive-strength-transition", fillExtrusionEmissiveStrengthTransition.value)
        }
        if (fillExtrusionFloodLightColor.notInitial) {
          setProperty("fill-extrusion-flood-light-color", fillExtrusionFloodLightColor.value)
        }
        if (fillExtrusionFloodLightColorTransition.notInitial) {
          setProperty("fill-extrusion-flood-light-color-transition", fillExtrusionFloodLightColorTransition.value)
        }
        if (fillExtrusionFloodLightGroundAttenuation.notInitial) {
          setProperty("fill-extrusion-flood-light-ground-attenuation", fillExtrusionFloodLightGroundAttenuation.value)
        }
        if (fillExtrusionFloodLightGroundAttenuationTransition.notInitial) {
          setProperty("fill-extrusion-flood-light-ground-attenuation-transition", fillExtrusionFloodLightGroundAttenuationTransition.value)
        }
        if (fillExtrusionFloodLightGroundRadius.notInitial) {
          setProperty("fill-extrusion-flood-light-ground-radius", fillExtrusionFloodLightGroundRadius.value)
        }
        if (fillExtrusionFloodLightGroundRadiusTransition.notInitial) {
          setProperty("fill-extrusion-flood-light-ground-radius-transition", fillExtrusionFloodLightGroundRadiusTransition.value)
        }
        if (fillExtrusionFloodLightIntensity.notInitial) {
          setProperty("fill-extrusion-flood-light-intensity", fillExtrusionFloodLightIntensity.value)
        }
        if (fillExtrusionFloodLightIntensityTransition.notInitial) {
          setProperty("fill-extrusion-flood-light-intensity-transition", fillExtrusionFloodLightIntensityTransition.value)
        }
        if (fillExtrusionFloodLightWallRadius.notInitial) {
          setProperty("fill-extrusion-flood-light-wall-radius", fillExtrusionFloodLightWallRadius.value)
        }
        if (fillExtrusionFloodLightWallRadiusTransition.notInitial) {
          setProperty("fill-extrusion-flood-light-wall-radius-transition", fillExtrusionFloodLightWallRadiusTransition.value)
        }
        if (fillExtrusionHeight.notInitial) {
          setProperty("fill-extrusion-height", fillExtrusionHeight.value)
        }
        if (fillExtrusionHeightTransition.notInitial) {
          setProperty("fill-extrusion-height-transition", fillExtrusionHeightTransition.value)
        }
        if (fillExtrusionOpacity.notInitial) {
          setProperty("fill-extrusion-opacity", fillExtrusionOpacity.value)
        }
        if (fillExtrusionOpacityTransition.notInitial) {
          setProperty("fill-extrusion-opacity-transition", fillExtrusionOpacityTransition.value)
        }
        if (fillExtrusionPattern.notInitial) {
          fillExtrusionPattern.styleImage?.let {
            addImage(it)
          }
          setProperty("fill-extrusion-pattern", fillExtrusionPattern.value)
        }
        if (fillExtrusionRoundedRoof.notInitial) {
          setProperty("fill-extrusion-rounded-roof", fillExtrusionRoundedRoof.value)
        }
        if (fillExtrusionTranslate.notInitial) {
          setProperty("fill-extrusion-translate", fillExtrusionTranslate.value)
        }
        if (fillExtrusionTranslateTransition.notInitial) {
          setProperty("fill-extrusion-translate-transition", fillExtrusionTranslateTransition.value)
        }
        if (fillExtrusionTranslateAnchor.notInitial) {
          setProperty("fill-extrusion-translate-anchor", fillExtrusionTranslateAnchor.value)
        }
        if (fillExtrusionVerticalGradient.notInitial) {
          setProperty("fill-extrusion-vertical-gradient", fillExtrusionVerticalGradient.value)
        }
        if (fillExtrusionVerticalScale.notInitial) {
          setProperty("fill-extrusion-vertical-scale", fillExtrusionVerticalScale.value)
        }
        if (fillExtrusionVerticalScaleTransition.notInitial) {
          setProperty("fill-extrusion-vertical-scale-transition", fillExtrusionVerticalScaleTransition.value)
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
      update(sourceState) {
        updateSource(sourceState)
      }
      update(layerId) {
        updateLayerId(layerId)
      }
      update(fillExtrusionEdgeRadius) {
        setProperty("fill-extrusion-edge-radius", fillExtrusionEdgeRadius.value)
      }
      update(fillExtrusionAmbientOcclusionGroundAttenuation) {
        setProperty("fill-extrusion-ambient-occlusion-ground-attenuation", fillExtrusionAmbientOcclusionGroundAttenuation.value)
      }
      update(fillExtrusionAmbientOcclusionGroundAttenuationTransition) {
        setProperty("fill-extrusion-ambient-occlusion-ground-attenuation-transition", fillExtrusionAmbientOcclusionGroundAttenuationTransition.value)
      }
      update(fillExtrusionAmbientOcclusionGroundRadius) {
        setProperty("fill-extrusion-ambient-occlusion-ground-radius", fillExtrusionAmbientOcclusionGroundRadius.value)
      }
      update(fillExtrusionAmbientOcclusionGroundRadiusTransition) {
        setProperty("fill-extrusion-ambient-occlusion-ground-radius-transition", fillExtrusionAmbientOcclusionGroundRadiusTransition.value)
      }
      update(fillExtrusionAmbientOcclusionIntensity) {
        setProperty("fill-extrusion-ambient-occlusion-intensity", fillExtrusionAmbientOcclusionIntensity.value)
      }
      update(fillExtrusionAmbientOcclusionIntensityTransition) {
        setProperty("fill-extrusion-ambient-occlusion-intensity-transition", fillExtrusionAmbientOcclusionIntensityTransition.value)
      }
      update(fillExtrusionAmbientOcclusionRadius) {
        setProperty("fill-extrusion-ambient-occlusion-radius", fillExtrusionAmbientOcclusionRadius.value)
      }
      update(fillExtrusionAmbientOcclusionRadiusTransition) {
        setProperty("fill-extrusion-ambient-occlusion-radius-transition", fillExtrusionAmbientOcclusionRadiusTransition.value)
      }
      update(fillExtrusionAmbientOcclusionWallRadius) {
        setProperty("fill-extrusion-ambient-occlusion-wall-radius", fillExtrusionAmbientOcclusionWallRadius.value)
      }
      update(fillExtrusionAmbientOcclusionWallRadiusTransition) {
        setProperty("fill-extrusion-ambient-occlusion-wall-radius-transition", fillExtrusionAmbientOcclusionWallRadiusTransition.value)
      }
      update(fillExtrusionBase) {
        setProperty("fill-extrusion-base", fillExtrusionBase.value)
      }
      update(fillExtrusionBaseTransition) {
        setProperty("fill-extrusion-base-transition", fillExtrusionBaseTransition.value)
      }
      update(fillExtrusionColor) {
        setProperty("fill-extrusion-color", fillExtrusionColor.value)
      }
      update(fillExtrusionColorTransition) {
        setProperty("fill-extrusion-color-transition", fillExtrusionColorTransition.value)
      }
      update(fillExtrusionCutoffFadeRange) {
        setProperty("fill-extrusion-cutoff-fade-range", fillExtrusionCutoffFadeRange.value)
      }
      update(fillExtrusionEmissiveStrength) {
        setProperty("fill-extrusion-emissive-strength", fillExtrusionEmissiveStrength.value)
      }
      update(fillExtrusionEmissiveStrengthTransition) {
        setProperty("fill-extrusion-emissive-strength-transition", fillExtrusionEmissiveStrengthTransition.value)
      }
      update(fillExtrusionFloodLightColor) {
        setProperty("fill-extrusion-flood-light-color", fillExtrusionFloodLightColor.value)
      }
      update(fillExtrusionFloodLightColorTransition) {
        setProperty("fill-extrusion-flood-light-color-transition", fillExtrusionFloodLightColorTransition.value)
      }
      update(fillExtrusionFloodLightGroundAttenuation) {
        setProperty("fill-extrusion-flood-light-ground-attenuation", fillExtrusionFloodLightGroundAttenuation.value)
      }
      update(fillExtrusionFloodLightGroundAttenuationTransition) {
        setProperty("fill-extrusion-flood-light-ground-attenuation-transition", fillExtrusionFloodLightGroundAttenuationTransition.value)
      }
      update(fillExtrusionFloodLightGroundRadius) {
        setProperty("fill-extrusion-flood-light-ground-radius", fillExtrusionFloodLightGroundRadius.value)
      }
      update(fillExtrusionFloodLightGroundRadiusTransition) {
        setProperty("fill-extrusion-flood-light-ground-radius-transition", fillExtrusionFloodLightGroundRadiusTransition.value)
      }
      update(fillExtrusionFloodLightIntensity) {
        setProperty("fill-extrusion-flood-light-intensity", fillExtrusionFloodLightIntensity.value)
      }
      update(fillExtrusionFloodLightIntensityTransition) {
        setProperty("fill-extrusion-flood-light-intensity-transition", fillExtrusionFloodLightIntensityTransition.value)
      }
      update(fillExtrusionFloodLightWallRadius) {
        setProperty("fill-extrusion-flood-light-wall-radius", fillExtrusionFloodLightWallRadius.value)
      }
      update(fillExtrusionFloodLightWallRadiusTransition) {
        setProperty("fill-extrusion-flood-light-wall-radius-transition", fillExtrusionFloodLightWallRadiusTransition.value)
      }
      update(fillExtrusionHeight) {
        setProperty("fill-extrusion-height", fillExtrusionHeight.value)
      }
      update(fillExtrusionHeightTransition) {
        setProperty("fill-extrusion-height-transition", fillExtrusionHeightTransition.value)
      }
      update(fillExtrusionOpacity) {
        setProperty("fill-extrusion-opacity", fillExtrusionOpacity.value)
      }
      update(fillExtrusionOpacityTransition) {
        setProperty("fill-extrusion-opacity-transition", fillExtrusionOpacityTransition.value)
      }
      update(fillExtrusionPattern) {
        fillExtrusionPattern.styleImage?.let {
          addImage(it)
        }
        setProperty("fill-extrusion-pattern", fillExtrusionPattern.value)
      }
      update(fillExtrusionRoundedRoof) {
        setProperty("fill-extrusion-rounded-roof", fillExtrusionRoundedRoof.value)
      }
      update(fillExtrusionTranslate) {
        setProperty("fill-extrusion-translate", fillExtrusionTranslate.value)
      }
      update(fillExtrusionTranslateTransition) {
        setProperty("fill-extrusion-translate-transition", fillExtrusionTranslateTransition.value)
      }
      update(fillExtrusionTranslateAnchor) {
        setProperty("fill-extrusion-translate-anchor", fillExtrusionTranslateAnchor.value)
      }
      update(fillExtrusionVerticalGradient) {
        setProperty("fill-extrusion-vertical-gradient", fillExtrusionVerticalGradient.value)
      }
      update(fillExtrusionVerticalScale) {
        setProperty("fill-extrusion-vertical-scale", fillExtrusionVerticalScale.value)
      }
      update(fillExtrusionVerticalScaleTransition) {
        setProperty("fill-extrusion-vertical-scale-transition", fillExtrusionVerticalScaleTransition.value)
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
  sourceState.UpdateProperties()
}
// End of generated file.