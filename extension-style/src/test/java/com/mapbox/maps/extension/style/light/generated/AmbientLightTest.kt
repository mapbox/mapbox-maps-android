// This file is generated.

package com.mapbox.maps.extension.style.light.generated

import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.light.setLights
import com.mapbox.maps.extension.style.types.transitionOptions
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.logE
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AmbientLightTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()

  @Before
  fun prepareTest() {
    every { style.getStyleLightProperty(any(), any()) } returns styleProperty
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.setStyleLightProperty(any(), any(), any()) } returns expected
    every { style.setStyleLights(any()) } returns expected
    every { expected.error } returns null
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun colorAsColorIntSet() {
    val light = ambientLight("id") {
      color(Color.CYAN)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=[rgba, 0, 255, 255, 1.0]"))
  }

  @Test
  fun colorAsColorIntSetAfterInitialization() {
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    light.color(Color.CYAN)
    verify { style.setStyleLightProperty("id", "color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun colorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(Color.RED, light.colorAsColorInt!!)
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorSet() {
    val light = ambientLight("id") {
      color("rgba(0, 0, 0, 1)")
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorSetAfterInitialization() {
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    light.color("rgba(0, 0, 0, 1)")
    verify { style.setStyleLightProperty("id", "color", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals("rgba(0, 0, 0, 1)".toString(), light.color!!.toString())
    verify { style.getStyleLightProperty("id", "color") }
  }
  // Expression Tests

  @Test
  fun colorAsExpressionSet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = ambientLight("id") {
      color(expression)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun colorAsExpressionSetAfterInitialization() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    light.color(expression)
    verify { style.setStyleLightProperty("id", "color", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun colorAsExpressionGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.colorAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorAsExpressionGetNull() {
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.colorAsExpression)
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.colorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", light.color)
    assertEquals(Color.BLACK, light.colorAsColorInt)
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorTransitionSet() {
    val light = ambientLight("id") {
      colorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionSetAfterInitialization() {
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    light.colorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("id", "color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = ambientLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.colorTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "color-transition") }
  }

  @Test
  fun colorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = ambientLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.colorTransition)
    verify { style.getStyleLightProperty("id", "color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun colorTransitionGetException() {
    val light = ambientLight("id") {}
    light.colorTransition
  }

  @Test
  fun colorTransitionSetDsl() {
    val light = ambientLight("id") {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }

  @Test
  fun intensitySet() {
    val light = ambientLight("id") {
      intensity(1.0)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity=1.0"))
  }

  @Test
  fun intensitySetAfterInitialization() {
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    light.intensity(1.0)
    verify { style.setStyleLightProperty("id", "intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun intensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(1.0.toString(), light.intensity!!.toString())
    verify { style.getStyleLightProperty("id", "intensity") }
  }
  // Expression Tests

  @Test
  fun intensityAsExpressionSet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = ambientLight("id") {
      intensity(expression)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun intensityAsExpressionSetAfterInitialization() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    light.intensity(expression)
    verify { style.setStyleLightProperty("id", "intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun intensityAsExpressionGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.intensityAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityAsExpressionGetNull() {
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.intensityAsExpression)
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    assertEquals(1.0, light.intensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, light.intensity!!, 1E-5)
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityTransitionSet() {
    val light = ambientLight("id") {
      intensityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionSetAfterInitialization() {
    val light = ambientLight("id") { }
    style.setLights(listOf(light))
    light.intensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("id", "intensity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = ambientLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.intensityTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "intensity-transition") }
  }

  @Test
  fun intensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = ambientLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.intensityTransition)
    verify { style.getStyleLightProperty("id", "intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun intensityTransitionGetException() {
    val light = ambientLight("id") {}
    light.intensityTransition
  }

  @Test
  fun intensityTransitionSetDsl() {
    val light = ambientLight("id") {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }
}

// End of generated file.