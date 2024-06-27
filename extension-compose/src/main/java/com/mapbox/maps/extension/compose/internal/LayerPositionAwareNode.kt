package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import com.mapbox.maps.logW

/**
 * Internal interface for layers and annotations to handle layer position in the node tree.
 */
internal interface LayerPositionAwareNode {
  /**
   * Get the layer id of the node.
   */
  fun getLayerIds(): List<String>

  /**
   * Reposition all the layers within the current node according to [getRelativePositionInfo].
   */
  fun MapboxStyleManager.repositionCurrentNode(parent: MapNode) {
    repositionLayersForCurrentNode(getRelativePositionInfo(parent))
  }

  /**
   * Reposition all the layers associated with the current Node to the given [layerPosition].
   */
  private fun MapboxStyleManager.repositionLayersForCurrentNode(layerPosition: LayerPosition?) {
    val associatedLayers = getLayerIds()
    if (associatedLayers.isEmpty()) {
      logW(
        "LayerPositionAwareNode",
        "Cannot reposition layer for $this, associatedLayers is empty."
      )
    }
    // Move the last layer according to the given layer position
    moveStyleLayer(associatedLayers.last(), layerPosition)

    if (associatedLayers.size > 1) {
      // move the rest of layers below the last layer in the associated layer stack
      for (index in associatedLayers.size - 2 downTo 0) {
        moveStyleLayer(
          associatedLayers[index],
          LayerPosition(
            /* above = */ null,
            /* below = */ associatedLayers[index + 1],
            /* at = */ null
          )
        )
      }
    }
  }

  /**
   * Search the children of the parent node, and see if there's any other [LayerPositionAwareNode]
   * which is on top of the current node, construct the [LayerPosition] with below layer id, so the
   * current layer is inserted at the correct position and consistent with the node tree.
   *
   * If there's nothing above current node, take the [targetLayerPosition] as it's position.
   *
   * @param parent the parent node of the node to be positioned.
   * @param targetLayerPosition the optional target layer position, useful in case of [StyleLayerPositionNode].
   */
  fun getRelativePositionInfo(
    parent: MapNode,
    targetLayerPosition: LayerPosition? = null
  ): LayerPosition? {
    return with(parent.children.filterIsInstance<LayerPositionAwareNode>()) {
      elementAtOrNull(indexOf(this@LayerPositionAwareNode) + 1)?.getLayerIds()?.firstOrNull()
        ?.let { belowLayerId ->
          LayerPosition(
            /* above = */ null,
            /* below = */ belowLayerId,
            /* at = */ null
          )
        } ?: targetLayerPosition
    }
  }
}