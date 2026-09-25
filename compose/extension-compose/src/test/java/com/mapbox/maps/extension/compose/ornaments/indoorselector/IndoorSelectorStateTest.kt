package com.mapbox.maps.extension.compose.ornaments.indoorselector

import com.mapbox.maps.IndoorFloor
import com.mapbox.maps.MapboxExperimental
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class, com.mapbox.annotation.MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
internal class IndoorSelectorStateTest {

  private lateinit var state: IndoorSelectorState

  @Before
  fun setUp() {
    state = IndoorSelectorState()
  }

  @Test
  fun `initial floors list is empty`() {
    assertTrue(state.floors.isEmpty())
  }

  @Test
  fun `initial selectedFloorId is null`() {
    assertNull(state.selectedFloorId)
  }

  @Test
  fun `setting selectedFloorId stores the value`() {
    state.selectedFloorId = "floor-1"
    assertEquals("floor-1", state.selectedFloorId)
  }

  @Test
  fun `setting selectedFloorId to null stores null`() {
    state.selectedFloorId = "floor-1"
    state.selectedFloorId = null
    assertNull(state.selectedFloorId)
  }

  @Test
  fun `setting same value is idempotent`() {
    state.selectedFloorId = "floor-2"
    assertEquals("floor-2", state.selectedFloorId)
    state.selectedFloorId = "floor-2"
    assertEquals("floor-2", state.selectedFloorId)
  }

  @Test
  fun `handleIndoorUpdate with empty selectedFloorId maps to null`() {
    state.handleIndoorUpdate(mockIndoorState(listOf(mockFloor("f1", "1F")), ""))

    assertNull(state.selectedFloorId)
    assertEquals(1, state.floors.size)
  }

  @Test
  fun `handleIndoorUpdate with non-empty selectedFloorId preserves the id`() {
    val floors = listOf(mockFloor("f1", "1F"), mockFloor("f2", "2F"))
    state.handleIndoorUpdate(mockIndoorState(floors, "f2"))

    assertEquals("f2", state.selectedFloorId)
    assertEquals(2, state.floors.size)
  }

  @Test
  fun `handleIndoorUpdate replaces floor list`() {
    state.handleIndoorUpdate(mockIndoorState(listOf(mockFloor("f1", "1F")), "f1"))
    assertEquals(1, state.floors.size)

    val newFloors = listOf(mockFloor("a1", "B1"), mockFloor("a2", "B2"), mockFloor("a3", "B3"))
    state.handleIndoorUpdate(mockIndoorState(newFloors, "a2"))

    assertEquals(3, state.floors.size)
    assertEquals("a2", state.selectedFloorId)
  }

  @Test
  fun `handleIndoorUpdate with empty floors clears state`() {
    state.handleIndoorUpdate(mockIndoorState(listOf(mockFloor("f1", "1F")), "f1"))
    assertEquals(1, state.floors.size)

    state.handleIndoorUpdate(mockIndoorState(emptyList(), ""))

    assertTrue(state.floors.isEmpty())
    assertNull(state.selectedFloorId)
  }

  private fun mockFloor(id: String, name: String): IndoorFloor =
    mockk { every { this@mockk.id } returns id; every { this@mockk.name } returns name }

  private fun mockIndoorState(floors: List<IndoorFloor>, selectedFloorId: String): com.mapbox.maps.IndoorState =
    mockk { every { this@mockk.floors } returns floors; every { this@mockk.selectedFloorId } returns selectedFloorId }
}