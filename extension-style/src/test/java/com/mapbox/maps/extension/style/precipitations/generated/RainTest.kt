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
class RainTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()

  @Before
  fun prepareTest() {
    every { style.getStyleRainProperty(any()) } returns styleProperty
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.setStyleRainProperty(any(), any()) } returns expected
    every { style.setStyleRain(any()) } returns expected
    every { expected.error } returns null
  }

  @After
  fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun centerThinningSet() {
    val rain = rain {
      centerThinning(1.0)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("center-thinning=1.0"))
  }

  @Test
  fun centerThinningSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.centerThinning(1.0)
    verify { style.setStyleRainProperty("center-thinning", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun centerThinningGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0.toString(), rain.centerThinning!!.toString())
    verify { style.getStyleRainProperty("center-thinning") }
  }
  // Expression Tests

  @Test
  fun centerThinningAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.centerThinningAsExpression)
    verify { style.getStyleRainProperty("center-thinning") }
  }

  @Test
  fun centerThinningAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0, rain.centerThinningAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, rain.centerThinning!!, 1E-5)
    verify { style.getStyleRainProperty("center-thinning") }
  }

  @Test
  fun centerThinningTransitionSet() {
    val rain = rain {
      centerThinningTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("center-thinning-transition={duration=100, delay=200}"))
  }

  @Test
  fun centerThinningTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.centerThinningTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("center-thinning-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun centerThinningTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.centerThinningTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("center-thinning-transition") }
  }

  @Test
  fun centerThinningTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.centerThinningTransition)
    verify { style.getStyleRainProperty("center-thinning-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun centerThinningTransitionGetException() {
    val rain = rain {}
    rain.centerThinningTransition
  }

  @Test
  fun centerThinningTransitionSetDsl() {
    val rain = rain {
      centerThinningTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("center-thinning-transition={duration=100, delay=200}"))
  }
  @Test
  fun colorAsColorIntSet() {
    val rain = rain {
      color(Color.CYAN)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertEquals("{color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun colorAsColorIntSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.color(Color.CYAN)
    verify { style.setStyleRainProperty("color", capture(valueSlot)) }
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

    val rain = rain {}
    rain.bindTo(style)
    assertEquals(Color.RED, rain.colorAsColorInt!!)
    verify { style.getStyleRainProperty("color") }
  }

  @Test
  fun colorSet() {
    val rain = rain {
      color("rgba(0, 0, 0, 1)")
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun colorSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.color("rgba(0, 0, 0, 1)")
    verify { style.setStyleRainProperty("color", capture(valueSlot)) }
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

    val rain = rain { }
    rain.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), rain.color!!.toString())
    verify { style.getStyleRainProperty("color") }
  }

  // Expression Tests

  @Test
  fun colorAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.colorAsExpression)
    verify { style.getStyleRainProperty("color") }
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

    val rain = rain {
      color(expression)
    }
    rain.bindTo(style)
    assertEquals(expression.toString(), rain.colorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", rain.color)
    assertEquals(Color.BLACK, rain.colorAsColorInt)
    verify { style.getStyleRainProperty("color") }
  }

  @Test
  fun colorTransitionSet() {
    val rain = rain {
      colorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.colorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun colorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.colorTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("color-transition") }
  }

  @Test
  fun colorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.colorTransition)
    verify { style.getStyleRainProperty("color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun colorTransitionGetException() {
    val rain = rain {}
    rain.colorTransition
  }

  @Test
  fun colorTransitionSetDsl() {
    val rain = rain {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("color-transition={duration=100, delay=200}"))
  }
  @Test
  fun densitySet() {
    val rain = rain {
      density(1.0)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("density=1.0"))
  }

  @Test
  fun densitySetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.density(1.0)
    verify { style.setStyleRainProperty("density", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun densityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0.toString(), rain.density!!.toString())
    verify { style.getStyleRainProperty("density") }
  }
  // Expression Tests

  @Test
  fun densityAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.densityAsExpression)
    verify { style.getStyleRainProperty("density") }
  }

  @Test
  fun densityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0, rain.densityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, rain.density!!, 1E-5)
    verify { style.getStyleRainProperty("density") }
  }

  @Test
  fun densityTransitionSet() {
    val rain = rain {
      densityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("density-transition={duration=100, delay=200}"))
  }

  @Test
  fun densityTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.densityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("density-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun densityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.densityTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("density-transition") }
  }

  @Test
  fun densityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.densityTransition)
    verify { style.getStyleRainProperty("density-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun densityTransitionGetException() {
    val rain = rain {}
    rain.densityTransition
  }

  @Test
  fun densityTransitionSetDsl() {
    val rain = rain {
      densityTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("density-transition={duration=100, delay=200}"))
  }
  @Test
  fun directionSet() {
    val rain = rain {
      direction(listOf(0.0, 1.0))
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction=[0.0, 1.0]"))
  }

  @Test
  fun directionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.direction(listOf(0.0, 1.0))
    verify { style.setStyleRainProperty("direction", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("[0.0, 1.0]"))
  }

  @Test
  fun directionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(listOf(0.0, 1.0).toString(), rain.direction!!.toString())
    verify { style.getStyleRainProperty("direction") }
  }
  // Expression Tests

  @Test
  fun directionAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.directionAsExpression)
    verify { style.getStyleRainProperty("direction") }
  }

  @Test
  fun directionAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val rain = rain { }
    rain.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", rain.directionAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), rain.direction)
    verify { style.getStyleRainProperty("direction") }
  }

  @Test
  fun directionTransitionSet() {
    val rain = rain {
      directionTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction-transition={duration=100, delay=200}"))
  }

  @Test
  fun directionTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.directionTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("direction-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun directionTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.directionTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("direction-transition") }
  }

  @Test
  fun directionTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.directionTransition)
    verify { style.getStyleRainProperty("direction-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun directionTransitionGetException() {
    val rain = rain {}
    rain.directionTransition
  }

  @Test
  fun directionTransitionSetDsl() {
    val rain = rain {
      directionTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("direction-transition={duration=100, delay=200}"))
  }
  @Test
  fun distortionStrengthSet() {
    val rain = rain {
      distortionStrength(1.0)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("distortion-strength=1.0"))
  }

  @Test
  fun distortionStrengthSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.distortionStrength(1.0)
    verify { style.setStyleRainProperty("distortion-strength", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun distortionStrengthGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0.toString(), rain.distortionStrength!!.toString())
    verify { style.getStyleRainProperty("distortion-strength") }
  }
  // Expression Tests

  @Test
  fun distortionStrengthAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.distortionStrengthAsExpression)
    verify { style.getStyleRainProperty("distortion-strength") }
  }

  @Test
  fun distortionStrengthAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0, rain.distortionStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, rain.distortionStrength!!, 1E-5)
    verify { style.getStyleRainProperty("distortion-strength") }
  }

  @Test
  fun distortionStrengthTransitionSet() {
    val rain = rain {
      distortionStrengthTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("distortion-strength-transition={duration=100, delay=200}"))
  }

  @Test
  fun distortionStrengthTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.distortionStrengthTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("distortion-strength-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun distortionStrengthTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.distortionStrengthTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("distortion-strength-transition") }
  }

  @Test
  fun distortionStrengthTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.distortionStrengthTransition)
    verify { style.getStyleRainProperty("distortion-strength-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun distortionStrengthTransitionGetException() {
    val rain = rain {}
    rain.distortionStrengthTransition
  }

  @Test
  fun distortionStrengthTransitionSetDsl() {
    val rain = rain {
      distortionStrengthTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("distortion-strength-transition={duration=100, delay=200}"))
  }
  @Test
  fun dropletSizeSet() {
    val rain = rain {
      dropletSize(listOf(0.0, 1.0))
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("droplet-size=[0.0, 1.0]"))
  }

  @Test
  fun dropletSizeSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.dropletSize(listOf(0.0, 1.0))
    verify { style.setStyleRainProperty("droplet-size", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("[0.0, 1.0]"))
  }

  @Test
  fun dropletSizeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(listOf(0.0, 1.0).toString(), rain.dropletSize!!.toString())
    verify { style.getStyleRainProperty("droplet-size") }
  }
  // Expression Tests

  @Test
  fun dropletSizeAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.dropletSizeAsExpression)
    verify { style.getStyleRainProperty("droplet-size") }
  }

  @Test
  fun dropletSizeAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0))
    val rain = rain { }
    rain.bindTo(style)
    assertEquals("[literal, [0.0, 1.0]]", rain.dropletSizeAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), rain.dropletSize)
    verify { style.getStyleRainProperty("droplet-size") }
  }

  @Test
  fun dropletSizeTransitionSet() {
    val rain = rain {
      dropletSizeTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("droplet-size-transition={duration=100, delay=200}"))
  }

  @Test
  fun dropletSizeTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.dropletSizeTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("droplet-size-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun dropletSizeTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.dropletSizeTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("droplet-size-transition") }
  }

  @Test
  fun dropletSizeTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.dropletSizeTransition)
    verify { style.getStyleRainProperty("droplet-size-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun dropletSizeTransitionGetException() {
    val rain = rain {}
    rain.dropletSizeTransition
  }

  @Test
  fun dropletSizeTransitionSetDsl() {
    val rain = rain {
      dropletSizeTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("droplet-size-transition={duration=100, delay=200}"))
  }
  @Test
  fun intensitySet() {
    val rain = rain {
      intensity(1.0)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity=1.0"))
  }

  @Test
  fun intensitySetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.intensity(1.0)
    verify { style.setStyleRainProperty("intensity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun intensityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0.toString(), rain.intensity!!.toString())
    verify { style.getStyleRainProperty("intensity") }
  }
  // Expression Tests

  @Test
  fun intensityAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.intensityAsExpression)
    verify { style.getStyleRainProperty("intensity") }
  }

  @Test
  fun intensityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0, rain.intensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, rain.intensity!!, 1E-5)
    verify { style.getStyleRainProperty("intensity") }
  }

  @Test
  fun intensityTransitionSet() {
    val rain = rain {
      intensityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.intensityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("intensity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun intensityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.intensityTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("intensity-transition") }
  }

  @Test
  fun intensityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.intensityTransition)
    verify { style.getStyleRainProperty("intensity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun intensityTransitionGetException() {
    val rain = rain {}
    rain.intensityTransition
  }

  @Test
  fun intensityTransitionSetDsl() {
    val rain = rain {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("intensity-transition={duration=100, delay=200}"))
  }
  @Test
  fun opacitySet() {
    val rain = rain {
      opacity(1.0)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("opacity=1.0"))
  }

  @Test
  fun opacitySetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.opacity(1.0)
    verify { style.setStyleRainProperty("opacity", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun opacityGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0.toString(), rain.opacity!!.toString())
    verify { style.getStyleRainProperty("opacity") }
  }
  // Expression Tests

  @Test
  fun opacityAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.opacityAsExpression)
    verify { style.getStyleRainProperty("opacity") }
  }

  @Test
  fun opacityAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0, rain.opacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, rain.opacity!!, 1E-5)
    verify { style.getStyleRainProperty("opacity") }
  }

  @Test
  fun opacityTransitionSet() {
    val rain = rain {
      opacityTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("opacity-transition={duration=100, delay=200}"))
  }

  @Test
  fun opacityTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.opacityTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("opacity-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun opacityTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.opacityTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("opacity-transition") }
  }

  @Test
  fun opacityTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.opacityTransition)
    verify { style.getStyleRainProperty("opacity-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun opacityTransitionGetException() {
    val rain = rain {}
    rain.opacityTransition
  }

  @Test
  fun opacityTransitionSetDsl() {
    val rain = rain {
      opacityTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("opacity-transition={duration=100, delay=200}"))
  }
  @Test
  fun vignetteSet() {
    val rain = rain {
      vignette(1.0)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette=1.0"))
  }

  @Test
  fun vignetteSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.vignette(1.0)
    verify { style.setStyleRainProperty("vignette", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("1.0"))
  }

  @Test
  fun vignetteGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0.toString(), rain.vignette!!.toString())
    verify { style.getStyleRainProperty("vignette") }
  }
  // Expression Tests

  @Test
  fun vignetteAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.vignetteAsExpression)
    verify { style.getStyleRainProperty("vignette") }
  }

  @Test
  fun vignetteAsExpressionGetFromLiteral() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(1.0, rain.vignetteAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, rain.vignette!!, 1E-5)
    verify { style.getStyleRainProperty("vignette") }
  }

  @Test
  fun vignetteTransitionSet() {
    val rain = rain {
      vignetteTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-transition={duration=100, delay=200}"))
  }

  @Test
  fun vignetteTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.vignetteTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("vignette-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun vignetteTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.vignetteTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("vignette-transition") }
  }

  @Test
  fun vignetteTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.vignetteTransition)
    verify { style.getStyleRainProperty("vignette-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun vignetteTransitionGetException() {
    val rain = rain {}
    rain.vignetteTransition
  }

  @Test
  fun vignetteTransitionSetDsl() {
    val rain = rain {
      vignetteTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-transition={duration=100, delay=200}"))
  }
  @Test
  fun vignetteColorAsColorIntSet() {
    val rain = rain {
      vignetteColor(Color.CYAN)
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertEquals("{vignette-color=[rgba, 0, 255, 255, 1.0]}", valueSlot.captured.toString())
  }

  @Test
  fun vignetteColorAsColorIntSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.vignetteColor(Color.CYAN)
    verify { style.setStyleRainProperty("vignette-color", capture(valueSlot)) }
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

    val rain = rain {}
    rain.bindTo(style)
    assertEquals(Color.RED, rain.vignetteColorAsColorInt!!)
    verify { style.getStyleRainProperty("vignette-color") }
  }

  @Test
  fun vignetteColorSet() {
    val rain = rain {
      vignetteColor("rgba(0, 0, 0, 1)")
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-color=rgba(0, 0, 0, 1)"))
  }

  @Test
  fun vignetteColorSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.vignetteColor("rgba(0, 0, 0, 1)")
    verify { style.setStyleRainProperty("vignette-color", capture(valueSlot)) }
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

    val rain = rain { }
    rain.bindTo(style)
    assertEquals("rgba(0, 0, 0, 1)".toString(), rain.vignetteColor!!.toString())
    verify { style.getStyleRainProperty("vignette-color") }
  }

  // Expression Tests

  @Test
  fun vignetteColorAsExpressionGetNull() {
    val rain = rain { }
    rain.bindTo(style)
    assertEquals(null, rain.vignetteColorAsExpression)
    verify { style.getStyleRainProperty("vignette-color") }
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

    val rain = rain {
      vignetteColor(expression)
    }
    rain.bindTo(style)
    assertEquals(expression.toString(), rain.vignetteColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", rain.vignetteColor)
    assertEquals(Color.BLACK, rain.vignetteColorAsColorInt)
    verify { style.getStyleRainProperty("vignette-color") }
  }

  @Test
  fun vignetteColorTransitionSet() {
    val rain = rain {
      vignetteColorTransition(
        transitionOptions {
          duration(100)
          delay(200)
        }
      )
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun vignetteColorTransitionSetAfterInitialization() {
    val rain = rain { }
    rain.bindTo(style)
    rain.vignetteColorTransition(
      transitionOptions {
        duration(100)
        delay(200)
      }
    )
    verify { style.setStyleRainProperty("vignette-color-transition", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("{duration=100, delay=200}"))
  }

  @Test
  fun vignetteColorTransitionGet() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(transition.toValue().toString(), rain.vignetteColorTransition!!.toValue().toString())
    verify { style.getStyleRainProperty("vignette-color-transition") }
  }

  @Test
  fun vignetteColorTransitionGetNull() {
    val transition = "wrong type"
    every { styleProperty.value } returns TypeUtils.wrapToValue(transition)
    val rain = rain {}
    rain.bindTo(style)
    assertEquals(null, rain.vignetteColorTransition)
    verify { style.getStyleRainProperty("vignette-color-transition") }
  }

  @Test(expected = RuntimeException::class)
  fun vignetteColorTransitionGetException() {
    val rain = rain {}
    rain.vignetteColorTransition
  }

  @Test
  fun vignetteColorTransitionSetDsl() {
    val rain = rain {
      vignetteColorTransition {
        duration(100)
        delay(200)
      }
    }
    rain.bindTo(style)
    verify { style.setStyleRain(capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("vignette-color-transition={duration=100, delay=200}"))
  }

  @Test
  fun getRainTest() {
    assertNotNull(style.getRain())
    verify(exactly = 0) { style.setStyleRain(any()) }
  }

  @Test
  fun removeRainTest() {
    style.removeRain()
    verify { style.setStyleRain(Value.nullValue()) }
  }
}

// End of generated file.