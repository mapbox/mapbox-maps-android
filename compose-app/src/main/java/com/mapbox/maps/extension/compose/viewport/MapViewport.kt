package com.mapbox.maps.extension.compose.viewport

import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import java.util.*


public sealed class MapViewport {
  /**
   * Represents the current status is a [FollowPuckState].
   */
  public class FollowPuckState(
    /**
     * The current [FollowPuckViewportStateOptions].
     */
    public val stateOptions: FollowPuckViewportStateOptions,
    /**
     * The current [DefaultViewportTransitionOptions].
     */
    public val transitionOptions: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder()
      .build(),
  ) : MapViewport() {
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    override fun equals(other: Any?): Boolean =
      other is FollowPuckState && other.stateOptions === this.stateOptions &&
        other.transitionOptions === this.transitionOptions

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode(): Int = Objects.hash(stateOptions, transitionOptions)

    /**
     * Returns a String for the object.
     */
    override fun toString(): String =
      "MapViewport#FollowPuckState(stateOptions=$stateOptions, transitionOptions=$transitionOptions)"
  }

  /**
   * Represents the current status is a [OverviewState].
   */
  public class OverviewState(
    /**
     * The current [OverviewViewportStateOptions].
     */
    public val stateOptions: OverviewViewportStateOptions,
    /**
     * The current [DefaultViewportTransitionOptions].
     */
    public val transitionOptions: DefaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder()
      .build(),
  ) : MapViewport() {
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    override fun equals(other: Any?): Boolean =
      other is OverviewState && other.stateOptions === this.stateOptions &&
        other.transitionOptions === this.transitionOptions

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode(): Int = Objects.hash(stateOptions, transitionOptions)

    /**
     * Returns a String for the object.
     */
    override fun toString(): String =
      "MapViewport#OverviewState(stateOptions=$stateOptions,transitionOptions=$transitionOptions)"
  }

  /**
   * Represents the current status is Idle.
   */
  public object Idle : MapViewport() {
    /**
     * Returns a String for the object.
     */
    override fun toString(): String = "MapViewport#Idle"
  }
}