// This file is generated.

package com.mapbox.maps.testapp.style.terrain.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.terrain.generated.*
import com.mapbox.maps.extension.style.types.transitionOptions
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for Terrain
 */
@RunWith(AndroidJUnit4::class)
class TerrainTest : BaseStyleTest() {
  private val sourceId = "1"

  @Test
  @UiThreadTest
  fun exaggerationTest() {
    val terrain = terrain(sourceId) {
      exaggeration(1.0)
    }
    setupTerrain(terrain)
    assertEquals(1.0, terrain.exaggeration!!, 1E-5)
  }

  // Add Expression Test
  @Test
  @UiThreadTest
  fun exaggerationAsExpressionTest() {
    val expression = literal(1.0)

    val terrain = terrain(sourceId) {
      exaggeration(expression)
    }
    setupTerrain(terrain)
    assertEquals(1.0, terrain.exaggerationAsExpression?.contents as Double, 1E-5)
  }

  @Test
  @UiThreadTest
  fun exaggerationTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val terrain = terrain(sourceId) {
      exaggerationTransition(transition)
    }
    setupTerrain(terrain)
    assertEquals(transition, terrain.exaggerationTransition)
  }

  @Test
  @UiThreadTest
  fun exaggerationTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val terrain = terrain(sourceId) {
      exaggerationTransition {
        duration(100)
        delay(200)
      }
    }
    setupTerrain(terrain)
    assertEquals(transition, terrain.exaggerationTransition)
  }
}

// End of generated file.