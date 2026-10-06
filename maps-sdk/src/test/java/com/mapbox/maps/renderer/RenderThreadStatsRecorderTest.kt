package com.mapbox.maps.renderer

import com.mapbox.maps.MapboxExperimental
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class RenderThreadStatsRecorderTest {

  @Test
  fun endSumsAllThreeNewFieldsAcrossCalls() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    recorder.addFrameStats(1.0, 2, 1, 3)
    recorder.addFrameStats(1.0, 1, 0, 2)
    val stats = recorder.stop()

    assertEquals(3L, stats.totalSkippedVsync)
    assertEquals(1L, stats.pacedSkippedVsync)
    assertEquals(5L, stats.missedMapRenderFrames)
  }

  @Test
  fun secondSessionStartsFromZero() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    recorder.addFrameStats(1.0, 5, 4, 3)
    recorder.stop()

    recorder.start()
    recorder.addFrameStats(1.0, 1, 1, 1)
    val secondStats = recorder.stop()

    assertEquals(1L, secondStats.totalSkippedVsync)
    assertEquals(1L, secondStats.pacedSkippedVsync)
    assertEquals(1L, secondStats.missedMapRenderFrames)
  }

  @Test
  fun endWithNoFramesRecordedReturnsZeroedStats() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    val stats = recorder.stop()

    assertEquals(0L, stats.totalSkippedVsync)
    assertEquals(0L, stats.pacedSkippedVsync)
    assertEquals(0L, stats.missedMapRenderFrames)
    assertEquals(0L, stats.totalFrames)
  }

  @Test
  fun frameStatsAddedWhileNotRecordingAreIgnored() {
    val recorder = RenderThreadStatsRecorder()
    recorder.addFrameStats(1.0, 1, 1, 1)
    recorder.start()
    recorder.addFrameStats(2.0, 2, 2, 2)
    recorder.stop()
    recorder.addFrameStats(3.0, 3, 3, 3)

    recorder.start()
    val stats = recorder.stop()

    assertEquals(0L, stats.totalFrames)
    assertEquals(emptyList<Double>(), stats.frameTimeList)
  }

  @Test(timeout = 30_000)
  fun concurrentAddFrameStatsWhileStartingAndEndingProducesConsistentSnapshots() {
    val recorder = RenderThreadStatsRecorder()
    val stop = AtomicBoolean(false)
    val writerError = AtomicReference<Throwable?>(null)
    val writerStarted = CountDownLatch(1)
    // Simulates the render thread feeding frames while the user thread starts and stops sessions.
    val writer = thread(name = "fake-render-thread") {
      writerStarted.countDown()
      try {
        while (!stop.get()) {
          recorder.addFrameStats(1.0, 1, 1, 1)
          // A real render thread calls addFrameStats once per frame. Yield so this loop does not
          // keep winning the non-fair lock against start() and stop().
          Thread.yield()
        }
      } catch (t: Throwable) {
        writerError.set(t)
      }
    }

    writerStarted.await()
    var totalRecordedFrames = 0L
    try {
      repeat(SESSIONS) {
        recorder.start()
        Thread.yield()
        val stats = recorder.stop()
        // Every addFrameStats call bumps all counters and the list together, so a consistent
        // snapshot must have them all equal.
        val frames = stats.frameTimeList.size.toLong()
        assertEquals(frames, stats.totalSkippedVsync)
        assertEquals(frames, stats.pacedSkippedVsync)
        assertEquals(frames, stats.missedMapRenderFrames)
        assertEquals(frames * 2, stats.totalFrames)
        totalRecordedFrames += frames
      }
    } finally {
      stop.set(true)
      writer.join()
    }

    assertNull(writerError.get())
    // Sanity check that the writer actually overlapped with the sessions.
    assertTrue(totalRecordedFrames > 0)
  }

  @Test(timeout = 10_000)
  fun addFrameStatsDoesNotWaitWhileAnotherThreadHoldsTheLock() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    val lockHeld = CountDownLatch(1)
    val release = CountDownLatch(1)
    // ReentrantLock is reentrant, so the lock must be held from a different thread than the caller.
    val holder = thread(name = "lock-holder") {
      recorder.lock.lock()
      try {
        lockHeld.countDown()
        release.await()
      } finally {
        recorder.lock.unlock()
      }
    }

    try {
      assertTrue(lockHeld.await(5, TimeUnit.SECONDS))
      // Must return right away instead of waiting for the holder. If it waited, the test would
      // hit its timeout because the holder is only released below.
      recorder.addFrameStats(1.0, 1, 1, 1)
    } finally {
      release.countDown()
      holder.join()
    }

    val stats = recorder.stop()
    assertEquals(0L, stats.totalFrames)
    assertEquals(emptyList<Double>(), stats.frameTimeList)
  }

  @Test
  fun nextSessionStillRecordsAfterLongSession() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    repeat(100) { recorder.addFrameStats(it.toDouble(), 0, 0, 0) }
    assertEquals(100, recorder.stop().frameTimeList.size)

    recorder.start()
    recorder.addFrameStats(1.0, 0, 0, 0)
    assertEquals(listOf(1.0), recorder.stop().frameTimeList)
  }

  @Test
  fun frameTimesKeepInsertionOrderAcrossGrowthAndPercentilesAreComputed() {
    val recorder = RenderThreadStatsRecorder()
    // More frames than the pre-size cap and the minimum growth capacity, so the array grows.
    val frameTimes = (1..1_000).map { it.toDouble() }.shuffled(java.util.Random(42))
    recorder.start()
    frameTimes.forEach { recorder.addFrameStats(it, 0, 0, 0) }
    val stats = recorder.stop()

    assertEquals(frameTimes, stats.frameTimeList)
    assertEquals(1_000L, stats.totalFrames)
    assertEquals(500.0, stats.percentile50!!, 0.0)
    assertEquals(900.0, stats.percentile90!!, 0.0)
    assertEquals(950.0, stats.percentile95!!, 0.0)
    assertEquals(990.0, stats.percentile99!!, 0.0)
  }

  @Test
  fun endWithNoFramesRecordedReturnsNullPercentiles() {
    val recorder = RenderThreadStatsRecorder()
    recorder.start()
    val stats = recorder.stop()

    assertNull(stats.percentile50)
    assertNull(stats.percentile99)
  }

  private companion object {
    const val SESSIONS = 2_000
  }
}