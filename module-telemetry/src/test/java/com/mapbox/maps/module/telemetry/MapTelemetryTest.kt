package com.mapbox.maps.module.telemetry

import android.content.Context
import android.telephony.TelephonyManager
import android.view.Display
import android.view.WindowManager
import com.mapbox.android.telemetry.MapboxTelemetry
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MapTelemetryTest {

  private lateinit var mapboxTelemetry: MapboxTelemetry
  private lateinit var telemetry: MapTelemetryImpl

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
    telemetry = MapTelemetryImpl(mapboxTelemetry, context, "sk.foobar")
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