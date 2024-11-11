package com.mapbox.maps.viewannotation

/**
 * Describes modes that could be applied to update view annotation positions and
 * synchronize them with current map camera.
 *
 * Note: behaviour could still be undetermined as we could not control Android render thread directly.
 */
class ViewAnnotationUpdateMode private constructor(
  /**
   * Mode name.
   */
  private val mode: String,
) {
  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is ViewAnnotationUpdateMode &&
    mode == other.mode

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = mode.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = mode

  /**
   * Companion object.
   */
  companion object {
    /**
     * View annotations are updated on the next frame comparing to the MapView camera.
     *
     * Using this mode introduces fixed 1-frame delay for view annotation position and if panning the map
     * quickly view annotation will be visually behind the map.
     */
    @JvmField
    val MAP_FIXED_DELAY = ViewAnnotationUpdateMode("MAP_FIXED_DELAY")

    /**
     * View annotations are updated on the same frame as the MapView camera.
     *
     * Using this mode results in updating both map camera and view annotation position with 1-frame delay.
     * Please note that using this mode may slightly decrease FPS when view annotations are visible on the map.
     */
    @JvmField
    val MAP_SYNCHRONIZED = ViewAnnotationUpdateMode("MAP_SYNCHRONIZED")
  }
}