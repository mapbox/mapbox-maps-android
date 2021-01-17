package com.mapbox.maps.plugin.location

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ModelLocationSourceWrapperTest {

  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val expected: Expected<Void, String> = mockk(relaxed = true)

  @Before
  fun setup() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { expected.error } returns null
  }

  @Test
  fun testValue() {
    val modelSource = ModelLocationSourceWrapper(SOURCE_ID, "uri", listOf(1.0, 2.0))
    val value = modelSource.toValue()
    assertEquals("{models={defaultModel={orientation=[0.0, 0.0, 0.0], position=[1.0, 2.0], uri=uri}}, type=model}", value.toString())
  }

  @Test
  fun testSetPosition() {
    val styleState = mockk<MapStyleStateDelegate>()
    every { styleState.isFullyLoaded() } returns true
    val modelSource = ModelLocationSourceWrapper(SOURCE_ID, "uri", listOf(1.0, 2.0))
    modelSource.bindTo(style, styleState)

    val position = arrayListOf(5.0)
    modelSource.setPosition(position)
    val property = hashMapOf(Pair(ModelLocationSourceWrapper.POSITION, Value(position.map { Value(it) })), Pair(ModelLocationSourceWrapper.URL, Value("uri")))
    val updateModel = hashMapOf(Pair(ModelLocationSourceWrapper.DEFAULT_MODEL_NAME, Value(property)))
    verify { style.setStyleSourceProperty(SOURCE_ID, ModelLocationSourceWrapper.MODELS, Value(updateModel)) }
  }

  companion object {
    private const val SOURCE_ID = "modelLocationSourceId"
  }
}