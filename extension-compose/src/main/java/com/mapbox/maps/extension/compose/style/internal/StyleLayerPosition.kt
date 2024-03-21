package com.mapbox.maps.extension.compose.style.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable

internal class StyleLayerPositionNode(
  internal val layerPosition: LayerPosition,
) : MapNode() {
  lateinit var mapStyleNode: MapStyleNode
  override fun onAttached(parent: MapNode) {
    mapStyleNode = parent as MapStyleNode
  }

  override fun toString(): String {
    return "StyleLayerPositionNode(layerPosition=$layerPosition)"
  }
}

/**
 * This is a internal composable function because we only want to expose it through Style with hashmap.
 * As it's bound with the style layers available in the style JSON, and we also don't want multiple layerPosition declaration
 * with the same position, as it will mess up with our layer ordering.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
internal fun StyleLayerPosition(
  layerPosition: LayerPosition,
  content: (@Composable @MapboxMapComposable () -> Unit)? = null
) {
  val applier = currentComposer.applier as? MapApplier
  applier?.let {
    ComposeNode<StyleLayerPositionNode, MapApplier>(
      factory = {
        StyleLayerPositionNode(layerPosition)
      },
      update = { }
    ) {
      content?.invoke()
    }
  }
    ?: throw IllegalStateException("Illegal use of StyleLayerPosition composable outside of MapboxMapComposable")
}