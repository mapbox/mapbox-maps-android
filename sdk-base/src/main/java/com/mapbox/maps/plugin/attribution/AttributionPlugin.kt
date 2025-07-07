package com.mapbox.maps.plugin.attribution

import com.mapbox.maps.plugin.LifecyclePlugin
import com.mapbox.maps.plugin.ViewPlugin
import com.mapbox.maps.plugin.attribution.generated.AttributionSettingsInterface
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

/**
 * Presenter interface for the attribution.
 */
interface AttributionPlugin : ViewPlugin, LifecyclePlugin, AttributionSettingsInterface {

  /**
   * Set a custom AttributionDialogManager that is invoked when the attribution view is clicked.
   */
  fun setCustomAttributionDialogManager(dialogManager: AttributionDialogManager)

  /**
   * Set the content description for the attribution view.
   *
   * @param contentDescription the content description text
   */
  fun setContentDescription(contentDescription: CharSequence?) {
    // Default empty implementation for backward compatibility
  }

  /**
   * Get the instance of MapAttributionDelegate
   */
  fun getMapAttributionDelegate(): MapAttributionDelegate
}