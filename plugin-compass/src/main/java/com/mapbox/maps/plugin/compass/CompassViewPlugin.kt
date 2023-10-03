package com.mapbox.maps.plugin.compass

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RestrictTo
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.doOnEnd
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_CAMERA_PLUGIN_ID
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.compass.generated.CompassAttributeParser
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.compass.generated.CompassSettingsBase
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.abs

/**
 * Concrete implementation of CompassPlugin.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class CompassViewPlugin(
  private val viewImplProvider: (Context) -> CompassViewImpl = { CompassViewImpl(it) },
  @SuppressLint("Recycle")
  private val fadeAnimator: ValueAnimator = ValueAnimator.ofFloat(1f, 0f)
) : CompassPlugin, CompassSettingsBase() {

  private lateinit var compassView: CompassView
  private lateinit var mapCameraManager: MapCameraManagerDelegate
  internal var bearing: Double = DEFAULT_BEARING
  private var animationPlugin: CameraAnimationsPlugin? = null

  private var isHidden = false

  override var internalSettings: CompassSettings = CompassSettings { }

  private val compassClickListeners: CopyOnWriteArraySet<OnCompassClickListener> =
    CopyOnWriteArraySet()

  init {
    fadeAnimator.apply {
      duration = TIME_FADE_ANIMATION
      startDelay = TIME_WAIT_IDLE
      doOnEnd {
        compassView.isCompassVisible = false
      }
      addUpdateListener {
        val value = it.animatedValue as Float
        if (value < internalSettings.opacity) {
          compassView.setCompassAlpha(value)
        }
      }
    }
  }

  override fun applySettings() {
    compassView.apply {
      compassGravity = internalSettings.position
      internalSettings.image?.let { imageHolder ->
        val context = (this as CompassViewImpl).context
        imageHolder.bitmap?.let {
          compassImage = BitmapDrawable(context.resources, it)
        }
        imageHolder.drawableId?.let { id ->
          if (id != -1) {
            compassImage = AppCompatResources.getDrawable(context, id)!!
          }
        }
      }
      compassRotation = internalSettings.rotation
      isCompassEnabled = internalSettings.enabled
      setCompassAlpha(internalSettings.opacity)
      setCompassMargins(
        internalSettings.marginLeft.toInt(),
        internalSettings.marginTop.toInt(),
        internalSettings.marginRight.toInt(),
        internalSettings.marginBottom.toInt()
      )
      update(bearing)
      requestLayout()
    }
  }

  /**
   * Defines whether the plugins is enabled or disabled.
   */
  override var enabled: Boolean
    get() = compassView.isCompassEnabled
    set(value) {
      internalSettings = internalSettings.toBuilder().setEnabled(value).build()
      compassView.isCompassEnabled = value
      update(bearing)
      if (value && !shouldHideCompass()) {
        compassView.setCompassAlpha(internalSettings.opacity)
        compassView.isCompassVisible = true
      } else {
        compassView.setCompassAlpha(0f)
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
    mapCameraManager = delegateProvider.mapCameraManagerDelegate
    bearing = mapCameraManager.cameraState.bearing
    animationPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(MAPBOX_CAMERA_PLUGIN_ID)
      ?: throw InvalidPluginConfigurationException(
        "Can't look up an instance of plugin, " +
          "is it available on the clazz path and loaded through the map?"
      )
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "started" state.
   */
  override fun onStart() {
    update(bearing)
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    fadeAnimator.cancel()
  }

  /**
   * Called whenever camera position changes.
   * Could be invoked from any thread when map starts rendering.
   */
  override fun onCameraMove(
      center: Point,
      zoom: Double,
      pitch: Double,
      bearing: Double,
      padding: EdgeInsets
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
        CameraOptions.Builder().bearing(DEFAULT_BEARING).build(),
        mapAnimationOptions {
          owner(MapAnimationOwnerRegistry.COMPASS)
          duration(BEARING_NORTH_ANIMATION_DURATION)
        }
      ) ?: mapCameraManager.setCamera(CameraOptions.Builder().bearing(DEFAULT_BEARING).build())
      compassClickListeners.forEach { it.onCompassClick() }
    }
  }

  private fun update(bearing: Double) {
    this.bearing = bearing
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
        compassView.setCompassAlpha(0f)
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
  private companion object {
    private const val DEFAULT_BEARING = 0.0
    private const val TIME_WAIT_IDLE = 500L
    private const val TIME_FADE_ANIMATION = TIME_WAIT_IDLE
    private const val BEARING_NORTH_ANIMATION_DURATION = 300L
  }
}