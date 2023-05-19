// This file is generated.

package com.mapbox.maps.testapp.style.sources.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.SourceDataLoadedCallback
import com.mapbox.maps.SourceDataLoadedType
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Basic smoke tests for GeoJsonSource.
 */
@RunWith(AndroidJUnit4::class)
class GeoJsonSourceTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun dataTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      data(TEST_GEOJSON)
    }
    setupSource(testSource)
    assertNotNull(testSource.data)
  }

  @Test
  fun dataAfterBindTest() {
    val latch = CountDownLatch(1)
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        assertNotNull(testSource.data)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
          testSource.data(TEST_GEOJSON)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  @UiThreadTest
  fun emptyDataTest() {
    val testSource = geoJsonSource(SOURCE_ID)
    setupSource(testSource)
    assertNotNull(testSource.data)
  }

  @Test
  fun urlTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    assertEquals(TEST_URI, answerList[1])
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  fun urlAfterBindTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) { }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
          testSource.url(TEST_URI)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    assertEquals(TEST_URI, answerList[1])
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  @UiThreadTest
  fun maxzoomTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      maxzoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.maxzoom)
  }

  @Test
  @UiThreadTest
  fun attributionTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      attribution("abc")
    }
    setupSource(testSource)
    assertEquals("abc", testSource.attribution)
  }

  @Test
  @UiThreadTest
  fun bufferTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      buffer(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.buffer)
  }

  @Test
  @UiThreadTest
  fun toleranceTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      tolerance(1.0)
    }
    setupSource(testSource)
    assertEquals(1.0, testSource.tolerance)
  }

  @Test
  @UiThreadTest
  fun clusterTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      cluster(true)
    }
    setupSource(testSource)
    assertEquals(true, testSource.cluster)
  }

  @Test
  @UiThreadTest
  fun clusterRadiusTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      clusterRadius(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.clusterRadius)
  }

  @Test
  @UiThreadTest
  fun clusterMaxZoomTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      clusterMaxZoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.clusterMaxZoom)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun clusterPropertiesTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      clusterProperties((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>))
    }
    setupSource(testSource)
    assertEquals((hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>), testSource.clusterProperties)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun clusterPropertyTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
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
    val testSource = geoJsonSource(SOURCE_ID) {
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
    val testSource = geoJsonSource(SOURCE_ID) {
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
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      lineMetrics(true)
    }
    setupSource(testSource)
    assertEquals(true, testSource.lineMetrics)
  }

  @Test
  @UiThreadTest
  fun generateIdTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      generateId(true)
    }
    setupSource(testSource)
    assertEquals(true, testSource.generateId)
  }

  @Test
  @UiThreadTest
  fun promoteIdTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      promoteId(PromoteId(propertyName = "abc"))
    }
    setupSource(testSource)
    assertEquals(PromoteId(propertyName = "abc"), testSource.promoteId)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
      prefetchZoomDelta(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaAfterBindTest() {
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.prefetchZoomDelta(1L)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  fun featureTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val dataIdList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) {
      feature(FEATURE, DATA_ID)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        it.dataID?.let(dataIdList::add)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    // Plain json string data getter is not supported due to performance consideration.
    assertNull(answerList[1])
    assertEquals(1, dataIdList.size)
    assertEquals(DATA_ID, dataIdList.first())
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  fun featureCollectionTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val dataIdList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) {
      featureCollection(FEATURE_COLLECTION, DATA_ID)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        it.dataID?.let(dataIdList::add)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    // Plain json string data getter is not supported due to performance consideration.
    assertNull(answerList[1])
    assertEquals(1, dataIdList.size)
    assertEquals(DATA_ID, dataIdList.first())
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  fun geometryTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val dataIdList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) {
      geometry(FEATURE.geometry()!!, DATA_ID)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        it.dataID?.let(dataIdList::add)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    // Plain json string data getter is not supported due to performance consideration.
    assertNull(answerList[1])
    assertEquals(1, dataIdList.size)
    assertEquals(DATA_ID, dataIdList.first())
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  fun featureAfterBindTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val dataIdList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        it.dataID?.let(dataIdList::add)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
          testSource.feature(FEATURE, DATA_ID)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    // Plain json string data getter is not supported due to performance consideration.
    assertNull(answerList[1])
    assertEquals(1, dataIdList.size)
    assertEquals(DATA_ID, dataIdList.first())
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  fun featureCollectionAfterBindTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val dataIdList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        println("Data-id : ${it.dataID}")
        it.dataID?.let(dataIdList::add)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
          testSource.featureCollection(FEATURE_COLLECTION, DATA_ID)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    // Plain json string data getter is not supported due to performance consideration.
    assertNull(answerList[1])
    assertEquals(1, dataIdList.size)
    assertEquals(DATA_ID, dataIdList.first())
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  fun geometryAfterBindTest() {
    val latch = CountDownLatch(2)
    val answerList = mutableListOf<String?>()
    val dataIdList = mutableListOf<String?>()
    val testSource = geoJsonSource(SOURCE_ID) {
      url(TEST_URI)
    }
    val listener = SourceDataLoadedCallback {
      if (it.type == SourceDataLoadedType.METADATA && it.sourceID == SOURCE_ID) {
        answerList.add(testSource.data)
        it.dataID?.let(dataIdList::add)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          setupSource(testSource)
          testSource.geometry(FEATURE.geometry()!!, DATA_ID)
        }
      }
    }
    if (!latch.await(LATCH_MAX_TIME_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(2, answerList.size)
    assertEquals("", answerList[0])
    // Plain json string data getter is not supported due to performance consideration.
    assertNull(answerList[1])
    assertEquals(1, dataIdList.size)
    assertEquals(DATA_ID, dataIdList.first())
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  // Default source properties getter tests

  @Test
  @UiThreadTest
  fun defaultSourcePropertiesTest() {
    assertNotNull("defaultMaxzoom should not be null", GeoJsonSource.defaultMaxzoom)
    assertNotNull("defaultBuffer should not be null", GeoJsonSource.defaultBuffer)
    assertNotNull("defaultTolerance should not be null", GeoJsonSource.defaultTolerance)
    assertNotNull("defaultCluster should not be null", GeoJsonSource.defaultCluster)
    assertNotNull("defaultClusterRadius should not be null", GeoJsonSource.defaultClusterRadius)
    assertNotNull("defaultClusterMaxZoom should not be null", GeoJsonSource.defaultClusterMaxZoom)
    assertNotNull("defaultLineMetrics should not be null", GeoJsonSource.defaultLineMetrics)
    assertNotNull("defaultGenerateId should not be null", GeoJsonSource.defaultGenerateId)
    assertNotNull("defaultPrefetchZoomDelta should not be null", GeoJsonSource.defaultPrefetchZoomDelta)
  }

  private companion object {
    const val TEST_URI = "https://raw.githubusercontent.com/mapbox/mapbox-gl-native-android/master/MapboxGLAndroidSDKTestApp/src/main/assets/earthquakes.geojson"
    const val SOURCE_ID = "testId"
    val TEST_GEOJSON = FeatureCollection.fromFeatures(listOf()).toJson()
    const val LATCH_MAX_TIME_MS = 10_000L
    const val DATA_ID = "data-id"
    val FEATURE = Feature.fromJson(
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
    val FEATURE_COLLECTION = FeatureCollection.fromJson(
      """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "properties": {},
              "geometry": {
                "type": "Point",
                "coordinates": [102.0, 0.5]
              }
            },
            {
              "type": "Feature",
              "properties": {},
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