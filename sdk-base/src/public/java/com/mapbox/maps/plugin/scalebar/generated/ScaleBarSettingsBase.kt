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
      if (this.internalSettings.enabled != value) {
        this.internalSettings.enabled = value
        applySettings()
      }
    }

  /**
   * Defines where the scale bar is positioned on the map
   */
  override var position: Int
    get() {
      return this.internalSettings.position
    }
    set(value) {
      if (this.internalSettings.position != value) {
        this.internalSettings.position = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the left that the scale bar honors.
   */
  override var marginLeft: Float
    get() {
      return this.internalSettings.marginLeft
    }
    set(value) {
      if (this.internalSettings.marginLeft != value) {
        this.internalSettings.marginLeft = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the top that the scale bar honors.
   */
  override var marginTop: Float
    get() {
      return this.internalSettings.marginTop
    }
    set(value) {
      if (this.internalSettings.marginTop != value) {
        this.internalSettings.marginTop = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the right that the scale bar honors.
   */
  override var marginRight: Float
    get() {
      return this.internalSettings.marginRight
    }
    set(value) {
      if (this.internalSettings.marginRight != value) {
        this.internalSettings.marginRight = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the bottom that the scale bar honors.
   */
  override var marginBottom: Float
    get() {
      return this.internalSettings.marginBottom
    }
    set(value) {
      if (this.internalSettings.marginBottom != value) {
        this.internalSettings.marginBottom = value
        applySettings()
      }
    }

  /**
   * Defines text color of the scale bar.
   */
  override var textColor: Int
    get() {
      return this.internalSettings.textColor
    }
    set(value) {
      if (this.internalSettings.textColor != value) {
        this.internalSettings.textColor = value
        applySettings()
      }
    }

  /**
   * Defines primary color of the scale bar.
   */
  override var primaryColor: Int
    get() {
      return this.internalSettings.primaryColor
    }
    set(value) {
      if (this.internalSettings.primaryColor != value) {
        this.internalSettings.primaryColor = value
        applySettings()
      }
    }

  /**
   * Defines secondary color of the scale bar.
   */
  override var secondaryColor: Int
    get() {
      return this.internalSettings.secondaryColor
    }
    set(value) {
      if (this.internalSettings.secondaryColor != value) {
        this.internalSettings.secondaryColor = value
        applySettings()
      }
    }

  /**
   * Defines width of the border for the scale bar.
   */
  override var borderWidth: Float
    get() {
      return this.internalSettings.borderWidth
    }
    set(value) {
      if (this.internalSettings.borderWidth != value) {
        this.internalSettings.borderWidth = value
        applySettings()
      }
    }

  /**
   * Defines height of the scale bar.
   */
  override var height: Float
    get() {
      return this.internalSettings.height
    }
    set(value) {
      if (this.internalSettings.height != value) {
        this.internalSettings.height = value
        applySettings()
      }
    }

  /**
   * Defines margin of the text bar of the scale bar.
   */
  override var textBarMargin: Float
    get() {
      return this.internalSettings.textBarMargin
    }
    set(value) {
      if (this.internalSettings.textBarMargin != value) {
        this.internalSettings.textBarMargin = value
        applySettings()
      }
    }

  /**
   * Defines text border width of the scale bar.
   */
  override var textBorderWidth: Float
    get() {
      return this.internalSettings.textBorderWidth
    }
    set(value) {
      if (this.internalSettings.textBorderWidth != value) {
        this.internalSettings.textBorderWidth = value
        applySettings()
      }
    }

  /**
   * Defines text size of the scale bar.
   */
  override var textSize: Float
    get() {
      return this.internalSettings.textSize
    }
    set(value) {
      if (this.internalSettings.textSize != value) {
        this.internalSettings.textSize = value
        applySettings()
      }
    }

  /**
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false if the scale bar is using imperial units.
   */
  override var isMetricUnits: Boolean
    get() {
      return this.internalSettings.isMetricUnits
    }
    set(value) {
      if (this.internalSettings.isMetricUnits != value) {
        this.internalSettings.isMetricUnits = value
        applySettings()
      }
    }

  /**
   * Configures minimum refresh interval, in millisecond, default is 15.
   */
  override var refreshInterval: Long
    get() {
      return this.internalSettings.refreshInterval
    }
    set(value) {
      if (this.internalSettings.refreshInterval != value) {
        this.internalSettings.refreshInterval = value
        applySettings()
      }
    }

  /**
   * Configures whether to show the text border or not, default is true.
   */
  override var showTextBorder: Boolean
    get() {
      return this.internalSettings.showTextBorder
    }
    set(value) {
      if (this.internalSettings.showTextBorder != value) {
        this.internalSettings.showTextBorder = value
        applySettings()
      }
    }

  /**
   * configures ratio of scale bar max width compared with MapView width, default is 0.5.
   */
  override var ratio: Float
    get() {
      return this.internalSettings.ratio
    }
    set(value) {
      if (this.internalSettings.ratio != value) {
        this.internalSettings.ratio = value
        applySettings()
      }
    }

  /**
   * If set to True scale bar will be triggering onDraw depending on [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to False scale bar will redraw only on demand. Defaults to False and should not be changed explicitly in most cases. Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  override var useContinuousRendering: Boolean
    get() {
      return this.internalSettings.useContinuousRendering
    }
    set(value) {
      if (this.internalSettings.useContinuousRendering != value) {
        this.internalSettings.useContinuousRendering = value
        applySettings()
      }
    }
}

// End of generated file.