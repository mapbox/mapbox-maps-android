package com.mapbox.maps.plugin.viewannotation

import android.view.View
import com.mapbox.maps.CameraState

/**
 * View annotations always include Android native view [View] displayed on top of the map.
 */
sealed class ViewAnnotationDescriptor(
  val initialCamera: CameraState
) {
  /**
   * Callout views do not get transformed as map camera changes.
   * Changing camera pitch and bearing will not affect [Callout] view positioning.
   */
  class Callout(initialCamera: CameraState) : ViewAnnotationDescriptor(initialCamera)

  /**
   * Native views get transformed as map camera changes.
   * Changing camera pitch and bearing will also affect [Native] view positioning.
   */
  class Native(initialCamera: CameraState) : ViewAnnotationDescriptor(initialCamera)
}
