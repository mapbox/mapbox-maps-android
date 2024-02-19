package com.mapbox.maps.extension.compose.ornaments.scalebar.internal

import android.content.Context
import android.util.AttributeSet
import com.mapbox.common.Cancelable
import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.CameraState
import com.mapbox.maps.Projection.getMetersPerPixelAtLatitude
import com.mapbox.maps.plugin.ContextBinder
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapSizePlugin
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.scalebar.ScaleBarImpl

/**
 * Concrete implementation of [ScaleBarComposePlugin] that's added to the map during composition.
 */
internal class ScaleBarComposePlugin(
  private val scaleBar: ScaleBarImpl
) : MapSizePlugin, MapPlugin, ContextBinder {
  private lateinit var mapListenerDelegate: MapListenerDelegate
  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate
  private val cameraChangeListener = CameraChangedCallback {
    invalidateScaleBar()
  }
  private var cancelable: Cancelable? = null

  init {
    scaleBar.enable = true
  }
  /**
   * Invoked when MapView's width and height have changed.
   * @param width the width of mapView
   * @param height the height of mapView
   */
  override fun onSizeChanged(width: Int, height: Int) {
    scaleBar.mapViewWidth = width.toFloat()
    invalidateScaleBar()
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    cancelable?.cancel()
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    cancelable = mapListenerDelegate.subscribeCameraChanged(cameraChangeListener)
    invalidateScaleBar()
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

  override fun bind(context: Context, attrs: AttributeSet?, pixelRatio: Float) {
    scaleBar.pixelRatio = pixelRatio
    invalidateScaleBar()
  }

  internal companion object {
    private const val PLUGIN_ID = "MAPBOX_SCALE_BAR_COMPOSE_PLUGIN"
    private var INSTANCE_COUNT = 0
    internal fun getNextId(): String {
      return "$PLUGIN_ID-${INSTANCE_COUNT++}"
    }
  }
}