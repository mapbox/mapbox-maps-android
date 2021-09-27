package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class MapIntegrationTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  private val pointCount = 10000

  private fun createRandomPoint(): Point {
    val random = Random()
    return Point.fromLngLat(
      random.nextDouble() * -360.0 + 180.0,
      random.nextDouble() * -180.0 + 90.0
    )
  }

  private fun getFeatureCollection(): FeatureCollection {
    val pointList = mutableListOf<Feature>()
    for (i in 0..pointCount) {
      val point = createRandomPoint()
      pointList.add(Feature.fromGeometry(point))
    }
    return FeatureCollection.fromFeatures(pointList)
  }

  @Before
  @UiThreadTest
  fun setUp() {
  }

  @Test
  fun testSetCameraOnStart() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapView.onStart()
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(17.045988781311138, 51.12341909978734))
            .bearing(27.254667247679752)
            .pitch(0.0)
            .padding(EdgeInsets(140.0, 140.0, 140.0, 140.0))
            .zoom(13.282170222962456)
            .build()
        )
        countDownLatch.countDown()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplyDataToGeoJson() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapboxMap.loadStyle(
          style(Style.MAPBOX_STREETS) {
            +geoJsonSource("source") {
              data("")
            }
            +circleLayer("layer", "source") {
              circleColor("red")
              circleRadius(1.0)
            }
          }
        ) {
          countDownLatch.countDown()
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplyDataWithFeatureGeojsonBuilder() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(0.1, 0.1))
            .zoom(9.0)
            .build()
        )
        mapboxMap.loadStyle(
          style(Style.MAPBOX_STREETS) {
            // prepare layer
            +circleLayer("layer", "source") {
              circleColor("red")
              circleRadius(10.0)
            }
            // async loading
            +geoJsonSource("source") {
              // set geometry
              geometry(Point.fromLngLat(0.0, 0.0))
              // and then set data - data must overwrite geometry despite geometry being async
              data(
                """
            {
              "type": "FeatureCollection",
              "features": [
                {
                  "type": "Feature",
                  "geometry": {
                    "type": "Point",
                    "coordinates": [
                      0.1,
                      0.1
                    ]
                  },
                  "properties": {
                    "icon-image": "cafe-15",
                    "is-draggable": true
                  }
                }
              ]
            }
                """.trimIndent()
              )
            }
          }
        ) {
          mapView.postDelayed(
            {
              mapboxMap.queryRenderedFeatures(
                ScreenCoordinate(mapView.width / 2.0, mapView.height / 2.0),
                RenderedQueryOptions(listOf("layer"), null)
              ) { result ->
                if (result.value?.size == 1) {
                  countDownLatch.countDown()
                }
              }
            },
            1_000L
          )
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplySyncDataToGeoJson1() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(0.1, 0.1))
            .zoom(9.0)
            .build()
        )
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
          // prepare layer
          val layer = circleLayer("layer", "source") {
            circleColor("red")
            circleRadius(10.0)
          }
          // async loading
          val source = geoJsonSource("source") {
            geometry(Point.fromLngLat(0.0, 0.0))
          }
          source.geometry(Point.fromLngLat(0.0, 0.0)) {}
          source.feature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0))) {}
          source.featureCollection(
            FeatureCollection.fromFeature(
              Feature.fromGeometry(
                Point.fromLngLat(
                  0.0,
                  0.0
                )
              )
            )
          ) {}
          // sync method must clear async queue and not allow current running async task apply changes
          source.geometry(Point.fromLngLat(0.1, 0.1))
          style.addSource(source)
          style.addLayer(layer)
          mapView.postDelayed(
            {
              mapboxMap.queryRenderedFeatures(
                ScreenCoordinate(mapView.width / 2.0, mapView.height / 2.0),
                RenderedQueryOptions(listOf("layer"), null)
              ) { result ->
                if (result.value?.size == 1) {
                  countDownLatch.countDown()
                }
              }
            },
            1_000L
          )
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplySyncDataToGeoJson2() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(0.1, 0.1))
            .zoom(9.0)
            .build()
        )
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
          // prepare layer
          val layer = circleLayer("layer", "source") {
            circleColor("red")
            circleRadius(10.0)
          }
          // async loading
          val source = geoJsonSource("source") {
            geometry(Point.fromLngLat(0.0, 0.0))
          }
          // sync method must clear async queue and not allow current running async task apply changes
          source.data(
            """
            {
              "type": "FeatureCollection",
              "features": [
                {
                  "type": "Feature",
                  "geometry": {
                    "type": "Point",
                    "coordinates": [
                      0.1,
                      0.1
                    ]
                  },
                  "properties": {
                    "icon-image": "cafe-15",
                    "is-draggable": true
                  }
                }
              ]
            }
            """.trimIndent()
          )
          style.addSource(source)
          style.addLayer(layer)
          mapView.postDelayed(
            {
              mapboxMap.queryRenderedFeatures(
                ScreenCoordinate(mapView.width / 2.0, mapView.height / 2.0),
                RenderedQueryOptions(listOf("layer"), null)
              ) { result ->
                if (result.value?.size == 1) {
                  countDownLatch.countDown()
                }
              }
            },
            1_000L
          )
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplySyncDataToGeoJson3() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(0.2, 0.2))
            .zoom(9.0)
            .build()
        )
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
          // prepare layer
          val layer = circleLayer("layer", "source") {
            circleColor("red")
            circleRadius(10.0)
          }
          // async loading
          val source = geoJsonSource("source") {
            geometry(Point.fromLngLat(0.0, 0.0))
          }
          // sync method must clear async queue and not allow current running async task apply changes
          source.geometry(Point.fromLngLat(0.1, 0.1))
          // follow up with new async that must take effect
          source.geometry(Point.fromLngLat(0.2, 0.2)) {}
          style.addSource(source)
          style.addLayer(layer)
          mapView.postDelayed(
            {
              mapboxMap.queryRenderedFeatures(
                ScreenCoordinate(mapView.width / 2.0, mapView.height / 2.0),
                RenderedQueryOptions(listOf("layer"), null)
              ) { result ->
                if (result.value?.size == 1) {
                  countDownLatch.countDown()
                }
              }
            },
            1_000L
          )
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplySyncDataToGeoJson4() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(0.2, 0.2))
            .zoom(9.0)
            .build()
        )
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
          // prepare layer
          val layer = circleLayer("layer", "source") {
            circleColor("red")
            circleRadius(10.0)
          }
          // async loading, but we proceed after parsing
          geoJsonSource(
            "source",
            config = {
              geometry(Point.fromLngLat(0.0, 0.0))
            }
          ) { source ->
            // sync method
            source.geometry(Point.fromLngLat(0.1, 0.1))
            // follow up with new async that must take effect
            source.geometry(Point.fromLngLat(0.2, 0.2)) {}
            style.addSource(source)
            style.addLayer(layer)
            mapView.postDelayed(
              {
                mapboxMap.queryRenderedFeatures(
                  ScreenCoordinate(mapView.width / 2.0, mapView.height / 2.0),
                  RenderedQueryOptions(listOf("layer"), null)
                ) { result ->
                  if (result.value?.size == 1) {
                    countDownLatch.countDown()
                  }
                }
              },
              1_000L
            )
          }
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplySyncDataToGeoJsonUsingGetSourceAs() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.getMapboxMap()
        it.frameLayout.addView(mapView)
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(0.0, 0.0))
            .zoom(9.0)
            .build()
        )
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
          val source = geoJsonSource("source") {
            featureCollection(FeatureCollection.fromFeatures(emptyList()))
          }
          style.addSource(source)
          val layer = circleLayer("layer", "source") {
            circleColor("red")
            circleRadius(10.0)
          }
          style.addLayer(layer)
          val feature = Feature.fromJson(
            """
                {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [0.0, 0.0]
                    },
                    "properties": {
                        "fillOpacity": 0.5,
                        "fillColor": "rgba(255, 0, 255, 1.0)",
                        "fillPattern": "",
                        "type": "circle",
                        "radius": 1.0
                    }
                }
            """.trimIndent()
          )
          style.getSourceAs<GeoJsonSource>("source")!!
            .featureCollection(
              FeatureCollection.fromFeatures(
                listOf(feature)
              )
            )
          mapView.postDelayed(
            {
              mapboxMap.queryRenderedFeatures(
                ScreenCoordinate(mapView.width / 2.0, mapView.height / 2.0),
                RenderedQueryOptions(listOf("layer"), null)
              ) { result ->
                if (result.value?.size == 1) {
                  countDownLatch.countDown()
                }
              }
            },
            1_000L
          )
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testApplyLargeDataAfterCreate() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      mapView = MapView(it)
      mapboxMap = mapView.getMapboxMap()
      it.frameLayout.addView(mapView)
      mapboxMap.setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(0.0, 0.0))
          .zoom(9.0)
          .build()
      )
      mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
        val source = geoJsonSource("source") {
          geometry(Point.fromLngLat(0.0, 0.0))
        }
        // Set a large data async
        source.featureCollection(getFeatureCollection()) {
          fail("This callback should not be invoked")
        }
        // change geometry immediately, this should cancel the last large data update
        source.geometry(Point.fromLngLat(0.0, 0.0))
        // Set a large data async, this should update correctly.
        source.featureCollection(getFeatureCollection()) {
          countDownLatch.countDown()
        }
      }
    }
    if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }
}