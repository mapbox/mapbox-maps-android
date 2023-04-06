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
   * Enabled
   * Whether the logo is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Position
   * Defines where the logo is positioned on the map
   */
  public val position: Int,
  /**
   * MarginLeft
   * Defines the margin to the left that the attribution icon honors. This property is specified in
   * pixels.
   */
  public val marginLeft: Float,
  /**
   * MarginTop
   * Defines the margin to the top that the attribution icon honors. This property is specified in
   * pixels.
   */
  public val marginTop: Float,
  /**
   * MarginRight
   * Defines the margin to the right that the attribution icon honors. This property is specified in
   * pixels.
   */
  public val marginRight: Float,
  /**
   * MarginBottom
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
        marginLeft == other.marginLeft && marginTop == other.marginTop &&
        marginRight == other.marginRight && marginBottom == other.marginBottom
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
     * Enabled
     * Whether the logo is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean? = true

    /**
     * Position
     * Defines where the logo is positioned on the map
     */
    @set:JvmSynthetic
    public var position: Int? = Gravity.BOTTOM or Gravity.START

    /**
     * MarginLeft
     * Defines the margin to the left that the attribution icon honors. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float? = 4f

    /**
     * MarginTop
     * Defines the margin to the top that the attribution icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float? = 4f

    /**
     * MarginRight
     * Defines the margin to the right that the attribution icon honors. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float? = 4f

    /**
     * MarginBottom
     * Defines the margin to the bottom that the attribution icon honors. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float? = 4f

    /**
     * Set enabled
     * Whether the logo is visible on the map.
     *
     * @param enabled enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean?): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Set position
     * Defines where the logo is positioned on the map
     *
     * @param position position
     * @return Builder
     */
    public fun setPosition(position: Int?): Builder {
      this.position = position
      return this
    }

    /**
     * Set marginLeft
     * Defines the margin to the left that the attribution icon honors. This property is specified
     * in pixels.
     *
     * @param marginLeft marginLeft
     * @return Builder
     */
    public fun setMarginLeft(marginLeft: Float?): Builder {
      this.marginLeft = marginLeft
      return this
    }

    /**
     * Set marginTop
     * Defines the margin to the top that the attribution icon honors. This property is specified in
     * pixels.
     *
     * @param marginTop marginTop
     * @return Builder
     */
    public fun setMarginTop(marginTop: Float?): Builder {
      this.marginTop = marginTop
      return this
    }

    /**
     * Set marginRight
     * Defines the margin to the right that the attribution icon honors. This property is specified
     * in pixels.
     *
     * @param marginRight marginRight
     * @return Builder
     */
    public fun setMarginRight(marginRight: Float?): Builder {
      this.marginRight = marginRight
      return this
    }

    /**
     * Set marginBottom
     * Defines the margin to the bottom that the attribution icon honors. This property is specified
     * in pixels.
     *
     * @param marginBottom marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float?): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Returns a [LogoSettings] reference to the object being constructed by the builder.
     *
     * Throws an [IllegalArgumentException] when a non-null property wasn't initialised.
     *
     * @return LogoSettings
     */
    public fun build(): LogoSettings {
      if (enabled==null) {
      	throw IllegalArgumentException("""Null enabled found when building
          LogoSettings.""".trimIndent())
      }
      if (position==null) {
      	throw IllegalArgumentException("""Null position found when building
          LogoSettings.""".trimIndent())
      }
      if (marginLeft==null) {
      	throw IllegalArgumentException("""Null marginLeft found when building
          LogoSettings.""".trimIndent())
      }
      if (marginTop==null) {
      	throw IllegalArgumentException("""Null marginTop found when building
          LogoSettings.""".trimIndent())
      }
      if (marginRight==null) {
      	throw IllegalArgumentException("""Null marginRight found when building
          LogoSettings.""".trimIndent())
      }
      if (marginBottom==null) {
      	throw IllegalArgumentException("""Null marginBottom found when building
          LogoSettings.""".trimIndent())
      }
      return LogoSettings(enabled!!, position!!, marginLeft!!, marginTop!!, marginRight!!,
          marginBottom!!)
    }
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
