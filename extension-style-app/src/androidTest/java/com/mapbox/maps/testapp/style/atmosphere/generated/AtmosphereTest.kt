// This file is generated.

package com.mapbox.maps.testapp.style.atmosphere.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
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
}

// End of generated file.