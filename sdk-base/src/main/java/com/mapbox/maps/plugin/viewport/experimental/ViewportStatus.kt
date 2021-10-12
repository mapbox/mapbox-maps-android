package com.mapbox.maps.plugin.viewport.experimental

import com.mapbox.maps.plugin.viewport.experimental.state.ViewportState
import com.mapbox.maps.plugin.viewport.experimental.transition.ViewportTransition
import java.util.*

/**
 * Represents the status of the viewport.
 *
 * It could be either a [ViewportState] or [ViewportTransition].
 */
sealed class ViewportStatus {
  /**
   * Represents the current status is a [ViewportState].
   *
   * The state is null if current status is IDLE.
   */
  class State(
    /**
     * The current [ViewportState].
     */
    val state: ViewportState?
  ) : ViewportStatus()

  /**
   * Represents the current status is a [ViewportTransition].
   */
  class Transition(
    /**
     * The current [ViewportTransition].
     */
    val transition: ViewportTransition,
    /**
     * The previous [ViewportState].
     */
    val fromState: ViewportState?,
    /**
     * The target [ViewportState].
     */
    val toState: ViewportState
  ) : ViewportStatus()

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean = when (this) {
    is State -> other is State && other.state === this.state
    is Transition ->
      other is Transition &&
        other.transition === this.transition &&
        other.fromState === this.fromState &&
        other.toState === this.toState
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = when (this) {
    is State -> Objects.hash(state)
    is Transition -> Objects.hash(transition, fromState, toState)
  }

  /**
   * Returns a String for the object.
   */
  override fun toString() = when (this) {
    is State -> "ViewportStatus#State(state=$state)"
    is Transition -> "ViewportStatus#Transition(transition=$transition, fromState=$fromState, toState=$toState)"
  }
}