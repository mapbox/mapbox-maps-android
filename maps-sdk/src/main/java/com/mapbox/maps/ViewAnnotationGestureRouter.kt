package com.mapbox.maps

import android.view.MotionEvent
import com.mapbox.maps.viewannotation.ViewAnnotationManagerImpl

/**
 * Decides, for a touch gesture, whether [mapView] pans or whether a view annotation owns the gesture.
 *
 * The decision is by scroll, which is signaled via [MapView.requestDisallowInterceptTouchEvent].
 * A static annotation never claims, so a drag across it still pans.
 *
 * The child gets one decision move on which the drag crosses touch slop, which is where a
 * scrollable decides it is scrolling. If it claims then, the map keeps yielding and the content
 * scrolls. Otherwise, the map intercepts the next move and pans from the original press point.
 */
internal class ViewAnnotationGestureRouter(
  private val mapView: MapView,
) {

  private var disallowIntercept = false
  private var downOnViewAnnotation = false
  private var decisionMoveDispatched = false

  fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    this.disallowIntercept = disallowIntercept
  }

  fun onDown(event: MotionEvent) {
    disallowIntercept = false
    decisionMoveDispatched = false
    downOnViewAnnotation = isDownOnViewAnnotation(event)
  }

  fun shouldInterceptMove(crossedSlop: Boolean): Boolean {
    // The child claimed the gesture
    if (disallowIntercept) {
      return false
    }
    if (!downOnViewAnnotation) {
      return crossedSlop
    }
    // not a scroll yet, let the child keep the events.
    if (!crossedSlop) {
      return false
    }
    if (!decisionMoveDispatched) {
      decisionMoveDispatched = true
      return false
    }
    // The child had the decision move and never claimed
    return true
  }

  private fun isDownOnViewAnnotation(event: MotionEvent): Boolean {
    if (!mapView.isViewAnnotationManagerInitialized) {
      return false
    }
    return (mapView.viewAnnotationManager as ViewAnnotationManagerImpl)
      .viewAnnotationViewAt(event.x, event.y) != null
  }
}