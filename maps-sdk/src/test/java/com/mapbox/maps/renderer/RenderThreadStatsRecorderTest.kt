package com.mapbox.maps.renderer

import com.mapbox.maps.MapboxExperimental
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class RenderThreadStatsRecorderTest {

  @Test
  fun endSumsAllThreeNewFieldsAcrossCalls() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    recorder.addFrameStats(1.0, 2, 1, 3)
    recorder.addFrameStats(1.0, 1, 0, 2)
    val stats = recorder.end()

    assertEquals(3L, stats.totalSkippedVsync)
    assertEquals(1L, stats.pacedSkippedVsync)
    assertEquals(5L, stats.missedMapRenderFrames)
  }

  @Test
  fun secondSessionStartsFromZero() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    recorder.addFrameStats(1.0, 5, 4, 3)
    recorder.end()

    recorder.start()
    recorder.addFrameStats(1.0, 1, 1, 1)
    val secondStats = recorder.end()

    assertEquals(1L, secondStats.totalSkippedVsync)
    assertEquals(1L, secondStats.pacedSkippedVsync)
    assertEquals(1L, secondStats.missedMapRenderFrames)
  }

  @Test
  fun endWithNoFramesRecordedReturnsZeroedStats() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    val stats = recorder.end()

    assertEquals(0L, stats.totalSkippedVsync)
    assertEquals(0L, stats.pacedSkippedVsync)
    assertEquals(0L, stats.missedMapRenderFrames)
    assertEquals(0L, stats.totalFrames)
  }

  @Test
  @Suppress("DEPRECATION")
  fun deprecatedTotalDroppedFramesMirrorsTotalSkippedVsync() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    recorder.addFrameStats(1.0, 4, 2, 1)
    val stats = recorder.end()

    assertEquals(stats.totalSkippedVsync, stats.totalDroppedFrames)
  }
}