package com.mapbox.maps.extension.compose.annotation.internal

import androidx.annotation.CallSuper
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.internal.LayerPositionAwareNode
import com.mapbox.maps.extension.compose.internal.MapNode

internal abstract class BaseAnnotationNode(private val mapboxStyleManager: MapboxStyleManager) : LayerPositionAwareNode, MapNode() {

  @CallSuper
  override fun onAttached(parent: MapNode) {
    mapboxStyleManager.moveStyleLayer(getLayerId(), getRelativePositionInfo(parent))
  }

  @CallSuper
  override fun onMoved(parent: MapNode, from: Int, to: Int) {
    mapboxStyleManager.moveStyleLayer(getLayerId(), getRelativePositionInfo(parent))
  }
  override fun onClear() {
    cleanUp()
  }

  override fun onRemoved(parent: MapNode) {
    cleanUp()
  }

  protected abstract fun cleanUp()
}