package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.Style
import com.mapbox.maps.logE
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationLayerWrapperTest {
  private lateinit var locationLayerWrapper: LocationLayerWrapper
  private val layerId = "testLayerId"
  private val mapStyleDelegate = mockk<Style>(relaxed = true)

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    locationLayerWrapper = LocationLayerWrapper(layerId)
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
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
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }

  fun testVisibilityWhenLayerExistSetPropertyFailed() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createError("error")
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify(exactly = 1) { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
    verify(exactly = 1) { logE(any(), any()) }
  }

  @Test
  fun updateStyle() {
    every { mapStyleDelegate.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createNone()
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    val newStyle = mockk<Style>()
    every { newStyle.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createNone()
    locationLayerWrapper.updateStyle(newStyle)
    locationLayerWrapper.visibility(true)
    verify(exactly = 0) { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
    verify(exactly = 1) { newStyle.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }
}