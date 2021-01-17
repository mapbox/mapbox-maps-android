package com.mapbox.maps.plugin.compass

import com.mapbox.maps.plugin.LifecyclePlugin
import com.mapbox.maps.plugin.MapCameraPlugin
import com.mapbox.maps.plugin.ViewPlugin
import com.mapbox.maps.plugin.compass.generated.CompassSettingsInterface

/**
 * Presenter interface for the Compass.
 */
interface CompassPlugin : ViewPlugin, MapCameraPlugin, LifecyclePlugin, CompassSettingsInterface {

  /**
   * Add an OnClick listener to the presenter.
   *
   * @param onClickListener Listener for OnClick events
   */
  fun addCompassClickListener(onClickListener: OnCompassClickListener)

  /**
   * Remove an OnClick listener from the presenter.
   *
   * @param onClickListener Listener for OnClick events
   */
  fun removeCompassClickListener(onClickListener: OnCompassClickListener)

  /**
   * Invoked when the compass view is clicked.
   */
  fun onCompassClicked()
}