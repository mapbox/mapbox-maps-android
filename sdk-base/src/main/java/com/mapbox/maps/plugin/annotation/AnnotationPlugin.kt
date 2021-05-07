package com.mapbox.maps.plugin.annotation

import android.view.View
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapSizePlugin
import com.mapbox.maps.plugin.MapStyleObserverPlugin

/**
 * Plugin interface for the annotation.
 */
interface AnnotationPlugin : MapPlugin, MapSizePlugin, MapStyleObserverPlugin {
  /**
   * Get an annotation manger
   *
   * @param mapView the mapView
   * @param type The type of he type of annotation manger
   * @param annotationConfig the configuration for AnnotationManager
   * @return the annotation manger
   */
  fun createAnnotationManager(
    mapView: View,
    type: AnnotationType,
    annotationConfig: AnnotationConfig?
  ): AnnotationManager<*, *, *, *, *, *, *>
}