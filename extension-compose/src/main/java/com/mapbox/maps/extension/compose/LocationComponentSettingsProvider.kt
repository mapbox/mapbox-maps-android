package com.mapbox.maps.extension.compose

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.R
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings

/**
 * Utility to get default [LocationComponentSettings] with 2D puck.
 */
public object LocationComponentSettingsProvider {
  private fun Context.getCompatDrawable(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(
    this.resources,
    resId,
    null
  )

  /**
   * Create a [LocationPuck2D] instance with or without an arrow bearing image.
   * @param context the context of application
   * @param withBearing if true, the location puck will show an arrow bearing image, default is false.
   */
  @JvmOverloads
  public fun createDefault2DPuck(
    context: Context,
    withBearing: Boolean = false
  ): LocationPuck2D = LocationPuck2D(
    topImage = context.getCompatDrawable(R.drawable.mapbox_user_icon),
    bearingImage = context.getCompatDrawable(
      if (withBearing)
        R.drawable.mapbox_user_bearing_icon
      else
        R.drawable.mapbox_user_stroke_icon
    ),
    shadowImage = context.getCompatDrawable(
      if (withBearing)
        R.drawable.mapbox_user_stroke_icon
      else
        R.drawable.mapbox_user_icon_shadow
    )
  )

  /**
   * Get the default [LocationComponentSettings].
   *
   * @param context the context of the application
   */
  public fun getDefaultSettings(context: Context): LocationComponentSettings {
    return LocationComponentSettings(
      locationPuck = createDefault2DPuck(context)
    )
  }
}