package com.mapbox.maps.extension.compose.internal

import androidx.compose.runtime.AbstractApplier
import com.mapbox.maps.MapView

/**
 * Defines the contract of a MapNode, the MapNode will be notified when it's added/moved/removed from
 * the node tree.
 */
internal interface MapNode {
  /**
   * Invoked when the [MapNode] is attached to the MapboxMap node tree.
   */
  fun onAttached() {}

  /**
   * Invoked when the [MapNode] is moved inside the node tree.
   *
   * @param from original position
   * @param to the target position
   */
  fun onMoved(from: Int, to: Int) {}

  /**
   * Invoked when the [MapNode] is removed from the MapboxMap node tree.
   */
  fun onRemoved() {}

  /**
   * Invoked when the MapboxMap node tree is cleared.
   */
  fun onClear() {}
}

/**
 * Root level [MapNode] for MapboxMap composable function.
 */
private object RootMapNode : MapNode

/**
 * MapApplier is responsible for applying the tree-based operations on MapboxMap, that get emitted
 * during a composition.
 */
internal class MapApplier(
  /**
   * The raw map controller to be used within current Compose.
   */
  val mapView: MapView
) : AbstractApplier<MapNode>(RootMapNode) {
  private val decorations = mutableListOf<MapNode>()

  override fun onClear() {
    decorations.forEach { it.onClear() }
    decorations.clear()
  }

  override fun insertBottomUp(index: Int, instance: MapNode) {
    decorations.add(index, instance)
    instance.onAttached()
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
    // TODO: Notify the nodes that has been moved
    decorations.move(from, to, count)
  }

  override fun remove(index: Int, count: Int) {
    repeat(count) { offset ->
      decorations[index + offset].onRemoved()
    }
    decorations.remove(index, count)
  }
}