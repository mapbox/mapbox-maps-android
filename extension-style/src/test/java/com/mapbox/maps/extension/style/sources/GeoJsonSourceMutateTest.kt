package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class GeoJsonSourceMutateTest {

  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)

  @Before
  fun prepare() {
    every { expected.error } returns null
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs

    every { style.addStyleSource(any(), any()) } returns expected

    mockkStatic(StyleManager::class)
  }

  @After
  fun tearDown() {
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).run {
      if (isPaused) unPause()
    }
  }

  @Test
  fun fullAfterPartialAfterFull() {
    val testSource = geoJsonSource(SOURCE_ID) {}
    testSource.bindTo(style)
    clearMocks(style)

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()

    testSource.featureCollection(FeatureCollection.fromFeatures(ALL_FEATURES), FULL_ID)
    testSource.addGeoJSONSourceFeatures(ADD_FEATURES, ADD_ID)
    testSource.updateGeoJSONSourceFeatures(UPDATE_FEATURES, UPDATE_ID)
    testSource.removeGeoJSONSourceFeatures(REMOVE_FEATURES.map { it.id()!! }, REMOVE_ID)

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()

    testSource.featureCollection(FeatureCollection.fromFeatures(ALL_FEATURES_2), FULL_ID_2)

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()

    verifyOrder {
      style.setStyleGeoJSONSourceData(
        SOURCE_ID,
        FULL_ID,
        any(),
      )
      style.addGeoJSONSourceFeatures(
        SOURCE_ID,
        ADD_ID,
        ADD_FEATURES,
      )
      style.updateGeoJSONSourceFeatures(
        SOURCE_ID,
        UPDATE_ID,
        UPDATE_FEATURES,
      )
      style.removeGeoJSONSourceFeatures(
        SOURCE_ID,
        REMOVE_ID,
        REMOVE_FEATURES.map { it.id()!! },
      )
      style.setStyleGeoJSONSourceData(
        SOURCE_ID,
        FULL_ID_2,
        any(),
      )
    }
  }

  @Test
  fun fullOverwritesPartialAfterFull() {
    val testSource = geoJsonSource(SOURCE_ID) {}
    testSource.bindTo(style)
    clearMocks(style)

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()

    testSource.featureCollection(FeatureCollection.fromFeatures(ALL_FEATURES), FULL_ID)
    testSource.addGeoJSONSourceFeatures(ADD_FEATURES, ADD_ID)
    testSource.updateGeoJSONSourceFeatures(UPDATE_FEATURES, UPDATE_ID)
    testSource.removeGeoJSONSourceFeatures(REMOVE_FEATURES.map { it.id()!! }, REMOVE_ID)
    testSource.featureCollection(FeatureCollection.fromFeatures(ALL_FEATURES_2), FULL_ID_2)

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()

    verifyOrder {
      style.setStyleGeoJSONSourceData(
        SOURCE_ID,
        FULL_ID_2,
        any(),
      )
    }
    verify(exactly = 0) {
      style.setStyleGeoJSONSourceData(
        SOURCE_ID,
        FULL_ID,
        any(),
      )
    }
    verify(exactly = 0) {
      style.addGeoJSONSourceFeatures(any(), any(), any())
    }
    verify(exactly = 0) {
      style.updateGeoJSONSourceFeatures(any(), any(), any())
    }
    verify(exactly = 0) {
      style.removeGeoJSONSourceFeatures(any(), any(), any())
    }
  }

  @Test
  fun partialUpdatesBeforeStyleLoadLost() {
    val testSource = geoJsonSource(SOURCE_ID) {}

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()

    testSource.featureCollection(FeatureCollection.fromFeatures(ALL_FEATURES), FULL_ID)
    testSource.addGeoJSONSourceFeatures(ADD_FEATURES, ADD_ID)
    testSource.updateGeoJSONSourceFeatures(UPDATE_FEATURES, UPDATE_ID)
    testSource.removeGeoJSONSourceFeatures(REMOVE_FEATURES.map { it.id()!! }, REMOVE_ID)

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()
    Shadows.shadowOf(GeoJsonSource.workerThread.looper).pause()

    testSource.bindTo(style)
    clearMocks(style)

    Shadows.shadowOf(GeoJsonSource.workerThread.looper).idle()

    verifyOrder {
      style.setStyleGeoJSONSourceData(
        SOURCE_ID,
        FULL_ID,
        any(),
      )
    }
    verify(exactly = 0) {
      style.addGeoJSONSourceFeatures(any(), any(), any())
    }
    verify(exactly = 0) {
      style.updateGeoJSONSourceFeatures(any(), any(), any())
    }
    verify(exactly = 0) {
      style.removeGeoJSONSourceFeatures(any(), any(), any())
    }
    verify(exactly = 3) {
      logW("GeoJsonSourceUtils", any())
    }
  }

  companion object {
    private val SOURCE_ID = "source-id"
    private val FULL_ID = "full-id"
    private val FULL_ID_2 = "full-id-2"
    private val ADD_ID = "add-id"
    private val REMOVE_ID = "remove-id"
    private val UPDATE_ID = "update-id"

    private fun feature(id: Int) = Feature.fromGeometry(
      Point.fromLngLat(0.01 * id, 0.01 * id),
      null,
      "feature-$id"
    )

    private val ALL_FEATURES = (0..100).map { feature(it) }
    private val ALL_FEATURES_2 = (200..300).map { feature(it) }
    private val ADD_FEATURES = (101..120).map { feature(it) }
    private val REMOVE_FEATURES = (50..70).map { feature(it) }
    private val UPDATE_FEATURES = (20..80).map { feature(it) }
  }
}