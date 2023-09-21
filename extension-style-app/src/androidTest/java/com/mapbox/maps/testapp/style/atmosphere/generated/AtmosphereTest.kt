// This file is generated.

package com.mapbox.maps.testapp.style.atmosphere.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.types.transitionOptions
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for Atmosphere
 */
@RunWith(AndroidJUnit4::class)
class AtmosphereTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun colorAsColorIntTest() {
    val atmosphere = atmosphere {
      color(Color.CYAN)
    }
    setupAtmosphere(atmosphere)
    assertEquals(Color.CYAN, atmosphere.colorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun colorTest() {
    val atmosphere = atmosphere {
      color("rgba(0, 0, 0, 1)")
    }
    setupAtmosphere(atmosphere)
    assertEquals("rgba(0, 0, 0, 1)", atmosphere.color)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun colorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }

    val atmosphere = atmosphere {
      color(expression)
    }
    setupAtmosphere(atmosphere)
    assertEquals(expression.toString(), atmosphere.colorAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun colorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      colorTransition(transition)
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.colorTransition)
  }

  @Test
  @UiThreadTest
  fun colorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.colorTransition)
  }

  @Test
  @UiThreadTest
  fun highColorAsColorIntTest() {
    val atmosphere = atmosphere {
      highColor(Color.CYAN)
    }
    setupAtmosphere(atmosphere)
    assertEquals(Color.CYAN, atmosphere.highColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun highColorTest() {
    val atmosphere = atmosphere {
      highColor("rgba(0, 0, 0, 1)")
    }
    setupAtmosphere(atmosphere)
    assertEquals("rgba(0, 0, 0, 1)", atmosphere.highColor)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun highColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }

    val atmosphere = atmosphere {
      highColor(expression)
    }
    setupAtmosphere(atmosphere)
    assertEquals(expression.toString(), atmosphere.highColorAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun highColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      highColorTransition(transition)
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.highColorTransition)
  }

  @Test
  @UiThreadTest
  fun highColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      highColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.highColorTransition)
  }

  @Test
  @UiThreadTest
  fun horizonBlendTest() {
    val atmosphere = atmosphere {
      horizonBlend(1.0)
    }
    setupAtmosphere(atmosphere)
    assertEquals(1.0, atmosphere.horizonBlend!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun horizonBlendAsExpressionTest() {
    val expression = literal(1.0)

    val atmosphere = atmosphere {
      horizonBlend(expression)
    }
    setupAtmosphere(atmosphere)
    assertEquals(1.0, atmosphere.horizonBlendAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun horizonBlendTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      horizonBlendTransition(transition)
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.horizonBlendTransition)
  }

  @Test
  @UiThreadTest
  fun horizonBlendTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      horizonBlendTransition {
        duration(100)
        delay(200)
      }
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.horizonBlendTransition)
  }

  @Test
  @UiThreadTest
  fun rangeTest() {
    val atmosphere = atmosphere {
      range(listOf(0.0, 1.0))
    }
    setupAtmosphere(atmosphere)
    assertEquals(listOf(0.0, 1.0), atmosphere.range)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun rangeAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))

    val atmosphere = atmosphere {
      range(expression)
    }
    setupAtmosphere(atmosphere)
    assertEquals(expression.toString(), atmosphere.rangeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun rangeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      rangeTransition(transition)
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.rangeTransition)
  }

  @Test
  @UiThreadTest
  fun rangeTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      rangeTransition {
        duration(100)
        delay(200)
      }
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.rangeTransition)
  }

  @Test
  @UiThreadTest
  fun spaceColorAsColorIntTest() {
    val atmosphere = atmosphere {
      spaceColor(Color.CYAN)
    }
    setupAtmosphere(atmosphere)
    assertEquals(Color.CYAN, atmosphere.spaceColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun spaceColorTest() {
    val atmosphere = atmosphere {
      spaceColor("rgba(0, 0, 0, 1)")
    }
    setupAtmosphere(atmosphere)
    assertEquals("rgba(0, 0, 0, 1)", atmosphere.spaceColor)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun spaceColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }

    val atmosphere = atmosphere {
      spaceColor(expression)
    }
    setupAtmosphere(atmosphere)
    assertEquals(expression.toString(), atmosphere.spaceColorAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun spaceColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      spaceColorTransition(transition)
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.spaceColorTransition)
  }

  @Test
  @UiThreadTest
  fun spaceColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      spaceColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.spaceColorTransition)
  }

  @Test
  @UiThreadTest
  fun starIntensityTest() {
    val atmosphere = atmosphere {
      starIntensity(1.0)
    }
    setupAtmosphere(atmosphere)
    assertEquals(1.0, atmosphere.starIntensity!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun starIntensityAsExpressionTest() {
    val expression = literal(1.0)

    val atmosphere = atmosphere {
      starIntensity(expression)
    }
    setupAtmosphere(atmosphere)
    assertEquals(1.0, atmosphere.starIntensityAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun starIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      starIntensityTransition(transition)
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.starIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun starIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      starIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.starIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun verticalRangeTest() {
    val atmosphere = atmosphere {
      verticalRange(listOf(0.0, 1.0))
    }
    setupAtmosphere(atmosphere)
    assertEquals(listOf(0.0, 1.0), atmosphere.verticalRange)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun verticalRangeAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))

    val atmosphere = atmosphere {
      verticalRange(expression)
    }
    setupAtmosphere(atmosphere)
    assertEquals(expression.toString(), atmosphere.verticalRangeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun verticalRangeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      verticalRangeTransition(transition)
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.verticalRangeTransition)
  }

  @Test
  @UiThreadTest
  fun verticalRangeTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val atmosphere = atmosphere {
      verticalRangeTransition {
        duration(100)
        delay(200)
      }
    }
    setupAtmosphere(atmosphere)
    assertEquals(transition, atmosphere.verticalRangeTransition)
  }
}

// End of generated file.