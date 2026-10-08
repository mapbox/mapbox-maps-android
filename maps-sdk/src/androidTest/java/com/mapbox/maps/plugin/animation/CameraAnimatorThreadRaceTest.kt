package com.mapbox.maps.plugin.animation

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.animation.animator.CameraTypeEvaluator
import com.mapbox.maps.threading.AnimationThreadController
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

/**
 * Regression tests for the cross-thread animator race in [CameraAnimator] when
 * [AnimationThreadController.useBackgroundThread] is enabled.
 *
 * Android's animator internals can crash if one thread rewrites a running animator while the
 * animator thread is reading it. To avoid that, every mutation of a running [CameraAnimator] must
 * happen on the animator thread.
 *
 * In production, two public entry points can rewrite values from the main thread:
 *  - [CameraAnimator.updateObjectValues], called by `CameraAnimationsPluginImpl` from
 *    `onAnimationStart`;
 *  - [CameraAnimator.setObjectValues], called by the viewport plugin on follow-puck updates.
 *
 * The harness exaggerates both sides of the race: batched writes on the main thread and repeated
 * reads on the animator thread. The reader yields between chunks so posted writes can interleave
 * with reads. Without the fix, the crash shows up quickly.
 */
@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
@LargeTest
class CameraAnimatorThreadRaceTest {

  private val crash = AtomicReference<Pair<String, Throwable>?>(null)
  private var originalHandler: Thread.UncaughtExceptionHandler? = null

  @Before
  fun setUp() {
    originalHandler = Thread.getDefaultUncaughtExceptionHandler()
    // The animator thread has no uncaught-exception handler of its own. Capture failures here
    // instead of letting them kill the instrumentation process.
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
      crash.compareAndSet(null, thread.name to throwable)
    }
    // Start from a fresh MapboxAnimThread. useBackgroundThread() is a no-op if it is already on.
    AnimationThreadController.useMainThread()
    AnimationThreadController.useBackgroundThread()
  }

  @After
  fun tearDown() {
    AnimationThreadController.useMainThread()
    Thread.setDefaultUncaughtExceptionHandler(originalHandler)
  }

  @Test
  fun updateObjectValuesFromMainThreadDoesNotCrashAnimatorThread() {
    val evaluator = RecordingEvaluator()
    // No startValue is provided, so updateObjectValues resolves it from the CameraState below.
    // Changing zoom on every write keeps each value set distinct and prevents canSkip().
    val animator = ZoomAnimator(evaluator, cameraAnimatorOptions(NEAR_TARGET, FAR_TARGET))
    val cameraStates = Array(CAMERA_STATE_POOL) { index ->
      CameraState(
        Point.fromLngLat(-111.0, 45.0),
        EdgeInsets(0.0, 0.0, 0.0, 0.0),
        BASE_ZOOM + index * ZOOM_STEP,
        0.0,
        0.0,
      )
    }
    var cursor = 0
    var current: CameraState = cameraStates[0]
    val startCameraState: () -> CameraState = { current }

    val run = race(animator, evaluator) {
      cursor = (cursor + 1) and (CAMERA_STATE_POOL - 1)
      current = cameraStates[cursor]
      animator.updateObjectValues(startCameraState)
    }

    assertNoAnimatorThreadCrash(run)
    assertHarnessRaced(run)
    assertTrue(
      "Harness never reached the setObjectValues call inside updateObjectValues; result is meaningless.\n$run",
      animator.resolvedCount.get() > 0
    )
  }

  @Test
  fun setObjectValuesFromMainThreadDoesNotCrashAnimatorThread() {
    val evaluator = RecordingEvaluator()
    val animator = ZoomAnimator(evaluator, cameraAnimatorOptions(NEAR_TARGET, FAR_TARGET))
    var cursor = 0

    val run = race(animator, evaluator) {
      cursor = (cursor + 1) and (CAMERA_STATE_POOL - 1)
      // Mirror the viewport follow-puck path: keep rewriting the (start, target) pair.
      animator.setObjectValues(BASE_ZOOM + cursor * ZOOM_STEP, FAR_TARGET)
    }

    assertNoAnimatorThreadCrash(run)
    assertHarnessRaced(run)
  }

  // ---- harness -------------------------------------------------------------------------------

  private class RunResult(
    val writes: Long,
    val reads: Long,
    val readThreads: Set<String>,
    val sawFarTarget: Boolean,
    val elapsedMs: Long,
  ) {
    override fun toString(): String = """
      |writes            = $writes
      |reads             = $reads
      |read threads      = ${readThreads.toSortedSet()}
      |saw written value = $sawFarTarget
      |elapsed           = ${elapsedMs}ms
    """.trimMargin()
  }

  /**
   * Starts [animator], hammers reads on the animator thread, and runs [write] in batches on the
   * main thread for [RACE_BUDGET_MS]. Each batch waits for the animator thread to drain the writes
   * it posted before scheduling the next one, so the queue stays bounded.
   */
  private fun race(
    animator: ZoomAnimator,
    evaluator: RecordingEvaluator,
    write: () -> Unit,
  ): RunResult {
    animator.duration = ANIMATION_DURATION_MS
    animator.repeatCount = ValueAnimator.INFINITE
    animator.repeatMode = ValueAnimator.RESTART
    animator.markRegistered()
    animator.start()
    awaitFirstRead(evaluator)

    val reads = AtomicLong()
    val stopReader = AtomicBoolean(false)
    val readerDone = CountDownLatch(1)
    AnimationThreadController.postOnAnimatorThread {
      // Re-post through the looper handler because postOnAnimatorThread would run inline here.
      val animatorHandler = Handler(Looper.myLooper()!!)
      var fraction = 0f
      lateinit var chunk: Runnable
      chunk = Runnable {
        var done = 0
        while (done < READ_CHUNK) {
          fraction += READ_FRACTION_STEP
          if (fraction > 1f) fraction = 0f
          animator.setCurrentFraction(fraction)
          done++
        }
        reads.addAndGet(done.toLong())
        if (stopReader.get() || crash.get() != null) {
          readerDone.countDown()
        } else {
          animatorHandler.post(chunk)
        }
      }
      animatorHandler.post(chunk)
    }

    val mainHandler = Handler(Looper.getMainLooper())
    val writes = AtomicLong()
    val finished = CountDownLatch(1)
    val startedAt = SystemClock.elapsedRealtime()
    val deadline = startedAt + RACE_BUDGET_MS
    lateinit var batch: Runnable
    batch = Runnable {
      var done = 0
      while (done < WRITE_BATCH && crash.get() == null) {
        write()
        done++
      }
      writes.addAndGet(done.toLong())
      if (crash.get() != null || SystemClock.elapsedRealtime() >= deadline) {
        finished.countDown()
      } else {
        // Wait until this batch's posted writes have run on the animator thread.
        AnimationThreadController.postOnAnimatorThread { mainHandler.post(batch) }
      }
    }
    mainHandler.post(batch)
    finished.await(RACE_BUDGET_MS + GRACE_MS, TimeUnit.MILLISECONDS)
    val elapsedMs = SystemClock.elapsedRealtime() - startedAt

    stopReader.set(true)
    readerDone.await(GRACE_MS, TimeUnit.MILLISECONDS)
    if (crash.get() == null) {
      // Tear down from the main thread. If MapboxAnimThread has already died, its looper is null
      // and calling postOnAnimatorThread from another thread could run cancel() inline.
      val drained = CountDownLatch(1)
      mainHandler.post {
        animator.cancel()
        AnimationThreadController.postOnAnimatorThread { drained.countDown() }
      }
      drained.await(GRACE_MS, TimeUnit.MILLISECONDS)
    }

    return RunResult(
      writes = writes.get(),
      reads = reads.get(),
      readThreads = evaluator.readThreads,
      sawFarTarget = evaluator.sawFarTarget.get(),
      elapsedMs = elapsedMs,
    )
  }

  private fun awaitFirstRead(evaluator: RecordingEvaluator) {
    val until = SystemClock.elapsedRealtime() + STARTUP_TIMEOUT_MS
    while (evaluator.readThreads.isEmpty() && crash.get() == null && SystemClock.elapsedRealtime() < until) {
      SystemClock.sleep(5L)
    }
  }

  /**
   * Makes sure a crash-free run was meaningful: reads must happen on the animator thread and must
   * observe written values. A crash already proves reads and writes overlapped, so check this after
   * the crash assertion.
   */
  private fun assertHarnessRaced(run: RunResult) {
    assertTrue(
      "Keyframes were never read on $ANIM_THREAD_NAME, so reads and writes were never concurrent; result is meaningless.\n$run",
      ANIM_THREAD_NAME in run.readThreads
    )
    assertTrue(
      "The reader never observed $FAR_TARGET, so the written keyframes never reached it; result is meaningless.\n$run",
      run.sawFarTarget
    )
  }

  private fun assertNoAnimatorThreadCrash(run: RunResult) {
    val (threadName, throwable) = crash.get() ?: return
    val stack = StringWriter().also { throwable.printStackTrace(PrintWriter(it)) }.toString()
    fail("Animator thread '$threadName' crashed while the main thread mutated the animator.\n$run\n$stack")
  }

  // ---- animator under test -------------------------------------------------------------------

  /**
   * Minimal concrete [CameraAnimator] for ZOOM. The real `CameraZoomAnimator` is internal, but the
   * race lives in the shared base-class behavior, which this test exercises unchanged.
   */
  private class ZoomAnimator(
    evaluator: TypeEvaluator<Double>,
    options: CameraAnimatorOptions<Double>,
  ) : CameraAnimator<Double>(evaluator, options) {

    override val type: CameraAnimatorType = CameraAnimatorType.ZOOM

    val resolvedCount = AtomicLong()

    override fun resolveAnimationObjectValues(startValue: Any): Array<*> {
      resolvedCount.incrementAndGet()
      return super.resolveAnimationObjectValues(startValue)
    }

    /**
     * `start()` will not run until the plugin marks the animator as registered. That wiring is
     * private and needs a live map, so the test sets the flag directly because it only gates start.
     */
    fun markRegistered() {
      CameraAnimator::class.java.getDeclaredField("registered").apply { isAccessible = true }
        .setBoolean(this, true)
    }
  }

  /** Stand-in for `Evaluators.DOUBLE` that records which thread evaluates and what values it sees. */
  private class RecordingEvaluator : CameraTypeEvaluator<Double> {
    val readThreads: MutableSet<String> = Collections.newSetFromMap(ConcurrentHashMap())
    val sawFarTarget = AtomicBoolean(false)

    override fun evaluate(fraction: Float, startValue: Double, endValue: Double): Double {
      if (readThreads.size < READ_THREAD_SAMPLES) {
        readThreads.add(Thread.currentThread().name)
      }
      if (endValue == FAR_TARGET || startValue == FAR_TARGET) {
        sawFarTarget.set(true)
      }
      return startValue + fraction * (endValue - startValue)
    }
  }

  private companion object {
    const val ANIM_THREAD_NAME = "MapboxAnimThread"

    /** First target; also the temporary value the base class installs in `init`. */
    const val NEAR_TARGET = 5.0

    /** Second target; only appears in values written after start, never in the initial seed. */
    const val FAR_TARGET = 15.0

    const val BASE_ZOOM = 6.0
    const val ZOOM_STEP = 0.01

    /** Power of two so the write loop can mask instead of divide. */
    const val CAMERA_STATE_POOL = 64

    const val ANIMATION_DURATION_MS = 500L
    const val WRITE_BATCH = 20_000
    const val READ_CHUNK = 512
    const val READ_FRACTION_STEP = 0.01f
    const val READ_THREAD_SAMPLES = 4

    /** Without the fix the race fails quickly; a crash-free run of this length passes. */
    const val RACE_BUDGET_MS = 10_000L
    const val GRACE_MS = 5_000L
    const val STARTUP_TIMEOUT_MS = 5_000L
  }
}