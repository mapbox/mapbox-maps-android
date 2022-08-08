package com.mapbox.maps.module.telemetry

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.mapbox.android.telemetry.MapboxTelemetry
import com.mapbox.bindgen.Value
import com.mapbox.common.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class MapTelemetryEventsServiceTest {
  private lateinit var eventsService: EventsService
  private lateinit var mapboxTelemetry: MapboxTelemetry
  private lateinit var telemetry: MapTelemetryImpl

  @Before
  fun setUp() {
    HttpServiceFactory.setUserDefined(DummyHttpClient())
    val context = InstrumentationRegistry.getInstrumentation().context

    val options = EventsServerOptions("empty", "empty")
    eventsService = EventsService.getOrCreate(options)
    mapboxTelemetry = MapboxTelemetry(context, "empty", "empty")

    telemetry = MapTelemetryImpl(mapboxTelemetry, context, "sk.foobar", eventsService)
  }

  @Test
  fun testSetUserTelemetryRequestStateDisabled() {
    telemetry.setUserTelemetryRequestState(false)
    Assert.assertFalse(TelemetryUtils.getEventsCollectionState())
  }

  @Test
  fun testSetUserTelemetryRequestStateEnabled() {
    fun testSetUserTelemetryRequestStateEnabled() {}
    telemetry.setUserTelemetryRequestState(true)
    Assert.assertTrue(TelemetryUtils.getEventsCollectionState())
  }

  @Test
  fun testDisableTelemetrySession() {
    telemetry.disableTelemetrySession()
    val latch = CountDownLatch(1)
    val eventsServiceObserver = object : EventsServiceObserver {
      override fun didEncounterError(error: EventsServiceError, events: Value) {
        latch.countDown()
      }

      override fun didSendEvents(events: Value) {
      }
    }

    eventsService.registerObserver(eventsServiceObserver)
    for (i in 0 until TEST_ITERATIONS) {
      telemetry.onPerformanceEvent(null)
    }

    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    eventsService.unregisterObserver(eventsServiceObserver)
    eventsService.resumeEventsCollection()
  }

  @Test
  @Throws(InterruptedException::class, TimeoutException::class)
  fun testPerformanceEvent() {
    telemetry.setUserTelemetryRequestState(true)
    val latch = CountDownLatch(1)
    val eventsServiceObserver = object : EventsServiceObserver {
      override fun didEncounterError(error: EventsServiceError, events: Value) {
      }

      override fun didSendEvents(events: Value) {
        latch.countDown()
      }
    }

    eventsService.registerObserver(eventsServiceObserver)
    for (i in 0 until TEST_ITERATIONS) {
      telemetry.onPerformanceEvent(null)
    }

    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    eventsService.unregisterObserver(eventsServiceObserver)
  }

  companion object {
    private const val TEST_ITERATIONS = 190
  }
}