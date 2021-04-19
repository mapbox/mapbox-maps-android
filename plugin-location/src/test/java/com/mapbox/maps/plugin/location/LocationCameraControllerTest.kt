package com.mapbox.maps.plugin.location

import android.animation.ValueAnimator
import android.graphics.RectF
import android.location.Location
import android.os.Handler
import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
import com.mapbox.maps.plugin.PLUGIN_GESTURE_CLASS_NAME
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.location.LocationComponentConstants.TRANSITION_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.location.listeneres.OnCameraTrackingChangedListener
import com.mapbox.maps.plugin.location.listeneres.OnLocationCameraTransitionListener
import com.mapbox.maps.plugin.location.modes.CameraMode
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

internal class LocationCameraControllerTest {
  private val mainHandler = mockk<Handler>()
  private val delegateProvider: MapDelegateProvider = mockk(relaxed = true)
  private val transformDelegate: MapTransformDelegate = mockk(relaxed = true)
  private val projectionDelegate: MapProjectionDelegate = mockk(relaxed = true)
  private val pluginProviderDelegate: MapPluginProviderDelegate = mockk(relaxed = true)

  private val gesturesPlugin: GesturesPlugin = mockk(relaxed = true)
  private val animationPlugin: CameraAnimationsPlugin = mockk(relaxed = true)

  private val moveGestureDetector: MoveGestureDetector = mockk(relaxed = true)
  private val onCameraTrackingChangedListener: OnCameraTrackingChangedListener =
    mockk(relaxed = true)
  private val initialGesturesManager: AndroidGesturesManager = mockk(relaxed = true)
  private val internalGesturesManager: AndroidGesturesManager = mockk(relaxed = true)
  private val options: LocationComponentOptions = mockk(relaxed = true)

  private lateinit var locationCameraController: LocationCameraController
  private val runnableSlot = slot<Runnable>()

  @Before
  fun setup() {
    every { mainHandler.post(capture(runnableSlot)) } answers {
      runnableSlot.captured.run()
      true
    }
    every { delegateProvider.mapTransformDelegate } returns transformDelegate
    every { delegateProvider.mapProjectionDelegate } returns projectionDelegate
    every { delegateProvider.mapPluginProviderDelegate } returns pluginProviderDelegate

    every { pluginProviderDelegate.getPlugin<GesturesPlugin>(PLUGIN_GESTURE_CLASS_NAME) } returns gesturesPlugin
    every {
      pluginProviderDelegate.getPlugin<CameraAnimationsPlugin>(
        PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
      )
    } returns animationPlugin
    every { gesturesPlugin.getGesturesManager() } returns initialGesturesManager

    every { internalGesturesManager.moveGestureDetector } returns moveGestureDetector
    every { initialGesturesManager.moveGestureDetector } returns moveGestureDetector
    locationCameraController = LocationCameraController(
      internalGesturesManager = internalGesturesManager,
      delegateProvider = delegateProvider,
      internalCameraTrackingChangedListener = onCameraTrackingChangedListener,
      handler = mainHandler
    )
  }

  @Test
  fun setCameraMode_mapTransitionsAreCancelled() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS)
    verify { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
  }

  @Test
  fun transition_customAnimationDisabled() {
    val location: Location = mockk()
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(false)
    locationCameraController.setCameraMode(CameraMode.TRACKING, location, 1200, 14.0, 13.0, 45.0, null)
    verify(exactly = 0) { animationPlugin.flyTo(any(), any()) }
  }

  @Test
  fun setCameraMode_gestureThresholdIsAdjusted() {
    val moveThreshold = 5f
    every { options.trackingInitialMoveThreshold() } returns moveThreshold
    every { options.trackingGesturesManagement() } returns true
    locationCameraController.initializeOptions(options)
    verify(exactly = 1) { moveGestureDetector.moveThresholdRect = any() }
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS)
    verify { moveGestureDetector.moveThreshold = moveThreshold }
    verify(exactly = 1) { moveGestureDetector.moveThresholdRect = any() }
  }

  @Test
  fun setCameraMode_gestureThresholdNotAdjustedWhenDisabled() {
    val moveThreshold = 5f
    every { options.trackingInitialMoveThreshold() } returns moveThreshold
    every { options.trackingGesturesManagement() } returns false
    locationCameraController.initializeOptions(options)

    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS)

    verify(exactly = 0) { moveGestureDetector.moveThreshold = moveThreshold }
    verify(exactly = 0) { moveGestureDetector.moveThreshold = 0f }
    verify(exactly = 0) { moveGestureDetector.moveThresholdRect = any() }
  }

  @Test
  fun setCameraMode_gestureThresholdIsResetWhenNotTracking() {
    every { options.trackingGesturesManagement() } returns true
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.NONE)
    verify(exactly = 1) { moveGestureDetector.moveThreshold = 0f } // one for initialization
    verify(exactly = 1) { moveGestureDetector.moveThresholdRect = null } // one for initialization
  }

  @Test
  fun setCameraMode_notTrackingAdjustsFocalPoint() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS)
    locationCameraController.setCameraMode(CameraMode.NONE)
    verify { gesturesPlugin.updateSettings(any()) }
  }

  @Test
  fun setCameraMode_setUserAnimationInProgress() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS)
    verify { transformDelegate.setUserAnimationInProgress(true) }
    locationCameraController.setCameraMode(CameraMode.NONE)
    verify { transformDelegate.setUserAnimationInProgress(true) }
  }

  @Test
  fun setCameraMode_trackingChangeListenerCameraDismissedIsCalled() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS)
    locationCameraController.setCameraMode(CameraMode.NONE)
    verify { onCameraTrackingChangedListener.onCameraTrackingDismissed() }
  }

  @Test
  fun setCameraMode_internalCameraTrackingChangeListenerIsCalled() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS_NORTH)
    verify { onCameraTrackingChangedListener.onCameraTrackingChanged(CameraMode.TRACKING_GPS_NORTH) }
  }

  @Test
  fun setCameraMode_doNotNotifyAboutDuplicates_NONE() {
    locationCameraController.initializeOptions(options)
    val cameraMode = CameraMode.NONE
    locationCameraController.setCameraMode(cameraMode)
    locationCameraController.setCameraMode(cameraMode)
    verify(exactly = 0) { onCameraTrackingChangedListener.onCameraTrackingChanged(cameraMode) }
  }

  @Test
  fun setCameraMode_doNotNotifyAboutDuplicates_TRACKING_GPS() {
    locationCameraController.initializeOptions(options)
    val cameraMode = CameraMode.TRACKING_GPS
    locationCameraController.setCameraMode(cameraMode)
    locationCameraController.setCameraMode(cameraMode)
    verify(exactly = 1) { onCameraTrackingChangedListener.onCameraTrackingChanged(cameraMode) }
  }

  @Test
  fun setCameraMode_cancelTransitionsWhenSet() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.NONE_COMPASS)
    verify(exactly = 1) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    locationCameraController.setCameraMode(CameraMode.NONE_GPS)
    verify(exactly = 2) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    verify(exactly = 3) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    locationCameraController.setCameraMode(CameraMode.TRACKING_COMPASS)
    verify(exactly = 4) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS)
    verify(exactly = 5) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    locationCameraController.setCameraMode(CameraMode.TRACKING_GPS_NORTH)
    verify(exactly = 6) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
  }

  @Test
  fun setCameraMode_dontCancelTransitionsWhenNoneSet() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.NONE)
    verify(exactly = 0) { animationPlugin.cancelAllAnimators() }
  }

  @Test
  fun gesturesManagement_enabled() {
    every { options.trackingGesturesManagement() } returns true
    locationCameraController.initializeOptions(options)
    verify {
      gesturesPlugin.setGesturesManager(
        internalGesturesManager,
        attachDefaultListeners = true,
        setDefaultMutuallyExclusives = true
      )
    }
  }

  @Test
  fun gesturesManagement_disabled() {
    every { options.trackingGesturesManagement() } returns false
    every { gesturesPlugin.getGesturesManager() } returns internalGesturesManager
    locationCameraController.initializeOptions(options)
    verify {
      gesturesPlugin.setGesturesManager(
        initialGesturesManager,
        attachDefaultListeners = true,
        setDefaultMutuallyExclusives = true
      )
    }
  }

  @Test
  fun gesturesManagement_optionNotChangedInitial() {
    every { options.trackingGesturesManagement() } returns false
    locationCameraController.initializeOptions(options)
    verify(exactly = 0) {
      gesturesPlugin.setGesturesManager(
        initialGesturesManager,
        attachDefaultListeners = true,
        setDefaultMutuallyExclusives = true
      )
    }
  }

  @Test
  fun gesturesManagement_optionNotChangedInternal() {
    every { options.trackingGesturesManagement() } returns true
    every { gesturesPlugin.getGesturesManager() } returns internalGesturesManager
    locationCameraController.initializeOptions(options)
    verify(exactly = 0) {
      gesturesPlugin.setGesturesManager(
        internalGesturesManager,
        attachDefaultListeners = true,
        setDefaultMutuallyExclusives = true
      )
    }
  }

  @Test
  fun gesturesManagement_moveGesture_notTracking() {
    every { moveGestureDetector.pointersCount } returns 1
    every { options.trackingGesturesManagement() } returns true
    val initial = 100f
    val multiFinger = 200f
    val multiFingerArea: RectF = mockk(relaxed = true)

    every { options.trackingInitialMoveThreshold() } returns initial
    every { options.trackingMultiFingerMoveThreshold() } returns multiFinger
    every { options.trackingMultiFingerProtectedMoveArea() } returns multiFingerArea

    locationCameraController.initializeOptions(options)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    verify(exactly = 1) { moveGestureDetector.moveThreshold = 0f }
    verify(exactly = 1) { moveGestureDetector.moveThresholdRect = null }
  }

  @Test
  fun gesturesManagement_moveGesture_singlePointer_tracking() {
    every { moveGestureDetector.pointersCount } returns 1
    every { options.trackingGesturesManagement() } returns true
    val initial = 100f

    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    every { options.trackingInitialMoveThreshold() } returns initial
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    verify(exactly = 1) { moveGestureDetector.moveThreshold = initial }
    verify(exactly = 0) { moveGestureDetector.moveThresholdRect = isNull(inverse = true) }
  }

  @Test
  fun gesturesManagement_moveGesture_singlePointer_tracking_duplicateCall() {
    every { moveGestureDetector.pointersCount } returns 1
    every { options.trackingGesturesManagement() } returns true
    val initial = 100f

    every { options.trackingInitialMoveThreshold() } returns initial
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    every { moveGestureDetector.moveThreshold } returns initial
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    verify(exactly = 1) { moveGestureDetector.moveThreshold = initial }
    verify(exactly = 0) { moveGestureDetector.moveThresholdRect = isNull(inverse = true) }
  }

  @Test
  fun gesturesManagement_moveGesture_singlePointer_tracking_thresholdMet() {
    every { moveGestureDetector.pointersCount } returns 1
    every { options.trackingGesturesManagement() } returns true
    val initial = 100f

    every { options.trackingInitialMoveThreshold() } returns initial
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    every { moveGestureDetector.moveThreshold } returns initial
    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    verify(exactly = 1) { moveGestureDetector.interrupt() }
    locationCameraController.onMoveListener.onMoveEnd(moveGestureDetector)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    verify(exactly = 2) { moveGestureDetector.interrupt() }
    locationCameraController.onMoveListener.onMoveEnd(moveGestureDetector)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    locationCameraController.onMoveListener.onMoveEnd(moveGestureDetector)
    verify(exactly = 2) { moveGestureDetector.interrupt() }
    verify { moveGestureDetector.moveThreshold = 0f }
  }

  @Test
  fun gesturesManagement_moveGesture_multiPointer_tracking() {
    every { moveGestureDetector.pointersCount } returns 2
    every { options.trackingGesturesManagement() } returns true
    val initial = 100f
    val multiFinger = 200f
    val multiFingerArea: RectF = mockk(relaxed = true)

    every { options.trackingInitialMoveThreshold() } returns initial
    every { options.trackingMultiFingerMoveThreshold() } returns multiFinger
    every { options.trackingMultiFingerProtectedMoveArea() } returns multiFingerArea

    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    verify(exactly = 1) { moveGestureDetector.moveThreshold = multiFinger }
    verify(exactly = 1) { moveGestureDetector.moveThresholdRect = multiFingerArea }
  }

  @Test
  fun gesturesManagement_moveGesture_multiPointer_tracking_duplicateCall() {
    every { moveGestureDetector.pointersCount } returns 2
    every { options.trackingGesturesManagement() } returns true
    val initial = 100f
    val multiFinger = 200f
    val multiFingerArea: RectF = mockk(relaxed = true)

    every { options.trackingInitialMoveThreshold() } returns initial
    every { options.trackingMultiFingerMoveThreshold() } returns multiFinger
    every { options.trackingMultiFingerProtectedMoveArea() } returns multiFingerArea

    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    every { moveGestureDetector.moveThreshold } returns multiFinger
    every { moveGestureDetector.moveThresholdRect } returns multiFingerArea
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    verify(exactly = 1) { moveGestureDetector.moveThreshold = multiFinger }
    verify(exactly = 1) { moveGestureDetector.moveThresholdRect = multiFingerArea }
  }

  @Test
  fun gesturesManagement_moveGesture_multiPointer_tracking_thresholdMet() {
    every { moveGestureDetector.pointersCount } returns 2
    every { options.trackingGesturesManagement() } returns true
    val initial = 100f
    val multiFinger = 200f
    val multiFingerArea: RectF = mockk(relaxed = true)

    every { options.trackingInitialMoveThreshold() } returns initial
    every { options.trackingMultiFingerMoveThreshold() } returns multiFinger
    every { options.trackingMultiFingerProtectedMoveArea() } returns multiFingerArea

    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    every { moveGestureDetector.moveThreshold } returns multiFinger
    every { moveGestureDetector.moveThresholdRect } returns multiFingerArea
    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    verify(exactly = 1) { moveGestureDetector.interrupt() }
    locationCameraController.onMoveListener.onMoveEnd(moveGestureDetector)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    verify(exactly = 2) { moveGestureDetector.interrupt() }
    locationCameraController.onMoveListener.onMoveEnd(moveGestureDetector)
    locationCameraController.onMoveListener.onMoveBegin(moveGestureDetector)
    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    locationCameraController.onMoveListener.onMoveEnd(moveGestureDetector)
    verify(exactly = 2) { moveGestureDetector.interrupt() }
    verify { moveGestureDetector.moveThreshold = 0f }
    verify { moveGestureDetector.moveThresholdRect = isNull() }
  }

  @Test
  fun onMove_notCancellingTransitionWhileNone() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.NONE)
    locationCameraController.onMoveListener.onMove(moveGestureDetector)

    verify(exactly = 0) { animationPlugin.cancelAllAnimators() }
    verify(exactly = 0) { moveGestureDetector.interrupt() }

    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    verify(exactly = 0) { animationPlugin.cancelAllAnimators() }
    verify(exactly = 0) { moveGestureDetector.interrupt() }
  }

  @Test
  fun onMove_cancellingTransitionWhileGps() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.TRACKING)
    locationCameraController.onMoveListener.onMove(moveGestureDetector)

    verify(exactly = 1) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    verify(exactly = 1) { moveGestureDetector.interrupt() }

    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    verify(exactly = 1) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    verify(exactly = 1) { moveGestureDetector.interrupt() }
  }

  @Test
  fun onMove_cancellingTransitionWhileBearing() {
    locationCameraController.initializeOptions(options)
    locationCameraController.setCameraMode(CameraMode.NONE_COMPASS)
    locationCameraController.onMoveListener.onMove(moveGestureDetector)

    verify(exactly = 1) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    verify(exactly = 1) { moveGestureDetector.interrupt() }

    locationCameraController.onMoveListener.onMove(moveGestureDetector)
    verify(exactly = 1) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
    verify(exactly = 1) { moveGestureDetector.interrupt() }
  }

  @Test
  fun transition_locationIsNull() {
    locationCameraController.initializeOptions(options)
    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      null,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    assertEquals(CameraMode.TRACKING, locationCameraController.cameraMode)
    verify { listener.onLocationCameraTransitionFinished(CameraMode.TRACKING) }
    verify(exactly = 0) { animationPlugin.flyTo(any(), any()) }
  }

  @Test
  fun transition_notTracking() {
    locationCameraController.initializeOptions(options)
    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    locationCameraController.setCameraMode(
      CameraMode.NONE,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    verify(exactly = 1) { listener.onLocationCameraTransitionFinished(CameraMode.NONE) }
    verify(exactly = 0) { animationPlugin.unregisterAnimators(cameraAnimators = emptyArray(), cancelAnimators = true) }
  }

  @Test
  fun transition_trackingChanged() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1000.0

    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    val cancelable = mockk<Cancelable>()
    every { animationPlugin.flyTo(any(), any()) } answers {
      listener.onLocationCameraTransitionFinished(CameraMode.TRACKING)
      cancelable
    }
    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    verify { listener.onLocationCameraTransitionFinished(CameraMode.TRACKING) }
    verify { animationPlugin.flyTo(any(), any()) }
  }

  @Test
  fun transition_trackingNotChanged() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1000.0

    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    val cancelable = mockk<Cancelable>()
    every {
      animationPlugin.flyTo(any(), any())
    } answers {
      listener.onLocationCameraTransitionFinished(CameraMode.TRACKING_GPS_NORTH)
      cancelable
    }
    locationCameraController.setCameraMode(
      CameraMode.TRACKING_GPS_NORTH,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    verify(exactly = 1) { listener.onLocationCameraTransitionFinished(CameraMode.TRACKING_GPS_NORTH) }
    verify(exactly = 1) { animationPlugin.flyTo(any(), any()) }
  }

  @Test
  fun transition_duplicateMode() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1000.0

    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    val cancelable = mockk<Cancelable>()
    every {
      animationPlugin.flyTo(any(), any())
    } answers {
      listener.onLocationCameraTransitionFinished(CameraMode.TRACKING)
      cancelable
    }
    assertEquals(CameraMode.TRACKING, locationCameraController.cameraMode)
    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    verify(exactly = 1) {
      animationPlugin.flyTo(any(), any())
    }
  }

  @Test
  fun transition_canceled() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1000.0

    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)

    val cancelable = mockk<Cancelable>()
    every { animationPlugin.flyTo(any(), any()) } answers {
      listener.onLocationCameraTransitionCanceled(CameraMode.TRACKING)
      cancelable
    }

    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    verify { listener.onLocationCameraTransitionCanceled(CameraMode.TRACKING) }
    verify { animationPlugin.flyTo(any(), any()) }
  }

  @Test
  fun transition_mapboxCallbackFinished() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    val cancelable = mockk<Cancelable>()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1000.0
    every { animationPlugin.flyTo(any(), any()) } returns cancelable
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    every { location.latitude } returns 1.0
    every { location.longitude } returns 1.0
    every { location.bearing } returns 30f
    every { location.altitude } returns 0.0

    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    val camera =
      CameraOptions.Builder().center(Point.fromLngLat(location.longitude, location.latitude))
        .build()

    val cameraOptionsSlot = slot<CameraOptions>()
    val transitionOptions = slot<MapAnimationOptions>()
    verify {
      animationPlugin.flyTo(
        capture(cameraOptionsSlot),
        capture(transitionOptions)
      )
    }
    assertEquals(TRANSITION_ANIMATION_DURATION_MS, transitionOptions.captured.duration)
    assertTrue(locationCameraController.isTransitioning)
    assertEquals(camera.toString(), cameraOptionsSlot.captured.toString())
    transitionOptions.captured.animatorListener?.onAnimationEnd(ValueAnimator())
    assertFalse(locationCameraController.isTransitioning)
    verify { listener.onLocationCameraTransitionFinished(CameraMode.TRACKING) }
  }

  @Test
  fun transition_mapboxCallbackFinishedImmediately() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1.0
    every { transformDelegate.setCamera(any()) } returns Unit
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    every { location.latitude } returns 1.0
    every { location.longitude } returns 1.0
    every { location.bearing } returns 30f
    every { location.altitude } returns 0.0

    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    val camera =
      CameraOptions.Builder().center(Point.fromLngLat(location.longitude, location.latitude))
        .build()

    val cameraOptionsSlot = slot<CameraOptions>()
    verify {
      transformDelegate.setCamera(capture(cameraOptionsSlot))
    }
    assertEquals(camera.toString(), cameraOptionsSlot.captured.toString())
    assertFalse(locationCameraController.isTransitioning)
    verify { listener.onLocationCameraTransitionFinished(CameraMode.TRACKING) }
  }

  @Test
  fun transition_mapboxCallbackCanceled() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1000.0
    val cancelable = mockk<Cancelable>()
    every { animationPlugin.flyTo(any(), any()) } returns cancelable
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    every { location.latitude } returns 1.0
    every { location.longitude } returns 1.0
    every { location.bearing } returns 30f
    every { location.altitude } returns 0.0

    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    val camera =
      CameraOptions.Builder().center(Point.fromLngLat(location.longitude, location.latitude))
        .build()

    val cameraOptionsSlot = slot<CameraOptions>()
    val transitionOptions = slot<MapAnimationOptions>()
    verify {
      animationPlugin.flyTo(
        capture(cameraOptionsSlot),
        capture(transitionOptions)
      )
    }
    assertEquals(TRANSITION_ANIMATION_DURATION_MS, transitionOptions.captured.duration)
    assertTrue(locationCameraController.isTransitioning)
    assertEquals(camera.toString(), cameraOptionsSlot.captured.toString())
    transitionOptions.captured.animatorListener?.onAnimationCancel(ValueAnimator())
    assertFalse(locationCameraController.isTransitioning)
    verify { listener.onLocationCameraTransitionCanceled(CameraMode.TRACKING) }
  }

  @Test
  fun transition_mapboxAnimateBearing() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1.0
    every { transformDelegate.setCamera(any()) } returns Unit
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    every { location.latitude } returns 1.0
    every { location.longitude } returns 1.0
    every { location.bearing } returns 30f
    every { location.altitude } returns 0.0

    locationCameraController.setCameraMode(
      CameraMode.TRACKING_GPS,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    val camera =
      CameraOptions.Builder().center(Point.fromLngLat(location.longitude, location.latitude))
        .bearing(30.0)
        .build()

    val cameraOptionsSlot = slot<CameraOptions>()
    verify {
      transformDelegate.setCamera(capture(cameraOptionsSlot))
    }
    assertEquals(camera.toString(), cameraOptionsSlot.captured.toString())
  }

  @Test
  fun transition_mapboxAnimateNorth() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1.0
    every { transformDelegate.setCamera(any()) } returns Unit
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val location: Location = mockk(relaxed = true)
    every { location.latitude } returns 1.0
    every { location.longitude } returns 1.0
    every { location.bearing } returns 30f
    every { location.altitude } returns 0.0

    locationCameraController.setCameraMode(
      CameraMode.TRACKING_GPS_NORTH,
      location,
      TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      listener
    )
    val camera =
      CameraOptions.Builder().center(Point.fromLngLat(location.longitude, location.latitude))
        .bearing(0.0)
        .build()

    val cameraOptionsSlot = slot<CameraOptions>()
    verify { transformDelegate.setCamera(capture(cameraOptionsSlot)) }
    assertEquals(camera.toString(), cameraOptionsSlot.captured.toString())
  }

  @Test
  fun transition_customAnimation() {
    every { transformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().center(
      Point.fromLngLat(
        0.0,
        0.0
      )
    ).bearing(0.0).build()
    every { projectionDelegate.getMetersPerPixelAtLatitude(any()) } returns 1000.0
    val cancelable = mockk<Cancelable>()
    every {
      animationPlugin.flyTo(any(), any())
    } returns cancelable
    locationCameraController.initializeOptions(options)
    locationCameraController.setEnabled(true)

    val location: Location = mockk(relaxed = true)
    every { location.latitude } returns 1.0
    every { location.longitude } returns 1.0
    every { location.bearing } returns 30f
    every { location.altitude } returns 0.0

    val camera =
      CameraOptions.Builder().center(Point.fromLngLat(location.longitude, location.latitude))
        .zoom(14.0)
        .bearing(13.0)
        .pitch(45.0)
        .build()

    locationCameraController.setCameraMode(
      CameraMode.TRACKING,
      location,
      1200,
      14.0,
      13.0,
      45.0,
      null
    )

    val cameraOptionsSlot = slot<CameraOptions>()
    val transitionOptions = slot<MapAnimationOptions>()
    verify {
      animationPlugin.flyTo(
        capture(cameraOptionsSlot),
        capture(transitionOptions)
      )
    }
    assertEquals(1200L, transitionOptions.captured.duration)
    assertEquals(camera.toString(), cameraOptionsSlot.captured.toString())
  }
}