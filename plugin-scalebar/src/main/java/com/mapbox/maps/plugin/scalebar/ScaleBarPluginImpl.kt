package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mapbox.maps.Projection.getMetersPerPixelAtLatitude
import com.mapbox.maps.plugin.delegates.*
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
  private lateinit var mapTransformDelegate: MapTransformDelegate

  override var internalSettings: ScaleBarSettings = ScaleBarSettings()

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
      invalidateScaleBar(mapCameraDelegate.getLat(), mapCameraDelegate.getZoom())
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
   * Called when the map camera is moved
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
    invalidateScaleBar(lat, zoom)
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    applySettings()
  }

  /**
   * Invalid scale bar
   */
  private fun invalidateScaleBar(lat: Double, zoom: Double) {
    val metersPerPixelAtLatitude = getMetersPerPixelAtLatitude(lat, zoom)
    val pixelRatio = mapTransformDelegate.getMapOptions().pixelRatio
    scaleBar.distancePerPixel = (metersPerPixelAtLatitude / pixelRatio).toFloat()
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapCameraDelegate = delegateProvider.mapCameraDelegate
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
        invalidateScaleBar(mapCameraDelegate.getLat(), mapCameraDelegate.getZoom())
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