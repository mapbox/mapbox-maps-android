// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.sum
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class GeoJsonSourceTest {
  private val style = mockk<StyleManagerInterface>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()
  private val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)
  private val expectedDelta = mockk<Expected<Byte, String>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
    every { expected.error } returns null
    every { expectedDelta.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleSourcePropertyDefaultValue(any(), any()) } returns styleProperty
  }

  @Test
  fun getTypeTest() {
    val testSource = geoJsonSource("testId") {}
    assertEquals("geojson", testSource.getType())
  }

  @Test
  fun dataSet() {
    val testSource = geoJsonSource("testId") {
      data(TEST_GEOJSON)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("data={\"type\":\"FeatureCollection\",\"features\":[]}"))
  }

  @Test
  fun dataAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      data(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("data=[+, 2, 3]"))
  }

  @Test
  fun dataSetAfterBind() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.data(TEST_GEOJSON)

    verify { style.setStyleSourceProperty("testId", "data", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{\"type\":\"FeatureCollection\",\"features\":[]}")
  }

  @Test
  fun dataAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.data(expression)

    verify { style.setStyleSourceProperty("testId", "data", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun dataGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(TEST_GEOJSON)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(TEST_GEOJSON.toString(), testSource.data?.toString())
    verify { style.getStyleSourceProperty("testId", "data") }
  }

  @Test
  fun dataAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.dataAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "data") }
  }

  @Test
  fun urlSetTest() {
    val testSource = geoJsonSource("testId") {
      url("testUrl")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("data=testUrl"))
  }

  @Test
  fun urlExpressionSetTest() {
    val expression = literal("testUrl")
    val testSource = geoJsonSource("testId") {
      url(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("data=$expression"))
  }

  @Test
  fun urlSetAfterBindTest() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.url("testUrl")

    verify { style.setStyleSourceProperty("testId", "data", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "testUrl")
  }

  @Test
  fun urlExpressionSetAfterBindTest() {
    val expression = literal("testUrl")
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.url(expression)

    verify { style.setStyleSourceProperty("testId", "data", capture(valueSlot)) }
    assertEquals(expression.toString(), valueSlot.captured.toString())
  }

  @Test
  fun maxzoomSet() {
    val testSource = geoJsonSource("testId") {
      maxzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("maxzoom=1"))
  }

  @Test
  fun maxzoomAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      maxzoom(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("maxzoom=[+, 2, 3]"))
  }

  @Test
  fun maxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "maxzoom") }
  }

  @Test
  fun maxzoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.maxzoomAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "maxzoom") }
  }

  @Test
  fun attributionSet() {
    val testSource = geoJsonSource("testId") {
      attribution("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("attribution=abc"))
  }

  @Test
  fun attributionAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      attribution(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("attribution=[+, 2, 3]"))
  }

  @Test
  fun attributionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.attribution?.toString())
    verify { style.getStyleSourceProperty("testId", "attribution") }
  }

  @Test
  fun attributionAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.attributionAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "attribution") }
  }

  @Test
  fun bufferSet() {
    val testSource = geoJsonSource("testId") {
      buffer(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("buffer=1"))
  }

  @Test
  fun bufferAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      buffer(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("buffer=[+, 2, 3]"))
  }

  @Test
  fun bufferGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.buffer?.toString())
    verify { style.getStyleSourceProperty("testId", "buffer") }
  }

  @Test
  fun bufferAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.bufferAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "buffer") }
  }

  @Test
  fun toleranceSet() {
    val testSource = geoJsonSource("testId") {
      tolerance(1.0)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tolerance=1.0"))
  }

  @Test
  fun toleranceAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      tolerance(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tolerance=[+, 2, 3]"))
  }

  @Test
  fun toleranceGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1.0.toString(), testSource.tolerance?.toString())
    verify { style.getStyleSourceProperty("testId", "tolerance") }
  }

  @Test
  fun toleranceAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.toleranceAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "tolerance") }
  }

  @Test
  fun clusterSet() {
    val testSource = geoJsonSource("testId") {
      cluster(true)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("cluster=true"))
  }

  @Test
  fun clusterAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      cluster(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("cluster=[+, 2, 3]"))
  }

  @Test
  fun clusterGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(true.toString(), testSource.cluster?.toString())
    verify { style.getStyleSourceProperty("testId", "cluster") }
  }

  @Test
  fun clusterAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.clusterAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "cluster") }
  }

  @Test
  fun clusterRadiusSet() {
    val testSource = geoJsonSource("testId") {
      clusterRadius(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("clusterRadius=1"))
  }

  @Test
  fun clusterRadiusAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      clusterRadius(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("clusterRadius=[+, 2, 3]"))
  }

  @Test
  fun clusterRadiusGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.clusterRadius?.toString())
    verify { style.getStyleSourceProperty("testId", "clusterRadius") }
  }

  @Test
  fun clusterRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.clusterRadiusAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "clusterRadius") }
  }

  @Test
  fun clusterMaxZoomSet() {
    val testSource = geoJsonSource("testId") {
      clusterMaxZoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("clusterMaxZoom=1"))
  }

  @Test
  fun clusterMaxZoomAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      clusterMaxZoom(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("clusterMaxZoom=[+, 2, 3]"))
  }

  @Test
  fun clusterMaxZoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.clusterMaxZoom?.toString())
    verify { style.getStyleSourceProperty("testId", "clusterMaxZoom") }
  }

  @Test
  fun clusterMaxZoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.clusterMaxZoomAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "clusterMaxZoom") }
  }

  @Test
  fun clusterPropertiesSet() {
    val testSource = geoJsonSource("testId") {
      clusterProperties((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("clusterProperties={key1=x, key2=y}"))
  }

  @Test
  fun clusterPropertiesAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      clusterProperties(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("clusterProperties=[+, 2, 3]"))
  }

  @Test
  fun clusterPropertiesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>))
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>).toString(), testSource.clusterProperties?.toString())
    verify { style.getStyleSourceProperty("testId", "clusterProperties") }
  }

  @Test
  fun clusterPropertiesAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.clusterPropertiesAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "clusterProperties") }
  }
  // Cluster Properties is not mutable so afterBind test is ignored
  @Test
  fun clusterPropertyTest() {
    val testSource = geoJsonSource("testId") {
      clusterProperty("sum", sum { get { literal("scalerank") } })
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(
      valueSlot.captured.toString().contains("clusterProperties={sum=[+, [get, scalerank]]}")
    )
  }

  @Test
  fun clusterPropertyAdvancedTest() {
    val testSource = geoJsonSource("testId") {
      clusterProperty(
        "sum",
        sum {
          accumulated()
          get {
            literal("sum")
          }
        },
        get {
          literal("scalerank")
        }
      )
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(
      valueSlot.captured.toString()
        .contains("clusterProperties={sum=[[+, [accumulated], [get, sum]], [get, scalerank]]}")
    )
  }

  @Test
  fun clusterPropertyTwiceTest() {
    val testSource = geoJsonSource("testId") {
      clusterProperty("sum", sum { get { literal("scalerank") } })
      clusterProperty(
        "sum1",
        sum {
          accumulated()
          get {
            literal("sum")
          }
        },
        get {
          literal("scalerank")
        }
      )
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(
      valueSlot.captured.toString()
        .contains("clusterProperties={sum1=[[+, [accumulated], [get, sum]], [get, scalerank]], sum=[+, [get, scalerank]]}")
    )
  }

  @Test
  fun lineMetricsSet() {
    val testSource = geoJsonSource("testId") {
      lineMetrics(true)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("lineMetrics=true"))
  }

  @Test
  fun lineMetricsAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      lineMetrics(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("lineMetrics=[+, 2, 3]"))
  }

  @Test
  fun lineMetricsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(true.toString(), testSource.lineMetrics?.toString())
    verify { style.getStyleSourceProperty("testId", "lineMetrics") }
  }

  @Test
  fun lineMetricsAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.lineMetricsAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "lineMetrics") }
  }

  @Test
  fun prefetchZoomDeltaSet() {
    val testSource = geoJsonSource("testId") {
      prefetchZoomDelta(1L)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals("1", valueSlot.captured.toString())
  }

  @Test
  fun prefetchZoomDeltaAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {
      prefetchZoomDelta(expression)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals("[+, 2, 3]", valueSlot.captured.toString())
  }

  @Test
  fun prefetchZoomDeltaSetAfterBind() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(1L)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun prefetchZoomDeltaAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(expression)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun prefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.prefetchZoomDelta?.toString())
    verify { style.getStyleSourceProperty("testId", "prefetch-zoom-delta") }
  }

  @Test
  fun prefetchZoomDeltaAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.prefetchZoomDeltaAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "prefetch-zoom-delta") }
  }

  @Test
  fun featureAfterBindTest() {
    val feature = Feature.fromJson(
      """
        {
          "type": "Feature",
          "geometry": {
            "type": "Point",
            "coordinates": [102.0, 0.5]
          },
          "properties": {
                  "prop0": "value0"
                }
        }
      """.trimIndent()
    )
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.feature(feature)
    verify { style.setStyleSourceProperty("testId", "data", any()) }
  }

  @Test
  fun featureCollectionAfterBindTest() {
    val featureCollection = FeatureCollection.fromJson(
      """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "geometry": {
                "type": "Point",
                "coordinates": [102.0, 0.5]
              }
            },
            {
              "type": "Feature",
              "geometry": {
                "type": "LineString",
                "coordinates": [
                  [102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]
                ]
              }
            }
          ]
        }
      """.trimIndent()
    )
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.featureCollection(featureCollection)
    verify { style.setStyleSourceProperty("testId", "data", any()) }
  }

  @Test
  fun geometryAfterBindTest() {
    val feature = Feature.fromJson(
      """
        {
          "type": "Feature",
          "geometry": {
            "type": "Point",
            "coordinates": [102.0, 0.5]
          },
          "properties": {
                  "prop0": "value0"
                }
        }
      """.trimIndent()
    )
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.geometry(feature.geometry()!!)
    verify { style.setStyleSourceProperty("testId", "data", any()) }
  }

  @Test
  fun featureTest() {
    val feature = Feature.fromJson(
      """
        {
          "type": "Feature",
          "geometry": {
            "type": "Point",
            "coordinates": [102.0, 0.5]
          },
          "properties": {
                  "prop0": "value0"
                }
        }
      """.trimIndent()
    )
    val testSource = geoJsonSource("testId") {
      feature(feature)
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("type=geojson"))
  }

  @Test
  fun featureCollectionTest() {
    val featureCollection = FeatureCollection.fromJson(
      """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "geometry": {
                "type": "Point",
                "coordinates": [102.0, 0.5]
              }
            },
            {
              "type": "Feature",
              "geometry": {
                "type": "LineString",
                "coordinates": [
                  [102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]
                ]
              }
            }
          ]
        }
      """.trimIndent()
    )
    val testSource = geoJsonSource("testId") {
      featureCollection(featureCollection)
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("type=geojson"))
  }

  @Test
  fun geometryTest() {
    val feature = Feature.fromJson(
      """
        {
          "type": "Feature",
          "geometry": {
            "type": "Point",
            "coordinates": [102.0, 0.5]
          },
          "properties": {
                  "prop0": "value0"
                }
        }
      """.trimIndent()
    )
    val testSource = geoJsonSource("testId") {
      geometry(feature.geometry()!!)
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("type=geojson"))
  }
  // Default source property getters tests

  @Test
  fun defaultMaxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultMaxzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "maxzoom") }
  }

  @Test
  fun defaultMaxzoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultMaxzoomAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "maxzoom") }
  }

  @Test
  fun defaultBufferGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultBuffer?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "buffer") }
  }

  @Test
  fun defaultBufferAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultBufferAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "buffer") }
  }

  @Test
  fun defaultToleranceGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    assertEquals(1.0.toString(), GeoJsonSource.defaultTolerance?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "tolerance") }
  }

  @Test
  fun defaultToleranceAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultToleranceAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "tolerance") }
  }

  @Test
  fun defaultClusterGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    assertEquals(true.toString(), GeoJsonSource.defaultCluster?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "cluster") }
  }

  @Test
  fun defaultClusterAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultClusterAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "cluster") }
  }

  @Test
  fun defaultClusterRadiusGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultClusterRadius?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterRadius") }
  }

  @Test
  fun defaultClusterRadiusAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultClusterRadiusAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterRadius") }
  }

  @Test
  fun defaultClusterMaxZoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultClusterMaxZoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterMaxZoom") }
  }

  @Test
  fun defaultClusterMaxZoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultClusterMaxZoomAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterMaxZoom") }
  }

  @Test
  fun defaultLineMetricsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    assertEquals(true.toString(), GeoJsonSource.defaultLineMetrics?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "lineMetrics") }
  }

  @Test
  fun defaultLineMetricsAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultLineMetricsAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "lineMetrics") }
  }

  @Test
  fun defaultPrefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultPrefetchZoomDelta?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "prefetch-zoom-delta") }
  }

  @Test
  fun defaultPrefetchZoomDeltaAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), GeoJsonSource.defaultPrefetchZoomDeltaAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "prefetch-zoom-delta") }
  }

  companion object {
    private val TEST_GEOJSON = FeatureCollection.fromFeatures(listOf()).toJson()
  }
}

// End of generated file.