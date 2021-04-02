package com.mapbox.maps.plugin.overlay

import android.view.View
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapSizePlugin

/**
 * Interface for MapOverlay Container that manage MapOverlays.
 */
@MapboxExperimental
interface MapOverlayPlugin : MapSizePlugin, MapPlugin {
  /**
   * To register a [MapOverlayCoordinatesProvider] instance to the Container
   */
  fun registerMapOverlayCoordinatesProvider(provider: MapOverlayCoordinatesProvider)

  /**
   * Unregister the [MapOverlayCoordinatesProvider].
   */
  fun unregisterMapOverlayCoordinatesProvider()

  /**
   * Register an view as overlay.
   * @param overlay the registered overlay view
   */
  fun registerOverlay(overlay: View)

  /**
   * Register views as overlays
   * @param overlays the registered overlay view
   */
  fun registerOverlays(overlays: List<View>)

  /**
   * Unregister an view.
   * @param overlay the unregistered overlay view
   */
  fun unregisterOverlay(overlay: View)

  /**
   * Unregister views.
   * @param overlays the unregistered overlay views
   */
  fun unregisterOverlays(overlays: List<View>)

  /**
   * Set the margins for the area that displaying import POIs without covered. These margins can let the
   * POIs near the border not by cut or covered.
   * @param marginTop the margin on the top, in pixel
   * @param marginLeft the margin on the left, in pixel
   * @param marginBottom the margin on the bottom, in pixel
   * @param marginRight the margin on the right, in pixel
   */
  fun setDisplayingAreaMargins(marginTop: Int, marginLeft: Int, marginBottom: Int, marginRight: Int)

  /**
   * Reframe MapView to a certain zoom and position to make sure every coordinate will be shown
   * on the MapView and not covered by registered MapOverlays.
   * If no [OnReframeFinished] object is provided, MapView will jump to the new [CameraOptions] directly;
   * if [OnReframeFinished] object is provided, the new [CameraOptions] will be return and users can define their
   * own animation to move the camera.
   *
   * @param onReframeFinished the listener to get the CameraOptions
   */
  fun reframe(onReframeFinished: OnReframeFinished? = null)

  /**
   * Get an EdgeInsets that represent the width and height of overlays
   *
   * @return the EdgeInsets
   */
  fun getEdgeInsets(): EdgeInsets
}