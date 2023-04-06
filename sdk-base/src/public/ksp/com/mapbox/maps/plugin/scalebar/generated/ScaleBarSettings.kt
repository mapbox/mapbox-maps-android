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
   * Enabled
   * Whether the scale is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Position
   * Defines where the scale bar is positioned on the map
   */
  public val position: Int,
  /**
   * MarginLeft
   * Defines the margin to the left that the scale bar honors. This property is specified in pixels.
   */
  public val marginLeft: Float,
  /**
   * MarginTop
   * Defines the margin to the top that the scale bar honors. This property is specified in pixels.
   */
  public val marginTop: Float,
  /**
   * MarginRight
   * Defines the margin to the right that the scale bar honors. This property is specified in
   * pixels.
   */
  public val marginRight: Float,
  /**
   * MarginBottom
   * Defines the margin to the bottom that the scale bar honors. This property is specified in
   * pixels.
   */
  public val marginBottom: Float,
  /**
   * TextColor
   * Defines text color of the scale bar.
   */
  public val textColor: Int,
  /**
   * PrimaryColor
   * Defines primary color of the scale bar.
   */
  public val primaryColor: Int,
  /**
   * SecondaryColor
   * Defines secondary color of the scale bar.
   */
  public val secondaryColor: Int,
  /**
   * BorderWidth
   * Defines width of the border for the scale bar. This property is specified in pixels.
   */
  public val borderWidth: Float,
  /**
   * Height
   * Defines height of the scale bar. This property is specified in pixels.
   */
  public val height: Float,
  /**
   * TextBarMargin
   * Defines margin of the text bar of the scale bar. This property is specified in pixels.
   */
  public val textBarMargin: Float,
  /**
   * TextBorderWidth
   * Defines text border width of the scale bar. This property is specified in pixels.
   */
  public val textBorderWidth: Float,
  /**
   * TextSize
   * Defines text size of the scale bar. This property is specified in pixels.
   */
  public val textSize: Float,
  /**
   * IsMetricUnits
   * Whether the scale bar is using metric unit. True if the scale bar is using metric system, false
   * if the scale bar is using imperial units.
   */
  public val isMetricUnits: Boolean,
  /**
   * RefreshInterval
   * Configures minimum refresh interval, in millisecond, default is 15.
   */
  public val refreshInterval: Long,
  /**
   * ShowTextBorder
   * Configures whether to show the text border or not, default is true.
   */
  public val showTextBorder: Boolean,
  /**
   * Ratio
   * configures ratio of scale bar max width compared with MapView width, default is 0.5.
   */
  public val ratio: Float,
  /**
   * UseContinuousRendering
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
        marginLeft == other.marginLeft && marginTop == other.marginTop &&
        marginRight == other.marginRight && marginBottom == other.marginBottom &&
        textColor == other.textColor && primaryColor == other.primaryColor &&
        secondaryColor == other.secondaryColor && borderWidth == other.borderWidth &&
        height == other.height && textBarMargin == other.textBarMargin &&
        textBorderWidth == other.textBorderWidth && textSize == other.textSize &&
        isMetricUnits == other.isMetricUnits && refreshInterval == other.refreshInterval &&
        showTextBorder == other.showTextBorder && ratio == other.ratio &&
        useContinuousRendering == other.useContinuousRendering
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
     * Enabled
     * Whether the scale is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean? = true

    /**
     * Position
     * Defines where the scale bar is positioned on the map
     */
    @set:JvmSynthetic
    public var position: Int? = Gravity.TOP or Gravity.START

    /**
     * MarginLeft
     * Defines the margin to the left that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float? = 4f

    /**
     * MarginTop
     * Defines the margin to the top that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float? = 4f

    /**
     * MarginRight
     * Defines the margin to the right that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float? = 4f

    /**
     * MarginBottom
     * Defines the margin to the bottom that the scale bar honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float? = 4f

    /**
     * TextColor
     * Defines text color of the scale bar.
     */
    @set:JvmSynthetic
    public var textColor: Int? = Color.BLACK

    /**
     * PrimaryColor
     * Defines primary color of the scale bar.
     */
    @set:JvmSynthetic
    public var primaryColor: Int? = Color.BLACK

    /**
     * SecondaryColor
     * Defines secondary color of the scale bar.
     */
    @set:JvmSynthetic
    public var secondaryColor: Int? = Color.WHITE

    /**
     * BorderWidth
     * Defines width of the border for the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var borderWidth: Float? = 2f

    /**
     * Height
     * Defines height of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var height: Float? = 2f

    /**
     * TextBarMargin
     * Defines margin of the text bar of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var textBarMargin: Float? = 8f

    /**
     * TextBorderWidth
     * Defines text border width of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var textBorderWidth: Float? = 2f

    /**
     * TextSize
     * Defines text size of the scale bar. This property is specified in pixels.
     */
    @set:JvmSynthetic
    public var textSize: Float? = 8f

    /**
     * IsMetricUnits
     * Whether the scale bar is using metric unit. True if the scale bar is using metric system,
     * false if the scale bar is using imperial units.
     */
    @set:JvmSynthetic
    public var isMetricUnits: Boolean? = true

    /**
     * RefreshInterval
     * Configures minimum refresh interval, in millisecond, default is 15.
     */
    @set:JvmSynthetic
    public var refreshInterval: Long? = 15

    /**
     * ShowTextBorder
     * Configures whether to show the text border or not, default is true.
     */
    @set:JvmSynthetic
    public var showTextBorder: Boolean? = true

    /**
     * Ratio
     * configures ratio of scale bar max width compared with MapView width, default is 0.5.
     */
    @set:JvmSynthetic
    public var ratio: Float? = 0.5f

    /**
     * UseContinuousRendering
     * If set to True scale bar will be triggering onDraw depending on
     * [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to False scale bar
     * will redraw only on demand. Defaults to False and should not be changed explicitly in most
     * cases. Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
     */
    @set:JvmSynthetic
    public var useContinuousRendering: Boolean? = false

    /**
     * Set enabled
     * Whether the scale is visible on the map.
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
     * Defines where the scale bar is positioned on the map
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
     * Defines the margin to the left that the scale bar honors. This property is specified in
     * pixels.
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
     * Defines the margin to the top that the scale bar honors. This property is specified in
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
     * Defines the margin to the right that the scale bar honors. This property is specified in
     * pixels.
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
     * Defines the margin to the bottom that the scale bar honors. This property is specified in
     * pixels.
     *
     * @param marginBottom marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float?): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Set textColor
     * Defines text color of the scale bar.
     *
     * @param textColor textColor
     * @return Builder
     */
    public fun setTextColor(textColor: Int?): Builder {
      this.textColor = textColor
      return this
    }

    /**
     * Set primaryColor
     * Defines primary color of the scale bar.
     *
     * @param primaryColor primaryColor
     * @return Builder
     */
    public fun setPrimaryColor(primaryColor: Int?): Builder {
      this.primaryColor = primaryColor
      return this
    }

    /**
     * Set secondaryColor
     * Defines secondary color of the scale bar.
     *
     * @param secondaryColor secondaryColor
     * @return Builder
     */
    public fun setSecondaryColor(secondaryColor: Int?): Builder {
      this.secondaryColor = secondaryColor
      return this
    }

    /**
     * Set borderWidth
     * Defines width of the border for the scale bar. This property is specified in pixels.
     *
     * @param borderWidth borderWidth
     * @return Builder
     */
    public fun setBorderWidth(borderWidth: Float?): Builder {
      this.borderWidth = borderWidth
      return this
    }

    /**
     * Set height
     * Defines height of the scale bar. This property is specified in pixels.
     *
     * @param height height
     * @return Builder
     */
    public fun setHeight(height: Float?): Builder {
      this.height = height
      return this
    }

    /**
     * Set textBarMargin
     * Defines margin of the text bar of the scale bar. This property is specified in pixels.
     *
     * @param textBarMargin textBarMargin
     * @return Builder
     */
    public fun setTextBarMargin(textBarMargin: Float?): Builder {
      this.textBarMargin = textBarMargin
      return this
    }

    /**
     * Set textBorderWidth
     * Defines text border width of the scale bar. This property is specified in pixels.
     *
     * @param textBorderWidth textBorderWidth
     * @return Builder
     */
    public fun setTextBorderWidth(textBorderWidth: Float?): Builder {
      this.textBorderWidth = textBorderWidth
      return this
    }

    /**
     * Set textSize
     * Defines text size of the scale bar. This property is specified in pixels.
     *
     * @param textSize textSize
     * @return Builder
     */
    public fun setTextSize(textSize: Float?): Builder {
      this.textSize = textSize
      return this
    }

    /**
     * Set isMetricUnits
     * Whether the scale bar is using metric unit. True if the scale bar is using metric system,
     * false if the scale bar is using imperial units.
     *
     * @param isMetricUnits isMetricUnits
     * @return Builder
     */
    public fun setIsMetricUnits(isMetricUnits: Boolean?): Builder {
      this.isMetricUnits = isMetricUnits
      return this
    }

    /**
     * Set refreshInterval
     * Configures minimum refresh interval, in millisecond, default is 15.
     *
     * @param refreshInterval refreshInterval
     * @return Builder
     */
    public fun setRefreshInterval(refreshInterval: Long?): Builder {
      this.refreshInterval = refreshInterval
      return this
    }

    /**
     * Set showTextBorder
     * Configures whether to show the text border or not, default is true.
     *
     * @param showTextBorder showTextBorder
     * @return Builder
     */
    public fun setShowTextBorder(showTextBorder: Boolean?): Builder {
      this.showTextBorder = showTextBorder
      return this
    }

    /**
     * Set ratio
     * configures ratio of scale bar max width compared with MapView width, default is 0.5.
     *
     * @param ratio ratio
     * @return Builder
     */
    public fun setRatio(ratio: Float?): Builder {
      this.ratio = ratio
      return this
    }

    /**
     * Set useContinuousRendering
     * If set to True scale bar will be triggering onDraw depending on
     * [ScaleBarSettings.refreshInterval] even if actual data did not change. If set to False scale bar
     * will redraw only on demand. Defaults to False and should not be changed explicitly in most
     * cases. Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
     *
     * @param useContinuousRendering useContinuousRendering
     * @return Builder
     */
    public fun setUseContinuousRendering(useContinuousRendering: Boolean?): Builder {
      this.useContinuousRendering = useContinuousRendering
      return this
    }

    /**
     * Returns a [ScaleBarSettings] reference to the object being constructed by the builder.
     *
     * Throws an [IllegalArgumentException] when a non-null property wasn't initialised.
     *
     * @return ScaleBarSettings
     */
    public fun build(): ScaleBarSettings {
      if (enabled==null) {
      	throw IllegalArgumentException("""Null enabled found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (position==null) {
      	throw IllegalArgumentException("""Null position found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (marginLeft==null) {
      	throw IllegalArgumentException("""Null marginLeft found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (marginTop==null) {
      	throw IllegalArgumentException("""Null marginTop found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (marginRight==null) {
      	throw IllegalArgumentException("""Null marginRight found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (marginBottom==null) {
      	throw IllegalArgumentException("""Null marginBottom found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (textColor==null) {
      	throw IllegalArgumentException("""Null textColor found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (primaryColor==null) {
      	throw IllegalArgumentException("""Null primaryColor found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (secondaryColor==null) {
      	throw IllegalArgumentException("""Null secondaryColor found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (borderWidth==null) {
      	throw IllegalArgumentException("""Null borderWidth found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (height==null) {
      	throw IllegalArgumentException("""Null height found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (textBarMargin==null) {
      	throw IllegalArgumentException("""Null textBarMargin found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (textBorderWidth==null) {
      	throw IllegalArgumentException("""Null textBorderWidth found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (textSize==null) {
      	throw IllegalArgumentException("""Null textSize found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (isMetricUnits==null) {
      	throw IllegalArgumentException("""Null isMetricUnits found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (refreshInterval==null) {
      	throw IllegalArgumentException("""Null refreshInterval found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (showTextBorder==null) {
      	throw IllegalArgumentException("""Null showTextBorder found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (ratio==null) {
      	throw IllegalArgumentException("""Null ratio found when building
          ScaleBarSettings.""".trimIndent())
      }
      if (useContinuousRendering==null) {
      	throw IllegalArgumentException("""Null useContinuousRendering found when building
          ScaleBarSettings.""".trimIndent())
      }
      return ScaleBarSettings(enabled!!, position!!, marginLeft!!, marginTop!!, marginRight!!,
          marginBottom!!, textColor!!, primaryColor!!, secondaryColor!!, borderWidth!!, height!!,
          textBarMargin!!, textBorderWidth!!, textSize!!, isMetricUnits!!, refreshInterval!!,
          showTextBorder!!, ratio!!, useContinuousRendering!!)
    }
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
