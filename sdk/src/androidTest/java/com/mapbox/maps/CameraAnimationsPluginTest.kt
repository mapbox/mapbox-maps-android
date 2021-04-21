package com.mapbox.maps

import android.animation.Animator
import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.common.Logger
import com.mapbox.maps.plugin.animation.*
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class CameraAnimationsPluginTest : BaseAnimationMapTest() {

  @Test
  fun testAnimatorWithAnimatorDelay() {
    val duration = 2000L
    val delay = 1000L
    val animatorListener = CameraAnimatorListener()
    val bearingAnimator = createBearingAnimator(cameraAnimationPlugin, 180.0, delay, duration)
    bearingAnimator.addListener(animatorListener)

    val latchHalfDelay = CountDownLatch(1)
    val latchDelay = CountDownLatch(1)
    val latchDelayAndDuration = CountDownLatch(1)

    mainHandler.post {
      cameraAnimationPlugin.registerAnimators(bearingAnimator)
      bearingAnimator.start()
    }
    mainHandler.postDelayed({ latchHalfDelay.countDown() }, delay / 2)
    mainHandler.postDelayed({ latchDelay.countDown() }, delay + 500)
    mainHandler.postDelayed({ latchDelayAndDuration.countDown() }, delay + duration + 200)

    if (latchHalfDelay.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertFalse(animatorListener.started)
      assertFalse(animatorListener.ended)
    } else {
      TimeoutException()
    }

    if (latchDelay.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertTrue(animatorListener.started)
      assertFalse(animatorListener.ended)
    } else {
      TimeoutException()
    }

    if (latchDelayAndDuration.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertTrue(animatorListener.started)
      assertTrue(animatorListener.ended)
    } else {
      TimeoutException()
    }
  }

  @Test
  fun testCancelAnimatorWithDelayed() {
    val duration = 2000L
    val delay = 500L
    val animatorListener = CameraAnimatorListener()
    val animatorListener2 = CameraAnimatorListener()
    val bearingAnimator = createBearingAnimator(cameraAnimationPlugin, 180.0, delay, duration)
    val bearingAnimator2 = createBearingAnimator(cameraAnimationPlugin, 90.0, 0, duration)
    val cameraAnimationPlugin = mapView.camera
    bearingAnimator.addListener(animatorListener)
    bearingAnimator2.addListener(animatorListener2)

    val latchDelayAndDuration = CountDownLatch(1)

    mainHandler.post {
      cameraAnimationPlugin.registerAnimators(bearingAnimator, bearingAnimator2)
      bearingAnimator.start()
      bearingAnimator2.start()
    }
    mainHandler.postDelayed({ latchDelayAndDuration.countDown() }, delay + duration + 500)

    if (latchDelayAndDuration.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {

      assertTrue(animatorListener.started)
      assertTrue(animatorListener.ended)
      assertFalse(animatorListener.canceled)

      assertTrue(animatorListener2.started)
      assertTrue(animatorListener2.ended)
      assertTrue(animatorListener2.canceled)

      assertEquals(180.0, mapView.getMapboxMap().getCameraOptions(null).bearing!!, EPS)
    } else {
      TimeoutException()
    }
  }

  @Test
  fun testAnimatorWithHandlerPostDelay() {
    val duration = 1000L
    val delay = 2000L
    val animatorListener = CameraAnimatorListener()
    val bearingAnimator = createBearingAnimator(cameraAnimationPlugin, 180.0, 0, duration)
    val cameraAnimationPlugin = mapView.camera
    bearingAnimator.addListener(animatorListener)

    val latchHalfDelay = CountDownLatch(1)
    val latchDelay = CountDownLatch(1)
    val latchDelayAndDuration = CountDownLatch(1)

    mainHandler.postDelayed(
      {
        cameraAnimationPlugin.registerAnimators(bearingAnimator)
        bearingAnimator.start()
      },
      delay
    )
    mainHandler.postDelayed({ latchHalfDelay.countDown() }, delay / 2)
    mainHandler.postDelayed({ latchDelay.countDown() }, delay + 500)
    mainHandler.postDelayed({ latchDelayAndDuration.countDown() }, delay + duration + 200)

    if (latchHalfDelay.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertFalse(animatorListener.started)
      assertFalse(animatorListener.ended)
    } else {
      TimeoutException()
    }

    if (latchDelay.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertTrue(animatorListener.started)
      assertFalse(animatorListener.ended)
    } else {
      TimeoutException()
    }

    if (latchDelayAndDuration.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertTrue(animatorListener.started)
      assertTrue(animatorListener.ended)
    } else {
      TimeoutException()
    }
  }

  @Test
  fun testCameraUpdateFrequency() {
    val duration = 3000L

    // Considering min update frequency is not less than 20 ms assuming running on x86 emulator on CI
    val expectedUpdatesMin = duration / 20
    val animatorListener = CameraAnimatorListener()
    var actualUpdates = 0L

    val bearingAnimator = createBearingAnimator(cameraAnimationPlugin, 180.0, 0, duration)
    bearingAnimator.addListener(animatorListener)

    val cameraAnimationPlugin = mapView.camera
    cameraAnimationPlugin.addCameraBearingChangeListener {
      Logger.i(TAG, "onChanged $it")
      actualUpdates += 1
    }

    mainHandler.post {
      cameraAnimationPlugin.registerAnimators(bearingAnimator)
      bearingAnimator.start()
    }

    if (animatorListener.latchEnd.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      MatcherAssert.assertThat(actualUpdates, Matchers.greaterThanOrEqualTo(expectedUpdatesMin))
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testRegisterExistedAnimatorTypeAndStart() {
    val duration = 2000L

    val animatorListener1 = CameraAnimatorListener()
    val bearingAnimator1 = createBearingAnimator(cameraAnimationPlugin, 70.0, 0, duration)
    bearingAnimator1.addListener(animatorListener1)

    val animatorListener2 = CameraAnimatorListener()
    val bearingAnimator2 = createBearingAnimator(cameraAnimationPlugin, 170.0, 0, duration)
    bearingAnimator2.addListener(animatorListener2)

    val cameraAnimationPlugin = mapView.camera

    mainHandler.post {
      cameraAnimationPlugin.registerAnimators(bearingAnimator1)
      bearingAnimator1.start()
    }

    val delay2 = duration / 2
    mainHandler.postDelayed(
      {
        cameraAnimationPlugin.registerAnimators(bearingAnimator2)
        bearingAnimator2.start()
      },
      delay2
    )

    val awaitLatch = CountDownLatch(1)
    if (animatorListener2.latchStart.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {

      assertTrue(animatorListener1.canceled)
      assertFalse(animatorListener2.ended)
      assertFalse(animatorListener2.canceled)

      awaitLatch.await(100L, TimeUnit.MILLISECONDS)

      // Check that new animator reset previous Camera options -
      // so camera should be definitely less than 35.0 (ideally near 0.0)
      val cameraOptions = mapView.getMapboxMap().getCameraOptions(null)
      MatcherAssert.assertThat(cameraOptions.bearing, Matchers.lessThan(35.0))
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testEaseToSingleDurationZero() {
    val targetBearing = 5.0
    val cameraOptions = CameraOptions.Builder().bearing(targetBearing).build()
    val expectedValues = mutableSetOf(-0.0, targetBearing)
    val updatedValues = mutableListOf<Double>()

    val latch = CountDownLatch(2)
    val cameraAnimationPlugin = mapView.camera
    cameraAnimationPlugin.addCameraBearingChangeListener {
      Logger.i(TAG, "onChanged $it")
      updatedValues.add(it)
      latch.countDown()
    }

    mainHandler.post { cameraAnimationPlugin.easeTo(cameraOptions, mapAnimationOptions { duration(0) }) }

    if (latch.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testEaseToSingleDurationShort() {
    val targetBearing = 5.0
    val cameraOptions = CameraOptions.Builder().bearing(targetBearing).build()
    val expectedValues = mutableSetOf(-0.0, targetBearing)
    val updatedValues = mutableListOf<Double>()

    val latch = CountDownLatch(2)
    val cameraAnimationPlugin = mapView.camera
    cameraAnimationPlugin.addCameraBearingChangeListener {
      Logger.i(TAG, "onChanged $it")
      updatedValues.add(it)
      latch.countDown()
    }

    mainHandler.post { cameraAnimationPlugin.easeTo(cameraOptions, mapAnimationOptions { duration(1) }) }

    if (latch.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testEaseToSequenceDurationZero() {

    val targetBearing1 = 5.0
    val targetBearing2 = 10.0
    val targetBearing3 = 15.0
    val cameraOptions1 = CameraOptions.Builder().bearing(targetBearing1).build()
    val cameraOptions2 = CameraOptions.Builder().bearing(targetBearing2).build()
    val cameraOptions3 = CameraOptions.Builder().bearing(targetBearing3).build()
    val expectedValues = mutableSetOf(-0.0, targetBearing1, targetBearing2, targetBearing3)
    val updatedValues = mutableListOf<Double>()

    val latch = CountDownLatch(4)
    val cameraAnimationPlugin = mapView.camera
    cameraAnimationPlugin.addCameraBearingChangeListener {
      Logger.i(TAG, "onChanged $it")
      updatedValues.add(it)
      latch.countDown()
    }
    mainHandler.post { cameraAnimationPlugin.easeTo(cameraOptions1, mapAnimationOptions { duration(0) }) }
    mainHandler.postDelayed({ cameraAnimationPlugin.easeTo(cameraOptions2, mapAnimationOptions { duration(0) }) }, 500)
    mainHandler.postDelayed({ cameraAnimationPlugin.easeTo(cameraOptions3, mapAnimationOptions { duration(0) }) }, 1000)

    if (latch.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testEaseToSequenceDurationShort() {

    val targetBearing1 = 5.0
    val targetBearing2 = 10.0
    val targetBearing3 = 15.0
    val cameraOptions1 = CameraOptions.Builder().bearing(targetBearing1).build()
    val cameraOptions2 = CameraOptions.Builder().bearing(targetBearing2).build()
    val cameraOptions3 = CameraOptions.Builder().bearing(targetBearing3).build()
    val expectedValues = mutableSetOf(-0.0, targetBearing1, targetBearing2, targetBearing3)
    val updatedValues = mutableListOf<Double>()

    val latch = CountDownLatch(4)
    val cameraAnimationPlugin = mapView.camera
    cameraAnimationPlugin.addCameraBearingChangeListener {
      Logger.i(TAG, "onChanged $it")
      updatedValues.add(it)
      latch.countDown()
    }
    mainHandler.post { cameraAnimationPlugin.easeTo(cameraOptions1, mapAnimationOptions { duration(1) }) }
    mainHandler.postDelayed({ cameraAnimationPlugin.easeTo(cameraOptions2, mapAnimationOptions { duration(1) }) }, 500)
    mainHandler.postDelayed({ cameraAnimationPlugin.easeTo(cameraOptions3, mapAnimationOptions { duration(1) }) }, 1000)

    if (latch.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testEaseToAnimatorSequenceLessThenFrameUpdate() {

    val targetBearing1 = 5.0
    val targetBearing2 = 10.0
    val targetBearing3 = 15.0
    val cameraOptions1 = CameraOptions.Builder().bearing(targetBearing1).build()
    val cameraOptions2 = CameraOptions.Builder().bearing(targetBearing2).build()
    val cameraOptions3 = CameraOptions.Builder().bearing(targetBearing3).build()
    val expectedValues = mutableSetOf(-0.0, targetBearing3)
    val updatedValues = mutableListOf<Double>()

    val latch = CountDownLatch(2)
    val cameraAnimationPlugin = mapView.camera
    cameraAnimationPlugin.addCameraBearingChangeListener {
      Logger.i(TAG, "onChanged $it")
      updatedValues.add(it)
      latch.countDown()
    }
    // assuming frame update time as 16 ms, putting delays less than it
    mainHandler.post { cameraAnimationPlugin.easeTo(cameraOptions1, mapAnimationOptions { duration(1) }) }
    mainHandler.postDelayed({ cameraAnimationPlugin.easeTo(cameraOptions2, mapAnimationOptions { duration(1) }) }, 4)
    mainHandler.postDelayed({ cameraAnimationPlugin.easeTo(cameraOptions3, mapAnimationOptions { duration(1) }) }, 7)

    if (latch.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testIndependentAnimators() {

    val bearingAnimator = createBearingAnimator(cameraAnimationPlugin, 180.0, 0, 500L)
    val bearingListener = CameraAnimatorListener()
    bearingAnimator.addListener(bearingListener)

    val pithListener = CameraAnimatorListener()
    val pitchAnimator = createPitchAnimator(cameraAnimationPlugin, 50.0, 0, 2000L)
    pitchAnimator.addListener(pithListener)

    val cameraAnimationPlugin = mapView.camera
    mainHandler.post {
      cameraAnimationPlugin.registerAnimators(bearingAnimator, pitchAnimator)
      bearingAnimator.start()
      pitchAnimator.start()
    }

    if (bearingListener.latchEnd.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertTrue(bearingListener.ended)
      assertTrue(pithListener.started)
      assertFalse(pithListener.ended)
    } else {
      throw TimeoutException()
    }
  }

  @Test
  fun testPostDelayedAndStartDelayedAnimators() {
    val pitchAnimatorOne = createPitchAnimator(cameraAnimationPlugin, 10.0, 0, 1000L)
    val pitchListenerOne = CameraAnimatorListener()
    pitchAnimatorOne.addListener(pitchListenerOne)

    val pitchAnimatorTwo = createPitchAnimator(cameraAnimationPlugin, 20.0, 500, 1000L)
    val pitchListenerTwo = CameraAnimatorListener()
    pitchAnimatorTwo.addListener(pitchListenerTwo)

    val pitchAnimatorThree = createPitchAnimator(cameraAnimationPlugin, 30.0, 0, 1000L)
    val pitchListenerThree = CameraAnimatorListener()
    pitchAnimatorThree.addListener(pitchListenerThree)

    var currentPitch = 0.0
    val cameraAnimationPlugin = mapView.camera
    cameraAnimationPlugin.addCameraPitchChangeListener {
      Logger.i(TAG, "onChanged $it")
      currentPitch = it
    }
    mainHandler.post {
      cameraAnimationPlugin.registerAnimators(
        pitchAnimatorOne,
        pitchAnimatorTwo,
        pitchAnimatorThree
      )
      pitchAnimatorOne.start()
      pitchAnimatorTwo.start()
    }
    mainHandler.postDelayed(
      {
        pitchAnimatorThree.start()
      },
      750
    )

    if (pitchListenerTwo.latchStart.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertTrue(pitchListenerOne.canceled)
      assertFalse(pitchListenerThree.started)
    } else {
      throw TimeoutException()
    }
    if (pitchListenerThree.latchStart.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertTrue(pitchListenerTwo.canceled)
    } else {
      throw TimeoutException()
    }
    if (pitchListenerThree.latchEnd.await(LATCH_MAX_TIME, TimeUnit.MILLISECONDS)) {
      assertFalse(pitchListenerThree.canceled)
      assertEquals(30.0, currentPitch, EPS)
      assertEquals(30.0, mapView.getMapboxMap().getCameraOptions(null).pitch!!, EPS)
    } else {
      throw TimeoutException()
    }
  }

  class CameraAnimatorListener : Animator.AnimatorListener {

    var latchEnd = CountDownLatch(1)
    var latchCancel = CountDownLatch(1)
    var latchStart = CountDownLatch(1)
    var started = false
    var ended = false
    var canceled = false

    override fun onAnimationRepeat(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {
      ended = true
      latchEnd.countDown()
    }

    override fun onAnimationCancel(animation: Animator?) {
      canceled = true
      latchCancel.countDown()
    }

    override fun onAnimationStart(animation: Animator?) {
      started = true
      latchStart.countDown()
    }
  }

  private val mainHandler = Handler(Looper.getMainLooper())

  private fun createBearingAnimator(
    cameraPlugin: CameraAnimationsPlugin,
    target: Double,
    animatorDelay: Long,
    animatorDuration: Long
  ) =
    cameraPlugin.createBearingAnimator(
      cameraAnimatorOptions(target) {
        startValue(0.0)
      }
    ) {
      duration = animatorDuration
      startDelay = animatorDelay
    }

  private fun createPitchAnimator(
    cameraPlugin: CameraAnimationsPlugin,
    target: Double,
    animatorDelay: Long,
    animatorDuration: Long
  ) =
    cameraPlugin.createPitchAnimator(
      cameraAnimatorOptions(target) {
        startValue(0.0)
      }
    ) {
      duration = animatorDuration
      startDelay = animatorDelay
    }

  companion object {
    private const val TAG = "Mbgl-CameraManager"
    private const val EPS = 0.000001
    private const val LATCH_MAX_TIME = 5_000L
  }
}