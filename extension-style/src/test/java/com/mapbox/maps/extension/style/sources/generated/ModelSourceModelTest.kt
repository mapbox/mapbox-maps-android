// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.common.toValue
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class ModelSourceModelTest {
  private val modelSource = mockk<ModelSource>(relaxUnitFun = true, relaxed = true)
  private val propertyValueSlot = slot<PropertyValue<*>>()

  @Before
  fun prepareTest() {
    every { modelSource.setProperty(any()) } returns Unit
    every { modelSource.getPropertyValue<HashMap<String, HashMap<String, Any>>>(any()) } returns null
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun idTest() {
    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()

    assertEquals("test-id", model.id)
  }

  @Test
  fun featurePropertiesSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .featureProperties(hashMapOf<String, Any>("name" to "test"))
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun featurePropertiesSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.featureProperties(hashMapOf<String, Any>("updated" to true))

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun featurePropertiesGet() {
    val testValue = hashMapOf("name" to "test".toValue()).toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("featureProperties" to testValue)

    model.bindTo(modelSource)

    assertEquals(testValue, model.featureProperties)
  }

  @Test
  fun materialOverrideNamesSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .materialOverrideNames(listOf("material1", "material2"))
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun materialOverrideNamesSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.materialOverrideNames(listOf("material3"))

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun materialOverrideNamesGet() {
    val testValue = listOf("material1".toValue(), "material2".toValue()).toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("materialOverrideNames" to testValue)

    model.bindTo(modelSource)

    assertEquals(testValue, model.materialOverrideNames)
  }

  @Test
  fun materialOverridesSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .materialOverrides(listOf(ModelMaterialOverride.Builder("mat1").modelEmissiveStrength(1.5).build()))
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun materialOverridesSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.materialOverrides(listOf(ModelMaterialOverride.Builder("mat2").modelEmissiveStrength(2.0).build()))

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun materialOverridesGet() {
    val override = ModelMaterialOverride.Builder("mat1").modelEmissiveStrength(1.5).build()
    val testValue = hashMapOf("mat1" to override.toValue()).toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("materialOverrides" to testValue)

    model.bindTo(modelSource)

    assertEquals(listOf(override), model.materialOverrides)
  }

  @Test
  fun nodeOverrideNamesSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .nodeOverrideNames(listOf("node1", "node2"))
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun nodeOverrideNamesSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.nodeOverrideNames(listOf("node3"))

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun nodeOverrideNamesGet() {
    val testValue = listOf("node1".toValue(), "node2".toValue()).toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("nodeOverrideNames" to testValue)

    model.bindTo(modelSource)

    assertEquals(testValue, model.nodeOverrideNames)
  }

  @Test
  fun nodeOverridesSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .nodeOverrides(listOf(ModelNodeOverride.Builder("node1").orientation(listOf(0.0, 0.0, 45.0)).build()))
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun nodeOverridesSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.nodeOverrides(listOf(ModelNodeOverride.Builder("node2").orientation(listOf(0.0, 45.0, 0.0)).build()))

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun nodeOverridesGet() {
    val override = ModelNodeOverride.Builder("node1").orientation(listOf(0.0, 0.0, 45.0)).build()
    val testValue = hashMapOf("node1" to override.toValue()).toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("nodeOverrides" to testValue)

    model.bindTo(modelSource)

    assertEquals(listOf(override), model.nodeOverrides)
  }

  @Test
  fun orientationSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .orientation(listOf(0.0, 0.0, 90.0))
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun orientationSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.orientation(listOf(45.0, 0.0, 90.0))

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun orientationGet() {
    val testValue = listOf(0.0.toValue(), 0.0.toValue(), 90.0.toValue()).toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("orientation" to testValue)

    model.bindTo(modelSource)

    assertEquals(testValue, model.orientation)
  }

  @Test
  fun positionSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .position(listOf(122.4, 37.8))
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun positionSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.position(listOf(-122.5, 37.9))

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun positionGet() {
    val testValue = listOf(122.4.toValue(), 37.8.toValue()).toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("position" to testValue)

    model.bindTo(modelSource)

    assertEquals(testValue, model.position)
  }

  @Test
  fun uriSet() {
    every { modelSource.setProperty(capture(propertyValueSlot)) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    verify { modelSource.setProperty(any()) }
    assertEquals("models", propertyValueSlot.captured.propertyName)
  }

  @Test
  fun uriSetAfterBind() {
    every { modelSource.setProperty(any()) } returns Unit

    val model = ModelSourceModel.Builder("test-id")
      .uri("https://example.com/model.glb")
      .build()
    model.bindTo(modelSource)

    val localSlot = slot<PropertyValue<*>>()
    every { modelSource.setProperty(capture(localSlot)) } returns Unit

    model.uri("https://example.com/updated.glb")

    verify { modelSource.setProperty(any()) }
    assertEquals("models", localSlot.captured.propertyName)
  }

  @Test
  fun uriGet() {
    val testValue = "https://example.com/model.glb".toValue()
    val model = spyk(
      ModelSourceModel.Builder("test-id")
        .uri("https://example.com/model.glb")
        .build()
    )
    every { model.getProperties() } returns hashMapOf("uri" to testValue)

    model.bindTo(modelSource)

    assertEquals(testValue, model.uri)
  }
}

// End of generated file.