package com.mapbox.maps.viewannotation

/**
 * Describes modes that could be applied to update view annotation positions and
 * synchronize them with current map camera.
 */
enum class ViewAnnotationUpdateMode {
  /**
   * View annotations are updated on the next frame comparing to the MapView camera.
   *
   * Using this mode introduces fixed 1-frame delay for view annotation position and if panning the map
   * quickly view annotation will be visually behind the map.
   *
   * This mode is used by default as it has most consistent behaviour and no performance penalty.
   */
  FIXED_DELAY,

  /**
   * View annotations are updated independent from MapView.
   * View annotation position could be updated on the same frame as the map camera as well as on next one.
   *
   * Using this mode is not recommended as it could bring in view annotation jittering.
   */
  MAP_INDEPENDENT,

  /**
   * View annotations are updated on the same frame as the MapView camera.
   *
   * Using this mode uses the same frame to update both map camera and view annotation position.
   * Please note that using this mode may slightly decrease FPS when view annotations are visible on the map.
   */
  MAP_SYNCHRONIZED,
}