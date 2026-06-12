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
 * Only has effect when [com.mapbox.maps.ViewAnnotationOptions.enableSymbolLayerCollision] is true
 * on the enclosing annotation.
 */
@MapboxExperimental
var View.mbxCollisionBox: Boolean
  get() = getTag(R.id.collisionBox) as? Boolean ?: false
  set(value) {
      setTag(R.id.collisionBox, value)
  }