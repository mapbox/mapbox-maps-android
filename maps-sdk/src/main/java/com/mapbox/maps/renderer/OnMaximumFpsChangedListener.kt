package com.mapbox.maps.renderer

import androidx.annotation.RestrictTo

/**
 * Callback invoked when [com.mapbox.maps.MapView.setMaximumFps] or
 * [com.mapbox.maps.MapView.clearMaximumFps] is called.
 *
 * Fires synchronously on the Mapbox thread.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
fun interface OnMaximumFpsChangedListener {
    /** @param maximumFps the new configured max, or `null` if cleared. */
    fun onMaximumFpsChanged(maximumFps: Int?)
}