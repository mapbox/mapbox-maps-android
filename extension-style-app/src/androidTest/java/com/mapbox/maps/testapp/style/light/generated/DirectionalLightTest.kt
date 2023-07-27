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
 * Basic smoke tests for Directional light.
 */
@RunWith(AndroidJUnit4::class)
class DirectionalLightTest : BaseStyleTest() {
  @Test
  @UiThreadTest
  fun castShadowsTest() {
    val light = directionalLight {
      castShadows(true)
    }
    setupLight(ambientLight { }, light)
    assertEquals(true, light.castShadows)
  }
  // Add Expression Test
  @Test
  @UiThreadTest
  fun castShadowsAsExpressionTest() {
    val expression = literal(true)
    val light = directionalLight {
      castShadows(expression)
    }
    setupLight(ambientLight { }, light)
    assertEquals(expression.toString(), light.castShadowsAsExpression.toString())
  }
  @Test
  @UiThreadTest
  fun colorAsColorIntTest() {
    val light = directionalLight {
      color(Color.CYAN)
    }
    setupLight(ambientLight { }, light)
    assertEquals(Color.CYAN, light.colorAsColorInt)
  }
  @Test
  @UiThreadTest
  fun colorTest() {
    val light = directionalLight {
      color("rgba(0, 0, 0, 1)")
    }
    setupLight(ambientLight { }, light)
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
    val light = directionalLight {
      color(expression)
    }
    setupLight(ambientLight { }, light)
    assertEquals(expression.toString(), light.colorAsExpression.toString())
  }
  @Test
  @UiThreadTest
  fun colorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      colorTransition(transition)
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.colorTransition)
  }
  @Test
  @UiThreadTest
  fun colorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      colorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.colorTransition)
  }
  @Test
  @UiThreadTest
  fun directionTest() {
    val light = directionalLight {
      direction(listOf(0.0, 1.0))
    }
    setupLight(ambientLight { }, light)
    assertEquals(listOf(0.0, 1.0), light.direction)
  }
  // Add Expression Test
  @Test
  @UiThreadTest
  fun directionAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val light = directionalLight {
      direction(expression)
    }
    setupLight(ambientLight { }, light)
    assertEquals(expression.toString(), light.directionAsExpression.toString())
  }
  @Test
  @UiThreadTest
  fun directionTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      directionTransition(transition)
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.directionTransition)
  }
  @Test
  @UiThreadTest
  fun directionTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      directionTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.directionTransition)
  }
  @Test
  @UiThreadTest
  fun intensityTest() {
    val light = directionalLight {
      intensity(1.0)
    }
    setupLight(ambientLight { }, light)
    assertEquals(1.0, light.intensity!!, 1E-5)
  }
  // Add Expression Test
  @Test
  @UiThreadTest
  fun intensityAsExpressionTest() {
    val expression = literal(1.0)
    val light = directionalLight {
      intensity(expression)
    }
    setupLight(ambientLight { }, light)
    assertEquals(1.0, light.intensityAsExpression?.contents as Double, 1E-5)
  }
  @Test
  @UiThreadTest
  fun intensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      intensityTransition(transition)
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.intensityTransition)
  }
  @Test
  @UiThreadTest
  fun intensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      intensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.intensityTransition)
  }
  @Test
  @UiThreadTest
  fun shadowIntensityTest() {
    val light = directionalLight {
      shadowIntensity(1.0)
    }
    setupLight(ambientLight { }, light)
    assertEquals(1.0, light.shadowIntensity!!, 1E-5)
  }
  // Add Expression Test
  @Test
  @UiThreadTest
  fun shadowIntensityAsExpressionTest() {
    val expression = literal(1.0)
    val light = directionalLight {
      shadowIntensity(expression)
    }
    setupLight(ambientLight { }, light)
    assertEquals(1.0, light.shadowIntensityAsExpression?.contents as Double, 1E-5)
  }
  @Test
  @UiThreadTest
  fun shadowIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      shadowIntensityTransition(transition)
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.shadowIntensityTransition)
  }
  @Test
  @UiThreadTest
  fun shadowIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val light = directionalLight {
      shadowIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLight(ambientLight { }, light)
    assertEquals(transition, light.shadowIntensityTransition)
  }
}
// End of generated file.