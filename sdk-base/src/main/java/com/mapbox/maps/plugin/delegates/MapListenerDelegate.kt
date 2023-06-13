package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.*

/**
 * Definition of a listener manager delegate to manage all kinds of listeners.
 */
interface MapListenerDelegate {
  // Camera Events
  /**
   * Add a listener that's going to be invoked whenever map camera changes.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeCameraChange] instead.",
    replaceWith = ReplaceWith("subscribeCameraChanged(cameraChangedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnCameraChangeListener(cameraChangedCallback: CameraChangedCallback)

  /**
   * Remove the camera change listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeCameraChange] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnCameraChangeListener(cameraChangedCallback: CameraChangedCallback)

  // Map events
  /**
   * Add a listener that's going to be invoked whenever map has entered the idle state.
   *
   * The Map is in the idle state when there are no ongoing transitions and the Map has rendered all
   * available tiles.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapIdle] instead.",
    replaceWith = ReplaceWith("subscribeMapIdle(mapIdleCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnMapIdleListener(mapIdleCallback: MapIdleCallback)

  /**
   * Remove the map idle listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapIdle] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapIdleListener(mapIdleCallback: MapIdleCallback)

  /**
   * Add a listener that's going to be invoked whenever there's a map load error.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapLoadingError] instead.",
    replaceWith = ReplaceWith("subscribeMapLoadingError(mapLoadingErrorCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadErrorListener(mapLoadingErrorCallback: MapLoadingErrorCallback)

  /**
   * Remove the map error listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapLoadingError] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadErrorListener(mapLoadingErrorCallback: MapLoadingErrorCallback)

  /**
   * Add a listener that's going to be invoked whenever the Map's style has been fully loaded, and
   * the Map has rendered all visible tiles.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapLoaded] instead.",
    replaceWith = ReplaceWith("subscribeMapLoaded(mapLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadedListener(mapLoadedCallback: MapLoadedCallback)

  /**
   * Remove the map loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadedListener(mapLoadedCallback: MapLoadedCallback)

  // Render frame events
  /**
   * Add a listener that's going to be invoked whenever the Map started rendering a frame.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeRenderFrameStarted] instead.",
    replaceWith = ReplaceWith("subscribeRenderFrameStarted(renderFrameStartedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameStartedListener(renderFrameStartedCallback: RenderFrameStartedCallback)

  /**
   * Remove the render frame started listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeRenderFrameStarted] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameStartedListener(renderFrameStartedCallback: RenderFrameStartedCallback)

  /**
   * Add a listener that's going to be invoked whenever the Map finished rendering a frame.
   *
   * The render-mode value tells whether the Map has all data ("full") required to render the visible viewport.
   * The needs-repaint value provides information about ongoing transitions that trigger Map repaint.
   * The placement-changed value tells if the symbol placement has been changed in the visible viewport.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeRenderFrameFinished] instead.",
    replaceWith = ReplaceWith("subscribeRenderFrameFinished(renderFrameFinishedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameFinishedListener(renderFrameFinishedCallback: RenderFrameFinishedCallback)

  /**
   * Remove the render frame finished listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeRenderFrameFinished] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameFinishedListener(renderFrameFinishedCallback: RenderFrameFinishedCallback)

  // Source events
  /**
   * Add a listener that's going to be invoked whenever a source has been added with StyleManager#addStyleSource
   * runtime API.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceAdded] instead.",
    replaceWith = ReplaceWith("subscribeSourceAdded(sourceAddedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceAddedListener(sourceAddedCallback: SourceAddedCallback)

  /**
   * Remove the source added listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceAdded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceAddedListener(sourceAddedCallback: SourceAddedCallback)

  /**
   * Add a listener that's going to be invoked whenever the source data has been loaded.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceDataLoaded] instead.",
    replaceWith = ReplaceWith("subscribeSourceDataLoaded(sourceDataLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceDataLoadedListener(sourceDataLoadedCallback: SourceDataLoadedCallback)

  /**
   * Remove the source data loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceDataLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceDataLoadedListener(sourceDataLoadedCallback: SourceDataLoadedCallback)

  /**
   * Add a listener that's going to be invoked whenever a source has been removed with StyleManager#removeStyleSource
   * runtime API.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceRemoved] instead.",
    replaceWith = ReplaceWith("subscribeSourceRemoved(sourceRemovedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceRemovedListener(sourceRemovedCallback: SourceRemovedCallback)

  /**
   * Remove the source removed listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceRemoved] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceRemovedListener(sourceRemovedCallback: SourceRemovedCallback)

  // Style events
  /**
   * Add a listener that's going to be invoked whenever the requested style data been loaded.
   * The 'type' property defines what kind of style data has been loaded.
   *
   * This event may be useful when application needs to modify style layers or sources and add or remove sources
   * before style is fully loaded.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleLoaded] instead.",
    replaceWith = ReplaceWith("subscribeStyleLoaded(styleDataLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleDataLoadedListener(styleDataLoadedCallback: StyleDataLoadedCallback)

  /**
   * Remove the style data loaded listener
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleDataLoadedListener(styleDataLoadedCallback: StyleDataLoadedCallback)

  /**
   * Add a listener that's going to be invoked whenever the requested style has been fully loaded,
   * including the style specified sprite and sources.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleDataLoaded] instead.",
    replaceWith = ReplaceWith("subscribeStyleDataLoaded(styleLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleLoadedListener(styleLoadedCallback: StyleLoadedCallback)

  /**
   * Remove the style loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleDataLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleLoadedListener(styleLoadedCallback: StyleLoadedCallback)

  /**
   * Add a listener that's going to be invoked whenever a style has a missing image.
   *
   * This event is emitted when the Map renders visible tiles and one of the required images is
   * missing in the sprite sheet.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleImageMissing] instead.",
    replaceWith = ReplaceWith("subscribeStyleImageMissing(styleImageMissingCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageMissingListener(styleImageMissingCallback: StyleImageMissingCallback)

  /**
   * Remove the style image missing listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleImageMissing] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageMissingListener(styleImageMissingCallback: StyleImageMissingCallback)

  /**
   * Add a listener that's going to be invoked whenever an image added to the Style is no longer
   * needed and can be removed using StyleManager#removeStyleImage method.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleImageUnused] instead.",
    replaceWith = ReplaceWith("subscribeStyleImageUnused(styleImageRemoveUnusedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageUnusedListener(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback)

  /**
   * Remove the style image unused listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleImageUnused] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageUnusedListener(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback)
}