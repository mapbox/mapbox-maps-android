package com.mapbox.maps

/**
 * Annotation class to mark API as internal.
 */
@RequiresOptIn(
  level = RequiresOptIn.Level.WARNING,
  message = "This API is internal. It may be changed in the future without notice."
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
annotation class MapboxInternal