package com.mapbox.maps.plugin.annotation

import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapSizePlugin

/**
 * Plugin interface for the annotation.
 */
fun interface AnnotationPlugin : MapPlugin, MapSizePlugin {
  /**
   * Get an annotation manger
   *
   * @param type the type of annotation manger
   * @param belowLayerId the id of the layer above the annotation layer
   * @param scrollX the scrolled left position of mapView
   * @param scrollY the scrolled top position of mapView
   *
   * @return the annotation manger
   */
  fun getAnnotationManager(
    type: AnnotationType,
    belowLayerId: String?,
    scrollX: Int,
    scrollY: Int
  ): AnnotationManager<*, *, *, *, *, *>
}