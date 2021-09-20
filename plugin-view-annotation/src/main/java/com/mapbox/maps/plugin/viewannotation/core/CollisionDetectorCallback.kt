package com.mapbox.maps.plugin.viewannotation.core

fun interface CollisionDetectorCallback {

  fun onCollisionDetected(visibleViewList: List<String>)
}