package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.Gravity
import com.mapbox.maps.plugin.scalebar.ScaleBarImpl.Companion.MSG_RENDER_CONTINUOUS
import com.mapbox.maps.plugin.scalebar.ScaleBarImpl.Companion.MSG_RENDER_ON_DEMAND
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowProjection::class])
class ScaleBarImplTest {
  private lateinit var scaleBarView: ScaleBarImpl
  private val context: Context = mockk(relaxed = true)
  private val scaleBarSettings = ScaleBarSettings(
    true, Gravity.TOP or Gravity.START,
    4f, 4f, 4f, 4f, Color.BLACK, Color.BLACK, Color.WHITE,
    2f, 2f, 8f, 2f, 8f, true, 15,
    true, 0.5f
  )

  @Before
  fun setUp() {
    scaleBarView = ScaleBarImpl(context)
    scaleBarView.settings = scaleBarSettings
  }

  @Test
  fun scaleTable() {
    assertEquals(metricTable, scaleBarView.scaleTable)
    scaleBarSettings.isMetricUnits = false
    scaleBarView.settings = scaleBarSettings
    assertEquals(imperialTable, scaleBarView.scaleTable)
  }

  @Test
  fun textPain() {
    assertEquals(Color.BLACK, scaleBarView.textPaint.color)
    assertEquals(8f, scaleBarView.textPaint.textSize)
  }

  @Test
  fun barPaint() {
    assertTrue(scaleBarView.barPaint.isAntiAlias)
  }

  @Test
  fun strokePaint() {
    assertEquals(2f, scaleBarView.strokePaint.strokeWidth)
    assertEquals(8f, scaleBarView.strokePaint.textSize)
  }

  @Test
  fun maxBarWidth() {
    assertEquals(0f, scaleBarView.mapViewWidth)
    assertEquals(0f, scaleBarView.maxBarWidth)
    scaleBarView.mapViewWidth = 100f
    assertEquals(46f, scaleBarView.maxBarWidth)
    scaleBarSettings.ratio = 1.0f
    scaleBarView.settings = scaleBarSettings
    assertEquals(96f, scaleBarView.maxBarWidth)
    scaleBarSettings.ratio = 0.5f
  }

  @Test
  fun enabled() {
    assertEquals(true, scaleBarView.enable)
    scaleBarView.enable = false
    assertEquals(false, scaleBarView.enable)
    scaleBarView.enable = true
    assertEquals(true, scaleBarView.enable)
  }

  @Test
  fun distancePerPixel() {
    assertEquals(0.0f, scaleBarView.distancePerPixel)
    scaleBarView.distancePerPixel = 1.0f
    assertEquals(1.0f, scaleBarView.distancePerPixel)
  }

  @Test
  fun getDistanceText() {
    assertEquals("0", scaleBarView.getDistanceText(0))
    assertEquals("100 m", scaleBarView.getDistanceText(100))
    assertEquals("999 m", scaleBarView.getDistanceText(999))
    assertEquals("1 km", scaleBarView.getDistanceText(1000))
    assertEquals("1 km", scaleBarView.getDistanceText(1001))
    assertEquals("4.4 km", scaleBarView.getDistanceText(4444))
    assertEquals("5.6 km", scaleBarView.getDistanceText(5555))
    assertEquals("10 km", scaleBarView.getDistanceText(10000))

    scaleBarSettings.isMetricUnits = false
    scaleBarView.settings = scaleBarSettings
    assertEquals("0", scaleBarView.getDistanceText(0))
    assertEquals("100 ft", scaleBarView.getDistanceText(100))
    assertEquals("5279 ft", scaleBarView.getDistanceText(5279))
    assertEquals("1 mi", scaleBarView.getDistanceText(5280))
    assertEquals("1.1 mi", scaleBarView.getDistanceText(6000))
    assertEquals("1.9 mi", scaleBarView.getDistanceText(10000))
    assertEquals("2 mi", scaleBarView.getDistanceText(10560))
    assertEquals("10 mi", scaleBarView.getDistanceText(52800))
  }

  @Test
  @LooperMode(LooperMode.Mode.PAUSED)
  fun renderingOnDemandTest() {
    scaleBarView.useContinuousRendering = false
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    scaleBarView.distancePerPixel = 1.0f
    assertTrue(scaleBarView.refreshHandler.hasMessages(MSG_RENDER_ON_DEMAND))
    assertFalse(scaleBarView.refreshHandler.hasMessages(MSG_RENDER_CONTINUOUS))
    Shadows.shadowOf(Looper.getMainLooper()).idle()
  }

  @Test
  @LooperMode(LooperMode.Mode.PAUSED)
  fun renderingContinuousTest() {
    scaleBarView.useContinuousRendering = true
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    scaleBarView.distancePerPixel = 1.0f
    assertFalse(scaleBarView.refreshHandler.hasMessages(MSG_RENDER_ON_DEMAND))
    assertTrue(scaleBarView.refreshHandler.hasMessages(MSG_RENDER_CONTINUOUS))
    Shadows.shadowOf(Looper.getMainLooper()).idle()
  }
}