package com.mapbox.maps.renderer

/**
 * Denotes that the annotated method should only be called on a Mapbox render thread.
 * If the annotated element is a class, then all methods in the class should be called
 * on a Mapbox render thread.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.CONSTRUCTOR,
  AnnotationTarget.CLASS,
)
annotation class RenderThread