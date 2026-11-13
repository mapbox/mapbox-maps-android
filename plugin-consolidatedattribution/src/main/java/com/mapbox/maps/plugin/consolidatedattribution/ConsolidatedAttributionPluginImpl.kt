package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RestrictTo
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.MapSizePlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.attribution.AttributionDialogManager
import com.mapbox.maps.plugin.attribution.AttributionPlugin
import com.mapbox.maps.plugin.attribution.setHiddenByConsolidatedAttribution
import com.mapbox.maps.plugin.consolidatedattribution.generated.ConsolidatedAttributionAttributeParser
import com.mapbox.maps.plugin.consolidatedattribution.generated.ConsolidatedAttributionSettings
import com.mapbox.maps.plugin.consolidatedattribution.generated.ConsolidatedAttributionSettingsBase
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.logo.LogoPlugin
import com.mapbox.maps.plugin.logo.setHiddenByConsolidatedAttribution

/**
 * Concrete implementation of [ConsolidatedAttributionPlugin], for a control hosted by the MapView.
 *
 * The control's behaviour lives in [ConsolidatedAttributionController]; this plugin adds what only
 * a MapView-hosted control needs - creating the view, and positioning it with the gravity and
 * margins from the settings.
 */
@OptIn(MapboxExperimental::class, com.mapbox.annotation.MapboxExperimental::class)
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ConsolidatedAttributionPluginImpl(
  private val viewImplProvider: (Context) -> ConsolidatedAttributionView = {
    ConsolidatedAttributionView(it)
  }
) : ConsolidatedAttributionPlugin, MapSizePlugin, ConsolidatedAttributionSettingsBase() {

  private lateinit var consolidatedView: ConsolidatedAttributionView
  private lateinit var controller: ConsolidatedAttributionController
  private var customDialogManager: AttributionDialogManager? = null

  /**
   * The registry hands the delegates over before it binds the view, so they are held until
   * [onPluginView] has a view to build the controller around.
   */
  private var delegateProvider: MapDelegateProvider? = null

  override var internalSettings: ConsolidatedAttributionSettings = ConsolidatedAttributionSettings { }

  override fun applySettings() {
    (consolidatedView.layoutParams as? FrameLayout.LayoutParams)?.gravity = internalSettings.position
    consolidatedView.setPillMargins(
      internalSettings.marginLeft.toInt(),
      internalSettings.marginTop.toInt(),
      internalSettings.marginRight.toInt(),
      internalSettings.marginBottom.toInt()
    )
    controller.applyAppearance(internalSettings)
    consolidatedView.requestLayout()
    hideReplacedOrnaments(internalSettings.enabled)
  }

  /** The pill stands in for the logo and the attribution button, as on iOS. */
  private fun hideReplacedOrnaments(hidden: Boolean) {
    val plugins = delegateProvider?.mapPluginProviderDelegate ?: return
    plugins.getPlugin<LogoPlugin>(Plugin.MAPBOX_LOGO_PLUGIN_ID)
      ?.setHiddenByConsolidatedAttribution(hidden)
    plugins.getPlugin<AttributionPlugin>(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID)
      ?.setHiddenByConsolidatedAttribution(hidden)
  }

  override fun setCustomAttributionDialogManager(dialogManager: AttributionDialogManager) {
    customDialogManager = dialogManager
    if (::controller.isInitialized) {
      controller.setCustomAttributionDialogManager(dialogManager)
    }
  }

  override fun getMapAttributionDelegate(): MapAttributionDelegate =
    controller.getMapAttributionDelegate()

  override fun bind(mapView: FrameLayout, attrs: AttributeSet?, pixelRatio: Float): View {
    internalSettings = ConsolidatedAttributionAttributeParser.parseConsolidatedAttributionSettings(
      mapView.context,
      attrs,
      pixelRatio
    )
    return viewImplProvider(mapView.context).apply {
      layoutParams = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
      )
    }
  }

  override fun onPluginView(view: View) {
    consolidatedView = view as? ConsolidatedAttributionView
      ?: throw IllegalArgumentException(
        "The provided view needs to be a ConsolidatedAttributionView"
      )
    controller = ConsolidatedAttributionController(consolidatedView).apply {
      customDialogManager?.let(::setCustomAttributionDialogManager)
      attachTapHandling(consolidatedView.context)
      delegateProvider?.let(::onDelegateProvider)
    }
  }

  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
    // The registry provides the delegates before binding the view, but do not depend on that:
    // forward them straight away once there is a controller to take them.
    if (::controller.isInitialized) {
      controller.onDelegateProvider(delegateProvider)
    }
  }

  /** The pill's expanded width is bounded by the map's width, so it tracks the map's size. */
  override fun onSizeChanged(width: Int, height: Int) {
    controller.onMapWidthChanged(width)
  }

  override fun initialize() {
    controller.initialize()
    applySettings()
  }

  override fun onStop() {
    controller.onStop()
  }

  override fun cleanup() {
    controller.cleanup()
    if (internalSettings.enabled) {
      hideReplacedOrnaments(false)
    }
  }
}