// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import android.os.Handler
import android.os.Looper
import android.os.Process
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.GeoJSONSourceData
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.dsl.generated.sum
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class GeoJsonSourceTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()
  private val jsonSlot = slot<GeoJSONSourceData>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val expectedDelta = mockk<Expected<String, Byte>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
    every { expected.error } returns null
    every { expectedDelta.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.isValid() } returns true

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleSourcePropertyDefaultValue(any(), any()) } returns styleProperty
  }

  @After
  fun tearDown() {
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).run {
      if (isPaused)
        unPause()
    }
  }

  @Test
  fun getTypeTest() {
    val testSource = geoJsonSource("testId") {}
    assertEquals("geojson", testSource.getType())
  }

  @Test
  fun dataSet() {
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    val testSource = geoJsonSource("testId") {
      data(TEST_GEOJSON)
    }
    testSource.bindTo(style)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()

    verify { style.setStyleGeoJSONSourceData("testId", capture(jsonSlot)) }
    assertTrue(jsonSlot.captured.string.contains("{\"type\":\"FeatureCollection\",\"features\":[]}"))
  }

  @Test
  fun dataSetWithId() {
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    val testSource = geoJsonSource("testId") {
      data(TEST_GEOJSON, DATA_ID)
    }
    testSource.bindTo(style)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()

    verify { style.setStyleGeoJSONSourceData("testId", DATA_ID, capture(jsonSlot)) }
    assertTrue(jsonSlot.captured.string.contains("{\"type\":\"FeatureCollection\",\"features\":[]}"))
  }

  @Test
  fun dataSetAfterBind() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.data(TEST_GEOJSON)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    verify { style.setStyleGeoJSONSourceData("testId", capture(jsonSlot)) }
    assertEquals(jsonSlot.captured.string, "{\"type\":\"FeatureCollection\",\"features\":[]}")

    testSource.data(TEST_GEOJSON, DATA_ID)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    verify { style.setStyleGeoJSONSourceData("testId", DATA_ID, capture(jsonSlot)) }
    assertEquals(jsonSlot.captured.string, "{\"type\":\"FeatureCollection\",\"features\":[]}")
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
  fun urlSetTest() {
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    val testSource = geoJsonSource("testId") {
      url("testUrl")
    }
    testSource.bindTo(style)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()

    verify { style.setStyleGeoJSONSourceData("testId", capture(jsonSlot)) }
    assertEquals(jsonSlot.captured.string, "testUrl")
  }

  @Test
  fun urlSetAfterBindTest() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.url("testUrl")
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    verify { style.setStyleGeoJSONSourceData("testId", capture(jsonSlot)) }
    assertEquals(jsonSlot.captured.string, "testUrl")
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
  fun maxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxzoom?.toString())
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
  fun attributionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.attribution?.toString())
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
  fun bufferGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.buffer?.toString())
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
  fun toleranceGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1.0.toString(), testSource.tolerance?.toString())
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
  fun clusterGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(true.toString(), testSource.cluster?.toString())
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
  fun clusterRadiusGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.clusterRadius?.toString())
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
  fun clusterMaxZoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.clusterMaxZoom?.toString())
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
  fun clusterPropertiesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>))
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>).toString(), testSource.clusterProperties?.toString())
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
  fun lineMetricsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(true.toString(), testSource.lineMetrics?.toString())
    verify { style.getStyleSourceProperty("testId", "lineMetrics") }
  }

  @Test
  fun generateIdSet() {
    val testSource = geoJsonSource("testId") {
      generateId(true)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("generateId=true"))
  }

  @Test
  fun generateIdGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(true.toString(), testSource.generateId?.toString())
    verify { style.getStyleSourceProperty("testId", "generateId") }
  }

  @Test
  fun promoteIdSet() {
    val testSource = geoJsonSource("testId") {
      promoteId(PromoteId(propertyName = "abc"))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("promoteId=abc"))
  }

  @Test
  fun promoteIdGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)

    assertEquals(PromoteId(propertyName = "abc"), testSource.promoteId)
    verify { style.getStyleSourceProperty("testId", "promoteId") }
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
  fun prefetchZoomDeltaSetAfterBind() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(1L)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
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
  fun emptyDataTest() {
    val testSource = geoJsonSource("testId")
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("type=geojson"))
  }

  @Test
  fun dataSetBeforeBind() {
    val testSource = geoJsonSource("testId") {}
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.data(TEST_GEOJSON, DATA_ID)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.bindTo(style)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    verify(exactly = 1) {
      style.setStyleGeoJSONSourceData(
        "testId",
        DATA_ID,
        capture(jsonSlot)
      )
    }
    assertEquals(jsonSlot.captured.string, "{\"type\":\"FeatureCollection\",\"features\":[]}")
  }

  @Test
  fun featureAfterBindTest() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.feature(FEATURE)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { style.setStyleGeoJSONSourceData("testId", any()) }

    testSource.feature(FEATURE, DATA_ID)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { style.setStyleGeoJSONSourceData("testId", DATA_ID, any()) }
  }

  @Test
  fun featureCollectionAfterBindTest() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.featureCollection(FEATURE_COLLECTION)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { style.setStyleGeoJSONSourceData("testId", any()) }

    testSource.featureCollection(FEATURE_COLLECTION, DATA_ID)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { style.setStyleGeoJSONSourceData("testId", DATA_ID, any()) }
  }

  @Test
  fun featureCollectionBeforeBindTest() {
    val testSource = geoJsonSource("testId") {}
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.featureCollection(FEATURE_COLLECTION, DATA_ID)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.bindTo(style)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) {
      style.setStyleGeoJSONSourceData(
        "testId",
        DATA_ID,
        any()
      )
    }
  }

  @Test
  fun geometryAfterBindTest() {
    val testSource = geoJsonSource("testId") {}
    testSource.bindTo(style)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()
    testSource.geometry(FEATURE.geometry()!!)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { style.setStyleGeoJSONSourceData("testId", any()) }

    testSource.geometry(FEATURE.geometry()!!, DATA_ID)
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { style.setStyleGeoJSONSourceData("testId", DATA_ID, any()) }
  }

  @Test
  fun featureTest() {
    val testSource = geoJsonSource("testId") {
      feature(FEATURE)
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("type=geojson"))
  }

  @Test
  fun featureCollectionTest() {
    val testSource = geoJsonSource("testId") {
      featureCollection(FEATURE_COLLECTION)
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("type=geojson"))
  }

  @Test
  fun geometryTest() {
    val testSource = geoJsonSource("testId") {
      geometry(FEATURE.geometry()!!)
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
  fun defaultBufferGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultBuffer?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "buffer") }
  }

  @Test
  fun defaultToleranceGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    assertEquals(1.0.toString(), GeoJsonSource.defaultTolerance?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "tolerance") }
  }

  @Test
  fun defaultClusterGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    assertEquals(true.toString(), GeoJsonSource.defaultCluster?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "cluster") }
  }

  @Test
  fun defaultClusterRadiusGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultClusterRadius?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterRadius") }
  }

  @Test
  fun defaultClusterMaxZoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultClusterMaxZoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "clusterMaxZoom") }
  }

  @Test
  fun defaultLineMetricsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    assertEquals(true.toString(), GeoJsonSource.defaultLineMetrics?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "lineMetrics") }
  }

  @Test
  fun defaultGenerateIdGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    assertEquals(true.toString(), GeoJsonSource.defaultGenerateId?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "generateId") }
  }

  @Test
  fun defaultPrefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), GeoJsonSource.defaultPrefetchZoomDelta?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("geojson", "prefetch-zoom-delta") }
  }

  @Test
  fun testWorkerThread() {
    val latch = CountDownLatch(1)
    Handler(GeoJsonSource.workerThread.looper).post {
      val actualPriority = Process.getThreadPriority(Process.myTid())
      assertEquals(Process.THREAD_PRIORITY_DEFAULT, actualPriority)
      assertEquals("GEOJSON_PARSER", Thread.currentThread().name)
      latch.countDown()
    }
    if (!latch.await(2, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  companion object {
    private val TEST_GEOJSON = FeatureCollection.fromFeatures(listOf()).toJson()
    private val DATA_ID = "data-id"
    private val FEATURE = Feature.fromJson(
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
    private val FEATURE_COLLECTION = FeatureCollection.fromJson(
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
  }
}

// End of generated file.