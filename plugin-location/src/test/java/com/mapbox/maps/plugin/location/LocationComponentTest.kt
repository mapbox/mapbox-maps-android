package com.mapbox.maps.plugin.location

import android.content.Context
import android.content.res.Resources
import android.location.Location
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.common.ShadowLogger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.location.LocationComponentConstants.TRANSITION_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.location.listeneres.*
import com.mapbox.maps.plugin.location.modes.CameraMode
import com.mapbox.maps.plugin.location.modes.RenderMode
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class LocationComponentTest {
  private lateinit var locationComponent: LocationComponentPlugin

  private var locationComponentOptions: LocationComponentOptions = mockk(relaxed = true)

  private val delegateProvider: MapDelegateProvider = mockk(relaxed = true)

  private val transform: MapTransformDelegate = mockk(relaxed = true)

  private val context: Context = mockk(relaxed = true)

  private val locationEngine: LocationEngine = mockk(relaxed = true)

  private val locationEngineRequest: LocationEngineRequest = mockk(relaxed = true)

  private val currentListener: LocationComponentPlugin.CurrentLocationEngineCallback =
    mockk(relaxed = true)

  private val lastListener: LocationComponentPlugin.LastLocationEngineCallback =
    mockk(relaxed = true)

  private val compassEngine: CompassEngine = mockk(relaxed = true)

  private val locationLayerController: LocationLayerController = mockk(relaxed = true)

  private val locationCameraController: LocationCameraController = mockk(relaxed = true)

  private val locationAnimatorCoordinator: LocationLayerAnimatorCoordinator = mockk(relaxed = true)

  private val staleStateManager: StaleStateManager = mockk(relaxed = true)

  private val locationEngineProvider: LocationComponentPlugin.InternalLocationEngineProvider =
    mockk(relaxed = true)

  private val style: StyleManagerInterface = mockk(relaxed = true)

  private val gesturesPlugin: GesturesPlugin = mockk(relaxed = true)

  private val developerAnimationListeners = mutableListOf<OnDeveloperAnimationListener>()

  @Before
  fun before() {
    every { delegateProvider.mapPluginProviderDelegate.getPlugin(any<Class<GesturesPlugin>>()) } returns gesturesPlugin
    every { locationEngineProvider.getBestLocationEngine(context) } returns locationEngine
    every { delegateProvider.getStyle(any()) } answers {
      firstArg<(StyleManagerInterface) -> Unit>().invoke(style)
    }
    val styleStateDelegate = mockk<MapStyleStateDelegate>()
    every { delegateProvider.styleStateDelegate } returns styleStateDelegate
    every { styleStateDelegate.isFullyLoaded() } returns true

    locationComponent = LocationComponentPlugin(
      delegateProvider = delegateProvider,
      developerAnimationListeners = developerAnimationListeners,
      currentListener = currentListener,
      lastListener = lastListener,
      locationLayerController = locationLayerController,
      locationCameraController = locationCameraController,
      locationAnimatorCoordinator = locationAnimatorCoordinator,
      staleStateManager = staleStateManager,
      compassEngine = compassEngine,
      internalLocationEngineProvider = locationEngineProvider
    )
  }

  @Test
  fun activateWithRequestTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()
    locationComponent.activateLocationComponent(options)

    assertEquals(locationEngineRequest, locationComponent.getLocationEngineRequest())

    every {
      context.obtainStyledAttributes(
        R.style.mapbox_LocationComponent,
        R.styleable.mapbox_LocationComponent
      )
    } returns mockk(relaxed = true)

    val resources: Resources = mockk(relaxed = true)
    every { context.resources } returns resources
    every { resources.getDimension(R.dimen.mapbox_locationComponentTrackingMultiFingerMoveThreshold) } returns 0f
    every { resources.getDimension(R.dimen.mapbox_locationComponentTrackingMultiFingerMoveThreshold) } returns 0f

    locationComponent.activateLocationComponent(
      LocationComponentActivationOptions.builder(
        context,
        style
      ).useDefaultLocationEngine(true)
        .locationEngineRequest(locationEngineRequest)
        .build()
    )
    assertEquals(locationEngineRequest, locationComponent.getLocationEngineRequest())
  }

  @Test
  fun activateWithDefaultLocationEngineRequestAndOptionsTestDefaultLocationEngine() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .useDefaultLocationEngine(true)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()
    locationComponent.activateLocationComponent(options)
    assertEquals(locationEngineRequest, locationComponent.getLocationEngineRequest())
    assertNotNull(locationComponent.getLocationEngine())
  }

  @Test
  fun activateWithDefaultLocationEngineRequestAndOptionsTestCustomLocationEngine() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .useDefaultLocationEngine(false)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()
    locationComponent.activateLocationComponent(options)
    assertEquals(locationEngineRequest, locationComponent.getLocationEngineRequest())
    Assert.assertNull(locationComponent.getLocationEngine())
  }

  @Test
  fun locationUpdatesWhenEnabledDisableTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()
    locationComponent.activateLocationComponent(options)
    verify(exactly = 0) { locationEngine.removeLocationUpdates(currentListener) }
    verify(exactly = 0) {
      locationEngine.requestLocationUpdates(
        locationEngineRequest,
        currentListener,
        any()
      )
    }

    locationComponent.onStart()
    verify(exactly = 0) { locationEngine.removeLocationUpdates(currentListener) }
    verify(exactly = 0) {
      locationEngine.requestLocationUpdates(
        locationEngineRequest,
        currentListener,
        any()
      )
    }

    locationComponent.enabled = true
    verify {
      locationEngine.requestLocationUpdates(
        locationEngineRequest,
        currentListener,
        any()
      )
    }

    locationComponent.enabled = false
    verify {
      locationEngine.requestLocationUpdates(
        locationEngineRequest,
        currentListener,
        any()
      )
    }
    verify { locationEngine.removeLocationUpdates(currentListener) }
  }

  @Test
  fun locationUpdatesWhenStartedStoppedTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()
    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true

    locationComponent.onStop()
    verify { locationEngine.removeLocationUpdates(currentListener) }

    locationComponent.onStart()
    verify(exactly = 2) {
      locationEngine.requestLocationUpdates(
        locationEngineRequest,
        currentListener,
        any()
      )
    }
  }

  @Test
  fun locationUpdatesWhenNewRequestTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true

    val newRequest: LocationEngineRequest = mockk(relaxed = true)
    locationComponent.setLocationEngineRequest(newRequest)
    verify { locationEngine.removeLocationUpdates(currentListener) }
    verify {
      locationEngine.requestLocationUpdates(
        newRequest,
        currentListener,
        any()
      )
    }
  }

  @Test
  fun lastLocationUpdateOnStartTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true

    verify { locationEngine.getLastLocation(lastListener) }
  }

  @Test
  fun transitionCallbackFinishedTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)

    val callback = slot<OnLocationCameraTransitionListener>()
    locationComponent.setCameraMode(CameraMode.TRACKING, listener)
    verify {
      locationCameraController.setCameraMode(
        CameraMode.TRACKING,
        any(),
        TRANSITION_ANIMATION_DURATION_MS,
        isNull(),
        isNull(),
        isNull(),
        capture(callback)
      )
    }
    callback.captured.onLocationCameraTransitionFinished(CameraMode.TRACKING)
    verify { listener.onLocationCameraTransitionFinished(CameraMode.TRACKING) }
    verify { locationCameraController.resetAllCameraAnimations() }
  }

  @Test
  fun transitionCallbackCanceledTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val callback = slot<OnLocationCameraTransitionListener>()
    locationComponent.setCameraMode(CameraMode.TRACKING, listener)
    verify {
      locationCameraController.setCameraMode(
        CameraMode.TRACKING,
        any(),
        TRANSITION_ANIMATION_DURATION_MS,
        isNull(),
        isNull(),
        isNull(),
        capture(callback)
      )
    }
    callback.captured.onLocationCameraTransitionCanceled(CameraMode.TRACKING)
    verify { listener.onLocationCameraTransitionCanceled(CameraMode.TRACKING) }
    verify { locationCameraController.resetAllCameraAnimations() }
  }

  @Test
  fun transitionCustomFinishedTest() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()

    val listener: OnLocationCameraTransitionListener = mockk(relaxed = true)
    val callback = slot<OnLocationCameraTransitionListener>()
    locationComponent.setCameraMode(CameraMode.TRACKING, 1200, 14.0, 13.0, 45.0, listener)
    verify {
      locationCameraController.setCameraMode(
        CameraMode.TRACKING,
        any(),
        1200L,
        14.0,
        13.0,
        45.0,
        capture(callback)
      )
    }
    callback.captured.onLocationCameraTransitionFinished(CameraMode.TRACKING)
    verify { listener.onLocationCameraTransitionFinished(CameraMode.TRACKING) }
    verify { locationCameraController.resetAllCameraAnimations() }
  }

  @Test
  fun compass_listenWhenConsumedByNoneCamera() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.isConsumingCompass } returns true
    locationComponent.cameraMode = CameraMode.NONE_COMPASS
    verify { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_listenWhenConsumedByTrackingCamera() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()
    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.isConsumingCompass } returns true
    locationComponent.cameraMode = CameraMode.TRACKING_COMPASS
    verify { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_listenWhenConsumedByLayer() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.isConsumingCompass } returns true
    locationComponent.renderMode = RenderMode.COMPASS
    verify { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_notListenWhenNotConsumed() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()

    every { locationLayerController.isConsumingCompass } returns false
    every { locationCameraController.isConsumingCompass } returns false

    locationComponent.renderMode = RenderMode.GPS
    locationComponent.renderMode = RenderMode.NORMAL
    locationComponent.cameraMode = CameraMode.TRACKING
    locationComponent.cameraMode = CameraMode.NONE
    locationComponent.cameraMode = CameraMode.NONE_GPS
    locationComponent.cameraMode = CameraMode.TRACKING_GPS
    locationComponent.cameraMode = CameraMode.TRACKING_GPS_NORTH

    verify(exactly = 0) { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_removeListenerOnChange() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true
    locationComponent.renderMode = RenderMode.COMPASS
    every { locationLayerController.isConsumingCompass } returns false
    locationComponent.renderMode = RenderMode.NORMAL
    verify { compassEngine.removeCompassListener(any()) }
  }

  @Test
  fun compass_removeListenerOnStop() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true
    locationComponent.renderMode = RenderMode.COMPASS
    locationComponent.onStop()
    verify { compassEngine.removeCompassListener(any()) }
  }

  @Test
  fun compass_reAddListenerOnStart() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true

    locationComponent.renderMode = RenderMode.COMPASS
    verify(exactly = 1) { compassEngine.addCompassListener(any()) }
    locationComponent.onStop()
    locationComponent.onStart()
    verify(exactly = 2) { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_removeListenerOnStyleStartLoad() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true

    locationComponent.renderMode = RenderMode.COMPASS
    locationComponent.onStartLoadingMap()
    verify { compassEngine.removeCompassListener(any()) }
  }

  @Test
  fun compass_reAddListenerOnStyleLoadFinished() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true

    locationComponent.renderMode = RenderMode.COMPASS
    locationComponent.onStartLoadingMap()
    locationComponent.onFinishLoadingStyle()
    verify(exactly = 2) { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_reAddListenerOnlyWhenEnabled() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true

    locationComponent.renderMode = RenderMode.COMPASS
    locationComponent.enabled = false

    locationComponent.onStartLoadingMap()
    locationComponent.onFinishLoadingStyle()
    verify { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_notAdListenerWhenDisabled() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true
    locationComponent.renderMode = RenderMode.COMPASS
    verify(exactly = 0) { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_notAdListenerWhenStopped() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true

    locationComponent.renderMode = RenderMode.COMPASS
    verify(exactly = 0) { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun compass_notAddListenerWhenLayerNotReady() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.onStart()
    locationComponent.enabled = true
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationLayerController.isConsumingCompass } returns true

    locationComponent.renderMode = RenderMode.COMPASS
    verify(exactly = 1) { compassEngine.addCompassListener(any()) }

    locationComponent.onStartLoadingMap()
    // Layer should be disabled at this point
    locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS)
    verify(exactly = 1) { compassEngine.addCompassListener(any()) }
  }

  @Test
  fun developerAnimationCalled() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true
    for (listener in developerAnimationListeners) {
      listener.onDeveloperAnimationStarted()
    }
    verify {
      locationCameraController.setCameraMode(
        CameraMode.NONE,
        isNull(),
        TRANSITION_ANIMATION_DURATION_MS,
        isNull(),
        isNull(),
        isNull(),
        any()
      )
    }
  }

  @Test
  fun internal_cameraTrackingChangedListener_onCameraTrackingDismissed() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    val cameraChangeListener: OnCameraTrackingChangedListener = mockk(relaxed = true)
    locationComponent.addOnCameraTrackingChangedListener(cameraChangeListener)

    locationComponent.cameraTrackingChangedListener.onCameraTrackingDismissed()

    verify { cameraChangeListener.onCameraTrackingDismissed() }
  }

  @Test
  fun internal_cameraTrackingChangedListener_onCameraTrackingChanged() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    val layerValueListener: AnimatorListenerHolder = mockk(relaxed = true)
    every { locationLayerController.animationListeners } returns setOf(layerValueListener)

    val cameraChangeListener: OnCameraTrackingChangedListener = mockk(relaxed = true)
    locationComponent.addOnCameraTrackingChangedListener(cameraChangeListener)

    locationComponent.cameraTrackingChangedListener.onCameraTrackingChanged(CameraMode.TRACKING_GPS)

    verify { locationCameraController.cancelZoomAnimation() }
    verify { locationCameraController.cancelPitchAnimation() }
    val holdersSlot = slot<Set<AnimatorListenerHolder>>()
    verify {
      locationAnimatorCoordinator.updateAnimatorListenerHolders(capture(holdersSlot))
    }
    assertEquals(
      setOf(
        layerValueListener
      ),
      holdersSlot.captured
    )
    verify { locationCameraController.resetAllCameraAnimations() }
    verify { locationAnimatorCoordinator.resetAllLayerAnimations() }
    verify { cameraChangeListener.onCameraTrackingChanged(CameraMode.TRACKING_GPS) }
  }

  @Test
  fun internal_renderModeChangedListener_onRenderModeChanged() {
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    val layerValueListener: AnimatorListenerHolder = mockk(relaxed = true)

    every { locationLayerController.animationListeners } returns setOf(layerValueListener)

    val renderChangeListener: OnRenderModeChangedListener = mockk(relaxed = true)
    locationComponent.addOnRenderModeChangedListener(renderChangeListener)

    locationComponent.renderModeChangedListener.onRenderModeChanged(RenderMode.NORMAL)

    val holdersSlot = slot<Set<AnimatorListenerHolder>>()
    verify {
      locationAnimatorCoordinator.updateAnimatorListenerHolders(capture(holdersSlot))
    }
    assertEquals(
      setOf(
        layerValueListener
      ),
      holdersSlot.captured
    )

    verify { locationCameraController.resetAllCameraAnimations() }
    verify { locationAnimatorCoordinator.resetAllLayerAnimations() }
    verify { renderChangeListener.onRenderModeChanged(RenderMode.NORMAL) }
  }

  @Test
  fun tiltWhileTracking_notReady() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()

    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.pitchWhileTracking(30.0, 500L, callback)
    verify { callback.onCancel() }
    verify(exactly = 0) {
      locationCameraController.feedNewCameraPitch(any(), any(), any())
    }
  }

  @Test
  fun tiltWhileTracking_notTracking() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.cameraMode } returns CameraMode.NONE
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    locationComponent.onStart()

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.pitchWhileTracking(30.0, 500L, callback)
    verify { callback.onCancel() }
    verify(exactly = 0) {
      locationCameraController.feedNewCameraPitch(any(), any(), any())
    }
  }

  @Test
  fun tiltWhileTracking_transitioning() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.cameraMode } returns CameraMode.TRACKING
    every { locationCameraController.isTransitioning } returns true

    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    locationComponent.onStart()

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.pitchWhileTracking(30.0, 500L, callback)
    verify { callback.onCancel() }
    verify(exactly = 0) {
      locationCameraController.feedNewCameraPitch(any(), any(), any())
    }
  }

  @Test
  fun pitchWhileTracking_sucessful() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.cameraMode } returns CameraMode.TRACKING
    every { locationCameraController.isTransitioning } returns false
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    locationComponent.onStart()

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.pitchWhileTracking(30.0, 500L, callback)
    verify(exactly = 0) { callback.onCancel() }
    verify {
      locationCameraController.feedNewCameraPitch(
        30.0,
        500L,
        callback
      )
    }
  }

  @Test
  fun zoomWhileTracking_notReady() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.zoomWhileTracking(14.0, 500L, callback)
    verify { callback.onCancel() }
    verify(exactly = 0) {
      locationCameraController.feedNewCameraZoomLevel(any(), any(), any())
    }
  }

  @Test
  fun zoomWhileTracking_notTracking() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.cameraMode } returns CameraMode.NONE
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true
    locationComponent.onStart()

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.zoomWhileTracking(14.0, 500L, callback)
    verify { callback.onCancel() }
    verify(exactly = 0) {
      locationCameraController.feedNewCameraZoomLevel(any(), any(), any())
    }
  }

  @Test
  fun zoomWhileTracking_transitioning() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.cameraMode } returns CameraMode.TRACKING
    every { locationCameraController.isTransitioning } returns true
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true
    locationComponent.onStart()

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.zoomWhileTracking(14.0, 500L, callback)
    verify { callback.onCancel() }
    verify(exactly = 0) {
      locationCameraController.feedNewCameraZoomLevel(any(), any(), any())
    }
  }

  @Test
  fun zoomWhileTracking_successful() {
    every { transform.getCameraOptions(null) } returns CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .bearing(0.0)
      .build()
    every { locationCameraController.cameraMode } returns CameraMode.TRACKING
    every { locationCameraController.isTransitioning } returns false
    val options =
      LocationComponentActivationOptions.Builder(context, style)
        .locationEngine(locationEngine)
        .locationEngineRequest(locationEngineRequest)
        .locationComponentOptions(locationComponentOptions).build()

    locationComponent.activateLocationComponent(options)
    locationComponent.enabled = true
    locationComponent.onStart()

    val callback: CancelableCallback = mockk(relaxed = true)

    locationComponent.zoomWhileTracking(14.0, 500L, callback)
    verify(exactly = 0) { callback.onCancel() }
    verify {
      locationCameraController.feedNewCameraZoomLevel(
        14.0,
        500L,
        callback
      )
    }
  }

  @Test
  fun newLocation_accuracy_indicatorLayerRadiusValue() {
    val location = Location("test")
    location.accuracy = 50f
    locationComponent = LocationComponentPlugin(
      delegateProvider = delegateProvider,
      developerAnimationListeners = developerAnimationListeners,
      currentListener = currentListener,
      lastListener = lastListener,
      locationLayerController = locationLayerController,
      locationCameraController = locationCameraController,
      locationAnimatorCoordinator = locationAnimatorCoordinator,
      staleStateManager = staleStateManager,
      compassEngine = compassEngine,
      internalLocationEngineProvider = locationEngineProvider
    )
    locationComponent.activateLocationComponent(
      LocationComponentActivationOptions.builder(context, style)
        .locationComponentOptions(locationComponentOptions)
        .useDefaultLocationEngine(false)
        .build()
    )
    locationComponent.enabled = true
    locationComponent.onStart()
    locationComponent.forceLocationUpdate(location)

    verify { locationAnimatorCoordinator.feedNewAccuracyRadius(location.accuracy, false) }
  }

  @Test
  fun internal_indicatorPositionChangedListener_onIndicatorPositionChanged() {
    locationComponent.activateLocationComponent(
      context,
      mockk(),
      null,
      locationEngine,
      locationEngineRequest,
      locationComponentOptions
    )
    locationComponent.enabled = true

    val onIndicatorPositionChangedListener: OnIndicatorPositionChangedListener = mockk(relaxed = true)
    locationComponent.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    val point: Point = mockk()
    locationComponent.indicatorPositionChangedListener.onIndicatorPositionChanged(point)
    verify { onIndicatorPositionChangedListener.onIndicatorPositionChanged(point) }
  }

  @Test
  fun newLocation_nullAnimationDurationPassed() {
    val location = Location("test")
    location.accuracy = 50f
    locationComponent.activateLocationComponent(
      LocationComponentActivationOptions.builder(context, style)
        .locationComponentOptions(locationComponentOptions)
        .useDefaultLocationEngine(false)
        .build()
    )
    locationComponent.enabled = true
    locationComponent.onStart()
    locationComponent.forceLocationUpdate(
      LocationUpdate(location)
    )
    verify { locationAnimatorCoordinator.feedNewLocation(eq(listOf(location).toTypedArray()), any(), isNull()) }
  }

  @Test
  fun whenComponentReEnabled_noEngine_animationDurationZero() {
    val location = Location("test")
    location.accuracy = 50f
    locationComponent.activateLocationComponent(
      LocationComponentActivationOptions.builder(context, style)
        .locationComponentOptions(locationComponentOptions)
        .useDefaultLocationEngine(false)
        .build()
    )
    locationComponent.enabled = true
    locationComponent.onStart()
    locationComponent.forceLocationUpdate(
      LocationUpdate(location, null, 1500L)
    )
    verify { locationAnimatorCoordinator.feedNewLocation(eq(listOf(location).toTypedArray()), any(), eq(1500L)) }

    locationComponent.onStop()
    locationComponent.onStart()
    verify { locationAnimatorCoordinator.feedNewLocation(eq(listOf(location).toTypedArray()), any(), eq(0L)) }
  }
}