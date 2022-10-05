package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mapbox.maps.CameraState
import com.mapbox.maps.Projection.getMetersPerPixelAtLatitude
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
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
  private lateinit var mapListenerDelegate: MapListenerDelegate
  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate

  override var internalSettings: ScaleBarSettings = ScaleBarSettings()

  private val cameraChangeListener = OnCameraChangeListener {
    invalidateScaleBar()
  }

  override fun applySettings() {
    scaleBar.settings = internalSettings
    invalidateScaleBar()
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
    val scaleBarImpl = viewImplProvider(mapView.context)
    (scaleBarImpl as ScaleBar).pixelRatio = pixelRatio
    return scaleBarImpl
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
  private fun invalidateScaleBar() {
    val cameraState: CameraState = mapCameraManagerDelegate.cameraState
    val metersPerPixelAtLatitude = getMetersPerPixelAtLatitude(
      cameraState.center.latitude(),
      cameraState.zoom
    )
    val pixelRatio = mapTransformDelegate.getMapOptions().pixelRatio
    scaleBar.distancePerPixel = (metersPerPixelAtLatitude / pixelRatio).toFloat()
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapCameraManagerDelegate = delegateProvider.mapCameraManagerDelegate
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

  /**
   * If set to True scale bar will be triggering onDraw depending on [ScaleBarSettings.refreshInterval]
   * even if actual data did not change. If set to False scale bar will redraw only on demand.
   *
   * Defaults to False and should not be changed explicitly in most cases.
   * Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  override var useContinuousRendering: Boolean
    get() = scaleBar.useContinuousRendering
    set(value) {
      scaleBar.useContinuousRendering = value
    }
}