// This file is generated.

package com.mapbox.maps.testapp.style.precipitations.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.precipitations.generated.rain
import com.mapbox.maps.extension.style.types.transitionOptions
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for Rain
 */
@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
class RainTest : BaseStyleTest() {
  @Test
  @UiThreadTest
  fun centerThinningTest() {
    val rain = rain {
      centerThinning(1.0)
    }
    setupRain(rain)
    assertEquals(1.0, rain.centerThinning!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun centerThinningAsExpressionTest() {
    val expression = literal(1.0)

    val rain = rain {
      centerThinning(expression)
    }
    setupRain(rain)
    assertEquals(1.0, rain.centerThinningAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun centerThinningTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      centerThinningTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.centerThinningTransition)
  }

  @Test
  @UiThreadTest
  fun centerThinningTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      centerThinningTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.centerThinningTransition)
  }

  @Test
  @UiThreadTest
  fun colorAsColorIntTest() {
    val rain = rain {
      color(Color.CYAN)
    }
    setupRain(rain)
    assertEquals(Color.CYAN, rain.colorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun colorTest() {
    val rain = rain {
      color("rgba(0, 0, 0, 1)")
    }
    setupRain(rain)
    assertEquals("rgba(0, 0, 0, 1)", rain.color)
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

    val rain = rain {
      color(expression)
    }
    setupRain(rain)
    assertEquals(expression.toString(), rain.colorAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun colorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      colorTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.colorTransition)
  }

  @Test
  @UiThreadTest
  fun colorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.colorTransition)
  }
  @Test
  @UiThreadTest
  fun densityTest() {
    val rain = rain {
      density(1.0)
    }
    setupRain(rain)
    assertEquals(1.0, rain.density!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun densityAsExpressionTest() {
    val expression = literal(1.0)

    val rain = rain {
      density(expression)
    }
    setupRain(rain)
    assertEquals(1.0, rain.densityAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun densityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      densityTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.densityTransition)
  }

  @Test
  @UiThreadTest
  fun densityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      densityTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.densityTransition)
  }
  @Test
  @UiThreadTest
  fun directionTest() {
    val rain = rain {
      direction(listOf(0.0, 1.0))
    }
    setupRain(rain)
    assertEquals(listOf(0.0, 1.0), rain.direction)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun directionAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))

    val rain = rain {
      direction(expression)
    }
    setupRain(rain)
    assertEquals(expression.toString(), rain.directionAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun directionTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      directionTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.directionTransition)
  }

  @Test
  @UiThreadTest
  fun directionTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      directionTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.directionTransition)
  }
  @Test
  @UiThreadTest
  fun distortionStrengthTest() {
    val rain = rain {
      distortionStrength(1.0)
    }
    setupRain(rain)
    assertEquals(1.0, rain.distortionStrength!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun distortionStrengthAsExpressionTest() {
    val expression = literal(1.0)

    val rain = rain {
      distortionStrength(expression)
    }
    setupRain(rain)
    assertEquals(1.0, rain.distortionStrengthAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun distortionStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      distortionStrengthTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.distortionStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun distortionStrengthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      distortionStrengthTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.distortionStrengthTransition)
  }
  @Test
  @UiThreadTest
  fun dropletSizeTest() {
    val rain = rain {
      dropletSize(listOf(0.0, 1.0))
    }
    setupRain(rain)
    assertEquals(listOf(0.0, 1.0), rain.dropletSize)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun dropletSizeAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))

    val rain = rain {
      dropletSize(expression)
    }
    setupRain(rain)
    assertEquals(expression.toString(), rain.dropletSizeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun dropletSizeTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      dropletSizeTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.dropletSizeTransition)
  }

  @Test
  @UiThreadTest
  fun dropletSizeTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      dropletSizeTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.dropletSizeTransition)
  }
  @Test
  @UiThreadTest
  fun intensityTest() {
    val rain = rain {
      intensity(1.0)
    }
    setupRain(rain)
    assertEquals(1.0, rain.intensity!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun intensityAsExpressionTest() {
    val expression = literal(1.0)

    val rain = rain {
      intensity(expression)
    }
    setupRain(rain)
    assertEquals(1.0, rain.intensityAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun intensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      intensityTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.intensityTransition)
  }

  @Test
  @UiThreadTest
  fun intensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.intensityTransition)
  }
  @Test
  @UiThreadTest
  fun opacityTest() {
    val rain = rain {
      opacity(1.0)
    }
    setupRain(rain)
    assertEquals(1.0, rain.opacity!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun opacityAsExpressionTest() {
    val expression = literal(1.0)

    val rain = rain {
      opacity(expression)
    }
    setupRain(rain)
    assertEquals(1.0, rain.opacityAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun opacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      opacityTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.opacityTransition)
  }

  @Test
  @UiThreadTest
  fun opacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      opacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.opacityTransition)
  }
  @Test
  @UiThreadTest
  fun vignetteTest() {
    val rain = rain {
      vignette(1.0)
    }
    setupRain(rain)
    assertEquals(1.0, rain.vignette!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun vignetteAsExpressionTest() {
    val expression = literal(1.0)

    val rain = rain {
      vignette(expression)
    }
    setupRain(rain)
    assertEquals(1.0, rain.vignetteAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun vignetteTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      vignetteTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.vignetteTransition)
  }

  @Test
  @UiThreadTest
  fun vignetteTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      vignetteTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.vignetteTransition)
  }

  @Test
  @UiThreadTest
  fun vignetteColorAsColorIntTest() {
    val rain = rain {
      vignetteColor(Color.CYAN)
    }
    setupRain(rain)
    assertEquals(Color.CYAN, rain.vignetteColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun vignetteColorTest() {
    val rain = rain {
      vignetteColor("rgba(0, 0, 0, 1)")
    }
    setupRain(rain)
    assertEquals("rgba(0, 0, 0, 1)", rain.vignetteColor)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun vignetteColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }

    val rain = rain {
      vignetteColor(expression)
    }
    setupRain(rain)
    assertEquals(expression.toString(), rain.vignetteColorAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun vignetteColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      vignetteColorTransition(transition)
    }
    setupRain(rain)
    assertEquals(transition, rain.vignetteColorTransition)
  }

  @Test
  @UiThreadTest
  fun vignetteColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val rain = rain {
      vignetteColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupRain(rain)
    assertEquals(transition, rain.vignetteColorTransition)
  }
}

// End of generated file.