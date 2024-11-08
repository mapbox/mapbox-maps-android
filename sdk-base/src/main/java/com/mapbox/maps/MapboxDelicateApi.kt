package com.mapbox.maps

/**
 * Marks declarations that are **delicate** &mdash;
 * they have limited use-case and shall be used with care in general code.
 * Any use of a delicate declaration has to be carefully reviewed to make sure it is
 * properly used.
 * Carefully read documentation of any declaration marked as `MapboxDelicateApi`.
 */
@RequiresOptIn(
  level = RequiresOptIn.Level.WARNING,
  message = "This is a delicate API and its use requires care." +
    " Make sure you fully read and understand documentation of the declaration that is marked as a delicate API."
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR)
@MustBeDocumented
annotation class MapboxDelicateApi