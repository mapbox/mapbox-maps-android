package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mapbox.maps.Projection.getMetersPerPixelAtLatitude
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarAttributeParser
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettingsBase

/**
 * Concrete implementation of ScaleBarViewPlugin.
 */
open class ScaleBarPluginImpl(
  private val viewImplProvider: (Context) -> ScaleBarImpl = { ScaleBarImpl(it) }
) : ScaleBarPlugin, ScaleBarSettingsBase() {

  private lateinit var scaleBar: ScaleBar
  private lateinit var mapCameraDelegate: MapCameraDelegate
  private lateinit var mapListenerDelegate: MapListenerDelegate
  private lateinit var mapTransformDelegate: MapTransformDelegate

  override var internalSettings: ScaleBarSettings = ScaleBarSettings()

  private val cameraChangeListener = OnCameraChangeListener {
    invalidateScaleBar()
  }

  override fun applySettings() {
    scaleBar.settings = internalSettings
  }

  /**
   * Invoked when MapView's width and height have changed.
   * @param width the width of mapView
   * @param height the height of mapView
   */
  override fun onSizeChanged(width: Int, height: Int) {
    scaleBar.mapViewWidth = width.toFloat()
    if (enabled) {
      invalidateScaleBar()
    }
  }

  /**
   * Bind the ViewPlugin with current map context. This will create a View that
   * will be added to the MapView.
   *
   * @param mapView parent view which can be used to fetch [android.content.Context] or [ViewGroup.LayoutParams]
   * @return View that will be added to the MapView
   */
  override fun bind(mapView: FrameLayout, attrs: AttributeSet?, pixelRatio: Float): View {
    internalSettings =
      ScaleBarAttributeParser.parseScaleBarSettings(mapView.context, attrs, pixelRatio)
    return viewImplProvider(mapView.context)
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    mapListenerDelegate.removeOnCameraChangeListener(cameraChangeListener)
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    applySettings()
    mapListenerDelegate.addOnCameraChangeListener(cameraChangeListener)
  }

  /**
   * Invalid scale bar
   */
  internal fun invalidateScaleBar() {
    val metersPerPixelAtLatitude = getMetersPerPixelAtLatitude(
      mapCameraDelegate.getLat(),
      mapCameraDelegate.getZoom()
    )
    val pixelRatio = mapTransformDelegate.getMapOptions().pixelRatio
    scaleBar.distancePerPixel = (metersPerPixelAtLatitude / pixelRatio).toFloat()
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapCameraDelegate = delegateProvider.mapCameraDelegate
    mapListenerDelegate = delegateProvider.mapListenerDelegate
    mapTransformDelegate = delegateProvider.mapTransformDelegate
  }

  /**
   * Provides a view instances returned in [bind] after it's been added to the MapView.
   *
   * @param view plugin view
   */
  override fun onPluginView(view: View) {
    scaleBar = view as? ScaleBar
      ?: throw IllegalArgumentException("The provided view needs to implement ScaleBarContract.ScaleBarView")
  }

  /**
   * Defines whether the plugins is enabled or disabled.
   */
  override var enabled: Boolean
    get() = internalSettings.enabled
    set(value) {
      if (value) {
        mapListenerDelegate.addOnCameraChangeListener(cameraChangeListener)
        invalidateScaleBar()
      } else {
        mapListenerDelegate.removeOnCameraChangeListener(cameraChangeListener)
      }
      internalSettings.enabled = value
      scaleBar.enable = value
    }

  /**
   * How many meters in each pixel.
   */
  override var distancePerPixel: Float
    get() = scaleBar.distancePerPixel
    set(value) {
      scaleBar.distancePerPixel = value
    }
}

/**
 * Extension function for MapView to get the ScaleBar plugin instance.
 *
 * @return ScaleBar plugin instance
 */
fun MapPluginProviderDelegate.getScaleBarPlugin(): ScaleBarPluginImpl {
  return this.getPlugin(ScaleBarPluginImpl::class.java)!!
}