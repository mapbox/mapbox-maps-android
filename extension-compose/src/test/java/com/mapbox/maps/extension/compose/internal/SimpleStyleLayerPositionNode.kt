package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.extension.compose.SimpleLayerPositionAwareNode
import org.junit.Assert

internal data class SimpleStyleLayerPositionNode(
  val layerPosition: LayerPosition
) : MapNode() {
  internal fun addAndValidatePosition(
    node: SimpleLayerPositionAwareNode,
    idx: Int? = null,
    above: String? = null,
    below: String? = null
  ) {
    if (idx != null) {
      children.add(idx, node)
    } else {
      children.add(node)
    }
    val relativePosition = node.getRelativePositionInfo(this, layerPosition)!!
    Assert.assertEquals(above, relativePosition.above)
    Assert.assertEquals(below, relativePosition.below)
  }
}