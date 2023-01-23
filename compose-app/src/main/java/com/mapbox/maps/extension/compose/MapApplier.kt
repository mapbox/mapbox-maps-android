package com.mapbox.maps.extension.compose


import androidx.compose.runtime.AbstractApplier
import com.mapbox.maps.MapControllable
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

internal interface MapNode {
  fun onAttached() {}
  fun onRemoved() {}
  fun onCleared() {}
}

private object MapNodeRoot : MapNode

internal class MapApplier(
  val mapController: MapControllable,
  val mapPluginProvider: MapPluginProviderDelegate,
) : AbstractApplier<MapNode>(MapNodeRoot) {

  private val decorations = mutableListOf<MapNode>()

  override fun onClear() {
    decorations.forEach { it.onCleared() }
    decorations.clear()
  }

  override fun insertBottomUp(index: Int, instance: MapNode) {
    decorations.add(index, instance)
    instance.onAttached()
  }

  override fun insertTopDown(index: Int, instance: MapNode) {
    // insertBottomUp is preferred
  }

  override fun move(from: Int, to: Int, count: Int) {
    decorations.move(from, to, count)
  }

  override fun remove(index: Int, count: Int) {
    repeat(count) {
      decorations[index + it].onRemoved()
    }
    decorations.remove(index, count)
  }
}