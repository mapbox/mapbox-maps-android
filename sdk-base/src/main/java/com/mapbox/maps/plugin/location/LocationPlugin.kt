package com.mapbox.maps.plugin.location

import com.mapbox.maps.plugin.LifecyclePlugin
import com.mapbox.maps.plugin.MapCameraPlugin
import com.mapbox.maps.plugin.MapStyleObserverPlugin

/**
 * Define the interfaces for the Location plugin.
 */
interface LocationPlugin : MapStyleObserverPlugin, LifecyclePlugin, MapCameraPlugin