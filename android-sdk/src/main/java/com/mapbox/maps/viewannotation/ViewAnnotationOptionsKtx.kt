package com.mapbox.maps.viewannotation

import com.mapbox.maps.ViewAnnotationOptions

/**
 * DSL builder function to create [ViewAnnotationOptions] object.
 */
inline fun viewAnnotationOptions(block: ViewAnnotationOptions.Builder.() -> Unit): ViewAnnotationOptions =
  ViewAnnotationOptions.Builder().apply(block).build()