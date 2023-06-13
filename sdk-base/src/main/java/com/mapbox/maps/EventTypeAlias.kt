package com.mapbox.maps

/**
 * Use platform based listener as native provided callbacks.
 */

/**
 * Type alias for [CameraChangedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use CameraChangedCallback instead.",
  replaceWith = ReplaceWith("CameraChangedCallback"),
  level = DeprecationLevel.WARNING
)
typealias OnCameraChangeListener = CameraChangedCallback
/**
 * Type alias for [MapIdleCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use MapIdleCallback instead.",
  replaceWith = ReplaceWith("MapIdleCallback"),
  level = DeprecationLevel.WARNING
)
typealias OnMapIdleListener = MapIdleCallback

/**
 * Type alias for [MapLoadingErrorCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use MapLoadingErrorCallback instead.",
  replaceWith = ReplaceWith("MapLoadingErrorCallback")
)
typealias OnMapLoadErrorListener = MapLoadingErrorCallback

/**
 * Type alias for [MapLoadedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use MapLoadedCallback instead.",
  replaceWith = ReplaceWith("MapLoadedCallback")
)
typealias OnMapLoadedListener = MapLoadedCallback

/**
 * Type alias for [StyleDataLoadedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use StyleDataLoadedCallback instead.",
  replaceWith = ReplaceWith("StyleDataLoadedCallback")
)
typealias OnStyleDataLoadedListener = StyleDataLoadedCallback

/**
 * Type alias for [StyleLoadedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use StyleLoadedCallback instead.",
  replaceWith = ReplaceWith("StyleLoadedCallback")
)
typealias OnStyleLoadedListener = StyleLoadedCallback

/**
 * Type alias for [StyleImageMissingCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use StyleImageMissingCallback instead.",
  replaceWith = ReplaceWith("StyleImageMissingCallback")
)
typealias OnStyleImageMissingListener = StyleImageMissingCallback

/**
 * Type alias for [StyleImageRemoveUnusedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use StyleImageRemoveUnusedCallback instead.",
  replaceWith = ReplaceWith("StyleImageRemoveUnusedCallback")
)
typealias OnStyleImageUnusedListener = StyleImageRemoveUnusedCallback

/**
 * Type alias for [RenderFrameStartedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use RenderFrameStartedCallback instead.",
  replaceWith = ReplaceWith("RenderFrameStartedCallback")
)
typealias OnRenderFrameStartedListener = RenderFrameStartedCallback
/**
 * Type alias for [RenderFrameFinishedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use RenderFrameFinishedCallback instead.",
  replaceWith = ReplaceWith("RenderFrameFinishedCallback")
)
typealias OnRenderFrameFinishedListener = RenderFrameFinishedCallback
/**
 * Type alias for [SourceAddedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use SourceAddedCallback instead.",
  replaceWith = ReplaceWith("SourceAddedCallback")
)
typealias OnSourceAddedListener = SourceAddedCallback

/**
 * Type alias for [SourceDataLoadedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use SourceDataLoadedCallback instead.",
  replaceWith = ReplaceWith("SourceDataLoadedCallback")
)
typealias OnSourceDataLoadedListener = SourceDataLoadedCallback

/**
 * Type alias for [SourceRemovedCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use SourceRemovedCallback instead.",
  replaceWith = ReplaceWith("SourceRemovedCallback")
)
typealias OnSourceRemovedListener = SourceRemovedCallback

/**
 * Type alias for [ResourceRequestCallback]
 */
@Deprecated(
  message = "This listener is deprecated, and will be removed in next major release. use ResourceRequestCallback instead.",
  replaceWith = ReplaceWith("ResourceRequestCallback")
)
typealias OnResourceRequestListener = ResourceRequestCallback