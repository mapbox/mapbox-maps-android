package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.StyleManagerInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class LocationIndicatorLayerWrapperTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val layer = LocationIndicatorLayerWrapper(INDICATOR_LAYER_ID)
  private val expected: Expected<String, None> = mockk(relaxed = true)

  @Before
  fun setup() {
    every { style.styleLayerExists(any()) } returns true
    every { style.addPersistentStyleLayer(any(), any()) } returns expected
    every { style.setStyleLayerProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null

    layer.bindTo(style)
  }

  @Test
  fun testInitialProperties() {
    val value = layer.toValue()
    assertEquals("{image-pitch-displacement=4.0, location-transition={duration=0, delay=0}, perspective-compensation=0.9, id=indicatorLayerId, type=location-indicator}", value.toString())
  }

  @Test
  fun testBearing() {
    val bearing = 1.0
    layer.bearing(bearing)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "bearing", Value(bearing)) }
  }

  @Test
  fun testLocation() {
    val location = arrayListOf(1.0, 2.0)
    layer.location(location)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "location", Value(location.map { Value(it) })) }
  }

  @Test
  fun testAccuracyRadiusColor() {
    val expression = arrayListOf(Value("rgb"), Value(1), Value(2))
    layer.accuracyRadiusColor(expression)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "accuracy-radius-color", Value(expression)) }
  }

  @Test
  fun testAccuracyRadiusBorderColor() {
    val expression = arrayListOf(Value("rgb"), Value(1), Value(2))
    layer.accuracyRadiusBorderColor(expression)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "accuracy-radius-border-color", Value(expression)) }
  }

  @Test
  fun testShadowImageSize() {
    val expression = Value(arrayListOf(Value(1.0), Value(2.0)))
    layer.shadowImageSize(expression)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "shadow-image-size", expression) }
  }

  @Test
  fun testBearingImageSize() {
    val expression = Value(arrayListOf(Value(1.0), Value(2.0)))
    layer.bearingImageSize(expression)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "bearing-image-size", expression) }
  }

  @Test
  fun testTopImageSize() {
    val expression = Value(arrayListOf(Value(1.0), Value(2.0)))
    layer.topImageSize(expression)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "top-image-size", expression) }
  }

  @Test
  fun testAccuracyRadius() {
    val radius = 1.0
    layer.accuracyRadius(radius)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "accuracy-radius", Value(radius)) }
  }

  @Test
  fun testEmphasisCircleRadius() {
    val radius = 1.0
    layer.emphasisCircleRadius(radius)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "emphasis-circle-radius", Value(radius)) }
  }

  @Test
  fun testEmphasisCircleColorTransition() {
    val delay = 1L
    val duration = 2L
    layer.emphasisCircleColorTransition(delay = delay, duration = duration)
    val transition = HashMap<String, Value>()
    transition["delay"] = Value(delay)
    transition["duration"] = Value(duration)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "emphasis-circle-color-transition", Value(transition)) }
  }

  @Test
  fun testEmphasisCircleColor() {
    val expression = arrayListOf(Value("rgb"), Value(1), Value(2))
    layer.emphasisCircleColor(expression)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "emphasis-circle-color", Value(expression)) }
  }

  @Test
  fun testTopImage() {
    val topImage = "topImage"
    layer.topImage(topImage)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "top-image", Value(topImage)) }
  }

  @Test
  fun testBearingImage() {
    val bearingImage = "bearingImage"
    layer.bearingImage(bearingImage)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "bearing-image", Value(bearingImage)) }
  }

  @Test
  fun testShadowImage() {
    val shadowImage = "shadowImage"
    layer.shadowImage(shadowImage)
    verify { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "shadow-image", Value(shadowImage)) }
  }

  @Test
  fun testLayerNotReady() {
    every { style.styleLayerExists(any()) } returns false
    val bearing = 1.0
    layer.bearing(bearing)
    verify(exactly = 0) { style.setStyleLayerProperty(INDICATOR_LAYER_ID, "bearing", Value(bearing)) }
  }

  companion object {
    private const val INDICATOR_LAYER_ID = "indicatorLayerId"
  }
}