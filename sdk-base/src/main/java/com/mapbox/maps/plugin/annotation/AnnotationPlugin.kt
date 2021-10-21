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
   * Create an annotation manger.
   *
   * @param mapView Default is null, pass in a MapView instance if need scrollX and scrollY support while dragging.
   * @param type The type of the created annotation manger
   * @param annotationConfig Default is null, used for some custom configs
   * @return The created annotation manger
   */
  fun createAnnotationManager(
    mapView: View?,
    type: AnnotationType,
    annotationConfig: AnnotationConfig?
  ): AnnotationManager<*, *, *, *, *, *, *>

  /**
   * Removes an annotation manager, this will remove the underlying layer and source from the style.
   * A removed annotation manager will not be able to reuse anymore, users need to create new annotation manger
   * to add annotations.
   */
  fun removeAnnotationManager(annotationManager: AnnotationManager<*, *, *, *, *, *, *>)
}