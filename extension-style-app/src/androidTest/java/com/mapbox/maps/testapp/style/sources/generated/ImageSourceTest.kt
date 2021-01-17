// This file is generated.

package com.mapbox.maps.testapp.style.sources.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for ImageSource.
 */
@RunWith(AndroidJUnit4::class)
class ImageSourceTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun urlTest() {
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
      url("abc")
    }
    setupSource(testSource)
    assertEquals("abc", testSource.url)
  }

  @Test
  @UiThreadTest
  fun urlAfterBindTest() {
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
    }
    setupSource(testSource)
    testSource.url("abc")
    assertEquals("abc", testSource.url)
  }

  @Test
  @UiThreadTest
  fun urlAsExpressionTest() {
    val expression = literal("abc")
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
      url(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.urlAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun coordinatesTest() {
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
      coordinates(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)))
    }
    setupSource(testSource)
    assertEquals(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)), testSource.coordinates)
  }

  @Test
  @UiThreadTest
  fun coordinatesAfterBindTest() {
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
    }
    setupSource(testSource)
    testSource.coordinates(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)))
    assertEquals(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)), testSource.coordinates)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/500")
  fun coordinatesAsExpressionTest() {
    val expression = literal(listOf(listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0), listOf(0.0, 1.0)))
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
      coordinates(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.coordinatesAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaTest() {
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
      prefetchZoomDelta(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaAfterBindTest() {
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
    }
    setupSource(testSource)
    testSource.prefetchZoomDelta(1L)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaAsExpressionTest() {
    val expression = literal(1L)
    val testSource = imageSource("testId") {
      url(TEST_URI)
      coordinates(TEST_COORDINATES)
      prefetchZoomDelta(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.prefetchZoomDeltaAsExpression?.toString())
  }

  // Default source properties getter tests

  @Test
  @UiThreadTest
  fun defaultSourcePropertiesTest() {
    assertNotNull("defaultPrefetchZoomDelta should not be null", ImageSource.defaultPrefetchZoomDelta)
    assertNotNull("defaultPrefetchZoomDeltaAsExpression should not be null", ImageSource.defaultPrefetchZoomDeltaAsExpression)
  }

  companion object {
    private const val TEST_URI = "https://raw.githubusercontent.com/mapbox/mapbox-gl-native-android/master/MapboxGLAndroidSDKTestApp/src/main/assets/earthquakes.geojson"
    private val TEST_COORDINATES = listOf(
      listOf(-35.859375, 58.44773280389084),
      listOf(-16.171875, 58.44773280389084),
      listOf(-16.171875, 54.7246201949245),
      listOf(-35.859375, 54.7246201949245)
    )
  }
}

// End of generated file.