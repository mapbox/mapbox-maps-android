package com.mapbox.maps.extension.compose

import com.mapbox.common.LogConfiguration
import com.mapbox.common.LoggingLevel
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.internal.MapStyleNode
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implements

@Implements(LogConfiguration::class)
public object ShadowLogConfiguration {
  @JvmStatic
  public fun getLoggingLevel(): LoggingLevel = LoggingLevel.DEBUG

  @JvmStatic
  public fun getLoggingLevel(@Suppress("UNUSED_PARAMETER") category: String): LoggingLevel =
    LoggingLevel.DEBUG
}

@Config(shadows = [ShadowLogConfiguration::class])
@RunWith(RobolectricTestRunner::class)
public class MapApplierTest {
  private val mapView = mockk<MapView>()
  private lateinit var mapApplier: MapApplier

  @Before
  public fun setup() {
    mapApplier = MapApplier(mapView)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    val messageSlot = slot<String>()
    every { logD(any(), capture(messageSlot)) } answers {
      println("logD: ${messageSlot.captured}")
    }
    every { logW(any(), capture(messageSlot)) } answers {
      println("logW: ${messageSlot.captured}")
    }
    every { logE(any(), capture(messageSlot)) } answers {
      println("logE: ${messageSlot.captured}")
    }
  }

  @After
  public fun cleanUp() {
    unmockkAll()
  }

  @Test
  public fun testMapApplierInsertBottomUp() {
    val mapNode0 = spyk(object : MapNode() {})
    val mapNode1 = spyk(object : MapNode() {})

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)

    verify { mapNode0.onAttached(mapApplier.root) }
    verify { mapNode1.onAttached(mapApplier.root) }
  }

  @Test
  public fun testMapApplierRemove() {
    val mapNode0 = spyk(object : MapNode() {})
    val mapNode1 = spyk(object : MapNode() {})

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)

    mapApplier.remove(1, 1)
    verify { mapNode1.onRemoved(mapApplier.root) }
    verify(exactly = 0) { mapNode0.onRemoved(any()) }
  }

  @Test
  public fun testMapApplierClear() {
    val mapNode0 = spyk(object : MapNode() {})
    val mapNode1 = spyk(object : MapNode() {})

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)

    mapApplier.clear()
    verify { mapNode1.onClear() }
    verify { mapNode1.onClear() }
  }

  @Test
  public fun testMapApplierMoveToHigher() {
    val mapNode0 = spyk(object : MapNode() {}, name = "node0")
    val mapNode1 = spyk(object : MapNode() {}, name = "node1")
    val mapNode2 = spyk(object : MapNode() {}, name = "node2")
    val mapNode3 = spyk(object : MapNode() {}, name = "node3")
    val mapNode4 = spyk(object : MapNode() {}, name = "node4")
    val mapNode5 = spyk(object : MapNode() {}, name = "node5")

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)
    mapApplier.insertBottomUp(2, mapNode2)
    mapApplier.insertBottomUp(3, mapNode3)
    mapApplier.insertBottomUp(4, mapNode4)
    mapApplier.insertBottomUp(5, mapNode5)

    mapApplier.move(0, 4, 3)
    // move: 0, 4, 3
    // before: [null(node0#5), null(node1#6), null(node2#7), null(node3#8), null(node4#9), null(node5#10)]
    // after: [null(node3#8), null(node0#5), null(node1#6), null(node2#7), null(node4#9), null(node5#10)]
    verify { mapNode0.onMoved(mapApplier.root) }
    verify { mapNode1.onMoved(mapApplier.root) }
    verify { mapNode2.onMoved(mapApplier.root) }
  }

  @Test
  public fun testMapApplierMoveToLower() {
    val mapNode0 = spyk(object : MapNode() {}, name = "node0")
    val mapNode1 = spyk(object : MapNode() {}, name = "node1")
    val mapNode2 = spyk(object : MapNode() {}, name = "node2")
    val mapNode3 = spyk(object : MapNode() {}, name = "node3")
    val mapNode4 = spyk(object : MapNode() {}, name = "node4")
    val mapNode5 = spyk(object : MapNode() {}, name = "node5")

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)
    mapApplier.insertBottomUp(2, mapNode2)
    mapApplier.insertBottomUp(3, mapNode3)
    mapApplier.insertBottomUp(4, mapNode4)
    mapApplier.insertBottomUp(5, mapNode5)

    mapApplier.move(4, 0, 2)
    // move: 4, 0, 2
    // before: [null(node0#5), null(node1#6), null(node2#7), null(node3#8), null(node4#9), null(node5#10)]
    // after: [null(node4#9), null(node5#10), null(node0#5), null(node1#6), null(node2#7), null(node3#8)]
    verify { mapNode4.onMoved(mapApplier.root) }
    verify { mapNode5.onMoved(mapApplier.root) }
  }

  @Test
  public fun testMapApplierMoveCorner() {
    val mapNode0 = spyk(object : MapNode() {}, name = "node0")
    val mapNode1 = spyk(object : MapNode() {}, name = "node1")
    val mapNode2 = spyk(object : MapNode() {}, name = "node2")
    val mapNode3 = spyk(object : MapNode() {}, name = "node3")
    val mapNode4 = spyk(object : MapNode() {}, name = "node4")
    val mapNode5 = spyk(object : MapNode() {}, name = "node5")

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)
    mapApplier.insertBottomUp(2, mapNode2)
    mapApplier.insertBottomUp(3, mapNode3)
    mapApplier.insertBottomUp(4, mapNode4)
    mapApplier.insertBottomUp(5, mapNode5)

    mapApplier.move(0, 5, 2)
    verify { mapNode0.onMoved(mapApplier.root) }
    verify { mapNode1.onMoved(mapApplier.root) }
  }

  @Test
  public fun testMapApplierMoveCorner2() {
    val mapNode0 = spyk(object : MapNode() {}, name = "node0")
    val mapNode1 = spyk(object : MapNode() {}, name = "node1")
    val mapNode2 = spyk(object : MapNode() {}, name = "node2")
    val mapNode3 = spyk(object : MapNode() {}, name = "node3")
    val mapNode4 = spyk(object : MapNode() {}, name = "node4")
    val mapNode5 = spyk(object : MapNode() {}, name = "node5")

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)
    mapApplier.insertBottomUp(2, mapNode2)
    mapApplier.insertBottomUp(3, mapNode3)
    mapApplier.insertBottomUp(4, mapNode4)
    mapApplier.insertBottomUp(5, mapNode5)

    mapApplier.move(0, 6, 2)
    verify { mapNode0.onMoved(mapApplier.root) }
    verify { mapNode1.onMoved(mapApplier.root) }
  }

  @Test
  public fun testMapApplierMoveCorner3() {
    val mapNode0 = spyk(object : MapNode() {}, name = "node0")
    val mapNode1 = spyk(object : MapNode() {}, name = "node1")
    val mapNode2 = spyk(object : MapNode() {}, name = "node2")
    val mapNode3 = spyk(object : MapNode() {}, name = "node3")
    val mapNode4 = spyk(object : MapNode() {}, name = "node4")
    val mapNode5 = spyk(object : MapNode() {}, name = "node5")

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)
    mapApplier.insertBottomUp(2, mapNode2)
    mapApplier.insertBottomUp(3, mapNode3)
    mapApplier.insertBottomUp(4, mapNode4)
    mapApplier.insertBottomUp(5, mapNode5)

    mapApplier.move(5, 0, 1)
    verify { mapNode5.onMoved(mapApplier.root) }
  }

  @Test
  public fun testMapApplierMoveAll() {
    val mapNode0 = spyk(object : MapNode() {}, name = "node0")
    val mapNode1 = spyk(object : MapNode() {}, name = "node1")
    val mapNode2 = spyk(object : MapNode() {}, name = "node2")
    val mapNode3 = spyk(object : MapNode() {}, name = "node3")
    val mapNode4 = spyk(object : MapNode() {}, name = "node4")
    val mapNode5 = spyk(object : MapNode() {}, name = "node5")

    mapApplier.insertBottomUp(0, mapNode0)
    mapApplier.insertBottomUp(1, mapNode1)
    mapApplier.insertBottomUp(2, mapNode2)
    mapApplier.insertBottomUp(3, mapNode3)
    mapApplier.insertBottomUp(4, mapNode4)
    mapApplier.insertBottomUp(5, mapNode5)

    mapApplier.move(0, 6, 6)
    verify { mapNode0.onMoved(mapApplier.root) }
    verify { mapNode1.onMoved(mapApplier.root) }
    verify { mapNode2.onMoved(mapApplier.root) }
    verify { mapNode3.onMoved(mapApplier.root) }
    verify { mapNode4.onMoved(mapApplier.root) }
    verify { mapNode5.onMoved(mapApplier.root) }
  }

  /**
   * Validates the relative position when layers are moved. Starting with:
   * ```
   * aboveLayer("style_layer") {
   *   layer("layer_01")
   *   layer("layer_02")
   *   layer("layer_03")
   * }
   * ```
   */
  @Test
  public fun moveWithinAboveLayerBlock() {
    val mapApplier = MapApplier(mapView)
    val mapStyleNodeChildren = mutableListOf<MapNode>()
    val mapStyleNode: MapStyleNode = mockk(relaxed = true) {
      every { children } returns mapStyleNodeChildren
    }
    mapApplier.insertBottomUp(0, mapStyleNode)
    mapApplier.down(mapStyleNode)
    Assert.assertEquals(mapStyleNode, mapApplier.root.children[0])
    Assert.assertEquals(mapStyleNode, mapApplier.current)

    // Insert the `aboveLayer("style_layer")` node
    val parent = StyleLayerPositionNode(LayerPosition("style_layer", null, null))
    mapApplier.insertBottomUp(0, parent)
    mapApplier.down(parent)
    Assert.assertEquals(parent, mapApplier.root.children[0].children[0])
    Assert.assertEquals(parent, mapApplier.current)

    val layer1 = SimpleLayerPositionAwareNode("layer_01")
    val layer2 = SimpleLayerPositionAwareNode("layer_02")
    val layer3 = SimpleLayerPositionAwareNode("layer_03")

    mapApplier.insertBottomUp(0, layer1)
    // current render layers top to bottom:
    // "layer_01"
    // "style_layer"
    layer1.validateInserted(above = "style_layer")

    mapApplier.insertBottomUp(1, layer2)
    // current render layers top to bottom:
    // "layer_02"
    // "layer_01"
    // "style_layer"
    layer2.validateInserted(above = "layer_01")

    mapApplier.insertBottomUp(2, layer3)
    // current render layers top to bottom:
    // "layer_03"
    // "layer_02"
    // "layer_01"
    // "style_layer"
    layer3.validateInserted(above = "layer_02")
    parent.assertLayerIds(listOf("layer_01", "layer_02", "layer_03"))

    // move layer_02 to the first position in the composition tree
    mapApplier.move(1, 0, 1)
    // current render layers top to bottom:
    // "layer_03"
    // "layer_01"
    // "layer_02"
    // "style_layer"
    parent.assertLayerIds(listOf("layer_02", "layer_01", "layer_03"))
    // Make sure layer_02 is moved and rendered above style_layer
    layer2.validateMoved(above = "style_layer")
    // Make sure layer_01 is moved and rendered above layer_02
    layer1.validateMoved(above = "layer_02")
    // Make sure layer_03 is moved and rendered above layer_01
    layer3.validateMoved(above = "layer_01")

    // move layer_01 and layer_03 to the first position in the composition tree
    mapApplier.move(1, 0, 2)
    // current render layers:
    // "layer_02"
    // "layer_03"
    // "layer_01"
    // "style_layer"
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02"))
    // Make sure layer_01 is moved and rendered above style_layer
    layer1.validateMoved(above = "style_layer")
    // Make sure layer_03 is moved and rendered above layer_01
    layer3.validateMoved(above = "layer_01")
    // Make sure layer_02 is moved and rendered above layer_03
    layer2.validateMoved(above = "layer_03")

    // Insert layer_04
    val layer4 = SimpleLayerPositionAwareNode("layer_04")
    mapApplier.insertBottomUp(3, layer4)
    // current render layers:
    // "layer_04"
    // "layer_02"
    // "layer_03"
    // "layer_01"
    // "style_layer"
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02", "layer_04"))
    // Make sure layer_01 and layer_03 are moved and rendered above layer_04
    mapApplier.move(0, 4, 2)
    parent.assertLayerIds(listOf("layer_02", "layer_04", "layer_01", "layer_03"))
    // current render layers:
    // "layer_03"
    // "layer_01"
    // "layer_04"
    // "layer_02"
    // "style_layer"
    layer3.validateMoved(above = "layer_01")
    layer1.validateMoved(above = "layer_04")
    layer4.validateMoved(above = "layer_02")
    layer2.validateMoved(above = "style_layer")
  }

  /**
   * Validates the relative position when layers are moved. Starting with:
   * ```
   * belowLayer("style_layer") {
   *   layer("layer_01")
   *   layer("layer_02")
   *   layer("layer_03")
   * }
   * ```
   */
  @Test
  public fun moveWithinBelowLayerBlock() {
    val mapApplier = MapApplier(mapView)
    val mapStyleNodeChildren = mutableListOf<MapNode>()
    val mapStyleNode: MapStyleNode = mockk(relaxed = true) {
      every { children } returns mapStyleNodeChildren
    }
    mapApplier.insertBottomUp(0, mapStyleNode)
    mapApplier.down(mapStyleNode)
    Assert.assertEquals(mapStyleNode, mapApplier.root.children[0])
    Assert.assertEquals(mapStyleNode, mapApplier.current)

    // Insert the `belowLayer("style_layer")` node
    val parent = StyleLayerPositionNode(LayerPosition(null, "style_layer", null))
    mapApplier.insertBottomUp(0, parent)
    mapApplier.down(parent)
    Assert.assertEquals(parent, mapApplier.root.children[0].children[0])
    Assert.assertEquals(parent, mapApplier.current)

    val layer1 = SimpleLayerPositionAwareNode("layer_01")
    val layer2 = SimpleLayerPositionAwareNode("layer_02")
    val layer3 = SimpleLayerPositionAwareNode("layer_03")

    mapApplier.insertBottomUp(0, layer1)
    // current render layers top to bottom:
    // "style_layer"
    // "layer_01"
    layer1.validateInserted(below = "style_layer")

    mapApplier.insertBottomUp(1, layer2)
    // current render layers top to bottom:
    // "style_layer"
    // "layer_02"
    // "layer_01"
    layer2.validateInserted(above = "layer_01")

    mapApplier.insertBottomUp(2, layer3)
    // current render layers top to bottom:
    // "style_layer"
    // "layer_03"
    // "layer_02"
    // "layer_01"
    layer3.validateInserted(above = "layer_02")
    parent.assertLayerIds(listOf("layer_01", "layer_02", "layer_03"))

    // move layer_02 to the first position in the composition tree
    mapApplier.move(1, 0, 1)
    // current render layers top to bottom:
    // "style_layer"
    // "layer_03"
    // "layer_01"
    // "layer_02"
    parent.assertLayerIds(listOf("layer_02", "layer_01", "layer_03"))
    // Make sure layer_02 is moved and rendered below style_layer
    layer2.validateMoved(below = "style_layer")
    // Make sure layer_01 is moved and rendered above layer_02
    layer1.validateMoved(above = "layer_02")
    // Make sure layer_03 is moved and rendered above layer_01
    layer3.validateMoved(above = "layer_01")

    // move layer_01 and layer_03 to the first position in the composition tree
    mapApplier.move(1, 0, 2)
    // current render layers:
    // "style_layer"
    // "layer_02"
    // "layer_03"
    // "layer_01"
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02"))
    // Make sure layer_01 is moved and rendered below style_layer
    layer1.validateMoved(below = "style_layer")
    // Make sure layer_03 is moved and rendered above layer_01
    layer3.validateMoved(above = "layer_01")
    // Make sure layer_02 is moved and rendered above layer_03
    layer2.validateMoved(above = "layer_03")

    // Insert layer_04
    val layer4 = SimpleLayerPositionAwareNode("layer_04")
    mapApplier.insertBottomUp(3, layer4)
    // current render layers:
    // "style_layer"
    // "layer_04"
    // "layer_02"
    // "layer_03"
    // "layer_01"
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02", "layer_04"))
    // Make sure layer_01 and layer_03 are moved and rendered above layer_04
    mapApplier.move(0, 4, 2)
    parent.assertLayerIds(listOf("layer_02", "layer_04", "layer_01", "layer_03"))
    // current render layers:
    // "style_layer"
    // "layer_03"
    // "layer_01"
    // "layer_04"
    // "layer_02"
    layer2.validateMoved(below = "style_layer")
    layer4.validateMoved(above = "layer_02")
    layer1.validateMoved(above = "layer_04")
    layer3.validateMoved(above = "layer_01")
  }
}