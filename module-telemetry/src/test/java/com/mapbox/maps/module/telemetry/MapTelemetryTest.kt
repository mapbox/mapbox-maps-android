package com.mapbox.maps.module.telemetry

import android.content.Context
import android.telephony.TelephonyManager
import android.view.Display
import android.view.WindowManager
import com.mapbox.common.*
import com.mapbox.maps.base.BuildConfig
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(EventsService::class)
object ShadowEventsService {
  @Implementation
  open fun setEventsCollectionState(
    enableCollection: Boolean,
    callback: EventsServiceResponseCallback?
  ) {
  }

  @Implementation
  open fun getEventsCollectionState(): Boolean {
    return true
  }
}

@Implements(TelemetryService::class)
object ShadowTelemetryService

@Implements(TelemetryUtils::class)
object ShadowTelemetryUtils {
  @Implementation
  fun setEventsCollectionState(
    enableCollection: Boolean,
    callback: TelemetryUtilsResponseCallback?
  ) {
  }

  @Implementation
  fun getEventsCollectionState(): Boolean {
    return true
  }

  @Implementation
  fun getUserID(): String = ""
}

@Config(shadows = [ShadowEventsService::class, ShadowTelemetryService::class, ShadowTelemetryUtils::class])
@RunWith(RobolectricTestRunner::class)
class MapTelemetryTest {

  private lateinit var telemetry: MapTelemetryImpl
  private val eventsService: EventsService = mockk()
  private val telemetryService: TelemetryService = mockk()
  private val context = mockk<Context>(relaxed = true)
  private val telephonyManager = mockk<TelephonyManager>()
  private val windowManager = mockk<WindowManager>(relaxUnitFun = true)
  private val display = mockk<Display>(relaxUnitFun = true)

  private val turnstileEvent = TurnstileEvent(
    UserSKUIdentifier.MAPS_MAUS,
    BuildConfig.MAPBOX_SDK_IDENTIFIER,
    BuildConfig.MAPBOX_SDK_VERSION
  )

  @Before
  fun setUp() {
    mockkStatic(EventsService::class)
    mockkStatic(TelemetryService::class)
    mockkStatic(TelemetryUtils::class)
    every { TelemetryUtils.setEventsCollectionState(any(), any()) } returns Unit
    every { TelemetryUtils.getEventsCollectionState() } returns true
    every { TelemetryUtils.getClientServerEventsCollectionState(any()) } returns TelemetryCollectionState.ENABLED

    every { EventsService.getOrCreate(any()) } returns eventsService
    every { eventsService.sendEvent(any(), any()) } returns Unit
    every { eventsService.sendTurnstileEvent(any(), any()) } returns Unit

    every { TelemetryService.getOrCreate(any()) } returns telemetryService

    every { context.getSystemService(Context.TELEPHONY_SERVICE) } returns telephonyManager
    every { telephonyManager.networkType } returns TelephonyManager.NETWORK_TYPE_GPRS
    every { telephonyManager.networkOperatorName } returns "foobar"

    every { context.getSystemService(Context.WINDOW_SERVICE) } returns windowManager
    every { windowManager.defaultDisplay } returns display

    telemetry = MapTelemetryImpl(context, "sk.foobar")
  }

  @After
  fun cleanUp() {
    unmockkStatic(EventsService::class)
    unmockkStatic(TelemetryService::class)
    unmockkStatic(TelemetryUtils::class)
    unmockkAll()
  }

  @Test
  fun testConstructorInitialisation() {
    // validate the event service is initialised
    verify { EventsService.getOrCreate(any()) }
    // validate the telemetry service is initialised
    verify { TelemetryService.getOrCreate(any()) }
  }

  @Test
  fun testOnAppUserTurnstileEvent() {
    telemetry.onAppUserTurnstileEvent()
    verify {
      eventsService.sendTurnstileEvent(turnstileEvent, any())
    }
    val slot = slot<Event>()
    verify { eventsService.sendEvent(capture(slot), any()) }
    assertEquals(EventPriority.QUEUED, slot.captured.priority)
    assertTrue(slot.captured.attributes.toJson().contains("\"event\":\"map.load\""))
    assertTrue(
      slot.captured.attributes.toJson().contains("\"sdkIdentifier\":\"mapbox-maps-android\"")
    )
  }

  @Test
  fun testOnAppUserTurnstileStateTurnstileOnly() {
    every { TelemetryUtils.getClientServerEventsCollectionState(any()) } returns TelemetryCollectionState.TURNSTILE_EVENTS_ONLY
    telemetry.onAppUserTurnstileEvent()
    verify {
      eventsService.sendTurnstileEvent(turnstileEvent, any())
    }
    verify(exactly = 0) { eventsService.sendEvent(any(), any()) }
  }

  @Test
  fun testOnAppUserTurnstileStateUnknown() {
    every { TelemetryUtils.getClientServerEventsCollectionState(any()) } returns TelemetryCollectionState.UNKNOWN
    telemetry.onAppUserTurnstileEvent()
    verify {
      eventsService.sendTurnstileEvent(
        turnstileEvent, any()
      )
    }
    verify(exactly = 1) { eventsService.sendEvent(any(), any()) }
  }

  @Test
  fun testSetUserTelemetryRequestStateEnabled() {
    telemetry.userTelemetryRequestState = true
    verify { TelemetryUtils.setEventsCollectionState(true, any()) }
  }

  @Test
  fun testSetUserTelemetryRequestStateDisabled() {
    telemetry.userTelemetryRequestState = false
    verify { TelemetryUtils.setEventsCollectionState(false, any()) }
  }

  @Test
  fun testDisableTelemetrySession() {
    telemetry.disableTelemetrySession()
    verify { TelemetryUtils.setEventsCollectionState(false, any()) }
  }

  @Test
  fun testPerformanceEvent() {
    telemetry.onPerformanceEvent(null)
    val slot = slot<Event>()
    verify { eventsService.sendEvent(capture(slot), any()) }
    assertEquals(EventPriority.QUEUED, slot.captured.priority)
    assertTrue(slot.captured.attributes.toJson().contains("\"event\":\"mobile.performance_trace\""))
  }

  @Test
  fun testPerformanceEventDisabledStateTurnstileOnly() {
    every { TelemetryUtils.getClientServerEventsCollectionState(any()) } returns TelemetryCollectionState.TURNSTILE_EVENTS_ONLY
    telemetry.onPerformanceEvent(null)
    verify(exactly = 0) { eventsService.sendEvent(any(), any()) }
  }

  @Test
  fun testPerformanceEventDisabledStateUnknown() {
    every { TelemetryUtils.getClientServerEventsCollectionState(any()) } returns TelemetryCollectionState.UNKNOWN
    telemetry.onPerformanceEvent(null)
    verify(exactly = 1) { eventsService.sendEvent(any(), any()) }
  }
}