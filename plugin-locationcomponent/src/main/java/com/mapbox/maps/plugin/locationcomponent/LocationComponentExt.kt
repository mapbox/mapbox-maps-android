@file:JvmName("LocationComponentUtils")

package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import androidx.annotation.DrawableRes
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
 * Extension val to get the LocationComponentPlugin instance with interface LocationComponentPlugin2 to handle accuracy ring.
 */
val MapPluginProviderDelegate.location2: LocationComponentPlugin2
  @JvmName("getLocationComponent2")
  get() = this.getPlugin(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID)!!

private fun Context.getCompatDrawable(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(
  this.resources,
  resId,
  null
)

/**
 * Create a [LocationPuck2D] instance with or without an arrow bearing image.
 * @param context the context of application
 * @param withBearing if ture, the location puck will show an arrow bearing image, default is false.
 */
@JvmOverloads
fun LocationComponentPlugin.createDefault2DPuck(
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