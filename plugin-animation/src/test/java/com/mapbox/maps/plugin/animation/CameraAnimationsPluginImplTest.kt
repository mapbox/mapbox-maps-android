package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper.getMainLooper
import androidx.core.animation.addListener
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImpl.Companion.TAG
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImplTest.Companion.cameraState
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImplTest.Companion.toCameraState
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.animation.animator.CameraBearingAnimator
import com.mapbox.maps.plugin.animation.animator.CameraCenterAnimator
import com.mapbox.maps.plugin.animation.animator.CameraPitchAnimator
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import io.mockk.*
import io.mockk.verify
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLog
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class CameraAnimationsPluginImplTest {

  private lateinit var cameraAnimationsPluginImpl: CameraAnimationsPluginImpl
  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate
  private lateinit var cameraAnimatorsFactory: CameraAnimatorsFactory
  private lateinit var bearingAnimator: CameraBearingAnimator
  private lateinit var centerAnimator: CameraCenterAnimator

  @Before
  fun setUp() {
    ShadowLog.stream = System.out
    cameraAnimatorsFactory = mockk(relaxed = true)
    bearingAnimator = mockk(relaxed = true)
    centerAnimator = mockk(relaxed = true)
    every { cameraAnimatorsFactory.getEaseTo(any()) } returns arrayOf(
      bearingAnimator,
      centerAnimator
    )
    every { cameraAnimatorsFactory.getMoveBy(any()) } returns arrayOf(
      bearingAnimator,
      centerAnimator
    )
    every { cameraAnimatorsFactory.getPitchBy(any()) } returns arrayOf(
      bearingAnimator,
      centerAnimator
    )
    every { cameraAnimatorsFactory.getRotateBy(any(), any()) } returns arrayOf(
      bearingAnimator,
      centerAnimator
    )
    every { cameraAnimatorsFactory.getScaleBy(any(), any()) } returns arrayOf(
      bearingAnimator,
      centerAnimator
    )

    val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
    mapCameraManagerDelegate = mockk(relaxed = true)
    mapTransformDelegate = mockk(relaxed = true)
    mockkObject(CameraTransform)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { logI(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { delegateProvider.mapTransformDelegate } returns mapTransformDelegate
    cameraAnimationsPluginImpl = CameraAnimationsPluginImpl().apply {
      onDelegateProvider(delegateProvider)
    }
    mockkObject(CameraAnimationsPluginImpl)
    every { CameraAnimationsPluginImpl.immediateCameraUpdatesEnabled() } returns false
  }

  @After
  fun cleanUp() {
    unmockkObject(CameraTransform)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    unmockkObject(CameraAnimationsPluginImpl)
  }

  @Test
  fun registerAnimators() {
    val animators = arrayOf(
      centerAnimator, bearingAnimator
    )
    cameraAnimationsPluginImpl.registerAnimators(*animators)
    animators.forEach {
      verify { it.addInternalListener(any()) }
    }
  }

  @Test
  fun unregisterAnimators() {
    val animators = arrayOf(
      centerAnimator, bearingAnimator
    )
    cameraAnimationsPluginImpl.registerAnimators(*animators)
    cameraAnimationsPluginImpl.unregisterAnimators(*animators)
    animators.forEach {
      verify {
        it.addInternalListener(any())
        it.removeInternalListener()
      }
    }
  }

  @Test
  fun unregisterAllAnimators() {
    val animators = arrayOf(
      centerAnimator, bearingAnimator
    )
    cameraAnimationsPluginImpl.registerAnimators(*animators)
    cameraAnimationsPluginImpl.unregisterAllAnimators()
    animators.forEach {
      verify {
        it.addInternalListener(any())
        it.removeInternalListener()
      }
    }
  }

  @Test
  fun startRegisteredAnimation() {
    val bearingAnimator = CameraBearingAnimator(
      cameraAnimatorOptions(10.0) {
        startValue(0.0)
      },
      true
    )
    val animators = arrayOf(
      bearingAnimator
    )
    cameraAnimationsPluginImpl.registerAnimators(*animators)
    AnimatorSet().apply {
      duration = DURATION
      playTogether(*animators)
      start()
    }

    shadowOf(getMainLooper()).idle()

    verify { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun startUnregisteredAnimation() {
    cameraAnimationsPluginImpl.unregisterAllAnimators()
    val bearingAnimator = CameraBearingAnimator(
      cameraAnimatorOptions(10.0) {
        startValue(0.0)
      },
      true
    )
    val animators = arrayOf(
      bearingAnimator
    )
    AnimatorSet().apply {
      duration = DURATION
      playTogether(*animators)
      start()
    }
    verify(exactly = 0) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun testEaseToRegister() {
    cameraAnimationsPluginImpl.cameraAnimationsFactory = cameraAnimatorsFactory
    cameraAnimationsPluginImpl.easeTo(
      cameraState.toCameraOptions(),
      mapAnimationOptions { duration(DURATION) }
    )
    verify {
      centerAnimator.addInternalListener(any())
      bearingAnimator.addInternalListener(any())
    }
  }

  @Test
  fun testMoveToRegister() {
    cameraAnimationsPluginImpl.cameraAnimationsFactory = cameraAnimatorsFactory
    cameraAnimationsPluginImpl.moveBy(
      ScreenCoordinate(VALUE, VALUE),
      mapAnimationOptions { duration(DURATION) }
    )
    verify {
      centerAnimator.addInternalListener(any())
      bearingAnimator.addInternalListener(any())
    }
  }

  @Test
  fun testScaleByRegister() {
    cameraAnimationsPluginImpl.cameraAnimationsFactory = cameraAnimatorsFactory
    cameraAnimationsPluginImpl.scaleBy(
      VALUE,
      ScreenCoordinate(VALUE, VALUE),
      mapAnimationOptions { duration(DURATION) }
    )
    verify {
      centerAnimator.addInternalListener(any())
      bearingAnimator.addInternalListener(any())
    }
  }

  @Test
  fun testRotateByRegister() {
    cameraAnimationsPluginImpl.cameraAnimationsFactory = cameraAnimatorsFactory
    cameraAnimationsPluginImpl.rotateBy(
      ScreenCoordinate(VALUE, VALUE),
      ScreenCoordinate(VALUE, VALUE),
      mapAnimationOptions { duration(DURATION) }
    )
    verify {
      centerAnimator.addInternalListener(any())
      bearingAnimator.addInternalListener(any())
    }
  }

  @Test
  fun testPitchByRegister() {
    cameraAnimationsPluginImpl.cameraAnimationsFactory = cameraAnimatorsFactory
    cameraAnimationsPluginImpl.pitchBy(VALUE, mapAnimationOptions { duration(DURATION) })
    verify {
      centerAnimator.addInternalListener(any())
      bearingAnimator.addInternalListener(any())
    }
  }

  @Test
  fun startSubsequentAnimationsWithTheSameType1() {
    var cameraPosition = CameraOptions.Builder().build()
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg()
    }

    val targetFirst = 10.0
    val bearingAnimatorFirst = createBearingAnimator(targetFirst, 2, 5)
    val listenerFirst = CameraAnimatorListener()
    bearingAnimatorFirst.addListener(listenerFirst)

    val targetSecond = 12.0
    val bearingAnimatorSecond = createBearingAnimator(targetSecond, 4, 5)
    val listenerSecond = CameraAnimatorListener()
    bearingAnimatorSecond.addListener(listenerSecond)

    cameraAnimationsPluginImpl.registerAnimators(bearingAnimatorFirst, bearingAnimatorSecond)

    shadowOf(getMainLooper()).pause()
    bearingAnimatorFirst.start()
    bearingAnimatorSecond.start()

    shadowOf(getMainLooper()).idle()

    assertTrue(listenerFirst.started)
    assertTrue(listenerSecond.started)
    assertTrue(listenerFirst.canceled)
    assertTrue(listenerSecond.ended)
    assertTrue(listenerSecond.ended)

    assertEquals(targetSecond, cameraPosition.bearing)
  }

  @Test
  fun startSubsequentAnimationsWithTheSameType2() {
    var cameraPosition = CameraOptions.Builder().build()
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg()
    }

    val targetFirst = 10.0
    val bearingAnimatorFirst = createBearingAnimator(targetFirst, 2, 3)
    val listenerFirst = CameraAnimatorListener()
    bearingAnimatorFirst.addListener(listenerFirst)

    val targetSecond = 12.0
    val bearingAnimatorSecond = createBearingAnimator(targetSecond, 7, 3)
    val listenerSecond = CameraAnimatorListener()
    bearingAnimatorSecond.addListener(listenerSecond)

    cameraAnimationsPluginImpl.registerAnimators(bearingAnimatorFirst, bearingAnimatorSecond)

    shadowOf(getMainLooper()).pause()
    bearingAnimatorFirst.start()
    bearingAnimatorSecond.start()

    shadowOf(getMainLooper()).idle()

    assertTrue(listenerFirst.started)
    assertTrue(listenerSecond.started)
    assertFalse(listenerFirst.canceled)
    assertFalse(listenerSecond.canceled)
    assertTrue(listenerSecond.ended)
    assertTrue(listenerSecond.ended)

    assertEquals(targetSecond, cameraPosition.bearing)
  }

  @Test
  fun testEaseToSingleDurationZero() {
    var cameraPosition = CameraOptions.Builder().build()
    every {
      mapCameraManagerDelegate.cameraState
    } answers { cameraPosition.toCameraState() }
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg<CameraOptions>()
      cameraAnimationsPluginImpl.onCameraMove(cameraPosition.toCameraState())
    }
    val targetPitch = 5.0
    val cameraOptions = CameraOptions.Builder().pitch(targetPitch).build()
    val expectedValues = mutableSetOf(targetPitch)
    val updatedValues = mutableListOf<Double>()

    cameraAnimationsPluginImpl.addCameraPitchChangeListener { updatedValue ->
      updatedValues.add(
        updatedValue
      )
    }

    shadowOf(getMainLooper()).pause()

    cameraAnimationsPluginImpl.easeTo(cameraOptions, mapAnimationOptions { duration(0) })

    shadowOf(getMainLooper()).idle()

    assertEquals(targetPitch, cameraPosition.pitch)
    assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
  }

  @Test
  fun testEaseToSingleDurationShort() {
    var cameraPosition = CameraOptions.Builder().build()
    every {
      mapCameraManagerDelegate.cameraState
    } answers { cameraPosition.toCameraState() }
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg<CameraOptions>()
      cameraAnimationsPluginImpl.onCameraMove(cameraPosition.toCameraState())
    }
    val targetPitch = 5.0
    val cameraOptions = CameraOptions.Builder().pitch(targetPitch).build()
    val expectedValues = mutableSetOf(VALUE, targetPitch)
    val updatedValues = mutableListOf<Double>()

    cameraAnimationsPluginImpl.addCameraPitchChangeListener { updatedValue ->
      updatedValues.add(
        updatedValue
      )
    }

    shadowOf(getMainLooper()).pause()

    cameraAnimationsPluginImpl.easeTo(cameraOptions, mapAnimationOptions { duration(1L) })

    shadowOf(getMainLooper()).idle()

    assertEquals(targetPitch, cameraPosition.pitch)
    assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
  }

  @Test
  fun testEaseToSequenceDurationZero() {
    var cameraPosition = CameraState(
      Point.fromLngLat(90.0, 90.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      3.0,
      90.0,
      0.0
    )
    every {
      mapCameraManagerDelegate.cameraState
    } answers { cameraPosition }
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg<CameraOptions>().toCameraState()
      cameraAnimationsPluginImpl.onCameraMove(cameraPosition)
    }
    val targetPitchFirst = 5.0
    val targetPitchSecond = 10.0
    val targetPitchThird = 15.0
    val cameraOptions1 = CameraOptions.Builder().pitch(targetPitchFirst).build()
    val cameraOptions2 = CameraOptions.Builder().pitch(targetPitchSecond).build()
    val cameraOptions3 = CameraOptions.Builder().pitch(targetPitchThird).build()
    val expectedValues = mutableSetOf(targetPitchFirst, targetPitchSecond, targetPitchThird)
    val updatedValues = mutableListOf<Double>()

    cameraAnimationsPluginImpl.addCameraPitchChangeListener { updatedValue ->
      updatedValues.add(
        updatedValue
      )
    }

    shadowOf(getMainLooper()).pause()

    val handler = Handler(getMainLooper())
    cameraAnimationsPluginImpl.easeTo(cameraOptions1, mapAnimationOptions { duration(0) })

    handler.postDelayed(
      {
        cameraAnimationsPluginImpl.easeTo(
          cameraOptions2,
          mapAnimationOptions { duration(0) }
        )
      },
      1
    )
    handler.postDelayed(
      {
        cameraAnimationsPluginImpl.easeTo(
          cameraOptions3,
          mapAnimationOptions { duration(0) }
        )
      },
      2
    )

    shadowOf(getMainLooper()).idleFor(Duration.ofMillis(2))

    assertEquals(targetPitchThird, cameraPosition.pitch, EPS)
    assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
  }

  @Test
  fun testEaseToSequenceQuickDuration() {
    var cameraPosition = CameraState(
      Point.fromLngLat(90.0, 90.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      3.0,
      90.0,
      0.0
    )
    every {
      mapCameraManagerDelegate.cameraState
    } answers { cameraPosition }
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg<CameraOptions>().toCameraState()
      cameraAnimationsPluginImpl.onCameraMove(cameraPosition)
    }
    val targetPitchFirst = 5.0
    val targetPitchSecond = 10.0
    val targetPitchThird = 15.0
    val cameraOptions1 = CameraOptions.Builder().pitch(targetPitchFirst).build()
    val cameraOptions2 = CameraOptions.Builder().pitch(targetPitchSecond).build()
    val cameraOptions3 = CameraOptions.Builder().pitch(targetPitchThird).build()
    val expectedValues = mutableSetOf(0.0, targetPitchFirst, targetPitchSecond, targetPitchThird)
    val updatedValues = mutableListOf<Double>()

    cameraAnimationsPluginImpl.addCameraPitchChangeListener { updatedValue ->
      updatedValues.add(
        updatedValue
      )
    }

    shadowOf(getMainLooper()).pause()

    val handler = Handler(getMainLooper())
    cameraAnimationsPluginImpl.easeTo(cameraOptions1, mapAnimationOptions { duration(1) })
    shadowOf(getMainLooper()).idleFor(Duration.ofMillis(0))
    shadowOf(getMainLooper()).idle()
    handler.postDelayed(
      {
        cameraAnimationsPluginImpl.easeTo(
          cameraOptions2,
          mapAnimationOptions { duration(1) }
        )
      },
      2
    )
    shadowOf(getMainLooper()).idleFor(Duration.ofMillis(2))
    shadowOf(getMainLooper()).idle()
    handler.postDelayed(
      {
        cameraAnimationsPluginImpl.easeTo(
          cameraOptions3,
          mapAnimationOptions { duration(1) }
        )
      },
      8
    )
    shadowOf(getMainLooper()).idleFor(Duration.ofMillis(8))
    shadowOf(getMainLooper()).idle()

    assertEquals(targetPitchThird, cameraPosition.pitch, EPS)
    assertArrayEquals(expectedValues.toDoubleArray(), updatedValues.toDoubleArray(), EPS)
  }

  @Test
  fun testDelayedAnimatorsFinalStateAndCallbackResult() {
    var cameraPosition = CameraOptions.Builder().build()
    every {
      mapCameraManagerDelegate.cameraState
    } answers { cameraPosition.toCameraState() }
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg<CameraOptions>()
      cameraAnimationsPluginImpl.onCameraMove(cameraPosition.toCameraState())
    }
    val targetPitchOne = 10.0
    val pitchAnimatorOne = createPitchAnimator(targetPitchOne, 0, 1000L)
    val pitchListenerOne = CameraAnimatorListener()
    pitchAnimatorOne.addListener(pitchListenerOne)

    val targetPitchTwo = 20.0
    val pitchAnimatorTwo = createPitchAnimator(targetPitchTwo, 500, 1000L)
    val pitchListenerTwo = CameraAnimatorListener()
    pitchAnimatorTwo.addListener(pitchListenerTwo)

    val targetPitchThree = 30.0
    val pitchAnimatorThree = createPitchAnimator(targetPitchThree, 750, 1000L)
    val pitchListenerThree = CameraAnimatorListener()
    pitchAnimatorThree.addListener(pitchListenerThree)

    var currentPitch = 0.0
    cameraAnimationsPluginImpl.addCameraPitchChangeListener { updatedValue ->
      currentPitch = updatedValue
    }

    cameraAnimationsPluginImpl.registerAnimators(
      pitchAnimatorOne,
      pitchAnimatorTwo,
      pitchAnimatorThree
    )

    shadowOf(getMainLooper()).pause()

    pitchAnimatorOne.start()
    pitchAnimatorTwo.start()
    pitchAnimatorThree.start()

    shadowOf(getMainLooper()).idle()

    assertTrue(pitchListenerOne.canceled)
    assertTrue(pitchListenerTwo.canceled)
    assertFalse(pitchListenerThree.canceled)
    assertTrue(pitchListenerThree.ended)
    assertEquals(targetPitchThree, currentPitch, EPS)
    assertEquals(targetPitchThree, cameraPosition.pitch ?: -1.0, EPS)
  }

  @Test
  fun testUpdateFrequency() {
    val bearingDuration = 37L
    val bearingAnimator = createBearingAnimator(10.0, 2, bearingDuration)

    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    shadowOf(getMainLooper()).pause()

    bearingAnimator.start()
    shadowOf(getMainLooper()).idle()

    // Adding value 2 because of first call after Animator.start()
    val countUpdates = (bearingDuration + 1).toInt()
    verify(exactly = countUpdates) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun testAnimatorListenersCallsCount() {
    val bearingDuration = 17L
    val bearingAnimator = createBearingAnimator(10.0, 2, bearingDuration)
    val bearingListener = CameraAnimatorListener()
    val bearingUpdateListener = CameraUpdateAnimatorListener()
    bearingAnimator.addListener(bearingListener)
    bearingAnimator.addUpdateListener(bearingUpdateListener)

    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    shadowOf(getMainLooper()).pause()

    bearingAnimator.start()
    shadowOf(getMainLooper()).idle()

    assertEquals(1, bearingListener.endedCount)
    assertEquals(1, bearingListener.startedCount)

    // Adding +1 because of count of interpolated intervals (which is equal to bearing duration) and start value
    assertEquals(bearingDuration + 1, bearingUpdateListener.updateCount)
  }

  @Test
  fun executeBearingAnimator() {
    var cameraPosition = CameraOptions.Builder().build()
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg()
    }

    val targetBearing = 12.0
    val bearingAnimator = CameraBearingAnimator(
      cameraAnimatorOptions(targetBearing) {
        startValue(0.0)
      },
      true
    ) {
      duration = DURATION
    }

    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    shadowOf(getMainLooper()).pause()

    bearingAnimator.start()

    shadowOf(getMainLooper()).idle()

    assertEquals(targetBearing, cameraPosition.bearing)
  }

  @Test
  fun testSetUserAnimationProgressSubsequentAnimators() {
    val bearingAnimatorFirst = createBearingAnimator(10.0, 2, 5)
    val bearingAnimatorSecond = createBearingAnimator(12.0, 8, 5)

    cameraAnimationsPluginImpl.registerAnimators(bearingAnimatorFirst, bearingAnimatorSecond)

    shadowOf(getMainLooper()).pause()

    bearingAnimatorFirst.start()
    bearingAnimatorSecond.start()

    shadowOf(getMainLooper()).idle()

    verifyOrder {
      mapTransformDelegate.setUserAnimationInProgress(true)
      mapTransformDelegate.setUserAnimationInProgress(false)
      mapTransformDelegate.setUserAnimationInProgress(true)
      mapTransformDelegate.setUserAnimationInProgress(false)
    }
  }

  @Test
  fun testSetUserAnimationProgressOverlappedAnimators() {
    val bearingAnimatorFirst = createBearingAnimator(10.0, 2, 5)
    val bearingAnimatorSecond = createBearingAnimator(12.0, 4, 5)

    cameraAnimationsPluginImpl.registerAnimators(bearingAnimatorFirst, bearingAnimatorSecond)

    shadowOf(getMainLooper()).pause()

    bearingAnimatorFirst.start()
    bearingAnimatorSecond.start()

    shadowOf(getMainLooper()).idle()

    verifyOrder {
      mapTransformDelegate.setUserAnimationInProgress(true)
      mapTransformDelegate.setUserAnimationInProgress(true)
      mapTransformDelegate.setUserAnimationInProgress(false)
    }
  }

  @Test
  fun testPlayAnimatorsTogether() {
    val pitch = createPitchAnimator(15.0, 0, 5)
    val pitchListener = CameraAnimatorListener()
    pitch.addListener(pitchListener)

    val bearing = createBearingAnimator(10.0, 0, 5)
    bearing.addListener(
      onStart = {
        assertEquals(pitchListener.started, true)
        assertEquals(pitchListener.ended, false)
        assertEquals(pitchListener.canceled, false)
        assertEquals((it as CameraAnimator<*>).owner, MapAnimationOwnerRegistry.INTERNAL)
      }
    )

    shadowOf(getMainLooper()).pause()

    cameraAnimationsPluginImpl.playAnimatorsTogether(pitch, bearing)

    shadowOf(getMainLooper()).idle()
  }

  @Test
  fun testPlayAnimatorsTogetherCustomOwner() {
    val pitch = createPitchAnimator(15.0, 0, 5, owner = MapAnimationOwnerRegistry.GESTURES)
    val pitchListener = CameraAnimatorListener()
    pitch.addListener(pitchListener)

    val bearing = createBearingAnimator(10.0, 0, 5, owner = MapAnimationOwnerRegistry.GESTURES)
    bearing.addListener(
      onStart = {
        assertEquals(pitchListener.started, true)
        assertEquals(pitchListener.ended, false)
        assertEquals(pitchListener.canceled, false)
        assertEquals((it as CameraAnimator<*>).owner, MapAnimationOwnerRegistry.GESTURES)
      }
    )

    shadowOf(getMainLooper()).pause()

    cameraAnimationsPluginImpl.playAnimatorsTogether(pitch, bearing)

    shadowOf(getMainLooper()).idle()
  }

  @Test
  fun testPlayAnimatorsSequentially() {

    val pitch = createPitchAnimator(15.0, 0, 5)
    val pitchListener = CameraAnimatorListener()
    pitch.addListener(pitchListener)

    val bearing = createBearingAnimator(10.0, 0, 5)
    bearing.addListener(
      onStart = {
        assertEquals(pitchListener.started, true)
        assertEquals(pitchListener.ended, true)
      }
    )

    shadowOf(getMainLooper()).pause()

    cameraAnimationsPluginImpl.playAnimatorsSequentially(pitch, bearing)

    shadowOf(getMainLooper()).idle()
  }

  @Test
  fun testAnimatorListenerParameterEnd() {
    val listener = CameraAnimatorListener()
    shadowOf(getMainLooper()).pause()
    cameraAnimationsPluginImpl.easeTo(
      cameraState.toCameraOptions(),
      mapAnimationOptions {
        duration(100L)
        animatorListener(listener)
      }
    )
    shadowOf(getMainLooper()).idle()
    assertEquals(true, listener.started)
    assertEquals(false, listener.canceled)
    assertEquals(true, listener.ended)
  }

  @Test
  fun testAnimatorListenerParameterCancel() {
    val listener = CameraAnimatorListener()

    shadowOf(getMainLooper()).pause()

    val handler = Handler(getMainLooper())
    cameraAnimationsPluginImpl.easeTo(
      cameraState.toCameraOptions(),
      mapAnimationOptions {
        duration(10L)
        animatorListener(listener)
      }
    )
    handler.postDelayed(
      {
        cameraAnimationsPluginImpl.cancelAllAnimators()
      },
      5L
    )

    shadowOf(getMainLooper()).idleFor(Duration.ofMillis(5))
    shadowOf(getMainLooper()).idle()

    assertEquals(true, listener.started)
    assertEquals(true, listener.canceled)
    // end is triggered after cancel in any case
    assertEquals(true, listener.ended)
  }

  @Test
  fun testCancelAllExceptProtected() {
    val listenerOne = CameraAnimatorListener()
    val listenerTwo = CameraAnimatorListener()
    val listenerThree = CameraAnimatorListener()

    val animatorOne = cameraAnimationsPluginImpl.createBearingAnimator(
      cameraAnimatorOptions(2.0) {
        owner("Owner_1")
      }
    ) {
      duration = 200L
      addListener(listenerOne)
    }
    val animatorTwo = cameraAnimationsPluginImpl.createZoomAnimator(
      cameraAnimatorOptions(3.0) {
        owner("Owner_2")
      }
    ) {
      duration = 200L
      addListener(listenerTwo)
    }
    val animatorThree = cameraAnimationsPluginImpl.createPitchAnimator(
      cameraAnimatorOptions(4.0) {
        owner("Owner_3")
      }
    ) {
      duration = 200L
      addListener(listenerThree)
    }

    val handler = Handler(getMainLooper())

    shadowOf(getMainLooper()).pause()
    cameraAnimationsPluginImpl.registerAnimators(animatorOne, animatorTwo, animatorThree)
    animatorOne.start()
    animatorTwo.start()
    animatorThree.start()
    handler.postDelayed(
      {
        cameraAnimationsPluginImpl.cancelAllAnimators(listOf("Owner_1", "Owner_3"))
      },
      5L
    )
    shadowOf(getMainLooper()).idleFor(Duration.ofMillis(20L))
    shadowOf(getMainLooper()).idle()

    assertEquals(false, listenerOne.canceled)
    assertEquals(true, listenerTwo.canceled)
    assertEquals(false, listenerThree.canceled)
  }

  @Test
  fun testAnimatorListenerParameterCancelAnotherAnimation() {
    val listenerOne = CameraAnimatorListener()
    val listenerTwo = CameraAnimatorListener()

    shadowOf(getMainLooper()).pause()

    val handler = Handler(getMainLooper())
    cameraAnimationsPluginImpl.easeTo(
      cameraState.toCameraOptions(),
      mapAnimationOptions {
        duration(10L)
        animatorListener(listenerOne)
      }
    )
    handler.postDelayed(
      {
        cameraAnimationsPluginImpl.easeTo(
          cameraState.toCameraOptions(),
          mapAnimationOptions {
            duration(10L)
            animatorListener(listenerTwo)
          }
        )
      },
      5L
    )

    shadowOf(getMainLooper()).idleFor(Duration.ofMillis(20L))
    shadowOf(getMainLooper()).idle()

    assertEquals(true, listenerOne.started)
    assertEquals(true, listenerOne.canceled)
    // end is triggered after cancel in any case
    assertEquals(true, listenerOne.ended)

    assertEquals(true, listenerTwo.started)
    assertEquals(false, listenerTwo.canceled)
    assertEquals(true, listenerTwo.ended)
  }

  @Test
  fun testLifecycleListener() {
    val listenerOne = LifecycleListener()
    shadowOf(getMainLooper()).pause()
    cameraAnimationsPluginImpl.addCameraAnimationsLifecycleListener(listenerOne)
    val bearingAnimatorOne = cameraAnimationsPluginImpl.createBearingAnimator(
      cameraAnimatorOptions(60.0) {
        startValue(10.0)
      }
    ) {
      duration = 10L
    }
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimatorOne)
    bearingAnimatorOne.start()
    shadowOf(getMainLooper()).idle()

    assertEquals(true, listenerOne.starting)
    assertEquals(false, listenerOne.cancelling)
    assertEquals(true, listenerOne.ending)
    assertEquals(false, listenerOne.interrupting)

    shadowOf(getMainLooper()).pause()

    val bearingAnimatorTwo = cameraAnimationsPluginImpl.createBearingAnimator(
      cameraAnimatorOptions(90.0) {
        startValue(10.0)
      }
    ) {
      duration = 50L
    }
    val listenerTwo = LifecycleListener()
    cameraAnimationsPluginImpl.removeCameraAnimationsLifecycleListener(listenerOne)
    cameraAnimationsPluginImpl.addCameraAnimationsLifecycleListener(listenerTwo)
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimatorTwo)
    bearingAnimatorOne.start()
    bearingAnimatorTwo.start()

    shadowOf(getMainLooper()).idle()

    assertEquals(false, listenerOne.cancelling)
    assertEquals(true, listenerTwo.starting)
    assertEquals(true, listenerTwo.cancelling)
    assertEquals(true, listenerTwo.ending)
    assertEquals(true, listenerTwo.interrupting)
  }

  @Test
  fun registerOnlyCameraAnimatorsTest() {
    val pitch = createPitchAnimator(15.0, 0, 5)
    val bearing = createBearingAnimator(10.0, 0, 5)
    val animator = ValueAnimator.ofFloat(0.0f, 10.0f)
    cameraAnimationsPluginImpl.playAnimatorsSequentially(pitch, bearing, animator)
    assert(cameraAnimationsPluginImpl.animators.size == 2)
    cameraAnimationsPluginImpl.unregisterAllAnimators()
    cameraAnimationsPluginImpl.playAnimatorsTogether(pitch, bearing, animator)
    assert(cameraAnimationsPluginImpl.animators.size == 2)
  }

  @Test
  fun cancelStartedHighLevelAnimation() {
    val listener = CameraAnimatorListener()
    shadowOf(getMainLooper()).pause()
    val cancelable = cameraAnimationsPluginImpl.flyTo(
      CameraOptions.Builder()
        .center(Point.fromLngLat(VALUE, VALUE))
        .bearing(VALUE)
        .build(),
      mapAnimationOptions {
        duration(50L)
        animatorListener(listener)
      }
    )
    cancelable.cancel()
    shadowOf(getMainLooper()).idle()
    // expecting 1 (and not 2) because we register 1 high-level animator listener
    assertEquals(1, listener.startedCount)
    assertEquals(1, listener.canceledCount)
  }

  @Test
  fun anchorTest() {
    shadowOf(getMainLooper()).pause()
    val anchor = ScreenCoordinate(7.0, 7.0)
    val anchorAnimator = cameraAnimationsPluginImpl.createAnchorAnimator(
      cameraAnimatorOptions(anchor) {
        startValue(anchor)
      }
    ) {
      duration = 10L
    }
    cameraAnimationsPluginImpl.registerAnimators(anchorAnimator)
    anchorAnimator.start()
    shadowOf(getMainLooper()).idle()
    assertEquals(anchor, cameraAnimationsPluginImpl.anchor)
    var counter = 0
    cameraAnimationsPluginImpl.addCameraAnchorChangeListener { counter++ }
    cameraAnimationsPluginImpl.anchor = null
    assertEquals(null, cameraAnimationsPluginImpl.anchor)
    assertEquals(1, counter)
  }

  @Test
  fun nestedHighLevelAnimationListeners() {
    val listener = CameraAnimatorListener()
    shadowOf(getMainLooper()).pause()
    cameraAnimationsPluginImpl.flyTo(
      CameraOptions.Builder()
        .center(Point.fromLngLat(VALUE, VALUE))
        .bearing(VALUE)
        .build(),
      mapAnimationOptions {
        duration(50L)
        animatorListener(object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            cameraAnimationsPluginImpl.flyTo(
              CameraOptions.Builder()
                .center(Point.fromLngLat(VALUE, VALUE))
                .bearing(VALUE)
                .build(),
              mapAnimationOptions {
                duration(50L)
                animatorListener(listener)
              }
            )
          }
        })
      }
    )
    shadowOf(getMainLooper()).idle()
    assertEquals(1, listener.startedCount)
    assertEquals(1, listener.endedCount)
  }

  @Test
  fun firstUpdateTickTest() {
    val updateList = mutableListOf<CameraOptions>()
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      updateList.add(firstArg())
    }
    shadowOf(getMainLooper()).pause()
    val bearingAnimator = cameraAnimationsPluginImpl.createBearingAnimator(
      cameraAnimatorOptions(60.0) {
        startValue(20.0)
      }
    ) {
      duration = 50
    }
    val pitchAnimator = cameraAnimationsPluginImpl.createPitchAnimator(
      cameraAnimatorOptions(40.0) {
        startValue(10.0)
      }
    ) {
      duration = 50
    }
    cameraAnimationsPluginImpl.registerAnimators(
      bearingAnimator, pitchAnimator
    )
    bearingAnimator.start()
    pitchAnimator.start()
    shadowOf(getMainLooper()).idle()
    // Both, tick and bearing are updated to the start values at first tick.
    assertEquals(20.0, updateList.first().bearing!!, EPS)
    assertEquals(10.0, updateList.first().pitch!!, EPS)
  }

  @Test
  fun debugModeTrueTest() {
    cameraAnimationsPluginImpl.debugMode = true
    shadowOf(getMainLooper()).pause()
    cameraAnimationsPluginImpl.easeTo(
      cameraState.toCameraOptions(),
      mapAnimationOptions { duration(DURATION) }
    )
    shadowOf(getMainLooper()).idle()
    verify { logI(TAG, any()) }
  }

  @Test
  fun debugModeFalseTest() {
    cameraAnimationsPluginImpl.debugMode = false
    shadowOf(getMainLooper()).pause()
    cameraAnimationsPluginImpl.easeTo(
      cameraState.toCameraOptions(),
      mapAnimationOptions { duration(DURATION) }
    )
    shadowOf(getMainLooper()).idle()
    verify(exactly = 0) { logI(TAG, any()) }
  }

  @Test
  fun catchExceptionOnSetCameraTest() {
    val targetCameraOptions = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .pitch(50.0)
      .bearing(50.0)
      .zoom(5.0)
      .build()
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } throws Exception("Invalid camera options")

    cameraAnimationsPluginImpl.performMapJump(targetCameraOptions)

    verify(exactly = 1) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun skipsEmptyCameraOptions() {
    val targetCameraOptions = CameraOptions.Builder().build()

    cameraAnimationsPluginImpl.performMapJump(targetCameraOptions)

    verify(exactly = 0) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun skipsAlreadyAppliedCameraOptions() {
    val startCameraOptions = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .pitch(50.0)
      .bearing(50.0)
      .zoom(5.0)
      .build()

    val sameCameraOptions1 = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .bearing(50.0)
      .build()
    val sameCameraOptions2 = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .bearing(50.0)
      .build()
    val sameCameraOptions3 = CameraOptions.Builder()
      .zoom(5.0)
      .build()

    cameraAnimationsPluginImpl.onCameraMove(startCameraOptions.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(sameCameraOptions1)
    cameraAnimationsPluginImpl.performMapJump(sameCameraOptions2)
    cameraAnimationsPluginImpl.performMapJump(sameCameraOptions3)
    cameraAnimationsPluginImpl.performMapJump(startCameraOptions)

    verify(exactly = 0) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun doesNotSkipNewCameraOptions() {
    val startCameraOptions = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .pitch(50.0)
      .bearing(50.0)
      .zoom(5.0)
      .build()
    val newCameraOptions1 = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .bearing(60.0)
      .build()
    val newCameraOptions2 = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .bearing(20.0)
      .build()
    val newCameraOptions3 = CameraOptions.Builder()
      .zoom(1.0)
      .build()

    cameraAnimationsPluginImpl.onCameraMove(startCameraOptions.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(newCameraOptions1)

    cameraAnimationsPluginImpl.onCameraMove(newCameraOptions1.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(newCameraOptions2)

    cameraAnimationsPluginImpl.onCameraMove(newCameraOptions2.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(newCameraOptions3)

    cameraAnimationsPluginImpl.onCameraMove(newCameraOptions3.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(startCameraOptions)

    verify(exactly = 4) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun doesNotSkipCameraOptionsWithAnchor() {
    val startCameraOptions = CameraOptions.Builder()
      .center(Point.fromLngLat(50.0, 50.0))
      .pitch(50.0)
      .bearing(50.0)
      .zoom(5.0)
      .anchor(ScreenCoordinate(0.0, 5.0))
      .build()

    val newCameraOptions = CameraOptions.Builder()
      .anchor(ScreenCoordinate(0.0, 5.0))
      .build()

    cameraAnimationsPluginImpl.onCameraMove(startCameraOptions.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(startCameraOptions)

    cameraAnimationsPluginImpl.onCameraMove(startCameraOptions.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(newCameraOptions)

    verify(exactly = 2) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun skipsCameraOptionsWithLowPitch() {
    val startCameraOptionsPitchLow = CameraOptions.Builder()
      .pitch(50.0)
      .build()

    cameraAnimationsPluginImpl.onCameraMove(startCameraOptionsPitchLow.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(startCameraOptionsPitchLow)
    cameraAnimationsPluginImpl.performMapJump(startCameraOptionsPitchLow)
    cameraAnimationsPluginImpl.performMapJump(startCameraOptionsPitchLow)

    verify(exactly = 0) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun doesNotSkipCameraOptionsWithPitch60() {
    val startCameraOptionsPitchHigh = CameraOptions.Builder()
      .pitch(65.0)
      .build()

    cameraAnimationsPluginImpl.onCameraMove(startCameraOptionsPitchHigh.toCameraState())
    cameraAnimationsPluginImpl.performMapJump(startCameraOptionsPitchHigh)
    cameraAnimationsPluginImpl.performMapJump(startCameraOptionsPitchHigh)
    cameraAnimationsPluginImpl.performMapJump(startCameraOptionsPitchHigh)

    verify(exactly = 3) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun listenersAreNotified() {
    val cameraState = CameraOptions.Builder()
      .center(Point.fromLngLat(30.0, 20.0))
      .pitch(50.0)
      .bearing(60.0)
      .zoom(5.0)
      .padding(EdgeInsets(1.0, 2.0, 3.0, 4.0))
      .build()
      .toCameraState()

    var pitch = 0.0
    cameraAnimationsPluginImpl.addCameraPitchChangeListener { pitch = it }
    var edgeInsets: EdgeInsets? = null
    cameraAnimationsPluginImpl.addCameraPaddingChangeListener { edgeInsets = it }
    var center: Point? = null
    cameraAnimationsPluginImpl.addCameraCenterChangeListener { center = it }
    var bearing = 0.0
    cameraAnimationsPluginImpl.addCameraBearingChangeListener { bearing = it }
    var zoom = 0.0
    cameraAnimationsPluginImpl.addCameraZoomChangeListener { zoom = it }

    cameraAnimationsPluginImpl.onCameraMove(cameraState)

    assertEquals(50.0, pitch, EPS)
    assertEquals(60.0, bearing, EPS)
    assertEquals(5.0, zoom, EPS)
    assertEquals(Point.fromLngLat(30.0, 20.0), center)
    assertEquals(EdgeInsets(1.0, 2.0, 3.0, 4.0), edgeInsets)
  }

  class LifecycleListener : CameraAnimationsLifecycleListener {
    var starting = false
    var interrupting = false
    var cancelling = false
    var ending = false

    override fun onAnimatorStarting(
      type: CameraAnimatorType,
      animator: ValueAnimator,
      owner: String?
    ) {
      starting = true
    }

    override fun onAnimatorInterrupting(
      type: CameraAnimatorType,
      runningAnimator: ValueAnimator,
      runningAnimatorOwner: String?,
      newAnimator: ValueAnimator,
      newAnimatorOwner: String?
    ) {
      interrupting = true
    }

    override fun onAnimatorEnding(
      type: CameraAnimatorType,
      animator: ValueAnimator,
      owner: String?
    ) {
      ending = true
    }

    override fun onAnimatorCancelling(
      type: CameraAnimatorType,
      animator: ValueAnimator,
      owner: String?
    ) {
      cancelling = true
    }
  }

  class CameraUpdateAnimatorListener : ValueAnimator.AnimatorUpdateListener {

    var updateCount = 0L

    override fun onAnimationUpdate(animation: ValueAnimator) {
      updateCount += 1
    }
  }

  class CameraAnimatorListener : Animator.AnimatorListener {

    var started = false
    var startedCount = 0
    var ended = false
    var endedCount = 0
    var canceled = false
    var canceledCount = 0

    override fun onAnimationRepeat(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {
      endedCount += 1
      ended = true
    }

    override fun onAnimationCancel(animation: Animator?) {
      canceledCount += 1
      canceled = true
    }

    override fun onAnimationStart(animation: Animator?) {
      startedCount += 1
      started = true
    }
  }

  private fun createBearingAnimator(
    target: Double,
    animatorDelay: Long,
    animatorDuration: Long,
    owner: String? = null
  ) =
    CameraBearingAnimator(
      cameraAnimatorOptions(target) {
        startValue(0.0)
        owner?.let {
          owner(it)
        }
      },
      true
    ) {
      startDelay = animatorDelay
      duration = animatorDuration
    }

  private fun createPitchAnimator(
    target: Double,
    animatorDelay: Long,
    animatorDuration: Long,
    owner: String? = null
  ) =
    CameraPitchAnimator(
      cameraAnimatorOptions(target) {
        startValue(0.0)
        owner?.let {
          owner(it)
        }
      }
    ) {
      startDelay = animatorDelay
      duration = animatorDuration
    }

  companion object {
    private const val DURATION = 3000L
    const val VALUE = 10.0
    val cameraState: CameraState = CameraState(
      Point.fromLngLat(VALUE, VALUE),
      EdgeInsets(VALUE, VALUE, VALUE, VALUE),
      VALUE,
      VALUE,
      VALUE
    )

    internal fun CameraOptions.toCameraState(): CameraState {
      return CameraState(
        center ?: cameraState.center,
        padding ?: cameraState.padding,
        zoom ?: cameraState.zoom,
        bearing ?: cameraState.bearing,
        pitch ?: cameraState.pitch
      )
    }

    private fun CameraAnimationsPluginImpl.onCameraMove(cameraState: CameraState) {
      onCameraMove(
        lat = cameraState.center.latitude(),
        lon = cameraState.center.longitude(),
        zoom = cameraState.zoom,
        pitch = cameraState.pitch,
        bearing = cameraState.bearing,
        padding = cameraState.padding.let { insets ->
          arrayOf(insets.left, insets.top, insets.right, insets.bottom)
        }
      )
    }

    const val EPS = 0.000001
  }
}