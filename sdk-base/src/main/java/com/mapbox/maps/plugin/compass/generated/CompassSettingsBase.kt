// This file is generated.

package com.mapbox.maps.plugin.compass.generated

import android.graphics.drawable.Drawable

/**
 * Abstract settings class for CompassPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the CompassPlugin.
 */
abstract class CompassSettingsBase : CompassSettingsInterface {
  /**
   * Shows the compass on the map.
   */
  protected abstract var internalSettings: CompassSettings

  /**
   * Apply the changes to the CompassSettings to the CompassPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current compass configuration.
   *
   * @return compass settings
   */
  override fun getSettings(): CompassSettings {
    return internalSettings.copy()
  }

  /**
   * Update compass configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of CompassSettings
   */
  override fun updateSettings(block: CompassSettings.() -> Unit) {
    this.internalSettings.apply(block)
    applySettings()
  }

  /**
   * Whether the compass is visible on the map.
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
   * Defines where the compass is positioned on the map
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
   * Defines the margin to the left that the compass icon honors.
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
   * Defines the margin to the top that the compass icon honors.
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
   * Defines the margin to the right that the compass icon honors.
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
   * Defines the margin to the bottom that the compass icon honors.
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
   * The alpha channel value of the compass image
   */
  override var opacity: Float
    get() {
      return this.internalSettings.opacity
    }
    set(value) {
      if (this.internalSettings.opacity != value) {
        this.internalSettings.opacity = value
        applySettings()
      }
    }

  /**
   * The clockwise rotation value in degrees of the compass.
   */
  override var rotation: Float
    get() {
      return this.internalSettings.rotation
    }
    set(value) {
      if (this.internalSettings.rotation != value) {
        this.internalSettings.rotation = value
        applySettings()
      }
    }

  /**
   * Whether the compass is displayed.
   */
  override var visibility: Boolean
    get() {
      return this.internalSettings.visibility
    }
    set(value) {
      if (this.internalSettings.visibility != value) {
        this.internalSettings.visibility = value
        applySettings()
      }
    }

  /**
   * Whether the compass fades out to invisible when facing north direction.
   */
  override var fadeWhenFacingNorth: Boolean
    get() {
      return this.internalSettings.fadeWhenFacingNorth
    }
    set(value) {
      if (this.internalSettings.fadeWhenFacingNorth != value) {
        this.internalSettings.fadeWhenFacingNorth = value
        applySettings()
      }
    }

  /**
   * Whether the compass can be clicked and click events can be registered.
   */
  override var clickable: Boolean
    get() {
      return this.internalSettings.clickable
    }
    set(value) {
      if (this.internalSettings.clickable != value) {
        this.internalSettings.clickable = value
        applySettings()
      }
    }

  /**
   * The compass image, the visual representation of the compass.
   */
  override var image: Drawable?
    get() {
      return this.internalSettings.image
    }
    set(value) {
      if (this.internalSettings.image != value) {
        this.internalSettings.image = value
        applySettings()
      }
    }
}

// End of generated file.