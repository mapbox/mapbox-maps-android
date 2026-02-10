// This file is generated.

package com.mapbox.maps.testapp.style.sources.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for ModelSourceModel.
 */
@RunWith(AndroidJUnit4::class)
class ModelSourceModelTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun featurePropertiesTest() {
    val testValue = hashMapOf<String, Any>("name" to "test")
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .featureProperties(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue, source.models?.first()?.featureProperties)
  }

  @Test
  @UiThreadTest
  fun featurePropertiesAfterBindTest() {
    val testValue = hashMapOf<String, Any>("name" to "test")
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.featureProperties(testValue)

    assertEquals(testValue, source.models?.first()?.featureProperties)
  }

  @Test
  @UiThreadTest
  fun materialOverrideNamesTest() {
    val testValue = listOf("material1", "material2")
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .materialOverrideNames(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue.sorted(), source.models?.first()?.materialOverrideNames?.sorted())
  }

  @Test
  @UiThreadTest
  fun materialOverrideNamesAfterBindTest() {
    val testValue = listOf("material1", "material2")
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.materialOverrideNames(testValue)

    assertEquals(testValue.sorted(), source.models?.first()?.materialOverrideNames?.sorted())
  }

  @Test
  @UiThreadTest
  fun materialOverridesTest() {
    val testValue = listOf(ModelMaterialOverride.Builder("mat1").modelEmissiveStrength(1.5).build())
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .materialOverrides(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue, source.models?.first()?.materialOverrides)
  }

  @Test
  @UiThreadTest
  fun materialOverridesAfterBindTest() {
    val testValue = listOf(ModelMaterialOverride.Builder("mat1").modelEmissiveStrength(1.5).build())
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.materialOverrides(testValue)

    assertEquals(testValue, source.models?.first()?.materialOverrides)
  }

  @Test
  @UiThreadTest
  fun nodeOverrideNamesTest() {
    val testValue = listOf("node1", "node2")
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .nodeOverrideNames(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue.sorted(), source.models?.first()?.nodeOverrideNames?.sorted())
  }

  @Test
  @UiThreadTest
  fun nodeOverrideNamesAfterBindTest() {
    val testValue = listOf("node1", "node2")
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.nodeOverrideNames(testValue)

    assertEquals(testValue.sorted(), source.models?.first()?.nodeOverrideNames?.sorted())
  }

  @Test
  @UiThreadTest
  fun nodeOverridesTest() {
    val testValue = listOf(ModelNodeOverride.Builder("node1").orientation(listOf(0.0, 0.0, 45.0)).build())
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .nodeOverrides(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue, source.models?.first()?.nodeOverrides)
  }

  @Test
  @UiThreadTest
  fun nodeOverridesAfterBindTest() {
    val testValue = listOf(ModelNodeOverride.Builder("node1").orientation(listOf(0.0, 0.0, 45.0)).build())
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.nodeOverrides(testValue)

    assertEquals(testValue, source.models?.first()?.nodeOverrides)
  }

  @Test
  @UiThreadTest
  fun orientationTest() {
    val testValue = listOf(0.0, 0.0, 90.0)
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue, source.models?.first()?.orientation)
  }

  @Test
  @UiThreadTest
  fun orientationAfterBindTest() {
    val testValue = listOf(0.0, 0.0, 90.0)
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.orientation(testValue)

    assertEquals(testValue, source.models?.first()?.orientation)
  }

  @Test
  @UiThreadTest
  fun positionTest() {
    val testValue = listOf(122.4, 37.8)
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .orientation(listOf(0.0, 0.0, 0.0))
      .position(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue, source.models?.first()?.position)
  }

  @Test
  @UiThreadTest
  fun positionAfterBindTest() {
    val testValue = listOf(122.4, 37.8)
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.position(testValue)

    assertEquals(testValue, source.models?.first()?.position)
  }

  @Test
  @UiThreadTest
  fun uriTest() {
    val testValue = "https://example.com/model.glb"
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .uri(testValue)
      .build()

    source.models(listOf(model))

    assertEquals(testValue, source.models?.first()?.uri)
  }

  @Test
  @UiThreadTest
  fun uriAfterBindTest() {
    val testValue = "https://example.com/model.glb"
    val source = modelSource(SOURCE_ID) { url(TEST_URI) }
    setupSource(source)

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(0.0, 0.0))
      .orientation(listOf(0.0, 0.0, 0.0))
      .build()

    source.models(listOf(model))

    model.uri(testValue)

    assertEquals(testValue, source.models?.first()?.uri)
  }

  private companion object {
    const val SOURCE_ID = "testId"
    const val TEST_URI = "https://example.com/model.glb"
  }
}

// End of generated file.