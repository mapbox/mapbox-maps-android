package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.util.Pair
import android.view.Gravity
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.mapbox.maps.plugin.DistanceUnits
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
  private val scaleBarSettings = ScaleBarSettings {
    enabled = true
    position = Gravity.TOP or Gravity.START
    marginLeft = 4f
    marginTop = 4f
    marginRight = 4f
    marginBottom = 4f
    textColor = Color.BLACK
    primaryColor = Color.BLACK
    secondaryColor = Color.WHITE
    borderWidth = 2f
    height = 2f
    textBarMargin = 8f
    textBorderWidth = 2f
    textSize = 8f
    distanceUnits = DistanceUnits.METRIC
    refreshInterval = 15
    showTextBorder = true
    ratio = 0.5f
  }

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
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
    scaleBarView.settings = scaleBarSettings.toBuilder().setDistanceUnits(DistanceUnits.IMPERIAL).build()
    assertEquals(imperialTable, scaleBarView.scaleTable)
    scaleBarView.settings = scaleBarSettings.toBuilder().setDistanceUnits(DistanceUnits.NAUTICAL).build()
    assertEquals(nauticalTable, scaleBarView.scaleTable)
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
  fun useContinuousRendering() {
    assertEquals(false, scaleBarView.useContinuousRendering)
    scaleBarView.useContinuousRendering = true
    assertEquals(true, scaleBarView.useContinuousRendering)

    scaleBarView.settings = ScaleBarSettings { useContinuousRendering = false }
    assertEquals(false, scaleBarView.useContinuousRendering)
  }

  @Test
  @LooperMode(LooperMode.Mode.PAUSED)
  fun renderingOnDemandTest() {
    scaleBarView.useContinuousRendering = false
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    scaleBarView.distancePerPixel = 1.0f
    assertTrue(scaleBarView.refreshHandlerHasMessages(MSG_RENDER_ON_DEMAND))
    assertFalse(scaleBarView.refreshHandlerHasMessages(MSG_RENDER_CONTINUOUS))
    Shadows.shadowOf(Looper.getMainLooper()).idle()
  }

  @Test
  @LooperMode(LooperMode.Mode.PAUSED)
  fun renderingContinuousTest() {
    scaleBarView.useContinuousRendering = true
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    scaleBarView.distancePerPixel = 1.0f
    assertFalse(scaleBarView.refreshHandlerHasMessages(MSG_RENDER_ON_DEMAND))
    assertTrue(scaleBarView.refreshHandlerHasMessages(MSG_RENDER_CONTINUOUS))
    Shadows.shadowOf(Looper.getMainLooper()).idle()
  }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
class ScaleBarImplGetDistanceTextTest(
  private val units: DistanceUnits,
  private val distance: Float,
  private val expectedDistanceText: String
) {
  private lateinit var scaleBarView: ScaleBarImpl

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    scaleBarView = ScaleBarImpl(context)
    val layoutParams = mockk<FrameLayout.LayoutParams>(relaxed = true)
    scaleBarView.layoutParams = layoutParams
  }

  private companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "units: {0}, distance: {1}")
    fun data() = listOf(
      arrayOf(DistanceUnits.METRIC, 0F, "0"),
      arrayOf(DistanceUnits.METRIC, 0.1F, "0.1 m"),
      arrayOf(DistanceUnits.METRIC, 0.5F, "0.5 m"),
      arrayOf(DistanceUnits.METRIC, 100F, "100 m"),
      arrayOf(DistanceUnits.METRIC, 999F, "999 m"),
      arrayOf(DistanceUnits.METRIC, 1000F, "1 km"),
      arrayOf(DistanceUnits.METRIC, 1001F, "1 km"),
      arrayOf(DistanceUnits.METRIC, 4444F, "4.4 km"),
      arrayOf(DistanceUnits.METRIC, 5555F, "5.6 km"),
      arrayOf(DistanceUnits.METRIC, 10000F, "10 km"),
      arrayOf(DistanceUnits.IMPERIAL, 0F, "0"),
      arrayOf(DistanceUnits.IMPERIAL, 0.1F, "0.1 ft"),
      arrayOf(DistanceUnits.IMPERIAL, 0.5F, "0.5 ft"),
      arrayOf(DistanceUnits.IMPERIAL, 100F, "100 ft"),
      arrayOf(DistanceUnits.IMPERIAL, 5279F, "1 mi"),
      arrayOf(DistanceUnits.IMPERIAL, 5280F, "1 mi"),
      arrayOf(DistanceUnits.IMPERIAL, 6000F, "1.1 mi"),
      arrayOf(DistanceUnits.IMPERIAL, 10000F, "1.9 mi"),
      arrayOf(DistanceUnits.IMPERIAL, 10560F, "2 mi"),
      arrayOf(DistanceUnits.IMPERIAL, 52800F, "10 mi"),
      arrayOf(DistanceUnits.NAUTICAL, 0F, "0"),
      arrayOf(DistanceUnits.NAUTICAL, 0.6F, "0.1 fth"),
      arrayOf(DistanceUnits.NAUTICAL, 3F, "0.5 fth"),
      arrayOf(DistanceUnits.NAUTICAL, 600F, "100 fth"),
      arrayOf(DistanceUnits.NAUTICAL, 6075F, "1 nmi"),
      arrayOf(DistanceUnits.NAUTICAL, 6076.12F, "1 nmi"),
      arrayOf(DistanceUnits.NAUTICAL, 6685F, "1.1 nmi"),
      arrayOf(DistanceUnits.NAUTICAL, 11545F, "1.9 nmi"),
      arrayOf(DistanceUnits.NAUTICAL, 12152.24F, "2 nmi"),
      arrayOf(DistanceUnits.NAUTICAL, 60761.2F, "10 nmi"),
      )
  }

  @Test
  fun getDistanceText() {
    val unit = when (units) {
      DistanceUnits.METRIC -> METER_UNIT
      DistanceUnits.IMPERIAL -> FEET_UNIT
      DistanceUnits.NAUTICAL -> FATHOM_UNIT
      else -> METER_UNIT
    }
    assertEquals(expectedDistanceText, scaleBarView.getDistanceText(distance, unit))
  }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
class ScaleBarImplScaleBarSegmentsTest(
  private val units: DistanceUnits,
  private val scaleTable: List<Pair<Int, Int>>,
) {
  private lateinit var scaleBarView: ScaleBarImpl

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    scaleBarView = ScaleBarImpl(context)
    val layoutParams = mockk<FrameLayout.LayoutParams>(relaxed = true)
    scaleBarView.layoutParams = layoutParams
  }

  private companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "units: {0}")
    fun data() = listOf(
      arrayOf(DistanceUnits.METRIC, metricTable),
      arrayOf(DistanceUnits.IMPERIAL, imperialTable),
      arrayOf(DistanceUnits.NAUTICAL, nauticalTable),
    )
  }

  @Test
  fun `verify below and above scale bar segments`() {
    scaleBarView.settings = scaleBarView.settings.toBuilder().setDistanceUnits(units).build()
    scaleBarView.distancePerPixel = 0.01F
    val unit = when (units) {
      DistanceUnits.METRIC -> METER_UNIT
      DistanceUnits.IMPERIAL -> FEET_UNIT
      DistanceUnits.NAUTICAL -> FATHOM_UNIT
      else -> METER_UNIT
    }
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