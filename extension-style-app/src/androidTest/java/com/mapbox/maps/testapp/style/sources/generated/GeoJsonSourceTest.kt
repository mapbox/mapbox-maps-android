// This file is generated.

package com.mapbox.maps.testapp.style.sources.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for GeoJsonSource.
 */
@RunWith(AndroidJUnit4::class)
class GeoJsonSourceTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun dataTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      data(TEST_GEOJSON)
    }
    setupSource(testSource)
    assertEquals(null, testSource.data)
  }

  @Test
  @UiThreadTest
  fun dataAfterBindTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.data(TEST_GEOJSON)
    assertEquals(null, testSource.data)
  }

  @Test
  @UiThreadTest
  fun dataAsExpressionTest() {
    val expression = literal(TEST_URI)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      data(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.dataAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun urlTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
    }
    setupSource(testSource)
    assertEquals(TEST_URI, testSource.data)
  }

  @Test
  @UiThreadTest
  fun urlAfterBindTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.url(TEST_URI)
    assertEquals(TEST_URI, testSource.data)
  }

  @Test
  @UiThreadTest
  fun urlAsExpressionTest() {
    val expression = literal(TEST_URI)
    val testSource = geoJsonSource("testId") {
      url(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.dataAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun maxzoomTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      maxzoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.maxzoom)
  }

  @Test
  @UiThreadTest
  fun maxzoomAsExpressionTest() {
    val expression = literal(1L)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      maxzoom(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.maxzoomAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun attributionTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      attribution("abc")
    }
    setupSource(testSource)
    assertEquals("abc", testSource.attribution)
  }

  @Test
  @UiThreadTest
  fun attributionAsExpressionTest() {
    val expression = literal("abc")
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      attribution(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.attributionAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun bufferTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      buffer(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.buffer)
  }

  @Test
  @UiThreadTest
  fun bufferAsExpressionTest() {
    val expression = literal(1L)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      buffer(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.bufferAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun toleranceTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      tolerance(1.0)
    }
    setupSource(testSource)
    assertEquals(1.0, testSource.tolerance)
  }

  @Test
  @UiThreadTest
  fun toleranceAsExpressionTest() {
    val expression = literal(1.0)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      tolerance(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.toleranceAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun clusterTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      cluster(true)
    }
    setupSource(testSource)
    assertEquals(true, testSource.cluster)
  }

  @Test
  @UiThreadTest
  fun clusterAsExpressionTest() {
    val expression = literal(true)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      cluster(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.clusterAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun clusterRadiusTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterRadius(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.clusterRadius)
  }

  @Test
  @UiThreadTest
  fun clusterRadiusAsExpressionTest() {
    val expression = literal(1L)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterRadius(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.clusterRadiusAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun clusterMaxZoomTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterMaxZoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.clusterMaxZoom)
  }

  @Test
  @UiThreadTest
  fun clusterMaxZoomAsExpressionTest() {
    val expression = literal(1L)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterMaxZoom(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.clusterMaxZoomAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun clusterPropertiesTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterProperties((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>))
    }
    setupSource(testSource)
    assertEquals((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>), testSource.clusterProperties)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun clusterPropertiesAsExpressionTest() {
    val expression = literal(hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterProperties(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.clusterPropertiesAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun clusterPropertyTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterProperty(
        "sum",
        sum {
          accumulated()
          get { literal("scalerank") }
        }
      )
    }
    setupSource(testSource)
    assertEquals("", testSource.clusterProperties.toString())
  }

  @Test
  @UiThreadTest
  fun clusterPropertyAdvancedTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterProperty(
        "sum",
        sum {
          accumulated()
          get { literal("sum") }
        },
        get { literal("scalerank") }
      )
    }
    setupSource(testSource)
    assertEquals("{sum=[[+, [number, [accumulated]], [number, [get, sum]]], [get, scalerank]]}", testSource.clusterProperties.toString())
  }

  @Test
  @UiThreadTest
  fun multiClusterPropertyTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      clusterProperty(
        "sum",
        sum {
          accumulated()
          get { literal("sum") }
        },
        get { literal("scalerank") }
      )
      clusterProperty(
        "test",
        get { literal("scalerank") },
        sum {
          literal(1)
          literal(2)
        }
      )
    }
    setupSource(testSource)
    assertEquals("{test=[[get, scalerank], 3.0], sum=[[+, [number, [accumulated]], [number, [get, sum]]], [get, scalerank]]}", testSource.clusterProperties.toString())
  }

  @Test
  @UiThreadTest
  fun lineMetricsTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      lineMetrics(true)
    }
    setupSource(testSource)
    assertEquals(true, testSource.lineMetrics)
  }

  @Test
  @UiThreadTest
  fun lineMetricsAsExpressionTest() {
    val expression = literal(true)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      lineMetrics(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.lineMetricsAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun generateIdTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      generateId(true)
    }
    setupSource(testSource)
    assertEquals(true, testSource.generateId)
  }

  @Test
  @UiThreadTest
  fun generateIdAsExpressionTest() {
    val expression = literal(true)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      generateId(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.generateIdAsExpression?.toString())
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      prefetchZoomDelta(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaAfterBindTest() {
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.prefetchZoomDelta(1L)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaAsExpressionTest() {
    val expression = literal(1L)
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
      prefetchZoomDelta(expression)
    }
    setupSource(testSource)
    assertEquals(expression.toString(), testSource.prefetchZoomDeltaAsExpression?.toString())
  }

  @Test
  @UiThreadTest
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
    setupSource(testSource)
    // Plain json string data getter is not supported due to performance consideration.
    assertEquals(null, testSource.data)
  }

  @Test
  @UiThreadTest
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
    setupSource(testSource)
    // Plain json string data getter is not supported due to performance consideration.
    assertEquals(null, testSource.data)
  }

  @Test
  @UiThreadTest
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
    setupSource(testSource)
    // Plain json string data getter is not supported due to performance consideration.
    assertEquals(null, testSource.data)
  }

  @Test
  @UiThreadTest
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
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.feature(feature)
    // Plain json string data getter is not supported due to performance consideration.
    assertEquals(null, testSource.data)
  }

  @Test
  @UiThreadTest
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
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.featureCollection(featureCollection)
    // Plain json string data getter is not supported due to performance consideration.
    assertEquals(null, testSource.data)
  }

  @Test
  @UiThreadTest
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
    val testSource = geoJsonSource("testId") {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.geometry(feature.geometry()!!)
    // Plain json string data getter is not supported due to performance consideration.
    assertEquals(null, testSource.data)
  }

  // Default source properties getter tests

  @Test
  @UiThreadTest
  fun defaultSourcePropertiesTest() {
    assertNotNull("defaultMaxzoom should not be null", GeoJsonSource.defaultMaxzoom)
    assertNotNull("defaultMaxzoomAsExpression should not be null", GeoJsonSource.defaultMaxzoomAsExpression)
    assertNotNull("defaultBuffer should not be null", GeoJsonSource.defaultBuffer)
    assertNotNull("defaultBufferAsExpression should not be null", GeoJsonSource.defaultBufferAsExpression)
    assertNotNull("defaultTolerance should not be null", GeoJsonSource.defaultTolerance)
    assertNotNull("defaultToleranceAsExpression should not be null", GeoJsonSource.defaultToleranceAsExpression)
    assertNotNull("defaultCluster should not be null", GeoJsonSource.defaultCluster)
    assertNotNull("defaultClusterAsExpression should not be null", GeoJsonSource.defaultClusterAsExpression)
    assertNotNull("defaultClusterRadius should not be null", GeoJsonSource.defaultClusterRadius)
    assertNotNull("defaultClusterRadiusAsExpression should not be null", GeoJsonSource.defaultClusterRadiusAsExpression)
    assertNotNull("defaultClusterMaxZoom should not be null", GeoJsonSource.defaultClusterMaxZoom)
    assertNotNull("defaultClusterMaxZoomAsExpression should not be null", GeoJsonSource.defaultClusterMaxZoomAsExpression)
    assertNotNull("defaultLineMetrics should not be null", GeoJsonSource.defaultLineMetrics)
    assertNotNull("defaultLineMetricsAsExpression should not be null", GeoJsonSource.defaultLineMetricsAsExpression)
    assertNotNull("defaultGenerateId should not be null", GeoJsonSource.defaultGenerateId)
    assertNotNull("defaultGenerateIdAsExpression should not be null", GeoJsonSource.defaultGenerateIdAsExpression)
    assertNotNull("defaultPrefetchZoomDelta should not be null", GeoJsonSource.defaultPrefetchZoomDelta)
    assertNotNull("defaultPrefetchZoomDeltaAsExpression should not be null", GeoJsonSource.defaultPrefetchZoomDeltaAsExpression)
  }

  companion object {
    private const val TEST_URI = "https://raw.githubusercontent.com/mapbox/mapbox-gl-native-android/master/MapboxGLAndroidSDKTestApp/src/main/assets/earthquakes.geojson"
    private val TEST_GEOJSON = FeatureCollection.fromFeatures(listOf()).toJson()
  }
}

// End of generated file.