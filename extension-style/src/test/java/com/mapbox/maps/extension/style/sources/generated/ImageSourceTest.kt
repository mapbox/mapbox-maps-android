// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.dsl.generated.sum
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class ImageSourceTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val expectedDelta = mockk<Expected<String, Byte>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
    every { expected.error } returns null
    every { expectedDelta.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleSourcePropertyDefaultValue(any(), any()) } returns styleProperty
  }

  @Test
  fun getTypeTest() {
    val testSource = imageSource("testId") {}
    assertEquals("image", testSource.getType())
  }

  @Test
  fun urlSet() {
    val testSource = imageSource("testId") {
      url("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("url=abc"))
  }

  @Test
  fun urlAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = imageSource("testId") {
      url(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("url=[+, 2, 3]"))
  }

  @Test
  fun urlSetAfterBind() {
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.url("abc")

    verify { style.setStyleSourceProperty("testId", "url", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun urlAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.url(expression)

    verify { style.setStyleSourceProperty("testId", "url", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun urlGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.url?.toString())
    verify { style.getStyleSourceProperty("testId", "url") }
  }

  @Test
  fun urlAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.urlAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "url") }
  }

  @Test
  fun coordinatesSet() {
    val testSource = imageSource("testId") {
      coordinates(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("coordinates=[[0.0, 1.0], [0.0, 1.0], [0.0, 1.0], [0.0, 1.0]]"))
  }

  @Test
  fun coordinatesAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = imageSource("testId") {
      coordinates(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("coordinates=[+, 2, 3]"))
  }

  @Test
  fun coordinatesSetAfterBind() {
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.coordinates(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)))

    verify { style.setStyleSourceProperty("testId", "coordinates", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[[0.0, 1.0], [0.0, 1.0], [0.0, 1.0], [0.0, 1.0]]")
  }

  @Test
  fun coordinatesAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.coordinates(expression)

    verify { style.setStyleSourceProperty("testId", "coordinates", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun coordinatesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)))
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)).toString(), testSource.coordinates?.toString())
    verify { style.getStyleSourceProperty("testId", "coordinates") }
  }

  @Test
  fun coordinatesAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.coordinatesAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "coordinates") }
  }

  @Test
  fun prefetchZoomDeltaSet() {
    val testSource = imageSource("testId") {
      prefetchZoomDelta(1L)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals("1", valueSlot.captured.toString())
  }

  @Test
  fun prefetchZoomDeltaAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = imageSource("testId") {
      prefetchZoomDelta(expression)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals("[+, 2, 3]", valueSlot.captured.toString())
  }

  @Test
  fun prefetchZoomDeltaSetAfterBind() {
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(1L)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun prefetchZoomDeltaAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(expression)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun prefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.prefetchZoomDelta?.toString())
    verify { style.getStyleSourceProperty("testId", "prefetch-zoom-delta") }
  }

  @Test
  fun prefetchZoomDeltaAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = imageSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.prefetchZoomDeltaAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "prefetch-zoom-delta") }
  }
  // Default source property getters tests

  @Test
  fun defaultPrefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), ImageSource.defaultPrefetchZoomDelta?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("image", "prefetch-zoom-delta") }
  }

  @Test
  fun defaultPrefetchZoomDeltaAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), ImageSource.defaultPrefetchZoomDeltaAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("image", "prefetch-zoom-delta") }
  }
}

// End of generated file.