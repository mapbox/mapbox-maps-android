package com.mapbox.maps.plugin.location

import com.mapbox.bindgen.Value

internal class IndicatorLocationLayerWrapper(layerId: String) : LocationLayerWrapper(layerId) {

  init {
    layerProperties["id"] = Value(layerId)
    layerProperties["type"] = Value("location-indicator")
    layerProperties["location-transition"] = buildTransition(0, 0)
    layerProperties["perspective-compensation"] = Value(0.9)
    layerProperties["image-pitch-displacement"] = Value(4.0)
  }

  private fun buildTransition(delay: Long = 0, duration: Long = 0): Value {
    val transition = HashMap<String, Value>()
    transition["delay"] = Value(delay)
    transition["duration"] = Value(duration)
    return Value(transition)
  }

  fun bearing(bearing: Double) = updateProperty("bearing", Value(bearing))

  fun location(location: List<Double>) = updateProperty("location", Value(location.map { Value(it) }))

  fun accuracyRadiusColor(expression: List<Value>) = updateProperty("accuracy-radius-color", Value(expression))

  fun accuracyRadiusBorderColor(expression: List<Value>) = updateProperty("accuracy-radius-border-color", Value(expression))

  fun shadowImageSize(shadowImageSizeExpression: List<Value>) = updateProperty("shadow-image-size", Value(shadowImageSizeExpression))

  fun bearingImageSize(bearingImageSizeExpression: List<Value>) = updateProperty("bearing-image-size", Value(bearingImageSizeExpression))

  fun topImageSize(topImageSizeExpression: List<Value>) = updateProperty("top-image-size", Value(topImageSizeExpression))

  fun accuracyRadius(accuracyRadius: Double) = updateProperty("accuracy-radius", Value(accuracyRadius))

  fun emphasisCircleRadius(emphasisCircleRadius: Double) = updateProperty("emphasis-circle-radius", Value(emphasisCircleRadius))

  fun emphasisCircleColorTransition(delay: Long = 0, duration: Long = 0) {
    val transition = buildTransition(delay, duration)
    updateProperty("emphasis-circle-color-transition", transition)
  }

  fun emphasisCircleColor(emphasisCircleColorExpression: List<Value>) = updateProperty("emphasis-circle-color", Value(emphasisCircleColorExpression))

  fun topImage(topImage: String) = updateProperty("top-image", Value(topImage))

  fun bearingImage(bearingImage: String) = updateProperty("bearing-image", Value(bearingImage))

  fun shadowImage(shadowImage: String) = updateProperty("shadow-image", Value(shadowImage))
}