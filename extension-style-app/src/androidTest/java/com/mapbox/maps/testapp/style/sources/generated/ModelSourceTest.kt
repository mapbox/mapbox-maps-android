// This file is generated.

package com.mapbox.maps.testapp.style.sources.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for ModelSource.
 */
@RunWith(AndroidJUnit4::class)
class ModelSourceTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun urlTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
      url("abc")
    }
    setupSource(testSource)
    assertEquals("abc", testSource.url)
  }

  @Test
  @UiThreadTest
  fun urlAfterBindTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
    }
    setupSource(testSource)
    testSource.url("abc")
    assertEquals("abc", testSource.url)
  }

  @Test
  @UiThreadTest
  fun maxzoomTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
      maxzoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.maxzoom)
  }

  @Test
  @UiThreadTest
  fun maxzoomAfterBindTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
    }
    setupSource(testSource)
    testSource.maxzoom(1L)
    assertEquals(1L, testSource.maxzoom)
  }

  @Test
  @UiThreadTest
  fun minzoomTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
      minzoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.minzoom)
  }

  @Test
  @UiThreadTest
  fun minzoomAfterBindTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
    }
    setupSource(testSource)
    testSource.minzoom(1L)
    assertEquals(1L, testSource.minzoom)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun tilesTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
      tiles(listOf("a", "b", "c"))
    }
    setupSource(testSource)
    assertEquals(listOf("a", "b", "c"), testSource.tiles)
  }

  @Test
  @UiThreadTest
  fun tilesAfterBindTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf(TEST_URI))
      batched(true)
    }
    setupSource(testSource)
    testSource.tiles(listOf("a", "b", "c"))
    assertEquals(listOf("a", "b", "c"), testSource.tiles)
  }

  @Test
  @UiThreadTest
  fun modelsTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
      models(listOf(ModelSourceModel.Builder("model").uri("something://somewhere").orientation(listOf(0.0, 0.0, 0.0)).position(listOf(0.0, 0.0)).build()))
    }
    setupSource(testSource)
  }

  @Test
  @UiThreadTest
  fun modelsAfterBindTest() {
    val testSource = modelSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.models(listOf(ModelSourceModel.Builder("model").uri("something://somewhere").orientation(listOf(0.0, 0.0, 0.0)).position(listOf(0.0, 0.0)).build()))
  }

  // Default source properties getter tests

  @Test
  @UiThreadTest
  fun defaultSourcePropertiesTest() {
    assertNotNull("defaultMaxzoom should not be null", ModelSource.defaultMaxzoom)
    assertNotNull("defaultMinzoom should not be null", ModelSource.defaultMinzoom)
  }

  private companion object {
    const val TEST_URI = "https://raw.githubusercontent.com/mapbox/mapbox-gl-native-android/master/MapboxGLAndroidSDKTestApp/src/main/assets/earthquakes.geojson"
    const val SOURCE_ID = "testId"
  }
}

// End of generated file.