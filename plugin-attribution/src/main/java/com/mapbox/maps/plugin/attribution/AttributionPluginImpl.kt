package com.mapbox.maps.plugin.attribution

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RestrictTo
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.attribution.generated.AttributionAttributeParser
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.attribution.generated.AttributionSettingsBase
import com.mapbox.maps.plugin.consolidatedattribution.ConsolidatedAttributionPlugin
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider

/**
 * Concrete implementation of AttributionViewPlugin.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class AttributionPluginImpl(
  private val viewImplProvider: (Context) -> AttributionViewImpl = { AttributionViewImpl(it) }
) : AttributionPlugin, AttributionSettingsBase(), View.OnClickListener {

  private lateinit var attributionView: AttributionView
  private lateinit var pluginView: View
  private var delegateProvider: MapDelegateProvider? = null
  private lateinit var mapAttributionDelegate: MapAttributionDelegate
  private lateinit var dialogManager: AttributionDialogManager

  /** Set while the consolidated attribution control stands in for the attribution button. */
  internal var hiddenByConsolidatedAttribution = false
    set(value) {
      if (field == value) {
        return
      }
      field = value
      if (::attributionView.isInitialized) {
        applyVisibility()
      }
    }

  override var internalSettings: AttributionSettings = AttributionSettings { }

  override fun applySettings() {
    attributionView.setGravity(internalSettings.position)
    applyVisibility()
    attributionView.setIconColor(internalSettings.iconColor)
    attributionView.setAttributionMargins(
      internalSettings.marginLeft.toInt(),
      internalSettings.marginTop.toInt(),
      internalSettings.marginRight.toInt(),
      internalSettings.marginBottom.toInt()
    )
    attributionView.requestLayout()
  }

  private fun applyVisibility() {
    if (hiddenByConsolidatedAttribution) {
      // Skips the "attribution disabled" warning: the consolidated control shows the attribution.
      pluginView.visibility = View.GONE
    } else {
      attributionView.setEnable(internalSettings.enabled)
    }
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
   * Set the content description for the attribution view.
   *
   * @param contentDescription the content description text
   */
  override fun setContentDescription(contentDescription: CharSequence?) {
    if (::attributionView.isInitialized) {
      attributionView.setContentDescription(contentDescription)
    }
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
    dialogManager = AttributionDialogManagerImpl(mapView.context)

    return viewImplProvider(mapView.context)
  }

  /**
   * Called when the plugin is first added to the map.
   */
  @OptIn(MapboxExperimental::class)
  override fun initialize() {
    // The consolidated plugin may have been created first.
    hiddenByConsolidatedAttribution = delegateProvider?.mapPluginProviderDelegate
      ?.getPlugin<ConsolidatedAttributionPlugin>(Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID)
      ?.enabled == true
    applySettings()
  }

  /**
   * Provides map delegate instance.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapAttributionDelegate = delegateProvider.mapAttributionDelegate
    this.delegateProvider = delegateProvider
  }

  /**
   * Provides a view instances returned in [bind] after it's been added to the MapView.
   *
   * @param view plugin view
   */
  override fun onPluginView(view: View) {
    attributionView = view as? AttributionView
      ?: throw IllegalArgumentException("The provided view needs to implement AttributionView")
    pluginView = view
    attributionView.setViewOnClickListener(this)
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    dialogManager.onStop()
  }
}