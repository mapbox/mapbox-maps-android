package com.mapbox.maps

/**
 * Mapbox exception class flagging map related [RuntimeException] did occur.
 */
class MapboxMapException(
  exceptionText: String?
) : RuntimeException(exceptionText)

/**
 * Mapbox exception class flagging view annotation related [RuntimeException] did occur.
 */
class MapboxViewAnnotationException(
  exceptionText: String?
) : RuntimeException(exceptionText)

/**
 * Mapbox exception class flagging location component plugin related [RuntimeException] did occur.
 */
class MapboxLocationComponentException(
  exceptionText: String?
) : RuntimeException(exceptionText)

/**
 * Mapbox exception class flagging annotation plugin related [RuntimeException] did occur.
 */
class MapboxAnnotationException(
  exceptionText: String?
) : RuntimeException(exceptionText)

/**
 * Mapbox exception class flagging camera animation plugin related [RuntimeException] did occur.
 */
class MapboxCameraAnimationException(
  exceptionText: String?
) : RuntimeException(exceptionText)

/**
 * Mapbox exception class flagging style extension related [RuntimeException] did occur.
 */
class MapboxStyleException(
  exceptionText: String?
) : RuntimeException(exceptionText)

/**
 * Mapbox exception thrown when any collection used to build GeoJson data is mutated.
 */
class MapboxConcurrentGeometryModificationException(
  exceptionText: String,
  @Suppress("UNUSED_PARAMETER")
  sourceId: String
) : ConcurrentModificationException(exceptionText)