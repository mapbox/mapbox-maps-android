@file:JvmName("LocationComponentUtils")

package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.RestrictTo
import androidx.core.content.res.ResourcesCompat
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val to get the LocationComponentPlugin instance.
 */
val MapPluginProviderDelegate.location: LocationComponentPlugin
  @JvmName("getLocationComponent")
  get() = this.getPlugin(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID)!!

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
fun LocationComponentPlugin.createDefault2DPuck(
  @Suppress("UNUSED_PARAMETER")
  context: Context,
  withBearing: Boolean = false
): LocationPuck2D = LocationPuck2D(
  topImage = ImageHolder.from(R.drawable.mapbox_user_icon),
  bearingImage = if (withBearing)
    ImageHolder.from(R.drawable.mapbox_user_bearing_icon)
  else
    ImageHolder.from(R.drawable.mapbox_user_stroke_icon),
  shadowImage = if (withBearing)
    ImageHolder.from(R.drawable.mapbox_user_stroke_icon)
  else
    ImageHolder.from(R.drawable.mapbox_user_icon_shadow)
)

/**
 * Static method to create instance of Mapbox location component plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createLocationComponentPlugin(): LocationComponentPlugin {
  return LocationComponentPluginImpl()
}