package com.mapbox.maps.extension.compose

import com.mapbox.maps.MapView
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
public class MapApplierTest {
  private val mapView = mockk<MapView>()
  private lateinit var mapApplier: MapApplier

  @Before
  public fun setup() {
    mapApplier = MapApplier(mapView)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logD(any(), any()) } just Runs
    every { logW(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs
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
    verify { mapNode0.onMoved(mapApplier.root, 0, 1) }
    verify { mapNode1.onMoved(mapApplier.root, 1, 2) }
    verify { mapNode2.onMoved(mapApplier.root, 2, 3) }
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
    verify { mapNode4.onMoved(mapApplier.root, 4, 0) }
    verify { mapNode5.onMoved(mapApplier.root, 5, 1) }
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
    verify { mapNode0.onMoved(mapApplier.root, 0, 3) }
    verify { mapNode1.onMoved(mapApplier.root, 1, 4) }
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
    verify { mapNode0.onMoved(mapApplier.root, 0, 4) }
    verify { mapNode1.onMoved(mapApplier.root, 1, 5) }
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
    verify { mapNode5.onMoved(mapApplier.root, 5, 0) }
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
    verify { mapNode0.onMoved(mapApplier.root, 0, 0) }
    verify { mapNode1.onMoved(mapApplier.root, 1, 1) }
    verify { mapNode2.onMoved(mapApplier.root, 2, 2) }
    verify { mapNode3.onMoved(mapApplier.root, 3, 3) }
    verify { mapNode4.onMoved(mapApplier.root, 4, 4) }
    verify { mapNode5.onMoved(mapApplier.root, 5, 5) }
  }
}