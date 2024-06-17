package com.mapbox.maps.extension.compose.style.imports

import androidx.compose.runtime.ComposableTargetMarker

/**
 * Marks a composable function as being expected to be used inside another composable function that is
 * also marked or inferred to be marked as a [MapboxStyleImportComposable].
 *
 * This will produce build warnings when [MapboxStyleImportComposable] composable functions are used outside
 * of a [MapboxStyleImportComposable] content lambda, and vice versa.
 */
@Retention(AnnotationRetention.BINARY)
@ComposableTargetMarker(description = "Mapbox Style Import Composable")
@Target(
  AnnotationTarget.FILE,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.TYPE,
  AnnotationTarget.TYPE_PARAMETER,
)
public annotation class MapboxStyleImportComposable