package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW

/**
 * Internal interface for layers and annotations to handle layer position in the node tree.
 */
internal interface LayerPositionAwareNode {
  var isAttached: Boolean
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
    logD(
      TAG,
      "[${this@LayerPositionAwareNode}] repositionLayersForCurrentNode() called with: layerPosition = $layerPosition"
    )
    val associatedLayers = getLayerIds()
    if (associatedLayers.isEmpty()) {
      logW(
        TAG,
        "Cannot reposition layer for $this, associatedLayers is empty."
      )
    }
    // Move the last layer according to the given layer position
    moveStyleLayer(associatedLayers.last(), layerPosition).onError { error ->
      logW(
        TAG,
        "Failed to move layer $associatedLayers.last() to $layerPosition: $error"
      )
      logE(TAG, "Available layers in style:")
      styleManager.styleLayers.forEach { logE(TAG, "\t ${it.id}") }
    }

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
   * Compose inserts node in the order of the position in the block.
   * For example, given the following compose block:
   * ```
   * aboveLayer("buildings") {
   *  layerPositionAwareNode_1
   *  layerPositionAwareNode_2
   *  layerPositionAwareNode_3
   * }
   * ```
   * Compose will insert the nodes in the following order:
   *   layerPositionAwareNode_1, layerPositionAwareNode_2, layerPositionAwareNode_3.
   *
   * This method is used to find the previous [LayerPositionAwareNode] in the children's list of the
   * parent node, so that this node can be inserted above it.
   * It is important to find the previous node because the layers are added in the order of the
   * children list. Otherwise, we might end up with layer not found errors (e.g. in *AnnotationNodes).
   *
   * Meaning that the relative position of the layers should be:
   * ```
   * layerPositionAwareNode_1 above "buildings"
   * layerPositionAwareNode_2 above layerPositionAwareNode_1
   * layerPositionAwareNode_3 above layerPositionAwareNode_2
   * ```
   *
   * @param parent the parent node of the node to be positioned.
   */
  fun getRelativePositionInfo(parent: MapNode): LayerPosition? =
    parent.children.filterIsInstance<LayerPositionAwareNode>().let { children ->
      val me = this@LayerPositionAwareNode
      // The compose nodes are inserted to the map engine in the order of the children list.
      // Meaning that the node that is before this one will have its layer added when we add this one.
      val indexOfMe = children.indexOf(me)
      val anchorLayerNode = children.elementAtOrNull(indexOfMe - 1)
      return if (anchorLayerNode == null) {
        when (parent) {
          // If the parent node is a Style one then we should use its layer position as the anchor.
          is StyleLayerPositionNode -> parent.layerPosition

          // For other parent nodes we use the next node if it's attached or null (which just relies on the order of calls).
          else -> children.elementAtOrNull(indexOfMe + 1)?.let {
            if (it.isAttached) {
              LayerPosition(
                /* above = */ null,
                /* below = */ it.getLayerIds().first(),
                /* at = */ null
              )
            } else {
              null
            }
          }
        }
      } else {
        // If the anchor layer is not null then we should insert this node above the
        // top-most (last) layer in the anchor.
        val anchorLayerId = anchorLayerNode.getLayerIds().last()
        // Because the previous layer is already added we should insert this node above the previous one.
        LayerPosition(
          /* above = */ anchorLayerId,
          /* below = */ null,
          /* at = */ null
        )
      }
    }

  private companion object {
    private const val TAG = "LayerPositionAwareNode"
  }
}