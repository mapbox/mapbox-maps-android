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

/**
 * Location Indicator layer.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers#location-indicator)
 *
 * @param layerId the ID of the layer, by default, a random id will be generated with UUID.
 * @param bearingImage Name of image in sprite to use as the middle of the location indicator.
 * @param shadowImage Name of image in sprite to use as the background of the location indicator.
 * @param topImage Name of image in sprite to use as the top of the location indicator.
 * @param accuracyRadius The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
 * @param accuracyRadiusBorderColor The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
 * @param accuracyRadiusColor The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
 * @param bearing The bearing of the location indicator.
 * @param bearingImageSize The size of the bearing image, as a scale factor applied to the size of the specified image.
 * @param emphasisCircleColor The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
 * @param emphasisCircleRadius The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
 * @param imagePitchDisplacement The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
 * @param location An array of [latitude, longitude, altitude] position of the location indicator.
 * @param locationIndicatorOpacity The opacity of the entire location indicator layer.
 * @param perspectiveCompensation The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
 * @param shadowImageSize The size of the shadow image, as a scale factor applied to the size of the specified image.
 * @param topImageSize The size of the top image, as a scale factor applied to the size of the specified image.
 * @param visibility Whether this layer is displayed.
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 * @param sourceLayer Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 * @param filter An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 */
@MapboxExperimental
@Composable
@MapboxMapComposable
public fun LocationIndicatorLayer(
  layerId: String = remember {
    generateRandomLayerId("location-indicator")
  },
  bearingImage: BearingImage = BearingImage.default,
  shadowImage: ShadowImage = ShadowImage.default,
  topImage: TopImage = TopImage.default,
  accuracyRadius: AccuracyRadius = AccuracyRadius.default,
  accuracyRadiusTransition: Transition = Transition.default,
  accuracyRadiusBorderColor: AccuracyRadiusBorderColor = AccuracyRadiusBorderColor.default,
  accuracyRadiusBorderColorTransition: Transition = Transition.default,
  accuracyRadiusColor: AccuracyRadiusColor = AccuracyRadiusColor.default,
  accuracyRadiusColorTransition: Transition = Transition.default,
  bearing: Bearing = Bearing.default,
  bearingTransition: Transition = Transition.default,
  bearingImageSize: BearingImageSize = BearingImageSize.default,
  bearingImageSizeTransition: Transition = Transition.default,
  emphasisCircleColor: EmphasisCircleColor = EmphasisCircleColor.default,
  emphasisCircleColorTransition: Transition = Transition.default,
  emphasisCircleRadius: EmphasisCircleRadius = EmphasisCircleRadius.default,
  emphasisCircleRadiusTransition: Transition = Transition.default,
  imagePitchDisplacement: ImagePitchDisplacement = ImagePitchDisplacement.default,
  location: Location = Location.default,
  locationTransition: Transition = Transition.default,
  locationIndicatorOpacity: LocationIndicatorOpacity = LocationIndicatorOpacity.default,
  locationIndicatorOpacityTransition: Transition = Transition.default,
  perspectiveCompensation: PerspectiveCompensation = PerspectiveCompensation.default,
  shadowImageSize: ShadowImageSize = ShadowImageSize.default,
  shadowImageSizeTransition: Transition = Transition.default,
  topImageSize: TopImageSize = TopImageSize.default,
  topImageSizeTransition: Transition = Transition.default,
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
        layerType = "location-indicator",
        layerId = layerId,
        coroutineScope = coroutineScope
      )
    },
    update = {
      init {
        if (bearingImage != BearingImage.default) {
          setProperty(BearingImage.NAME, bearingImage.value)
        }
        if (shadowImage != ShadowImage.default) {
          setProperty(ShadowImage.NAME, shadowImage.value)
        }
        if (topImage != TopImage.default) {
          setProperty(TopImage.NAME, topImage.value)
        }
        if (accuracyRadius != AccuracyRadius.default) {
          setProperty(AccuracyRadius.NAME, accuracyRadius.value)
        }
        if (accuracyRadiusTransition != Transition.default) {
          setProperty(AccuracyRadius.TRANSITION_NAME, accuracyRadiusTransition.value)
        }
        if (accuracyRadiusBorderColor != AccuracyRadiusBorderColor.default) {
          setProperty(AccuracyRadiusBorderColor.NAME, accuracyRadiusBorderColor.value)
        }
        if (accuracyRadiusBorderColorTransition != Transition.default) {
          setProperty(AccuracyRadiusBorderColor.TRANSITION_NAME, accuracyRadiusBorderColorTransition.value)
        }
        if (accuracyRadiusColor != AccuracyRadiusColor.default) {
          setProperty(AccuracyRadiusColor.NAME, accuracyRadiusColor.value)
        }
        if (accuracyRadiusColorTransition != Transition.default) {
          setProperty(AccuracyRadiusColor.TRANSITION_NAME, accuracyRadiusColorTransition.value)
        }
        if (bearing != Bearing.default) {
          setProperty(Bearing.NAME, bearing.value)
        }
        if (bearingTransition != Transition.default) {
          setProperty(Bearing.TRANSITION_NAME, bearingTransition.value)
        }
        if (bearingImageSize != BearingImageSize.default) {
          setProperty(BearingImageSize.NAME, bearingImageSize.value)
        }
        if (bearingImageSizeTransition != Transition.default) {
          setProperty(BearingImageSize.TRANSITION_NAME, bearingImageSizeTransition.value)
        }
        if (emphasisCircleColor != EmphasisCircleColor.default) {
          setProperty(EmphasisCircleColor.NAME, emphasisCircleColor.value)
        }
        if (emphasisCircleColorTransition != Transition.default) {
          setProperty(EmphasisCircleColor.TRANSITION_NAME, emphasisCircleColorTransition.value)
        }
        if (emphasisCircleRadius != EmphasisCircleRadius.default) {
          setProperty(EmphasisCircleRadius.NAME, emphasisCircleRadius.value)
        }
        if (emphasisCircleRadiusTransition != Transition.default) {
          setProperty(EmphasisCircleRadius.TRANSITION_NAME, emphasisCircleRadiusTransition.value)
        }
        if (imagePitchDisplacement != ImagePitchDisplacement.default) {
          setProperty(ImagePitchDisplacement.NAME, imagePitchDisplacement.value)
        }
        if (location != Location.default) {
          setProperty(Location.NAME, location.value)
        }
        if (locationTransition != Transition.default) {
          setProperty(Location.TRANSITION_NAME, locationTransition.value)
        }
        if (locationIndicatorOpacity != LocationIndicatorOpacity.default) {
          setProperty(LocationIndicatorOpacity.NAME, locationIndicatorOpacity.value)
        }
        if (locationIndicatorOpacityTransition != Transition.default) {
          setProperty(LocationIndicatorOpacity.TRANSITION_NAME, locationIndicatorOpacityTransition.value)
        }
        if (perspectiveCompensation != PerspectiveCompensation.default) {
          setProperty(PerspectiveCompensation.NAME, perspectiveCompensation.value)
        }
        if (shadowImageSize != ShadowImageSize.default) {
          setProperty(ShadowImageSize.NAME, shadowImageSize.value)
        }
        if (shadowImageSizeTransition != Transition.default) {
          setProperty(ShadowImageSize.TRANSITION_NAME, shadowImageSizeTransition.value)
        }
        if (topImageSize != TopImageSize.default) {
          setProperty(TopImageSize.NAME, topImageSize.value)
        }
        if (topImageSizeTransition != Transition.default) {
          setProperty(TopImageSize.TRANSITION_NAME, topImageSizeTransition.value)
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
        updateLayerId(layerId)
      }
      update(bearingImage) {
        setProperty(BearingImage.NAME, bearingImage.value)
      }
      update(shadowImage) {
        setProperty(ShadowImage.NAME, shadowImage.value)
      }
      update(topImage) {
        setProperty(TopImage.NAME, topImage.value)
      }
      update(accuracyRadius) {
        setProperty(AccuracyRadius.NAME, accuracyRadius.value)
      }
      update(accuracyRadiusTransition) {
        setProperty(AccuracyRadius.TRANSITION_NAME, accuracyRadiusTransition.value)
      }
      update(accuracyRadiusBorderColor) {
        setProperty(AccuracyRadiusBorderColor.NAME, accuracyRadiusBorderColor.value)
      }
      update(accuracyRadiusBorderColorTransition) {
        setProperty(AccuracyRadiusBorderColor.TRANSITION_NAME, accuracyRadiusBorderColorTransition.value)
      }
      update(accuracyRadiusColor) {
        setProperty(AccuracyRadiusColor.NAME, accuracyRadiusColor.value)
      }
      update(accuracyRadiusColorTransition) {
        setProperty(AccuracyRadiusColor.TRANSITION_NAME, accuracyRadiusColorTransition.value)
      }
      update(bearing) {
        setProperty(Bearing.NAME, bearing.value)
      }
      update(bearingTransition) {
        setProperty(Bearing.TRANSITION_NAME, bearingTransition.value)
      }
      update(bearingImageSize) {
        setProperty(BearingImageSize.NAME, bearingImageSize.value)
      }
      update(bearingImageSizeTransition) {
        setProperty(BearingImageSize.TRANSITION_NAME, bearingImageSizeTransition.value)
      }
      update(emphasisCircleColor) {
        setProperty(EmphasisCircleColor.NAME, emphasisCircleColor.value)
      }
      update(emphasisCircleColorTransition) {
        setProperty(EmphasisCircleColor.TRANSITION_NAME, emphasisCircleColorTransition.value)
      }
      update(emphasisCircleRadius) {
        setProperty(EmphasisCircleRadius.NAME, emphasisCircleRadius.value)
      }
      update(emphasisCircleRadiusTransition) {
        setProperty(EmphasisCircleRadius.TRANSITION_NAME, emphasisCircleRadiusTransition.value)
      }
      update(imagePitchDisplacement) {
        setProperty(ImagePitchDisplacement.NAME, imagePitchDisplacement.value)
      }
      update(location) {
        setProperty(Location.NAME, location.value)
      }
      update(locationTransition) {
        setProperty(Location.TRANSITION_NAME, locationTransition.value)
      }
      update(locationIndicatorOpacity) {
        setProperty(LocationIndicatorOpacity.NAME, locationIndicatorOpacity.value)
      }
      update(locationIndicatorOpacityTransition) {
        setProperty(LocationIndicatorOpacity.TRANSITION_NAME, locationIndicatorOpacityTransition.value)
      }
      update(perspectiveCompensation) {
        setProperty(PerspectiveCompensation.NAME, perspectiveCompensation.value)
      }
      update(shadowImageSize) {
        setProperty(ShadowImageSize.NAME, shadowImageSize.value)
      }
      update(shadowImageSizeTransition) {
        setProperty(ShadowImageSize.TRANSITION_NAME, shadowImageSizeTransition.value)
      }
      update(topImageSize) {
        setProperty(TopImageSize.NAME, topImageSize.value)
      }
      update(topImageSizeTransition) {
        setProperty(TopImageSize.TRANSITION_NAME, topImageSizeTransition.value)
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