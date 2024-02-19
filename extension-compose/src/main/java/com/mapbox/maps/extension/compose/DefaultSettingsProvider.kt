package com.mapbox.maps.extension.compose

import android.content.Context
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings

/**
 * Utility that provides the default values of the Settings for each plugin.
 */
@MapboxExperimental
public object DefaultSettingsProvider {
  /**
   * Create a default [LocationComponentSettings] with 2D location puck.
   *
   * @param context the context of the application
   * @param pixelRatio the pixel ratio to be applied to the [LocationComponentSettings.pulsingMaxRadius]
   */
  @JvmOverloads
  @MapboxExperimental
  public fun defaultLocationComponentSettings(
    context: Context,
    pixelRatio: Float = context.resources.displayMetrics.density
  ): LocationComponentSettings {
    return LocationComponentSettings(
      locationPuck = createDefault2DPuck(withBearing = false)
    ) {
      pulsingMaxRadius *= pixelRatio
      // above `locationPuck = createDefault2DPuck()` is without bearing images so disable it
      puckBearingEnabled = false
    }
  }

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