package com.mapbox.maps.module.telemetry

import android.content.Context
import android.telephony.TelephonyManager
import android.view.Display
import android.view.WindowManager
import com.mapbox.android.telemetry.Event
import com.mapbox.android.telemetry.MapboxTelemetry
import com.mapbox.common.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
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
  ) {}

  @Implementation
  open fun getEventsCollectionState(): Boolean {
    return true
  }
}

@Config(shadows = [ShadowEventsService::class])
@RunWith(RobolectricTestRunner::class)
class MapTelemetryTest {

  private lateinit var mapboxTelemetry: MapboxTelemetry
  private lateinit var telemetry: MapTelemetryImpl
  private lateinit var eventsService: EventsServiceInterface

  @Before
  fun setUp() {
    val context = mockk<Context>(relaxed = true)
    val telephonyManager = mockk<TelephonyManager>()
    every { context.getSystemService(Context.TELEPHONY_SERVICE) } returns telephonyManager
    every { telephonyManager.networkType } returns TelephonyManager.NETWORK_TYPE_GPRS
    every { telephonyManager.networkOperatorName } returns "foobar"

    val windowManager = mockk<WindowManager>(relaxUnitFun = true)
    every { context.getSystemService(Context.WINDOW_SERVICE) } returns windowManager
    val display = mockk<Display>(relaxUnitFun = true)
    every { windowManager.defaultDisplay } returns display

    mapboxTelemetry = mockk(relaxed = true)
    eventsService = mockk<EventsServiceInterface>()

    val eventsService: EventsServiceInterface = mockk()
    telemetry = MapTelemetryImpl(mapboxTelemetry, context, "sk.foobar", eventsService)
    every { eventsService.sendEvent(any(), any()) } returns Unit
    every { eventsService.pauseEventsCollection() } returns Unit
    mockkStatic(EventsService::class)
    every { EventsService.setEventsCollectionState(any(), any()) } returns Unit
  }

  private var eventsServiceMock = object : EventsServiceInterface {
    override fun registerObserver(observer: EventsServiceObserver) {}

    override fun unregisterObserver(observer: EventsServiceObserver) {}

    override fun sendTurnstileEvent(
      turnstileEvent: TurnstileEvent,
      callback: EventsServiceResponseCallback?
    ) {}

    override fun sendEvent(
      event: com.mapbox.common.Event,
      callback: EventsServiceResponseCallback?
    ) {}

    override fun pauseEventsCollection() {}

    override fun resumeEventsCollection() {}
  }

  @Test
  fun testSetUserTelemetryRequestStateEnabled() {
    telemetry.setUserTelemetryRequestState(true)
    verify { mapboxTelemetry.enable() }
  }

  @Test
  fun testSetUserTelemetryRequestStateDisabled() {
    telemetry.setUserTelemetryRequestState(false)
    verify { mapboxTelemetry.disable() }
  }

  @Test
  fun testDisableTelemetrySession() {
    telemetry.disableTelemetrySession()
    verify { mapboxTelemetry.disable() }
  }

  @Test
  fun testSetSessionIdRotationInterval() {
    telemetry.setSessionIdRotationInterval(22)
    verify { mapboxTelemetry.updateSessionIdRotationInterval(any()) }
  }

  @Test
  fun testSetDebugLoggingEnabled() {
    telemetry.setDebugLoggingEnabled(true)
    verify { mapboxTelemetry.updateDebugLoggingEnabled(true) }
  }

  @Test
  fun testSetDebugLoggingDisabled() {
    telemetry.setDebugLoggingEnabled(false)
    verify { mapboxTelemetry.updateDebugLoggingEnabled(false) }
  }

  @Test
  fun testPerformanceEvent() {
    telemetry.onPerformanceEvent(null)
    verify { mapboxTelemetry.push(any()) }
  }
}