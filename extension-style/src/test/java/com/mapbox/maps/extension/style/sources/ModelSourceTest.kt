package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.StylePropertyValue
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ModelSourceTest {
  private val style = mockk<StyleManagerInterface>(relaxUnitFun = true, relaxed = true)
  private val slot = slot<Value>()
  private val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)
  private val expectedDelta = mockk<Expected<Byte, String>>(relaxUnitFun = true, relaxed = true)
  private val stylePropertyValue = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns stylePropertyValue
    every { expected.error } returns null
    every { expectedDelta.error } returns null
  }

  @Test
  fun urlTest() {
    val testSource = modelSource("testId") {
      url("abc")
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(slot)) }
    assertTrue(slot.captured.toString().contains("uri=abc"))
    assertTrue(slot.captured.toString().contains("type=model"))
  }

  @Test
  fun pointTest() {
    val testSource = modelSource("testId") {
      position(
        listOf(-35.859375, 58.44773280389084)
      )
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(slot)) }
    assertTrue(slot.captured.toString().contains("position=[-35.859375, 58.44773280389084]"))
    assertTrue(slot.captured.toString().contains("type=model"))
  }

  @Test
  fun orientationTest() {
    val testSource = modelSource("testId") {
      orientation(
        listOf(1.0, 2.0, 3.0)
      )
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(slot)) }
    assertTrue(slot.captured.toString().contains("orientation=[1.0, 2.0, 3.0]"))
    assertTrue(slot.captured.toString().contains("type=model"))
  }

  @Test
  fun uriTest() {
    val testSource = modelSource("testId") {
      url("abc")
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(slot)) }
    assertTrue(slot.captured.toString().contains("uri=abc"))
    assertTrue(slot.captured.toString().contains("type=model"))
  }

  @Test
  fun getModelSourceTest() {
    val map = HashMap<String, Value>()
    map["type"] = Value("model")
    every { style.getStyleSourceProperties("id") } returns ExpectedFactory.createValue(Value(map))
    val source = style.getSource("id") as ModelSource
    assertNotNull(source)
    assertNotNull(source.delegate)
    assertEquals(style, source.delegate)
  }

  @Test
  fun updateURLTest() {
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.setURL("uri")
    val property = hashMapOf(Pair(ModelSource.Builder.URL, Value("uri")))
    val updateModel = hashMapOf(Pair(ModelSource.Builder.DEFAULT_MODEL_NAME, Value(property)))
    verify { style.setStyleSourceProperty("testId", ModelSource.Builder.MODELS, Value(updateModel)) }
  }

  @Test
  fun updatePointTest() {
    val position = listOf(-35.859375, 58.44773280389084)
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.setPosition(position)

    val property = hashMapOf(Pair(ModelSource.Builder.POSITION, Value(position.map { Value(it) })))
    val updateModel = hashMapOf(Pair(ModelSource.Builder.DEFAULT_MODEL_NAME, Value(property)))
    verify { style.setStyleSourceProperty("testId", ModelSource.Builder.MODELS, Value(updateModel)) }
  }

  @Test
  fun updateOrientationTest() {
    val orientation = listOf(1.0, 2.0, 3.0)
    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.setOrientation(orientation)

    val property =
      hashMapOf(Pair(ModelSource.Builder.ORIENTATION, Value(orientation.map { Value(it) })))
    val updateModel = hashMapOf(Pair(ModelSource.Builder.DEFAULT_MODEL_NAME, Value(property)))
    verify { style.setStyleSourceProperty("testId", ModelSource.Builder.MODELS, Value(updateModel)) }
  }

  @Test
  fun addModelTest() {
    val position = listOf(-35.859375, 58.44773280389084)
    val orientation = listOf(1.0, 2.0, 3.0)

    val testSource = modelSource("testId") {}
    testSource.bindTo(style)
    testSource.addModel("modelName", "uri", position, orientation)

    val property = hashMapOf(
      Pair(ModelSource.Builder.URL, Value("uri")),
      Pair(ModelSource.Builder.POSITION, Value(position.map { Value(it) })),
      Pair(ModelSource.Builder.ORIENTATION, Value(orientation.map { Value(it) }))
    )
    val updateModel = hashMapOf(Pair("modelName", Value(property)))
    verify { style.setStyleSourceProperty("testId", ModelSource.Builder.MODELS, Value(updateModel)) }
  }
}