package com.mapbox.maps.plugin.annotation

import com.mapbox.geojson.Geometry

/**
 * Options interface for building annotations
 */
fun interface AnnotationOptions<G : Geometry, T : Annotation<G>> {
  /**
   * Build an annotation
   *
   * @param id: the id for this annotation
   * @param annotationManager: the annotationManager that manage this annotation
   *
   * @return the annotation that is built
   */
  fun build(id: String, annotationManager: AnnotationManager<G, T, *, *, *, *, *>): T
}