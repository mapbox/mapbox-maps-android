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
 * An extruded (3D) polygon.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-fill-extrusion)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
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
  layerId: String,
  sourceId: String,
  fillExtrusionEdgeRadius: FillExtrusionEdgeRadius = FillExtrusionEdgeRadius.default,
  fillExtrusionAmbientOcclusionGroundAttenuation: FillExtrusionAmbientOcclusionGroundAttenuation = FillExtrusionAmbientOcclusionGroundAttenuation.default,
  fillExtrusionAmbientOcclusionGroundRadius: FillExtrusionAmbientOcclusionGroundRadius = FillExtrusionAmbientOcclusionGroundRadius.default,
  fillExtrusionAmbientOcclusionIntensity: FillExtrusionAmbientOcclusionIntensity = FillExtrusionAmbientOcclusionIntensity.default,
  fillExtrusionAmbientOcclusionRadius: FillExtrusionAmbientOcclusionRadius = FillExtrusionAmbientOcclusionRadius.default,
  fillExtrusionAmbientOcclusionWallRadius: FillExtrusionAmbientOcclusionWallRadius = FillExtrusionAmbientOcclusionWallRadius.default,
  fillExtrusionBase: FillExtrusionBase = FillExtrusionBase.default,
  fillExtrusionColor: FillExtrusionColor = FillExtrusionColor.default,
  fillExtrusionCutoffFadeRange: FillExtrusionCutoffFadeRange = FillExtrusionCutoffFadeRange.default,
  fillExtrusionEmissiveStrength: FillExtrusionEmissiveStrength = FillExtrusionEmissiveStrength.default,
  fillExtrusionFloodLightColor: FillExtrusionFloodLightColor = FillExtrusionFloodLightColor.default,
  fillExtrusionFloodLightGroundAttenuation: FillExtrusionFloodLightGroundAttenuation = FillExtrusionFloodLightGroundAttenuation.default,
  fillExtrusionFloodLightGroundRadius: FillExtrusionFloodLightGroundRadius = FillExtrusionFloodLightGroundRadius.default,
  fillExtrusionFloodLightIntensity: FillExtrusionFloodLightIntensity = FillExtrusionFloodLightIntensity.default,
  fillExtrusionFloodLightWallRadius: FillExtrusionFloodLightWallRadius = FillExtrusionFloodLightWallRadius.default,
  fillExtrusionHeight: FillExtrusionHeight = FillExtrusionHeight.default,
  fillExtrusionOpacity: FillExtrusionOpacity = FillExtrusionOpacity.default,
  fillExtrusionPattern: FillExtrusionPattern = FillExtrusionPattern.default,
  fillExtrusionRoundedRoof: FillExtrusionRoundedRoof = FillExtrusionRoundedRoof.default,
  fillExtrusionTranslate: FillExtrusionTranslate = FillExtrusionTranslate.default,
  fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor.default,
  fillExtrusionVerticalGradient: FillExtrusionVerticalGradient = FillExtrusionVerticalGradient.default,
  fillExtrusionVerticalScale: FillExtrusionVerticalScale = FillExtrusionVerticalScale.default,
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
        layerId = layerId,
        sourceId = sourceId,
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
        if (fillExtrusionAmbientOcclusionGroundRadius != FillExtrusionAmbientOcclusionGroundRadius.default) {
          setProperty(FillExtrusionAmbientOcclusionGroundRadius.NAME, fillExtrusionAmbientOcclusionGroundRadius.value)
        }
        if (fillExtrusionAmbientOcclusionIntensity != FillExtrusionAmbientOcclusionIntensity.default) {
          setProperty(FillExtrusionAmbientOcclusionIntensity.NAME, fillExtrusionAmbientOcclusionIntensity.value)
        }
        if (fillExtrusionAmbientOcclusionRadius != FillExtrusionAmbientOcclusionRadius.default) {
          setProperty(FillExtrusionAmbientOcclusionRadius.NAME, fillExtrusionAmbientOcclusionRadius.value)
        }
        if (fillExtrusionAmbientOcclusionWallRadius != FillExtrusionAmbientOcclusionWallRadius.default) {
          setProperty(FillExtrusionAmbientOcclusionWallRadius.NAME, fillExtrusionAmbientOcclusionWallRadius.value)
        }
        if (fillExtrusionBase != FillExtrusionBase.default) {
          setProperty(FillExtrusionBase.NAME, fillExtrusionBase.value)
        }
        if (fillExtrusionColor != FillExtrusionColor.default) {
          setProperty(FillExtrusionColor.NAME, fillExtrusionColor.value)
        }
        if (fillExtrusionCutoffFadeRange != FillExtrusionCutoffFadeRange.default) {
          setProperty(FillExtrusionCutoffFadeRange.NAME, fillExtrusionCutoffFadeRange.value)
        }
        if (fillExtrusionEmissiveStrength != FillExtrusionEmissiveStrength.default) {
          setProperty(FillExtrusionEmissiveStrength.NAME, fillExtrusionEmissiveStrength.value)
        }
        if (fillExtrusionFloodLightColor != FillExtrusionFloodLightColor.default) {
          setProperty(FillExtrusionFloodLightColor.NAME, fillExtrusionFloodLightColor.value)
        }
        if (fillExtrusionFloodLightGroundAttenuation != FillExtrusionFloodLightGroundAttenuation.default) {
          setProperty(FillExtrusionFloodLightGroundAttenuation.NAME, fillExtrusionFloodLightGroundAttenuation.value)
        }
        if (fillExtrusionFloodLightGroundRadius != FillExtrusionFloodLightGroundRadius.default) {
          setProperty(FillExtrusionFloodLightGroundRadius.NAME, fillExtrusionFloodLightGroundRadius.value)
        }
        if (fillExtrusionFloodLightIntensity != FillExtrusionFloodLightIntensity.default) {
          setProperty(FillExtrusionFloodLightIntensity.NAME, fillExtrusionFloodLightIntensity.value)
        }
        if (fillExtrusionFloodLightWallRadius != FillExtrusionFloodLightWallRadius.default) {
          setProperty(FillExtrusionFloodLightWallRadius.NAME, fillExtrusionFloodLightWallRadius.value)
        }
        if (fillExtrusionHeight != FillExtrusionHeight.default) {
          setProperty(FillExtrusionHeight.NAME, fillExtrusionHeight.value)
        }
        if (fillExtrusionOpacity != FillExtrusionOpacity.default) {
          setProperty(FillExtrusionOpacity.NAME, fillExtrusionOpacity.value)
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
        if (fillExtrusionTranslateAnchor != FillExtrusionTranslateAnchor.default) {
          setProperty(FillExtrusionTranslateAnchor.NAME, fillExtrusionTranslateAnchor.value)
        }
        if (fillExtrusionVerticalGradient != FillExtrusionVerticalGradient.default) {
          setProperty(FillExtrusionVerticalGradient.NAME, fillExtrusionVerticalGradient.value)
        }
        if (fillExtrusionVerticalScale != FillExtrusionVerticalScale.default) {
          setProperty(FillExtrusionVerticalScale.NAME, fillExtrusionVerticalScale.value)
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
      update(sourceId) {
        setConstructorProperty("source", Value(sourceId))
      }
      update(fillExtrusionEdgeRadius) {
        setProperty(FillExtrusionEdgeRadius.NAME, fillExtrusionEdgeRadius.value)
      }
      update(fillExtrusionAmbientOcclusionGroundAttenuation) {
        setProperty(FillExtrusionAmbientOcclusionGroundAttenuation.NAME, fillExtrusionAmbientOcclusionGroundAttenuation.value)
      }
      update(fillExtrusionAmbientOcclusionGroundRadius) {
        setProperty(FillExtrusionAmbientOcclusionGroundRadius.NAME, fillExtrusionAmbientOcclusionGroundRadius.value)
      }
      update(fillExtrusionAmbientOcclusionIntensity) {
        setProperty(FillExtrusionAmbientOcclusionIntensity.NAME, fillExtrusionAmbientOcclusionIntensity.value)
      }
      update(fillExtrusionAmbientOcclusionRadius) {
        setProperty(FillExtrusionAmbientOcclusionRadius.NAME, fillExtrusionAmbientOcclusionRadius.value)
      }
      update(fillExtrusionAmbientOcclusionWallRadius) {
        setProperty(FillExtrusionAmbientOcclusionWallRadius.NAME, fillExtrusionAmbientOcclusionWallRadius.value)
      }
      update(fillExtrusionBase) {
        setProperty(FillExtrusionBase.NAME, fillExtrusionBase.value)
      }
      update(fillExtrusionColor) {
        setProperty(FillExtrusionColor.NAME, fillExtrusionColor.value)
      }
      update(fillExtrusionCutoffFadeRange) {
        setProperty(FillExtrusionCutoffFadeRange.NAME, fillExtrusionCutoffFadeRange.value)
      }
      update(fillExtrusionEmissiveStrength) {
        setProperty(FillExtrusionEmissiveStrength.NAME, fillExtrusionEmissiveStrength.value)
      }
      update(fillExtrusionFloodLightColor) {
        setProperty(FillExtrusionFloodLightColor.NAME, fillExtrusionFloodLightColor.value)
      }
      update(fillExtrusionFloodLightGroundAttenuation) {
        setProperty(FillExtrusionFloodLightGroundAttenuation.NAME, fillExtrusionFloodLightGroundAttenuation.value)
      }
      update(fillExtrusionFloodLightGroundRadius) {
        setProperty(FillExtrusionFloodLightGroundRadius.NAME, fillExtrusionFloodLightGroundRadius.value)
      }
      update(fillExtrusionFloodLightIntensity) {
        setProperty(FillExtrusionFloodLightIntensity.NAME, fillExtrusionFloodLightIntensity.value)
      }
      update(fillExtrusionFloodLightWallRadius) {
        setProperty(FillExtrusionFloodLightWallRadius.NAME, fillExtrusionFloodLightWallRadius.value)
      }
      update(fillExtrusionHeight) {
        setProperty(FillExtrusionHeight.NAME, fillExtrusionHeight.value)
      }
      update(fillExtrusionOpacity) {
        setProperty(FillExtrusionOpacity.NAME, fillExtrusionOpacity.value)
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
      update(fillExtrusionTranslateAnchor) {
        setProperty(FillExtrusionTranslateAnchor.NAME, fillExtrusionTranslateAnchor.value)
      }
      update(fillExtrusionVerticalGradient) {
        setProperty(FillExtrusionVerticalGradient.NAME, fillExtrusionVerticalGradient.value)
      }
      update(fillExtrusionVerticalScale) {
        setProperty(FillExtrusionVerticalScale.NAME, fillExtrusionVerticalScale.value)
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