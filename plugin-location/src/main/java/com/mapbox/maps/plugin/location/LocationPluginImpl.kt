package com.mapbox.maps.plugin.location

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.SensorManager
import android.location.Location
import android.os.Looper
import android.os.SystemClock
import android.view.WindowManager
import androidx.annotation.RequiresPermission
import androidx.annotation.StyleRes
import androidx.annotation.VisibleForTesting
import com.mapbox.android.core.location.*
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.common.Logger
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
import com.mapbox.maps.plugin.PLUGIN_GESTURE_CLASS_NAME
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.location.LocationComponentConstants.COMPASS_UPDATE_RATE_MS
import com.mapbox.maps.plugin.location.LocationComponentConstants.DEFAULT_TRACKING_PADDING_ANIM_DURATION
import com.mapbox.maps.plugin.location.listeneres.*
import com.mapbox.maps.plugin.location.modes.CameraMode
import com.mapbox.maps.plugin.location.modes.RenderMode
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * The Location Component provides location awareness to your mobile application. Enabling this
 * component provides a contextual experience to your users by showing an icon representing the users
 * current location. A few different modes are offered to provide the right context to your users at
 * the correct time. [RenderMode.NORMAL] simply shows the users location on the map
 * represented as a dot. [RenderMode.COMPASS] mode allows you to display an arrow icon
 * (by default) that points in the direction the device is pointing in.
 * [RenderMode.GPS] can be used in conjunction with our Navigation SDK to
 * display a larger icon (customized with [LocationComponentOptions.gpsDrawable]) we call the user puck.
 *
 *
 * This component also offers the ability to set a map camera behavior for tracking the user
 * location. These different [CameraMode]s will track, stop tracking the location based on the
 * mode set with [LocationPluginImpl.setCameraMode].
 *
 *
 * **
 * To get the component object use [MapPluginProviderDelegate.locationLegacy] and activate it with
 * [.activateLocationComponent] or one of the overloads.
 * Then, manage its visibility with [.setLocationComponentEnabled].
 * The component will not process location updates right after activation, but only after being enabled.
 ** *
 *
 *
 * Using this component requires you to request permission beforehand manually or using
 * [PermissionsManager]. Either
 * `ACCESS_COARSE_LOCATION` or `ACCESS_FINE_LOCATION` permissions can be requested for
 * this component to work as expected.
 *
 *
 * This component offers a default, built-in [LocationEngine] with some of the activation methods.
 * This engine will be obtained by [LocationEngineProvider.getBestLocationEngine] which defaults
 * to the [com.mapbox.android.core.location.MapboxFusedLocationEngineImpl]. If you'd like to utilize Google
 * Play Services
 * for more precise location updates, simply add the Google Play Location Services dependency in your build script.
 * This will make the default engine the [com.mapbox.android.core.location.GoogleLocationEngineImpl] instead.
 * After a custom engine is passed to the component, or the built-in is initialized,
 * the location updates are going to be requested with the [LocationEngineRequest], either a default one,
 * or the one passed during the activation.
 * When using any engine, requesting/removing the location updates is going to be managed internally.
 *
 *
 * You can also push location updates to the component without any internal engine management.
 * To achieve that, use [.activateLocationComponent] with false.
 * No engine is going to be initialized and you can push location updates with [.forceLocationUpdate].
 *
 *
 * For location puck animation purposes, like navigation,
 * we recommend limiting the maximum zoom level of the map for the best user experience.
 *
 *
 * Location Component doesn't support state saving out-of-the-box.
 */
@Deprecated("Use LocationComponentPlugin instead.")
class LocationPluginImpl : LocationPlugin {
  private lateinit var style: StyleManagerInterface
  private lateinit var delegateProvider: MapDelegateProvider

  private lateinit var options: LocationComponentOptions
  private var internalLocationEngineProvider =
    InternalLocationEngineProvider()
  private var locationEngine: LocationEngine? = null
  private var locationEngineRequest =
    LocationEngineRequest.Builder(LocationComponentConstants.DEFAULT_INTERVAL_MILLIS)
      .setFastestInterval(LocationComponentConstants.DEFAULT_FASTEST_INTERVAL_MILLIS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .build()
  private var currentLocationEngineListener: LocationEngineCallback<LocationEngineResult> =
    CurrentLocationEngineCallback(this)
  private var lastLocationEngineListener: LocationEngineCallback<LocationEngineResult> =
    LastLocationEngineCallback(this)
  private var compassEngine: CompassEngine? = null
  private lateinit var locationLayerController: LocationLayerController
  private lateinit var locationCameraController: LocationCameraController
  private lateinit var locationAnimatorCoordinator: LocationLayerAnimatorCoordinator

  /**
   * Holds last location which is being returned in the [.getLastKnownLocation]
   * when there is no [.locationEngine] set or when the last location returned by the engine is null.
   */
  private var lastLocation: Location? = null
  /**
   * Returns whether the location component is activated.
   *
   * @return true if the component is activated, false otherwise
   */
  /**
   * Indicates whether the component has been initialized.
   */
  var isLocationComponentActivated = false
    private set

  private var isLocationComponentEnabled: Boolean = false

  /**
   * Indicated that component's lifecycle [.onStart] method has been called.
   * This allows Mapbox components enter started state and display data, and adds state safety for methods like
   * [.setLocationComponentEnabled]
   */
  private var isComponentStarted = false

  /**
   * Indicates if Mapbox components are ready to be interacted with. This can differ from [.isComponentStarted]
   * if the Mapbox style is being reloaded.
   */
  private var isLayerReady = false

  /**
   * Indicates whether we are listening for compass updates.
   */
  private var isListeningToCompass = false

  private lateinit var staleStateManager: StaleStateManager
  private val onLocationStaleListeners =
    CopyOnWriteArrayList<OnLocationStaleListener>()
  private val onLocationClickListeners =
    CopyOnWriteArrayList<OnLocationClickListener>()
  private val onLocationLongClickListeners =
    CopyOnWriteArrayList<OnLocationLongClickListener>()
  private val onCameraTrackingChangedListeners =
    CopyOnWriteArrayList<OnCameraTrackingChangedListener>()
  private val onRenderModeChangedListeners =
    CopyOnWriteArrayList<OnRenderModeChangedListener>()
  private val onIndicatorPositionChangedListener =
    CopyOnWriteArrayList<OnIndicatorPositionChangedListener>()

  // Workaround for too frequent updates, see https://github.com/mapbox/mapbox-gl-native/issues/13587
  private var fastestInterval = 0L
  private var lastUpdateTime = 0L

  /**
   * Default empty constructor for LocationComponentPlugin.
   */
  constructor()

  /**
   * Internal use.
   */
  constructor(developerAnimationListeners: MutableList<OnDeveloperAnimationListener>) {
    developerAnimationListeners.add(developerAnimationListener)
  }

  @VisibleForTesting
  internal constructor(
    delegateProvider: MapDelegateProvider,
    developerAnimationListeners: MutableList<OnDeveloperAnimationListener>,
    currentListener: LocationEngineCallback<LocationEngineResult>,
    lastListener: LocationEngineCallback<LocationEngineResult>,
    locationLayerController: LocationLayerController,
    locationCameraController: LocationCameraController,
    locationAnimatorCoordinator: LocationLayerAnimatorCoordinator,
    staleStateManager: StaleStateManager,
    compassEngine: CompassEngine,
    internalLocationEngineProvider: InternalLocationEngineProvider
  ) {
    this.delegateProvider = delegateProvider
    delegateProvider.getStyle { this.style = it }
    developerAnimationListeners.add(developerAnimationListener)
    currentLocationEngineListener = currentListener
    lastLocationEngineListener = lastListener
    this.locationLayerController = locationLayerController
    this.locationCameraController = locationCameraController
    this.locationAnimatorCoordinator = locationAnimatorCoordinator
    this.staleStateManager = staleStateManager
    this.compassEngine = compassEngine
    this.internalLocationEngineProvider = internalLocationEngineProvider
    isLocationComponentActivated = true
  }

  @VisibleForTesting
  internal fun activateLocationComponent(
    context: Context,
    style: StyleManagerInterface,
    locationModelLayerOptions: LocationModelLayerOptions?,
    locationEngine: LocationEngine?,
    locationEngineRequest: LocationEngineRequest,
    options: LocationComponentOptions
  ) {
    initialize(context, style, locationModelLayerOptions, options)
    setLocationEngineRequest(locationEngineRequest)
    setLocationEngine(locationEngine)
    applyStyle(options)
  }

  /**
   * This method initializes the component and needs to be called before any other operations are performed.
   * Afterwards, you can manage component's visibility by [.setLocationComponentEnabled].
   *
   * @param activationOptions a fully built [LocationComponentActivationOptions] object
   */
  @Deprecated("LocationPlugin is deprecated, use LocationComponentPlugin instead.")
  fun activateLocationComponent(activationOptions: LocationComponentActivationOptions) {
    var options = activationOptions.locationComponentOptions()
    if (options == null) {
      var styleRes = activationOptions.styleRes()
      if (styleRes == 0) {
        styleRes = R.style.mapbox_LocationComponent
      }
      options = LocationComponentOptions.createFromAttributes(activationOptions.context(), styleRes)
    }
    // Initialize the LocationComponent with Context, the map's `Style`, and either custom LocationComponentOptions
    // or backup options created from default/custom attributes
    initialize(
      activationOptions.context(),
      activationOptions.style(),
      activationOptions.locationModelLayerOptions,
      options
    )
    // Apply the LocationComponent styling
    // TODO avoid doubling style initialization
    applyStyle(options)

    // Set the LocationEngine request if one was given to LocationComponentActivationOptions
    val locationEngineRequest = activationOptions.locationEngineRequest()
    locationEngineRequest?.let { setLocationEngineRequest(it) }

    // Set the LocationEngine if one was given to LocationComponentActivationOptions
    val locationEngine = activationOptions.locationEngine()
    if (locationEngine != null) {
      setLocationEngine(locationEngine)
    } else {
      if (activationOptions.useDefaultLocationEngine()) {
        initializeLocationEngine(activationOptions.context())
      } else {
        setLocationEngine(null)
      }
    }
  }

  /**
   * Defines whether the [LocationPluginImpl] is enabled.
   */
  @set:RequiresPermission(anyOf = [permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION])
  var enabled: Boolean
    /**
     * Returns whether the plugin is enabled, meaning that location can be displayed and camera modes can be used.
     *
     * @return true if the plugin is enabled, false otherwise
     */
    get() {
      checkActivationState()
      return isLocationComponentEnabled
    }
    /**
     * Manage component's visibility after activation.
     *
     * @param isEnabled true if the plugin should be visible and listen for location updates, false otherwise.
     */
    set(isEnabled) {
      checkActivationState()
      if (isEnabled) {
        enableLocationComponent()
      } else {
        disableLocationComponent()
      }
      locationCameraController.setEnabled(isEnabled)
    }

  /**
   * Sets the camera mode, which determines how the map camera will track the rendered location.
   *
   *
   * When camera is transitioning to a new mode, it will reject inputs like [.zoomWhileTracking] or
   * [.tiltWhileTracking].
   * Use [OnLocationCameraTransitionListener] to listen for the transition state.
   *
   *
   *
   *  * [CameraMode.NONE]: No camera tracking
   *  * [CameraMode.NONE_COMPASS]: Camera does not track location, but does track compass bearing
   *  * [CameraMode.NONE_GPS]: Camera does not track location, but does track GPS bearing
   *  * [CameraMode.TRACKING]: Camera tracks the user location
   *  * [CameraMode.TRACKING_COMPASS]: Camera tracks the user location, with bearing provided by a compass
   *  * [CameraMode.TRACKING_GPS]: Camera tracks the user location, with normalized bearing
   *  * [CameraMode.TRACKING_GPS_NORTH]: Camera tracks the user location, with bearing always set to north
   *
   *
   * @param cameraMode one of the modes found in [CameraMode]
   * @param transitionListener callback that's going to be invoked when the transition animation finishes
   */
  fun setCameraMode(
    cameraMode: CameraMode,
    transitionListener: OnLocationCameraTransitionListener? = null
  ) {
    setCameraMode(
      cameraMode,
      LocationComponentConstants.TRANSITION_ANIMATION_DURATION_MS,
      null,
      null,
      null,
      transitionListener
    )
  }

  /**
   * Sets the camera mode, which determines how the map camera will track the rendered location.
   *
   *
   * When camera is transitioning to a new mode, it will reject inputs like [.zoomWhileTracking] or
   * [.tiltWhileTracking].
   * Use [OnLocationCameraTransitionListener] to listen for the transition state.
   *
   *
   * Set values of zoom, bearing and pitch that the camera will transition to. If null is passed to any of those,
   * current value will be used for that parameter instead.
   * If the camera is already tracking, provided values are ignored.
   *
   *
   *
   *  * [CameraMode.NONE]: No camera tracking
   *  * [CameraMode.NONE_COMPASS]: Camera does not track location, but does track compass bearing
   *  * [CameraMode.NONE_GPS]: Camera does not track location, but does track GPS bearing
   *  * [CameraMode.TRACKING]: Camera tracks the user location
   *  * [CameraMode.TRACKING_COMPASS]: Camera tracks the user location, with bearing provided by a compass
   *  * [CameraMode.TRACKING_GPS]: Camera tracks the user location, with normalized bearing
   *  * [CameraMode.TRACKING_GPS_NORTH]: Camera tracks the user location, with bearing always set to north
   *
   *
   * @param cameraMode one of the modes found in [CameraMode]
   * @param transitionDuration duration of the transition in milliseconds
   * @param zoom target zoom, set to null to use current camera position
   * @param bearing target bearing, set to null to use current camera position
   * @param pitch target pitch, set to null to use current camera position
   * @param transitionListener callback that's going to be invoked when the transition animation finishes
   */
  fun setCameraMode(
    cameraMode: CameraMode,
    transitionDuration: Long,
    zoom: Double?,
    bearing: Double?,
    pitch: Double?,
    transitionListener: OnLocationCameraTransitionListener?
  ) {
    checkActivationState()
    locationCameraController.setCameraMode(
      cameraMode, lastLocation, transitionDuration, zoom, bearing, pitch,
      CameraTransitionListener(transitionListener)
    )
    updateCompassListenerState(true)
  }

  /**
   * Used to reset camera animators and notify listeners when the transition finishes.
   */
  private inner class CameraTransitionListener(private val externalListener: OnLocationCameraTransitionListener?) :
    OnLocationCameraTransitionListener {
    override fun onLocationCameraTransitionFinished(cameraMode: CameraMode) {
      externalListener?.onLocationCameraTransitionFinished(cameraMode)
      reset()
    }

    override fun onLocationCameraTransitionCanceled(cameraMode: CameraMode) {
      externalListener?.onLocationCameraTransitionCanceled(cameraMode)
      reset()
    }

    private fun reset() {
      locationCameraController.resetAllCameraAnimations()
    }
  }

  /**
   * The camera mode determines how the map camera will track the rendered location.
   *
   *
   * When camera is transitioning to a new mode, it will reject inputs like [.zoomWhileTracking] or
   * [.tiltWhileTracking].
   * Use [OnLocationCameraTransitionListener] to listen for the transition state.
   *
   *  * [CameraMode.NONE]: No camera tracking
   *  * [CameraMode.NONE_COMPASS]: Camera does not track location, but does track compass bearing
   *  * [CameraMode.NONE_GPS]: Camera does not track location, but does track GPS bearing
   *  * [CameraMode.TRACKING]: Camera tracks the user location
   *  * [CameraMode.TRACKING_COMPASS]: Camera tracks the user location, with bearing provided by a compass
   *  * [CameraMode.TRACKING_GPS]: Camera tracks the user location, with normalized bearing
   *  * [CameraMode.TRACKING_GPS_NORTH]: Camera tracks the user location, with bearing always set to north
   */
  var cameraMode: CameraMode
    /**
     * Provides the current camera mode being used to track the location or compass updates.
     *
     * @return the current camera mode
     */
    get() {
      checkActivationState()
      return locationCameraController.cameraMode
    }
    /**
     * Sets the camera mode, which determines how the map camera will track the rendered location.
     *
     *
     * When camera is transitioning to a new mode, it will reject inputs like [.zoomWhileTracking] or
     * [.tiltWhileTracking].
     * Use [OnLocationCameraTransitionListener] to listen for the transition state.
     *
     *  * [CameraMode.NONE]: No camera tracking
     *  * [CameraMode.NONE_COMPASS]: Camera does not track location, but does track compass bearing
     *  * [CameraMode.NONE_GPS]: Camera does not track location, but does track GPS bearing
     *  * [CameraMode.TRACKING]: Camera tracks the user location
     *  * [CameraMode.TRACKING_COMPASS]: Camera tracks the user location, with bearing provided by a compass
     *  * [CameraMode.TRACKING_GPS]: Camera tracks the user location, with normalized bearing
     *  * [CameraMode.TRACKING_GPS_NORTH]: Camera tracks the user location, with bearing always set to north
     *
     * @param cameraMode one of the modes found in [CameraMode]
     */
    set(cameraMode) {
      setCameraMode(cameraMode, null)
    }

  /**
   * The render mode determines how the location updates will be rendered on the map.
   *
   *  * [RenderMode.NORMAL]: Shows user location, bearing ignored
   *  * [RenderMode.COMPASS]: Shows user location with bearing considered from compass
   *  * [RenderMode.GPS]: Shows user location with bearing considered from location
   *
   */
  var renderMode: RenderMode
    /**
     * Provides the current render mode being used to show
     * the location and/or compass updates on the map.
     *
     * @return the current render mode
     */
    get() {
      checkActivationState()
      return locationLayerController.getRenderMode()
    }
    /**
     * Sets the render mode, which determines how the location updates will be rendered on the map.
     *
     *  * [RenderMode.NORMAL]: Shows user location, bearing ignored
     *  * [RenderMode.COMPASS]: Shows user location with bearing considered from compass
     *  * [RenderMode.GPS]: Shows user location with bearing considered from location
     *
     * @param renderMode one of the modes found in [RenderMode]
     */
    set(renderMode) {
      checkActivationState()
      if (renderMode == RenderMode.GPS) {
        lastLocation?.let {
          locationAnimatorCoordinator.cancelAndRemoveGpsBearingAnimation()
          locationLayerController.setGpsBearing(it.bearing)
        }
      }
      locationLayerController.setRenderMode(renderMode)
      updateCompassListenerState(true)
    }

  /**
   * Returns the current location options being used.
   *
   * @return the current [LocationComponentOptions]
   */
  val locationComponentOptions: LocationComponentOptions
    get() {
      checkActivationState()
      return options
    }

  /**
   * Apply a new component style with a style resource.
   *
   * @param styleRes a XML style overriding some or all the options
   */
  fun applyStyle(context: Context, @StyleRes styleRes: Int) {
    checkActivationState()
    applyStyle(LocationComponentOptions.createFromAttributes(context, styleRes))
  }

  /**
   * Apply a new component style with location component options.
   *
   * @param options to update the current style
   */
  fun applyStyle(options: LocationComponentOptions) {
    checkActivationState()
    this.options = options
    delegateProvider.getStyle {
      if (delegateProvider.styleStateDelegate.isFullyLoaded()) {
        locationLayerController.applyStyle(options)
        locationCameraController.initializeOptions(options)
        staleStateManager.setEnabled(options.enableStaleState())
        staleStateManager.setDelayTime(options.staleStateTimeout())
        locationAnimatorCoordinator.accuracyAnimationEnabled = options.accuracyAnimationEnabled()
        if (options.pulseEnabled()) {
          startPulsingLocationCircle()
        } else {
          stopPulsingLocationCircle()
        }
        updateMapWithOptions(options)
      }
    }
  }

  /**
   * Starts the LocationComponent's pulsing circle UI.
   */
  private fun startPulsingLocationCircle() {
    if (isLocationComponentEnabled && isLayerReady) {
      locationAnimatorCoordinator.startLocationComponentCirclePulsing(options)
      locationLayerController.adjustPulsingCircleLayerVisibility(true)
    }
  }

  /**
   * Zooms to the desired zoom level.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapTransformDelegate.moveBy],
   * [MapTransformDelegate.easeTo] or [)][MapTransformDelegate] instead.
   *
   *
   * If the camera is transitioning when the zoom change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the zoom as a camera change argument.
   *
   *
   * @param zoomLevel The desired zoom level.
   * @param animationDuration The zoom animation duration.
   * @param callback The callback with finish/cancel information
   */
  fun zoomWhileTracking(
    zoomLevel: Double,
    animationDuration: Long,
    callback: CancelableCallback?
  ) {
    checkActivationState()
    if (!isLayerReady) {
      notifyUnsuccessfulCameraOperation(callback, null)
      return
    } else if (cameraMode == CameraMode.NONE) {
      notifyUnsuccessfulCameraOperation(
        callback,
        String.format(
          "%s%s",
          "LocationComponent#zoomWhileTracking method can only be used",
          " when a camera mode other than CameraMode#NONE is engaged."
        )
      )
      return
    } else if (locationCameraController.isTransitioning) {
      notifyUnsuccessfulCameraOperation(
        callback,
        "LocationComponent#zoomWhileTracking method call is ignored because the camera mode is transitioning"
      )
      return
    }

    locationCameraController.feedNewCameraZoomLevel(zoomLevel, animationDuration, callback)
  }

  /**
   * Zooms to the desired zoom level.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the zoom change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the zoom as a camera change argument.
   *
   * @param zoomLevel The desired zoom level.
   * @param animationDuration The zoom animation duration.
   */
  fun zoomWhileTracking(zoomLevel: Double, animationDuration: Long) {
    checkActivationState()
    zoomWhileTracking(zoomLevel, animationDuration, null)
  }

  /**
   * Zooms to the desired zoom level.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the zoom change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the zoom as a camera change argument.
   *
   *
   * @param zoomLevel The desired zoom level.
   */
  fun zoomWhileTracking(zoomLevel: Double) {
    checkActivationState()
    zoomWhileTracking(
      zoomLevel,
      LocationComponentConstants.DEFAULT_TRACKING_ZOOM_ANIM_DURATION,
      null
    )
  }

  /**
   * Cancels animation started by [.zoomWhileTracking].
   */
  fun cancelZoomWhileTrackingAnimation() {
    checkActivationState()
    locationCameraController.cancelZoomAnimation()
  }

  /**
   * Sets the padding.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the padding change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the padding as a camera change argument.
   *
   *
   * @param padding The desired padding.
   */
  fun paddingWhileTracking(padding: DoubleArray) {
    paddingWhileTracking(
      padding,
      DEFAULT_TRACKING_PADDING_ANIM_DURATION,
      null
    )
  }

  /**
   * Sets the padding.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the padding change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the padding as a camera change argument.
   *
   *
   * @param padding The desired padding.
   * @param animationDuration The padding animation duration.
   */
  fun paddingWhileTracking(
    padding: DoubleArray,
    animationDuration: Long
  ) {
    paddingWhileTracking(padding, animationDuration, null)
  }

  /**
   * Sets the padding.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the padding change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the padding as a camera change argument.
   *
   *
   * @param padding The desired padding.
   * @param animationDuration The padding animation duration.
   * @param callback The callback with finish/cancel information
   */
  fun paddingWhileTracking(
    padding: DoubleArray,
    animationDuration: Long,
    callback: CancelableCallback?
  ) {
    checkActivationState()
    if (!isLayerReady) {
      notifyUnsuccessfulCameraOperation(callback, null)
      return
    } else if (cameraMode === CameraMode.NONE) {
      notifyUnsuccessfulCameraOperation(
        callback,
        String.format(
          "%s%s",
          "LocationComponent#paddingWhileTracking method can only be used",
          " when a camera mode other than CameraMode#NONE is engaged."
        )
      )
      return
    } else if (locationCameraController.isTransitioning) {
      notifyUnsuccessfulCameraOperation(
        callback,
        "LocationComponent#paddingWhileTracking method call is ignored because the camera mode is transitioning"
      )
      return
    }
    // convert ltrb to tlrb
    val edgeInsets = EdgeInsets(padding[1], padding[0], padding[2], padding[3])
    locationCameraController.feedNewCameraPadding(edgeInsets, animationDuration, callback)
  }

  /**
   * Cancels animation started by [.paddingWhileTracking].
   */
  fun cancelPaddingWhileTrackingAnimation() {
    checkActivationState()
    locationCameraController.cancelPaddingAnimation()
  }

  /**
   * Tilts the camera.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the pitch change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the pitch as a camera change argument.
   *
   *
   * @param pitch The desired camera pitch.
   * @param animationDuration The pitch animation duration.
   * @param callback The callback with finish/cancel information
   */
  fun pitchWhileTracking(
    pitch: Double,
    animationDuration: Long,
    callback: CancelableCallback?
  ) {
    checkActivationState()
    if (!isLayerReady) {
      notifyUnsuccessfulCameraOperation(callback, null)
      return
    } else if (cameraMode == CameraMode.NONE) {
      notifyUnsuccessfulCameraOperation(
        callback,
        String.format(
          "%s%s",
          "LocationComponent#tiltWhileTracking method can only be used",
          " when a camera mode other than CameraMode#NONE is engaged."
        )
      )
      return
    } else if (locationCameraController.isTransitioning) {
      notifyUnsuccessfulCameraOperation(
        callback,
        "LocationComponent#tiltWhileTracking method call is ignored because the camera mode is transitioning"
      )
      return
    }
    locationCameraController.feedNewCameraPitch(pitch, animationDuration, callback)
  }

  /**
   * Tilts the camera.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the pitch change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the pitch as a camera change argument.
   *
   * @param pitch The desired camera pitch.
   * @param animationDuration The pitch animation duration.
   */
  fun pitchWhileTracking(pitch: Double, animationDuration: Long) {
    checkActivationState()
    pitchWhileTracking(pitch, animationDuration, null)
  }

  /**
   * Pitches the camera.
   * This API can only be used in pair with camera modes other than [CameraMode.NONE].
   * If you are not using any of [CameraMode] modes,
   * use one of [MapboxMap.moveCamera],
   * [MapboxMap.easeCamera] or [MapboxMap.animateCamera] instead.
   *
   *
   * If the camera is transitioning when the pitch change is requested, the call is going to be ignored.
   * Use [CameraTransitionListener] to chain the animations, or provide the pitch as a camera change argument.
   *
   *
   * @param pitch The desired camera pitch.
   */
  fun pitchWhileTracking(pitch: Double) {
    checkActivationState()
    pitchWhileTracking(pitch, LocationComponentConstants.DEFAULT_TRACKING_TILT_ANIM_DURATION, null)
  }

  /**
   * Cancels animation started by [.tiltWhileTracking].
   */
  fun cancelPitchWhileTrackingAnimation() {
    checkActivationState()
    locationCameraController.cancelPitchAnimation()
  }

  /**
   * Use to either force a location update or to manually control when the user location gets
   * updated.
   *
   * @param location where the location icon is placed on the map
   */
  @Deprecated("Use #forceLocationUpdate(LocationUpdate) instead")
  fun forceLocationUpdate(location: Location?) {
    checkActivationState()
    updateLocation(location, false)
  }

  /**
   * Use to either force a location update or to manually control when the user location gets
   * updated.
   *
   * @param locationUpdate location update
   * @see LocationUpdate
   */
  fun forceLocationUpdate(locationUpdate: LocationUpdate) {
    updateLocation(locationUpdate, false)
  }

  /**
   * Set max FPS at which location animators can output updates. The throttling will only impact the location puck
   * and camera tracking smooth animations.
   *
   *
   * Setting this **will not impact** any other animations schedule with [MapboxMap], gesture animations or
   * [.zoomWhileTracking]/[.tiltWhileTracking].
   *
   *
   * Use this setting to limit animation rate of the location puck on higher zoom levels to decrease the stress on
   * the device's CPU which can directly improve battery life, without sacrificing UX.
   *
   *
   * Example usage:
   * <pre>
   * `mapboxMap.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
   * { @}Override
   * public void onCameraIdle() {
   * double zoom = mapboxMap.getCameraPosition().zoom;
   * int maxAnimationFps;
   * if (zoom < 5) {
   * maxAnimationFps = 3;
   * } else if (zoom < 10) {
   * maxAnimationFps = 5;
   * } else if (zoom < 15) {
   * maxAnimationFps = 7;
   * } else if (zoom < 18) {
   * maxAnimationFps = 15;
   * } else {
   * maxAnimationFps = Integer.MAX_VALUE;
   * }
   * locationComponent.setMaxAnimationFps(maxAnimationFps);
   * }
   * });
   * </pre>
   *
   *
   * If you're looking for a way to throttle the FPS of the whole map, including other animations and gestures, see
   * [MapView.setMaximumFps].
   *
   * @param maxAnimationFps max location animation FPS
   */
  fun setMaxAnimationFps(maxAnimationFps: Int) {
    checkActivationState()
    locationAnimatorCoordinator.maxAnimationFps = maxAnimationFps
  }

  /**
   * Set the location engine to update the current user location.
   *
   *
   * If `null` is passed in, all updates will have to occur through the
   * [LocationPluginImpl.forceLocationUpdate] method.
   *
   * @param locationEngine a [LocationEngine] this component should use to handle updates
   */
  @SuppressLint("MissingPermission")
  fun setLocationEngine(locationEngine: LocationEngine?) {
    checkActivationState()
    // If internal location engines being used, extra steps need to be taken to deconstruct the instance.
    this.locationEngine?.removeLocationUpdates(currentLocationEngineListener)
    this.locationEngine = null

    if (locationEngine != null) {
      fastestInterval = locationEngineRequest.fastestInterval
      this.locationEngine = locationEngine
      if (isLayerReady && isLocationComponentEnabled) {
        setLastLocation()
        locationEngine.requestLocationUpdates(
          locationEngineRequest, currentLocationEngineListener, Looper.getMainLooper()
        )
      }
    } else {
      fastestInterval = 0
    }
  }

  /**
   * Set the location request that's going to be used when requesting location updates.
   *
   * @param locationEngineRequest the location request
   */
  fun setLocationEngineRequest(locationEngineRequest: LocationEngineRequest) {
    checkActivationState()
    this.locationEngineRequest = locationEngineRequest

    // reset internal LocationEngine ref to re-request location updates if needed
    setLocationEngine(locationEngine)
  }

  /**
   * Get the location request that's going to be used when requesting location updates.
   */
  fun getLocationEngineRequest(): LocationEngineRequest {
    checkActivationState()
    return locationEngineRequest
  }

  /**
   * Returns the current [LocationEngine] being used for updating the user location.
   *
   * @return the [LocationEngine] being used to update the user location
   */
  fun getLocationEngine(): LocationEngine? {
    checkActivationState()
    return locationEngine
  }

  /**
   * Sets the compass engine used to provide compass heading values.
   *
   * @param compassEngine to be used
   */
  fun setCompassEngine(compassEngine: CompassEngine?) {
    checkActivationState()
    if (this.compassEngine != null) {
      updateCompassListenerState(false)
    }
    this.compassEngine = compassEngine
    updateCompassListenerState(true)
  }

  /**
   * Returns the compass engine used to provide compass heading values.
   *
   * @return compass engine currently being used
   */
  fun getCompassEngine(): CompassEngine? {
    checkActivationState()
    return compassEngine
  }

  /**
   * Get the last know location of the location component.
   *
   * @return the last known location
   */
  val lastKnownLocation: Location?
    get() {
      checkActivationState()
      return lastLocation
    }

  /**
   * Adds a listener that gets invoked when the user clicks the displayed location.
   *
   *
   * If there are registered location click listeners and the location is clicked,
   * only [OnLocationClickListener.onLocationComponentClick] is going to be delivered,
   * [com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener.onMapClick] is going to be consumed
   * and not pushed to the listeners registered after the component's activation.
   *
   * @param listener The location click listener that is invoked when the
   * location is clicked
   */
  fun addOnLocationClickListener(listener: OnLocationClickListener) {
    onLocationClickListeners.add(listener)
  }

  /**
   * Removes the passed listener from the current list of location click listeners.
   *
   * @param listener to be removed
   */
  fun removeOnLocationClickListener(listener: OnLocationClickListener) {
    onLocationClickListeners.remove(listener)
  }

  /**
   * Adds a listener that gets invoked when the user long clicks the displayed location.
   *
   *
   * If there are registered location long click listeners and the location is long clicked,
   * only [OnLocationLongClickListener.onLocationComponentLongClick] is going to be delivered,
   * [com.mapbox.mapboxsdk.maps.MapboxMap.OnMapLongClickListener.onMapLongClick] is going to be consumed
   * and not pushed to the listeners registered after the component's activation.
   *
   * @param listener The location click listener that is invoked when the
   * location is clicked
   */
  fun addOnLocationLongClickListener(listener: OnLocationLongClickListener) {
    onLocationLongClickListeners.add(listener)
  }

  /**
   * Removes the passed listener from the current list of location long click listeners.
   *
   * @param listener to be removed
   */
  fun removeOnLocationLongClickListener(listener: OnLocationLongClickListener) {
    onLocationLongClickListeners.remove(listener)
  }

  /**
   * Adds a listener that gets invoked when camera tracking state changes.
   *
   * @param listener Listener that gets invoked when camera tracking state changes.
   */
  fun addOnCameraTrackingChangedListener(listener: OnCameraTrackingChangedListener) {
    onCameraTrackingChangedListeners.add(listener)
  }

  /**
   * Removes a listener that gets invoked when camera tracking state changes.
   *
   * @param listener Listener that gets invoked when camera tracking state changes.
   */
  fun removeOnCameraTrackingChangedListener(listener: OnCameraTrackingChangedListener) {
    onCameraTrackingChangedListeners.remove(listener)
  }

  /**
   * Adds a listener that gets invoked when render mode changes.
   *
   * @param listener Listener that gets invoked when render mode changes.
   */
  fun addOnRenderModeChangedListener(listener: OnRenderModeChangedListener) {
    onRenderModeChangedListeners.add(listener)
  }

  /**
   * Removes a listener that gets invoked when render mode changes.
   *
   * @param listener Listener that gets invoked when render mode changes.
   */
  fun removeRenderModeChangedListener(listener: OnRenderModeChangedListener) {
    onRenderModeChangedListeners.remove(listener)
  }

  /**
   * Adds the passed listener that gets invoked when user updates have stopped long enough for the last update
   * to be considered stale.
   *
   *
   * This timeout is set by [LocationComponentOptions.staleStateTimeout].
   *
   * @param listener invoked when last update is considered stale
   */
  fun addOnLocationStaleListener(listener: OnLocationStaleListener) {
    onLocationStaleListeners.add(listener)
  }

  /**
   * Removes the passed listener from the current list of stale listeners.
   *
   * @param listener to be removed from the list
   */
  fun removeOnLocationStaleListener(listener: OnLocationStaleListener) {
    onLocationStaleListeners.remove(listener)
  }

  /**
   * Adds a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes
   */
  fun addOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener) {
    onIndicatorPositionChangedListener.add(listener)
  }

  /**
   * Removes a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes.
   */
  fun removeOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener) {
    onIndicatorPositionChangedListener.remove(listener)
  }

  /**
   * Internal use.
   */
  fun onDestroy() {}

  /**
   * Internal use.
   */
  fun onStartLoadingMap() {
    onLocationLayerStop()
  }

  /**
   * Internal use.
   */
  fun onFinishLoadingStyle() {
    if (isLocationComponentActivated && !isLayerReady) {
      delegateProvider.getStyle { style = it }
      locationLayerController.initializeComponents(style, options)
      locationCameraController.initializeOptions(options)
      onLocationLayerStart()
    }
  }

  /**
   * Stop the LocationComponent's pulsing circle animation.
   */
  private fun stopPulsingLocationCircle() {
    locationAnimatorCoordinator.stopPulsingCircleAnimation()
    locationLayerController.adjustPulsingCircleLayerVisibility(false)
  }

  @SuppressLint("MissingPermission")
  private fun onLocationLayerStart() {
    if (!isLocationComponentActivated || !isComponentStarted || !delegateProvider.styleStateDelegate.isFullyLoaded()) {
      return
    }
    if (!isLayerReady) {
      isLayerReady = true
      // Add camera listeners here
      if (options.enableStaleState()) {
        staleStateManager.onStart()
      }
    }
    if (isLocationComponentEnabled) {
      locationEngine?.let {
        try {
          it.requestLocationUpdates(
            locationEngineRequest, currentLocationEngineListener, Looper.getMainLooper()
          )
        } catch (se: SecurityException) {
          Logger.e(
            TAG, "Unable to request location updates, $se"
          )
        }
      }
      cameraMode = locationCameraController.cameraMode
      if (options.pulseEnabled()) {
        startPulsingLocationCircle()
      } else {
        stopPulsingLocationCircle()
      }
      setLastLocation()
      updateCompassListenerState(true)
      setLastCompassHeading()
    }
  }

  private fun onLocationLayerStop() {
    if (!isLocationComponentActivated || !isLayerReady || !isComponentStarted) {
      return
    }
    isLayerReady = false
    staleStateManager.onStop()
    if (compassEngine != null) {
      updateCompassListenerState(false)
    }
    locationAnimatorCoordinator.cancelAllAnimations()
    locationEngine?.removeLocationUpdates(currentLocationEngineListener)
    // Remove camera listeners
  }

  private fun initialize(
    context: Context,
    style: StyleManagerInterface,
    locationModelLayerOptions: LocationModelLayerOptions?,
    options: LocationComponentOptions
  ) {
    if (isLocationComponentActivated) {
      return
    }
    isLocationComponentActivated = true

    check(delegateProvider.styleStateDelegate.isFullyLoaded()) { "Style is invalid, provide the most recently loaded one." }

    this.style = style
    this.options = options

    val gesturePlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
      Class.forName(
        PLUGIN_GESTURE_CLASS_NAME
      ) as Class<GesturesPlugin>
    )
      ?: throw InvalidPluginConfigurationException(
        "Can't look up an instance of plugin, " +
          "is it available on the clazz path and loaded through the map?"
      )
    val animationPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
      Class.forName(
        PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
      ) as Class<CameraAnimationsPlugin>
    )
      ?: throw InvalidPluginConfigurationException(
        "Can't look up an instance of plugin, " +
          "is it available on the clazz path and loaded through the map?"
      )

    gesturePlugin.addOnMapClickListener(onMapClickListener)
    gesturePlugin.addOnMapLongClickListener(onMapLongClickListener)

    val sourceProvider = LayerSourceProvider()
    val bitmapProvider = LayerBitmapProvider(context)

    locationLayerController = LocationLayerController(
      delegateProvider, style, sourceProvider, locationModelLayerOptions,
      bitmapProvider, options, indicatorPositionChangedListener, renderModeChangedListener
    )
    locationCameraController = LocationCameraController(
      context,
      delegateProvider,
      gesturePlugin,
      animationPlugin,
      cameraTrackingChangedListener,
      options
    )
    locationAnimatorCoordinator = LocationLayerAnimatorCoordinator(
      delegateProvider.mapProjectionDelegate,
      MapboxAnimatorSetProvider.instance,
      MapboxAnimatorProvider.INSTANCE
    )
    val windowManager =
      context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val sensorManager =
      context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    if (windowManager != null && sensorManager != null) {
      compassEngine = LocationComponentCompassEngine(windowManager, sensorManager)
    }
    staleStateManager = StaleStateManager(onLocationStaleListener, options)

    updateMapWithOptions(options)

    renderMode = RenderMode.NORMAL
    cameraMode = CameraMode.NONE
    updateAnimatorListenerHolders()
    onLocationLayerStart()
  }

  private fun initializeLocationEngine(context: Context) {
    locationEngine?.removeLocationUpdates(currentLocationEngineListener)
    setLocationEngine(internalLocationEngineProvider.getBestLocationEngine(context))
  }

  private fun updateCompassListenerState(canListen: Boolean) {
    compassEngine?.let {
      if (!canListen) { // We shouldn't listen, simply unregistering
        removeCompassListener(it)
        return
      }
      if (!isLocationComponentActivated || !isComponentStarted || !isLocationComponentEnabled || !isLayerReady) {
        return
      }
      if (locationCameraController.isConsumingCompass || locationLayerController.isConsumingCompass) { // If we have a consumer, and not yet listening, then start listening
        if (!isListeningToCompass) {
          isListeningToCompass = true
          it.addCompassListener(compassListener)
        }
      } else { // If we have no consumers, stop listening
        removeCompassListener(it)
      }
    }
  }

  private fun removeCompassListener(engine: CompassEngine) {
    if (isListeningToCompass) {
      isListeningToCompass = false
      engine.removeCompassListener(compassListener)
    }
  }

  private fun enableLocationComponent() {
    isLocationComponentEnabled = true
    onLocationLayerStart()
  }

  private fun disableLocationComponent() {
    isLocationComponentEnabled = false
    locationLayerController.hide()
    onLocationLayerStop()
  }

  private fun updateMapWithOptions(options: LocationComponentOptions) {
    val padding = options.padding()
    if (padding != null && padding.isNotEmpty()) {
      // fixme https://github.com/mapbox/mapbox-maps-android/issues/280
      delegateProvider.mapTransformDelegate.setCamera(
        CameraOptions.Builder().padding(
          EdgeInsets(
            padding[0].toDouble(),
            padding[1].toDouble(),
            padding[2].toDouble(),
            padding[3].toDouble()
          )
        ).build()
      )
    }
  }

  /**
   * Updates the user location icon.
   *
   * @param location the latest user location
   */
  private fun updateLocation(
    location: Location?,
    fromLastLocation: Boolean
  ) {
    if (location != null) {
      updateLocation(
        LocationUpdate(location),
        fromLastLocation
      )
    }
  }

  private fun updateLocation(locationUpdate: LocationUpdate, fromLastLocation: Boolean) {
    if (!isLayerReady) {
      lastLocation = locationUpdate.location
      return
    } else {
      val currentTime = SystemClock.elapsedRealtime()
      lastUpdateTime = if (currentTime - lastUpdateTime < fastestInterval) {
        return
      } else {
        currentTime
      }
    }
    showLocationLayerIfHidden()
    if (!fromLastLocation) {
      staleStateManager.updateLatestLocationTime()
    }

    val locations = getTargetLocationWithIntermediates(
      locationUpdate.location,
      locationUpdate.intermediatePoints
    )
    val animationDuration = locationAnimatorCoordinator.feedNewLocation(
      locations,
      lastLocation,
      if (fromLastLocation) 0L else locationUpdate.animationDuration
    )
    locationCameraController.feedNewCameraLocation(locations, animationDuration)
    updateAccuracyRadius(locationUpdate.location, false)
    lastLocation = locationUpdate.location
  }

  private fun getTargetLocationWithIntermediates(
    location: Location,
    intermediatePoints: List<Location>?
  ): Array<Location> {
    intermediatePoints?.let {
      val locations = ArrayList<Location>(it.size + 1)
      locations.addAll(intermediatePoints)
      locations.add(location)
      return locations.toTypedArray()
    }
    return arrayOf(location)
  }

  private fun showLocationLayerIfHidden() {
    val isLocationLayerHidden = locationLayerController.isHidden
    if (isLocationComponentEnabled && isComponentStarted && isLocationLayerHidden) {
      locationLayerController.show()
      if (options.pulseEnabled()) {
        locationLayerController.adjustPulsingCircleLayerVisibility(true)
      }
    }
  }

  private fun updateCompassHeading(heading: Float) {
    val duration = if (options.compassAnimationEnabled()) COMPASS_UPDATE_RATE_MS else 0L
    locationAnimatorCoordinator.feedNewCompassBearing(heading, duration)
    locationCameraController.feedNewCameraCompassBearing(heading, duration)
  }

  /**
   * If the locationEngine contains a last location value, we use it for the initial location layer
   * position.
   */
  @SuppressLint("MissingPermission")
  private fun setLastLocation() {
    locationEngine?.getLastLocation(lastLocationEngineListener) ?: updateLocation(
      lastKnownLocation,
      true
    )
  }

  private fun setLastCompassHeading() {
    updateCompassHeading(compassEngine?.lastHeading ?: 0.0f)
  }

  private fun updateAccuracyRadius(
    location: Location?,
    noAnimation: Boolean
  ) {
    val radius: Float = location?.accuracy ?: 0f
    locationAnimatorCoordinator.feedNewAccuracyRadius(radius, noAnimation)
  }

  private fun updateAnimatorListenerHolders() {
    locationAnimatorCoordinator.updateAnimatorListenerHolders(locationLayerController.animationListeners)
    locationCameraController.resetAllCameraAnimations()
    locationAnimatorCoordinator.resetAllLayerAnimations()
  }

  private val onMapClickListener = OnMapClickListener {
    if (!onLocationClickListeners.isEmpty()) {
      locationLayerController.onMapClick(it) {
        for (listener in onLocationClickListeners) {
          listener.onLocationComponentClick()
        }
      }
      return@OnMapClickListener true
    }
    false
  }
  private val onMapLongClickListener = OnMapLongClickListener {
    if (!onLocationLongClickListeners.isEmpty()) {
      locationLayerController.onMapClick(it) { expected ->
        expected.value?.let {
          if (it.isNotEmpty()) {
            for (listener in onLocationLongClickListeners) {
              listener.onLocationComponentLongClick()
            }
          }
        }
      }
      return@OnMapLongClickListener true
    }
    false
  }
  private val onLocationStaleListener =
    OnLocationStaleListener { isStale ->
      locationLayerController.setLocationsStale(isStale)
      for (listener in onLocationStaleListeners) {
        listener.onStaleStateChange(isStale)
      }
    }

  private val compassListener: CompassListener = object : CompassListener {
    override fun onCompassChanged(userHeading: Float) {
      updateCompassHeading(userHeading)
    }

    override fun onCompassAccuracyChange(compassStatus: Int) { // Currently don't handle this inside SDK
    }
  }

  @VisibleForTesting
  internal class CurrentLocationEngineCallback(impl: LocationPluginImpl) :
    LocationEngineCallback<LocationEngineResult> {
    private val weakReferenceImpl: WeakReference<LocationPluginImpl> =
      WeakReference(impl)

    override fun onSuccess(result: LocationEngineResult) {
      weakReferenceImpl.get()?.updateLocation(result.lastLocation, false)
    }

    override fun onFailure(exception: Exception) {
      Logger.e(
        TAG,
        "Failed to obtain location update $exception"
      )
    }
  }

  @VisibleForTesting
  internal class LastLocationEngineCallback(impl: LocationPluginImpl) :
    LocationEngineCallback<LocationEngineResult> {
    private val weakReferenceImpl: WeakReference<LocationPluginImpl> =
      WeakReference(impl)

    override fun onSuccess(result: LocationEngineResult) {
      val component =
        weakReferenceImpl.get()
      component?.updateLocation(result.lastLocation, true)
    }

    override fun onFailure(exception: Exception) {
      Logger.e(
        TAG,
        "Failed to obtain last location update $exception"
      )
    }
  }

  @VisibleForTesting
  internal var cameraTrackingChangedListener: OnCameraTrackingChangedListener = object :
    OnCameraTrackingChangedListener {
    override fun onCameraTrackingDismissed() {
      for (listener in onCameraTrackingChangedListeners) {
        listener.onCameraTrackingDismissed()
      }
    }

    override fun onCameraTrackingChanged(currentMode: CameraMode) {
      locationCameraController.cancelZoomAnimation()
      locationCameraController.cancelPitchAnimation()
      updateAnimatorListenerHolders()
      for (listener in onCameraTrackingChangedListeners) {
        listener.onCameraTrackingChanged(currentMode)
      }
    }
  }

  @VisibleForTesting
  internal var renderModeChangedListener = OnRenderModeChangedListener {
    updateAnimatorListenerHolders()
    for (listener in onRenderModeChangedListeners) {
      listener.onRenderModeChanged(it)
    }
  }

  @VisibleForTesting
  internal var indicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    for (listener in onIndicatorPositionChangedListener) {
      listener.onIndicatorPositionChanged(it)
    }
  }

  private val developerAnimationListener = OnDeveloperAnimationListener {
    if (isLocationComponentActivated && isLocationComponentEnabled) {
      cameraMode = CameraMode.NONE
    }
  }

  internal class InternalLocationEngineProvider {
    fun getBestLocationEngine(
      context: Context
    ): LocationEngine {
      return LocationEngineProvider.getBestLocationEngine(context)
    }
  }

  private fun checkActivationState() {
    if (!isLocationComponentActivated) {
      throw LocationComponentNotInitializedException()
    }
  }

  private fun notifyUnsuccessfulCameraOperation(
    callback: CancelableCallback?,
    msg: String?
  ) {
    if (msg != null) {
      Logger.e(
        TAG,
        msg
      )
    }
    callback?.onCancel()
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "started" state.
   */
  override fun onStart() {
    isComponentStarted = true
    onLocationLayerStart()
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    onLocationLayerStop()
    isComponentStarted = false
  }

  /**
   * Called whenever camera position changes.
   */
  override fun onCameraMove(
    lat: Double,
    lon: Double,
    zoom: Double,
    pitch: Double,
    bearing: Double,
    padding: Array<Double>?,
    anchor: Pair<Double, Double>?
  ) {
    if (this::locationCameraController.isInitialized) {
      locationCameraController.onCameraMove()
    }
    if (this::locationLayerController.isInitialized) {
      locationLayerController.setZoomLevel(zoom)
    }
  }

  /**
   * Called when a new Style is loaded.
   */
  override fun onStyleChanged(styleDelegate: StyleManagerInterface) {
    Logger.d(TAG, "onStyleChanged")
    onStartLoadingMap()
    onFinishLoadingStyle()
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-LocationComponent"
  }
}

/**
 * Extension function to the the LocationComponentPlugin instance.
 */
@Deprecated(
  "Use getLocationComponentPlugin instead.",
  ReplaceWith("getLocationComponentPlugin")
)
fun MapPluginProviderDelegate.locationLegacy(): LocationPluginImpl {
  return this.getPlugin(LocationPluginImpl::class.java)!!
}