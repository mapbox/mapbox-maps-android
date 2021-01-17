package com.mapbox.maps.plugin.location

import android.animation.Animator
import android.animation.ValueAnimator
import android.location.Location
import android.util.SparseArray
import android.view.animation.LinearInterpolator
import com.mapbox.common.ShadowLogger
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.location.LocationComponentConstants.*
import com.mapbox.maps.plugin.location.MapboxAnimator.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.registerInstanceFactory
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class LocationAnimatorCoordinatorTest {

  private lateinit var locationAnimatorCoordinator: LocationLayerAnimatorCoordinator
  private val animatorProvider: MapboxAnimatorProvider = mockk()
  private val animatorSetProvider: MapboxAnimatorSetProvider = mockk()
  private val animationsPlugin: CameraAnimationsPlugin = mockk(relaxed = true)
  private val projection: MapProjectionDelegate = mockk()
  private val initialLocation = Location("").apply {
    latitude = 0.0
    longitude = 0.0
    bearing = 0f
  }

  @Before
  fun setUp() {
    locationAnimatorCoordinator = LocationLayerAnimatorCoordinator(
      projection,
      animatorSetProvider,
      animatorProvider
    )
    configureAnimatorProvider()
    every { projection.getMetersPerPixelAtLatitude(any()) } answers { 1.0 }
    val startedAnimatorsSlot = slot<List<Animator>>()
    every { animatorSetProvider.startAnimation(capture(startedAnimatorsSlot), any(), any()) } answers {
      startedAnimatorsSlot.captured.forEach {
        it.start()
      }
    }

    locationAnimatorCoordinator.updateAnimatorListenerHolders(
      getListenerHoldersSet(
        ANIMATOR_LAYER_LATLNG,
        ANIMATOR_LAYER_GPS_BEARING,
        ANIMATOR_LAYER_COMPASS_BEARING,
        ANIMATOR_LAYER_ACCURACY
      )
    )
  }

  private fun configureAnimatorProvider() {
    // workaround https://github.com/mockk/mockk/issues/229#issuecomment-457816131
    registerInstanceFactory { AnimationsValueChangeListener<Float> {} }
    registerInstanceFactory { AnimationsValueChangeListener<Point> {} }
    val floatsSlot = slot<Array<Float>>()
    val listenerSlot = slot<AnimationsValueChangeListener<*>>()
    val maxFpsSlot = slot<Int>()
    every {
      animatorProvider.floatAnimator(
        capture(floatsSlot),
        capture(listenerSlot),
        capture(maxFpsSlot)
      )
    } answers {
      MapboxFloatAnimator(floatsSlot.captured, listenerSlot.captured, maxFpsSlot.captured)
    }

    val latLngsSlot = slot<Array<Point>>()
    every {
      animatorProvider.latLngAnimator(
        capture(latLngsSlot),
        capture(listenerSlot),
        capture(maxFpsSlot)
      )
    } answers {
      MapboxLatLngAnimator(latLngsSlot.captured, listenerSlot.captured, maxFpsSlot.captured)
    }
  }

  @Test
  fun feedNewLocation_animatorsAreCreated() {
    locationAnimatorCoordinator.feedNewLocation(arrayOf(Location("")), Location(""), 100L)
    assertTrue(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_LATLNG] != null)
    assertTrue(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING] != null)
  }

  @Test
  fun feedNewLocation_animatorValue() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 35f

    val previousLocation = Location("")
    location.latitude = 50.0
    location.longitude = 16.0
    location.bearing = 34f
    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), previousLocation, 100L)

    val layerLatLngTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_LATLNG]?.target as Point
    assertEquals(location.latitude, layerLatLngTarget.latitude(), 1E-5)
    assertEquals(location.longitude, layerLatLngTarget.longitude(), 1E-5)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(location.bearing, layerBearingTarget)
  }

  @Test
  fun feedNewLocation_animatorValue_multiplePoints() {
    val locationInter = Location("")
    locationInter.latitude = 51.1
    locationInter.longitude = 17.1
    locationInter.bearing = 35f
    val location = Location("")
    location.latitude = 51.2
    location.longitude = 17.2
    location.bearing = 36f
    locationAnimatorCoordinator.feedNewLocation(
      arrayOf(locationInter, location),
      initialLocation,
      100L
    )

    val layerLatLngTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_LATLNG]?.target as Point
    assertEquals(location.latitude, layerLatLngTarget.latitude(), 1E-5)
    assertEquals(location.longitude, layerLatLngTarget.longitude(), 1E-5)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(location.bearing, layerBearingTarget)

    verify {
      animatorProvider.latLngAnimator(
        arrayOf(
          Point.fromLngLat(initialLocation.longitude, initialLocation.latitude),
          Point.fromLngLat(locationInter.longitude, locationInter.latitude),
          Point.fromLngLat(location.longitude, location.latitude)
        ),
        any(), any()
      )
    }
    verify {
      animatorProvider.floatAnimator(
        arrayOf(initialLocation.bearing, locationInter.bearing, location.bearing), any(), any()
      )
    }
  }

  @Test
  fun feedNewLocation_animatorValue_multiplePoints_animationDuration() {
    every { projection.getMetersPerPixelAtLatitude(any()) } answers { 10000.0 } // disable snap
    val locationInter = Location("")
    locationInter.latitude = 51.1
    locationInter.longitude = 17.1
    locationInter.bearing = 35f

    val location = Location("")
    location.latitude = 51.2
    location.longitude = 17.2
    location.bearing = 36f

    val animationDuration = 100L
    locationAnimatorCoordinator.feedNewLocation(
      arrayOf(locationInter, location),
      initialLocation,
      animationDuration
    )

    verify {
      animatorSetProvider.startAnimation(
        eq(
          listOf(
            locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_LATLNG],
            locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]
          )
        ),
        any<LinearInterpolator>(),
        animationDuration
      )
    }
  }

  @Test
  fun feedNewLocation_animatorValue_multiplePoints_animationDuration_lookAhead() {
    every { projection.getMetersPerPixelAtLatitude(any()) } answers { 10000.0 } // disable snap

    val locationInter = Location("")
    locationInter.latitude = 51.1
    locationInter.longitude = 17.1
    locationInter.bearing = 35f

    val location = Location("")
    location.latitude = 51.2
    location.longitude = 17.2
    location.bearing = 36f
    location.time = System.currentTimeMillis() + 2000

    val animationDuration = 100L
    locationAnimatorCoordinator.feedNewLocation(
      arrayOf(locationInter, location),
      initialLocation,
      animationDuration
    )

    verify {
      animatorSetProvider.startAnimation(
        eq(
          listOf(
            locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_LATLNG],
            locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]
          )
        ),
        any<LinearInterpolator>(), animationDuration
      )
    }
  }

  @Test
  fun feedNewLocation_animatorValue_correctRotation_1() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 0f

    val animator = mockk<MapboxFloatAnimator>(relaxed = true)
    every { animator.animatedValue } returns 270f
    locationAnimatorCoordinator.puckAnimators.put(ANIMATOR_LAYER_GPS_BEARING, animator)

    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(360f, layerBearingTarget)
  }

  @Test
  fun feedNewLocation_animatorValue_correctRotation_2() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 90f

    val animator = mockk<MapboxFloatAnimator>(relaxed = true)
    every { animator.animatedValue } returns 280f
    locationAnimatorCoordinator.puckAnimators.put(ANIMATOR_LAYER_GPS_BEARING, animator)

    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(450f, layerBearingTarget)
  }

  @Test
  fun feedNewLocation_animatorValue_correctRotation_3() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 300f

    val animator = mockk<MapboxFloatAnimator>(relaxed = true)
    every { animator.animatedValue } returns 450f
    locationAnimatorCoordinator.puckAnimators.put(ANIMATOR_LAYER_GPS_BEARING, animator)

    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(-60f, layerBearingTarget)
  }

  @Test
  fun feedNewLocation_animatorValue_correctRotation_4() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 350f

    val animator = mockk<MapboxFloatAnimator>(relaxed = true)
    every { animator.animatedValue } returns 10f
    locationAnimatorCoordinator.puckAnimators.put(ANIMATOR_LAYER_GPS_BEARING, animator)

    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(-10f, layerBearingTarget)
  }

  @Test
  fun feedNewLocation_animatorValue_correctRotation_5() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 90f

    val animator = mockk<MapboxFloatAnimator>(relaxed = true)
    every { animator.animatedValue } returns -280f
    locationAnimatorCoordinator.puckAnimators.put(ANIMATOR_LAYER_GPS_BEARING, animator)

    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(90f, layerBearingTarget)
  }

  @Test
  fun feedNewLocation_animatorValue_correctRotation_6() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 270f

    val animator = mockk<MapboxFloatAnimator>(relaxed = true)
    every { animator.animatedValue } returns -350f
    locationAnimatorCoordinator.puckAnimators.put(ANIMATOR_LAYER_GPS_BEARING, animator)

    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(-90f, layerBearingTarget)
  }

  @Test
  fun feedNewLocation_isNorth_animatorsAreCreated() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 35f
    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    assertTrue(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_LATLNG] != null)
    assertTrue(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING] != null)
  }

  @Test
  fun feedNewLocation_isNorth_animatorValue() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 35f
    locationAnimatorCoordinator.feedNewLocation(arrayOf(location), initialLocation, 100L)

    val layerLatLngTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_LATLNG]?.target as Point
    assertEquals(layerLatLngTarget.latitude(), layerLatLngTarget.latitude(), 1E-5)

    val layerBearingTarget =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.target as Float
    assertEquals(location.bearing, layerBearingTarget)
  }

  @Test
  fun feedNewCompassBearing_animatorsAreCreated() {
    locationAnimatorCoordinator.feedNewCompassBearing(77f, 100L)
    assertTrue(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_COMPASS_BEARING] != null)
  }

  @Test
  fun feedNewCompassBearing_animatorValue() {
    val bearing = 77.0
    locationAnimatorCoordinator.feedNewCompassBearing(bearing.toFloat(), 100L)

    val layerBearingTarget = locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_COMPASS_BEARING]?.target as Float
    assertEquals(bearing.toFloat(), layerBearingTarget)
  }

  @Test
  fun feedNewAccuracyRadius_animatorsCreated() {
    locationAnimatorCoordinator.feedNewAccuracyRadius(150f, false)
    assertTrue(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_ACCURACY] != null)
  }

  @Test
  fun feedNewAccuracyRadius_animatorValue() {
    val accuracy = 150f
    locationAnimatorCoordinator.feedNewAccuracyRadius(accuracy, false)

    val layerAccuracy =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_ACCURACY]?.target as Float
    assertEquals(layerAccuracy, accuracy)
  }

  @Test
  fun feedNewAccuracyRadius_noAnimation_animatorsCreated() {
    locationAnimatorCoordinator.feedNewAccuracyRadius(150f, true)

    assertTrue(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_ACCURACY] != null)
  }

  @Test
  fun feedNewAccuracyRadius_noAnimation_animatorValue() {
    val accuracy = 150f
    locationAnimatorCoordinator.feedNewAccuracyRadius(accuracy, true)

    val layerAccuracy =
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_ACCURACY]?.target as Float
    assertEquals(layerAccuracy, accuracy)
  }

  @Test
  fun resetAllLayerAnimations_empty() {
    locationAnimatorCoordinator.resetAllLayerAnimations()
    assertTrue(locationAnimatorCoordinator.puckAnimators.size() == 0)
  }

  @Test
  fun updateListeners() {
    val listener: AnimationsValueChangeListener<*> = mockk(relaxed = true)
    val holder = AnimatorListenerHolder(ANIMATOR_LAYER_LATLNG, listener)
    val set = HashSet<AnimatorListenerHolder>().also {
      it.add(holder)
    }
    locationAnimatorCoordinator.updateAnimatorListenerHolders(set)

    val listener2: AnimationsValueChangeListener<*> = mockk(relaxed = true)
    val holder2 = AnimatorListenerHolder(ANIMATOR_LAYER_LATLNG, listener2)
    val listener3: AnimationsValueChangeListener<*> = mockk(relaxed = true)
    val holder3 = AnimatorListenerHolder(ANIMATOR_LAYER_ACCURACY, listener3)
    val set2 = HashSet<AnimatorListenerHolder>().also {
      it.add(holder2)
      it.add(holder3)
    }
    locationAnimatorCoordinator.updateAnimatorListenerHolders(set2)

    assertTrue(locationAnimatorCoordinator.listeners.size() == 2)
    assertTrue(locationAnimatorCoordinator.listeners.contains(listener2))
    assertTrue(locationAnimatorCoordinator.listeners.contains(listener3))
  }

  @Test
  fun updateListeners_listenerRemoved_animtorInvalid() {
    // setup accuracy animator
    val listener: AnimationsValueChangeListener<Float> = mockk(relaxUnitFun = true)
    val holder = AnimatorListenerHolder(ANIMATOR_LAYER_ACCURACY, listener)
    val set = HashSet<AnimatorListenerHolder>().also {
      it.add(holder)
    }
    locationAnimatorCoordinator.updateAnimatorListenerHolders(set)
    locationAnimatorCoordinator.feedNewAccuracyRadius(500f, true)

    // reset animator listeners which should make the previously created animator invalid
    val listener2: AnimationsValueChangeListener<*> = mockk()
    val holder2 = AnimatorListenerHolder(ANIMATOR_LAYER_LATLNG, listener2)

    val set2 = HashSet<AnimatorListenerHolder>().also {
      it.add(holder2)
    }
    locationAnimatorCoordinator.updateAnimatorListenerHolders(set2)

    // try pushing an update to verify it was ignored
    val valueAnimator: ValueAnimator = mockk()
    every { valueAnimator.animatedValue } returns 10f
    val animator = locationAnimatorCoordinator.puckAnimators.get(ANIMATOR_LAYER_ACCURACY)
    animator.onAnimationUpdate(valueAnimator)

    verify(exactly = 0) { listener.onNewAnimationValue(10f) }
  }

  @Test
  fun feedNewCompassBearing() {
    val animationDuration = 100L
    locationAnimatorCoordinator.compassAnimationEnabled = true
    locationAnimatorCoordinator.feedNewCompassBearing(77f, animationDuration)

    verify(exactly = 1) {
      animatorSetProvider.startAnimation(
        eq(listOf(locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_COMPASS_BEARING])),
        ofType(LinearInterpolator::class),
        eq(animationDuration)
      )
    }
  }

  @Test
  fun feedNewAccuracy_withAnimation() {
    locationAnimatorCoordinator.accuracyAnimationEnabled = true
    locationAnimatorCoordinator.feedNewAccuracyRadius(150f, false)

    val animators = mutableListOf<Animator>(
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_ACCURACY]
    )

    verify(exactly = 1) {
      animatorSetProvider.startAnimation(
        eq(animators),
        ofType(LinearInterpolator::class),
        eq(ACCURACY_RADIUS_ANIMATION_DURATION)
      )
    }
  }

  @Test
  fun feedNewAccuracy_withoutAnimation() {
    locationAnimatorCoordinator.accuracyAnimationEnabled = false
    locationAnimatorCoordinator.feedNewAccuracyRadius(150f, false)

    val animators = mutableListOf<Animator>(
      locationAnimatorCoordinator.puckAnimators[ANIMATOR_LAYER_ACCURACY]
    )

    verify(exactly = 1) {
      animatorSetProvider.startAnimation(
        eq(animators),
        ofType(LinearInterpolator::class),
        eq(0)
      )
    }
  }

  @Test
  fun maxFps_setter() {
    locationAnimatorCoordinator.maxAnimationFps = 5
    assertEquals(5, locationAnimatorCoordinator.maxAnimationFps)
  }

  @Test
  fun maxFps_moreThanZeroRequired() {
    locationAnimatorCoordinator.maxAnimationFps = 0
    assertEquals(Int.MAX_VALUE, locationAnimatorCoordinator.maxAnimationFps)
    locationAnimatorCoordinator.maxAnimationFps = -1
    assertEquals(Int.MAX_VALUE, locationAnimatorCoordinator.maxAnimationFps)
  }

  @Test
  fun maxFps_givenToAnimator() {
    locationAnimatorCoordinator.maxAnimationFps = 5
    locationAnimatorCoordinator.feedNewLocation(arrayOf(Location("")), initialLocation, 100L)
    verify { animatorProvider.floatAnimator(any(), any(), 5) }
  }

  @Test
  fun remove_gps_animator() {
    val animator = mockk<MapboxFloatAnimator>(relaxed = true)
    locationAnimatorCoordinator.puckAnimators.put(ANIMATOR_LAYER_GPS_BEARING, animator)

    locationAnimatorCoordinator.cancelAndRemoveGpsBearingAnimation()
    assertTrue(locationAnimatorCoordinator.puckAnimators.get(ANIMATOR_LAYER_GPS_BEARING) == null)
  }

  private fun getListenerHoldersSet(vararg animatorTypes: Int): Set<AnimatorListenerHolder> {
    return HashSet<AnimatorListenerHolder>().also {
      for (type in animatorTypes) {
        it.add(AnimatorListenerHolder(type, mockk(relaxUnitFun = true)))
      }
    }
  }
}

private fun <E> SparseArray<E>.contains(listener: AnimationsValueChangeListener<*>?): Boolean {
  for (i in 0 until this.size()) {
    val element = this.get(this.keyAt(i))
    if (element == listener) {
      return true
    }
  }
  return false
}