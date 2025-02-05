package com.mapbox.maps.extension.compose

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.internal.LayerPositionAwareNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert

internal data class SimpleLayerPositionAwareNode(
  val id: String,
  var attachedLayerPosition: LayerPosition? = null,
  val movedLayerIdSlot: CapturingSlot<String> = slot<String>(),
) : LayerPositionAwareNode, MapNode() {
  private val movedLayerPosition: CapturingSlot<LayerPosition> = slot<LayerPosition>()

  private val mapboxStyleManager = mockk<MapboxStyleManager> {
    every {
      moveStyleLayer(
        capture(movedLayerIdSlot),
        capture(movedLayerPosition)
      )
    } answers {
      attachedLayerPosition = movedLayerPosition.captured
      ExpectedFactory.createNone()
    }
  }

  private val layerIds = listOf(id)
  override fun getLayerIds(): List<String> = layerIds

  override fun onMoved(parent: MapNode) {
    println("($this).onMoved")
    mapboxStyleManager.repositionCurrentNode(parent)
  }

  override fun onAttached(parent: MapNode) {
    val parentPositionNode = parent as StyleLayerPositionNode
    attachedLayerPosition =
      getRelativePositionInfo(parentPositionNode, parentPositionNode.layerPosition)
  }

  internal fun validateMoved(
    above: String? = null,
    below: String? = null
  ) {
    Assert.assertEquals(id, movedLayerIdSlot.captured.also { movedLayerIdSlot.clear() })
    Assert.assertEquals(above, attachedLayerPosition!!.above)
    Assert.assertEquals(below, attachedLayerPosition!!.below)
  }
}