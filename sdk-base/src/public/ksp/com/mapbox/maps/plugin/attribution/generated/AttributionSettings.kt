@file:Suppress("RedundantVisibilityModifier")

package com.mapbox.maps.plugin.attribution.generated

import android.graphics.Color
import android.os.Parcelable
import android.view.Gravity
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Unit
import kotlin.jvm.JvmSynthetic
import kotlinx.parcelize.Parcelize

/**
 * Shows the attribution icon on the map.
 */
@Parcelize
public class AttributionSettings private constructor(
  /**
   * Whether the attribution icon is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Defines text color of the attribution icon.
   */
  public val iconColor: Int,
  /**
   * Defines where the attribution icon is positioned on the map
   */
  public val position: Int,
  /**
   * Defines the margin to the left that the attribution icon honors. This property is specified in
   * pixels.
   */
  public val marginLeft: Float,
  /**
   * Defines the margin to the top that the attribution icon honors. This property is specified in
   * pixels.
   */
  public val marginTop: Float,
  /**
   * Defines the margin to the right that the attribution icon honors. This property is specified in
   * pixels.
   */
  public val marginRight: Float,
  /**
   * Defines the margin to the bottom that the attribution icon honors. This property is specified
   * in pixels.
   */
  public val marginBottom: Float,
  /**
   * Whether the attribution can be clicked and click events can be registered.
   */
  public val clickable: Boolean
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """AttributionSettings(enabled=$enabled, iconColor=$iconColor,
      position=$position, marginLeft=$marginLeft, marginTop=$marginTop, marginRight=$marginRight,
      marginBottom=$marginBottom, clickable=$clickable)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as AttributionSettings
    return enabled == other.enabled && iconColor == other.iconColor && position == other.position &&
        marginLeft.compareTo(other.marginLeft) == 0 && marginTop.compareTo(other.marginTop) == 0 &&
        marginRight.compareTo(other.marginRight) == 0 &&
        marginBottom.compareTo(other.marginBottom) == 0 && clickable == other.clickable
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(enabled, iconColor, position, marginLeft,
      marginTop, marginRight, marginBottom, clickable)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder() .setEnabled(enabled) .setIconColor(iconColor)
      .setPosition(position) .setMarginLeft(marginLeft) .setMarginTop(marginTop)
      .setMarginRight(marginRight) .setMarginBottom(marginBottom) .setClickable(clickable)

  /**
   * Composes and builds a [AttributionSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * Whether the attribution icon is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean = true

    /**
     * Defines text color of the attribution icon.
     */
    @set:JvmSynthetic
    public var iconColor: Int = Color.parseColor("#FF1E8CAB")

    /**
     * Defines where the attribution icon is positioned on the map
     */
    @set:JvmSynthetic
    public var position: Int = Gravity.BOTTOM or Gravity.START

    /**
     * Defines the margin to the left that the attribution icon honors. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float = 92f

    /**
     * Defines the margin to the top that the attribution icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float = 4f

    /**
     * Defines the margin to the right that the attribution icon honors. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float = 4f

    /**
     * Defines the margin to the bottom that the attribution icon honors. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float = 4f

    /**
     * Whether the attribution can be clicked and click events can be registered.
     */
    @set:JvmSynthetic
    public var clickable: Boolean = true

    /**
     * Setter for enabled: whether the attribution icon is visible on the map.
     *
     * @param enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Setter for iconColor: defines text color of the attribution icon.
     *
     * @param iconColor
     * @return Builder
     */
    public fun setIconColor(iconColor: Int): Builder {
      this.iconColor = iconColor
      return this
    }

    /**
     * Setter for position: defines where the attribution icon is positioned on the map.
     *
     * @param position
     * @return Builder
     */
    public fun setPosition(position: Int): Builder {
      this.position = position
      return this
    }

    /**
     * Setter for marginLeft: defines the margin to the left that the attribution icon honors. This
     * property is specified in pixels.
     *
     * @param marginLeft
     * @return Builder
     */
    public fun setMarginLeft(marginLeft: Float): Builder {
      this.marginLeft = marginLeft
      return this
    }

    /**
     * Setter for marginTop: defines the margin to the top that the attribution icon honors. This
     * property is specified in pixels.
     *
     * @param marginTop
     * @return Builder
     */
    public fun setMarginTop(marginTop: Float): Builder {
      this.marginTop = marginTop
      return this
    }

    /**
     * Setter for marginRight: defines the margin to the right that the attribution icon honors.
     * This property is specified in pixels.
     *
     * @param marginRight
     * @return Builder
     */
    public fun setMarginRight(marginRight: Float): Builder {
      this.marginRight = marginRight
      return this
    }

    /**
     * Setter for marginBottom: defines the margin to the bottom that the attribution icon honors.
     * This property is specified in pixels.
     *
     * @param marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Setter for clickable: whether the attribution can be clicked and click events can be
     * registered.
     *
     * @param clickable
     * @return Builder
     */
    public fun setClickable(clickable: Boolean): Builder {
      this.clickable = clickable
      return this
    }

    /**
     * Returns a [AttributionSettings] reference to the object being constructed by the builder.
     *
     * @return AttributionSettings
     */
    public fun build(): AttributionSettings = AttributionSettings(enabled, iconColor, position,
        marginLeft, marginTop, marginRight, marginBottom, clickable)
  }
}

/**
 * Creates a [AttributionSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return AttributionSettings
 */
@JvmSynthetic
public fun AttributionSettings(initializer: AttributionSettings.Builder.() -> Unit):
    AttributionSettings = AttributionSettings.Builder().apply(initializer).build()
