package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.LayerPosition

/**
 * Internal interface for layers and annotations to handle layer position in the node tree.
 */
internal interface LayerPositionAwareNode {
  /**
   * Get the layer id of the node.
   */
  fun getLayerId(): String

  /**
   * Search the children of the parent node, and see if there's any other LayerPositionAwareNode
   * which is on top of the current node, construct the [LayerPosition] with below layer id, so the
   * current layer is inserted at the correct position and consistent with the node tree.
   */
  fun getRelativePositionInfo(parent: MapNode): LayerPosition {
    return LayerPosition(
      /* above = */ null,
      /* below = */ with(parent.children.filterIsInstance<LayerPositionAwareNode>()) {
        elementAtOrNull(indexOf(this@LayerPositionAwareNode) + 1)?.getLayerId()
      },
      /* at = */ null
    )
  }
}