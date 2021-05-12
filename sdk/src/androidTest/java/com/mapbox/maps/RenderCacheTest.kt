package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RenderCacheTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Before
  @UiThreadTest
  fun setUp() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      mapView = MapView(it)
      mapboxMap = mapView.getMapboxMap()
      it.frameLayout.addView(mapView)
      mapView.onStart()
      countDownLatch.countDown()
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun setDisabledRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.setRenderCache(RenderCache.Disabled)
        Assert.assertEquals(
          RenderCache.CACHE_DISABLED,
          Settings.get(RenderCache.RENDER_CACHE_SETTING).contents as Long
        )
      }
    }
  }

  @Test
  fun setSmallRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.setRenderCache(RenderCache.Small)
        Assert.assertEquals(
          RenderCache.CACHE_SIZE_SMALL_MB,
          Settings.get(RenderCache.RENDER_CACHE_SETTING).contents as Long
        )
      }
    }
  }

  @Test
  fun setLargeRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.setRenderCache(RenderCache.Large)
        Assert.assertEquals(
          RenderCache.CACHE_SIZE_LARGE_MB,
          Settings.get(RenderCache.RENDER_CACHE_SETTING).contents as Long
        )
      }
    }
  }

  @Test
  fun setCustomRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        val customSize = 256L
        mapView.setRenderCache(RenderCache.Custom(customSize))
        Assert.assertEquals(
          customSize, Settings.get(RenderCache.RENDER_CACHE_SETTING).contents as Long
        )
      }
    }
  }

  @Test
  fun setInvalidRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        val customSize = -1L
        mapView.setRenderCache(RenderCache.Custom(customSize))
        // render cache disabled by default now so checking for 0 here
        Assert.assertEquals(
          0L, Settings.get(RenderCache.RENDER_CACHE_SETTING).contents as Long
        )
      }
    }
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }
}