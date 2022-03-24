package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxLocationComponentException
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.*
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentAttributeParser
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Default implementation of the LocationComponentPlugin, it renders the configured location puck
 * to the user's current location.
 */
class LocationComponentPluginImpl : LocationComponentPlugin2, LocationConsumer2,
  LocationComponentSettingsBase2() {
  private lateinit var delegateProvider: MapDelegateProvider

  private lateinit var context: WeakReference<Context>

  @VisibleForTesting(otherwise = PRIVATE)
  internal var locationPuckManager: LocationPuckManager? = null

  @VisibleForTesting(otherwise = PRIVATE)
  internal var locationProvider: LocationProvider? = null

  @VisibleForTesting(otherwise = PRIVATE)
  internal var isLocationComponentActivated = false

  private val onIndicatorPositionChangedListeners =
    CopyOnWriteArraySet<OnIndicatorPositionChangedListener>()

  private val onIndicatorBearingChangedListeners =
    CopyOnWriteArraySet<OnIndicatorBearingChangedListener>()

  private val onIndicatorAccuracyRadiusChangedListeners =
    CopyOnWriteArraySet<OnIndicatorAccuracyRadiusChangedListener>()

  /**
   * Adds a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes
   */
  override fun addOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener) {
    onIndicatorPositionChangedListeners.add(listener)
  }

  /**
   * Removes a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes.
   */
  override fun removeOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener) {
    onIndicatorPositionChangedListeners.remove(listener)
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal val indicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    for (listener in onIndicatorPositionChangedListeners) {
      listener.onIndicatorPositionChanged(it)
    }
  }

  /**
   * Adds a listener that gets invoked when indicator bearing changes.
   *
   * @param listener Listener that gets invoked when indicator bearing changes
   */
  override fun addOnIndicatorBearingChangedListener(listener: OnIndicatorBearingChangedListener) {
    onIndicatorBearingChangedListeners.add(listener)
  }

  /**
   * Removes a listener that gets invoked when indicator bearing changes.
   *
   * @param listener Listener that gets invoked when indicator bearing changes.
   */
  override fun removeOnIndicatorBearingChangedListener(listener: OnIndicatorBearingChangedListener) {
    onIndicatorBearingChangedListeners.remove(listener)
  }

  /**
   * Adds a listener that gets invoked when indicator accuracy radius changes.
   *
   * @param listener Listener that gets invoked when indicator accuracy radius changes
   */
  override fun addOnIndicatorAccuracyRadiusChangedListener(listener: OnIndicatorAccuracyRadiusChangedListener) {
    onIndicatorAccuracyRadiusChangedListeners.add(listener)
  }

  /**
   * Removes a listener that gets invoked when indicator accuracy radius changes.
   *
   * @param listener Listener that gets invoked when indicator accuracy radius changes.
   */
  override fun removeOnIndicatorAccuracyRadiusChangedListener(listener: OnIndicatorAccuracyRadiusChangedListener) {
    onIndicatorAccuracyRadiusChangedListeners.remove(listener)
  }

  /**
   * Check whether the rendered location puck is on the given point.
   *
   * @param point the point to validate
   * @param listener Listener that gets invoked when the validation finished.
   */
  override fun isLocatedAt(point: Point, listener: PuckLocatedAtPointListener) {
    delegateProvider.mapFeatureQueryDelegate.queryRenderedFeatures(
      RenderedQueryGeometry(delegateProvider.mapCameraManagerDelegate.pixelForCoordinate(point)),
      RenderedQueryOptions(
        listOf(
          LOCATION_INDICATOR_LAYER,
          MODEL_LAYER
        ),
        null
      )
    ) { expected ->
      expected.value?.let {
        if (it.isNotEmpty()) {
          listener.onResult(true)
        } else {
          listener.onResult(false)
        }
      }
      expected.error?.let {
        throw MapboxLocationComponentException(it)
      }
    }
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal val indicatorBearingChangedListener = OnIndicatorBearingChangedListener {
    for (listener in onIndicatorBearingChangedListeners) {
      listener.onIndicatorBearingChanged(it)
    }
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal val indicatorAccuracyRadiusChangedListener = OnIndicatorAccuracyRadiusChangedListener {
    for (listener in onIndicatorAccuracyRadiusChangedListeners) {
      listener.onIndicatorAccuracyRadiusChanged(it)
    }
  }

  /**
   * Set the LocationProvider, it will replace the default location provider provided by the LocationComponentPlugin.
   */
  override fun setLocationProvider(locationProvider: LocationProvider) {
    this.locationProvider?.unRegisterLocationConsumer(this)
    this.locationProvider = locationProvider
    if (isLocationComponentActivated) {
      locationProvider.registerLocationConsumer(this)
    }
  }

  /**
   * Get the current LocationProvider under usage with the LocationComponentPlugin.
   *
   * @return The location provider currently under usage, and will return null if the location component plugin is not enabled and not initialised.
   */
  override fun getLocationProvider(): LocationProvider? {
    return locationProvider
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "started" state.
   */
  override fun onStart() {
    activateLocationComponent()
  }

  private fun activateLocationComponent() {
    if (internalSettings.enabled) {
      delegateProvider.getStyle { style ->
        if (locationPuckManager?.isLayerInitialised() == true && isLocationComponentActivated) {
          return@getStyle
        }
        if (locationPuckManager == null) {
          locationPuckManager = LocationPuckManager(
            settings = internalSettings,
            settings2 = internalSettings2,
            delegateProvider = delegateProvider,
            positionManager = LocationComponentPositionManager(
              style,
              internalSettings.layerAbove,
              internalSettings.layerBelow
            ),
            layerSourceProvider = LayerSourceProvider(),
            animationManager = PuckAnimatorManager(
              indicatorPositionChangedListener,
              indicatorBearingChangedListener,
              indicatorAccuracyRadiusChangedListener
            )
          )
        }
        locationPuckManager?.initialize(style)
        locationPuckManager?.onStart()
        locationProvider?.registerLocationConsumer(this)
        isLocationComponentActivated = true
      }
    }
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    isLocationComponentActivated = false
    locationPuckManager?.onStop()
    locationProvider?.unRegisterLocationConsumer(this)
  }

  private fun deactivateLocationComponent() {
    locationPuckManager?.cleanUp()
    locationPuckManager = null
    locationProvider?.unRegisterLocationConsumer(this)
    isLocationComponentActivated = false
  }

  /**
   * Bind the ViewPlugin with current map context. This will create a View that
   * will be added to the MapView.
   *
   * @param context The hosting context
   * @param attrs parent attributes
   * @param pixelRatio the pixel ratio of the device
   * @return View that will be added to the MapView
   */
  override fun bind(context: Context, attrs: AttributeSet?, pixelRatio: Float) {
    this.context = WeakReference(context)
    internalSettings =
      LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, pixelRatio)
    internalSettings2 =
      LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, pixelRatio)

    if (internalSettings.enabled && locationProvider == null) {
      locationProvider = DefaultLocationProvider(context.applicationContext).apply {
        updatePuckBearingSource(internalSettings2.puckBearingSource)
      }
    }
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal fun bind(
    context: Context,
    attrs: AttributeSet?,
    pixelRatio: Float,
    locationProvider: LocationProvider,
    locationPuckManager: LocationPuckManager
  ) {
    this.context = WeakReference(context)
    this.internalSettings =
      LocationComponentAttributeParser.parseLocationComponentSettings(context, attrs, pixelRatio)
    this.internalSettings2 =
      LocationComponentAttributeParser2.parseLocationComponentSettings2(context, attrs, pixelRatio)
    this.locationProvider = locationProvider
    this.locationPuckManager = locationPuckManager
  }

  /**
   * Called whenever the location is updated.
   * @param location - supports multiple points to create more complex animations with intermediate points.
   *  Last [location] value will always be the animator target for next animation.
   * @param options - if specified explicitly will apply current animator option to single location update animation.
   *  Otherwise default animator options will be used.
   */
  override fun onLocationUpdated(vararg location: Point, options: (ValueAnimator.() -> Unit)?) {
    locationPuckManager?.updateCurrentPosition(*location, options = options)
  }

  /**
   * Called whenever the bearing is updated.
   * @param bearing - supports multiple bearing values to create more complex animations with intermediate points.
   *  Last [bearing] value will always be the animator target for next animation.
   * @param options - if specified explicitly will apply current animator option to single location bearing animation.
   *  Otherwise default animator options will be used.
   */
  override fun onBearingUpdated(vararg bearing: Double, options: (ValueAnimator.() -> Unit)?) {
    locationPuckManager?.updateCurrentBearing(*bearing, options = options)
  }

  /**
   * Called whenever the accuracy radius is updated.
   * @param radius - supports multiple radius value to create more complex animations with intermediate points.
   *  Last [radius] value will always be the animator target for next animation.
   * @param options - if specified explicitly will apply current animator option to radius animation.
   *  Otherwise default animator options will be used.
   */
  override fun onAccuracyRadiusUpdated(
    vararg radius: Double,
    options: (ValueAnimator.() -> Unit)?
  ) {
    locationPuckManager?.updateAccuracyRadius(*radius, options = options)
  }

  /**
   * Update [ValueAnimator] options that will be used to animate between [Point] updates by default.
   * This will apply to all upcoming updates.
   */
  override fun onPuckLocationAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit) {
    locationPuckManager?.updateLocationAnimator(options)
  }

  /**
   * Update [ValueAnimator] options that will be used to animate between bearing [Double] updates by default.
   * This will apply to all upcoming updates.
   */
  override fun onPuckBearingAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit) {
    locationPuckManager?.updateBearingAnimator(options)
  }

  /**
   * Update [ValueAnimator] options that will be used to animate between accuracy radius [Double] updates by default.
   * This will apply to all upcoming updates.
   */
  override fun onPuckAccuracyRadiusAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit) {
    locationPuckManager?.updateAccuracyRadiusAnimator(options)
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
  }

  /**
   * Called when a new Style is loaded.
   *
   * @param styleDelegate
   */
  override fun onStyleChanged(styleDelegate: StyleInterface) {
    locationPuckManager?.updateStyle(styleDelegate)
  }

  override lateinit var internalSettings: LocationComponentSettings

  /**
   * Apply the changes to the LocationComponentSettings to the LocationComponentPlugin.
   */
  override fun applySettings() {
    if (internalSettings.enabled && !isLocationComponentActivated) {
      context.get()?.let {
        if (locationProvider == null) {
          locationProvider = DefaultLocationProvider(it)
        }
        activateLocationComponent()
      }
    }
    if (internalSettings.enabled) {
      locationPuckManager?.updateSettings(internalSettings)
    } else {
      deactivateLocationComponent()
    }
  }

  override lateinit var internalSettings2: LocationComponentSettings2

  override fun applySettings2() {
    if (internalSettings.enabled) {
      locationPuckManager?.updateSettings2(internalSettings2)
      (locationProvider as? DefaultLocationProvider)?.updatePuckBearingSource(internalSettings2.puckBearingSource)
    }
  }
}