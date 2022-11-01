package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental
import kotlin.math.abs

/**
 * Specifies widget position relative to the screen.
 *
 * @property horizontal position relative to the screen.
 * @property vertical position relative to screen.
 * @property offsetX horizontal offset of the widget.
 * @property offsetY vertical offset of the widget.
 */
@MapboxExperimental
class WidgetPosition private constructor(
  val horizontal: Horizontal,
  val vertical: Vertical,
  val offsetX: Float,
  val offsetY: Float
) {
  @Deprecated(
    message = "Direct constructor of WidgetPosition is deprecated, and might be removed in future releases.",
    replaceWith = ReplaceWith("WidgetPosition.Builder()")
  )
  constructor(horizontal: Horizontal, vertical: Vertical) : this (horizontal, vertical, 0f, 0f)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "WidgetPosition(horizontal=$horizontal,vertical=$vertical,offsetX=$offsetX,offsetY=$offsetY"

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is WidgetPosition &&
    horizontal == other.horizontal &&
    vertical == other.vertical &&
    abs(offsetX - other.offsetX) < EPS &&
    abs(offsetY - other.offsetY) < EPS

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    var result = 7
    result = 31 * result + horizontal.hashCode()
    result = 31 * result + vertical.hashCode()
    result = 31 * result + offsetX.hashCode()
    result = 31 * result + offsetY.hashCode()
    return result
  }

  /**
   * Builder for WidgetPosition class.
   *
   * @property horizontal position relative to the screen, defaults to [Horizontal.LEFT].
   * @property vertical position relative to screen, defaults to [Vertical.TOP].
   * @property offsetX horizontal offset of the widget, defaults to 0.
   * @property offsetY vertical offset of the widget, defaults to 0.
   */
  class Builder {
    @set:JvmSynthetic
    var horizontal: Horizontal = Horizontal.LEFT

    @set:JvmSynthetic
    var vertical: Vertical = Vertical.TOP

    @set:JvmSynthetic
    var offsetX: Float = 0f

    @set:JvmSynthetic
    var offsetY: Float = 0f

    /**
     * Set the horizontal position, defaults to [Horizontal.LEFT].
     */
    fun setHorizontal(horizontal: Horizontal) = apply { this.horizontal = horizontal }

    /**
     * Set the vertical position, defaults to [Vertical.TOP].
     */
    fun setVertical(vertical: Vertical) = apply { this.vertical = vertical }

    /**
     * Set the offset along X axis, defaults to 0.
     */
    fun setOffsetX(offsetX: Float) = apply { this.offsetX = offsetX }

    /**
     * Set the offset along Y axis, defaults to 0.
     */
    fun setOffsetY(offsetY: Float) = apply { this.offsetY = offsetY }

    /**
     * Build the [WidgetPosition] from the current settings.
     */
    fun build() = WidgetPosition(horizontal, vertical, offsetX, offsetY)
  }

  /**
   * Widget position Horizontal
   */
  enum class Horizontal {
    /**
     * Left Horizontal Position
     */
    LEFT,

    /**
     * Center Horizontal Position
     */
    CENTER,

    /**
     * Right Horizontal Position
     */
    RIGHT,
  }

  /**
   * Widget position Vertical
   */
  enum class Vertical {
    /**
     * Top Vertical Position
     */
    TOP,

    /**
     * Center Vertical Position
     */
    CENTER,

    /**
     * Bottom Vertical Position
     */
    BOTTOM,
  }

  private companion object {
    private const val EPS = 1E-5f
  }
}

/**
 * Creates a [WidgetPosition] through a DSL builder.
 *
 * @param initializer the initialisation block
 * @return [WidgetPosition]
 */
@JvmSynthetic
fun WidgetPosition(initializer: WidgetPosition.Builder.() -> Unit): WidgetPosition {
  return WidgetPosition.Builder().apply(initializer).build()
}