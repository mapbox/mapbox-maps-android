package com.mapbox.maps.plugin.annotation

/**
 * Enum class for annotation types
 */
enum class AnnotationType(
  /** The value of type */
  var value: Int
) {
  /** PolygonAnnotation type */
  PolygonAnnotation(1),

  /** PolylineAnnotation type */
  PolylineAnnotation(2),

  /** PointAnnotation type */
  PointAnnotation(3),

  /** CircleAnnotation type */
  CircleAnnotation(4),
}