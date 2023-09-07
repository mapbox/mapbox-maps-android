package com.mapbox.maps.extension.compose

import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.MapIdleCallback
import com.mapbox.maps.MapLoadedCallback
import com.mapbox.maps.MapLoadingErrorCallback
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.RenderFrameFinishedCallback
import com.mapbox.maps.RenderFrameStartedCallback
import com.mapbox.maps.ResourceRequestCallback
import com.mapbox.maps.SourceAddedCallback
import com.mapbox.maps.SourceDataLoadedCallback
import com.mapbox.maps.SourceRemovedCallback
import com.mapbox.maps.StyleDataLoadedCallback
import com.mapbox.maps.StyleImageMissingCallback
import com.mapbox.maps.StyleImageRemoveUnusedCallback
import com.mapbox.maps.StyleLoadedCallback

/**
 * Entry point for adding a Mapbox Map events to the Jetpack Compose UI.
 *
 * @param onMapLoaded Callback for `MapLoaded` event.
 * @param onMapIdle Callback for `MapIdle` event.
 * @param onMapLoadingError Callback for `MapLoadingError` event.
 * @param onStyleLoaded Callback for `StyleLoaded` event.
 * @param onStyleDataLoaded Callback for `StyleDataLoaded` event.
 * @param onSourceDataLoaded Callback for `SourceDataLoaded` event.
 * @param onSourceAdded Callback for `SourceAdded` event.
 * @param onSourceRemoved Callback for `SourceRemoved` event.
 * @param onStyleImageMissing Callback for `StyleImageMissing` event.
 * @param onStyleImageRemoveUnused Callback for `StyleImageRemoveUnused` event.
 * @param onCameraChanged Callback for `CameraChanged` event.
 * @param onRenderFrameStarted Callback for `RenderFrameStarted` event.
 * @param onRenderFrameFinished Callback for `RenderFrameFinished` event.
 * @param onResourceRequest Callback for `ResourceRequest` event.
 */
@MapboxExperimental
public class MapEvents(
  public val onMapLoaded: MapLoadedCallback? = null,
  public val onMapIdle: MapIdleCallback? = null,
  public val onMapLoadingError: MapLoadingErrorCallback? = null,
  public val onStyleLoaded: StyleLoadedCallback? = null,
  public val onStyleDataLoaded: StyleDataLoadedCallback? = null,
  public val onSourceDataLoaded: SourceDataLoadedCallback? = null,
  public val onSourceAdded: SourceAddedCallback? = null,
  public val onSourceRemoved: SourceRemovedCallback? = null,
  public val onStyleImageMissing: StyleImageMissingCallback? = null,
  public val onStyleImageRemoveUnused: StyleImageRemoveUnusedCallback? = null,
  public val onCameraChanged: CameraChangedCallback? = null,
  public val onRenderFrameStarted: RenderFrameStartedCallback? = null,
  public val onRenderFrameFinished: RenderFrameFinishedCallback? = null,
  public val onResourceRequest: ResourceRequestCallback? = null,
)