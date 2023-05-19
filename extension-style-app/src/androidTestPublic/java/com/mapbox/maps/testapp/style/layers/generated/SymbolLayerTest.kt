// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for SymbolLayer
 */
@RunWith(AndroidJUnit4::class)
class SymbolLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = symbolLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = symbolLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = symbolLayer("id", "source") {
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
    val layer = symbolLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun iconAllowOverlapTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconAllowOverlap?.toString())
  }

  @Test
  @UiThreadTest
  fun iconAllowOverlapAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconAllowOverlapAsExpression.toString())
    assertEquals(true, layer.iconAllowOverlap!!)
  }

  @Test
  @UiThreadTest
  fun iconAnchorTest() {
    val layer = symbolLayer("id", "source") {
      iconAnchor(IconAnchor.CENTER)
    }
    setupLayer(layer)
    assertEquals(IconAnchor.CENTER, layer.iconAnchor)
  }

  @Test
  @UiThreadTest
  fun iconAnchorAsExpressionTest() {
    val expression = toString {
      get {
        literal("enum")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconAnchorAsExpression.toString())
    assertEquals(null, layer.iconAnchor)
  }

  @Test
  @UiThreadTest
  fun iconIgnorePlacementTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      iconIgnorePlacement(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconIgnorePlacement?.toString())
  }

  @Test
  @UiThreadTest
  fun iconIgnorePlacementAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      iconIgnorePlacement(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconIgnorePlacementAsExpression.toString())
    assertEquals(true, layer.iconIgnorePlacement!!)
  }

  @Test
  @UiThreadTest
  fun iconImageTest() {
    val testValue = "abc"
    val layer = symbolLayer("id", "source") {
      iconImage(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconImage?.toString())
  }

  @Test
  @UiThreadTest
  fun iconImageAsExpressionTest() {
    val expression = image {
      string {
        get {
          literal("resolvedImage")
        }
      }
    }
    val layer = symbolLayer("id", "source") {
      iconImage(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconImageAsExpression.toString())
    assertEquals(null, layer.iconImage)
  }

  @Test
  @UiThreadTest
  fun iconKeepUprightTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      iconKeepUpright(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconKeepUpright?.toString())
  }

  @Test
  @UiThreadTest
  fun iconKeepUprightAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      iconKeepUpright(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconKeepUprightAsExpression.toString())
    assertEquals(true, layer.iconKeepUpright!!)
  }

  @Test
  @UiThreadTest
  fun iconOffsetTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = symbolLayer("id", "source") {
      iconOffset(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconOffset?.toString())
  }

  @Test
  @UiThreadTest
  fun iconOffsetAsExpressionTest() {
    val expression = array {
      literal("number")
      literal(2)
      get {
        literal("array")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconOffset(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconOffsetAsExpression.toString())
    assertEquals(null, layer.iconOffset)
  }

  @Test
  @UiThreadTest
  fun iconOptionalTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      iconOptional(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconOptional?.toString())
  }

  @Test
  @UiThreadTest
  fun iconOptionalAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      iconOptional(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconOptionalAsExpression.toString())
    assertEquals(true, layer.iconOptional!!)
  }

  @Test
  @UiThreadTest
  fun iconPaddingTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      iconPadding(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.iconPadding!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun iconPaddingAsExpressionTest() {
    val expression = literal(1.0)
    val layer = symbolLayer("id", "source") {
      iconPadding(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.iconPaddingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.iconPadding!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun iconPitchAlignmentTest() {
    val layer = symbolLayer("id", "source") {
      iconPitchAlignment(IconPitchAlignment.MAP)
    }
    setupLayer(layer)
    assertEquals(IconPitchAlignment.MAP, layer.iconPitchAlignment)
  }

  @Test
  @UiThreadTest
  fun iconPitchAlignmentAsExpressionTest() {
    val expression = literal("map")
    val layer = symbolLayer("id", "source") {
      iconPitchAlignment(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconPitchAlignmentAsExpression.toString())
    assertEquals(IconPitchAlignment.MAP, layer.iconPitchAlignment!!)
  }

  @Test
  @UiThreadTest
  fun iconRotateTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      iconRotate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.iconRotate!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun iconRotateAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconRotate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconRotateAsExpression.toString())
    assertEquals(null, layer.iconRotate)
  }

  @Test
  @UiThreadTest
  fun iconRotationAlignmentTest() {
    val layer = symbolLayer("id", "source") {
      iconRotationAlignment(IconRotationAlignment.MAP)
    }
    setupLayer(layer)
    assertEquals(IconRotationAlignment.MAP, layer.iconRotationAlignment)
  }

  @Test
  @UiThreadTest
  fun iconRotationAlignmentAsExpressionTest() {
    val expression = literal("map")
    val layer = symbolLayer("id", "source") {
      iconRotationAlignment(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconRotationAlignmentAsExpression.toString())
    assertEquals(IconRotationAlignment.MAP, layer.iconRotationAlignment!!)
  }

  @Test
  @UiThreadTest
  fun iconSizeTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      iconSize(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.iconSize!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun iconSizeAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconSize(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconSizeAsExpression.toString())
    assertEquals(null, layer.iconSize)
  }

  @Test
  @UiThreadTest
  fun iconTextFitTest() {
    val layer = symbolLayer("id", "source") {
      iconTextFit(IconTextFit.NONE)
    }
    setupLayer(layer)
    assertEquals(IconTextFit.NONE, layer.iconTextFit)
  }

  @Test
  @UiThreadTest
  fun iconTextFitAsExpressionTest() {
    val expression = literal("none")
    val layer = symbolLayer("id", "source") {
      iconTextFit(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconTextFitAsExpression.toString())
    assertEquals(IconTextFit.NONE, layer.iconTextFit!!)
  }

  @Test
  @UiThreadTest
  fun iconTextFitPaddingTest() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0)
    val layer = symbolLayer("id", "source") {
      iconTextFitPadding(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconTextFitPadding?.toString())
  }

  @Test
  @UiThreadTest
  fun iconTextFitPaddingAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0, 2.0, 3.0))
    val layer = symbolLayer("id", "source") {
      iconTextFitPadding(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconTextFitPaddingAsExpression.toString())
    assertEquals(listOf(0.0, 1.0, 2.0, 3.0), layer.iconTextFitPadding!!)
  }

  @Test
  @UiThreadTest
  fun symbolAvoidEdgesTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      symbolAvoidEdges(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.symbolAvoidEdges?.toString())
  }

  @Test
  @UiThreadTest
  fun symbolAvoidEdgesAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      symbolAvoidEdges(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.symbolAvoidEdgesAsExpression.toString())
    assertEquals(true, layer.symbolAvoidEdges!!)
  }

  @Test
  @UiThreadTest
  fun symbolPlacementTest() {
    val layer = symbolLayer("id", "source") {
      symbolPlacement(SymbolPlacement.POINT)
    }
    setupLayer(layer)
    assertEquals(SymbolPlacement.POINT, layer.symbolPlacement)
  }

  @Test
  @UiThreadTest
  fun symbolPlacementAsExpressionTest() {
    val expression = literal("point")
    val layer = symbolLayer("id", "source") {
      symbolPlacement(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.symbolPlacementAsExpression.toString())
    assertEquals(SymbolPlacement.POINT, layer.symbolPlacement!!)
  }

  @Test
  @UiThreadTest
  fun symbolSortKeyTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      symbolSortKey(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.symbolSortKey!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun symbolSortKeyAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      symbolSortKey(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.symbolSortKeyAsExpression.toString())
    assertEquals(null, layer.symbolSortKey)
  }

  @Test
  @UiThreadTest
  fun symbolSpacingTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      symbolSpacing(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.symbolSpacing!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun symbolSpacingAsExpressionTest() {
    val expression = literal(1.0)
    val layer = symbolLayer("id", "source") {
      symbolSpacing(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.symbolSpacingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.symbolSpacing!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun symbolZOrderTest() {
    val layer = symbolLayer("id", "source") {
      symbolZOrder(SymbolZOrder.AUTO)
    }
    setupLayer(layer)
    assertEquals(SymbolZOrder.AUTO, layer.symbolZOrder)
  }

  @Test
  @UiThreadTest
  fun symbolZOrderAsExpressionTest() {
    val expression = literal("auto")
    val layer = symbolLayer("id", "source") {
      symbolZOrder(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.symbolZOrderAsExpression.toString())
    assertEquals(SymbolZOrder.AUTO, layer.symbolZOrder!!)
  }

  @Test
  @UiThreadTest
  fun textAllowOverlapTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      textAllowOverlap(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textAllowOverlap?.toString())
  }

  @Test
  @UiThreadTest
  fun textAllowOverlapAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      textAllowOverlap(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textAllowOverlapAsExpression.toString())
    assertEquals(true, layer.textAllowOverlap!!)
  }

  @Test
  @UiThreadTest
  fun textAnchorTest() {
    val layer = symbolLayer("id", "source") {
      textAnchor(TextAnchor.CENTER)
    }
    setupLayer(layer)
    assertEquals(TextAnchor.CENTER, layer.textAnchor)
  }

  @Test
  @UiThreadTest
  fun textAnchorAsExpressionTest() {
    val expression = toString {
      get {
        literal("enum")
      }
    }
    val layer = symbolLayer("id", "source") {
      textAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textAnchorAsExpression.toString())
    assertEquals(null, layer.textAnchor)
  }

  @Test
  @UiThreadTest
  fun textFieldTest() {
    val testValue = formatted {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    val layer = symbolLayer("id", "source") {
      textField(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textField?.toString())
  }

  @Test
  @UiThreadTest
  fun textFieldAsExpressionTest() {
    val layer = symbolLayer("id", "source") {
      textField(
        format {
          formatSection("cyan") {
            fontScale(0.9)
            textFont(listOf("Open Sans Regular", "Arial Unicode MS Regular"))
            textColor(Color.CYAN)
          }
          formatSection("black") {
            fontScale(
              get {
                literal("scale")
              }
            )
            textFont(listOf("Open Sans Regular", "Arial Unicode MS Regular"))
            textColor(
              rgba {
                literal(0.0)
                literal(0.0)
                literal(0.0)
                literal(1.0)
              }
            )
          }
        }
      )
    }
    setupLayer(layer)
    assertEquals(
      "[format, cyan, {text-color=[rgba, 0.0, 255.0, 255.0, 1.0], font-scale=0.9, text-font=[literal, [Open Sans Regular, Arial Unicode MS Regular]]}, black, {text-color=[rgba, 0.0, 0.0, 0.0, 1.0], font-scale=[number, [get, scale]], text-font=[literal, [Open Sans Regular, Arial Unicode MS Regular]]}]",
      layer.textFieldAsExpression?.toString()
    )
  }

  @Test
  @UiThreadTest
  fun textFieldAsStringTest() {
    val layer = symbolLayer("id", "source") {
      textField("abc")
    }
    setupLayer(layer)
    assertEquals("abc", layer.textFieldAsString)
  }

  @Test
  @UiThreadTest
  fun textFieldSetDslTest() {
    val layer = symbolLayer("id", "source") {
      textField {
        formattedSection("cyan") {
          fontScale = 0.9
          fontStack = listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
          textColorAsInt = Color.CYAN
        }
        formattedSection("black") {
          fontScale = 2.0
          fontStack = listOf(
            "Open Sans Regular",
            "Arial Unicode MS Regular"
          )
          textColor = "rgba(0, 0, 0, 1)"
        }
      }
    }
    setupLayer(layer)
    assertEquals(
      "[[cyan, {text-color=rgba(0, 255, 255, 1), font-scale=0.9, text-font=[Open Sans Regular, Arial Unicode MS Regular]}], [black, {text-color=rgba(0, 0, 0, 1), font-scale=2.0, text-font=[Open Sans Regular, Arial Unicode MS Regular]}]]",
      layer.textField?.toValue().toString()
    )
  }

  @Test
  @UiThreadTest
  fun textFontTest() {
    val testValue = listOf("a", "b", "c")
    val layer = symbolLayer("id", "source") {
      textFont(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textFont?.toString())
  }

  @Test
  @UiThreadTest
  fun textFontAsExpressionTest() {
    val expression = array {
      literal("string")
      get {
        literal("array")
      }
    }
    val layer = symbolLayer("id", "source") {
      textFont(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textFontAsExpression.toString())
    assertEquals(null, layer.textFont)
  }

  @Test
  @UiThreadTest
  fun textIgnorePlacementTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      textIgnorePlacement(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textIgnorePlacement?.toString())
  }

  @Test
  @UiThreadTest
  fun textIgnorePlacementAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      textIgnorePlacement(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textIgnorePlacementAsExpression.toString())
    assertEquals(true, layer.textIgnorePlacement!!)
  }

  @Test
  @UiThreadTest
  fun textJustifyTest() {
    val layer = symbolLayer("id", "source") {
      textJustify(TextJustify.AUTO)
    }
    setupLayer(layer)
    assertEquals(TextJustify.AUTO, layer.textJustify)
  }

  @Test
  @UiThreadTest
  fun textJustifyAsExpressionTest() {
    val expression = toString {
      get {
        literal("enum")
      }
    }
    val layer = symbolLayer("id", "source") {
      textJustify(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textJustifyAsExpression.toString())
    assertEquals(null, layer.textJustify)
  }

  @Test
  @UiThreadTest
  fun textKeepUprightTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      textKeepUpright(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textKeepUpright?.toString())
  }

  @Test
  @UiThreadTest
  fun textKeepUprightAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      textKeepUpright(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textKeepUprightAsExpression.toString())
    assertEquals(true, layer.textKeepUpright!!)
  }

  @Test
  @UiThreadTest
  fun textLetterSpacingTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textLetterSpacing(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textLetterSpacing!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textLetterSpacingAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textLetterSpacing(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textLetterSpacingAsExpression.toString())
    assertEquals(null, layer.textLetterSpacing)
  }

  @Test
  @UiThreadTest
  fun textLineHeightTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textLineHeight(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textLineHeight!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textLineHeightAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textLineHeight(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textLineHeightAsExpression.toString())
    assertEquals(null, layer.textLineHeight)
  }

  @Test
  @UiThreadTest
  fun textMaxAngleTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textMaxAngle(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textMaxAngle!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textMaxAngleAsExpressionTest() {
    val expression = literal(1.0)
    val layer = symbolLayer("id", "source") {
      textMaxAngle(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.textMaxAngleAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textMaxAngle!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textMaxWidthTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textMaxWidth(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textMaxWidth!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textMaxWidthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textMaxWidth(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textMaxWidthAsExpression.toString())
    assertEquals(null, layer.textMaxWidth)
  }

  @Test
  @UiThreadTest
  fun textOffsetTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = symbolLayer("id", "source") {
      textOffset(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textOffset?.toString())
  }

  @Test
  @UiThreadTest
  fun textOffsetAsExpressionTest() {
    val expression = array {
      literal("number")
      literal(2)
      get {
        literal("array")
      }
    }
    val layer = symbolLayer("id", "source") {
      textOffset(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textOffsetAsExpression.toString())
    assertEquals(null, layer.textOffset)
  }

  @Test
  @UiThreadTest
  fun textOptionalTest() {
    val testValue = true
    val layer = symbolLayer("id", "source") {
      textOptional(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textOptional?.toString())
  }

  @Test
  @UiThreadTest
  fun textOptionalAsExpressionTest() {
    val expression = literal(true)
    val layer = symbolLayer("id", "source") {
      textOptional(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textOptionalAsExpression.toString())
    assertEquals(true, layer.textOptional!!)
  }

  @Test
  @UiThreadTest
  fun textPaddingTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textPadding(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textPadding!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textPaddingAsExpressionTest() {
    val expression = literal(1.0)
    val layer = symbolLayer("id", "source") {
      textPadding(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.textPaddingAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.textPadding!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textPitchAlignmentTest() {
    val layer = symbolLayer("id", "source") {
      textPitchAlignment(TextPitchAlignment.MAP)
    }
    setupLayer(layer)
    assertEquals(TextPitchAlignment.MAP, layer.textPitchAlignment)
  }

  @Test
  @UiThreadTest
  fun textPitchAlignmentAsExpressionTest() {
    val expression = literal("map")
    val layer = symbolLayer("id", "source") {
      textPitchAlignment(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textPitchAlignmentAsExpression.toString())
    assertEquals(TextPitchAlignment.MAP, layer.textPitchAlignment!!)
  }

  @Test
  @UiThreadTest
  fun textRadialOffsetTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textRadialOffset(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textRadialOffset!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textRadialOffsetAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textRadialOffset(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textRadialOffsetAsExpression.toString())
    assertEquals(null, layer.textRadialOffset)
  }

  @Test
  @UiThreadTest
  fun textRotateTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textRotate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textRotate!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textRotateAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textRotate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textRotateAsExpression.toString())
    assertEquals(null, layer.textRotate)
  }

  @Test
  @UiThreadTest
  fun textRotationAlignmentTest() {
    val layer = symbolLayer("id", "source") {
      textRotationAlignment(TextRotationAlignment.MAP)
    }
    setupLayer(layer)
    assertEquals(TextRotationAlignment.MAP, layer.textRotationAlignment)
  }

  @Test
  @UiThreadTest
  fun textRotationAlignmentAsExpressionTest() {
    val expression = literal("map")
    val layer = symbolLayer("id", "source") {
      textRotationAlignment(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textRotationAlignmentAsExpression.toString())
    assertEquals(TextRotationAlignment.MAP, layer.textRotationAlignment!!)
  }

  @Test
  @UiThreadTest
  fun textSizeTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textSize(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textSize!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textSizeAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textSizeAsExpression.toString())
    assertEquals(null, layer.textSize)
  }

  @Test
  @UiThreadTest
  fun textTransformTest() {
    val layer = symbolLayer("id", "source") {
      textTransform(TextTransform.NONE)
    }
    setupLayer(layer)
    assertEquals(TextTransform.NONE, layer.textTransform)
  }

  @Test
  @UiThreadTest
  fun textTransformAsExpressionTest() {
    val expression = toString {
      get {
        literal("enum")
      }
    }
    val layer = symbolLayer("id", "source") {
      textTransform(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textTransformAsExpression.toString())
    assertEquals(null, layer.textTransform)
  }

  @Test
  @UiThreadTest
  fun textVariableAnchorTest() {
    val testValue = listOf("center", "left")
    val layer = symbolLayer("id", "source") {
      textVariableAnchor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textVariableAnchor?.toString())
  }

  @Test
  @UiThreadTest
  fun textVariableAnchorAsExpressionTest() {
    val expression = literal(listOf("center", "left"))
    val layer = symbolLayer("id", "source") {
      textVariableAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textVariableAnchorAsExpression.toString())
    assertEquals(listOf("center", "left"), layer.textVariableAnchor!!)
  }

  @Test
  @UiThreadTest
  fun textWritingModeTest() {
    val testValue = listOf("horizontal", "vertical")
    val layer = symbolLayer("id", "source") {
      textWritingMode(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textWritingMode?.toString())
  }

  @Test
  @UiThreadTest
  fun textWritingModeAsExpressionTest() {
    val expression = literal(listOf("horizontal", "vertical"))
    val layer = symbolLayer("id", "source") {
      textWritingMode(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textWritingModeAsExpression.toString())
    assertEquals(listOf("horizontal", "vertical"), layer.textWritingMode!!)
  }

  @Test
  @UiThreadTest
  fun iconColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = symbolLayer("id", "source") {
      iconColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconColor?.toString())
  }

  @Test
  @UiThreadTest
  fun iconColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconColorAsExpression.toString())
    assertEquals(null, layer.iconColor)
  }

  @Test
  @UiThreadTest
  fun iconColorAsColorIntTest() {
    val layer = symbolLayer("id", "source") {
      iconColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.iconColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun iconColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconColorTransition)
  }

  @Test
  @UiThreadTest
  fun iconColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconColorTransition)
  }

  @Test
  @UiThreadTest
  fun iconHaloBlurTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      iconHaloBlur(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.iconHaloBlur!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun iconHaloBlurAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconHaloBlur(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconHaloBlurAsExpression.toString())
    assertEquals(null, layer.iconHaloBlur)
  }

  @Test
  @UiThreadTest
  fun iconHaloBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconHaloBlurTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconHaloBlurTransition)
  }

  @Test
  @UiThreadTest
  fun iconHaloBlurTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconHaloBlurTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconHaloBlurTransition)
  }

  @Test
  @UiThreadTest
  fun iconHaloColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = symbolLayer("id", "source") {
      iconHaloColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconHaloColor?.toString())
  }

  @Test
  @UiThreadTest
  fun iconHaloColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconHaloColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconHaloColorAsExpression.toString())
    assertEquals(null, layer.iconHaloColor)
  }

  @Test
  @UiThreadTest
  fun iconHaloColorAsColorIntTest() {
    val layer = symbolLayer("id", "source") {
      iconHaloColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.iconHaloColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun iconHaloColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconHaloColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconHaloColorTransition)
  }

  @Test
  @UiThreadTest
  fun iconHaloColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconHaloColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconHaloColorTransition)
  }

  @Test
  @UiThreadTest
  fun iconHaloWidthTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      iconHaloWidth(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.iconHaloWidth!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun iconHaloWidthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconHaloWidth(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconHaloWidthAsExpression.toString())
    assertEquals(null, layer.iconHaloWidth)
  }

  @Test
  @UiThreadTest
  fun iconHaloWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconHaloWidthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconHaloWidthTransition)
  }

  @Test
  @UiThreadTest
  fun iconHaloWidthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconHaloWidthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconHaloWidthTransition)
  }

  @Test
  @UiThreadTest
  fun iconOpacityTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      iconOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.iconOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun iconOpacityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconOpacityAsExpression.toString())
    assertEquals(null, layer.iconOpacity)
  }

  @Test
  @UiThreadTest
  fun iconOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun iconOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun iconTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = symbolLayer("id", "source") {
      iconTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.iconTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun iconTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = symbolLayer("id", "source") {
      iconTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.iconTranslate!!)
  }

  @Test
  @UiThreadTest
  fun iconTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun iconTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      iconTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.iconTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun iconTranslateAnchorTest() {
    val layer = symbolLayer("id", "source") {
      iconTranslateAnchor(IconTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(IconTranslateAnchor.MAP, layer.iconTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun iconTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = symbolLayer("id", "source") {
      iconTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.iconTranslateAnchorAsExpression.toString())
    assertEquals(IconTranslateAnchor.MAP, layer.iconTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun textColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = symbolLayer("id", "source") {
      textColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textColor?.toString())
  }

  @Test
  @UiThreadTest
  fun textColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = symbolLayer("id", "source") {
      textColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textColorAsExpression.toString())
    assertEquals(null, layer.textColor)
  }

  @Test
  @UiThreadTest
  fun textColorAsColorIntTest() {
    val layer = symbolLayer("id", "source") {
      textColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.textColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun textColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.textColorTransition)
  }

  @Test
  @UiThreadTest
  fun textColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.textColorTransition)
  }

  @Test
  @UiThreadTest
  fun textHaloBlurTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textHaloBlur(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textHaloBlur!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textHaloBlurAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textHaloBlur(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textHaloBlurAsExpression.toString())
    assertEquals(null, layer.textHaloBlur)
  }

  @Test
  @UiThreadTest
  fun textHaloBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textHaloBlurTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.textHaloBlurTransition)
  }

  @Test
  @UiThreadTest
  fun textHaloBlurTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textHaloBlurTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.textHaloBlurTransition)
  }

  @Test
  @UiThreadTest
  fun textHaloColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = symbolLayer("id", "source") {
      textHaloColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textHaloColor?.toString())
  }

  @Test
  @UiThreadTest
  fun textHaloColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = symbolLayer("id", "source") {
      textHaloColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textHaloColorAsExpression.toString())
    assertEquals(null, layer.textHaloColor)
  }

  @Test
  @UiThreadTest
  fun textHaloColorAsColorIntTest() {
    val layer = symbolLayer("id", "source") {
      textHaloColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.textHaloColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun textHaloColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textHaloColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.textHaloColorTransition)
  }

  @Test
  @UiThreadTest
  fun textHaloColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textHaloColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.textHaloColorTransition)
  }

  @Test
  @UiThreadTest
  fun textHaloWidthTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textHaloWidth(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textHaloWidth!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textHaloWidthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textHaloWidth(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textHaloWidthAsExpression.toString())
    assertEquals(null, layer.textHaloWidth)
  }

  @Test
  @UiThreadTest
  fun textHaloWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textHaloWidthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.textHaloWidthTransition)
  }

  @Test
  @UiThreadTest
  fun textHaloWidthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textHaloWidthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.textHaloWidthTransition)
  }

  @Test
  @UiThreadTest
  fun textOpacityTest() {
    val testValue = 1.0
    val layer = symbolLayer("id", "source") {
      textOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.textOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun textOpacityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = symbolLayer("id", "source") {
      textOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textOpacityAsExpression.toString())
    assertEquals(null, layer.textOpacity)
  }

  @Test
  @UiThreadTest
  fun textOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.textOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun textOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.textOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun textTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = symbolLayer("id", "source") {
      textTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.textTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun textTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = symbolLayer("id", "source") {
      textTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.textTranslate!!)
  }

  @Test
  @UiThreadTest
  fun textTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.textTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun textTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = symbolLayer("id", "source") {
      textTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.textTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun textTranslateAnchorTest() {
    val layer = symbolLayer("id", "source") {
      textTranslateAnchor(TextTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(TextTranslateAnchor.MAP, layer.textTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun textTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = symbolLayer("id", "source") {
      textTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.textTranslateAnchorAsExpression.toString())
    assertEquals(TextTranslateAnchor.MAP, layer.textTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = symbolLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", SymbolLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", SymbolLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", SymbolLayer.defaultMaxZoom)
    assertNotNull("defaultIconAllowOverlap should not be null", SymbolLayer.defaultIconAllowOverlap)
    assertNotNull("defaultIconAllowOverlapAsExpression should not be null", SymbolLayer.defaultIconAllowOverlapAsExpression)
    assertNotNull("defaultIconAnchor should not be null", SymbolLayer.defaultIconAnchor)
    assertNotNull("defaultIconAnchorAsExpression should not be null", SymbolLayer.defaultIconAnchorAsExpression)
    assertNotNull("defaultIconIgnorePlacement should not be null", SymbolLayer.defaultIconIgnorePlacement)
    assertNotNull("defaultIconIgnorePlacementAsExpression should not be null", SymbolLayer.defaultIconIgnorePlacementAsExpression)
    assertNotNull("defaultIconImage should not be null", SymbolLayer.defaultIconImage)
    assertNotNull("defaultIconImageAsExpression should not be null", SymbolLayer.defaultIconImageAsExpression)
    assertNotNull("defaultIconKeepUpright should not be null", SymbolLayer.defaultIconKeepUpright)
    assertNotNull("defaultIconKeepUprightAsExpression should not be null", SymbolLayer.defaultIconKeepUprightAsExpression)
    assertNotNull("defaultIconOffset should not be null", SymbolLayer.defaultIconOffset)
    assertNotNull("defaultIconOffsetAsExpression should not be null", SymbolLayer.defaultIconOffsetAsExpression)
    assertNotNull("defaultIconOptional should not be null", SymbolLayer.defaultIconOptional)
    assertNotNull("defaultIconOptionalAsExpression should not be null", SymbolLayer.defaultIconOptionalAsExpression)
    assertNotNull("defaultIconPadding should not be null", SymbolLayer.defaultIconPadding)
    assertNotNull("defaultIconPaddingAsExpression should not be null", SymbolLayer.defaultIconPaddingAsExpression)
    assertNotNull("defaultIconPitchAlignment should not be null", SymbolLayer.defaultIconPitchAlignment)
    assertNotNull("defaultIconPitchAlignmentAsExpression should not be null", SymbolLayer.defaultIconPitchAlignmentAsExpression)
    assertNotNull("defaultIconRotate should not be null", SymbolLayer.defaultIconRotate)
    assertNotNull("defaultIconRotateAsExpression should not be null", SymbolLayer.defaultIconRotateAsExpression)
    assertNotNull("defaultIconRotationAlignment should not be null", SymbolLayer.defaultIconRotationAlignment)
    assertNotNull("defaultIconRotationAlignmentAsExpression should not be null", SymbolLayer.defaultIconRotationAlignmentAsExpression)
    assertNotNull("defaultIconSize should not be null", SymbolLayer.defaultIconSize)
    assertNotNull("defaultIconSizeAsExpression should not be null", SymbolLayer.defaultIconSizeAsExpression)
    assertNotNull("defaultIconTextFit should not be null", SymbolLayer.defaultIconTextFit)
    assertNotNull("defaultIconTextFitAsExpression should not be null", SymbolLayer.defaultIconTextFitAsExpression)
    assertNotNull("defaultIconTextFitPadding should not be null", SymbolLayer.defaultIconTextFitPadding)
    assertNotNull("defaultIconTextFitPaddingAsExpression should not be null", SymbolLayer.defaultIconTextFitPaddingAsExpression)
    assertNotNull("defaultSymbolAvoidEdges should not be null", SymbolLayer.defaultSymbolAvoidEdges)
    assertNotNull("defaultSymbolAvoidEdgesAsExpression should not be null", SymbolLayer.defaultSymbolAvoidEdgesAsExpression)
    assertNotNull("defaultSymbolPlacement should not be null", SymbolLayer.defaultSymbolPlacement)
    assertNotNull("defaultSymbolPlacementAsExpression should not be null", SymbolLayer.defaultSymbolPlacementAsExpression)
    assertNotNull("defaultSymbolSortKey should not be null", SymbolLayer.defaultSymbolSortKey)
    assertNotNull("defaultSymbolSortKeyAsExpression should not be null", SymbolLayer.defaultSymbolSortKeyAsExpression)
    assertNotNull("defaultSymbolSpacing should not be null", SymbolLayer.defaultSymbolSpacing)
    assertNotNull("defaultSymbolSpacingAsExpression should not be null", SymbolLayer.defaultSymbolSpacingAsExpression)
    assertNotNull("defaultSymbolZOrder should not be null", SymbolLayer.defaultSymbolZOrder)
    assertNotNull("defaultSymbolZOrderAsExpression should not be null", SymbolLayer.defaultSymbolZOrderAsExpression)
    assertNotNull("defaultTextAllowOverlap should not be null", SymbolLayer.defaultTextAllowOverlap)
    assertNotNull("defaultTextAllowOverlapAsExpression should not be null", SymbolLayer.defaultTextAllowOverlapAsExpression)
    assertNotNull("defaultTextAnchor should not be null", SymbolLayer.defaultTextAnchor)
    assertNotNull("defaultTextAnchorAsExpression should not be null", SymbolLayer.defaultTextAnchorAsExpression)
    assertNotNull("defaultTextField should not be null", SymbolLayer.defaultTextField)
    assertNotNull("defaultTextFieldAsExpression should not be null", SymbolLayer.defaultTextFieldAsExpression)
    assertNotNull("defaultTextFieldAsString should not be null", SymbolLayer.defaultTextFieldAsString)
    assertNotNull("defaultTextFont should not be null", SymbolLayer.defaultTextFont)
    assertNotNull("defaultTextFontAsExpression should not be null", SymbolLayer.defaultTextFontAsExpression)
    assertNotNull("defaultTextIgnorePlacement should not be null", SymbolLayer.defaultTextIgnorePlacement)
    assertNotNull("defaultTextIgnorePlacementAsExpression should not be null", SymbolLayer.defaultTextIgnorePlacementAsExpression)
    assertNotNull("defaultTextJustify should not be null", SymbolLayer.defaultTextJustify)
    assertNotNull("defaultTextJustifyAsExpression should not be null", SymbolLayer.defaultTextJustifyAsExpression)
    assertNotNull("defaultTextKeepUpright should not be null", SymbolLayer.defaultTextKeepUpright)
    assertNotNull("defaultTextKeepUprightAsExpression should not be null", SymbolLayer.defaultTextKeepUprightAsExpression)
    assertNotNull("defaultTextLetterSpacing should not be null", SymbolLayer.defaultTextLetterSpacing)
    assertNotNull("defaultTextLetterSpacingAsExpression should not be null", SymbolLayer.defaultTextLetterSpacingAsExpression)
    assertNotNull("defaultTextLineHeight should not be null", SymbolLayer.defaultTextLineHeight)
    assertNotNull("defaultTextLineHeightAsExpression should not be null", SymbolLayer.defaultTextLineHeightAsExpression)
    assertNotNull("defaultTextMaxAngle should not be null", SymbolLayer.defaultTextMaxAngle)
    assertNotNull("defaultTextMaxAngleAsExpression should not be null", SymbolLayer.defaultTextMaxAngleAsExpression)
    assertNotNull("defaultTextMaxWidth should not be null", SymbolLayer.defaultTextMaxWidth)
    assertNotNull("defaultTextMaxWidthAsExpression should not be null", SymbolLayer.defaultTextMaxWidthAsExpression)
    assertNotNull("defaultTextOffset should not be null", SymbolLayer.defaultTextOffset)
    assertNotNull("defaultTextOffsetAsExpression should not be null", SymbolLayer.defaultTextOffsetAsExpression)
    assertNotNull("defaultTextOptional should not be null", SymbolLayer.defaultTextOptional)
    assertNotNull("defaultTextOptionalAsExpression should not be null", SymbolLayer.defaultTextOptionalAsExpression)
    assertNotNull("defaultTextPadding should not be null", SymbolLayer.defaultTextPadding)
    assertNotNull("defaultTextPaddingAsExpression should not be null", SymbolLayer.defaultTextPaddingAsExpression)
    assertNotNull("defaultTextPitchAlignment should not be null", SymbolLayer.defaultTextPitchAlignment)
    assertNotNull("defaultTextPitchAlignmentAsExpression should not be null", SymbolLayer.defaultTextPitchAlignmentAsExpression)
    assertNotNull("defaultTextRadialOffset should not be null", SymbolLayer.defaultTextRadialOffset)
    assertNotNull("defaultTextRadialOffsetAsExpression should not be null", SymbolLayer.defaultTextRadialOffsetAsExpression)
    assertNotNull("defaultTextRotate should not be null", SymbolLayer.defaultTextRotate)
    assertNotNull("defaultTextRotateAsExpression should not be null", SymbolLayer.defaultTextRotateAsExpression)
    assertNotNull("defaultTextRotationAlignment should not be null", SymbolLayer.defaultTextRotationAlignment)
    assertNotNull("defaultTextRotationAlignmentAsExpression should not be null", SymbolLayer.defaultTextRotationAlignmentAsExpression)
    assertNotNull("defaultTextSize should not be null", SymbolLayer.defaultTextSize)
    assertNotNull("defaultTextSizeAsExpression should not be null", SymbolLayer.defaultTextSizeAsExpression)
    assertNotNull("defaultTextTransform should not be null", SymbolLayer.defaultTextTransform)
    assertNotNull("defaultTextTransformAsExpression should not be null", SymbolLayer.defaultTextTransformAsExpression)
    assertNotNull("defaultTextVariableAnchor should not be null", SymbolLayer.defaultTextVariableAnchor)
    assertNotNull("defaultTextVariableAnchorAsExpression should not be null", SymbolLayer.defaultTextVariableAnchorAsExpression)
    assertNotNull("defaultTextWritingMode should not be null", SymbolLayer.defaultTextWritingMode)
    assertNotNull("defaultTextWritingModeAsExpression should not be null", SymbolLayer.defaultTextWritingModeAsExpression)
    assertNotNull("defaultIconColor should not be null", SymbolLayer.defaultIconColor)
    assertNotNull("defaultIconColorAsExpression should not be null", SymbolLayer.defaultIconColorAsExpression)
    assertNotNull("defaultIconColorAsColorInt should not be null", SymbolLayer.defaultIconColorAsColorInt)
    assertNotNull("defaultIconColorTransition should not be null", SymbolLayer.defaultIconColorTransition)
    assertNotNull("defaultIconHaloBlur should not be null", SymbolLayer.defaultIconHaloBlur)
    assertNotNull("defaultIconHaloBlurAsExpression should not be null", SymbolLayer.defaultIconHaloBlurAsExpression)
    assertNotNull("defaultIconHaloBlurTransition should not be null", SymbolLayer.defaultIconHaloBlurTransition)
    assertNotNull("defaultIconHaloColor should not be null", SymbolLayer.defaultIconHaloColor)
    assertNotNull("defaultIconHaloColorAsExpression should not be null", SymbolLayer.defaultIconHaloColorAsExpression)
    assertNotNull("defaultIconHaloColorAsColorInt should not be null", SymbolLayer.defaultIconHaloColorAsColorInt)
    assertNotNull("defaultIconHaloColorTransition should not be null", SymbolLayer.defaultIconHaloColorTransition)
    assertNotNull("defaultIconHaloWidth should not be null", SymbolLayer.defaultIconHaloWidth)
    assertNotNull("defaultIconHaloWidthAsExpression should not be null", SymbolLayer.defaultIconHaloWidthAsExpression)
    assertNotNull("defaultIconHaloWidthTransition should not be null", SymbolLayer.defaultIconHaloWidthTransition)
    assertNotNull("defaultIconOpacity should not be null", SymbolLayer.defaultIconOpacity)
    assertNotNull("defaultIconOpacityAsExpression should not be null", SymbolLayer.defaultIconOpacityAsExpression)
    assertNotNull("defaultIconOpacityTransition should not be null", SymbolLayer.defaultIconOpacityTransition)
    assertNotNull("defaultIconTranslate should not be null", SymbolLayer.defaultIconTranslate)
    assertNotNull("defaultIconTranslateAsExpression should not be null", SymbolLayer.defaultIconTranslateAsExpression)
    assertNotNull("defaultIconTranslateTransition should not be null", SymbolLayer.defaultIconTranslateTransition)
    assertNotNull("defaultIconTranslateAnchor should not be null", SymbolLayer.defaultIconTranslateAnchor)
    assertNotNull("defaultIconTranslateAnchorAsExpression should not be null", SymbolLayer.defaultIconTranslateAnchorAsExpression)
    assertNotNull("defaultTextColor should not be null", SymbolLayer.defaultTextColor)
    assertNotNull("defaultTextColorAsExpression should not be null", SymbolLayer.defaultTextColorAsExpression)
    assertNotNull("defaultTextColorAsColorInt should not be null", SymbolLayer.defaultTextColorAsColorInt)
    assertNotNull("defaultTextColorTransition should not be null", SymbolLayer.defaultTextColorTransition)
    assertNotNull("defaultTextHaloBlur should not be null", SymbolLayer.defaultTextHaloBlur)
    assertNotNull("defaultTextHaloBlurAsExpression should not be null", SymbolLayer.defaultTextHaloBlurAsExpression)
    assertNotNull("defaultTextHaloBlurTransition should not be null", SymbolLayer.defaultTextHaloBlurTransition)
    assertNotNull("defaultTextHaloColor should not be null", SymbolLayer.defaultTextHaloColor)
    assertNotNull("defaultTextHaloColorAsExpression should not be null", SymbolLayer.defaultTextHaloColorAsExpression)
    assertNotNull("defaultTextHaloColorAsColorInt should not be null", SymbolLayer.defaultTextHaloColorAsColorInt)
    assertNotNull("defaultTextHaloColorTransition should not be null", SymbolLayer.defaultTextHaloColorTransition)
    assertNotNull("defaultTextHaloWidth should not be null", SymbolLayer.defaultTextHaloWidth)
    assertNotNull("defaultTextHaloWidthAsExpression should not be null", SymbolLayer.defaultTextHaloWidthAsExpression)
    assertNotNull("defaultTextHaloWidthTransition should not be null", SymbolLayer.defaultTextHaloWidthTransition)
    assertNotNull("defaultTextOpacity should not be null", SymbolLayer.defaultTextOpacity)
    assertNotNull("defaultTextOpacityAsExpression should not be null", SymbolLayer.defaultTextOpacityAsExpression)
    assertNotNull("defaultTextOpacityTransition should not be null", SymbolLayer.defaultTextOpacityTransition)
    assertNotNull("defaultTextTranslate should not be null", SymbolLayer.defaultTextTranslate)
    assertNotNull("defaultTextTranslateAsExpression should not be null", SymbolLayer.defaultTextTranslateAsExpression)
    assertNotNull("defaultTextTranslateTransition should not be null", SymbolLayer.defaultTextTranslateTransition)
    assertNotNull("defaultTextTranslateAnchor should not be null", SymbolLayer.defaultTextTranslateAnchor)
    assertNotNull("defaultTextTranslateAnchorAsExpression should not be null", SymbolLayer.defaultTextTranslateAnchorAsExpression)
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
    val iconAllowOverlapTestValue = true
    val iconAnchorTestValue = IconAnchor.CENTER
    val iconIgnorePlacementTestValue = true
    val iconImageTestValue = "abc"
    val iconKeepUprightTestValue = true
    val iconOffsetTestValue = listOf(0.0, 1.0)
    val iconOptionalTestValue = true
    val iconPaddingTestValue = 1.0
    val iconPitchAlignmentTestValue = IconPitchAlignment.MAP
    val iconRotateTestValue = 1.0
    val iconRotationAlignmentTestValue = IconRotationAlignment.MAP
    val iconSizeTestValue = 1.0
    val iconTextFitTestValue = IconTextFit.NONE
    val iconTextFitPaddingTestValue = listOf(0.0, 1.0, 2.0, 3.0)
    val symbolAvoidEdgesTestValue = true
    val symbolPlacementTestValue = SymbolPlacement.POINT
    val symbolSortKeyTestValue = 1.0
    val symbolSpacingTestValue = 1.0
    val symbolZOrderTestValue = SymbolZOrder.AUTO
    val textAllowOverlapTestValue = true
    val textAnchorTestValue = TextAnchor.CENTER
    val textFieldTestValue = formatted {
      formattedSection("cyan") {
        fontScale = 0.9
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColorAsInt = Color.CYAN
      }
      formattedSection("black") {
        fontScale = 2.0
        fontStack = listOf(
          "Open Sans Regular",
          "Arial Unicode MS Regular"
        )
        textColor = "rgba(0, 0, 0, 1)"
      }
    }
    val textFontTestValue = listOf("a", "b", "c")
    val textIgnorePlacementTestValue = true
    val textJustifyTestValue = TextJustify.AUTO
    val textKeepUprightTestValue = true
    val textLetterSpacingTestValue = 1.0
    val textLineHeightTestValue = 1.0
    val textMaxAngleTestValue = 1.0
    val textMaxWidthTestValue = 1.0
    val textOffsetTestValue = listOf(0.0, 1.0)
    val textOptionalTestValue = true
    val textPaddingTestValue = 1.0
    val textPitchAlignmentTestValue = TextPitchAlignment.MAP
    val textRadialOffsetTestValue = 1.0
    val textRotateTestValue = 1.0
    val textRotationAlignmentTestValue = TextRotationAlignment.MAP
    val textSizeTestValue = 1.0
    val textTransformTestValue = TextTransform.NONE
    val textVariableAnchorTestValue = listOf("center", "left")
    val textWritingModeTestValue = listOf("horizontal", "vertical")
    val iconColorTestValue = "rgba(0, 0, 0, 1)"
    val iconHaloBlurTestValue = 1.0
    val iconHaloColorTestValue = "rgba(0, 0, 0, 1)"
    val iconHaloWidthTestValue = 1.0
    val iconOpacityTestValue = 1.0
    val iconTranslateTestValue = listOf(0.0, 1.0)
    val iconTranslateAnchorTestValue = IconTranslateAnchor.MAP
    val textColorTestValue = "rgba(0, 0, 0, 1)"
    val textHaloBlurTestValue = 1.0
    val textHaloColorTestValue = "rgba(0, 0, 0, 1)"
    val textHaloWidthTestValue = 1.0
    val textOpacityTestValue = 1.0
    val textTranslateTestValue = listOf(0.0, 1.0)
    val textTranslateAnchorTestValue = TextTranslateAnchor.MAP

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = symbolLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      iconAllowOverlap(iconAllowOverlapTestValue)
      iconAnchor(iconAnchorTestValue)
      iconIgnorePlacement(iconIgnorePlacementTestValue)
      iconImage(iconImageTestValue)
      iconKeepUpright(iconKeepUprightTestValue)
      iconOffset(iconOffsetTestValue)
      iconOptional(iconOptionalTestValue)
      iconPadding(iconPaddingTestValue)
      iconPitchAlignment(iconPitchAlignmentTestValue)
      iconRotate(iconRotateTestValue)
      iconRotationAlignment(iconRotationAlignmentTestValue)
      iconSize(iconSizeTestValue)
      iconTextFit(iconTextFitTestValue)
      iconTextFitPadding(iconTextFitPaddingTestValue)
      symbolAvoidEdges(symbolAvoidEdgesTestValue)
      symbolPlacement(symbolPlacementTestValue)
      symbolSortKey(symbolSortKeyTestValue)
      symbolSpacing(symbolSpacingTestValue)
      symbolZOrder(symbolZOrderTestValue)
      textAllowOverlap(textAllowOverlapTestValue)
      textAnchor(textAnchorTestValue)
      textField(textFieldTestValue)
      textFont(textFontTestValue)
      textIgnorePlacement(textIgnorePlacementTestValue)
      textJustify(textJustifyTestValue)
      textKeepUpright(textKeepUprightTestValue)
      textLetterSpacing(textLetterSpacingTestValue)
      textLineHeight(textLineHeightTestValue)
      textMaxAngle(textMaxAngleTestValue)
      textMaxWidth(textMaxWidthTestValue)
      textOffset(textOffsetTestValue)
      textOptional(textOptionalTestValue)
      textPadding(textPaddingTestValue)
      textPitchAlignment(textPitchAlignmentTestValue)
      textRadialOffset(textRadialOffsetTestValue)
      textRotate(textRotateTestValue)
      textRotationAlignment(textRotationAlignmentTestValue)
      textSize(textSizeTestValue)
      textTransform(textTransformTestValue)
      textVariableAnchor(textVariableAnchorTestValue)
      textWritingMode(textWritingModeTestValue)
      iconColor(iconColorTestValue)
      iconHaloBlur(iconHaloBlurTestValue)
      iconHaloColor(iconHaloColorTestValue)
      iconHaloWidth(iconHaloWidthTestValue)
      iconOpacity(iconOpacityTestValue)
      iconTranslate(iconTranslateTestValue)
      iconTranslateAnchor(iconTranslateAnchorTestValue)
      textColor(textColorTestValue)
      textHaloBlur(textHaloBlurTestValue)
      textHaloColor(textHaloColorTestValue)
      textHaloWidth(textHaloWidthTestValue)
      textOpacity(textOpacityTestValue)
      textTranslate(textTranslateTestValue)
      textTranslateAnchor(textTranslateAnchorTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as SymbolLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(iconAllowOverlapTestValue, cachedLayer.iconAllowOverlap)
    assertEquals(iconAnchorTestValue, cachedLayer.iconAnchor)
    assertEquals(iconIgnorePlacementTestValue, cachedLayer.iconIgnorePlacement)
    assertEquals(iconImageTestValue, cachedLayer.iconImage)
    assertEquals(iconKeepUprightTestValue, cachedLayer.iconKeepUpright)
    assertEquals(iconOffsetTestValue, cachedLayer.iconOffset)
    assertEquals(iconOptionalTestValue, cachedLayer.iconOptional)
    assertEquals(iconPaddingTestValue, cachedLayer.iconPadding)
    assertEquals(iconPitchAlignmentTestValue, cachedLayer.iconPitchAlignment)
    assertEquals(iconRotateTestValue, cachedLayer.iconRotate)
    assertEquals(iconRotationAlignmentTestValue, cachedLayer.iconRotationAlignment)
    assertEquals(iconSizeTestValue, cachedLayer.iconSize)
    assertEquals(iconTextFitTestValue, cachedLayer.iconTextFit)
    assertEquals(iconTextFitPaddingTestValue, cachedLayer.iconTextFitPadding)
    assertEquals(symbolAvoidEdgesTestValue, cachedLayer.symbolAvoidEdges)
    assertEquals(symbolPlacementTestValue, cachedLayer.symbolPlacement)
    assertEquals(symbolSortKeyTestValue, cachedLayer.symbolSortKey)
    assertEquals(symbolSpacingTestValue, cachedLayer.symbolSpacing)
    assertEquals(symbolZOrderTestValue, cachedLayer.symbolZOrder)
    assertEquals(textAllowOverlapTestValue, cachedLayer.textAllowOverlap)
    assertEquals(textAnchorTestValue, cachedLayer.textAnchor)
    assertEquals(textFieldTestValue, cachedLayer.textField)
    assertEquals(textFontTestValue, cachedLayer.textFont)
    assertEquals(textIgnorePlacementTestValue, cachedLayer.textIgnorePlacement)
    assertEquals(textJustifyTestValue, cachedLayer.textJustify)
    assertEquals(textKeepUprightTestValue, cachedLayer.textKeepUpright)
    assertEquals(textLetterSpacingTestValue, cachedLayer.textLetterSpacing)
    assertEquals(textLineHeightTestValue, cachedLayer.textLineHeight)
    assertEquals(textMaxAngleTestValue, cachedLayer.textMaxAngle)
    assertEquals(textMaxWidthTestValue, cachedLayer.textMaxWidth)
    assertEquals(textOffsetTestValue, cachedLayer.textOffset)
    assertEquals(textOptionalTestValue, cachedLayer.textOptional)
    assertEquals(textPaddingTestValue, cachedLayer.textPadding)
    assertEquals(textPitchAlignmentTestValue, cachedLayer.textPitchAlignment)
    assertEquals(textRadialOffsetTestValue, cachedLayer.textRadialOffset)
    assertEquals(textRotateTestValue, cachedLayer.textRotate)
    assertEquals(textRotationAlignmentTestValue, cachedLayer.textRotationAlignment)
    assertEquals(textSizeTestValue, cachedLayer.textSize)
    assertEquals(textTransformTestValue, cachedLayer.textTransform)
    assertEquals(textVariableAnchorTestValue, cachedLayer.textVariableAnchor)
    assertEquals(textWritingModeTestValue, cachedLayer.textWritingMode)
    assertEquals(iconColorTestValue, cachedLayer.iconColor)
    assertEquals(iconHaloBlurTestValue, cachedLayer.iconHaloBlur)
    assertEquals(iconHaloColorTestValue, cachedLayer.iconHaloColor)
    assertEquals(iconHaloWidthTestValue, cachedLayer.iconHaloWidth)
    assertEquals(iconOpacityTestValue, cachedLayer.iconOpacity)
    assertEquals(iconTranslateTestValue, cachedLayer.iconTranslate)
    assertEquals(iconTranslateAnchorTestValue, cachedLayer.iconTranslateAnchor)
    assertEquals(textColorTestValue, cachedLayer.textColor)
    assertEquals(textHaloBlurTestValue, cachedLayer.textHaloBlur)
    assertEquals(textHaloColorTestValue, cachedLayer.textHaloColor)
    assertEquals(textHaloWidthTestValue, cachedLayer.textHaloWidth)
    assertEquals(textOpacityTestValue, cachedLayer.textOpacity)
    assertEquals(textTranslateTestValue, cachedLayer.textTranslate)
    assertEquals(textTranslateAnchorTestValue, cachedLayer.textTranslateAnchor)
  }
}

// End of generated file.