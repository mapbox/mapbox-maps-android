package com.mapbox.maps.extension.compose.annotation.internal

import com.mapbox.maps.extension.compose.internal.MapNode

internal abstract class BaseAnnotationNode : MapNode() {
  override fun onClear() {
    cleanUp()
  }

  override fun onRemoved(parent: MapNode) {
    cleanUp()
  }

  protected abstract fun cleanUp()
}