package com.mapbox.maps.plugin.delegates

import com.mapbox.common.Cancelable
import com.mapbox.maps.*
import com.mapbox.maps.plugin.delegates.listeners.*

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
  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener)

  /**
   * Remove the camera change listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeCameraChange] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener)

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
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener)

  /**
   * Remove the map idle listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapIdle] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener)

  /**
   * Add a listener that's going to be invoked whenever there's a map load error.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapLoadingError] instead.",
    replaceWith = ReplaceWith("subscribeMapLoadingError(mapLoadingErrorCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener)

  /**
   * Remove the map error listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapLoadingError] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener)

  /**
   * Add a listener that's going to be invoked whenever the Map's style has been fully loaded, and
   * the Map has rendered all visible tiles.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeMapLoaded] instead.",
    replaceWith = ReplaceWith("subscribeMapLoaded(mapLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener)

  /**
   * Remove the map loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeMapLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener)

  // Render frame events
  /**
   * Add a listener that's going to be invoked whenever the Map started rendering a frame.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeRenderFrameStarted] instead.",
    replaceWith = ReplaceWith("subscribeRenderFrameStarted(renderFrameStartedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener)

  /**
   * Remove the render frame started listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeRenderFrameStarted] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener)

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
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener)

  /**
   * Remove the render frame finished listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeRenderFrameFinished] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener)

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
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener)

  /**
   * Remove the source added listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceAdded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener)

  /**
   * Add a listener that's going to be invoked whenever the source data has been loaded.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceDataLoaded] instead.",
    replaceWith = ReplaceWith("subscribeSourceDataLoaded(sourceDataLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener)

  /**
   * Remove the source data loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceDataLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener)

  /**
   * Add a listener that's going to be invoked whenever a source has been removed with StyleManager#removeStyleSource
   * runtime API.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeSourceRemoved] instead.",
    replaceWith = ReplaceWith("subscribeSourceRemoved(sourceRemovedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener)

  /**
   * Remove the source removed listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeSourceRemoved] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener)

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
  fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener)

  /**
   * Remove the style data loaded listener
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener)

  /**
   * Add a listener that's going to be invoked whenever the requested style has been fully loaded,
   * including the style specified sprite and sources.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleDataLoaded] instead.",
    replaceWith = ReplaceWith("subscribeStyleDataLoaded(styleLoadedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener)

  /**
   * Remove the style loaded listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleDataLoaded] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener)

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
  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener)

  /**
   * Remove the style image missing listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleImageMissing] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener)

  /**
   * Add a listener that's going to be invoked whenever an image added to the Style is no longer
   * needed and can be removed using StyleManager#removeStyleImage method.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use [subscribeStyleImageUnused] instead.",
    replaceWith = ReplaceWith("subscribeStyleImageUnused(styleImageRemoveUnusedCallback)"),
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener)

  /**
   * Remove the style image unused listener.
   */
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release. use cancelable returned from [subscribeStyleImageUnused] to remove the listener.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener)

  /**
   * Subscribes to `CameraChanged` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param cameraChangedCallback
   */
  fun subscribeCameraChanged(cameraChangedCallback: CameraChangedCallback): Cancelable

  /**
   * Subscribes to `MapLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param mapLoadedCallback
   */
  fun subscribeMapLoaded(mapLoadedCallback: MapLoadedCallback): Cancelable

  /**
   * Subscribes to `MapIdle` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param mapIdleCallback
   */
  fun subscribeMapIdle(mapIdleCallback: MapIdleCallback): Cancelable

  /**
   * Subscribes to `MapLoadingError` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param mapLoadingErrorCallback
   */
  fun subscribeMapLoadingError(mapLoadingErrorCallback: MapLoadingErrorCallback): Cancelable

  /**
   * Subscribes to `StyleLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleLoadedCallback
   */
  fun subscribeStyleLoaded(styleLoadedCallback: StyleLoadedCallback): Cancelable

  /**
   * Subscribes to `StyleDataLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleDataLoadedCallback
   */
  fun subscribeStyleDataLoaded(styleDataLoadedCallback: StyleDataLoadedCallback): Cancelable

  /**
   * Subscribes to `SourceDataLoaded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param sourceDataLoadedCallback
   */
  fun subscribeSourceDataLoaded(sourceDataLoadedCallback: SourceDataLoadedCallback): Cancelable

  /**
   * Subscribes to `SourceAdded` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param sourceAddedCallback
   */
  fun subscribeSourceAdded(sourceAddedCallback: SourceAddedCallback): Cancelable

  /**
   * Subscribes to `SourceRemoved` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param sourceRemovedCallback
   */
  fun subscribeSourceRemoved(sourceRemovedCallback: SourceRemovedCallback): Cancelable

  /**
   * Subscribes to `StyleImageMissing` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleImageMissingCallback
   */
  fun subscribeStyleImageMissing(styleImageMissingCallback: StyleImageMissingCallback): Cancelable

  /**
   * Subscribes to `StyleImageRemoveUnused` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param styleImageRemoveUnusedCallback
   */
  fun subscribeStyleImageUnused(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback): Cancelable

  /**
   * Subscribes to `RenderFrameStarted` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param renderFrameStartedCallback
   */
  fun subscribeRenderFrameStarted(renderFrameStartedCallback: RenderFrameStartedCallback): Cancelable

  /**
   * Subscribes to `RenderFrameFinished` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param renderFrameFinishedCallback
   */
  fun subscribeRenderFrameFinished(renderFrameFinishedCallback: RenderFrameFinishedCallback): Cancelable

  /**
   * Subscribes to `ResourceRequest` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param resourceRequestCallback
   */
  fun subscribeResourceRequest(resourceRequestCallback: ResourceRequestCallback): Cancelable

  /**
   * Subscribes to an experimental `GenericEvent` event.
   *
   * @return cancellable object to unsubscribe from the event.
   *
   * @param eventName
   * @param genericEventCallback
   */
  @MapboxExperimental
  fun subscribeGenericEvent(eventName: String, genericEventCallback: GenericEventCallback): Cancelable
}