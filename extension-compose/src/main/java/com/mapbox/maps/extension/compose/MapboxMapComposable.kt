package com.mapbox.maps.extension.compose

/**
 * Marks a composable function being expected to be used inside another composable function marked as
 * [MapboxMapComposable].
 *
 * This will produce build warnings when [MapboxMapComposable] composable functions are used outside
 * of a [MapboxMapComposable] content lambda, and vice versa.
 */
@Retention(AnnotationRetention.BINARY)
// Only available after Compose Version 1.2.0-alpha04
// @ComposableTargetMarker(description = "Mapbox Map Composable")
@Target(
  AnnotationTarget.FILE,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.TYPE,
  AnnotationTarget.TYPE_PARAMETER,
)
public annotation class MapboxMapComposable