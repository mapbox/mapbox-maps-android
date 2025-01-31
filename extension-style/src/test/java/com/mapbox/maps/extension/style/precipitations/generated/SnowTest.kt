// This file is generated.
package com.mapbox.maps.extension.style.precipitations.generated

import android.graphics.Color
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
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
@OptIn(MapboxExperimental::class)
class SnowTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()

  @Before
  fun prepareTest() {
    every { style.getStyleSnowProperty(any()) } returns styleProperty
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.setStyleSnowProperty(any(), any()) } returns expected
    every { style.setStyleSnow(any()) } returns expected
    every { expected.error } returns null
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun centerThinningSet() {
    val snow = snow {
      centerThinning(1.0)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("center-thinning=1.0"))
  }

  @Test
  fun centerThinningSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.centerThinning(1.0)
    verify { style.setStyleSnowProperty("center-thinning", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun centerThinningGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0.toString(), snow.centerThinning!!.toString())
    verify { style.getStyleSnowProperty("center-thinning") }
  }
  // Expression Tests

  @Test
  fun centerThinningAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.centerThinningAsExpression)
    verify { style.getStyleSnowProperty("center-thinning") }
  }

  @Test
  fun centerThinningAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0, snow.centerThinningAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, snow.centerThinning!!, 1E-5)
    verify { style.getStyleSnowProperty("center-thinning") }
  }

  @Test
  fun centerThinningTransitionSet() {
    val snow = snow {
      centerThinningTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("center-thinning-transition={duration=100, delay=200}"))
  }

  @Test
  fun centerThinningTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.centerThinningTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("center-thinning-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun centerThinningTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.centerThinningTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("center-thinning-transition") }
  }

  @Test
  fun centerThinningTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.centerThinningTransition)
    verify { style.getStyleSnowProperty("center-thinning-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun centerThinningTransitionGetException() {
    val snow = snow {}
    snow.centerThinningTransition
  }

  @Test
  fun centerThinningTransitionSetDsl() {
    val snow = snow {
      centerThinningTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("center-thinning-transition={duration=100, delay=200}"))
  }
  @Test
  fun colorAsColorIntSet() {
    val snow = snow {
      color(Color.CYAN)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertEquals("{color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun colorAsColorIntSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.color(Color.CYAN)
    verify { style.setStyleSnowProperty("color", capture(valueSlot)) }
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

    val snow = snow {}
    snow.bindTo(style)
    assertEquals(Color.RED, snow.colorAsColorInt!!)
    verify { style.getStyleSnowProperty("color") }
  }

  @Test
  fun colorSet() {
    val snow = snow {
      color("rgba(0, 0, 0, 1)")
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.color("rgba(0, 0, 0, 1)")
    verify { style.setStyleSnowProperty("color", capture(valueSlot)) }
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

    val snow = snow { }
    snow.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), snow.color!!.toString())
    verify { style.getStyleSnowProperty("color") }
  }

  // Expression Tests

  @Test
  fun colorAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.colorAsExpression)
    verify { style.getStyleSnowProperty("color") }
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

    val snow = snow {
      color(expression)
    }
    snow.bindTo(style)
    assertEquals(expression.toString(), snow.colorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", snow.color)
    assertEquals(Color.BLACK, snow.colorAsColorInt)
    verify { style.getStyleSnowProperty("color") }
  }

  @Test
  fun colorTransitionSet() {
    val snow = snow {
      colorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.colorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.colorTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("color-transition") }
  }

  @Test
  fun colorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.colorTransition)
    verify { style.getStyleSnowProperty("color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun colorTransitionGetException() {
    val snow = snow {}
    snow.colorTransition
  }

  @Test
  fun colorTransitionSetDsl() {
    val snow = snow {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }
  @Test
  fun densitySet() {
    val snow = snow {
      density(1.0)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("density=1.0"))
  }

  @Test
  fun densitySetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.density(1.0)
    verify { style.setStyleSnowProperty("density", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun densityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0.toString(), snow.density!!.toString())
    verify { style.getStyleSnowProperty("density") }
  }
  // Expression Tests

  @Test
  fun densityAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.densityAsExpression)
    verify { style.getStyleSnowProperty("density") }
  }

  @Test
  fun densityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0, snow.densityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, snow.density!!, 1E-5)
    verify { style.getStyleSnowProperty("density") }
  }

  @Test
  fun densityTransitionSet() {
    val snow = snow {
      densityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("density-transition={duration=100, delay=200}"))
  }

  @Test
  fun densityTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.densityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("density-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun densityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.densityTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("density-transition") }
  }

  @Test
  fun densityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.densityTransition)
    verify { style.getStyleSnowProperty("density-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun densityTransitionGetException() {
    val snow = snow {}
    snow.densityTransition
  }

  @Test
  fun densityTransitionSetDsl() {
    val snow = snow {
      densityTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("density-transition={duration=100, delay=200}"))
  }
  @Test
  fun directionSet() {
    val snow = snow {
      direction(listOf(0.0, 1.0))
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction=[0.0, 1.0]"))
  }

  @Test
  fun directionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.direction(listOf(0.0, 1.0))
    verify { style.setStyleSnowProperty("direction", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("[0.0, 1.0]"))
  }

  @Test
  fun directionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))

    val snow = snow { }
    snow.bindTo(style)
    assertEquals(listOf(0.0, 1.0).toString(), snow.direction!!.toString())
    verify { style.getStyleSnowProperty("direction") }
  }
  // Expression Tests

  @Test
  fun directionAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.directionAsExpression)
    verify { style.getStyleSnowProperty("direction") }
  }

  @Test
  fun directionAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val snow = snow { }
    snow.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", snow.directionAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), snow.direction)
    verify { style.getStyleSnowProperty("direction") }
  }

  @Test
  fun directionTransitionSet() {
    val snow = snow {
      directionTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction-transition={duration=100, delay=200}"))
  }

  @Test
  fun directionTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.directionTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("direction-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun directionTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.directionTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("direction-transition") }
  }

  @Test
  fun directionTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.directionTransition)
    verify { style.getStyleSnowProperty("direction-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun directionTransitionGetException() {
    val snow = snow {}
    snow.directionTransition
  }

  @Test
  fun directionTransitionSetDsl() {
    val snow = snow {
      directionTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction-transition={duration=100, delay=200}"))
  }
  @Test
  fun flakeSizeSet() {
    val snow = snow {
      flakeSize(1.0)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("flake-size=1.0"))
  }

  @Test
  fun flakeSizeSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.flakeSize(1.0)
    verify { style.setStyleSnowProperty("flake-size", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun flakeSizeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0.toString(), snow.flakeSize!!.toString())
    verify { style.getStyleSnowProperty("flake-size") }
  }
  // Expression Tests

  @Test
  fun flakeSizeAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.flakeSizeAsExpression)
    verify { style.getStyleSnowProperty("flake-size") }
  }

  @Test
  fun flakeSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0, snow.flakeSizeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, snow.flakeSize!!, 1E-5)
    verify { style.getStyleSnowProperty("flake-size") }
  }

  @Test
  fun flakeSizeTransitionSet() {
    val snow = snow {
      flakeSizeTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("flake-size-transition={duration=100, delay=200}"))
  }

  @Test
  fun flakeSizeTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.flakeSizeTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("flake-size-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun flakeSizeTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.flakeSizeTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("flake-size-transition") }
  }

  @Test
  fun flakeSizeTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.flakeSizeTransition)
    verify { style.getStyleSnowProperty("flake-size-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun flakeSizeTransitionGetException() {
    val snow = snow {}
    snow.flakeSizeTransition
  }

  @Test
  fun flakeSizeTransitionSetDsl() {
    val snow = snow {
      flakeSizeTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("flake-size-transition={duration=100, delay=200}"))
  }
  @Test
  fun intensitySet() {
    val snow = snow {
      intensity(1.0)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity=1.0"))
  }

  @Test
  fun intensitySetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.intensity(1.0)
    verify { style.setStyleSnowProperty("intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun intensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0.toString(), snow.intensity!!.toString())
    verify { style.getStyleSnowProperty("intensity") }
  }
  // Expression Tests

  @Test
  fun intensityAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.intensityAsExpression)
    verify { style.getStyleSnowProperty("intensity") }
  }

  @Test
  fun intensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0, snow.intensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, snow.intensity!!, 1E-5)
    verify { style.getStyleSnowProperty("intensity") }
  }

  @Test
  fun intensityTransitionSet() {
    val snow = snow {
      intensityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.intensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("intensity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.intensityTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("intensity-transition") }
  }

  @Test
  fun intensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.intensityTransition)
    verify { style.getStyleSnowProperty("intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun intensityTransitionGetException() {
    val snow = snow {}
    snow.intensityTransition
  }

  @Test
  fun intensityTransitionSetDsl() {
    val snow = snow {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }
  @Test
  fun opacitySet() {
    val snow = snow {
      opacity(1.0)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("opacity=1.0"))
  }

  @Test
  fun opacitySetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.opacity(1.0)
    verify { style.setStyleSnowProperty("opacity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun opacityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0.toString(), snow.opacity!!.toString())
    verify { style.getStyleSnowProperty("opacity") }
  }
  // Expression Tests

  @Test
  fun opacityAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.opacityAsExpression)
    verify { style.getStyleSnowProperty("opacity") }
  }

  @Test
  fun opacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0, snow.opacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, snow.opacity!!, 1E-5)
    verify { style.getStyleSnowProperty("opacity") }
  }

  @Test
  fun opacityTransitionSet() {
    val snow = snow {
      opacityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("opacity-transition={duration=100, delay=200}"))
  }

  @Test
  fun opacityTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.opacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("opacity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun opacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.opacityTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("opacity-transition") }
  }

  @Test
  fun opacityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.opacityTransition)
    verify { style.getStyleSnowProperty("opacity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun opacityTransitionGetException() {
    val snow = snow {}
    snow.opacityTransition
  }

  @Test
  fun opacityTransitionSetDsl() {
    val snow = snow {
      opacityTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("opacity-transition={duration=100, delay=200}"))
  }
  @Test
  fun vignetteSet() {
    val snow = snow {
      vignette(1.0)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette=1.0"))
  }

  @Test
  fun vignetteSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.vignette(1.0)
    verify { style.setStyleSnowProperty("vignette", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun vignetteGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0.toString(), snow.vignette!!.toString())
    verify { style.getStyleSnowProperty("vignette") }
  }
  // Expression Tests

  @Test
  fun vignetteAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.vignetteAsExpression)
    verify { style.getStyleSnowProperty("vignette") }
  }

  @Test
  fun vignetteAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(1.0, snow.vignetteAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, snow.vignette!!, 1E-5)
    verify { style.getStyleSnowProperty("vignette") }
  }

  @Test
  fun vignetteTransitionSet() {
    val snow = snow {
      vignetteTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-transition={duration=100, delay=200}"))
  }

  @Test
  fun vignetteTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.vignetteTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("vignette-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun vignetteTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.vignetteTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("vignette-transition") }
  }

  @Test
  fun vignetteTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.vignetteTransition)
    verify { style.getStyleSnowProperty("vignette-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun vignetteTransitionGetException() {
    val snow = snow {}
    snow.vignetteTransition
  }

  @Test
  fun vignetteTransitionSetDsl() {
    val snow = snow {
      vignetteTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-transition={duration=100, delay=200}"))
  }
  @Test
  fun vignetteColorAsColorIntSet() {
    val snow = snow {
      vignetteColor(Color.CYAN)
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertEquals("{vignette-color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun vignetteColorAsColorIntSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.vignetteColor(Color.CYAN)
    verify { style.setStyleSnowProperty("vignette-color", capture(valueSlot)) }
    assertEquals("[rgba, 0, 255, 255, 1.0]", valueSlot.captured.toString())
  }

  @Test
  fun vignetteColorAsColorIntGet() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val snow = snow {}
    snow.bindTo(style)
    assertEquals(Color.RED, snow.vignetteColorAsColorInt!!)
    verify { style.getStyleSnowProperty("vignette-color") }
  }

  @Test
  fun vignetteColorSet() {
    val snow = snow {
      vignetteColor("rgba(0, 0, 0, 1)")
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun vignetteColorSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.vignetteColor("rgba(0, 0, 0, 1)")
    verify { style.setStyleSnowProperty("vignette-color", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("rgba(0, 0, 0, 1)"))
  }

  @Test
  fun vignetteColorGet() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val snow = snow { }
    snow.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), snow.vignetteColor!!.toString())
    verify { style.getStyleSnowProperty("vignette-color") }
  }

  // Expression Tests

  @Test
  fun vignetteColorAsExpressionGetNull() {
    val snow = snow { }
    snow.bindTo(style)
    assertEquals(null, snow.vignetteColorAsExpression)
    verify { style.getStyleSnowProperty("vignette-color") }
  }

  @Test
  fun vignetteColorAsExpressionGetFromLiteral() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    every { styleProperty.value } returns expression

    val snow = snow {
      vignetteColor(expression)
    }
    snow.bindTo(style)
    assertEquals(expression.toString(), snow.vignetteColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", snow.vignetteColor)
    assertEquals(Color.BLACK, snow.vignetteColorAsColorInt)
    verify { style.getStyleSnowProperty("vignette-color") }
  }

  @Test
  fun vignetteColorTransitionSet() {
    val snow = snow {
      vignetteColorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun vignetteColorTransitionSetAfterInitialization() {
    val snow = snow { }
    snow.bindTo(style)
    snow.vignetteColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleSnowProperty("vignette-color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun vignetteColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(transition.toValue().toString(), snow.vignetteColorTransition!!.toValue().toString())
    verify { style.getStyleSnowProperty("vignette-color-transition") }
  }

  @Test
  fun vignetteColorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val snow = snow {}
    snow.bindTo(style)
    assertEquals(null, snow.vignetteColorTransition)
    verify { style.getStyleSnowProperty("vignette-color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun vignetteColorTransitionGetException() {
    val snow = snow {}
    snow.vignetteColorTransition
  }

  @Test
  fun vignetteColorTransitionSetDsl() {
    val snow = snow {
      vignetteColorTransition {
        duration(100)
        delay(200)
      }
    }
    snow.bindTo(style)
    verify { style.setStyleSnow(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun getSnowTest() {
    assertNotNull(style.getSnow())
    verify(exactly = 0) { style.setStyleSnow(any()) }
  }

  @Test
  fun removeSnowTest() {
    style.removeSnow()
    verify { style.setStyleSnow(Value.nullValue()) }
  }
}

// End of generated file.