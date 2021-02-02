package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentAttributeParser
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettingsBase
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Default implementation of the LocationComponentPlugin, it renders the configured location puck
 * to the user's current location.
 */
class LocationComponentPluginImpl : LocationComponentPlugin, LocationConsumer,
  LocationComponentSettingsBase() {
  private lateinit var delegateProvider: MapDelegateProvider
  private lateinit var context: WeakReference<Context>

  private var locationPuckManager: LocationPuckManager? = null
  private var locationProvider: LocationProvider? = null

  private var isLocationComponentActivated = false

  private val onIndicatorPositionChangedListener =
    CopyOnWriteArrayList<OnIndicatorPositionChangedListener>()

  private val onIndicatorBearingChangedListener =
    CopyOnWriteArrayList<OnIndicatorBearingChangedListener>()

  /**
   * Adds a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes
   */
  override fun addOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener) {
    onIndicatorPositionChangedListener.add(listener)
  }

  /**
   * Removes a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes.
   */
  override fun removeOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener) {
    onIndicatorPositionChangedListener.remove(listener)
  }

  private val indicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    for (listener in onIndicatorPositionChangedListener) {
      listener.onIndicatorPositionChanged(it)
    }
  }

  /**
   * Adds a listener that gets invoked when indicator bearing changes.
   *
   * @param listener Listener that gets invoked when indicator bearing changes
   */
  override fun addOnIndicatorBearingChangedListener(listener: OnIndicatorBearingChangedListener) {
    onIndicatorBearingChangedListener.add(listener)
  }

  /**
   * Removes a listener that gets invoked when indicator bearing changes.
   *
   * @param listener Listener that gets invoked when indicator bearing changes.
   */
  override fun removeOnIndicatorBearingChangedListener(listener: OnIndicatorBearingChangedListener) {
    onIndicatorBearingChangedListener.remove(listener)
  }

  private val indicatorBearingChangedListener = OnIndicatorBearingChangedListener {
    for (listener in onIndicatorBearingChangedListener) {
      listener.onIndicatorBearingChanged(it)
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
   * Called when a new Style is being loaded.
   */
  override fun onStyleLoading() {
    // no-ops
  }

  /**
   * Called when a new Style is loaded.
   */
  override fun onStyleChanged(styleDelegate: StyleManagerInterface) {
    locationPuckManager?.let {
      if (!it.isLayerInitialised()) {
        it.initialize(styleDelegate)
      }
    }
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
            delegateProvider = delegateProvider,
            positionManager = LocationComponentPositionManager(
              style,
              internalSettings.layerAbove,
              internalSettings.layerBelow
            ),
            layerSourceProvider = LayerSourceProvider(),
            animationManager = PuckAnimatorManager(
              indicatorPositionChangedListener,
              indicatorBearingChangedListener
            )
          )
        }
        locationPuckManager?.let {
          if (!it.isLayerInitialised()) {
            it.initialize(style)
          }
        }
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
    if (internalSettings.enabled && locationProvider == null) {
      locationProvider = LocationProviderImpl(context)
    }
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
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
  }

  override lateinit var internalSettings: LocationComponentSettings

  /**
   * Apply the changes to the LocationComponentSettings to the LocationComponentPlugin.
   */
  override fun applySettings() {
    if (internalSettings.enabled && !isLocationComponentActivated) {
      context.get()?.let {
        if (locationProvider == null) {
          locationProvider = LocationProviderImpl(it)
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
}

/**
 * Extension function to the the LocationComponentPlugin instance.
 */
fun MapPluginProviderDelegate.getLocationComponentPlugin(): LocationComponentPlugin {
  return this.getPlugin(LocationComponentPluginImpl::class.java)!!
}