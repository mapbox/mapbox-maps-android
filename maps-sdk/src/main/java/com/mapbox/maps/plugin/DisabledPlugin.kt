package com.mapbox.maps.plugin

import androidx.annotation.RestrictTo

/**
 * A placeholder [MapPlugin] used to intentionally disable another plugin while keeping its id
 * registered on the map. A host that replaces a plugin with its own implementation can register
 * `Plugin.Custom(id, DisabledPlugin(..))` in place of the real plugin.
 *
 * Accessing the corresponding plugin getter (e.g. `mapView.compass`) then throws a
 * [PluginDisabledException] carrying [message] instead of returning a value that would fail later.
 *
 * This is a Mapbox-internal mechanism and is not intended for use by third-party developers.
 *
 * @param message actionable description of why the plugin is unavailable.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class DisabledPlugin(val message: String) : MapPlugin