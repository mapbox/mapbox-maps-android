package com.mapbox.maps.plugin.annotation

/**
 * Config class for annotation plugin to get the annotation manager
 */
data class AnnotationConfig(
  /** The id of the layer above the annotation layer*/
  val belowLayerId: String? = null,
  /** The layer id for layer inside of annotation manager, if not set will use the default id managed by annotation manager.*/
  val layerId: String? = null,
  /** The source id for layer inside of annotation manager, if not set will use the default id managed by annotation manager.*/
  val sourceId: String? = null,
  /** The configure for GeoJsonSource inside of AnnotationManager*/
  val annotationSourceOptions: AnnotationSourceOptions? = null
)