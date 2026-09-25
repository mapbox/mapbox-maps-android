package com.mapbox.maps.extension.compose.internal

import androidx.compose.runtime.AbstractApplier
import com.mapbox.common.LogConfiguration
import com.mapbox.common.LoggingLevel
import com.mapbox.maps.MapView
import com.mapbox.maps.logD

/**
 * Defines the contract of a MapNode, the MapNode will be notified when it's added/moved/removed from
 * the node tree.
 */
internal abstract class MapNode {
  val children = mutableListOf<MapNode>()

  /**
   * Invoked when this [MapNode] is attached to the node tree.
   */
  open fun onAttached(parent: MapNode) {}

  /**
   * Invoked when this [MapNode] is moved inside the node tree.
   */
  open fun onMoved(parent: MapNode) {}

  /**
   * Invoked when this [MapNode] is removed from the node tree.
   */
  open fun onRemoved(parent: MapNode) {}

  /**
   * Invoked when the node tree is cleared.
   */
  open fun onClear() {}
}

/**
 * Root level [MapNode] for MapboxMap composable function.
 */
internal class RootMapNode : MapNode() {
  override fun toString(): String {
    return "RootMapNode(#${hashCode()})"
  }
}

/**
 * MapApplier is responsible for applying the tree-based operations on MapboxMap, that get emitted
 * during a composition.
 */
internal class MapApplier(
  /**
   * The raw map controller to be used within current Compose.
   */
  val mapView: MapView
) : AbstractApplier<MapNode>(RootMapNode()) {

  override fun onClear() {
    logD(TAG, "onClear: current=$current")
    root.children.forEach { it.onClear() }
    root.children.clear()
    printNodesTree("MapNodeCleared")
  }

  override fun insertBottomUp(index: Int, instance: MapNode) {
    logD(TAG, "insertBottomUp: $index, $instance")
    current.children.add(index, instance)
    instance.onAttached(parent = current)
    // If the inserted node is a LayerPositionAwareNode, we need to notify the next
    // LayerPositionAwareNode one so it can re-position itself
    if (instance is LayerPositionAwareNode) {
      notifyMoveNextLayerPositionAwareNode(index + 1)
    }
    printNodesTree("MapNodeInserted")
  }

  override fun insertTopDown(index: Int, instance: MapNode) {
    // An applier should insert the node into the tree either in insertTopDown or insertBottomUp, not both.
    // The insertTopDown method is called before the children of instance have been created and inserted
    // into it. insertBottomUp is called after all children have been created and inserted.

    // insertBottomUp is preferred here because MapNode trees are faster to build bottom-up(MapboxMapNode
    // will be notified about child node changes), and the notifications are linear to the number of
    // nodes inserted.
  }

  /**
   * Indicates that [count] children of [current] should be moved from index [from] to index [to].
   *
   * The [to] index is relative to the position before the change, so, for example, to move an
   * element at position 1 to after the element at position 2, [from] should be `1` and [to]
   * should be `3`. If the elements were A B C D E, calling `move(1, 3, 1)` would result in the
   * elements being reordered to A C B D E.
   */
  override fun move(from: Int, to: Int, count: Int) {
    logD(TAG, "move: $from, $to, $count")
    current.children.move(from, to, count)

    // Let's move the children nodes in the tree
    current.children.forEach { child ->
      child.onMoved(current)
    }
    printNodesTree("MapNodeMoved")
  }

  override fun remove(index: Int, count: Int) {
    logD(TAG, "remove: $index, $count")
    var layerPositionAwareNodeRemoved = false
    repeat(count) { offset ->
      val child = current.children[index + offset]
      child.onRemoved(parent = current)
      layerPositionAwareNodeRemoved = layerPositionAwareNodeRemoved or (child is LayerPositionAwareNode)
    }
    current.children.remove(index, count)
    // If any of the removed nodes are a LayerPositionAwareNode, we need to notify the next
    // LayerPositionAwareNode one so it can re-position itself
    if (layerPositionAwareNodeRemoved) {
      notifyMoveNextLayerPositionAwareNode(index)
    }
    printNodesTree("MapNodeRemoved")
  }

  private fun notifyMoveNextLayerPositionAwareNode(index: Int) {
    for (i in index until current.children.size) {
      if (current.children[i] is LayerPositionAwareNode) {
        current.children[i].onMoved(parent = current)
        break
      }
    }
  }

  private fun printNodesTree(tag: String = TAG) {
    // We don't know how many nodes are in the tree, so we only walk the tree when the logging
    // level is DEBUG.
    LogConfiguration.getLoggingLevel(tag)?.let {
      if (it.ordinal <= LoggingLevel.DEBUG.ordinal) {
        walkChildren(tag = tag, node = root)
      }
    }
  }

  private fun walkChildren(tag: String = TAG, prefix: String = "\t", node: MapNode) {
    logD(tag, "$prefix - $node")
    node.children.forEach {
      walkChildren(tag = tag, prefix = "$prefix\t", node = it)
    }
  }

  private companion object {
    private const val TAG = "MapApplier"
  }
}