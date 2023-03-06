package com.mapbox.maps.plugin.annotation

import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapSizePlugin
import com.mapbox.maps.plugin.MapStyleObserverPlugin

/**
 * Plugin interface for the annotation.
 */
interface AnnotationPlugin : MapPlugin, MapSizePlugin, MapStyleObserverPlugin {
  /**
   * Create an annotation manager.
   *
   * @param type The type of the created annotation manager
   * @param annotationConfig Default is null, used for some custom configs
   * @return The created annotation manager
   */
  fun createAnnotationManager(
    type: AnnotationType,
    annotationConfig: AnnotationConfig?
  ): AnnotationManager<*, *, *, *, *, *, *>

  /**
   * Removes an annotation manager, this will remove the underlying layer and source from the style.
   * A removed annotation manager will not be able to reuse anymore, users need to create new annotation manager
   * to add annotations.
   */
  fun removeAnnotationManager(annotationManager: AnnotationManager<*, *, *, *, *, *, *>)
}