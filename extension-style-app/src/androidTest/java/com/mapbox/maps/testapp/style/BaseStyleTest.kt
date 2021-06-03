package com.mapbox.maps.testapp.style

import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.light.generated.setLight
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.terrain.generated.setTerrain
import com.mapbox.maps.extension.testapp.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumentation test for Layers to test Layer properties.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseStyleTest {

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  protected lateinit var style: Style

  @get:Rule
  var rule = ActivityScenarioRule(AppCompatActivity::class.java)

  @Before
  fun before() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mapView = MapView(context)
        mapView.id = R.id.mapView
        it.setContentView(mapView)

        mapboxMap = mapView.getMapboxMap()
        mapboxMap.loadStyleUri(
          "mapbox://styles/mapbox/empty-v9"
        ) {
          this@BaseStyleTest.style = it
          latch.countDown()
        }
        mapView.onStart()
      }
    }
    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  fun tearDown() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStop()
        mapView.onDestroy()
        latch.countDown()
      }
    }
    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  fun setupLayer(layer: StyleContract.StyleLayerExtension) {
    style.addLayer(layer)
  }

  fun setupLight(light: StyleContract.StyleLightExtension) {
    style.setLight(light)
  }

  fun setupTerrain(terrain: StyleContract.StyleTerrainExtension) {
    style.setTerrain(terrain)
  }

  fun setupSource(source: StyleContract.StyleSourceExtension) {
    style.addSource(source)
  }
}