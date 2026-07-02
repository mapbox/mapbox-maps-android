package com.mapbox.maps.module.telemetry

import androidx.annotation.RestrictTo

/**
 * Metadata attached to map telemetry events.
 *
 * Use [MapTelemetryMetadata.Builder] to construct an instance.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class MapTelemetryMetadata private constructor(
  /**
   * The UI framework used to create the map instance, or null if not specified.
   */
  val uiFramework: UiFramework?
) {
  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is MapTelemetryMetadata &&
    uiFramework == other.uiFramework

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = uiFramework.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "MapTelemetryMetadata(uiFramework=$uiFramework)"

  /**
   * Builder for [MapTelemetryMetadata].
   */
  class Builder {
    private var uiFramework: UiFramework? = null

    /**
     * Set the UI framework used to create the map instance.
     *
     * @param uiFramework the UI framework, or null if not specified
     */
    fun uiFramework(uiFramework: UiFramework?): Builder = apply {
      this.uiFramework = uiFramework
    }

    /**
     * Build the [MapTelemetryMetadata] instance.
     */
    fun build(): MapTelemetryMetadata = MapTelemetryMetadata(uiFramework)
  }
}