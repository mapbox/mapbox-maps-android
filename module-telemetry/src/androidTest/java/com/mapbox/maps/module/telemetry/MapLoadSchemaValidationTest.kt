package com.mapbox.maps.module.telemetry

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.common.ConfigurationOptions
import com.mapbox.common.ConfigurationService
import com.mapbox.common.ConfigurationServiceError
import com.mapbox.common.ConfigurationServiceObserver
import com.mapbox.common.Event
import com.mapbox.common.EventPriority
import com.mapbox.common.EventsServerOptions
import com.mapbox.common.EventsService
import com.mapbox.common.EventsServiceError
import com.mapbox.common.EventsServiceErrorCode
import com.mapbox.common.EventsServiceObserver
import com.mapbox.common.SdkInformation
import com.mapbox.common.TelemetryCollectionState
import com.mapbox.common.TelemetryService
import com.mapbox.common.TelemetryUtils
import com.mapbox.common.TurnstileEvent
import com.mapbox.common.UserSKUIdentifier
import com.mapbox.maps.module.telemetry.UiFramework.Companion.ANDROID_VIEW
import com.mapbox.maps.module.telemetry.UiFramework.Companion.FLUTTER
import com.mapbox.maps.module.telemetry.UiFramework.Companion.JETPACK_COMPOSE
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Assume
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import com.mapbox.maps.base.BuildConfig as BaseBuildConfig

private const val TAG = "MapLoadSchemaValidation"

/**
 * End-to-end schema-validation test for the Maps SDK telemetry events.
 *
 */
@RunWith(AndroidJUnit4::class)
class MapLoadSchemaValidationTest {

  private val context: Context
    get() = InstrumentationRegistry.getInstrumentation().context

  private lateinit var eventsService: EventsService

  @Before
  fun setUp() {
    eventsService = EventsService.getOrCreate(options)
  }

  @Test
  fun mapLoadEventIsAcceptedForEveryUiFramework() {
    val metadataVariants = listOf(
      ANDROID_VIEW,
      JETPACK_COMPOSE,
      FLUTTER
    ).map { uiFramework ->
      MapTelemetryMetadata.Builder().uiFramework(uiFramework).build()
    } + null

    val mapLoadPayloads = metadataVariants.map { metadata ->
      val mapLoadEvent = MapEventFactory.buildMapLoadEvent(PhoneState(context), metadata)
      Gson().toJson(mapLoadEvent)
    }

    sendAndAssertAccepted(mapLoadPayloads)
  }

  @Test
  fun malformedMapLoadEventIsRejectedWith422() {
    val validMapLoad = MapEventFactory.buildMapLoadEvent(PhoneState(context), null)
    val json = Gson().toJsonTree(validMapLoad).asJsonObject.apply {
      addProperty("invalidField", "this field is not part of the map.load schema")
    }.toString()

    val collector = EventCollector(expected = 1)
    eventsService.registerObserver(collector)
    try {
      val attributes = requireNotNull(Value.fromJson(json).value) {
        "Could not parse malformed event JSON into a Value: $json"
      }
      eventsService.sendEvent(Event(EventPriority.QUEUED, attributes, null)) { response ->
        if (response.isError) Log.w(TAG, "sendEvent enqueue error: ${response.error}")
      }
      flushAndAwait(collector, expectFlushError = true)
      collector.assertInvalidRecordRejected()
    } finally {
      eventsService.unregisterObserver(collector)
    }
  }

  @Test
  fun turnstileEventIsAccepted() {
    val collector = EventCollector(expected = 1)
    eventsService.registerObserver(collector)
    try {
      eventsService.sendTurnstileEvent(TurnstileEvent(UserSKUIdentifier.MAPS_MAUS)) { response ->
        if (response.isError) Log.w(TAG, "sendTurnstileEvent enqueue error: ${response.error}")
      }
      flushAndAwait(collector)
      collector.assertNoInvalidRecords()
    } finally {
      eventsService.unregisterObserver(collector)
    }
  }

  @Test
  fun productionTurnstilePathSendsOnlyAcceptedRecords() {
    val telemetry = MapTelemetryImpl(
      context,
      eventsService,
      TelemetryService.getOrCreate(),
      options,
      Dispatchers.Unconfined,
    )
    val collectionEnabled = CountDownLatch(1)
    TelemetryUtils.setEventsCollectionState(true) { collectionEnabled.countDown() }
    Assert.assertTrue(
      "Could not enable events collection",
      collectionEnabled.await(2_000, TimeUnit.MILLISECONDS)
    )
    Assert.assertTrue(
      "Server configuration restricts collection to turnstile-only",
      TelemetryUtils.getClientServerEventsCollectionState() != TelemetryCollectionState.TURNSTILE_EVENTS_ONLY
    )

    val collector = EventCollector(expected = 2)
    eventsService.registerObserver(collector)
    try {
      telemetry.onAppUserTurnstileEvent(
        MapTelemetryMetadata.Builder().uiFramework(ANDROID_VIEW).build()
      )
      flushAndAwait(collector)
      collector.assertNoInvalidRecords()
    } finally {
      eventsService.unregisterObserver(collector)
    }
  }

  private fun sendAndAssertAccepted(payloads: List<String>) {
    val collector = EventCollector(expected = payloads.size)
    eventsService.registerObserver(collector)
    try {
      payloads.forEach { json ->
        val attributes = requireNotNull(Value.fromJson(json).value) {
          "Could not parse event JSON into a Value: $json"
        }
        eventsService.sendEvent(Event(EventPriority.QUEUED, attributes, null)) { response ->
          if (response.isError) Log.w(TAG, "sendEvent enqueue error: ${response.error}")
        }
      }
      flushAndAwait(collector)
      collector.assertNoInvalidRecords()
      Assert.assertEquals(
        "Not every event was accepted by the backend",
        payloads.size,
        collector.acceptedCount.get()
      )
    } finally {
      eventsService.unregisterObserver(collector)
    }
  }

  private fun flushAndAwait(collector: EventCollector, expectFlushError: Boolean = false) {
    var flushResult: Expected<String, None>? = null
    val flushLatch = CountDownLatch(1)
    eventsService.flush {
      flushResult = it
      flushLatch.countDown()
    }
    Assert.assertTrue("flush callback timed out", flushLatch.await(FLUSH_TIMEOUT_MS, TimeUnit.MILLISECONDS))
    if (expectFlushError) {
      Assert.assertNotNull(
        "flush was expected to report the rejected batch, but reported no error",
        flushResult?.error
      )
    } else {
      Assert.assertNull("flush reported an error: ${flushResult?.error}", flushResult?.error)
    }

    Assert.assertTrue(
      "Backend did not account for every event within ${BACKEND_TIMEOUT_MS}ms " +
        "(accepted=${collector.acceptedCount.get()}, errors=${collector.errors})",
      collector.doneLatch.await(BACKEND_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    )
  }

  private class EventCollector(expected: Int) : EventsServiceObserver {
    val doneLatch = CountDownLatch(expected)
    val acceptedCount = AtomicInteger(0)
    val errors = CopyOnWriteArrayList<EventsServiceError>()

    override fun didSendEvents(events: Value) {
      val size = events.batchSize()
      acceptedCount.addAndGet(size)
      repeat(size) { doneLatch.countDown() }
    }

    override fun didEncounterError(error: EventsServiceError, events: Value) {
      Log.e(TAG, "didEncounterError() code=${error.code}, message=${error.message}, events=$events")
      errors.add(error)
      repeat(events.batchSize()) { doneLatch.countDown() }
    }

    fun assertNoInvalidRecords() {
      val invalidRecords = errors.filter { it.code == EventsServiceErrorCode.INVALID_PAYLOAD }
      Assert.assertTrue(
        "Backend rejected records as invalid (HTTP 422): $invalidRecords",
        invalidRecords.isEmpty()
      )
      Assert.assertTrue("Backend reported errors while sending events: $errors", errors.isEmpty())
    }

    fun assertInvalidRecordRejected() {
      Assert.assertTrue(
        "Expected the backend to reject the malformed event with " +
          "${EventsServiceErrorCode.INVALID_PAYLOAD} (HTTP 422), but it did not. errors=$errors",
        errors.any { it.code == EventsServiceErrorCode.INVALID_PAYLOAD }
      )
      Assert.assertEquals(
        "The malformed event should not have been accepted by the backend",
        0,
        acceptedCount.get()
      )
    }
  }

  companion object {
    private const val FLUSH_TIMEOUT_MS = 5_000L
    private const val BACKEND_TIMEOUT_MS = 15_000L

    private val options = EventsServerOptions(
      SdkInformation(
        BaseBuildConfig.MAPBOX_SDK_IDENTIFIER,
        BaseBuildConfig.MAPBOX_SDK_VERSION,
        "com.mapbox.maps.module.telemetry.test"
      ),
      null
    )

    private fun Value.batchSize(): Int = (contents as? List<*>)?.size ?: 1

    @BeforeClass
    @JvmStatic
    fun waitForValidConfiguration() {
      Assume.assumeTrue(
        "Telemetry backend integration tests disabled via " +
          "-Pmapbox.runTelemetryIntegrationTests=false",
        BuildConfig.RUN_TELEMETRY_INTEGRATION_TESTS
      )
      val configurationUpdateLatch = CountDownLatch(1)
      val configurationLatch = CountDownLatch(1)
      var configurationResult: Expected<ConfigurationServiceError, ConfigurationOptions>? = null
      val configurationService = ConfigurationService.getOrCreate(options)
      val configurationObserver = object : ConfigurationServiceObserver {
        override fun didStartUpdate() = Unit

        override fun didUpdate(options: ConfigurationOptions) {
          configurationUpdateLatch.countDown()
        }

        override fun didEncounterError(error: ConfigurationServiceError) {
          Log.d(TAG, "configuration didEncounterError() called with: error = $error")
        }
      }
      configurationService.registerObserver(configurationObserver)
      configurationService.getConfig {
        configurationResult = it
        configurationLatch.countDown()
      }

      Assert.assertTrue(configurationLatch.await(10_000L, TimeUnit.MILLISECONDS))
      if (configurationResult!!.isError) {
        // If there's no cached configuration yet, wait for the first update before sending events.
        Assert.assertTrue(configurationUpdateLatch.await(30_000L, TimeUnit.MILLISECONDS))
      }
      configurationService.unregisterObserver(configurationObserver)
    }
  }
}