package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.logW
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ModelSourceWrapperTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val expected: Expected<String, None> = mockk(relaxed = true)

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { style.styleSourceExists(any()) } returns true
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
    val modelSource = ModelSourceWrapper(SOURCE_ID, "uri", listOf(1.0, 2.0))
    val value = modelSource.toValue()
    assertEquals("{models={defaultModel={orientation=[0.0, 0.0, 0.0], position=[1.0, 2.0], uri=uri}}, type=model}", value.toString())
  }

  @Test
  fun testSetPosition() {
    val modelSource = ModelSourceWrapper(SOURCE_ID, "uri", listOf(1.0, 2.0))
    modelSource.bindTo(style)

    val position = arrayListOf(5.0)
    modelSource.setPosition(position)
    val property = hashMapOf(Pair(ModelSourceWrapper.POSITION, Value(position.map { Value(it) })), Pair(ModelSourceWrapper.URL, Value("uri")))
    val updateModel = hashMapOf(Pair(ModelSourceWrapper.DEFAULT_MODEL_NAME, Value(property)))
    verify { style.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
  }

  @Test
  fun testSourceNotReady() {
    every { style.styleSourceExists(any()) } returns false
    val modelSource = ModelSourceWrapper(SOURCE_ID, "uri", listOf(1.0, 2.0))
    modelSource.bindTo(style)

    val position = arrayListOf(5.0)
    modelSource.setPosition(position)
    val property = hashMapOf(Pair(ModelSourceWrapper.POSITION, Value(position.map { Value(it) })), Pair(ModelSourceWrapper.URL, Value("uri")))
    val updateModel = hashMapOf(Pair(ModelSourceWrapper.DEFAULT_MODEL_NAME, Value(property)))
    verify(exactly = 0) { style.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
  }

  @Test
  fun testUpdateStyle() {
    val modelSource = ModelSourceWrapper(SOURCE_ID, "uri", listOf(1.0, 2.0))
    modelSource.bindTo(style)
    val newStyle = mockk<StyleInterface>()
    every { newStyle.styleSourceExists(any()) } returns true
    every { newStyle.addStyleSource(any(), any()) } returns expected
    every { newStyle.setStyleSourceProperty(any(), any(), any()) } returns expected
    modelSource.updateStyle(newStyle)
    val position = arrayListOf(5.0)
    modelSource.setPosition(position)
    val property = hashMapOf(Pair(ModelSourceWrapper.POSITION, Value(position.map { Value(it) })), Pair(ModelSourceWrapper.URL, Value("uri")))
    val updateModel = hashMapOf(Pair(ModelSourceWrapper.DEFAULT_MODEL_NAME, Value(property)))
    verify(exactly = 0) { style.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
    verify(exactly = 1) { newStyle.setStyleSourceProperty(SOURCE_ID, ModelSourceWrapper.MODELS, Value(updateModel)) }
  }

  companion object {
    private const val SOURCE_ID = "modelLocationSourceId"
  }
}