package com.mapbox.maps

/**
 * Annotation class to mark API as experimental.
 */
@RequiresOptIn(
  level = RequiresOptIn.Level.WARNING,
  message = "This API is experimental. It may be changed in the future without notice."
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@MustBeDocumented
annotation class MapboxExperimental