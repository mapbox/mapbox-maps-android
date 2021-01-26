package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Expected
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
class ModelSourceWrapperTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  @Before
  fun setup() {
    every { style.styleSourceExists(any()) } returns true
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
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

  companion object {
    private const val SOURCE_ID = "modelLocationSourceId"
  }
}