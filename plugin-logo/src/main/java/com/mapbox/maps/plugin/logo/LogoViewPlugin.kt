package com.mapbox.maps.plugin.logo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RestrictTo
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.consolidatedattribution.ConsolidatedAttributionPlugin
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.logo.generated.LogoAttributeParser
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.logo.generated.LogoSettingsBase

/**
 * Concrete implementation of LogoViewPlugin.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class LogoViewPlugin(
  private val viewImplProvider: (Context) -> LogoViewImpl = { LogoViewImpl(it) }
) : LogoPlugin, LogoSettingsBase() {

  private lateinit var logoView: LogoView
  private lateinit var pluginView: View
  private var delegateProvider: MapDelegateProvider? = null

  /** Set while the consolidated attribution control stands in for the logo. */
  internal var hiddenByConsolidatedAttribution = false
    set(value) {
      if (field == value) {
        return
      }
      field = value
      if (::logoView.isInitialized) {
        applyVisibility()
      }
    }

  override var internalSettings: LogoSettings = LogoSettings { }

  override fun applySettings() {
    logoView.setLogoMargins(
      internalSettings.marginLeft.toInt(),
      internalSettings.marginTop.toInt(),
      internalSettings.marginRight.toInt(),
      internalSettings.marginBottom.toInt()
    )
    logoView.logoGravity = internalSettings.position
    applyVisibility()
    logoView.requestLayout()
  }

  private fun applyVisibility() {
    if (hiddenByConsolidatedAttribution) {
      // Skips the "logo disabled" warning: the consolidated control shows the logo.
      pluginView.visibility = View.GONE
    } else {
      logoView.logoEnabled = internalSettings.enabled
    }
  }

  /**
   * Defines whether the plugins is enabled or disabled.
   */
  override var enabled: Boolean
    get() = internalSettings.enabled
    set(value) {
      if (internalSettings.enabled != value) {
        internalSettings = internalSettings.toBuilder().setEnabled(value).build()
        applySettings()
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
    internalSettings = LogoAttributeParser.parseLogoSettings(mapView.context, attrs, pixelRatio)
    return viewImplProvider(mapView.context).also {
      it.injectPresenter(this)
    }
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

  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
  }

  /**
   * Provides a view instances returned in [bind] after it's been added to the MapView.
   *
   * @param view plugin view
   */
  override fun onPluginView(view: View) {
    logoView = view as? LogoView
      ?: throw IllegalArgumentException("The provided view needs to implement LogoContract.LogoView")
    pluginView = view
  }
}