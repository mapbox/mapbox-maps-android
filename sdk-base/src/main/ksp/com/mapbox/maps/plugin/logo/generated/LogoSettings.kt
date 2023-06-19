@file:Suppress("RedundantVisibilityModifier")

package com.mapbox.maps.plugin.logo.generated

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
 * Shows the Mapbox logo on the map.
 */
@Parcelize
public class LogoSettings private constructor(
  /**
   * Whether the logo is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Defines where the logo is positioned on the map
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
  public val marginBottom: Float
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """LogoSettings(enabled=$enabled, position=$position,
      marginLeft=$marginLeft, marginTop=$marginTop, marginRight=$marginRight,
      marginBottom=$marginBottom)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as LogoSettings
    return enabled == other.enabled && position == other.position &&
        marginLeft.compareTo(other.marginLeft) == 0 && marginTop.compareTo(other.marginTop) == 0 &&
        marginRight.compareTo(other.marginRight) == 0 &&
        marginBottom.compareTo(other.marginBottom) == 0
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(enabled, position, marginLeft, marginTop,
      marginRight, marginBottom)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder() .setEnabled(enabled) .setPosition(position)
      .setMarginLeft(marginLeft) .setMarginTop(marginTop) .setMarginRight(marginRight)
      .setMarginBottom(marginBottom)

  /**
   * Composes and builds a [LogoSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * Whether the logo is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean = true

    /**
     * Defines where the logo is positioned on the map
     */
    @set:JvmSynthetic
    public var position: Int = Gravity.BOTTOM or Gravity.START

    /**
     * Defines the margin to the left that the attribution icon honors. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float = 4f

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
     * Setter for enabled: whether the logo is visible on the map.
     *
     * @param enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Setter for position: defines where the logo is positioned on the map.
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
     * Returns a [LogoSettings] reference to the object being constructed by the builder.
     *
     * @return LogoSettings
     */
    public fun build(): LogoSettings = LogoSettings(enabled, position, marginLeft, marginTop,
        marginRight, marginBottom)
  }
}

/**
 * Creates a [LogoSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return LogoSettings
 */
@JvmSynthetic
public fun LogoSettings(initializer: LogoSettings.Builder.() -> Unit): LogoSettings =
    LogoSettings.Builder().apply(initializer).build()
