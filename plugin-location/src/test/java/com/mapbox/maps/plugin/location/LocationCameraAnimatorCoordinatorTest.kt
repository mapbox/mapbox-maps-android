package com.mapbox.maps.plugin.location

import android.animation.Animator
import android.animation.ValueAnimator
import android.location.Location
import android.os.Looper
import android.view.animation.LinearInterpolator
import com.mapbox.common.ShadowLogger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.animation.*
import com.mapbox.maps.plugin.animation.CameraAnimatorType.*
import com.mapbox.maps.plugin.animation.animator.*
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.location.LocationComponentConstants.*
import com.mapbox.maps.plugin.location.modes.CameraMode.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
@LooperMode(LooperMode.Mode.PAUSED)
class LocationCameraAnimatorCoordinatorTest {

  private lateinit var locationAnimatorCoordinator: LocationCameraAnimatorCoordinator
  private val cameraPosition =
    CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).pitch(0.0).zoom(0.0).bearing(0.0)
      .build()

  private val animatorSetProvider: MapboxAnimatorSetProvider = mockk()
  private val animationsPlugin: CameraAnimationsPlugin = mockk(relaxed = true)
  private val projection: MapProjectionDelegate = mockk()
  private val transform: MapTransformDelegate = mockk()

  @Before
  fun setUp() {
    locationAnimatorCoordinator = LocationCameraAnimatorCoordinator(
      projection,
      transform,
      animatorSetProvider,
      animationsPlugin
    )
    every { projection.getMetersPerPixelAtLatitude(any()) } answers { 1.0 }
    val startedAnimatorsSlot = slot<List<Animator>>()
    every {
      animatorSetProvider.startAnimation(
        capture(startedAnimatorsSlot),
        any(),
        any()
      )
    } answers {
      startedAnimatorsSlot.captured.forEach {
        it.start()
      }
    }

    val optionsZoom = slot<CameraAnimatorOptions<Double>>()
    every {
      animationsPlugin.createZoomAnimator(
        capture(optionsZoom)
      )
    } answers {
      val animator = ValueAnimator.ofObject(Evaluators.DOUBLE, optionsZoom.captured.startValue, *optionsZoom.captured.targets)
      animator.interpolator = LinearInterpolator()
      animator
    }
    val optionsPitch = slot<CameraAnimatorOptions<Double>>()
    every {
      animationsPlugin.createPitchAnimator(
        capture(optionsPitch)
      )
    } answers {
      ValueAnimator.ofObject(Evaluators.DOUBLE, optionsPitch.captured.startValue, *optionsPitch.captured.targets)
    }
    val optionsPadding = slot<CameraAnimatorOptions<EdgeInsets>>()
    every {
      animationsPlugin.createPaddingAnimator(
        capture(optionsPadding)
      )
    } answers {
      ValueAnimator.ofObject(
        Evaluators.EDGE_INSET,
        optionsPadding.captured.startValue,
        *optionsPadding.captured.targets
      )
    }
    val optionsBearing = slot<CameraAnimatorOptions<Double>>()
    every {
      animationsPlugin.createBearingAnimator(
        capture(optionsBearing)
      )
    } answers {
      ValueAnimator.ofObject(Evaluators.DOUBLE, optionsBearing.captured.startValue, *optionsBearing.captured.targets)
    }
    val optionsCenter = slot<CameraAnimatorOptions<Point>>()
    every {
      animationsPlugin.createCenterAnimator(
        capture(optionsCenter)
      )
    } answers {
      ValueAnimator.ofObject(PointEvaluator(), optionsCenter.captured.startValue, *optionsCenter.captured.targets)
    }
  }

  @Test
  fun feedNewLocation_animatorsAreCreated() {
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(Location("")),
      cameraPosition,
      100L,
      TRACKING_GPS
    )
    assertTrue(locationAnimatorCoordinator.cameraAnimators[CENTER] != null)
    assertTrue(locationAnimatorCoordinator.cameraAnimators[BEARING] != null)
  }

  @Test
  fun feedNewLocation_animatorValue() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 35f
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(location),
      cameraPosition,
      100L,
      TRACKING_GPS
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    val cameraLatLngTarget =
      (locationAnimatorCoordinator.cameraAnimators[CENTER]?.animator)!!.animatedValue as Point
    assertEquals(location.latitude, cameraLatLngTarget.latitude(), 1E-5)
    assertEquals(location.longitude, cameraLatLngTarget.longitude(), 1E-5)

    locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!.currentPlayTime = 9000

    val cameraBearingTarget =
      (locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator)!!.animatedValue as Double
    assertEquals(location.bearing, cameraBearingTarget.toFloat())
  }

  @Test
  fun feedNewLocation_animatorValue_multiplePoints() {
    val previousLocation = Location("")
    previousLocation.latitude = 0.0
    previousLocation.longitude = 0.0
    previousLocation.bearing = 0f

    val locationInter = Location("")
    locationInter.latitude = 51.1
    locationInter.longitude = 17.1
    locationInter.bearing = 35f
    val location = Location("")
    location.latitude = 51.2
    location.longitude = 17.2
    location.bearing = 36f
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(locationInter, location),
      cameraPosition,
      100L,
      TRACKING_GPS
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    val cameraLatLngTarget =
      (locationAnimatorCoordinator.cameraAnimators[CENTER]?.animator!!.animatedValue) as Point
    assertEquals(location.latitude, cameraLatLngTarget.latitude(), 1E-5)
    assertEquals(location.longitude, cameraLatLngTarget.longitude(), 1E-5)

    locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!.currentPlayTime = 9000

    val cameraBearingTarget =
      (locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!.animatedValue) as Double
    assertEquals(location.bearing, cameraBearingTarget.toFloat())
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
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(locationInter, location),
      cameraPosition,
      animationDuration,
      TRACKING_GPS
    )

    val cameraCenterAnimator = locationAnimatorCoordinator.cameraAnimators[CENTER]?.animator!!
    val cameraBearingAnimator = locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!
    verify {
      animatorSetProvider.startAnimation(
        eq(
          listOf(cameraCenterAnimator, cameraBearingAnimator)
        ),
        any<LinearInterpolator>(), animationDuration
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
    val animationDuration = 1000L
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(locationInter, location),
      cameraPosition,
      animationDuration,
      TRACKING_GPS
    )

    val cameraCenterAnimator = locationAnimatorCoordinator.cameraAnimators[CENTER]?.animator!!
    val cameraBearingAnimator = locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!
    verify {
      animatorSetProvider.startAnimation(
        eq(
          listOf(cameraCenterAnimator, cameraBearingAnimator)
        ),
        any<LinearInterpolator>(), animationDuration
      )
    }
  }

  @Test
  fun feedNewLocation_isNorth_animatorsAreCreated() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 35f
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(location),
      cameraPosition,
      100L,
      TRACKING_GPS
    )

    assertTrue(locationAnimatorCoordinator.cameraAnimators[CENTER] != null)
    assertTrue(locationAnimatorCoordinator.cameraAnimators[BEARING] != null)
  }

  @Test
  fun feedNewLocation_isNorth_animatorValue() {
    val location = Location("")
    location.latitude = 51.0
    location.longitude = 17.0
    location.bearing = 35f
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(location),
      cameraPosition,
      100L,
      TRACKING_GPS_NORTH
    )

    val cameraLatLngTarget =
      locationAnimatorCoordinator.cameraAnimators[CENTER]?.animator!!.animatedValue as Point
    assertEquals(cameraLatLngTarget.latitude(), cameraLatLngTarget.latitude(), 1E-5)

    val cameraBearingTarget =
      locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!.animatedValue as Double
    assertEquals(0f, cameraBearingTarget.toFloat())
  }

  @Test
  fun feedNewCompassBearing_animatorsAreCreated() {
    locationAnimatorCoordinator.feedNewCompassCameraBearing(
      77f,
      cameraPosition,
      100L,
      TRACKING_COMPASS
    )
    assertTrue(locationAnimatorCoordinator.cameraAnimators[BEARING] != null)
  }

  @Test
  fun feedNewCompassBearing_animatorValue() {
    val bearing = 77.0
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCompassCameraBearing(
      bearing.toFloat(),
      cameraPosition,
      100L,
      TRACKING_COMPASS
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    val cameraBearingTarget =
      locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!.animatedValue as Double
    assertEquals(bearing, cameraBearingTarget, 0.000001)
  }

  @Test
  fun feedNewZoomLevel_animatorsCreated() {
    locationAnimatorCoordinator.feedNewCameraZoomLevel(
      15.0,
      cameraPosition,
      DEFAULT_TRACKING_ZOOM_ANIM_DURATION,
      null
    )

    assertTrue(locationAnimatorCoordinator.cameraAnimators[ZOOM] != null)
  }

  @Test
  fun feedNewZoomLevel_animatorValue() {
    val zoom = 15.0
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraZoomLevel(
      zoom,
      cameraPosition,
      DEFAULT_TRACKING_ZOOM_ANIM_DURATION,
      null
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    val animator = locationAnimatorCoordinator.cameraAnimators[ZOOM]?.animator!!
    assertEquals(zoom, animator.animatedValue as Double, 0.00000001)
    verify {
      animatorSetProvider.startAnimation(
        eq(listOf(animator)),
        any<LinearInterpolator>(),
        DEFAULT_TRACKING_ZOOM_ANIM_DURATION
      )
    }
  }

  @Test
  fun feedNewPadding_animatorsCreated() {
    locationAnimatorCoordinator.feedNewCameraPadding(
      EdgeInsets(100.0, 200.0, 300.0, 400.0),
      cameraPosition,
      DEFAULT_TRACKING_PADDING_ANIM_DURATION,
      null
    )

    assertTrue(locationAnimatorCoordinator.cameraAnimators[PADDING] != null)
  }

  @Test
  fun feedNewPadding_animatorValue() {
    val padding = EdgeInsets(100.0, 200.0, 300.0, 400.0)
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraPadding(
      padding,
      cameraPosition,
      DEFAULT_TRACKING_PADDING_ANIM_DURATION,
      null
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    val animator = locationAnimatorCoordinator.cameraAnimators[PADDING]
    assertTrue(padding == (animator?.animator!!.animatedValue as EdgeInsets))
    verify {
      animatorSetProvider.startAnimation(
        eq(listOf(animator.animator)),
        any<LinearInterpolator>(),
        DEFAULT_TRACKING_PADDING_ANIM_DURATION
      )
    }
  }

  @Test
  fun feedNewTiltLevel_animatorsCreated() {
    locationAnimatorCoordinator.feedNewCameraPitch(
      30.0,
      cameraPosition,
      DEFAULT_TRACKING_TILT_ANIM_DURATION,
      null
    )

    assertTrue(locationAnimatorCoordinator.cameraAnimators[PITCH] != null)
  }

  @Test
  fun feedNewPitchLevel_animatorValue() {
    val pitch = 30.0
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraPitch(
      pitch,
      cameraPosition,
      DEFAULT_TRACKING_TILT_ANIM_DURATION,
      null
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    val animator =
      locationAnimatorCoordinator.cameraAnimators[PITCH]?.animator!!
    assertEquals(pitch, animator.animatedValue as Double, 0.00000001)
    verify {
      animatorSetProvider.startAnimation(
        eq(listOf(animator)),
        any<LinearInterpolator>(),
        DEFAULT_TRACKING_TILT_ANIM_DURATION
      )
    }
  }

  @Test
  fun cancelAllAnimators() {
    locationAnimatorCoordinator.feedNewCameraLocation(
      arrayOf(Location("")),
      cameraPosition,
      100L,
      TRACKING_GPS
    )

    every { animationsPlugin.cancelAllAnimators() } answers {
      for (animators in locationAnimatorCoordinator.cameraAnimators.values) {
        animators.animator.cancel()
      }
    }
    animationsPlugin.cancelAllAnimators()
    assertFalse((locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator!!).isRunning)
  }

  @Test
  fun cancelZoomAnimators() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraZoomLevel(
      15.0,
      cameraPosition,
      DEFAULT_TRACKING_ZOOM_ANIM_DURATION,
      null
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    locationAnimatorCoordinator.cancelZoomAnimation()
    assertFalse((locationAnimatorCoordinator.cameraAnimators[ZOOM]?.animator as ValueAnimator).isStarted)
  }

  @Test
  fun cancelPaddingAnimators() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraPadding(
      EdgeInsets(100.0, 200.0, 300.0, 400.0),
      cameraPosition,
      DEFAULT_TRACKING_PADDING_ANIM_DURATION,
      null
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    locationAnimatorCoordinator.cancelPaddingAnimation()
    assertFalse((locationAnimatorCoordinator.cameraAnimators[PADDING]?.animator as ValueAnimator).isStarted)
  }

  @Test
  fun cancelPitchAnimation() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    locationAnimatorCoordinator.feedNewCameraPitch(
      30.0,
      cameraPosition,
      DEFAULT_TRACKING_TILT_ANIM_DURATION,
      null
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    locationAnimatorCoordinator.cancelPitchAnimation()
    assertFalse((locationAnimatorCoordinator.cameraAnimators[PITCH]?.animator as ValueAnimator).isStarted)
  }

  @Test
  fun resetAllCameraAnimations_empty() {
    locationAnimatorCoordinator.resetAllCameraAnimations(cameraPosition, false)
    assertTrue(locationAnimatorCoordinator.cameraAnimators.size == 0)
  }

  @Test
  fun feedNewCompassBearing() {
    val animationDuration = 100L
    locationAnimatorCoordinator.feedNewCompassCameraBearing(
      77f,
      cameraPosition,
      animationDuration,
      TRACKING_COMPASS
    )
    val bearingAnimator =
      locationAnimatorCoordinator.cameraAnimators[BEARING]?.animator as ValueAnimator
    verify(exactly = 1) {
      animatorSetProvider.startAnimation(
        eq(listOf(bearingAnimator)),
        ofType(LinearInterpolator::class),
        eq(animationDuration)
      )
    }
  }
}