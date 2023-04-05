package com.mapbox.maps.plugin.locationcomponent

/**
 * Callback used in PermissionsManager
 */
interface PermissionsListener {
  /**
   * Called when an explanation to the user needs to be shown
   */
  fun onExplanationNeeded(permissionsToExplain: List<String>)

  /**
   * Called when permissions have been granted or denied
   */
  fun onPermissionResult(granted: Boolean)
}