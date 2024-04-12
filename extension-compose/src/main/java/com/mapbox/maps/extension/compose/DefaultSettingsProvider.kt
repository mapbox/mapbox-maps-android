package com.mapbox.maps.extension.compose

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener

/**
 * Utility that provides the default values of the Settings for each plugin.
 */
@MapboxExperimental
public object DefaultSettingsProvider {
  /**
   * Get the default [OnMapClickListener] that does nothing.
   */
  @MapboxExperimental
  public val defaultOnClickListener: OnMapClickListener = OnMapClickListener { false }

  /**
   * Get the default [OnMapLongClickListener] that does nothing.
   */
  @MapboxExperimental
  public val defaultOnLongClickListener: OnMapLongClickListener = OnMapLongClickListener { false }
}