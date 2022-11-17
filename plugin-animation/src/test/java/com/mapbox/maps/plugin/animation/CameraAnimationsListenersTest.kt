package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Looper
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImplTest.Companion.toCameraState
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.animator.CameraBearingAnimator
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import io.mockk.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class CameraAnimationsListenersTest {

  private lateinit var cameraAnimationsPluginImpl: CameraAnimationsPluginImpl
  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate

  private class Listener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {}

    override fun onAnimationCancel(animation: Animator?) {}

    override fun onAnimationRepeat(animation: Animator?) {}
  }

  @Before
  fun setUp() {
    ShadowLog.stream = System.out
    val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
    mapCameraManagerDelegate = mockk(relaxed = true)
    mapTransformDelegate = mockk(relaxed = true)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    mockkObject(CameraTransform)
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { delegateProvider.mapTransformDelegate } returns mapTransformDelegate
    cameraAnimationsPluginImpl = CameraAnimationsPluginImpl().apply {
      onDelegateProvider(delegateProvider)
    }
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    unmockkObject(CameraTransform)
  }

  @Test
  fun testAddListenerBeforeRegister() {
    val bearingAnimator = CameraBearingAnimator(CameraAnimatorOptions.Builder(1.0).build(), true)
    val listener1 = Listener()
    val listener2 = Listener()
    bearingAnimator.addListener(listener1)
    bearingAnimator.addListener(listener2)
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    Assert.assertEquals(3, bearingAnimator.listeners.size)
    Assert.assertEquals(
      "User animator should be second (first one is always internal)",
      listener1,
      bearingAnimator.listeners[1]
    )
    Assert.assertEquals(
      "User animator should be third (first one is always internal)",
      listener2,
      bearingAnimator.listeners[2]
    )
  }

  @Test
  fun testAddListenerAfterRegister() {
    val bearingAnimator = CameraBearingAnimator(CameraAnimatorOptions.Builder(1.0).build(), true)
    val listener1 = Listener()
    val listener2 = Listener()
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    bearingAnimator.addListener(listener1)
    bearingAnimator.addListener(listener2)
    Assert.assertEquals(3, bearingAnimator.listeners.size)
    Assert.assertEquals(
      "User animator should be second (first one is always internal)",
      listener1,
      bearingAnimator.listeners[1]
    )
    Assert.assertEquals(
      "User animator should be third (first one is always internal)",
      listener2,
      bearingAnimator.listeners[2]
    )
  }

  @Test
  fun testAddAndRemoveUserListeners() {
    val bearingAnimator = CameraBearingAnimator(CameraAnimatorOptions.Builder(1.0).build(), true)
    val listener1 = Listener()
    bearingAnimator.addListener(listener1)
    bearingAnimator.removeListener(listener1)
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    Assert.assertEquals(1, bearingAnimator.listeners.size)
    Assert.assertNotEquals(listener1, bearingAnimator.listeners[0])

    bearingAnimator.addListener(listener1)
    bearingAnimator.addListener(listener1)
    Assert.assertEquals(3, bearingAnimator.listeners.size)
    Assert.assertEquals(listener1, bearingAnimator.listeners[1])
    Assert.assertEquals(listener1, bearingAnimator.listeners[2])

    bearingAnimator.removeListener(listener1)
    Assert.assertEquals(2, bearingAnimator.listeners.size)
    Assert.assertNotEquals(listener1, bearingAnimator.listeners[0])
    Assert.assertEquals(listener1, bearingAnimator.listeners[1])
  }

  @Test
  fun testAddAndRemoveInternalListeners() {
    val bearingAnimator = CameraBearingAnimator(CameraAnimatorOptions.Builder(1.0).build(), true)
    val listener1 = Listener()
    bearingAnimator.addListener(listener1)
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    cameraAnimationsPluginImpl.unregisterAnimators(bearingAnimator)

    Assert.assertEquals(1, bearingAnimator.listeners.size)
    Assert.assertEquals(
      "User animator should be first (internal should be removed)",
      listener1,
      bearingAnimator.listeners[0]
    )
    val listener2 = Listener()
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    bearingAnimator.addListener(listener2)

    Assert.assertEquals(3, bearingAnimator.listeners.size)
    Assert.assertEquals(
      "User animator should be second one (first one is always internal)",
      listener1,
      bearingAnimator.listeners[1]
    )
    Assert.assertEquals(
      "User animator should be third one (first one is always internal)",
      listener2,
      bearingAnimator.listeners[2]
    )

    cameraAnimationsPluginImpl.unregisterAllAnimators()

    Assert.assertEquals(2, bearingAnimator.listeners.size)
    Assert.assertEquals(
      "User animator should be first (internal should be removed)",
      listener1,
      bearingAnimator.listeners[0]
    )
    Assert.assertEquals(
      "User animator should be first (internal should be removed)",
      listener2,
      bearingAnimator.listeners[1]
    )
  }

  @Test
  fun testAddUpdateListenerBeforeRegister() {
    val bearingAnimator = CameraBearingAnimator(
      CameraAnimatorOptions.Builder(100.0).build(),
      true
    ) {
      duration = 0
    }
    val valuesList = mutableListOf<Int>()
    val listener1 = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(1)
    }
    val listener2 = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(2)
    }
    bearingAnimator.addUpdateListener(listener1)
    bearingAnimator.addUpdateListener(listener2)
    cameraAnimationsPluginImpl.addCameraBearingChangeListener { valuesList.add(0) }
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertEquals(5, valuesList.size)
    Assert.assertArrayEquals(intArrayOf(0, 1, 2), valuesList.slice(0..2).toIntArray())
  }

  @Test
  fun testAddUpdateListenerAfterRegister() {
    val bearingAnimator = CameraBearingAnimator(
      CameraAnimatorOptions.Builder(100.0).build(),
      true
    ) {
      duration = 0
    }
    val valuesList = mutableListOf<Int>()
    val listener1 = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(1)
    }
    val listener2 = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(2)
    }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener { valuesList.add(0) }
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    bearingAnimator.addUpdateListener(listener1)
    bearingAnimator.addUpdateListener(listener2)

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertEquals(5, valuesList.size)
    Assert.assertArrayEquals(intArrayOf(0, 1, 2), valuesList.slice(0..2).toIntArray())
  }

  @Test
  fun testAddAndRemoveUserUpdateListeners() {
    val bearingAnimator = CameraBearingAnimator(
      CameraAnimatorOptions.Builder(100.0).build(),
      true
    ) {
      duration = 0
    }
    val valuesList = mutableListOf<Int>()
    val listener = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(1)
    }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener { valuesList.add(0) }
    bearingAnimator.addUpdateListener(listener)
    bearingAnimator.removeUpdateListener(listener)
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertArrayEquals(intArrayOf(0), valuesList.toIntArray())
  }

  @Test
  fun testAddTwoSameUserUpdateListeners() {
    val bearingAnimator = CameraBearingAnimator(
      CameraAnimatorOptions.Builder(100.0).build(),
      true
    ) {
      duration = 0
    }
    val valuesList = mutableListOf<Int>()
    val listener = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(1)
    }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener { valuesList.add(0) }
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)

    bearingAnimator.addUpdateListener(listener)
    bearingAnimator.addUpdateListener(listener)

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertEquals(3, valuesList.size)
    Assert.assertArrayEquals(intArrayOf(0, 1, 1), valuesList.slice(0..2).toIntArray())
  }

  @Test
  fun testAddAndRemoveInternalUpdateListeners() {
    val bearingAnimator = CameraBearingAnimator(
      CameraAnimatorOptions.Builder(100.0).build(),
      true
    ) {
      duration = 0
    }
    val valuesList = mutableListOf<Int>()
    val listener1 = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(1)
    }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener { valuesList.add(0) }
    bearingAnimator.addUpdateListener(listener1)
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    cameraAnimationsPluginImpl.unregisterAnimators(bearingAnimator)

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertEquals(0, valuesList.size)

    val listener2 = ValueAnimator.AnimatorUpdateListener {
      valuesList.add(2)
    }
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    bearingAnimator.addUpdateListener(listener2)

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertEquals(5, valuesList.size)
    Assert.assertArrayEquals(intArrayOf(0, 1, 2), valuesList.slice(0..2).toIntArray())
    valuesList.clear()

    cameraAnimationsPluginImpl.unregisterAllAnimators()

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertEquals(0, valuesList.size)
  }

  @Test
  fun testRemoveAllListeners() {
    val bearingAnimator = CameraBearingAnimator(
      CameraAnimatorOptions.Builder(1.0).build(),
      true
    )
    val listener1 = Listener()
    val listener2 = Listener()
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    bearingAnimator.addListener(listener1)
    bearingAnimator.addListener(listener2)

    bearingAnimator.removeAllListeners()
    Assert.assertEquals(1, bearingAnimator.listeners.size)
    Assert.assertNotEquals(listener1, bearingAnimator.listeners[0])
    Assert.assertNotEquals(listener2, bearingAnimator.listeners[0])
  }

  @Test
  fun testRemoveAllUpdateListeners() {
    var cameraPosition = CameraOptions.Builder().build()
    val bearingAnimator = CameraBearingAnimator(
      cameraAnimatorOptions(100.0) {
        startValue(0.0)
      },
      true
    ) {
      duration = 50
    }
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } answers {
      cameraPosition = firstArg()
    }
    every { mapCameraManagerDelegate.cameraState } answers {
      cameraPosition.toCameraState()
    }
    var bearing = 0.0
    cameraAnimationsPluginImpl.registerAnimators(bearingAnimator)
    cameraAnimationsPluginImpl.addCameraBearingChangeListener { bearing = it }
    var userBearing = bearing
    val listener1 = ValueAnimator.AnimatorUpdateListener {
      userBearing = it.animatedValue as Double
    }
    val listener2 = ValueAnimator.AnimatorUpdateListener {
      userBearing = it.animatedValue as Double
    }
    bearingAnimator.addUpdateListener(listener1)
    bearingAnimator.addUpdateListener(listener2)

    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.start()
    bearingAnimator.removeAllUpdateListeners()
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    Assert.assertEquals(0.0, userBearing, EPS)
    Assert.assertEquals(100.0, bearing, EPS)
  }

  companion object {
    private const val EPS = 0.000001
  }
}