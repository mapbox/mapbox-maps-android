package com.mapbox.maps.plugin.logo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mapbox.maps.plugin.logo.generated.LogoAttributeParser
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.logo.generated.LogoSettingsBase

/**
 * Concrete implementation of LogoViewPlugin.
 */
open class LogoViewPlugin(
  private val viewImplProvider: (Context) -> LogoViewImpl = { LogoViewImpl(it) }
) : LogoPlugin, LogoSettingsBase() {

  private lateinit var logoView: LogoView

  override var internalSettings: LogoSettings = LogoSettings()

  override fun applySettings() {
    logoView.setLogoMargins(
      internalSettings.marginLeft.toInt(),
      internalSettings.marginTop.toInt(),
      internalSettings.marginRight.toInt(),
      internalSettings.marginBottom.toInt()
    )
    logoView.logoGravity = internalSettings.position
    logoView.logoEnabled = internalSettings.enabled
    logoView.requestLayout()
  }

  /**
   * Defines whether the plugins is enabled or disabled.
   */
  override var enabled: Boolean
    get() = logoView.logoEnabled
    set(value) {
      logoView.logoEnabled = value
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
  override fun initialize() {
    applySettings()
  }

  /**
   * Provides a view instances returned in [bind] after it's been added to the MapView.
   *
   * @param view plugin view
   */
  override fun onPluginView(view: View) {
    logoView = view as? LogoView
      ?: throw IllegalArgumentException("The provided view needs to implement LogoContract.LogoView")
  }
}