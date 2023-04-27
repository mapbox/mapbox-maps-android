package com.mapbox.maps.plugin.attribution

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_ATTRIBUTION_PLUGIN_ID
import com.mapbox.maps.plugin.attribution.generated.AttributionAttributeParser
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.attribution.generated.AttributionSettingsBase
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Concrete implementation of AttributionViewPlugin.
 */
open class AttributionViewPlugin(
  private val viewImplProvider: (Context) -> AttributionViewImpl = { AttributionViewImpl(it) }
) : AttributionPlugin, AttributionSettingsBase(), View.OnClickListener {

  private lateinit var attributionView: AttributionView
  private lateinit var mapAttributionDelegate: MapAttributionDelegate
  private lateinit var dialogManager: AttributionDialogManager

  override var internalSettings: AttributionSettings = AttributionSettings { }

  override fun applySettings() {
    attributionView.setGravity(internalSettings.position)
    attributionView.setEnable(internalSettings.enabled)
    attributionView.setIconColor(internalSettings.iconColor)
    attributionView.setAttributionMargins(
      internalSettings.marginLeft.toInt(),
      internalSettings.marginTop.toInt(),
      internalSettings.marginRight.toInt(),
      internalSettings.marginBottom.toInt()
    )
    attributionView.requestLayout()
  }

  /**
   * Get the instance of MapAttributionDelegate
   */
  override fun getMapAttributionDelegate(): MapAttributionDelegate {
    return mapAttributionDelegate
  }

  /**
   * Called when the attribution view has been clicked.
   *
   * @param v The attribution view that was clicked.
   */
  override fun onClick(v: View?) {
    if (!internalSettings.clickable) {
      return
    }
    dialogManager.showAttribution(mapAttributionDelegate)
  }

  /**
   * Set a custom AttributionDialogManager that is invoked when the attribution view is clicked.
   */
  override fun setCustomAttributionDialogManager(dialogManager: AttributionDialogManager) {
    this.dialogManager = dialogManager
  }

  /**
   * Bind the ViewPlugin with current map context. This will create a View that
   * will be added to the MapView.
   *
   * @param mapView parent view which can be used to fetch [android.content.Context] or [ViewGroup.LayoutParams]
   * @return View that will be added to the MapView
   */
  override fun bind(mapView: FrameLayout, attrs: AttributeSet?, pixelRatio: Float): View {
    internalSettings = AttributionAttributeParser.parseAttributionSettings(
      mapView.context,
      attrs,
      pixelRatio
    )
    dialogManager = AttributionDialogManagerImpl(
      mapView.context
    )

    return viewImplProvider(mapView.context)
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    applySettings()
  }

  /**
   * Provides map delegate instance.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapAttributionDelegate = delegateProvider.mapAttributionDelegate
  }

  /**
   * Provides a view instances returned in [bind] after it's been added to the MapView.
   *
   * @param view plugin view
   */
  override fun onPluginView(view: View) {
    attributionView = view as? AttributionView
      ?: throw IllegalArgumentException("The provided view needs to implement AttributionView")
    attributionView.setViewOnClickListener(this)
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    dialogManager.onStop()
  }
}

/**
 * Extension val for MapView to get the Compass View plugin instance.
 */
val MapPluginProviderDelegate.attribution: AttributionPlugin
  get() = this.getPlugin(MAPBOX_ATTRIBUTION_PLUGIN_ID)!!