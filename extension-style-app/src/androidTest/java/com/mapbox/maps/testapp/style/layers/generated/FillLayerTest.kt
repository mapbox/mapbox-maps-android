// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for FillLayer
 */
@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
class FillLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = fillLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = fillLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = fillLayer("id", "source") {
      maxZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.maxZoom)
  }

  @Test
  @UiThreadTest
  fun filterTest() {
    val expression = eq {
      get {
        literal("undefined")
      }
      literal(1.0)
    }
    // Set filter property.
    val layer = fillLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun fillConstructBridgeGuardRailTest() {
    val testValue = true
    val layer = fillLayer("id", "source") {
      fillConstructBridgeGuardRail(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillConstructBridgeGuardRail?.toString())
  }

  @Test
  @UiThreadTest
  fun fillConstructBridgeGuardRailAsExpressionTest() {
    val expression = get {
      literal("boolean")
    }
    val layer = fillLayer("id", "source") {
      fillConstructBridgeGuardRail(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillConstructBridgeGuardRailAsExpression.toString())
    assertEquals(null, layer.fillConstructBridgeGuardRail)
  }

  @Test
  @UiThreadTest
  fun fillElevationReferenceTest() {
    val layer = fillLayer("id", "source") {
      fillElevationReference(FillElevationReference.NONE)
    }
    setupLayer(layer)
    assertEquals(FillElevationReference.NONE, layer.fillElevationReference)
  }

  @Test
  @UiThreadTest
  fun fillElevationReferenceAsExpressionTest() {
    val expression = literal("none")
    val layer = fillLayer("id", "source") {
      fillElevationReference(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillElevationReferenceAsExpression.toString())
    assertEquals(FillElevationReference.NONE, layer.fillElevationReference!!)
  }

  @Test
  @UiThreadTest
  fun fillSortKeyTest() {
    val testValue = 1.0
    val layer = fillLayer("id", "source") {
      fillSortKey(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillSortKey!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillSortKeyAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillLayer("id", "source") {
      fillSortKey(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillSortKeyAsExpression.toString())
    assertEquals(null, layer.fillSortKey)
  }

  @Test
  @UiThreadTest
  fun fillAntialiasTest() {
    val testValue = true
    val layer = fillLayer("id", "source") {
      fillAntialias(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillAntialias?.toString())
  }

  @Test
  @UiThreadTest
  fun fillAntialiasAsExpressionTest() {
    val expression = literal(true)
    val layer = fillLayer("id", "source") {
      fillAntialias(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillAntialiasAsExpression.toString())
    assertEquals(true, layer.fillAntialias!!)
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillBridgeGuardRailColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillBridgeGuardRailColorAsExpression.toString())
    assertEquals(null, layer.fillBridgeGuardRailColor)
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColorUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.fillBridgeGuardRailColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColorUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.fillBridgeGuardRailColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorAsColorIntTest() {
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillBridgeGuardRailColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorUseTheme() {
    val theme = "none"
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.fillBridgeGuardRailColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillBridgeGuardRailColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillBridgeGuardRailColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillBridgeGuardRailColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillBridgeGuardRailColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillLayer("id", "source") {
      fillColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillLayer("id", "source") {
      fillColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillColorAsExpression.toString())
    assertEquals(null, layer.fillColor)
  }

  @Test
  @UiThreadTest
  fun fillColorUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = fillLayer("id", "source") {
      fillColorUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.fillColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillColorUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = fillLayer("id", "source") {
      fillColorUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.fillColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillColorAsColorIntTest() {
    val layer = fillLayer("id", "source") {
      fillColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillColorUseTheme() {
    val theme = "none"
    val layer = fillLayer("id", "source") {
      fillColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.fillColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun fillColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillEmissiveStrengthTest() {
    val testValue = 1.0
    val layer = fillLayer("id", "source") {
      fillEmissiveStrength(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillEmissiveStrength!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillEmissiveStrengthAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillLayer("id", "source") {
      fillEmissiveStrength(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillEmissiveStrength!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillEmissiveStrengthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun fillEmissiveStrengthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillEmissiveStrengthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun fillOpacityTest() {
    val testValue = 1.0
    val layer = fillLayer("id", "source") {
      fillOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillOpacityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillLayer("id", "source") {
      fillOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillOpacityAsExpression.toString())
    assertEquals(null, layer.fillOpacity)
  }

  @Test
  @UiThreadTest
  fun fillOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillLayer("id", "source") {
      fillOutlineColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillOutlineColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillLayer("id", "source") {
      fillOutlineColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillOutlineColorAsExpression.toString())
    assertEquals(null, layer.fillOutlineColor)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = fillLayer("id", "source") {
      fillOutlineColorUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.fillOutlineColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = fillLayer("id", "source") {
      fillOutlineColorUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.fillOutlineColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorAsColorIntTest() {
    val layer = fillLayer("id", "source") {
      fillOutlineColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillOutlineColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorUseTheme() {
    val theme = "none"
    val layer = fillLayer("id", "source") {
      fillOutlineColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.fillOutlineColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOutlineColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOutlineColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillOutlineColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillOutlineColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillOutlineColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillPatternTest() {
    val testValue = "abc"
    val layer = fillLayer("id", "source") {
      fillPattern(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillPattern?.toString())
  }

  @Test
  @UiThreadTest
  fun fillPatternAsExpressionTest() {
    val expression = image {
      string {
        get {
          literal("resolvedImage")
        }
      }
    }
    val layer = fillLayer("id", "source") {
      fillPattern(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillPatternAsExpression.toString())
    assertEquals(null, layer.fillPattern)
  }

  @Test
  @UiThreadTest
  fun fillTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = fillLayer("id", "source") {
      fillTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun fillTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = fillLayer("id", "source") {
      fillTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.fillTranslate!!)
  }

  @Test
  @UiThreadTest
  fun fillTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillTranslateAnchorTest() {
    val layer = fillLayer("id", "source") {
      fillTranslateAnchor(FillTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(FillTranslateAnchor.MAP, layer.fillTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun fillTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = fillLayer("id", "source") {
      fillTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillTranslateAnchorAsExpression.toString())
    assertEquals(FillTranslateAnchor.MAP, layer.fillTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillTunnelStructureColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillTunnelStructureColorAsExpression.toString())
    assertEquals(null, layer.fillTunnelStructureColor)
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColorUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.fillTunnelStructureColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColorUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.fillTunnelStructureColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorAsColorIntTest() {
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillTunnelStructureColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorUseTheme() {
    val theme = "none"
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.fillTunnelStructureColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillTunnelStructureColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillTunnelStructureColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillTunnelStructureColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillTunnelStructureColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillZOffsetTest() {
    val testValue = 1.0
    val layer = fillLayer("id", "source") {
      fillZOffset(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillZOffset!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillZOffsetAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillLayer("id", "source") {
      fillZOffset(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillZOffsetAsExpression.toString())
    assertEquals(null, layer.fillZOffset)
  }

  @Test
  @UiThreadTest
  fun fillZOffsetTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillZOffsetTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillZOffsetTransition)
  }

  @Test
  @UiThreadTest
  fun fillZOffsetTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillLayer("id", "source") {
      fillZOffsetTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillZOffsetTransition)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = fillLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = fillLayer("id", "source") {
      visibility(
        concat {
          literal("no")
          literal("ne")
        }
      )
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", FillLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", FillLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", FillLayer.defaultMaxZoom)
    assertNotNull("defaultFillConstructBridgeGuardRail should not be null", FillLayer.defaultFillConstructBridgeGuardRail)
    assertNotNull("defaultFillConstructBridgeGuardRailAsExpression should not be null", FillLayer.defaultFillConstructBridgeGuardRailAsExpression)
    assertNotNull("defaultFillElevationReference should not be null", FillLayer.defaultFillElevationReference)
    assertNotNull("defaultFillElevationReferenceAsExpression should not be null", FillLayer.defaultFillElevationReferenceAsExpression)
    assertNotNull("defaultFillSortKey should not be null", FillLayer.defaultFillSortKey)
    assertNotNull("defaultFillSortKeyAsExpression should not be null", FillLayer.defaultFillSortKeyAsExpression)
    assertNotNull("defaultFillAntialias should not be null", FillLayer.defaultFillAntialias)
    assertNotNull("defaultFillAntialiasAsExpression should not be null", FillLayer.defaultFillAntialiasAsExpression)
    assertNotNull("defaultFillBridgeGuardRailColor should not be null", FillLayer.defaultFillBridgeGuardRailColor)
    assertNotNull("defaultFillBridgeGuardRailColorAsExpression should not be null", FillLayer.defaultFillBridgeGuardRailColorAsExpression)
    assertNotNull("defaultFillBridgeGuardRailColorAsColorInt should not be null", FillLayer.defaultFillBridgeGuardRailColorAsColorInt)
    assertNotNull("defaultFillBridgeGuardRailColorUseTheme should not be null", FillLayer.defaultFillBridgeGuardRailColorUseTheme)
    assertNotNull("defaultFillBridgeGuardRailColorUseThemeAsExpression should not be null", FillLayer.defaultFillBridgeGuardRailColorUseThemeAsExpression)
    assertNotNull("defaultFillBridgeGuardRailColorTransition should not be null", FillLayer.defaultFillBridgeGuardRailColorTransition)
    assertNotNull("defaultFillColor should not be null", FillLayer.defaultFillColor)
    assertNotNull("defaultFillColorAsExpression should not be null", FillLayer.defaultFillColorAsExpression)
    assertNotNull("defaultFillColorAsColorInt should not be null", FillLayer.defaultFillColorAsColorInt)
    assertNotNull("defaultFillColorUseTheme should not be null", FillLayer.defaultFillColorUseTheme)
    assertNotNull("defaultFillColorUseThemeAsExpression should not be null", FillLayer.defaultFillColorUseThemeAsExpression)
    assertNotNull("defaultFillColorTransition should not be null", FillLayer.defaultFillColorTransition)
    assertNotNull("defaultFillEmissiveStrength should not be null", FillLayer.defaultFillEmissiveStrength)
    assertNotNull("defaultFillEmissiveStrengthAsExpression should not be null", FillLayer.defaultFillEmissiveStrengthAsExpression)
    assertNotNull("defaultFillEmissiveStrengthTransition should not be null", FillLayer.defaultFillEmissiveStrengthTransition)
    assertNotNull("defaultFillOpacity should not be null", FillLayer.defaultFillOpacity)
    assertNotNull("defaultFillOpacityAsExpression should not be null", FillLayer.defaultFillOpacityAsExpression)
    assertNotNull("defaultFillOpacityTransition should not be null", FillLayer.defaultFillOpacityTransition)
    assertNotNull("defaultFillOutlineColor should not be null", FillLayer.defaultFillOutlineColor)
    assertNotNull("defaultFillOutlineColorAsExpression should not be null", FillLayer.defaultFillOutlineColorAsExpression)
    assertNotNull("defaultFillOutlineColorAsColorInt should not be null", FillLayer.defaultFillOutlineColorAsColorInt)
    assertNotNull("defaultFillOutlineColorUseTheme should not be null", FillLayer.defaultFillOutlineColorUseTheme)
    assertNotNull("defaultFillOutlineColorUseThemeAsExpression should not be null", FillLayer.defaultFillOutlineColorUseThemeAsExpression)
    assertNotNull("defaultFillOutlineColorTransition should not be null", FillLayer.defaultFillOutlineColorTransition)
    assertNotNull("defaultFillPattern should not be null", FillLayer.defaultFillPattern)
    assertNotNull("defaultFillPatternAsExpression should not be null", FillLayer.defaultFillPatternAsExpression)
    assertNotNull("defaultFillTranslate should not be null", FillLayer.defaultFillTranslate)
    assertNotNull("defaultFillTranslateAsExpression should not be null", FillLayer.defaultFillTranslateAsExpression)
    assertNotNull("defaultFillTranslateTransition should not be null", FillLayer.defaultFillTranslateTransition)
    assertNotNull("defaultFillTranslateAnchor should not be null", FillLayer.defaultFillTranslateAnchor)
    assertNotNull("defaultFillTranslateAnchorAsExpression should not be null", FillLayer.defaultFillTranslateAnchorAsExpression)
    assertNotNull("defaultFillTunnelStructureColor should not be null", FillLayer.defaultFillTunnelStructureColor)
    assertNotNull("defaultFillTunnelStructureColorAsExpression should not be null", FillLayer.defaultFillTunnelStructureColorAsExpression)
    assertNotNull("defaultFillTunnelStructureColorAsColorInt should not be null", FillLayer.defaultFillTunnelStructureColorAsColorInt)
    assertNotNull("defaultFillTunnelStructureColorUseTheme should not be null", FillLayer.defaultFillTunnelStructureColorUseTheme)
    assertNotNull("defaultFillTunnelStructureColorUseThemeAsExpression should not be null", FillLayer.defaultFillTunnelStructureColorUseThemeAsExpression)
    assertNotNull("defaultFillTunnelStructureColorTransition should not be null", FillLayer.defaultFillTunnelStructureColorTransition)
    assertNotNull("defaultFillZOffset should not be null", FillLayer.defaultFillZOffset)
    assertNotNull("defaultFillZOffsetAsExpression should not be null", FillLayer.defaultFillZOffsetAsExpression)
    assertNotNull("defaultFillZOffsetTransition should not be null", FillLayer.defaultFillZOffsetTransition)
  }

  @Test
  @UiThreadTest
  fun getLayerTest() {
    val filterTestValue = eq {
      get {
        literal("undefined")
      }
      literal(1.0)
    }
    val fillConstructBridgeGuardRailTestValue = true
    val fillElevationReferenceTestValue = FillElevationReference.NONE
    val fillSortKeyTestValue = 1.0
    val fillAntialiasTestValue = true
    val fillBridgeGuardRailColorTestValue = "rgba(0, 0, 0, 1)"
    val fillBridgeGuardRailColorUseThemeTestValue = "default"
    val fillColorTestValue = "rgba(0, 0, 0, 1)"
    val fillColorUseThemeTestValue = "default"
    val fillEmissiveStrengthTestValue = 1.0
    val fillOpacityTestValue = 1.0
    val fillOutlineColorTestValue = "rgba(0, 0, 0, 1)"
    val fillOutlineColorUseThemeTestValue = "default"
    val fillPatternTestValue = "abc"
    val fillTranslateTestValue = listOf(0.0, 1.0)
    val fillTranslateAnchorTestValue = FillTranslateAnchor.MAP
    val fillTunnelStructureColorTestValue = "rgba(0, 0, 0, 1)"
    val fillTunnelStructureColorUseThemeTestValue = "default"
    val fillZOffsetTestValue = 1.0

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = fillLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      fillConstructBridgeGuardRail(fillConstructBridgeGuardRailTestValue)
      fillElevationReference(fillElevationReferenceTestValue)
      fillSortKey(fillSortKeyTestValue)
      fillAntialias(fillAntialiasTestValue)
      fillBridgeGuardRailColor(fillBridgeGuardRailColorTestValue)
      fillBridgeGuardRailColorUseTheme(fillBridgeGuardRailColorUseThemeTestValue)
      fillColor(fillColorTestValue)
      fillColorUseTheme(fillColorUseThemeTestValue)
      fillEmissiveStrength(fillEmissiveStrengthTestValue)
      fillOpacity(fillOpacityTestValue)
      fillOutlineColor(fillOutlineColorTestValue)
      fillOutlineColorUseTheme(fillOutlineColorUseThemeTestValue)
      fillPattern(fillPatternTestValue)
      fillTranslate(fillTranslateTestValue)
      fillTranslateAnchor(fillTranslateAnchorTestValue)
      fillTunnelStructureColor(fillTunnelStructureColorTestValue)
      fillTunnelStructureColorUseTheme(fillTunnelStructureColorUseThemeTestValue)
      fillZOffset(fillZOffsetTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as FillLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(fillConstructBridgeGuardRailTestValue, cachedLayer.fillConstructBridgeGuardRail)
    assertEquals(fillElevationReferenceTestValue, cachedLayer.fillElevationReference)
    assertEquals(fillSortKeyTestValue, cachedLayer.fillSortKey)
    assertEquals(fillAntialiasTestValue, cachedLayer.fillAntialias)
    assertEquals(fillBridgeGuardRailColorTestValue, cachedLayer.fillBridgeGuardRailColor)
    assertEquals(fillBridgeGuardRailColorUseThemeTestValue, cachedLayer.fillBridgeGuardRailColorUseTheme)
    assertEquals(fillColorTestValue, cachedLayer.fillColor)
    assertEquals(fillColorUseThemeTestValue, cachedLayer.fillColorUseTheme)
    assertEquals(fillEmissiveStrengthTestValue, cachedLayer.fillEmissiveStrength)
    assertEquals(fillOpacityTestValue, cachedLayer.fillOpacity)
    assertEquals(fillOutlineColorTestValue, cachedLayer.fillOutlineColor)
    assertEquals(fillOutlineColorUseThemeTestValue, cachedLayer.fillOutlineColorUseTheme)
    assertEquals(fillPatternTestValue, cachedLayer.fillPattern)
    assertEquals(fillTranslateTestValue, cachedLayer.fillTranslate)
    assertEquals(fillTranslateAnchorTestValue, cachedLayer.fillTranslateAnchor)
    assertEquals(fillTunnelStructureColorTestValue, cachedLayer.fillTunnelStructureColor)
    assertEquals(fillTunnelStructureColorUseThemeTestValue, cachedLayer.fillTunnelStructureColorUseTheme)
    assertEquals(fillZOffsetTestValue, cachedLayer.fillZOffset)
  }
}

// End of generated file.