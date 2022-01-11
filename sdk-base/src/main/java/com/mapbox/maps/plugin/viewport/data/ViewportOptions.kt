package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.plugin.viewport.state.ViewportState

/**
 * Options that impact the [ViewportPlugin].
 */
class ViewportOptions private constructor(
  /**
   * Indicates whether to transition [ViewportState] to IDLE (null) when user interact with the map
   * using gestures.
   *
   * Defaults to true.
   */
  val transitionsToIdleUponUserInteraction: Boolean
) {
  /**
   * Returns a builder that created the [ViewportOptions]
   */
  fun toBuilder() = Builder().transitionsToIdleUponUserInteraction(transitionsToIdleUponUserInteraction)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is ViewportOptions &&
    transitionsToIdleUponUserInteraction == other.transitionsToIdleUponUserInteraction

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = transitionsToIdleUponUserInteraction.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "ViewportPluginOptions(transitionsToIdleUponUserInteraction=$transitionsToIdleUponUserInteraction)"

  /**
   * Builder for [ViewportOptions]
   */
  class Builder {
    private var transitionsToIdleUponUserInteraction: Boolean = true

    /**
     * Indicates whether to transition [ViewportState] to IDLE (null) when user interact with the map
     * using gestures.
     *
     * Defaults to true.
     */
    fun transitionsToIdleUponUserInteraction(transitionsToIdleUponUserInteraction: Boolean) = apply {
      this.transitionsToIdleUponUserInteraction = transitionsToIdleUponUserInteraction
    }

    /**
     * Builds [ViewportOptions]
     */
    fun build() = ViewportOptions(transitionsToIdleUponUserInteraction)
  }
}