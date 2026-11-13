@file:Suppress("RedundantVisibilityModifier")

package com.mapbox.maps.plugin.consolidatedattribution.generated

import android.graphics.Color
import android.os.Parcelable
import android.view.Gravity
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Unit
import kotlin.jvm.JvmSynthetic
import kotlinx.parcelize.Parcelize

/**
 * Shows the consolidated attribution control, a single pill combining the Mapbox logo, the data
 * attribution text, and a disclosure chevron.
 */
@MapboxExperimental
@Parcelize
public class ConsolidatedAttributionSettings private constructor(
  /**
   * Whether the consolidated attribution control is visible on the map. When enabled, it replaces
   * the separate Mapbox logo and attribution icon. Default value: false.
   */
  public val enabled: Boolean,
  /**
   * Defines where the consolidated attribution control is positioned on the map. Default value:
   * "bottom-left".
   */
  public val position: Int,
  /**
   * Defines the margin to the left that the consolidated attribution control honors. Default value:
   * 8. This property is specified in pixels.
   */
  public val marginLeft: Float,
  /**
   * Defines the margin to the top that the consolidated attribution control honors. Default value:
   * 8. This property is specified in pixels.
   */
  public val marginTop: Float,
  /**
   * Defines the margin to the right that the consolidated attribution control honors. Default
   * value: 8. This property is specified in pixels.
   */
  public val marginRight: Float,
  /**
   * Defines the margin to the bottom that the consolidated attribution control honors. Default
   * value: 8. This property is specified in pixels.
   */
  public val marginBottom: Float,
  /**
   * Defines how much of the consolidated attribution control remains visible once it collapses.
   * Default value: "icon-and-wordmark".
   */
  public val collapsedStyle: ConsolidatedAttributionCollapsedStyle,
  /**
   * Defines the material filling the background of the consolidated attribution control. Light and
   * dark rendering follows the night mode of the host configuration rather than this property. Default
   * value: "solid".
   */
  public val material: ConsolidatedAttributionMaterial,
  /**
   * Defines how often the consolidated attribution control plays its intro expansion. Default
   * value: "once".
   */
  public val introExpansion: ConsolidatedAttributionIntroExpansion,
  /**
   * Nudges the background of the consolidated attribution control toward the hue of this color,
   * bringing the control closer to the host application palette. Defaults to fully transparent, which
   * applies no tint. Only the hue is taken, so the background keeps the brightness and the
   * translucency selected by the material. The tint applies to the light appearance only, and the
   * Mapbox logo, the attribution text and the chevron are never tinted. Default value: "#00000000".
   */
  public val tintColor: Int
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """ConsolidatedAttributionSettings(enabled=$enabled,
      position=$position, marginLeft=$marginLeft, marginTop=$marginTop, marginRight=$marginRight,
      marginBottom=$marginBottom, collapsedStyle=$collapsedStyle, material=$material,
      introExpansion=$introExpansion, tintColor=$tintColor)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as ConsolidatedAttributionSettings
    return enabled == other.enabled && position == other.position &&
        marginLeft.compareTo(other.marginLeft) == 0 && marginTop.compareTo(other.marginTop) == 0 &&
        marginRight.compareTo(other.marginRight) == 0 &&
        marginBottom.compareTo(other.marginBottom) == 0 && collapsedStyle == other.collapsedStyle &&
        material == other.material && introExpansion == other.introExpansion &&
        tintColor == other.tintColor
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(enabled, position, marginLeft, marginTop,
      marginRight, marginBottom, collapsedStyle, material, introExpansion, tintColor)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder()
    .setEnabled(enabled)
    .setPosition(position)
    .setMarginLeft(marginLeft)
    .setMarginTop(marginTop)
    .setMarginRight(marginRight)
    .setMarginBottom(marginBottom)
    .setCollapsedStyle(collapsedStyle)
    .setMaterial(material)
    .setIntroExpansion(introExpansion)
    .setTintColor(tintColor)

  /**
   * Composes and builds a [ConsolidatedAttributionSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * Whether the consolidated attribution control is visible on the map. When enabled, it replaces
     * the separate Mapbox logo and attribution icon. Default value: false.
     */
    @set:JvmSynthetic
    public var enabled: Boolean = false

    /**
     * Defines where the consolidated attribution control is positioned on the map. Default value:
     * "bottom-left".
     */
    @set:JvmSynthetic
    public var position: Int = Gravity.BOTTOM or Gravity.START

    /**
     * Defines the margin to the left that the consolidated attribution control honors. Default
     * value: 8. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float = 8f

    /**
     * Defines the margin to the top that the consolidated attribution control honors. Default
     * value: 8. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float = 8f

    /**
     * Defines the margin to the right that the consolidated attribution control honors. Default
     * value: 8. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float = 8f

    /**
     * Defines the margin to the bottom that the consolidated attribution control honors. Default
     * value: 8. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float = 8f

    /**
     * Defines how much of the consolidated attribution control remains visible once it collapses.
     * Default value: "icon-and-wordmark".
     */
    @set:JvmSynthetic
    public var collapsedStyle: ConsolidatedAttributionCollapsedStyle =
        ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK

    /**
     * Defines the material filling the background of the consolidated attribution control. Light
     * and dark rendering follows the night mode of the host configuration rather than this property.
     * Default value: "solid".
     */
    @set:JvmSynthetic
    public var material: ConsolidatedAttributionMaterial = ConsolidatedAttributionMaterial.SOLID

    /**
     * Defines how often the consolidated attribution control plays its intro expansion. Default
     * value: "once".
     */
    @set:JvmSynthetic
    public var introExpansion: ConsolidatedAttributionIntroExpansion =
        ConsolidatedAttributionIntroExpansion.ONCE

    /**
     * Nudges the background of the consolidated attribution control toward the hue of this color,
     * bringing the control closer to the host application palette. Defaults to fully transparent,
     * which applies no tint. Only the hue is taken, so the background keeps the brightness and the
     * translucency selected by the material. The tint applies to the light appearance only, and the
     * Mapbox logo, the attribution text and the chevron are never tinted. Default value: "#00000000".
     */
    @set:JvmSynthetic
    public var tintColor: Int = Color.parseColor("#00000000")

    /**
     * Setter for enabled: whether the consolidated attribution control is visible on the map. When
     * enabled, it replaces the separate Mapbox logo and attribution icon. Default value: false.
     *
     * @param enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Setter for position: defines where the consolidated attribution control is positioned on the
     * map. Default value: "bottom-left".
     *
     * @param position
     * @return Builder
     */
    public fun setPosition(position: Int): Builder {
      this.position = position
      return this
    }

    /**
     * Setter for marginLeft: defines the margin to the left that the consolidated attribution
     * control honors. Default value: 8. This property is specified in pixels.
     *
     * @param marginLeft
     * @return Builder
     */
    public fun setMarginLeft(marginLeft: Float): Builder {
      this.marginLeft = marginLeft
      return this
    }

    /**
     * Setter for marginTop: defines the margin to the top that the consolidated attribution control
     * honors. Default value: 8. This property is specified in pixels.
     *
     * @param marginTop
     * @return Builder
     */
    public fun setMarginTop(marginTop: Float): Builder {
      this.marginTop = marginTop
      return this
    }

    /**
     * Setter for marginRight: defines the margin to the right that the consolidated attribution
     * control honors. Default value: 8. This property is specified in pixels.
     *
     * @param marginRight
     * @return Builder
     */
    public fun setMarginRight(marginRight: Float): Builder {
      this.marginRight = marginRight
      return this
    }

    /**
     * Setter for marginBottom: defines the margin to the bottom that the consolidated attribution
     * control honors. Default value: 8. This property is specified in pixels.
     *
     * @param marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Setter for collapsedStyle: defines how much of the consolidated attribution control remains
     * visible once it collapses. Default value: "icon-and-wordmark".
     *
     * @param collapsedStyle
     * @return Builder
     */
    public fun setCollapsedStyle(collapsedStyle: ConsolidatedAttributionCollapsedStyle): Builder {
      this.collapsedStyle = collapsedStyle
      return this
    }

    /**
     * Setter for material: defines the material filling the background of the consolidated
     * attribution control. Light and dark rendering follows the night mode of the host configuration
     * rather than this property. Default value: "solid".
     *
     * @param material
     * @return Builder
     */
    public fun setMaterial(material: ConsolidatedAttributionMaterial): Builder {
      this.material = material
      return this
    }

    /**
     * Setter for introExpansion: defines how often the consolidated attribution control plays its
     * intro expansion. Default value: "once".
     *
     * @param introExpansion
     * @return Builder
     */
    public fun setIntroExpansion(introExpansion: ConsolidatedAttributionIntroExpansion): Builder {
      this.introExpansion = introExpansion
      return this
    }

    /**
     * Setter for tintColor: nudges the background of the consolidated attribution control toward
     * the hue of this color, bringing the control closer to the host application palette. Defaults to
     * fully transparent, which applies no tint. Only the hue is taken, so the background keeps the
     * brightness and the translucency selected by the material. The tint applies to the light
     * appearance only, and the Mapbox logo, the attribution text and the chevron are never tinted.
     * Default value: "#00000000".
     *
     * @param tintColor
     * @return Builder
     */
    public fun setTintColor(tintColor: Int): Builder {
      this.tintColor = tintColor
      return this
    }

    /**
     * Returns a [ConsolidatedAttributionSettings] reference to the object being constructed by the
     * builder.
     *
     * @return ConsolidatedAttributionSettings
     */
    public fun build(): ConsolidatedAttributionSettings = ConsolidatedAttributionSettings(enabled,
        position, marginLeft, marginTop, marginRight, marginBottom, collapsedStyle, material,
        introExpansion, tintColor)
  }
}

/**
 * Creates a [ConsolidatedAttributionSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return ConsolidatedAttributionSettings
 */
@JvmSynthetic
public
    fun ConsolidatedAttributionSettings(initializer: ConsolidatedAttributionSettings.Builder.() -> Unit):
    ConsolidatedAttributionSettings =
    ConsolidatedAttributionSettings.Builder().apply(initializer).build()
