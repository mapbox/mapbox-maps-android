package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.plugin.delegates.listeners.*

/**
 * Definition of a listener manager delegate to manage all kinds of listeners.
 */
interface MapListenerDelegate {

  /**
   * Add a listener that's going to be invoked whenever map state changes.
   */
  fun addOnMapChangedListener(onMapChangeListener: OnMapChangedListener)

  /**
   * Remove the map change listener.
   */
  fun removeOnMapChangedListener(onMapChangedListener: OnMapChangedListener)

  /**
   * Add a listener that's going to be invoked whenever a map load error occurs.
   */
  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener)

  /**
   * Remove the map load error listener.
   */
  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener)

  /**
   * Add a listener that's going to be invoked whenever frame finished rendering.
   */
  fun addOnDidFinishRenderingFrameListener(onDidFinishRenderingFrameListener: OnDidFinishRenderingFrameListener)

  /**
   * Remove the finish rendering frame listener.
   */
  fun removeOnDidFinishRenderingFrameListener(onDidFinishRenderingFrameListener: OnDidFinishRenderingFrameListener)

  /**
   * Add a listener that's going to be invoked whenever the camera position changes.
   */
  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener)

  /**
   * Remove the camera change listener.
   */
  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener)

  /**
   * Add a listener that's going to be invoked whenever a source changes.
   */
  fun addOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener)

  /**
   * Remove the source change listener.
   */
  fun removeOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener)

  /**
   * Add a listener that's going to be invoked whenever style image state changes.
   */
  fun addOnStyleImageChangeListener(onStyleImageChangeListener: OnStyleImageChangeListener)

  /**
   * Remove the style image change listener.
   */
  fun removeOnStyleImageChangeListener(onStyleImageChangeListener: OnStyleImageChangeListener)

  /**
   * Add a listener that's going to be invoked whenever the map finished rendering.
   */
  fun addOnDidFinishRenderingMapListener(onDidFinishRenderingMapListener: OnDidFinishRenderingMapListener)

  /**
   * Remove the did finish rendering map listener.
   */
  fun removeOnDidFinishRenderingMapListener(onDidFinishRenderingMapListener: OnDidFinishRenderingMapListener)
}