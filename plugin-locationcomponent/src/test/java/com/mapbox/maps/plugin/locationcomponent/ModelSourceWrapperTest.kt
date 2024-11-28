package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.logE
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ModelSourceWrapperTest {

  private val style: MapboxStyleManager = mockk(relaxed = true)
  private val expected: Expected<String, None> = mockk(relaxed = true)

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun testValue() {
    val modelSource = ModelSourceWrapper(
      sourceId = SOURCE_ID,
      url = "uri",
      position = listOf(1.0, 2.0),
      nodeOverrides = listOf("node1", "node2"),
      materialOverrides = listOf("material1", "material2")
    )
    val value = modelSource.toValue()
    assertEquals("{models={defaultModel={orientation=[0.0, 0.0, 0.0], nodeOverrides=[node1, node2], position=[1.0, 2.0], materialOverrides=[material1, material2], uri=uri}}, type=model}", value.toString())
  }

  @Test
  fun testSetPosition() {
    val modelSource = ModelSourceWrapper(
      sourceId = SOURCE_ID,
      url = "uri",
      position = listOf(1.0, 2.0),
      nodeOverrides = listOf("node1", "node2"),
      materialOverrides = listOf("material1", "material2")
    )
    modelSource.bindTo(style)

    val position = arrayListOf(5.0, 5.0)
    val orientation = arrayListOf(0.0, 0.0, 5.0)
    modelSource.setPositionAndOrientation(position, orientation)
    val property = hashMapOf(
      Pair(ModelSourceWrapper.POSITION, Value(position.map(::Value))),
      Pair(ModelSourceWrapper.ORIENTATION, Value(orientation.map(::Value))),
      Pair(ModelSourceWrapper.URL, Value("uri"))
    )
    val updateModel = hashMapOf(Pair(ModelSourceWrapper.DEFAULT_MODEL_NAME, Value(property)))
    verify { style.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
  }

  @Test
  fun testSetPropertyFailed() {
    val modelSource = ModelSourceWrapper(
      sourceId = SOURCE_ID,
      url = "uri",
      position = listOf(1.0, 2.0),
      nodeOverrides = listOf("node1", "node2"),
      materialOverrides = listOf("material1", "material2")
    )
    every { style.setStyleSourceProperty(any(), any(), any()) } returns ExpectedFactory.createError("error")
    modelSource.bindTo(style)

    val position = arrayListOf(5.0, 5.0)
    val orientation = arrayListOf(0.0, 0.0, 5.0)
    modelSource.setPositionAndOrientation(position, orientation)
    val property = hashMapOf(
      Pair(ModelSourceWrapper.POSITION, Value(position.map(::Value))),
      Pair(ModelSourceWrapper.ORIENTATION, Value(orientation.map(::Value))),
      Pair(ModelSourceWrapper.URL, Value("uri"))
    )
    val updateModel = hashMapOf(Pair(ModelSourceWrapper.DEFAULT_MODEL_NAME, Value(property)))
    verify(exactly = 1) { style.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
    verify(exactly = 1) { logE(any(), any()) }
  }

  @Test
  fun testUpdateStyle() {
    val modelSource = ModelSourceWrapper(
      sourceId = SOURCE_ID,
      url = "uri",
      position = listOf(1.0, 2.0),
      nodeOverrides = listOf("node1", "node2"),
      materialOverrides = listOf("material1", "material2")
    )
    modelSource.bindTo(style)
    val newStyle = mockk<MapboxStyleManager>()
    every { newStyle.addStyleSource(any(), any()) } returns expected
    every { newStyle.setStyleSourceProperty(any(), any(), any()) } returns expected
    modelSource.updateStyle(newStyle)
    val position = arrayListOf(5.0, 5.0)
    val orientation = arrayListOf(0.0, 0.0, 5.0)
    modelSource.setPositionAndOrientation(position, orientation)
    val property = hashMapOf(
      Pair(ModelSourceWrapper.POSITION, Value(position.map(::Value))),
      Pair(ModelSourceWrapper.ORIENTATION, Value(orientation.map(::Value))),
      Pair(ModelSourceWrapper.URL, Value("uri"))
    )
    val updateModel = hashMapOf(Pair(ModelSourceWrapper.DEFAULT_MODEL_NAME, Value(property)))
    verify(exactly = 0) { style.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
    verify(exactly = 1) { newStyle.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
  }

  companion object {
    private const val SOURCE_ID = "modelLocationSourceId"
  }
}