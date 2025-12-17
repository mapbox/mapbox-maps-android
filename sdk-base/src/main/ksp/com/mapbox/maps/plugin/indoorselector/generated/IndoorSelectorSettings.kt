@file:Suppress("RedundantVisibilityModifier")

package com.mapbox.maps.plugin.indoorselector.generated

import android.os.Parcelable
import android.view.Gravity
import com.mapbox.maps.MapboxExperimental
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Unit
import kotlin.jvm.JvmSynthetic
import kotlinx.parcelize.Parcelize

/**
 * Settings for the indoor floor selector.
 */
@MapboxExperimental
@Parcelize
public class IndoorSelectorSettings private constructor(
  /**
   * Whether the indoor selector is visible on the map. Default value: true.
   */
  public val enabled: Boolean,
  /**
   * Defines where the indoor selector is positioned on the map. Default value: "top-right".
   */
  public val position: Int,
  /**
   * Defines the margin to the left that the indoor selector honors. Default value: 8. This property
   * is specified in pixels.
   */
  public val marginLeft: Float,
  /**
   * Defines the margin to the top that the indoor selector honors. Default value: 60. This property
   * is specified in pixels.
   */
  public val marginTop: Float,
  /**
   * Defines the margin to the right that the indoor selector honors. Default value: 8. This
   * property is specified in pixels.
   */
  public val marginRight: Float,
  /**
   * Defines the margin to the bottom that the indoor selector honors. Default value: 8. This
   * property is specified in pixels.
   */
  public val marginBottom: Float
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """IndoorSelectorSettings(enabled=$enabled, position=$position,
      marginLeft=$marginLeft, marginTop=$marginTop, marginRight=$marginRight,
      marginBottom=$marginBottom)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as IndoorSelectorSettings
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
   * Composes and builds a [IndoorSelectorSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * Whether the indoor selector is visible on the map. Default value: true.
     */
    @set:JvmSynthetic
    public var enabled: Boolean = true

    /**
     * Defines where the indoor selector is positioned on the map. Default value: "top-right".
     */
    @set:JvmSynthetic
    public var position: Int = Gravity.TOP or Gravity.END

    /**
     * Defines the margin to the left that the indoor selector honors. Default value: 8. This
     * property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float = 8f

    /**
     * Defines the margin to the top that the indoor selector honors. Default value: 60. This
     * property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float = 60f

    /**
     * Defines the margin to the right that the indoor selector honors. Default value: 8. This
     * property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float = 8f

    /**
     * Defines the margin to the bottom that the indoor selector honors. Default value: 8. This
     * property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float = 8f

    /**
     * Setter for enabled: whether the indoor selector is visible on the map. Default value: true.
     *
     * @param enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Setter for position: defines where the indoor selector is positioned on the map. Default
     * value: "top-right".
     *
     * @param position
     * @return Builder
     */
    public fun setPosition(position: Int): Builder {
      this.position = position
      return this
    }

    /**
     * Setter for marginLeft: defines the margin to the left that the indoor selector honors.
     * Default value: 8. This property is specified in pixels.
     *
     * @param marginLeft
     * @return Builder
     */
    public fun setMarginLeft(marginLeft: Float): Builder {
      this.marginLeft = marginLeft
      return this
    }

    /**
     * Setter for marginTop: defines the margin to the top that the indoor selector honors. Default
     * value: 60. This property is specified in pixels.
     *
     * @param marginTop
     * @return Builder
     */
    public fun setMarginTop(marginTop: Float): Builder {
      this.marginTop = marginTop
      return this
    }

    /**
     * Setter for marginRight: defines the margin to the right that the indoor selector honors.
     * Default value: 8. This property is specified in pixels.
     *
     * @param marginRight
     * @return Builder
     */
    public fun setMarginRight(marginRight: Float): Builder {
      this.marginRight = marginRight
      return this
    }

    /**
     * Setter for marginBottom: defines the margin to the bottom that the indoor selector honors.
     * Default value: 8. This property is specified in pixels.
     *
     * @param marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Returns a [IndoorSelectorSettings] reference to the object being constructed by the builder.
     *
     * @return IndoorSelectorSettings
     */
    public fun build(): IndoorSelectorSettings = IndoorSelectorSettings(enabled, position,
        marginLeft, marginTop, marginRight, marginBottom)
  }
}

/**
 * Creates a [IndoorSelectorSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return IndoorSelectorSettings
 */
@JvmSynthetic
public fun IndoorSelectorSettings(initializer: IndoorSelectorSettings.Builder.() -> Unit):
    IndoorSelectorSettings = IndoorSelectorSettings.Builder().apply(initializer).build()
