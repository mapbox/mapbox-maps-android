package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.*
import java.lang.RuntimeException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@MapboxExperimental
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
        mapView.getMapboxMap().setRenderCacheOptions(RenderCacheOptions.Builder().setDisabled().build())
        Assert.assertEquals(
          RENDER_CACHE_DISABLED,
          mapView.getMapboxMap().getRenderCacheOptions().size
        )
      }
    }
  }

  @Test
  fun setSmallRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.getMapboxMap().setRenderCacheOptions(RenderCacheOptions.Builder().setSmallSize().build())
        Assert.assertEquals(
          RENDER_CACHE_SIZE_SMALL_MB,
          mapView.getMapboxMap().getRenderCacheOptions().size
        )
      }
    }
  }

  @Test
  fun setLargeRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.getMapboxMap().setRenderCacheOptions(RenderCacheOptions.Builder().setLargeSize().build())
        Assert.assertEquals(
          RENDER_CACHE_SIZE_LARGE_MB,
          mapView.getMapboxMap().getRenderCacheOptions().size
        )
      }
    }
  }

  @Test
  fun setCustomRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        val customSize = 256
        mapView.getMapboxMap().setRenderCacheOptions(RenderCacheOptions.Builder().size(customSize).build())
        Assert.assertEquals(
          customSize,
          mapView.getMapboxMap().getRenderCacheOptions().size
        )
      }
    }
  }

  @Test(expected = RuntimeException::class)
  fun setInvalidRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        val invalidSize = -1
        mapView.getMapboxMap().setRenderCacheOptions(RenderCacheOptions.Builder().size(invalidSize).build())
      }
    }
  }

  @Test
  fun setNoSizeRenderCache() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.getMapboxMap().setRenderCacheOptions(RenderCacheOptions.Builder().build())
        Assert.assertEquals(
          RENDER_CACHE_DISABLED,
          mapView.getMapboxMap().getRenderCacheOptions().size
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