package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.util.Pair
import android.view.Gravity
import android.widget.FrameLayout
import com.mapbox.maps.plugin.scalebar.ScaleBarImpl.Companion.MSG_RENDER_CONTINUOUS
import com.mapbox.maps.plugin.scalebar.ScaleBarImpl.Companion.MSG_RENDER_ON_DEMAND
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
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
    val layoutParams = mockk<FrameLayout.LayoutParams>(relaxed = true)
    scaleBarView.layoutParams = layoutParams
    scaleBarView.settings = scaleBarSettings
  }

  @Test
  fun calculateWidthAndHeight() {
    scaleBarView.pixelRatio = 2.0f
    scaleBarView.mapViewWidth = 100f
    val viewSize = scaleBarView.calculateWidthAndHeight()
    // 50 + ScaleBarImpl.DEFAULT_PIXEL_RATIO * 2
    assertEquals(70.0f, viewSize.first)
    assertEquals(22.0f, viewSize.second)
  }

  @Test
  fun pixelRatio() {
    assertEquals(ScaleBarImpl.DEFAULT_PIXEL_RATIO, scaleBarView.pixelRatio)
    scaleBarView.pixelRatio = 2.0f
    assertEquals(2.0f, scaleBarView.pixelRatio)
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

@RunWith(ParameterizedRobolectricTestRunner::class)
class ScaleBarImplGetDistanceTextTest(
  private val isMetricUnits: Boolean,
  private val distance: Float,
  private val expectedDistanceText: String
) {
  private lateinit var scaleBarView: ScaleBarImpl
  private val context: Context = mockk(relaxed = true)

  @Before
  fun setUp() {
    scaleBarView = ScaleBarImpl(context)
    val layoutParams = mockk<FrameLayout.LayoutParams>(relaxed = true)
    scaleBarView.layoutParams = layoutParams
  }

  private companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "isMetric: {0}, distance: {1}")
    fun data() = listOf(
      arrayOf(true, 0F, "0"),
      arrayOf(true, 0.1F, "0.1 m"),
      arrayOf(true, 0.5F, "0.5 m"),
      arrayOf(true, 100F, "100 m"),
      arrayOf(true, 999F, "999 m"),
      arrayOf(true, 1000F, "1 km"),
      arrayOf(true, 1001F, "1 km"),
      arrayOf(true, 4444F, "4.4 km"),
      arrayOf(true, 5555F, "5.6 km"),
      arrayOf(true, 10000F, "10 km"),
      arrayOf(false, 0F, "0"),
      arrayOf(false, 0.1F, "0.1 ft"),
      arrayOf(false, 0.5F, "0.5 ft"),
      arrayOf(false, 100F, "100 ft"),
      arrayOf(false, 5279F, "5279 ft"),
      arrayOf(false, 5280F, "1 mi"),
      arrayOf(false, 6000F, "1.1 mi"),
      arrayOf(false, 10000F, "1.9 mi"),
      arrayOf(false, 10560F, "2 mi"),
      arrayOf(false, 52800F, "10 mi"),
    )
  }

  @Test
  fun getDistanceText() {
    val unit = if (isMetricUnits) METER_UNIT else FEET_UNIT
    assertEquals(expectedDistanceText, scaleBarView.getDistanceText(distance, unit))
  }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
class ScaleBarImplScaleBarSegmentsTest(
  private val isMetricUnits: Boolean,
  private val scaleTable: List<Pair<Int, Int>>,
) {
  private lateinit var scaleBarView: ScaleBarImpl
  private val context: Context = mockk(relaxed = true)

  @Before
  fun setUp() {
    scaleBarView = ScaleBarImpl(context)
    val layoutParams = mockk<FrameLayout.LayoutParams>(relaxed = true)
    scaleBarView.layoutParams = layoutParams
  }

  private companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "isMetric: {0}")
    fun data() = listOf(
      arrayOf(true, metricTable),
      arrayOf(false, imperialTable),
    )
  }

  @Test
  fun `verify below and above scale bar segments`() {
    scaleBarView.settings = scaleBarView.settings.copy(isMetricUnits = isMetricUnits)
    scaleBarView.distancePerPixel = 0.01F
    val unit = if (isMetricUnits) METER_UNIT else FEET_UNIT
    // Special case where max distance is smaller than the first entry segment in the table
    val belowFirstUnitBarDistance = (scaleTable[0].first.toFloat() / scaleTable[0].second) - 0.1F
    val result =
      scaleBarView.calculateSegmentsConfiguration(
        belowFirstUnitBarDistance,
        scaleBarView.distancePerPixel,
        scaleBarView.scaleTable,
        scaleBarView.textPaint,
        scaleBarView.strokePaint.strokeWidth,
        METER_UNIT,
        Int.MAX_VALUE
      )
    assertEquals(belowFirstUnitBarDistance, result.unitDistance, 0.01F)
    assertEquals(1, result.rectCount)

    for (index in 1 until scaleTable.size) {
      // We need to adapt the distancePerPixel to the scale entry we're targeting
      if (index > 20) scaleBarView.distancePerPixel = 10.0F
      val pair: Pair<Int, Int> = scaleTable[index]
      val distance = pair.first.toFloat()
      val rectCount = pair.second

      val maxDistanceBelowCurrent = distance - if (distance < 100) 0.1F else 10F
      val resultBelow = scaleBarView.calculateSegmentsConfiguration(
        maxDistanceBelowCurrent,
        scaleBarView.distancePerPixel,
        scaleBarView.scaleTable,
        scaleBarView.textPaint,
        scaleBarView.strokePaint.strokeWidth,
        unit,
        Int.MAX_VALUE
      )
      val previousEntry = scaleTable[index - 1]
      val expectedUnitDistance = previousEntry.first.toFloat() / previousEntry.second
      assertEquals(expectedUnitDistance, resultBelow.unitDistance, 0.01F)
      val expectedSegments = previousEntry.second
      assertEquals(expectedSegments, resultBelow.rectCount)

      val maxDistanceAboveCurrent = distance + if (distance < 100) 0.1F else 10F
      val resultAbove = scaleBarView.calculateSegmentsConfiguration(
        maxDistanceAboveCurrent,
        scaleBarView.distancePerPixel,
        scaleBarView.scaleTable,
        scaleBarView.textPaint,
        scaleBarView.strokePaint.strokeWidth,
        unit,
        Int.MAX_VALUE
      )
      assertEquals("Failed for entry $index", distance / rectCount, resultAbove.unitDistance, 0.01F)
      assertEquals(rectCount, resultAbove.rectCount)
    }
  }
}