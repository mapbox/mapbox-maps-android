package com.mapbox.maps.extension.compose

import androidx.compose.runtime.ComposableTargetMarker
import com.mapbox.maps.MapboxExperimental

/**
 * Marks a composable function as being expected to be used inside another composable function that is
 * also marked or inferred to be marked as a [MapboxMapComposable].
 *
 * This will produce build warnings when [MapboxMapComposable] composable functions are used outside
 * of a [MapboxMapComposable] content lambda, and vice versa.
 */
@Retention(AnnotationRetention.BINARY)
@ComposableTargetMarker(description = "Mapbox Map Composable")
@Target(
  AnnotationTarget.FILE,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.TYPE,
  AnnotationTarget.TYPE_PARAMETER,
)
@MapboxExperimental
public annotation class MapboxMapComposable