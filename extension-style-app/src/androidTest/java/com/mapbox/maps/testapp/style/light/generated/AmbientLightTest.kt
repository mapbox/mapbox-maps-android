// This file is generated.

package com.mapbox.maps.testapp.style.light.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.light.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for Ambient light.
 */
@RunWith(AndroidJUnit4::class)
class AmbientLightTest : BaseStyleTest() {
  @Test
  @UiThreadTest
  fun colorAsColorIntTest() {
    val light = ambientLight {
      color(Color.CYAN)
    }
    setupLight(light, directionalLight { })
    assertEquals(Color.CYAN, light.colorAsColorInt)
  }
  @Test
  @UiThreadTest
  fun colorTest() {
    val light = ambientLight {
      color("rgba(0, 0, 0, 1)")
    }
    setupLight(light, directionalLight { })
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
    val light = ambientLight {
      color(expression)
    }
    setupLight(light, directionalLight { })
    assertEquals(expression.toString(), light.colorAsExpression.toString())
  }
  @Test
  @UiThreadTest
  fun colorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = ambientLight {
      colorTransition(transition)
    }
    setupLight(light, directionalLight { })
    assertEquals(transition, light.colorTransition)
  }
  @Test
  @UiThreadTest
  fun colorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = ambientLight {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(light, directionalLight { })
    assertEquals(transition, light.colorTransition)
  }
  @Test
  @UiThreadTest
  fun intensityTest() {
    val light = ambientLight {
      intensity(1.0)
    }
    setupLight(light, directionalLight { })
    assertEquals(1.0, light.intensity!!, 1E-5)
  }
  // Add Expression Test
  @Test
  @UiThreadTest
  fun intensityAsExpressionTest() {
    val expression = literal(1.0)
    val light = ambientLight {
      intensity(expression)
    }
    setupLight(light, directionalLight { })
    assertEquals(1.0, light.intensityAsExpression?.contents as Double, 1E-5)
  }
  @Test
  @UiThreadTest
  fun intensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = ambientLight {
      intensityTransition(transition)
    }
    setupLight(light, directionalLight { })
    assertEquals(transition, light.intensityTransition)
  }
  @Test
  @UiThreadTest
  fun intensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = ambientLight {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(light, directionalLight { })
    assertEquals(transition, light.intensityTransition)
  }
}
// End of generated file.