package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.state.ViewportState

/**
 * Configuration options that impact the [ViewportPlugin].
 */
class ViewportOptions private constructor(
  /**
   * Indicates whether the [ViewportPlugin] should idle when user interact with the map using gestures.
   *
   * Set this property to false to enable building custom [ViewportState] that can work simultaneously
   * with certain types of gestures.
   *
   * Defaults to true.
   */
  val transitionsToIdleUponUserInteraction: Boolean
) {
  /**
   * Returns a builder that created the [ViewportOptions]
   */
  fun toBuilder(): Builder = Builder().transitionsToIdleUponUserInteraction(transitionsToIdleUponUserInteraction)

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
     * Indicates whether the [ViewportPlugin] should idle when user interact with the map using gestures.
     *
     * Set this property to false to enable building custom [ViewportState] that can work simultaneously
     * with certain types of gestures.
     *
     * Defaults to true.
     */
    fun transitionsToIdleUponUserInteraction(transitionsToIdleUponUserInteraction: Boolean): Builder = apply {
      this.transitionsToIdleUponUserInteraction = transitionsToIdleUponUserInteraction
    }

    /**
     * Builds [ViewportOptions]
     */
    fun build() = ViewportOptions(transitionsToIdleUponUserInteraction)
  }
}