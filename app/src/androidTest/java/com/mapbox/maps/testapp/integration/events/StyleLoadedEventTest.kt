package com.mapbox.maps.testapp.integration.events

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.MapView
import com.mapbox.maps.R
import com.mapbox.maps.testapp.EmptyActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Test that the style loaded event is fired even when the sprites loading is failed.
 */
@RunWith(AndroidJUnit4::class)
class StyleLoadedEventTest {
  @get:Rule
  var rule: ActivityScenarioRule<EmptyActivity> = ActivityScenarioRule(EmptyActivity::class.java)

  lateinit var mapView: MapView
  lateinit var countDownLatch: CountDownLatch

  @Before
  fun setUp() {
    rule.scenario.onActivity {
      mapView = MapView(it)
      mapView.id = R.id.mapView
      it.setContentView(mapView)
    }
  }

  @After
  fun cleanUp() {
    rule.scenario.onActivity {
      mapView.onStop()
      mapView.onDestroy()
    }
  }

  @Test
  fun testStyleLoadedEventWhenSpritesLoadFails() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      mapView.getMapboxMap().loadStyle(
        """
      {
        "version": 8,
        "name": "Land",
        "metadata": {
          "mapbox:autocomposite": true
        },
        "sources": {
          "composite": {
            "url": "mapbox://mapbox.mapbox-terrain-v2",
            "type": "vector"
          }
        },
        "glyphs": "mapbox://fonts/mapbox/{fontstack}/{range}.pbf",
        "layers": [
          {
            "layout": {
              "visibility": "visible"
            },
            "type": "fill",
            "source": "composite",
            "id": "admin",
            "paint": {
              "fill-color": "hsl(359, 100%, 50%)",
              "fill-opacity": 1
            },
            "source-layer": "landcover"
          },
          {
            "layout": {
              "visibility": "visible"
            },
            "type": "fill",
            "source": "composite",
            "id": "layer-0",
            "paint": {
              "fill-opacity": 1,
              "fill-color": "hsl(359, 100%, 50%)"
            },
            "source-layer": "Layer_0"
          }
        ]
      }
        """.trimIndent()
      ) {
        countDownLatch.countDown()
      }
      mapView.onStart()
    }

    if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }
}