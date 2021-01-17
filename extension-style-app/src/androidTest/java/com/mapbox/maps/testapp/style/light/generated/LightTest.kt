// This file is generated.

package com.mapbox.maps.testapp.style.light.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.light.LightPosition
import com.mapbox.maps.extension.style.light.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for Light
 */
@RunWith(AndroidJUnit4::class)
class LightTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun anchorTest() {
    val light = light {
      anchor(Anchor.MAP)
    }
    setupLight(light)
    assertEquals(Anchor.MAP, light.anchor)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun anchorAsExpressionTest() {
    val expression = literal("map")

    val light = light {
      anchor(expression)
    }
    setupLight(light)
    assertEquals(expression.toString(), light.anchorAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun colorAsColorIntTest() {
    val light = light {
      color(Color.CYAN)
    }
    setupLight(light)
    assertEquals(Color.CYAN, light.colorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun colorTest() {
    val light = light {
      color("rgba(0, 0, 0, 1)")
    }
    setupLight(light)
    assertEquals("rgba(0, 0, 0, 1)", light.color)
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

    val light = light {
      color(expression)
    }
    setupLight(light)
    assertEquals(expression.toString(), light.colorAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun colorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = light {
      colorTransition(transition)
    }
    setupLight(light)
    assertEquals(transition, light.colorTransition)
  }

  @Test
  @UiThreadTest
  fun colorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = light {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(light)
    assertEquals(transition, light.colorTransition)
  }

  @Test
  @UiThreadTest
  fun intensityTest() {
    val light = light {
      intensity(1.0)
    }
    setupLight(light)
    assertEquals(1.0, light.intensity!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun intensityAsExpressionTest() {
    val expression = literal(1.0)

    val light = light {
      intensity(expression)
    }
    setupLight(light)
    assertEquals(1.0, light.intensityAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun intensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = light {
      intensityTransition(transition)
    }
    setupLight(light)
    assertEquals(transition, light.intensityTransition)
  }

  @Test
  @UiThreadTest
  fun intensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = light {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(light)
    assertEquals(transition, light.intensityTransition)
  }

  @Test
  @UiThreadTest
  fun positionDslTest() {
    val position = LightPosition(1.0, 2.0, 3.0)
    val light = light {
      position(1.0, 2.0, 3.0)
    }
    setupLight(light)
    assertEquals(position, light.position)
  }

  @Test
  @UiThreadTest
  fun positionTest() {
    val light = light {
      position(LightPosition(0.0, 1.0, 2.0))
    }
    setupLight(light)
    assertEquals(LightPosition(0.0, 1.0, 2.0), light.position)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun positionAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0, 2.0))

    val light = light {
      position(expression)
    }
    setupLight(light)
    assertEquals(expression.toString(), light.positionAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun positionTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = light {
      positionTransition(transition)
    }
    setupLight(light)
    assertEquals(transition, light.positionTransition)
  }

  @Test
  @UiThreadTest
  fun positionTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = light {
      positionTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(light)
    assertEquals(transition, light.positionTransition)
  }
}

// End of generated file.