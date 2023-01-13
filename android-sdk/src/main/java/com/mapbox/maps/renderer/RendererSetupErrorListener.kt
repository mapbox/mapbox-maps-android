package com.mapbox.maps.renderer

import com.mapbox.maps.MapView

/**
 * Callback used to get notified when issues occur during renderer preparation.
 * Note that callback will be delivered on main thread.
 */
fun interface RendererSetupErrorListener {
  /**
   * Triggered when error occurs during renderer setup. As the result the map will not be rendered properly.
   *
   * In some cases (e.g. when [RendererError.OutOfMemory] is returned) it may be sufficient try
   * to release some Android resources (e.g. free up some memory) and re-create the [MapView] again.
   *
   * @param error typed renderer error taken from [RendererError] enum
   */
  fun onError(error: RendererError)
}