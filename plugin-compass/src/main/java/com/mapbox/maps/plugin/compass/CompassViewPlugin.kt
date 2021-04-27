package com.mapbox.maps.plugin.compass

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.animation.addListener
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.compass.generated.CompassAttributeParser
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.compass.generated.CompassSettingsBase
import com.mapbox.maps.plugin.delegates.MapCameraDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.abs

/**
 * Concrete implementation of CompassPlugin.
 */
open class CompassViewPlugin(
  private val viewImplProvider: (Context) -> CompassViewImpl = { CompassViewImpl(it) },
  private val fadeAnimator: ValueAnimator = ValueAnimator.ofFloat(1f, 0f)
) :
  CompassPlugin, CompassSettingsBase() {

  private lateinit var compassView: CompassView
  private lateinit var mapCameraDelegate: MapCameraDelegate
  private var animationPlugin: CameraAnimationsPlugin? = null

  private var isHidden = false

  override var internalSettings: CompassSettings = CompassSettings()

  private val compassClickListeners: CopyOnWriteArrayList<OnCompassClickListener> =
    CopyOnWriteArrayList()

  init {
    fadeAnimator.duration = TIME_FADE_ANIMATION
    fadeAnimator.startDelay = TIME_WAIT_IDLE
    fadeAnimator.addListener(
      {
        // onEnd
        compassView.isCompassVisible = false
      }
    )
    fadeAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      if (value < internalSettings.opacity) {
        compassView.setCompassAlpha(value)
      }
    }
  }

  override fun applySettings() {
    compassView.compassGravity = internalSettings.position
    internalSettings.image?.let {
      compassView.compassImage = it
    }
    compassView.compassRotation = internalSettings.rotation
    compassView.isCompassEnabled = internalSettings.enabled
    compassView.setCompassAlpha(internalSettings.opacity)
    compassView.setCompassMargins(
      internalSettings.marginLeft.toInt(),
      internalSettings.marginTop.toInt(),
      internalSettings.marginRight.toInt(),
      internalSettings.marginBottom.toInt()
    )
    update(mapCameraDelegate.getBearing())
    compassView.requestLayout()
  }

  /**
   * Defines whether the plugins is enabled or disabled.
   */
  override var enabled: Boolean
    get() = compassView.isCompassEnabled
    set(value) {
      internalSettings.enabled = value
      compassView.isCompassEnabled = value
      update(mapCameraDelegate.getBearing())
      if (value && !shouldHideCompass()) {
        compassView.setCompassAlpha(1.0f)
        compassView.isCompassVisible = true
      } else {
        compassView.setCompassAlpha(0.0f)
        compassView.isCompassVisible = false
      }
    }

  /**
   * Bind the ViewPlugin with current map context. This will create a View that
   * will be added to the MapView.
   *
   * @param mapView parent view which can be used to fetch [android.content.Context] or [ViewGroup.LayoutParams]
   * @param attrs parent attributes
   * @param pixelRatio the pixel ratio of the device
   * @return View that will be added to the MapView
   */
  override fun bind(mapView: FrameLayout, attrs: AttributeSet?, pixelRatio: Float): View {
    internalSettings =
      CompassAttributeParser.parseCompassSettings(mapView.context, attrs, pixelRatio)
    return viewImplProvider(mapView.context).also {
      it.injectPresenter(this)
    }
  }

  /**
   * Provides a view instances returned in [bind] after it's been added to the MapView.
   *
   * @param view plugin view
   */
  override fun onPluginView(view: View) {
    compassView = view as? CompassView
      ?: throw IllegalArgumentException("The provided view needs to implement CompassContract.CompassView")
    updateVisibility(false)
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    applySettings()
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    compassClickListeners.clear()
    fadeAnimator.cancel()
    compassView.isCompassEnabled = false
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapCameraDelegate = delegateProvider.mapCameraDelegate
    animationPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
      Class.forName(
        PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
      ) as Class<CameraAnimationsPlugin>
    )
      ?: throw InvalidPluginConfigurationException(
        "Can't look up an instance of plugin, " +
          "is it available on the clazz path and loaded through the map?"
      )
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "started" state.
   */
  override fun onStart() {
    update(mapCameraDelegate.getBearing())
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    fadeAnimator.cancel()
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
    update(bearing)
  }

  /**
   * Add an OnClick listener to the presenter.
   *
   * @param onClickListener Listener for OnClick events
   */
  override fun addCompassClickListener(onClickListener: OnCompassClickListener) {
    compassClickListeners.add(onClickListener)
  }

  /**
   * Remove an OnClick listener from the presenter.
   *
   * @param onClickListener Listener for OnClick events
   */
  override fun removeCompassClickListener(onClickListener: OnCompassClickListener) {
    compassClickListeners.remove(onClickListener)
  }

  /**
   * Invoked when the compass view is clicked.
   */
  override fun onCompassClicked() {
    if (internalSettings.clickable) {
      animationPlugin?.flyTo(
        CameraOptions.Builder().bearing(0.0).build(),
        mapAnimationOptions {
          owner(MapAnimationOwnerRegistry.COMPASS)
          duration(BEARING_NORTH_ANIMATION_DURATION)
        }
      ) ?: mapCameraDelegate.setBearing(0.0)
      compassClickListeners.forEach { it.onCompassClick() }
    }
  }

  private fun update(bearing: Double) {
    compassView.compassRotation = -bearing.toFloat()
    updateVisibility()
  }

  private fun updateVisibility(withAnimator: Boolean = true) {
    if (!compassView.isCompassEnabled) {
      return
    }

    if (shouldHideCompass()) {
      if (isHidden) {
        return
      }
      isHidden = true
      if (withAnimator) {
        fadeAnimator.start()
      } else {
        compassView.isCompassVisible = false
        compassView.setCompassAlpha(0.0f)
      }
    } else {
      isHidden = false
      fadeAnimator.cancel()
      compassView.isCompassVisible = true
      compassView.setCompassAlpha(internalSettings.opacity)
    }
  }

  private fun shouldHideCompass(): Boolean {
    return internalSettings.fadeWhenFacingNorth && isFacingNorth()
  }

  private fun isFacingNorth(): Boolean {
    // increase range of facing north to more than only 0.0
    return abs(compassView.compassRotation) >= 359.0 || abs(compassView.compassRotation) <= 1.0
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TIME_WAIT_IDLE = 500L
    private const val TIME_FADE_ANIMATION = TIME_WAIT_IDLE
    private const val BEARING_NORTH_ANIMATION_DURATION = 300L
  }
}

/**
 * Extension val for MapView to get the Compass View plugin instance.
 */
val MapPluginProviderDelegate.compass: CompassPlugin
  get() = this.getPlugin(CompassViewPlugin::class.java)!!