package com.mapbox.maps

/**
 * Interface for getting all style related events for snapshotter.
 */
interface SnapshotStyleListener {

  /**
   * Notifies the client when style loading completed, not including the style specified sprite and sources.
   *
   * @param style style that was loaded for given snapshotter.
   */
  fun onDidFinishLoadingStyle(style: Style)

  /**
   * Notifies the client when style loading completed, including the style specified sprite and sources.
   *
   * @param style style that was loaded for given snapshotter.
   */
  fun onDidFullyLoadStyle(style: Style) { }

  /**
   * Notifies the client about style load errors.
   *
   * @param message Error message associated with the error.
   */
  fun onDidFailLoadingStyle(message: String) { }

  /**
   * Notifies the client about a missing style image.
   *
   * @param imageId The id of the image that is missing.
   */
  fun onStyleImageMissing(imageId: String) { }
}