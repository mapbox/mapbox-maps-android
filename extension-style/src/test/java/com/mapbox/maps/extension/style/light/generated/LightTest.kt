// This file is generated.

package com.mapbox.maps.extension.style.light.generated

import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.layers.properties.generated.Anchor
import com.mapbox.maps.extension.style.light.LightPosition
import com.mapbox.maps.extension.style.types.transitionOptions
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LightTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()

  @Before
  fun prepareTest() {
    every { style.getStyleLightProperty(any()) } returns styleProperty
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.setStyleLightProperty(any(), any()) } returns expected
    every { style.setStyleLight(any()) } returns expected
    every { expected.error } returns null
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun anchorSet() {
    val light = light {
      anchor(Anchor.MAP)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("anchor=map"))
  }

  @Test
  fun anchorSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.anchor(Anchor.MAP)
    verify { style.setStyleLightProperty("anchor", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("map"))
  }

  @Test
  fun anchorGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("map")

    val light = light { }
    light.bindTo(style)
    assertEquals(Anchor.MAP, light.anchor!!)
    verify { style.getStyleLightProperty("anchor") }
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

    val light = light {
      anchor(expression)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    light.anchor(expression)
    verify { style.setStyleLightProperty("anchor", capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    assertEquals(expression.toString(), light.anchorAsExpression?.toString())
    verify { style.getStyleLightProperty("anchor") }
  }

  @Test
  fun anchorAsExpressionGetNull() {
    val light = light { }
    light.bindTo(style)
    assertEquals(null, light.anchorAsExpression)
    verify { style.getStyleLightProperty("anchor") }
  }

  @Test
  fun anchorAsExpressionGetFromLiteral() {
    val value = "map"
    every { styleProperty.value } returns TypeUtils.wrapToValue(value)

    val light = light { }
    light.bindTo(style)
    assertEquals(value.toString(), light.anchorAsExpression?.toString())
    assertEquals(Anchor.MAP.value, light.anchorAsExpression.toString())
    assertEquals(Anchor.MAP, light.anchor)
    verify { style.getStyleLightProperty("anchor") }
  }

  @Test
  fun castShadowsSet() {
    val light = light {
      castShadows(true)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("cast-shadows=true"))
  }

  @Test
  fun castShadowsSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.castShadows(true)
    verify { style.setStyleLightProperty("cast-shadows", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("true"))
  }

  @Test
  fun castShadowsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    val light = light { }
    light.bindTo(style)
    assertEquals(true.toString(), light.castShadows!!.toString())
    verify { style.getStyleLightProperty("cast-shadows") }
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

    val light = light {
      castShadows(expression)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    light.castShadows(expression)
    verify { style.setStyleLightProperty("cast-shadows", capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    assertEquals(expression.toString(), light.castShadowsAsExpression?.toString())
    verify { style.getStyleLightProperty("cast-shadows") }
  }

  @Test
  fun castShadowsAsExpressionGetNull() {
    val light = light { }
    light.bindTo(style)
    assertEquals(null, light.castShadowsAsExpression)
    verify { style.getStyleLightProperty("cast-shadows") }
  }

  @Test
  fun castShadowsAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val light = light { }
    light.bindTo(style)
    assertEquals("true", light.castShadowsAsExpression.toString())
    assertEquals(true, light.castShadows)
    verify { style.getStyleLightProperty("cast-shadows") }
  }

  @Test
  fun colorAsColorIntSet() {
    val light = light {
      color(Color.CYAN)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertEquals("{color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun colorAsColorIntSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.color(Color.CYAN)
    verify { style.setStyleLightProperty("color", capture(valueSlot)) }
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

    val light = light {}
    light.bindTo(style)
    assertEquals(Color.RED, light.colorAsColorInt!!)
    verify { style.getStyleLightProperty("color") }
  }

  @Test
  fun colorSet() {
    val light = light {
      color("rgba(0, 0, 0, 1)")
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.color("rgba(0, 0, 0, 1)")
    verify { style.setStyleLightProperty("color", capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), light.color!!.toString())
    verify { style.getStyleLightProperty("color") }
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

    val light = light {
      color(expression)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    light.color(expression)
    verify { style.setStyleLightProperty("color", capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    assertEquals(expression.toString(), light.colorAsExpression?.toString())
    verify { style.getStyleLightProperty("color") }
  }

  @Test
  fun colorAsExpressionGetNull() {
    val light = light { }
    light.bindTo(style)
    assertEquals(null, light.colorAsExpression)
    verify { style.getStyleLightProperty("color") }
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

    val light = light { }
    light.bindTo(style)
    assertEquals(expression.toString(), light.colorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", light.color)
    assertEquals(Color.BLACK, light.colorAsColorInt)
    verify { style.getStyleLightProperty("color") }
  }

  @Test
  fun colorTransitionSet() {
    val light = light {
      colorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.colorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(transition.toValue().toString(), light.colorTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("color-transition") }
  }

  @Test
  fun colorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(null, light.colorTransition)
    verify { style.getStyleLightProperty("color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun colorTransitionGetException() {
    val light = light {}
    light.colorTransition
  }

  @Test
  fun colorTransitionSetDsl() {
    val light = light {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }

  @Test
  fun intensitySet() {
    val light = light {
      intensity(1.0)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity=1.0"))
  }

  @Test
  fun intensitySetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.intensity(1.0)
    verify { style.setStyleLightProperty("intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun intensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val light = light { }
    light.bindTo(style)
    assertEquals(1.0.toString(), light.intensity!!.toString())
    verify { style.getStyleLightProperty("intensity") }
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

    val light = light {
      intensity(expression)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    light.intensity(expression)
    verify { style.setStyleLightProperty("intensity", capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    assertEquals(expression.toString(), light.intensityAsExpression?.toString())
    verify { style.getStyleLightProperty("intensity") }
  }

  @Test
  fun intensityAsExpressionGetNull() {
    val light = light { }
    light.bindTo(style)
    assertEquals(null, light.intensityAsExpression)
    verify { style.getStyleLightProperty("intensity") }
  }

  @Test
  fun intensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val light = light { }
    light.bindTo(style)
    assertEquals(1.0, light.intensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, light.intensity!!, 1E-5)
    verify { style.getStyleLightProperty("intensity") }
  }

  @Test
  fun intensityTransitionSet() {
    val light = light {
      intensityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.intensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("intensity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(transition.toValue().toString(), light.intensityTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("intensity-transition") }
  }

  @Test
  fun intensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(null, light.intensityTransition)
    verify { style.getStyleLightProperty("intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun intensityTransitionGetException() {
    val light = light {}
    light.intensityTransition
  }

  @Test
  fun intensityTransitionSetDsl() {
    val light = light {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun positionSetDsl() {
    val light = light {
      position(1.0, 2.0, 3.0)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position=[1.0, 2.0, 3.0]"))
  }

  @Test
  fun positionSet() {
    val light = light {
      position(LightPosition(0.0, 1.0, 2.0))
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position=[0.0, 1.0, 2.0]"))
  }

  @Test
  fun positionSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.position(LightPosition(0.0, 1.0, 2.0))
    verify { style.setStyleLightProperty("position", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("[0.0, 1.0, 2.0]"))
  }

  @Test
  fun positionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(LightPosition(0.0, 1.0, 2.0))

    val light = light { }
    light.bindTo(style)
    assertEquals(LightPosition(0.0, 1.0, 2.0).toString(), light.position!!.toString())
    verify { style.getStyleLightProperty("position") }
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

    val light = light {
      position(expression)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    light.position(expression)
    verify { style.setStyleLightProperty("position", capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    assertEquals(expression.toString(), light.positionAsExpression?.toString())
    verify { style.getStyleLightProperty("position") }
  }

  @Test
  fun positionAsExpressionGetNull() {
    val light = light { }
    light.bindTo(style)
    assertEquals(null, light.positionAsExpression)
    verify { style.getStyleLightProperty("position") }
  }

  @Test
  fun positionAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(LightPosition(0.0, 1.0, 2.0))
    val light = light { }
    light.bindTo(style)
    assertEquals("[literal, [0.0, 1.0, 2.0]]", light.positionAsExpression.toString())
    assertEquals(LightPosition(0.0, 1.0, 2.0), light.position)
    verify { style.getStyleLightProperty("position") }
  }

  @Test
  fun positionTransitionSet() {
    val light = light {
      positionTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position-transition={duration=100, delay=200}"))
  }

  @Test
  fun positionTransitionSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.positionTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("position-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun positionTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(transition.toValue().toString(), light.positionTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("position-transition") }
  }

  @Test
  fun positionTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(null, light.positionTransition)
    verify { style.getStyleLightProperty("position-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun positionTransitionGetException() {
    val light = light {}
    light.positionTransition
  }

  @Test
  fun positionTransitionSetDsl() {
    val light = light {
      positionTransition {
        duration(100)
        delay(200)
      }
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("position-transition={duration=100, delay=200}"))
  }

  @Test
  fun shadowIntensitySet() {
    val light = light {
      shadowIntensity(1.0)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("shadow-intensity=1.0"))
  }

  @Test
  fun shadowIntensitySetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.shadowIntensity(1.0)
    verify { style.setStyleLightProperty("shadow-intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun shadowIntensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val light = light { }
    light.bindTo(style)
    assertEquals(1.0.toString(), light.shadowIntensity!!.toString())
    verify { style.getStyleLightProperty("shadow-intensity") }
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

    val light = light {
      shadowIntensity(expression)
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    light.shadowIntensity(expression)
    verify { style.setStyleLightProperty("shadow-intensity", capture(valueSlot)) }
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

    val light = light { }
    light.bindTo(style)
    assertEquals(expression.toString(), light.shadowIntensityAsExpression?.toString())
    verify { style.getStyleLightProperty("shadow-intensity") }
  }

  @Test
  fun shadowIntensityAsExpressionGetNull() {
    val light = light { }
    light.bindTo(style)
    assertEquals(null, light.shadowIntensityAsExpression)
    verify { style.getStyleLightProperty("shadow-intensity") }
  }

  @Test
  fun shadowIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val light = light { }
    light.bindTo(style)
    assertEquals(1.0, light.shadowIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, light.shadowIntensity!!, 1E-5)
    verify { style.getStyleLightProperty("shadow-intensity") }
  }

  @Test
  fun shadowIntensityTransitionSet() {
    val light = light {
      shadowIntensityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("shadow-intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun shadowIntensityTransitionSetAfterInitialization() {
    val light = light { }
    light.bindTo(style)
    light.shadowIntensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleLightProperty("shadow-intensity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun shadowIntensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(transition.toValue().toString(), light.shadowIntensityTransition!!.toValue().toString())
    verify { style.getStyleLightProperty("shadow-intensity-transition") }
  }

  @Test
  fun shadowIntensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val light = light {}
    light.bindTo(style)
    assertEquals(null, light.shadowIntensityTransition)
    verify { style.getStyleLightProperty("shadow-intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun shadowIntensityTransitionGetException() {
    val light = light {}
    light.shadowIntensityTransition
  }

  @Test
  fun shadowIntensityTransitionSetDsl() {
    val light = light {
      shadowIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    light.bindTo(style)
    verify { style.setStyleLight(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("shadow-intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun getLightTest() {
    assertNotNull(style.getLight())
    verify { style.setStyleLight(any()) }
  }
}

// End of generated file.