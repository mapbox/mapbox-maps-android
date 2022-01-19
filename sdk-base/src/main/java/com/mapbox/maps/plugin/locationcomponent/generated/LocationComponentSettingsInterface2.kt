// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated


/**
 * Interface that defines the new public settings interface for LocationComponentPlugin.
 */
interface LocationComponentSettingsInterface2 : LocationComponentSettingsInterface {
  fun getNewSettings(): LocationComponentNewSettings

  fun updateNewSettings(block: LocationComponentNewSettings.() -> Unit)

  var newSettings: Boolean
}

// End of generated file.