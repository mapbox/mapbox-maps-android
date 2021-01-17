package com.mapbox.maps.plugin.annotation

/**
 * Enum class for annotation types
 */
enum class AnnotationType(
  /** The value of type */
  var value: Int
) {
  /** Fill annotation type */
  Fill(1),

  /** Line annotation type */
  Line(2),

  /** Symbol annotation type */
  Symbol(3),

  /** Circle annotation type */
  Circle(4),
}