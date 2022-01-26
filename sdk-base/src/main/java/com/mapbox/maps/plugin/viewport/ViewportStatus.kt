package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import java.util.*

/**
 * Represents the status of the viewport.
 *
 * It could be either a [ViewportState] or [ViewportTransition].
 */
@MapboxExperimental
sealed class ViewportStatus {
  /**
   * Represents the current status is a [ViewportState].
   */
  class State(
    /**
     * The current [ViewportState].
     */
    val state: ViewportState
  ) : ViewportStatus() {
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    override fun equals(other: Any?): Boolean = other is State && other.state === this.state

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode() = Objects.hash(state)

    /**
     * Returns a String for the object.
     */
    override fun toString() = "ViewportStatus#State(state=$state)"
  }

  /**
   * Represents the current status is a [ViewportTransition].
   */
  class Transition(
    /**
     * The current [ViewportTransition].
     */
    val transition: ViewportTransition,
    /**
     * The target [ViewportState].
     */
    val toState: ViewportState
  ) : ViewportStatus() {
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    override fun equals(other: Any?): Boolean =
      other is Transition &&
        other.transition === this.transition &&
        other.toState === this.toState

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode() = Objects.hash(transition, toState)

    /**
     * Returns a String for the object.
     */
    override fun toString() =
      "ViewportStatus#Transition(transition=$transition, toState=$toState)"
  }

  /**
   * Represents the current status is Idle.
   */
  object Idle : ViewportStatus() {
    /**
     * Returns a String for the object.
     */
    override fun toString() = "ViewportStatus#Idle"
  }
}