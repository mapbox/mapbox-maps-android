package com.mapbox.maps.module.telemetry

import androidx.annotation.RestrictTo

/**
 * Identifies the UI framework used to create a map instance.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class UiFramework private constructor(
  /**
   * The string sent in the telemetry events payload.
   */
  val value: String
) {
  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is UiFramework &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "UiFramework(value=$value)"

  /**
   * Companion object.
   */
  companion object {
    /**
     * Traditional Android View-based map (`MapView`).
     */
    @JvmField
    val ANDROID_VIEW = UiFramework("android-view")

    /**
     * Jetpack Compose map (`MapboxMap` composable).
     */
    @JvmField
    val JETPACK_COMPOSE = UiFramework("jetpack-compose")
  }
}