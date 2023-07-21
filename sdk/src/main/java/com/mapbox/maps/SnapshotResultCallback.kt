package com.mapbox.maps

import android.graphics.Bitmap

/**
 * Callback invoked when obtaining the map snapshot.
 */
fun interface SnapshotResultCallback {

  /**
   * If snapshot was successful - [snapshot] will contain the actual [Bitmap] with an actual map snapshot result.
   * If snapshot was not successful - [snapshot] will be NULL and [errorMessage] will contain the error message.
   */
  fun onSnapshotResult(snapshot: Bitmap?, errorMessage: String?)
}