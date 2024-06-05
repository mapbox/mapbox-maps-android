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
import com.mapbox.maps.extension.compose.style.layers.ImageValue
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
 * @param accuracyRadiusTransition Defines the transition of [accuracyRadius].
 * @param accuracyRadiusBorderColor The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
 * @param accuracyRadiusBorderColorTransition Defines the transition of [accuracyRadiusBorderColor].
 * @param accuracyRadiusColor The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
 * @param accuracyRadiusColorTransition Defines the transition of [accuracyRadiusColor].
 * @param bearing The bearing of the location indicator.
 * @param bearingTransition Defines the transition of [bearing].
 * @param bearingImageSize The size of the bearing image, as a scale factor applied to the size of the specified image.
 * @param bearingImageSizeTransition Defines the transition of [bearingImageSize].
 * @param emphasisCircleColor The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
 * @param emphasisCircleColorTransition Defines the transition of [emphasisCircleColor].
 * @param emphasisCircleRadius The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
 * @param emphasisCircleRadiusTransition Defines the transition of [emphasisCircleRadius].
 * @param imagePitchDisplacement The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
 * @param location An array of [latitude, longitude, altitude] position of the location indicator.
 * @param locationTransition Defines the transition of [location].
 * @param locationIndicatorOpacity The opacity of the entire location indicator layer.
 * @param locationIndicatorOpacityTransition Defines the transition of [locationIndicatorOpacity].
 * @param perspectiveCompensation The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
 * @param shadowImageSize The size of the shadow image, as a scale factor applied to the size of the specified image.
 * @param shadowImageSizeTransition Defines the transition of [shadowImageSize].
 * @param topImageSize The size of the top image, as a scale factor applied to the size of the specified image.
 * @param topImageSizeTransition Defines the transition of [topImageSize].
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
  bearingImage: ImageValue = ImageValue.INITIAL,
  shadowImage: ImageValue = ImageValue.INITIAL,
  topImage: ImageValue = ImageValue.INITIAL,
  accuracyRadius: DoubleValue = DoubleValue.INITIAL,
  accuracyRadiusTransition: Transition = Transition.INITIAL,
  accuracyRadiusBorderColor: ColorValue = ColorValue.INITIAL,
  accuracyRadiusBorderColorTransition: Transition = Transition.INITIAL,
  accuracyRadiusColor: ColorValue = ColorValue.INITIAL,
  accuracyRadiusColorTransition: Transition = Transition.INITIAL,
  bearing: DoubleValue = DoubleValue.INITIAL,
  bearingTransition: Transition = Transition.INITIAL,
  bearingImageSize: DoubleValue = DoubleValue.INITIAL,
  bearingImageSizeTransition: Transition = Transition.INITIAL,
  emphasisCircleColor: ColorValue = ColorValue.INITIAL,
  emphasisCircleColorTransition: Transition = Transition.INITIAL,
  emphasisCircleRadius: DoubleValue = DoubleValue.INITIAL,
  emphasisCircleRadiusTransition: Transition = Transition.INITIAL,
  imagePitchDisplacement: DoubleValue = DoubleValue.INITIAL,
  location: DoubleListValue = DoubleListValue.INITIAL,
  locationTransition: Transition = Transition.INITIAL,
  locationIndicatorOpacity: DoubleValue = DoubleValue.INITIAL,
  locationIndicatorOpacityTransition: Transition = Transition.INITIAL,
  perspectiveCompensation: DoubleValue = DoubleValue.INITIAL,
  shadowImageSize: DoubleValue = DoubleValue.INITIAL,
  shadowImageSizeTransition: Transition = Transition.INITIAL,
  topImageSize: DoubleValue = DoubleValue.INITIAL,
  topImageSizeTransition: Transition = Transition.INITIAL,
  visibility: VisibilityValue = VisibilityValue.INITIAL,
  minZoom: LongValue = LongValue.INITIAL,
  maxZoom: LongValue = LongValue.INITIAL,
  sourceLayer: StringValue = StringValue.INITIAL,
  filter: Filter = Filter.INITIAL,
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of LocationIndicatorLayer inside unsupported composable function")

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
        if (bearingImage.notInitial) {
          bearingImage.styleImage?.let {
            addImage(it)
          }
          setProperty("bearing-image", bearingImage.value)
        }
        if (shadowImage.notInitial) {
          shadowImage.styleImage?.let {
            addImage(it)
          }
          setProperty("shadow-image", shadowImage.value)
        }
        if (topImage.notInitial) {
          topImage.styleImage?.let {
            addImage(it)
          }
          setProperty("top-image", topImage.value)
        }
        if (accuracyRadius.notInitial) {
          setProperty("accuracy-radius", accuracyRadius.value)
        }
        if (accuracyRadiusTransition.notInitial) {
          setProperty("accuracy-radius-transition", accuracyRadiusTransition.value)
        }
        if (accuracyRadiusBorderColor.notInitial) {
          setProperty("accuracy-radius-border-color", accuracyRadiusBorderColor.value)
        }
        if (accuracyRadiusBorderColorTransition.notInitial) {
          setProperty("accuracy-radius-border-color-transition", accuracyRadiusBorderColorTransition.value)
        }
        if (accuracyRadiusColor.notInitial) {
          setProperty("accuracy-radius-color", accuracyRadiusColor.value)
        }
        if (accuracyRadiusColorTransition.notInitial) {
          setProperty("accuracy-radius-color-transition", accuracyRadiusColorTransition.value)
        }
        if (bearing.notInitial) {
          setProperty("bearing", bearing.value)
        }
        if (bearingTransition.notInitial) {
          setProperty("bearing-transition", bearingTransition.value)
        }
        if (bearingImageSize.notInitial) {
          setProperty("bearing-image-size", bearingImageSize.value)
        }
        if (bearingImageSizeTransition.notInitial) {
          setProperty("bearing-image-size-transition", bearingImageSizeTransition.value)
        }
        if (emphasisCircleColor.notInitial) {
          setProperty("emphasis-circle-color", emphasisCircleColor.value)
        }
        if (emphasisCircleColorTransition.notInitial) {
          setProperty("emphasis-circle-color-transition", emphasisCircleColorTransition.value)
        }
        if (emphasisCircleRadius.notInitial) {
          setProperty("emphasis-circle-radius", emphasisCircleRadius.value)
        }
        if (emphasisCircleRadiusTransition.notInitial) {
          setProperty("emphasis-circle-radius-transition", emphasisCircleRadiusTransition.value)
        }
        if (imagePitchDisplacement.notInitial) {
          setProperty("image-pitch-displacement", imagePitchDisplacement.value)
        }
        if (location.notInitial) {
          setProperty("location", location.value)
        }
        if (locationTransition.notInitial) {
          setProperty("location-transition", locationTransition.value)
        }
        if (locationIndicatorOpacity.notInitial) {
          setProperty("location-indicator-opacity", locationIndicatorOpacity.value)
        }
        if (locationIndicatorOpacityTransition.notInitial) {
          setProperty("location-indicator-opacity-transition", locationIndicatorOpacityTransition.value)
        }
        if (perspectiveCompensation.notInitial) {
          setProperty("perspective-compensation", perspectiveCompensation.value)
        }
        if (shadowImageSize.notInitial) {
          setProperty("shadow-image-size", shadowImageSize.value)
        }
        if (shadowImageSizeTransition.notInitial) {
          setProperty("shadow-image-size-transition", shadowImageSizeTransition.value)
        }
        if (topImageSize.notInitial) {
          setProperty("top-image-size", topImageSize.value)
        }
        if (topImageSizeTransition.notInitial) {
          setProperty("top-image-size-transition", topImageSizeTransition.value)
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
      update(bearingImage) {
        bearingImage.styleImage?.let {
          addImage(it)
        }
        setProperty("bearing-image", bearingImage.value)
      }
      update(shadowImage) {
        shadowImage.styleImage?.let {
          addImage(it)
        }
        setProperty("shadow-image", shadowImage.value)
      }
      update(topImage) {
        topImage.styleImage?.let {
          addImage(it)
        }
        setProperty("top-image", topImage.value)
      }
      update(accuracyRadius) {
        setProperty("accuracy-radius", accuracyRadius.value)
      }
      update(accuracyRadiusTransition) {
        setProperty("accuracy-radius-transition", accuracyRadiusTransition.value)
      }
      update(accuracyRadiusBorderColor) {
        setProperty("accuracy-radius-border-color", accuracyRadiusBorderColor.value)
      }
      update(accuracyRadiusBorderColorTransition) {
        setProperty("accuracy-radius-border-color-transition", accuracyRadiusBorderColorTransition.value)
      }
      update(accuracyRadiusColor) {
        setProperty("accuracy-radius-color", accuracyRadiusColor.value)
      }
      update(accuracyRadiusColorTransition) {
        setProperty("accuracy-radius-color-transition", accuracyRadiusColorTransition.value)
      }
      update(bearing) {
        setProperty("bearing", bearing.value)
      }
      update(bearingTransition) {
        setProperty("bearing-transition", bearingTransition.value)
      }
      update(bearingImageSize) {
        setProperty("bearing-image-size", bearingImageSize.value)
      }
      update(bearingImageSizeTransition) {
        setProperty("bearing-image-size-transition", bearingImageSizeTransition.value)
      }
      update(emphasisCircleColor) {
        setProperty("emphasis-circle-color", emphasisCircleColor.value)
      }
      update(emphasisCircleColorTransition) {
        setProperty("emphasis-circle-color-transition", emphasisCircleColorTransition.value)
      }
      update(emphasisCircleRadius) {
        setProperty("emphasis-circle-radius", emphasisCircleRadius.value)
      }
      update(emphasisCircleRadiusTransition) {
        setProperty("emphasis-circle-radius-transition", emphasisCircleRadiusTransition.value)
      }
      update(imagePitchDisplacement) {
        setProperty("image-pitch-displacement", imagePitchDisplacement.value)
      }
      update(location) {
        setProperty("location", location.value)
      }
      update(locationTransition) {
        setProperty("location-transition", locationTransition.value)
      }
      update(locationIndicatorOpacity) {
        setProperty("location-indicator-opacity", locationIndicatorOpacity.value)
      }
      update(locationIndicatorOpacityTransition) {
        setProperty("location-indicator-opacity-transition", locationIndicatorOpacityTransition.value)
      }
      update(perspectiveCompensation) {
        setProperty("perspective-compensation", perspectiveCompensation.value)
      }
      update(shadowImageSize) {
        setProperty("shadow-image-size", shadowImageSize.value)
      }
      update(shadowImageSizeTransition) {
        setProperty("shadow-image-size-transition", shadowImageSizeTransition.value)
      }
      update(topImageSize) {
        setProperty("top-image-size", topImageSize.value)
      }
      update(topImageSizeTransition) {
        setProperty("top-image-size-transition", topImageSizeTransition.value)
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