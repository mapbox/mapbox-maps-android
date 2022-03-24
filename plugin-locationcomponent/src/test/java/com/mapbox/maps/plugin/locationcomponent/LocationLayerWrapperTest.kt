package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.extension.style.StyleInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.RuntimeException

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class LocationLayerWrapperTest {
  private lateinit var locationLayerWrapper: LocationLayerWrapper
  private val layerId = "testLayerId"
  private val mapStyleDelegate = mockk<StyleInterface>(relaxed = true)

  @Before
  fun setup() {
    locationLayerWrapper = LocationLayerWrapper(layerId)
  }

  @Test
  fun testBindTo() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    verify { mapStyleDelegate.addPersistentStyleLayer(any(), position) }
  }

  @Test(expected = RuntimeException::class)
  fun testBindToAddLayerFailed() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createError("error")
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    verify { mapStyleDelegate.addPersistentStyleLayer(any(), position) }
  }

  @Test
  fun testVisibilityWhenLayerExists() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.styleLayerExists(any()) } returns true
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }

  @Test
  fun testVisibilityWhenLayerNotExist() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.styleLayerExists(any()) } returns false
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify(exactly = 0) { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }

  @Test(expected = RuntimeException::class)
  fun testVisibilityWhenLayerExistSetPropertyFailed() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createError("error")
    every { mapStyleDelegate.styleLayerExists(any()) } returns true
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify(exactly = 1) { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }

  @Test
  fun updateStyle() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.styleLayerExists(any()) } returns true
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    val newStyle = mockk<StyleInterface>()
    every { newStyle.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createNone()
    every { newStyle.styleLayerExists(any()) } returns true
    locationLayerWrapper.updateStyle(newStyle)
    locationLayerWrapper.visibility(true)
    verify(exactly = 0) { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
    verify(exactly = 1) { newStyle.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }
}