package com.mapbox.maps.plugin.indoorselector

import com.mapbox.annotation.MapboxExperimental
import com.mapbox.maps.indoorFloor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class IndoorSelectorViewImplTest {

  private lateinit var view: IndoorSelectorViewImpl

  @Before
  fun setUp() {
    view = IndoorSelectorViewImpl(RuntimeEnvironment.getApplication())
  }

  @Test
  fun `scroll offset positions selected floor below up-arrow`() {
    val floors = listOf(
      indoorFloor("b0", "G"),
      indoorFloor("b1", "1"),
      indoorFloor("b2", "2"),
    )
    // index 2 → (2-1)*itemHeightPx so floor above selected is covered by arrow, not selected itself
    assertEquals(1 * view.itemHeightPx, view.selectedFloorScrollOffset(floors, "b2"))
  }

  @Test
  fun `scroll offset is zero when first floor selected`() {
    val floors = listOf(
      indoorFloor("b0", "G"),
      indoorFloor("b1", "1"),
      indoorFloor("b2", "2"),
    )
    // index 0 → coerceAtLeast(0) = 0
    assertEquals(0, view.selectedFloorScrollOffset(floors, "b0"))
  }

  @Test
  fun `scroll offset is zero when second floor selected`() {
    val floors = listOf(
      indoorFloor("b0", "G"),
      indoorFloor("b1", "1"),
      indoorFloor("b2", "2"),
    )
    // index 1 → (1-1)*itemHeightPx = 0
    assertEquals(0, view.selectedFloorScrollOffset(floors, "b1"))
  }

  @Test
  fun `scroll offset is zero when selected floor not found`() {
    val floors = listOf(
      indoorFloor("b0", "G"),
      indoorFloor("b1", "1"),
    )
    assertEquals(0, view.selectedFloorScrollOffset(floors, null))
  }

  @Test
  fun `scroll offset is clamped to max scroll when last floor selected beyond visible window`() {
    // 6 floors > MAX_VISIBLE_ITEMS (4): naive (index - 1) offset would be 4 * itemHeightPx,
    // but only 2 items' worth of content is scrollable, so it must clamp to that.
    val floors = (0..5).map { indoorFloor("b$it", "$it") }
    val maxScroll = 2 * view.itemHeightPx
    assertEquals(maxScroll, view.selectedFloorScrollOffset(floors, "b5"))
  }

  @Test
  fun `scroll offset is unclamped when mid-list floor selected in a long floor list`() {
    // 6 floors > MAX_VISIBLE_ITEMS (4), but index 2 → (2-1)*itemHeightPx stays within
    // max scroll (2*itemHeightPx), so the clamp should be a no-op here.
    val floors = (0..5).map { indoorFloor("b$it", "$it") }
    assertEquals(1 * view.itemHeightPx, view.selectedFloorScrollOffset(floors, "b2"))
  }
}