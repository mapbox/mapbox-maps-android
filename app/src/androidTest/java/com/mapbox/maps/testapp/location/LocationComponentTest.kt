package com.mapbox.maps.testapp.location

import android.Manifest
import android.location.Location
import android.os.Handler
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.location.*
import com.mapbox.maps.testapp.EmptyActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

@RunWith(AndroidJUnit4::class)
@LargeTest
class LocationComponentTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  @get:Rule
  var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Before
  @UiThreadTest
  fun setUp() {
    rule.scenario.onActivity {
      mapView = MapView(it)
      mapboxMap = mapView.getMapboxMap()
      it.setContentView(mapView)
      mapView.onStart()
    }
  }

  @Test
  fun modelSourceTest() {
    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
          val locationComponent = mapView.getLocationPlugin()
          locationComponent.activateLocationComponent(
            LocationComponentActivationOptions
              .builder(activity, it)
              .useDefaultLocationEngine(false)
              .locationModelLayerOptions(LocationModelLayerOptions("asset://race_car_model.gltf"))
              .build()
          )
          locationComponent.enabled = true
          locationComponent.randomLocationUpdates()
          Handler().postDelayed(
            {
              locationComponent.randomLocationUpdates()
              mapboxMap.loadStyleUri(Style.DARK) {
                locationComponent.randomLocationUpdates()
                Handler().postDelayed(
                  {
                    locationComponent.randomLocationUpdates()
                    countDownLatch.countDown()
                  },
                  500
                )
                locationComponent.randomLocationUpdates()
              }
              locationComponent.randomLocationUpdates()
            },
            500
          )
          locationComponent.randomLocationUpdates()
        }
      }
    }
    countDownLatch = CountDownLatch(1)
    if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun locationLayerTest() {
    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
          val locationComponent = mapView.getLocationPlugin()
          locationComponent.activateLocationComponent(
            LocationComponentActivationOptions
              .builder(activity, it)
              .useDefaultLocationEngine(false)
              .build()
          )
          locationComponent.enabled = true
          locationComponent.randomLocationUpdates()
          Handler().postDelayed(
            {
              locationComponent.randomLocationUpdates()
              mapboxMap.loadStyleUri(Style.DARK) {
                locationComponent.randomLocationUpdates()
                Handler().postDelayed(
                  {
                    locationComponent.randomLocationUpdates()
                    countDownLatch.countDown()
                  },
                  500
                )
                locationComponent.randomLocationUpdates()
              }
              locationComponent.randomLocationUpdates()
            },
            500
          )
          locationComponent.randomLocationUpdates()
        }
      }
    }
    countDownLatch = CountDownLatch(1)
    if (!countDownLatch.await(15, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }

  private fun LocationPluginImpl.randomLocationUpdates() {
    val updateCounter = nextInt(50) + 20
    for (i in 0..updateCounter) {
      updateLocationRandomly(Location(""))
    }
  }

  private fun LocationPluginImpl.updateLocationRandomly(location: Location) {
    location.updateLocationRandomly()
    forceLocationUpdate(LocationUpdate(location))
  }

  private fun Location.updateLocationRandomly() {
    latitude = Random.nextDouble(10.0)
    longitude = Random.nextDouble(10.0)
  }
}