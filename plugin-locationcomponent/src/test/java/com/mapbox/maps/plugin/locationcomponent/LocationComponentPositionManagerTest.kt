package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.common.BaseMapboxInitializer
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleManager
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test

class LocationComponentPositionManagerTest {

  private var style: MapboxStyleManager = mockk(relaxed = true)
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

  @Test
  fun updateStyle() {
    val positionManager = LocationComponentPositionManager(style, null, null)
    val newStyle = mockk<MapboxStyleManager>()
    positionManager.updateStyle(newStyle)
    positionManager.update(null, "below")
    positionManager.addLayerToMap(layerWrapper)
    verify(exactly = 0) { layerWrapper.bindTo(style, LayerPosition(null, "below", null)) }
    verify(exactly = 1) { layerWrapper.bindTo(newStyle, LayerPosition(null, "below", null)) }
  }

  companion object {
    @JvmStatic
    @BeforeClass
    fun before() {
      mockkObject(BaseMapboxInitializer)
      every { BaseMapboxInitializer.init<Any>(any()) } just Runs
    }
  }
}