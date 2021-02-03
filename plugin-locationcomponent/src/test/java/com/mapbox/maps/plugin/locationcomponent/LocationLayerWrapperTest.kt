package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.StyleManagerInterface
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
  private val mapStyleDelegate = mockk<StyleManagerInterface>(relaxed = true)

  @Before
  fun setup() {
    locationLayerWrapper = LocationLayerWrapper(layerId)
  }

  @Test
  fun testBindTo() {
    every { mapStyleDelegate.addStyleLayer(any(), any()) } returns ExpectedFactory.createValue()
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    verify { mapStyleDelegate.addStyleLayer(any(), position) }
  }

  @Test(expected = RuntimeException::class)
  fun testBindToAddLayerFailed() {
    every { mapStyleDelegate.addStyleLayer(any(), any()) } returns ExpectedFactory.createError("error")
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    verify { mapStyleDelegate.addStyleLayer(any(), position) }
  }

  @Test
  fun testVisibilityWhenLayerExists() {
    every { mapStyleDelegate.addStyleLayer(any(), any()) } returns ExpectedFactory.createValue()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createValue()
    every { mapStyleDelegate.styleLayerExists(any()) } returns true
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }

  @Test
  fun testVisibilityWhenLayerNotExist() {
    every { mapStyleDelegate.addStyleLayer(any(), any()) } returns ExpectedFactory.createValue()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createValue()
    every { mapStyleDelegate.styleLayerExists(any()) } returns false
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify(exactly = 0) { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }

  @Test(expected = RuntimeException::class)
  fun testVisibilityWhenLayerExistSetPropertyFailed() {
    every { mapStyleDelegate.addStyleLayer(any(), any()) } returns ExpectedFactory.createValue()
    every { mapStyleDelegate.setStyleLayerProperty(layerId, any(), any()) } returns ExpectedFactory.createError("error")
    every { mapStyleDelegate.styleLayerExists(any()) } returns true
    val position = LayerPosition("above", "below", 0)
    locationLayerWrapper.bindTo(mapStyleDelegate, position)
    locationLayerWrapper.visibility(true)
    verify(exactly = 1) { mapStyleDelegate.setStyleLayerProperty(layerId, "visibility", Value("visible")) }
  }
}