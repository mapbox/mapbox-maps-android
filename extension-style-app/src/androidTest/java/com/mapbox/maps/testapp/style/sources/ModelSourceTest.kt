package com.mapbox.maps.testapp.style.sources

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.sources.ModelSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.sources.modelSource
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
    val testSource = modelSource("testId") {
      url(TEST_URI)
      position(listOf(-35.859375, 58.44773280389084))
    }
    setupSource(testSource)
    assertEquals(TEST_URI, testSource.getURL())
    testSource.setURL(NEW_URI)
    assertEquals(NEW_URI, testSource.getURL())
  }

  @Test
  @UiThreadTest
  fun positionTest() {
    val testSource = modelSource("testId") {
      url(TEST_URI)
      position(listOf(-35.859375, 58.44773280389084))
    }
    setupSource(testSource)
    assertEquals(listOf(-35.859375, 58.44773280389084), testSource.getPosition())
    testSource.setPosition(listOf(0.0, 0.0))
    assertEquals(listOf(0.0, 0.0), testSource.getPosition())
  }

  @Test
  @UiThreadTest
  fun orientationTest() {
    val testSource = modelSource("testId") {
      url(TEST_URI)
      position(listOf(-35.859375, 58.44773280389084))
      orientation(listOf(1.0, 2.0, 3.0))
    }
    setupSource(testSource)
    assertEquals(listOf(1.0, 2.0, 3.0), testSource.getOrientation())
    testSource.setOrientation(listOf(0.0, 0.0, 0.0))
    assertEquals(listOf(0.0, 0.0, 0.0), testSource.getOrientation())
  }

  @Test
  @UiThreadTest
  fun getModelSourceTest() {
    val testSource = modelSource("testId") {
      url(TEST_URI)
      position(listOf(-35.859375, 58.44773280389084))
    }
    setupSource(testSource)
    val source = style.getSource("testId") as ModelSource
    assertNotNull(source)
  }

  companion object {
    private const val TEST_URI = "asset://race_car_model.gltf"
    private const val NEW_URI = "asset://arrow.gltf"
  }
}