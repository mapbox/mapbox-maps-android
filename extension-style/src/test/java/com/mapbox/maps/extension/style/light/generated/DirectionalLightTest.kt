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
class DirectionalLightTest {
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
  fun castShadowsSet() {
    val light = directionalLight("id") {
      castShadows(true)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("cast-shadows=true"))
  }

  @Test
  fun castShadowsSetAfterInitialization() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.castShadows(true)
    verify { style.setStyleLightProperty("id", "cast-shadows", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("true"))
  }

  @Test
  fun castShadowsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(true.toString(), light.castShadows!!.toString())
    verify { style.getStyleLightProperty("id", "cast-shadows") }
  }
  // Expression Tests

  @Test
  fun castShadowsAsExpressionSet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = directionalLight("id") {
      castShadows(expression)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun castShadowsAsExpressionSetAfterInitialization() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.castShadows(expression)
    verify { style.setStyleLightProperty("id", "cast-shadows", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun castShadowsAsExpressionGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.castShadowsAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "cast-shadows") }
  }

  @Test
  fun castShadowsAsExpressionGetNull() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.castShadowsAsExpression)
    verify { style.getStyleLightProperty("id", "cast-shadows") }
  }

  @Test
  fun castShadowsAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertTrue(light.castShadowsAsExpression.toString().contains("true"))
    assertEquals(true, light.castShadows)
    verify { style.getStyleLightProperty("id", "cast-shadows") }
  }

  @Test
  fun colorAsColorIntSet() {
    val light = directionalLight("id") {
      color(Color.CYAN)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=[rgba, 0, 255, 255, 1.0]"))
  }

  @Test
  fun colorAsColorIntSetAfterInitialization() {
    val light = directionalLight("id") { }
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

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(Color.RED, light.colorAsColorInt!!)
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorSet() {
    val light = directionalLight("id") {
      color("rgba(0, 0, 0, 1)")
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorSetAfterInitialization() {
    val light = directionalLight("id") { }
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

    val light = directionalLight("id") { }
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

    val light = directionalLight("id") {
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

    val light = directionalLight("id") { }
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

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.colorAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorAsExpressionGetNull() {
    val light = directionalLight("id") { }
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

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.colorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", light.color)
    assertEquals(Color.BLACK, light.colorAsColorInt)
    verify { style.getStyleLightProperty("id", "color") }
  }

  @Test
  fun colorTransitionSet() {
    val light = directionalLight("id") {
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
    val light = directionalLight("id") { }
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
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.colorTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "color-transition") }
  }

  @Test
  fun colorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.colorTransition)
    verify { style.getStyleLightProperty("id", "color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun colorTransitionGetException() {
    val light = directionalLight("id") {}
    light.colorTransition
  }

  @Test
  fun colorTransitionSetDsl() {
    val light = directionalLight("id") {
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
  fun directionSet() {
    val light = directionalLight("id") {
      direction(listOf(0.0, 1.0))
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction=[0.0, 1.0]"))
  }

  @Test
  fun directionSetAfterInitialization() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.direction(listOf(0.0, 1.0))
    verify { style.setStyleLightProperty("id", "direction", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("[0.0, 1.0]"))
  }

  @Test
  fun directionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(listOf(0.0, 1.0).toString(), light.direction!!.toString())
    verify { style.getStyleLightProperty("id", "direction") }
  }
  // Expression Tests

  @Test
  fun directionAsExpressionSet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = directionalLight("id") {
      direction(expression)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun directionAsExpressionSetAfterInitialization() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.direction(expression)
    verify { style.setStyleLightProperty("id", "direction", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun directionAsExpressionGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.directionAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "direction") }
  }

  @Test
  fun directionAsExpressionGetNull() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.directionAsExpression)
    verify { style.getStyleLightProperty("id", "direction") }
  }

  @Test
  fun directionAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals("[literal, [0.0, 1.0]]", light.directionAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), light.direction)
    verify { style.getStyleLightProperty("id", "direction") }
  }

  @Test
  fun directionTransitionSet() {
    val light = directionalLight("id") {
      directionTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction-transition={duration=100, delay=200}"))
  }

  @Test
  fun directionTransitionSetAfterInitialization() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.directionTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("id", "direction-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun directionTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.directionTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "direction-transition") }
  }

  @Test
  fun directionTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.directionTransition)
    verify { style.getStyleLightProperty("id", "direction-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun directionTransitionGetException() {
    val light = directionalLight("id") {}
    light.directionTransition
  }

  @Test
  fun directionTransitionSetDsl() {
    val light = directionalLight("id") {
      directionTransition {
        duration(100)
        delay(200)
      }
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction-transition={duration=100, delay=200}"))
  }

  @Test
  fun intensitySet() {
    val light = directionalLight("id") {
      intensity(1.0)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity=1.0"))
  }

  @Test
  fun intensitySetAfterInitialization() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.intensity(1.0)
    verify { style.setStyleLightProperty("id", "intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun intensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val light = directionalLight("id") { }
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

    val light = directionalLight("id") {
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

    val light = directionalLight("id") { }
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

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.intensityAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityAsExpressionGetNull() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.intensityAsExpression)
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(1.0, light.intensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, light.intensity!!, 1E-5)
    verify { style.getStyleLightProperty("id", "intensity") }
  }

  @Test
  fun intensityTransitionSet() {
    val light = directionalLight("id") {
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
    val light = directionalLight("id") { }
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
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.intensityTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "intensity-transition") }
  }

  @Test
  fun intensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.intensityTransition)
    verify { style.getStyleLightProperty("id", "intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun intensityTransitionGetException() {
    val light = directionalLight("id") {}
    light.intensityTransition
  }

  @Test
  fun intensityTransitionSetDsl() {
    val light = directionalLight("id") {
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
  fun shadowIntensitySet() {
    val light = directionalLight("id") {
      shadowIntensity(1.0)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("shadow-intensity=1.0"))
  }

  @Test
  fun shadowIntensitySetAfterInitialization() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.shadowIntensity(1.0)
    verify { style.setStyleLightProperty("id", "shadow-intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun shadowIntensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(1.0.toString(), light.shadowIntensity!!.toString())
    verify { style.getStyleLightProperty("id", "shadow-intensity") }
  }
  // Expression Tests

  @Test
  fun shadowIntensityAsExpressionSet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = directionalLight("id") {
      shadowIntensity(expression)
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun shadowIntensityAsExpressionSetAfterInitialization() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.shadowIntensity(expression)
    verify { style.setStyleLightProperty("id", "shadow-intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains(expression.toString()))
  }

  @Test
  fun shadowIntensityAsExpressionGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(expression.toString(), light.shadowIntensityAsExpression?.toString())
    verify { style.getStyleLightProperty("id", "shadow-intensity") }
  }

  @Test
  fun shadowIntensityAsExpressionGetNull() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(null, light.shadowIntensityAsExpression)
    verify { style.getStyleLightProperty("id", "shadow-intensity") }
  }

  @Test
  fun shadowIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    assertEquals(1.0, light.shadowIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, light.shadowIntensity!!, 1E-5)
    verify { style.getStyleLightProperty("id", "shadow-intensity") }
  }

  @Test
  fun shadowIntensityTransitionSet() {
    val light = directionalLight("id") {
      shadowIntensityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("shadow-intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun shadowIntensityTransitionSetAfterInitialization() {
    val light = directionalLight("id") { }
    style.setLights(listOf(light))
    light.shadowIntensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("id", "shadow-intensity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun shadowIntensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(transition.toValue().toString(), light.shadowIntensityTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("id", "shadow-intensity-transition") }
  }

  @Test
  fun shadowIntensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = directionalLight("id") {}
    style.setLights(listOf(light))
    assertEquals(null, light.shadowIntensityTransition)
    verify { style.getStyleLightProperty("id", "shadow-intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun shadowIntensityTransitionGetException() {
    val light = directionalLight("id") {}
    light.shadowIntensityTransition
  }

  @Test
  fun shadowIntensityTransitionSetDsl() {
    val light = directionalLight("id") {
      shadowIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    style.setLights(listOf(light))
    verify { style.setStyleLights(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("shadow-intensity-transition={duration=100, delay=200}"))
  }
}

// End of generated file.