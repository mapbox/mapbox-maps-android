package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.plugin.viewport.ViewportStatus

/**
 * The reason why the [ViewportStatus] has been changed.
 */
class ViewportStatusChangeReason private constructor(
  /**
   * The string describing the change reason.
   */
  private val reason: String
) {
  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is ViewportStatusChangeReason &&
    reason == other.reason

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = reason.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "ViewportStatusChangeReason(reason=$reason)"

  /**
   * Companion object.
   */
  companion object {
    /**
     * The [ViewportStatus] is changed by the Idle request.
     */
    @JvmField
    val IDLE_REQUESTED = ViewportStatusChangeReason("IDLE_REQUESTED")

    /**
     * The [ViewportStatus] is changed by a transition started event.
     */
    @JvmField
    val TRANSITION_STARTED = ViewportStatusChangeReason("TRANSITION_STARTED")

    /**
     * The [ViewportStatus] is changed by a transition succeeded event.
     */
    @JvmField
    val TRANSITION_SUCCEEDED = ViewportStatusChangeReason("TRANSITION_SUCCEEDED")

    /**
     * The [ViewportStatus] is changed by a transition failed event.
     */
    @JvmField
    val TRANSITION_FAILED = ViewportStatusChangeReason("TRANSITION_FAILED")

    /**
     * The [ViewportStatus] is changed by an user interaction event.
     */
    @JvmField
    val USER_INTERACTION = ViewportStatusChangeReason("USER_INTERACTION")
  }
}