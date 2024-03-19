package com.mapbox.maps.extension.compose.internal

import androidx.compose.runtime.AbstractApplier
import com.mapbox.maps.MapView
import com.mapbox.maps.base.BuildConfig
import com.mapbox.maps.logD
import com.mapbox.maps.logW

/**
 * Defines the contract of a MapNode, the MapNode will be notified when it's added/moved/removed from
 * the node tree.
 */
internal abstract class MapNode {
  val children = mutableListOf<MapNode>()

  /**
   * Invoked when the [MapNode] is attached to the node tree.
   */
  open fun onAttached(parent: MapNode) {}

  /**
   * Invoked when the [MapNode] is moved inside the node tree.
   *
   * @param from original position
   * @param to the target position
   */
  open fun onMoved(from: Int, to: Int) {}

  /**
   * Invoked when the [MapNode] is removed from the node tree.
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
    return "RootMapNode(${hashCode()})"
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
  }

  override fun insertBottomUp(index: Int, instance: MapNode) {
    logD(TAG, "insertBottomUp: $index, $instance")
    current.children.add(index, instance)
    instance.onAttached(parent = current)
    printNodesTree()
  }

  override fun insertTopDown(index: Int, instance: MapNode) {
    // An applier should insert the node into the tree either in insertTopDown or insertBottomUp, not both.
    // The insertTopDown method is called before the children of instance have been created and inserted
    // into it. insertBottomUp is called after all children have been created and inserted.

    // insertBottomUp is preferred here because MapNode trees are faster to build bottom-up(MapboxMapNode
    // will be notified about child node changes), and the notifications are linear to the number of
    // nodes inserted.
  }

  override fun move(from: Int, to: Int, count: Int) {
    logD(TAG, "move: $from, $to, $count")
    // TODO: Notify the nodes that has been moved
    current.children.move(from, to, count)
    printNodesTree()
  }

  override fun remove(index: Int, count: Int) {
    logD(TAG, "remove: $index, $count")
    repeat(count) { offset ->
      current.children[index + offset].onRemoved(parent = current)
    }
    current.children.remove(index, count)
    printNodesTree()
  }

  private fun printNodesTree() {
    if (BuildConfig.DEBUG) {
      walkChildren(node = root)
    }
  }

  private fun walkChildren(prefix: String = "\t", node: MapNode) {
    logW(TAG, "$prefix - $node")
    node.children.forEach {
      walkChildren("$prefix\t", it)
    }
  }

  private companion object {
    private const val TAG = "MapApplier"
  }
}