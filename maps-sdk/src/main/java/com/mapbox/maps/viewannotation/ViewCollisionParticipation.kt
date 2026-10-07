package com.mapbox.maps.viewannotation

import android.view.View
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.R

/**
 * Marks this view as a collision box for the enclosing view annotation.
 *
 * When at least one subview is marked, only marked subviews' frames are used
 * as collision boxes. When none are marked, the full annotation bounds are used.
 *
 * A view removed from the hierarchy or with visibility `GONE` stops colliding.
 * A view with visibility `INVISIBLE` keeps its layout and still collides.
 *
 * Boxes are collected during a layout pass. Moves that do not request one are not picked up:
 * `translationX` and `translationY`, `setX` and `setY`, `offsetLeftAndRight`, and property
 * animations on them. Scale and rotation are not reflected either, because the box is taken from
 * the layout position. Call `requestLayout()` on the view after such a change if the collision box
 * has to follow it.
 *
 * Must be set on the main thread: the setter requests a layout pass on the view.
 */
@MapboxExperimental
var View.mbxViewAnnotationCollisionBox: Boolean
  get() = getTag(R.id.collisionBox) as? Boolean ?: false
  set(value) {
    if (mbxViewAnnotationCollisionBox == value) return
    setTag(R.id.collisionBox, value)
    // The tag itself is not observable; a layout pass re-collects the boxes.
    requestLayout()
  }