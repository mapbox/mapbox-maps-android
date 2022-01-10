@file:JvmName("LocationComponentUtils")

package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val to get the LocationComponentPlugin instance.
 */
val MapPluginProviderDelegate.location: LocationComponentPlugin
  @JvmName("getLocationComponent")
  get() = this.getPlugin(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID)!!

/**
 * Create a [LocationPuck2D] instance with or without an arrow bearing image.
 * @param context the context of application
 * @param withBearing if ture, the location puck will show an arrow bearing image, default is false.
 */
@JvmOverloads
fun LocationComponentPlugin.createDefault2DPuck(
  context: Context,
  withBearing: Boolean = false
): LocationPuck2D {
  if (withBearing) {
    return LocationPuck2D(
      topImage = ResourcesCompat.getDrawable(context.resources, R.drawable.mapbox_user_icon, null),
      bearingImage = ResourcesCompat.getDrawable(
        context.resources,
        R.drawable.mapbox_user_bearing_icon,
        null
      ),
      shadowImage = ResourcesCompat.getDrawable(
        context.resources,
        R.drawable.mapbox_user_stroke_icon,
        null
      )
    )
  } else {
    return LocationPuck2D(
      topImage = ResourcesCompat.getDrawable(
        context.resources,
        R.drawable.mapbox_user_icon,
        null
      ),
      bearingImage = ResourcesCompat.getDrawable(
        context.resources,
        R.drawable.mapbox_user_stroke_icon,
        null
      ),
      shadowImage = ResourcesCompat.getDrawable(
        context.resources,
        R.drawable.mapbox_user_icon_shadow,
        null
      )
    )
  }
}