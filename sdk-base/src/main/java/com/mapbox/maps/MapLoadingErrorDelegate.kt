package com.mapbox.maps

import androidx.annotation.RestrictTo

/**
 * For internal usage.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
fun interface MapLoadingErrorDelegate {

  /**
   * Send [MapLoadingError].
   */
  fun sendMapLoadingError(error: MapLoadingError)
}