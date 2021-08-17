package com.mapbox.maps.testapp.utils.viewannotations

import android.view.View

/**
 * View annotations always include Android native view [View] displayed on top of the map.
 */
sealed class ViewAnnotationDescriptor(viewAnnotationType: ViewAnnotationType) {
  /**
   * Callout views do not get transformed as map camera changes.
   * Changing camera pitch and bearing will not affect [Callout] view positioning.
   */
  object Callout : ViewAnnotationDescriptor(ViewAnnotationType.CALLOUT)

  /**
   * Native views get transformed as map camera changes.
   * Changing camera pitch and bearing will also affect [Native] view positioning.
   */
  class Native(
    val initialBearing: Float
  ) : ViewAnnotationDescriptor(ViewAnnotationType.NATIVE)
}
