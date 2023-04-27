package com.mapbox.maps

/**
 * Interface for getting snapshot when it's finished.
 */
fun interface SnapshotCreatedListener {

  /**
   * Invoked when snapshot finished despite successful or not.
   *
   * @param snapshot An image snapshot of a map rendered by MapSnapshotter.
   * If [snapshot] = NULL it means snapshot was not successful and error message may be found in log.
   */
  fun onSnapshotResult(snapshot: MapSnapshotResult?)
}