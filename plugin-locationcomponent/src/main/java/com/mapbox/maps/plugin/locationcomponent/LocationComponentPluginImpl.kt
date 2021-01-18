package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentAttributeParser
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettingsBase
import com.mapbox.maps.plugin.locationcomponent.listeneres.OnLocationStaleListener
import java.lang.ref.WeakReference

/**
 * Default implementation of the LocationComponentPlugin, it renders the configured location puck
 * to the user's current location.
 */
class LocationComponentPluginImpl : LocationComponentPlugin, LocationConsumer, LocationComponentSettingsBase() {
  private lateinit var delegateProvider: MapDelegateProvider
  private lateinit var puckPresetProvider: PuckPresetProvider
  private lateinit var context: WeakReference<Context>

  private var bitmapProvider = LayerBitmapProvider()
  private var locationPuckManager: LocationPuckManager? = null
  private var locationProvider: LocationProvider? = null
  private var staleStateManager: StaleStateManager? = null

  private var isLocationComponentActivated = false

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
   * Called when a new Style is being loaded.
   */
  override fun onStyleLoading() {
    // TODO("Not yet implemented")
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
      if (staleStateManager == null) {
        staleStateManager = StaleStateManager(
          onLocationStaleListener,
          internalSettings.staleStateEnabled,
          internalSettings.staleStateTimeout
        )
      }

      delegateProvider.getStyle { style ->
        if (locationPuckManager == null) {
          locationPuckManager = LocationPuckManager(
            settings = internalSettings,
            delegateProvider = delegateProvider,
            style = style,
            layerSourceProvider = LayerSourceProvider(),
            bitmapProvider = bitmapProvider,
            presetProvider = puckPresetProvider
          )
        }
        locationPuckManager?.let {
          if (!it.isLayerInitialised()) {
            it.initialize(style)
          }
        }
        locationPuckManager?.onStart()
        locationProvider?.registerLocationConsumer(this)
        staleStateManager?.onStart()
        isLocationComponentActivated = true
      }
    }
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    locationPuckManager?.onStop()
    staleStateManager?.onStop()
    locationProvider?.unRegisterLocationConsumer(this)
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
    puckPresetProvider = PuckPresetProvider(context)
    if (internalSettings.enabled && locationProvider == null) {
      locationProvider = LocationProviderImpl(context)
    }
  }

  /**
   * Called whenever the location is updated.
   * Supports multiple points to create more complex animations with intermediate points.
   * Last [location] value will always be first animator target for next animation.
   */
  override fun onLocationUpdated(vararg location: Point) {
    locationPuckManager?.updateCurrentPosition(*location)
    staleStateManager?.updateLatestLocationTime()
  }

  /**
   * Called whenever the bearing is updated.
   * Supports multiple bearing values to create more complex animations with intermediate points.
   * Last [bearing] value will always be first animator target for next animation.
   */
  override fun onBearingUpdated(vararg bearing: Double) {
    locationPuckManager?.updateCurrentBearing(*bearing)
  }

  /**
   * Update [ValueAnimator] options that will be used to animate between [Point] updates.
   */
  override fun onPuckLocationAnimatorOptionsUpdated(options: ValueAnimator.() -> Unit) {
    locationPuckManager?.updateLocationAnimator(options)
  }

  /**
   * Update [ValueAnimator] options that will be used to animate between bearing [Double] updates.
   */
  override fun onPuckBearingAnimatorOptionsUpdated(options: ValueAnimator.() -> Unit) {
    locationPuckManager?.updateBearingAnimator(options)
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
  }

  private val onLocationStaleListener =
    object : OnLocationStaleListener {
      override fun onStaleStateChange(isStale: Boolean) {
        locationPuckManager?.setLocationsStale(isStale)
      }
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
    locationPuckManager?.updateSettings(internalSettings)
  }
}

/**
 * Extension function to the the LocationComponentPlugin instance.
 */
fun MapPluginProviderDelegate.getLocationComponentPlugin(): LocationComponentPlugin {
  return this.getPlugin(LocationComponentPluginImpl::class.java)!!
}