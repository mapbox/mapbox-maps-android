package com.mapbox.maps.plugin.viewannotation.core

fun interface CollisionDetectorCallback {

  fun onCollisionDetected(
    visibleViewsAfterCollisionDetection: List<String>,
    visibleViewsAfterFeatureCollisionDetection: List<String>
  )
}