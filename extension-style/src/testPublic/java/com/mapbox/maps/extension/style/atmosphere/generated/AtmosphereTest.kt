// This file is generated.
package com.mapbox.maps.extension.style.atmosphere.generated

import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
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
class AtmosphereTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()

  @Before
  fun prepareTest() {
    every { style.getStyleAtmosphereProperty(any()) } returns styleProperty
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.setStyleAtmosphereProperty(any(), any()) } returns expected
    every { style.setStyleAtmosphere(any()) } returns expected
    every { expected.error } returns null
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun colorAsColorIntSet() {
    val atmosphere = atmosphere {
      color(Color.CYAN)
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertEquals("{color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun colorAsColorIntSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.color(Color.CYAN)
    verify { style.setStyleAtmosphereProperty("color", capture(valueSlot)) }
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

    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(Color.RED, atmosphere.colorAsColorInt!!)
    verify { style.getStyleAtmosphereProperty("color") }
  }

  @Test
  fun colorSet() {
    val atmosphere = atmosphere {
      color("rgba(0, 0, 0, 1)")
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.color("rgba(0, 0, 0, 1)")
    verify { style.setStyleAtmosphereProperty("color", capture(valueSlot)) }
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

    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), atmosphere.color!!.toString())
    verify { style.getStyleAtmosphereProperty("color") }
  }

  // Expression Tests

  @Test
  fun colorAsExpressionGetNull() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.colorAsExpression)
    verify { style.getStyleAtmosphereProperty("color") }
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

    val atmosphere = atmosphere {
      color(expression)
    }
    atmosphere.bindTo(style)
    assertEquals(expression.toString(), atmosphere.colorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", atmosphere.color)
    assertEquals(Color.BLACK, atmosphere.colorAsColorInt)
    verify { style.getStyleAtmosphereProperty("color") }
  }

  @Test
  fun colorTransitionSet() {
    val atmosphere = atmosphere {
      colorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.colorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleAtmosphereProperty("color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(transition.toValue().toString(), atmosphere.colorTransition!!.toValue().toString())
    verify { style.getStyleAtmosphereProperty("color-transition") }
  }

  @Test
  fun colorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.colorTransition)
    verify { style.getStyleAtmosphereProperty("color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun colorTransitionGetException() {
    val atmosphere = atmosphere {}
    atmosphere.colorTransition
  }

  @Test
  fun colorTransitionSetDsl() {
    val atmosphere = atmosphere {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }
  @Test
  fun highColorAsColorIntSet() {
    val atmosphere = atmosphere {
      highColor(Color.CYAN)
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertEquals("{high-color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun highColorAsColorIntSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.highColor(Color.CYAN)
    verify { style.setStyleAtmosphereProperty("high-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun highColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(Color.RED, atmosphere.highColorAsColorInt!!)
    verify { style.getStyleAtmosphereProperty("high-color") }
  }

  @Test
  fun highColorSet() {
    val atmosphere = atmosphere {
      highColor("rgba(0, 0, 0, 1)")
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("high-color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun highColorSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.highColor("rgba(0, 0, 0, 1)")
    verify { style.setStyleAtmosphereProperty("high-color", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("rgba(0, 0, 0, 1)"))
  }

  @Test
  fun highColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), atmosphere.highColor!!.toString())
    verify { style.getStyleAtmosphereProperty("high-color") }
  }

  // Expression Tests

  @Test
  fun highColorAsExpressionGetNull() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.highColorAsExpression)
    verify { style.getStyleAtmosphereProperty("high-color") }
  }

  @Test
  fun highColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val atmosphere = atmosphere {
      highColor(expression)
    }
    atmosphere.bindTo(style)
    assertEquals(expression.toString(), atmosphere.highColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", atmosphere.highColor)
    assertEquals(Color.BLACK, atmosphere.highColorAsColorInt)
    verify { style.getStyleAtmosphereProperty("high-color") }
  }

  @Test
  fun highColorTransitionSet() {
    val atmosphere = atmosphere {
      highColorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("high-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun highColorTransitionSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.highColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleAtmosphereProperty("high-color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun highColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(transition.toValue().toString(), atmosphere.highColorTransition!!.toValue().toString())
    verify { style.getStyleAtmosphereProperty("high-color-transition") }
  }

  @Test
  fun highColorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.highColorTransition)
    verify { style.getStyleAtmosphereProperty("high-color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun highColorTransitionGetException() {
    val atmosphere = atmosphere {}
    atmosphere.highColorTransition
  }

  @Test
  fun highColorTransitionSetDsl() {
    val atmosphere = atmosphere {
      highColorTransition {
        duration(100)
        delay(200)
      }
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("high-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun horizonBlendSet() {
    val atmosphere = atmosphere {
      horizonBlend(1.0)
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("horizon-blend=1.0"))
  }

  @Test
  fun horizonBlendSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.horizonBlend(1.0)
    verify { style.setStyleAtmosphereProperty("horizon-blend", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun horizonBlendGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(1.0.toString(), atmosphere.horizonBlend!!.toString())
    verify { style.getStyleAtmosphereProperty("horizon-blend") }
  }

  // Expression Tests

  @Test
  fun horizonBlendAsExpressionGetNull() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.horizonBlendAsExpression)
    verify { style.getStyleAtmosphereProperty("horizon-blend") }
  }

  @Test
  fun horizonBlendAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(1.0, atmosphere.horizonBlendAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, atmosphere.horizonBlend!!, 1E-5)
    verify { style.getStyleAtmosphereProperty("horizon-blend") }
  }

  @Test
  fun horizonBlendTransitionSet() {
    val atmosphere = atmosphere {
      horizonBlendTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("horizon-blend-transition={duration=100, delay=200}"))
  }

  @Test
  fun horizonBlendTransitionSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.horizonBlendTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleAtmosphereProperty("horizon-blend-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun horizonBlendTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(transition.toValue().toString(), atmosphere.horizonBlendTransition!!.toValue().toString())
    verify { style.getStyleAtmosphereProperty("horizon-blend-transition") }
  }

  @Test
  fun horizonBlendTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.horizonBlendTransition)
    verify { style.getStyleAtmosphereProperty("horizon-blend-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun horizonBlendTransitionGetException() {
    val atmosphere = atmosphere {}
    atmosphere.horizonBlendTransition
  }

  @Test
  fun horizonBlendTransitionSetDsl() {
    val atmosphere = atmosphere {
      horizonBlendTransition {
        duration(100)
        delay(200)
      }
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("horizon-blend-transition={duration=100, delay=200}"))
  }

  @Test
  fun rangeSet() {
    val atmosphere = atmosphere {
      range(listOf(0.0, 1.0))
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("range=[0.0, 1.0]"))
  }

  @Test
  fun rangeSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.range(listOf(0.0, 1.0))
    verify { style.setStyleAtmosphereProperty("range", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("[0.0, 1.0]"))
  }

  @Test
  fun rangeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))

    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(listOf(0.0, 1.0).toString(), atmosphere.range!!.toString())
    verify { style.getStyleAtmosphereProperty("range") }
  }

  // Expression Tests

  @Test
  fun rangeAsExpressionGetNull() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.rangeAsExpression)
    verify { style.getStyleAtmosphereProperty("range") }
  }

  @Test
  fun rangeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", atmosphere.rangeAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), atmosphere.range)
    verify { style.getStyleAtmosphereProperty("range") }
  }

  @Test
  fun rangeTransitionSet() {
    val atmosphere = atmosphere {
      rangeTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("range-transition={duration=100, delay=200}"))
  }

  @Test
  fun rangeTransitionSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.rangeTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleAtmosphereProperty("range-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun rangeTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(transition.toValue().toString(), atmosphere.rangeTransition!!.toValue().toString())
    verify { style.getStyleAtmosphereProperty("range-transition") }
  }

  @Test
  fun rangeTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.rangeTransition)
    verify { style.getStyleAtmosphereProperty("range-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun rangeTransitionGetException() {
    val atmosphere = atmosphere {}
    atmosphere.rangeTransition
  }

  @Test
  fun rangeTransitionSetDsl() {
    val atmosphere = atmosphere {
      rangeTransition {
        duration(100)
        delay(200)
      }
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("range-transition={duration=100, delay=200}"))
  }
  @Test
  fun spaceColorAsColorIntSet() {
    val atmosphere = atmosphere {
      spaceColor(Color.CYAN)
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertEquals("{space-color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun spaceColorAsColorIntSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.spaceColor(Color.CYAN)
    verify { style.setStyleAtmosphereProperty("space-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun spaceColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(Color.RED, atmosphere.spaceColorAsColorInt!!)
    verify { style.getStyleAtmosphereProperty("space-color") }
  }

  @Test
  fun spaceColorSet() {
    val atmosphere = atmosphere {
      spaceColor("rgba(0, 0, 0, 1)")
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("space-color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun spaceColorSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.spaceColor("rgba(0, 0, 0, 1)")
    verify { style.setStyleAtmosphereProperty("space-color", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("rgba(0, 0, 0, 1)"))
  }

  @Test
  fun spaceColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), atmosphere.spaceColor!!.toString())
    verify { style.getStyleAtmosphereProperty("space-color") }
  }

  // Expression Tests

  @Test
  fun spaceColorAsExpressionGetNull() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.spaceColorAsExpression)
    verify { style.getStyleAtmosphereProperty("space-color") }
  }

  @Test
  fun spaceColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val atmosphere = atmosphere {
      spaceColor(expression)
    }
    atmosphere.bindTo(style)
    assertEquals(expression.toString(), atmosphere.spaceColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", atmosphere.spaceColor)
    assertEquals(Color.BLACK, atmosphere.spaceColorAsColorInt)
    verify { style.getStyleAtmosphereProperty("space-color") }
  }

  @Test
  fun spaceColorTransitionSet() {
    val atmosphere = atmosphere {
      spaceColorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("space-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun spaceColorTransitionSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.spaceColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleAtmosphereProperty("space-color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun spaceColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(transition.toValue().toString(), atmosphere.spaceColorTransition!!.toValue().toString())
    verify { style.getStyleAtmosphereProperty("space-color-transition") }
  }

  @Test
  fun spaceColorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.spaceColorTransition)
    verify { style.getStyleAtmosphereProperty("space-color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun spaceColorTransitionGetException() {
    val atmosphere = atmosphere {}
    atmosphere.spaceColorTransition
  }

  @Test
  fun spaceColorTransitionSetDsl() {
    val atmosphere = atmosphere {
      spaceColorTransition {
        duration(100)
        delay(200)
      }
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("space-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun starIntensitySet() {
    val atmosphere = atmosphere {
      starIntensity(1.0)
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("star-intensity=1.0"))
  }

  @Test
  fun starIntensitySetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.starIntensity(1.0)
    verify { style.setStyleAtmosphereProperty("star-intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun starIntensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(1.0.toString(), atmosphere.starIntensity!!.toString())
    verify { style.getStyleAtmosphereProperty("star-intensity") }
  }

  // Expression Tests

  @Test
  fun starIntensityAsExpressionGetNull() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.starIntensityAsExpression)
    verify { style.getStyleAtmosphereProperty("star-intensity") }
  }

  @Test
  fun starIntensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    assertEquals(1.0, atmosphere.starIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, atmosphere.starIntensity!!, 1E-5)
    verify { style.getStyleAtmosphereProperty("star-intensity") }
  }

  @Test
  fun starIntensityTransitionSet() {
    val atmosphere = atmosphere {
      starIntensityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("star-intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun starIntensityTransitionSetAfterInitialization() {
    val atmosphere = atmosphere { }
    atmosphere.bindTo(style)
    atmosphere.starIntensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleAtmosphereProperty("star-intensity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun starIntensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(transition.toValue().toString(), atmosphere.starIntensityTransition!!.toValue().toString())
    verify { style.getStyleAtmosphereProperty("star-intensity-transition") }
  }

  @Test
  fun starIntensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val atmosphere = atmosphere {}
    atmosphere.bindTo(style)
    assertEquals(null, atmosphere.starIntensityTransition)
    verify { style.getStyleAtmosphereProperty("star-intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun starIntensityTransitionGetException() {
    val atmosphere = atmosphere {}
    atmosphere.starIntensityTransition
  }

  @Test
  fun starIntensityTransitionSetDsl() {
    val atmosphere = atmosphere {
      starIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    atmosphere.bindTo(style)
    verify { style.setStyleAtmosphere(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("star-intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun getAtmosphereTest() {
    assertNotNull(style.getAtmosphere())
    verify(exactly = 0) { style.setStyleAtmosphere(any()) }
  }

  @Test
  fun removeAtmosphereTest() {
    style.removeAtmosphere()
    verify { style.setStyleAtmosphere(Value.nullValue()) }
  }
}

// End of generated file.