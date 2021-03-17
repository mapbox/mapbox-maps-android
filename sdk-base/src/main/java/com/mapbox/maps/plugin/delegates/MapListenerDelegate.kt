package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.plugin.delegates.listeners.*

/**
 * Definition of a listener manager delegate to manage all kinds of listeners.
 */
interface MapListenerDelegate {
  // Camera Events
  /**
   * Add a listener that's going to be invoked whenever map camera changes.
   */
  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener)

  /**
   * Remove the camera change listener.
   */
  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener)

  // Map events
  /**
   * Add a listener that's going to be invoked whenever map has entered the idle state.
   *
   * The Map is in the idle state when there are no ongoing transitions and the Map has rendered all
   * available tiles.
   */
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener)

  /**
   * Remove the map idle listener.
   */
  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener)

  /**
   * Add a listener that's going to be invoked whenever there's a map load error.
   */
  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener)

  /**
   * Remove the map error listener.
   */
  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener)

  /**
   * Add a listener that's going to be invoked whenever the Map's style has been fully loaded, and
   * the Map has rendered all visible tiles.
   */
  fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener)

  /**
   * Remove the map loaded listener.
   */
  fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener)

  // Render frame events
  /**
   * Add a listener that's going to be invoked whenever the Map started rendering a frame.
   */
  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener)

  /**
   * Remove the render frame started listener.
   */
  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener)

  /**
   * Add a listener that's going to be invoked whenever the Map finished rendering a frame.
   *
   * The render-mode value tells whether the Map has all data ("full") required to render the visible viewport.
   * The needs-repaint value provides information about ongoing transitions that trigger Map repaint.
   * The placement-changed value tells if the symbol placement has been changed in the visible viewport.
   */
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener)

  /**
   * Remove the render frame finished listener.
   */
  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener)

  // Source events
  /**
   * Add a listener that's going to be invoked whenever a source has been added with StyleManager#addStyleSource
   * runtime API.
   */
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener)

  /**
   * Remove the source added listener.
   */
  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener)

  /**
   * Add a listener that's going to be invoked whenever the source data has been loaded.
   */
  fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener)

  /**
   * Remove the source data loaded listener.
   */
  fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener)

  /**
   * Add a listener that's going to be invoked whenever a source has been removed with StyleManager#removeStyleSource
   * runtime API.
   */
  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener)

  /**
   * Remove the source removed listener.
   */
  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener)

  // Style events
  /**
   * Add a listener that's going to be invoked whenever the requested style data been loaded.
   * The 'type' property defines what kind of style data has been loaded.
   *
   * This event may be useful when application needs to modify style layers or sources and add or remove sources
   * before style is fully loaded.
   */
  fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener)

  /**
   * Remove the style data loaded listener
   */
  fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener)

  /**
   * Add a listener that's going to be invoked whenever the requested style has been fully loaded,
   * including the style specified sprite and sources.
   */
  fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener)

  /**
   * Remove the style loaded listener.
   */
  fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener)

  /**
   * Add a listener that's going to be invoked whenever a style has a missing image.
   *
   * This event is emitted when the Map renders visible tiles and one of the required images is
   * missing in the sprite sheet.
   */
  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener)

  /**
   * Remove the style image missing listener.
   */
  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener)

  /**
   * Add a listener that's going to be invoked whenever an image added to the Style is no longer
   * needed and can be removed using StyleManager#removeStyleImage method.
   */
  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener)

  /**
   * Remove the style image unused listener.
   */
  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener)
}