package com.mapbox.maps.module.telemetry

import android.content.Context
import android.telephony.TelephonyManager
import android.view.Display
import android.view.WindowManager
import com.mapbox.common.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Ignore
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
  private lateinit var eventsService: EventsServiceInterface
  private lateinit var telemetryService: TelemetryService

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

    eventsService = mockk()
    telemetryService = mockk()
    telemetry = MapTelemetryImpl(context, "sk.foobar", eventsService, telemetryService)
    every { eventsService.sendEvent(any(), any()) } returns Unit
    mockkStatic(EventsService::class)
    mockkStatic(TelemetryUtils::class)
    every { TelemetryUtils.setEventsCollectionState(any(), any()) } returns Unit
  }

  @Test
  fun testSetUserTelemetryRequestStateEnabled() {
    telemetry.setUserTelemetryRequestState(true)
    verify { TelemetryUtils.setEventsCollectionState(true, any()) }
  }

  @Test
  fun testSetUserTelemetryRequestStateDisabled() {
    telemetry.setUserTelemetryRequestState(false)
    verify { TelemetryUtils.setEventsCollectionState(false, any()) }
  }

  @Test
  fun testDisableTelemetrySession() {
    telemetry.disableTelemetrySession()
    verify { TelemetryUtils.setEventsCollectionState(false, any()) }
  }

  @Test
  fun testSetSessionIdRotationInterval() {
    assertFalse(telemetry.setSessionIdRotationInterval(22))
  }

  @Test
  @Ignore("testSetDebugLoggingEnabled has been deprecated")
  fun testSetDebugLoggingEnabled() {
    // no-ops
  }

  @Test
  @Ignore("testSetDebugLoggingEnabled has been deprecated")
  fun testSetDebugLoggingDisabled() {
    // no-ops
  }

  @Test
  fun testPerformanceEvent() {
    telemetry.onPerformanceEvent(null)
    verify { eventsService.sendEvent(any(), any()) }
  }
}