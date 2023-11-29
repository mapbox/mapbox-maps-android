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
import com.mapbox.maps.extension.style.layers.properties.generated.Anchor
import com.mapbox.maps.extension.style.light.LightPosition
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
class FlatLightTest {
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
  fun anchorSet() {
    val light = flatLight("id") {
      anchor(Anchor.MAP)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("anchor=map"))
  }

  @Test
  fun anchorSetAfterInitialization() {
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    light.anchor(Anchor.MAP)
    verify { style.setStyleLightProperty("id", "anchor", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("map"))
  }

  @Test
  fun anchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(Anchor.MAP, light.anchor!!)
    verify { style.getStyleLightProperty("id", "anchor") }
  }
  // Expression Tests

  @Test
  fun anchorAsExpressionSet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = flatLight("id") {
      anchor(expression)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun anchorAsExpressionSetAfterInitialization() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    light.anchor(expression)
    verify { style.setStyleLightProperty("id", "anchor", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun anchorAsExpressionGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.anchorAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "anchor") }
  }

  @Test
  fun anchorAsExpressionGetNull() {
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.anchorAsExpression)
    verify { style.getStyleLightProperty("id", "anchor") }
  }

  @Test
  fun anchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(value.toString(), light.anchorAsExpression?.toString())
    assertEquals(Anchor.MAP.value, light.anchorAsExpression.toString())
    assertEquals(Anchor.MAP, light.anchor)
    verify { style.getStyleLightProperty("id", "anchor") }
  }

  @Test
  fun colorAsColorIntSet() {
    val light = flatLight("id") {
      color(Color.CYAN)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=[rgba, 0, 255, 255, 1.0]"))
  }

  @Test
  fun colorAsColorIntSetAfterInitialization() {
    val light = flatLight("id") { }
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

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(Color.RED, light.colorAsColorInt!!)
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorSet() {
    val light = flatLight("id") {
      color("rgba(0, 0, 0, 1)")
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorSetAfterInitialization() {
    val light = flatLight("id") { }
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

    val light = flatLight("id") { }
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

    val light = flatLight("id") {
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

    val light = flatLight("id") { }
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

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.colorAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorAsExpressionGetNull() {
    val light = flatLight("id") { }
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

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.colorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", light.color)
    assertEquals(Color.BLACK, light.colorAsColorInt)
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorTransitionSet() {
    val light = flatLight("id") {
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
    val light = flatLight("id") { }
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
    val light = flatLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.colorTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "color-transition") }
  }

  @Test
  fun colorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = flatLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.colorTransition)
    verify { style.getStyleLightProperty("id", "color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun colorTransitionGetException() {
    val light = flatLight("id") {}
    light.colorTransition
  }

  @Test
  fun colorTransitionSetDsl() {
    val light = flatLight("id") {
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
    val light = flatLight("id") {
      intensity(1.0)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity=1.0"))
  }

  @Test
  fun intensitySetAfterInitialization() {
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    light.intensity(1.0)
    verify { style.setStyleLightProperty("id", "intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun intensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val light = flatLight("id") { }
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

    val light = flatLight("id") {
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

    val light = flatLight("id") { }
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

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.intensityAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityAsExpressionGetNull() {
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.intensityAsExpression)
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(1.0, light.intensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, light.intensity!!, 1E-5)
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityTransitionSet() {
    val light = flatLight("id") {
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
    val light = flatLight("id") { }
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
    val light = flatLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.intensityTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "intensity-transition") }
  }

  @Test
  fun intensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = flatLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.intensityTransition)
    verify { style.getStyleLightProperty("id", "intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun intensityTransitionGetException() {
    val light = flatLight("id") {}
    light.intensityTransition
  }

  @Test
  fun intensityTransitionSetDsl() {
    val light = flatLight("id") {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun positionSetDsl() {
    val light = flatLight("id") {
      position(1.0, 2.0, 3.0)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position=[1.0, 2.0, 3.0]"))
  }

  @Test
  fun positionSet() {
    val light = flatLight("id") {
      position(LightPosition(0.0, 1.0, 2.0))
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position=[0.0, 1.0, 2.0]"))
  }

  @Test
  fun positionSetAfterInitialization() {
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    light.position(LightPosition(0.0, 1.0, 2.0))
    verify { style.setStyleLightProperty("id", "position", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("[0.0, 1.0, 2.0]"))
  }

  @Test
  fun positionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(LightPosition(0.0, 1.0, 2.0))

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(LightPosition(0.0, 1.0, 2.0).toString(), light.position!!.toString())
    verify { style.getStyleLightProperty("id", "position") }
  }
  // Expression Tests

  @Test
  fun positionAsExpressionSet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = flatLight("id") {
      position(expression)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun positionAsExpressionSetAfterInitialization() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    light.position(expression)
    verify { style.setStyleLightProperty("id", "position", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun positionAsExpressionGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.positionAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "position") }
  }

  @Test
  fun positionAsExpressionGetNull() {
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.positionAsExpression)
    verify { style.getStyleLightProperty("id", "position") }
  }

  @Test
  fun positionAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(LightPosition(0.0, 1.0, 2.0))
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    assertEquals("[literal, [0.0, 1.0, 2.0]]", light.positionAsExpression.toString())
    assertEquals(LightPosition(0.0, 1.0, 2.0), light.position)
    verify { style.getStyleLightProperty("id", "position") }
  }

  @Test
  fun positionTransitionSet() {
    val light = flatLight("id") {
      positionTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position-transition={duration=100, delay=200}"))
  }

  @Test
  fun positionTransitionSetAfterInitialization() {
    val light = flatLight("id") { }
    style.setLights(listOf(light))
    light.positionTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("id", "position-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun positionTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = flatLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.positionTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "position-transition") }
  }

  @Test
  fun positionTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = flatLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.positionTransition)
    verify { style.getStyleLightProperty("id", "position-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun positionTransitionGetException() {
    val light = flatLight("id") {}
    light.positionTransition
  }

  @Test
  fun positionTransitionSetDsl() {
    val light = flatLight("id") {
      positionTransition {
        duration(100)
        delay(200)
      }
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position-transition={duration=100, delay=200}"))
  }
}

// End of generated file.