@file:Suppress("RedundantVisibilityModifier")

package com.mapbox.maps.plugin.scalebar.generated

import android.graphics.Color
import android.os.Parcelable
import android.view.Gravity
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.Unit
import kotlin.jvm.JvmSynthetic
import kotlinx.parcelize.Parcelize

/**
 * Shows the scale bar on the map.
 */
@Parcelize
public class ScaleBarSettings private constructor(
  /**
   * Whether the scale is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Defines where the scale bar is positioned on the map
   */
  public val position: Int,
  /**
   * Defines the margin to the left that the scale bar honors. This property is specified in pixels.
   */
  public val marginLeft: Float,
  /**
   * Defines the margin to the top that the scale bar honors. This property is specified in pixels.
   */
  public val marginTop: Float,
  /**
   * Defines the margin to the right that the scale bar honors. This property is specified in
   * pixels.
   */
  public val marginRight: Float,
  /**
   * Defines the margin to the bottom that the scale bar honors. This property is specified in
   * pixels.
   */
  public val marginBottom: Float,
  /**
   * Defines text color of the scale bar.
   */
  public val textColor: Int,
  /**
   * Defines primary color of the scale bar.
   */
  public val primaryColor: Int,
  /**
   * Defines secondary color of the scale bar.
   */
  public val secondaryColor: Int,
  /**
   * Defines width of the border for the scale bar. This property is specified in pixels.
   */
  public val borderWidth: Float,
  /**
   * Defines height of the scale bar. This property is specified in pixels.
   */
  public val height: Float,
  /**
   * Defines margin of the text bar of the scale bar. This property is specified in pixels.
   */
  public val textBarMargin: Float,
  /**
   * Defines text border width of the scale bar. This property is specified in pixels.
   */
  public val textBorderWidth: Float,
  /**
   * Defines text size of the scale bar. This property is specified in pixels.
   */
  public val textSize: Float,
  /**
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false
   * if the scale bar is using imperial units.
   */
  public val isMetricUnits: Boolean,
  /**
   * Configures minimum refresh interval, in millisecond, default is 15.
   */
  public val refreshInterval: Long,
  /**
   * Configures whether to show the text border or not, default is true.
   */
  public val showTextBorder: Boolean,
  /**
   * configures ratio of scale bar max width compared with MapView width, default is 0.5.
   */
  public val ratio: Float,
  /**
   * If set to True scale bar will be triggering onDraw depending on
   * [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to False scale bar
   * will redraw only on demand. Defaults to False and should not be changed explicitly in most cases.
   * Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  public val useContinuousRendering: Boolean
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """ScaleBarSettings(enabled=$enabled, position=$position,
      marginLeft=$marginLeft, marginTop=$marginTop, marginRight=$marginRight,
      marginBottom=$marginBottom, textColor=$textColor, primaryColor=$primaryColor,
      secondaryColor=$secondaryColor, borderWidth=$borderWidth, height=$height,
      textBarMargin=$textBarMargin, textBorderWidth=$textBorderWidth, textSize=$textSize,
      isMetricUnits=$isMetricUnits, refreshInterval=$refreshInterval,
      showTextBorder=$showTextBorder, ratio=$ratio,
      useContinuousRendering=$useContinuousRendering)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as ScaleBarSettings
    return enabled == other.enabled && position == other.position &&
        marginLeft.compareTo(other.marginLeft) == 0 && marginTop.compareTo(other.marginTop) == 0 &&
        marginRight.compareTo(other.marginRight) == 0 &&
        marginBottom.compareTo(other.marginBottom) == 0 && textColor == other.textColor &&
        primaryColor == other.primaryColor && secondaryColor == other.secondaryColor &&
        borderWidth.compareTo(other.borderWidth) == 0 && height.compareTo(other.height) == 0 &&
        textBarMargin.compareTo(other.textBarMargin) == 0 &&
        textBorderWidth.compareTo(other.textBorderWidth) == 0 &&
        textSize.compareTo(other.textSize) == 0 && isMetricUnits == other.isMetricUnits &&
        refreshInterval == other.refreshInterval && showTextBorder == other.showTextBorder &&
        ratio.compareTo(other.ratio) == 0 && useContinuousRendering == other.useContinuousRendering
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(enabled, position, marginLeft, marginTop,
      marginRight, marginBottom, textColor, primaryColor, secondaryColor, borderWidth, height,
      textBarMargin, textBorderWidth, textSize, isMetricUnits, refreshInterval, showTextBorder,
      ratio, useContinuousRendering)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder() .setEnabled(enabled) .setPosition(position)
      .setMarginLeft(marginLeft) .setMarginTop(marginTop) .setMarginRight(marginRight)
      .setMarginBottom(marginBottom) .setTextColor(textColor) .setPrimaryColor(primaryColor)
      .setSecondaryColor(secondaryColor) .setBorderWidth(borderWidth) .setHeight(height)
      .setTextBarMargin(textBarMargin) .setTextBorderWidth(textBorderWidth) .setTextSize(textSize)
      .setIsMetricUnits(isMetricUnits) .setRefreshInterval(refreshInterval)
      .setShowTextBorder(showTextBorder) .setRatio(ratio)
      .setUseContinuousRendering(useContinuousRendering)

  /**
   * Composes and builds a [ScaleBarSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * Whether the scale is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean = true

    /**
     * Defines where the scale bar is positioned on the map
     */
    @set:JvmSynthetic
    public var position: Int = Gravity.TOP or Gravity.START

    /**
     * Defines the margin to the left that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float = 4f

    /**
     * Defines the margin to the top that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float = 4f

    /**
     * Defines the margin to the right that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float = 4f

    /**
     * Defines the margin to the bottom that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float = 4f

    /**
     * Defines text color of the scale bar.
     */
    @set:JvmSynthetic
    public var textColor: Int = Color.BLACK

    /**
     * Defines primary color of the scale bar.
     */
    @set:JvmSynthetic
    public var primaryColor: Int = Color.BLACK

    /**
     * Defines secondary color of the scale bar.
     */
    @set:JvmSynthetic
    public var secondaryColor: Int = Color.WHITE

    /**
     * Defines width of the border for the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var borderWidth: Float = 2f

    /**
     * Defines height of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var height: Float = 2f

    /**
     * Defines margin of the text bar of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var textBarMargin: Float = 8f

    /**
     * Defines text border width of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var textBorderWidth: Float = 2f

    /**
     * Defines text size of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var textSize: Float = 8f

    /**
     * Whether the scale bar is using metric unit. True if the scale bar is using metric system,
     * false if the scale bar is using imperial units.
     */
    @set:JvmSynthetic
    public var isMetricUnits: Boolean = true

    /**
     * Configures minimum refresh interval, in millisecond, default is 15.
     */
    @set:JvmSynthetic
    public var refreshInterval: Long = 15

    /**
     * Configures whether to show the text border or not, default is true.
     */
    @set:JvmSynthetic
    public var showTextBorder: Boolean = true

    /**
     * configures ratio of scale bar max width compared with MapView width, default is 0.5.
     */
    @set:JvmSynthetic
    public var ratio: Float = 0.5f

    /**
     * If set to True scale bar will be triggering onDraw depending on
     * [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to False scale bar
     * will redraw only on demand. Defaults to False and should not be changed explicitly in most
     * cases. Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
     */
    @set:JvmSynthetic
    public var useContinuousRendering: Boolean = false

    /**
     * Setter for enabled: whether the scale is visible on the map.
     *
     * @param enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Setter for position: defines where the scale bar is positioned on the map.
     *
     * @param position
     * @return Builder
     */
    public fun setPosition(position: Int): Builder {
      this.position = position
      return this
    }

    /**
     * Setter for marginLeft: defines the margin to the left that the scale bar honors. This
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
     * Setter for marginTop: defines the margin to the top that the scale bar honors. This property
     * is specified in pixels.
     *
     * @param marginTop
     * @return Builder
     */
    public fun setMarginTop(marginTop: Float): Builder {
      this.marginTop = marginTop
      return this
    }

    /**
     * Setter for marginRight: defines the margin to the right that the scale bar honors. This
     * property is specified in pixels.
     *
     * @param marginRight
     * @return Builder
     */
    public fun setMarginRight(marginRight: Float): Builder {
      this.marginRight = marginRight
      return this
    }

    /**
     * Setter for marginBottom: defines the margin to the bottom that the scale bar honors. This
     * property is specified in pixels.
     *
     * @param marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Setter for textColor: defines text color of the scale bar.
     *
     * @param textColor
     * @return Builder
     */
    public fun setTextColor(textColor: Int): Builder {
      this.textColor = textColor
      return this
    }

    /**
     * Setter for primaryColor: defines primary color of the scale bar.
     *
     * @param primaryColor
     * @return Builder
     */
    public fun setPrimaryColor(primaryColor: Int): Builder {
      this.primaryColor = primaryColor
      return this
    }

    /**
     * Setter for secondaryColor: defines secondary color of the scale bar.
     *
     * @param secondaryColor
     * @return Builder
     */
    public fun setSecondaryColor(secondaryColor: Int): Builder {
      this.secondaryColor = secondaryColor
      return this
    }

    /**
     * Setter for borderWidth: defines width of the border for the scale bar. This property is
     * specified in pixels.
     *
     * @param borderWidth
     * @return Builder
     */
    public fun setBorderWidth(borderWidth: Float): Builder {
      this.borderWidth = borderWidth
      return this
    }

    /**
     * Setter for height: defines height of the scale bar. This property is specified in pixels.
     *
     * @param height
     * @return Builder
     */
    public fun setHeight(height: Float): Builder {
      this.height = height
      return this
    }

    /**
     * Setter for textBarMargin: defines margin of the text bar of the scale bar. This property is
     * specified in pixels.
     *
     * @param textBarMargin
     * @return Builder
     */
    public fun setTextBarMargin(textBarMargin: Float): Builder {
      this.textBarMargin = textBarMargin
      return this
    }

    /**
     * Setter for textBorderWidth: defines text border width of the scale bar. This property is
     * specified in pixels.
     *
     * @param textBorderWidth
     * @return Builder
     */
    public fun setTextBorderWidth(textBorderWidth: Float): Builder {
      this.textBorderWidth = textBorderWidth
      return this
    }

    /**
     * Setter for textSize: defines text size of the scale bar. This property is specified in
     * pixels.
     *
     * @param textSize
     * @return Builder
     */
    public fun setTextSize(textSize: Float): Builder {
      this.textSize = textSize
      return this
    }

    /**
     * Setter for isMetricUnits: whether the scale bar is using metric unit. True if the scale bar
     * is using metric system, false if the scale bar is using imperial units.
     *
     * @param isMetricUnits
     * @return Builder
     */
    public fun setIsMetricUnits(isMetricUnits: Boolean): Builder {
      this.isMetricUnits = isMetricUnits
      return this
    }

    /**
     * Setter for refreshInterval: configures minimum refresh interval, in millisecond, default is
     * 15.
     *
     * @param refreshInterval
     * @return Builder
     */
    public fun setRefreshInterval(refreshInterval: Long): Builder {
      this.refreshInterval = refreshInterval
      return this
    }

    /**
     * Setter for showTextBorder: configures whether to show the text border or not, default is
     * true.
     *
     * @param showTextBorder
     * @return Builder
     */
    public fun setShowTextBorder(showTextBorder: Boolean): Builder {
      this.showTextBorder = showTextBorder
      return this
    }

    /**
     * Setter for ratio: configures ratio of scale bar max width compared with MapView width,
     * default is 0.5.
     *
     * @param ratio
     * @return Builder
     */
    public fun setRatio(ratio: Float): Builder {
      this.ratio = ratio
      return this
    }

    /**
     * Setter for useContinuousRendering: if set to True scale bar will be triggering onDraw
     * depending on [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to
     * False scale bar will redraw only on demand. Defaults to False and should not be changed
     * explicitly in most cases. Could be set to True to produce correct GPU frame metrics when running
     * gfxinfo command.
     *
     * @param useContinuousRendering
     * @return Builder
     */
    public fun setUseContinuousRendering(useContinuousRendering: Boolean): Builder {
      this.useContinuousRendering = useContinuousRendering
      return this
    }

    /**
     * Returns a [ScaleBarSettings] reference to the object being constructed by the builder.
     *
     * @return ScaleBarSettings
     */
    public fun build(): ScaleBarSettings = ScaleBarSettings(enabled, position, marginLeft,
        marginTop, marginRight, marginBottom, textColor, primaryColor, secondaryColor, borderWidth,
        height, textBarMargin, textBorderWidth, textSize, isMetricUnits, refreshInterval,
        showTextBorder, ratio, useContinuousRendering)
  }
}

/**
 * Creates a [ScaleBarSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return ScaleBarSettings
 */
@JvmSynthetic
public fun ScaleBarSettings(initializer: ScaleBarSettings.Builder.() -> Unit): ScaleBarSettings =
    ScaleBarSettings.Builder().apply(initializer).build()
