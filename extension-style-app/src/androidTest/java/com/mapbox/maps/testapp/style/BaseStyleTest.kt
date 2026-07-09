package com.mapbox.maps.testapp.style

import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.atmosphere.generated.setAtmosphere
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.light.Light
import com.mapbox.maps.extension.style.light.generated.AmbientLight
import com.mapbox.maps.extension.style.light.generated.DirectionalLight
import com.mapbox.maps.extension.style.light.generated.FlatLight
import com.mapbox.maps.extension.style.light.getLight
import com.mapbox.maps.extension.style.light.setLight
import com.mapbox.maps.extension.style.precipitations.generated.Rain
import com.mapbox.maps.extension.style.precipitations.generated.Snow
import com.mapbox.maps.extension.style.precipitations.generated.setRain
import com.mapbox.maps.extension.style.precipitations.generated.setSnow
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.terrain.generated.setTerrain
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumentation test for Layers to test Layer properties.
 *
 * The [ActivityScenario] is launched once per test class (in [setupClass]) to save time.
 * A fresh [MapView] and [MapboxMap] are created for each test (in [before]) for isolation.
 * UI thread work is dispatched via [runOnUiThread] ([Instrumentation.runOnMainSync]) to
 * avoid depending on the Activity not being destroyed between tests.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseStyleTest {

  private lateinit var mapView: MapView
  private lateinit var style: Style
  protected lateinit var mapboxMap: MapboxMap

  companion object {
    private lateinit var _scenario: ActivityScenario<AppCompatActivity>
    private lateinit var _activity: AppCompatActivity

    @BeforeClass
    @JvmStatic
    fun setupClass() {
      _scenario = ActivityScenario.launch(AppCompatActivity::class.java)
      _scenario.onActivity { _activity = it }
    }

    @AfterClass
    @JvmStatic
    fun tearDownClass() {
      _scenario.close()
    }
  }

  protected fun runOnUiThread(block: () -> Unit) {
    InstrumentationRegistry.getInstrumentation().runOnMainSync(block)
  }

  @Before
  fun before() {
    val latch = CountDownLatch(1)
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
      mapView = MapView(_activity)
      mapboxMap = mapView.mapboxMap
      mapboxMap.loadStyle(
        "mapbox://styles/mapbox/empty-v9"
      ) { style ->
        this@BaseStyleTest.style = style
        latch.countDown()
      }
      mapView.onStart()
    }
    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  fun tearDown() {
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
      mapView.onStop()
      mapView.onDestroy()
    }
  }

  fun setupLayer(layer: StyleContract.StyleLayerExtension) {
    style.addLayer(layer)
  }

  fun removeLayer(layer: Layer) {
    style.removeStyleLayer(layer.layerId)
  }

  fun setupTerrain(terrain: StyleContract.StyleTerrainExtension) {
    style.setTerrain(terrain)
  }

  fun setupAtmosphere(atmosphere: StyleContract.StyleAtmosphereExtension) {
    style.setAtmosphere(atmosphere)
  }

  fun setupSource(source: StyleContract.StyleSourceExtension) {
    style.addSource(source)
  }

  fun setupLight(light: FlatLight) {
    style.setLight(light)
  }

  fun setupLight(ambientLight: AmbientLight, directionalLight: DirectionalLight) {
    style.setLight(ambientLight, directionalLight)
  }

  @OptIn(MapboxExperimental::class)
  fun setupSnow(snow: Snow) {
    style.setSnow(snow)
  }

  @OptIn(MapboxExperimental::class)
  fun setupRain(rain: Rain) {
    style.setRain(rain)
  }

  fun getLayer(id: String): Layer? {
    return style.getLayer(id)
  }

  fun getLight(id: String): Light? {
    return style.getLight(id)
  }
}