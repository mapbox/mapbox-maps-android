package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.style.LayerPositionedContent
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
    val layerPosition = (parent as? StyleLayerPositionNode)?.layerPosition
    repositionLayersForCurrentNode(getRelativePositionInfo(parent, layerPosition))
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
   * which is rendered on top of this one (for [LayerPositionedContent.belowLayer]) or below (for [LayerPositionedContent.aboveLayer])
   * of the current node, construct the [LayerPosition] with the found layer id, so the
   * current layer is inserted at the correct position and consistent with the node tree.
   *
   * If there's nothing above/below current node, take the [targetLayerPosition] as its insertion position.
   *
   * For example, the following compose block:
   * ```
   * aboveLayer("buildings") {
   *  layerPositionAwareNode_1
   *  layerPositionAwareNode_2
   *  layerPositionAwareNode_3
   * }
   * ```
   * Should be rendered in the map in the following order (top visible layer to bottom layer):
   * ```
   *  layerPositionAwareNode_3
   *  layerPositionAwareNode_2
   *  layerPositionAwareNode_1
   *  "buildings"
   * ```
   *
   * Meaning that the relative position of the layers should be:
   * ```
   * layerPositionAwareNode_3 above layerPositionAwareNode_2
   * layerPositionAwareNode_2 above layerPositionAwareNode_1
   * layerPositionAwareNode_1 above "buildings"
   * ```
   *
   *
   * @param parent the parent node of the node to be positioned.
   * @param targetLayerPosition the optional target layer position, useful in case of [StyleLayerPositionNode].
   */
  fun getRelativePositionInfo(
    parent: MapNode,
    targetLayerPosition: LayerPosition? = null
  ): LayerPosition? =
    parent.children.filterIsInstance<LayerPositionAwareNode>().let { children ->
      val me = this@LayerPositionAwareNode
      val isAbove = targetLayerPosition?.above != null
      val anchorLayerIdx = children.indexOf(me) + if (isAbove) {
        -1
      } else {
        1
      }
      val anchorLayerNode = children.elementAtOrNull(anchorLayerIdx)
      return if (anchorLayerNode == null) {
        targetLayerPosition
      } else {
        val anchorLayerId = anchorLayerNode.getLayerIds().first()
        LayerPosition(
          /* above = */ if (isAbove) anchorLayerId else null,
          /* below = */ if (isAbove) null else anchorLayerId,
          /* at = */ null
        )
      }
    }
}