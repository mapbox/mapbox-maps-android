package com.mapbox.maps.module.telemetry

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.common.ConfigurationOptions
import com.mapbox.common.ConfigurationService
import com.mapbox.common.ConfigurationServiceError
import com.mapbox.common.ConfigurationServiceObserver
import com.mapbox.common.EventsServerOptions
import com.mapbox.common.EventsService
import com.mapbox.common.EventsServiceError
import com.mapbox.common.EventsServiceObserver
import com.mapbox.common.SdkInformation
import com.mapbox.common.TelemetryService
import com.mapbox.common.TelemetryUtils
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

private const val TAG = "MapTelemetryEventsServi"

@RunWith(AndroidJUnit4::class)
class MapTelemetryEventsServiceTest {
  private lateinit var eventsService: EventsService
  private lateinit var telemetry: MapTelemetryImpl

  @Before
  fun setUp() {
    eventsService = EventsService.getOrCreate(options)

    val telemetryService = TelemetryService.getOrCreate()
    val context = InstrumentationRegistry.getInstrumentation().context
    telemetry = MapTelemetryImpl(context, eventsService, telemetryService, options)
  }

  @Test
  fun testSetUserTelemetryRequestStateDisabled() {
    telemetry.userTelemetryRequestState = false
    Assert.assertFalse(TelemetryUtils.getEventsCollectionState())
  }

  @Test
  fun testSetUserTelemetryRequestStateEnabled() {
    telemetry.userTelemetryRequestState = true
    Assert.assertTrue(TelemetryUtils.getEventsCollectionState())
  }

  @Test
  fun testDisableTelemetrySession() {
    telemetry.disableTelemetrySession()
    val observerErrors = mutableListOf<String>()
    val eventsServiceObserver = object : EventsServiceObserver {
      override fun didEncounterError(error: EventsServiceError, events: Value) {
        Log.e(TAG, "didEncounterError() called with: error = $error, events = $events")
        observerErrors.add("We should not reach this. No errors should have been encountered")
      }

      override fun didSendEvents(events: Value) {
        Log.e(TAG, "didSendEvents() called with: events = $events")
        observerErrors.add("We should not reach this. No events should have been sent!")
      }
    }

    eventsService.registerObserver(eventsServiceObserver)
    try {
      // Try to send events while being disabled
      repeat(TEST_ITERATIONS) {
        telemetry.onPerformanceEvent(null)
      }

      var flushResult: Expected<String, None>? = null
      val flushLatch = CountDownLatch(1)
      eventsService.flush {
        flushResult = it
        flushLatch.countDown()
      }

      Assert.assertTrue(flushLatch.await(1_000L, TimeUnit.MILLISECONDS))
      Assert.assertEquals(
        "Failed to flush 0 event(s): events dispatch disabled",
        flushResult!!.error
      )
      Assert.assertTrue("Errors found: $observerErrors", observerErrors.isEmpty())
    } finally {
      eventsService.unregisterObserver(eventsServiceObserver)
    }
  }

  @Test
  @Throws(InterruptedException::class, TimeoutException::class)
  fun testPerformanceEvent() {
    telemetry.userTelemetryRequestState = true
    val latch = CountDownLatch(TEST_ITERATIONS)
    val eventsServiceObserver = object : EventsServiceObserver {
      override fun didEncounterError(error: EventsServiceError, events: Value) {
        Log.w(TAG, "didEncounterError() called with: error = $error, events = $events")
        throw RuntimeException("Failed to send events")
      }

      override fun didSendEvents(eventsValue: Value) {
        val events = eventsValue.contents as ArrayList<*>
        repeat(events.size) { latch.countDown() }
      }
    }

    eventsService.registerObserver(eventsServiceObserver)

    try {
      repeat(TEST_ITERATIONS) {
        telemetry.onPerformanceEvent(null)
      }

      var flushResult: Expected<String, None>? = null
      val flushLatch = CountDownLatch(1)
      eventsService.flush {
        flushResult = it
        flushLatch.countDown()
      }
      Assert.assertTrue(flushLatch.await(1_000L, TimeUnit.MILLISECONDS))
      Assert.assertNull(flushResult!!.error)
      Assert.assertEquals(None.getInstance(), flushResult!!.value)

      Assert.assertTrue(latch.await(10_000, TimeUnit.MILLISECONDS))
    } finally {
      eventsService.unregisterObserver(eventsServiceObserver)
    }
  }

  companion object {
    private const val TEST_ITERATIONS = 190

    private val options = EventsServerOptions(
      SdkInformation("empty", "empty", "com.mapbox.maps.module.telemetry.test"),
      null
    )

    @BeforeClass
    @JvmStatic
    fun waitForValidConfiguration() {
      val configurationUpdateLatch = CountDownLatch(1)
      val configurationLatch = CountDownLatch(1)
      var configurationResult: Expected<ConfigurationServiceError, ConfigurationOptions>? = null
      val configurationService = ConfigurationService.getOrCreate(options)
      val configurationObserver = object : ConfigurationServiceObserver {
        override fun didStartUpdate() {
          Log.d(TAG, "didStartUpdate() called")
        }

        override fun didUpdate(options: ConfigurationOptions) {
          Log.d(TAG, "didUpdate() called with: options = $options")
          configurationUpdateLatch.countDown()
        }

        override fun didEncounterError(error: ConfigurationServiceError) {
          Log.d(TAG, "didEncounterError() called with: error = $error")
        }
      }
      configurationService.registerObserver(configurationObserver)
      configurationService.getConfig {
        Log.d(TAG, "setUp: configuration result ${it.value} or ${it.error}")
        configurationResult = it
        configurationLatch.countDown()
      }

      Assert.assertTrue(configurationLatch.await(10_000L, TimeUnit.MILLISECONDS))
      if (configurationResult!!.isError) {
        // If there's no configuration let's wait for an update...
        Assert.assertTrue(configurationUpdateLatch.await(30_000L, TimeUnit.MILLISECONDS))
      }
      configurationService.unregisterObserver(configurationObserver)
    }
  }
}