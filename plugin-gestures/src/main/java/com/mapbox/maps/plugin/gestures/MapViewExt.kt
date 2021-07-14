@file:JvmName("MapboxViewUtils")
@file:JvmMultifileClass

package com.mapbox.maps.plugin.gestures

import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Gestures plugin instance.
 */
val MapPluginProviderDelegate.gestures: GesturesPlugin
  get() = this.getPlugin(GesturesPluginImpl::class.java)!!