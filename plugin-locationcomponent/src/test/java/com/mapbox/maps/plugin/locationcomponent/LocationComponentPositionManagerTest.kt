package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.StyleManagerInterface
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LocationComponentPositionManagerTest {

  private var style: StyleManagerInterface = mockk(relaxed = true)
  private var layerWrapper: LocationLayerWrapper = mockk(relaxed = true)

  @Test
  fun update_noChange_null() {
    val positionManager = LocationComponentPositionManager(style, null, null)
    val requiresUpdate = positionManager.update(null, null)
    assertFalse(requiresUpdate)
  }

  @Test
  fun update_noChange_above() {
    val positionManager = LocationComponentPositionManager(style, "above", null)
    val requiresUpdate = positionManager.update("above", null)
    assertFalse(requiresUpdate)
  }

  @Test
  fun update_noChange_below() {
    val positionManager = LocationComponentPositionManager(style, null, "below")
    val requiresUpdate = positionManager.update(null, "below")
    assertFalse(requiresUpdate)
  }

  @Test
  fun update_fromNull_above() {
    val positionManager = LocationComponentPositionManager(style, null, null)
    val requiresUpdate = positionManager.update("above", null)
    assertTrue(requiresUpdate)
  }

  @Test
  fun update_fromNull_below() {
    val positionManager = LocationComponentPositionManager(style, null, null)
    val requiresUpdate = positionManager.update(null, "below")
    assertTrue(requiresUpdate)
  }

  @Test
  fun update_toNull_above() {
    val positionManager = LocationComponentPositionManager(style, "above", null)
    val requiresUpdate = positionManager.update(null, null)
    assertTrue(requiresUpdate)
  }

  @Test
  fun update_toNull_below() {
    val positionManager = LocationComponentPositionManager(style, null, "below")
    val requiresUpdate = positionManager.update(null, null)
    assertTrue(requiresUpdate)
  }

  @Test
  fun update_fromValue_above() {
    val positionManager = LocationComponentPositionManager(style, "above1", null)
    val requiresUpdate = positionManager.update("above2", null)
    assertTrue(requiresUpdate)
  }

  @Test
  fun update_fromValue_below() {
    val positionManager = LocationComponentPositionManager(style, null, "below1")
    val requiresUpdate = positionManager.update(null, "below2")
    assertTrue(requiresUpdate)
  }

  @Test
  fun addLayer_noModifier() {
    val positionManager = LocationComponentPositionManager(style, null, null)
    positionManager.addLayerToMap(layerWrapper)

    verify { layerWrapper.bindTo(style, null) }
  }

  @Test
  fun addLayer_above() {
    val positionManager = LocationComponentPositionManager(style, "above", null)
    positionManager.addLayerToMap(layerWrapper)

    verify { layerWrapper.bindTo(style, LayerPosition("above", null, null)) }
  }

  @Test
  fun addLayer_below() {
    val positionManager = LocationComponentPositionManager(style, null, "below")
    positionManager.addLayerToMap(layerWrapper)

    verify { layerWrapper.bindTo(style, LayerPosition(null, "below", null)) }
  }

  @Test
  fun addLayer_afterUpdate_above() {
    val positionManager = LocationComponentPositionManager(style, null, null)
    positionManager.update("above", null)
    positionManager.addLayerToMap(layerWrapper)

    verify { layerWrapper.bindTo(style, LayerPosition("above", null, null)) }
  }

  @Test
  fun addLayer_afterUpdate_below() {
    val positionManager = LocationComponentPositionManager(style, null, null)
    positionManager.update(null, "below")
    positionManager.addLayerToMap(layerWrapper)

    verify { layerWrapper.bindTo(style, LayerPosition(null, "below", null)) }
  }
}