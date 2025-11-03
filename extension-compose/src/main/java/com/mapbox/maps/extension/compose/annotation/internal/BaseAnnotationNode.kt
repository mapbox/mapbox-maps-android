package com.mapbox.maps.extension.compose.annotation.internal

import androidx.annotation.CallSuper
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.internal.LayerPositionAwareNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.internal.StyleLifecycleAwareNode
import com.mapbox.maps.extension.compose.style.internal.StyleSlotNode
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import kotlinx.coroutines.CoroutineScope

internal abstract class BaseAnnotationNode(
  mapboxStyleManager: MapboxStyleManager,
  coroutineScope: CoroutineScope,
) : LayerPositionAwareNode, StyleLifecycleAwareNode(mapboxStyleManager, coroutineScope) {

  override var isAttached: Boolean = false

  @CallSuper
  override fun onAttached(parent: MapNode) {
    logD(TAG, "[$this] onAttached() called with: parent = $parent")
    super.onAttached(parent)
    dispatchWhenReady { mapboxStyleManager ->
      // For annotations nodes within a slot we need to set the slot property on all the layers
      if (parent is StyleSlotNode) {
        getLayerIds().forEach { layerId ->
          mapboxStyleManager.setStyleLayerProperty(layerId, "slot", Value(parent.slotName)).onError {
            logE(TAG, "Failed to set slot property for layer $layerId: $it")
          }
        }
      }
      mapboxStyleManager.repositionCurrentNode(parent)
      isAttached = true
    }
  }

  @CallSuper
  override fun onMoved(parent: MapNode) {
    dispatchWhenReady {
      it.repositionCurrentNode(parent)
    }
  }

  override fun onClear() {
    cleanUp()
  }

  override fun onRemoved(parent: MapNode) {
    cleanUp()
  }

  protected abstract fun cleanUp()

  private companion object {
    private const val TAG = "BaseAnnotationNode"
  }
}