// This file is generated.

package com.mapbox.maps.plugin.scalebar.generated

/**
 * Abstract settings class for ScaleBarPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the ScaleBarPlugin.
 */
abstract class ScaleBarSettingsBase : ScaleBarSettingsInterface {
  /**
   * Shows the scale bar on the map.
   */
  protected abstract var internalSettings: ScaleBarSettings

  /**
   * Apply the changes to the ScaleBarSettings to the ScaleBarPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current scalebar configuration.
   *
   * @return scalebar settings
   */
  override fun getSettings(): ScaleBarSettings {
    return internalSettings.copy()
  }

  /**
   * Update scalebar configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of ScaleBarSettings
   */
  override fun updateSettings(block: ScaleBarSettings.() -> Unit) {
    this.internalSettings.apply(block)
    applySettings()
  }

  /**
   * Whether the scale is visible on the map.
   */
  override var enabled: Boolean
    get() {
      return this.internalSettings.enabled
    }
    set(value) {
      this.internalSettings.enabled = value
      applySettings()
    }

  /**
   * Defines where the scale bar is positioned on the map
   */
  override var position: Int
    get() {
      return this.internalSettings.position
    }
    set(value) {
      this.internalSettings.position = value
      applySettings()
    }

  /**
   * Defines the margin to the left that the scale bar honors.
   */
  override var marginLeft: Float
    get() {
      return this.internalSettings.marginLeft
    }
    set(value) {
      this.internalSettings.marginLeft = value
      applySettings()
    }

  /**
   * Defines the margin to the top that the scale bar honors.
   */
  override var marginTop: Float
    get() {
      return this.internalSettings.marginTop
    }
    set(value) {
      this.internalSettings.marginTop = value
      applySettings()
    }

  /**
   * Defines the margin to the right that the scale bar honors.
   */
  override var marginRight: Float
    get() {
      return this.internalSettings.marginRight
    }
    set(value) {
      this.internalSettings.marginRight = value
      applySettings()
    }

  /**
   * Defines the margin to the bottom that the scale bar honors.
   */
  override var marginBottom: Float
    get() {
      return this.internalSettings.marginBottom
    }
    set(value) {
      this.internalSettings.marginBottom = value
      applySettings()
    }

  /**
   * Defines text color of the scale bar.
   */
  override var textColor: Int
    get() {
      return this.internalSettings.textColor
    }
    set(value) {
      this.internalSettings.textColor = value
      applySettings()
    }

  /**
   * Defines primary color of the scale bar.
   */
  override var primaryColor: Int
    get() {
      return this.internalSettings.primaryColor
    }
    set(value) {
      this.internalSettings.primaryColor = value
      applySettings()
    }

  /**
   * Defines secondary color of the scale bar.
   */
  override var secondaryColor: Int
    get() {
      return this.internalSettings.secondaryColor
    }
    set(value) {
      this.internalSettings.secondaryColor = value
      applySettings()
    }

  /**
   * Defines width of the border for the scale bar.
   */
  override var borderWidth: Float
    get() {
      return this.internalSettings.borderWidth
    }
    set(value) {
      this.internalSettings.borderWidth = value
      applySettings()
    }

  /**
   * Defines height of the scale bar.
   */
  override var height: Float
    get() {
      return this.internalSettings.height
    }
    set(value) {
      this.internalSettings.height = value
      applySettings()
    }

  /**
   * Defines margin of the text bar of the scale bar.
   */
  override var textBarMargin: Float
    get() {
      return this.internalSettings.textBarMargin
    }
    set(value) {
      this.internalSettings.textBarMargin = value
      applySettings()
    }

  /**
   * Defines text border width of the scale bar.
   */
  override var textBorderWidth: Float
    get() {
      return this.internalSettings.textBorderWidth
    }
    set(value) {
      this.internalSettings.textBorderWidth = value
      applySettings()
    }

  /**
   * Defines text size of the scale bar.
   */
  override var textSize: Float
    get() {
      return this.internalSettings.textSize
    }
    set(value) {
      this.internalSettings.textSize = value
      applySettings()
    }

  /**
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false if the scale bar is using imperial units.
   */
  override var isMetricUnits: Boolean
    get() {
      return this.internalSettings.isMetricUnits
    }
    set(value) {
      this.internalSettings.isMetricUnits = value
      applySettings()
    }

  /**
   * Configures minimum refresh interval, in millisecond, default is 15.
   */
  override var refreshInterval: Long
    get() {
      return this.internalSettings.refreshInterval
    }
    set(value) {
      this.internalSettings.refreshInterval = value
      applySettings()
    }

  /**
   * Configures whether to show the text border or not, default is true.
   */
  override var showTextBorder: Boolean
    get() {
      return this.internalSettings.showTextBorder
    }
    set(value) {
      this.internalSettings.showTextBorder = value
      applySettings()
    }

  /**
   * configures ratio of scale bar max width compared with MapView width, default is 0.5.
   */
  override var ratio: Float
    get() {
      return this.internalSettings.ratio
    }
    set(value) {
      this.internalSettings.ratio = value
      applySettings()
    }
}

// End of generated file.